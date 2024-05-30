/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.esolver;

import com.google.common.util.concurrent.AtomicDouble;
import rc.so.atlante.Atl_dati_clienti;
import rc.so.atlante.Atl_dati_fatture;
import rc.so.atlante.Atl_details_dati_fatture;
import rc.so.entity.Branch;
import static rc.so.esolver.Client_es.tag_GEN;
import static rc.so.esolver.Client_es.tag_RIG;
import static rc.so.esolver.Client_es.tag_TES;
import static rc.so.esolver.Client_es.separator;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatStringtoStringDate;
import static rc.so.util.Utility.roundDoubleandFormat;
import static rc.so.util.Utility.verificaClientNumber;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Constant.patternnormdate_filter;
import static rc.so.util.Engine.insertTR;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.apache.commons.lang3.StringUtils.replace;

/**
 *
 * @author rcosco
 */
public class Client_at {

    private final String mastroCLienti = "110303";
    private static final String scontrino_AG_ATL = "703";
    private static final String tag_IVA = "IVA";

    private List<Atl_dati_clienti> dc_removeBT1(List<Atl_dati_clienti> daticlienti) {
        List<Atl_dati_clienti> out = new ArrayList<>();
        daticlienti.forEach(df -> {
            boolean is_BT = df.getClientcode().startsWith("010 1 1") || df.getClientcode().startsWith("010 8 1");
            if (!is_BT) {
                out.add(df);
            }
        });
        return out;
    }

    private boolean is_BT1(List<Atl_dati_fatture> datifattura, List<Atl_dati_clienti> daticlienti) {
        AtomicInteger in = new AtomicInteger(0);
        if (datifattura != null) {
            datifattura.forEach(incassiATL -> {
                incassiATL.getDetails().forEach(detREG -> {
                    boolean is_BT = detREG.getContoreg().startsWith("010 1 1") || detREG.getContoreg().startsWith("010 8 1");
                    if (is_BT) {
                        in.addAndGet(1);
                    }
                });
            });
        }
        if (daticlienti != null) {
            daticlienti.forEach(df -> {

                boolean is_BT = df.getClientcode().startsWith("010 1 1") || df.getClientcode().startsWith("010 8 1");
                if (is_BT) {
                    in.addAndGet(1);
                    daticlienti.remove(in.get());
                }
            });
        }
        return in.get() > 0;
    }

    //ATLANTE

