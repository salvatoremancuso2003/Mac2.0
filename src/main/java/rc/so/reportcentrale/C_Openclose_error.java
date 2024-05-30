/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.reportcentrale;

import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import com.itextpdf.text.Font;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.BOLDITALIC;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.FontFactory.getFont;
import static com.itextpdf.text.PageSize.A4;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.TOP;
import static com.itextpdf.text.pdf.BaseFont.HELVETICA;
import static com.itextpdf.text.pdf.BaseFont.WINANSI;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import com.itextpdf.text.pdf.draw.LineSeparator;
import rc.so.db.Db_Master;
import rc.so.entity.Branch;
import rc.so.entity.Openclose;
import rc.so.report.Openclose_Error_value;
import rc.so.report.Openclose_Error_value_stock;
import static rc.so.util.Constant.is_IT;
import static rc.so.util.Engine.formatBankBranchReport;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.fd;
import static rc.so.util.Utility.formatMysqltoDisplay;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.roundDoubleandFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 *
 * @author rcosco
 */
public class C_Openclose_error {

    //column
    /**
     *
     */
    public static final float[] columnWidths0 = new float[]{60f, 40f};

    /**
     *
     */
    public static final float[] columnWidths1 = new float[]{60f, 30f};

    /**
     *
     */
    public static float[] columnWidths2 = new float[]{15f, 25f, 50f, 45f, 20f, 55f};

    /**
     *
     */
    public static float[] columnWidths3 = new float[]{10f, 10f, 10f, 10f, 15f, 15f, 20f};

    /**
     *
     */
    public static float[] columnWidths3a = new float[]{18f, 10f, 15f, 15f, 10f, 20f};

    /**
     *
     */
    public static float[] columnWidths3d = new float[]{18f, 10f, 15f, 15f, 10f, 20f};

    /**
     *
     */
    public static float[] columnWidths3b = new float[]{18f, 10f, 15f, 15f, 15f, 20f, 15f, 10f};

    /**
     *
     */
    public static float[] columnWidths3c = new float[]{18f, 10f, 15f, 15f, 15f, 20f, 15f, 10f};

    /**
     *
     */
    public static float[] columnWidths3new = new float[]{15f, 15f, 15f, 15f, 15f, 15f, 15f, 20f};
    String intestazionePdf = "Open / Close Error ";
    Phrase vuoto = new Phrase("\n");

    //resource
//    public static final String logo = "web/resource/logocl.png";
    //other
    /**
     *
     */
    public static final String br = "\n";

    /**
     *
     */
    public static final String blank = " ";

    Font f0_bold, f1_bold, f2_bold, f1_normal, f2_normal, f3_normal, f3_bold, f4_bold;

    /**
     ** Constructor
     */
    public C_Openclose_error() {

        this.f0_bold = getFont(HELVETICA, WINANSI, 14.04f, BOLD);
        this.f1_bold = getFont(HELVETICA, WINANSI, 9.96f, BOLD);
        this.f2_bold = getFont(HELVETICA, WINANSI, 8f, BOLD);
        this.f3_bold = getFont(HELVETICA, WINANSI, 8f, BOLDITALIC);
        this.f4_bold = getFont(HELVETICA, WINANSI, 6.96f, BOLD);
        this.f1_normal = getFont(HELVETICA, WINANSI, 9.96f, NORMAL);
        this.f2_normal = getFont(HELVETICA, WINANSI, 8f, NORMAL);
        this.f3_normal = getFont(HELVETICA, WINANSI, 6.96f, NORMAL);

    }

