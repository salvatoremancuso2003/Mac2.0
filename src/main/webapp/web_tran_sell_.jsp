<%@page import="rc.so.entity.BlacklistM"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.entity.Booking"%>
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
        <title>Mac2.0 - Sell Tr. Website</title>
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
            String esito = (String) session.getAttribute("esito_s");
            if (esito == null) {
                esito = "none";
            }
            session.setAttribute("esito_s", null);

            String anag = request.getParameter("anag");
            if (anag == null) {
                anag = "";
            }

            String pswx = Utility.checkAttribute(session,"us_pwd");
            String nation = Constant.nation;
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;

            List_new ln = new List_new("S", session);
            String local_cur = ln.getLocalcur()[0];
            ArrayList<String[]> array_till = ln.getArray_till();
            ArrayList<Till> array_till_open = ln.getArray_till_open();
            ArrayList<CustomerKind> array_custkind = ln.getArray_custkind();
            ArrayList<String[]> array_country = ln.getArray_country();
            ArrayList<String[]> array_country_1 = ln.getCountryFlag("1");
            ArrayList<String[]> array_country_2 = ln.getCountryFlag("2");
            ArrayList<String[]> array_country_3 = ln.getCountryFlag("3");
            ArrayList<String[]> array_identificationCard = ln.getArray_identificationCard();
            ArrayList<String[]> array_credit_card = ln.getArray_credit_card();
            ArrayList<Company> array_listCompany = ln.getArray_listCompany();
            ArrayList<String[]> array_list_oc_change = ln.getArray_list_oc_change();
            ArrayList<String[]> array_currency_min_sizes = ln.getArray_currency_min_sizes();
            ArrayList<Client> array_client = new ArrayList<Client>();

            ArrayList<String[]> array_kind_pay = ln.getArray_kind_pay();
            ArrayList<String[]> array_intbook = ln.getArray_internetbooking();
            ArrayList<Figures> array_figsell = ln.getArray_figsell();
            //CAMBIARE - verificare
            ArrayList<BlacklistM> array_bl = ln.getArray_blm();
            ArrayList<String[]> array_district = ln.getArray_district();

        %>

        <script type="text/javascript">

            function checkesito() {
                var es1 = '<%=esito%>';
                if (es1 !== "none") {
                    var ermsg = "";
                    if (es1 === "kobl") {
                        ermsg = "<span class='font-red'>Attention! </span>Not executable transaction. This name is present in the international blacklist";
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
                    } else if (es1 === "koins") {
                        ermsg = "Generic error. Check values and try again";
                    }

                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = ermsg;
                }
            }

            var separatordecimal = '<%=decimal%>';
            var separatorthousand = '<%=thousand%>';
            //  show row 
            //  reset row

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
                ;
                if (cf === "") {
                    return false;
                }

                var surname = document.getElementById("heavy_surname").value.trim().toUpperCase();
                ;
                var name = document.getElementById("heavy_name").value.trim().toUpperCase();
                ;
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
                    var mo = cf.substring(8, 9).trim();
                    var datebirth = dn + "/" + formatMonthcf(mo) + "/" + "19" + an;
                    var codcom = cf.substring(11, 15).trim().toUpperCase();
                    var country = "";
                    var city = "";
                    var district = "";
                    var found_country = false;
                    var found_city = false;
                    if (codcom.length > 1) {
                        var start = codcom.substring(0, 1).trim();
                        country = '<%=ln.getNazioneCodice("ITALIA")%>';
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
                                disable_sel2('heavy_pob_district', 'form1');
                                document.getElementById('heavy_pob_district_div').style.display = "";
                                document.getElementById("heavy_pob_district_STR").style.display = "none";
                                document.getElementById("heavy_pob_district_STR").value = "";
                                $('#heavy_pob_district').val(district).change();
                            }
                        });





                    }

                    $('#heavy_sex').val(sex).change();
                    disable_sel2('heavy_sex', 'form1');
                    document.getElementById("heavy_codfisc").value = cf;
                    document.getElementById("heavy_pob_date").value = datebirth;
                    $("#heavy_pob_date").attr('readonly', 'readonly');
                    $('#heavy_pob_country').val(country).change();
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
                var kind = 'kind' + index;
                $('#' + kind).select2({
                    dropdownAutoWidth: true
                });
            <%for (int i = 0; i < array_figsell.size(); i++) {%>
                var o = $("<option/>", {value: "<%=array_figsell.get(i).getSupporto()%>", text: "<%=array_figsell.get(i).getDe_supporto()%>"});
                $('#' + kind).append(o);
            <%}%>
                $('#' + kind).val($('#' + kind + ' option:first-child').val()).trigger('change');
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
                document.getElementById('heavy_pob_district_div').style.display = "none";
                $("#heavy_pob_city").attr('readonly', 'readonly');
                $('#heavy_pob_district').val(' ').trigger('change');
                $('#heavy_pob_country').val(' ').trigger('change');
                disable_sel2('heavy_pob_country', 'form1');
                $("#heavy_pob_date").attr('readonly', 'readonly');
                $('#heavy_identcard').val(' ').trigger('change');
                disable_sel2('heavy_identcard', 'form1');
                $('#heavy_pepI').val(' ').trigger('change');
                disable_sel2('heavy_pepI', 'form1', true);
                document.getElementById('heavy_numberidentcard').disabled = true;
                document.getElementById('heavy_issuedateidentcard').disabled = true;
                document.getElementById('heavy_exdateidentcard').disabled = true;
                document.getElementById('heavy_issuedbyidentcard').disabled = true;
                document.getElementById('heavy_issuedplaceidentcard').disabled = true;
                document.getElementById('heavy_email').disabled = true;
                document.getElementById('heavy_phonenu').disabled = true;
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
                document.getElementById('heavy_email').disabled = false;
                document.getElementById('heavy_phonenu').disabled = false;
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
                    //var iss = docout.split("Issuing State:")[1].trim().split("\n")[0].trim();
                    var naz = docout.split("Nationality:")[1].trim().split("\n")[0].trim();
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
                    if (country === ita) {
                        document.getElementById("heavy_pob_district_STR").style.display = "none";
                        document.getElementById("heavy_pob_district_STR").value = "";
                        document.getElementById('heavy_pob_district_div').style.display = "";
                    } else {
                        document.getElementById('heavy_pob_district_div').style.display = "none";
                        document.getElementById("heavy_pob_district_STR").style.display = "";
                        document.getElementById("heavy_pob_district_STR").value = "";
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
                    document.getElementById('heavy_sex').disabled = true;
                    document.getElementById("heavy_name").value = name;
                    document.getElementById("heavy_pob_date").value = datebirth;
                    $('#heavy_pob_country').val(country).change();
                    document.getElementById("heavy_pob_country").disabled = true;
                    $('#heavy_identcard').val(tdo).change();
                    document.getElementById("heavy_identcard").disabled = true;
                    document.getElementById("heavy_numberidentcard").value = dnu;
                    document.getElementById("heavy_exdateidentcard").value = exD;
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
                                        }
                                    });



                                    document.getElementById('heavy_pob_district_div').style.display = "none";
                                    document.getElementById("heavy_pob_district_STR").style.display = "";
                                    document.getElementById("heavy_pob_district_STR").value = "";
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
                                            disable_sel2('heavy_pob_district', 'form1');
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
                            document.getElementById("heavy_pob_date").value = datebirth;


                            $('#heavy_sex').val(sex).change();
                            disable_sel2('heavy_sex', 'form1');
                            disable_sel2('heavy_pob_country', 'form1');
                            checkcodfisc();
                        }

                    }
                }
                dismiss(modal);
            }

            //change district nation
            function changedistrictpob() {
                var ita = '<%=ln.getNazioneCodice("ITALIA")%>';
                if (document.getElementById('heavy_pob_country').value === ita) {
                    document.getElementById('heavy_pob_district_div').style.display = "";
                    document.getElementById("heavy_pob_district_STR").style.display = "none";
                    document.getElementById("heavy_pob_district_STR").value = "";
                } else {
                    document.getElementById("heavy_codfisc").value = '';

                    if (document.getElementById('heavy_pob_country').value !== '') {
                        document.getElementById("heavy_codfisc").disabled = true;
                        document.getElementById("buttoncheckcf").disabled = true;
                        document.getElementById("buttoncheckaeg").disabled = true;
                    }


                    document.getElementById('heavy_pob_district_div').style.display = "none";
                    document.getElementById("heavy_pob_district_STR").style.display = "";
                    document.getElementById("heavy_pob_district_STR").value = "";
                }
            }

            function changecustomerKind() {
                var v1 = document.getElementById("customerKind").value;
                var thranag = "";
                var maxweek = "";
                var res = "";
                var tipocli = "";
                var area = "";

            <%for (int i = 0; i < array_custkind.size(); i++) {%>
                var kind_controlla = '<%=array_custkind.get(i).getTipologia_clienti()%>';
                if (v1 === kind_controlla) {
                    res = '<%=array_custkind.get(i).getFg_nazionalita()%>';
                    tipocli = '<%=array_custkind.get(i).getFg_tipo_cliente()%>';
                    area = '<%=array_custkind.get(i).getFg_area_geografica()%>';
                    thranag = '<%=array_custkind.get(i).getIp_max_singola_transazione()%>';
                    maxweek = '<%=array_custkind.get(i).getIp_max_settimanale()%>';

                }
            <%}%>

                document.getElementById('thranag').value = thranag;
                document.getElementById('maxweek').value = maxweek;
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
                        document.getElementById("heavy_country").disabled = true;
                        document.getElementById("heavy_city_dis").value = "";
                        document.getElementById("heavy_city_dis").style.display = "none";
                        document.getElementById("heavy_city_div").style.display = "";

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
                        if ('<%=nation%>' === 'CZ') {
                            document.getElementById('czh1').style.display = '';
                        }

                    } else if (area === '2') { //EUROPEO

                        $('#heavy_country').empty().trigger('change');
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
                        document.getElementById("custinfo_2").style.display = "none";
                        document.getElementById("custinfo_1").style.display = "";

                        document.getElementById("heavy_codfisc").value = '';
                        document.getElementById("heavy_codfisc").disabled = true;
                        document.getElementById("buttoncheckcf").disabled = true;
                        document.getElementById("buttoncheckaeg").disabled = true;
                        unlockheavy();
                        document.getElementById('czh1').style.display = 'none';
                    } else if (area === '3') {
                        //EXTRAEURO
                        document.getElementById("heavy_country").disabled = false;
                        $('#heavy_country').empty().trigger('change');
            <%for (int j = 0; j < array_country_3.size(); j++) {%>
                        var o = $("<option/>", {value: "<%=array_country_3.get(j)[0]%>", text: "<%=array_country_3.get(j)[1]%>"});
                        $('#heavy_country').append(o);
            <%}%>
                        $('#heavy_country').val($('#heavy_country' + ' option:first-child').val()).trigger('change');

                        document.getElementById("heavy_country_dis").value = "";

                        document.getElementById("heavy_city").disabled = true;
                        document.getElementById("heavy_city_div").style.display = "none";
                        document.getElementById("heavy_city_dis").style.display = "";

                        document.getElementById("heavy_district").disabled = true;
                        document.getElementById("heavy_district_div").style.display = "none";
                        document.getElementById("heavy_district_dis").style.display = "";
                        document.getElementById("custinfo_2").style.display = "none";
                        document.getElementById("custinfo_1").style.display = "";
                        document.getElementById("heavy_codfisc").value = '';
                        document.getElementById("heavy_codfisc").disabled = true;
                        document.getElementById("buttoncheckcf").disabled = true;
                        document.getElementById("buttoncheckaeg").disabled = true;
                        unlockheavy();
                        document.getElementById('czh1').style.display = 'none';
                    }

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


            // check date
            function checkheavy_pob_date() {
                var pobdate = document.getElementById('heavy_pob_date').value.trim();
                if (moment(pobdate, 'DD/MM/YYYY').isValid()) {
                    var limitdate = moment().subtract(15, 'year');
                    var pobdate1 = moment(pobdate, 'DD/MM/YYYY', true);
                    return limitdate.isSameOrAfter(pobdate1);
                }
                return false;
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
                    var nowdate = moment();
                    var exiday1 = moment(exiday, 'DD/MM/YYYY', true);
                    var ch1 = exiday1.isSameOrAfter(nowdate);
                    return ch1;
                }
                return false;
            }
            //total value

            function checkEmail(email) {
                if (email !== "") {
                    var v1 = validate.single(email, {presence: true, email: true});
                    if (undefined !== v1) {
                        return false;
                    }
                }
                return true;
            }

            function fieldobbl() {

                var customerKind = document.getElementById("customerKind").value;
                var res = "";
                var tipocli = "";
                var area = "";
                var thranag = "";
            <%for (int i = 0; i < array_custkind.size(); i++) {%>
                var kind_controlla = '<%=array_custkind.get(i).getTipologia_clienti()%>';
                if (customerKind === kind_controlla) {
                    res = '<%=array_custkind.get(i).getFg_nazionalita()%>';
                    tipocli = '<%=array_custkind.get(i).getFg_tipo_cliente()%>';
                    area = '<%=array_custkind.get(i).getFg_area_geografica()%>';
                    thranag = '<%=array_custkind.get(i).getIp_max_singola_transazione()%>';
                }
            <%}%>
                if (tipocli === '10') { //utente


                    if (res === "1") { //residente ita

                        var heavy_surname = document.getElementById("heavy_surname").value.trim();
                        var heavy_sex = document.getElementById("heavy_sex").value.trim();
                        var heavy_name = document.getElementById("heavy_name").value.trim();
                        var heavy_codfisc = document.getElementById("heavy_codfisc").value.trim();
                        var heavy_pepI = document.getElementById("heavy_pepI").value.trim();
                        var heavy_address = document.getElementById("heavy_address").value.trim();
                        var heavy_country = document.getElementById("heavy_country").value.trim();
                        var heavy_zipcode = document.getElementById("heavy_zipcode").value.trim();
                        var heavy_city = document.getElementById("heavy_city").value.trim();
                        var heavy_district = document.getElementById("heavy_district").value.trim();
                        var heavy_pob_city = document.getElementById("heavy_pob_city").value.trim();
                        var heavy_pob_district_STR = document.getElementById("heavy_pob_district_STR").value.trim();
                        var heavy_pob_district = document.getElementById("heavy_pob_district").value.trim();
                        var heavy_pob_country = document.getElementById("heavy_pob_country").value.trim();
                        var heavy_pob_date = document.getElementById("heavy_pob_date").value.trim();
                        var heavy_identcard = document.getElementById("heavy_identcard").value.trim();
                        var heavy_numberidentcard = document.getElementById("heavy_numberidentcard").value.trim();
                        var heavy_issuedateidentcard = document.getElementById("heavy_issuedateidentcard").value.trim();
                        var heavy_exdateidentcard = document.getElementById("heavy_exdateidentcard").value.trim();
                        var heavy_issuedbyidentcard = document.getElementById("heavy_issuedbyidentcard").value.trim();
                        var heavy_issuedplaceidentcard = document.getElementById("heavy_issuedplaceidentcard").value.trim();

                        if (isempty(heavy_surname) || isempty(heavy_sex) || isempty(heavy_name) || isempty(heavy_codfisc) || isempty(heavy_pepI) || isempty(heavy_city) ||
                                isempty(heavy_address) || isempty(heavy_country) || isempty(heavy_district) || isempty(heavy_pob_city) ||
                                (isempty(heavy_pob_district_STR) && isempty(heavy_pob_district))
                                || isempty(heavy_pob_country) || isempty(heavy_pob_date) || isempty(heavy_identcard) || isempty(heavy_numberidentcard) ||
                                isempty(heavy_issuedateidentcard) || isempty(heavy_exdateidentcard) ||
                                isempty(heavy_issuedbyidentcard) || isempty(heavy_issuedplaceidentcard) || isempty(heavy_zipcode)) {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = "Fields with <span class='font-red'>*</span> must be completed.";
                            return false;
                        }



                    } else if (res === "2") {
                        var heavy_surname = document.getElementById("heavy_surname").value.trim();
                        var heavy_sex = document.getElementById("heavy_sex").value.trim();
                        var heavy_name = document.getElementById("heavy_name").value.trim();
                        var heavy_country = document.getElementById("heavy_country").value.trim();
                        var heavy_city = document.getElementById("heavy_city").value.trim();
                        var heavy_pepI = document.getElementById("heavy_pepI").value.trim();
                        var heavy_address = document.getElementById("heavy_address").value.trim();
                        var heavy_zipcode = document.getElementById("heavy_zipcode").value.trim();
                        var heavy_pob_city = document.getElementById("heavy_city_dis").value.trim();
                        var heavy_pob_district_STR = document.getElementById("heavy_pob_district_STR").value.trim();
                        var heavy_pob_district = document.getElementById("heavy_pob_district").value.trim();
                        var heavy_pob_country = document.getElementById("heavy_pob_country").value.trim();
                        var heavy_pob_date = document.getElementById("heavy_pob_date").value.trim();
                        var heavy_identcard = document.getElementById("heavy_identcard").value.trim();
                        var heavy_numberidentcard = document.getElementById("heavy_numberidentcard").value.trim();
                        var heavy_issuedateidentcard = document.getElementById("heavy_issuedateidentcard").value.trim();
                        var heavy_exdateidentcard = document.getElementById("heavy_exdateidentcard").value.trim();
                        var heavy_issuedbyidentcard = document.getElementById("heavy_issuedbyidentcard").value.trim();
                        var heavy_issuedplaceidentcard = document.getElementById("heavy_issuedplaceidentcard").value.trim();


                        if (isempty(heavy_surname) || isempty(heavy_sex) || isempty(heavy_name) || isempty(heavy_codfisc) || isempty(heavy_pepI) ||
                                isempty(heavy_address) || isempty(heavy_country) || isempty(heavy_district) || isempty(heavy_pob_city) || isempty(heavy_city) ||
                                (isempty(heavy_pob_district_STR) && isempty(heavy_pob_district))
                                || isempty(heavy_pob_country) || isempty(heavy_pob_date) || isempty(heavy_identcard) || isempty(heavy_numberidentcard) ||
                                isempty(heavy_issuedateidentcard) || isempty(heavy_exdateidentcard) ||
                                isempty(heavy_issuedbyidentcard) || isempty(heavy_issuedplaceidentcard) || isempty(heavy_zipcode)) {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = "Fields with <span class='font-red'>*</span> must be completed.";
                            return false;
                        }

                    } else if (res === "3") {
                        var heavy_surname = document.getElementById("heavy_surname").value.trim();
                        var heavy_sex = document.getElementById("heavy_sex").value.trim();
                        var heavy_name = document.getElementById("heavy_name").value.trim();
                        var heavy_codfisc = document.getElementById("heavy_codfisc").value.trim();
                        var heavy_city = document.getElementById("heavy_city").value.trim();
                        var heavy_pepI = document.getElementById("heavy_pepI").value.trim();
                        var heavy_address = document.getElementById("heavy_address").value.trim();
                        var heavy_country = document.getElementById("heavy_country").value.trim();
                        var heavy_zipcode = document.getElementById("heavy_zipcode").value.trim();

                        var heavy_district = document.getElementById("heavy_district").value.trim();
                        var heavy_pob_city = document.getElementById("heavy_city_dis").value.trim();
                        var heavy_pob_district_STR = document.getElementById("heavy_pob_district_STR").value.trim();
                        var heavy_pob_district = document.getElementById("heavy_pob_district").value.trim();
                        var heavy_pob_country = document.getElementById("heavy_pob_country").value.trim();
                        var heavy_pob_date = document.getElementById("heavy_pob_date").value.trim();
                        var heavy_identcard = document.getElementById("heavy_identcard").value.trim();
                        var heavy_numberidentcard = document.getElementById("heavy_numberidentcard").value.trim();
                        var heavy_issuedateidentcard = document.getElementById("heavy_issuedateidentcard").value.trim();
                        var heavy_exdateidentcard = document.getElementById("heavy_exdateidentcard").value.trim();
                        var heavy_issuedbyidentcard = document.getElementById("heavy_issuedbyidentcard").value.trim();
                        var heavy_issuedplaceidentcard = document.getElementById("heavy_issuedplaceidentcard").value.trim();

                        if (isempty(heavy_surname) || isempty(heavy_sex) || isempty(heavy_name) || isempty(heavy_codfisc) || isempty(heavy_pepI) ||
                                isempty(heavy_address) || isempty(heavy_country) || isempty(heavy_district) || isempty(heavy_pob_city) || isempty(heavy_city) ||
                                (isempty(heavy_pob_district_STR) && isempty(heavy_pob_district))
                                || isempty(heavy_pob_country) || isempty(heavy_pob_date) || isempty(heavy_identcard) || isempty(heavy_numberidentcard) ||
                                isempty(heavy_issuedateidentcard) || isempty(heavy_exdateidentcard) ||
                                isempty(heavy_issuedbyidentcard) || isempty(heavy_issuedplaceidentcard) || isempty(heavy_zipcode)) {
                            document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                            document.getElementById('errorlarge').style.display = "block";
                            document.getElementById('errorlargetext').innerHTML = "Fields with <span class='font-red'>*</span> must be completed.";
                            return false;
                        }
                    }

                } else {    //company

                }

                //if(tipocli === ""){

                //}
                return false;
            }

            function checkpaymode() {
                var kind_p1 = document.getElementById('kind_p1').value.trim();
                if (kind_p1 === "...") {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "Field 'Local Figures' must be completed.";
                    return false;
                } else if (kind_p1 === "06") {
                    var cc_number = document.getElementById('cc_number').value.trim();
                    if (cc_number === "") {
                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                        document.getElementById('errorlarge').style.display = "block";
                        document.getElementById('errorlargetext').innerHTML = "Field 'CC Number' must be completed.";
                        return false;
                    }
                }
                return true;
            }

            function subform() {
                //controlli
                if (finalcheck()) {
                } else {
                    return false;
                }
                return true;
            }

            function checkquantitytill_SELL() {
                var idopti = document.getElementById('idopentillv').value.trim();
                var kinfig = "01";
                var codfig = document.getElementById('figs').value.trim();
                var quafig = document.getElementById('quantity').value.trim();
                var found = false;
            <%for (int i = 0; i < array_list_oc_change.size(); i++) {%>
                var ch = "<%=array_list_oc_change.get(i)[0]%>";
                if (idopti === ch) {
                    if (kinfig === "<%=array_list_oc_change.get(i)[1]%>" && codfig === "<%=array_list_oc_change.get(i)[2]%>") {
                        found = true;
                        if (parseFloatRaf(quafig) > parseFloatRaf("<%=array_list_oc_change.get(i)[3]%>")) {
                            return false;
                        }
                    }
                }
            <%}%>
                if (!found) {
                    return false;
                }
                return true;
            }

            function checksizescurrency() {
                var total = document.getElementById('payout0').innerHTML;
                var localcur = '<%=local_cur%>';
                var min = "";
            <%for (int x = 0; x < array_currency_min_sizes.size(); x++) {%>
                var cu_check = '<%=array_currency_min_sizes.get(x)[0]%>';
                if (cu_check === localcur) {
                    min = '<%=array_currency_min_sizes.get(x)[1]%>';
                }
            <%}%>

                var modul = modR(parseFloatRaf(total), parseFloatRaf(min)).toString();
                if (modul !== "0") {
                    return false;
                } else {
                    return true;
                }
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

            function finalcheck() {

                var er = false;
                var ermsg = "";



                var dch = datachecked();

                if (!dch) {
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

                //date birth > 15 year
                var ch1 = checkheavy_pob_date();
                if (!ch1) {
                    er = true;
                    ermsg = ermsg + "Date of birth error<p class='ab'></p>";
                }


                // var fo = fieldobbl();
                //if (!fo) {
                //    return false;
                //}





                var ch_1 = checksizescurrency();
                //array_currency_sizes
                if (!ch_1) {
                    er = true;
                    ermsg = ermsg + "Cuts of currency not available. Check currency tables.<p class='ab'></p>";
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

                if (parseFloatRaf(document.getElementById("payout1").value) >= parseFloatRaf(document.getElementById('maxweek').value.trim())) {
                    er = true;
                    ermsg = ermsg + "You can not complete the transaction. The payout exceeds the Max Weekly Threshold ("
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

            function verificaclient() {
                var oldclient = '0';
                var heavy_codfisc = document.getElementById('heavy_codfisc').value;
                if (heavy_codfisc !== "") {
                    $.ajax({
                        type: "POST",
                        url: "Query?type=verificaclient&q=" + heavy_codfisc,
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
                                    $("#heavy_issuedateidentcard").removeAttr('readonly');
                                    document.getElementById('heavy_numberidentcard').value = arrayJson[8];
                                    document.getElementById('heavy_issuedateidentcard').value = arrayJson[9];
                                    document.getElementById('heavy_exdateidentcard').value = arrayJson[10];
                                    document.getElementById('heavy_issuedbyidentcard').value = arrayJson[11];
                                    document.getElementById('heavy_issuedplaceidentcard').value = arrayJson[12];
                                    document.getElementById('heavy_email').value = arrayJson[13];
                                    document.getElementById('heavy_phonenu').value = arrayJson[14];
                                    for (var x = 1; x < 6; x++) {
                                        document.getElementById('comperc' + x).value = arrayJson[15];
                                        formatValueDecimal_1(document.getElementById('comperc' + x), separatorthousand, separatordecimal);
                                    }
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
                var kind = document.getElementById('kind_p1').value;
                if (kind === '01' || kind === '...') {
                    document.getElementById('pos_0').style.display = 'none';
                    //document.getElementById('pos_1').style.display = 'none';
                } else {
                    document.getElementById('pos_0').style.display = '';
                    //document.getElementById('pos_1').style.display = '';
                }

                if (kind !== '06') {
                    document.getElementById('pos_1').style.display = 'none';
                } else {

                    document.getElementById('pos_1').style.display = '';
                }

            }








            //page load
            function loadpage() {
                $('#largelogin').on('shown.bs.modal', function () {
                    $('#passwordlargelogin').focus();
                });
                online();
                loadtill();
                changetill();
                changelocalfig();
                changecustomerKind();
                heavyczh('<%=nation%>');
                checkesito();
                inputvirgola();
            }




        </script>


    </head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">
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
                                            <input class="form-control" type="password" autocomplete="off" placeholder="Password" maxlength="10" name="passwordlargelogin" id="passwordlargelogin" onkeypress="checkkeysub('buttonsubmitlargelogin', event);"> 
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
                                <input class="form-control" type="password" autocomplete="off" placeholder="Code" id="unlockCode" name="unlockCode"> 
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
                        <textarea class="form-control" rows="10" placeholder="Click HERE before reading" name="documentReading" id="documentReading"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline red-haze " onclick="document.getElementById('documentReading').value = '';" ><i class="fa fa-refresh"></i> Clear Text</button>
                        <button type="button" class="btn btn-outline green-sharp" data-dismiss="modal" onclick="return readDoc('largereaddoc');"><i class="fa fa-arrow-right"></i> Continue</button>
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
            <%@ include file="menu/menu_es1.jsp"%>
            <!-- END MENU -->
            <%                String lan_index = (String) session.getAttribute("language");
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
                            <h3 class="page-title">Transaction <small><b>SELL</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                            <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                        </div>
                    </div>

                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <%
                        String first = "sell";
                        String first_label = StringUtils.capitalize(first);
                    %>

                    <form action="Operazioni_test?type=ch_tr_sell_webtr" method="post" name="form1" id="form1" onsubmit="return subform();">
                        <div class="row">
                            <!-- BUY -->
                            <%
                                String cod = request.getParameter("cod");
                                Booking bo = Engine.get_prenot(cod);

                                ArrayList<Currency> cu = Engine.list_all_currency();

                                String butok = "<button type='button' class='btn btn-circle btn-success btn-sm'>YES</button>";
                                String butko = "<button type='button' class='btn btn-circle btn-danger btn-sm'>NO</button>";

                                String yo_di = "";
                                if (bo.getFg_sconto_giovani().equals("1")) {
                                    yo_di = butok;
                                } else {
                                    yo_di = butko;
                                }
                                String buyba = "";
                                if (bo.getFg_fxbb().equals("1")) {
                                    buyba = butok;
                                } else {
                                    buyba = butko;
                                }
                                String cou = "";
                                if (bo.getFg_coupon().equals("1")) {
                                    cou = butok;
                                } else {
                                    cou = butko;
                                }

                                String days10 = "";
                                if (bo.getFg_ritiro_10().equals("1")) {
                                    days10 = butok;
                                } else {
                                    days10 = butko;
                                }

                            %>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Type</label>
                                    <div class="input-icon">
                                        <i class="fa fa-check font-green-sharp"></i>
                                        <input type="text" class="form-control uppercase" name="locfig" readonly="readonly" value="<%=first_label%>"> 
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
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>&nbsp;</label><p class='ab'></p>
                                    <div class="input-icon">
                                        <a href="transaction_s.jsp?anag=true" class="btn btn-outline dark tooltips" title="Load List Client" data-placement="top"><i class="fa fa-user"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <!-- till -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Till</label><p class='ab'></p>
                                    <select class="form-control select2" name="till" id="till" onchange="return changetill();"></select>
                                </div>
                            </div>
                            <!-- open till -->
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>&nbsp;</label><p class='ab'></p>
                                    <a target="_blank" href="ti_openclose.jsp" class="btn btn-outline dark tooltips" title="Open/Close cash and till" data-placement="top"><i class="fa fa-calculator"></i></a>
                                    <a href="transaction_searchcurr.jsp" class="btn btn-outline dark tooltips fancyBoxRaf" 
                                       title="Search Currency" data-placement="top"><i class="fa fa-search"></i>
                                    </a>
                                </div>
                            </div>

                            <div class="clearfix"></div>
                            <!-- customer kind -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Customer Kind</label><p class='ab'></p>
                                    <select class="form-control select2" name="customerKind" id="customerKind" onchange="return changecustomerKind();">
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
                                            <h3 class="list-title pull-left"><i class="fa <%=Constant.iconcur%>"></i></h3>
                                            <h3 class="list-title"> Pay In <b><span class="pull-right" id="payout0"><%=Utility.formatMysqltoDisplay(Utility.sanitize(bo.getEuro()))%></span></b></h3>
                                            <input type="hidden" id="payout1" name="payout1" value="<%=Utility.sanitize(bo.getEuro())%>"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <!-- total -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Total</label>
                                    <div class="input-icon">
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" name="total0" readonly="readonly" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(bo.getEuro()))%>"> 
                                    </div>
                                </div>
                            </div>
                            <!-- fix -->
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Fix</label>
                                    <div class="input-icon">
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" name="fix0" readonly="readonly" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(bo.getFx_comm()))%>"> 
                                    </div>
                                </div>
                            </div>
                            <!-- com -->
                            <!-- round -->
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Round</label>
                                    <div class="input-icon">
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" name="round0" readonly="readonly" value="0<%=decimal%>00"> 
                                    </div>
                                </div>
                            </div>
                            <!-- commission -->
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Discount</label>
                                    <div class="input-icon">
                                        <i class="fa <%=Constant.iconcur%> font-green-sharp"></i>
                                        <input type="text" class="form-control inputright inputbold" name="commission0" readonly="readonly" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(bo.getSconti()))%>"> 
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <!-- note -->
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label><i class="fa fa-sticky-note-o font-green-sharp"></i> Note</label>
                                    <input type="text" class="form-control" name="note1" readonly="readonly" value="<%=Utility.sanitize(Engine.visualNote(bo.getNote(),100))%>"> 
                                </div>
                            </div>

                            <div class="clearfix"></div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Type</label>
                                    <input type="text" class="form-control" readonly="readonly" value="Internet Booking"> 
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Internet Booking</label>
                                    <input type="text" class="form-control" readonly="readonly" value="<%=Utility.sanitize(Utility.formatAL(bo.getCanale(), array_intbook, 1))%>">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label><i class="fa fa-check-square-o font-green-sharp"></i> Code</label>
                                    <input type="text" class="form-control" name="code_intbook" value="<%=Utility.sanitize(cod)%>" readonly="readonly"> 
                                </div>
                            </div>
                            <p class='ab'></p>
                            <div class="clearfix"></div>
                            <!-- tax free no change -->
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

                                                <table class="table table-bordered" id="sample_1" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th>Kind</th>
                                                            <th>Figures</th>
                                                            <th>Total</th>
                                                            <th>Rate</th>
                                                            <th>Fx Com.</th>
                                                            <th>Fx Discount</th>
                                                            <th>Youth Discount</th>
                                                            <th>Coupon</th>
                                                            <th>BuyBack</th>
                                                            <th>10 days</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td>NOTES</td>
                                                            <td><%=Utility.sanitize(bo.getCurrency()) + " - " + Utility.sanitize(Engine.formatALCurrency(bo.getCurrency(), cu))%></td>
                                                            <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(bo.getTotal()))%></td>
                                                            <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(bo.getRate()))%></td>
                                                            <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(bo.getFx_comm()))%></td>
                                                            <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(bo.getSconti()))%></td>
                                                            <td><%=yo_di%></td>
                                                            <td><%=cou%></td>
                                                            <td><%=buyba%></td>
                                                            <td><%=days10%></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>

                            <input type="hidden" id="figs" name="figs" value="<%=Utility.sanitize(bo.getCurrency())%>"/>
                            <input type="hidden" id="quantity" name="quantity" value="<%=Utility.sanitize(bo.getTotal())%>"/>
                            <input type="hidden" id="rate" name="rate" value="<%=Utility.sanitize(bo.getRate())%>"/>
                            <input type="hidden" id="fxcomm" name="fxcomm" value="<%=Utility.sanitize(bo.getFx_comm())%>"/>
                            <input type="hidden" id="discount" name="discount" value="<%=Utility.sanitize(bo.getSconti())%>"/>
                            <input type="hidden" id="buyb" name="buyb" value="<%=Utility.sanitize(bo.getFg_fxbb())%>"/>

                            <input type="hidden" name="oldclient" id="oldclient" value="0"/>
                            <input type="hidden" name="thranag" id="thranag" value="0"/>
                            <input type="hidden" name="maxweek" id="maxweek" value="0"/>
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
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user font-green-sharp"></i> Surname <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" id="heavy_surname" name="heavy_surname" maxlength="100" 
                                                           onchange="return checkcodfisc();" onkeyup="return fieldNameSurname(this.id);"
                                                           value="<%=Utility.sanitize(bo.getCl_cognome())%>" onkeypress="return keysub(this, event);"/> 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user font-green-sharp"></i> Sex <span class='font-red'>*</span></label>
                                                    <select class="form-control select2" name="heavy_sex" id="heavy_sex" onkeypress="return keysub(this, event);">
                                                        <option value="M">Male</option>
                                                        <option value="F">Female</option>
                                                        <%if (Constant.is_UK) {%>
                                                        <option value="O">Other</option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label>&nbsp;</label>
                                                    <div class="input-icon">
                                                        <a class="btn btn-outline dark tooltips" title="Read Document" data-toggle="modal" href="#largereaddoc">
                                                            <i class="fa fa-file"></i>
                                                        </a>
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
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user font-green-sharp"></i> Name <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" id="heavy_name" name="heavy_name" maxlength="100" 
                                                           onchange="return checkcodfisc();" onkeypress="return keysub(this, event);" onkeyup="return fieldNameSurname(this.id);"
                                                           value="<%=Utility.sanitize(bo.getCl_nome())%>"> 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card font-green-sharp"></i> Tax Code <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" id="heavy_codfisc"
                                                           name="heavy_codfisc" maxlength="20"onchange="return checkcodfisc();" onkeypress="return keysub(this, event);"> 
                                                </div>
                                            </div>
                                            <!--CHZ-->
                                            <div class="col-md-2" id="czh1" style="display: none;">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user font-green-sharp"></i> Personal No. <span class='font-red'>*</span></label>
                                                    <div class="clearfix">
                                                        <select class="form-control select2" name="heavy_pno0" id="heavy_pno0" onchange="return changepersonalne_czh();"onkeypress="return keysub(this, event);">
                                                            <option value="NO">No</option>
                                                            <option value="YES">Yes</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-2" id="czh2" style="display: none;">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card font-green-sharp"></i> Personal No. <span class='font-red'>*</span></label>
                                                    <div class="clearfix">
                                                        <input type="text" class="form-control" id="heavy_pno1" name="heavy_pno1" maxlength="20"onkeypress="return keysub(this, event);"> 
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-flag font-green-sharp"></i> Country <span class='font-red'>*</span></label>
                                                    <input type="hidden" name="heavy_country_dis" id="heavy_country_dis"/>
                                                    <select class="form-control select2" id="heavy_country" name="heavy_country" disabled="disabled"onkeypress="return keysub(this, event);"></select>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-home font-green-sharp"></i> City <span class='font-red'>*</span></label>
                                                    <input class="form-control" type="text" name="heavy_city_dis" id="heavy_city_dis" style="display: none;"onkeypress="return keysub(this, event);"/>
                                                    <div class="clearfix" id="heavy_city_div">
                                                        <select class="form-control select2" name="heavy_city" 
                                                                id="heavy_city" disabled="disabled" onkeypress="return keysub(this, event);"
                                                                onchange="return selectcityDistrict('heavy_city', 'heavy_zipcode');"  
                                                                style="width: 100%;" onkeypress="return keysub(this, event);">

                                                        </select>


                                                    </div>
                                                </div>
                                            </div>
                                            <!--CHZ-->
                                            <div class="col-md-2" id="czh3" style="display: none;">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user font-green-sharp"></i> Sanctions <span class='font-red'>*</span></label>
                                                    <div class="clearfix">
                                                        <select class="form-control select2" name="heavy_sanctions" id="heavy_sanctions"onkeypress="return keysub(this, event);">
                                                            <option value="NO">No</option>
                                                            <option value="YES">Yes / Call Manager</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-2" id="czh4" style="display: none;">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user font-green-sharp"></i> Client is PEP <span class='font-red'>*</span></label>
                                                    <div class="clearfix">
                                                        <select class="form-control select2" name="heavy_pep" id="heavy_pep" onchange="return changepep_czh();"onkeypress="return keysub(this, event);">
                                                            <option value="-">---</option>
                                                            <option value="NO">No</option>
                                                            <option value="YES">Yes / Call Manager</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-2" id="pepITA" style="display: none;">
                                                <div class="form-group">
                                                    <label><i class="fa fa-user font-blue"></i> Client is PEP <span class='font-red'>*</span></label>
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

                                                    <input type="text" class="form-control" id="heavy_address" name="heavy_address"onkeypress="return keysub(this, event);"> 

                                                </div>
                                            </div>
                                            <!--CHZ-->
                                            <div class="col-md-4" id="czh5" style="display: none;">
                                                <div class="form-group">
                                                    <label><i class="fa fa-font font-green-sharp"></i> Money Source <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" id="heavy_moneysource" name="heavy_moneysource" maxlength="100"onkeypress="return keysub(this, event);"> 
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-home font-green-sharp"></i> Zip Code <span class='font-red'>*</span></label>

                                                    <input type="text" class="form-control" id="heavy_zipcode" name="heavy_zipcode" maxlength="15"onkeypress="return keysub(this, event);"> 

                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-home font-green-sharp"></i> District <span class='font-red'>*</span></label>
                                                    <input class="form-control" type="text" name="heavy_district_dis" id="heavy_district_dis" style="display: none;" onkeypress="return keysub(this, event);" disabled="disabled"/>
                                                    <div id="heavy_district_div">
                                                        <select class="form-control select2" id="heavy_district" 
                                                                name="heavy_district" disabled="disabled"  style="width: 100%;" onkeypress="return keysub(this, event);">
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <!--CHZ-->
                                            <div class="col-md-4" id="czh6" style="display: none;">
                                                <div class="form-group">
                                                    <label><i class="fa fa-font font-green-sharp"></i> Transaction Reason <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" id="heavy_transactionre" name="heavy_transactionre" maxlength="100"onkeypress="return keysub(this, event);"> 
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-12">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Place of Birth</h3>
                                                    </div>
                                                    <div class="panel-body"> <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label><i class="fa fa-home font-green-sharp"></i> City <span class='font-red'>*</span></label>

                                                                <input type="text" class="form-control" name="heavy_pob_city" id="heavy_pob_city"onkeypress="return keysub(this, event);"> 

                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label><i class="fa fa-home font-green-sharp"></i> District <span class='font-red'>*</span></label>

                                                                <input type="text" class="form-control" name="heavy_pob_district_STR" id="heavy_pob_district_STR" disabled="disabled"onkeypress="return keysub(this, event);"> 
                                                                <div id="heavy_pob_district_div">
                                                                    <select class="form-control select2" id="heavy_pob_district" name="heavy_pob_district"onkeypress="return keysub(this, event);">
                                                                        <%for (int i = 0; i < array_district.size(); i++) {%>
                                                                        <option value="<%=array_district.get(i)[0]%>"><%=array_district.get(i)[1]%></option>
                                                                        <%}%>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label><i class="fa fa-flag font-green-sharp"></i> Country <span class='font-red'>*</span></label>
                                                                <select class="form-control select2" id="heavy_pob_country" name="heavy_pob_country" onkeypress="return keysub(this, event);" onchange="return changedistrictpob();">
                                                                    <%for (int i = 0; i < array_country.size(); i++) {%>
                                                                    <option value="<%=array_country.get(i)[0]%>"><%=array_country.get(i)[1]%></option>
                                                                    <%}%>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <div class="form-group">
                                                                <label><i class="fa fa-calendar font-green-sharp"></i> Date <span class='font-red'>*</span></label>
                                                                <input type="text" class="form-control date-picker" name="heavy_pob_date" id="heavy_pob_date"onkeypress="return keysub(this, event);"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card font-green-sharp"></i> Identification Card <span class='font-red'>*</span></label>
                                                    <select class="form-control select2" id="heavy_identcard" name="heavy_identcard"onkeypress="return keysub(this, event);">
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

                                                    <input type="text" class="form-control" id="heavy_numberidentcard" name="heavy_numberidentcard" maxlength="100" onkeypress="return keysub(this, event);"> 

                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-calendar font-green-sharp"></i> Issue Date <span class='font-red'>*</span></label>

                                                    <input type="text" class="form-control date-picker" id="heavy_issuedateidentcard"name="heavy_issuedateidentcard" maxlength="100"onkeypress="return keysub(this, event);"> 

                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-calendar font-green-sharp"></i> Expiration Date <span class='font-red'>*</span></label>

                                                    <input type="text" class="form-control date-picker" id="heavy_exdateidentcard" name="heavy_exdateidentcard" onkeypress="return keysub(this, event);"maxlength="100" onchange="return checkheavy_exdateidentcard();"> 

                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card font-green-sharp"></i> Issued By <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" id="heavy_issuedbyidentcard" name="heavy_issuedbyidentcard" maxlength="100"onkeypress="return keysub(this, event);"> 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-credit-card font-green-sharp"></i> Place of Issue <span class='font-red'>*</span></label>
                                                    <input type="text" class="form-control" id="heavy_issuedplaceidentcard" name="heavy_issuedplaceidentcard"onkeypress="return keysub(this, event);"> 
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-envelope font-green-sharp"></i> Email</label>
                                                    <input type="text" class="form-control" id="heavy_email" name="heavy_email"onkeypress="return keysub(this, event);"
                                                           value="<%=Utility.sanitize(bo.getCl_email())%>"
                                                           > 
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label><i class="fa fa-phone font-green-sharp"></i> Phone Number</label>
                                                    <input type="text" class="form-control" id="heavy_phonenu" name="heavy_phonenu"value="<%=Utility.sanitize(bo.getCl_telefono())%>"onkeypress="return keysub(this, event);"> 
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
                                                    <select class="form-control select2" id="kind_p1" name="kind_p1" placeholder="..." onchange="return changelocalfig();">
                                                        <option value="...">...</option>
                                                        <%for (int i = 0; i < array_kind_pay.size(); i++) {%>
                                                        <option value="<%=array_kind_pay.get(i)[0]%>"><%=array_kind_pay.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-3" id="pos_0">
                                                <div class="form-group">
                                                    <label>Pos <span class='font-red'>*</span></label>
                                                    <select class="form-control select2" id="posnum" name="posnum" placeholder="...">
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
                                                               onkeyup="return fieldOnlyNumber(this.id);"/> 
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>



                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <center><button class="btn btn-lg red btn-block" type="submit"><i class="fa fa-save"></i> Confirm Transaction</button></center>
                            </div>                            
                        </div>   

                        <input type="hidden" name="bookid" value="<%=Utility.sanitize(bo.getBookid())%>" />
                    </form>
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