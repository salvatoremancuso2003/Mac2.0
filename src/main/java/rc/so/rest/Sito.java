package rc.so.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static rc.so.util.Engine.getConf;
import static rc.so.util.Engine.insertTR;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 *
 * @author rcosco
 */
public class Sito {

    //////////////SITO BACKEND//////////////////
    /**
     *
     * @param json
     * @param codbooking
     * @return
     */
    public static String sendRequest_EDIT_CB(String json, String codbooking) {
        try {
            String link = getConf("sito.editcb.2023");
            insertTR("W", "site", "sendRequest_EDIT_CB() - " + link + " : " + json);            
            URL obj = new URL(link);
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("userId", "a1bcdefgh");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);
            postConnection.setConnectTimeout(10000);
            try ( OutputStream os = postConnection.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }
            int responseCode = postConnection.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                StringBuilder response;
                try ( //success
                         BufferedReader in = new BufferedReader(new InputStreamReader(
                                postConnection.getInputStream()))) {
                    String inputLine;
                    response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                String out = response.toString();
                insertTR("W", "site", codbooking + " sendRequest_EDIT_CB(OUT) " + out);
                return format_response_API(out);
            }
            insertTR("E", "site", codbooking + " sendRequest_EDIT_CB(OUT) ERROR: " + responseCode + " -- " + postConnection.getResponseMessage());
            return "ERROR: PAYMENT NOT SUCCESSFUL.";
        } catch (Exception ex) {
            insertTR("E", "site", codbooking + " sendRequest_EDIT_CB(OUT) ERROR: " + ex.getMessage());
            return "ERROR: unreachable payment system.".toUpperCase();
        }
    }
    public static String sendRequest_EDIT_WT(String json, String codbooking) {
        try {
            String link = getConf("sito.editwt.2023");
            insertTR("W", "site", "sendRequest_EDIT_WT() - " + link + " : " + json);            
            URL obj = new URL(link);
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("userId", "a1bcdefgh");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);
            postConnection.setConnectTimeout(10000);
            try ( OutputStream os = postConnection.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }
            int responseCode = postConnection.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                StringBuilder response;
                try ( //success
                         BufferedReader in = new BufferedReader(new InputStreamReader(
                                postConnection.getInputStream()))) {
                    String inputLine;
                    response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                String out = response.toString();
                insertTR("W", "site", codbooking + " sendRequest_EDIT_WT(OUT) " + out);
                return format_response_API(out);
            }
            insertTR("E", "site", codbooking + " sendRequest_EDIT_WT(OUT) ERROR: " + responseCode + " -- " + postConnection.getResponseMessage());
            return "ERROR: PAYMENT NOT SUCCESSFUL.";
        } catch (Exception ex) {
            insertTR("E", "site", codbooking + " sendRequest_EDIT_WT(OUT) ERROR: " + ex.getMessage());
            return "ERROR: unreachable payment system.".toUpperCase();
        }
    }
    /**
     *
     * @param json
     * @param codbooking
     * @return
     */
    public static String sendRequest_EDIT(String json, String codbooking) {
        try {
            String link = getConf("sito.edit.2022");
            insertTR("W", "site", "sendRequest_SITO() - " + link + " : " + json);            
            URL obj = new URL(link);
            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("userId", "a1bcdefgh");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);
            postConnection.setConnectTimeout(10000);
            try ( OutputStream os = postConnection.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }
            int responseCode = postConnection.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                StringBuilder response;
                try ( //success
                         BufferedReader in = new BufferedReader(new InputStreamReader(
                                postConnection.getInputStream()))) {
                    String inputLine;
                    response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                String out = response.toString();
                insertTR("W", "site", codbooking + " sendRequest_SITO(OUT) " + out);
                return format_response_API(out);
            }
            insertTR("E", "site", codbooking + " sendRequest_SITO(OUT) ERROR: " + responseCode + " -- " + postConnection.getResponseMessage());
            return "ERROR: PAYMENT NOT SUCCESSFUL.";
        } catch (Exception ex) {
            insertTR("E", "site", codbooking + " sendRequest_SITO(OUT) ERROR: " + ex.getMessage());
            return "ERROR: unreachable payment system.".toUpperCase();
        }
    }

    private static String format_response_API(String code) {
        try {
            Type messagetype = new TypeToken<RespAPI>() {
            }.getType();
            RespAPI ob = new Gson().fromJson(code, messagetype);
            String msg = ob.getMessage().toString();
            if (msg.contains("GW00461")) {
                msg = "pre-authorization not found".toUpperCase();
            }
            insertTR("I", "API", ob.isResult() + " - " + ob.isStatus() + " - " + msg);
            if (ob.isResult() && ob.isStatus()) {
                return "OK";
            } else if (ob.getMessage().toString().contains("GW00461")) { // preauth non trovata
                return msg;
            }
        } catch (Exception ex) {
            insertTR("E", "site", "sendRequest_SITO(OUT) ERROR JSON: " + ex.getMessage());
        }
        return "ERROR: PAYMENT NOT SUCCESSFUL.";
    }

}

class RespAPI {

    boolean result, status;
    Object message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE);
    }

}
