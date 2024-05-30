<%@page import="rc.so.entity.Office"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.util.Etichette"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<%
    String link_value = Engine.verifyUser(request);
    if (link_value != null) {
        Utility.redirect(request, response, link_value);
    }
%>
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8" />
        <title>Mac2.0 - Homepage</title>
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
        <script type="text/javascript" src="assets/soop/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>





        <script type="text/javascript">

            function checkbranch() {
                var cbdate = document.getElementById('cbdate').value.trim();
                document.getElementById('modt').innerHTML = 'CHECK BRANCH - Date ' + cbdate;
                $.ajax({
                    type: "POST",
                    url: "Query?type=checkbranch&q=" + cbdate,
                    success: function (data) {
                        if (data !== "NESS") {
                            var arrayJson = JSON.parse(data);
                            if (arrayJson.length > 0) {
                                var msg = "<div class='row'><div class='col-md-12'><center><span class='help-block'><b>Do not result open/close safe in the following branches:</b></span></center></div></div><p class='ab'></p><div class='row'><div class='col-md-12'>";
                                for (var i = 0; i < arrayJson.length; i++) {
                                    msg = msg + "<div class='col-md-6'><center>" + arrayJson[i] + "</center></div>";
                                }
                                msg = msg + "</div></div>";
                                document.getElementById('mbll').innerHTML = msg;
                            } else {
                                var msg = "All branches have at least one open/close safe";
                                document.getElementById('mbll').innerHTML = msg;
                            }
                        } else {
                            var msg = "All branches have at least one open/close safe";
                            document.getElementById('mbll').innerHTML = msg;
                        }
                    }
                });
                document.getElementById('largelock_but').click();
            }


            function subform(but) {
                $("#" + but.id).html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");
                but.disabled = true;
            }
            function printZpl() {
                var printWindow = window.open();
                printWindow.document.open('text/plain');
                printWindow.document.write("zpl -- - - - - -");
                printWindow.document.close();
                printWindow.focus();
                printWindow.print();
                printWindow.close();
            }

        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return online();">
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu1.jsp"%>
            <!-- END MENU -->

            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                int[] countNews = Engine.countNews(session);
                String search = Utility.generaId();
                String dateyest = new DateTime().minusDays(1).toString("dd/MM/yyyy");
                String datenow = new DateTime().toString("dd/MM/yyyy");
                Office of = Engine.get_national_office();
                ArrayList<String[]> indice_rischio_Tra = Engine.indice_rischio();
                ArrayList<String[]> indice_rischio_Sog = Engine.indice_rischio_new();
                String tipo = (String) session.getAttribute("us_tip");
                if (tipo == null) {
                    tipo = "";
                }
                boolean central = Engine.isCentral();
                String fi = Engine.getFil()[0];
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
                    <%@ include file="menu/shortcuts_online.jsp"%>
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Homepage</h3> 
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;" /> 
                        </div>
                    </div>
                    <div class="modal fade" id="largelock_butdiv" role="dialog">
                        <button type="button" data-toggle="modal"data-target="#largelock" id="largelock_but"></button>
                    </div>
                    <div class="modal fade" id="largelock" role="dialog">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title" id="modt"> </h4>
                                </div>
                                <div class="modal-body" id="mbll">

                                </div>
                                <div class="modal-footer">
                                    <a type="button" class="btn btn-outline red" data-dismiss="modal" onclick="return dismiss('largelock');">
                                        <i class="fa fa-window-close"></i> Close</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal fade" id="largelock_butdiv1" role="dialog">
                        <button type="button" data-toggle="modal" data-target="#unlock_mode" id="unlockbtn"></button>
                    </div>
                    <form action="Operazioni?type=unlock_it_et&so=index.jsp" method="post">
                        <div class="modal fade" id="unlock_mode" role="dialog">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                        <h4 class="modal-title uppercase">Unlock pending operation</h4>
                                    </div>
                                    <div class="modal-body">
                                        Check that no one is doing any other operation before proceeding. Are you sure want to unlock it the operation?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-outline green-jungle">
                                            <i class="fa fa-check"></i> Confirm</button>
                                        <a class="btn btn-outline red" data-dismiss="modal" onclick="return dismiss('largelock');">
                                            <i class="fa fa-window-close"></i> Close</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                    <div class="row">
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 bordered">
                                <h4 class="widget-thumb-heading">Actions</h4>
                                <%boolean operpending = Engine.isBlockedOperationUser(session);
                                    if (operpending) {%>
                                <div class="widget-thumb-wrap">
                                    <a data-toggle="modal" data-target="#unlock_mode">
                                        <i class="widget-thumb-icon bg-green-sharp fa fa-unlock"></i>
                                        <div class="widget-thumb-body">
                                            <span class="widget-thumb-subtitle">Unlock pending operation</span>
                                        </div>
                                    </a>
                                </div>
                                <%} else {%>
                                <div class="widget-thumb-wrap">
                                    <i class="widget-thumb-icon bg-grey fa fa-unlock"></i>
                                    <div class="widget-thumb-body">
                                        <span class="widget-thumb-subtitle">Unlock pending operation</span>
                                    </div>
                                </div>
                                <%}%>

                            </div>
                        </div>

                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <!-- BEGIN WIDGET THUMB -->
                            <div class="widget-thumb widget-bg-color-white text-uppercase margin-bottom-20 bordered">
                                <h4 class="widget-thumb-heading">Newsletter</h4>
                                <a class="widget-thumb-wrap" style="text-decoration: none;" href="nl_view.jsp?status=U&search=<%=search%>">
                                    <i class="widget-thumb-icon bg-green-jungle fa fa-exclamation"></i>
                                    <div class="widget-thumb-body">
                                        <span class="widget-thumb-subtitle">New</span>
                                        <span class="widget-thumb-body-stat" data-counter="counterup" data-value="<%=countNews[0]%>">0</span>
                                    </div>
                                </a>
                                <a class="widget-thumb-wrap" style="text-decoration: none;" href="nl_view.jsp?status=...&search=<%=search%>">
                                    <i class="widget-thumb-icon bg-blue-dark fa fa-file"></i>
                                    <div class="widget-thumb-body ">
                                        <span class="widget-thumb-subtitle">Total</span>
                                        <span class="widget-thumb-body-stat" data-counter="counterup" data-value="<%=countNews[1]%>">0</span>
                                    </div>
                                </a>
                            </div>
                            <!-- END WIDGET THUMB -->
                        </div>

                        <!-- END WIDGET THUMB -->
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                            <%if (tipo.equals("2") || tipo.equals("3")) {%>

                            <div class="widget-thumb widget-bg-color-white  margin-bottom-20 bordered">
                                <h4 class="widget-thumb-heading text-uppercase">Check Branch</h4>
                                <div class="form-group">
                                    <label>Date</label>
                                    <div class="input-icon right">
                                        <i class="fa fa-search" onclick="return checkbranch();"></i>
                                        <input type="text" class="form-control date-picker" id="cbdate" name="cbdate" value="<%=dateyest%>"/>
                                    </div>
                                </div>
                            </div>

                            <%}%>
                        </div>            
                    </div>
                    <div class="row">
                        <%if (central && (tipo.equals("2") || tipo.equals("3"))) {%>
                        <div class="col-md-6 col-sm-6">
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <span class="caption-subject font-grey-cascade bold uppercase">Risk Assessment Index - Number of Transactions (<%=indice_rischio_Tra.size()%>)</span>
                                    </div>
                                    <div class="actions">
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    
                                    
                                    
                                    <div class="scroller" style="height: 250px;" data-always-visible="1" data-rail-visible="0">
                                        <ul class="feeds">
                                            <%if (indice_rischio_Tra.isEmpty()) {%>
                                            <li>
                                                <div class="col1">
                                                    <div class="cont">
                                                        <div class="cont-col1">
                                                            <div class="label label-sm label-success">
                                                                <i class="fa fa-check"></i>
                                                            </div>
                                                        </div>
                                                        <div class="cont-col2">
                                                            <div class="desc"> No customers reported. </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col2">
                                                    <%=datenow%> 
                                                </div>
                                            </li>
                                            <%} else {
                                                for (int i = 0; i < indice_rischio_Tra.size(); i++) {

                                            %>
                                            <li>
                                                <div class="col1">
                                                    <div class="cont">
                                                        <div class="cont-col1">
                                                            <div class="label label-sm label-warning">
                                                                <i class="fa fa-exclamation-triangle"></i>
                                                            </div>
                                                        </div>
                                                        <div class="cont-col2">
                                                            <div class="desc"> <%=indice_rischio_Tra.get(i)[0]%></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col2">
                                                    <%=indice_rischio_Tra.get(i)[1]%> 
                                                </div>
                                            </li>
                                            <%}
                                                }%>
                                        </ul>
                                    </div>
                                    <span class="help-block">Last <%=of.getRisk_days()%> days - Limit: <%=of.getRisk_ntr()%> transactions</span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 col-sm-6">
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <span class="caption-subject font-grey-cascade bold uppercase">Risk Assessment Index - Threshold (<%=indice_rischio_Sog.size()%>)</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">

                                    <div class="scroller" style="height: 250px;" data-always-visible="1" data-rail-visible="0">
                                        <ul class="feeds">
                                            <%if (indice_rischio_Sog.isEmpty()) {%>
                                            <li>
                                                <div class="col1">
                                                    <div class="cont">
                                                        <div class="cont-col1">
                                                            <div class="label label-sm label-success">
                                                                <i class="fa fa-check"></i>
                                                            </div>
                                                        </div>
                                                        <div class="cont-col2">
                                                            <div class="desc"> No customers reported. </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col2">
                                                    <%=datenow%> 
                                                </div>
                                            </li>
                                            <%} else {
                                                for (int i = 0; i < indice_rischio_Sog.size(); i++) {
                                            %>
                                            <li>
                                                <div class="col1">
                                                    <div class="cont">
                                                        <div class="cont-col1">
                                                            <div class="label label-sm label-warning">
                                                                <i class="fa fa-exclamation-triangle"></i>
                                                            </div>
                                                        </div>
                                                        <div class="cont-col2">
                                                            <div class="desc"> <%=indice_rischio_Sog.get(i)[0]%></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col2">
                                                    <%=indice_rischio_Sog.get(i)[1]%> 
                                                </div>
                                            </li>
                                            <%}
                                                }%>
                                        </ul>
                                    </div>
                                    <span class="help-block">Last <%=of.getRisk_days()%> days - Limit: <%=Utility.formatMysqltoDisplay(of.getRisk_soglia())%> total</span>
                                </div>
                            </div>
                        </div>
                        <%}%>
                        <!-- END PAGE TITLE-->
                        <!-- END PAGE HEADER-->
                        <%if (!central) {
                                String alertcopfx = Engine.alert_sogliaCOPFX(fi);

                                if (alertcopfx != null) {%>
                        <div class="col-md-6 col-sm-6">
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <span class="caption-subject font-grey-cascade bold uppercase">ALERT THRESHOLD COP+FX</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">

                                    <div class="scroller" style="height: 250px;" data-always-visible="1" data-rail-visible="0">
                                        <ul class="feeds">
                                            <li>
                                                <div class="col1">
                                                    <div class="cont">
                                                        <div class="cont-col1">
                                                            <div class="label label-sm label-danger">
                                                                <i class="fa fa-exclamation-triangle"></i>
                                                            </div>
                                                        </div>
                                                        <div class="cont-col2">
                                                            <div class="desc"> <%=alertcopfx%> </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <span class="help-block">Last 5 days</span>
                                </div>
                            </div>
                        </div>
                        <%}%>
                        <%}%>
                    </div>
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
                <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
                <script src="assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
                <!-- END CORE PLUGINS -->
                <!-- BEGIN PAGE LEVEL PLUGINS -->
                <!-- END PAGE LEVEL PLUGINS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->
                <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
                <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
                <!-- END THEME GLOBAL SCRIPTS -->
                <!-- BEGIN PAGE LEVEL SCRIPTS -->
                <!-- END PAGE LEVEL SCRIPTS -->
                <!-- BEGIN THEME LAYOUT SCRIPTS -->
                <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
                <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
                <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
                <!-- END CORE PLUGINS -->
                <!-- BEGIN PAGE LEVEL PLUGINS -->
                <script src="assets/global/plugins/counterup/jquery.waypoints.min.js" type="text/javascript"></script>
                <script src="assets/global/plugins/counterup/jquery.counterup.min.js" type="text/javascript"></script>
                <!-- END PAGE LEVEL PLUGINS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->


                <!-- END THEME LAYOUT SCRIPTS -->


                <script type="text/javascript">

                                            $(document).ready(function () {
                                                window.history.pushState(null, "", window.location.href);
                                                window.onpopstate = function () {
                                                    window.history.pushState(null, "", window.location.href);
                                                };
                                            });
                </script>

                <%if (Constant.tr_1302) {%>
                <script src="assets/soop/evo/datepicker_new.js" type="text/javascript"></script>
                <%}%>

                </body>

                </html>
