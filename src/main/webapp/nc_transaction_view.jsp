<%@page import="rc.so.entity.Ch_transaction_file"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.entity.Openclose"%>
<%@page import="rc.so.entity.NC_transaction"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="rc.so.entity.NC_category"%>
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
        <title>Mac2.0 - No Change Tr.</title>
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
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;
            String fil = Engine.getFil()[0];
            String cod = request.getParameter("cod");
            NC_transaction nc = Engine.get_NC_transaction(cod, request.getParameter("type"));
            ArrayList<String[]> array_kind_payment = Engine.kind_payment();
            ArrayList<NC_category> array_nc_cat = Engine.list_nc_category_enabled();
            ArrayList<NC_causal> array_nc_caus = Engine.list_nc_causal_enabled();
            ArrayList<String[]> array_country = Engine.country();
            ArrayList<String[]> array_credit_card = Engine.credit_card(nc.getFiliale());
            ArrayList<String[]> array_bankacc = Engine.list_bankAccount();
            ArrayList<Till> listTill = Engine.list_till_status(null, null, nc.getFiliale(), request.getParameter("type"));

            String comm = "0";
            if (Utility.fd(nc.getCommissione()) > 0) {
                comm = nc.getCommissione();
            } else {
                comm = nc.getTi_ticket_fee();
            }

        %>
        <script type="text/javascript">

            var separatordecimal = '<%=decimal%>';
            var separatorthousand = '<%=thousand%>';

            function changecausal() {
                var val_cat = "<%=Utility.sanitize(nc.getGruppo_nc()))%>";
                var val_caus = "<%=Utility.sanitize(nc.getCausale_nc()))%>";
                var nc_kind = '';
                var nc_inout = '';
                var nc_type = '';
                var nc_batch = '0';
                var nc_price_cat = '0';
                var nc_price_caus = '0';
                var cat_conto01 = '';
                var cat_conto02 = '';
                var cat_ticfee = '';
                var caus_ticfee = '';
                var cat_maxtic = '';
                var caus_maxtic = '';
                var percIva = '';
                var paymat = '';


            <%for (int x = 0; x < array_nc_caus.size(); x++) {
                    NC_causal ncjs = array_nc_caus.get(x);%>
                var confront = '<%=ncjs.getCausale_nc()%>';
                if (confront === val_caus) {
                    nc_kind = "<%=ncjs.getFg_tipo_transazione_nc()%>".trim();
                    nc_type = "<%=ncjs.getNc_de()%>".trim();
                    nc_inout = "<%=ncjs.getFg_in_out()%>".trim();
                    nc_batch = "<%=ncjs.getFg_batch()%>".trim();
                    nc_price_caus = "<%=ncjs.getIp_prezzo_nc()%>".trim();
                    caus_ticfee = "<%=ncjs.getTicket_fee()%>".trim();
                    caus_ticfee = "<%=ncjs.getMax_ticket()%>".trim();
                    paymat = "<%=ncjs.getPaymat()%>".trim();
                }
            <%}%>

            <%for (int x = 0; x < array_nc_cat.size(); x++) {
                    NC_category ncat_js = array_nc_cat.get(x);%>
                var confront = '<%=ncat_js.getGruppo_nc()%>';
                if (confront === val_cat) {
                    nc_price_cat = "<%=ncat_js.getIp_prezzo_nc()%>".trim();
                    cat_conto01 = "<%=ncat_js.getConto_coge_01()%>".trim();
                    cat_conto02 = "<%=ncat_js.getConto_coge_02()%>".trim();
                    cat_ticfee = "<%=ncat_js.getTicket_fee()%>".trim();
                    cat_maxtic = "<%=ncat_js.getMax_ticket()%>".trim();
                    percIva = "<%=ncat_js.getInt_iva()%>".trim();
                }
            <%}%>

                var quant = false;
                var wunet = false;
                var rights = false;

                var quantity = "0.00";
                var price = "0.00";
                var right = "0.00";



                document.getElementById("labrec").innerHTML = "Receipt";





                if (nc_kind === '1' && nc_type === '01') {
                    wunet = true;
                    document.getElementById('ex1').style.display = '';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = 'none';
                    document.getElementById('ex3A').style.display = 'none';
                    document.getElementById('ex3B').style.display = 'none';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = 'none';
                    document.getElementById('ex5').style.display = 'none';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = 'none';
                } else if (nc_kind === '1' && nc_type === '02') {

                    wunet = true;
                    document.getElementById('ex1').style.display = '';
                    document.getElementById('ex2').style.display = '';
                    document.getElementById('ex3').style.display = 'none';
                    document.getElementById('ex3A').style.display = 'none';
                    document.getElementById('ex3B').style.display = 'none';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = 'none';
                    document.getElementById('ex5').style.display = 'none';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = 'none';
                } else if (nc_kind === '3' && nc_type === '03') {
                    quant = true;
                    document.getElementById("labrec").innerHTML = "Number of Forms";
                    document.getElementById("quantlab").innerHTML = "Total Amount";
                    document.getElementById('ex1').style.display = 'none';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = '';
                    document.getElementById('ex3A').style.display = '';
                    document.getElementById('ex3B').style.display = 'none';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = '';
                    document.getElementById('ex5').style.display = '';
                    document.getElementById('ex6A').style.display = 'none';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = 'none';



                } else if (nc_kind === '3' && nc_type === '04') {
                    quant = true;
                    document.getElementById("labrec").innerHTML = "Number of Forms";
                    document.getElementById("quantlab").innerHTML = "Total Amount";
                    document.getElementById('ex1').style.display = 'none';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = '';
                    document.getElementById('ex3A').style.display = '';
                    document.getElementById('ex3B').style.display = 'none';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = '';
                    document.getElementById('ex5').style.display = '';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = 'none';
                } else if (nc_kind === '2') {
                    quant = true;
                    document.getElementById('ex1').style.display = 'none';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = 'none';
                    document.getElementById('ex3A').style.display = '';
                    document.getElementById('ex3B').style.display = '';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = 'none';
                    document.getElementById('ex5').style.display = 'none';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = 'none';
                } else if (nc_kind === '4') {
                    quant = true;
                    document.getElementById('ex1').style.display = 'none';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = 'none';
                    document.getElementById('ex3A').style.display = '';
                    document.getElementById('ex3B').style.display = '';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = 'none';
                    document.getElementById('ex5').style.display = 'none';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = 'none';
                } else if (nc_kind === '21') { //ticket
                    quant = true;
                    document.getElementById('ex1').style.display = 'none';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = 'none';
                    document.getElementById('ex3A').style.display = '';
                    document.getElementById('ex3B').style.display = '';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = 'none';
                    document.getElementById('ex5').style.display = 'none';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = 'none';
                } else if (nc_kind === '5') {
                    quant = true;
                    document.getElementById('ex1').style.display = 'none';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = 'none';
                    document.getElementById('ex3A').style.display = '';
                    document.getElementById('ex3B').style.display = '';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = 'none';
                    document.getElementById('ex5').style.display = '';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = '';
                    document.getElementById('ex7').style.display = '';
                } else if (nc_kind === '6' && nc_type === '07') {
                    quant = true;
                    document.getElementById('ex1').style.display = 'none';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = 'none';
                    document.getElementById('ex3A').style.display = '';
                    document.getElementById('ex3B').style.display = '';
                    document.getElementById('ex3BA').style.display = 'none';
                    document.getElementById('ex4').style.display = 'none';
                    document.getElementById('ex5').style.display = 'none';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = '';
                } else if (nc_kind === '7') {
                    quant = true;
                    document.getElementById('ex1').style.display = 'none';
                    document.getElementById('ex2').style.display = 'none';
                    document.getElementById('ex3').style.display = 'none';
                    document.getElementById('ex3A').style.display = '';
                    document.getElementById('ex3B').style.display = '';
                    document.getElementById('ex3BA').style.display = '';
                    document.getElementById('ex4').style.display = 'none';
                    document.getElementById('ex5').style.display = 'none';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex8').style.display = 'none';
                    document.getElementById('ex7').style.display = 'none';

                    document.getElementById('piva0').value = percIva;
                    formatValueDecimal_1(document.getElementById('piva0'), separatorthousand, separatordecimal);


                }

                if (nc_type === '01' || nc_type === '03' || nc_type === '04' || nc_type === '06' || nc_type === '08' || nc_type === '10' || nc_type === '07' || nc_type === '14' || nc_type === '16') {
                    document.getElementById('ex6').style.display = 'none';
                    document.getElementById('ex6A').style.display = 'none';
                    document.getElementById('ex6B').style.display = 'none';

                } else {
                    document.getElementById('ex6').style.display = '';
                    document.getElementById('ex6A').style.display = '';
                    document.getElementById('ex6B').style.display = '';
                }

                //ticket general rules
                if (wunet || cat_conto02 === "") { //non visual rights
                    document.getElementById('ex3C').style.display = 'none';
                } else {
                    document.getElementById('ex3C').style.display = '';
                }
            }

            function loadpage() {
                changecausal();
            }


        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return loadpage();">

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
                            <h3 class="page-title">Transaction No Change <small><b>View</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                        <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->

                    <%if (nc != null) {
                            Openclose oc = Engine.query_oc(nc.getId_open_till(), request.getParameter("type"));
                            String idoc = "NO ID";
                            if (oc != null) {
                                idoc = oc.getId();
                            }
                    %>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Code</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getId())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(nc.getData(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getUser())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Status</label><p class='ab'></p>
                                <%=nc.formatStatus(nc.getDel_fg())%>
                            </div>
                        </div>
                        <div class="clearfix"></div>  
                        <% if (nc.getDel_fg().equals("1")) {%>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(nc.getDel_dt(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getDel_user())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Motivation</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getDel_motiv())%>"/>
                            </div>
                        </div>
                        <div class="clearfix"></div>  
                        <%}%>
                        <hr>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Category</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Engine.formatALNC_category(nc.getGruppo_nc(), array_nc_cat).trim())%>"/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Causal</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Engine.formatALNC_causal(nc.getCausale_nc(), array_nc_caus).trim())%>"/>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Id open till</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(idoc)%>"> 
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Till</label><p class='ab'></p>
                                <%String des = nc.getTill().equals("000") ? "Safe" : Engine.formatAL_Till(nc.getTill(), listTill);%>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getTill()) + " - " + Utility.sanitize(des)%>"> 
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div id="ex1" style="display: none;">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>MTCN</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getMtcn())%>">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>WU Net</label>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getNetto()))%>">
                                </div>
                            </div>
                        </div>
                        <div id="ex2" style="display: none;">
                            <div class="clearfix"></div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>WU Commission</label>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getCommissione()))%>">
                                </div>
                            </div>
                        </div>
                        <div id="ex3" style="display: none;">          
                            <div class="clearfix"></div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label id="labrec">Receipt</label>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getRicevuta()))%>">
                                </div>
                            </div>
                        </div>
                        <div id="ex3A" style="display: none;">          
                            <div class="clearfix"></div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label id="quantlab">Quantity</label> 
                                    <%

                                        if (nc.getFg_tipo_transazione_nc().equals("3")) {

                                        }
                                    %>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getQuantita()))%>">

                                </div>
                            </div>

                        </div>
                        <div id="ex3B" style="display: none;">          
                            <div class="clearfix"></div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Price</label>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getPrezzo()))%>">
                                </div>
                            </div>
                        </div>
                        <div id="ex3BA">          
                            <div class="clearfix"></div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Percent IVA</label>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getPercentiva()))%>">
                                </div>
                            </div>
                        </div>

                        <div id="ex3C" style="display: none;">          
                            <div class="clearfix"></div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Fee &#37;</label>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getTi_diritti()))%>">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Fee Value</label>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getTi_ticket_fee()))%>">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Fee Total</label>
                                    <input type="text" class="form-control inputright" disabled="disabled" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(comm))%>">
                                </div>
                            </div>
                        </div>

                        <div class="clearfix"></div>
                        <div class="col-md-12">
                            <div class="mt-element-list">
                                <div class="mt-list-head list-news font-white bg-blue">
                                    <div class="list-head-title-container">
                                        <h3 class="list-title pull-left"><i class="fa <%=Constant.iconcur%>"></i></h3><h3 class="list-title"> Total 
                                            <span class="pull-right">
                                                <b id="total0"><%=Utility.sanitize(Utility.formatMysqltoDisplay(nc.getTotal()))%></b>
                                            </span> 
                                        </h3>
                                    </div>
                                </div>
                            </div>
                            <hr>
                        </div>

                        <div class="clearfix"></div>  
                        <div id="ex8" style="display: none;">          
                            <div class="clearfix"></div>
                            <p class='ab'></p>   
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Identification Code</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getAss_idcode())%>"/> 
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Start Date</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getAss_startdate())%>"/> 
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>End Date</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getAss_enddate())%>"/> 
                                </div>
                            </div>
                        </div>    

                        <div id="ex4" style="display: none;">          
                            <div class="clearfix"></div>
                            <p class='ab'></p>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Customs</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Engine.format_FG_dogana(nc.getFg_dogana()))%>"/> 
                                </div>
                            </div>
                        </div>          

                        <div id="ex5" style="display: none;">
                            <div class="clearfix"></div>
                            <p class='ab'></p>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Surname</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getCl_cognome())%>"/> 
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Name</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getCl_nome())%>"/> 
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Address</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getCl_indirizzo())%>"/> 
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>City</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getCl_citta())%>"/> 
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Country</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatAL(nc.getCl_nazione(), array_country, 1))%>"/> 
                                </div>
                            </div>
                            <div id="ex7" style="display: none;">    
                                <div class="clearfix"></div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Zip Code</label>
                                        <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getCl_cap())%>"/> 
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>District</label>
                                        <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getCl_provincia())%>"/> 
                                    </div>
                                </div>
                            </div>    
                            <div class="clearfix"></div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getCl_email())%>"/> 
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Phone Number</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getCl_telefono())%>"/> 
                                </div>
                            </div>
                        </div>

                        <div class="clearfix"></div>
                        <p class='ab'></p>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label>Note</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getNote())%>"/> 
                            </div>
                        </div>
                        <%

                            ArrayList<Ch_transaction_file> modify = Engine.list_transaction_modify(nc.getCod());
                            boolean modificato = modify.size() > 0;
                        %>    
                        <div class="clearfix"></div>
                        <div class="col-md-6"id="ex6A">
                            <div class="form-group">
                                <label>Payment mode 
                                    <%if (modificato) {%>
                                    <button class='btn btn-sm btn-circle white popovers' ata-container='body' data-trigger='hover' data-placement='top' 
                                            data-content='View Change Payment Mode' type="button" data-toggle="modal" data-target="#largelock">
                                        <i class='fa fa-edit'></i></button>
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

                                                                    if (oldk.equals("06") || oldk.equals("07")) {
                                                                        de_old_ps = Utility.formatAL(modify.get(x).getOld_ps(), array_credit_card, 1);
                                                                    }
                                                                    if (oldk.equals("08")) {
                                                                        de_old_ps = Utility.formatAL(modify.get(x).getOld_ps(), array_bankacc, 1);
                                                                    }
                                                                    if (newk.equals("06") || newk.equals("07")) {
                                                                        de_new_ps = Utility.formatAL(modify.get(x).getNew_ps(), array_credit_card, 1);
                                                                    }
                                                                    if (newk.equals("08")) {
                                                                        de_new_ps = Utility.formatAL(modify.get(x).getNew_ps(), array_bankacc, 1);
                                                                    }


                                                            %>
                                                            <tr>
                                                                    <td class="tabnow"><%=Utility.sanitize(oldk)%> - <%=Utility.sanitize(Utility.formatAL(oldk,
                                                                            array_kind_payment, 1))%></td>
                                                                    <td class="tabnow"><%=Utility.sanitize(newk)%> - <%=Utility.sanitize(Utility.formatAL(newk,
                                                                            array_kind_payment, 1))%></td>
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
                                    <%}%>
                                </label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getSupporto()) + " - " + Utility.sanitize(Utility.formatAL(nc.getSupporto(), array_kind_payment, 1))%>"/> 
                            </div>
                        </div>
                        <div class="col-md-6" id="ex6">
                            <label>Pos</label>
                            <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getPos()) + " - " + Utility.sanitize(Utility.formatAL(nc.getPos(), array_credit_card, 1))%>"/> 
                        </div>
                        <div class="clearfix"></div>
                        <div class="col-md-6" id="ex6B">
                            <label>CC Number</label>
                            <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(nc.getPosnum())%>"/> 
                        </div>
                        <div class="clearfix"></div>
                        <%if (nc.getDocrico() != null) {
                                if (!nc.getDocrico().equals("-")) {
                                    String text = "Download Identity Document / Receipt";
                        %>
                        <hr>
                        <div class="clearfix"></div>
                        <div class="col-md-6">
                            <a class="btn btn-outline red" target="_blank" href="Fileview?type=viewncfile&cod=<%=Utility.sanitize(nc.getCod())%>">
                                <%%>
                                <i class="fa fa-file-pdf-o"></i> <%=text%>
                            </a>
                        </div>
                        <%}
                            }
                            String loy = Engine.query_LOY_transaction(nc.getCod(), request.getParameter("type"), fil);
                        %>
                        <%if (loy != null) {%>
                        <hr>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Loyalty Code</label>
                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(loy)%>"/>
                            </div>
                        </div>
                        <%}%>
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
