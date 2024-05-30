/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.rest;

import rc.so.entity.Brand;
import rc.so.entity.Esiti_response;
import rc.so.entity.Paymat_conf;
import rc.so.entity.Taglio;
import rc.so.entity.Ticket;
import rc.so.entity.VerificaRicarica;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.generaId;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author rcosco
 */
public class Paymat_new {

    private static String getNodeValuefromName(Document doc, String name, int index) {
        if (doc.getElementsByTagName(name).item(index) != null) {
            return doc.getElementsByTagName(name).item(index).getTextContent().trim();
        }
        return "";
    }

    /**
     *
     * @param path
     * @param pc
     * @param value_requestId
     * @return
     */
    public ArrayList<Brand> infoBrandRicariche(String path, Paymat_conf pc, String value_requestId) {
        ArrayList<Brand> list = new ArrayList<>();
        File xmlreq = new File(path + "Request_ibr_" + value_requestId + generaId(25) + ".xml");
        File xmlresp = new File(path + "Response_ibr_" + value_requestId + generaId(25) + ".xml");
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("ws", pc.getServerURI());
            SOAPBody soapBody = envelope.getBody();
            SOAPElement infoBrandRicariche = soapBody.addChildElement("infoBrandRicariche", "ws");
            SOAPElement request = infoBrandRicariche.addChildElement("request", "ws");
            SOAPElement callerId = request.addChildElement("callerId", "ws");
            callerId.addTextNode(pc.getValue_callerId());
            SOAPElement requestId = request.addChildElement("requestId", "ws");
            requestId.addTextNode(value_requestId);
            SOAPElement skin = request.addChildElement("skin", "ws");
            skin.addTextNode(pc.getValue_skin());
            String firmamd5 = md5Hex(pc.getValue_callerId() + value_requestId + pc.getValue_sk());
            SOAPElement firma = request.addChildElement("firma", "ws");
            firma.addTextNode(firmamd5);
            SOAPElement token = request.addChildElement("token", "ws");
            token.addTextNode(pc.getValue_token());
            SOAPElement idAziendaDistributore = request.addChildElement("idAziendaDistributore", "ws");
            idAziendaDistributore.addTextNode(pc.getValue_idAziendaEsercente());
            SOAPElement idAziendaEsercente = request.addChildElement("idAziendaEsercente", "ws");
            idAziendaEsercente.addTextNode(pc.getValue_idAziendaEsercente());
            soapMessage.saveChanges();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, pc.getUrl());
            try(OutputStream outr = new FileOutputStream(xmlreq); OutputStream out = new FileOutputStream(xmlresp)){
                soapMessage.writeTo(outr);
                soapResponse.writeTo(out);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Document doc = builder1.parse(xmlresp);
            NodeList infoBrandRicaricheResponse = doc.getElementsByTagName("infoBrandRicaricheResponse");
            if (infoBrandRicaricheResponse.getLength() == 1) {
                NodeList infoBrandRicaricheResult = doc.getElementsByTagName("infoBrandRicaricheResult");
                if (infoBrandRicaricheResult.getLength() >= 1) {
                    String resultCode = getNodeValuefromName(doc, "resultCode", 0);
                    String resultDesc = getNodeValuefromName(doc, "resultDesc", 0);
                    Brand br = new Brand();
                    br.setResultCode(resultCode);
                    br.setResultDesc(resultDesc);
                    list.add(br);
                    if (resultCode.equals("0")) {
                        NodeList brands = doc.getElementsByTagName("Brand");
                        int numprod = brands.getLength();
                        if (numprod >= 1) {
                            for (int i = 0; i < numprod; i++) {
                                Node brand = brands.item(i);
                                if (brand.getNodeType() == 1) {
                                    NodeList brandvalue = brand.getChildNodes();

                                    if (brandvalue.getLength() > 0) {
                                        Brand br1 = new Brand();
                                        for (int k = 0; k < brandvalue.getLength(); k++) {
                                            Node brandvalue_1 = brandvalue.item(k);
                                            switch (brandvalue_1.getNodeName()) {
                                                case "codiceBrand":
                                                    br1.setCodiceBrand(brandvalue_1.getTextContent().trim());
                                                    break;
                                                case "descrizione":
                                                    br1.setDescrizione(brandvalue_1.getTextContent().trim());
                                                    break;
                                                case "tipologia":
                                                    br1.setTipologia(brandvalue_1.getTextContent().trim());
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                        list.add(br1);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
            soapConnection.close();
        } catch (Exception ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            list = new ArrayList<>();
            Brand br = new Brand();
            br.setResultCode("-1");
            br.setResultDesc("ERROR: " + ex.getMessage());
            list.add(br);
        }
        xmlresp.delete();
        xmlreq.delete();
        return list;
    }

    /**
     *
     * @param path
     * @param pc
     * @param value_requestId
     * @param value_idBrand
     * @param namebrand
     * @return
     */
    public ArrayList<Taglio> infoTagliRicariche(String path, Paymat_conf pc, String value_requestId,
            String value_idBrand, String namebrand) {
        ArrayList<Taglio> list = new ArrayList<>();
        File xmlreq = new File(path + "Request_itr_" + value_requestId + "_" + value_idBrand + ".xml");
        File xmlresp = new File(path + "Response_itr_" + value_requestId + "_" + value_idBrand + ".xml");
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("ws", pc.getServerURI());
            SOAPBody soapBody = envelope.getBody();
            SOAPElement infoBrandRicariche = soapBody.addChildElement("infoTagliRicariche", "ws");
            SOAPElement request = infoBrandRicariche.addChildElement("request", "ws");
            SOAPElement callerId = request.addChildElement("callerId", "ws");
            callerId.addTextNode(pc.getValue_callerId());
            SOAPElement requestId = request.addChildElement("requestId", "ws");
            requestId.addTextNode(value_requestId);
            SOAPElement skin = request.addChildElement("skin", "ws");
            skin.addTextNode(pc.getValue_skin());
            SOAPElement userIP = request.addChildElement("userIP", "ws");
            userIP.addTextNode(pc.getValue_userIP());
            String firmamd5 = md5Hex(pc.getValue_callerId() + value_requestId + pc.getValue_sk());
            SOAPElement firma = request.addChildElement("firma", "ws");
            firma.addTextNode(firmamd5);
            SOAPElement token = request.addChildElement("token", "ws");
            token.addTextNode(pc.getValue_token());
            SOAPElement idAziendaDistributore = request.addChildElement("idAziendaDistributore", "ws");
            idAziendaDistributore.addTextNode(pc.getValue_idAziendaEsercente());
            SOAPElement idAziendaEsercente = request.addChildElement("idAziendaEsercente", "ws");
            idAziendaEsercente.addTextNode(pc.getValue_idAziendaEsercente());
            SOAPElement idBrand = request.addChildElement("idBrand", "ws");
            idBrand.addTextNode(value_idBrand);
            soapMessage.saveChanges();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, pc.getUrl());
            try(OutputStream outr = new FileOutputStream(xmlreq); OutputStream out = new FileOutputStream(xmlresp)){
            soapMessage.writeTo(outr);
            soapResponse.writeTo(out);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Document doc = builder1.parse(xmlresp);
            NodeList infoBrandRicaricheResponse = doc.getElementsByTagName("infoTagliRicaricheResponse");
            if (infoBrandRicaricheResponse.getLength() == 1) {
                NodeList infoBrandRicaricheResult = doc.getElementsByTagName("infoTagliRicaricheResult");
                if (infoBrandRicaricheResult.getLength() >= 1) {
                    String resultCode = getNodeValuefromName(doc, "resultCode", 0);
                    String resultDesc = getNodeValuefromName(doc, "resultDesc", 0);
                    Taglio ta = new Taglio();
                    ta.setResultCode(resultCode);
                    ta.setResultDesc(resultDesc);
                    list.add(ta);

                    if (resultCode.equals("0")) {
                        NodeList tagli = doc.getElementsByTagName("Taglio");
                        int numtagli = tagli.getLength();

                        if (numtagli >= 1) {
                            for (int i = 0; i < numtagli; i++) {
                                Node taglio = tagli.item(i);
                                if (taglio.getNodeType() == 1) {
                                    NodeList tagliovalue = taglio.getChildNodes();
                                    if (tagliovalue.getLength() > 0) {
                                        Taglio tag = new Taglio();
                                        tag.setBrand(namebrand);
                                        tag.setBrandcode(value_idBrand);
                                        tag.setTipoProdotto("Ricariche Tel");
                                        for (int k = 0; k < tagliovalue.getLength(); k++) {
                                            Node tagliovalue_1 = tagliovalue.item(k);
                                            switch (tagliovalue_1.getNodeName()) {
                                                case "codiceTaglio":
                                                    tag.setCodiceTaglio(tagliovalue_1.getTextContent().trim());
                                                    break;
                                                case "Descrizione":
                                                    tag.setDescrizione(tagliovalue_1.getTextContent().trim());
                                                    break;
                                                case "Tipologia":
                                                    tag.setTipologia(tagliovalue_1.getTextContent().trim());
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                        list.add(tag);
                                    }
                                }
                            }
                        }
                    } else {
                        insertTR("E", "System", new Exception().getStackTrace()[0].getMethodName() + ": "
                                + value_idBrand + " --- ERROR: " + resultCode + " - " + resultDesc);
                    }
                }
            }
        }
            soapConnection.close();
        
        } catch (IOException | UnsupportedOperationException | ParserConfigurationException | SOAPException
                | DOMException | SAXException ex) {
            ex.printStackTrace();
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            list = new ArrayList<>();
            Taglio ta = new Taglio();
            ta.setResultCode("-1");
            ta.setResultDesc("ERROR: " + ex.getMessage());
            list.add(ta);
        }
        xmlresp.delete();
        xmlreq.delete();
        return list;
    }

    /**
     *
     * @param path
     * @param pc
     * @param value_requestId
     * @param value_idBrand
     * @param value_idTerminale
     * @param value_numeroTelefonico
     * @param value_taglio
     * @param value_importo
     * @return
     */
    public VerificaRicarica reserveRicaricaTelefonica(String path, Paymat_conf pc, String value_requestId,
            String value_idBrand,
            String value_idTerminale, String value_numeroTelefonico, String value_taglio, String value_importo) {
        File xmlreq = new File(path + "Request_rrt_" + value_requestId + ".xml");
        File xmlresp = new File(path + "Response_rrt_" + value_requestId + ".xml");
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("ws", pc.getServerURI());
            SOAPBody soapBody = envelope.getBody();
            SOAPElement infoBrandRicariche = soapBody.addChildElement("reserveRicaricaTelefonica", "ws");
            SOAPElement request = infoBrandRicariche.addChildElement("request", "ws");
            SOAPElement callerId = request.addChildElement("callerId", "ws");
            callerId.addTextNode(pc.getValue_callerId());
            SOAPElement requestId = request.addChildElement("requestId", "ws");
            requestId.addTextNode(value_requestId);
            SOAPElement skin = request.addChildElement("skin", "ws");
            skin.addTextNode(pc.getValue_skin());
            SOAPElement userIP = request.addChildElement("userIP", "ws");
            userIP.addTextNode(pc.getValue_userIP());
            String firmamd5 = md5Hex(pc.getValue_callerId() + value_requestId + pc.getValue_sk());
            SOAPElement firma = request.addChildElement("firma", "ws");
            firma.addTextNode(firmamd5);
            SOAPElement token = request.addChildElement("token", "ws");
            token.addTextNode(pc.getValue_token());
            SOAPElement idAziendaDistributore = request.addChildElement("idAziendaDistributore", "ws");
            idAziendaDistributore.addTextNode(pc.getValue_idAziendaEsercente());
            SOAPElement idAziendaEsercente = request.addChildElement("idAziendaEsercente", "ws");
            idAziendaEsercente.addTextNode(pc.getValue_idAziendaEsercente());
            SOAPElement Cab = request.addChildElement("cab", "ws");
            Cab.addTextNode(pc.getValue_CAB());
            SOAPElement idTerminale = request.addChildElement("idTerminale", "ws");
            idTerminale.addTextNode(value_idTerminale);
            SOAPElement idBrand = request.addChildElement("brand", "ws");
            idBrand.addTextNode(value_idBrand);
            SOAPElement numeroTelefonico = request.addChildElement("numeroTelefonico", "ws");
            numeroTelefonico.addTextNode(value_numeroTelefonico);
            SOAPElement taglio = request.addChildElement("taglio", "ws");
            taglio.addTextNode(value_taglio);
            SOAPElement importo = request.addChildElement("importo", "ws");
            importo.addTextNode(value_importo);
            soapMessage.saveChanges();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, pc.getUrl());
            try(OutputStream outr = new FileOutputStream(xmlreq); OutputStream out = new FileOutputStream(xmlresp)){
                soapMessage.writeTo(outr);
                soapResponse.writeTo(out);
            soapConnection.close();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            Document doc = builder1.parse(xmlresp);

            NodeList reserveRicaricaTelefonicaResponse = doc.getElementsByTagName("reserveRicaricaTelefonicaResponse");
            if (reserveRicaricaTelefonicaResponse.getLength() == 1) {
                NodeList reserveRicaricaTelefonicaResult = doc.getElementsByTagName("reserveRicaricaTelefonicaResult");
                if (reserveRicaricaTelefonicaResult.getLength() >= 1) {
                    String resultCode = getNodeValuefromName(doc, "resultCode", 0);
                    String resultDesc = getNodeValuefromName(doc, "resultDesc", 0);
                    VerificaRicarica vr = new VerificaRicarica();
                    vr.setResultCode(resultCode);
                    vr.setResultDesc(resultDesc);
                    if (resultCode.equals("0")) {
                        vr.setIdTranPaymat(getNodeValuefromName(doc, "idTranPaymat", 0));
                        vr.setIdTranEsterno(getNodeValuefromName(doc, "idTranEsterno", 0));
                    }
                    return vr;
                }
            }
        }
        } catch (IOException | UnsupportedOperationException | ParserConfigurationException | SOAPException
                | SAXException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            VerificaRicarica vr = new VerificaRicarica();
            vr.setResultCode("-1");
            vr.setResultDesc("ERROR: " + ex.getMessage());
            return vr;
        }
        xmlresp.delete();
        xmlreq.delete();
        VerificaRicarica vr = new VerificaRicarica();
        vr.setResultCode("-1");
        vr.setResultDesc("ERROR: NO MESSAGE");
        return vr;
    }

    /**
     *
     * @param path
     * @param pc
     * @param value_requestId
     * @param value_idBrand
     * @param value_reserveRequestId
     * @param value_idTerminale
     * @param value_numeroTelefonico
     * @param value_taglio
     * @param value_importo
     * @return
     */
    public Ticket confirmRicaricaTelefonica(String path, Paymat_conf pc, String value_requestId, String value_idBrand,
            String value_reserveRequestId,
            String value_idTerminale, String value_numeroTelefonico, String value_taglio, String value_importo) {

        File xmlreq = new File(path + "Request_crt_" + value_requestId + ".xml");
        File xmlresp = new File(path + "Response_crt_" + value_requestId + ".xml");

        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("ws", pc.getServerURI());
            SOAPBody soapBody = envelope.getBody();
            SOAPElement infoBrandRicariche = soapBody.addChildElement("confirmRicaricaTelefonica", "ws");
            SOAPElement request = infoBrandRicariche.addChildElement("request", "ws");
            SOAPElement callerId = request.addChildElement("callerId", "ws");
            callerId.addTextNode(pc.getValue_callerId());
            SOAPElement requestId = request.addChildElement("requestId", "ws");
            requestId.addTextNode(value_requestId);
            SOAPElement skin = request.addChildElement("skin", "ws");
            skin.addTextNode(pc.getValue_skin());
            SOAPElement userIP = request.addChildElement("userIP", "ws");
            userIP.addTextNode(pc.getValue_userIP());
            String firmamd5 = md5Hex(pc.getValue_callerId() + value_requestId + pc.getValue_sk());
            SOAPElement firma = request.addChildElement("firma", "ws");
            firma.addTextNode(firmamd5);
            SOAPElement token = request.addChildElement("token", "ws");
            token.addTextNode(pc.getValue_token());
            SOAPElement idAziendaDistributore = request.addChildElement("idAziendaDistributore", "ws");
            idAziendaDistributore.addTextNode(pc.getValue_idAziendaEsercente());

            SOAPElement idAziendaEsercente = request.addChildElement("idAziendaEsercente", "ws");
            idAziendaEsercente.addTextNode(pc.getValue_idAziendaEsercente());

            SOAPElement Cab = request.addChildElement("cab", "ws");
            Cab.addTextNode(pc.getValue_CAB());

            SOAPElement idTerminale = request.addChildElement("idterminale", "ws");
            idTerminale.addTextNode(value_idTerminale);

            SOAPElement reserveRequestId = request.addChildElement("reserveRequestId", "ws");
            reserveRequestId.addTextNode(value_reserveRequestId);

            SOAPElement idBrand = request.addChildElement("brand", "ws");
            idBrand.addTextNode(value_idBrand);

            soapMessage.saveChanges();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, pc.getUrl());

            try(OutputStream outr = new FileOutputStream(xmlreq); OutputStream out = new FileOutputStream(xmlresp)){
                soapMessage.writeTo(outr);
                soapResponse.writeTo(out);
            soapConnection.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Document doc = builder1.parse(xmlresp);
            NodeList confirmRicaricaTelefonicaResponse = doc.getElementsByTagName("confirmRicaricaTelefonicaResponse");
            if (confirmRicaricaTelefonicaResponse.getLength() == 1) {
                NodeList confirmRicaricaTelefonicaResult = doc.getElementsByTagName("confirmRicaricaTelefonicaResult");
                if (confirmRicaricaTelefonicaResult.getLength() >= 1) {
                    String resultCode = getNodeValuefromName(doc, "resultCode", 0);
                    String resultDesc = getNodeValuefromName(doc, "resultDesc", 0);
                    Ticket ti = new Ticket();
                    ti.setResultCode(resultCode);
                    ti.setResultDesc(resultDesc);
                    if (resultCode.equals("0")) {
                        ti.setIdTranPaymat(getNodeValuefromName(doc, "idTranPaymat", 0));
                        ti.setLogoAziendale(getNodeValuefromName(doc, "logoAziendale", 0));
                        ti.setNomeEsercente(getNodeValuefromName(doc, "nomeEsercente", 0));
                        ti.setDescrizioneProdotto(getNodeValuefromName(doc, "descrizioneProdotto", 0));
                        ti.setDataOraTransazione(getNodeValuefromName(doc, "dataOraTransazione", 0));
                        ti.setEsitoTransazione(getNodeValuefromName(doc, "esitoTransazione", 0));
                        ti.setLogoBrand(getNodeValuefromName(doc, "logoBrand", 0));
                        ti.setImportoFacciale(getNodeValuefromName(doc, "importoFacciale", 0));
                        ti.setNoteTransazione(getNodeValuefromName(doc, "noteTransazione", 0));
                        ti.setLinkSitoWeb(getNodeValuefromName(doc, "linkSitoWeb", 0));
                        ti.setNumeroAssistenza(getNodeValuefromName(doc, "numeroAssistenza", 0));
                        ti.setFooterScontrino(getNodeValuefromName(doc, "footerScontrino", 0));
                        ti.setIdTerminale(getNodeValuefromName(doc, "idTerminale", 0));
                        ti.setABI(getNodeValuefromName(doc, "ABI", 0));
                        ti.setCAB(getNodeValuefromName(doc, "CAB", 0));
                        ti.setNumero_operazione(getNodeValuefromName(doc, "numero_operazione", 0));
                        ti.setId_autorizzazione(getNodeValuefromName(doc, "id_autorizzazione", 0));
                        ti.setNumeroTelefonico(getNodeValuefromName(doc, "numeroTelefonico", 0));
                        ti.setIdTranPaymat_ticket(getNodeValuefromName(doc, "idTranPaymat", 1));
                    }
                    return ti;
                }
            }
        }
        } catch (IOException | UnsupportedOperationException | ParserConfigurationException | SOAPException
                | DOMException | SAXException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            Ticket ti = new Ticket();
            ti.setResultCode("-1");
            ti.setResultDesc("ERROR: " + ex.getMessage());
            return ti;
        }
        xmlresp.delete();
        xmlreq.delete();
        Ticket ti = new Ticket();
        ti.setResultCode("-1");
        ti.setResultDesc("ERROR: NO MESSAGE");
        return ti;
    }

    /**
     *
     * @param pc
     * @param value_requestId
     * @param value_idBrand
     * @param value_reserveRequestId
     * @return
     */
    public Esiti_response cancelRicaricaTelefonica(Paymat_conf pc, String value_requestId, String value_idBrand,
            String value_reserveRequestId) {
        File xmlreq = new File("Request_cancrt_" + value_requestId + ".xml");
        File xmlresp = new File("Response_cancrt_" + value_requestId + ".xml");
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration("ws", pc.getServerURI());
            SOAPBody soapBody = envelope.getBody();
            SOAPElement infoBrandRicariche = soapBody.addChildElement("cancelRicaricaTelefonica", "ws");
            SOAPElement request = infoBrandRicariche.addChildElement("request", "ws");
            SOAPElement callerId = request.addChildElement("callerId", "ws");
            callerId.addTextNode(pc.getValue_callerId());
            SOAPElement requestId = request.addChildElement("requestId", "ws");
            requestId.addTextNode(value_requestId);
            SOAPElement skin = request.addChildElement("skin", "ws");
            skin.addTextNode(pc.getValue_skin());
            SOAPElement userIP = request.addChildElement("userIP", "ws");
            userIP.addTextNode(pc.getValue_userIP());
            String firmamd5 = md5Hex(pc.getValue_callerId() + value_requestId + pc.getValue_sk());
            SOAPElement firma = request.addChildElement("firma", "ws");
            firma.addTextNode(firmamd5);
            SOAPElement token = request.addChildElement("token", "ws");
            token.addTextNode(pc.getValue_token());
            SOAPElement idAziendaDistributore = request.addChildElement("idAziendaDistributore", "ws");
            idAziendaDistributore.addTextNode(pc.getValue_idAziendaEsercente());

            SOAPElement idAziendaEsercente = request.addChildElement("idAziendaEsercente", "ws");
            idAziendaEsercente.addTextNode(pc.getValue_idAziendaEsercente());

            SOAPElement reserveRequestId = request.addChildElement("reserveRequestId", "ws");
            reserveRequestId.addTextNode(value_reserveRequestId);

            SOAPElement idBrand = request.addChildElement("brand", "ws");
            idBrand.addTextNode(value_idBrand);

            soapMessage.saveChanges();
            SOAPMessage soapResponse = soapConnection.call(soapMessage, pc.getUrl());

            try(OutputStream outr = new FileOutputStream(xmlreq); OutputStream out = new FileOutputStream(xmlresp)){
                soapMessage.writeTo(outr);
                soapResponse.writeTo(out);
            soapConnection.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Document doc = builder1.parse(xmlresp);
            NodeList cancelRicaricaTelefonicaResponse = doc.getElementsByTagName("cancelRicaricaTelefonicaResponse");
            if (cancelRicaricaTelefonicaResponse.getLength() == 1) {
                NodeList cancelRicaricaTelefonicaResult = doc.getElementsByTagName("cancelRicaricaTelefonicaResult");
                if (cancelRicaricaTelefonicaResult.getLength() >= 1) {
                    String resultCode = doc.getElementsByTagName("resultCode").item(0).getTextContent();
                    String resultDesc = doc.getElementsByTagName("resultDesc").item(0).getTextContent();
                    Esiti_response er = new Esiti_response();
                    er.setCodice(resultCode);
                    er.setDescrizione(resultDesc);
                    return er;
                }
            }
        }
        } catch (IOException | UnsupportedOperationException | ParserConfigurationException | SOAPException
                | DOMException | SAXException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
            Esiti_response er = new Esiti_response();
            er.setCodice("-1");
            er.setDescrizione("ERROR: " + ex.getMessage());
            return er;
        }
        xmlresp.delete();
        xmlreq.delete();
        Esiti_response er = new Esiti_response();
        er.setCodice("-1");
        er.setDescrizione("ERROR: NO MESSAGE");
        return er;
    }
}
