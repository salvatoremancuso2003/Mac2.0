<%@page import="rc.so.util.Constant"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="rc.so.entity.NC_transaction"%>
<%@page import="rc.so.entity.Ch_transaction_doc"%>
<%@page import="rc.so.entity.Client"%>
<%@page import="rc.so.entity.Ch_transaction_value"%>
<%@page import="rc.so.entity.Ch_transaction"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="rc.so.util.Engine"%>
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
        <title>Mac2.0</title>
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


        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />

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
            function checkdescr() {
                var di_code = document.getElementById("di_code").value.trim();
                var descr = document.getElementById("descr").value.trim();
                if (di_code === "" || descr === "") {
                    var ermsg = "You must complete all fields.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
            }
        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return inputvirgola();">
        <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title font-red uppercase"><b>Error message</b></h4>
                    </div>
                    <div class="modal-body" id="errorlargetext">ERROR</div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" onclick="return dismiss('errorlarge');" data-dismiss="modal">Close</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
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
                    <div class="clearfix"></div>
                    <%
                        String ty = "central";
                        String codcl = request.getParameter("codcl");
                        ArrayList<Ch_transaction> li = Engine.query_transaction_ch_CLIENT_central(codcl);
                        ArrayList<NC_transaction> li_nc = Engine.query_NC_transaction_codcl_centr(codcl);
                        Client cc = Engine.query_Client_central(codcl);
                        String loy = Engine.getCodiceClienteAttivo(codcl);
                        if (cc != null && !li.isEmpty() && loy != null) {

                            String lasttrcod = li.get(li.size() - 1).getCod();
                            Ch_transaction_doc d1 = Engine.get_tr_doc_central(lasttrcod, "_docrico");

                            String font = "";
                            if (!Engine.data_scadenza_attiva(cc.getDt_scadenza_documento())) {
                                font = "font-red";
                            }

                    %>

                    <div class="row">
                        <div class="col-md-12">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-user font-blue-dark"></i>
                                        <span class="caption-subject font-blue-dark bold uppercase">Customer</span>
                                    </div>
                                    <div class="tools">
                                        <form action="Download?type=reprint_doc_tr&cod=<%=Utility.sanitize(d1.getCodice_documento())%>" target="_blank" method="post">
                                            <button type="submit" class="btn btn-outline blue-dark"><i class="fa fa-file-pdf-o"></i> View Last Identity Document </button>
                                        </form>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <label class="col-md-3 control-label">Surname</label>
                                            <label class="col-md-3 control-label bold"><%=Utility.sanitize(cc.getCognome())%></label>
                                            <label class="col-md-3 control-label">Name</label>
                                            <label class="col-md-3 control-label bold"><%=Utility.sanitize(cc.getNome())%></label>
                                        </div>
                                        <div class="col-md-12">
                                            <label class="col-md-3 control-label">Tax Code</label>
                                            <label class="col-md-3 control-label bold"><%=Utility.sanitize(cc.getCodfisc())%></label>
                                            <label class="col-md-3 control-label">Loyalty Code</label>
                                            <label class="col-md-3 control-label bold"><%=Utility.sanitize(loy)%></label>
                                        </div>
                                        <div class="col-md-12">
                                            <label class="col-md-3 control-label">Expiration Date </label>
                                            <label class="col-md-3 control-label bold <%=font%>"><%=Utility.sanitize(cc.getDt_scadenza_documento())%></label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%if (!li.isEmpty()) {%>
                    <div class="row">
                        <div class="col-md-12">


                            <div class="portlet box blue-dark">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Transaction Change</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>

                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-bordered" id="sample_1" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th class="tabnow" style="width: 80px;">Actions</th>
                                                        <th class="tabnow" style="width: 80px;">BranchID</th>
                                                        <th class="tabnow" style="width: 100px;">Code</th>
                                                        <th class="tabnow" style="width: 100px;">Date</th>
                                                        <th class="tabnow" style="width: 80px;">Operator</th>
                                                        <th class="tabnow" style="width: 100px;">Type</th>
                                                        <th class="tabnow" style="width: 100px;">Total</th>
                                                        <th class="tabnow" style="width: 100px;">Net</th>
                                                        <th class="tabnow" style="width: 100px;">Commission</th>
                                                        <th class="tabnow" style="width: 100px;">Spread</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        for (int x = 0; x < li.size(); x++) {
                                                            Ch_transaction res = li.get(x);
                                                            String bb = "";
                                                            if (!res.getBb().equals("0")) {
                                                                if (res.getBb().equals("1")) {
                                                                    bb = "<span class='font-green-jungle ital'><small>&nbsp;&nbsp;<i class='fa fa-bold'></i><i class='fa fa-bold'></i></small></span>";
                                                                } else if (res.getBb().equals("2")) {
                                                                    bb = "<span class='font-red ital'><small>&nbsp;&nbsp;<i class='fa fa-bold'></i><i class='fa fa-bold'></i></small></span>";
                                                                }
                                                            }
                                                            String ib = "";
                                                            if (res.getIntbook().equals("1")) {
                                                                ib = "<span class='font-green-soft ital'><small>&nbsp;&nbsp;<i class='fa fa-italic'></i><i class='fa fa-bold'></i></small></span>";
                                                            }
                                                            String ca = "";
                                                            if (res.getTipotr().equals("B")) {
                                                                ArrayList<Ch_transaction_value> va = Engine.query_transaction_value(res.getCod());
                                                                for (int f = 0; f < va.size(); f++) {
                                                                    if (va.get(f).getSupporto().equals("04")) {
                                                                        ca = "<span class='font-dark ital'><b><u>CA</u></b></span>";
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            

                                                    %>
                                                    <tr>
                                                        <td>
                                                            <form action="transaction_view.jsp" method="post" target="_blank">
                                                                <button type="submit" class="btn btn-sm btn-outline btn-circle blue-dark">
                                                                    <i class="fa fa-eye"></i> Show</button>
                                                                <input type="hidden" name="cod" value="<%=Utility.sanitize(res.getCod())%>"/>
                                                                <input type="hidden" name="type" value="<%=ty%>"/>
                                                            </form>
                                                        </td>
                                                        <td><%=Utility.sanitize(res.getFiliale())%></td>
                                                        <td><%=Utility.sanitize(res.getId())%></td>
                                                        <td><%=Utility.formatStringtoStringDate(res.getData().split("\\.")[0], "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss")%></td>
                                                        <td><%=Utility.sanitize(res.getUser())%></td>
                                                        <td><%=Ch_transaction.formatType(res.getTipotr()) + bb + ib + ca%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(res.getTotal()))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(res.getPay()))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(res.getCommission()))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(res.getSpread_total()))%></td>
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
                    <%}%>
                    <%if (!li_nc.isEmpty()) {%>        
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Transaction No Change</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-responsive table-bordered" id="sample_2" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th class="tabnow" style="width: 80px;">Actions</th>
                                                        <th class="tabnow" style="width: 80px;">BranchID</th>
                                                        <th class="tabnow" style="width: 100px;">Code</th>
                                                        <th class="tabnow" style="width: 200px;">Date</th>
                                                        <th class="tabnow" style="width: 80px;">Operator</th>
                                                        <th class="tabnow" style="width: 100px;">Total</th>
                                                        <th class="tabnow" style="width: 100px;">Quantity</th>
                                                        <th class="tabnow" style="width: 100px;">Price</th>
                                                        <th class="tabnow" style="width: 100px;">Fee</th>
                                                        <th class="tabnow" style="width: 300px;">Category</th>
                                                        <th class="tabnow" style="width: 300px;">Causal</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<NC_category> array_nc_cat = Engine.list_nc_category_enabled();
                                                        ArrayList<NC_causal> array_nc_caus = Engine.list_nc_causal_enabled();

                                                        for (int x = 0; x < li_nc.size(); x++) {

                                                            NC_transaction res = li_nc.get(x);
                                                            String q1 = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(res.getQuantita()), 0));
                                                            String p1 = Utility.sanitize(Utility.formatMysqltoDisplay(res.getPrezzo()));
                                                            String f1 = Utility.formatMysqltoDisplay("0.00");

                                                            if (res.getFg_tipo_transazione_nc().equals("1")) {
                                                                q1 = "1";
                                                                p1 = Utility.sanitize(Utility.formatMysqltoDisplay(res.getNetto()));
                                                                f1 = Utility.sanitize(Utility.formatMysqltoDisplay(res.getCommissione()));
                                                            } else if (res.getFg_tipo_transazione_nc().equals("3")) {
                                                                q1 = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(res.getRicevuta()), 0));
                                                                p1 = Utility.sanitize(Utility.formatMysqltoDisplay(res.getQuantita()));
                                                            } else if (res.getFg_tipo_transazione_nc().equals("21")) {
                                                                String comm = "0.00";
                                                                if (Utility.fd(res.getCommissione()) > 0) {
                                                                    comm = res.getCommissione();
                                                                } else {
                                                                    comm = res.getTi_ticket_fee();
                                                                }
                                                                f1 = Utility.sanitize(Utility.formatMysqltoDisplay(comm));
                                                            }%>
                                                    <tr>
                                                        <td>
                                                            <form action="nc_transaction_view.jsp" method="post" target="_blank">
                                                                <button type="submit" class="btn btn-sm btn-outline btn-circle blue">
                                                                    <i class="fa fa-eye"></i> Show</button>
                                                                <input type="hidden" name="cod" value="<%=res.getCod()%>"/>
                                                                <input type="hidden" name="type" value="<%=ty%>"/>
                                                            </form>
                                                        </td>
                                                        
                                                        <td><%=Utility.sanitize(res.getFiliale())%></td>
                                                        <td><%=Utility.sanitize(res.getId())%></td>
                                                        <td><%=Utility.sanitize(Utility.formatStringtoStringDate(res.getData(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss"))%></td>
                                                        <td><%=Utility.sanitize(res.getUser()%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(res.getTotal()))%></td>
                                                        <td><%=q1%></td>
                                                        <td><%=Utility.sanitize(p1)%></td>
                                                        <td><%=Utility.sanitize(f1)%></td>
                                                        <td><%=Utility.sanitize(res.getGruppo_nc()) + " - " + Utility.sanitize(StringUtils.replace(Utility.visualizzaStringaMySQL(Engine.formatALNC_category(res.getGruppo_nc(), array_nc_cat))), "€", "&#0128;")%></td>
                                                        <td><%=Utility.sanitize(res.getCausale_nc()) + " - " + Utility.sanitize(StringUtils.replace(Utility.visualizzaStringaMySQL(Engine.formatALNC_causal(res.getCausale_nc(), array_nc_caus))), "€", "&#0128;")%></td>
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
                    <%}%>
                    <%} else {%>
                    <%}%>
                    <!-- BEGIN CONTENT -->
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                </div>
                <!-- END CONTENT -->
                <!-- BEGIN QUICK SIDEBAR -->
                <!-- END QUICK SIDEBAR -->
            </div>
            <!-- END CONTAINER -->
            <!-- BEGIN FOOTER -->

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


            <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
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
                                        initComplete: function (settings, json) {
                                            $('.popovers').popover();
                                        },
                                        columnDefs: [
                                            {orderable: !1, targets: [0]},
                                            {orderable: !1, targets: [1]},
                                            {orderable: !1, targets: [2]},
                                            {orderable: !1, targets: [3]},
                                            {orderable: !1, targets: [4]},
                                            {orderable: !1, targets: [5]},
                                            {orderable: !1, targets: [6]},
                                            {orderable: !1, targets: [7]},
                                            {orderable: !1, targets: [8]},
                                            {orderable: !1, targets: [9]}
                                        ],
                                        buttons: []
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
            <script type="text/javascript">
                jQuery(document).ready(function () {
                    var dt2 = function () {
                        var g = $("#sample_2");
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
                            scrollX: true,
                            scrollY: false,
                            columnDefs: [
                                {orderable: !1, targets: [0]},
                                {orderable: !1, targets: [1]},
                                {orderable: !1, targets: [2]},
                                {orderable: !1, targets: [3]},
                                {orderable: !1, targets: [4]},
                                {orderable: !1, targets: [5]},
                                {orderable: !1, targets: [6]},
                                {orderable: !1, targets: [7]},
                                {orderable: !1, targets: [8]},
                                {orderable: !1, targets: [9]},
                                {orderable: !1, targets: [10]}
                            ],
                            initComplete: function (settings, json) {
                                $('.popovers').popover();
                            },
                            buttons: []
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
                            //dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

                            processing: true
                        });

                    };
                    jQuery().dataTable && dt2();
                });
                function dropDownFixPosition(button, dropdown) {
                    var dropDownTop = button.offset().top + button.outerHeight();
                    dropdown.css('top', dropDownTop + "px");
                    dropdown.css('left', button.offset().left + "px");
                }

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
