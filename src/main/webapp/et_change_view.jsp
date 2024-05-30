<%@page import="rc.so.entity.Openclose"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.ET_change"%>
<%@page import="rc.so.entity.Office"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Branch"%>
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
        <title>Mac2.0 - Ext. Tr.</title>
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
        <div class="clearfix"> </div>
        <div class="page-container">

            <div class="page-content-wrapper">
                <div class="page-content">
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction External Transfer <small><b>View</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>


                    <%
                        String lan_index = (String) session.getAttribute("language");
                        lan_index = "IT";
                        Etichette et_index = new Etichette(lan_index);
                        String cod = request.getParameter("cod");
                        ET_change et = Engine.get_ET_change(cod);

                        if (et != null) {
                            String fil[] = Engine.getFil();
                            ArrayList<String[]> array_till = Engine.list_till_enabled();
                            ArrayList<String[]> array_kind = Engine.list_all_kind(fil[0]);
                            ArrayList<String[]> array_bank = Engine.list_bank_enabled();
                            ArrayList<Branch> array_branch = Engine.list_branch_enabled();
                            ArrayList<String[]> array_credit_card = Engine.list_bank_pos_enabled();
                            String desc_safe = Utility.formatAL(et.getTill_from(), array_till, 1);
                            String id_oc = et.getIdopen_from();

                            String fr = "-";
                            Openclose oc_fr = Engine.query_oc(id_oc);
                            if (oc_fr != null) {
                                fr = oc_fr.getId();
                            }

                            String tf = "";
                            if (et.getFg_tofrom().equals("T")) {
                                tf = "checked";
                            }

                            String am = "";
                            if (et.getAuto().equals("A")) {
                                am = "checked";
                            }


                    %>

                    <div class="row">

                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Code</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getId())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(et.getDt_it(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getUser())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Status</label><p class='ab'></p>
                                <%=et.formatStatus(et.getFg_annullato())%>
                            </div>
                        </div>
                        <div class="clearfix"></div>  
                        <% if (et.getFg_annullato().equals("1")) {%>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(et.getDel_dt(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getDel_user())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Motivation</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getDel_motiv())%>"/>
                            </div>
                        </div>
                        <div class="clearfix"></div>  
                        <%}%>
                        <hr>


                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Safe/Till</label>
                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(desc_safe)%>">

                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id open/close</label>
                                <div class="input-icon">
                                    <i class="fa fa-bookmark font-blue-dark"></i>
                                    <input type="text" class="form-control" id="idopentillfrom" name="idopentillfrom" readonly="readonly" value="<%=Utility.sanitize(fr)%>">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>To / From</label><p class='ab'></p>
                                <input type="checkbox" <%=tf%> readonly class="make-switch" 
                                       id="tofrom" data-size="normal" 
                                       data-on-color="primary" data-off-color="info"
                                       data-on-text="<span class='tabnow'>To</span>" 
                                       data-off-text="<span class='tabnow'>From</span>"
                                       >
                            </div>
                        </div>
                        <%if (et.getFg_tofrom().equals("F") && et.getFg_brba().equals("BR")) {%>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Auto / Manual</label><p class='ab'></p>
                                <input type="checkbox" <%=am%> readonly class="make-switch"
                                       id="autman" name="autman" data-size="normal" 
                                       data-on-color="primary" data-off-color="info"
                                       data-on-text="<span class='clearfix'>&nbsp;Auto&nbsp;</span>" 
                                       data-off-text="<span class='clearfix'>&nbsp;Manual&nbsp;</span>" />

                            </div>
                        </div>

                        <%}%>


                        <div class="clearfix"></div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Type</label>
                                <input type="text" class="form-control" id="typeop" readonly name="typeop"
                                       value="<%=Engine.formatBankBranch(et.getFg_brba())%>">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Bank / Branch</label>
                                <input type="text" class="form-control"  readonly  name="bankbranch"
                                       value="<%=Engine.formatBankBranch(et.getCod_dest(), et.getFg_brba(), array_bank, array_branch, array_credit_card)%>"></input>
                            </div>
                        </div>
                        <%if (et.getFg_tofrom().equals("F") && et.getFg_brba().equals("BR")) {%>
                        <div class="col-md-3">
                            <div class="form-group"id="srcing_div_2">
                                <label>Source Code (From Branch)</label>
                                <input type="text" class="form-control"  readonly  name="srcing" value="<%=Utility.sanitize(et.getId_in())%>"/>
                            </div>
                        </div>
                        <%}%>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Note</label>
                                <div class="input-icon">
                                    <i class="fa fa-sticky-note-o font-blue"></i>
                                    <input type="text" class="form-control" disabled value="<%=Utility.sanitize(et.getNote())%>"/>
                                </div>
                            </div>
                        </div>  
                        <%String notepartenza = Engine.note_fromBranch(et.getCod_in()).toUpperCase();
                            if (!notepartenza.equals("")) {%>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Note (From Branch)</label>
                                <div class="input-icon">
                                    <i class="fa fa-sticky-note-o font-blue"></i>
                                    <input type="text" class="form-control" disabled value="<%=Utility.sanitize(notepartenza)%>"/>
                                </div>
                            </div>
                        </div>  
                        <%}%>        

                    </div>  

                    <%

                        String typeop = request.getParameter("typeop");
                        if (typeop == null) {
                            typeop = "";
                        }

                        if (typeop.equals("CH")) {

                            ArrayList<ET_change> val = Engine.get_ET_change_value(cod);
                            ArrayList<ET_change> tg = Engine.get_ET_change_tg(cod);

                    %>




                    <div class="row">
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
                                            <table class="table table-bordered" id="sample_1" width="100%">
                                                <thead style="display: none;"></thead>

                                                <tbody>
                                                    <tr>
                                                        <th class="tabnow">Figures</th>
                                                        <th class="tabnow">Kind</th>
                                                        <th class="tabnow"style="width: 200px;">N. Stock</th>
                                                        <th class="tabnow"style="width: 200px;">Quantity</th>
                                                        <th class="tabnow"style="width: 200px;">Rate</th>
                                                        <th class="tabnow"style="width: 200px;">Total</th>
                                                        <th class="tabnow"style="width: 200px;">Buy Value</th>
                                                        <th class="tabnow"style="width: 200px;">Spread</th>
                                                        <th class="tabnow"style="width: 80px;">Actions</th>
                                                    </tr>
                                                </tbody>
                                                <%for (int i = 0; i < val.size(); i++) {
                                                        ET_change et1 = val.get(i);
                                                        Currency cu = Engine.getCurrency(et1.getValuta());
                                                        boolean nospread = Constant.newpread && et.getFg_brba().equals("BR");

                                                %>
                                                <tr>
                                                    <td><%=Utility.sanitize(cu.getCode())%> - <%=Utility.sanitize(cu.getDescrizione())%></td>
                                                    <td><%=Utility.sanitize(et1.getSupporto())%> - <%=Utility.sanitize(Utility.formatAL(et1.getSupporto(), array_kind, 1))%></td>

                                                    <td><%=Utility.formatMysqltoDisplay((Utility.roundDoubleandFormat(Utility.fd(et1.getIp_stock()), 0)))%></td>
                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(et1.getIp_quantity()))%></td>
                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(et1.getIp_rate()))%></td>
                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(et1.getIp_total()))%></td>
                                                    <%
                                                        if (nospread) {%>
                                                    <td></td>
                                                    <td></td>
                                                    <%} else {%>
                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(et1.getIp_buyvalue()))%></td>
                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(et1.getIp_spread()))%></td>
                                                    <%}
                                                    %>

                                                    
                                                    <td>
                                                        <%if (et1.getSupporto().equals("01")) {%>
                                                        <a class="btn btn-circle blue btn-outline btn-sm" data-toggle="modal" 
                                                           href="#tg_full<%=i%>"><i class="fa fa-info-circle"></i> Info</a>

                                                        <div class="modal fade" id="tg_full<%=i%>" tabindex="-1" role="dialog" >
                                                            <div class="modal-dialog modal-full">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                                        <h4 class="modal-title">
                                                                            Figures: <b><%=Utility.sanitize(cu.getCode())%> - <%=Utility.sanitize(cu.getDescrizione())%></b></h4>
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
                                                                                        <table class="table table-bordered table-responsive" id="tg_sample_cuts_<%=i%>" width="100%">
                                                                                            <thead style="display: none;"></thead>
                                                                                            <tbody>
                                                                                                <tr>
                                                                                                    <th>Size</th>
                                                                                                    <th>Quantity</th>
                                                                                                    <th>Total</th>
                                                                                                </tr>
                                                                                                <%for (int j = 0; j < tg.size(); j++) {

                                                                                                        ET_change et3 = tg.get(j);
                                                                                                        if (et3.getValuta().equals(cu.getCode())) {


                                                                                                %>
                                                                                                <tr id="tg_trcuts<%=i%>_<%=j%>" valign="middle">
                                                                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(et3.getIp_taglio()))%></td>
                                                                                                    <td><%=Utility.formatMysqltoDisplay((Utility.roundDoubleandFormat(Utility.fd(et3.getIp_quantity(), 2), 0)))%></td>

                                                                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(et3.getIp_total()))%></td>
                                                                                                </tr>
                                                                                                <%}
                                                                                                    }%>
                                                                                            </tbody>
                                                                                        </table>
                                                                                        <script type="text/javascript">
                                                                                            jQuery(document).ready(function () {
                                                                                                var dt = function () {
                                                                                                    var e = $("#tg_sample_cuts_<%=i%>");
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
                                                                            <button type="button" class="btn green btn-outline" data-dismiss="modal"><i class="fa fa-window-close"></i> Close</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <%}%>
                                                    </td>
                                                </tr>
                                                <%}%>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>



                    <%} else if (typeop.equals("NC")) {

                        ArrayList<ET_change> val = Engine.get_ET_nochange_value(cod);
                        ArrayList<NC_category> array_nc = Engine.all_nc_category();
                    %>
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
                                                                <%for (int i = 0; i < val.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=Utility.sanitize(val.get(i).getNc_causal())%> - 
                                                                        <%=Utility.sanitize(Engine.formatALNC_category(val.get(i).getNc_causal(), array_nc))%>
                                                                    </td>
                                                                    <td>
                                                                        <%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(val.get(i).getIp_quantity()), 0))%>
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
                    <%}%>



                    <!-- END CONTENT -->
                    <!-- BEGIN QUICK SIDEBAR -->
                    <!-- END QUICK SIDEBAR -->
                </div>
                <!-- END CONTAINER -->
                <!-- BEGIN FOOTER -->
            </div>
            <div class="page-footer">
                <div class="page-footer-inner"> <%=et_index.getFooter()%></div>
                <div class="scroll-to-top">
                    <i class="icon-arrow-up"></i>
                </div>
            </div>
            <!-- END FOOTER -->
            <!-- BEGIN CORE PLUGINS -->

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
                                {orderable: !1, targets: [4]},
                                {orderable: !1, targets: [5]},
                                {orderable: !1, targets: [6]},
                                {orderable: !1, targets: [7]},
                                {orderable: !1, targets: [8]},
                                {orderable: !1, targets: [9]},
                                {orderable: !1, targets: [10]}
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
                        var e = $("#sample_1");
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
                                {orderable: !1, targets: [6]},
                                {orderable: !1, targets: [7]},
                                {orderable: !1, targets: [8]}
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

                $(document).ready(function () {
                    window.history.pushState(null, "", window.location.href);
                    window.onpopstate = function () {
                        window.history.pushState(null, "", window.location.href);
                    };
                });
            </script>
            <!-- BEGIN THEME GLOBAL SCRIPTS -->

    </body>

</html>
