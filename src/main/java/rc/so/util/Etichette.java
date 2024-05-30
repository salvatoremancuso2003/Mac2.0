package rc.so.util;

import static rc.so.servlets.Query.filiale;
import static rc.so.util.Constant.nation;
import static rc.so.util.Constant.version;

/**
 *
 * @author rcosco
 */
public class Etichette {

    String label1_profilo;
    String label2_blosche;
    String label3_logout;
    String homepage;
    String categoria1;
    String nomecat1;
    String sottocat1;
    String categoria2;
    String nomecat2;
    String sottocat2;
    String footer;
    String accedi;
    String username;
    String password;
    String login;
    String resetpassword;
    String error_log1;
    String messagereset1;
    String email;
    String indietro_log;
    String avanti_log;

    /**
     *
     * @param la
     */
    public Etichette(String la) {
        if (la.toUpperCase().equals("IT")) {
            this.label1_profilo = "Profilo personale";
            this.label2_blosche = "Blocca Schermo";
            this.label3_logout = "Logout";
            this.homepage = "Homepage";
            this.categoria1 = "Gestione";
            this.nomecat1 = "Nuova sottoscrizione";
            this.sottocat1 = "";
            this.categoria2 = "Consultazione";
            this.nomecat2 = "Sottoscrizioni";
            this.sottocat2 = "";
            this.footer = "2020 Mac "+version;
            if (filiale.equals("000")) {
                String bandiera = "<img alt='' src='assets/soop/img/" + nation.toLowerCase() + ".png' height='12px'/> ";
                this.footer = bandiera + this.footer;
            }
            this.accedi = "Accedi";
            this.username = "Username";
            this.password = "Password";
            this.login = "Login";
            this.resetpassword = "Password dimenticata?";
            this.error_log1 = "Inserire username e password";
            this.messagereset1 = "Inserire il vostro indirizzo email per reimpostare la password";
            this.email = "Email";
            this.indietro_log = "Indietro";
            this.avanti_log = "Salva";
        }
    }

    /**
     *
     * @return
     */
    public String getCategoria2() {
        return this.categoria2;
    }

    /**
     *
     * @param categoria2
     */
    public void setCategoria2(String categoria2) {
        this.categoria2 = categoria2;
    }

    /**
     *
     * @return
     */
    public String getNomecat2() {
        return this.nomecat2;
    }

    /**
     *
     * @param nomecat2
     */
    public void setNomecat2(String nomecat2) {
        this.nomecat2 = nomecat2;
    }

    /**
     *
     * @return
     */
    public String getSottocat2() {
        return this.sottocat2;
    }

    /**
     *
     * @param sottocat2
     */
    public void setSottocat2(String sottocat2) {
        this.sottocat2 = sottocat2;
    }

    /**
     *
     * @return
     */
    public String getMessagereset1() {
        return this.messagereset1;
    }

    /**
     *
     * @param messagereset1
     */
    public void setMessagereset1(String messagereset1) {
        this.messagereset1 = messagereset1;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getIndietro_log() {
        return this.indietro_log;
    }

    /**
     *
     * @param indietro_log
     */
    public void setIndietro_log(String indietro_log) {
        this.indietro_log = indietro_log;
    }

    /**
     *
     * @return
     */
    public String getAvanti_log() {
        return this.avanti_log;
    }

    /**
     *
     * @param avanti_log
     */
    public void setAvanti_log(String avanti_log) {
        this.avanti_log = avanti_log;
    }

    /**
     *
     * @return
     */
    public String getError_log1() {
        return this.error_log1;
    }

    /**
     *
     * @param error_log1
     */
    public void setError_log1(String error_log1) {
        this.error_log1 = error_log1;
    }

    /**
     *
     * @return
     */
    public String getAccedi() {
        return this.accedi;
    }

    /**
     *
     * @param accedi
     */
    public void setAccedi(String accedi) {
        this.accedi = accedi;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return this.login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     */
    public String getResetpassword() {
        return this.resetpassword;
    }

    /**
     *
     * @param resetpassword
     */
    public void setResetpassword(String resetpassword) {
        this.resetpassword = resetpassword;
    }

    /**
     *
     * @return
     */
    public String getFooter() {
        return this.footer;
    }

    /**
     *
     * @param footer
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }

    /**
     *
     * @return
     */
    public String getSottocat1() {
        return this.sottocat1;
    }

    /**
     *
     * @param sottocat1
     */
    public void setSottocat1(String sottocat1) {
        this.sottocat1 = sottocat1;
    }

    /**
     *
     * @return
     */
    public String getCategoria1() {
        return this.categoria1;
    }

    /**
     *
     * @param categoria1
     */
    public void setCategoria1(String categoria1) {
        this.categoria1 = categoria1;
    }

    /**
     *
     * @return
     */
    public String getNomecat1() {
        return this.nomecat1;
    }

    /**
     *
     * @param nomecat1
     */
    public void setNomecat1(String nomecat1) {
        this.nomecat1 = nomecat1;
    }

    /**
     *
     * @return
     */
    public String getHomepage() {
        return this.homepage;
    }

    /**
     *
     * @param homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     *
     * @return
     */
    public String getLabel1_profilo() {
        return this.label1_profilo;
    }

    /**
     *
     * @param label1_profilo
     */
    public void setLabel1_profilo(String label1_profilo) {
        this.label1_profilo = label1_profilo;
    }

    /**
     *
     * @return
     */
    public String getLabel2_blosche() {
        return this.label2_blosche;
    }

    /**
     *
     * @param label2_blosche
     */
    public void setLabel2_blosche(String label2_blosche) {
        this.label2_blosche = label2_blosche;
    }

    /**
     *
     * @return
     */
    public String getLabel3_logout() {
        return this.label3_logout;
    }

    /**
     *
     * @param label3_logout
     */
    public void setLabel3_logout(String label3_logout) {
        this.label3_logout = label3_logout;
    }

}
