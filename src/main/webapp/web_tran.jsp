<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Utility"%>
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
        <title>Mac2.0 - Website</title>
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




        <script type="text/javascript">
            function search_ing() {
                var status = document.getElementById('status').value;
                if (status !== '1') {
                    var d1 = document.getElementById('d1').value;
                    var d2 = document.getElementById('d2').value;
                    if (d1 === "" || d2 === "") {
                        document.getElementById('saerchmodbtn').click();
                        return false;
                    }
                }
            }

            function changestatus() {
                var status = document.getElementById('status').value;
                if (status === '1') {
                    document.getElementById('d1').value = '';
                    document.getElementById('d2').value = '';
                } else {

                }
            }

            function loadpage() {
                online();
                inputvirgola();
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
            <%@ include file="menu/menu_es1.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                ArrayList<Branch> array_branch = Engine.list_branch_completeAFTER311217();
                String pdfstr = Utility.generaId(50);

            %>

            <div class="modal fade" id="largelogin" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Password request</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <div class="input-icon">
                                    <i class="fa fa-key font-blue"></i>
                                    <input class="form-control" type="password" autocomplete="off" placeholder="Password" name="password"> 
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <form id="checkpasswordmodal" name="checkpasswordmodal" action="" method="post">
                                <button type="submit" class="btn btn-outline blue"><i class="fa fa-arrow-right"></i> Continue</button>
                                <a class="btn btn-outline red" data-dismiss="modal" onclick="return dismiss('largelogin');"><i class="fa fa-remove"></i> Cancel</a>
                            </form>
                        </div>
                    </div>
                </div>
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
                            <h3 class="page-title">External Services <small><b>Website</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <%if (Constant.is_IT) {%>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT BUY/SELL -->
                    <div class="modal fade" id="saerchmod" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">Search</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <p>
                                            <b>Warning!</b> Fields 'date' must be complete for this 'Status' value.
                                        </p>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <a class="btn btn-outline red" data-dismiss="modal" onclick="return dismiss('saerchmod');"><i class="fa fa-remove"></i> OK</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal fade" id="saerchmod2" tabindex="-1" role="dialog" aria-hidden="true">
                        <button type="button" id="saerchmodbtn" class="" data-toggle="modal" data-target="#saerchmod"></button>
                    </div>
                    <%
                        //if (Constant.test) {
                        if (Constant.is_IT) {
                            //if (Constant.external) {
                            String scode = "r1";
                            boolean iscentral = Engine.isCentral();

                            String[] fil = Engine.getFil();
                            ArrayList<String[]> status = Engine.sito_stati(null, null, true);
                            //ArrayList<String[]> status = Engine.listStatus_booking(null, null, true);
                            if (request.getParameter("search") == null) {
                    %>
                    <form name="f1" method="post" action="web_tran.jsp">
                        <input type="hidden" name="search" value="<%=scode%>"/>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet box green">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-search"></i> Search</div>
                                        <div class="tools">
                                            <a href="javascript:;" class="collapse"> </a>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label>Type</label>
                                                        <select class="bs-select form-control" data-show-subtext="true" id="tipoweb" name="tipoweb">
                                                            <option value="">...</option>
                                                            <option value="BUY">BUY</option>
                                                            <option value="SELL">SELL</option>                                                            
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>Branch</label>
                                                        <select class="form-control select2" name="branch" id="branch">

                                                            <%if (iscentral) {%>
                                                            <option value="" selected="selected"></option>
                                                            <%for (int j = 0; j < array_branch.size(); j++) {%>
                                                            <option value="<%=array_branch.get(j).getCod()%>">
                                                                <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                            </option>
                                                            <%}%>

                                                            <%} else {%>
                                                            <option value="<%=fil[0]%>">
                                                                <%=fil[0]%> - <%=fil[1]%>
                                                            </option>
                                                            <%}%>

                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>Status</label>
                                                        <select class="bs-select form-control" data-show-subtext="true" id="status" name="status">
                                                            <%for (int i = 0; i < status.size(); i++) {%>
                                                            <option value="<%=status.get(i)[0]%>" data-content="<%=status.get(i)[2]%>"><%=status.get(i)[0]%></option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label>Date From</label>
                                                        <input type="text" class="form-control date-picker" id="d1" name="d1"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label>Date To</label>
                                                        <input type="text" class="form-control date-picker" id="d2" name="d2"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label><p class='ab'></p>
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                        <a href="web_tran.jsp" class="btn btn-outline red" ><i class="fa fa-remove"></i> Cancel</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else if (request.getParameter("search").equals("r1")) {

                        String sel_buy = Utility.getRequestValue(request, "tipoweb").equals("BUY") ? "selected" : "";
                        String sel_sell = Utility.getRequestValue(request, "tipoweb").equals("SELL") ? "selected" : "";

                    %>
                    <form name="f1" id="f1" method="post" action="web_tran.jsp">
                        <input type="hidden" name="search" value="<%=scode%>"/>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet box green">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-search"></i> Search</div>
                                        <div class="tools">
                                            <a href="javascript:;" class="collapse"> </a>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label>Type</label>
                                                        <select class="bs-select form-control" data-show-subtext="true" id="tipoweb" name="tipoweb">
                                                            <option value="">...</option>
                                                            <option value="BUY" <%=sel_buy%>>BUY</option>
                                                            <option value="SELL" <%=sel_sell%>>SELL</option>                                                            
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>Branch</label>
                                                        <select class="form-control select2" name="branch" id="branch">
                                                            <%if (iscentral) {%>
                                                            <option value="" selected="selected"></option>
                                                            <%for (int j = 0; j < array_branch.size(); j++) {
                                                                    String sel = "";
                                                                    if (array_branch.get(j).getCod().equals(request.getParameter(""))) {
                                                                        sel = "";
                                                                    }
                                                            %>
                                                            <option <%=sel%> value="<%=array_branch.get(j).getCod()%>">
                                                                <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                            </option>
                                                            <%}%>

                                                            <%} else {%>
                                                            <option value="<%=fil[0]%>">
                                                                <%=fil[0]%> - <%=fil[1]%>
                                                            </option>
                                                            <%}%>



                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>Status</label>
                                                        <select class="bs-select form-control" data-show-subtext="true" id="status" name="status">
                                                            <%for (int i = 0; i < status.size(); i++) {
                                                                    if (status.get(i)[0].equals(request.getParameter("status"))) {%>
                                                            <option value="<%=status.get(i)[0]%>" selected data-content="<%=status.get(i)[2]%>"><%=status.get(i)[0]%></option>
                                                            <%} else {%>
                                                            <option value="<%=status.get(i)[0]%>" data-content="<%=status.get(i)[2]%>"><%=status.get(i)[0]%></option>
                                                            <%}%>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label>Date From</label>
                                                        <input type="text" class="form-control date-picker" id="d1" name="d1" value="<%=Utility.sanitize(request.getParameter("d1"))%>"/>
                                                    </div>
                                                </div>
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label>Date To</label>
                                                        <input type="text" class="form-control date-picker" id="d2" name="d2" value="<%=Utility.sanitize(request.getParameter("d2"))%>"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label><p class='ab'></p>
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                        <a href="web_tran.jsp" class="btn btn-outline red" ><i class="fa fa-remove"></i> Cancel</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>    
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue-dark">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>

                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-bordered" id="sample_1" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th class="tabnow" style="width: 100px;">Type</th>
                                                        <th class="tabnow" style="width: 100px;">Branch</th>
                                                        <th class="tabnow" style="width: 100px;">Code</th>
                                                        <th class="tabnow" style="width: 100px;">Currency</th>
                                                        <th class="tabnow" style="width: 100px;">Quantity</th>
                                                        <th class="tabnow" style="width: 100px;">Date</th>
                                                        <th class="tabnow" style="width: 100px;">Surname</th>
                                                        <th class="tabnow" style="width: 100px;">Name</th>
                                                        <th class="tabnow" style="width: 100px;">Status</th>
                                                        <th class="tabnow" style="width: 100px;">Actions</th>
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
                    <%}%>
                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-warning">
                                <strong>This feature will be activated soon.</strong>
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
    <!-- END THEME LAYOUT SCRIPTS -->
    <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
    <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
    <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>

    <script type="text/javascript">
                                        jQuery(document).ready(function () {
                                            var dt2 = function () {
                                                var g = $("#sample_1");
                                                g.dataTable({
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
                                                    ajax: {
                                                        url: "Query?type=prenot_list&branch=<%=Utility.sanitize(Utility.parseArrayValues(request.getParameterValues("branch"))%>&tipoweb=<%=Utility.sanitize(request.getParameter("tipoweb"))%>&d1=<%=Utility.sanitize(request.getParameter("d1"))%>&d2=<%=Utility.sanitize(request.getParameter("d2"))%>&status=<%=Utility.sanitize(request.getParameter("status"))%>&pdf=<%=Utility.sanitize(pdfstr))%>",
                                                        dataSrc: "aaData",
                                                        type: "GET"
                                                    },
                                                    initComplete: function (settings, json) {
                                                        $('.popovers').popover();
                                                    },
                                                    scrollX: true,
                                                    columnDefs: [
                                                        {orderable: 1, targets: [0]},
                                                        {orderable: 1, targets: [1]},
                                                        {orderable: 1, targets: [2]},
                                                        {orderable: 1, targets: [3]},
                                                        {orderable: 1, targets: [4]},
                                                        {orderable: 1, targets: [5]},
                                                        {orderable: 1, targets: [6]},
                                                        {orderable: 1, targets: [7]},
                                                        {orderable: 1, targets: [8]},
                                                        {orderable: !1, targets: [9]}
                                                    ],
                                                    buttons: [
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
                                                    dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
                                                    processing: true
                                                });
                                            };
                                            jQuery().dataTable && dt2();

                                        });

    </script>


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
