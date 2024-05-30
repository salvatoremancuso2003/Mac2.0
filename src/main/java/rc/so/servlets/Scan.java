/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import static com.google.gson.JsonParser.parseString;
import rc.so.db.Db_Master;
import rc.so.entity.Ch_transaction_doc;
import static rc.so.util.Constant.patternsqldate;
import static rc.so.util.Constant.patterntsdate2;
import static rc.so.util.Engine.country;
import static rc.so.util.Engine.insertTR;
import rc.so.util.Utility;
import static rc.so.util.Utility.formatTypeDOC;
import static rc.so.util.Utility.formatdatePassport;
import static rc.so.util.Utility.jsonToMap;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.sendPostRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Boolean.valueOf;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.lang3.StringUtils.substring;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author rcosco
 */
public class Scan extends HttpServlet {
    
     private String getObjectContent(JsonObject root,String name) {
        try {
            String content = root.get(name).getAsString();
            if (content != null) {
                return content.trim();
            }
        } catch (Exception e) {
        }
        return "";
    }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void checkADDR(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token").trim();
//        token = "ADD200310122911352JzrM6Ha3qaEnDXgu2zXrJGJYi5vr0Vlc";
        Db_Master db = new Db_Master();
        String[] out = db.get_value_ADDRESS(token);
        db.closeDB();
        
        Gson gson = new Gson();
        ArrayList<String> JSONRequest = new ArrayList<>();
        if(out==null){
            JSONRequest.add(gson.toJson("false"));
        }else{
            JSONRequest.add(gson.toJson("true"));
            JSONRequest.add(gson.toJson(out[0]));
            JSONRequest.add(gson.toJson(out[1]));
            JSONRequest.add(gson.toJson(out[2]));
        }
        try (PrintWriter output = response.getWriter()) {
            output.print(Utility.sanitize(JSONRequest.toString()));
        }
        
    }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void readADDR(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = new BufferedReader(new InputStreamReader(request.getInputStream())).readLine();
        if (json != null) {
            
            JsonElement je = parseString(json);
            JsonObject root = je.getAsJsonObject();
            
            String toke_1 = getObjectContent(root,"Token");
            String addr_2 = substring(getObjectContent(root,"ad1"), 0, 100).toUpperCase();
            String cell_3 = substring(getObjectContent(root,"te1"), 0, 20);
            String mail_4 = substring(getObjectContent(root,"ma1"), 0, 40).toLowerCase();
            Db_Master db = new Db_Master();
            boolean es = db.insert_value_ADDRESS(toke_1, addr_2, cell_3, mail_4);
            db.insertTR("I", "Address", toke_1+" -- "+es);
            db.closeDB();
        }
        
    }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void scanner(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonout = new JSONObject();
        String json = new BufferedReader(new InputStreamReader(request.getInputStream())).readLine();
        if (json != null) {
            try {
                String Response = "{}";
                String Success = "false";
                Map<String, Object> hashMap = new HashMap<>(jsonToMap(json));
                for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                    if (entry.getKey().equals("Success")) {
                        Success = entry.getValue().toString().trim();
                    } else if (entry.getKey().equals("Response")) {
                        Response = entry.getValue().toString().trim();
                    }
                }
                if (valueOf(Success)) {

                    ArrayList<String[]> country = country();

                    jsonout = new JSONObject();
                    jsonout.put("Success", "true");
                    jsonout.put("Error", "");
                    Map<String, Object> hashMap2 = new HashMap<>(jsonToMap(Response));
                    for (Map.Entry<String, Object> entry : hashMap2.entrySet()) {
                        String ind1 = entry.getKey();
                        if (entry.getValue() == null
                                || entry.getValue().toString().trim().equals("null")
                                || entry.getValue().toString().trim().equals("")
                                || entry.getValue().toString().trim().equals("-")
                                || entry.getValue().toString().trim().equals("...")) {
                            
                        } else {
                            String v1 = entry.getValue().toString().trim();
                            if (ind1.equals("heavy_pob_country")) {
                                for (int i = 0; i < country.size(); i++) {
                                    if (v1.trim().equalsIgnoreCase(country.get(i)[2])) {
                                        v1 = country.get(i)[0];
                                        break;
                                    }
                                }
                            } else if (ind1.equals("doc_type")) {
                                ind1 = "heavy_identcard";
                                v1 = formatTypeDOC(v1);
                            } else if (entry.getKey().equals("heavy_issuedplaceidentcard")) {
                                v1 = "-";
                            } else if (entry.getKey().equals("heavy_issuedbyidentcard")) {
                                for (int i = 0; i < country.size(); i++) {
                                    if (v1.trim().equalsIgnoreCase(country.get(i)[2])) {
                                        v1 = country.get(i)[1].toUpperCase();
                                        jsonout.put("heavy_issuedplaceidentcard", v1);
                                        break;
                                    }
                                }
                            } else if (ind1.equals("heavy_pob_date") 
                                    || ind1.equals("heavy_exdateidentcard")) {
                                v1 = formatdatePassport(v1);
                            }
                            //suddividere i valori in base all'ingresso
                            if (!ind1.equals("-")) {
                                jsonout.put(ind1, v1);
                            }
                        }
                    }
                } else {
                    try {
                        jsonout = new JSONObject();
                        jsonout.put("Success", "false");
                        jsonout.put("Error", "response error");
                    } catch (JSONException ex1) {
                        ex1.printStackTrace();
                    }
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
                try {
                    jsonout = new JSONObject();
                    jsonout.put("Success", "false");
                    jsonout.put("Error", ex.getMessage());
                } catch (JSONException ex1) {
                    ex1.printStackTrace();
                }
            }
        }
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonout.toString());
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void scannerOLD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonout = new JSONObject();
        String json = new BufferedReader(new InputStreamReader(request.getInputStream())).readLine();
        if (json != null) {
            JsonElement je = parseString(json);
            JsonObject root = je.getAsJsonObject();
            String CallbackUrl = root.get("CallbackUrl").getAsString().trim();
            String Command = root.get("Command").getAsString().trim();
            String Counter = root.get("Counter").getAsString().trim();
            String Token = root.get("Token").getAsString().trim();
            String scannerHost = root.get("ipad").getAsString().trim();
            try {
                JSONObject json1 = new JSONObject();
                json1.put("CallbackUrl", CallbackUrl);
                json1.put("Command", Command);
                json1.put("Counter", Counter);
                json1.put("Token", Token);

                String link = "http://localhost:9000/scan";

                String resp = sendPostRequest(link, json1.toString());
                if (!resp.startsWith("Err")) {
                    String Response = null;
                    String Success = null;
                    Map<String, Object> hashMap = new HashMap<>(jsonToMap(resp));
                    for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                        if (entry.getKey().equals("Success")) {
                            Success = entry.getValue().toString().trim();
                        } else if (entry.getKey().equals("Response")) {
                            Response = entry.getValue().toString().trim();
                        }
                    }
                    if (valueOf(Success)) {
                        jsonout = new JSONObject();
                        jsonout.put("Success", "true");
                        jsonout.put("Error", "");
                        Map<String, Object> hashMap2 = new HashMap<>(jsonToMap(Response));
                        for (Map.Entry<String, Object> entry : hashMap2.entrySet()) {
                            String v1 = "";
                            if (entry.getValue() == null
                                    || entry.getValue().equals("null")
                                    || entry.getValue().equals("")
                                    || entry.getValue().equals("...")) {

                            } else {
                                v1 = entry.getValue().toString().trim();
                            }
                            jsonout.put(entry.getKey(), v1);
                        }
                    } else {
                        try {
                            jsonout = new JSONObject();
                            jsonout.put("Success", "false");
                            jsonout.put("Error", "response error");
                        } catch (JSONException ex1) {
                        }
                    }
                } else {
                    try {
                        jsonout = new JSONObject();
                        jsonout.put("Success", "false");
                        jsonout.put("Error", resp);
                    } catch (JSONException ex1) {
                    }
                }
            } catch (JSONException ex) {
                try {
                    jsonout = new JSONObject();
                    jsonout.put("Success", "false");
                    jsonout.put("Error", ex.getMessage());
                } catch (JSONException ex1) {
                }
            }
        } else {
            try {
                jsonout = new JSONObject();
                jsonout.put("Success", "false");
                jsonout.put("Error", "request error");
            } catch (JSONException ex1) {
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(Utility.sanitize(jsonout.toString()));
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void removescannerdocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String coddoc_del = request.getParameter("coddoc_del");
        Db_Master db = new Db_Master();
        boolean es = db.delete_doc_transaction_cd(coddoc_del);
        db.closeDB();
        try (PrintWriter out = response.getWriter()) {
            out.print(es);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void scannerdocument(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String read = new BufferedReader(new InputStreamReader(request.getInputStream())).readLine();
        if (read != null) {
            JsonElement je = parseString(read);
            JsonObject root = je.getAsJsonObject();
            String codtr = request.getParameter("trid").trim();
            String Token = root.get("Token").getAsString().trim();
            String base64 = root.get("Payload").getAsString().trim();
            if (base64.equals("")) {
                insertTR("W", "scanner", "Transaction " + codtr + " - PDF scanned is null or blank.");
            } else {
                String dateoper = new DateTime().toString(patternsqldate);
                String filename = new DateTime().toString(patterntsdate2) + codtr + "_scanned.pdf";
                Ch_transaction_doc chd = new Ch_transaction_doc(Token, codtr, "_docrico", base64, filename, dateoper, "-", "Y");
                Db_Master db = new Db_Master();
                boolean es = db.insert_transaction_doc(chd);
                db.closeDB();
            }
        }
    }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void testereet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Ch_transaction tra = Engine.query_transaction_ch("305190327103434190oNFoCQV");
//         //VERIFICA EET
//        rc.so.exe.Client.invia(tra,null);
    }
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void sendPOS_transaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cod = request.getParameter("cod");
        String esito = request.getParameter("esito");
        String resp = Utility.sendPOS_transaction(cod);
        request.getSession().setAttribute("pos1", resp);
        redirect(request, response, "nc_transaction_esito.jsp?esito="+esito+"&cod="+cod);
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String type = request.getParameter("type");
            switch (type) {
                case "scanner":
                    scanner(request, response);
                    break;
                case "removescannerdocument":
                    removescannerdocument(request, response);
                    break;
                case "sendpos":
                    sendPOS_transaction(request, response);
                    break;
                case "checkADDR":
                    checkADDR(request, response);
                    break;
                case "readADDR":
                    readADDR(request, response);
                    break;
                case "testereet":
                    testereet(request, response);
                    break;
                case "scannerdocument":
                    scannerdocument(request, response);
                    break;
                default:
                    break;
            }
        } catch (ServletException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
