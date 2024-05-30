<%@page import="java.util.List"%>
<%@page import="rc.so.entity.Sizecuts"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.List_ma"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="rc.so.util.Etichette"%>
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
        <title>Mac2.0 - Safe RealTime</title>
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
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>



        <script type="text/javascript">
            function search_ing() {
                var branch = document.getElementById('branch').value;
                if (branch === "" || branch === "...") {
                    document.getElementById('saerchmodbtn').click();
                    return false;
                }

            }

        </script>
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
            <%@ include file="menu/menu_li7.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                String decimal = Constant.decimal;
                String thousand = Constant.thousand;
                boolean central = Engine.isCentral();
                ArrayList<Branch> array_branch = Engine.list_branch_enabled();
                String cod1 = Engine.getFil()[0];
                Branch br1 = Engine.get_branch(cod1);
                String startvalue = "0" + decimal + "00";
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
                            <h3 class="page-title">List <small><b>Safe Real-Time</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
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
                                            <b>Warning!</b> You must completed all fields.
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
                        String scode = "r1";
                        if (request.getParameter("search") == null) {
                    %>
                    <form name="f1" method="post" action="oc_safereal.jsp" onsubmit="return search_ing();">
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
                                                <%if (central) {%>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Branch</label>
                                                        <select class="form-control select2-allow-clear" name="branch" id="branch" >
                                                            <option value="" selected="selected"></option>
                                                            <%for (int j = 0; j < array_branch.size(); j++) {%>
                                                            <option value="<%=array_branch.get(j).getCod()%>">
                                                                <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                            </option>
                                                            <%}%>

                                                        </select>
                                                    </div>
                                                </div>
                                                <%} else {%>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Branch</label>
                                                        <input type="text" class="form-control" disabled value="<%=br1.getCod() + " - " + br1.getDe_branch()%>"/>
                                                        <input type="hidden" name="branch" id="branch" value="<%=br1.getCod()%>"/>
                                                    </div>
                                                </div>
                                                <%}%>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label><p class='ab'></p>
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                        <a href="oc_safereal.jsp" class="btn btn-outline red" ><i class="fa fa-remove"></i> Cancel</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else if (request.getParameter("search").equals("r1")) {%>


                    <form name="f1" id="f1" method="post" action="oc_safereal.jsp" onsubmit="return search_ing();">
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
                                                <%if (central) {%>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Branch ID</label>
                                                        <select class="form-control select2-allow-clear" name="branch" id="branch" >
                                                            <option value="" selected="selected"></option>
                                                            <%for (int j = 0; j < array_branch.size(); j++) {
                                                                    String selected = "";
                                                                    if (array_branch.get(j).getCod().equals(request.getParameter("branch"))) {
                                                                        selected = "selected";
                                                                    }
                                                            %>
                                                            <option <%=selected%> value="<%=array_branch.get(j).getCod()%>"> 
                                                                <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                            </option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                </div>  
                                                <%} else {%>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Branch</label>
                                                        <input type="text" class="form-control" disabled value="<%=br1.getCod() + " - " + br1.getDe_branch()%>"/>
                                                        <input type="hidden" name="branch" id="branch" value="<%=br1.getCod()%>"/>
                                                    </div>
                                                </div>
                                                <%}%>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label><p class='ab'></p>
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                        <a href="oc_safereal.jsp" class="btn btn-outline red" ><i class="fa fa-remove"></i> Cancel</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>    
                    <%
                        
                        ArrayList<Till> array_till = Engine.list_till_status(null, null, request.getParameter("branch"));
                        Till safe = Engine.getContainsTill("000", array_till);
                        String opencloseid = safe.getId_opcl();

                        ArrayList<Currency> array_all_currency = Engine.list_all_currency();
                        ArrayList<String[]> array_kind = Engine.list_all_kind(request.getParameter("branch"));
                        List<Sizecuts> array_figures_sizecuts = Engine.figures_sizecuts_enabled();
                        ArrayList<NC_category> array_nc = Engine.all_nc_category();
                        
                        String status = "";
                        
                        ArrayList<String[]> array_list_oc_change = null;
                        ArrayList<String[]> list_oc_nochange = null;
                        ArrayList<String[]> list_oc_change_cuts = null;

                        if (safe.getTy_opcl().equals("CLOSE")) {
                            status = "CLOSED";
                            array_list_oc_change = Engine.list_oc_change_fisici(opencloseid);
                            list_oc_change_cuts = Engine.list_oc_change_cuts_fisici(opencloseid);
                            list_oc_nochange = Engine.list_oc_nochange_fisici(opencloseid);
                        } else {
                            status = "OPENED";
                            array_list_oc_change = Engine.list_oc_change_real(opencloseid);
                            list_oc_change_cuts = Engine.list_oc_change_cuts_real(opencloseid);
                            list_oc_nochange = Engine.list_oc_nochange_real(opencloseid);
                        }

                        

                        String dt1 = Engine.getNow_norm();
                    %>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=dt1%>"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Safe - Till</label>
                                <input type="text" class="form-control uppercase" disabled value="<%=Utility.sanitize(safe.getName())%>"> 
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>Status</label>
                                <input type="text" class="form-control uppercase" disabled value="<%=status%>"> 
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-money"></i>
                                        <span class="caption-subject">Figures </span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-responsive table-bordered" id="sample_0" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th class="tabnow">Figures</th>
                                                        <th class="tabnow">Kind</th>
                                                        <th class="tabnow">Actual Quantity</th>
                                                        <th class="tabnow">Actual Total</th>
                                                        <th class="tabnow" style="width: 80px;">Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <%
                                                        for (int i = 0; i < array_list_oc_change.size(); i++) {
                                                            if (Utility.fd(array_list_oc_change.get(i)[3]) > 0) {
                                                                String act_total1 = Utility.formatMysqltoDisplay(Utility.sanitize(array_list_oc_change.get(i)[3]));
                                                                String act_quant = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(array_list_oc_change.get(i)[4]), 0));
                                                    %>
                                                    <tr id="trfig<%=i%>">
                                                        <td id="trfig_td1_<%=i%>"><%=Utility.sanitize(array_list_oc_change.get(i)[2])%> - <%=Utility.sanitize(Engine.formatALCurrency(array_list_oc_change.get(i)[2], array_all_currency))%>
                                                        </td>
                                                        <td id="trfig_td2_<%=i%>"><%=Utility.sanitize(array_list_oc_change.get(i)[1])%> - <%=Utility.sanitize(Utility.formatAL(array_list_oc_change.get(i)[1], array_kind, 1))%>
                                                        </td>
                                                        <td id="trfig_td3_<%=i%>">
                                                            <span style="text-align: right;float: right;"  id="s_quantold<%=i%>" name="sss">
                                                                <%=act_quant%>
                                                            </span>
                                                            <input type="hidden"  name="s_quantold<%=i%>"/>
                                                            <input type="hidden"  name="currency<%=i%>" value="<%=Utility.sanitize(array_list_oc_change.get(i)[2])%>"/>
                                                            <input type="hidden"  name="kind<%=i%>" value="<%=Utility.sanitize(array_list_oc_change.get(i)[1])%>"/>
                                                        </td>
                                                        <td id="trfig_td3_<%=i%>">
                                                            <span style="text-align: right;float: right;" id="s_totold<%=i%>" name="sss">
                                                                <%=Utility.sanitize(act_total1)%>
                                                            </span>
                                                            <input type="hidden"  name="s_totold<%=i%>"/>
                                                        </td>
                                                        <td>
                                                            <%if (array_list_oc_change.get(i)[1].equals("01")) {%>
                                                            <a class="btn btn-circle green btn-outline btn-sm" data-toggle="modal" href="#full<%=i%>"><i class="fa fa-eye"></i> View</a>
                                                            <%
                                                                ArrayList<String> array_sizecuts = new ArrayList<>();
                                                                for (int j = 0; j < array_figures_sizecuts.size(); j++) {
                                                                    if (array_figures_sizecuts.get(j).getValuta().equals(array_list_oc_change.get(i)[2])) {
                                                                        array_sizecuts.add(array_figures_sizecuts.get(j).getIp_taglio());
                                                                    }
                                                                }
                                                            %>
                                                            <div class="modal fade" id="full<%=i%>" tabindex="-1" role="dialog" >
                                                                <div class="modal-dialog modal-full">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                                            <h4 class="modal-title">
                                                                                Figures: <b><%=Utility.sanitize(array_list_oc_change.get(i)[2])%> - <%=Utility.sanitize(Engine.formatALCurrency(array_list_oc_change.get(i)[2], array_all_currency))%></b></h4>
                                                                        </div>
                                                                        <div class="modal-body"> 
                                                                            <div class="portlet box blue-dark">
                                                                                <div class="portlet-title">
                                                                                    <div class="caption">
                                                                                        <i class="fa fa-money"></i>
                                                                                        <span class="caption-subject">Size</span>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="portlet-body">
                                                                                    <div class="row">
                                                                                        <div class="col-md-12">
                                                                                            <table class="table table-bordered table-responsive" id="sample_cuts_<%=i%>" width="100%" >
                                                                                                <thead style="display: none;"></thead>
                                                                                                <tbody>
                                                                                                    <tr>
                                                                                                        <th>Size</th>
                                                                                                        <th>Actual Stock</th>
                                                                                                        <th>Actual Total</th>
                                                                                                    </tr>
                                                                                                    <%

                                                                                                        boolean visual = true;

                                                                                                        //int actualst = 0;
                                                                                                        double tot_act = 0.00;
                                                                                                        for (int j = 0; j < array_sizecuts.size(); j++) {
                                                                                                            String act_size = "";
                                                                                                            String act_total = "";
                                                                                                            if (visual) {
                                                                                                                act_size = "0";
                                                                                                                act_total = "0" + decimal + "00";
                                                                                                                for (int k = 0; k < list_oc_change_cuts.size(); k++) {
                                                                                                                    if (list_oc_change_cuts.get(k)[1].equals(array_list_oc_change.get(i)[2])) {
                                                                                                                        if (list_oc_change_cuts.get(k)[3].equals(array_sizecuts.get(j))) {
                                                                                                                            act_size = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(list_oc_change_cuts.get(k)[4]), 0));
                                                                                                                            act_total = Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_change_cuts.get(k)[5]));
                                                                                                                            tot_act = tot_act + Utility.fd(Utility.formatDoubleforMysql(act_total));
                                                                                                                            break;
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }%>
                                                                                                    <tr id="trcuts<%=i%>_<%=j%>" valign="middle">
                                                                                                <input type="hidden"  name="cuts_currency<%=i%>_<%=j%>" value="<%=Utility.sanitize(array_list_oc_change.get(i)[2])%>"/>
                                                                                                <input type="hidden"  name="cuts_kind<%=i%>_<%=j%>" value="<%=Utility.sanitize(array_list_oc_change.get(i)[1])%>"/>

                                                                                                <td><%=Utility.formatMysqltoDisplay(array_sizecuts.get(j))%></td>
                                                                                                <input type="hidden" name="sizecuts<%=i%>_<%=j%>" value="<%=array_sizecuts.get(j)%>"/>
                                                                                                <td >
                                                                                                    <span style="text-align: right;float: right;"id="quantold<%=i%>_<%=j%>" name="sss">
                                                                                                        <%=act_size%>
                                                                                                    </span><input type="hidden" name="quantold<%=i%>_<%=j%>"/>
                                                                                                </td>
                                                                                                <td ><span style="text-align: right;float: right;"
                                                                                                           id="totold<%=i%>_<%=j%>" name="sss"
                                                                                                           ><%=Utility.sanitize(act_total)%></span>
                                                                                                    <input type="hidden" name="totold<%=i%>_<%=j%>"/>
                                                                                                </td>

                                                                                                </tr>
                                                                                                <%}%>

                                                                                                <tr>
                                                                                                    <th>TOTAL</th>
                                                                                                    <th><span class="font-blue-dark" style="text-align: right;float: right;" ><%=act_quant%></span></th>
                                                                                                    <th><span class="font-blue-dark" style="text-align: right;float: right;" >
                                                                                                            <%=Utility.sanitize(act_total1)%>
                                                                                                        </span></th>
                                                                                                </tr>
                                                                                                </tbody>
                                                                                            </table>
                                                                                            <input type="hidden" name="totquantcuts<%=i%>"/>
                                                                                            <input type="hidden" name="totnewcuts<%=i%>"/>
                                                                                            <script type="text/javascript">
                                                                                                jQuery(document).ready(function () {
                                                                                                    var dt = function () {
                                                                                                        var e = $("#sample_cuts_<%=i%>");
                                                                                                        e.dataTable({
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
                                                                                                                {orderable: !1, targets: [0]},
                                                                                                                {orderable: !1, targets: [1]},
                                                                                                                {orderable: !1, targets: [2]}
                                                                                                            ],
                                                                                                            scrollX: true,
                                                                                                            lengthMenu: [[-1], ["All"]],
                                                                                                            pageLength: -1,
                                                                                                            order: [],
                                                                                                            dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'><'col-md-6 col-sm-12'f>r><t>"
                                                                                                        });

                                                                                                    };
                                                                                                    jQuery().dataTable && dt();
                                                                                                });
                                                                                            </script>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <%}%>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <%}

                                                        }%>
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue-dark">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-arrows-v"></i>
                                        <span class="caption-subject">No change</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">

                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-bordered" id="sample_1" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">Actual Stock</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for (int i = 0; i < list_oc_nochange.size(); i++) {%>
                                                                <tr id="trnoc<%=i%>">
                                                                    <td id="trnoc_td1_<%=i%>"><%=Utility.sanitize(list_oc_nochange.get(i)[1])%> - <%=Utility.sanitize(Engine.formatALNC_category(list_oc_nochange.get(i)[1], array_nc))%>
                                                                    </td>
                                                            <input type="hidden" name="nc_causal<%=i%>" value="<%=Utility.sanitize(list_oc_nochange.get(i)[1])%>"/>
                                                            <td>
                                                                <span style="text-align: right;float: right;" id="nc_quantold<%=i%>"><%=Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_nochange.get(i)[2]))%></span>
                                                                <input type="hidden" name="nc_quantold<%=i%>" value="<%=Utility.sanitize(list_oc_nochange.get(i)[2])%>"/>
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
        </div>
        <!-- END FOOTER -->
        <!--[if lt IE 9]>
<script src="../assets/global/plugins/respond.min.js"></script>
<script src="../assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        
        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
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


        <!-- END PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>




        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script type="text/javascript">
                                                                                                jQuery(document).ready(function () {
                                                                                                    var dt = function () {
                                                                                                        var e = $("#sample_0");
                                                                                                        e.dataTable({
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
                                                                                                                {orderable: !1, targets: [2]},
                                                                                                                {orderable: !1, targets: [3]},
                                                                                                                {orderable: !1, targets: [4]}
                                                                                                            ],
                                                                                                            scrollX: true,
                                                                                                            lengthMenu: [[-1], ["All"]],
                                                                                                            pageLength: -1,
                                                                                                            order: [],
                                                                                                            dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'><'col-md-6 col-sm-12'f>r><t>"
                                                                                                        });
                                                                                                    };
                                                                                                    jQuery().dataTable && dt();
                                                                                                });
        </script>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                var dt1 = function () {
                    var f = $("#sample_1");
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
                            {orderable: !1, targets: [1]}
                        ],
                        lengthMenu: [[-1], ["All"]],
                        pageLength: -1,
                        order: [],
                        dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'><'col-md-6 col-sm-12'f>r><t>"
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
