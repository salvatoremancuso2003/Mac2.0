<%-- 
    Document   : login
    Created on : 7-giu-2016, 15.46.28
    Author     : raffaele
--%>

<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.util.Etichette"%>
<%
    String link_value = Engine.verifyUser(request);
    if (link_value != null) {
        Utility.redirect(request, response, link_value);
    }
%>
<!DOCTYPE HTML>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>Mac2.0 - Reset Password</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="assets/soop/fontg/fontsgoogle1.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THE GLOBAL STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link href="assets/pages/css/login.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> </head>

    <script src="assets/soop/js/core-min.js" type="text/javascript"></script>
    <script src="assets/soop/js/md5-min.js" type="text/javascript"></script>
    <script src="assets/soop/js/md5.js" type="text/javascript"></script>

    <script src="assets/soop/js/controlli.js" type="text/javascript"></script>


    <script type="text/javascript">
        <%
            String pwd = Utility.checkAttribute(session,"us_pwd");

            String esito = request.getParameter("esito");
            if (esito == null) {
                esito = "";
            }%>


        function checkesito() {
            var es = "<%=Utility.sanitize(esito)%>".trim();
            if (es !== "") {
                var ermsg = "Error: " + es;

                if (es === "KO1") {
                    ermsg = "Login incorrect. Wrong credentials. Check Username/Password.";
                }
                if (es === "KO2") {
                    ermsg = "Login incorrect. Please insert Username/Password.";
                }
                if (es === "KO3") {
                    ermsg = "Login incorrect. Your credentials are inactive.";
                }
                if (es === "KO4") {
                    ermsg = "Login incorrect. You are not authorized to access this branch.";
                }

                document.getElementById("errorlargetext").innerHTML = ermsg;
                document.getElementById('showerror').click();
            }

        }

        function checklogin() {
            var oldp = document.getElementById('old').value.trim();
            var newp = document.getElementById('new').value.trim();
            var confp = document.getElementById('conf').value.trim();
            if (oldp === "" || newp === "" || confp === "") {
                var ermsg = "Reset incorrect. Please complete all fields.";
                document.getElementById("errorlargetext").innerHTML = ermsg;
                document.getElementById('showerror').click();
                return false;
            }

            if (!checkpass(oldp, "<%=pwd%>")) {
                var ermsg = "Reset incorrect. Old password are incorrect.";
                document.getElementById("errorlargetext").innerHTML = ermsg;
                document.getElementById('showerror').click();
                return false;
            }
            if (newp === oldp) {
                var ermsg = "Reset incorrect. New password must be different from the previous.";
                document.getElementById("errorlargetext").innerHTML = ermsg;
                document.getElementById('showerror').click();
                return false;
            }

            if (newp !== confp) {
                var ermsg = "Reset incorrect. Confirm password do not match.";
                document.getElementById("errorlargetext").innerHTML = ermsg;
                document.getElementById('showerror').click();
                return false;
            }
            //Length min.6, Length max.10, 1 alphabetical, 1 numeric 
            if (newp.length < 8) {
                var ermsg = "Reset incorrect. The minimum password length is 8 char.";
                document.getElementById("errorlargetext").innerHTML = ermsg;
                document.getElementById('showerror').click();
                return false;
            }

            var numeric = newp.match(/\d+/g);
            if (numeric === null) {
                var ermsg = "Reset incorrect. Password must contain at least one numeric character.";
                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                document.getElementById("errorlarge").style.display = "block";
                document.getElementById("errorlargetext").innerHTML = ermsg;
                return false;
            }

            var letters = newp.search(/[a-z]/i);
            if (letters === -1) {
                var ermsg = "Reset incorrect. Password must contain at least one alphabetical character.";
                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                document.getElementById("errorlarge").style.display = "block";
                document.getElementById("errorlargetext").innerHTML = ermsg;
                return false;
            }


            //return false;
        }

        function closing() {
            dismiss('errorlarge');
        }


    </script>
    <!-- END HEAD -->

    <body class="login" onload="return checkesito();">
        <div class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
            <button id="showerror" data-toggle="modal" data-target="#errorlarge" style="size: 1px;"/>
        </div>
        <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title font-red uppercase"><b>LOGIN ERROR</b></h4>
                    </div>
                    <div class="modal-body" id="errorlargetext">ERROR</div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" onclick="return closing();" data-dismiss="modal">Close</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <%
            String lan_index = (String) session.getAttribute("language");
            lan_index = "IT";
            Etichette et_index = new Etichette(lan_index);
        %>

        <!-- BEGIN LOGO -->
        <div class="logo">
            <img src="assets/soop/img/logocl.png" alt="" /> 
        </div>
        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content">
            <!-- BEGIN LOGIN FORM -->
            <form class="login-form" action="Login?type=3" method="post" onsubmit="return checklogin();">
                <h3 class="form-title font-grey-cascade">Reset Password</h3>

                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Old Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" 
                           type="password" autocomplete="off" maxlength="10" 
                           placeholder="Old Password" name="old" id="old" /> 
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">New Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" 
                           type="password" autocomplete="off" maxlength="10"
                           placeholder="New Password" name="new" id="new" /> 
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9">Confirm Password</label>
                    <input class="form-control form-control-solid placeholder-no-fix" type="password"
                           autocomplete="off" maxlength="10" placeholder="Confirm Password" name="conf" id="conf" /> 
                </div>
                <div class="form-group help-block alert-info">
                    <label>The password must meet the following requirements:<p class="ab"></p><b>Length min: 8,<p class="ab"></p>Length max: 10,<p class="ab"></p>1 alphabetical char,<p class="ab"></p>1 numeric char</b></label>
                </div>

                <div class="form-actions">
                    <center><button type="submit" class="btn btn-outline grey-cascade uppercase">Confirm <i class="icon-check"></i></button></center>
                </div>

            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM -->

            <!-- END FORGOT PASSWORD FORM -->
            <!-- BEGIN REGISTRATION FORM -->
            <!-- END REGISTRATION FORM -->
        </div>
        <div class="copyright"><%=et_index.getFooter()%></div>
        <!-- BEGIN CORE PLUGINS -->
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>

        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->

        <script type="text/javascript">

                $(document).ready(function () {
                    window.history.pushState(null, "", window.location.href);
                    window.onpopstate = function () {
                        window.history.pushState(null, "", window.location.href);
                    };
                });
        </script>

    </body>

</html>
