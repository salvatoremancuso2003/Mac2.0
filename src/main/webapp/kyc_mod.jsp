<%@page import="rc.so.entity.Document"%>
<%@page import="rc.so.entity.Ch_transaction_doc"%>
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
        <title>Mac2.0 - Edit KYC</title>
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
        
        <script type="text/javascript" src="assets/soop/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>
        
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>



        <%
            List_new ln = new List_new("B", session);

            ArrayList<Company> array_listCompany = ln.getArray_listCompany();
            ArrayList<String[]> array_identificationCard = ln.getArray_identificationCard();
            ArrayList<String[]> array_credit_card = ln.getArray_credit_card();
            ArrayList<String[]> array_undermincommjustify = ln.getArray_undermincommjustify();
            ArrayList<String[]> array_kindcommissionefissa = ln.getArray_kindcommissionefissa();


        %>


        <script type="text/javascript">
            function RemoveAccents(str) {
                var accents = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
                var accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";
                str = str.split('');
                var strLen = str.length;
                var i, x;
                for (i = 0; i < strLen; i++) {
                    if ((x = accents.indexOf(str[i])) !== -1) {
                        str[i] = accentsOut[x] + "'";
                    }
                }
                return str.join('');
            }
            function modificaOAMSurname(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase());
                fieldNameSurnameNEW(field.id);
            }
            function modificaOAM(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase());
                fieldNOSPecial_2(field.id);
            }

            function fieldNameSurnameNEW(fieldid) {
                var stringToReplace = document.getElementById(fieldid).value;
                var specialChars = "~`!#$%^&*+=-[]();,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°èéòàù§*ç@|!£$%&/()=?^€ì";
                for (var i = 0; i < specialChars.length; i++) {
                    stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
                }
                stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
                document.getElementById(fieldid).value = stringToReplace;
            }
            function loadform() {
                $.fn.extend({
                    trackChanges: function () {
                        $(":input", this).change(function () {
                            $(this.form).data("changed", true);
                        });
                    }
                    ,
                    isChanged: function () {
                        return this.data("changed");
                    }
                });
                $("#formmod").trackChanges();
            }

            function verform() {
                if ($("#formmod").isChanged()) {
                    return true;
                } else {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "Warning! No changes detected.";
                    return false;
                }
            }


        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return loadform();">
        <!--- MODAL PASSWORD --->

        <!-- BEGIN HEADER -->
        <!-- ERROR MODAL -->
        <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
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

                String cod = request.getParameter("cod");

                Ch_transaction tra = Engine.query_transaction_ch(cod);

                Branch br1 = Engine.get_branch(tra.getFiliale());

                Openclose oc = Engine.query_oc(tra.getId_open_till());
                Till t1 = Engine.get_single_Till(oc.getFiliale(), oc.getTill());
                CustomerKind ck = Engine.get_customerKind(tra.getTipocliente());

                Codici_sblocco cs1 = Engine.getCod_tr(cod, "01");
                String resp1 = "";
                if (cs1 != null) {
                    resp1 = Engine.getRespCod(cs1.getCodice());
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
                            <h3 class="page-title">Transaction <%=Utility.sanitize(tra.formatType(tra.getTipotr()))%> <small><b>Edit KYC</b> </small>
                                <a class="" href="Fileview?type=transactionpdf&cod=<%=Utility.sanitize(tra.getCod())%>" title="Export PDF" target="_blank">  
                                    <img src="assets/soop/img/Adobe-PDF-Document-icon.png" alt="Export PDF"/>
                                </a> 
                            </h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                            <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>

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
                                <%=Utility.sanitize(tra.formatStatus(tra.getDel_fg()))%>
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

                        <% if (cs1 != null) {%>
                        <div class="clearfix"></div>   
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Unlock Code | Type</label>
                                <input type="text" class="form-control" disabled="disabled" 
                                       value="<%=Utility.sanitize(cs1.getCodice()) + " | " + Utility.sanitize(Codici_sblocco.format_ty_util(cs1.getTy_util()))%>"/>
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
                                    <i class="fa fa-check <%=Utility.sanitize(font)%>"></i>
                                    <input type="text" class="form-control uppercase" readonly="readonly"
                                           value="<%=Utility.sanitize(tra.formatType(tra.getTipotr()).toUpperCase())%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- ID OPEN -->
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id open till</label>
                                <div class="input-icon">
                                    <i class="fa fa-bookmark <%=Utility.sanitize(font)%>"></i>
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
                                <div class="mt-list-head list-news font-white bg-<%=Utility.sanitize(color)%>">
                                    <div class="list-head-title-container">
                                        <h3 class="list-title pull-left"><i class="fa <%=Utility.sanitize(Constant.iconcur)%>"></i></h3>
                                        <h3 class="list-title"> Pay <%=Utility.sanitize(inout)%> <b>
                                                <span class="pull-right" id="payout0"><%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getPay()))%></span></b>
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
                                    <i class="fa <%=Utility.sanitize(Constant.iconcur)%> <%=Utility.sanitize(font)%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getTotal()))%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- fix -->
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Fix</label>
                                <div class="input-icon">
                                    <i class="fa <%=Utility.sanitize(Constant.iconcur)%> <%=Utility.sanitize(font)%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getFix()))%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- com -->
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>Com</label>
                                <div class="input-icon">
                                    <i class="fa <%=Utility.sanitize(Constant.iconcur)%> <%=Utility.sanitize(font)%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getCom())%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- round -->
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>Round</label>
                                <div class="input-icon">
                                    <i class="fa <%=Constant.iconcur%> <%=Utility.sanitize(font)%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(tra.getRound())%>" /> 
                                </div>
                            </div>
                        </div>
                        <!-- commission -->
                        <div class="col-md-2">
                            <div class="form-group">
                                <label>Commission</label>
                                <div class="input-icon">
                                    <i class="fa <%=Constant.iconcur%> <%=Utility.sanitize(font)%>"></i>
                                    <input type="text" class="form-control inputright inputbold" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getCommission()))%>" /> 
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <!-- note -->
                        <div class="col-md-12">
                            <div class="form-group">
                                <label><i class="fa fa-sticky-note-o <%=Utility.sanitize(font)%>"></i> Note</label>
                                <input type="text" class="form-control" 
                                       readonly="readonly" value="<%=Utility.sanitize(tra.getNote())%>" />
                            </div>
                        </div>

                        <%
                            if (showagency) {
                                Agency ag = Engine.get_agency(tra.getAgency_cod());
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

                        <%if (tra.getTipotr().equals("S")) {

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
                                        <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Utility.sanitize(Utility.formatAL(internetbooking_ch[0], array_intbook, 1))%>"/>
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

                        <%}

                            if (tra.getIntbook_type().equals("1")) {

                                ArrayList<NC_causal> array_nc_causal = Engine.list_nc_causal_sell();
                                NC_causal nc1 = Engine.getNC_causal(array_nc_causal, tra.getIntbook_1_tf(),null);
                                NC_causal nc2 = Engine.getNC_causal(array_nc_causal, tra.getIntbook_2_tf(),null);
                                NC_causal nc3 = Engine.getNC_causal(array_nc_causal, tra.getIntbook_3_tf(),null);

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
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(tra.getIntbook_1_mod()), 0)))%>" />
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Value #1</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_1_val())%>" />
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
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(tra.getIntbook_2_mod()), 0)))%>" />>
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Value #2</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_2_val()))%>" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group has-success">
                                    <label class="control-label">Maccorp</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_mac()))%>" />
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
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(tra.getIntbook_3_mod()), 0)))%>" />
                                </div>
                            </div>
                            <div class="col-md-3" id="intbook_2">
                                <div class="form-group">
                                    <label>Value #3</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_3_val()))%>" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group has-warning">
                                    <label class="control-label">Customer</label>
                                    <input type="text" class="form-control inputright" 
                                           readonly="readonly" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(tra.getIntbook_cli()))%>" />
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <%ArrayList<NC_transaction> linc = Engine.list_nctransactionfromchange(tra.getCod());
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



                        <%}
                            }%>
                        <div class="clearfix"></div>

                        <p class='ab'></p>

                        <div class="col-md-12">
                            <div class="portlet box <%=Utility.sanitize(color)%>">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-money"></i>
                                        <span class="caption-subject">Figures</span>
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
                                                            <th class="tabnow" style="width:120px;">Kind</th>
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
                                                            <th class="tabnow" style="width:100px;">Total</th>
                                                            <th class="tabnow" style="width:190px;">Kind Fix Com.</th>
                                                            <th class="tabnow" style="width:180px;">Low Com. Justify</th>
                                                            <th class="tabnow" style="width:10px;">BB</th>
                                                            <th class="tabnow" style="width:200px;">Fidelity Code</th>
                                                            <th class="tabnow" style="width:50px;">Reset</th>
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
                                                            <th class="tabnow" style="width:10px;">BB</th>
                                                            <th class="tabnow" style="width:50px;">Reset</th>
                                                        </tr>
                                                    </thead>
                                                    <%}%>

                                                    <tbody>
                                                        <%
                                                            ArrayList<Ch_transaction_value> listvalue = Engine.query_transaction_value(cod);
                                                            for (int i = 0; i < listvalue.size(); i++) {
                                                                Ch_transaction_value val = listvalue.get(i);
                                                                Figures fi1 = Engine.get_figures(val.getSupporto(), tra.getFiliale());
                                                                Currency cu1 = Engine.getCurrency(val.getValuta());

                                                                String underminjust = Utility.sanitize(Utility.formatAL(val.getLow_com_ju(), array_undermincommjustify, 1);
                                                                String kindcom = Utility.sanitize(Utility.formatAL(val.getKind_fix_comm(), array_kindcommissionefissa, 1);
                                                                String bbck = "";
                                                                if (val.getBb().equals("Y")) {
                                                                    bbck = "checked";
                                                                }
                                                                if (tra.getTipotr().equals("B")) {
                                                                    String pos = "";
                                                                    String ccnumb = "";
                                                                    if (!val.getPos().equals("-") && !val.getPos().equals("N")) {
                                                                        pos = Utility.sanitize(Utility.formatAL(val.getPos(), array_credit_card, 1);
                                                                        ccnumb = val.getPosnum();
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
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(val.getQuantita()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(val.getRate()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(val.getCom_perc()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(val.getCom_perc_tot()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(val.getFx_com()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(val.getTot_com()))%>"/> 
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(val.getNet()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getTotal())%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(kindcom)%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(underminjust)%>"/> 
                                                            <td> 
                                                                <div class="md-checkbox">
                                                                    <input type="checkbox" disabled="disabled" id="bb<%=i%>" name="bb<%=i%>" class="md-checkbox" <%=Utility.sanitize(bbck)%>> 
                                                                    <label for="bb<%=i%>">
                                                                        <span></span>
                                                                        <span class="check"></span>
                                                                        <span class="box"></span>
                                                                    </label>
                                                                </div>
                                                            </td>
                                                            <td class="tabnow">
                                                                <div class="input-icon right">
                                                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(val.getBb_fidcode())%>"/> 
                                                                </div>
                                                            </td>
                                                            <td>
                                                                &nbsp;
                                                            </td>
                                                        </tr>
                                                        <%} else {%>
                                                        <tr>
                                                            <td class="tabnow" valign="top"><%=i + 1%></td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(fi1.getDe_supporto())%>" />
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
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getSpread()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(val.getTotal()))%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=kindcom%>"/> 
                                                            </td>
                                                            <td class="tabnow">
                                                                <input type="text" class="form-control" disabled="disabled" value="<%=underminjust%>"/> 
                                                            <td> 
                                                                <div class="md-checkbox">
                                                                    <input type="checkbox" disabled="disabled" id="bb<%=i%>" name="bb<%=i%>" class="md-checkbox" <%=Utility.sanitize(bbck)%> disabled> 
                                                                    <label for="bb<%=i%>">
                                                                        <span></span>
                                                                        <span class="check"></span>
                                                                        <span class="box"></span>
                                                                    </label>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                &nbsp;
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
                                            <%if (tra.getTipotr().equals("S")) {%>
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
                        <%
                            ArrayList<String[]> array_kind_pay = Engine.kind_payment();
                            ArrayList<String[]> array_bankacc = Engine.list_bankAccount();
                            ArrayList<Ch_transaction_doc> doc = Engine.get_list_tr_doc(cod);

                        %>



                        <%if (tra.getTipotr().equals("S")) {
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
                                                <label>Local Figures</label>
                                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(Utility.sanitize(Utility.formatAL(tra.getLocalfigures(), array_kind_pay, 1))%>"/>
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
                        <!-- customer information pf -->
                        <%if (!ck.getTipologia_clienti().equals("003")) {

                                boolean havemodify = Engine.have_kyc_modified(tra.getCod());

                                Ch_transaction_doc di = Engine.formatfromlist_doc(doc, "_docrico");

                                String view = "class='btn btn-default green-jungle'"
                                        + " href='Download?type=view_doc_tr_reprint&tr=_docrico&cod=" + di.getCodice_documento() + "'";
                                Client cl = Engine.query_Client_transaction(tra.getCod(), tra.getCl_cod());

                        %>
                        <form method="post" action="Edit?type=edit_client" id="formmod" onsubmit="return verform();">
                            <input type="hidden" name="tr_cod" value="<%=Utility.sanitize(cod)%>"/>
                            <input type="hidden" name="cl_cod" value="<%=Utility.sanitize(cl.getCode())%>"/>
                            <div class="col-md-12">
                                <div class="portlet box red">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-user"></i>
                                            <span class="caption-subject">KYC - Know Your Customer - EDIT</span>
                                        </div>
                                    </div>
                                    <div class="portlet-body">

                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user <%=Utility.sanitize(font)%>"></i> Surname</label>
                                                    <input type="text" class="form-control" 
                                                           id="heavy_surname" name="heavy_surname" 
                                                           maxlength="30" 
                                                           value="<%=Utility.sanitize(cl.getCognome().toUpperCase())%>"
                                                           onkeypress="return keysub(this, event);" 
                                                           onkeyup="return modificaOAMSurname(this, event);"/> 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user <%=Utility.sanitize(font)%>"></i> Sex </label>

                                                    <select class="form-control select2" name="heavy_sex" id="heavy_sex" onkeypress="return keysub(this, event);">
                                                        <%
                                                            String selm = "";
                                                            String self = "";
                                                            String selo = "";
                                                            if (cl.getSesso().toUpperCase().equals("M")) {
                                                                selm = "selected";
                                                            } else if (cl.getSesso().toUpperCase().equals("F")) {
                                                                self = "selected";
                                                            } else {
                                                                selo = "selected";
                                                            }
                                                        %>
                                                        <option <%=Utility.sanitize(selm)%> value="M">Male</option>
                                                        <option <%=Utility.sanitize(self)%> value="F">Female</option>
                                                        <%if(Constant.is_UK){%>
                                                        <option <%=Utility.sanitize(selo)%> value="O">Other</option>
                                                        <%}%>
                                                        
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label>&nbsp;</label>
                                                    <div class="input-icon">
                                                        <a <%=Utility.sanitize(view)%> target="_blank"><i class="fa fa-file"></i> View Identity Document</a>
                                                        <a href="transaction_upldoc.jsp?mod=true&codtr=<%=Utility.sanitize(cod)%>&coddoc=_docrico" 
                                                           class='btn btn-default blue-hoki fancyBoxRafreload' ><i class="fa fa-upload"></i> Replace Identity Document</a>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user <%=Utility.sanitize(font)%>"></i> Name </label>
                                                    <input type="text" class="form-control" id="heavy_name" name="heavy_name"
                                                           maxlength="30"
                                                           value="<%=Utility.sanitize(cl.getNome().toUpperCase())%>"
                                                           onkeypress="return keysub(this, event);"
                                                           onkeyup="return modificaOAMSurname(this, event);"/>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card <%=Utility.sanitize(font)%>"></i> Tax Code</label>

                                                    <input type="text" class="form-control" id="heavy_codfisc" name="heavy_codfisc" maxlength="16"
                                                           value="<%=Utility.sanitize(cl.getCodfisc().toUpperCase())%>"
                                                           onkeypress="return keysub(this, event);" 
                                                           onkeyup="return fieldNOSPecial_1(this.id);"/> 
                                                </div>
                                            </div>
                                            <%if (havemodify) {%>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label>&nbsp;</label>
                                                    <div class="input-icon">
                                                        <a href="kyc_mod_list.jsp?cod=<%=Utility.sanitize(cod)%>" class="btn btn-default dark fancyBoxRaf"
                                                           ><i class="fa fa-history"></i> History Modify</a>
                                                    </div>
                                                </div>
                                            </div>            
                                            <%}%>

                                            <!--CHZ-->
                                            <%if (Constant.is_CZ) {%>
                                            <div class="col-md-2" id="czh1">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user <%=Utility.sanitize(font)%>"></i> Personal No.</label>
                                                    <input type="text" class="form-control" disabled value="CONTROLLARE"/> 
                                                </div>
                                            </div>
                                            <div class="col-md-2" id="czh2">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card <%=Utility.sanitize(font)%>"></i> Personal No. <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" disabled value="CONTROLLARE"/> 
                                                </div>
                                            </div>
                                            <%}
                                                ArrayList<String[]> country = Engine.country();
                                                ArrayList<String[]> district = Engine.district();

                                            %>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-flag <%=Utility.sanitize(font)%>"></i> Country</label>
                                                    <select class="form-control select2" id="heavy_country" 
                                                            name="heavy_country" onkeypress="return keysub(this, event);">
                                                        <%for (int i = 0; i < country.size(); i++) {
                                                                String sel = "";
                                                                if (country.get(i)[0].equals(cl.getNazione())) {
                                                                    sel = "selected";
                                                                }%>
                                                        <option <%=Utility.sanitize(sel)%> value="<%=Utility.sanitize(country.get(i)[0])%>"><%=Utility.sanitize(country.get(i)[1])%></option>
                                                        <%}%>      
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-home <%=Utility.sanitize(font)%>"></i> City</label>
                                                    <%if (!ck.getTipologia_clienti().equals("002")) {%>
                                                    <input class="form-control" type="text" 
                                                           name="heavy_city" maxlength="40"
                                                           value="<%=Utility.sanitize(cl.getCitta())%>"
                                                           onkeypress="return keysub(this, event);" 
                                                           onkeyup="return modificaOAMSurname(this, event);" />
                                                    <%} else {
                                                        ArrayList<String[]> city = Engine.city_Italy_APM();

                                                    %>
                                                    <div class="clearfix" id="heavy_city_div">
                                                        <select class="form-control select2" name="heavy_city" 
                                                                id="heavy_city"
                                                                onkeypress="return keysub(this, event);" style="width: 100%;">
                                                            <%for (int i = 0; i < city.size(); i++) {
                                                                    String sel = "";
                                                                    if (city.get(i)[0].equals(cl.getCitta())) {
                                                                        sel = "selected";
                                                                    }%>
                                                            <option <%=Utility.sanitize(sel)%> value="<%=Utility.sanitize(city.get(i)[0])%>"><%=Utility.sanitize(city.get(i)[1])%></option>
                                                            <%}%>     

                                                        </select>
                                                    </div>
                                                    <%}%>
                                                </div>
                                            </div>
                                            <%if (Constant.is_CZ) {%>
                                            <!--CHZ-->
                                            <div class="col-md-2" id="czh3">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user <%=Utility.sanitize(font)%>"></i> Sanctions</label>
                                                    <input type="text" class="form-control" disabled value="CONTROLLARE"/> 
                                                </div>
                                            </div>
                                            <div class="col-md-2" id="czh4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user <%=Utility.sanitize(font)%>"></i> Client is PEP</label>
                                                    <input type="text" class="form-control" disabled value="CONTROLLARE"/> 
                                                </div>
                                            </div>
                                            <%} else {

                                                String sels = "";
                                                String seln = "";

                                                if (cl.getPep().equals("YES")) {
                                                    sels = "selected";
                                                } else {
                                                    seln = "selected";
                                                }
                                            %>
                                            <div class="col-md-2">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user <%=Utility.sanitize(font)%>"></i> Client is PEP</label>
                                                    <select class="form-control select2" name="heavy_pepI" id="heavy_pepI" 
                                                            onkeypress="return keysub(this, event);">
                                                        <option <%=Utility.sanitize(seln)%> value="NO">No</option>
                                                        <option <%=Utility.sanitize(sels)%> value="YES">Yes</option>
                                                    </select>


                                                </div>
                                            </div>
                                            <%}%>

                                            <div class="clearfix"></div>
                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <label><i class="fa fa-home <%=Utility.sanitize(font)%>"></i> Address </label>
                                                    <input type="text" class="form-control" id="heavy_address" maxlength="100"
                                                           name="heavy_address" 
                                                           value="<%=Utility.sanitize(cl.getIndirizzo())%>"
                                                           onkeypress="return keysub(this, event);" 
                                                           onkeyup="return modificaOAM(this, event);"/>
                                                </div>
                                            </div>
                                            <%if (Constant.is_CZ) {%>
                                            <!--CHZ-->
                                            <div class="col-md-4" id="czh5">
                                                <div class="form-group">
                                                    <label><i class="fa fa-font <%=Utility.sanitize(font)%>"></i> Money Source</label>
                                                    <input type="text" class="form-control" disabled value="CONTROLLARE"/> 
                                                </div>
                                            </div>
                                            <%}%>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-home <%=Utility.sanitize(font)%>"></i> Zip Code </label>
                                                    <input type="text" class="form-control uppercase" id="heavy_zipcode" 
                                                           name="heavy_zipcode" maxlength="15" 
                                                           value="<%=Utility.sanitize(cl.getCap())%>"
                                                           onkeypress="return keysub(this, event);" 
                                                           onkeyup="return fieldNOSPecial_1(this.id);"> 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-home <%=Utility.sanitize(font)%>"></i> District</label>
                                                    <%if (!ck.getTipologia_clienti().equals("002")) {%>
                                                    <input class="form-control" type="text" name="heavy_district" 
                                                           id="heavy_district_dis" value="<%=Utility.sanitize(cl.getProvincia())%>"
                                                           onkeypress="return keysub(this, event);" maxlength="2"
                                                           onkeyup="return modificaOAMSurname(this, event);"/>
                                                    <%} else {

                                                    %>
                                                    <div id="heavy_district_div">

                                                        <select class="form-control select2" id="heavy_district" 
                                                                name="heavy_district" onkeypress="return keysub(this, event);" 
                                                                style="width: 100%;">
                                                            <%for (int i = 0; i < district.size(); i++) {
                                                                    String sel = "";
                                                                    if (district.get(i)[0].equals(cl.getProvincia())) {
                                                                        sel = "selected";
                                                                    }%>
                                                            <option <%=Utility.sanitize(sel)%> value="<%=Utility.sanitize(district.get(i)[0])%>"><%=Utility.sanitize(district.get(i)[1])%></option>
                                                            <%}%>  
                                                        </select>
                                                    </div>
                                                    <%}%>
                                                </div>
                                            </div>
                                            <!--CHZ-->
                                            <%if (Constant.is_CZ) {%>
                                            <div class="col-md-4" id="czh6" style="display: none;">
                                                <div class="form-group">
                                                    <label><i class="fa fa-font <%=Utility.sanitize(font)%>"></i> Transaction Reason <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" disabled value="CONTROLLARE"/> 
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
                                                                <label><i class="fa fa-home <%=Utility.sanitize(font)%>"></i> City</label>
                                                                <input type="text" class="form-control" name="heavy_pob_city" 
                                                                       id="heavy_pob_city" maxlength="40"
                                                                       value="<%=Utility.sanitize(cl.getCitta_nascita())%>"
                                                                       onkeypress="return keysub(this, event);"
                                                                       onkeyup="return modificaOAMSurname(this, event);" />
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label><i class="fa fa-home <%=Utility.sanitize(font)%>"></i> District</label>

                                                                <%if (!cl.getNazione_nascita().equals(Constant.codnaz)) {  
                                                                    if(cl.getProvincia_nascita().equals("---")){
                                                                        cl.setProvincia_nascita("EE");
                                                                    }
                                                                
                                                                %>
                                                                <input type="text" class="form-control" name="heavy_pob_district" 
                                                                       id="heavy_pob_district" 
                                                                       onkeypress="return keysub(this, event);" 
                                                                       maxlength="2" value="<%=Utility.sanitize(cl.getProvincia_nascita())%>"
                                                                       onkeyup="return modificaOAMSurname(this, event);"  /> 
                                                                <%} else {%>
                                                                <div id="heavy_pob_district_div">
                                                                    <select class="form-control select2" id="heavy_pob_district" 
                                                                            name="heavy_pob_district" onkeypress="return keysub(this, event);">
                                                                        <option value="---">None</option>
                                                                        <%for (int i = 0; i < district.size(); i++) {
                                                                                String sel = "";
                                                                                if (district.get(i)[0].equals(cl.getProvincia_nascita())) {
                                                                                    sel = "selected";
                                                                                }%>
                                                                        <option <%=Utility.sanitize(sel)%> value="<%=Utility.sanitize(district.get(i)[0])%>"><%=Utility.sanitize(district.get(i)[1])%></option>
                                                                        <%}%>
                                                                    </select>
                                                                </div>
                                                                <%}%>

                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label><i class="fa fa-flag <%=Utility.sanitize(font)%>"></i> Country</label>

                                                                <select class="form-control select2" id="heavy_pob_country" name="heavy_pob_country" onchange="return changedistrictpob();" onkeypress="return keysub(this, event);">
                                                                    <%for (int i = 0; i < country.size(); i++) {
                                                                            String sel = "";
                                                                            if (country.get(i)[0].equals(cl.getNazione_nascita())) {
                                                                                sel = "selected";
                                                                            }%>
                                                                    <option <%=Utility.sanitize(sel)%> value="<%=Utility.sanitize(country.get(i)[0])%>"><%=Utility.sanitize(country.get(i)[1])%></option>
                                                                    <%}%>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label><i class="fa fa-calendar <%=Utility.sanitize(font)%>"></i> Date</label>
                                                                <input type="text" class="form-control date-picker" name="heavy_pob_date" 
                                                                       value="<%=Utility.sanitize(cl.getDt_nascita())%>"      id="heavy_pob_date" onkeypress="return keysub(this, event);" > 

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card <%=Utility.sanitize(font)%>"></i> Identification Card</label>
                                                    <select class="form-control select2" id="heavy_identcard" name="heavy_identcard" onkeypress="return keysub(this, event);">
                                                        <%

                                                            for (int i = 0; i < array_identificationCard.size(); i++) {

                                                                String sel = "";
                                                                if (array_identificationCard.get(i)[0].equals(cl.getTipo_documento())) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option <%=Utility.sanitize(sel)%> value="<%=Utility.sanitize(array_identificationCard.get(i)[0])%>"><%=Utility.sanitize(array_identificationCard.get(i)[1])%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card <%=Utility.sanitize(font)%>"></i> Number of Id. Card</label>
                                                    <input type="text" class="form-control uppercase" id="heavy_numberidentcard" 
                                                           name="heavy_numberidentcard" maxlength="20" value="<%=Utility.sanitize(cl.getNumero_documento())%>"
                                                           onkeypress="return keysub(this, event);"  
                                                           onkeyup="return modificaOAM(this, event);" /> 

                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-calendar <%=Utility.sanitize(font)%>"></i> Issue Date</label>
                                                    <input type="text" class="form-control date-picker" value="<%=Utility.sanitize(cl.getDt_rilascio_documento())%>" id="heavy_issuedateidentcard"name="heavy_issuedateidentcard" onkeypress="return keysub(this, event);"> 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-calendar <%=Utility.sanitize(font)%>"></i> Expiration Date</label>
                                                    <input type="text" class="form-control date-picker" id="heavy_exdateidentcard" 
                                                           name="heavy_exdateidentcard" value="<%=Utility.sanitize(cl.getDt_scadenza_documento())%>"
                                                           onkeypress="return keysub(this, event);" />
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card <%=Utility.sanitize(font)%>"></i> Issued By</label>
                                                    <input type="text" class="form-control" id="heavy_issuedbyidentcard" name="heavy_issuedbyidentcard" 
                                                           maxlength="40" onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.sanitize(cl.getRilasciato_da_documento())%>"
                                                           onkeyup="return modificaOAMSurname(this, event);" /> 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card <%=Utility.sanitize(font)%>"></i> Place of Issue</label>
                                                    <input type="text" class="form-control" id="heavy_issuedplaceidentcard" 
                                                           name="heavy_issuedplaceidentcard" 
                                                           maxlength="40" value="<%=Utility.sanitize(cl.getLuogo_rilascio_documento())%>"
                                                           onkeypress="return keysub(this, event);" 
                                                           onkeyup="return modificaOAMSurname(this, event);" /> 
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-envelope <%=Utility.sanitize(font)%>"></i> Email</label>
                                                    <input type="text" class="form-control"
                                                           id="heavy_email" name="heavy_email" value="<%=Utility.sanitize(cl.getEmail())%>"
                                                           onkeypress="return keysub(this, event);"
                                                           onkeyup="return fieldNOSPecial_2(this.id);"/> 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-phone <%=Utility.sanitize(font)%>"></i> Phone Number</label>
                                                    <input type="text" class="form-control" id="heavy_phonenu" value="<%=Utility.sanitize(cl.getTelefono())%>"
                                                           name="heavy_phonenu" onkeypress="return keysub(this, event);" 
                                                           onkeyup="return fieldOnlyNumber(this.id);">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-12">
                                                <hr>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-md-12">
                                                <center><button class="btn btn-lg <%=Utility.sanitize(color)%> btn-block" type="submit" id="butconf"><i class="fa fa-save"></i> Save</button></center>
                                            </div>
                                        </div>


                                    </div>
                                </div>
                            </div>
                        </form>
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
                <%if (tra.getTipotr().equals("B")) {%>
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
                                    {orderable: !1, targets: [15]}
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