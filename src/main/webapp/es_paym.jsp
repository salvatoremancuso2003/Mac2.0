<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Utility"%>
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
        <title>Mac2.0 - Paymat</title>
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
        <link href="assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->


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
        <script type="text/javascript" src="assets/soop/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>




        <script type="text/javascript">
            function confnumb(box) {
                document.getElementById('confrimbutt').action = document.getElementById(box.id.replace(/_v_/g, "")).value;
                document.getElementById("insertnumber").className = document.getElementById("insertnumber").className + " in";
                document.getElementById("insertnumber").style.display = "block";
                document.getElementById("errornumb").style.display = "none";
                document.getElementById('numb').value = "";
                document.getElementById('numb').focus();
                return false;
            }

            function submitonEnter(idform) {
                var keycode;
                if (window.event)
                    keycode = window.event.keyCode;
                else if (e)
                    keycode = e.which;
                if (keycode + "" === "13") {
                    checknumb(idform);
                }
            }

            function checknumb(formid) {
                document.getElementById("errornumb").style.display = "none";
                var numb = document.getElementById('numb').value.trim();
                if (isPhone(numb, 8, 15)) {
                    var actdef = document.getElementById(formid.id).action + "&numb=" + numb;
                    document.getElementById('confrimbutt').action = actdef;
                    document.getElementById('confrimbutt').submit();
                } else {
                    document.getElementById("errornumb").style.display = "block";
                    return false;
                }
            }
            function loadpage() {
                inputvirgola();
                online();
            }

        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">


        <div class="modal fade bs-modal-lg" id="insertnumber" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title font-blue uppercase"><b>Insert Number</b></h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <div class="input-icon">
                                <i class="fa fa-phone font-blue"></i>
                                <input class="form-control" type="text" autocomplete="off" onkeydown="return submitonEnter(document.getElementById('confrimbutt'));"
                                       placeholder="Phone Number" name="numb" id="numb"> 
                            </div>
                        </div>
                        <div class="form-group" id="errornumb" style="display: none;">
                            <div class="alert alert-danger">
                                <b>Errror!</b> The number entered is incorrect. 
                            </div>
                        </div>
                    </div>
                    <form id="confrimbutt" method="post" onsubmit="return checknumb(this);">
                        <div class="modal-footer">
                            <button type="submit" class="btn blue btn-outline" ><i class="fa fa-arrow-right"></i> Confirm</button>
                            <button type="button" class="btn red btn-outline" onclick="return dismiss('insertnumber');"
                                    data-dismiss="modal"><i class="fa fa-window-close"></i> Close</button>
                        </div>
                    </form>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>


        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_es3.jsp"%>
            <!-- END MENU -->

            <%                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
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
                            <h3 class="page-title">External Services <small><b>Paymat</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <%if (Constant.is_IT) {%>

                    <%
                        
                        
                        boolean abilita = Constant.test && !Engine.isCentral();
                        
                        Branch br = Engine.get_branch(Engine.getFil()[0]);
                        boolean senzacredenziali = 
                        br.getPay_token().equals("-") || br.getPay_token().equals("") || 
                        br.getPay_user().equals("-") || br.getPay_user().equals("") || 
                        br.getPay_password().equals("-") || br.getPay_password().equals("");
                        
                        //boolean abilita = !senzacredenziali && !Engine.isCentral();
                        
                        if (abilita) {
                            String search = request.getParameter("search");
                        if (search == null) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <form action="es_paym.jsp" method="post" >
                                <input type="hidden" name="search" value="sra1"/>
                                <button type="submit" class="btn btn-outline dark"><i class="fa fa-search"></i> Product List</button>
                            </form>
                        </div>
                    </div>
                    <%} else if (search.equals("sra1")) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <form action="es_paym.jsp" method="post" >
                                <input type="hidden" name="search" value="sra1"/>
                                <button type="submit" class="btn btn-outline dark"><i class="fa fa-refresh"></i> Refresh List</button>
                                <a href="es_paym.jsp" class="btn btn-outline red"><i class="fa fa-remove"></i> Reset</a>
                            </form>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Product List</span>
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
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_0" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Brand</th>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">Code</th>
                                                                    <th class="tabnow">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
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

                    <%}%>
                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong class="uppercase">this branch is not authorized to perform this operation.</strong>
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
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
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
            <!-- BEGIN THEME LAYOUT SCRIPTS -->
            <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
            <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
            <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
            <!-- END CORE PLUGINS -->
            <!-- BEGIN PAGE LEVEL PLUGINS -->
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
                                            scrollX: true,
                                            scrollY: true,
                                            ajax: {
                                                url: "Operazioni?type=list_paymat",
                                                dataSrc: "aaData",
                                                type: "GET"
                                            },
                                            initComplete: function (settings, json) {
                                                $('.popovers').popover();
                                            },
                                            columnDefs: [
                                                {orderable: 1, targets: [0]},
                                                {orderable: 1, targets: [1]},
                                                {orderable: 1, targets: [2]},
                                                {orderable: !1, targets: [3]}
                                            ],
                                            buttons: []
                                            ,
                                            colReorder: {reorderCallback: function () {
                                                    console.info("callback");
                                                }},
                                            lengthMenu: [
                                                [25, 50, 100, -1],
                                                [25, 50, 100, "All"]
                                            ],
                                            pageLength: 25,
                                            order: [],
                                            dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
                                            processing: true
                                        });
                                    };
                                    jQuery().dataTable && dt1();
                                });
            </script>
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
