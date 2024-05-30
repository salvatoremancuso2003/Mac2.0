<%@page import="com.google.common.base.Splitter"%>
<%@page import="rc.so.entity.Booking"%>
<%@page import="rc.so.entity.Ch_transaction_file"%>
<%@page import="rc.so.entity.Client_CZ"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.entity.NC_transaction"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="rc.so.entity.Codici_sblocco"%>
<%@page import="rc.so.entity.Ch_transaction_value"%>
<%@page import="rc.so.entity.Openclose"%>
<%@page import="rc.so.entity.Ch_transaction"%>
<%@page import="rc.so.entity.BlacklistM"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Office"%>
<%@page import="rc.so.entity.Client"%>
<%@page import="rc.so.entity.Company"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.entity.Agency"%>
<%@page import="rc.so.entity.Figures"%>
<%@page import="rc.so.entity.CustomerKind"%>
<%@page import="rc.so.util.List_new"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.List_ma"%>
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
        <title>Mac2.0 - Transaction</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <link href="assets/soop/fontg/fontsgoogle1.css" rel="stylesheet" type="text/css" />

        <link href="assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!--<link href="assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />-->
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



        <%
            List_new ln = new List_new("B", session);

            ArrayList<Company> array_listCompany = ln.getArray_listCompany();
            ArrayList<String[]> array_identificationCard = ln.getArray_identificationCard();
            ArrayList<String[]> array_credit_card = ln.getArray_credit_card();
            ArrayList<String[]> array_undermincommjustify = ln.getArray_undermincommjustify();
            ArrayList<String[]> array_kindcommissionefissa = ln.getArray_kindcommissionefissa();
            ArrayList<String[]> array_unlockrate = Engine.unlockratejustify();

        %>

    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white">
        <!--- MODAL PASSWORD --->

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
                String fil = Engine.getFil()[0];
                String cod = request.getParameter("cod");

                Ch_transaction tra = Engine.query_transaction_ch(cod, request.getParameter("type"));

                Branch br1 = Engine.get_branch(tra.getFiliale(), request.getParameter("type"));

                Openclose oc = Engine.query_oc(tra.getId_open_till(), request.getParameter("type"));
                Till t1 = Engine.get_single_Till(oc.getFiliale(), oc.getTill(), request.getParameter("type"));
                CustomerKind ck = Engine.get_customerKind(tra.getTipocliente(), request.getParameter("type"));

                Codici_sblocco cs1 = Engine.getCod_tr(cod, "01", request.getParameter("type"));
                String resp1 = "";
                if (cs1 != null) {
                    resp1 = Engine.getRespCod(cs1.getCodice(), request.getParameter("type"));
                }

                boolean showagency = tra.getAgency().equals("1");
                String inout = "Out";
                String font = "font-blue";
                String color = "blue";
                if (tra.getTipotr().equals("S")) {
                    font = "font-green-sharp";
                    color = "green-sharp";
                    inout = "In";
                }
                ArrayList<String[]> array_kind_pay = Engine.kind_payment();
                ArrayList<String[]> array_bankacc = Engine.list_bankAccount();
                ArrayList<Ch_transaction_file> modify = Engine.list_transaction_modify(cod);
                boolean modificato_BUY = (modify.size() > 0) && tra.getTipotr().equals("B");
                boolean modificato_SELL = (modify.size() > 0) && tra.getTipotr().equals("S");
            %>
            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <!-- VUOTO RAF -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction <%=tra.formatType(tra.getTipotr())%> <small><b>VIEW</b> </small>
                                <%if (request.getParameter("type") == null) {%>
                                <a title="Export PDF" onclick="return document.getElementById('viewpdf').submit();">  
                                    <img src="assets/soop/img/Adobe-PDF-Document-icon.png" alt="Export PDF"/>
                                </a> 
                                <%}%>
                            </h3>
                            <form id="viewpdf" action="Fileview" target="_blank" method="post">
                                <input type="hidden" name="type" value="transactionpdf"/>
                                <input type="hidden" name="cod" value="<%=Utility.sanitize(tra.getCod())%>"/>
                            </form>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                            <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>

                        </div>
                    </div>
                    <div class="modal fade" id="largelock" role="dialog">
                        <div class="modal-dialog modal-full">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title">History of changes</h4>
                                </div>
                                <div class="modal-body">
                                    <table class="table table-responsive table-bordered table-hover" width="100%">
                                        <thead>
                                            <tr>
                                                <th class="tabnow">Old Kind</th>
                                                <th class="tabnow">New Kind</th>
                                                <th class="tabnow">Old Pos/Bank</th>
                                                <th class="tabnow">New Pos/Bank</th>
                                                <th class="tabnow">User</th>
                                                <th class="tabnow">Notes</th>
                                                <th class="tabnow">Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%for (int x = 0; x < modify.size(); x++) {

                                                    String oldk = modify.get(x).getOld_kind();
                                                    String newk = modify.get(x).getNew_kind();

                                                    String de_old_ps = "";
                                                    String de_new_ps = "";

                                                    if (oldk.equals("04") || oldk.equals("06") || oldk.equals("07")) {
                                                        de_old_ps = Utility.formatAL(modify.get(x).getOld_ps(), array_credit_card, 1);
                                                    }
                                                    if (oldk.equals("08")) {
                                                        de_old_ps = Utility.formatAL(modify.get(x).getOld_ps(), array_bankacc, 1);
                                                    }
                                                    if (newk.equals("04") || newk.equals("06") || newk.equals("07")) {
                                                        de_new_ps = Utility.formatAL(modify.get(x).getNew_ps(), array_credit_card, 1);
                                                    }
                                                    if (newk.equals("08")) {
                                                        de_new_ps = Utility.formatAL(modify.get(x).getNew_ps(), array_bankacc, 1);
                                                    }


                                            %>
                                            <tr>    
                                                    <td class="tabnow"><%=Utility.sanitize(oldk)%> - <%=Utility.sanitize(Utility.formatAL(oldk,
                                                        array_kind_pay, 1))%></td>
                                                    <td class="tabnow"><%=Utility.sanitize(newk)%> - <%=Utility.sanitize(Utility.formatAL(newk,
                                                            array_kind_pay, 1))%></td>
                                                <td class="tabnow"><%=Utility.sanitize(modify.get(x).getOld_ps())%> - <%=Utility.sanitize(de_old_ps)%></td>
                                                <td class="tabnow"><%=Utility.sanitize(modify.get(x).getNew_ps())%> - <%=Utility.sanitize(de_new_ps)%></td>
                                                <td class="tabnow"><%=Utility.sanitize(modify.get(x).getUser())%></td>
                                                <td><%=Utility.sanitize(StringUtils.replace(modify.get(x).getNotes(), "MODPAY#", "").trim())%></td>
                                                <td class="tabnow"><%=Utility.sanitize(modify.get(x).getData())%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                    </table>



                                </div>
                                <div class="modal-footer">
                                    <a type="button" class="btn btn-outline red" data-dismiss="modal"><i class="fa fa-remove"></i> Close</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Branch ID</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(br1.getCod())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Branch Description</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(br1.getDe_branch())%>"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Branch Address</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(br1.getAdd_via()) + " ; " + Utility.sanitize(br1.getAdd_cap()) + " ; " + Utility.sanitize(br1.getAdd_city())%>"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Code</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(tra.getId())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(tra.getData(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(tra.getUser())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Status</label><p class='ab'></p>
                                <%=tra.formatStatus(tra.getDel_fg())%>
                            </div>
                        </div>
                        <div class="clearfix"></div>  
                        <% if (tra.getDel_fg().equals("1")) {%>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(tra.getDel_dt(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(tra.getDel_user())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Motivation</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(tra.getDel_motiv())%>"/>
                            </div>
                        </div>
                        <div class="clearfix"></div>  
                        <%}%>



                        <hr>

                        <% if (cs1 != null) {
                                String verifica = cs1.getCodice();
                                if (cs1.getCodice().contains("###")) {
                                    verifica = Utility.formatAL(Splitter.on("###").splitToList(cs1.getCodice()).get(1), array_unlockrate, 1).toUpperCase();
                                }
                        %>
                        <div class="clearfix"></div>   
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Unlock Code / Motivation | Type</label>
                                <input type="text" class="form-control" disabled="disabled" 
                                       value="<%=Utility.sanitize(verifica) + " | " + Utility.sanitize(Codici_sblocco.format_ty_util(cs1.getTy_util()))%>"/>
                            </div>
                        </div>  
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled" 
                                       value="<%=Utility.sanitize(Utility.formatStringtoStringDate(cs1.getDt_utilizzo(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>  
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Operator</label>
                                <input type="text" class="form-control" disabled="disabled" 
                                       value="<%=Utility.sanitize(cs1.getUser_gen())%>"/>
                            </div>
                        </div>  
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Reference</label>
                                <input type="text" class="form-control" disabled="disabled" 
                                       value="<%=Utility.sanitize(resp1)%>"/>
                            </div>
                        </div>  
                        <div class="clearfix"></div>
                        <hr>
                        <%}%>
                        <!-- BUY -->
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Type</label>
                                <div class="input-icon">
                                    <i class="fa fa-check <%=font%>"></i>
                                    <input type="text" class="form-control uppercase" readonly="readonly"
                                           value="<%=tra.formatType(tra.getTipotr()).toUpperCase()%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- ID OPEN -->
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id open till</label>
                                <div class="input-icon">
                                    <i class="fa fa-bookmark <%=font%>"></i>
                                    <input type="text" class="form-control" 
                                           value="<%=Utility.sanitize(oc.getId())%>"
                                           readonly="readonly"/>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <!-- till -->
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Till</label>
                                <input type="text" class="form-control" 
                                       value="<%=Utility.sanitize(t1.getName())%>"
                                       readonly="readonly"/>
                            </div>
                        </div>

                        <div class="clearfix"></div>
                        <!-- customer kind -->
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Customer Kind</label>
                                <input type="text" class="form-control" 
                                       value="<%=Utility.sanitize(ck.getDe_tipologia_clienti())%>"
                                       readonly="readonly"/>
                            </div>
                        </div>
                        <!-- pay out -->
                        <div class="col-md-6">
                            <div class="mt-element-list">
                                <div class="mt-list-head list-news font-white bg-<%=color%>">
                                    <div class="list-head-title-container">
                                        <h3 class="list-title pull-left"><i class="fa <%=Constant.iconcur%>"></i></h3>
                                        <h3 class="list-title"> Pay <%=inout%> <b>
                                                <span class="pull-right" id="payout0"><%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getPay()))%></span></b>
                                        </h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <!-- total -->
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Total</label>
                                <div class="input-icon">
                                    <i class="fa <%=Constant.iconcur%> <%=font%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getTotal()))%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- fix -->
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Fix</label>
                                <div class="input-icon">
                                    <i class="fa <%=Constant.iconcur%> <%=font%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getFix()))%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- com -->
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>Com</label>
                                <div class="input-icon">
                                    <i class="fa <%=Constant.iconcur%> <%=font%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getCom()))%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- round -->
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>Round</label>
                                <div class="input-icon">
                                    <i class="fa <%=Constant.iconcur%> <%=font%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getRound()))%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- commission -->
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>Commission</label>
                                <div class="input-icon">
                                    <i class="fa <%=Constant.iconcur%> <%=font%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getCommission()))%>" /> 
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <!-- note -->
                        <div class="col-md-12">
                            <div class="form-group">
                                <label><i class="fa fa-sticky-note-o <%=font%>"></i> Note</label>
                                <input type="text" class="form-control" 
                                       readonly="readonly" value="<%=Utility.sanitize(StringEscapeUtils.unescapeHtml4(tra.getNote()))%>" />
                            </div>
                        </div>

                        <%
                            if (showagency) {
                                Agency ag = Engine.get_agency(tra.getAgency_cod(), request.getParameter("type"));
                        %>
                        <div class="clearfix"></div>
                        <!-- agen -->
                        <div class="col-md-12">
                            <div class="md-checkbox">
                                <input type="checkbox" id="agroy" name="agroy" class="md-checkbox" checked> 
                                <label for="agroy">
                                    <span></span>
                                    <span class="check"></span>
                                    <span class="box"></span> Agency Royalty
                                </label>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Agency</label>
                                <input type="text" class="form-control" 
                                       readonly="readonly"
                                       value="<%=Utility.sanitize(ag.getAgenzia()) + " - " + Utility.sanitize(ag.getDe_agenzia())%>" />
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <%}%>

                        <%
                            if (tra.getIntbook().equals("1")) {
                                ArrayList<String[]> array_intbook = Engine.list_internetbooking();
                                String[] internetbooking_ch = Engine.internetbooking_ch(tra.getCod());

                        %>
                        <div class="clearfix"></div>
                        <!-- Internet Booking-->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-3">
                                    <div class="md-checkbox">
                                        <input type="checkbox" id="newintbook0" name="newintbook0" class="md-checkbox" checked readonly
                                               onclick="return false;"> 
                                        <label for="newintbook0">
                                            <span></span>
                                            <span class="check"></span>
                                            <span class="box"></span> Internet Booking
                                        </label>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Channel</label>
                                        <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Utility.formatAL(internetbooking_ch[0], array_intbook, 1))%>"/>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Code</label>
                                        <input type="text" class="form-control" disabled value="<%=Utility.sanitize(internetbooking_ch[1])%>"/> 
                                    </div>
                                </div>
                            </div>    
                        </div>    

                        <%}%>

                        <%
                            if (tra.getIntbook_type().equals("1")) {
                                ArrayList<NC_causal> array_nc_causal = Engine.list_nc_causal_sell(request.getParameter("type"));
                                NC_causal nc1 = Engine.getNC_causal(array_nc_causal, tra.getIntbook_1_tf(), null);
                                NC_causal nc2 = Engine.getNC_causal(array_nc_causal, tra.getIntbook_2_tf(), null);
                                NC_causal nc3 = Engine.getNC_causal(array_nc_causal, tra.getIntbook_3_tf(), null);

                                String v1 = "-";
                                if (nc1 != null) {
                                    v1 = tra.getIntbook_1_tf() + " - " + nc1.getDe_causale_nc();
                                }

                                String v2 = "-";
                                if (nc2 != null) {
                                    v2 = tra.getIntbook_2_tf() + " - " + nc2.getDe_causale_nc();
                                }
                                String v3 = "-";
                                if (nc3 != null) {
                                    v3 = tra.getIntbook_3_tf() + " - " + nc3.getDe_causale_nc();
                                }


                        %>
                        <div class="clearfix"></div>
                        <!-- tax free no change -->
                        <div class="col-md-12">
                            <div class="md-checkbox">
                                <input type="checkbox" id="tfnc0" name="tfnc0" class="md-checkbox" checked> 
                                <label for="tfnc0">
                                    <span></span>
                                    <span class="check"></span>
                                    <span class="box"></span> Tax Free No-Change
                                </label>
                            </div>
                        </div>
                        <div>
                            <div class="clearfix"></div>
                            <p class='ab'></p>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Tax Free #1</label>
                                    <input type="text" class="form-control" 
                                           readonly="readonly" value="<%=Utility.sanitize(v1)%>" />
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Mod. #1</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(tra.getIntbook_1_mod()), 0))%>" />
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Value #1</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_1_val()))%>" />
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Tax Free #2</label>
                                    <input type="text" class="form-control" 
                                           readonly="readonly" value="<%=Utility.sanitize(v2)%>" />
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Mod. #2</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(tra.getIntbook_2_mod()), 0))%>" />
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Value #2</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_2_val()))%>" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group has-success">
                                    <label class="control-label">Maccorp</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_mac()))%>" />
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Tax Free #3</label>
                                    <input type="text" class="form-control" 
                                           readonly="readonly" value="<%=Utility.sanitize(v3)%>" />
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Mod. #3</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(tra.getIntbook_3_mod()), 0))%>" />
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Value #3</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_3_val()))%>" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group has-warning">
                                    <label class="control-label">Customer</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_cli()))%>" />
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <%ArrayList<NC_transaction> linc = Engine.list_nctransactionfromchange(tra.getCod(), request.getParameter("type"));

                                for (int c = 0; c < linc.size(); c++) {%>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label class="control-label bold">ID Transaction No Change</label>
                                    <input type="text" class="form-control" 
                                           readonly="readonly" value="<%=Utility.sanitize(linc.get(c).getId())%>" />
                                </div>
                            </div>  
                            <%}
                            %>


                            <p class='ab'></p>
                        </div>
                        <%}%>
                        <div class="clearfix"></div>

                        <p class='ab'></p>

                        <div class="col-md-12">
                            <div class="portlet box <%=color%>">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-money"></i>
                                        <span class="caption-subject">Figures </span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <input type="hidden" name="index_t1" id="index_t1" value="1"/>
                                            <%if (!Utility.isfirefox(request)) {%>
                                            <table class="table table-responsive table-bordered table-scrollable raftable" id="sample_0">
                                                <%} else {%>
                                                <table class="table table-responsive table-bordered table-scrollable raftablefirefox" id="sample_0">
                                                    <%}%>

                                                    <%if (tra.getTipotr().equals("B")) {%>
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow inputright" style="width:5px;">#</th>
                                                            <th class="tabnow" style="width:120px;">Kind <%
                                                                if (modificato_BUY) {%>
                                                                <button class='btn btn-sm white popovers' ata-container='body' data-trigger='hover' data-placement='right' 
                                                                        data-content='View Change Local Figures' type="button" data-toggle="modal" data-target="#largelock">
                                                                    <i class='fa fa-edit'></i></button>
                                                                    <%}
                                                                    %></th>
                                                            <th class="tabnow" style="width:100px;">POS</th>
                                                            <th class="tabnow" style="width:80px;">CC Number</th>
                                                            <th class="tabnow" style="width:200px;">Figures</th>
                                                            <th class="tabnow" style="width:130px;">Quantity</th>
                                                            <th class="tabnow" style="width:160px;">Rate</th>
                                                            <th class="tabnow" style="width:60px;">% Com.</th>
                                                            <th class="tabnow" style="width:80px;">% Tot</th>
                                                            <th class="tabnow" style="width:60px;">Fx Com.</th>
                                                            <th class="tabnow" style="width:120px;">Tot Com.</th>
                                                            <th class="tabnow" style="width:140px;">Net</th>
                                                                <%if (Constant.newpread) {%>
                                                            <th class="tabnow" style="width:140px;">Spread</th>
                                                                <%}%>
                                                            <th class="tabnow" style="width:100px;">Total</th>
                                                            <th class="tabnow" style="width:190px;">Kind Fix Com.</th>
                                                            <th class="tabnow" style="width:180px;">Low Com. Justify</th>
                                                            <th class="tabnow" style="width:50px;">BB</th>
                                                            <th class="tabnow" style="width:200px;">Fidelity Code</th>
                                                            <th class="tabnow" style="width:50px;">SB</th>

                                                        </tr>
                                                    </thead>
                                                    <%} else {%>
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow inputright" style="width:5px;">#</th>
                                                            <th class="tabnow" style="width:120px;">Kind</th>
                                                            <th class="tabnow" style="width:200px;">Figures</th>
                                                            <th class="tabnow" style="width:130px;">Quantity</th>
                                                            <th class="tabnow" style="width:160px;">Rate</th>
                                                            <th class="tabnow" style="width:60px;">% Com.</th>
                                                            <th class="tabnow" style="width:80px;">% Tot</th>
                                                            <th class="tabnow" style="width:60px;">Fx Com.</th>
                                                            <th class="tabnow" style="width:120px;">Tot Com.</th>
                                                            <th class="tabnow" style="width:140px;">Net</th>
                                                            <th class="tabnow" style="width:140px;">Spread</th>
                                                            <th class="tabnow" style="width:100px;">Total</th>
                                                            <th class="tabnow" style="width:190px;">Kind Fix Com.</th>
                                                            <th class="tabnow" style="width:180px;">Low Com. Justify</th>
                                                            <th class="tabnow" style="width:50px;">BB</th>
                                                            <th class="tabnow" style="width:50px;">SB</th>
                                                            <th class="tabnow" style="width:200px;">Fidelity Code</th>
                                                        </tr>
                                                    </thead>
                                                    <%}%>

                                                    <tbody>
                                                        <%
                                                            ArrayList<Ch_transaction_value> listvalue = Engine.query_transaction_value(cod, request.getParameter("type"));
                                                            for (int i = 0; i < listvalue.size(); i++) {
                                                                Ch_transaction_value val = listvalue.get(i);
                                                                Figures fi1 = Engine.get_figures(val.getSupporto(), tra.getFiliale(), request.getParameter("type"));
                                                                Currency cu1 = Engine.getCurrency(val.getValuta());

                                                                String underminjust = Utility.formatAL(val.getLow_com_ju(), array_undermincommjustify, 1);
                                                                String kindcom = Utility.formatAL(val.getKind_fix_comm(), array_kindcommissionefissa, 1);
                                                                //String bbck = "";
                                                                //if (val.getBb().equals("Y")) {
                                                                //    bbck = "checked";
                                                                //}

                                                                String bb_status = "N";
                                                                String sb_status = "N";

                                                                if (tra.getTipotr().equals("B")) {
                                                                    String pos = "";
                                                                    String ccnumb = "";
                                                                    if (!val.getPos().equals("-") && !val.getPos().equals("N")) {
                                                                        pos = Utility.formatAL(val.getPos(), array_credit_card, 1);
                                                                        ccnumb = val.getPosnum();
                                                                    }

                                                                    if (tra.getBb().equals("1")) {
                                                                        if (val.getBb().equals("Y") || val.getBb().equals("F")) {
                                                                            bb_status = val.getBb();
                                                                        }
                                                                    }
                                                                    if (tra.getBb().equals("3") || tra.getBb().equals("4")) {
                                                                        if (val.getBb().equals("Y") || val.getBb().equals("F")) {
                                                                            sb_status = val.getBb();
                                                                        }
                                                                    }

                                                        %>
                                                        <tr>
                                                            <td class="tabnow" valign="top"><%=i + 1%></td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(fi1.getDe_supporto())%>" />
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(pos)%>" />
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(ccnumb)%>" />
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(cu1.getCode()) + " - " + Utility.sanitize(cu1.getDescrizione())%>" />
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getQuantita()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getRate()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getCom_perc()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getCom_perc_tot()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getFx_com()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getTot_com()))%>"/> 
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getNet()))%>"/> 
                                                            </td>

                                                            <%if (Constant.newpread) {%>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getSpread()))%>"/> 
                                                            </td>
                                                            <%}%>

                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getTotal()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(kindcom)%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(underminjust)%>"/> 
                                                            <td class="tabnow"> 
                                                                <input type="text" class="form-control" 
                                                                       disabled="disabled" value="<%=Ch_transaction.formatSTATUS_BBSB(bb_status)%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <div class="input-icon right">
                                                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(val.getBb_fidcode())%>"/> 
                                                                </div>
                                                            </td>
                                                            <td class="tabnow"> 
                                                                <input type="text" class="form-control" 
                                                                       disabled="disabled" value="<%=Ch_transaction.formatSTATUS_BBSB(sb_status)%>"/> 
                                                            </td>

                                                        </tr>
                                                        <%} else {
                                                            if (tra.getBb().equals("1") || tra.getBb().equals("2")) {
                                                                if (val.getBb().equals("Y") || val.getBb().equals("F")) {
                                                                    bb_status = val.getBb();
                                                                }
                                                            }
                                                            if (tra.getBb().equals("3") || tra.getBb().equals("4")) {
                                                                if (val.getBb().equals("Y") || val.getBb().equals("F")) {
                                                                    sb_status = val.getBb();
                                                                }
                                                            }

                                                            if (tra.getIntbook().equals("1")) {
                                                                underminjust = "Internet Booking";
                                                                kindcom = "Internet Booking";
                                                            }
                                                        %>
                                                        <tr>
                                                            <td class="tabnow" valign="top"><%=i + 1%></td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(fi1.getDe_supporto())%>" />
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(cu1.getCode()) + " - " + cu1.getDescrizione())%>" />
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getQuantita()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getRate()))%>"/> 
                                                            </td>

                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getCom_perc()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getCom_perc_tot()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getFx_com()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getTot_com()))%>"/> 
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getNet()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getSpread()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getTotal()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(kindcom)%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(underminjust)%>"/> 
                                                            <td class="tabnow"> 
                                                                <input type="text" class="form-control" 
                                                                       disabled="disabled" value="<%=Ch_transaction.formatSTATUS_BBSB(bb_status)%>"/> 
                                                            </td>
                                                            <td class="tabnow"> 
                                                                <input type="text" class="form-control" 
                                                                       disabled="disabled" value="<%=Ch_transaction.formatSTATUS_BBSB(sb_status)%>"/> 
                                                            </td>
                                                            <td class="tabnow"> 
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(val.getBb_fidcode())%>"/> 
                                                            </td>
                                                        </tr>
                                                        <%}
                                                            }%>
                                                    </tbody>
                                                </table>
                                        </div>
                                        <div class="col-md-4">
                                        </div>
                                        <div class="col-md-4"></div>
                                        <h4 class="col-md-4" style="text-align: right;">Net Total <b id="totalnet"><%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getPay()))%></b></h4>
                                            <%if (tra.getTipotr().equals("S") || Constant.newpread) {%>
                                        <div class="col-md-4"></div>
                                        <div class="col-md-4"></div>
                                        <h4 class="col-md-4" style="text-align: right;">Spread Total <b id="totalspread"><%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getSpread_total()))%></b></h4>
                                            <%}%>
                                        <div class="col-md-4"></div><div class="col-md-4"></div>
                                        <h4 class="col-md-4" style="text-align: right;">Total <b id="total00"><%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getTotal()))%></b></h4>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- customer information pf -->
                        <%if (!ck.getTipologia_clienti().equals("003")) {
                                boolean view = true;
                                Client cl = Engine.query_Client_transaction(tra.getCod(), tra.getCl_cod(), request.getParameter("type"));
                                String loy = Engine.query_LOY_transaction(tra.getCod(), request.getParameter("type"), fil);
                                if (!Constant.is_IT) {
                                    if (cl.getCode().equals("---") && cl.getRepceca() == null) {
                                        view = false;
                                    }
                                }

                                if (view) {
                                    if (tra.getIntbook().equals("1")) {
                                        String[] internetbooking_ch = Engine.internetbooking_ch(tra.getCod());
                                        Booking bo = Engine.get_prenot(internetbooking_ch[1]);
                                        if (bo != null) {%>
                        <div class="clearfix"></div>
                        <p class='ab'></p>
                        <div class="col-md-6">
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
                        <div class="col-md-6">
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
                        <%}
                            }
                        %>

                        <div class="col-md-12">
                            <div class="portlet box <%=color%>">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-user"></i>
                                        <span class="caption-subject">KYC - Know Your Customer</span>
                                    </div>
                                </div>
                                <div class="portlet-body">

                                    <%if (loy != null) {
                                            ArrayList<NC_transaction> li_wc_ti = Engine.list_nctransaction_wc_ti(tra);

                                            boolean wk = false;
                                            String id_wk = "";
                                            boolean tic = false;
                                            String id_tic = "";

                                            for (int v = 0; v < li_wc_ti.size(); v++) {
                                                if (li_wc_ti.get(v).getGruppo_nc().equalsIgnoreCase("WELCOMEKIT") || li_wc_ti.get(v).getGruppo_nc().equalsIgnoreCase("WELKITSELL")) {
                                                    wk = true;
                                                    id_wk = li_wc_ti.get(v).getId();
                                                } else if (li_wc_ti.get(v).getGruppo_nc().equalsIgnoreCase("TOPITACARD")) {
                                                    tic = true;
                                                    id_tic = li_wc_ti.get(v).getId();
                                                }
                                            }

                                    %>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label><i class="fa fa-credit-card <%=font%>"></i> Loyalty Code</label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(loy)%>"/> 
                                            </div>
                                        </div> 


                                        <div class="col-md-4">
                                            <label>&nbsp;</label>
                                            <div class="md-checkbox">
                                                <input type="checkbox" id="welc" name="welc" 
                                                       class="md-checkbox" 
                                                       onclick="return false;" <%if (wk) {%> checked <%}%>> 
                                                <label for="welc">
                                                    <span></span>
                                                    <span class="check"></span>
                                                    <span class="box"></span> Welcome Kit <%if (!id_wk.equals("")) {%> (ID Transaction No Change: <%=Utility.sanitize(id_wk)%>)<%}%>
                                                </label>
                                            </div>
                                        </div>


                                        <div class="col-md-4">
                                            <label>&nbsp;</label>
                                            <div class="md-checkbox">
                                                <input type="checkbox" id="fidel" name="fidel" 
                                                       class="md-checkbox"
                                                       onclick="return false;" <%if (tic) {%> checked <%}%>> 
                                                <label for="fidel">
                                                    <span></span>
                                                    <span class="check"></span>
                                                    <span class="box"></span> Top Italy Card <%if (!id_tic.equals("")) {%> (ID Transaction No Change: <%=Utility.sanitize(id_tic)%>)<%}%>
                                                </label>
                                            </div>
                                        </div>        


                                    </div>
                                    <hr>
                                    <%}%>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label><i class="fa fa-user <%=font%>"></i> Surname</label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getCognome().toUpperCase())%>"/> 
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label><i class="fa fa-user <%=font%>"></i> Sex </label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Engine.formatSex(cl.getSesso()).toUpperCase())%>"/> 
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label><i class="fa fa-user <%=font%>"></i> Name </label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getNome().toUpperCase())%>"/> 
                                            </div>
                                        </div>
                                        <%if (Constant.is_IT) {%>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label><i class="fa fa-credit-card <%=font%>"></i> Tax Code</label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getCodfisc().toUpperCase())%>"/> 
                                            </div>
                                        </div>
                                        <%}%>
                                        <%if (Constant.is_CZ) {%>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label><i class="fa fa-credit-card <%=font%>"></i> Personal No. </label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getRepceca().getHeavy_pno1().toUpperCase())%>"/>
                                            </div>
                                        </div>
                                    </div>
                                    <%}%>
                                    <!--CHZ-->
                                    <%

                                        String country[] = Engine.get_country(cl.getNazione());

                                        String city[] = Engine.getCity_apm(cl.getCitta());
                                        String distr[] = Engine.get_district(cl.getProvincia());
                                        String country_nas[] = Engine.get_country(cl.getNazione_nascita());
                                        String city_nas[] = Engine.getCity_apm(cl.getCitta_nascita());
                                        String distr_nas[] = Engine.get_district(cl.getProvincia_nascita());

                                        String countrycz[] = null;
                                        String countrycziss[] = null;
                                        String countryczpep[] = null;
                                        if (cl.getRepceca() != null) {
                                            countrycz = Engine.get_country(cl.getRepceca().getHeavy_cz_country());
                                            countrycziss = Engine.get_country(cl.getRepceca().getHeavy_cz_issuingcountry());
                                            countryczpep = Engine.get_country(cl.getRepceca().getPep_country());
                                        }


                                    %>
                                    <div class="clearfix"></div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-flag <%=font%>"></i> Country</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(country[1].toUpperCase())%>"/> 
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-home <%=font%>"></i> City</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(city[1])%>"/> 
                                        </div>
                                    </div>
                                    <%if (countrycz != null) {%>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-flag <%=font%>"></i> Country of Citizenship</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(countrycz[1].toUpperCase())%>"/> 
                                        </div>
                                    </div>
                                    <%}%> 

                                    <%if (Constant.is_IT) {%>
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <label><i class="fa fa-user <%=font%>"></i> Client is PEP</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getPep())%>"/> 
                                        </div>
                                    </div>
                                    <%}%>

                                    <div class="clearfix"></div>
                                    <div class="col-md-8">
                                        <div class="form-group">
                                            <label><i class="fa fa-home <%=font%>"></i> Address </label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getIndirizzo().toUpperCase())%>"/> 
                                        </div>
                                    </div>

                                    <div class="clearfix"></div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-home <%=font%>"></i> Zip Code </label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getCap().toUpperCase())%>"/> 
                                        </div>
                                    </div>
                                    <%if (Constant.is_IT) {%>    
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-home <%=font%>"></i> District</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(distr[1])%>"/> 
                                        </div>
                                    </div>
                                    <%}%>
                                    <div class="clearfix"></div>
                                    <div class="col-md-12">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h3 class="panel-title">Place of Birth</h3>
                                            </div>
                                            <div class="panel-body"> <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-home <%=font%>"></i> City</label>

                                                        <input type="text" class="form-control" disabled value="<%=Utility.sanitize(city_nas[1])%>"/> 

                                                    </div>
                                                </div>
                                                <%if (Constant.is_IT) {%>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-home <%=font%>"></i> District</label>

                                                        <input type="text" class="form-control" disabled value="<%=Utility.sanitize(distr_nas[1])%>"/> 
                                                    </div>
                                                </div>
                                                <%}%>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-flag <%=font%>"></i> Country</label>
                                                        <input type="text" class="form-control" disabled value="<%=Utility.sanitize(country_nas[1].toUpperCase())%>"/> 
                                                    </div>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-calendar <%=font%>"></i> Date <span class='font-red'>*</span></label>
                                                        <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getDt_nascita())%>"/>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-credit-card <%=font%>"></i> Identification Card</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Utility.formatAL(cl.getTipo_documento(), array_identificationCard, 1))%>"/>

                                        </div>
                                    </div>
                                    <%if (countrycziss != null) {%>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-flag <%=font%>"></i> Issuing Country</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(countrycziss[1].toUpperCase())%>"/> 
                                        </div>
                                    </div>
                                    <%}%>
                                    <div class="clearfix"></div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-credit-card <%=font%>"></i> Number of Id. Card</label>

                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getNumero_documento())%>"/>

                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-calendar <%=font%>"></i> Issue Date</label>

                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getDt_rilascio_documento())%>"/>

                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-calendar <%=font%>"></i> Expiration Date</label>

                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getDt_scadenza_documento())%>"/>

                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-credit-card <%=font%>"></i> Issued By</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getRilasciato_da_documento())%>"/>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-credit-card <%=font%>"></i> Place of Issue</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getLuogo_rilascio_documento())%>"/>                                            </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-envelope <%=font%>"></i> Email</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getEmail())%>"/>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label><i class="fa fa-phone <%=font%>"></i> Phone Number</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getTelefono())%>"/>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <%if (!Constant.is_IT) {%>
                                    <div class="clearfix"></div>
                                    <div class="col-md-12">
                                        <hr>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><i class="fa fa-user <%=font%>"></i> International Sanctions</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getRepceca().getHeavy_sanctions())%>"/>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><i class="fa fa-user <%=font%>"></i> Client is PEP</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Client_CZ.formatYN(cl.getRepceca().getHeavy_pep()))%>"/>
                                        </div>
                                    </div>
                                    <%
                                        if (cl.getRepceca().getHeavy_pep().equals("Y")) {
                                    %>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><i class="fa fa-user <%=font%>"></i> Position of the PEP</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getRepceca().getPep_position())%>"/>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><i class="fa fa-user <%=font%>"></i> Country PEP empowered</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(countryczpep[1].toUpperCase())%>"/>
                                        </div>
                                    </div>    

                                    <%}%>
                                    <div class="clearfix"></div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><i class="fa fa-user <%=font%>"></i> Transaction Reason</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getRepceca().getHeavy_transactionre())%>"/>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><i class="fa fa-user <%=font%>"></i> Money Source</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getRepceca().getHeavy_moneysource())%>"/>
                                        </div>
                                    </div>
                                    <div class="col-md-3">
                                        <div class="form-group">
                                            <label><i class="fa fa-user <%=font%>"></i> Customer's Occupation</label>
                                            <input type="text" class="form-control" disabled value="<%=Utility.sanitize(cl.getRepceca().getOccupation())%>"/>
                                        </div>
                                    </div>
                                    <%if (cl.getRepceca().getOp_sos().equalsIgnoreCase("Y")) {%>
                                    <div class="col-md-3 ">
                                        <div class="form-group">
                                            <label>&nbsp;</label>
                                            <div class="md-checkbox">
                                                <input type="checkbox" id="op_sos" name="op_sos" checked onclick="return false;"> 
                                                <label for="op_sos">
                                                    <span></span>
                                                    <span class="check"></span>
                                                    <span class="box"></span> Suspicious Operation
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <%}%>
                                    <div class="clearfix"></div>
                                    <%}%>
                                </div>
                            </div>
                        </div>

                        <%}%>
                        <%} else {
                            Company c = Engine.get_Companylist(array_listCompany, tra.getCl_cod());
                            String var = "";
                            if (c != null) {
                                var = c.getRagione_sociale() + " - " + c.getCognome() + " " + c.getNome();
                            } else {
                                var = tra.getCl_cod();
                            }
                        %>
                        <div class="col-md-12" id="custinfo_2">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-user"></i>
                                        <span class="caption-subject">KYC - Know Your Customer</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Company</label><p class='ab'></p>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(var)%>"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%}
                            if (tra.getTipotr().equals("S")) {
                        %>                   
                        <div class="col-md-12">
                            <div class="portlet box green-sharp">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-money"></i>
                                        <span class="caption-subject">Payment Mode</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Local Figures <%
                                                    if (modificato_SELL) {%>
                                                    <a class='btn btn-sm white popovers' ata-container='body' data-trigger='hover' data-placement='top' 
                                                       data-content='View Change Local Figures' type="button" data-toggle="modal" data-target="#largelock">
                                                        <i class='fa fa-edit'></i></a>
                                                        <%}
                                                        %></label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Utility.formatAL(tra.getLocalfigures(), array_kind_pay, 1))%>"/>
                                            </div>
                                        </div>
                                        <%if (tra.getLocalfigures().equals("08")) {%>   
                                        <div class="col-md-3" id="ban_0">
                                            <div class="form-group">
                                                <label>Bank</label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Utility.formatAL(tra.getPos(), array_bankacc, 1))%>"/>
                                            </div>
                                        </div>
                                        <%}%>
                                        <%if (tra.getLocalfigures().equals("06") || tra.getLocalfigures().equals("07")) {%>   
                                        <div class="col-md-3" id="pos_0">
                                            <div class="form-group">
                                                <label>Pos</label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Utility.formatAL(tra.getPos(), array_credit_card, 1))%>"/>
                                            </div>
                                        </div>
                                        <%}%>
                                        <%if (tra.getLocalfigures().equals("06")) {%>   
                                        <!-- pos number -->
                                        <div class="col-md-3" id="pos_1">
                                            <div class="form-group">
                                                <label>CC Number</label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(tra.getCredccard_number())%>"/>
                                            </div>
                                        </div>
                                        <%}%>
                                    </div>
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
            <!-- END FOOTER -->
            <!-- [if lt IE 9]>
            <script src="../assets/global/plugins/respond.min.js"></script>
            <script src="../assets/global/plugins/excanvas.min.js"></script> 
            <![endif] -->
            <!-- BEGIN CORE PLUGINS -->
            <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
            <!--<script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>-->
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
            <script src="assets/soop/js/input-mask_buy.js" type="text/javascript"></script>
            <!-- END THEME LAYOUT SCRIPTS -->
            <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
            <!-- BEGIN THEME GLOBAL SCRIPTS -->
            <%if (tra.getTipotr()
                        .equals("B")) {%>
            <script type="text/javascript">
                                                    jQuery(document).ready(function () {
                                                        var dt = function () {
                                                            var e = $("#sample_0");
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
                                                                scrollY: false,
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
                                                                    {orderable: !1, targets: [9]},
                                                                    {orderable: !1, targets: [10]},
                                                                    {orderable: !1, targets: [11]},
                                                                    {orderable: !1, targets: [12]},
                                                                    {orderable: !1, targets: [13]},
                                                                    {orderable: !1, targets: [14]},
                                                                    {orderable: !1, targets: [15]},
                                                                    {orderable: !1, targets: [16]},
                                                                    {orderable: !1, targets: [17]}
                                                                ],
                                                                order: [],
                                                                dom: "<t>"
                                                            });
                                                        };
                                                        jQuery().dataTable && dt();
                                                    }
                                                    );
            </script>
            <%} else {%>
            <script type="text/javascript">
                jQuery(document).ready(function () {
                    var dt = function () {
                        var e = $("#sample_0");
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
                            initComplete: function (settings, json) {
                                $('.popovers').popover();
                            },
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
                                {orderable: !1, targets: [10]},
                                {orderable: !1, targets: [11]},
                                {orderable: !1, targets: [12]},
                                {orderable: !1, targets: [13]},
                                {orderable: !1, targets: [14]},
                                {orderable: !1, targets: [15]},
                                {orderable: !1, targets: [16]}
                            ],
                            order: [],
                            dom: "<t>"
                        });
                    };
                    jQuery().dataTable && dt();
                }
                );


            </script>
            <%}%>

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