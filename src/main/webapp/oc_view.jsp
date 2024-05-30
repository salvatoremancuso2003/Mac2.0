<%@page import="rc.so.entity.IT_change"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.entity.Openclose"%>
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
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>  




    </head>
    <!-- END HEAD -->
    <body class="page-full-width page-content-white">
        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                ArrayList<String[]> cclist = Engine.credit_card_enabled();
                ArrayList<String[]> ba = Engine.list_bankAccount();

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

                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Open/Close <small><b>View</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT TILL -->

                    <%                        String cod_oc = request.getParameter("cod");
                        Openclose co = Engine.query_oc(cod_oc);
                        if (co != null) {
                            ArrayList<Currency> array_all_currency = Engine.list_all_currency();
                            ArrayList<String[]> array_kind = Engine.list_all_kind(co.getFiliale());
                            ArrayList<Till> listTill = Engine.listTill(co.getFiliale());
                            Till safe = Engine.getSafe(co.getFiliale());
                            Till ti = Engine.getContainsTill(co.getTill(), listTill);
                            ArrayList<String[]> array_list_oc_change = Engine.list_oc_change(co.getCod());
                            ArrayList<String[]> list_oc_change_cuts = Engine.list_oc_change_cuts(co.getCod());
                            ArrayList<String[]> list_oc_nochange = Engine.list_oc_nochange(co.getCod());
                            ArrayList<String[]> list_oc_errors = Engine.list_oc_errors(co.getCod());
                            ArrayList<String[]> list_oc_pos = Engine.list_oc_allpos(co.getCod());
                            ArrayList<String[]> list_cc = Engine.credit_card(co.getFiliale());
                            ArrayList<NC_category> array_nc = Engine.all_nc_category();
                            IT_change itc = Engine.get_internal_transfer(co.getCod_it());
                            IT_change itnc = Engine.get_internal_transfer(co.getCod_itnc());
                    %>

                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Code</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(co.getId())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(co.getData(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(co.getUser())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Type</label><p class='ab'></p>
                                <%=co.formatType(co.getFg_tipo())%>
                            </div>
                        </div>
                        <div class="clearfix"></div>   
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Safe - Till</label>
                                <input type="text" class="form-control uppercase" disabled value="<%=Utility.sanitize(ti.getName())%>"> 
                            </div>
                        </div>
                        <%
                            if (Engine.is_selectAll_OC(cod_oc)) {
                        %>    
                        <div class="col-md-6">
                            <div class="form-group">
                                <div class="md-checkbox">
                                    <input type="checkbox" id="setall" checked disabled> 
                                    <label for="setall">
                                        <span></span>
                                        <span class="check"></span>
                                        <span class="box"></span> SELECT ALL
                                    </label>
                                </div>
                                <%if (!ti.isSafe()) {%>
                                <label id="setalllabel" class="bold uppercase">The cuts and currencies were verified.</label>
                                <%} else {%>
                                <label id="setalllabel" class="bold uppercase">The cuts and currencies were verified in Safe Real Time function</label>
                                <%}%>
                            </div>
                        </div>  
                        <%} else {%>
                        <div class="col-md-3"></div>
                        <div class="col-md-3"></div>
                        <%}%>
                        <div class="col-md-3 "></div>
                        <div class="clearfix"></div>
                        <%

                            if (!ti.getCod().equals(safe.getCod()) && co.getFg_tipo().equalsIgnoreCase("CLOSE")) {

                                String fo = "";
                                if (co.getForeign_tr().equals("Y")) {
                                    fo = "checked";
                                }
                                String lo = "";
                                if (co.getLocal_tr().equals("Y")) {
                                    lo = "checked";
                                }

                                String sto = "";
                                if (co.getStock_tr().equals("Y")) {
                                    sto = "checked";
                                }

                        %>
                        <div class="clearfix"></div>
                        <div class="col-md-12">
                            <div class="panel panel-default">
                                <div class="panel-heading inputbold">Transfer on safe after closure</div>
                                <div class="panel-body">
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Foreign</label>
                                            <div class="input-icon">
                                                <input type="checkbox" <%=fo%> 
                                                       disabled 
                                                       class="make-switch" name="foreign_tr" data-size="normal" data-on-text="Yes" data-off-text="No">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Local Figure</label>
                                            <div class="input-icon">
                                                <input type="checkbox" class="make-switch" <%=lo%> disabled name="local_tr" data-size="normal" data-on-text="Yes" data-off-text="No">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>Stock</label>
                                            <div class="input-icon">
                                                <input type="checkbox" class="make-switch" <%=sto%> disabled name="stock_tr" data-size="normal" data-on-text="Yes" data-off-text="No">
                                            </div>
                                        </div>
                                    </div>
                                    <%if (itc != null || itnc != null) {%>

                                    <div class="clearfix"></div>
                                    <%if (itc != null) {%>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>ID Internal Transfer (Change)</label>
                                            <div class="input-icon">
                                                <i class="fa fa-eye font-blue tooltips" title="View Internal" onclick="document.getElementById('formint<%=Utility.sanitize(itc.getId())%>').submit();"></i>
                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(itc.getId())%>"/>
                                            </div>
                                            <form id="formint<%=Utility.sanitize(itc.getId())%>" method='post' target='_blank' action='it_change_view.jsp'>
                                                <input type='hidden' name='cod' value='<%=Utility.sanitize(itc.getCod())%>'/>
                                                <input type='hidden' name='typeop' value='CH'/>
                                            </form>
                                        </div>
                                    </div>
                                    <%}%>  
                                    <%if (itnc != null) {%>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label>ID Internal Transfer (No Change)</label>
                                            <div class="input-icon">
                                                <i class="fa fa-eye font-blue tooltips" title="View Internal" onclick="document.getElementById('formint<%=Utility.sanitize(itnc.getCod())%>').submit();"></i>
                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(itnc.getId())%>"/>
                                            </div>
                                            <form id="formint<%=Utility.sanitize(itnc.getCod())%>" method='post' target='_blank' action='it_change_view.jsp'>
                                                <input type='hidden' name='cod' value='<%=Utility.sanitize(itnc.getCod())%>'/>
                                                <input type='hidden' name='typeop' value='NC'/>
                                            </form>
                                        </div>
                                    </div>
                                    <%}%>
                                    <%}%>
                                </div>
                            </div>
                        </div>
                        <%}%>     

                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-money"></i>
                                        <span class="caption-subject">Figures</span>
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
                                                        <th class="tabnow">Quantity</th>
                                                        <th class="tabnow">Total</th>
                                                        <th class="tabnow">Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%for (int i = 0; i < array_list_oc_change.size(); i++) {%>
                                                    <tr id="trfig<%=i%>">
                                                        <td><%=Utility.sanitize(array_list_oc_change.get(i)[2])%> - <%=Utility.sanitize(Engine.formatALCurrency(array_list_oc_change.get(i)[2], array_all_currency))%> 
                                                        </td>
                                                        <td><%=Utility.sanitize(array_list_oc_change.get(i)[1])%> - <%=Utility.sanitize(Utility.formatAL(array_list_oc_change.get(i)[1], array_kind, 1))%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.formatMysqltoDisplay(Utility.sanitize(array_list_oc_change.get(i)[4]))%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.formatMysqltoDisplay(Utility.sanitize(array_list_oc_change.get(i)[3]))%>
                                                        </td>

                                                        <td>
                                                            <%if (array_list_oc_change.get(i)[1].equals("01")) {%>
                                                            <a class="btn btn-circle green btn-outline btn-sm" data-toggle="modal" href="#full<%=i%>"><i class="fa fa-eye"></i> Show</a>
                                                            <div class="modal fade" id="full<%=i%>" tabindex="-1" role="dialog" aria-hidden="true">
                                                                <div class="modal-dialog modal-full">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                                            <h4 class="modal-title">
                                                                                Edit Figures: <b><%=Utility.sanitize(array_list_oc_change.get(i)[2])%> - <%=Utility.sanitize(Engine.formatALCurrency(array_list_oc_change.get(i)[2], array_all_currency))%>  - <%=Utility.sanitize(array_list_oc_change.get(i)[1])%> - <%=Utility.sanitize(Utility.formatAL(array_list_oc_change.get(i)[1], array_kind, 1))%></b></h4>
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
                                                                                            <table class="table table-responsive table-bordered" id="sample_cuts_<%=i%>" width="100%">
                                                                                                <thead style="display: none;">

                                                                                                </thead>
                                                                                                <tbody>
                                                                                                    <tr>
                                                                                                        <th class="tabnow">Size</th>
                                                                                                        <th class="tabnow">Quantity</th>
                                                                                                        <th class="tabnow">Total</th>
                                                                                                    </tr>
                                                                                                    <%
                                                                                                        for (int j = 0; j < list_oc_change_cuts.size(); j++) {
                                                                                                            if (list_oc_change_cuts.get(j)[1].equals(array_list_oc_change.get(i)[2])) {

                                                                                                    %>
                                                                                                    <tr id="trcuts<%=i%>_<%=j%>">
                                                                                                        <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_change_cuts.get(j)[3]))%></td>
                                                                                                        <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_change_cuts.get(j)[4]))%></td>
                                                                                                        <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_change_cuts.get(j)[5]))%></td>

                                                                                                    </tr>
                                                                                                    <%}
                                                                                                        }%>
                                                                                                </tbody>
                                                                                            </table>
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
                                                                                                            dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t>"
                                                                                                        });

                                                                                                    };
                                                                                                    jQuery().dataTable && dt();
                                                                                                });
                                                                                            </script>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button type="button" class="btn red btn-outline" data-dismiss="modal"><i class="fa fa-window-close"></i> Close</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <%} else {%>

                                                            <%}%>
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
                                            <table class="table table-responsive table-bordered" id="sample_1" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th class="tabnow">Description</th>
                                                        <th class="tabnow">Quantity User</th>
                                                        <th class="tabnow">Quantity System</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%for (int i = 0; i < list_oc_nochange.size(); i++) {%>
                                                    <tr>
                                                        <td><%=Utility.sanitize(list_oc_nochange.get(i)[1])%> - <%=Utility.sanitize(Engine.formatALNC_category(list_oc_nochange.get(i)[1], array_nc))%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(list_oc_nochange.get(i)[2]), 0))%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(list_oc_nochange.get(i)[3]), 0))%>
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


                        <div class="col-md-12">
                            <div class="portlet box green-dark">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-credit-card"></i>
                                        <span class="caption-subject">POS / Bank Account</span>
                                    </div>
                                </div>

                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-responsive table-bordered" id="sample_2" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th class="tabnow">Kind</th>
                                                        <th class="tabnow">Code - Description</th>
                                                        <th class="tabnow">Quantity</th>
                                                        <th class="tabnow">Total</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%for (int i = 0; i < list_oc_pos.size(); i++) {
                                                            String formbacc = "";
                                                            if (list_oc_pos.get(i)[2].equals("08")) {
                                                                formbacc = list_oc_pos.get(i)[3] + " - " + Utility.formatAL(list_oc_pos.get(i)[3], ba, 1);
                                                            } else {
                                                                formbacc = list_oc_pos.get(i)[3] + " - " + Utility.formatAL(list_oc_pos.get(i)[3], cclist, 1);
                                                            }

                                                            if (list_oc_pos.get(i)[2].equals("04") && Constant.is_CZ) {
                                                                String ba1 = list_oc_pos.get(i)[3].split("-")[0];
                                                                String ba2 = list_oc_pos.get(i)[3].split("-")[1];
                                                                formbacc = "<b>" + ba1 + "</b> " + Utility.formatAL(ba1, cclist, 1) + " - <b>" + ba2 + "</b> " + Engine.formatALCurrency(ba2, array_all_currency);
                                                            }
                                                    %>
                                                    <tr>
                                                        <td>
                                                            <%=Utility.sanitize(list_oc_pos.get(i)[2] + " - " + Utility.formatAL(list_oc_pos.get(i)[2], array_kind, 1))%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.sanitize(formbacc)%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_pos.get(i)[4]))%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_pos.get(i)[5]))%>
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
                        <div class="col-md-12">
                            <div class="portlet box red">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-arrows-v"></i>
                                        <span class="caption-subject">Errors</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-responsive table-bordered" id="sample_3" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th class="tabnow">Type</th>
                                                        <th class="tabnow">Kind</th>
                                                        <th class="tabnow">Figures</th>
                                                        <th class="tabnow">No Change</th>
                                                        <th class="tabnow">Credit Card</th>
                                                        <th class="tabnow">Quantity/Total Errors</th>
                                                        <th class="tabnow">Notes</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%for (int i = 0; i < list_oc_errors.size(); i++) {

                                                            String cur = list_oc_errors.get(i)[2] + " - " + Engine.formatALCurrency(list_oc_errors.get(i)[2], array_all_currency);
                                                            if (list_oc_errors.get(i)[2].equals("-")) {
                                                                cur = "";
                                                            }

                                                            String kin = list_oc_errors.get(i)[3] + " - " + Utility.formatAL(list_oc_errors.get(i)[3], array_kind, 1);
                                                            if (list_oc_errors.get(i)[3].equals("-")) {
                                                                kin = "";
                                                            }

                                                            String nc = list_oc_errors.get(i)[4] + " - " + Engine.formatALNC_category(list_oc_errors.get(i)[4], array_nc);
                                                            if (list_oc_errors.get(i)[4].equals("-")) {
                                                                nc = "";
                                                            }

                                                            String cc = list_oc_errors.get(i)[5] + " - " + Utility.formatAL(list_oc_errors.get(i)[5], list_cc, 1);
                                                            if (list_oc_errors.get(i)[5].equals("-")) {
                                                                cc = "";
                                                            }

                                                    %>
                                                    <tr>
                                                        <td><%=Openclose.formatTypeErrors(list_oc_errors.get(i)[1])%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.sanitize(kin)%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.sanitize(cur)%>
                                                        </td>
                                                        <td><%=Utility.sanitize(nc)%></td>
                                                        <td>
                                                            <%=Utility.sanitize(cc)%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_errors.get(i)[7]))%>
                                                        </td>
                                                        <td>
                                                            <%=Utility.sanitize(list_oc_errors.get(i)[6])%>
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
                        <%} else {%>
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
                <!-- END CORE PLUGINS -->
                <!-- BEGIN PAGE LEVEL PLUGINS -->
                <!-- END PAGE LEVEL PLUGINS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->
                <!-- END THEME LAYOUT SCRIPTS -->
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
                                                                                                                {orderable: !1, targets: [0]},
                                                                                                                {orderable: !1, targets: [1]},
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

                        jQuery().dataTable && dt1();
                    });
                </script>
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        var dt = function () {
                            var e = $("#sample_3");
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
                                    {orderable: !1, targets: [2]},
                                    {orderable: !1, targets: [3]},
                                    {orderable: !1, targets: [4]},
                                    {orderable: !1, targets: [5]},
                                    {orderable: !1, targets: [6]}
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
                        var dt = function () {
                            var e = $("#sample_2");
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
                                    {orderable: !1, targets: [2]},
                                    {orderable: !1, targets: [3]}
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