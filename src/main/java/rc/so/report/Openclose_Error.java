/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.report;

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
import static rc.so.util.Constant.formatdataCell;
import static rc.so.util.Constant.formatdataCellRate;
import static rc.so.util.Constant.formatdataCellint;
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
import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.lang3.StringUtils.capitalize;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import static org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.RIGHT;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rcosco
 */
public class Openclose_Error {

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
     * Costructor
     */
    public Openclose_Error() {

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
     * @param osplist
     * @param colonne
     * @param colonnedettaglio
     * @param colonnedettaglioNoChange
     * @param colonnedettaglioPOS
     * @param data1
     * @param data2
     * @param iscentral
     * @return
     */
    public String receipt(String path, ArrayList<Openclose_Error_value> osplist, ArrayList<String> colonne, ArrayList<String> colonnedettaglio,
            ArrayList<String> colonnedettaglioNoChange, ArrayList<String> colonnedettaglioPOS, String data1, String data2, boolean iscentral) {
        try {
            File pdf = new File(path + generaId(50) + "OpenCloseError.pdf");
            Document document = new Document(A4, 20, 20, 20, 20);
            try(OutputStream ou = new FileOutputStream(pdf)){
            PdfWriter wr = getInstance(document, ou);
            document.open();

            PdfPTable table = new PdfPTable(2);
            table.setWidths(columnWidths0);
            table.setWidthPercentage(100);

            if (iscentral) {
                intestazionePdf = "Cashier Open/Close Error";
            }

            Phrase phrase1 = new Phrase();
            phrase1.add(new Chunk(intestazionePdf + " from " + data1 + " to " + data2, f4_bold));
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(NO_BORDER);

            Paragraph pa1 = new Paragraph(new Phrase("", f3_normal));
            pa1.setAlignment(ALIGN_RIGHT);
            PdfPCell cell2 = new PdfPCell(pa1);
            cell2.setBorder(NO_BORDER);
            cell2.setHorizontalAlignment(ALIGN_RIGHT);

            for (int h = 0; h < osplist.size(); h++) {

                Openclose_Error_value osp = osplist.get(h);

                Phrase phrase3 = new Phrase();
                if (iscentral) {
                    phrase3.add(new Chunk("", f3_normal));

                } else {

                    phrase3.add(new Chunk("\n " + osp.getId_filiale() + " " + osp.getDe_filiale(), f3_normal));

                }
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

                    Openclose_Error_value_stock dpftemp =  dati.get(i);
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
                            double diff;
                            double qtautentediv = fd((temp.getQuantityUser()));
                            double qtasistdiv = fd(temp.getQuantitySystem());
                            diff = qtautentediv - qtasistdiv;
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
                            double diff;
                            double qtautente = fd(temp.getQuantityUser());
                            double qtasist = fd(temp.getQuantitySystem());

                            diff = qtautente - qtasist;

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
//                            double diff = 0;
//                            double qtautente = fd((temp.getAmountuser()));
//                            double qtasist = fd((temp.getAmountsystem()));
//
//                            diff = qtautente - qtasist;
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

            }

            document.close();
            wr.close();
            ou.close();
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;
        }

        } catch (IOException | DocumentException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param osplist
     * @param data1
     * @param data2
     * @param localcurrency
     * @param cashier
     * @param completecashier
     * @return
     */
    public String main_Excel(String path, ArrayList<Openclose_Error_value> osplist,
            String data1, String data2, String localcurrency,
            boolean cashier, ArrayList<Openclose_Error_value> completecashier) {
        return receiptExcel_NEW(path, osplist, data1, data2, localcurrency, cashier, completecashier);

    }

    /**
     *
     * @param path
     * @param osplist
     * @param data1
     * @param data2
     * @param localcurrency
     * @param cashier
     * @param completecashier
     * @return
     */
    public String receiptExcel_NEW(String path, ArrayList<Openclose_Error_value> osplist,
            String data1, String data2, String localcurrency, boolean cashier, List<Openclose_Error_value> completecashier) {
        try {
            File xlsx = new File(path + generaId(50) + "OpenCloseError_N.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("OpenCloseError");

            //CREAZIONE FONT
            XSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            XSSFCellStyle style1 = workbook.createCellStyle();
            style1.setFont(font);

            XSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);

            XSSFCellStyle style2 = workbook.createCellStyle();
            style2.setFont(font2);

            XSSFFont font3 = workbook.createFont();
            font3.setFontName(FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);
            font3.setBold(true);

            XSSFCellStyle style3 = workbook.createCellStyle();
            style3.setFont(font3);
            style3.setAlignment(CENTER);
            style3.setBorderTop(THIN);
            style3.setBorderLeft(THIN);
            style3.setBorderRight(THIN);
            style3.setBorderBottom(THIN);

            XSSFCellStyle style3left = workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            XSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            XSSFCellStyle style4 = workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderLeft(THIN);
            style4.setBorderRight(THIN);
            style4.setBorderBottom(THIN);

            XSSFDataFormat hssfDataFormat = workbook.createDataFormat();

            XSSFCellStyle cellStylenumint = workbook.createCellStyle();
            cellStylenumint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));
            cellStylenumint.setAlignment(RIGHT);
            cellStylenumint.setBorderTop(THIN);
            cellStylenumint.setBorderBottom(THIN);

            XSSFCellStyle style4left = workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderLeft(THIN);
            style4left.setBorderRight(THIN);
            style4left.setBorderBottom(THIN);

            XSSFCellStyle style4CE = workbook.createCellStyle();
            style4CE.setAlignment(CENTER);
            style4CE.setBorderTop(THIN);
            style4CE.setBorderLeft(THIN);
            style4CE.setBorderRight(THIN);
            style4CE.setBorderBottom(THIN);

            XSSFCellStyle cellStylenum = workbook.createCellStyle();

            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderLeft(THIN);
            cellStylenum.setBorderRight(THIN);
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle style3num = workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            XSSFCellStyle cellStylenumRATE = workbook.createCellStyle();
            XSSFDataFormat hssfDataFormatRATE = workbook.createDataFormat();
            cellStylenumRATE.setAlignment(RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderLeft(THIN);
            cellStylenumRATE.setBorderRight(THIN);
            cellStylenumRATE.setBorderBottom(THIN);
            cellStylenumRATE.setDataFormat(hssfDataFormatRATE.getFormat(formatdataCellRate));

            XSSFRow rowP = sheet.createRow(1);
            XSSFCell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " from " + data1 + " to " + data2);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 7));

            AtomicInteger start = new AtomicInteger(0);

            if (cashier) {

                Comparator<Openclose_Error_value> compareByUser = (Openclose_Error_value o1, Openclose_Error_value o2)
                        -> o1.getUser().compareTo(o2.getUser());
                sort(completecashier, compareByUser);

                AtomicInteger indice = new AtomicInteger(start.get() + 6);
                XSSFRow row = sheet.createRow(indice.get());

                Cell cl5 = row.createCell(1);
                cl5.setCellStyle(style3);
                cl5.setCellValue("USER");

                cl5 = row.createCell(2);
                cl5.setCellStyle(style3);
                cl5.setCellValue("DATE");

                cl5 = row.createCell(3);
                cl5.setCellStyle(style3);
                cl5.setCellValue("BRANCH");

                cl5 = row.createCell(4);
                cl5.setCellStyle(style3);
                cl5.setCellValue("KIND");

                cl5 = row.createCell(5);
                cl5.setCellStyle(style3);
                cl5.setCellValue("CURRENCY/CATEGORY");

                cl5 = row.createCell(6);
                cl5.setCellStyle(style3);
                cl5.setCellValue("DIFFERENCE (" + localcurrency + ")");

                cl5 = row.createCell(7);
                cl5.setCellStyle(style3);
                cl5.setCellValue("NOTE");

                cl5 = row.createCell(8);
                cl5.setCellStyle(style3);
                cl5.setCellValue("QUANTITY USER");

                cl5 = row.createCell(9);
                cl5.setCellStyle(style3);
                cl5.setCellValue("AMOUNT USER");

                cl5 = row.createCell(10);
                cl5.setCellStyle(style3);
                cl5.setCellValue("QUANTITY SYSTEM");

                cl5 = row.createCell(11);
                cl5.setCellStyle(style3);
                cl5.setCellValue("AMOUNT SYSTEM");

                cl5 = row.createCell(12);
                cl5.setCellStyle(style3);
                cl5.setCellValue("QUANTITY DIFFERENCE");

                cl5 = row.createCell(13);
                cl5.setCellStyle(style3);
                cl5.setCellValue("AMOUNT DIFFERENCE");

                cl5 = row.createCell(14);
                cl5.setCellStyle(style3);
                cl5.setCellValue("RATE / PRICE");

                cl5 = row.createCell(15);
                cl5.setCellStyle(style3);
                cl5.setCellValue("SAFE / TILL");

                cl5 = row.createCell(16);
                cl5.setCellStyle(style3);
                cl5.setCellValue("TYPE");

                cl5 = row.createCell(17);
                cl5.setCellStyle(style3);
                cl5.setCellValue("OPERATION");

                cl5 = row.createCell(18);
                cl5.setCellStyle(style3);
                cl5.setCellValue("POS / BANK ACCOUNT");

                completecashier.forEach(d2 -> {
                    indice.addAndGet(1);
                    XSSFRow row1 = sheet.createRow(indice.get());

                    double diff = fd(d2.getQuantityUser()) - fd(d2.getQuantitySystem());

                    XSSFCell f1 = row1.createCell(1);
                    f1.setCellStyle(style4left);
                    f1.setCellValue(d2.getUser());

                    f1 = row1.createCell(2);
                    f1.setCellStyle(style4);
                    f1.setCellValue(d2.getData());

                    f1 = row1.createCell(3);
                    f1.setCellStyle(style4);
                    f1.setCellValue(d2.getId_filiale() + " " + d2.getDe_filiale());

                    f1 = row1.createCell(4);
                    f1.setCellStyle(style4);
                    f1.setCellValue(d2.getKind());

                    f1 = row1.createCell(5);
                    f1.setCellStyle(style4left);
                    f1.setCellValue(d2.getCurrency());

                    f1 = row1.createCell(6, NUMERIC);
                    f1.setCellStyle(cellStylenum);
                    f1.setCellValue(fd(d2.getDiffAmount()));

                    f1 = row1.createCell(7);
                    f1.setCellStyle(style4left);
                    f1.setCellValue(d2.getNote());

                    f1 = row1.createCell(8, NUMERIC);
                    f1.setCellStyle(cellStylenum);
                    f1.setCellValue(fd(d2.getQuantityUser()));

                    f1 = row1.createCell(9, NUMERIC);
                    f1.setCellStyle(cellStylenum);
                    f1.setCellValue(fd(d2.getAmountuser()));

                    f1 = row1.createCell(10, NUMERIC);
                    f1.setCellStyle(cellStylenum);
                    f1.setCellValue(fd(d2.getQuantitySystem()));

                    f1 = row1.createCell(11, NUMERIC);
                    f1.setCellStyle(cellStylenum);
                    f1.setCellValue(fd(d2.getAmountsystem()));

                    f1 = row1.createCell(12, NUMERIC);
                    f1.setCellStyle(cellStylenum);
                    f1.setCellValue(fd(d2.getQuantitydiff()));

                    f1 = row1.createCell(13, NUMERIC);
                    f1.setCellStyle(cellStylenum);
                    f1.setCellValue(fd(d2.getDiffAmount()));

                    f1 = row1.createCell(14, NUMERIC);
                    f1.setCellStyle(cellStylenumRATE);
                    if (d2.getCurrency().equals(localcurrency)) {
                        f1.setCellValue(fd("1.00000000"));
                    } else {
                        f1.setCellValue(fd(d2.getRate()));
                    }

                    f1 = row1.createCell(15);
                    f1.setCellStyle(style4);
                    f1.setCellValue(d2.getTill());

                    f1 = row1.createCell(16);
                    f1.setCellStyle(style4);
                    f1.setCellValue(d2.getTipo());

                    f1 = row1.createCell(17);
                    f1.setCellStyle(style4);
                    f1.setCellValue(d2.getOperazione());

                    f1 = row1.createCell(18);
                    f1.setCellStyle(style4);
                    f1.setCellValue(d2.getPos());

                    if (d2.getType().equals("CH")) {

                        f1 = row1.createCell(8);
                        f1.setCellStyle(style4CE);
                        f1.setCellValue("-");

                        f1 = row1.createCell(9, NUMERIC);
                        f1.setCellStyle(cellStylenum);
                        f1.setCellValue(fd(d2.getQuantityUser()));

                        f1 = row1.createCell(10);
                        f1.setCellStyle(style4CE);
                        f1.setCellValue("-");

                        f1 = row1.createCell(11, NUMERIC);
                        f1.setCellStyle(cellStylenum);
                        f1.setCellValue(fd(d2.getQuantitySystem()));

                        f1 = row1.createCell(12);
                        f1.setCellStyle(style4CE);
                        f1.setCellValue("-");

                        f1 = row1.createCell(13, NUMERIC);
                        f1.setCellStyle(cellStylenum);
                        f1.setCellValue(fd(roundDoubleandFormat(diff, 2)));

                        f1 = row1.createCell(18);
                        f1.setCellStyle(style4CE);
                        f1.setCellValue("-");
                    } else if (d2.getType().equals("NC")) {

                        f1 = row1.createCell(4);
                        f1.setCellStyle(style4);
                        f1.setCellValue("NC");

                        f1 = row1.createCell(5);
                        f1.setCellStyle(style4left);
                        f1.setCellValue(d2.getNc());

                        f1 = row1.createCell(6, NUMERIC);
                        f1.setCellStyle(cellStylenum);
                        f1.setCellValue(fd(d2.getLocalamount()));

                        f1 = row1.createCell(9);
                        f1.setCellStyle(style4CE);
                        f1.setCellValue("-");

                        f1 = row1.createCell(11);
                        f1.setCellStyle(style4CE);
                        f1.setCellValue("-");

                        f1 = row1.createCell(12, NUMERIC);
                        f1.setCellStyle(cellStylenum);
                        f1.setCellValue(fd(d2.getDiffAmount()));

                        f1 = row1.createCell(13, NUMERIC);
                        f1.setCellStyle(cellStylenum);
                        f1.setCellValue(fd(d2.getLocalamount()));

                        f1 = row1.createCell(14, NUMERIC);
                        f1.setCellStyle(cellStylenum);
                        f1.setCellValue(fd(d2.getNcprice()));

                        f1 = row1.createCell(18);
                        f1.setCellStyle(style4CE);
                        f1.setCellValue("-");
                    } else if (d2.getType().equals("PO") && d2.getCurrency().equals(localcurrency)) {

                    } else {

                        f1 = row1.createCell(6, NUMERIC);
                        f1.setCellStyle(cellStylenum);
                        f1.setCellValue(fd(d2.getDiffContr()));
                    }
                    start.set(indice.get());
                });

            } else {
                for (int h = 0; h < osplist.size(); h++) {

                    sheet.addMergedRegion(new CellRangeAddress(start.get() + 3, start.get() + 3, 1, 7));

                    Openclose_Error_value osp = osplist.get(h);

                    XSSFRow row = sheet.createRow(start.get() + 3);
                    XSSFCell c2 = row.createCell(1);
                    c2.setCellStyle(style1);
                    c2.setCellValue(osp.getId_filiale() + " " + osp.getDe_filiale());

                    ArrayList<Openclose_Error_value_stock> dati = osp.getDati();
                    AtomicInteger indice = new AtomicInteger(start.get() + 6);
                    row = sheet.createRow(indice.get());

                    Cell cl5 = row.createCell(1);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("USER");

                    cl5 = row.createCell(2);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("DATE");

                    cl5 = row.createCell(3);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("BRANCH");

                    cl5 = row.createCell(4);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("KIND");

                    cl5 = row.createCell(5);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("CURRENCY/CATEGORY");

                    cl5 = row.createCell(6);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("DIFFERENCE (" + localcurrency + ")");

                    cl5 = row.createCell(7);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("NOTE");

                    cl5 = row.createCell(8);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("QUANTITY USER");

                    cl5 = row.createCell(9);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("AMOUNT USER");

                    cl5 = row.createCell(10);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("QUANTITY SYSTEM");

                    cl5 = row.createCell(11);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("AMOUNT SYSTEM");

                    cl5 = row.createCell(12);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("QUANTITY DIFFERENCE");

                    cl5 = row.createCell(13);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("AMOUNT DIFFERENCE");

                    cl5 = row.createCell(14);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("RATE / PRICE");

                    cl5 = row.createCell(15);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("SAFE / TILL");

                    cl5 = row.createCell(16);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("TYPE");

                    cl5 = row.createCell(17);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("OPERATION");

                    cl5 = row.createCell(18);
                    cl5.setCellStyle(style3);
                    cl5.setCellValue("POS / BANK ACCOUNT");

                    dati.forEach(d1 -> {

                        List<Openclose_Error_value> content = d1.getDati();

                        content.forEach(d2 -> {
                            indice.addAndGet(1);
                            XSSFRow row1 = sheet.createRow(indice.get());

                            double diff = fd(d2.getQuantityUser()) - fd(d2.getQuantitySystem());

                            XSSFCell f1 = row1.createCell(1);
                            f1.setCellStyle(style4left);
                            f1.setCellValue(d2.getUser());

                            f1 = row1.createCell(2);
                            f1.setCellStyle(style4);
                            f1.setCellValue(d2.getData());

                            f1 = row1.createCell(3);
                            f1.setCellStyle(style4);
                            f1.setCellValue(osp.getId_filiale() + " " + osp.getDe_filiale());

                            f1 = row1.createCell(4);
                            f1.setCellStyle(style4);
                            f1.setCellValue(d2.getKind());

                            f1 = row1.createCell(5);
                            f1.setCellStyle(style4left);
                            f1.setCellValue(d2.getCurrency());

                            f1 = row1.createCell(6, NUMERIC);
                            f1.setCellStyle(cellStylenum);
                            f1.setCellValue(fd(d2.getDiffAmount()));

                            f1 = row1.createCell(7);
                            f1.setCellStyle(style4left);
                            f1.setCellValue(d2.getNote());

                            f1 = row1.createCell(8, NUMERIC);
                            f1.setCellStyle(cellStylenum);
                            f1.setCellValue(fd(d2.getQuantityUser()));

                            f1 = row1.createCell(9, NUMERIC);
                            f1.setCellStyle(cellStylenum);
                            f1.setCellValue(fd(d2.getAmountuser()));

                            f1 = row1.createCell(10, NUMERIC);
                            f1.setCellStyle(cellStylenum);
                            f1.setCellValue(fd(d2.getQuantitySystem()));

                            f1 = row1.createCell(11, NUMERIC);
                            f1.setCellStyle(cellStylenum);
                            f1.setCellValue(fd(d2.getAmountsystem()));

                            f1 = row1.createCell(12, NUMERIC);
                            f1.setCellStyle(cellStylenum);
                            f1.setCellValue(fd(d2.getQuantitydiff()));

                            f1 = row1.createCell(13, NUMERIC);
                            f1.setCellStyle(cellStylenum);
                            f1.setCellValue(fd(d2.getDiffAmount()));

                            f1 = row1.createCell(14, NUMERIC);
                            f1.setCellStyle(cellStylenumRATE);
                            if (d2.getCurrency().equals(localcurrency)) {
                                f1.setCellValue(fd("1.00000000"));
                            } else {
                                f1.setCellValue(fd(d2.getRate()));
                            }

                            f1 = row1.createCell(15);
                            f1.setCellStyle(style4);
                            f1.setCellValue(d2.getTill());

                            f1 = row1.createCell(16);
                            f1.setCellStyle(style4);
                            f1.setCellValue(d2.getTipo());

                            f1 = row1.createCell(17);
                            f1.setCellStyle(style4);
                            f1.setCellValue(d1.getOperazione());

                            f1 = row1.createCell(18);
                            f1.setCellStyle(style4);
                            f1.setCellValue(d2.getPos());

                            if (d2.getType().equals("CH")) {

                                f1 = row1.createCell(8);
                                f1.setCellStyle(style4CE);
                                f1.setCellValue("-");

                                f1 = row1.createCell(9, NUMERIC);
                                f1.setCellStyle(cellStylenum);
                                f1.setCellValue(fd(d2.getQuantityUser()));

                                f1 = row1.createCell(10);
                                f1.setCellStyle(style4CE);
                                f1.setCellValue("-");

                                f1 = row1.createCell(11, NUMERIC);
                                f1.setCellStyle(cellStylenum);
                                f1.setCellValue(fd(d2.getQuantitySystem()));

                                f1 = row1.createCell(12);
                                f1.setCellStyle(style4CE);
                                f1.setCellValue("-");

                                f1 = row1.createCell(13, NUMERIC);
                                f1.setCellStyle(cellStylenum);
                                f1.setCellValue(fd(roundDoubleandFormat(diff, 2)));

                                f1 = row1.createCell(18);
                                f1.setCellStyle(style4CE);
                                f1.setCellValue("-");
                            } else if (d2.getType().equals("NC")) {

                                f1 = row1.createCell(4);
                                f1.setCellStyle(style4);
                                f1.setCellValue("NC");

                                f1 = row1.createCell(5);
                                f1.setCellStyle(style4left);
                                f1.setCellValue(d2.getNc());

                                f1 = row1.createCell(6, NUMERIC);
                                f1.setCellStyle(cellStylenum);
                                f1.setCellValue(fd(d2.getLocalamount()));

                                f1 = row1.createCell(9);
                                f1.setCellStyle(style4CE);
                                f1.setCellValue("-");

                                f1 = row1.createCell(11);
                                f1.setCellStyle(style4CE);
                                f1.setCellValue("-");

                                f1 = row1.createCell(12, NUMERIC);
                                f1.setCellStyle(cellStylenum);
                                f1.setCellValue(fd(d2.getDiffAmount()));

                                f1 = row1.createCell(13, NUMERIC);
                                f1.setCellStyle(cellStylenum);
                                f1.setCellValue(fd(d2.getLocalamount()));

                                f1 = row1.createCell(14, NUMERIC);
                                f1.setCellStyle(cellStylenum);
                                f1.setCellValue(fd(d2.getNcprice()));

                                f1 = row1.createCell(18);
                                f1.setCellStyle(style4CE);
                                f1.setCellValue("-");
                            } else if (d2.getType().equals("PO") && d2.getCurrency().equals(localcurrency)) {

                            } else {

                                f1 = row1.createCell(6, NUMERIC);
                                f1.setCellStyle(cellStylenum);
                                f1.setCellValue(fd(d2.getDiffContr()));
                            }

                            start.set(indice.get());

                        });
                    });

                    start.addAndGet(5);
                }
            }

            for (int i = 1; i < 19; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream out = new FileOutputStream(xlsx)) {
                workbook.write(out);
            }

            String base64 = new String(encodeBase64(readFileToByteArray(xlsx)));
            xlsx.delete();
            return base64;

        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param path
     * @param osplist
     * @param colonne
     * @param colonnedettaglio
     * @param colonnedettaglioNoChange
     * @param colonnedettaglioPOS
     * @param data1
     * @param data2
     * @param iscentral
     * @return
     */
    public String receiptExcel(String path, ArrayList<Openclose_Error_value> osplist, ArrayList<String> colonne, ArrayList<String> colonnedettaglio,
            ArrayList<String> colonnedettaglioNoChange, ArrayList<String> colonnedettaglioPOS, String data1, String data2, boolean iscentral) {
        try {

            File pdf = new File(path + generaId(50) + "OpenCloseError.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("OpenCloseError");
            //CREAZIONE FONT
            HSSFFont font = workbook.createFont();
            font.setFontName(FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);

            HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
            style1.setFont(font);

            HSSFFont font2 = workbook.createFont();
            font2.setFontName(FONT_ARIAL);
            font2.setFontHeightInPoints((short) 12);

            HSSFCellStyle style2 = (HSSFCellStyle) workbook.createCellStyle();
            style2.setFont(font2);

            HSSFFont font3 = workbook.createFont();
            font3.setFontName(FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);
            font3.setBold(true);

            HSSFCellStyle style3 = (HSSFCellStyle) workbook.createCellStyle();
            style3.setFont(font3);
            style3.setAlignment(RIGHT);
            style3.setBorderTop(THIN);
            style3.setBorderBottom(THIN);

            HSSFCellStyle style3left = (HSSFCellStyle) workbook.createCellStyle();
            style3left.setFont(font3);
            style3left.setAlignment(LEFT);
            style3left.setBorderTop(THIN);
            style3left.setBorderBottom(THIN);

            HSSFFont font4 = workbook.createFont();
            font4.setFontName(FONT_ARIAL);
            font4.setFontHeightInPoints((short) 10);

            HSSFCellStyle style4 = (HSSFCellStyle) workbook.createCellStyle();
            style4.setAlignment(RIGHT);
            style4.setBorderTop(THIN);
            style4.setBorderBottom(THIN);

            HSSFDataFormat hssfDataFormat = (HSSFDataFormat) workbook.createDataFormat();

            HSSFCellStyle cellStylenumint = (HSSFCellStyle) workbook.createCellStyle();
            cellStylenumint.setDataFormat(hssfDataFormat.getFormat(formatdataCellint));
            cellStylenumint.setAlignment(RIGHT);
            cellStylenumint.setBorderTop(THIN);
            cellStylenumint.setBorderBottom(THIN);

            HSSFCellStyle style4left = (HSSFCellStyle) workbook.createCellStyle();
            style4left.setAlignment(LEFT);
            style4left.setBorderTop(THIN);
            style4left.setBorderBottom(THIN);

            HSSFCellStyle cellStylenum = (HSSFCellStyle) workbook.createCellStyle();

            cellStylenum.setAlignment(RIGHT);
            cellStylenum.setBorderTop(THIN);
            cellStylenum.setBorderBottom(THIN);
            cellStylenum.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            HSSFCellStyle style3num = (HSSFCellStyle) workbook.createCellStyle();
            style3num.setFont(font3);
            style3num.setAlignment(RIGHT);
            style3num.setBorderTop(THIN);
            style3num.setBorderBottom(THIN);
            style3num.setDataFormat(hssfDataFormat.getFormat(formatdataCell));

            HSSFCellStyle cellStylenumRATE = (HSSFCellStyle) workbook.createCellStyle();
            HSSFDataFormat hssfDataFormatRATE = (HSSFDataFormat) workbook.createDataFormat();
            cellStylenumRATE.setAlignment(RIGHT);
            cellStylenumRATE.setBorderTop(THIN);
            cellStylenumRATE.setBorderBottom(THIN);
            cellStylenumRATE.setDataFormat(hssfDataFormatRATE.getFormat(formatdataCellRate));

            Row rowP = sheet.createRow((short) 1);

            if (iscentral) {
                intestazionePdf = "Cashier Open/Close Error";
            }

            Cell cl = rowP.createCell(1);
            cl.setCellStyle(style1);
            cl.setCellValue(intestazionePdf + " from " + data1 + " to " + data2);

//            Cell cl2 = rowP.createCell(6);
//            cl2.setCellStyle(style2);
//            cl2.setCellValue(data1);
            for (int h = 0; h < osplist.size(); h++) {

                Openclose_Error_value osp = osplist.get(h);

                Row row = sheet.createRow((short) 3);
                if (iscentral) {
                    row.createCell(1).setCellValue("");
                } else {
                    row.createCell(1).setCellValue(osp.getId_filiale() + " " + osp.getDe_filiale());
                }

                ArrayList<Openclose_Error_value_stock> dati = osp.getDati();

                int cntriga = 6;
                int cntrigachange = 0;
                int cntriganochange = 0;

                for (int i = 0; i < dati.size(); i++) {
                    cntriga++;

                    Row row5 = sheet.createRow((short) cntriga);
                    //mi scandisco le colonne
                    for (int j = 0; j < colonne.size(); j++) {

                        Cell cl5 = row5.createCell(j + 1);
                        cl5.setCellStyle(style3);
                        if (j == 2 || j == 4 || j == 5) {
                            cl5.setCellStyle(style3left);
                        }
                        cl5.setCellValue(colonne.get(j));

                    }

                    cntriga++;

                    Openclose_Error_value_stock dpftemp = (Openclose_Error_value_stock) dati.get(i);
                    List templist = dpftemp.getDati();

                    boolean firstc = true, firstnc = true, firstpos = true;

                    ArrayList<String> elencodiff = new ArrayList<>();
                    ArrayList<Double> elencodiffNC = new ArrayList<>();
                    ArrayList<Double> elencodiffPOS = new ArrayList<>();

                    boolean presch = false;
                    boolean presnc = false;
                    boolean prespos = false;

                    Row row6 = sheet.createRow((short) cntriga);
                    for (int x = 0; x < templist.size(); x++) {

//                    presch = false;
//                    presnc = false;
//                    prespos = false;
//
                        Openclose_Error_value temp = (Openclose_Error_value) templist.get(x);

                        if (x == 0) {

                            String tipoesteso = capitalize(temp.getTipo().toLowerCase());

                            Cell f1 = row6.createCell(1);
                            f1.setCellStyle(style4);
                            f1.setCellValue(temp.getUser());

                            Cell f2 = row6.createCell(2);
                            f2.setCellStyle(style4);
                            f2.setCellValue(temp.getTill());

                            Cell f3 = row6.createCell(3);
                            f3.setCellStyle(style4left);
                            f3.setCellValue(temp.getData());

                            Cell f4 = row6.createCell(4);
                            f4.setCellStyle(style4);
                            f4.setCellValue(temp.getCod());

                            Cell f5 = row6.createCell(5);
                            f5.setCellStyle(style4left);
                            f5.setCellValue(tipoesteso);

                            Cell f6 = row6.createCell(6);
                            f6.setCellStyle(style4left);
                            f6.setCellValue(temp.getId_filiale() + " " + temp.getDe_filiale());

//                        row6.createCell(1).setCellValue(temp.getUser());
//                        row6.createCell(2).setCellValue(temp.getTill());
//                        row6.createCell(3).setCellValue(temp.getData());
//                        row6.createCell(4).setCellValue(temp.getCod());
//                        row6.createCell(5).setCellValue(tipoesteso);
                            cntriga++;
                            cntriga++;
                        }
                        cntriga++;
//                    document.add(table3);
                        if (temp.getType().equalsIgnoreCase("ch")) {
                            presch = true;
                            if (firstc) {
                                firstc = false;
                                Row row8 = sheet.createRow((short) cntriga);
                                cntriga++;
                                for (int g = 0; g < colonnedettaglio.size(); g++) {

                                    Cell f6 = row8.createCell(g + 1);
                                    f6.setCellStyle(style3);
                                    f6.setCellValue(colonnedettaglio.get(g));
                                    if (g == 0 || g == 5) {
                                        f6.setCellStyle(style3left);
                                    }
//                                row8.createCell(g).setCellValue(colonnedettaglio.get(g));
//                                cntriga++;
                                }
                                cntriga++;
                                cntriga++;
                            }

//                        elencodiff.add(temp.getDiffAmount());
                            double diff;
                            double qtautentediv = fd(temp.getQuantityUser());
                            double qtasistdiv = fd(temp.getQuantitySystem());

//
                            diff = qtautentediv - qtasistdiv;
                            elencodiff.add(temp.getDiffAmount());

                            Row row9 = sheet.createRow((short) cntriga);

                            Cell f7 = row9.createCell(1);
                            f7.setCellStyle(style4left);
                            f7.setCellValue(temp.getCurrency());

                            Cell f8 = row9.createCell(2);
                            f8.setCellStyle(style4);
                            f8.setCellValue(temp.getKind());

                            Cell f9 = row9.createCell(3, NUMERIC);
                            f9.setCellStyle(cellStylenum);
                            f9.setCellValue(fd(temp.getQuantityUser()));

                            Cell f10 = row9.createCell(4, NUMERIC);
                            f10.setCellStyle(cellStylenum);
                            f10.setCellValue(fd(temp.getQuantitySystem()));

                            Cell f10bis = row9.createCell(5, NUMERIC);
                            f10bis.setCellStyle(cellStylenum);
                            f10bis.setCellValue(fd(roundDoubleandFormat(diff, 2)));

                            Cell f11 = row9.createCell(6, NUMERIC);
                            f11.setCellStyle(cellStylenumRATE);
                            f11.setCellValue(fd(temp.getRate()));

                            Cell f12 = row9.createCell(7, NUMERIC);
                            f12.setCellStyle(cellStylenum);
                            f12.setCellValue(fd(temp.getDiffAmount()));

                            Cell f13 = row9.createCell(8);
                            f13.setCellStyle(style4left);
                            f13.setCellValue(temp.getNote());

//                        row9.createCell(1).setCellValue(temp.getCurrency());
//                        row9.createCell(2).setCellValue(temp.getKind());
//                        row9.createCell(3).setCellValue(formatMysqltoDisplay(temp.getQuantityUser()));
//                        row9.createCell(4).setCellValue(formatMysqltoDisplay(temp.getQuantitySystem()));
//                        row9.createCell(5).setCellValue(formatMysqltoDisplay(temp.getRate()));
//                        row9.createCell(6).setCellValue(formatMysqltoDisplay(temp.getDiffAmount()));
//                        row9.createCell(7).setCellValue(temp.getNote());
                            cntriga++;

                            cntrigachange = cntriga;

                            cntriga++;
                            cntriga++;

                        }//if type = CH
                        else if (temp.getType().equalsIgnoreCase("nc")) {
                            presnc = true;

                            if (firstnc) {
                                cntriga++;
                                cntriga++;

                                cntriga++;
                                firstnc = false;
                                Row row10 = sheet.createRow((short) cntriga);
                                for (int g = 0; g < colonnedettaglioNoChange.size(); g++) {

                                    Cell f14 = row10.createCell(g + 1);
                                    f14.setCellStyle(style3);
                                    f14.setCellValue(colonnedettaglioNoChange.get(g));
                                    if (g == 0 || g == 5) {
                                        f14.setCellStyle(style3left);
                                    }
//                                row10.createCell(g).setCellValue(colonnedettaglioNoChange.get(g));
                                    //cntriga++;
                                }
                                cntriga++;

                            }

                            //mi calcolo i valori da inserire
                            double diff;
                            double qtautente = fd(temp.getQuantityUser());
                            double qtasist = fd(temp.getQuantitySystem());

                            diff = qtautente - qtasist;

                            elencodiffNC.add(fd(temp.getLocalamount()));

                            Row row11 = sheet.createRow((short) cntriga);

                            Cell f15 = row11.createCell(1);
                            f15.setCellStyle(style4left);
                            f15.setCellValue(temp.getNc());

                            Cell f16 = row11.createCell(2, NUMERIC);
                            f16.setCellStyle(cellStylenumint);
                            f16.setCellValue(fd(roundDoubleandFormat(fd(temp.getQuantityUser()), 0)));

                            Cell f17 = row11.createCell(3, NUMERIC);
                            f17.setCellStyle(cellStylenumint);
                            f17.setCellValue(fd(roundDoubleandFormat(fd(temp.getQuantitySystem()), 0)));

                            Cell f18 = row11.createCell(4, NUMERIC);
                            f18.setCellStyle(cellStylenumint);
                            f18.setCellValue(fd(roundDoubleandFormat(diff, 0)));

                            Cell f19 = row11.createCell(5, NUMERIC);
                            f19.setCellStyle(cellStylenum);
                            f19.setCellValue(fd(temp.getLocalamount()));

                            Cell f20 = row11.createCell(6);
                            f20.setCellStyle(style4left);
                            f20.setCellValue(temp.getNote());

//                        row11.createCell(1).setCellValue();
//                        row11.createCell(2).setCellValue();
//                        row11.createCell(3).setCellValue();
//                        row11.createCell(4).setCellValue();
//                        row11.createCell(5).setCellValue();
//                        row11.createCell(6).setCellValue();
                            cntriga++;

                            cntriganochange = cntriga;
                            cntriga++;
                            cntriga++;
                        } else {
                            prespos = true;

                            if (firstpos) {
                                cntriga++;
                                cntriga++;

                                cntriga++;
                                firstpos = false;
                                Row row12 = sheet.createRow((short) cntriga);
                                for (int g = 0; g < colonnedettaglioPOS.size(); g++) {

                                    Cell f21 = row12.createCell(g + 1);
                                    f21.setCellStyle(style3);
                                    f21.setCellValue(colonnedettaglioPOS.get(g));
                                    if (g == 0 || g == 5) {
                                        f21.setCellStyle(style3left);
                                    }
//                                row12.createCell(g).setCellValue(colonnedettaglioPOS.get(g));
                                    // cntriga++;
                                }
                                cntriga++;
                            }

                            //mi calcolo i valori da inserire
                            elencodiffPOS.add(fd(temp.getDiffAmount()));

                            Row row13 = sheet.createRow((short) cntriga + x);

                            Cell f22 = row13.createCell(1);
                            f22.setCellStyle(style4left);
                            f22.setCellValue(temp.getPos());

                            Cell f23 = row13.createCell(2, NUMERIC);
                            f23.setCellStyle(cellStylenumint);
                            f23.setCellValue(fd(roundDoubleandFormat(fd(temp.getQuantityUser()), 0)));

                            Cell f24 = row13.createCell(3, NUMERIC);
                            f24.setCellStyle(cellStylenum);
                            f24.setCellValue(fd(roundDoubleandFormat(fd(temp.getAmountuser()), 2)));

                            Cell f245 = row13.createCell(4, NUMERIC);
                            f245.setCellStyle(cellStylenumint);
                            f245.setCellValue(fd(roundDoubleandFormat(fd(temp.getQuantitySystem()), 0)));

                            Cell f2451 = row13.createCell(5, NUMERIC);
                            f2451.setCellStyle(cellStylenum);
                            f2451.setCellValue(fd(roundDoubleandFormat(fd(temp.getAmountsystem()), 2)));

                            Cell f25 = row13.createCell(6, NUMERIC);
                            f25.setCellStyle(cellStylenumint);
                            f25.setCellValue(fd(roundDoubleandFormat(fd(temp.getQuantitydiff()), 0)));

                            Cell f251 = row13.createCell(7, NUMERIC);
                            f251.setCellStyle(cellStylenum);
                            f251.setCellValue(fd(roundDoubleandFormat(fd(temp.getDiffAmount()), 2)));

                            Cell f227 = row13.createCell(8);
                            f227.setCellStyle(style4left);
                            f227.setCellValue(temp.getNote());

                            cntriga++;
                        }
                        cntriga++;

                    }

                    cntriga++;
//                cntriga++;

//                cntrigatot = cntriga;
                    double totdiff = 0;
                    if (presch) {

                        for (int t = 0; t < elencodiff.size(); t++) {
                            totdiff += fd(elencodiff.get(t));
                        }
                        cntrigachange++;
                        Row row14 = sheet.createRow((short) cntrigachange);
                        Cell f32 = row14.createCell(6);
                        f32.setCellStyle(style3);
                        f32.setCellValue("Balance Change");

                        Cell f33 = row14.createCell(7, NUMERIC);
                        f33.setCellStyle(style3num);
                        f33.setCellValue(fd(roundDoubleandFormat(totdiff, 2)));

//                    cntriga++;
//                    cntriga++;
//                    cntrigatot = cntriga;
                    }

                    cntriga++;
                    cntriga++;

                    double totdiffpNC = 0;
                    if (presnc) {

                        for (int t = 0; t < elencodiffNC.size(); t++) {
                            totdiffpNC += fd(elencodiffNC.get(t).toString());
                        }
                        cntriganochange++;
                        Row row14 = sheet.createRow((short) cntriganochange);
                        Cell f32 = row14.createCell(6);
                        f32.setCellStyle(style3);
                        f32.setCellValue("Balance No Change");

                        Cell f33 = row14.createCell(7, NUMERIC);
                        f33.setCellStyle(style3num);
                        f33.setCellValue(fd(roundDoubleandFormat(totdiffpNC, 2)));
                    }

                    double totdiffpos = 0;
                    if (prespos) {

                        for (int t = 0; t < elencodiffPOS.size(); t++) {
                            totdiffpos += fd(elencodiffPOS.get(t).toString());
                        }

                        Row row14 = sheet.createRow((short) cntriga);
                        Cell f32 = row14.createCell(6);
                        f32.setCellStyle(style3);
                        f32.setCellValue("Balance POS / Bank Account");

                        Cell f33 = row14.createCell(7, NUMERIC);
                        f33.setCellStyle(style3num);
                        f33.setCellValue(fd(roundDoubleandFormat(totdiffpos, 2)));

                        cntriga++;
                        cntriga++;

                    }

                    Row row141 = sheet.createRow((short) cntriga);
                    Cell f32 = row141.createCell(6);
                    f32.setCellStyle(style3);
                    f32.setCellValue("Final Balance");

                    Cell f33 = row141.createCell(7, NUMERIC);
                    f33.setCellStyle(style3num);
                    f33.setCellValue(fd(roundDoubleandFormat(totdiff + totdiffpNC + totdiffpos, 2)));

                    cntriga++;
                    cntriga++;
                    cntriga++;
                    cntriga++;

                }

            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);

            try (FileOutputStream out = new FileOutputStream(pdf)) {
                workbook.write(out);
            }
            String base64 = new String(encodeBase64(readFileToByteArray(pdf)));
            pdf.delete();
            return base64;

        } catch (IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
        return null;
    }

}