    /**
     *
     * @param path
     * @param osp
     * @param colonne
     * @param colonnedettaglio
     * @param colonnedettaglioNoChange
     * @param colonnedettaglioPOS
     * @param data1
     * @param data2
     * @param change
     * @param nochange
     * @param document
     * @param thres
     * @param b
     * @return
     */
    public Document receipt2(String path, Openclose_Error_value osp, ArrayList<String> colonne, ArrayList<String> colonnedettaglio,
            ArrayList<String> colonnedettaglioNoChange, ArrayList<String> colonnedettaglioPOS, String data1, String data2, boolean change,
            boolean nochange, Document document, String thres, int b) {
        try {
//            File pdf = new File(path + Utility.generaId(50) + "OpenCloseError.pdf");

//            OutputStream ou = new FileOutputStream(pdf);
//            PdfWriter wr = PdfWriter.getInstance(document, ou);
//            document.open();
            if (b > 0) {
                document.newPage();
            }

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

//            if (iscentral) {
//                intestazionePdf = "Cashier Open/Close Error";
//            }
            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " from " + data1 + " to " + data2, f4_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("", f3_normal));
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            Phrase phrase3 = new Phrase();
            phrase3.add(new Chunk("\n " + osp.getId_filiale() + " " + osp.getDe_filiale(), f3_normal));
            PdfPCell cell3 = new PdfPCell(phrase3);
            cell3.setBorder(NO_BORDER);

            Phrase phrase4 = new Phrase();
            phrase4.add(new Chunk("", f3_normal));
            PdfPCell cell4 = new PdfPCell(phrase4);
            cell4.setBorder(NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            document.add(table);

            vuoto.setFont(f3_normal);
            document.add(vuoto);

            ArrayList<Openclose_Error_value_stock> dati = osp.getDati();
            PdfPCell cell;
            Phrase phraset;
            PdfPCell cellt;

            PdfPTable table3 = new PdfPTable(colonne.size());
            table3.setWidths(columnWidths2);
            table3.setWidthPercentage(100);

            for (int i = 0; i < dati.size(); i++) {

                Openclose_Error_value_stock dpftemp = dati.get(i);
                List templist = dpftemp.getDati();

                boolean firstc = true, firstnc = true, firstpos = true;

                PdfPCell[] list = new PdfPCell[colonne.size()];
                //mi scandisco le colonne
                for (int j = 0; j < colonne.size(); j++) {
                    Phrase phraset1 = new Phrase();
                    phraset1.add(new Chunk(colonne.get(j), f3_normal));
                    PdfPCell cellt1 = new PdfPCell(phraset1);
                    if (j == 2 || j == 4 || j == 5) {
                        cellt1.setHorizontalAlignment(ALIGN_LEFT);
                    } else {
                        cellt1.setHorizontalAlignment(ALIGN_RIGHT);
                    }
                    cellt1.setBorder(BOTTOM | TOP);
                    // cellt1.setFixedHeight(20f);
                    cellt1.setBackgroundColor(LIGHT_GRAY);
                    //cellt1.setBorder(Rectangle.BOTTOM);
                    cellt1.setBorderWidth(0.7f);
                    list[j] = cellt1;

                }

                table3 = new PdfPTable(colonne.size());
                table3.setWidths(columnWidths2);
                table3.setWidthPercentage(80);
                table3.setHorizontalAlignment(ALIGN_LEFT);

                for (PdfPCell list1 : list) {
                    PdfPCell tempcell = (PdfPCell) (list1);
                    table3.addCell(tempcell);
                }

                document.add(table3);

                LineSeparator sep = new LineSeparator();
                sep.setOffset(-2);
                sep.setLineWidth((float) 0.5);

                ArrayList<String> elencodiff = new ArrayList<>();
                ArrayList<Double> elencodiffNC = new ArrayList<>();
                ArrayList<Double> elencodiffPOS = new ArrayList<>();

                PdfPTable table5 = new PdfPTable(8);
                table5.setWidths(columnWidths3c);
                table5.setWidthPercentage(100);
                table5.setHorizontalAlignment(ALIGN_LEFT);

                PdfPTable tablechange = new PdfPTable(colonnedettaglio.size());
                tablechange.setWidths(columnWidths3b);
                tablechange.setWidthPercentage(100);
                tablechange.setHorizontalAlignment(ALIGN_LEFT);

                PdfPTable tablenochange = new PdfPTable(colonnedettaglioNoChange.size());
                tablenochange.setWidths(columnWidths3a);
                tablenochange.setWidthPercentage(100);
                tablenochange.setHorizontalAlignment(ALIGN_LEFT);

                PdfPTable tablepos = new PdfPTable(colonnedettaglioPOS.size());
                tablepos.setWidths(columnWidths3new);
                tablepos.setWidthPercentage(100);
                tablepos.setHorizontalAlignment(ALIGN_LEFT);

                PdfPTable table6 = new PdfPTable(colonnedettaglioPOS.size());
                table6.setWidths(columnWidths3new);
                table6.setWidthPercentage(100);
                table6.setHorizontalAlignment(ALIGN_LEFT);

                PdfPTable table6NC = new PdfPTable(colonnedettaglioNoChange.size());
                table6NC.setWidths(columnWidths3a);
                table6NC.setWidthPercentage(100);
                table6NC.setHorizontalAlignment(ALIGN_LEFT);

                boolean presch = false;
                boolean presnc = false;
                boolean prespos = false;
                for (int x = 0; x < templist.size(); x++) {

//                    presch = false;
//                    presnc = false;
//                    prespos = false;
                    Openclose_Error_value temp = (Openclose_Error_value) templist.get(x);

                    PdfPTable table2 = new PdfPTable(colonne.size());
                    table2.setWidths(columnWidths2);
                    table2.setWidthPercentage(100);

                    //  document.add(table2);
                    // document.add(sep);
                    if (x == 0) {

                        table3 = new PdfPTable(colonne.size());
                        table3.setWidths(columnWidths2);
                        table3.setWidthPercentage(80);
                        table3.setHorizontalAlignment(ALIGN_LEFT);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getUser(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getTill(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getData(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getCod(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        String tipoesteso = capitalize(temp.getTipo().toLowerCase());

                        phraset = new Phrase();
                        phraset.add(new Chunk(tipoesteso, f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getId_filiale() + " " + temp.getDe_filiale(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        table3.addCell(cellt);

                    }

//                    document.add(table3);
                    if (temp.getType().equalsIgnoreCase("ch")) {
                        presch = true;
                        if (firstc) {
                            firstc = false;
                            for (int g = 0; g < colonnedettaglio.size(); g++) {
                                phraset = new Phrase();
                                phraset.add(new Chunk((String) colonnedettaglio.get(g), f4_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setBorder(BOTTOM);
                                if (g == 0 || g == 7) {
                                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                                }
                                tablechange.addCell(cellt);
                            }
                        }

                        //mi calcolo i valori da inserire
                        double qtautentediv = fd((temp.getQuantityUser()));
                        double qtasistdiv = fd(temp.getQuantitySystem());
                        double diff = qtautentediv - qtasistdiv;
                        elencodiff.add(temp.getDiffAmount());

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getCurrency(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        tablechange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getKind(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablechange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getQuantityUser()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablechange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getQuantitySystem()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablechange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(diff, 2) + ""), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablechange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getRate()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablechange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getDiffAmount()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablechange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getNote(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        tablechange.addCell(cellt);

                        //  document.add(tablechange);
//                        document.add(table5);
                    }//if type = CH
                    else if (temp.getType().equalsIgnoreCase("nc")) {
                        presnc = true;
//                        ArrayList<String> elencodiffNoChange = new ArrayList<>();

                        if (firstnc) {
                            firstnc = false;
                            for (int g = 0; g < colonnedettaglioNoChange.size(); g++) {
                                phraset = new Phrase();
                                phraset.add(new Chunk((String) colonnedettaglioNoChange.get(g), f4_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setBorder(BOTTOM);
                                if (g == 0 || g == 5) {
                                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                                }
                                tablenochange.addCell(cellt);
                            }

                        }

                        //mi calcolo i valori da inserire
                        double qtautente = fd(temp.getQuantityUser());
                        double qtasist = fd(temp.getQuantitySystem());

                        double diff = qtautente - qtasist;

                        elencodiffNC.add(fd(temp.getLocalamount()));

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getNc(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        tablenochange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantityUser()), 0)), f3_normal));

                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablenochange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantitySystem()), 0)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablenochange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(diff, 0)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablenochange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(temp.getLocalamount()), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablenochange.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getNote(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        tablenochange.addCell(cellt);

//                        document.add(tablenochange);
                        vuoto.setFont(f3_normal);
                        //  document.add(vuoto);

                    } else {

                        prespos = true;
                        if (firstpos) {
                            firstpos = false;

                            for (int g = 0; g < colonnedettaglioPOS.size(); g++) {
                                phraset = new Phrase();
                                phraset.add(new Chunk((String) colonnedettaglioPOS.get(g), f4_bold));
                                cellt = new PdfPCell(phraset);
                                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                                cellt.setBorder(BOTTOM);
                                if (g == 0 || g == 7) {
                                    cellt.setHorizontalAlignment(ALIGN_LEFT);
                                }
                                tablepos.addCell(cellt);
                            }
                        }

                        //mi calcolo i valori da inserire
                        elencodiffPOS.add(fd(temp.getDiffAmount()));

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getPos(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        tablepos.addCell(cellt);

                        phraset = new Phrase();

                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantityUser()), 0)), f3_normal));

                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablepos.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getAmountuser()), 2)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablepos.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantitySystem()), 0)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablepos.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getAmountsystem()), 2)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablepos.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getQuantitydiff()), 0)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablepos.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(fd(temp.getDiffAmount()), 2)), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_RIGHT);
                        cellt.setBorder(BOTTOM);
                        tablepos.addCell(cellt);

                        phraset = new Phrase();
                        phraset.add(new Chunk(temp.getNote(), f3_normal));
                        cellt = new PdfPCell(phraset);
                        cellt.setHorizontalAlignment(ALIGN_LEFT);
                        cellt.setBorder(BOTTOM);
                        tablepos.addCell(cellt);

