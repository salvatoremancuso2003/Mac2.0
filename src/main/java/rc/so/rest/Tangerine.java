/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.so.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import rc.so.util.Utility;

import static rc.so.util.Engine.getConfCentral;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Utility.estraiEccezione;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import org.apache.http.HttpEntity;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import static org.apache.http.impl.client.HttpClients.createDefault;
import org.json.JSONObject;

/**
 *
 * @author rcosco
 */
public class Tangerine {

    public static String login_TA(String username, String filiale) {
        try {
            String token = get_token();
            if (!token.startsWith("ERROR")) {
                String link = get_link(token, username, filiale);
                return link;
            } else {
                return token;
            }
        } catch (Exception e) {
            insertTR("E", "API", estraiEccezione(e));
            return "ERROR: TANGERINE: " + estraiEccezione(e);
        }
    }

    private static String get_link(String token, String username, String filiale) {
        try {
            Conf_Tangerine confTG = new Gson().fromJson(getConfCentral("client.tangerine"), new TypeToken<Conf_Tangerine>() {
            }.getType());
            HttpClient httpclient = createDefault();
            HttpPost httppost = new HttpPost(confTG.getEndpoint_link());
            JSONObject json = new JSONObject();
            String sanitizedusername = Utility.sanitize(username);
            String sanitizefiliale = Utility.sanitize(filiale);
            String sanitizeidentifier_type = Utility.sanitize(confTG.getIdentifier_type());
            json.put("identifier", sanitizedusername);
            json.put("identifier_type", sanitizeidentifier_type);
            json.put("filiale", sanitizefiliale);
            StringEntity params = new StringEntity(json.toString());
            httppost.addHeader(CONTENT_TYPE, "application/json");
            String sanitizedtoken = Utility.sanitize(token);
            httppost.addHeader(AUTHORIZATION, "Bearer " + sanitizedtoken);
            httppost.setEntity(params);
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity entity = resp.getEntity();
            if (entity != null) {
                try (InputStream instream = entity.getContent()) {
                    StringBuilder response;
                    try ( //success
                            BufferedReader in = new BufferedReader(new InputStreamReader(
                                    instream))) {
                        String inputLine;
                        response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                    }
                    String out = response.toString();
                    insertTR("I", "API", "TANGERINE GET LINK: " + out);
                    JSONObject output = new JSONObject(out);

                    if (output.has("link")) {
                        return output.getString("link");
                    } else {
                        return "ERROR: TANGERINE GET LINK: " + output.getString("message");
                    }
                }
            }
        } catch (Exception e) {
            insertTR("E", "API", estraiEccezione(e));
            return "ERROR: " + estraiEccezione(e);
        }
        return "ERROR: TANGERINE GET LINK: GENERIC ERROR.";
    }

    private static String get_token() {
        try {
            Conf_Tangerine confTG = new Gson().fromJson(getConfCentral("client.tangerine"), new TypeToken<Conf_Tangerine>() {
            }.getType());
            HttpClient httpclient = createDefault();
            HttpPost httppost = new HttpPost(confTG.getEndpoint_token());
            JSONObject json = new JSONObject();
            json.put("grant_type", confTG.getGrant_type());
            json.put("client_id", confTG.getClient_id());
            json.put("client_secret", confTG.getClient_secret());
            json.put("scope", confTG.getScope());
            StringEntity params = new StringEntity(json.toString());
            httppost.addHeader(CONTENT_TYPE, "application/json");
            httppost.setEntity(params);
            HttpResponse resp = httpclient.execute(httppost);
            HttpEntity entity = resp.getEntity();
            if (entity != null) {
//                System.out.println("rc.so.rest.Tangerine.get_token() " + json.toString());

                try (InputStream instream = entity.getContent()) {
                    StringBuilder response;
                    try ( //success
                            BufferedReader in = new BufferedReader(new InputStreamReader(
                                    instream))) {
                        String inputLine;
                        response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                    }
                    String out = response.toString();
                    insertTR("I", "API", "TANGERINE GET TOKEN: " + out);
                    JSONObject output = new JSONObject(out);
                    if (output.has("access_token")) {
                        return output.getString("access_token");
                    } else {
                        insertTR("E", "API", "TANGERINE GET TOKEN ERROR: " + output.getString("error_description"));
                        return "ERROR: TANGERINE GET ERROR: " + output.getString("error_description");
                    }
                }
            }
        } catch (Exception e) {
            insertTR("E", "API", estraiEccezione(e));
            return "ERROR: TANGERINE GET TOKEN: " + estraiEccezione(e);
        }
        return "ERROR: TANGERINE GET TOKEN: GENERIC ERROR.";
    }

}

class Conf_Tangerine {

    String endpoint_token, grant_type, client_id, client_secret, scope, endpoint_link, identifier_type;

    public String getEndpoint_token() {
        return endpoint_token;
    }

    public void setEndpoint_token(String endpoint_token) {
        this.endpoint_token = endpoint_token;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getEndpoint_link() {
        return endpoint_link;
    }

    public void setEndpoint_link(String endpoint_link) {
        this.endpoint_link = endpoint_link;
    }

    public String getIdentifier_type() {
        return identifier_type;
    }

    public void setIdentifier_type(String identifier_type) {
        this.identifier_type = identifier_type;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, SHORT_PREFIX_STYLE);
    }

}
