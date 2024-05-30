<%@page import="rc.so.entity.Openclose"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.IT_change"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Currency"%>
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
    if(link_value!=null){
        Utility.redirect(request, response,link_value);
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
        <title>Mac2.0 - Int. Tr.</title>
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


        <!-- END THEME LAYOUT SCRIPTS -->
        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>

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
                            <h3 class="page-title">Transaction Internal Transfer <small><b>View</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <%
                        String cod = request.getParameter("cod");

                        IT_change it = Engine.get_internal_transfer(cod);

                    %>
                    <%if (it != null) {
                            ArrayList<Currency> array_all_currency = Engine.list_all_currency();
                            ArrayList<String[]> array_kind = Engine.list_all_kind(Engine.getFil()[0]);
                            ArrayList<String[]> array_till = Engine.list_till_enabled();
                            
                            Openclose oc_from = Engine.query_oc(it.getIdopen_from());
                            Openclose oc_to = Engine.query_oc(it.getIdopen_to());

                    %>
                    <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Code</label>
                            <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(it.getId())%>"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Date</label>
                            <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(it.getDt_it(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Operator</label>
                            <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(it.getUser())%>"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Status</label><p class='ab'></p>
                            <%=it.formatStatus(it.getFg_annullato())%>
                        </div>
                    </div>
                    <div class="clearfix"></div>  
                    <% if (it.getFg_annullato().equals("1")) {%>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Delete Date</label>
                            <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(it.getDel_dt(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Delete Operator</label>
                            <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(it.getDel_user())%>"/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label>Delete Motivation</label>
                            <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(it.getDel_motiv())%>"/>
                        </div>
                    </div>
                    <div class="clearfix"></div>  
                    <%}%>
                    <hr>


                    
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>From</label>
                                <input type="text" class="form-control" readonly="readonly" name="tillfrom" value="<%=Utility.sanitize(Utility.formatAL(it.getTill_from(), array_till, 1))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>To</label>
                                <input type="text" class="form-control" readonly="readonly" name="tillto" value="<%=Utility.sanitize(Utility.formatAL(it.getTill_to(), array_till, 1))%>"/>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id open till - From</label>
                                <div class="input-icon">
                                    <i class="fa fa-bookmark font-blue-dark"></i>
                                    <input type="text" class="form-control uppercase" name="idopentillfrom" readonly="readonly"value="<%=Utility.sanitize(oc_from.getId())%>"> 
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id open till - To</label>
                                <div class="input-icon">
                                    <i class="fa fa-bookmark font-blue-dark"></i>
                                    <input type="text" class="form-control uppercase" name="idopentillto" readonly="readonly" value="<%=Utility.sanitize(oc_to.getId())%>"> 
                                </div>
                            </div>
                        </div>
                    </div>

                        
                    <%
                        String typeop = request.getParameter("typeop");
                        if (typeop == null) {
                            typeop = "";
                        }
                        if (typeop.equals("CH")) {
                            ArrayList<IT_change> values = Engine.get_internal_transfer_ch_value(cod);
                            ArrayList<IT_change> tagli = Engine.get_internal_transfer_ch_tg(cod);
                    %>
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
                                                        <th class="tabnow">Quantity</th>
                                                        <th class="tabnow">Total</th>
                                                        <th class="tabnow" style="width: 80px;">Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>

                                                    <%for (int i = 0; i < values.size(); i++) {%>

                                                    <tr id="trfig">
                                                        <td id="trfig_td1"><%=Utility.sanitize(values.get(i).getValuta())%> - <%=Utility.sanitize(Engine.formatALCurrency(values.get(i).getValuta(), array_all_currency))%>
                                                        </td>
                                                        <td id="trfig_td2"><%=Utility.sanitize(values.get(i).getSupporto())%> - <%=Utility.sanitize(Utility.formatAL(values.get(i).getSupporto(), array_kind, 1))%>
                                                        </td>
                                                        <td id="trfig_td4">
                                                            <span style="text-align: right;float: right;"id="s_quantnow<%=i%>" name="sss"><%=Utility.formatMysqltoDisplay(Utility.sanitize(values.get(i).getQuantita()))%></span>
                                                        </td>
                                                        <td id="trfig_td5">
                                                            <span style="text-align: right;float: right;" id="s_totnow<%=i%>" name="sss"><%=Utility.formatMysqltoDisplay(Utility.sanitize(values.get(i).getTotale()))%></span>
                                                        </td>
                                                        <td>
                                                            <%if(values.get(i).getSupporto().equals("01")){%>
                                                            <a class="btn btn-circle green btn-outline btn-sm" data-toggle="modal" href="#full<%=i%>"><i class="fa fa-eye"></i> Show </a>
                                                            <div class="modal fade" id="full<%=i%>" tabindex="-1" role="dialog" >
                                                                <div class="modal-dialog modal-full">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                                            <h4 class="modal-title">
                                                                                Figures: <b><%=Utility.sanitize(values.get(i).getValuta())%> - <%=Utility.sanitize(Engine.formatALCurrency(values.get(i).getValuta(), array_all_currency))%></b></h4>
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
                                                                                                        <th>Quantity</th>
                                                                                                        <th>Total</th>
                                                                                                    </tr>
                                                                                                    <%for (int j = 0; j < tagli.size(); j++) {   
                                                                                                            if(tagli.get(j).getValuta().equals(values.get(i).getValuta())){%>
                                                                                                    <tr id="trcuts<%=i%>_<%=j%>" valign="middle">
                                                                                                        <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(tagli.get(j).getTaglio()))%></td>
                                                                                                        <td><span style="text-align: right;float: right;" ><%=Utility.formatMysqltoDisplay(Utility.sanitize(tagli.get(j).getQuantita()))%></span>
                                                                                                        </td>
                                                                                                        <td><span style="text-align: right;float: right;" ><%=Utility.formatMysqltoDisplay(Utility.sanitize(tagli.get(j).getTotale()))%></span>
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                    <%}}%>
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
                                                                            <div class="modal-footer">
                                                                                <button type="button" class="btn red btn-outline" data-dismiss="modal"><i class="fa fa-window-close"></i> Close</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                                                                        <%}%>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <%
                                                        }%>
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%} else if (typeop.equals("NC")) {
                        ArrayList<IT_change> values = Engine.get_internal_transfer_noch_value(cod);
                        ArrayList<NC_category> array_nc = Engine.all_nc_category();%>
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
                                                                    <th class="tabnow">Quantity</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for (int i = 0; i < values.size(); i++) {%>
                                                                <tr id="trnoc<%=i%>">
                                                                    <td id="trnoc_td1"><%=Utility.sanitize(values.get(i).getNc_causal())%> - <%=Utility.sanitize(Engine.formatALNC_category(values.get(i).getNc_causal(), array_nc))%>
                                                                    </td>
                                                                    <td>
                                                                        <span style="text-align: right;float: right;" id="nc_quantold>"><%=Utility.formatMysqltoDisplay(Utility.sanitize(values.get(i).getQuantita()))%></span>

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


                    <%} else {%>
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
                <!-- BEGIN CORE PLUGINS -->
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
                                    {orderable: 1, targets: [2]},
                                    {orderable: 1, targets: [3]},
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