<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.List_ma"%>
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

    boolean onl = Engine.isCentral();
    //onl = false;
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
        <title>Mac2.0 - Open/Close</title>
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
        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/soop/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />




        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>

        <%
            String username = Utility.checkAttribute(session,"us_cod");
            //username="8811";
            ArrayList<Till> array_till_user = Engine.list_till_status("O", username);

        %>

        <script type="text/javascript">
            function submitforms(cod, open) {
                document.getElementById("tillselected").value = cod;
                if (open === "true") {
                    document.getElementById('f1R').action = "ti_close.jsp";
                } else {
                    document.getElementById('f1R').action = "ti_open.jsp";
                }
                document.getElementById('f1R').submit();
            }

            function loadpage() {
                online();
                inputvirgola();
                //chengelabelsubmit(document.getElementById('tillselected'));
            }

            function checkcentral(form, id, oc, user) {

                var ce = "<%=onl%>";
                if (ce === "true") {
                    document.getElementById('errorlargetext').innerHTML = "000 - Head Office can not perform transaction operation.";
                    document.getElementById('errorlarge_butt').click();
                    return false;
                } else {
                    var cassaapaerta = false;
                    var idaperto = "";
            <%for (int x = 0; x < array_till_user.size(); x++) {%>
                    var cons = '<%=array_till_user.get(x).getCod()%>';
                    if (cons !== "000") {
                        cassaapaerta = true;
                        idaperto = cons;
                    }
            <%}%>


                    if (cassaapaerta) {
                        if (user === "<%=username%>") {
                            if (idaperto !== id) {
                                if (id === "000") {
                                    document.getElementById(form).submit();
                                } else {
                                    document.getElementById('errorlargetext').innerHTML = "Operation not permitted. You have one till already opened! Please close that till and try again!.";
                                    document.getElementById('errorlarge_butt').click();
                                    return false;
                                }
                            } else {
                                document.getElementById(form).submit();
                            }


                        } else {
                            if (oc === "OPEN") {
                                document.getElementById('errorlargetext').innerHTML = "Only the operator <b>" + user + "</b> can do this operation.";
                                document.getElementById('errorlarge_butt').click();
                                return false;
                            } else {

                                if (id === "000") {
                                    document.getElementById(form).submit();
                                } else {
                                    document.getElementById('errorlargetext').innerHTML = "Operation not permitted. You have one till already opened! Please close that till and try again!.";
                                    document.getElementById('errorlarge_butt').click();
                                    return false;
                                }

                            }
                        }
                    } else {
                        if (oc === "OPEN") {
                            if (user === "<%=username%>") {
                                document.getElementById(form).submit();
                            } else {


                                document.getElementById('errorlargetext').innerHTML = "Only the operator <b>" + user + "</b> can do this operation.";
                                document.getElementById('errorlarge_butt').click();
                                return false;
                            }
                        } else {
                            document.getElementById(form).submit();
                        }
                    }
                }

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
            <%@ include file="menu/menu_tr1.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
            %>

            <div class="modal fade" id="errorlarge_hid" tabindex="-1" role="dialog" aria-hidden="true">
                <button type="button" class="btn btn-info btn-lg" id="errorlarge_butt" data-toggle="modal" data-target="#errorlarge">Open Modal</button>
            </div>
            <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                            <h4 class="modal-title font-red uppercase"><b>Error message</b></h4>
                        </div>
                        <div class="modal-body" id="errorlargetext">000 - Head Office can not perform transaction operation.</div>
                        <div class="modal-footer">
                            <button type="button" class="btn dark btn-outline" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>

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
                            <h3 class="page-title">Open/close</h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>

                    <%if (onl) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>000 - Head Office can not perform transaction operation.</strong>
                            </div>
                        </div>
                    </div>
                    <%} else {
                        String filiale = Engine.getFil()[0];
                        ArrayList<Till> list_ALL_till_enabled = Engine.list_ALL_till_enabled();
                        ArrayList<Till> listTill = Engine.listTill(filiale);
                        ArrayList<Till> array_till = Engine.list_till_status(null, null);
                        String[] interval = Utility.getlastDayInterval(30);
                    %>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT TILL -->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue-dark">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-history"></i>
                                        <span class="caption-subject">Shortcuts</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">

                                    <div class="row">
                                        <div class="col-md-12">


                                            <%
                                                for (int j = 0; j < list_ALL_till_enabled.size(); j++) {

                                                    Till t0 = list_ALL_till_enabled.get(j);
                                                    Till t1 = null;
                                                    String action = "";
                                                    for (int i = 0; i < array_till.size(); i++) {
                                                        if (t0.getCod().equals(array_till.get(i).getCod())) {
                                                            t1 = array_till.get(i);
                                                            if (t1.getTy_opcl().equals("OPEN")) {
                                                                action = "ti_close.jsp";
                                                            } else {
                                                                action = "ti_open.jsp";
                                                            }
                                                            break;
                                                        }
                                                    }
                                                    if (t1 == null) {
                                                        t1 = t0;
                                                        action = "ti_open.jsp";
                                                    }
                                            %>
                                            <a type="button" class="btn btn-outline <%=t1.getClassbutton()%> popovers"
                                               data-trigger="hover" data-container="body" data-placement="bottom"
                                               data-content="<%=t1.getLabeltools()%>" 
                                               data-original-title="<%=t1.getName()%>" onclick="return checkcentral('f1R_<%=t1.getCod()%>', '<%=t1.getCod()%>', '<%=t1.getTy_opcl()%>', '<%=t1.getUs_opcl()%>');">
                                                <i class="fa <%=t1.getClassicon()%>"></i> <%=t1.getName()%> | <%=t1.getLabelbutton()%>
                                            </a>
                                            <form id="f1R_<%=t1.getCod()%>" method="post" action="<%=action%>" style="display:none;">
                                                <input type="hidden" id="tillselected" name="tillselected" value="<%=t1.getCod()%>"/>
                                                <input type="hidden" id="opencloseid" name="opencloseid" value="<%=t1.getId_opcl()%>"/>
                                            </form>
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Safe/Till List</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-bordered table-hover" id="sample_0" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow"style="width: 70px;">Code</th>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow"style="width: 70px;">Type</th>
                                                                    <th class="tabnow">Open/Close USER</th>
                                                                    <th class="tabnow">Open/Close CODE</th>
                                                                    <th class="tabnow"style="width: 100px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for (int i = 0; i < array_till.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=array_till.get(i).getCod()%></td>
                                                                    <td><%=array_till.get(i).formatDescTill(listTill, array_till.get(i).getCod())%></td>
                                                                    <td><%=array_till.get(i).getTy_opcl()%></td>
                                                                    <td><%=array_till.get(i).getUs_opcl()%></td>
                                                                    <td><%=array_till.get(i).getCod_opcl()%></td>
                                                                    <td>
                                                                        <div class="btn-group">

                                                                            <a onclick="document.getElementById('och_<%=array_till.get(i).getCod()%>').submit();"
                                                                               class='btn btn-sm white popovers'
                                                                               container='body' data-trigger='hover' data-container='body' data-placement='top' 
                                                                               data-content='Open Close History'><i class='fa fa-history'></i></a>

                                                                            <form target="_blank" id="och_<%=array_till.get(i).getCod()%>" action="oc_list.jsp" method="post">
                                                                                <input type="hidden" name="search" value="r1"/>
                                                                                <input type="hidden" name="branch" value="<%=array_till.get(i).getFil_opcl()%>"/>
                                                                                <input type="hidden" name="till" value="<%=array_till.get(i).getCod()%>"/>
                                                                                <input type="hidden" name="d1" value="<%=interval[0]%>"/>
                                                                                <input type="hidden" name="d2" value="<%=interval[1]%>"/>
                                                                            </form>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <%}%>
                                                            </tbody>

                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
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
                        if (esito.startsWith("false")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Please try again.";
                        } else if (esito.equals("OK")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        }

                        if (!esito.equals("")) {
                    %>
                    <hr>
                    <div class="row">
                        <div class="col-md-12 ">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
                    <%}%>                                        
                    <%}%>                                        
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

            <!-- END PAGE LEVEL PLUGINS -->
            <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
            <!-- END CORE PLUGINS -->
            <!-- BEGIN PAGE LEVEL PLUGINS -->
            <!-- END PAGE LEVEL PLUGINS -->
            <!-- BEGIN THEME GLOBAL SCRIPTS -->
            <!-- END THEME LAYOUT SCRIPTS -->
            <!-- BEGIN THEME GLOBAL SCRIPTS -->
            <script type="text/javascript">
                                                                                jQuery(document).ready(function () {
                                                                                    var dt1 = function () {
                                                                                        var f = $("#sample_0");
                                                                                        f.dataTable({
                                                                                            language: {aria: {},
                                                                                                sProcessing: "Process...",
                                                                                                emptyTable: "No results found.",
                                                                                                info: "Show _START_ to _END_ of _TOTAL_ results",
                                                                                                infoEmpty: "No results found.",
                                                                                                infoFiltered: "(filtered to _MAX_ total)",
                                                                                                lengthMenu: "Show _MENU_",
                                                                                                search: "Search:",
                                                                                                zeroRecords: "No results found.",
                                                                                                paginate: {previous: "Prev", next: "Next", last: "Last", first: "First"}},
                                                                                            columnDefs: [
                                                                                                {orderable: 1, targets: [0]},
                                                                                                {orderable: 1, targets: [1]},
                                                                                                {orderable: 1, targets: [2]},
                                                                                                {orderable: 1, targets: [3]},
                                                                                                {orderable: 1, targets: [4]},
                                                                                                {orderable: !1, targets: [5]}
                                                                                            ],
                                                                                            buttons: [
                                                                                                {extend: "excel", className: "btn white btn-outline", text: "<i class='fa fa-file-excel-o'></i> Excel"},
                                                                                                {extend: "pdf", className: "btn white btn-outline", text: "<i class='fa fa-file-pdf-o'></i> Pdf"},
                                                                                                {extend: "colvis", className: "btn white btn-outline", text: "<i class='fa fa-list-alt'></i> Columns"},
                                                                                                {text: "<i class='fa fa fa-refresh'></i>",
                                                                                                    className: "btn white btn-outline",
                                                                                                    action: function (e, dt, node, config) {
                                                                                                        location.reload();
                                                                                                    }
                                                                                                }]
                                                                                            ,
                                                                                            colReorder: {reorderCallback: function () {
                                                                                                    
                                                                                                }},
                                                                                            lengthMenu: [
                                                                                                [25, 50, 100, -1],
                                                                                                [25, 50, 100, "All"]
                                                                                            ],
                                                                                            pageLength: 25,
                                                                                            order: [],
                                                                                            dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                                                                                        });
                                                                                    };
                                                                                    jQuery().dataTable && dt1();
                                                                                });
            </script>
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