    /**
     *
     * @param path
     * @param data
     * @param anno
     * @param filiale
     * @param datifattura
     * @return
     */
    public File FILEP2A(String path, String data, String anno, Branch filiale,
            List<Atl_dati_fatture> datifattura) {

        if (datifattura.isEmpty()) {
            return null;
        }
        try {
            File f = new File(path + filiale.getCod() + "_" + replace(data, "/", "") + "_P2A_eSol.txt");
            AtomicInteger nreg;
            try (PrintWriter writer = new PrintWriter(f)) {
                nreg = new AtomicInteger(1);
                datifattura.forEach(cor1 -> {
                    String d1 = formatStringtoStringDate(cor1.getDatereg(), patternsqldate, patternnormdate_filter);
                    writer.println(tag_TES + separator + scontrino_AG_ATL + separator + anno
                            + separator + d1 + separator + nreg.get() + separator + separator
                            + separator + separator + separator + separator
                            + separator + separator + separator + separator + separator + separator
                            + separator + "CORRISPETTIVI " + filiale.getDe_branch() + separator);
                    AtomicDouble total = new AtomicDouble(0.0);
                    AtomicDouble total2 = new AtomicDouble(0.0);
                    AtomicDouble imponibile = new AtomicDouble(0.0);
                    AtomicDouble imposta = new AtomicDouble(0.0);
                    AtomicDouble importo = new AtomicDouble(0.0);
                    StringBuilder conto_esover = new StringBuilder("");
                    StringBuilder codiceiva = new StringBuilder("");
                    StringBuilder conto_cassa = new StringBuilder("");
                    cor1.getDetails().forEach(details_N -> {
                        if (details_N.getSegnoreg().equals("D")) {
                            conto_cassa.append(details_N.getContoreg());
                            importo.addAndGet(fd(details_N.getImporto()));
                            total.addAndGet(fd(details_N.getImporto()));
                        } else if (details_N.getTipoconto().equals("G")) {
                            conto_esover.append(details_N.getContoreg());
                            imponibile.addAndGet(fd(details_N.getImporto()));
                            codiceiva.append(details_N.getIvareg());
                            total2.addAndGet(fd(details_N.getImporto()));
                        } else if (details_N.getTipoconto().equals("I")) {
                            imposta.addAndGet(fd(details_N.getImporto()));
                            total2.addAndGet(fd(details_N.getImporto()));
                        }
                    }); if (total.get() == 0) {
                        total.addAndGet(total2.get());
                    }   String[] conti = {conto_esover.toString(), conto_cassa.toString()};
                    if (total.get() < 0) {
                        writer.println(tag_RIG
                                + separator + scontrino_AG_ATL + separator + anno + separator + d1
                                + separator + nreg.get() + separator + conti[0]
                                + separator + replace(roundDoubleandFormat(total.get(), 2), ".", ",") + separator + codiceiva.toString() + separator + replace(roundDoubleandFormat(imponibile.get(), 2), ".", ",") + separator + replace(roundDoubleandFormat(imposta.get(), 2), ".", ",") + separator + filiale.getCod() + separator + "CORRISPETTIVI " + filiale.getDe_branch() + " ATLANTE" + separator + separator + separator + separator + separator + separator + separator);
                        if (importo.get() != 0) {
                            writer.println(tag_GEN + separator + scontrino_AG_ATL + separator + anno + separator + d1 + separator
                                    + nreg.get() + separator + separator + separator + separator + separator + separator
                                    + separator + separator + conti[1] + separator
                                    + filiale.getCod() + separator + separator + replace(roundDoubleandFormat(importo.get() * -1.00, 2), ".", ",") + separator + "INCASSO CORRISPETTIVI ATLANTE" + separator + separator + filiale.getCod());
                        }
//                    RIG;703;2019;25/06/2019;8; 188 1 1;-30,00;75;-30,00;0,00;079;CORRISPETTIVI Milano Duomo ATLANTE;;;;;;;
//                    GEN;703;2019;25/06/2019;8;;;;;;;; 0 6 1 2     3;079;; 30,00;INCASSO CORRISPETTIVI ATLANTE;;079
//                    
//                    RIG;703;2019;25/06/2019;6;188 1 1;-30,00;75;-30,00;0,00;079;CORRISPETTIVI Milano Duomo ATLANTE;;;;;;;
//                    GEN;703;2019;25/06/2019;6;;;;;;;;0 6 1 2     3;079;-30,00;; raf - INCASSO CORRISPETTIVI ATLANTE;;079
                    } else {
                        //CASO NORMALE
                        writer.println(tag_RIG
                                + separator + scontrino_AG_ATL + separator + anno + separator + d1
                                + separator + nreg.get() + separator + conti[0]
                                + separator + replace(roundDoubleandFormat(total.get(), 2), ".", ",") + separator + codiceiva.toString() + separator + replace(roundDoubleandFormat(imponibile.get(), 2), ".", ",") + separator + replace(roundDoubleandFormat(imposta.get(), 2), ".", ",") + separator + filiale.getCod() + separator + "CORRISPETTIVI " + filiale.getDe_branch() + " ATLANTE" + separator + separator + separator + separator + separator + separator + separator);
                        if (importo.get() != 0) {
                            writer.println(tag_GEN + separator + scontrino_AG_ATL + separator + anno + separator + d1 + separator
                                    + nreg.get() + separator + separator + separator + separator + separator + separator
                                    + separator + separator + conti[1] + separator
                                    + filiale.getCod() + separator + replace(roundDoubleandFormat(importo.get(), 2), ".", ",") + separator + separator + "INCASSO CORRISPETTIVI ATLANTE" + separator + separator + filiale.getCod());
                        }
                    }
                    nreg.addAndGet(1);
                });
            }

            if (nreg.get() > 1) {
                return f;
            }
            f.delete();
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;

    }

    /////////

    /**
     *
     * @param path
     * @param data
     * @param anno
     * @param filiale
     * @param datifattura
     * @return
     */
    public File FILEP4A(String path, String data, String anno, Branch filiale,
            List<Atl_dati_fatture> datifattura) {

        if (datifattura.isEmpty()) {
            return null;
        }

        try {

//            List<String> branchList_BT = branchList_BT();
            File f = new File(path + filiale.getCod() + "_" + replace(data, "/", "") + "_P4A_eSol.txt");
            AtomicInteger integ;
            try (PrintWriter writer = new PrintWriter(f)) {
                integ = new AtomicInteger(1);
                datifattura.forEach(incassiATL -> {
                    incassiATL.getDetails().forEach(detREG -> {
                        String d1 = formatStringtoStringDate(incassiATL.getDatereg(), patternsqldate, patternnormdate_filter);
                        if (detREG.getCategory().equals("TUR")) {
                            String importo = replace(detREG.getImporto(), ".", ",");
                            importo = importo.replace("-", "");
                            String valore[] = {importo.trim(), ""};
                            if (!detREG.getSegnoreg().equals("D")) {
                                valore = new String[2];
                                valore[0] = "";
                                valore[1] = importo.trim();
                                if (fd(detREG.getImporto()) < 0) {
                                    valore[0] = importo.trim();
                                    valore[1] = "";
                                }
                            }   String[] conti = {detREG.getContoreg(), ""};
//                        if (detREG.getContoreg().startsWith("010")) {
//                            if (!branchList_BT.contains(filiale.getCod())) {
//                                conti[0] = mastroCLienti;
//                                conti[1] = detREG.getContoreg();
//                            }
//                        }
writer.println(tag_GEN + separator + "TUR" + separator + anno + separator + d1 + separator + integ.get() + separator
        + conti[0] + separator + incassiATL.getBranchid() + separator + conti[1] + separator
        + valore[0] + separator + valore[1] + separator
        + incassiATL.getBranchid() + separator + detREG.getDesc().toUpperCase());
                        } else {
                            String[] conti = {detREG.getContoreg(), ""};
                            if (detREG.getContoreg().startsWith("010")) {
//                            String contosenzaspazi = StringUtils.deleteWhitespace(detREG.getContoreg());
boolean is_BT = detREG.getContoreg().startsWith("010 1 1") || detREG.getContoreg().startsWith("010 8 1");
if (!is_BT) {
//                            if (!branchList_BT.contains(filiale.getCod())) {
conti[0] = mastroCLienti;
conti[1] = detREG.getContoreg();
}
                            }   if (fd(detREG.getImporto()) < 0) {
                                insertTR("W", "System", f.getName() + " - LINEA " + integ.get() + " INVERTO LE POSIZIONI E METTO IMPORTI POSITIVI");
                            }   if (detREG.getSegnoreg().equals("D")) {
                                String importo = replace(detREG.getImporto(), ".", ",");
                                importo = importo.replace("-", "");
                                String valore[] = {importo.trim(), ""};
                                if (fd(detREG.getImporto()) < 0) {
                                    valore[0] = "";
                                    valore[1] = importo.trim();
                                }   writer.println(tag_GEN + separator + "IAT" + separator + anno + separator
                                        + d1 + separator + integ.get() + separator
                                        + conti[0] + separator + incassiATL.getBranchid() + separator + conti[1] + separator
                                        + valore[0] + separator + valore[1] + separator
                                        + incassiATL.getBranchid() + separator + detREG.getDesc().toUpperCase());
                            } else {
                                String importo = replace(detREG.getImporto(), ".", ",");
                                importo = importo.replace("-", "");
                                String valore[] = {"", importo.trim()};
                                if (fd(detREG.getImporto()) < 0) {
                                    valore[0] = importo.trim();
                                    valore[1] = "";
                                }   writer.println(tag_GEN + separator + "IAT" + separator + anno + separator + d1 + separator + integ.get() + separator
                                        + conti[0] + separator + incassiATL.getBranchid() + separator + conti[1] + separator
                                        + valore[0] + separator + valore[1] + separator
                                        + incassiATL.getBranchid() + separator + detREG.getDesc().toUpperCase());
                            }
                        }
                    });
                    integ.addAndGet(1);
                });
            }
            if (integ.get() > 1) {
                return f;
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /////////

    /**
     *
     * @param path
     * @param data
     * @param filiale
     * @param datifattura
     * @return
     */
    public File FILEP6A(String path, String data, Branch filiale,
            List<Atl_dati_fatture> datifattura) {

        if (datifattura.isEmpty()) {
            return null;
        }

        try {

            boolean is_BT = is_BT1(datifattura, null);

//            List<String> branchList_BT = branchList_BT();
            File f = new File(path + filiale.getCod() + "_" + replace(data, "/", "") + "_P6_eSol.txt");
            try (PrintWriter writer = new PrintWriter(f)) {
                String tiporiga = "30";
                datifattura.forEach(df -> {
                    StringBuilder desc = new StringBuilder("");
                    StringBuilder starttipodoc = new StringBuilder("");
                    Atl_details_dati_fatture matchingObject = df.getDetails().get(0);
                    if (matchingObject != null) {
                        String contocliente = matchingObject.getContoreg();
                        String causaletestata = matchingObject.getDesc();
                        if (df.getTipomov().equals("F")) {
                            desc.append("Fattura Fiscale numero " + df.getNumreg() + " del "
                                    + formatStringtoStringDate(df.getDatereg(), patternsqldate, patternnormdate_filter));
                        } else if (df.getTipomov().equals("N")) {
                            desc.append("Nota di credito Fiscale numero " + df.getNumreg() + " del "
                                    + formatStringtoStringDate(df.getDatereg(), patternsqldate, patternnormdate_filter));
                            starttipodoc.append("NC");
                        }   Tipodoc tipodoc = new Tipodoc(starttipodoc.toString() + "710");
                        if (df.getSezionale().equalsIgnoreCase("V02")) {
                            tipodoc.setId(starttipodoc.toString() + "TER");
                        } else {
                            
                            if (df.getBranchid().equals("AMM") || is_BT) {
//                        if (df.getBranchid().equals("AMM") || branchList_BT.contains(filiale.getCod())) {
tipodoc.setId(starttipodoc.toString() + "BTR");
                            } else {
                                tipodoc.setId(starttipodoc.toString() + "FTV");
                            }
                        }   //                    if (df.getBranchid().equals("AMM") || df.getBranchid().equals("191")) {
//                    if (df.getBranchid().equals("AMM") || branchList_BT.contains(filiale.getCod())) {
//                        tipodoc.setId(starttipodoc.toString() + "BTR");
//                    } else if (df.getSezionale().equalsIgnoreCase("V01")) {
//                        tipodoc.setId(starttipodoc.toString() + "FTV");
//                    } else if (df.getSezionale().equalsIgnoreCase("V02")) {
//                        tipodoc.setId(starttipodoc.toString() + "TER");
//                    }
String TES = tag_TES + separator
        + tipodoc.getId() + separator
        + formatStringtoStringDate(df.getDatereg(), patternsqldate, patternnormdate_filter) + separator
        + df.getNumreg() + separator
        + contocliente + separator + separator + separator + separator + separator + separator + separator + separator
        + causaletestata + separator + separator + separator + separator + df.getBranchid() + separator
        + df.getSezionale();
writer.println(TES.toUpperCase());
df.getDetails().forEach(det1 -> {
    String RIG = tag_RIG + separator
            + tipodoc.getId() + separator
            + formatStringtoStringDate(df.getDatereg(), patternsqldate, patternnormdate_filter) + separator
            + df.getNumreg() + separator + separator
            + tiporiga + separator
            + det1.getContoreg() + separator + replace(det1.getImporto(), ".", ",") + separator + det1.getIvareg() + separator + filiale.getCod() + separator + separator + separator + separator + desc.toString() + separator + separator + separator + separator;
    if (!contocliente.equals(det1.getContoreg())) {
        writer.println(RIG.toUpperCase());
    }
                        });
df.getDetailsiva().forEach(det1 -> {
    String iva_value = replace(roundDoubleandFormat(fd(det1.getIva()), 2), ".", ",");
    String IVA = tag_IVA + separator
            + tipodoc.getId() + separator
            + formatStringtoStringDate(df.getDatereg(), patternsqldate, patternnormdate_filter) + separator
            + df.getNumreg() + separator + separator + separator
            + det1.getContoiva() + separator + separator + separator + separator + separator + separator + separator + separator
            + det1.getCodeiva() + separator + replace(det1.getImponibile(), ".", ",") + separator + iva_value + separator;
    writer.println(IVA.toUpperCase());
                        });
                    } else {
                        insertTR("E", "System", df.getCod() + " RIGA CLIENTE NON DISPONIBILE");
                    }
                });
            }
            if (f.length() > 0) {
                return f;
            }
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /////////

    /**
     *
     * @param path
     * @param data
     * @param filiale
     * @param daticlienti
     * @return
     */
    public File FILEP7A(String path, String data, Branch filiale,
            List<Atl_dati_clienti> daticlienti) {

        daticlienti = dc_removeBT1(daticlienti);

        boolean is_BT1 = is_BT1(null, daticlienti);

        if (daticlienti.isEmpty() || is_BT1) {
//        if (daticlienti.isEmpty() || branchList_BT().contains(filiale.getCod())) {
            return null;
        }

        try {
            File f = new File(path + filiale.getCod() + "_" + replace(data, "/", "") + "_P7_eSol.txt");
            try (PrintWriter writer = new PrintWriter(f)) {
                daticlienti.forEach(df -> {
                    String s1 = tag_GEN + separator;
                    String s3 = df.getRagsoc1() + separator;
                    String s4 = df.getRagsoc2() + separator;
                    String s5 = df.getAddress() + separator;
                    String s6 = df.getCity() + separator;
                    String s7 = df.getCountry() + separator;
                    String s8 = separator;
                    String s9 = df.getClientcode() + separator;
                    String s10 = df.getZipcode() + separator;
                    String s11 = df.getDistrict() + separator;
//              String s12 = separator;
String[] s12_s13 = verificaClientNumber(df.getClientnumber());
String s12 = s12_s13[0] + separator;
String s13 = s12_s13[1] + separator;
//                String s14 = df.getFatelet();
String s14 = "2" + separator;
String s15 = "0";
if (!df.getCountry().equalsIgnoreCase("IT")) {
    s10 = separator; //BLANK
    s11 = separator; //BLANK
    s12 = separator; //BLANK
    s13 = separator; //BLANK
    s14 = "0" + separator;
    s15 = "1";
}

String output = (s1 + s3 + s4 + s5 + s6 + s7 + s8 + s9 + s10 + s11 + s12 + s13 + s14 + s15).toUpperCase();
writer.println(output);
                });
            }
            return f;
        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    
}

class Tipodoc {

    String id;

    public Tipodoc() {
    }

    public Tipodoc(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
