package rc.so.servlets;

import rc.so.db.Db_Master;
import rc.so.entity.Openclose;
import rc.so.entity.Till;
import rc.so.entity.Users;
import static rc.so.util.Constant.patternsql;
import static rc.so.util.Engine.getLastLink;
import static rc.so.util.Engine.get_username;
import static rc.so.util.Engine.insertLogin;
import static rc.so.util.Engine.insertTR;
import static rc.so.util.Engine.list_all_users;
import static rc.so.util.Engine.list_till_status;
import static rc.so.util.HtmlEncoder.htmlMailResetPass;
import rc.so.util.Utility;
import static rc.so.util.Utility.checkLetters;
import static rc.so.util.Utility.checkNumber;
import static rc.so.util.Utility.convMd5;
import static rc.so.util.Utility.generaId;
import static rc.so.util.Utility.redirect;
import static rc.so.util.Utility.sendMailHtml;
import static rc.so.util.Utility.validatemail;
import static rc.so.util.Utility.visualizzaStringaMySQL;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Thread.currentThread;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.replace;
import org.joda.time.DateTime;

/**
 *
 * @author rcosco
 */
public class Login extends HttpServlet {

    private String getLink(Users u) {

        String str = getLastLink(u.getCod());
        if (str != null) {
            return str;
        }

        if (u.getFiliale().equals("000")) { //CENTRALE
            if (u.getFg_tipo().equals("2") || u.getFg_tipo().equals("3")) {
                if (u.getFg_stato().equals("1")) {
                    return "index.jsp"; //centrale modificare
                }
            }
        } else if (u.getFg_tipo().equals("0")) {
            return "index.jsp"; //normal modificare
        } else if (u.getFg_tipo().equals("1")) {
            return "index.jsp"; //advanced modificare
        } else if (u.getFg_tipo().equals("2")) {
            return "index.jsp"; //plus modificare
        } else if (u.getFg_tipo().equals("3")) {
            return "index.jsp"; //Admin modificare
        }
        //utenti non abilitati
        return "login.jsp?esito=KO4";
    }

//    String[] s1 = { "0", "User normal" };
//    String[] s2 = { "1", "User advanced" };
//    String[] s3 = { "2", "User plus" };
//    String[] s4 = { "3", "Admin" };
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = Utility.sanitize(request.getParameter("username"));
        String pass = Utility.sanitize(request.getParameter("password"));
        if ((user != null) && (pass != null)) {
            if (!user.trim().equals("") && !pass.trim().equals("")) {
                Db_Master dbm = new Db_Master();
                Users u = dbm.checkLogin(user, pass);
                String[] fil = dbm.getCodLocal(false);
                dbm.closeDB();
                if (u == null) {
                    //utente non trovato
                    redirect(request, response, "login.jsp?esito=KO1");
                } else //controlla stato
                if (u.getFg_stato().equals("0")) {
                    redirect(request, response, "login.jsp?esito=KO3");
                } else if (u.getFg_stato().equals("2")) {
                    HttpSession se = request.getSession();
                    se.setAttribute("us_pwd", u.getPwd());
                    se.setAttribute("us_cod", u.getCod());
                    se.setAttribute("us_tip", u.getFg_tipo());
                    redirect(request, response, "reset.jsp");
                } else {
                    //set sessione
                    u.setFiliale(fil[0]);

                    HttpSession se = request.getSession();
                    se.setAttribute("us_cod", u.getCod());
                    se.setAttribute("us_user", u.getUsername());
                    se.setAttribute("us_pwd", u.getPwd());
                    se.setAttribute("us_nom", u.getDe_nome());
                    se.setAttribute("us_cog", u.getDe_cognome());
                    se.setAttribute("us_con", u.getConto());
                    se.setAttribute("us_ema", u.getEmail());
                    se.setAttribute("us_tip", u.getFg_tipo());
                    se.setAttribute("us_sta", u.getFg_stato());
                    se.setAttribute("us_fil", u.getFiliale());
                    se.setAttribute("us_nfi", visualizzaStringaMySQL(fil[1]));

//////                    //DELETE VOLATILI CZ
////                    if (Constant.is_CZ && !fil[0].equals("000") && !Constant.test) {
////                        Db_Master db1 = new Db_Master();
////                        Engine.delete_volatili_WEBAPP(fil[0], db1);
////                        db1.closeDB();
////                    }
                    insertTR("I", u.getCod(), u.getFiliale() + " - LOGIN");

                    //NUOVO CONTROLLO
                    String esitonew = insertLogin(u.getUsername());

                    if (esitonew.equals("0")) {
                        ArrayList<Till> array_till_open = list_till_status("O", "");
                        if (!array_till_open.isEmpty()) {

                            String cod = "OCF" + generaId(22);
                            Openclose ocfittizio = new Openclose();
                            ocfittizio.setFiliale(u.getFiliale());
                            ocfittizio.setTill("ALL");
                            ocfittizio.setFg_tipo("SERVICE");
                            ocfittizio.setCod(cod);
                            ocfittizio.setId("-");
                            Db_Master db1 = new Db_Master();
                            DateTime dt = db1.getNowDT();
                            db1.generateandinsertStockPrice(ocfittizio, dt.minusDays(1).toString(patternsql) + " 23:59:59");
                            db1.closeDB();

                        }
                    }
                    redirect(request, response, getLink(u));
                }

            } else {
                //valori vuoti
                redirect(request, response, "login.jsp?esito=KO2");
            }
        } else {
            //valori vuoti
            redirect(request, response, "login.jsp?esito=KO2");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void resetfirst(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession se = request.getSession();
        String oldp = request.getParameter("old").trim();
        String newp = request.getParameter("new");
        String confp = request.getParameter("conf");
        String usr = Utility.checkAttribute(se, "us_cod");

        if (oldp != null && newp != null && confp != null) {
            oldp = oldp.trim();
            newp = newp.trim();
            confp = confp.trim();
            if (!oldp.equals("") && !newp.equals("") && !confp.equals("")) {
                if (!oldp.equals(newp)) {
                    if (newp.equals(confp)) {
                        if (checkNumber(newp)) {
                            if (checkNumber(newp)) {
                                if (checkLetters(newp)) {
                                    Db_Master dbm = new Db_Master();
                                    boolean es = dbm.setPasswordUser(usr, newp, "1", true);
                                    dbm.closeDB();
                                    if (es) {
                                        se.setAttribute("us_pwd", convMd5(newp));
                                        redirect(request, response, "login.jsp?esito=OKR");
                                    }
                                }
                            }
                        }
                    }
                }
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
    protected void resetpsw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession se = request.getSession();
        String oldp = request.getParameter("old").trim();
        String newp = request.getParameter("new");
        String confp = request.getParameter("conf");
        String usr = Utility.checkAttribute(se, "us_cod");

        insertTR("I", (String) request.getSession().getAttribute("us_cod"), (String) request.getSession().getAttribute("us_fil") + " - RESET PASSWORD");

        if (oldp != null && newp != null && confp != null) {
            oldp = oldp.trim();
            newp = newp.trim();
            confp = confp.trim();
            if (!oldp.equals("") && !newp.equals("") && !confp.equals("")) {
                if (!oldp.equals(newp)) {
                    if (newp.equals(confp)) {
                        if (checkNumber(newp)) {
                            if (checkNumber(newp)) {
                                if (checkLetters(newp)) {
                                    Db_Master dbm = new Db_Master();
                                    boolean es = dbm.setPasswordUser(usr, newp, "1", true);
                                    dbm.closeDB();
                                    if (es) {
                                        se.setAttribute("us_pwd", convMd5(newp));
                                        redirect(request, response, "changepassword.jsp?esito=OKR");
                                    } else {
                                        redirect(request, response, "changepassword.jsp?esito=KO7");
                                    }
                                } else {
                                    redirect(request, response, "changepassword.jsp?esito=KO6");
                                }
                            } else {
                                redirect(request, response, "changepassword.jsp?esito=KO5");
                            }
                        } else {
                            redirect(request, response, "changepassword.jsp?esito=KO4");
                        }
                    } else {
                        redirect(request, response, "changepassword.jsp?esito=KO3");
                    }
                } else {
                    redirect(request, response, "changepassword.jsp?esito=KO2");
                }
            } else {
                redirect(request, response, "changepassword.jsp?esito=KO1");
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
    protected void checkuserandreset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            String us = request.getParameter("us");
            ArrayList<Users> uslisList = list_all_users();
            Users u = get_username(us, uslisList);
            try ( PrintWriter out = response.getWriter()) {
                if (u == null) {
                    out.print("Username Not Found");
                } else {
                    String dest = u.getEmail();
                    boolean mailvalid = validatemail(dest);
                    if (!mailvalid) {
                        out.print("The email address associated with this user is not valid.<br>Please contact the system administrator.");
                    } else {
//                String newpass = "9999999a";
                        String newpass = randomNumeric(7) + randomAlphabetic(1);
                        Db_Master dbm = new Db_Master();
                        String filiale = dbm.getCodLocal(true)[0];
                        boolean es = dbm.edit_Pswuser(filiale, u.getCod(), newpass, u.getUsername());
                        dbm.closeDB();
                        if (es) {
                            String ogg = "Mac2.0 - Reset Password - BranchID " + filiale;
                            String txt = htmlMailResetPass();
                            txt = replace(txt, "@username", capitalize(u.getDe_cognome().toLowerCase()) + " " + capitalize(u.getDe_nome().toLowerCase()));
                            txt = replace(txt, "@password", newpass);
                            boolean esmail = sendMailHtml(dest, ogg, txt, null);
                            if (esmail) {
                                out.print("OK");
                            } else {
                                out.print("Impossibile inviare la mail, il server è temporaneamente non disponibile.<br>"
                                        + "La sua nuova password è la seguente: <b>" + newpass + "</b><br>"
                                        + "<u>The password must be changed at the first access.</u>");
                            }
                        } else {
                            out.print("Impossible to change the password. Please contact the system administrator.");
                        }
                    }
                }
            }

        } catch (Exception e) {
            insertTR("E", "SERVICE", "ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("us_cod");
        String fil = (String) request.getSession().getAttribute("us_fil");

        insertTR("I", user, fil + " - LOGOUT");
        request.getSession().invalidate();
        redirect(request, response, "login.jsp");
    }

    /**
     *
     * @param request
     * @param response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
////            request.setCharacterEncoding("UTF-8");
            String type = request.getParameter("type");
            switch (type) {
                case "1":
                    login(request, response);
                    break;
                case "2":
                    logout(request, response);
                    break;
                case "3":
                    resetfirst(request, response);
                    break;
                case "checkuser":
                    checkuserandreset(request, response);
                    break;
//                resnew(request, response);
                case "res":
                    break;
                case "4":
                    resetpsw(request, response);
                    break;
                default:
                    break;
            }
        } catch (ServletException | IOException ex) {
            insertTR("E", "System", currentThread().getStackTrace()[1].getMethodName() + ": " + ex.getMessage());
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
