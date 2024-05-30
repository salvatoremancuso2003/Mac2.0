<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Etichette"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.util.Utility"%>

<%@page import="rc.so.entity.Branch"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE HTML>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>Mac2.0 - Login</title>
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

            String fil[] = Engine.getFil();
            boolean central = fil[0].equals("000");
            //ArrayList<Branch> array_branch = Engine.list_branch();
            ArrayList<Branch> array_branch = Engine.list_branch_enabled();
            String esito = Utility.sanitize(request.getParameter("esito"));
            if (esito == null) {
                esito = "";
            }%>


        function checkesito() {
            if (document.getElementById('branch') !== null) {
                $("#branch").select2({
                    placeholder: "Select a Branch",
                    allowclear: true
                });
                var o = $("<option/>", {value: "", text: ""});
                $('#branch').append(o);
        <%for (int j = 0; j < array_branch.size(); j++) {%>
                var o = $("<option/>", {value: "<%=array_branch.get(j).getCod()%>", text: "<%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>"});
                            $('#branch').append(o);
        <%}%>
                        }

                        var es = "<%=esito%>".trim();
                        if (es !== "") {
                            var ermsg = "Error: " + es;

                            if (es === "KO1") {
                                ermsg = "Login incorrect. Wrong credentials. Check Username/Password.";
                            } else if (es === "KO2") {
                                ermsg = "Login incorrect. Please insert Username/Password.";
                            } else if (es === "KO3") {
                                ermsg = "Login incorrect. Your credentials are inactive.";
                            } else if (es === "KO4") {
                                ermsg = "Login incorrect. You are not authorized to access this branch.";
                            } else if (es === "KO5") {
                                ermsg = "Login incorrect. Branch error. This computer can not connect to this branch.";
                            } else if (es === "OKR") {
                                document.getElementById('showres').click();
                                return false;
                            }


                            document.getElementById("errorlargetext").innerHTML = ermsg;
                            document.getElementById('showerror').click();
                        } else {
                            document.getElementById('username').focus();
                        }

                    }

                    function checklogin() {

                        if (document.getElementById('branch') !== null) {
                            var branch = document.getElementById('branch').value.trim();
                            if (branch === "") {
                                var ermsg = "Login incorrect. Please insert Branch.";
                                document.getElementById("errorlargetext").innerHTML = ermsg;
                                document.getElementById('showerror').click();
                                return false;
                            }
                            if (branch !== "<%=fil[0]%>") {
                                var ermsg = "Login incorrect. Branch error. This computer can not connect to this branch.";
                                document.getElementById("errorlargetext").innerHTML = ermsg;
                                document.getElementById('showerror').click();
                                return false;
                            }
                        }
                        var us = document.getElementById('username').value.trim();
                        var ps = document.getElementById('password').value.trim();
                        if (us === "" || ps === "") {
                            var ermsg = "Login incorrect. Please insert Username/Password.";
                            document.getElementById("errorlargetext").innerHTML = ermsg;
                            document.getElementById('showerror').click();
                            return false;
                        }
                    }

                    function closing() {
                        dismiss('errorlarge');
                        document.getElementById('username').focus();
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
        <div class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
            <button id="showres" data-toggle="modal" data-target="#reslarge" style="size: 1px;"/>
        </div>
        <div class="modal fade bs-modal-lg" id="reslarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title font-green-jungle uppercase"><b>RESET PASSWORD</b></h4>
                    </div>
                    <div class="modal-body" id="errorlargetext">Success! Your password has been reset, please login again.</div>
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
            <form role="form" class="login-form" action="Login?type=1" method="post" onsubmit="return checklogin();">
                <input type="hidden"
                       name="_csrf"
                       value="4bfd1575-3ad1-4d21-96c7-4ef2d9f86721"/>
                <h3 class="form-title font-grey-cascade">Login</h3>
                <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span> <%=et_index.getError_log1()%> </span>
                </div>
                <%if (central) {%>
                <div class="form-group">
                    <ul class="list-group">
                        <li class="list-group-item grey-cascade uppercase" style="text-align: center;color: #95A5A6;">
                            <%=fil[0]%> - <%=fil[1]%></li>
                    </ul>
                </div>
                <input type="hidden"name="branch" value="<%=fil[0]%>"/>
                <%} else {%>
                <div class="form-group">
                    <select class="form-control select2-allow-clear" name="branch" id="branch">
                    </select>
                </div>
                <%}%>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9"><%=et_index.getUsername()%></label>
                    <input class="form-control form-control-solid placeholder-no-fix" 
                           type="text" autocomplete="off" 
                           placeholder="<%=et_index.getUsername()%>" name="username" id="username" 
                           onkeypress="return checkkeysub('subform', event);"
                           /> 
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><%=et_index.getPassword()%></label>
                    <input class="form-control form-control-solid placeholder-no-fix" 
                           type="password" autocomplete="off" 
                           placeholder="<%=et_index.getPassword()%>" name="password" id="password" 
                           onkeypress="return checkkeysub('subform', event);" maxlength="10" /> 
                </div>

                <div class="form-actions">
                    <center><button id="subform" type="submit" class="btn btn-outline grey-cascade uppercase"><%=et_index.getLogin()%> <i class="icon-key"></i></button></center>
                </div>
                <div class="forget-password">
                    <center><a href="forgot.jsp" class="btn btn-sm btn-outline red">Forgot the password <i class="fa fa-question-circle-o"></i></a></center>
                </div>
            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM
            <form action="Scan?type=testereet" method="post"><button type="submit">TEST</button></form>
            -->

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


        <%if (Constant.tr_1309) {%>
        <script src="assets/soop/evo/close_modal.js" type="text/javascript"></script>
        <%}%>
    </body>

</html>
