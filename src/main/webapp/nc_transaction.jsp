<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="rc.so.entity.NC_category"%>
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
        <script src="assets/soop/js/core-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5.js" type="text/javascript"></script>
        <script src="assets/soop/js/validate.min.js" type="text/javascript"></script>

        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>


        <%
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;
            ArrayList<String[]> array_kind_payment = Engine.kind_payment();
            ArrayList<String[]> nc_causal_payment_enabled = Engine.nc_causal_payment_enabled();
            //String fil = Engine.getFil()[0];
            ArrayList<NC_category> array_nc_cat = Engine.list_nc_category_enabled();
            ArrayList<NC_causal> array_nc_caus = Engine.list_nc_causal_enabled();
            ArrayList<String[]> array_country = Engine.country();
            ArrayList<String[]> array_credit_card = Engine.credit_card_enabled();
            ArrayList<String[]> array_bankacc = Engine.list_bankAccount();
            ArrayList<Till> listTill = Engine.list_till_status("O", Utility.checkAttribute(session,"us_cod"));

            Till safe_1 = Engine.getContainsTill("000", listTill);

            //Till safe_1 = Engine.getSafe();
            //boolean containsSafe = Engine.containsSafe(listTill, safe_1);
            boolean iscentral = Engine.isCentral();
            //iscentral = false;
            if (!iscentral) {
        %>


        <script type="text/javascript">

            var separatordecimal = '<%=decimal%>';
            var separatorthousand = '<%=thousand%>';

            function chsel1() {
                var category = document.getElementById('nc_cat1');
                var val_cat = category.value;
                var id_caus = '#nc_caus1';
                $(id_caus).empty().trigger('change');
                var o = $("<option/>", {value: "", text: ""});
                $(id_caus).append(o);

            <%for (int x = 0; x < array_nc_caus.size(); x++) {
                    NC_causal ncjs = array_nc_caus.get(x);
            %>
                var confront = '<%=ncjs.getGruppo_nc()%>';
                if (confront === val_cat) {
                    var value0 = "<%=ncjs.getCausale_nc()%>";
                    var value1 = "<%=ncjs.getCausale_nc()%> - <%=Utility.correggiValueJS(ncjs.getDe_causale_nc())%>";
                                var o = $("<option/>", {value: value0, text: value1});
                                $(id_caus).append(o);
                            }
            <%}%>
                            $(id_caus).val($(id_caus + ' option:first-child').val()).trigger('change');

                            changecausal();
                        }

                        function changecausal() {
                            var category = document.getElementById('nc_cat1');
                            var val_cat = category.value;
                            var causal = document.getElementById('nc_caus1');
                            var val_caus = causal.value;
                            var nc_kind = '';
                            var nc_inout = '';
                            var nc_type = '';
                            var nc_batch = '0';
                            var nc_price_cat = '0';
                            var nc_price_caus = '0';
                            var cat_conto01 = '';
                            var cat_conto02 = '';
                            var cat_ticfee_type = '';
                            var cat_ticfee = '';
                            var caus_ticfee = '';
                            var cat_maxtic = '';
                            var caus_maxtic = '';
                            var percIva = '';
                            var paymat = '';
                            var docrico = '';
                            var modificaFEE = '';

                            var emettiscontrino = '0';
                            var vend = false;




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
                                caus_maxtic = "<%=ncjs.getMax_ticket()%>".trim();
                                paymat = "<%=ncjs.getPaymat()%>".trim();
                                docrico = "<%=ncjs.getDocric()%>".trim();
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
                                cat_ticfee_type = "<%=ncat_js.getTicket_fee_type()%>".trim();

                                cat_maxtic = "<%=ncat_js.getMax_ticket()%>".trim();
                                percIva = "<%=ncat_js.getInt_iva()%>".trim();
                                modificaFEE = "<%=ncat_js.getTicket_enabled().equals("1")%>";
                                emettiscontrino = "<%=ncat_js.getFg_registratore()%>".trim();
                            }
            <%}%>


                            if (document.getElementById('h_cat_ticfee_type') === null) {
                                var input = document.createElement("input");
                                input.setAttribute("type", "hidden");
                                input.setAttribute("id", "h_cat_ticfee_type");
                                input.setAttribute("value", cat_ticfee_type);
                                document.getElementById("f1").appendChild(input);
                            } else {
                                document.getElementById('h_cat_ticfee_type').value = cat_ticfee_type;
                            }
                            if (document.getElementById('h_cat_ticfee') === null) {
                                var input = document.createElement("input");
                                input.setAttribute("type", "hidden");
                                input.setAttribute("id", "h_cat_ticfee");
                                input.setAttribute("value", cat_ticfee_type);
                                document.getElementById("f1").appendChild(input);
                            } else {
                                document.getElementById('h_cat_ticfee').value = cat_ticfee;
                            }
                            if (document.getElementById('h_caus_ticfee') === null) {
                                var input = document.createElement("input");
                                input.setAttribute("type", "hidden");
                                input.setAttribute("id", "h_caus_ticfee");
                                input.setAttribute("value", caus_ticfee);
                                document.getElementById("f1").appendChild(input);
                            } else {
                                document.getElementById('h_caus_ticfee').value = caus_ticfee;
                            }
                            if (document.getElementById('h_caus_maxtic') === null) {
                                var input = document.createElement("input");
                                input.setAttribute("type", "hidden");
                                input.setAttribute("id", "h_caus_maxtic");
                                input.setAttribute("value", caus_maxtic);
                                document.getElementById("f1").appendChild(input);
                            } else {
                                document.getElementById('h_caus_maxtic').value = caus_maxtic;
                            }
                            if (document.getElementById('h_modificaFEE') === null) {
                                var input = document.createElement("input");
                                input.setAttribute("type", "hidden");
                                input.setAttribute("id", "h_modificaFEE");
                                input.setAttribute("value", modificaFEE);
                                document.getElementById("f1").appendChild(input);
                            } else {
                                document.getElementById('h_modificaFEE').value = modificaFEE;
                            }




                            if (docrico === "1") {
                                document.getElementById('exF').style.display = '';
                            } else {
                                document.getElementById('exF').style.display = 'none';
                            }



                            document.getElementById('quantity0').value = '';
                            document.getElementById('wunet0').value = '';

                            var quant = false;
                            var wunet = false;
                            var rights = false;

                            var quantity = "0.00";
                            var price = "0.00";
                            var right = "0.00";

                            document.getElementById("nc_kind_h").value = nc_kind;
                            document.getElementById("nc_type_h").value = nc_type;

                            document.getElementById("labrec").innerHTML = "Receipt";
                            document.getElementById("quantlab").innerHTML = "Quantity";
                            if (nc_batch === '1') {
                                document.getElementById('ex1').style.display = 'none';
                                document.getElementById('ex2').style.display = 'none';
                                document.getElementById('ex3').style.display = 'none';
                                document.getElementById('ex3A').style.display = 'none';
                                document.getElementById('ex3B').style.display = 'none';
                                document.getElementById('ex3C').style.display = 'none';
                                document.getElementById('ex4').style.display = 'none';
                                document.getElementById('ex5').style.display = 'none';
                                document.getElementById('ex6').style.display = 'none';
                                document.getElementById('ex6A').style.display = 'none';
                                document.getElementById('ex6B').style.display = 'none';
                                document.getElementById('ex7').style.display = 'none';
                                document.getElementById('ex8').style.display = 'none';
                                var ermsg = 'Only NO-BATCH transaction admitted.';
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = ermsg;
                                document.getElementById("submittra").style.display = "none";
                                return false;
                            } else if (paymat === "1") {
                                document.getElementById('ex1').style.display = 'none';
                                document.getElementById('ex2').style.display = 'none';
                                document.getElementById('ex3').style.display = 'none';
                                document.getElementById('ex3A').style.display = 'none';
                                document.getElementById('ex3B').style.display = 'none';
                                document.getElementById('ex3C').style.display = 'none';
                                document.getElementById('ex4').style.display = 'none';
                                document.getElementById('ex5').style.display = 'none';
                                document.getElementById('ex6').style.display = 'none';
                                document.getElementById('ex6A').style.display = 'none';
                                document.getElementById('ex6B').style.display = 'none';
                                document.getElementById('ex7').style.display = 'none';
                                document.getElementById('ex8').style.display = 'none';
                                var ermsg = "<span class='font-blue'>Warning!</span> To execute this transaction go in the appropriate section: <span class='font-red'>External Services</span>.";
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = ermsg;
                                document.getElementById("submittra").style.display = "none";
                                return false;
                            } else {
                                document.getElementById("submittra").style.display = "";

                            }


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



                            if (nc_type === '01' || nc_type === '03' || nc_type === '04'
                                    || nc_type === '06' || nc_type === '08' || nc_type === '10'
                                    || nc_type === '07' || nc_type === '14' || nc_type === '16'
                                    || nc_type === '13' || nc_type === '18') {

                                document.getElementById('totalsign').innerHTML = "-";
                                document.getElementById('totalsign1').value = "-";
                                document.getElementById('ex6').style.display = 'none';
                                document.getElementById('ex6A').style.display = 'none';
                                document.getElementById('ex6B').style.display = 'none';
                                vend = false;
                            } else {
                                document.getElementById('totalsign').innerHTML = "";
                                document.getElementById('totalsign1').value = "";
                                document.getElementById('ex6').style.display = "";
                                document.getElementById('ex6A').style.display = "";
                                document.getElementById('ex6B').style.display = "";
                                if (nc_type !== "") {
                                    vend = true;
                                } else {
                                    vend = false;
                                }
                            }

                            //ticket general rules
                            if (wunet || cat_conto02 === "") { //non visual rights
                                document.getElementById('ex3C').style.display = 'none';
                            } else {
                                document.getElementById('ex3C').style.display = '';
                            }

                            //value ticket
                            if (document.getElementById('ex3C').style.display === '') { //rights true





                                if (cat_ticfee === '0.00') {
                                    if (caus_ticfee !== '0.00') {
                                        if (cat_ticfee_type === "0") {
                                            document.getElementById('rights0').value = accounting.formatNumber("0.00", 2, separatorthousand, separatordecimal);
                                            document.getElementById('rights1').value = accounting.formatNumber(caus_ticfee, 2, separatorthousand, separatordecimal);

                                        } else {
                                            document.getElementById('rights0').value = accounting.formatNumber(caus_ticfee, 2, separatorthousand, separatordecimal);
                                            document.getElementById('rights1').value = accounting.formatNumber("0.00", 2, separatorthousand, separatordecimal);

                                        }
                                        //chpercent da fare

                                        $('#rights0').prop('readonly', true);
                                        if (modificaFEE === "true") {
                                            $('#rights1').prop('readonly', false);
                                        } else {
                                            $('#rights1').prop('readonly', true);
                                        }

                                        rights = true;
                                    }
                                } else {
                                    //chpercent da fare
                                    if (cat_ticfee_type === "0") {
                                        document.getElementById('rights0').value = accounting.formatNumber("0.00", 2, separatorthousand, separatordecimal);
                                        document.getElementById('rights1').value = accounting.formatNumber(cat_ticfee, 2, separatorthousand, separatordecimal);
                                    } else {
                                        document.getElementById('rights0').value = accounting.formatNumber(cat_ticfee, 2, separatorthousand, separatordecimal);
                                        document.getElementById('rights1').value = accounting.formatNumber("0.00", 2, separatorthousand, separatordecimal);
                                    }
                                    //chpercent da fare

                                    $('#rights0').prop('readonly', true);
                                    if (modificaFEE === "true") {
                                        $('#rights1').prop('readonly', false);
                                    } else {
                                        $('#rights1').prop('readonly', true);
                                    }
                                    rights = true;
                                }
                            }



                            if (rights) {
                                //right = document.getElementById('rights0').value;
                            } else {
                                //document.getElementById('rights0').value = right;
                            }


                            if (quant) {
                                quantity = document.getElementById('quantity0').value;
                            }

                            if (wunet) {
                                quantity = document.getElementById('wunet0').value;
                            }

                            var enprice = false;

                            if (nc_price_cat !== '0' && nc_price_cat !== '' && nc_price_cat !== '0.00') {
                                price = nc_price_cat;
                                enprice = true;
                            } else {
                                if (nc_price_caus !== '0' && nc_price_caus !== '' && nc_price_caus !== '0.00') {
                                    price = nc_price_caus;
                                    enprice = true;
                                } else {
                                    price = "0.00";
                                }
                            }


                            if (nc_kind === '1' || nc_kind === '3') { // wu e no tax price 1
                                price = "1.00";
                            }



                            if (nc_kind === '2' && nc_inout === '3') {
                                $.ajax({
                                    type: "POST",
                                    url: "Query?type=queryquantitynochange&q=" + val_cat,
                                    success: function (data) {
                                        if (data !== "") {
                                            var arrayJson = JSON.parse(data);
                                            if (arrayJson.length > 0) {
                                                document.getElementById('ex9').style.display = '';
                                                document.getElementById('ex9').innerHTML = "";
                                                for (var i = 0; i < arrayJson.length - 1; i = i + 2) {
                                                    var span1 = document.createElement('span');
                                                    span1.className = 'label label-sm label-success';
                                                    span1.innerHTML = arrayJson[i] + ' - <b>' + arrayJson[i + 1] + '</b>';
                                                    var span2 = document.createElement('span');
                                                    span2.innerHTML = '&nbsp;';
                                                    document.getElementById('ex9').appendChild(span1);
                                                    document.getElementById('ex9').appendChild(span2);
                                                }
                                            } else {
                                                document.getElementById('ex9').innerHTML = "";
                                                document.getElementById('ex9').style.display = 'none';
                                            }
                                        }
                                    }
                                });




                            } else {
                                document.getElementById('ex9').innerHTML = "";
                                document.getElementById('ex9').style.display = 'none';
                            }


                            //valori

                            setValue(price, quantity, right, enprice);


                            var id_kind_1 = '#kind_1';
                            $(id_kind_1).empty().trigger('change');
                            var o = $("<option/>", {value: "...", text: "None"});
                            $(id_kind_1).append(o);

            <%for (int i = 0; i < array_kind_payment.size(); i++) {
                    for (int j = 0; j < nc_causal_payment_enabled.size(); j++) {
            %>
                            var su1 = "<%=array_kind_payment.get(i)[0]%>";
                            var su2 = "<%=nc_causal_payment_enabled.get(j)[3]%>";
                            var ca2 = "<%=nc_causal_payment_enabled.get(j)[2]%>";

                            if (val_caus === ca2 && su1 === su2) {
                                var o = $("<option/>", {value: "<%=array_kind_payment.get(i)[0]%>", text: "<%=array_kind_payment.get(i)[1]%>"});
                                $(id_kind_1).append(o);
                            }
            <%}
                }%>
                            $(id_kind_1).val($(id_kind_1 + ' option:first-child').val()).trigger('change');


                            if (emettiscontrino === "1" && vend) {
                                document.getElementById("scontr1").style.display = "";
                                document.getElementById("scontr2").value = "SI";
                            } else {
                                document.getElementById("scontr1").style.display = "none";
                                document.getElementById("scontr2").value = "NO";
                            }

                            changepaymentmode();
                            loadtill();

                        }

                        function setValue(price, quantity, right, enprice) {

                            var nc_kind = document.getElementById("nc_kind_h").value;
                            var nc_type = document.getElementById("nc_type_h").value;

                            document.getElementById('rights2').value = accounting.formatNumber("0.00", 2, separatorthousand, separatordecimal);
                            var tot = 0;
                            if (price === "0.00" || price === "0,00") {
                                document.getElementById('price0').value = accounting.formatNumber("0.00", 2, separatorthousand, separatordecimal);
                                $('#price0').prop('readonly', false);
                                tot = 0.00;
                            } else {

                                document.getElementById('price0').value = accounting.formatNumber(parseFloatRaf(price, separatorthousand, separatordecimal).toString(), 2, separatorthousand, separatordecimal);
                                if (enprice) {
                                    $('#price0').prop('readonly', true);
                                }


                                if (nc_kind === '1' && nc_type === '02') {
                                    tot = parseFloatRaf(quantity, separatorthousand, separatordecimal) *
                                            parseFloatRaf(price, separatorthousand, separatordecimal) +
                                            parseFloatRaf(document.getElementById('wucom0').value, separatorthousand, separatordecimal);
                                } else {

                                    if (nc_kind === "21") {

                                        formatValueDecimal_1(document.getElementById('rights1'), separatorthousand, separatordecimal);

                                        if (document.getElementById("h_cat_ticfee_type").value === "1") { //PERCENTUALE


                                            var comm = (parseFloatRaf(price, separatorthousand, separatordecimal) *
                                                    parseFloatRaf(document.getElementById('rights0').value, separatorthousand, separatordecimal) / 100);

                                            document.getElementById('rights2').value = accounting.formatNumber(new BigNumber(comm.toString()).round(2, BigNumber.ROUND_HALF_EVEN), 2, separatorthousand, separatordecimal);

                                            tot = parseFloatRaf(price, separatorthousand, separatordecimal) + comm;
                                            document.getElementById('rights1').value = accounting.formatNumber(new BigNumber(comm.toString()).round(2, BigNumber.ROUND_HALF_EVEN), 2, separatorthousand, separatordecimal);


                                        } else {
                                            var comm = (parseFloatRaf(quantity, separatorthousand, separatordecimal) *
                                                    parseFloatRaf(document.getElementById('rights1').value, separatorthousand, separatordecimal));

                                            tot = parseFloatRaf(price, separatorthousand, separatordecimal) + comm;

                                            document.getElementById('rights2').value = accounting.formatNumber(new BigNumber(comm.toString()).round(2, BigNumber.ROUND_HALF_EVEN), 2, separatorthousand, separatordecimal);


                                        }
                                    } else {
                                        tot = parseFloatRaf(quantity, separatorthousand, separatordecimal) *
                                                parseFloatRaf(price, separatorthousand, separatordecimal);
                                    }
                                }


                            }
                            var tot_VISUAL = accounting.formatNumber(new BigNumber(tot.toString()).round(2, BigNumber.ROUND_HALF_EVEN), 2, separatorthousand, separatordecimal);
                            document.getElementById('total0').innerHTML = tot_VISUAL;
                            document.getElementById('total1').value = tot_VISUAL;





                        }


                        function modfee() {

                            formatValueDecimal_1(document.getElementById('price0'), separatorthousand, separatordecimal);
                            formatValueDecimal_1(document.getElementById('rights1'), separatorthousand, separatordecimal);

                            var right1 = parseFloatRaf(document.getElementById('rights1').value, separatorthousand, separatordecimal);

                            var tot = parseFloatRaf(document.getElementById('price0').value, separatorthousand, separatordecimal) + right1;

                            document.getElementById('rights2').value = accounting.formatNumber(right1, 2, separatorthousand, separatordecimal);

                            var tot_VISUAL = accounting.formatNumber(new BigNumber(tot.toString()).round(2, BigNumber.ROUND_HALF_EVEN),
                                    2, separatorthousand, separatordecimal);
                            document.getElementById('total0').innerHTML = tot_VISUAL;
                            document.getElementById('total1').value = tot_VISUAL;

                        }



                        function changequan(idquant) {

                            fieldmaggiorezero(idquant);


                            var nc_kind = document.getElementById("nc_kind_h").value;
                            if (nc_kind === "1" || nc_kind === "3") {
                                formatValueDecimal_1(document.getElementById(idquant), separatorthousand, separatordecimal);
                            } else {
                                formatValueINT_1_change(document.getElementById(idquant), separatorthousand, separatordecimal);
                            }
                            var price = document.getElementById('price0').value;
                            var right = document.getElementById('rights0').value;



                            setValue(price, document.getElementById(idquant).value, right, false);
                        }

                        function changequancom(idcom, idquant) {
                            formatValueDecimal_1(document.getElementById(idcom), separatorthousand, separatordecimal);
                            setValue(document.getElementById('price0').value, document.getElementById(idquant).value, document.getElementById('rights0').value, false);
                        }

                        function changeprice() {

                            var quant = "0";
                            var idquant = '';

                            if (document.getElementById('ex3A').style.display === '') {
                                idquant = 'quantity0';
                            }
                            if (document.getElementById('ex1').style.display === '') {
                                idquant = 'wunet0';
                            }


                            var nc_kind = document.getElementById("nc_kind_h").value;
                            if (nc_kind === "1" || nc_kind === "3") {
                                formatValueDecimal_1(document.getElementById(idquant), separatorthousand, separatordecimal);
                            } else {
                                formatValueINT_1_change(document.getElementById(idquant), separatorthousand, separatordecimal);
                            }

                            var price = document.getElementById('price0').value;
                            var right = document.getElementById('rights0').value;
                            setValue(price, document.getElementById(idquant).value, right, false);
                        }

                        function changepaymentmode() {
                            var kind = document.getElementById('kind_1').value;
                            if (kind === "" || kind === "...") {
                                document.getElementById('ex6').style.display = 'none';
                                document.getElementById('ex6B').style.display = 'none';
                                document.getElementById('ex6C').style.display = 'none';
                                return false;
                            }
                            if (kind === '06' || kind === '07') {
                                document.getElementById('ex6').style.display = '';
                                document.getElementById('ex6C').style.display = 'none';
                                if (kind === '06') {
                                    document.getElementById('ex6B').style.display = '';
                                } else {
                                    document.getElementById('ex6B').style.display = 'none';
                                }
                            } else if (kind === '08') {
                                document.getElementById('ex6').style.display = 'none';
                                document.getElementById('ex6B').style.display = 'none';
                                document.getElementById('ex6C').style.display = '';
                            } else {
                                document.getElementById('ex6').style.display = 'none';
                                document.getElementById('ex6B').style.display = 'none';
                                document.getElementById('ex6C').style.display = 'none';
                            }

                            if (kind === '06') {
                                document.getElementById('ccobbl').style.display = '';
                                document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                                document.getElementById('infolarge').style.display = "block";
                                document.getElementById('infolargetext').innerHTML = "Remember to get the customer's data.";
                                return false;
                            } else {
                                document.getElementById('ccobbl').style.display = 'none';
                            }
                        }


                        function controllatilloccupato() {
                            var occ = "true";
                            $.ajax({
                                async: false,
                                type: "POST",
                                url: "Operazioni_test?type=controllaoccupato_till&q=" + document.getElementById('id_open_till_v').value.trim(),
                                success: function (data) {
                                    occ = data;
                                }
                            });
                            if (occ === "true") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Warning! Operation not permitted. You have an operation pending (Open/Close, Internal/External transfer, Transaction Change), before proceeding you MUST first finish that operation.";
                                return false;
                            } else if (occ === "true1") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Warning! Operation not permitted. The till is already closed.";
                                return false;
                            }
                            return true;
                        }


                        function verificaeurotill() {
                            var nc_cat1 = document.getElementById('nc_cat1').value.trim();
                            var nc_caus1 = document.getElementById('nc_caus1').value.trim();
                            var id_open_till = document.getElementById('id_open_till_v').value.trim();
                            var total1 = document.getElementById('total1').value.trim();
                            var disp = "true";
                            $.ajax({
                                async: false,
                                type: "POST",
                                url: "Operazioni?type=verificaeurotill&nc_cat1=" + nc_cat1 + "&nc_caus1=" +
                                        nc_caus1 + "&id_open_till=" + id_open_till + "&total1=" + total1,
                                success: function (data) {
                                    disp = data;
                                }
                            });
                            if (disp === "false") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "'Total' exceeds the amount of local currency in cash.";
                                return false;
                            }
                            return true;
                        }


                        function isemptyField(field) {
                            if (field === null) {
                                return true;
                            }
                            if (!field.disabled) {
                                if (field.value.trim() === "" || field.value.trim() === "None" || field.value.trim() === "..." || field.value.trim() === "---") {
                                    return true;
                                }
                            }
                            return false;
                        }

                        function verLOY() {
                            if (isemptyField(document.getElementById("loya"))) {
                                return true;
                            }
                            var loya = document.getElementById("loya").value.trim();
                            var es = "Loyalty Code invalid. Please check.";
                            if (loya.length === 8) {
                                $.ajax({
                                    async: false,
                                    type: "POST",
                                    url: "Query?type=queryloy&q=" + loya,
                                    success: function (data) {
                                        if (data !== "") {
                                            var arrayJson = JSON.parse(data);
                                            if (arrayJson.length > 1) {
                                                es = "0";
                                            } else {
                                                es = arrayJson[0];
                                            }
                                        }
                                    }
                                });
                                if (es !== "0") {
                                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                    document.getElementById('errorlarge').style.display = "block";
                                    if (es === "Loyalty Code found. The code will be associated with the customer.") {
                                        es = "No Loyalty code associated with this customer.";
                                    }
                                    document.getElementById('errorlargetext').innerHTML = es;
                                    return false;
                                } else {
                                    return true;
                                }
                            } else {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Loyalty Code invalid.";
                                return false;
                            }
                        }





                        function subform() {


                            var loy = verLOY();
                            if (!loy) {
                                return false;
                            }

                            var checkeuro = verificaeurotill();
                            if (!checkeuro) {
                                return false;
                            }






                            var ti1 = controllatilloccupato();
                            if (!ti1) {
                                return false;
                            }




                            document.getElementById('butconf').disabled = true;
                            $("#butconf").html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");



                            var nc_cat1 = document.getElementById('nc_cat1').value.trim();
                            if (nc_cat1 === "") {
                                var ermsg = "Category must be selected.";
                                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                document.getElementById("errorlarge").style.display = "block";
                                document.getElementById("errorlargetext").innerHTML = ermsg;
                                document.getElementById('butconf').disabled = false;
                                document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                return false;
                            }
                            var nc_caus1 = document.getElementById('nc_caus1').value.trim();
                            if (nc_caus1 === "") {
                                var ermsg = "Causal must be selected.";
                                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                document.getElementById("errorlarge").style.display = "block";
                                document.getElementById("errorlargetext").innerHTML = ermsg;
                                document.getElementById('butconf').disabled = false;
                                document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                return false;
                            }


                            var till = document.getElementById('till').value.trim();
                            if (till === "") {
                                var ermsg = "Safe/Till must be selected. Check list Open/Close.";
                                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                document.getElementById("errorlarge").style.display = "block";
                                document.getElementById("errorlargetext").innerHTML = ermsg;
                                document.getElementById('butconf').disabled = false;
                                document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                return false;
                            }

                            if (document.getElementById("nc_type_h").value === '14') {
                                var libero = "true";
                                $.ajax({
                                    async: false,
                                    type: "POST",
                                    url: "Operazioni_test?type=controllaoccupato",
                                    success: function (data) {
                                        libero = data;
                                    }
                                });
                                if (libero !== "true") {
                                    document.getElementById('errorlargeoccuptext').innerHTML = "Warning! Operation not permitted. The operation of the same type is in progress by the operator "
                                            + libero + ". Please <b class='font-red'>CLOSE</b> this window.";
                                    document.getElementById('occup_butt').click();
                                    return false;
                                }

                            }


                            if (document.getElementById("nc_kind_h").value !== "2") {

                                if (document.getElementById('total1') === "0" + separatordecimal + "00") {
                                    var ermsg = "Total must be different from 0" + separatordecimal + "00";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    document.getElementById('butconf').disabled = false;
                                    document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                    return false;
                                }
                            }

                            var idquant = "quantity0";

                            if (document.getElementById('ex3A').style.display === '') {
                                idquant = 'quantity0';
                            }
                            if (document.getElementById('ex1').style.display === '') {
                                idquant = 'wunet0';
                            }

                            var quantity = document.getElementById(idquant).value.trim();

                            if (parseFloatRaf(quantity, separatorthousand, separatordecimal) <= 0) {
                                var ermsg = "The field 'Quantity'/'Total Amount'/'WU Net' must be greater than zero.";
                                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                document.getElementById("errorlarge").style.display = "block";
                                document.getElementById("errorlargetext").innerHTML = ermsg;
                                document.getElementById('butconf').disabled = false;
                                document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                return false;
                            }

                            if (document.getElementById("nc_kind_h").value === "3") {
                                var recei0 = document.getElementById('recei0').value.trim();
                                if (parseFloatRaf(recei0, separatorthousand, separatordecimal) <= 0) {
                                    var ermsg = "The field 'Number of Forms' must be greater than zero.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    document.getElementById('butconf').disabled = false;
                                    document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                    return false;
                                }
                            }







                            //add controlli
                            var anag = document.getElementById('ex5').style.display.trim();
                            if (anag === "" || anag === "block") {

                                var heavy_surname = document.getElementById('heavy_surname').value.trim();
                                var heavy_name = document.getElementById('heavy_name').value.trim();
                                var heavy_addr = document.getElementById('heavy_addr').value.trim();
                                var heavy_city = document.getElementById('heavy_city').value.trim();
                                var heavy_country = document.getElementById('heavy_country').value.trim();

                                var heavy_zipcode = "-";
                                var heavy_district = "-";


                                var altr = document.getElementById('ex7').style.display.trim();
                                if (altr === "" || altr === "block") {
                                    heavy_zipcode = document.getElementById('heavy_zipcode').value.trim();
                                    heavy_district = document.getElementById('heavy_district').value.trim();
                                }


                                if (heavy_surname === "" || heavy_name === "" || heavy_addr === "" || heavy_city === "" || heavy_country === "" || heavy_zipcode === "" || heavy_district === "") {
                                    var ermsg = "You must complete all fields with <span class='font-red'>*</span>.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    document.getElementById('butconf').disabled = false;
                                    document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                    return false;
                                }

                                var divmail = document.getElementById('ex5').style.display.trim();
                                if (divmail === "") {
                                    var ch4 = checkEmail(document.getElementById("heavy_email").value.trim());
                                    if (!ch4) {
                                        var ermsg = "Incorrect value for field 'Email'.";
                                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                        document.getElementById("errorlarge").style.display = "block";
                                        document.getElementById("errorlargetext").innerHTML = ermsg;
                                        document.getElementById('butconf').disabled = false;
                                        document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                        return false;
                                    }
                                }



                            }

                            if (document.getElementById('ex6A').style.display === '' || document.getElementById('ex6A').style.display === 'block') {
                                var kind_1 = document.getElementById('kind_1').value;
                                if (kind_1 === null) {
                                    kind_1 = "";
                                }

                                if (kind_1.trim() === "" || kind_1.trim() === "...") {
                                    var ermsg = "You must complete all fields with <span class='font-red'>*</span>.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    document.getElementById('butconf').disabled = false;
                                    document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                    return false;
                                }
                            }

                            if (document.getElementById('exF').style.display === '') {
                                var ermsg = "";
                                if (document.getElementById("fileUpload").value === '') {
                                    ermsg = "'Identity Document' must be selected.";
                                } else {
                                    var name = document.getElementById("fileUpload").files[0].name.toLowerCase();
                                    var ext = name.endsWith(".pdf") || name.endsWith(".jpg") || name.endsWith(".jpeg");
                                    if (!ext) {
                                        ermsg = "'Identity Document' file must be .pdf, .jpeg or jpg.";
                                    } else if (document.getElementById("fileUpload").files[0].size > 3145728) {
                                        ermsg = "'Identity Document' exceed max dimension (3 MB).";
                                    }
                                }
                                if (ermsg !== "") {
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    document.getElementById('butconf').disabled = false;
                                    document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                    return false;
                                }
                            }






                            //quantity
                            if (document.getElementById('ex9').style.display === '') {




                                var nc_cat1 = document.getElementById('nc_cat1').value.trim();
                                var nc_caus1 = document.getElementById('nc_caus1').value.trim();
                                var id_open_till_v = document.getElementById('id_open_till_v').value.trim();


                                var chquan = false;
                                $.ajax({
                                    async: false,
                                    type: "POST",
                                    url: "Operazioni_test?type=verifyquantnoch&nc_cat1=" + nc_cat1 + "&nc_caus1=" + nc_caus1 + "&id_open_till_v=" + id_open_till_v + "&quantity=" + quantity,
                                    success: function (data) {
                                        chquan = (data === "true");
                                    }
                                });
                                if (!chquan) {
                                    var ermsg = "Quantity selected exceeds the amount available in this till.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    document.getElementById('butconf').disabled = false;
                                    document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                    return false;
                                }
                            }


                            //credit card numero obble

                            var kind_1 = document.getElementById('kind_1').value;
                            if (kind_1 === null) {
                                kind_1 = "";
                            }

                            if (kind_1 === '06') {

                                var posnum = document.getElementById('posnum').value.trim();
                                if (posnum === "" || posnum.length !== 4) {
                                    var ermsg = "You must complete all fields with <span class='font-red'>*</span>.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    document.getElementById('butconf').disabled = false;
                                    document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                    return false;
                                }


                            }




                        }

                        function checkEmail(email) {
                            if (email !== "") {
                                var v1 = validate.single(email, {presence: true, email: true});
                                if (undefined !== v1) {
                                    return false;
                                }
                            }
                            return true;
                        }

                        function chidopentill() {
                            var till1 = document.getElementById('till').value;



                            if (till1 === '000') {

            <%if (safe_1 != null) {%>
                                var tyop = '<%=safe_1.getTy_opcl()%>';
                                if (tyop === "CLOSE") {
                                    document.getElementById('id_open_till_v').value = "";
                                    document.getElementById('id_open_till').value = "";
                                    var ermsg = "Unable to perform this operation because the SAFE is closed.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    return false;

                                } else {
                                    document.getElementById('id_open_till_v').value = "<%=safe_1.getId_opcl()%>";
                                    document.getElementById('id_open_till').value = "<%=safe_1.getCod_opcl()%>";
                                }
            <%} else {%>
                                document.getElementById('id_open_till_v').value = "";
                                document.getElementById('id_open_till').value = "";
            <%}%>



                            } else {

            <%for (int x = 0; x < listTill.size(); x++) {
                    Till ti_js = listTill.get(x);%>
                                var confr = '<%=ti_js.getCod()%>';
                                if (confr === till1) {
                                    document.getElementById('id_open_till_v').value = "<%=ti_js.getId_opcl()%>";
                                    document.getElementById('id_open_till').value = "<%=ti_js.getCod_opcl()%>";
                                }
            <%}%>
                            }
                        }

                        function loadtill() {

                            document.getElementById('id_open_till_v').value = '';
                            document.getElementById('id_open_till').value = '';

                            var nc_type = '';
                            var nc_caus1 = document.getElementById('nc_caus1').value;
            <%for (int x = 0; x < array_nc_caus.size(); x++) {
                    NC_causal ncjs = array_nc_caus.get(x);%>
                            var confront = "<%=ncjs.getCausale_nc()%>";
                            if (confront === nc_caus1) {
                                nc_type = "<%=ncjs.getNc_de()%>".trim();
                            }
            <%}%>
                            var safe = '0';

                            //if (nc_type === '09' || nc_type === '12' || nc_type === '11' || nc_type === '05') {
                            if (nc_type === '14') {
                                safe = '1';
                            }


                            var till1 = '#till';
                            $(till1).empty().trigger('change');

                            if (safe === '1') {
            <%if (safe_1 != null) {%>

                                var tyop = '<%=safe_1.getTy_opcl()%>';
                                if (tyop === "CLOSE") {



                                    var ermsg = "Unable to perform this operation because the SAFE is closed.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    return false;
                                } else {

                                    var o = $("<option/>", {value: "<%=safe_1.getCod()%>", text: "<%=safe_1.getName()%>"});
                                    $(till1).append(o);
                                }




            <%}%>
                                if (nc_type === '14') {
                                    var libero = "true";
                                    $.ajax({
                                        async: false,
                                        type: "POST",
                                        url: "Operazioni_test?type=controllaoccupato",
                                        success: function (data) {
                                            libero = data;
                                        }
                                    });
                                    if (libero !== "true") {
                                        document.getElementById('errorlargeoccuptext').innerHTML = "Warning! Operation not permitted. The operation of the same type is in progress by the operator " + libero + ". Please <b class='font-red'>CLOSE</b> this window.";
                                        document.getElementById('occup_butt').click();
                                    }


                                }
                            }

            <%for (int x = 0; x < listTill.size(); x++) {%>
                            var value0 = "<%=listTill.get(x).getCod()%>";
                            var value1 = "<%=listTill.get(x).getName()%>";
                            var ty = "<%=listTill.get(x).isSafe()%>";
                            if (ty === "true") {
                                if (safe === '1') {
                                    var o = $("<option/>", {value: value0, text: value1});
                                    $(till1).append(o);
                                }
                            } else {
                                if (safe === '0') {
                                    var o = $("<option/>", {value: value0, text: value1});
                                    $(till1).append(o);
                                }
                            }
            <%}%>




                            $(till1).val($(till1 + ' option:first-child').val()).trigger('change');
                            chidopentill();
                        }

                        function loadpage() {
                            $('#largelogin').on('shown.bs.modal', function () {
                                $('#passwordlargelogin').focus();
                            });
                            chsel1();
                            loadtill();
                            changepaymentmode();
                            chidopentill();
                            online();
                            inputvirgola();
                        }


                        function modificaLOY(field, event) {
                            field.value = RemoveAccents(field.value.toUpperCase().trim());
                            field.value = field.value.replace(/[`~!@#$§°%^£&*()_|+\=?;:",.<>\'{\}\[\]]/gi, '');
                        }
                        function RemoveAccents(str) {
                            var accents = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
                            var accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";
                            str = str.split('');
                            var strLen = str.length;
                            var i, x;
                            for (i = 0; i < strLen; i++) {
                                if ((x = accents.indexOf(str[i])) !== -1) {
                                    str[i] = accentsOut[x];
                                }
                            }
                            return str.join('');
                        }


        </script>
        <%}%>
    </head>
    <!-- END HEAD -->
    <%if (iscentral || listTill.isEmpty()) {%>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return online();">
        <%} else {%>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">
        <%}%>
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_tr7.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                String pswx = Utility.checkAttribute(session,"us_pwd");
            %>
            <div class="modal fade" id="largelogin_hid" tabindex="-1" role="dialog" aria-hidden="true">
                <button type="button" class="btn btn-info btn-lg" id="largelogin_butt" data-toggle="modal" 
                        data-target="#largelogin">Open Modal</button>

                <button type="button" class="btn btn-info btn-lg" id="occup_butt" data-toggle="modal" 
                        data-target="#errorlargeoccup">Open Modal</button>
            </div>
            <div class="modal fade" id="largelogin" tabindex="-1" data-keyboard="false" data-backdrop="static">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Password request</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-10" style="text-align: left;">
                                            <div class="input-icon">
                                                <i class="fa fa-key font-blue"></i>
                                                <input class="form-control" type="password" autocomplete="off" autofocus
                                                       placeholder="Password" name="passwordlargelogin" id="passwordlargelogin" maxlength="10" 
                                                       onkeypress="checkkeysub('buttonsubmitlargelogin', event);"/> 
                                            </div>
                                        </div>
                                        <div class="col-md-2" style="text-align: right;">
                                            <button id="buttonsubmitlargelogin" 
                                                    type="submit" class="btn btn-outline blue" 
                                                    onclick="return checkvaluepass('passwordlargelogin', '<%=pswx%>', 'largelogin', 'errorlargelogin');">
                                                <i class="fa fa-arrow-right"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-10">
                                        <div class="alert alert-danger" id="errorlargelogin" style="display: none; text-align: left;">
                                            Error. The password is wrong.
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <a class="btn btn-outline btn-icon-only red tooltips" title="Return Homepage" data-placement="bottom" href="index.jsp"><i class="fa fa-home"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>





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

            <div class="modal fade" id="errorlargeoccup" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-full">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title font-red uppercase"><b>Error message</b></h4>
                        </div>
                        <div class="modal-body" id="errorlargeoccuptext">ERROR</div>
                        <div class="modal-footer">
                            <button type="button" class="btn red btn-outline" onclick="self.close();"><i class="fa fa-window-close-o"></i> Close Window</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>

            <div class="modal fade" id="infolarge" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                            <h4 class="modal-title font-blue-dark uppercase"><b>Information message</b></h4>
                        </div>
                        <div class="modal-body" id="infolargetext">INFO</div>
                        <div class="modal-footer">
                            <button type="button" class="btn dark btn-outline" onclick="return dismiss('infolarge');" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
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
                            <h3 class="page-title">Transaction <small><b>No Change</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                        <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                    </div>
                    <%if (!iscentral) {%>    
                    <%if (listTill.isEmpty()) { %>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="alert alert-danger">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> The operation could not be completed. No till opened. 
                            </div>
                        </div>
                    </div>     


                    <%} else {%>

                    <%
                        String esito = request.getParameter("esito");
                        if (esito == null) {
                            esito = "";
                        }
                        String classal = "alert-info";
                        String classfa = "fa-exclamation-triangle";
                        String msg = "Warning";
                        String msg1 = "No operation";
                        if (esito.startsWith("false")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Please try again.";
                        } else if (esito.equals("1A")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Total value is zero or null. Check it and try again.";
                        } else if (esito.equals("2A")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. There was an error in entering data value. Try again.";
                        } else if (esito.equals("1Q")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Actual quantity/total selected is less then the entered value. Check it and try again.";
                        } else if (esito.equals("OK")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";

                        }%>

                    <%if (!esito.equals("")) {%>
                    <div class="row">
                        <div class="col-md-12 ">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <%}%>


                    <form action="Operazioni?type=nc_tr" method="post" name="f1" id="f1" onsubmit="return subform();" enctype="multipart/form-data">
                        <input type="hidden" id="nc_kind_h" />
                        <input type="hidden" id="nc_type_h" />
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Category <span class='font-red'>*</span></label>
                                    <select class="form-control select2" id="nc_cat1" name="nc_cat1" placeholder="..." onchange="return chsel1();" allowclear>
                                        <option value=""></option>
                                        <%for (int i = 0; i < array_nc_cat.size(); i++) {%>
                                        <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Causal <span class='font-red'>*</span></label>
                                    <select class="form-control select2" id="nc_caus1" name="nc_caus1" placeholder="..." onchange="return changecausal();">
                                    </select>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Id open Safe/Till</label>
                                    <input type="text" class="form-control" id="id_open_till" disabled /> 
                                    <input type="hidden" id="id_open_till_v" name="id_open_till" /> 
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Safe/Till <span class='font-red'>*</span></label><p class='ab'></p>
                                    <select class="form-control select2" id="till" name="till" onchange="return chidopentill();">

                                    </select>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div id="ex1" style="display: none;">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>MTCN</label>
                                        <input type="text" class="form-control" name="mtcn0" id="mtcn0" maxlength="100" onkeypress="return keysub(this, event);" > 
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>WU Net</label>
                                        <input type="text" class="form-control inputright" name="wunet0" id="wunet0" onkeypress="return keysub(this, event);" onchange="return changequan('wunet0');" > 
                                    </div>
                                </div>
                            </div>
                            <div id="ex2" style="display: none;">
                                <div class="clearfix"></div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>WU Commission</label>
                                        <input type="text" class="form-control inputright" name="wucom0" id="wucom0" value="0<%=decimal%>00" onkeypress="return keysub(this, event);" onchange="return changequancom('wucom0', 'wunet0');"> 
                                    </div>
                                </div>
                            </div>
                            <div id="ex3" style="display: none;">          
                                <div class="clearfix"></div>
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label id="labrec">Receipt</label>
                                        <input type="text" class="form-control inputright" 
                                               onchange="return formatValueINT_1_change(this, separatorthousand, separatordecimal);"
                                               name="recei0" id="recei0" maxlength="100"
                                               onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <div id="ex3A" style="display: none;">          
                                <div class="clearfix"></div>
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label id="quantlab">Quantity</label> 
                                        <div class="pull-right" id="ex9"></div>
                                        <input type="text" class="form-control inputright" name="quantity0" id="quantity0"  
                                               onchange="return changequan('quantity0');"onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <div id="ex3B" style="display: none;">          
                                <div class="clearfix"></div>
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Price</label>
                                        <input type="text" class="form-control inputright" name="price0" id="price0" value="0<%=decimal%>00" onchange="return changeprice();"onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <div id="ex3BA">          
                                <div class="clearfix"></div>
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Percent IVA</label>
                                        <input type="text" class="form-control inputright" 
                                               name="piva0" id="piva0"
                                               onkeypress="return keysub(this, event);" readonly="readonly"/> 
                                    </div>
                                </div>
                            </div>

                            <div id="ex3C" style="display: none;">          
                                <div class="clearfix"></div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Fee &#37;</label>
                                        <input type="text" class="form-control inputright" name="rights0" id="rights0" value="0<%=decimal%>00" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Fee Value</label>
                                        <input type="text" class="form-control inputright" name="rights1" id="rights1" value="0<%=decimal%>00" 
                                               onkeypress="return keysub(this, event);" 
                                               onchange="return modfee();"> 
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Fee Total</label>
                                        <input type="text" class="form-control inputright" name="rights2" id="rights2" value="0<%=decimal%>00" 
                                               onkeypress="return keysub(this, event);"  readonly="readonly"> 
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
                                                    <b id="totalsign"></b> <b id="total0">0<%=decimal%>00</b>
                                                </span> 
                                                <input type="hidden" id="total1" name="total1" value="0<%=decimal%>00"/>
                                                <input type="hidden" id="totalsign1" name="totalsign1" value=""/>
                                            </h3>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="clearfix"></div>
                            <hr>
                            <div id="ex8" style="display: none;">          
                                <div class="clearfix"></div>
                                <p class='ab'></p>   
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Identification Code</label>
                                        <input type="text" class="form-control" name="ass_idcode" id="ass_idcode"onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Start Date</label>
                                        <input type="text" class="form-control date-picker" name="ass_startdate" id="ass_startdate"onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>End Date</label>
                                        <input type="text" class="form-control date-picker" name="ass_enddate" id="ass_enddate"onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>    
                            <div id="ex4" style="display: none;">          
                                <div class="clearfix"></div>
                                <p class='ab'></p>
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Customs</label>
                                        <select class="form-control select2" id="customs0" name="customs0" placeholder="...">
                                            <option value="---">---</option>
                                            <option value="00">Customs</option>
                                            <option value="01">No Customs</option>
                                        </select>
                                    </div>
                                </div>
                            </div>          

                            <div id="ex5" style="display: none;">
                                <div class="clearfix"></div>
                                <p class='ab'></p>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Surname <span class="font-red">*</span></label>
                                        <input type="text" class="form-control" id="heavy_surname" name="heavy_surname" maxlength="100" onkeypress="return keysub(this, event);"  onkeyup="return fieldNameSurnameNEW(this.id);"> 
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Name <span class="font-red">*</span></label>
                                        <input type="text" class="form-control" id="heavy_name" name="heavy_name" maxlength="100" onkeypress="return keysub(this, event);" onkeyup="return fieldNameSurnameNEW(this.id);"> 
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label>Address <span class="font-red">*</span></label>
                                        <input type="text" class="form-control" id="heavy_addr" name="heavy_addr" maxlength="100" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>City <span class="font-red">*</span></label>
                                        <input type="text" class="form-control" id="heavy_city" name="heavy_city" maxlength="100" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Country <span class="font-red">*</span></label>
                                        <select class="form-control select2" id="heavy_country" name="heavy_country">
                                            <option value="" ></option>
                                            <%for (int i = 0; i < array_country.size(); i++) {%>
                                            <option value="<%=array_country.get(i)[0]%>"><%=array_country.get(i)[1]%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                </div>
                                <div id="ex7" style="display: none;">    
                                    <div class="clearfix"></div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Zip Code <span class="font-red">*</span></label>
                                            <input type="text" class="form-control" id="heavy_zipcode" name="heavy_zipcode" maxlength="100"onkeypress="return keysub(this, event);"> 
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>District <span class="font-red">*</span></label>
                                            <input type="text" class="form-control" id="heavy_district" name="heavy_district" maxlength="100"onkeypress="return keysub(this, event);"> 
                                        </div>
                                    </div>
                                </div>    

                                <div class="clearfix"></div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Email</label>
                                        <input type="text" class="form-control" id="heavy_email" name="heavy_email" maxlength="100"onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Phone Number</label>
                                        <input type="text" class="form-control" id="heavy_phone" name="heavy_phone" 
                                               maxlength="100"onkeypress="return keysub(this, event);" onchange="return fieldOnlyNumber(this.id);"> 
                                    </div>
                                </div>
                            </div>

                            <div class="clearfix"></div>
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Note</label>
                                    <input type="text" class="form-control" name="note0" id="note0" 
                                           maxlength="100" onkeypress="return keysub(this, event);" onchange="return fieldNOSPecial_2(this.id);"/>  
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-6"id="ex6A">
                                <div class="form-group">
                                    <label>Payment mode <span class="font-red">*</span></label>
                                    <select class="form-control select2" id="kind_1" name="kind_1" placeholder="..." onchange="return changepaymentmode();">
                                        <option value="">None</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6" id="ex6">
                                <label>Pos</label>
                                <select class="form-control select2" id="pos_1" name="pos_1" placeholder="...">
                                    <%for (int i = 0; i < array_credit_card.size(); i++) {%>
                                    <option value="<%=array_credit_card.get(i)[0]%>"><%=array_credit_card.get(i)[1]%></option>
                                    <%}%>
                                </select>
                                <p class='ab'></p>
                            </div>
                            <div class="col-md-6" id="ex6C">
                                <label>Bank</label>
                                <select class="form-control select2" id="ban_1" name="ban_1" placeholder="...">
                                    <%for (int i = 0; i < array_bankacc.size(); i++) {%>
                                    <option value="<%=array_bankacc.get(i)[0]%>"><%=array_bankacc.get(i)[1]%></option>
                                    <%}%>
                                </select>
                                <p class='ab'></p>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-6" id="ex6B">
                                <label>CC Number <span class="font-red" id="ccobbl">*</span></label>
                                <input type="text" class="form-control" id="posnum" 
                                       name="posnum" maxlength="4" onkeyup="return fieldOnlyNumber(this.id);"/>
                            </div>
                            <p class='ab'></p>

                            <div class="clearfix"></div>

                            <div class="col-md-6" id="exF" style="display: none;">
                                <label>Identity Document <span class="font-red">*</span></label><p class='ab'></p>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="input-group input-large">
                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                            <i class="fa fa-file-pdf-o fileinput-exists"></i>&nbsp;
                                            <span class="fileinput-filename"> </span>
                                        </div>
                                        <span class="input-group-addon btn default btn-file">
                                            <span class="fileinput-new"> Select file </span>
                                            <span class="fileinput-exists"> Change </span>
                                            <input type="file" name="file" id="fileUpload" accept=".jpg,.pdf,.jpeg"> </span>
                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> Remove </a>
                                    </div>
                                </div>
                                <p class='ab'></p>
                            </div>
                            <%if (Constant.is_IT) {%>
                            <div class="clearfix"></div>
                            <p class='ab'></p>
                            <hr>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Loyalty Code</label>
                                    <input type="text" class="form-control" id="loya" name="loya" 
                                           maxlength="8" 
                                           onkeyup="return modificaLOY(this, event);"
                                           onkeypress="return keysub(this, event);"/> 
                                </div>
                            </div>
                            <%} else {%>
                            <input type="hidden" id="loya" name="loya" value="" /> 
                            <%}%>
                            <div class="clearfix"></div>
                            <p class='ab'></p>
                            <input type="hidden" name="scontr2" id="scontr2" value="NO"/>
                            <div id="scontr1">
                                <hr>
                                <div class="col-md-12">
                                    <div class="alert alert-warning">
                                        <b class="uppercase">this transaction will generate a fiscal receipt.</b>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <p class='ab'></p>
                            </div>
                            <hr>
                            <div class="col-md-12" id="submittra">
                                <center><button type="submit" class="btn btn-lg green-jungle btn-block" id="butconf"><i class="fa fa-save"></i> Confirm Transaction</button></center>
                            </div>                            
                        </div>  
                    </form>

                    <%}%>
                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>000 - Head Office can not perform transaction operation.</strong>
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
        <!-- BEGIN CORE PLUGINS -->

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
