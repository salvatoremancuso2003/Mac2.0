/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.rest;

import rc.so.entity.Outputf;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.generaId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import static javax.xml.soap.MessageFactory.newInstance;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author rcosco
 */
public class Sign {

    private static final String linkop = getConf("path.ws.sign");
    private static final String psw = getConf("path.ws.sign.pass");
    private static final String usr = getConf("path.ws.sign.user");
    private static final String namespace = getConf("path.ws.sign.name");

    /**
     *
     * @param pathout
     * @param codtr_value
     * @param user_value
     * @param branch_value
     * @param tipodoc_value
     * @param base64_value
     * @param filename_value
     * @return
     */
    public static Outputf sign_document(String pathout, String codtr_value, String user_value, String branch_value,
            String tipodoc_value, String base64_value, String filename_value) {
        Outputf outp = null;
        File xmlresp = new File(pathout + generaId(55) + "response_cancrt.xml");
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            MessageFactory messageFactory = newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("eng", namespace);
            SOAPBody soapBody = envelope.getBody();
            SOAPElement preparaFirma = soapBody.addChildElement("preparaFirma", "eng");
            SOAPElement usr_mac = preparaFirma.addChildElement("usr_mac");
            usr_mac.addTextNode(usr);
            SOAPElement psw_mac = preparaFirma.addChildElement("psw_mac");
            psw_mac.addTextNode(psw);
            SOAPElement codtr = preparaFirma.addChildElement("codtr");
            codtr.addTextNode(codtr_value);
            SOAPElement user = preparaFirma.addChildElement("user");
            user.addTextNode(user_value);
            SOAPElement branch = preparaFirma.addChildElement("branch");
            branch.addTextNode(branch_value);
            SOAPElement typedoc = preparaFirma.addChildElement("typedoc");
            typedoc.addTextNode(tipodoc_value);
            SOAPElement base64 = preparaFirma.addChildElement("base64");
            base64.addTextNode(base64_value);
            SOAPElement filename = preparaFirma.addChildElement("filename");
            filename.addTextNode(filename_value);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", "preparaFirma");
            soapMessage.saveChanges();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, linkop);
            try(OutputStream out = new FileOutputStream(xmlresp)){
            soapResponse.writeTo(out);
            out.close();
            soapConnection.close();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Document doc = builder1.parse(xmlresp);
            NodeList preparaFirmaResponse = doc.getElementsByTagName("ns2:preparaFirmaResponse");
            if (preparaFirmaResponse.getLength() == 1) {
                NodeList returnvalue = doc.getElementsByTagName("return");
                if (returnvalue.getLength() == 1) {
                    String coddoc = doc.getElementsByTagName("coddoc").item(0).getTextContent();
                    String docid = doc.getElementsByTagName("docid").item(0).getTextContent();
                    String error = doc.getElementsByTagName("error").item(0).getTextContent();
                    String esito = doc.getElementsByTagName("esito").item(0).getTextContent();
                    String linkand = doc.getElementsByTagName("linkand").item(0).getTextContent();
                    String linkos = doc.getElementsByTagName("linkos").item(0).getTextContent();
                    String linkweb = doc.getElementsByTagName("linkweb").item(0).getTextContent();
                    String worid = doc.getElementsByTagName("worid").item(0).getTextContent();
                    outp = new Outputf(esito, error, docid, worid, linkweb, linkand, linkos, coddoc);
                }
            }
        }
        } catch (IOException | UnsupportedOperationException | ParserConfigurationException | SOAPException
                | DOMException | SAXException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            outp = null;
        }
        xmlresp.delete();
        return outp;
    }

    /**
     *
     * @param pathout
     * @param codtr_value
     * @param typedoc_value
     * @return
     */
    public static Outputf verify_document(String pathout, String codtr_value, String typedoc_value) {
        Outputf outp = null;
        File xmlresp = new File(pathout + generaId(55) + "response_cancrt.xml");
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            MessageFactory messageFactory = newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("eng", namespace);
            SOAPBody soapBody = envelope.getBody();
            SOAPElement preparaFirma = soapBody.addChildElement("verificaFirma", "eng");
            SOAPElement usr_mac = preparaFirma.addChildElement("usr_mac");
            usr_mac.addTextNode(usr);
            SOAPElement psw_mac = preparaFirma.addChildElement("psw_mac");
            psw_mac.addTextNode(psw);
            SOAPElement codtr = preparaFirma.addChildElement("codtr");
            codtr.addTextNode(codtr_value);
            SOAPElement typedoc = preparaFirma.addChildElement("typedoc");
            typedoc.addTextNode(typedoc_value);
            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", "verificaFirma");
            soapMessage.saveChanges();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, linkop);
            try(OutputStream out = new FileOutputStream(xmlresp)){
            soapResponse.writeTo(out);
            out.close();
            soapConnection.close();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Document doc = builder1.parse(xmlresp);
            NodeList verificaFirmaResponse = doc.getElementsByTagName("ns2:verificaFirmaResponse");
            if (verificaFirmaResponse.getLength() == 1) {
                NodeList returnvalue = doc.getElementsByTagName("return");
                if (returnvalue.getLength() == 1) {
                    String esito = doc.getElementsByTagName("esito").item(0).getTextContent();
                    String error = doc.getElementsByTagName("error").item(0).getTextContent();
                    if (esito.equals("0")) {
                        String codice_documento = doc.getElementsByTagName("codice_documento").item(0).getTextContent();
                        String data_firma = doc.getElementsByTagName("data_firma").item(0).getTextContent();

                        String firma = doc.getElementsByTagName("firma").item(0).getTextContent();
                        String nomefile_firma = doc.getElementsByTagName("nomefile_firma").item(0).getTextContent();
                        String wor_id = doc.getElementsByTagName("wor_id").item(0).getTextContent();
                        outp = new Outputf(codice_documento, data_firma, error, esito, firma, nomefile_firma, wor_id);
                    } else {
                        outp = new Outputf("", "", error, esito, "", "", "");
                    }
                }
            }
        }
        } catch (SOAPException | UnsupportedOperationException | IOException | ParserConfigurationException
                | SAXException | DOMException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            outp = null;
        }
        xmlresp.delete();
        return outp;
    }

}