//                        document.add(tablepos);
                    }

                }

                double totdiff = 0;

                for (int t = 0; t < elencodiff.size(); t++) {
                    totdiff += fd(elencodiff.get(t));
                }

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Balance Change", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totdiff, 2)), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table5.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table5.addCell(cellt);

                double totdiffNC = 0;

                for (int t = 0; t < elencodiffNC.size(); t++) {
                    totdiffNC += fd(elencodiffNC.get(t).toString());
                }

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6NC.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6NC.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6NC.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Balance No Change", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table6NC.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totdiffNC, 2)), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table6NC.addCell(cellt);
                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6NC.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6NC.addCell(cellt);

                double totdiffpos = 0;

                for (int t = 0; t < elencodiffPOS.size(); t++) {
                    totdiffpos += fd(elencodiffPOS.get(t).toString());
                }

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Balance POS / Bank Account", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setColspan(2);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table6.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totdiffpos, 2)), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table6.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table6.addCell(cellt);

                document.add(table3);
                vuoto.setFont(f3_normal);
                document.add(vuoto);

                document.add(tablechange);

                PdfPTable table7 = new PdfPTable(8);
                table7.setWidths(columnWidths3c);
                table7.setWidthPercentage(100);
                table7.setHorizontalAlignment(ALIGN_LEFT);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table7.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table7.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table7.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table7.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table7.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk("Final Balance", f4_bold));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(BOTTOM);
                table7.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(formatMysqltoDisplay(roundDoubleandFormat(totdiff + totdiffNC + totdiffpos, 2)), f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_RIGHT);
                cellt.setBorder(BOTTOM);
                table7.addCell(cellt);

                phraset = new Phrase();
                phraset.add(new Chunk(" ", f3_normal));
                cellt = new PdfPCell(phraset);
                cellt.setHorizontalAlignment(ALIGN_LEFT);
                cellt.setBorder(NO_BORDER);
                table7.addCell(cellt);

                if (presch) {
                    document.add(table5);
                    document.add(vuoto);
                }
                document.add(tablenochange);

                if (presnc) {
                    document.add(table6NC);
                    document.add(vuoto);
                }

                document.add(tablepos);

                if (prespos) {
                    document.add(table6);
                    document.add(vuoto);
                }

                document.add(table7);

                vuoto.setFont(f3_normal);
                document.add(vuoto);

            }

            return document;

        } catch (DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param d3
     * @param d4
     * @param data1
     * @param data2
     * @param change
     * @param nochange
     * @param thres
     * @param br1
     * @param allenabledbr
     * @return
     */
    public String main(String path, String d3, String d4, String data1, String data2, boolean change, boolean nochange,
            String thres, ArrayList<String> br1, ArrayList<Branch> allenabledbr) {
        try {

            boolean add = false;

            File pdffile = new File(path + generaId(50) + "C_BankBranch.pdf");
            try(OutputStream ou = new FileOutputStream(pdffile)){
            Document document = new Document(A4, 20, 20, 20, 20);
            PdfWriter wr = getInstance(document, ou);
            document.open();
            Db_Master dbm = new Db_Master();

            for (int b = 0; b < br1.size(); b++) {
                ArrayList<Openclose> list = dbm.list_openclose_errors_report(br1.get(b), data1, data2);
                Openclose_Error_value pdf = new Openclose_Error_value();
                pdf.setId_filiale(br1.get(b));
                pdf.setDe_filiale(formatBankBranchReport(br1.get(b), "BR", null, allenabledbr));

                ArrayList<String> alcolonne = new ArrayList<>();
                alcolonne.add("User");
                alcolonne.add("Safe/Till");
                alcolonne.add("Date");
                alcolonne.add("Operation");
                alcolonne.add("Type");
                alcolonne.add("Branch");

                ArrayList<String> alcolonnedetaglierrore = new ArrayList<>();
                alcolonnedetaglierrore.add("Currency");
                alcolonnedetaglierrore.add("Kind");
                alcolonnedetaglierrore.add("Quantity User");
                alcolonnedetaglierrore.add("Quantity System");
                alcolonnedetaglierrore.add("Quantity Difference");
                alcolonnedetaglierrore.add("Rate");
                if (is_IT) {
                    alcolonnedetaglierrore.add("Difference (Euro)");
                } else {
                    alcolonnedetaglierrore.add("Difference (GBP)");
                }
                alcolonnedetaglierrore.add("Note");

                ArrayList<String> alcolonnedetaglierroreNoChange = new ArrayList<>();
                alcolonnedetaglierroreNoChange.add("Category");
                alcolonnedetaglierroreNoChange.add("Quantity User");
                alcolonnedetaglierroreNoChange.add("Quantity System");
                alcolonnedetaglierroreNoChange.add("Diff");
                alcolonnedetaglierroreNoChange.add("Amount");
                alcolonnedetaglierroreNoChange.add("Note");

                ArrayList<String> alcolonnedetaglierrorePos = new ArrayList<>();
                alcolonnedetaglierrorePos.add("POS/Bank Account");
                alcolonnedetaglierrorePos.add("Quantity User");
                alcolonnedetaglierrorePos.add("Amount User");
                alcolonnedetaglierrorePos.add("Quantity System");
                alcolonnedetaglierrorePos.add("Amount System");
                alcolonnedetaglierrorePos.add("Quantity Diff");
                alcolonnedetaglierrorePos.add("Amount Diff");
                alcolonnedetaglierrorePos.add("Note");

                ArrayList<Openclose_Error_value_stock> datifilialeperreport = new ArrayList<>();
                Db_Master dbm1 = new Db_Master();
                for (int i = 0; i < list.size(); i++) {
                    Openclose co = list.get(i);
                    ArrayList<String[]> list_oc_errors = dbm1.list_oc_errors(co.getCod());
                    ArrayList<Openclose_Error_value> dati = dbm1.list_openclose_errors_report(co, list_oc_errors, allenabledbr, thres);
//                    ArrayList<Openclose_Error_value> dati = dbm1.list_openclose_errors_report(co, list_oc_errors, allenabledbr, thres);
                    if (dati.size() > 0) {
                        Openclose_Error_value_stock dpf = new Openclose_Error_value_stock(dati, co.getId());

                        datifilialeperreport.add(dpf);
                    }
                }
                dbm1.closeDB();
                pdf.setDati(datifilialeperreport);

                if (datifilialeperreport.size() > 0) {
                    document = receipt2(path, pdf, alcolonne, alcolonnedetaglierrore, alcolonnedetaglierroreNoChange,
                            alcolonnedetaglierrorePos, d3, d4, change, nochange, document, thres, b);
                    add = true;
                }
            }

            dbm.closeDB();

            if (add) {
                document.close();
                wr.close();
                ou.close();
                String base64 = new String(encodeBase64(readFileToByteArray(pdffile)));
                pdffile.delete();
                return base64;
            } else {
                document.add(new Paragraph("XXX"));
                document.close();
                wr.close();
                ou.close();
                pdffile.delete();
                return null;
            }
        }
        } catch (DocumentException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }
}