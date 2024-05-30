
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="rc.so.entity.NC_transaction"%>
<%@page import="rc.so.util.Utility"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>Mac2.0 - No Change Tr.</title>
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
        <link href="assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />

        <link href="assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />

        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <link href="assets/layouts/layout/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/layouts/layout/css/themes/darkblue.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="assets/layouts/layout/css/custom.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 


        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>




    </head>
    <!-- END HEAD -->
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_tr7.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);

                String cod = Utility.sanitize(request.getParameter("cod"));
                if (cod == null) {
                    cod = "";
                }

                NC_transaction nc = Engine.get_NC_transaction(cod);

                String esito = Utility.sanitize(request.getParameter("esito"));
                if (esito == null) {
                    esito = "";
                }
                String classal = "alert-info";
                String classfa = "fa-exclamation-triangle";
                String msg = "Warning";
                String msg1 = "No operation";
                if (esito.startsWith("false")) {
                    classal = "alert-danger";
                    classfa = "fa-exclamation-triangle";
                    msg = "Error";
                    msg1 = "The operation could not be completed. Please try again.";
                } else if (esito.equals("1A")) {
                    classal = "alert-danger";
                    classfa = "fa-exclamation-triangle";
                    msg = "Error";
                    msg1 = "The operation could not be completed. Total value is zero or null. Check it and try again.";
                } else if (esito.equals("2A")) {
                    classal = "alert-danger";
                    classfa = "fa-exclamation-triangle";
                    msg = "Error";
                    msg1 = "The operation could not be completed. There was an error in entering data value. Try again.";
                } else if (esito.equals("1Q")) {
                    classal = "alert-danger";
                    classfa = "fa-exclamation-triangle";
                    msg = "Error";
                    msg1 = "The operation could not be completed. Actual quantity/total selected is less then the entered value. Check it and try again.";
                } else if (esito.equals("OK")) {
                    classal = "alert-success";
                    classfa = "fa-check";
                    msg = "Success";
                    msg1 = "Operation completed successfully.";
                }
            %>

            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <!--    VUOTO RAF  -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <%@ include file="menu/shortcuts.jsp"%>
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction <small><b>No Change</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                        <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                    </div>

                    <%if (nc != null) { %>
                    <%
                        NC_causal nccc = Engine.getNC_causal(nc.getCausale_nc());

                        if (nccc != null) {
                            if (nccc.getFg_scontrino().equals("1")) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <%if (nccc.getFg_tipo_transazione_nc().equals("3") && Constant.is_IT) {%>
                            <%if (Constant.signoffline) {%>
                            <form action="Pdf?type=_macnctaxf" method="post" target="_blank">
                                <input type="hidden" name="cod" value="<%=Utility.sanitize(cod)%>"/>
                                <button type="submit" class="btn green-jungle"><i class="fa fa-edit"></i> Sign receipt</button>
                            </form>
                            <%} else {%>
                            <form action="Print?type=sign_nochange_taxfree" method="post" target="_blank">

                                <input type="hidden" name="cod" value="<%=Utility.sanitize(cod)%>"/>
                                <button type="submit" class="btn green-jungle"><i class="fa fa-edit"></i> Sign receipt</button>
                            </form>
                            <br/>
                            <form id="ncform" target="_blank" method="post" action="Operazioni?type=verify_sign_nc&codtr=<%=cod%>&coddoc=_macnctaxf">
                                <button type="submit" class="btn blue"><i class="fa fa-sign-out"></i> Verify Sign</button>
                            </form>
                            <%}%>
                            <%} else {%>
                            <%if (Constant.tr_1310) {
                                    String base64 = Engine.view_reprint_nctr(cod);%>
                            <div class="col-md-12">
                                <iframe src="data:application/pdf;base64,<%=Utility.sanitize(base64)%>" 
                                        style="height:550px;width:100%;">
                                </iframe>
                            </div>
                            <%} else {%>
                            <form action="Download?type=view_reprint_nctr" method="post" target="_blank">
                                <input type="hidden" name="cod" value="<%=Utility.sanitize(cod)%>"/>
                                <button type="submit" class="btn red"><i class="fa fa-file-pdf-o"></i> Print receipt</button>
                            </form> 
                            <%}%> 
                            <%}%> 
                        </div>
                    </div>
                    <hr>
                    <%}
                        }%>
                    <div class="row">
                        <div class="col-md-12 ">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <%
                        NC_category nccc0 = Engine.getNC_category(nc.getGruppo_nc());
                        if (nccc0 != null) {
                            //if (nccc0.getFg_registratore().equals("1")) {
                            if (false) {%>
                    <div class="row">

                        <%
                            String espos = (String) session.getAttribute("pos1");
                            if (espos == null) {
                                espos = "OK";
                            }
                        %>
                        <div class="col-md-12">
                            <a href="#" onclick="return false;" class="btn blue">
                                <b>Result Print Receipt:</b> <%=Utility.getStringUTF8(espos)%></a>
                            <a href="Scan?type=sendpos&cod=<%=nc.getCod()%>&esito=<%=esito%>" class="btn red-haze">
                                <i class="fa fa-repeat"></i> Reprint Fiscal Receipt</a>
                        </div>
                    </div>
                    <hr>
                    <%}
                        }%>
                    <%}%>
                    <%
                        if (nc.getDocrico() != null) {
                            if (!nc.getDocrico().equals("-")) {
                                if (!nc.getDocrico().contains(";")) {
                    %>
                    <div class="row">
                        <div class="col-md-12">
                            <a class="btn btn-outline red" target="_blank" href="Fileview?type=viewncfile&cod=<%=nc.getCod()%>">
                                <i class="fa fa-file-pdf-o"></i> Download Receipt Paymat
                            </a>
                        </div>
                    </div>
                    <hr>
                    <%}
                            }
                        }%>
                    <div class="row">
                        <div class="col-md-12">
                            <a href="nc_transaction.jsp" class="btn dark"><i class="fa fa-plus-circle"></i> New No Change Transaction</a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- END CONTENT -->
            <!-- BEGIN QUICK SIDEBAR -->
            <!-- END QUICK SIDEBAR -->
        </div>
        <!-- END CONTAINER -->
        <!-- BEGIN FOOTER -->
        <div class="page-footer">
            <div class="page-footer-inner"> <%=et_index.getFooter()%></div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>
        <!-- END FOOTER -->
        <!-- BEGIN CORE PLUGINS -->
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>

        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="assets/soop/js/select2.full.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>

        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="assets/soop/js/form-input-mask.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->

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
