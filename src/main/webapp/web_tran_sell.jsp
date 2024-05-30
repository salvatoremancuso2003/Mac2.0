<%@page import="rc.so.entity.Booking"%>
<%@page import="rc.so.entity.BlacklistM"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.NC_causal"%>
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
        <title>Mac2.0 - Website Booking SELL</title>
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

        <script src="assets/soop/js/pace.js" type="text/javascript"></script>
        <link rel="stylesheet" href="assets/soop/css/pace-theme-center-circle-dark.css" />




        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>
        <script src="assets/soop/js/core-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5.js" type="text/javascript"></script>
        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <script src="assets/soop/js/cf.js" type="text/javascript"></script>
        <script src="assets/soop/js/validate.min.js" type="text/javascript"></script>

        <script type="text/javascript" src="assets/soop/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>

        <%
            boolean iscentral = Engine.isCentral();
            //iscentral = false;
            String esito = (String) session.getAttribute("esito_s");
            if (esito == null) {
                esito = "none";
            }
            session.setAttribute("esito_s", null);

            String msgbl1 = "";
            if (session.getAttribute("msgbl1") != null) {
                msgbl1 = session.getAttribute("msgbl1").toString();
                session.setAttribute("msgbl1", null);
            }

            String pswx = session.getAttribute("us_pwd").toString();
            String nation = Constant.nation;
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;

            List_new ln = new List_new("S", session);
            String Token = Utility.generaId(50);
            ArrayList<String[]> array_intbook = Engine.list_internetbooking();
            Office head = ln.getHeadhoffice();
            String operator = head.getChangetype();
            String local_cur = ln.getLocalcur()[0];
            ArrayList<String[]> array_till = ln.getArray_till();
            ArrayList<Till> array_till_open = ln.getArray_till_open();
            ArrayList<CustomerKind> array_custkind = ln.getArray_custkind();
            ArrayList<String[]> array_country = ln.getArray_country();
            ArrayList<String[]> array_country_1 = ln.getCountryFlag("1");
            ArrayList<String[]> array_country_2 = ln.getCountryFlag("2");
            ArrayList<String[]> array_country_3 = ln.getCountryFlag("3");
            ArrayList<String[]> array_identificationCard = ln.getArray_identificationCard();
            Booking bo = Engine.get_prenot(request.getParameter("cod"));
            ArrayList<String[]> array_credit_card = Engine.credit_card_enabled_WEB(bo);
            ArrayList<Company> array_listCompany = ln.getArray_listCompany();
            ArrayList<String[]> array_kind_pay = Engine.kind_payment_WEB(bo.getCanale());
            ArrayList<Figures> array_figsell = ln.getArray_figsell();
            ArrayList<Currency> array_currency = ln.getArray_currency();
            ArrayList<String[]> array_bankacc = Engine.list_bankAccount();
            ArrayList<BlacklistM> array_bl = ln.getArray_blm();
            ArrayList<String[]> array_district = ln.getArray_district();

            String linkfisso_scanner = Utility.createServerReturnScannerLink(bo.getCod_tr());
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
                    ;
                }
                return str.join('');
            }

            function modificaOAMSurname(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase());
                fieldNameSurnameNEW(field.id);
            }
            function modificaOAM_soloap(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase());
                field.value = field.value.replace(/[`~!@£§°#$%^&*()_|+\-=?;:",.<>\{\}\[\]\\\/]/gi, '');
            }
            function modificaOAM_Add(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase());
                //field.value = field.value.replace(/[0-9]/g, '');
                field.value = field.value.replace(/[`~!@#$§°%^£&*()_|+\=?;:",.<>\{\}\[\]]/gi, '');
            }
            function modificaIB(field, event) {
                field.value = RemoveAccents(field.value);
                field.value = field.value.replace(/[^a-zA-Z0-9]/g, '');
                field.value = field.value.replace(/\\/g, "");
            }

            function modificaOAM(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase());
                var specialChars = "~`!#$%^&*+=-[]();,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°èéòàù§*ç@|!£$%&/()=?^€'ì";
                for (var i = 0; i < specialChars.length; i++) {
                    field.value = field.value.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
                }
                field.value = field.value.replace(/\\/g, "");
                //fieldNOSPecial(field.id);
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



            function checkesito() {
                var es1 = '<%=esito%>';
                var msgbl1 = '<%=msgbl1%>';
                if (es1 !== "none") {
                    var ermsg = "";
                    if (es1 === "kobl") {
                        ermsg = "<span class='font-red'>Attention! </span>Not executable transaction. This name is present in the international blacklist." + msgbl1;
                    } else if (es1 === "koblm") {
                        var ms1 = "";
                        var cod1 = '<%=Utility.sanitize(request.getParameter("codbl"))%>';
            <%for (int i = 0; i < array_bl.size(); i++) {%>
                        var confr = '<%=array_bl.get(i).getCode()%>';
                        if (cod1 === confr) {
                            ms1 = "<%=Utility.correggiValueJS(array_bl.get(i).getText())%>";
                        }
            <%}%>
                        ermsg = ms1;
                    } else if (es1 === "koins2") {
                        ermsg = "You must select at least one row.";
                    } else if (es1 === "koquant") {
                        ermsg = "Quantity of figures exceeds the amount available in this till.";
                    } else if (es1 === "kofidelcodemax") {
                        ermsg = "Quantity exceeds the amount of fidelity code buy back.";
                    } else if (es1 === "kofidelcode") {
                        ermsg = "Fidelity code not found.";
                    } else if (es1 === "komaxsett") {
                        ermsg = "The max weekly threshold has been exceeded.";
                    } else if (es1 === "kofatt") {
                        ermsg = "Unable to generate invoice. Try again.";
                    } else if (es1 === "koins" || es1 === "koins1") {
                        ermsg = "Character not allowed: à è é ì ò ù. Please verify";
                    } else if (es1 === "co1") {
                        ermsg = "Error. This Unlock Code is not found or this code is already used.";
                    }

                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = ermsg;
                }
            }

            var separatordecimal = '<%=decimal%>';
            var separatorthousand = '<%=thousand%>';

            //  reset row

            function verificaonline() {
                var exit = false;
                $.ajax({
                    async: false,
                    type: "POST",
                    url: "Query?type=verificaonline",
                    success: function (data) {
                        if (data !== "") {
                            exit = data === "true";
                        }
                    }
                });
                console.log(exit);
                return exit;
            }

            function checkquantitytill_SELL() {
                var par2 = "";
                var i = 1;
                var trfig1 = document.getElementById('trfig' + i).style.display;
                if (trfig1 === "") {
                    var kinfig = document.getElementById('kind' + i).value.trim();
                    var codfig = document.getElementById('figs' + i).value.trim();
                    var quafig = document.getElementById('quantity' + i).value.trim();
                    par2 += "&kinfig" + i + "=" + kinfig + "&codfig" + i + "=" + codfig + "&quafig" + i + "=" + quafig;
                }
                var exit = false;
                $.ajax({
                    async: false,
                    type: "POST",
                    url: "Query?type=checkquantitytill_SELL&idopentillv=" + document.getElementById('idopentillv').value.trim() + par2,
                    success: function (data) {
                        if (data !== "") {
                            exit = data === "true";
                        }
                    }
                });
                return exit;
            }

            function resetRow(index) {
                var quant = "quantity" + index;
                var kind = "kind" + index;
                var figures = "figs" + index;
                var rate = "rate" + index;
                var fieldrate = "fieldrate" + index;
                var fx_com = "fxcomm" + index;
                var kindfixcomm1 = "kindfixcomm" + index;
                var comm = "comperc" + index;
                var totperc = "totperc" + index;
                var totcomm = "totcomm" + index;
                var net = "net" + index;
                var total = "total" + index;
                var justify = "lowcommjus" + index;
                var bb = "bb" + index;
                var spread = "spread" + index;
                $('#' + kind).html('').select2({data: [{id: '', text: ''}]});
                document.getElementById(kind).disabled = true;
                $('#' + figures).html('').select2({data: [{id: '', text: ''}]});
                document.getElementById(figures).disabled = true;
                $('#' + rate).html('').select2({data: [{id: '', text: ''}]});
                document.getElementById(rate).disabled = true;
                $('#' + kindfixcomm1).html('').select2({data: [{id: '', text: ''}]});
                document.getElementById(kindfixcomm1).disabled = true;
                document.getElementById(justify).disabled = true;
                document.getElementById(quant).value = '';
                document.getElementById(quant).disabled = true;
                document.getElementById(spread).value = '';
                document.getElementById(spread).disabled = true;
                document.getElementById(fieldrate).value = '';
                document.getElementById(fieldrate).disabled = true;
                document.getElementById(comm).value = '';
                document.getElementById(comm).disabled = true;
                document.getElementById(totperc).value = '';
                document.getElementById(totperc).disabled = true;
                document.getElementById(fx_com).value = '';
                document.getElementById(fx_com).disabled = true;
                document.getElementById(totcomm).value = '';
                document.getElementById(totcomm).disabled = true;
                document.getElementById(net).value = '';
                document.getElementById(net).disabled = true;
                document.getElementById(total).value = '';
                document.getElementById(total).disabled = true;
                document.getElementById(bb).checked = false;
                document.getElementById(bb).disabled = true;
                document.getElementById("delrow" + index).style.display = "none";
                document.getElementById("enarow" + index).style.display = "";
                document.getElementById("trfig" + index).style.background = "#eef1f5";
                setvalue(index);
            }

            function enableRow(index) {
                var quant = "quantity" + index;
                var kind = "kind" + index;
                var figures = "figs" + index;
                var rate = "rate" + index;
                var fieldrate = "fieldrate" + index;
                var fx_com = "fxcomm" + index;
                var kindfixcomm1 = "kindfixcomm" + index;
                var comm = "comperc" + index;
                var totperc = "totperc" + index;
                var totcomm = "totcomm" + index;
                var net = "net" + index;
                var total = "total" + index;
                var justify = "lowcommjus" + index;
                var bb = "bb" + index;
                var spread = "spread" + index;

                document.getElementById(kind).disabled = false;
                $('#' + kind).empty();
                document.getElementById(figures).disabled = false;
                $('#' + figures).empty();
                document.getElementById(rate).disabled = false;
                document.getElementById(kindfixcomm1).disabled = false;
                $('#' + kindfixcomm1).empty();

                document.getElementById(justify).disabled = true;
                document.getElementById(quant).disabled = false;
                document.getElementById(quant).value = '1' + separatordecimal + '00';
                document.getElementById(spread).value = '0' + separatordecimal + '00';
                document.getElementById(fieldrate).disabled = false;
                document.getElementById(fieldrate).value = '0' + separatordecimal + '00';
                document.getElementById(comm).disabled = false;
                document.getElementById(comm).value = '0' + separatordecimal + '00';
                document.getElementById(totperc).disabled = false;
                document.getElementById(totperc).value = '0' + separatordecimal + '00';
                document.getElementById(fx_com).disabled = false;
                document.getElementById(fx_com).value = '0' + separatordecimal + '00';
                document.getElementById(totcomm).disabled = false;
                document.getElementById(totcomm).value = '0' + separatordecimal + '00';
                document.getElementById(net).disabled = false;
                document.getElementById(net).value = '0' + separatordecimal + '00';
                document.getElementById(total).disabled = false;
                document.getElementById(total).value = '0' + separatordecimal + '00';
                document.getElementById(bb).disabled = false;


                document.getElementById("delrow" + index).style.display = "";
                document.getElementById("enarow" + index).style.display = "none";
                document.getElementById("trfig" + index).style.background = "#ffffff";

                loadkind(index);
                loadfigs(index);
                loadperccomm(index);
                kindfixcomm(index);
                changefigs(index);
                changequantity(index);
                setvalue(index);

            }
            //  

            function scandoc1() {
                var numsc = parseInt(document.getElementById("numscans").value) + 1;
                document.getElementById("numscans").value = numsc;
                var obj = {};
                obj.Command = "SCAN";
                //obj.Command = "DEMO";
                obj.CallbackUrl = "<%=Utility.sanitize(linkfisso_scanner)%>";
                obj.Counter = numsc;
                obj.Token = "<%=Token%>";
                //obj.Token = "1234567890AAA";
                obj.trid = "<%=Utility.sanitize(bo.getCod_tr())%>";
                obj.ipad = document.getElementById("ipaddress").value;
                console.log(JSON.stringify(obj));
                $.post("http://localhost:9000/scan",
                        JSON.stringify(obj),
                        function (data, status) {
                            $.ajax({
                                type: "POST",
                                url: "Scan?type=scanner",
                                contentType: "application/json; charset=utf-8",
                                dataType: "json",
                                data: JSON.stringify(data),
                                success: function (data) {
                                    document.getElementById('closescanwaiting').click();
                                    jQuery(data).each(function (i, item) {
                                        if (item.Success === "true") {
                                            setfield("heavy_pob_country", item.heavy_pob_country);
                                            setfield("heavy_numberidentcard", item.heavy_numberidentcard);
                                            setfield("heavy_surname", item.heavy_surname);
                                            setfield("heavy_name", item.heavy_name);
                                            setfield("heavy_address", item.heavy_address);
                                            setfield("heavy_zipcode", item.heavy_zipcode);
                                            setfield("heavy_pob_city", item.heavy_pob_city);
                                            setfield("heavy_identcard", item.heavy_identcard);
                                            setfield("heavy_sex", item.heavy_sex);
                                            setfield("heavy_pob_district_STR", item.heavy_pob_district_STR);
                                            setfield("heavy_issuedplaceidentcard", item.heavy_issuedplaceidentcard);
                                            setfield("heavy_issuedbyidentcard", item.heavy_issuedbyidentcard);
                                            setfield("heavy_city_dis", item.heavy_city_dis);
                                            setfield("heavy_district_dis", item.heavy_district_dis);
                                            setfieldDP("heavy_issuedateidentcard", item.heavy_issuedateidentcard);
                                            setfieldDP("heavy_pob_date", item.heavy_pob_date);
                                            setfieldDP("heavy_exdateidentcard", item.heavy_exdateidentcard);
                                            document.getElementById('closescanwaiting').click();
                                            document.getElementById('divscan1').style.display = "none";
                                            document.getElementById('divscan2').style.display = "";
                                            $("#btscdoc").removeClass("dark");
                                            $("#btscdoc").addClass("green-jungle");
                                            $('#btscdoc').attr("data-original-title", "View scanned document");
                                            verificaclient();
                                        } else {
                                            document.getElementById('closescanwaiting').click();
                                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                            document.getElementById('errorlarge').style.display = "block";
                                            document.getElementById('errorlargetext').innerHTML = "ERROR DURING SCAN. " + item.Error;
                                            $("#btscdoc").addClass("dark");
                                            $("#btscdoc").removeClass("green-jungle");
                                            $('#btscdoc').attr("data-original-title", "Scan document");
                                        }
                                    });
                                }
                            });
                        }).error(function (jqXHR, textStatus, errorThrown) {
                    document.getElementById('closescanwaiting').click();
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "ERROR DURING SCAN. APPLICATION ERROR, CONNECTION REFUSED.";
                    $("#btscdoc").addClass("dark");
                    $("#btscdoc").removeClass("green-jungle");
                    $('#btscdoc').attr("data-original-title", "Scan document");
                });
            }

            function viewdocscanned() {
                var url = 'Download?type=view_doc_tr&cod=<%=Token%>';
                var win = window.open(url, '_blank');
                win.focus();
            }

            function scandoc2() {
                document.getElementById("numscans").value = "0";
                $.ajax({
                    async: false,
                    type: "POST",
                    url: 'Scan?type=removescannerdocument&codtr_del=<%=Utility.sanitize(bo.getCod_tr())%>&coddoc_del=<%=Token%>',
                    success: function (data) {
                        resetfield("heavy_pob_country");
                        resetfield("heavy_numberidentcard");
                        resetfield("heavy_surname");
                        resetfield("heavy_name");
                        resetfield("heavy_address");
                        resetfield("heavy_zipcode");
                        resetfield("heavy_pob_city");
                        resetfield("heavy_identcard");
                        resetfield("heavy_sex");
                        resetfield("heavy_pob_district");
                        resetfield("heavy_pob_district_STR");
                        resetfield("heavy_issuedplaceidentcard");
                        resetfield("heavy_issuedateidentcard");
                        resetfield("heavy_issuedbyidentcard");
                        resetfield("heavy_city_dis");
                        resetfield("heavy_district_dis");
                        resetfield("heavy_pob_date");
                        resetfield("heavy_exdateidentcard");
                        $("#btscdoc").addClass("dark");
                        $("#btscdoc").removeClass("green-jungle");
                        $('#btscdoc').attr("data-original-title", "Scan document");
                        document.getElementById('divscan1').style.display = "";
                        document.getElementById('divscan2').style.display = "none";
                    }
                });
            }


            function verificaSblocco(modal) {
                document.getElementById('unlockCode_final').value = '-';
                document.getElementById('typerate').value = 'NORMAL';
                var unlockCode = document.getElementById("unlockCode").value.trim();
                //ind = parseInt(ind);
                document.getElementById("unlockCode").value = '';
                dismiss(modal);
                $.ajax({
                    type: "POST",
                    url: "Query?type=checkunlockcode&q=" + unlockCode,
                    success: function (data) {
                        if (data === "true") {
                            for (var i = 1; i <= 6; i++) {
                                if (document.getElementById("fieldrate" + i) !== null) {
                                    document.getElementById("fieldrate" + i).style.display = "";
                                    document.getElementById("fieldrate" + i).value = "99" + separatorthousand + "999" + separatordecimal + "9999";
                                }
                                if (document.getElementById("divrate" + i) !== null) {
                                    document.getElementById("divrate" + i).style.display = "none";
                                }
                            }
                            document.getElementById('unlockCode_final').value = unlockCode;
                            document.getElementById('typerate').value = 'UNLOCK';
                            document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                            document.getElementById('infolarge').style.display = "block";
                            document.getElementById('infolargetext').innerHTML = "The rate has been successfully unlocked. Please, modify the 'Rate' using the correct one.";
                        } else {

                            document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                            document.getElementById('infolarge').style.display = "block";
                            document.getElementById('infolargetext').innerHTML = 'This Unlock Code is not found or this code is already used.';
                        }
                    }
                });
            }

            function changetill() {
                var v1 = document.getElementById('till').value;
            <%for (int j = 0; j < array_till_open.size(); j++) {%>
                if (v1 === "<%=array_till_open.get(j).getCod()%>") {
                    document.getElementById('idopentill').value = padDigits('<%=array_till_open.get(j).getCod_opcl()%>', 15);
                    document.getElementById('idopentillv').value = '<%=array_till_open.get(j).getId_opcl()%>';
                }
            <%}%>
//                document.getElementById('idopentill').value = getRandom(5);
            }

            function getRandom(length) {
                return Math.floor(Math.pow(10, length - 1) + Math.random() * 9 * Math.pow(10, length - 1));
            }

            function checkcodfisc() {
                var er = false;
                var ermsg = "";
                var cf = document.getElementById("heavy_codfisc").value.trim().toUpperCase();
                document.getElementById("heavy_codfisc").value = cf;
                if (cf === "") {
                    return false;
                }

                var surname = document.getElementById("heavy_surname").value.trim().toUpperCase();
                var name = document.getElementById("heavy_name").value.trim().toUpperCase();
                var checksurname = getStringSurname(surname);
                var checkname = getStringName(name);
                if (checkCF(cf)) {
                    if (checksurname !== 'XXX') {
                        if (cf.substring(0, 3) !== checksurname) {
                            er = true;
                            ermsg = ermsg + "Surname does not conform<p class='ab'></p>";
                        }
                    }
                    if (checkname !== 'XXX') {
                        if (cf.substring(3, 6) !== checkname) {
                            er = true;
                            ermsg = ermsg + "Name does not conform<p class='ab'></p>";
                        }
                    }
                } else {
                    er = true;
                    ermsg = ermsg + "Tax Code Error<p class='ab'></p>";
                }

                if (er) {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = ermsg;
                    return false;
                } else {
                    unlockheavy();
                    document.getElementById('errorlargetext').innerHTML = "ERROR";
                    var sex = "";
                    var dn = cf.substring(9, 11).trim();
                    if (dn < 32) {
                        sex = "M";
                    }
                    if (dn > 40) {
                        sex = "F";
                        dn = dn - 40;
                        if (dn < 10) {
                            dn = "0" + dn;
                        }
                    }


                    var an = cf.substring(6, 8).trim();
                    var st1 = "19";
                    $.ajax({
                        async: false,
                        type: "POST",
                        url: "Operazioni_test?type=vercfanno&anno=" + an,
                        success: function (data) {
                            st1 = data;
                        }
                    });


                    var mo = cf.substring(8, 9).trim();
                    var datebirth = dn + "/" + formatMonthcf(mo) + "/" + st1 + an;
                    var codcom = cf.substring(11, 15).trim().toUpperCase();
                    var country = "";
                    var city = "";
                    var district = "---";
                    var found_country = false;
                    var found_city = false;
                    if (codcom.length > 1) {
                        var start = codcom.substring(0, 1).trim();
                        if (start === "Z") {
                            $.ajax({
                                async: false,
                                type: "POST",
                                url: "Query?type=querycountry&q=" + codcom,
                                success: function (data) {
                                    country = data;
                                    district = "EE";
                                    document.getElementById('heavy_pob_district_div').style.display = "";
                                    document.getElementById("heavy_pob_district_STR").style.display = "none";
                                    document.getElementById("heavy_pob_district_STR").value = "";
                                    $('#heavy_pob_country').val(country).change();
                                    document.getElementById("heavy_pob_city").value = $('#heavy_pob_country :selected').text();
                                    $("#heavy_pob_city").attr('readonly', 'readonly');
                                }
                            });
                        } else {
                            country = '<%=ln.getNazioneCodice("ITALIA")%>';
                            // add 06102023
                            var v1 = document.getElementById("customerKind").value;
                            if(v1==="002"){
                                $('#heavy_country').val(country).change();
                            }

                            $.ajax({
                                async: false,
                                type: "POST",
                                url: "Query?type=querycodcom_city&q=" + codcom,
                                success: function (data) {
                                    city = data;
                                    document.getElementById("heavy_pob_city").value = city;
                                    $("#heavy_pob_city").attr('readonly', 'readonly');
                                }
                            });
                            $.ajax({
                                async: false,
                                type: "POST",
                                url: "Query?type=querycodcom_distr&q=" + codcom,
                                success: function (data) {
                                    district = data;
                                    document.getElementById('heavy_pob_district_div').style.display = "";
                                    document.getElementById("heavy_pob_district_STR").style.display = "none";
                                    document.getElementById("heavy_pob_district_STR").value = "";
                                }
                            });
                        }
                    }
                    $('#heavy_sex').val(sex).change();
                    $('#heavy_pob_date').datepicker().datepicker('setDate', datebirth);
                    $("#heavy_pob_date").attr('readonly', 'readonly');
                    $('#heavy_pob_country').val(country).change();
                    $('#heavy_pob_district').val(district).change();
                    unlockheavy();
                    disable_sel2('heavy_sex', 'form1');
                    disable_sel2('heavy_pob_district', 'form1');
                    disable_sel2('heavy_pob_country', 'form1');
                }



                verificaclient();
            }

            function loadtill() {
            <%for (int j = 0; j < array_till_open.size(); j++) {

                    if (!array_till_open.get(j).isSafe()) {
            %>

                var o = $("<option/>", {value: "<%=array_till_open.get(j).getCod()%>", text: "<%=Utility.formatAL(array_till_open.get(j).getCod(), array_till, 1)%>"});
                $('#till').append(o);
            <%}
                }%>
                $('#till').val($('#till option:first-child').val()).trigger('change');
            }

            function loadkind(index) {
                var kind_value = document.getElementById('customerKind').value;
                var kindres = "0";
            <%for (int i = 0; i < array_custkind.size(); i++) {%>
                var sc = '<%=array_custkind.get(i).getTipologia_clienti()%>';
                if (sc === kind_value) {
                    kindres = '<%=array_custkind.get(i).getFg_nazionalita()%>';
                }
            <%}%>
                var kind = 'kind' + index;
                $('#' + kind).select2({
                    dropdownAutoWidth: true
                });
                $('#' + kind).empty();

            <%for (int i = 0; i < array_figsell.size(); i++) {%>
                var o = $("<option/>", {value: "<%=array_figsell.get(i).getSupporto()%>", text: "<%=array_figsell.get(i).getDe_supporto()%>"});
                if (kindres === "1") {
                    if (kindres === "<%=array_figsell.get(i).getResidenti()%>") {
                        $('#' + kind).append(o);
                    }
                } else {
                    $('#' + kind).append(o);
                }
            <%}%>
                $('#' + kind).val($('#' + kind + ' option:first-child').val()).trigger('change');
                document.getElementById("quantity" + index).disabled = false;
                document.getElementById("quantity" + index).value = '1' + separatordecimal + '00';
            }

            function loadfigs(index) {
                var figs = 'figs' + index;
                $('#' + figs).select2({
                    dropdownAutoWidth: true
                });

            <%for (int i = 0; i < array_currency.size(); i++) {%>



                var o = $("<option/>", {value: "<%=array_currency.get(i).getCode()%>", text: "<%=array_currency.get(i).getCode()%> - <%=array_currency.get(i).getDescrizione()%>"});

                        var fgeur = '<%=array_currency.get(i).getInternal_cur()%>';
                        if (fgeur === '1') {
                        } else {
                            $('#' + figs).append(o);
                        }

            <%}%>
                        $('#' + figs).val($('#' + figs + ' option:first-child').val()).trigger('change');

                    }

                    function blockheavy() {
                        $('#heavy_sex').val(' ').trigger('change');
                        disable_sel2('heavy_sex', 'form1');
                        document.getElementById('heavy_city').disabled = true;
                        $('#heavy_city').val(' ').trigger('change');
                        document.getElementById('heavy_address').disabled = true;
                        document.getElementById('heavy_zipcode').disabled = true;
                        document.getElementById('heavy_district').disabled = true;
                        $('#heavy_district').val(' ').trigger('change');
                        $("#heavy_pob_city").attr('readonly', 'readonly');
                        $('#heavy_pob_district').val(' ').trigger('change');
                        $('#heavy_pob_country').val(' ').trigger('change');
                        disable_sel2('heavy_pob_country', 'form1');
                        $("#heavy_pob_date").attr('readonly', 'readonly');
                        $('#heavy_identcard').val(' ').trigger('change');
                        disable_sel2('heavy_identcard', 'form1');
                        $('#heavy_pepI').val(' ').trigger('change');
                        disable_sel2('heavy_pepI', 'form1');
                        document.getElementById('heavy_numberidentcard').disabled = true;
                        document.getElementById('heavy_issuedateidentcard').disabled = true;
                        document.getElementById('heavy_exdateidentcard').disabled = true;
                        document.getElementById('heavy_issuedbyidentcard').disabled = true;
                        document.getElementById('heavy_issuedplaceidentcard').disabled = true;
                    }

                    function unlockheavy() {

                        enable_sel2('heavy_sex', 'form1');
                        document.getElementById('heavy_city').disabled = false;
                        document.getElementById('heavy_address').disabled = false;
                        document.getElementById('heavy_zipcode').disabled = false;
                        document.getElementById('heavy_district').disabled = false;
                        $('#heavy_district').removeAttr('readonly');
                        $("#heavy_pob_city").removeAttr('readonly');
                        $("#heavy_pob_date").removeAttr('readonly');
                        enable_sel2('heavy_pob_country', 'form1');
                        enable_sel2('heavy_identcard', 'form1');
                        $('#heavy_pepI').val('NO').trigger('change');
                        enable_sel2('heavy_pepI', 'form1', true);
                        document.getElementById('heavy_numberidentcard').disabled = false;
                        document.getElementById('heavy_issuedateidentcard').disabled = false;
                        document.getElementById('heavy_exdateidentcard').disabled = false;
                        document.getElementById('heavy_issuedbyidentcard').disabled = false;
                        document.getElementById('heavy_issuedplaceidentcard').disabled = false;
                    }


                    function readDoc(modal) {
                        var docout = document.getElementById("documentReading").value;
                        if (docout.indexOf("PASSPORT") !== -1 || docout.indexOf("IDENTITY CARD") !== -1) { //PASSAPORTO - IDENTITY CARD
                            docout = docout.split("START")[1].trim();
                            var surname = docout.split("Surname:")[1].trim().split("\n")[0].trim();
                            var name = docout.split("Forenames:")[1].trim().split("\n")[0].trim();
                            var sex = docout.split("Gender:")[1].trim().split("\n")[0].trim().substring(0, 1);
                            var datebirth = convertDate_1(docout.split("Date of Birth:")[1].trim().split("\n")[0].trim());
                            var exD = convertDate_2(docout.split("Expiry Date:")[1].trim().split("\n")[0].trim());
                            var dnu = docout.split("Doc. Number:")[1].trim().split("\n")[0].trim();
                            var iss = docout.split("Issuing State:")[1].trim().split("\n")[0].trim();

            <%for (int i = 0; i < array_country.size(); i++) {%>
                            var alphacode = '<%=array_country.get(i)[2]%>';
                            if (alphacode === iss) {
                                iss = "<%=array_country.get(i)[1]%>";
                            }
            <%}%>



                            var naz = "---";
                            if (docout.indexOf("Nationality:") !== -1) {
                                naz = docout.split("Nationality:")[1].trim().split("\n")[0].trim();
                                var country = "";
                                var found_country = false;

            <%for (int i = 0; i < array_country.size(); i++) {%>
                                var alphacode = '<%=array_country.get(i)[2]%>';
                                if (alphacode === naz) {
                                    country = '<%=array_country.get(i)[0]%>';
                                    found_country = true;
                                }
            <%}%>

                                var ita = '<%=ln.getNazioneCodice("ITALIA")%>';

                                if (document.getElementById('heavy_country').value === ita) {
                                    country = ita;
                                }

                                if (country === ita) {
                                    document.getElementById("heavy_pob_district_STR").style.display = "none";
                                    document.getElementById("heavy_pob_district_STR").value = "";
                                    document.getElementById('heavy_pob_district_div').style.display = "";
                                } else {
                                    document.getElementById('heavy_pob_district_div').style.display = "none";
                                    document.getElementById("heavy_pob_district_STR").style.display = "";
                                    document.getElementById("heavy_pob_district_STR").value = "";
                                }
                            }
                            var doc = docout.split("Document:")[1].trim().split("\n")[0].trim();
                            var tdo = "";
                            var found_tdo = false;
            <%for (int i = 0; i < array_identificationCard.size(); i++) {%>
                            var robcode = '<%=array_identificationCard.get(i)[3]%>';
                            if (robcode === doc) {
                                tdo = '<%=array_identificationCard.get(i)[0]%>';
                                found_tdo = true;
                            }
            <%}%>

                            document.getElementById("heavy_surname").value = surname;
                            $('#heavy_sex').val(sex).change();
                            //disable_sel2('heavy_sex', 'form1');
                            document.getElementById("heavy_name").value = name;
                            //document.getElementById("heavy_pob_date").value = datebirth;
                            $('#heavy_pob_date').datepicker().datepicker('setDate', datebirth);
                            if (found_country) {
                                $('#heavy_pob_country').val(country).change();
                                $('#heavy_country').val(country).change();
                                //disable_sel2('heavy_pob_country', 'form1');
                            }
                            $('#heavy_identcard').val(tdo).change();
                            //disable_sel2('heavy_identcard', 'form1');
                            document.getElementById("heavy_numberidentcard").value = dnu;
                            //document.getElementById("heavy_exdateidentcard").value = exD;
                            $('#heavy_exdateidentcard').datepicker().datepicker('setDate', exD);

                            document.getElementById("heavy_issuedbyidentcard").value = iss;
                            document.getElementById("heavy_issuedplaceidentcard").value = iss;





                        } else {
                            //TESSERA SANITARIA
                            if (docout.indexOf("START") !== -1 && docout.indexOf("END") !== -1) {
                                docout = docout.split("START")[1].trim();
                                docout = docout.split("\n")[0].trim();
                                docout = docout.split("Codice")[1].trim();
                                docout = docout.split("fiscale:")[1].trim();
                                if (docout.length > 16) {
                                    var cf = docout.substring(0, 16).trim();
                                    var sex = "";
                                    var dn = cf.substring(9, 11).trim();
                                    if (dn < 32) {
                                        sex = "M";
                                    }
                                    if (dn > 40) {
                                        sex = "F";
                                        dn = dn - 40;
                                        if (dn < 10) {
                                            dn = "0" + dn;
                                        }
                                    }
                                    var an = cf.substring(6, 8).trim();
                                    var mo = cf.substring(8, 9).trim();
                                    var datebirth = dn + "/" + formatMonthcf(mo) + "/" + "19" + an;
                                    var namecompl = docout.substring(16).trim();
                                    var surname = namecompl.split("  ")[0].trim();
                                    var name = namecompl.split("  ")[1].trim();
                                    var codcom = cf.substring(11, 15).trim();
                                    var country = "";
                                    var city = "";
                                    var district = "";
                                    var found_country = false;
                                    var found_city = false;
                                    if (codcom.length > 1) {
                                        var start = codcom.substring(0, 1).trim();
                                        if (start === "Z") {
                                            $.ajax({
                                                type: "POST",
                                                url: "Query?type=querycountry&q=" + codcom,
                                                success: function (data) {
                                                    country = data;
                                                    $('#heavy_pob_country').val(country).change();
                                                    $('#heavy_pob_district').val('EE').change();
                                                    disable_sel2('heavy_pob_district', 'form1');
                                                    document.getElementById('heavy_pob_district_div').style.display = "";
                                                    document.getElementById("heavy_pob_district_STR").style.display = "none";
                                                    document.getElementById("heavy_pob_district_STR").value = "";
                                                }
                                            });
                                        } else {
                                            country = '<%=ln.getNazioneCodice("ITALIA")%>';
                                            $('#heavy_pob_country').val(country).change();
                                            $.ajax({
                                                type: "POST",
                                                url: "Query?type=querycodcom_city&q=" + codcom,
                                                success: function (data) {
                                                    city = data;
                                                    document.getElementById("heavy_pob_city").value = city;
                                                    $("#heavy_pob_city").attr('readonly', 'readonly');
                                                }
                                            });
                                            $.ajax({
                                                type: "POST",
                                                url: "Query?type=querycodcom_distr&q=" + codcom,
                                                success: function (data) {
                                                    district = data;
                                                    //disable_sel2('heavy_pob_district', 'form1');
                                                    document.getElementById('heavy_pob_district_div').style.display = "";
                                                    document.getElementById("heavy_pob_district_STR").style.display = "none";
                                                    document.getElementById("heavy_pob_district_STR").value = "";
                                                    $('#heavy_pob_district').val(district).change();
                                                }
                                            });
                                        }
                                    }
                                    document.getElementById("heavy_surname").value = surname;
                                    document.getElementById("heavy_name").value = name;
                                    document.getElementById("heavy_codfisc").value = cf;
                                    //document.getElementById("heavy_pob_date").value = datebirth;
                                    $('#heavy_pob_date').datepicker().datepicker('setDate', datebirth);
                                    $('#heavy_sex').val(sex).change();
                                    //disable_sel2('heavy_sex', 'form1');
                                    //disable_sel2('heavy_pob_country', 'form1');
                                    checkcodfisc();
                                }

                            }
                        }
                        dismiss(modal);
                    }

                    //change district nation
                    function changedistrictpob() {
                        var ita = '<%=ln.getNazioneCodice("ITALIA")%>';
                        if (document.getElementById('heavy_pob_country').value === "") {
                            $('#heavy_pob_district').val(" ").change();
                            disable_sel2('heavy_pob_district', 'form1');
                        } else {
                            if (document.getElementById('heavy_pob_country').value === ita) {
                                $('#heavy_pob_district').val("---").change();
                                enable_sel2('heavy_pob_district', 'form1');
                                document.getElementById('heavy_pob_district_div').style.display = "";
                                document.getElementById("heavy_pob_district_STR").style.display = "none";
                                document.getElementById("heavy_pob_district_STR").value = "";
                            } else {
                                $('#heavy_pob_district').val("EE").change();
                                disable_sel2('heavy_pob_district', 'form1');
                            }
                        }
                        ricercaclientestraniero(true);
                    }




                    function changecustomerKind() {
                        document.getElementById('dataverified').style.display = 'none';
                        var v1 = document.getElementById("customerKind").value;
                        ricercaclientestraniero(false);
                        if (v1 !== "None") {

                            var showmod = "-1";
                            var dismod = "";

                            var thranag = "";
                            var maxweek = "";
                            var res = "";
                            var tipocli = "";
                            var area = "";
                            var tf = "";

            <%for (int i = 0; i < array_custkind.size(); i++) {%>
                            var kind_controlla = '<%=array_custkind.get(i).getTipologia_clienti()%>';
                            if (v1 === kind_controlla) {
                                res = '<%=array_custkind.get(i).getFg_nazionalita()%>';
                                tipocli = '<%=array_custkind.get(i).getFg_tipo_cliente()%>';
                                area = '<%=array_custkind.get(i).getFg_area_geografica()%>';
                                thranag = '<%=array_custkind.get(i).getIp_max_singola_transazione()%>';
                                maxweek = '<%=array_custkind.get(i).getIp_max_settimanale()%>';
                                tf = '<%=array_custkind.get(i).getTaxfree()%>';
                                dismod = '<%=array_custkind.get(i).getStampa_autocertificazione()%>';
                                if (dismod === "1") {
                                    showmod = '<%=array_custkind.get(i).getIp_soglia_extraCEE_certification()%>';
                                }

                            }
            <%}%>


                            disable_sel2('heavy_country');
                            enable_sel2('heavy_country');



                            document.getElementById('thranag').value = thranag;
                            document.getElementById('maxweek').value = maxweek;
                            document.getElementById('tf').value = tf;
                            document.getElementById('showmod').value = showmod;


                            if (tipocli === '20') { //PERSONA GIURIDICA
                                document.getElementById("custinfo_1").style.display = "none";
                                document.getElementById("custinfo_2").style.display = "";
                                document.getElementById("heavy_codfisc").disabled = false;
                                document.getElementById("buttoncheckcf").disabled = false;
                                document.getElementById("buttoncheckaeg").disabled = false;
                            } else {
                                if (area === '1') { //NORMALE ITALIA
                                    //  blocco select italia
                                    var country = '<%=ln.getNazioneCodice("ITALIA")%>';
                                    $('#heavy_country').empty().trigger('change');
            <%for (int j = 0; j < array_country_1.size(); j++) {%>
                                    var o = $("<option/>", {value: "<%=array_country_1.get(j)[0]%>", text: "<%=array_country_1.get(j)[1]%>"});
                                    $('#heavy_country').append(o);
            <%}%>
                                    $('#heavy_country').val($('#heavy_country' + ' option:first-child').val()).trigger('change');
                                    document.getElementById("heavy_country_dis").value = country;
                                    disable_sel2('heavy_country', 'form1');
                                    document.getElementById("heavy_city_dis").value = "";
                                    document.getElementById("heavy_city_dis").style.display = "none";
                                    document.getElementById("heavy_city_div").style.display = "";

                                    document.getElementById("zipcodelabel").style.display = "";

                                    $('#heavy_city').select2({
                                        ajax: {
                                            url: "Query?type=city_select",
                                            dataType: 'json',
                                            delay: 250,
                                            data: function (params) {
                                                return {
                                                    q: params.term, // search term
                                                    page: params.page
                                                };
                                            },
                                            processResults: function (data, params) {
                                                params.page = params.page || 1;
                                                return {
                                                    results: data.items,
                                                    pagination: {
                                                        more: (params.page * 30) < data.total_count
                                                    }
                                                };
                                            },
                                            cache: true
                                        },
                                        escapeMarkup: function (markup) {
                                            return markup; // let our custom formatter work
                                        },
                                        minimumInputLength: 1,
                                        templateResult: function (repo) {
                                            if (repo.loading)
                                                return repo.text;
                                            return repo.full_name;
                                        },
                                        templateSelection: function (repo) {
                                            return repo.full_name || repo.text;
                                        }
                                    });

                                    document.getElementById("heavy_district_dis").value = "";
                                    document.getElementById("heavy_district_dis").style.display = "none";
                                    document.getElementById("heavy_district_div").style.display = "";

                                    $('#heavy_district').select2({
                                        ajax: {
                                            url: "Query?type=district_select",
                                            dataType: 'json',
                                            delay: 250,
                                            data: function (params) {
                                                return {
                                                    q: params.term, // search term
                                                    page: params.page
                                                };
                                            },
                                            processResults: function (data, params) {
                                                // parse the results into the format expected by Select2
                                                // since we are using custom formatting functions we do not need to
                                                // alter the remote JSON data, except to indicate that infinite
                                                // scrolling can be used
                                                params.page = params.page || 1;

                                                // !IMPORTANT! your every item in data.items has to have an .id property - this is the actual value that Select2 uses
                                                // Luckily the source data.items already have one
                                                return {
                                                    results: data.items,
                                                    pagination: {
                                                        more: (params.page * 30) < data.total_count
                                                    }
                                                };
                                            },
                                            cache: true
                                        },
                                        escapeMarkup: function (markup) {
                                            return markup; // let our custom formatter work
                                        },
                                        minimumInputLength: 1,
                                        templateResult: function (repo) {
                                            if (repo.loading)
                                                return repo.text;
                                            return repo.full_name;
                                        },
                                        templateSelection: function (repo) {
                                            return repo.full_name || repo.text;
                                        }
                                    });




                                    document.getElementById("custinfo_2").style.display = "none";
                                    document.getElementById("custinfo_1").style.display = "";
                                    document.getElementById("heavy_codfisc").disabled = false;
                                    document.getElementById("buttoncheckcf").disabled = false;
                                    document.getElementById("buttoncheckaeg").disabled = false;
                                    blockheavy();

                                    document.getElementById("taxcodespan").style.display = "";
                                    document.getElementById("heavy_districtspan").style.display = "";
                                    document.getElementById("heavy_pob_districtspan").style.display = "";





                                } else if (area === '2') { //EUROPEO

                                    $('#heavy_country').empty().trigger('change');
                                    var o = $("<option/>", {value: "---", text: "None"});
                                    $('#heavy_country').append(o);

            <%for (int j = 0; j < array_country_2.size(); j++) {%>
                                    var o = $("<option/>", {value: "<%=array_country_2.get(j)[0]%>", text: "<%=array_country_2.get(j)[1]%>"});
                                    $('#heavy_country').append(o);
            <%}%>
                                    $('#heavy_country').val($('#heavy_country' + ' option:first-child').val()).trigger('change');
                                    document.getElementById("heavy_country").disabled = false;
                                    document.getElementById("heavy_country_dis").value = "";
                                    document.getElementById("heavy_city").disabled = true;
                                    document.getElementById("heavy_city_div").style.display = "none";
                                    document.getElementById("heavy_city_dis").style.display = "";
                                    document.getElementById("heavy_district").disabled = true;
                                    document.getElementById("heavy_district_div").style.display = "none";
                                    document.getElementById("heavy_district_dis").style.display = "";

                                    disable_sel2('heavy_pob_district', 'form1');
                                    document.getElementById("heavy_pob_district_div").style.display = "none";
                                    document.getElementById("heavy_pob_district_STR").style.display = "";


                                    document.getElementById("custinfo_2").style.display = "none";
                                    document.getElementById("custinfo_1").style.display = "";
                                    document.getElementById("zipcodelabel").style.display = "none";
                                    document.getElementById("heavy_codfisc").value = '';
                                    document.getElementById("heavy_codfisc").disabled = true;
                                    document.getElementById("buttoncheckcf").disabled = true;
                                    document.getElementById("buttoncheckaeg").disabled = true;
                                    unlockheavy();

                                    document.getElementById("taxcodespan").style.display = "none";
                                    document.getElementById("heavy_districtspan").style.display = "none";
                                    document.getElementById("heavy_pob_districtspan").style.display = "none";


                                } else if (area === '3') {
                                    //EXTRAEURO
                                    document.getElementById("heavy_country").disabled = false;
                                    $('#heavy_country').empty().trigger('change');
                                    var o = $("<option/>", {value: "---", text: "None"});
                                    $('#heavy_country').append(o);
            <%for (int j = 0; j < array_country_3.size(); j++) {%>
                                    var o = $("<option/>", {value: "<%=array_country_3.get(j)[0]%>", text: "<%=array_country_3.get(j)[1]%>"});
                                    $('#heavy_country').append(o);
            <%}%>
                                    $('#heavy_country').val($('#heavy_country' + ' option:first-child').val()).trigger('change');

                                    document.getElementById("heavy_country_dis").value = "";

                                    document.getElementById("heavy_city").disabled = true;
                                    document.getElementById("heavy_city_div").style.display = "none";
                                    document.getElementById("heavy_city_dis").style.display = "";
                                    document.getElementById("zipcodelabel").style.display = "none";
                                    document.getElementById("heavy_district").disabled = true;
                                    document.getElementById("heavy_district_div").style.display = "none";
                                    document.getElementById("heavy_district_dis").style.display = "";

                                    disable_sel2('heavy_pob_district', 'form1');
                                    document.getElementById("heavy_pob_district_div").style.display = "none";
                                    document.getElementById("heavy_pob_district_STR").style.display = "";



                                    document.getElementById("custinfo_2").style.display = "none";
                                    document.getElementById("custinfo_1").style.display = "";
                                    document.getElementById("heavy_codfisc").value = '';
                                    document.getElementById("heavy_codfisc").disabled = true;
                                    document.getElementById("buttoncheckcf").disabled = true;
                                    document.getElementById("buttoncheckaeg").disabled = true;
                                    unlockheavy();

                                    document.getElementById("taxcodespan").style.display = "none";
                                    document.getElementById("heavy_districtspan").style.display = "none";
                                    document.getElementById("heavy_pob_districtspan").style.display = "none";
                                }

                            }


                            setvalue('1');

                            //valori da prenotazione;


                            //CONVERTI VALORI

                            var naz_boN = "<%=Utility.sanitize(bo.getCl_nazione())%>";
                            if (naz_boN === null || naz_boN === '' || naz_boN.includes("IT") || naz_boN.includes("-")) {
                            } else {
                                $('#heavy_pob_country').val(naz_boN).trigger('change');
                            }

                            var naz_bo = "<%=Utility.sanitize(bo.getCl_indirizzinazione())%>";
                            if (naz_bo === null || naz_bo === '' || naz_bo.includes("IT") || naz_bo.includes("-")) {



                            } else {
                                $('#heavy_country').val(naz_bo).trigger('change');
                            }

                            var pro_bo = "<%=Utility.sanitize(bo.getCl_indirizzoprov())%>";
                            var pro_boD = "<%=Utility.sanitize(bo.getCl_indirizzoprov_D())%>";
                            if (pro_bo === null || pro_bo === '' || pro_boD === null || pro_boD === '') {

                            } else {
                                var d1 = $("<option/>", {value: pro_bo, text: pro_boD});
                                $('#heavy_district').append(d1);
                                $('#heavy_district').val(pro_bo).trigger('change');
                            }



                            var city_bo = "<%=Utility.sanitize(bo.getCl_indirizzocity())%>";
                            if (city_bo === null || city_bo === '') {
                            } else {
                                $('#heavy_city').val(city_bo).change();
                                document.getElementById("heavy_city_dis").value = city_bo;
                            }

                            var pro_boN = "<%=Utility.sanitize(bo.getCl_prov())%>";
                            if (pro_boN === null || pro_boN === '') {

                            } else {
                                $('#heavy_pob_district').val(pro_boN).trigger('change');
                                disable_sel2('heavy_pob_district', 'form1');
                                document.getElementById('heavy_pob_district_STR').value = pro_boN;
                            }
                            var sex_bo = "<%=Utility.sanitize(bo.getCl_sesso())%>";
                            if (sex_bo === null || sex_bo === '') {

                            } else {
                                enable_sel2('heavy_sex', 'form1');
                                $('#heavy_sex').val(sex_bo).change();
                                disable_sel2('heavy_sex', 'form1');
                            }

                        } else {
                            document.getElementById('tf').value = '0';
                        }




                    }

                    //cambio cap in base alla città
                    function selectcityDistrict(heavy_city, heavy_zipcode) {
                        var s1 = document.getElementById(heavy_city);
                        $.ajax({
                            type: "POST",
                            url: "Query?type=querycodcom_zip&q=" + s1.value,
                            success: function (data) {
                                var z1 = document.getElementById(heavy_zipcode);
                                z1.value = data;
                            }
                        });
                    }

                    //quantity che sceglie rate
                    function changequantity(index) {

                        var quant = document.getElementById("quantity" + index);
                        formatValueDecimal_1(quant, separatorthousand, separatordecimal);
                        setvalue(index);
                    }





                    function controv_opposto(num, den) {
                        if ('<%=operator%>' === '/') {
                            return num * den;
                        } else {
                            return num / den;
                        }
                    }
                    function changeNet(index, fxc) {

                        var net = document.getElementById("net" + index);
                        formatValueDecimal_1(net, separatorthousand, separatordecimal);
                        var netval = replacestringparam(net.value, separatorthousand);
                        var dis_rate = document.getElementById("divrate" + index).style.display;
                        var rate = document.getElementById("rate" + index).value;
                        if (dis_rate === "none") {
                            rate = document.getElementById("fieldrate" + index).value;
                        }

                        var comperc = replacestringparam(document.getElementById("comperc" + index).value, separatorthousand);
                        var kindfixcomm_value = document.getElementById("kindfixcomm" + index).value.trim();

                        var fx_com = document.getElementById("fx_com" + index).value.trim();



                        var q = 0;
                        if ('<%=operator%>' === "/") {
                            var num2 = parseFloatRaf(netval, separatorthousand, separatordecimal) - parseFloatRaf(fx_com, separatorthousand, separatordecimal);
                            var num1 = parseFloatRaf(rate, separatorthousand, separatordecimal, 8);
                            var de = 1 + (parseFloatRaf(comperc, separatorthousand, separatordecimal) / 100);
                            q = num1 * num2 / (de);
                        } else {
                            var num2 = parseFloatRaf(netval, separatorthousand, separatordecimal) - parseFloatRaf(fx_com, separatorthousand, separatordecimal);
                            var num1 = 1 / parseFloatRaf(rate, separatorthousand, separatordecimal, 8);
                            var de = 1 + (parseFloatRaf(comperc, separatorthousand, separatordecimal) / 100);
                            q = num1 * num2 / (de);
                        }
                        //var q = controv_fin / rate;
                        var quant_fin = accounting.formatNumber(new BigNumber(q.toString()).toFixed(2, 5), 2, separatorthousand, separatordecimal);
                        if (fxc === undefined) {

                            var nt0 = 0.0;

                            if ('<%=operator%>' === "/") {
                                nt0 = q / parseFloatRaf(rate, separatorthousand, separatordecimal, 8);
                            } else {
                                nt0 = q * parseFloatRaf(rate, separatorthousand, separatordecimal, 8);
                            }

                            var newfx = getfx(kindfixcomm_value, nt0, index);


                            if (newfx !== fx_com) {
                                changeNet(index, newfx);
                                return false;
                            }
                        }

                        document.getElementById("fxcomm" + index).value = fx_com;
                        formatValueDecimal_1(document.getElementById("fxcomm" + index), separatorthousand, separatordecimal);
                        document.getElementById("quantity" + index).value = quant_fin;
                        //changequantity(index);
                        setvalue(index);
                    }

                    // check date
                    function checkheavy_pob_date() {
                        var pobdate = document.getElementById('heavy_pob_date').value.trim();
                        if (moment(pobdate, 'DD/MM/YYYY').isValid()) {
                            var limitdate = moment().subtract(15, 'year');
                            var pobdate1 = moment(pobdate, 'DD/MM/YYYY', true);
                            if (!limitdate.isSameOrAfter(pobdate1)) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                        if (moment(pobdate, 'DD/MM/YYYY').isValid()) {
                            var pobdate1 = moment(pobdate, 'DD/MM/YYYY', true).add(100, 'year');
                            if (moment().isSameOrAfter(pobdate1)) {
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    }

                    function checkheavy_issuedateidentcard() {
                        var issuedate = document.getElementById('heavy_issuedateidentcard').value.trim();
                        if (moment(issuedate, 'DD/MM/YYYY').isValid()) {
                            var nowdate = moment();
                            var issuedate1 = moment(issuedate, 'DD/MM/YYYY', true);
                            var ch1 = nowdate.isSameOrAfter(issuedate1);
                            if (ch1) {
                                var pobdate = document.getElementById('heavy_pob_date').value.trim();
                                if (moment(pobdate, 'DD/MM/YYYY').isValid()) {
                                    var pobdate1 = moment(pobdate, 'DD/MM/YYYY', true);
                                    var ch2 = issuedate1.isSameOrAfter(pobdate1);
                                    return ch2;
                                }
                            }
                        }
                        return false;
                    }

                    function checkheavy_exdateidentcard() {
                        var exiday = document.getElementById('heavy_exdateidentcard').value.trim();
                        if (moment(exiday, 'DD/MM/YYYY').isValid()) {
                            var nowdate = moment(moment().format('DD/MM/YYYY'), 'DD/MM/YYYY', true);
                            var exiday1 = moment(exiday, 'DD/MM/YYYY', true);
                            var ch1 = exiday1.isSameOrAfter(nowdate);
                            return ch1;
                        }
                        return false;
                    }



                    //set Value row
                    function setvalue(index) {

                        if (document.getElementById("trfig" + index).style.display === ""
                                ||
                                document.getElementById("trfig" + index).style.display === "block") {

                            document.getElementById("totalspread").innerHTML = accounting.formatNumber("0", 2, separatorthousand, separatordecimal);
                            document.getElementById("totalspreadv").value = document.getElementById("totalspread").innerHTML;
                            var quantity = replacestringparam(document.getElementById("quantity" + index).value, separatorthousand);
                            var rate = document.getElementById("rate" + index).value;
                            var total = controv(parseFloatRaf(quantity, separatorthousand, separatordecimal), parseFloatRaf(rate, separatorthousand, separatordecimal, 8));

                            var total_VISUAL = new BigNumber(total.toFixed(3).toString()).toFixed(2, 5).toString();


                            if (checkValueCorrect(total_VISUAL)) {
                                document.getElementById("total" + index).value = accounting.formatNumber(total_VISUAL, 2, separatorthousand, separatordecimal);
                            } else {
                                document.getElementById("total" + index).value = '0' + separatordecimal + '00';
                            }

                            var com_perc = replacestringparam(document.getElementById("comperc" + index).value, separatorthousand);

                            var tot_perc = total * parseFloatRaf(com_perc, separatorthousand, separatordecimal) / 100;

                            var tot_perc_VISUAL = new BigNumber(tot_perc.toString()).toFixed(2, 5);
                            if (checkValueCorrect(tot_perc_VISUAL)) {
                                document.getElementById("totperc" + index).value = accounting.formatNumber(tot_perc_VISUAL, 2, separatorthousand, separatordecimal);
                            } else {
                                document.getElementById("totperc" + index).value = '0' + separatordecimal + '00';
                            }
                            var tot_comm = parseFloatRaf(document.getElementById("totcomm" + index).value, separatorthousand, separatordecimal);
                            var tot_comm_VISUAL = new BigNumber(tot_comm.toString()).toFixed(2, 5);
                            if (checkValueCorrect(tot_comm_VISUAL)) {
                                document.getElementById("totcomm" + index).value = accounting.formatNumber(tot_comm_VISUAL, 2, separatorthousand, separatordecimal);
                            } else {
                                document.getElementById("totcomm" + index).value = '0' + separatordecimal + '00';
                            }

//                            var net = parseFloatRaf(document.getElementById("net" + index).value, separatorthousand, separatordecimal);

                            var net = parseFloatRaf(total_VISUAL, separatorthousand, separatordecimal)
                                    + parseFloatRaf(get_fx(), separatorthousand, separatordecimal);
                            document.getElementById("roundvalue" + index).value = "0.00";
                            var nation = '<%=nation%>';
                            if (nation === "IT") {
                                var kindpay = document.getElementById('kind_p1').value.trim();
                                if (kindpay !== "..." && kindpay !== "01") {

                                } else {
                                    var out1 = round_and_split_ita_up(new BigNumber(net.toString()).toFixed(2, 5), 5);
                                    net = new BigNumber(parseFloatRaf(out1[0], separatorthousand, separatordecimal).toString()).toFixed(2, 5);
                                    var round_t = new BigNumber(parseFloatRaf(out1[1], separatorthousand, separatordecimal).toString()).toFixed(2, 5);
                                    document.getElementById("roundvalue" + index).value = round_t;
                                }
                            } else if (nation === "CZ") {
                                var payout_t = new BigNumber(net.toString()).ceil().toFixed(2);
                                var round_t = new BigNumber((parseFloatRaf(payout_t, separatorthousand, separatordecimal) - net).toString()).toFixed(2, 5);
                                net = accounting.formatNumber(payout_t, 2, separatorthousand, separatordecimal);
                                document.getElementById("roundvalue" + index).value = round_t;
                            }

                            var net_VISUAL = new BigNumber(net.toString()).toFixed(2, 5);
                            if (checkValueCorrect(net_VISUAL)) {
                                document.getElementById("net" + index).value = accounting.formatNumber(net_VISUAL, 2, separatorthousand, separatordecimal);
                            } else {
                                document.getElementById("net" + index).value = '0' + separatordecimal + '00';
                            }



                            calcolaspread(index);


                            //altri campi con somme totali
                        }
                        setvaluetotal();

                    }
                    //total value
                    function setvaluetotal() {


                        var t = 1;
                        var roundvalue = 0.00;
                        if (document.getElementById("roundvalue" + t).value !== null) {
                            roundvalue = roundvalue + parseFloatRaf(document.getElementById("roundvalue" + t).value, separatorthousand, separatordecimal);
                        }
                        document.getElementById("round0").value = accounting.formatNumber(roundvalue, 2, separatorthousand, separatordecimal);

                        var totalnet = 0.00;
                        if (document.getElementById("net" + t).value !== null) {
                            totalnet = totalnet + parseFloatRaf(replacestringparam(document.getElementById("net" + t).value, separatorthousand), separatorthousand, separatordecimal);
                        }

                        document.getElementById("totalnet").innerHTML = accounting.formatNumber(totalnet, 2, separatorthousand, separatordecimal);
                        document.getElementById("payout0").innerHTML = accounting.formatNumber(totalnet, 2, separatorthousand, separatordecimal);
                        document.getElementById("payout1").value = document.getElementById("payout0").innerHTML;
                        var total = 0.00;
                        if (document.getElementById("total" + t).value !== null && document.getElementById('trfig' + t).style.display === "") {
                            total = total + parseFloatRaf(replacestringparam(document.getElementById("total" + t).value, separatorthousand), separatorthousand, separatordecimal);
                        }

                        document.getElementById("total00").innerHTML = accounting.formatNumber(new BigNumber(total.toString()).toFixed(2, 5), 2, separatorthousand, separatordecimal);
                        document.getElementById("total0").value = accounting.formatNumber(new BigNumber(total.toString()).toFixed(2, 5), 2, separatorthousand, separatordecimal);

                        var total_fxcomm = 0.00;
                        if (document.getElementById("fxcomm" + t).value !== null && document.getElementById('trfig' + t).style.display === "") {
                            total_fxcomm = total_fxcomm + parseFloatRaf(replacestringparam(document.getElementById("fxcomm" + t).value, separatorthousand), separatorthousand, separatordecimal);
                        }
                        document.getElementById("fix0").value = accounting.formatNumber(new BigNumber(total_fxcomm.toString()).toFixed(2, 5), 2, separatorthousand, separatordecimal);
                        var total_perccomm = 0.00;
                        if (document.getElementById("totperc" + t).value !== null && document.getElementById('trfig' + t).style.display === "") {
                            total_perccomm = total_perccomm + parseFloatRaf(replacestringparam(document.getElementById("totperc" + t).value, separatorthousand), separatorthousand, separatordecimal);
                        }
                        document.getElementById("com0").value = accounting.formatNumber(new BigNumber(total_perccomm.toString()).toFixed(2, 5), 2, separatorthousand, separatordecimal);
                        var total_commission = 0.00;
                        if (document.getElementById("totcomm" + t).value !== null && document.getElementById('trfig' + t).style.display === "") {
                            total_commission = total_commission + parseFloatRaf(replacestringparam(document.getElementById("totcomm" + t).value, separatorthousand), separatorthousand, separatordecimal);
                        }
                        document.getElementById("commission0").value = accounting.formatNumber(new BigNumber(total_commission.toString()).toFixed(2, 5), 2, separatorthousand, separatordecimal);

                        var total = document.getElementById('payout1').value.trim();
                        if (parseFloatRaf(total, separatorthousand, separatordecimal) >= parseFloatRaf(document.getElementById('thranag').value.trim()), separatorthousand, separatordecimal) {
                            var v1 = document.getElementById("customerKind").value;
                            if (v1 !== "None") {
                                document.getElementById("showanag").style.display = "";
                            } else {
                                document.getElementById("showanag").style.display = "none";
                            }
                        } else {
                            document.getElementById("showanag").style.display = "none";
                        }

                        showmodallimit();
                        ismodified();


                        if (parseFloatRaf(document.getElementById('maxweek').value.trim() > 0)) {

                            if (parseFloatRaf(document.getElementById("payout1").value, separatorthousand, separatordecimal) > parseFloatRaf(document.getElementById('maxweek').value.trim(), separatorthousand, separatordecimal)) {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "You can not complete the transaction. The PayIn exceeds the Max Weekly Threshold (" + document.getElementById('maxweek').value.trim() + ")";
                                return false;
                            }
                        }


                    }

                    function checkEmail(email) {
                        if (email !== "") {
                            var emailFilter = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$/;
                            if (!emailFilter.test(email)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    function verificanomecogn() {
                        ricercaclientestraniero(true);
                        var er = false;
                        var ermsg = "";
                        var cf = document.getElementById("heavy_codfisc").value.trim().toUpperCase();
                        if (cf === "") {
                            return false;
                        }
                        var surname = document.getElementById("heavy_surname").value.trim().toUpperCase();
                        var name = document.getElementById("heavy_name").value.trim().toUpperCase();
                        var checksurname = getStringSurname(surname);
                        var checkname = getStringName(name);
                        if (checkCF(cf)) {
                            if (checksurname !== 'XXX') {
                                if (cf.substring(0, 3) !== checksurname) {
                                    er = true;
                                    ermsg = ermsg + "Surname does not conform<p class='ab'></p>";
                                }
                            }
                            if (checkname !== 'XXX') {
                                if (cf.substring(3, 6) !== checkname) {
                                    er = true;
                                    ermsg = ermsg + "Name does not conform<p class='ab'></p>";
                                }
                            }
                        } else {
                            er = true;
                            ermsg = ermsg + "Tax Code Error<p class='ab'></p>";
                        }

                        if (er) {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = ermsg;
                        }
                    }


                    function fieldobbl() {

                        var customerKind = document.getElementById("customerKind").value;
                        if (customerKind !== "None") {


                            var res = "";
                            var tipocli = "";
            <%for (int i = 0; i < array_custkind.size(); i++) {%>
                            var kind_controlla = '<%=array_custkind.get(i).getTipologia_clienti()%>';
                            if (customerKind === kind_controlla) {
                                res = '<%=array_custkind.get(i).getFg_nazionalita()%>';
                                tipocli = '<%=array_custkind.get(i).getFg_tipo_cliente()%>';
                            }
            <%}%>
                            if (tipocli === '10') { //utente

                                if (res === "1") { //residente ita

                                    var heavy_surname = document.getElementById("heavy_surname");
                                    var heavy_sex = document.getElementById("heavy_sex");
                                    var heavy_name = document.getElementById("heavy_name");
                                    var heavy_codfisc = document.getElementById("heavy_codfisc");
                                    var heavy_pepI = document.getElementById("heavy_pepI");
                                    var heavy_address = document.getElementById("heavy_address");
                                    var heavy_country = document.getElementById("heavy_country");
                                    var heavy_zipcode = document.getElementById("heavy_zipcode");
                                    var heavy_city = document.getElementById("heavy_city");
                                    var heavy_district = document.getElementById("heavy_district");
                                    var heavy_pob_city = document.getElementById("heavy_pob_city");
                                    var heavy_pob_district_STR = document.getElementById("heavy_pob_district_STR");
                                    var heavy_pob_district = document.getElementById("heavy_pob_district");



                                    var heavy_pob_country = document.getElementById("heavy_pob_country");
                                    var heavy_pob_date = document.getElementById("heavy_pob_date");
                                    var heavy_identcard = document.getElementById("heavy_identcard");
                                    var heavy_numberidentcard = document.getElementById("heavy_numberidentcard");
                                    var heavy_issuedateidentcard = document.getElementById("heavy_issuedateidentcard");
                                    var heavy_exdateidentcard = document.getElementById("heavy_exdateidentcard");
                                    var heavy_issuedbyidentcard = document.getElementById("heavy_issuedbyidentcard");
                                    var heavy_issuedplaceidentcard = document.getElementById("heavy_issuedplaceidentcard");

                                    if (isemptyField(heavy_surname) || isemptyField(heavy_sex) || isemptyField(heavy_name) || isemptyField(heavy_codfisc) || isemptyField(heavy_pepI) || isemptyField(heavy_city) ||
                                            isemptyField(heavy_address) || isemptyField(heavy_country) || isemptyField(heavy_district) || isemptyField(heavy_pob_city) ||
                                            (isemptyField(heavy_pob_district_STR) && isemptyField(heavy_pob_district))
                                            || isemptyField(heavy_pob_country) || isemptyField(heavy_pob_date) || isemptyField(heavy_identcard) || isemptyField(heavy_numberidentcard) ||
                                            isemptyField(heavy_issuedateidentcard) || isemptyField(heavy_exdateidentcard) ||
                                            isemptyField(heavy_issuedbyidentcard) || isemptyField(heavy_issuedplaceidentcard) || isemptyField(heavy_zipcode)) {
                                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                        document.getElementById('errorlarge').style.display = "block";
                                        document.getElementById('errorlargetext').innerHTML = "Fields with <span class='font-red'>*</span> must be completed.";
                                        return false;
                                    }
                                    var heavy_pob_district = document.getElementById("heavy_pob_district");
                                    if (heavy_pob_country.value === "<%=Constant.codnaz%>" && isemptyField(heavy_pob_district)) {
                                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                        document.getElementById('errorlarge').style.display = "block";
                                        document.getElementById('errorlargetext').innerHTML = "Field 'District' (Place of Birth) must be completed.";
                                        return false;
                                    }

                                } else if (res === "2") {

                                    var heavy_surname = document.getElementById("heavy_surname");
                                    var heavy_sex = document.getElementById("heavy_sex");
                                    var heavy_name = document.getElementById("heavy_name");
                                    var heavy_pepI = document.getElementById("heavy_pepI");
                                    var heavy_address = document.getElementById("heavy_address");
                                    var heavy_country = document.getElementById("heavy_country");
                                    var heavy_pob_city = document.getElementById("heavy_pob_city");
                                    var heavy_pob_country = document.getElementById("heavy_pob_country");
                                    var heavy_pob_date = document.getElementById("heavy_pob_date");
                                    var heavy_identcard = document.getElementById("heavy_identcard");
                                    var heavy_numberidentcard = document.getElementById("heavy_numberidentcard");
                                    var heavy_issuedateidentcard = document.getElementById("heavy_issuedateidentcard");
                                    var heavy_exdateidentcard = document.getElementById("heavy_exdateidentcard");
                                    var heavy_issuedbyidentcard = document.getElementById("heavy_issuedbyidentcard");
                                    var heavy_issuedplaceidentcard = document.getElementById("heavy_issuedplaceidentcard");



                                    if (isemptyField(heavy_surname)
                                            || isemptyField(heavy_sex)
                                            || isemptyField(heavy_name)
                                            || isemptyField(heavy_pepI)
                                            || isemptyField(heavy_address)
                                            || isemptyField(heavy_country)
                                            || isemptyField(heavy_pob_city)
                                            || isemptyField(heavy_pob_country)
                                            || isemptyField(heavy_pob_date)
                                            || isemptyField(heavy_identcard)
                                            || isemptyField(heavy_numberidentcard)
                                            || isemptyField(heavy_issuedateidentcard)
                                            || isemptyField(heavy_exdateidentcard)
                                            || isemptyField(heavy_issuedbyidentcard)
                                            || isemptyField(heavy_issuedplaceidentcard)) {
                                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                        document.getElementById('errorlarge').style.display = "block";
                                        document.getElementById('errorlargetext').innerHTML = "Fields with <span class='font-red'>*</span> must be completed.";
                                        return false;
                                    }

                                    var heavy_pob_district = document.getElementById("heavy_pob_district");
                                    if (heavy_pob_country.value === "<%=Constant.codnaz%>" && isemptyField(heavy_pob_district)) {
                                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                        document.getElementById('errorlarge').style.display = "block";
                                        document.getElementById('errorlargetext').innerHTML = "Field 'District' (Place of Birth) must be completed.";
                                        return false;
                                    }


                                    var heavy_city_dis = document.getElementById("heavy_city_dis");
                                    if (isemptyField(heavy_city_dis)) {
                                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                        document.getElementById('errorlarge').style.display = "block";
                                        document.getElementById('errorlargetext').innerHTML = "Field 'City' must be completed.";
                                        return false;
                                    }
                                }
                            } else {    //company

                            }

                        } else {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = "Fields with <span class='font-red'>*</span> must be completed.";
                            return false;
                        }
                        return true;
                    }

                    function checkpaymode() {
                        var kind_p1 = document.getElementById('kind_p1').value.trim();
                        if (kind_p1 === "...") {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = "Field 'Local Figures' must be completed.";
                            return false;
                        } else if (kind_p1 === "06") {
                            var posnum = document.getElementById('posnum').value.trim();
                            if (posnum === "...") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Field 'Pos' must be completed.";
                                return false;
                            }
                            var cc_number = document.getElementById('cc_number').value.trim();
                            if (cc_number === "") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Field 'CC Number' must be completed.";
                                return false;
                            }
                        } else if (kind_p1 === "07") {
                            var posnum = document.getElementById('posnum').value.trim();
                            if (posnum === "...") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Field 'Pos' must be completed.";
                                return false;
                            }
                        } else if (kind_p1 === "08") {
                            var ban_1 = document.getElementById('ban_1').value.trim();
                            if (ban_1 === "...") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Field 'Bank' must be completed.";
                                return false;
                            }
                        }
                        return true;
                    }

                    function datachecked() {
                        var d = document.getElementById('dataverified').style.display.trim();
                        if (d === "") {
                            if (!document.getElementById('verdat').checked) {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Filed 'The data has been verified' must be checked.";
                                return false;
                            }
                        }
                        return true;
                    }


                    function controllatilloccupato() {
                        var occ = "true";
                        $.ajax({
                            async: false,
                            type: "POST",
                            url: "Operazioni_test?type=controllaoccupato_till&q=" + document.getElementById('till').value.trim(),
                            success: function (data) {
                                occ = data;
                            }
                        });
                        if (occ === "true") {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = "Warning! Operation not permitted. You have an operation pending (Open/Close, Internal/External transfer, Transaction Change), before proceeding you MUST first finish that operation.";
                            return false;
                        }
                        return true;
                    }

                    function controllanazionecliente() {

                        var ck = document.getElementById("customerKind").value;
                        var na = document.getElementById("heavy_country").value;

                        if (ck === "001" || ck === "009") {
                            if (na === "<%=Constant.codnaz%>") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Please, choose a different country of residence - Italy not allowed";
                                return false;
                            }
                        }
                        return true;
                    }

                    function checksizescurrency_new() {

                        var pay = document.getElementById('kind_p1').value.trim();
                        var total = document.getElementById('payout0').innerHTML;
                        var localcur = "<%=local_cur%>";

                        var link = "Query?type=checksizecurrency&pay=" + pay + "&total=" + total + "&localcur=" + localcur + "&";

                        for (var ind = 1; ind < 6; ind++) {
                            if (document.getElementById("kind" + ind) !== null) {
                                if (document.getElementById("trfig" + ind).style.display === "") {
                                    var figs = document.getElementById("figs" + ind).value.trim();
                                    var quantity = document.getElementById("quantity" + ind).value.trim();
                                    link = link + "kind" + ind + "=01&figs" + ind + "=" + figs + "&quantity" + ind + "=" + quantity + "&";
                                }
                            }
                        }
                        var exit = false;
                        $.ajax({
                            async: false,
                            type: "POST",
                            url: link,
                            success: function (data) {
                                if (data === "") {
                                    exit = true;
                                } else {
                                    var arrayJson = JSON.parse(data);

                                    var msg = "";
                                    for (var e = 0; e < arrayJson.length; e++) {
                                        msg = msg + arrayJson[e] + "<p class='ab'></p>";
                                    }
                                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                    document.getElementById('errorlarge').style.display = "block";
                                    document.getElementById('errorlargetext').innerHTML = msg;
                                }
                            }
                        });
                        return exit;
                    }


                    function verbooking() {
                        if (document.getElementById('modifiedbooking').style.display === "") {
                            if (document.getElementById("kind_p1").value === "06" && document.getElementById("posnum").value === "999") {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "ERROR! THE BOOKING HAS BEEN CHANGED. FIELD 'POS' CAN NOT BE 'WEBSITE PAYMENT'.";
                                return false;

                            }
                        }

                        if (document.getElementById("kind_p1").value === "06" && document.getElementById("posnum").value === "999" && !verificaonline()) {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = "ERROR! BRANCH IS OFFLINE. FIELD 'POS' CAN NOT BE 'WEBSITE PAYMENT'.";
                            return false;
                        }



                        return true;
                    }

                    function verifica_WK_TI() {
                        var exit = true;
                        var wk1_d = $('#welc').prop('disabled');
                        var wk1_v = false;
                        if (!wk1_d) {
                            wk1_v = $("#welc").prop('checked');
                        }
                        var to1_d = $('#fidel').prop('disabled');
                        var to1_v = false;
                        if (!to1_d) {
                            to1_v = $("#fidel").prop('checked');
                        }
                        if (wk1_v || to1_v) {
                            $.ajax({
                                async: false,
                                type: "POST",
                                url: "Query?type=check_quantity_WK&c1=" + wk1_v +
                                        "&q1=WELKITSELL&c2=" + to1_v +
                                        "&q2=TOPITACARD&idopentillv=" + document.getElementById('idopentillv').value.trim(),
                                success: function (data) {
                                    if (data !== "") {
                                        exit = data === 'true';
                                    }
                                }
                            });
                        }

                        if (!exit) {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            if (wk1_v) {
                                document.getElementById('errorlargetext').innerHTML =
                                        "The required quantity of 'Welcome Kit' is not available.";
                            } else {
                                document.getElementById('errorlargetext').innerHTML =
                                        "The required quantity of 'Top Italy Card' is not available.";
                            }
                        }
                        return exit;
                    }


                    function finalcheck() {

                        var v_WK = verifica_WK_TI();
                        if (!v_WK) {
                            return false;
                        }

                        var vb1 = verbooking();
                        if (!vb1) {
                            return false;
                        }

                        var noc = controllanazionecliente();
                        if (!noc) {
                            return false;
                        }

                        var ti1 = controllatilloccupato();
                        if (!ti1) {
                            return false;
                        }

                        var dch = datachecked();

                        if (!dch) {
                            return false;
                        }

                        var csq = checksizescurrency_new();
                        if (!csq) {
                            return false;
                        }



                        var er = false;
                        var ermsg = "";


                        var fo = fieldobbl();
                        if (!fo) {
                            return false;
                        }

                        var cc1 = checkweekly();
                        if (!cc1) {
                            return false;
                        }


                        var chpm = checkpaymode();
                        if (!chpm) {
                            return false;
                        }


                        var qu = checkquantitytill_SELL();
                        if (!qu) {
                            er = true;
                            ermsg = ermsg + "The required quantity of currency is not available.<p class='ab'></p>";
                        }







                        if (document.getElementById('customerKind').value === "003") {
                            var company = document.getElementById('company').value;
                            if (company === "") {
                                er = true;
                                ermsg = ermsg + "'Company' must be selected<p class='ab'></p>";
                            }
                        } else {


                            //date birth > 15 year
                            var ch1 = checkheavy_pob_date();
                            if (!ch1) {
                                er = true;
                                ermsg = ermsg + "Date of birth error<p class='ab'></p>";
                            }

                            //issue date > date birth - issue date < now
                            var ch2 = checkheavy_issuedateidentcard();
                            if (!ch2) {
                                er = true;
                                ermsg = ermsg + "Issue date error<p class='ab'></p>";
                            }
                            // expire day > now
                            var ch3 = checkheavy_exdateidentcard();
                            if (!ch3) {
                                er = true;
                                ermsg = ermsg + "Expiration date error<p class='ab'></p>";
                            }
                            var ch4 = checkEmail(document.getElementById("heavy_email").value.trim());
                            if (!ch4) {
                                er = true;
                                ermsg = ermsg + "Email error<p class='ab'></p>";
                            }
                        }

                        if (parseFloatRaf(document.getElementById("payout1").value, separatorthousand, separatordecimal) >= parseFloatRaf(document.getElementById('maxweek').value.trim(), separatorthousand, separatordecimal)) {
                            er = true;
                            ermsg = ermsg + "You can not complete the transaction. The PayIn exceeds the Max Weekly Threshold ("
                                    + document.getElementById('maxweek').value.trim() + ")<p class='ab'></p>";
                        }

                        if (er) {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = ermsg;
                            return false;
                        } else {
                            return true;
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

                    function verificaclient() {
                        var oldclient = '0';
                        var heavy_codfisc = document.getElementById('heavy_codfisc').value;
                        if (heavy_codfisc !== "") {
                            $.ajax({
                                type: "POST",
                                url: "Query?type=verificaclient&ti=S&q=" + heavy_codfisc,
                                success: function (data) {
                                    if (data !== "") {
                                        var arrayJson = JSON.parse(data);
                                        if (arrayJson.length > 0) {
                                            document.getElementById('dataverified').style.display = '';
                                            document.getElementById('heavy_surname').value = arrayJson[0];
                                            document.getElementById('heavy_name').value = arrayJson[1];
                                            $('#heavy_country').val(arrayJson[2]).trigger('change');

                                            if (arrayJson[18] !== "-") {
                                                var o = $("<option/>", {value: arrayJson[3], text: arrayJson[18]});
                                                $('#heavy_city').append(o);
                                                $('#heavy_city').val($('#heavy_city option:first-child').val()).trigger('change');
                                            }

                                            document.getElementById('heavy_address').value = arrayJson[4];
                                            if (arrayJson[19] !== "-") {
                                                var o = $("<option/>", {value: arrayJson[6], text: arrayJson[19]});
                                                $('#heavy_district').append(o);
                                                $('#heavy_district').val($('#heavy_district option:first-child').val()).trigger('change');
                                            }

                                            $('#heavy_identcard').val(arrayJson[7]).trigger('change');
                                            $('#heavy_pepI').val(arrayJson[20]).trigger('change');
                                            $("#heavy_issuedateidentcard").removeAttr('readonly');
                                            document.getElementById('heavy_numberidentcard').value = arrayJson[8];

                                            $('#heavy_issuedateidentcard').datepicker().datepicker('setDate', arrayJson[9]);
                                            $('#heavy_exdateidentcard').datepicker().datepicker('setDate', arrayJson[10]);

                                            document.getElementById('heavy_issuedbyidentcard').value = arrayJson[11];
                                            document.getElementById('heavy_issuedplaceidentcard').value = arrayJson[12];
                                            document.getElementById('heavy_email').value = arrayJson[13];
                                            document.getElementById('heavy_phonenu').value = arrayJson[14];

                                            document.getElementById('heavy_zipcode').value = arrayJson[5];
                                            document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                                            document.getElementById('infolarge').style.display = "block";
                                            document.getElementById('infolargetext').innerHTML = 'The customer has been recognized. Please confirm the data.';
                                            oldclient = arrayJson[16];
                                        }
                                    }
                                    document.getElementById('oldclient').value = oldclient;
                                }
                            });
                        }

                        verifica_1558();
                    }

                    function verLOY() {
                        var loya = document.getElementById("loya").value.trim();
                        var es = "-1";
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
                                            unlockheavy();
                                            document.getElementById('dataverified').style.display = '';
                                            document.getElementById('heavy_surname').value = arrayJson[0];
                                            document.getElementById('heavy_name').value = arrayJson[1];

                                            $('#heavy_country').val(arrayJson[2]).trigger('change');
                                            if (arrayJson[18] !== "-") {
                                                var o = $("<option/>", {value: arrayJson[3], text: arrayJson[23]});
                                                $('#heavy_city').append(o);
                                                $('#heavy_city').val($('#heavy_city option:first-child').val()).trigger('change');
                                                document.getElementById('heavy_city_dis').value = arrayJson[3];
                                            }

                                            document.getElementById('heavy_address').value = arrayJson[4];
                                            if (arrayJson[19] !== "-") {
                                                var o = $("<option/>", {value: arrayJson[6], text: arrayJson[24]});
                                                $('#heavy_district').append(o);
                                                $('#heavy_district').val($('#heavy_district option:first-child').val()).trigger('change');
                                                document.getElementById('heavy_district_dis').value = arrayJson[24];
                                            }

                                            $('#heavy_identcard').val(arrayJson[7]).trigger('change');
                                            $('#heavy_pepI').val(arrayJson[18]).trigger('change');

                                            $("#heavy_issuedateidentcard").removeAttr('readonly');
                                            document.getElementById('heavy_numberidentcard').value = arrayJson[8];

                                            $('#heavy_issuedateidentcard').datepicker().datepicker('setDate', arrayJson[9]);
                                            $('#heavy_exdateidentcard').datepicker().datepicker('setDate', arrayJson[10]);
                                            document.getElementById('heavy_issuedbyidentcard').value = arrayJson[11];
                                            document.getElementById('heavy_issuedplaceidentcard').value = arrayJson[12];
                                            document.getElementById('heavy_email').value = arrayJson[13];
                                            document.getElementById('heavy_phonenu').value = arrayJson[14];
                                            document.getElementById('heavy_zipcode').value = arrayJson[5];
                                            document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                                            document.getElementById('infolarge').style.display = "block";
                                            document.getElementById('infolargetext').innerHTML = 'The customer has been recognized. Please confirm the data.';
                                            document.getElementById('oldclient').value = arrayJson[15];
                                            document.getElementById('heavy_codfisc').value = arrayJson[16];

                                            $('#heavy_sex').val(arrayJson[17]).trigger('change');
                                            document.getElementById('heavy_pob_city').value = arrayJson[19];
                                            document.getElementById('heavy_pob_city').readOnly = true;
                                            $('#heavy_pob_country').val(arrayJson[21]).trigger('change');
                                            disable_sel2('heavy_pob_country', 'form1');
                                            if (arrayJson[20] !== "-") {

                                                if (arrayJson[20] === "---") {
                                                    $('#heavy_pob_district').val("EE").change();
                                                } else {
                                                    $('#heavy_pob_district').val(arrayJson[20]).change();
                                                }
                                                disable_sel2('heavy_pob_district', 'form1');
                                                document.getElementById('heavy_pob_district_STR').value = arrayJson[20];
                                                document.getElementById('heavy_pob_district_STR').readOnly = true;
                                            }
                                            document.getElementById('heavy_pob_date').value = arrayJson[22];
                                            document.getElementById('heavy_pob_date').readOnly = true;

                                        } else {
                                            es = arrayJson[0];
                                        }
                                    }
                                }
                            });

                            $("#welc").prop("disabled", false);
                            $("#welc").removeAttr('disabled');
                            verifica_wk();
                            if (es !== "0") {
                                $("#fidel").prop("disabled", false);
                                $("#fidel").removeAttr('disabled');
                                verifica_ti();
                                document.getElementById('infolarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('infolarge').style.display = "block";
                                document.getElementById('infolargetext').innerHTML = es;
                                return false;
                            }

                            verifica_1558();


                        } else {

                            $("#welc").prop("disabled", true);
                            $("#fidel").prop("disabled", true);
                            $("#welc").attr('disabled', 'disabled');
                            $("#fidel").attr('disabled', 'disabled');
                            document.getElementById('ex9').style.display = 'none';
                            document.getElementById('ex10').style.display = 'none';
                            document.getElementById('infolarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('infolarge').style.display = "block";
                            document.getElementById('infolargetext').innerHTML = "Loyalty Code invalid.";
                            return false;
                        }
                    }

                    function verifica_1558() {
                        var cf = document.getElementById('heavy_codfisc').value;
                        var co1 = document.getElementById('heavy_surname').value.trim();
                        var na1 = document.getElementById('heavy_name').value.trim();
                        var nz1 = document.getElementById('heavy_pob_country').value.trim();
                        var dn1 = document.getElementById('heavy_pob_date').value.trim();
                        var loya = document.getElementById("loya").value.trim();
                        $.ajax({
                            type: "POST",
                            url: "Query?type=list_OPERATION_client&cf=" + cf + "&co1=" + co1 + "&na1=" + na1 + "&nz1=" + nz1 + "&dn1=" + dn1 + "&loya=" + loya,
                            success: function (data) {
                                if (data !== "") {
                                    var arrayJson = JSON.parse(data);

                                    if (arrayJson[0] === 'false') {
                                    } else {

                                        document.getElementById('infotra').className = document.getElementById('infotra').className + " in";
                                        document.getElementById('infotra').style.display = "block";
                                        document.getElementById('infotratext').innerHTML = arrayJson[1];

                                        document.getElementById('infolarge').className = "modal fade";
                                        document.getElementById('infolarge').style.display = "none";
                                    }
                                }
                            }
                        });
                    }


                    function convertDate_0(dataing) {
                        if (dataing.includes("-")) {
                            var dd = dataing.split("-")[2].trim();
                            var mm = dataing.split("-")[1].trim();
                            var aa = dataing.split("-")[0].trim();
                            var out = dd + "/" + mm + "/" + aa;
                            return out;
                        }
                        return "";

                    }

                    function heavyczh(nation) {
                        if (nation === 'CZ') {
                            //document.getElementById('czh1').style.display = '';
                            document.getElementById('czh3').style.display = '';
                            document.getElementById('czh4').style.display = '';
                        }
                        if (nation === 'IT') {
                            document.getElementById('pepITA').style.display = '';
                        }
                    }



                    function changepersonalne_czh() {
                        if (document.getElementById('heavy_pno0').value === 'YES') {
                            document.getElementById('czh2').style.display = '';
                        } else {
                            document.getElementById('czh2').style.display = 'none';
                            document.getElementById('heavy_pno1').value = '';
                        }
                    }

                    function changepep_czh() {
                        if (document.getElementById('heavy_pep').value === 'YES') {
                            document.getElementById('czh5').style.display = '';
                            document.getElementById('czh6').style.display = '';
                        } else {
                            document.getElementById('czh5').style.display = 'none';
                            document.getElementById('czh6').style.display = 'none';
                            document.getElementById('heavy_moneysource').value = '';
                            document.getElementById('heavy_transactionre').value = '';
                        }
                    }

                    function changelocalfig() {

                        if (document.getElementById('customerKind').value !== "None") {
                            var kind = document.getElementById('kind_p1').value;

                            if (kind === "" || kind === "...") {
                                document.getElementById('pos_0').style.display = 'none';
                                document.getElementById('pos_1').style.display = 'none';
                                document.getElementById('ban_0').style.display = 'none';
                                $("#notesbigV").prop('checked', false);
                                document.getElementById('notesbig').style.display = 'none';
                                return false;
                            }
                            if (kind === '06' || kind === '07') {
                                document.getElementById('pos_0').style.display = '';
                                document.getElementById('ban_0').style.display = 'none';
                                if (kind === '06') {
                                    document.getElementById('pos_1').style.display = '';
                                } else {
                                    document.getElementById('pos_1').style.display = 'none';
                                }
                            } else if (kind === '08') {
                                document.getElementById('pos_0').style.display = 'none';
                                document.getElementById('pos_1').style.display = 'none';
                                document.getElementById('ban_0').style.display = '';
                            } else {
                                document.getElementById('pos_0').style.display = 'none';
                                document.getElementById('pos_1').style.display = 'none';
                                document.getElementById('ban_0').style.display = 'none';
                            }
                            if (kind === '01') {
                                document.getElementById('notesbig').style.display = '';
                            } else {
                                $("#notesbigV").prop('checked', false);
                                document.getElementById('notesbig').style.display = 'none';
                            }
                            setvalue('1');
                        } else {
                            //document.getElementById('pos_0').style.display = 'none';
                            //document.getElementById('pos_1').style.display = 'none';
                            $("#notesbigV").prop('checked', false);
                            document.getElementById('notesbig').style.display = 'none';
                            document.getElementById('ban_0').style.display = 'none';
                            return false;
                        }
                    }

                    function changeintbook() {
                        var intbook = document.getElementById('intbook').value;
                        if (intbook === '---') {
                            document.getElementById('intbook_1').style.display = 'none';
                            document.getElementById('intbook_2').style.display = 'none';
                        } else {
                            document.getElementById('intbook_1').style.display = '';
                            document.getElementById('intbook_2').style.display = '';
                        }
                    }

                    function loadperccomm(index) {
                        var comperc = document.getElementById("comperc" + index);
                        comperc.value = "<%=array_figsell.get(0).getCommissione_vendita()%>";
                        formatValueDecimal_1(comperc, separatorthousand, separatordecimal);
                    }

                    function changetxfree() {
                        if (document.getElementById('tfnc0').checked) {
                            if (document.getElementById('kind_p1').value.trim() === "01") {
                                document.getElementById('txfrdiv0').style.display = '';
                            } else {
                                $('#kind_p1').val('01').trigger('change');

                                //document.getElementById('tfnc0').checked = false;
                                //document.getElementById('txfrdiv0').style.display = 'none';


                                //document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                                //document.getElementById('infolarge').style.display = "block";
                                //document.getElementById('infolargetext').innerHTML = "The field 'Local Figures' should be y to enable 'Tax Free No-Change'.";

                            }
                        } else {
                            document.getElementById('txfrdiv0').style.display = 'none';
                        }
                        changevaluetaxfree();
                    }


                    function changevaluetaxfree() {

                        if (!document.getElementById('tfnc0').checked) {
                            $('#intbook_1').val($('#intbook_1 option:first-child').val()).trigger('change');
                            $('#intbook_2').val($('#intbook_2 option:first-child').val()).trigger('change');
                            $('#intbook_3').val($('#intbook_3 option:first-child').val()).trigger('change');
                            document.getElementById("code_intbook_1").value = "0";
                            document.getElementById("code_intbook_2").value = "0";
                            document.getElementById("code_intbook_3").value = "0";
                            document.getElementById("value_intbook_1").value = "0.00";
                            document.getElementById("value_intbook_2").value = "0.00";
                            document.getElementById("value_intbook_3").value = "0.00";
                        }

                        fieldOnlyNumber('code_intbook_1');
                        fieldOnlyNumber('code_intbook_2');
                        fieldOnlyNumber('code_intbook_3');
                        formatValueDecimal_1(document.getElementById("value_intbook_1"), separatorthousand, separatordecimal);
                        formatValueDecimal_1(document.getElementById("value_intbook_2"), separatorthousand, separatordecimal);
                        formatValueDecimal_1(document.getElementById("value_intbook_3"), separatorthousand, separatordecimal);


                        var value_intbook_1 = parseFloatRaf(replacestringparam(document.getElementById("value_intbook_1").value, separatorthousand), separatorthousand, separatordecimal);
                        var value_intbook_2 = parseFloatRaf(replacestringparam(document.getElementById("value_intbook_2").value, separatorthousand), separatorthousand, separatordecimal);
                        var value_intbook_3 = parseFloatRaf(replacestringparam(document.getElementById("value_intbook_3").value, separatorthousand), separatorthousand, separatordecimal);
                        //var code_intbook_1 = parseFloatRaf(replacestringparam(document.getElementById("code_intbook_1").value, separatorthousand));
                        //var code_intbook_2 = parseFloatRaf(replacestringparam(document.getElementById("code_intbook_2").value, separatorthousand));
                        //var code_intbook_3 = parseFloatRaf(replacestringparam(document.getElementById("code_intbook_3").value, separatorthousand));

                        var v1 = value_intbook_1 + value_intbook_2 + value_intbook_3;

                        //var v1 = (value_intbook_1 * code_intbook_1) + (value_intbook_2 * code_intbook_2) + (value_intbook_3 * code_intbook_3);

                        var v1_f = accounting.formatNumber(v1.toString(), 2, separatorthousand, separatordecimal);

                        var po = parseFloatRaf(replacestringparam(document.getElementById("payout0").innerHTML, separatorthousand), separatorthousand, separatordecimal);

                        var v2 = po - v1;

                        //var v2 = po + v1_f;
                        var v2_f = accounting.formatNumber(v2.toString(), 2, separatorthousand, separatordecimal);
                        document.getElementById('cusval').value = v1_f;
                        document.getElementById('macval').value = v2_f;

                    }


                    function checktf() {


                        var tf = document.getElementById('tfnc0').checked;





                        var intbook_1 = document.getElementById('intbook_1').value.trim();
                        var intbook_2 = document.getElementById('intbook_2').value.trim();
                        var intbook_3 = document.getElementById('intbook_3').value.trim();
                        var code_intbook = parseFloatRaf(replacestringparam(document.getElementById("code_intbook_1").value, separatorthousand), separatorthousand, separatordecimal);
                        var value_intbook = parseFloatRaf(replacestringparam(document.getElementById("value_intbook_1").value, separatorthousand), separatorthousand, separatordecimal);
                        var code_intbook2 = parseFloatRaf(replacestringparam(document.getElementById("code_intbook_2").value, separatorthousand), separatorthousand, separatordecimal);
                        var value_intbook2 = parseFloatRaf(replacestringparam(document.getElementById("value_intbook_2").value, separatorthousand), separatorthousand, separatordecimal);
                        var code_intbook3 = parseFloatRaf(replacestringparam(document.getElementById("code_intbook_3").value, separatorthousand), separatorthousand, separatordecimal);
                        var value_intbook3 = parseFloatRaf(replacestringparam(document.getElementById("value_intbook_3").value, separatorthousand), separatorthousand, separatordecimal);

                        if (tf) {
                            if (intbook_1 === "..." && intbook_2 === "..." && intbook_3 === "..."
                                    && code_intbook <= 0 && value_intbook <= 0 && code_intbook2 <= 0 && value_intbook2 <= 0
                                    && code_intbook3 <= 0 && value_intbook3 <= 0) {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "At least one of the fields 'Tax Free' must be selected.";
                                return false;
                            }
                        }



                        if (intbook_1 !== "...") {
                            if (code_intbook <= 0 || value_intbook <= 0) {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Fields 'Mod. #1' and 'Value #1' (Tax Free No-Change) must be greater than zero.";
                                return false;
                            }
                        } else {

                            if (code_intbook > 0 || value_intbook > 0) {
                                //document.getElementById("code_intbook_1").value = "0";
                                //document.getElementById("value_intbook_1").value = "0" + separatordecimal + "00";
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Field 'Tax Free #1' must be selected.";
                                return false;
                            }
                        }

                        if (intbook_2 !== "...") {
                            if (code_intbook2 <= 0 || value_intbook2 <= 0) {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Fields 'Mod. #2' and 'Value #2' (Tax Free No-Change) must be greater than zero.";
                                return false;
                            }
                        } else {
                            if (code_intbook2 > 0 || value_intbook2 > 0) {
                                //document.getElementById("code_intbook_1").value = "0";
                                //document.getElementById("value_intbook_1").value = "0" + separatordecimal + "00";
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Field 'Tax Free #2' must be selected.";
                                return false;
                            }
                        }

                        if (intbook_3 !== "...") {
                            if (code_intbook3 <= 0 || value_intbook3 <= 0) {
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Fields 'Mod. #3' and 'Value #3' (Tax Free No-Change) must be greater than zero.";
                                return false;
                            }
                        } else {
                            if (code_intbook3 > 0 || value_intbook3 > 0) {
                                //document.getElementById("code_intbook_1").value = "0";
                                //document.getElementById("value_intbook_1").value = "0" + separatordecimal + "00";
                                document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                document.getElementById('errorlarge').style.display = "block";
                                document.getElementById('errorlargetext').innerHTML = "Field 'Tax Free #3' must be selected.";
                                return false;
                            }
                        }

                        return true;
                    }





                    function loadpage() {
                        //$('#largelogin').on('shown.bs.modal', function () {
                        //    $('#passwordlargelogin').focus();
                        //});
                        //$('#largereaddoc').on('shown.bs.modal', function () {
                        //    $('#documentReading').focus();
                        //});
                        //pagamento del sito
                        if ($('#newintbookCanale').val() === '01') {
                            $('#kind_p1').val("06").trigger('change');
                            $('#posnum').val("999").trigger('change');
                        }

                        online();
                        loadtill();
                        changetill();
                        changelocalfig();
                        changecustomerKind();
                        inputvirgola();
                        //loadperccomm('1');
                        //kindfixcomm('1');
                        //changefigs('1');
                        //changequantity('1');
                        //changeFixcomm_kind('1');
                        //setvalue('1');

                        checkesito();


                        var inputs, index;
                        //var msg = "";
                        inputs = document.getElementById('form1').getElementsByTagName('input');
                        for (index = 0; index < inputs.length; ++index) {
                            if (inputs[index].id !== "") {
                                if (document.getElementsByName(inputs[index].id)[0] !== null) {
                                    if (inputs[index].id.startsWith("se_") || inputs[index].id.startsWith("ag_")) {
                                        if (document.getElementById(inputs[index].id).checked) {
                                            document.getElementById(inputs[index].id).click();
                                            document.getElementById(inputs[index].id).click();
                                        }
                                    }
                                }
                            }
                        }

                    }


                    function subform() {
                        document.getElementById('butconf').disabled = true;
                        $("#butconf").html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");
                        //controlli
                        if (finalcheck()) {
                        } else {
                            document.getElementById('butconf').disabled = false;
                            document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                            return false;
                        }
                        return true;
                    }

                    function calcolaspread(index) {
                        $.ajax({
                            type: "POST",
                            url: "Operazioni_test?type=get_spread",
                            async: false,
                            data: {
                                type: "get_spread",
                                t: "S",
                                q1: $('#quantity' + index).val(),
                                f1: $('#figs' + index).val(),
                                ki: $('#kind' + index).val(),
                                r1: $('#rate' + index).val()
                            },
                            success: function (data) {
                                if (data !== "") {
                                    var arrayJson = JSON.parse(data);
                                    document.getElementById("spread" + index).value = arrayJson[1];
                                    if (arrayJson[0] === "OK") {

                                        var totalspread = 0.00;
                                        var t = 1;
                                        if (document.getElementById("spread" + t).value !== null) {
                                            totalspread = totalspread + parseFloatRaf(document.getElementById("spread" + t).value, separatorthousand, separatordecimal);
                                        }

                                        var fx1 = get_fx_solocalcolo();
                                        if (fx1 < 0) {
                                            totalspread = totalspread + fx1;
                                        }
                                        document.getElementById("spread" + index).value = accounting.formatNumber(new BigNumber(totalspread.toString()).toFixed(2, 5), 2, separatorthousand, separatordecimal);
                                        document.getElementById("totalspread").innerHTML =
                                                accounting.formatNumber(new BigNumber(totalspread.toString()).toFixed(2, 5), 2, separatorthousand, separatordecimal);
                                        document.getElementById("totalspreadv").value = document.getElementById("totalspread").innerHTML;
                                    } else {
                                        if (arrayJson[0] !== "-") {
                                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                            document.getElementById('errorlarge').style.display = "block";
                                            document.getElementById('errorlargetext').innerHTML = arrayJson[0];
                                            return false;
                                        }
                                    }
                                }
                            }
                        });

                    }


                    function showmodallimit() {
                        var i = document.getElementById('payout1').value.trim();
                        var showmod = document.getElementById('showmod').value.trim();
                        if (parseFloatRaf(showmod, separatorthousand, separatordecimal) > 0) {
                            if (parseFloatRaf(i, separatorthousand, separatordecimal) >= parseFloatRaf(showmod, separatorthousand, separatordecimal)) {
                                document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                                document.getElementById('infolarge').style.display = "block";
                                document.getElementById('infolargetext').innerHTML = "<%=Utility.correggi(head.getTxt_alert_threshold_1())%>";
                            }
                        }
                    }


                    function checkweekly() {
                        var exit = false;
                        var company = document.getElementById('company').value.trim();
                        var n = document.getElementById('heavy_name').value.trim();
                        var c = document.getElementById('heavy_surname').value.trim();
                        var d = document.getElementById('heavy_pob_date').value.trim();
                        var naz = document.getElementById('heavy_pob_country').value.trim();
                        var t = document.getElementById('customerKind').value.trim();
                        var cf = document.getElementById('heavy_codfisc').value.trim();
                        var i = document.getElementById('payout1').value.trim();
                        var heavy_identcard = document.getElementById('heavy_identcard').value.trim();
                        $.ajax({
                            async: false,
                            type: "POST",
                            url: "Query?type=sogliasetclient&company=" + company + "&n=" + n + "&c=" + c + "&d=" + d + "&naz=" +
                                    naz + "&cf=" + cf + "&t=" + t + "&i=" + i + "&doc=" + heavy_identcard,
                            success: function (data) {
                                if (data !== "") {
                                    var arrayJson = JSON.parse(data);
                                    if (arrayJson[0] === "false1") {
                                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                        document.getElementById('errorlarge').style.display = "block";
                                        document.getElementById('errorlargetext').innerHTML = arrayJson[1];
                                        exit = false;
                                    } else if (arrayJson[0] === "false3") {
                                        var msg = arrayJson[1];
                                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                        document.getElementById('errorlarge').style.display = "block";
                                        document.getElementById('errorlargetext').innerHTML = msg;
                                        exit = false;
                                    } else if (arrayJson[0] === "false2") {
                                        var msg = "";
                                        for (var e = 1; e < arrayJson.length; e++) {
                                            msg = msg + arrayJson[e] + "<p class='ab'></p>";
                                        }
                                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                        document.getElementById('errorlarge').style.display = "block";
                                        document.getElementById('errorlargetext').innerHTML = msg;
                                        exit = false;
                                    } else {
                                        exit = true;

                                    }
                                }
                            }
                        });
                        return exit;
                    }

                    function eseguiricercaclientestraniero() {
                        var oldclient = '0';
                        var co1 = document.getElementById('heavy_surname').value.trim();
                        var na1 = document.getElementById('heavy_name').value.trim();
                        var nz1 = document.getElementById('heavy_pob_country').value.trim();
                        var dn1 = document.getElementById('heavy_pob_date').value.trim();
                        if (co1 !== "" && na1 !== "" && nz1 !== "" && nz1 !== "---" && dn1 !== "") {
                            document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                            document.getElementById('infolarge').style.display = "block";
                            document.getElementById('infolargetext').innerHTML = "<i class='fa fa-spin fa-spinner'></i> PLEASE WAITING... SEARCHING CLIENT...";
                            $.ajax({
                                type: "POST",
                                url: "Query?type=verificaclientstraniero&ti=B&co1=" + co1 + "&na1=" + na1 + "&nz1=" + nz1 + "&dn1=" + dn1,
                                success: function (data) {
                                    if (data !== "") {
                                        var arrayJson = JSON.parse(data);
                                        var esito = arrayJson[0];
                                        if (esito === "OK") {
                                            $('#heavy_sex').val(arrayJson[1]).trigger('change');
                                            $('#heavy_country').val(arrayJson[2]).trigger('change');
                                            document.getElementById('heavy_city_dis').value = arrayJson[3];
                                            $('#heavy_pepI').val(arrayJson[4]).trigger('change');
                                            document.getElementById('heavy_address').value = arrayJson[5];
                                            document.getElementById('heavy_zipcode').value = arrayJson[6];
                                            document.getElementById('heavy_pob_city').value = arrayJson[7];
                                            if (arrayJson[8] !== "-") {
                                                $('#heavy_pob_district').val(arrayJson[8]).trigger('change');
                                            }
                                            $('#heavy_identcard').val(arrayJson[9]).trigger('change');
                                            $("#heavy_issuedateidentcard").removeAttr('readonly');
                                            document.getElementById('heavy_numberidentcard').value = arrayJson[10];
                                            $('#heavy_issuedateidentcard').datepicker().datepicker('setDate', arrayJson[11]);
                                            $('#heavy_exdateidentcard').datepicker().datepicker('setDate', arrayJson[12]);
                                            document.getElementById('heavy_issuedbyidentcard').value = arrayJson[13];
                                            document.getElementById('heavy_issuedplaceidentcard').value = arrayJson[14];
                                            document.getElementById('heavy_email').value = arrayJson[15];
                                            document.getElementById('heavy_phonenu').value = arrayJson[16];
                                            oldclient = arrayJson[18];
                                            document.getElementById('dataverified').style.display = '';
                                            document.getElementById('infolargetext').innerHTML = "<i class='fa fa-check font-green-jungle'></i> The customer has been recognized. Please confirm the data.";
                                        } else {
                                            document.getElementById('infolargetext').innerHTML = "<i class='fa fa-times font-red'></i> There are no customers found for the entered data.";
                                        }
                                    }
                                    document.getElementById('oldclient').value = oldclient;
                                }
                            });
                            verifica_1558();
                        } else {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = "Unable to search, missing fields. Try again.";
                        }
                    }

                    function ricercaclientestraniero(straniero) {

                        if (document.getElementById('customerKind').value === "001"
                                ||
                                document.getElementById('customerKind').value === "009") {
                            straniero = true;
                        } else {
                            straniero = false;
                        }

                        if (straniero) {
                            var x = document.getElementsByClassName("fgsearch");
                            x[0].className = "fgsearch has-warning";
                            x[1].className = "fgsearch has-warning";
                            x[2].className = "fgsearch has-warning";
                            x[3].className = "fgsearch has-warning";

                            var co1 = document.getElementById('heavy_surname').value.trim();
                            var na1 = document.getElementById('heavy_name').value.trim();
                            var nz1 = document.getElementById('heavy_pob_country').value.trim();
                            var dn1 = document.getElementById('heavy_pob_date').value.trim();

                            if (co1 !== "" && na1 !== "" && nz1 !== "" && nz1 !== "---" && dn1 !== "") {
                                document.getElementById('verforeign').disabled = false;
                            } else {
                                document.getElementById('verforeign').disabled = true;
                            }
                        } else {
                            var x = document.getElementsByClassName("fgsearch");
                            x[0].className = "fgsearch";
                            x[1].className = "fgsearch";
                            x[2].className = "fgsearch";
                            x[3].className = "fgsearch";
                            document.getElementById('verforeign').disabled = true;
                        }

                    }


                    function changenewintbook() {
                        if (document.getElementById('newintbook0').checked) {
                            document.getElementById('newintbookdiv').style.display = '';
                            disable_sel2('lowcommjus1');
                            disable_sel2('kindfixcomm1');
                            disable_sel2('lowcommjus2');
                            disable_sel2('kindfixcomm2');
                            disable_sel2('lowcommjus3');
                            disable_sel2('kindfixcomm3');
                            disable_sel2('lowcommjus4');
                            disable_sel2('kindfixcomm4');
                            disable_sel2('lowcommjus5');
                            disable_sel2('kindfixcomm5');
                            f_comperc('1');
                            f_comperc('2');
                            f_comperc('3');
                            f_comperc('4');
                            f_comperc('5');
                        } else {
                            document.getElementById('newintbookdiv').style.display = 'none';
                            document.getElementById('newintbookCodice').value = '';
                            $('#newintbookCanale').val($('#newintbookCanale option:first-child').val()).trigger('change');

                            $('#lowcommjus1').val($('#lowcommjus1 option:first-child').val()).trigger('change');
                            $('#lowcommjus2').val($('#lowcommjus2 option:first-child').val()).trigger('change');
                            $('#lowcommjus3').val($('#lowcommjus3 option:first-child').val()).trigger('change');
                            $('#lowcommjus4').val($('#lowcommjus4 option:first-child').val()).trigger('change');
                            $('#lowcommjus5').val($('#lowcommjus5 option:first-child').val()).trigger('change');
                            $('#kindfixcomm1').val($('#kindfixcomm1 option:first-child').val()).trigger('change');
                            $('#kindfixcomm2').val($('#kindfixcomm2 option:first-child').val()).trigger('change');
                            $('#kindfixcomm3').val($('#kindfixcomm3 option:first-child').val()).trigger('change');
                            $('#kindfixcomm4').val($('#kindfixcomm4 option:first-child').val()).trigger('change');
                            $('#kindfixcomm5').val($('#kindfixcomm5 option:first-child').val()).trigger('change');
                            disable_sel2('lowcommjus1');
                            enable_sel2('kindfixcomm1');
                            disable_sel2('lowcommjus2');
                            enable_sel2('kindfixcomm2');
                            disable_sel2('lowcommjus3');
                            enable_sel2('kindfixcomm3');
                            disable_sel2('lowcommjus4');
                            enable_sel2('kindfixcomm4');
                            disable_sel2('lowcommjus5');
                            enable_sel2('kindfixcomm5');
                            changeKind('1');
                            changeKind('2');
                            changeKind('3');
                            changeKind('4');
                            changeKind('5');
                        }


                        //f_comperc('1');
                        //f_comperc('2');
                        //f_comperc('3');
                        //f_comperc('4');
                        //f_comperc('5');

                    }


                    function controv(num, den) {
                        if ('<%=operator%>' === '/') {
                            return num / den;
                        } else {
                            return num * den;
                        }
                    }


        </script>


    </head>




    <!-- END HEAD -->
    <%if (array_till_open.isEmpty() || iscentral || bo == null) { %>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
        <%} else {%>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">
        <%}%>

        <!--- MODAL PASSWORD --->
        <div class="modal fade" id="largelogin_hid" tabindex="-1" role="dialog" aria-hidden="true">
            <button type="button" class="btn btn-info btn-lg" id="largelogin_butt" data-toggle="modal" data-target="#largelogin">Open Modal</button>
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
                                            <input class="form-control" type="password" autocomplete="off" maxlength="10" placeholder="Password" name="passwordlargelogin" id="passwordlargelogin" onkeypress="checkkeysub('buttonsubmitlargelogin', event);"> 
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
        <!-- MODAL UNLOCK -->
        <div class="modal fade" id="largelock" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Insert unlock code</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <div class="input-icon">
                                <i class="fa fa-key font-green-sharp"></i>
                                <input class="form-control" autocomplete="off" placeholder="Code" id="unlockCode" name="unlockCode" /> 
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline green-sharp" data-dismiss="modal" onclick="return verificaSblocco('largelock');"><i class="fa fa-arrow-right"></i> Continue</button>
                        <a type="button" class="btn btn-outline red" data-dismiss="modal" onclick="return dismiss('largelock');"><i class="fa fa-remove"></i> Cancel</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL READ DOC -->
        <div class="modal fade" id="largereaddoc" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Read Document</h4>
                    </div>

                    <div class="modal-body">
                        <textarea class="form-control" rows="10" placeholder="Click HERE before reading" 
                                  name="documentReading" id="documentReading"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline red-haze " onclick="document.getElementById('documentReading').value = '';
                                document.getElementById('documentReading').focus();" ><i class="fa fa-refresh"></i> Clear Text</button>
                        <button type="button" class="btn btn-outline green-sharp" data-dismiss="modal" onclick="return readDoc('largereaddoc');"><i class="fa fa-arrow-right"></i> Continue</button>
                    </div>

                </div>
            </div>
        </div>        
        <div class="modal fade" id="scandoc1" role="dialog" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Scan Document</h4>
                    </div>
                    <div class="modal-body">
                        <div id="divscan1">
                            <a data-toggle="modal" href="#myModal2"
                               class="btn blue btn-outline btn-block" 
                               onclick="return scandoc1();">
                                <i class="fa fa-image"></i> START SCAN</a>
                        </div>
                        <input type="hidden" id="numscans" value="0" />
                        <div id="divscan2" style="display: none;">
                            <a data-toggle="modal" href="#myModal2"
                               class="btn blue btn-outline" 
                               onclick="return scandoc1();" >
                                <i class="fa fa-file-pdf-o"></i> CONTINUE SCAN</a>

                            <button type="button" class="btn green-jungle btn-outline" 
                                    onclick="return viewdocscanned();"><i class="fa fa-file-pdf-o"></i> VIEW PDF</button>

                            <a class="btn red btn-outline" onclick="return scandoc2();">
                                <i class="fa fa-remove"></i> RESET SCAN</a>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" 
                                onclick="return dismiss('scandoc1');" data-dismiss="modal"><i class="fa fa-remove"></i> CLOSE</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="myModal2" role="dialog" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">SCAN IN PROGRESS</h4>
                    </div>
                    <div class="modal-body">
                        <h4 class="uppercase"><i class="fa fa-hourglass-half fa-spinner fa-spin"></i> WAITING... Do not close this window.</h4>
                    </div>
                    <div class="modal-footer" style="display: none;">
                        <button type="button" class="btn dark btn-outline" id="closescanwaiting" 
                                onclick="return dismiss('myModal2');" data-dismiss="modal"><i class="fa fa-remove"></i> CLOSE</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="myModal3" role="dialog" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title font-red"><i class="fa fa-remove"></i> DELETE IN PROGRESS</h4>
                    </div>
                    <div class="modal-body">
                        <h4 class="uppercase"><i class="fa fa-hourglass-half fa-spinner fa-spin"></i> WAITING... Do not close this window.</h4>
                    </div>
                    <div class="modal-footer" style="display: none;">
                        <button type="button" class="btn dark btn-outline" id="closescandelete" 
                                onclick="return dismiss('myModal3');" data-dismiss="modal"><i class="fa fa-remove"></i> CLOSE</button>
                    </div>
                </div>
            </div>
        </div>

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

        <!-- INFO MODAL info transaction -->
        <div class="modal fade" id="infotra" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-full">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title font-blue-dark uppercase"><b>LIST OF TRANSACTION</b></h4>
                        <h6 class="modal-title font-blue-dark uppercase">The customer has been recognized. Please confirm the data.</h6>
                    </div>
                    <div class="modal-body col-md-12" id="infotratext">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" onclick="return dismiss('infotra');" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- INFO MODAL -->
        <div class="modal fade bs-modal-lg" id="infolarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title font-green-sharp uppercase"><b>Information message</b></h4>
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
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_tr4.jsp"%>
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
                    <!-- VUOTO RAF -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <%@ include file="menu/shortcuts.jsp"%>
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Website Booking <small><b>SELL</b></small> - Canale: <%=Utility.sanitize(Utility.formatAL(bo.getCanale(), array_intbook, 1))%> </h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                            <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                        </div>
                    </div>

                    <%if (!iscentral) {%>

                    <%if (array_till_open.isEmpty() || bo == null) { %>

                    <div class="row">
                        <div class="col-md-6">
                            <div class="alert alert-danger">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> The operation could not be completed. Check the till and the booking. 
                            </div>
                        </div>
                    </div>     


                    <%} else {%>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <%
                        String first = "sell";
                        String first_label = StringUtils.capitalize(first);
                        String cod = Utility.generaIdMAC(bo.getFiliale());
                    %>
                    <form action="Operazioni_test?type=ch_tr_sell_webtr" method="post" name="form1" id="form1" onsubmit="return subform();">
                        <input type="hidden" name="cod" value="<%=Utility.sanitize(cod)%>" />
                        <div class="row">
                            <!-- BUY -->
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Type</label>
                                    <div class="input-icon">
                                        <i class="fa fa-check font-green-sharp"></i>
                                        <input type="text" class="form-control uppercase" readonly="readonly" value="<%=first_label%>" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <!-- ID OPEN -->
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Id open till</label>
                                    <div class="input-icon">
                                        <i class="fa fa-bookmark font-green-sharp"></i>
                                        <input type="text" class="form-control uppercase" id="idopentill" readonly="readonly" onkeypress="return keysub(this, event);">
                                        <input type="hidden" id="idopentillv" name="id_open_till">
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="modbook" id="modbook" value="NO"/>



                            <div class="clearfix"></div>
                            <!-- till -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Till</label><p class='ab'></p>
                                    <select class="form-control select2" name="till" id="till" onchange="return changetill();"></select>
                                </div>
                            </div>

                            <!-- open till -->


                            <div class="clearfix"></div>
                            <!-- customer kind -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Customer Kind <span class="font-red">*</span></label><p class='ab'></p>
                                    <select class="form-control select2" name="customerKind" id="customerKind" onchange="return changecustomerKind();" onkeypress="return keysub(this, event);">
                                        <option value="None">None</option>
                                        <%for (int i = 0; i < array_custkind.size(); i++) {
                                                if (array_custkind.get(i).getFg_tipo_cliente().equals("20")) {
                                                    if (ln.isCompanyenabled()) {%>
                                        <option value="<%=array_custkind.get(i).getTipologia_clienti()%>"><%=array_custkind.get(i).getDe_tipologia_clienti()%></option>
                                        <%}
                                        } else {%>
                                        <option value="<%=array_custkind.get(i).getTipologia_clienti()%>"><%=array_custkind.get(i).getDe_tipologia_clienti()%></option>
                                        <%}
                                            }%>
                                    </select>
                                </div>
                            </div>
                            <!-- pay out -->
                            <div class="col-md-6">
                                <div class="mt-element-list">
                                    <div class="mt-list-head list-news font-white bg-green-sharp">
                                        <div class="list-head-title-container">
                                            <h3 class="list-title pull-left"><i class="fa <%=Constant.iconcur%>"></i></h3><h3 class="list-title"> Pay In <b><span class="pull-right" id="payout0"><%=Utility.sanitize(Utility.formatMysqltoDisplay(bo.getEuro()))%></span></b></h3>
                                            <input type="hidden" id="payout1" name="payout1" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(bo.getEuro()))%>"/>
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
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" name="total0" id="total0" readonly="readonly" value="0<%=decimal%>00" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <!-- fix -->
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Fix</label>
                                    <div class="input-icon">
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" id="fix0" name="fix0" readonly="readonly" value="0<%=decimal%>00" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <!-- com -->
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Com</label>
                                    <div class="input-icon">
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" id="com0" name="com0" readonly="readonly" value="0<%=decimal%>00" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <!-- round -->
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Round</label>
                                    <div class="input-icon">
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" id="round0" name="round0" readonly="readonly" value="0<%=decimal%>00" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <!-- commission -->
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Commission</label>
                                    <div class="input-icon">
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" id="commission0" name="commission0" readonly="readonly" value="0<%=decimal%>00" onkeypress="return keysub(this, event);"> 
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <!-- note -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label><i class="fa fa-sticky-note-o font-green-sharp"></i> Client Notes</label>
                                    <input type="text" class="form-control" name="note1" id="note1" maxlength="100" readonly
                                           onkeypress="return keysub(this, event);" 
                                           onchange="return fieldNOSPecial_2(this.id);" value="<%=Utility.sanitize(Engine.visualNote(bo.getNote(), 100))%>"> 
                                </div>
                            </div>
                            <%
                                String intnote = Engine.visualNote(bo.getCrm_note(), 3000);
                            %>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label><i class="fa fa-sticky-note-o font-green-sharp"></i> Internal Notes</label>
                                    <!--<input type="text" class="form-control" name="noteCRM" id="noteCRM" maxlength="3000" readonly
                                           onkeypress="return keysub(this, event);" value=""> -->
                                    <textarea  class="form-control" style="resize:none;" rows="<%=Utility.get_altezza_textarea(intnote)%>" cols="50" 
                                               disabled><%=Utility.sanitize(intnote)%></textarea>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <!-- Internet Booking-->
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-3">
                                        <div class="md-checkbox">
                                            <input type="checkbox" id="newintbook0" name="newintbook0" class="md-checkbox" checked onclick="return false;"> 
                                            <label for="newintbook0">
                                                <span></span>
                                                <span class="check"></span>
                                                <span class="box"></span> Internet Booking
                                            </label>
                                        </div>
                                    </div>
                                    <div id="newintbookdiv">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Channel<p class='ab'></p></label>
                                                <select class="form-control select2" id="newintbookCanale" disabled
                                                        onkeypress="return keysub(this, event);">
                                                    <option value="<%=Utility.sanitize(bo.getCanale())%>"><%=Utility.sanitize(Utility.formatAL(bo.getCanale(), array_intbook, 1))%></option>
                                                </select>
                                                <input type="hidden" name="newintbookCanale" value="<%=Utility.sanitize(bo.getCanale())%>"/>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label>Code</label><p class='ab'></p></label>
                                                <input type="text" class="form-control" name="newintbookCodice" id="newintbookCodice" readonly
                                                       onkeyup="return modificaIB(this, event);" maxlength="50"
                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(bo.getCod())%>" /> 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <p class='ab'></p>
                            <!-- figurees -->
                            <div class="col-md-12">
                                <div class="portlet box green-sharp">
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
                                                        <thead>
                                                            <tr>
                                                                <th class="tabnow inputright" style="width:5px;">#</th>
                                                                <th class="tabnow" style="width:80px;">Kind</th>
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
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <%if (true) {

                                                                    Currency cur = Engine.getALCurrency(bo.getCurrency(), array_currency);

                                                                    int i = 1;
                                                                    String idrow = "trfig" + i;
                                                                    String style = "";
                                                            %>
                                                            <tr id="<%=idrow%>" <%=style%>>
                                                        <input type="hidden" id="roundvalue<%=i%>" name="roundvalue<%=i%>" value="0.00"/>
                                                        <input type="hidden" id="bbvalue<%=i%>" value="<%=array_figsell.get(0).getBuy_back_commission()%>"/>
                                                        <td class="tabnow" valign="top"><%=i%></td>
                                                        <td class="tabnow">
                                                            <select class="form-control select2" id="kind<%=i%>"  data-container="body" disabled
                                                                    onkeypress="return keysub(this, event);" >
                                                                <option value="01">NOTES</option>
                                                            </select>
                                                            <input type="hidden" name="kind<%=i%>" value="01"/>
                                                        </td>
                                                        <td class="tabnow">
                                                            <select class="form-control select2" id="figs<%=i%>" data-container="body" disabled
                                                                    onkeypress="return keysub(this, event);">
                                                                <option><%=Utility.sanitize(cur.getCode())%> - <%=Utility.sanitize(cur.getDescrizione())%></option>
                                                            </select>
                                                            <input type="hidden" name="figs<%=i%>" value="<%=Utility.sanitize(cur.getCode())%>"/>
                                                        </td>
                                                        <td class="tabnow">
                                                            <input type="text" class="form-control inputright" 
                                                                   onfocus="this.select();" id="quantity<%=i%>" maxlength="11"
                                                                   name="quantity<%=i%>" 
                                                                   value="<%=Utility.sanitize(.formatMysqltoDisplay(bo.getTotal()))%>" 
                                                                   onchange="return changequantity('<%=i%>');" 
                                                                   onkeypress="return keysub(this, event);">
                                                        </td>
                                                        <td class="tabnow">
                                                            <div id="divrate<%=i%>">
                                                                <select class="form-control select2"  data-container="body" disabled
                                                                        onkeypress="return keysub(this, event);">
                                                                    <option><%=Utility.sanitize(Utility.formatMysqltoDisplay(bo.getRate()))%></option>
                                                                </select>
                                                                <input type="hidden" id="rate<%=i%>" name="rate<%=i%>" value="<%=Utility.sanitize(bo.getRate())%>"/>
                                                            </div>        
                                                            <input type="text" onfocus="this.select();" class="form-control inputright" id="fieldrate<%=i%>" name="fieldrate<%=i%>" 
                                                                   value="0<%=decimal%>00" style="display: none;" onchange="return change_rate('<%=i%>')" onkeypress="return keysub(this, event);">
                                                        </td>
                                                        <td class="tabnow">
                                                            <input type="text" class="form-control inputright" onfocus="this.select();"
                                                                   id="comperc<%=i%>" name="comperc<%=i%>" value="0<%=decimal%>00" readonly onkeypress="return keysub(this, event);">
                                                        </td>
                                                        <td class="tabnow"><input type="text" class="form-control inputright" id="totperc<%=i%>" name="totperc<%=i%>" readonly="readonly" value="0<%=decimal%>00" onkeypress="return keysub(this, event);"></td>
                                                        <td class="tabnow">
                                                            <input type="text" class="form-control inputright" id="fxcomm<%=i%>" name="fxcomm<%=i%>" readonly="readonly" value="<%=Utility.formatMysqltoDisplay(bo.getFx_comm())%>" onkeypress="return keysub(this, event);">
                                                        </td>
                                                        <td class="tabnow"><input type="text" class="form-control inputright" id="totcomm<%=i%>" name="totcomm<%=i%>" readonly="readonly" value="<%=Utility.formatMysqltoDisplay(bo.getFx_comm())%>" onkeypress="return keysub(this, event);"></td>
                                                        <td class="tabnow">
                                                            <input type="text" class="form-control inputright" id="net<%=i%>" name="net<%=i%>" 
                                                                   value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(bo.getEuro()))%>" readonly
                                                                   onfocus="this.select();" onchange="return changeNet('<%=i%>');" onkeypress="return keysub(this, event);">
                                                        </td>
                                                        <td class="tabnow">
                                                            <input type="text" class="form-control inputright" id="spread<%=i%>" name="spread<%=i%>" value="0<%=decimal%>00" readonly="readonly" onkeypress="return keysub(this, event);">
                                                        </td>
                                                        <td class="tabnow"><input type="text" class="form-control inputright" id="total<%=i%>" name="total<%=i%>" readonly="readonly" value="0<%=decimal%>00" onkeypress="return keysub(this, event);"></td>
                                                        <td class="tabnow">
                                                            <input type="hidden" id="kindfixcomm<%=i%>" name="kindfixcomm<%=i%>" value="007" />
                                                            <select style="width:200px;"class="form-control select2" data-container="body" 
                                                                    onkeypress="return keysub(this, event);" disabled>
                                                                <option value="007">Internet Booking</option>
                                                            </select>
                                                        </td>
                                                        <td class="tabnow">
                                                            <input type="hidden" id="lowcommjus<%=i%>" name="lowcommjus<%=i%>" value="6" />
                                                            <select style="width:200px;"class="form-control select2"  data-container="body"  
                                                                    onkeypress="return keysub(this, event);" disabled>
                                                                <option value="6">Internet Booking</option>
                                                            </select>
                                                        </td>
                                                        </tr>
                                                        <%}%>
                                                        </tbody>
                                                    </table>
                                            </div>
                                            <div class="col-md-4">
                                            </div>
                                            <div class="col-md-4"></div>
                                            <h4 class="col-md-4" style="text-align: right;">Net Total <b id="totalnet">0<%=decimal%>00</b></h4>
                                            <div class="col-md-4"></div>
                                            <div class="col-md-4"></div>
                                            <%if (Constant.newpread) {%>
                                            <div class="col-md-4"></div>
                                            <h4 style="display: none;">Spread Total <b id="totalspread">0<%=decimal%>00</b></h4>
                                            <%} else {%>
                                            <h4 class="col-md-4" style="text-align: right;">Spread Total <b id="totalspread">0<%=decimal%>00</b></h4>
                                            <%}%>
                                            <input type="hidden" name="totalspread" id="totalspreadv" value="0<%=decimal%>00"/>
                                            <div class="col-md-4"></div><div class="col-md-4"></div>
                                            <h4 class="col-md-4" style="text-align: right;">Total <b id="total00">0<%=decimal%>00</b></h4>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>

                            <div class="col-md-6">
                                <%
                                    ArrayList<String[]> result = Engine.agevolazioni_varie(bo.getDt(), "S");
                                    if (!result.isEmpty()) {
                                %>
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
                                                        <%for (int x = 0; x < result.size(); x++) {
                                                                String sel = "";
                                                                if (bo.getSconti().contains(result.get(x)[0])) {
                                                                    sel = "checked";
                                                                }
                                                                String vv = "0.00";
                                                                String tipo = result.get(x)[2];
                                                                String perc = result.get(x)[3];
                                                                String valu = result.get(x)[4];
                                                                String sogliaminima = result.get(x)[5];
                                                                if (tipo.equals("0")) {
                                                                    vv = Utility.roundDoubleandFormat((Utility.fd(bo.getTotal()) / Utility.fd(bo.getRate()) + Utility.fd(bo.getFx_comm())) * Utility.fd(perc) / 100.00, 2);
                                                                } else {
                                                                    vv = valu;
                                                                }%>
                                                        <tr>
                                                            <td><div class="md-checkbox">
                                                                    <input type="checkbox" name="ag_<%=Utility.sanitize(result.get(x)[0])%>" 
                                                                           id="ag_<%=Utility.sanitize(result.get(x)[0])%>" class="md-checkbox" 
                                                                           <%=sel%> 
                                                                           onclick="return mod_fx(false, '<%=sogliaminima%>', this);" /> 
                                                                    <input type="hidden" id="value_ag_<%=Utility.sanitize(result.get(x)[0])%>"
                                                                           value="<%=Utility.sanitize(vv)%>" />
                                                                    <label for="ag_<%=Utility.sanitize(result.get(x)[0])%>">
                                                                        <span></span>
                                                                        <span class="check"></span>
                                                                        <span class="box"></span>  <%=Utility.sanitize(result.get(x)[1])%>
                                                                    </label>
                                                                </div>
                                                            </td>
                                                            <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(vv))%></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                            <div class="col-md-6">
                                <%
                                    ArrayList<String[]> ser = Engine.servizi_agg(bo.getDt(),"S");
                                    if (!ser.isEmpty()) {
                                %>
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
                                                <table class="table table-responsive" id="sample_3" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>Name</th>
                                                            <th>Value</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            for (int x = 0; x < ser.size(); x++) {
                                                                String sel = "";
                                                                if (bo.getProdotti().contains(ser.get(x)[0])) {
                                                                    sel = "checked";
                                                                }
                                                                String vv = "0.00";
                                                                String tipo = ser.get(x)[2];
                                                                String perc = ser.get(x)[3];
                                                                String valu = ser.get(x)[4];
                                                                String sogliaminima = ser.get(x)[5];
                                                                if (tipo.equals("0")) {
                                                                    vv = Utility.roundDoubleandFormat((Utility.fd(bo.getTotal()) / Utility.fd(bo.getRate()) + Utility.fd(bo.getFx_comm())) * Utility.fd(perc) / 100.00, 2);
                                                                } else {
                                                                    vv = valu;
                                                                }
                                                        %>
                                                        <tr>
                                                            <td>
                                                                <div class="md-checkbox">
                                                                    <input type="checkbox" name="se_<%=Utility.sanitize(ser.get(x)[0])%>" id="se_<%=Utility.sanitize(ser.get(x)[0])%>"
                                                                           class="md-checkbox" <%=sel%>
                                                                           onclick="return mod_fx(true, '<%=Utility.sanitize(sogliaminima)%>', this);" />
                                                                    <input type="hidden" id="value_se_<%=Utility.sanitize(ser.get(x)[0])%>"
                                                                           value="<%=Utility.sanitize(vv)%>" />
                                                                    <label for="se_<%=Utility.sanitize(ser.get(x)[0])%>">
                                                                        <span></span>
                                                                        <span class="check"></span>
                                                                        <span class="box"></span> <%=Utility.sanitize(ser.get(x)[1])%>
                                                                    </label>
                                                                </div>
                                                            </td>
                                                            <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(vv))%></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                            <script type="text/javascript">
                                function get_fx_solocalcolo() {

                                    var fx = parseFloatRaf('<%=Utility.sanitize(bo.getFx_comm())%>', separatorthousand, separatordecimal);
                                    $("#form1 input:checkbox:checked").each(function () {
                                        var id = $(this).attr('id');
                                        if (id.startsWith("ag_")) {
                                            fx = fx - parseFloatRaf(document.getElementById("value_" + id).value, separatorthousand, separatordecimal);
                                        } else if (id.startsWith("se_")) {
                                            fx = fx + parseFloatRaf(document.getElementById("value_" + id).value, separatorthousand, separatordecimal);
                                        }
                                    });
                                    return fx;
                                }

                                function get_fx() {

                                    document.getElementById("fxcomm1").value = "0" + separatordecimal + "00";
                                    document.getElementById("totcomm1").value = "0" + separatordecimal + "00";

                                    //var fx = 0.0;


                                    var fx = parseFloatRaf('<%=Utility.sanitize(bo.getFx_comm())%>', separatorthousand, separatordecimal);
                                    $("#form1 input:checkbox:checked").each(function () {
                                        var id = $(this).attr('id');
                                        if (id.startsWith("ag_")) {
                                            fx = fx - parseFloatRaf(document.getElementById("value_" + id).value,
                                                    separatorthousand, separatordecimal);
                                        } else if (id.startsWith("se_")) {
                                            fx = fx + parseFloatRaf(document.getElementById("value_" + id).value,
                                                    separatorthousand, separatordecimal);
                                        }
                                    });

                                    var output = fx;
                                    if (fx < 0) {
                                        fx = 0.0;
                                    }

                                    fx = new BigNumber(fx + '').toFixed(2);
                                    document.getElementById("fxcomm1").value = fx;
                                    document.getElementById("totcomm1").value = fx;
                                    console.log("c) " + fx);

                                    formatValueDecimal_1(document.getElementById("fxcomm1"), separatorthousand, separatordecimal);
                                    formatValueDecimal_1(document.getElementById("totcomm1"), separatorthousand, separatordecimal);

                                    var finalfx = new BigNumber(output + '').toFixed(2, 5);
                                    return finalfx;

                                }

                                function mod_fx(add, value, check) {
                                    if (check.checked) {
                                        var total = parseFloatRaf(document.getElementById("total0").value, separatorthousand, separatordecimal);
                                        var thr = parseFloatRaf(value, separatorthousand, separatordecimal);
                                        if (total > 0 && total < thr) {
                                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                            document.getElementById('errorlarge').style.display = "block";
                                            if (add) {
                                                document.getElementById('errorlargetext').innerHTML = "Unable to add this product. Please verify 'Pay In'.";
                                            } else {
                                                document.getElementById('errorlargetext').innerHTML = "Unable to add this discount. Please verify 'Pay In'.";
                                            }
                                            return false;
                                        }
                                    }
                                    setvalue("1");
                                }

                                function ismodified() {
                                    if ($('#newintbookCanale').val() === '01') {
                                        var oldvaluebo = document.getElementById("oldvaluebo").value;
                                        var nowvaluebo = document.getElementById("payout1").value;
                                        var kind_p1 = document.getElementById("kind_p1").value;
                                        var posnum = document.getElementById("posnum").value;

                                        var modi = parseFloatRaf(nowvaluebo, separatorthousand, separatordecimal) >
                                                parseFloatRaf(oldvaluebo, separatorthousand, separatordecimal);

                                        //var modi = parseFloatRaf(oldvaluebo, separatorthousand, separatordecimal) !== parseFloatRaf(nowvaluebo, separatorthousand, separatordecimal);

                                        if (modi || kind_p1 !== "06" || posnum !== "999") {
                                            document.getElementById("modifiedbooking").style.display = "";
                                            document.getElementById("modbook").value = "YES";
                                        } else {
                                            document.getElementById("modifiedbooking").style.display = "none";
                                            document.getElementById("modbook").value = "NO";
                                        }
                                    } else {
                                        document.getElementById("modifiedbooking").style.display = "none";
                                        document.getElementById("modbook").value = "NO";
                                    }
                                }
                            </script>

                            <input type="hidden" id="oldvaluebo" value="<%=Utility.sanitize(bo.getEuro())%>"/>
                            <input type="hidden" name="oldclient" id="oldclient" value="0"/>
                            <input type="hidden" name="unlockCode_final" id="unlockCode_final" value="-"/>
                            <input type="hidden" name="typerate" id="typerate" value="NORMAL"/>
                            <input type="hidden" name="thranag" id="thranag" value="0"/>
                            <input type="hidden" name="showmod" id="showmod" value="-1"/>
                            <input type="hidden" name="tf" id="tf" value="0"/>
                            <input type="hidden" name="maxweek" id="maxweek" value="0"/>

                            <div id="showanag" style="display: none;">
                                <!-- customer information pf -->
                                <div class="col-md-12" id="custinfo_1">
                                    <div class="portlet box green-sharp">
                                        <div class="portlet-title">
                                            <div class="caption">
                                                <i class="fa fa-user"></i>
                                                <span class="caption-subject">KYC - Know Your Customer</span>
                                            </div>
                                        </div>
                                        <div class="portlet-body">
                                            <div class="row">
                                                <div class="col-md-2">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-credit-card font-blue"></i> Loyalty Code</label>
                                                        <input type="text" class="form-control" id="loya" name="loya" 
                                                               maxlength="8" 
                                                               onkeyup="return modificaLOY(this, event);"
                                                               onkeypress="return keysub(this, event);"/> 

                                                    </div>
                                                </div> 
                                                <div class="col-md-2">
                                                    <label>&nbsp;</label>
                                                    <div class="input-icon">
                                                        <button type="button" class="btn btn-outline dark tooltips" 
                                                                title="Associate / Verify" onclick="return verLOY();">
                                                            <i class="fa fa-chevron-circle-down"></i>
                                                        </button>
                                                    </div> 
                                                </div>
                                                <%if (false) {%>
                                                <div class="col-md-4">
                                                    <label>&nbsp;</label>
                                                    <div class="md-checkbox">
                                                        <input type="checkbox" id="welc" name="welc" disabled
                                                               class="md-checkbox" 
                                                               onkeypress="return keysub(this, event);"> 
                                                        <label for="welc">
                                                            <span></span>
                                                            <span class="check"></span>
                                                            <span class="box"></span> Welcome Kit
                                                        </label>

                                                    </div>
                                                    <label id="ex9">&nbsp;</label>
                                                </div>
                                                <div class="col-md-4">
                                                    <label>&nbsp;</label>
                                                    <div class="md-checkbox">
                                                        <input type="checkbox" id="fidel" name="fidel" disabled
                                                               class="md-checkbox" onkeypress="return keysub(this, event);"> 
                                                        <label for="fidel">
                                                            <span></span>
                                                            <span class="check"></span>
                                                            <span class="box"></span> Active NEW Top Italy Card
                                                        </label>

                                                    </div>
                                                    <label id="ex10">&nbsp;</label>
                                                </div>
                                                <script type="text/javascript">
                                                    function verifica_wk() {
                                                        $.ajax({
                                                            type: "POST",
                                                            url: "Query?type=query_quantity_WK&q=WELKITSELL",
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
                                                    }
                                                    function verifica_ti() {
                                                        $.ajax({
                                                            type: "POST",
                                                            url: "Query?type=query_quantity_WK&q=TOPITACARD",
                                                            success: function (data) {
                                                                if (data !== "") {
                                                                    var arrayJson = JSON.parse(data);
                                                                    if (arrayJson.length > 0) {
                                                                        document.getElementById('ex10').style.display = '';
                                                                        document.getElementById('ex10').innerHTML = "";
                                                                        for (var i = 0; i < arrayJson.length - 1; i = i + 2) {
                                                                            var span1 = document.createElement('span');
                                                                            span1.className = 'label label-sm label-success';
                                                                            span1.innerHTML = arrayJson[i] + ' - <b>' + arrayJson[i + 1] + '</b>';
                                                                            var span2 = document.createElement('span');
                                                                            span2.innerHTML = '&nbsp;';
                                                                            document.getElementById('ex10').appendChild(span1);
                                                                            document.getElementById('ex10').appendChild(span2);
                                                                        }
                                                                    } else {
                                                                        document.getElementById('ex10').innerHTML = "";
                                                                        document.getElementById('ex10').style.display = 'none';
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }
                                                </script>
                                                <%}%>
                                            </div>
                                            <hr>
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="form-group fgsearch">
                                                        <label><i class="fa fa-user font-green-sharp"></i> Surname <span class='font-red'>*</span></label>
                                                        <input type="text" class="form-control" id="heavy_surname" 
                                                               name="heavy_surname" maxlength="30"
                                                               value="<%=Utility.sanitize(bo.getCl_cognome())%>"
                                                               onchange="return verificanomecogn();" 
                                                               onkeypress="return keysub(this, event);"
                                                               onkeyup="return modificaOAMSurname(this, event);" /> 
                                                    </div>
                                                </div>

                                                <%

                                                    String sel_M = "";
                                                    String sel_F = "";
                                                    if (bo.getCl_sesso().equals("M")) {
                                                        sel_M = "selected";
                                                    } else if (bo.getCl_sesso().equals("F")) {
                                                        sel_F = "selected";
                                                    }

                                                %>

                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-user font-green-sharp"></i> Sex <span class='font-red'>*</span></label>
                                                        <select class="form-control select2" name="heavy_sex" id="heavy_sex">
                                                            <option value=""></option>
                                                            <option <%=sel_M%> value="M">Male</option>
                                                            <option <%=sel_F%> value="F">Female</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label>
                                                        <div class="input-icon">
                                                            <button type="button" class="btn btn-outline dark tooltips" 
                                                                    id="btscdoc"
                                                                    title="Scan Document"
                                                                    data-toggle="modal" data-target="#scandoc1" >
                                                                <i class="fa fa-image"></i>
                                                            </button>
                                                            <button class="btn btn-outline dark tooltips" type="button" 
                                                                    title="Read Document" data-toggle="modal" href="#largereaddoc">
                                                                <i class="fa fa-file"></i>
                                                            </button>
                                                            <button class="btn btn-outline dark tooltips" type="button" title="Check Tax Code" id="buttoncheckcf" 
                                                                    onclick="return checkcodfisc();">
                                                                <i class="fa fa-credit-card"></i>
                                                            </button>
                                                            <button id="buttoncheckaeg"
                                                                    target="_blank" 
                                                                    onclick="return linkAE();"
                                                                    class="btn btn-outline dark tooltips" 
                                                                    title="Check AE">
                                                                <i class="fa fa-tasks"></i>
                                                            </button>
                                                            <button type="button" id="verforeign"
                                                                    onclick="return eseguiricercaclientestraniero();"
                                                                    class="btn btn-outline dark tooltips" 
                                                                    title="Verify Client (FOREIGN)">
                                                                <i class="fa fa-user-circle"></i>
                                                            </button>
                                                            <%
                                                                if (false) {
                                                                    String token = "ADD" + Utility.generaId(47);
                                                                    String fisso = Utility.createServerReturnADDAddress();
                                                                    String signport = Engine.getPath("signport");
                                                                    if (signport == null) {
                                                                        signport = "4880";
                                                                    }%>
                                                            <script type="text/javascript" >
                                                                function start_addr() {
                                                                    var d1 = '{\n\
                                                                        \"Command\":\"ADDRESS\",\n\
                                                                        \"Token\":\"<%=token%>\",\n\
                                                                        \"ad1\":\"\",\n\
                                                                        \"ma1\":\"\",\n\
                                                                        \"te1\":\"\",\n\
                                                                        \"Response\":\"\",\n\
                                                                        \"CallbackUrl\":\"<%=fisso%>",\n\
                                                                    \"Response\":\"\"}';

                                                                    $.ajax({
                                                                        async: true,
                                                                        url: 'http://localhost:<%=signport%>/',
                                                                        dataType: 'TEXT',
                                                                        type: 'POST',
                                                                        contentType: 'application/json',
                                                                        data: d1,
                                                                        success: function (data, textStatus, jQxhr) {
                                                                            console.info(data);
                                                                            console.info(textStatus);
                                                                            console.info(jQxhr);
                                                                        },
                                                                        error: function (jqXhr, textStatus, errorThrown) {
                                                                            console.error("RAF: " + this.data);
                                                                            console.error("RAF: " + errorThrown);
                                                                            console.error("RAF: " + textStatus);
                                                                            console.error("RAF: " + jqXhr);
                                                                            document.getElementById('errorlargetext').innerHTML = "APPLICATION ERROR. CONNECTION REFUSED.";
                                                                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                                                            document.getElementById('errorlarge').style.display = "block";
                                                                            return false;
                                                                        }
                                                                    });

                                                                }
                                                                function read_addr(token) {
                                                                    $.ajax({
                                                                        async: false,
                                                                        type: "POST",
                                                                        url: "Scan?type=checkADDR&token=" + token,
                                                                        success: function (data) {
                                                                            var arrayJson = JSON.parse(data);
                                                                            if (arrayJson[0] === 'true') {
                                                                                document.getElementById("heavy_address").value = arrayJson[1];
                                                                                document.getElementById("heavy_phonenu").value = arrayJson[2];
                                                                                document.getElementById("heavy_email").value = arrayJson[3];
                                                                                document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                                                                                document.getElementById('infolarge').style.display = "block";
                                                                                document.getElementById('infolargetext').innerHTML = "The customer data entered has been loaded!";
                                                                                return false;
                                                                            } else {
                                                                                document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                                                                                document.getElementById('infolarge').style.display = "block";
                                                                                document.getElementById('infolargetext').innerHTML = "Customer data NOT FOUND!";
                                                                                return false;
                                                                            }
                                                                        }
                                                                    });
                                                                }
                                                            </script>
                                                            <button type="button" class="btn btn-outline dark tooltips" onclick="return start_addr();"
                                                                    title="Start Insert Address">
                                                                <i class="fa fa-map-o"></i>
                                                            </button>
                                                            <button type="button" class="btn btn-outline dark tooltips" 
                                                                    title="Read Address" onclick="return read_addr('<%=token%>');">
                                                                <i class="fa fa-map-signs"></i>
                                                            </button>
                                                            <%}%>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-4">
                                                    <div class="form-group fgsearch">
                                                        <label><i class="fa fa-user font-green-sharp"></i> Name <span class='font-red'>*</span></label>
                                                        <input type="text" class="form-control" id="heavy_name" name="heavy_name" maxlength="30" 
                                                               onchange="return verificanomecogn();"
                                                               value="<%=Utility.sanitize(bo.getCl_nome())%>"
                                                               onkeyup="return modificaOAMSurname(this, event);" 
                                                               onkeypress="return keysub(this, event);"> 
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-credit-card font-green-sharp"></i> Tax Code <span class='font-red' id="taxcodespan">*</span></label>
                                                        <input type="text" class="form-control uppercase" 
                                                               id="heavy_codfisc" name="heavy_codfisc" maxlength="16"
                                                               onchange="return checkcodfisc();"  
                                                               value="<%=Utility.sanitize(bo.getCl_codfisc().trim())%>"
                                                               onkeypress="return keysub(this, event);"
                                                               onkeyup="return fieldNOSPecial_1(this.id);"/>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-flag font-green-sharp"></i> Country <span class='font-red'>*</span></label>
                                                        <input type="hidden" name="heavy_country_dis" id="heavy_country_dis"/>
                                                        <select class="form-control select2" id="heavy_country" name="heavy_country" 
                                                                disabled="disabled" 
                                                                onkeypress="return keysub(this, event);"></select>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-home font-green-sharp"></i> City <span class='font-red'>*</span></label>
                                                        <input class="form-control" type="text" 
                                                               name="heavy_city_dis" id="heavy_city_dis" 
                                                               style="display: none;" maxlength="40"
                                                               onkeypress="return keysub(this, event);" 
                                                               onkeyup="return modificaOAMSurname(this, event);"/>
                                                        <div class="clearfix" id="heavy_city_div">
                                                            <select class="form-control select2" name="heavy_city" 
                                                                    id="heavy_city" disabled="disabled" 
                                                                    style="width: 100%;" onkeypress="return keysub(this, event);">
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-2" id="pepITA">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-user font-green-sharp"></i> Client is PEP <span class='font-red'>*</span></label>
                                                        <div class="clearfix">
                                                            <select class="form-control select2" name="heavy_pepI" id="heavy_pepI" onkeypress="return keysub(this, event);">
                                                                <option value="NO">No</option>
                                                                <option value="YES">Yes</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-8">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-home font-green-sharp"></i> Address <span class='font-red'>*</span></label>
                                                        <input type="text" class="form-control" id="heavy_address" maxlength="100" 
                                                               value="<%=Utility.sanitize(bo.getCl_indirizzo())%>"
                                                               name="heavy_address" onkeypress="return keysub(this, event);" 
                                                               onkeyup="return modificaOAM_Add(this, event);"/> 

                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-home font-green-sharp"></i> Zip Code 
                                                            <span class='font-red' id="zipcodelabel">*</span></label>
                                                        <input type="text" class="form-control" id="heavy_zipcode" 
                                                               value="<%=Utility.sanitize(bo.getCl_indirizzocap())%>"
                                                               onkeyup="return fieldNOSPecial_1(this.id);" name="heavy_zipcode" 
                                                               maxlength="15" onkeypress="return keysub(this, event);"> 
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-home font-green-sharp"></i> District <span class='font-red' id="heavy_districtspan">*</span></label>
                                                        <input class="form-control" type="text" name="heavy_district_dis" 
                                                               id="heavy_district_dis" style="display: none;" readonly="readonly" 
                                                               onkeypress="return keysub(this, event);" maxlength="2"
                                                               onkeyup="return fieldNOSPecial_1(this.id);"/>
                                                        <div id="heavy_district_div">
                                                            <select class="form-control select2" id="heavy_district" 
                                                                    name="heavy_district" disabled="disabled"  style="width: 100%;" 
                                                                    onkeypress="return keysub(this, event);">
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-12">
                                                    <div class="panel panel-default">
                                                        <div class="panel-heading">
                                                            <h3 class="panel-title">Place of Birth</h3>
                                                        </div>
                                                        <div class="panel-body"> 
                                                            <div class="col-md-3">
                                                                <div class="form-group">
                                                                    <label><i class="fa fa-home font-green-sharp"></i> City <span class='font-red'>*</span></label>
                                                                    <input type="text" class="form-control" name="heavy_pob_city" 
                                                                           id="heavy_pob_city" maxlength="40" value="<%=Utility.sanitize(bo.getCl_city())%>"
                                                                           onkeypress="return keysub(this, event);"
                                                                           onkeyup="return modificaOAMSurname(this, event);" />  
                                                                </div>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <div class="form-group">
                                                                    <label><i class="fa fa-home font-green-sharp"></i> District <span class='font-red' id="heavy_pob_districtspan">*</span></label>
                                                                    <input type="text" class="form-control" name="heavy_pob_district_STR" 
                                                                           id="heavy_pob_district_STR" readonly="readonly" 
                                                                           onkeypress="return keysub(this, event);" maxlength="2"
                                                                           onkeyup="return fieldNOSPecial_1(this.id);" 
                                                                           style="display: none;" /> 
                                                                    <div id="heavy_pob_district_div">
                                                                        <select class="form-control select2" id="heavy_pob_district" name="heavy_pob_district" onkeypress="return keysub(this, event);">
                                                                            <option value="---">None</option>
                                                                            <%for (int i = 0; i < array_district.size(); i++) {
                                                                            %>
                                                                            <option value="<%=array_district.get(i)[0]%>"><%=array_district.get(i)[1]%></option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <div class="form-group fgsearch">
                                                                    <label><i class="fa fa-flag font-green-sharp"></i> Country <span class='font-red'>*</span></label>
                                                                    <select class="form-control select2" id="heavy_pob_country" name="heavy_pob_country" 
                                                                            onchange="return changedistrictpob();" onkeypress="return keysub(this, event);">
                                                                        <option value="---">None</option>
                                                                        <%for (int i = 0; i < array_country.size(); i++) {
                                                                                String sel = "";
                                                                                if (bo.getCl_nazione().equalsIgnoreCase(array_country.get(i)[0])) {
                                                                                    sel = "selected";
                                                                                }
                                                                        %>
                                                                        <option <%=sel%> value="<%=array_country.get(i)[0]%>"><%=array_country.get(i)[1]%></option>
                                                                        <%}%>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <div class="form-group fgsearch">
                                                                    <label><i class="fa fa-calendar font-green-sharp"></i> Date <span class='font-red'>*</span></label>
                                                                    <input type="text" class="form-control date-picker" value="<%=Utility.sanitize(bo.getCl_dtnascita())%>"
                                                                           name="heavy_pob_date" id="heavy_pob_date"  onchange="return ricercaclientestraniero(true);"
                                                                           onkeypress="return keysub(this, event);"> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-credit-card font-green-sharp"></i> Identification Card <span class='font-red'>*</span></label>
                                                        <select class="form-control select2" id="heavy_identcard" name="heavy_identcard" onkeypress="return keysub(this, event);">
                                                            <option value="---">None</option>
                                                            <%for (int i = 0; i < array_identificationCard.size(); i++) {%>
                                                            <option value="<%=array_identificationCard.get(i)[0]%>"><%=array_identificationCard.get(i)[1]%></option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-credit-card font-green-sharp"></i> Number of Id. Card <span class='font-red'>*</span></label>
                                                        <input type="text" class="form-control uppercase" id="heavy_numberidentcard" 
                                                               name="heavy_numberidentcard" maxlength="20" 
                                                               onkeypress="return keysub(this, event);" 
                                                               onkeyup="return modificaOAM(this, event);" />  
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-calendar font-green-sharp"></i> Issue Date <span class='font-red'>*</span></label>

                                                        <input type="text" class="form-control date-picker" id="heavy_issuedateidentcard"name="heavy_issuedateidentcard" maxlength="100" onkeypress="return keysub(this, event);"> 

                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-calendar font-green-sharp"></i> Expiration Date <span class='font-red'>*</span></label>

                                                        <input type="text" class="form-control date-picker" id="heavy_exdateidentcard" name="heavy_exdateidentcard" maxlength="100" onchange="return checkheavy_exdateidentcard();" onkeypress="return keysub(this, event);"> 

                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-credit-card font-green-sharp"></i> Issued By <span class='font-red'>*</span></label>
                                                        <input type="text" class="form-control" id="heavy_issuedbyidentcard" name="heavy_issuedbyidentcard" 
                                                               maxlength="40" onkeypress="return keysub(this, event);" 
                                                               onkeyup="return modificaOAM_soloap(this, event);" /> 
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-credit-card font-green-sharp"></i> Place of Issue <span class='font-red'>*</span></label>
                                                        <input type="text" class="form-control" id="heavy_issuedplaceidentcard" 
                                                               name="heavy_issuedplaceidentcard" 
                                                               maxlength="40"
                                                               onkeypress="return keysub(this, event);" 
                                                               onkeyup="return modificaOAM_soloap(this, event);" />
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-envelope font-green-sharp"></i> Email</label>
                                                        <input type="text" class="form-control" id="heavy_email" name="heavy_email" 
                                                               value="<%=Utility.sanitize(bo.getCl_email())%>"
                                                               onkeypress="return keysub(this, event);" onkeyup="return fieldNOSPecial_2(this.id);"> 
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-phone font-green-sharp"></i> Phone Number</label>
                                                        <input type="text" class="form-control" id="heavy_phonenu" name="heavy_phonenu" 
                                                               value="<%=Utility.sanitize(bo.getCl_telefono())%>"
                                                               onkeypress="return keysub(this, event);" onkeyup="return fieldOnlyNumber(this.id);"> 
                                                    </div>
                                                </div>
                                                <div class="col-md-4" id="dataverified" style="display: none;">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label>
                                                        <div class="md-checkbox">
                                                            <input type="checkbox" id="verdat" name="verdat"> 
                                                            <label for="verdat">
                                                                <span></span>
                                                                <span class="check"></span>
                                                                <span class="box"></span> The data has been verified
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="clearfix"></div>
                                <!-- customer information pg -->
                                <div class="col-md-12" id="custinfo_2">
                                    <div class="portlet box green-sharp">
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
                                                        <label>Company <span class='font-red'>*</span></label><p class='ab'></p>
                                                        <select class="form-control select2" name="company" id="company">
                                                            <%for (int i = 0; i < array_listCompany.size(); i++) {%>
                                                            <option value="<%=array_listCompany.get(i).getNdg()%>">
                                                                <%=array_listCompany.get(i).getRagione_sociale() + " - " + array_listCompany.get(i).getCognome() + " " + array_listCompany.get(i).getNome()%>
                                                            </option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>                  
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
                                                    <label>Local Figures <span class='font-red'>*</span></label>
                                                    <select class="form-control select2" id="kind_p1" name="kind_p1" placeholder="..." onchange="return changelocalfig();" onkeypress="return keysub(this, event);">
                                                        <option value="...">...</option>
                                                        <%for (int i = 0; i < array_kind_pay.size(); i++) {%>
                                                        <option value="<%=Utility.sanitize(array_kind_pay.get(i)[0])%>"><%=Utility.sanitize(array_kind_pay.get(i)[1])%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-3" id="ban_0">
                                                <div class="form-group">
                                                    <label>Bank <span class='font-red'>*</span></label>
                                                    <select class="form-control select2" id="ban_1" name="ban_1" placeholder="..." onkeypress="return keysub(this, event);">
                                                        <option value="...">None</option>
                                                        <%for (int i = 0; i < array_bankacc.size(); i++) {%>
                                                        <option value="<%=array_bankacc.get(i)[0]%>"><%=array_bankacc.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-3" id="pos_0">
                                                <div class="form-group">
                                                    <label>Pos <span class='font-red'>*</span></label>
                                                    <select class="form-control select2" id="posnum" name="posnum" placeholder="..." onkeypress="return keysub(this, event);" onchange="return ismodified();">
                                                        <option value="...">None</option>
                                                        <%for (int i = 0; i < array_credit_card.size(); i++) {%>
                                                        <option value="<%=array_credit_card.get(i)[0]%>"><%=array_credit_card.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <!-- pos number -->
                                            <div class="col-md-3" id="pos_1">
                                                <div class="form-group">
                                                    <label>CC Number <span class='font-red'>*</span></label>
                                                    <div class="input-icon">
                                                        <i class="fa fa-credit-card font-green-sharp"></i>
                                                        <input type="text" class="form-control" name="cc_number" id="cc_number" maxlength="4"  
                                                               onkeyup="return fieldOnlyNumber(this.id);" onkeypress="return keysub(this, event);"> 
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <hr>
                            </div>
                            <div class="col-md-4 ">
                                <div class="md-checkbox">
                                    <input type="checkbox" id="kycpres" name="kycpres" 
                                           class="md-checkbox" onkeypress="return keysub(this, event);"> 
                                    <label for="kycpres">
                                        <span></span>
                                        <span class="check"></span>
                                        <span class="box"></span> KYC User Reporting
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4 " id="notesbig">
                                <div class="md-checkbox">
                                    <input type="checkbox" id="notesbigV" name="notesbigV" 
                                           class="md-checkbox" 
                                           onkeypress="return keysub(this, event);" />
                                    <label for="notesbigV">
                                        <span></span>
                                        <span class="check"></span>
                                        <span class="box"></span> Large Notes (200/500 &#8364;)
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <hr>
                            </div>


                        </div>
                        <div class="row">
                            <div class="col-md-12" id="modifiedbooking" style="display: none;">
                                <div class="form-group">
                                    <center><span class="label label-primary uppercase"><i class="fa fa-exclamation-triangle"></i> the Booking has been changed.</span></center>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <center><button class="btn btn-lg red btn-block" type="submit" id="butconf"><i class="fa fa-save"></i> Confirm Transaction</button></center>
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
            <input type="hidden" id="ipaddress" value=""/>
            <!-- END FOOTER -->
            <!--    [if lt IE 9]>
            <script src="../assets/global/plugins/respond.min.js"></script>
            <script src="../assets/global/plugins/excanvas.min.js"></script> 
            <![endif]   -->
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
            <script src="assets/soop/js/select2-tab-fix.min.js" type="text/javascript"></script>
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
                                                           pageLength: 500,
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
                                                               {orderable: !1, targets: [13]}
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
                            pageLength: 500,
                            scrollX: true,
                            scrollY: false,
                            columnDefs: [
                                {orderable: !1, targets: [0]},
                                {orderable: !1, targets: [1]}
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
                        var e = $("#sample_3");
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
                            scrollX: false,
                            scrollY: true,
                            pageLength: 500,
                            columnDefs: [
                                {orderable: !1, targets: [0]},
                                {orderable: !1, targets: [1]}
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