<%@page import="rc.so.entity.Figures"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="rc.so.util.Etichette"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Mac2.0 - Cashier Conf.</title>
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



        <script type="text/javascript">
            function loadpage() {
                inputvirgola();
                online();
            }
        </script>
    </head>
    <!-- END HEAD -->
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_ma6.jsp"%>
            <!-- END MENU -->
            <%                
                String decimal = Constant.decimal;
                String thousand = Constant.thousand;
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                ArrayList<String[]> cashier_perf = Engine.list_fasce_cashier_perf();

                ArrayList<Figures> listfig = Engine.list_all_figures();
                boolean central = Engine.isCentral();
                String tipo = (String) request.getSession().getAttribute("us_tip");
                if (tipo == null) {
                    tipo = "";
                }
                //tipo="1";

                String dis = "disabled";
                String ico = "icon-eye";
                String tit = "View";

                if (central && tipo.equals("3")) {
                    dis = "";
                    ico = "icon-wrench";
                    tit = "Edit";
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
                            <h3 class="page-title">Maintenance <small><b>Cashier Conf.</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>

                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT TILL -->
                    <%if (true) {%>
                    <div class="row">
                        <form action="Edit?type=edit_r1_buy" method="post">
                            <div class="col-md-6">
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="<%=ico%> font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase"><%=tit%> Range Buy</span>
                                        </div>
                                        <div class="tools"> 
                                            <%if (central && tipo.equals("3")) {%>
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-group">
                                            <%                                            int x = 0;
                                                for (int i = 0; i < cashier_perf.size(); i++) {

                                                    String[] v = cashier_perf.get(i);
                                                    if (v[4].equals("B")) {
                                                        x++;
                                            %>
                                            <div class="row">
                                                <input type="hidden" name="idr_<%=v[0]%>" value="<%=v[0]%>" />
                                                <label class="col-md-2 control-label font-blue">#<%=x%></label>
                                                <div class="col-md-5">
                                                    <input  type="text" class="form-control inputright" 
                                                            name="r1_<%=v[0]%>" onkeypress="return keysub(this, event);" value="<%=v[1]%>"
                                                            onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');" <%=dis%> /> 
                                                </div>
                                                <div class="col-md-5">
                                                    <input type="text" class="form-control inputright" 
                                                           name="r2_<%=v[0]%>" onkeypress="return keysub(this, event);" value="<%=v[2]%>" 
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');" <%=dis%> /> 
                                                </div>
                                            </div>
                                            <%}
                                                }%>



                                        </div>
                                        <hr>
                                        <div class="form-group">
                                            <div class="caption">
                                                <i class="icon-info font-blue"></i>
                                                <span class="caption-subject font-blue bold uppercase">Figures Buy Commission</span>
                                            </div>
                                            <%for (int i = 0; i < listfig.size(); i++) {
                                                    Figures f1 = listfig.get(i);

                                                    String dis1 = Utility.formatMysqltoDisplay(f1.getCommissione_acquisto());
                                                    if (f1.getCommissione_acquisto().contains(",")) {
                                                        dis1 = f1.getCommissione_acquisto();
                                                    }

                                            %>
                                            <div class="row">
                                                <span class="col-md-6"><%=f1.getSupporto() + " " + f1.getDe_supporto()%></span>
                                                <span class="font-blue col-md-6"><b><%=dis1%></b></span>
                                            </div>
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <form action="Edit?type=edit_r1_sell" method="post">           
                            <div class="col-md-6">
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="<%=ico%> font-green-sharp"></i>
                                            <span class="caption-subject font-green-sharp bold uppercase"><%=tit%> Range Sell</span>
                                        </div>
                                        <div class="tools"> 
                                            <%if (central && tipo.equals("3")) {%>
                                            <button type="submit" class="btn btn-outline green-sharp"><i class="fa fa-save"></i> Save</button>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <%
                                            int i = 0;
                                            for (int j = 0; j < cashier_perf.size(); j++) {

                                                String[] v = cashier_perf.get(j);
                                                if (v[4].equals("S")) {
                                                    i++;
                                        %>
                                        <div class="row">
                                            <label class="col-md-2 control-label font-green-sharp">#<%=i%></label>
                                            <input type="hidden" name="idr_<%=v[0]%>" value="<%=v[0]%>" />
                                            <div class="col-md-5">
                                                <input type="text" class="form-control inputright" 
                                                       name="r1_<%=v[0]%>" 
                                                       onkeypress="return keysub(this, event);" 
                                                       value="<%=v[1]%>" <%=dis%> /> 
                                            </div>
                                            <div class="col-md-5">
                                                <input type="text" class="form-control inputright" 
                                                       name="r2_<%=v[0]%>" 
                                                       onkeypress="return keysub(this, event);" 
                                                       value="<%=v[2]%>" <%=dis%> /> 
                                            </div>
                                        </div>
                                        <%}
                                            }%>
                                        <hr>
                                        <div class="form-group">
                                            <div class="caption">
                                                <i class="icon-info font-blue"></i>
                                                <span class="caption-subject font-green-sharp bold uppercase">Figures Sell Commission</span>
                                            </div>
                                            <%for (int y = 0; y < listfig.size(); y++) {
                                                    Figures f1 = listfig.get(y);

                                                    String dis1 = Utility.formatMysqltoDisplay(f1.getCommissione_vendita());
                                                    if (f1.getCommissione_vendita().contains(",")) {
                                                        dis1 = f1.getCommissione_vendita();
                                                    }

                                            %>
                                            <div class="row">
                                                <span class="col-md-6"><%=f1.getSupporto() + " " + f1.getDe_supporto()%></span>
                                                <span class="font-green-sharp col-md-6"><b><%=dis1%></b></span>
                                            </div>
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <%
                        String esito = request.getParameter("esito");
                        if (esito == null) {
                            esito = "";
                        }
                        String classal = "alert-info";
                        String classfa = "fa-exclamation-triangle";
                        String msg = "Warning";
                        String msg1 = "No operation";
                        if (esito.equals("ok")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        } else if (esito.equals("ko")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed.";
                        }
                        if (!esito.equals("")) {
                    %>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
                    <%}%>
                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>This feature is not available in your country.</strong>
                            </div>
                        </div>
                    </div>
                    <%}%>

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
                <!--[if lt IE 9]>
        <script src="../assets/global/plugins/respond.min.js"></script>
        <script src="../assets/global/plugins/excanvas.min.js"></script> 
        <![endif]-->
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
                <!-- END PAGE LEVEL PLUGINS -->
                <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>


                <!-- END THEME GLOBAL SCRIPTS -->
                <!-- BEGIN PAGE LEVEL SCRIPTS -->
                <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>
                <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
                <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
                <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
                <script src="assets/soop/js/form-input-mask.min.js" type="text/javascript"></script>




                <!-- END PAGE LEVEL SCRIPTS -->
                <!-- BEGIN THEME LAYOUT SCRIPTS -->
                <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
                <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
                <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>

                <!-- BEGIN PAGE LEVEL PLUGINS -->
                <!-- END PAGE LEVEL PLUGINS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->
                <!-- END THEME LAYOUT SCRIPTS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->
                <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>

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
