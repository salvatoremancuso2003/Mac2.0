<%@page import="rc.so.entity.Ch_transaction"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.Booking"%>
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
        <title>Mac2.0 - Website Booking View</title>
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
        <script src="assets/soop/js/validate.min.js" type="text/javascript"></script>

        <%
            Booking bo = Engine.get_prenot(request.getParameter("cod"));

            String type = "BUY";
            String inout = "Out";
            String font = "font-blue";
            String color = "blue";
            if (!bo.getCod().startsWith("B") ){
                font = "font-green-sharp";
                color = "green-sharp";
                inout = "In";
                type = "SELL";
            }


        %>

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
            <%                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);

                ArrayList<String[]> array_intbook = Engine.list_internetbooking();

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
                            <h3 class="page-title">Website Booking <b><%=type%></b> - Canale: <%=Utility.sanitize(Utility.formatAL(bo.getCanale(), array_intbook, 1))%> </h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                        <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->

                    <%if (bo != null) {
                            ArrayList<String[]> listastati = Engine.sito_stati(null, null, true);
                            ArrayList<Currency> cu = Engine.list_all_currency();
                            ArrayList<Branch> br = Engine.list_branch();
                    %>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Code</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(bo.getCod())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled"
                                       value="<%=Utility.sanitize(Utility.formatStringtoStringDate(bo.getDt_ritiro(), Constant.patternsql, Constant.patternnormdate_filter))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Status</label><p class='ab'></p>
                                <%=Utility.sanitize(Utility.formatAL(bo.getStato(), listastati, 2))%>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Branch</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Engine.formatBankBranch(bo.getFiliale(), "BR", null, br, null)%>"/>
                            </div>
                        </div>
                        <div class="clearfix"></div> 
                        <div class="col-md-12">
                            <hr>
                        </div>
                        <div class="col-md-12">
                            <div class="mt-element-list">
                                <div class="mt-list-head list-news font-white bg-<%=color%>">
                                    <div class="list-head-title-container">
                                        <h3 class="list-title pull-left"><i class="fa <%=Constant.iconcur%>"></i></h3><h3 class="list-title"> Total 
                                            <span class="pull-right">
                                                <b id="total0"><%=Utility.sanitize(Utility.formatMysqltoDisplay(bo.getEuro()))%></b>
                                            </span> 
                                        </h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div> 
                        <div class="col-md-12">
                            <hr>
                        </div>


                        <div class="clearfix"></div>    
                        <div class="col-md-6">
                            <div class="portlet box <%=color%>">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-money"></i>
                                        <span class="caption-subject">Figures</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-responsive" id="sample_1" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th>Kind</th>
                                                        <th>Figures</th>
                                                        <th>Quantity</th>
                                                        <th>Rate</th>
                                                        <th>Fx Com.</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>NOTES</td>
                                                        <td><%=Utility.sanitize(bo.getCurrency() + " - " + Engine.formatALCurrency(bo.getCurrency(), cu))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(bo.getTotal()))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(bo.getRate()))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(bo.getFx_comm()))%></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="portlet box green-dark">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-check"></i>
                                        <span class="caption-subject">Active Discounts</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-responsive" id="sample_2" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>Value</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<String[]> result = Engine.agevolazioni_varie();
                                                        for (int x = 0; x < result.size(); x++) {
                                                            if (bo.getSconti().contains(result.get(x)[0])) {

                                                                String vv = "0.00";
                                                                String tipo = result.get(x)[2];
                                                                String perc = result.get(x)[3];
                                                                String valu = result.get(x)[4];
                                                                if (tipo.equals("0")) {
                                                                    vv = Utility.roundDoubleandFormat((Utility.fd(bo.getTotal()) / Utility.fd(bo.getRate()) + Utility.fd(bo.getFx_comm())) * Utility.fd(perc) / 100.00, 2);
                                                                } else {
                                                                    vv = valu;
                                                                }

                                                    %>
                                                    <tr>
                                                        <td><%=result.get(x)[1]%></td>
                                                        <td><%=Utility.formatMysqltoDisplay(vv)%></td>
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
                        <div class="col-md-3">
                            <div class="portlet box dark">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-check"></i>
                                        <span class="caption-subject">Additional Products</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <table class="table table-responsive" id="sample_2" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>Value</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<String[]> ser = Engine.servizi_agg();
                                                        for (int x = 0; x < ser.size(); x++) {
                                                            if (bo.getProdotti().contains(ser.get(x)[0])) {

                                                                String vv = "0.00";
                                                                String tipo = ser.get(x)[2];
                                                                String perc = ser.get(x)[3];
                                                                String valu = ser.get(x)[4];
                                                                if (tipo.equals("0")) {
                                                                    vv = Utility.roundDoubleandFormat((Utility.fd(bo.getTotal()) / Utility.fd(bo.getRate()) + Utility.fd(bo.getFx_comm())) * Utility.fd(perc) / 100.00, 2);
                                                                } else {
                                                                    vv = valu;
                                                                }

                                                    %>
                                                    <tr>
                                                        <td><%=ser.get(x)[1]%></td>
                                                        <td><%=Utility.formatMysqltoDisplay(vv)%></td>
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


                        <div>
                            <div class="clearfix"></div>
                            <p class='ab'></p>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Surname</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(bo.getCl_cognome()%>" /> 
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Name</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(bo.getCl_nome())%>" /> 
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(bo.getCl_email())%>" /> 
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Phone number</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(bo.getCl_telefono())%>" /> 
                                </div>
                            </div>
                            <div class="clearfix"></div>

                        </div>

                        <div class="clearfix"></div>
                        <p class='ab'></p>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Client Notes</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Engine.visualNote(bo.getNote(), 100))%>"/> 
                            </div>
                        </div>
                        <%
                            String intnote = Engine.visualNote(bo.getCrm_note(), 3000);
                        %>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Internal Notes</label>
                                <textarea class="form-control" style="resize:none;" rows="<%=Utility.get_altezza_textarea(intnote)%>" cols="50" disabled><%=Utility.sanitize(intnote)%></textarea>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <p class='ab'></p>
                        <%
                            if (bo.getStato().equals("7")) {
                                String[] o1 = Engine.get_internetbooking_tr(bo.getCod());
                                if (o1 != null) {
                                    Ch_transaction ch1 = Engine.query_transaction_ch(o1[0]);
                                    if (ch1 != null) {%>
                        <div class="col-md-12">
                            <h3>Transaction <a href='transaction_view.jsp?cod=<%=Utility.sanitize(o1[0])%>' target="_blank" 
                                               class='btn white popovers' container='body' 
                                               data-trigger='hover' data-container='body' 
                                               data-placement='top' data-content='Show'><i class='fa fa-eye'></i>
                                </a>
                            </h3>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id Transaction</label>
                                <input type="text" class="form-control" 
                                       disabled="disabled" value="<%=Utility.sanitize(ch1.getFiliale())%> <%=Utility.sanitize(ch1.getId())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled" 
                                       value="<%=Utility.sanitize(Utility.formatStringtoStringDate(ch1.getData(), Constant.patternsqldate, Constant.patternnormdate))%>"
                                       />
                            </div>
                        </div>
                        <%}
                                }
                            }
                        %>  

                        <%}%>
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
        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                var dt = function () {
                    var e = $("#sample_1");
                    e.dataTable({
                        language: {aria: {},
                            sProcessing: "Processo...",
                            emptyTable: "Nessun risultato trovato",
                            info: "Mostra _START_ di _END_ su _TOTAL_ risultati",
                            infoEmpty: "Nessun risultato trovato",
                            infoFiltered: "(filtrati su _MAX_ totali)",
                            lengthMenu: "Mostra _MENU_", search: "Trova:",
                            zeroRecords: "Nessun risultato trovato",
                            paginate: {previous: "Precedente", next: "Successiva", last: "Ultima", first: "Prima"}},
                        scrollX: true,
                        columnDefs: [
                            {orderable: !1, targets: [0]},
                            {orderable: !1, targets: [1]},
                            {orderable: !1, targets: [2]},
                            {orderable: !1, targets: [3]},
                            {orderable: !1, targets: [4]}
                        ],
                        order: [],
                        dom: "<t>"
                    });
                };
                jQuery().dataTable && dt();
            }
            );


        </script>
        <script type="text/javascript">
            jQuery(document).ready(function () {
                var dt = function () {
                    var e = $("#sample_2");
                    e.dataTable({
                        language: {aria: {},
                            sProcessing: "Processo...",
                            emptyTable: "Nessun risultato trovato",
                            info: "Mostra _START_ di _END_ su _TOTAL_ risultati",
                            infoEmpty: "Nessun risultato trovato",
                            infoFiltered: "(filtrati su _MAX_ totali)",
                            lengthMenu: "Mostra _MENU_", search: "Trova:",
                            zeroRecords: "Nessun risultato trovato",
                            paginate: {previous: "Precedente", next: "Successiva", last: "Ultima", first: "Prima"}},
                        scrollX: true,
                        columnDefs: [
                            {orderable: !1, targets: [0]},
                            {orderable: !1, targets: [1]},
                            {orderable: !1, targets: [2]},
                            {orderable: !1, targets: [3]},
                            {orderable: !1, targets: [4]}
                        ],
                        order: [],
                        dom: "<t>"
                    });
                };
                jQuery().dataTable && dt();
            }
            );


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
