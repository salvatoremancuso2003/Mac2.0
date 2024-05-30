<%-- 
    Document   : login
    Created on : 7-giu-2016, 15.46.28
    Author     : raffaele
--%>

<%@page import="rc.so.entity.Branch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.util.Etichette"%>
<!DOCTYPE HTML>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>Mac2.0 - Forgot Password</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <meta name="_csrf" content="4bfd1575-3ad1-4d21-96c7-4ef2d9f86721"/>
        <meta name="_csrf_header" content="X-CSRF-TOKEN"/>
        <meta http-equiv="Content-Security-Policy" content="default-src * 'unsafe-inline' 'unsafe-eval'">

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

    <script src="assets/soop/js/controlli.js" type="text/javascript"></script>




    <script type="text/javascript">
        <%
            String esito = request.getParameter("esito");
            if (esito == null) {
                esito = "";
            }%>


        function checklogin() {
            var us = document.getElementById('username').value.trim();
            if (us === "") {
                var ermsg = "Please insert Username.";
                document.getElementById("errorlargetext").innerHTML = ermsg;
                document.getElementById('showerror').click();
                return false;
            } else {
                $.ajax({
                    async: false,
                    type: "POST",
                    url: "Login?type=checkuser&us=" + us,
                    success: function (data) {
                        if (data === "OK") {
                            document.getElementById('showres').click();
                            return false;
                        } else {
                            document.getElementById("errorlargetext").innerHTML = data;
                            document.getElementById('showerror').click();
                            return false;
                        }
                    }
                });
                return false;
            }
        }

        function closing() {
            dismiss('errorlarge');
            document.getElementById('username').focus();
        }


    </script>


    <!-- END HEAD -->

    <body class="login">

        <div class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
            <button id="showerror" data-toggle="modal" data-target="#errorlarge" style="size: 1px;"/>
        </div>
        <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title font-red uppercase"><b>ERROR</b></h4>
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
        <div class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
            <button id="showres" data-toggle="modal" data-target="#reslarge" style="size: 1px;"/>
        </div>
        <div class="modal fade bs-modal-lg" id="reslarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title font-green-jungle uppercase"><b>RESET PASSWORD</b></h4>
                    </div>
                    <div class="modal-body" id="errorlargetext">Success! Your password has been reset, please check your email box.</div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" onclick="return dismiss('reslarge');" data-dismiss="modal">Close</button>
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
            <%if (Constant.test) {%>
            <img src="assets/soop/img/BT.png" alt="" /> 
            <%}%>
        </div>

        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content">
            <!-- BEGIN LOGIN FORM -->
            <form role="form" class="login-form" action="Login?type=resnew" method="post" onsubmit="return checklogin();">
                <input type="hidden"
                                           name="_csrf"
                                           value="4bfd1575-3ad1-4d21-96c7-4ef2d9f86721"/>
                <h3 class="form-title font-grey-cascade">Forgot your password?</h3>
                <div class="form-group">
                    <ul class="list-group">
                        <li class="list-group-item grey-cascade uppercase" style="text-align: center;color: #95A5A6;">
                            <b>Enter your username</b><p class="ap"></p> We'll email instructions on how to reset your password.</li>
                    </ul>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9"><%=et_index.getUsername()%></label>
                    <input class="form-control form-control-solid placeholder-no-fix" 
                           type="text" autocomplete="off" 
                           placeholder="<%=et_index.getUsername()%>" name="username" id="username" 
                           onkeypress="return checkkeysub('subform', event);"
                           /> 
                </div>
                <div class="form-actions">
                    <center><button id="subform" type="submit" class="btn btn-outline grey-cascade uppercase">
                            Send my password <i class="fa fa-send-o"></i></button></center>
                </div>
                <div class="forget-password">
                    <center><a href="login.jsp" class="btn btn-sm btn-outline red"> Home <i class="fa fa-home"></i></a></center>
                </div>
            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM -->

            <!-- END FORGOT PASSWORD FORM -->
            <!-- BEGIN REGISTRATION FORM -->
            <!-- END REGISTRATION FORM -->
        </div>
        <div class="copyright">
            <%=et_index.getFooter()%>
        </div>
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
        <script src="assets/soop/js/select2-tab-fix.min.js" type="text/javascript"></script>

        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>

        <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>

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
