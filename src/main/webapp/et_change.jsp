<%@page import="java.util.List"%>
<%@page import="rc.so.entity.Sizecuts"%>
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
        <%
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;
            boolean iscentral = Engine.isCentral();
            //iscentral=false;
            String startvalue = "0" + decimal + "00";
            String fil[] = Engine.getFil();

            ArrayList<ET_change> array_frombranch = Engine.get_ET_change_frombranch(fil[0], "0", "CH");
            ArrayList<String[]> array_till = Engine.list_till_enabled();
            ArrayList<Till> array_till_open = Engine.list_till_status("O", "");
            ArrayList<Currency> array_all_currency = Engine.list_all_currency();
            ArrayList<String[]> array_kind = Engine.list_all_kind(fil[0]);
            ArrayList<String[]> array_bank = Engine.list_bank_enabled();
            ArrayList<Branch> array_branch = Engine.list_branch_enabled();
            ArrayList<String[]> array_credit_card = Engine.list_bank_pos_enabled();

            String id_safe = "";
            String desc_safe = "";
            String id_oc = "";
            String codopentillfrom = "";
            for (int i = 0; i < array_till_open.size(); i++) {
                if (Utility.formatAL(array_till_open.get(i).getCod(), array_till, 2).equals("0")) {
                    id_safe = array_till_open.get(i).getCod();
                    codopentillfrom = array_till_open.get(i).getCod_opcl();
                    id_oc = array_till_open.get(i).getId_opcl();
                    desc_safe = Utility.formatAL(array_till_open.get(i).getCod(), array_till, 1);
                    break;
                }
            }

        %>



        <script src="assets/soop/js/pace.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="assets/soop/css/pace-theme-center-circle.css" />

        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>
        <script src="assets/soop/js/core-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5.js" type="text/javascript"></script>
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
        <script type="text/javascript">
            <%                Office head = Engine.get_national_office();
                String operator = head.getChangetype();
            %>

            var separatordecimal = '<%=Constant.decimal%>';
            var separatorthousand = '<%=Constant.thousand%>';

            function isfrombranch() {
                var f1 = document.getElementById("typeop");
                if (f1 !== null) {
                    var ty = document.getElementById("typeop").value;
                    var b1 = document.getElementById("bankbranch");
                    if (b1 !== null) {
                        var bankbranch = document.getElementById("bankbranch").value;
                        var tofrom = document.getElementById("tofrom").checked;
                        $('#srcing').empty().trigger('change');
                        if (ty === "BR" && !tofrom) {
                            document.getElementById('srcing_div').style.display = '';
                            document.getElementById('srcing_divM').style.display = '';
            <%for (int j = 0; j < array_frombranch.size(); j++) {
                    ET_change et1 = array_frombranch.get(j);%>
                            var conf = "<%=et1.getFiliale()%>";
                            if (bankbranch === conf) {
                                var o = $("<option/>", {value: "<%=array_frombranch.get(j).getCod()%>", text: "<%=array_frombranch.get(j).getId() + " - " + Utility.formatStringtoStringDate(array_frombranch.get(j).getDt_it(), Constant.patternsqldate, Constant.patternnormdate)%>"});
                                $('#srcing').append(o);
                            }
            <%}%>

                            $('#srcing').val($('#srcing option:first-child').val()).trigger('change');
                        } else {
                            document.getElementById('srcing_div').style.display = 'none';
                            document.getElementById('srcing_divM').style.display = 'none';
                        }
                    }
                }
                checkoff();
            }

            function changetype() {
                var f1 = document.getElementById("typeop");
                if (f1 !== null) {
                    var ty = document.getElementById("typeop").value;
                    $('#bankbranch').empty().trigger('change');
                    if (ty === 'BA') {
            <%for (int j = 0; j < array_bank.size(); j++) {%>
                        var o = $("<option/>", {value: "<%=array_bank.get(j)[0]%>", text: "<%=array_bank.get(j)[1].toUpperCase()%>"});
                        $('#bankbranch').append(o);
            <%}%>
                    } else if (ty === 'BR') {
            <%for (int j = 0; j < array_branch.size(); j++) {
                    if (!array_branch.get(j).getCod().equals(fil[0])) {
            %>
                        var o = $("<option/>", {value: "<%=array_branch.get(j).getCod()%>", text: "<%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch().toUpperCase()%>"});
                                        $('#bankbranch').append(o);
            <%}
                }%>
                                    }
                                    $('#bankbranch').val('').trigger('change');
                                    //$('#bankbranch').val($('#bankbranch option:first-child').val()).trigger('change');
                                }
                                isfrombranch();
                            }

                            function checkdiff_qu(index, size, indextotal, limitindex) {


                                formatValueINT_1_change(document.getElementById('quantnow' + index), separatorthousand, separatordecimal);

                                //var quantnow = document.getElementById('quantnow' + index).value.replace(/\D/g, '');
                                var quantnow = document.getElementById('quantnow' + index).value;

                                if (quantnow.trim() === "") {
                                    document.getElementById('quantnow' + index).value = "0";
                                    quantnow = "0";
                                }

                                //quantnow = accounting.formatNumber(quantnow, 0, separatorthousand, separatordecimal);
                                var quantnowcheck = quantnow.replace(/\D/g, '');

                                document.getElementById('totnow' + index).innerHTML =
                                        accounting.formatNumber(parseFloatRaf((parseFloatRaf(quantnowcheck, separatorthousand, separatordecimal) *
                                                parseFloatRaf(size, separatorthousand, separatordecimal)).toString()).toString(), 2, separatorthousand, separatordecimal);

                                //document.getElementById('quantnow' + index).value = parseInt(quantnowcheck);
                                settotal_checkdiff_qu(indextotal, limitindex);
                            }

                            function val1(id, index) {
                                fieldOnlyNumber(id);
                                setValueRow(index);
                            }

                            function val2(field, index) {
                                formatValueDecimal_1(field, '<%=thousand%>', '<%=decimal%>');
                                setValueRow(index);
                            }

                            function settotal_checkdiff_qu(indextotal, size) {
                                var tot = 0;
                                for (var i = 0; i < size; i++) {
                                    tot = parseIntRaf(getvalueofFieldINT(document.getElementById('quantnow' + indextotal + '_' + i))) + tot;
                                }
                                document.getElementById('totquantcuts' + indextotal).innerHTML = accounting.formatNumber(parseIntRaf(tot), 0, separatorthousand, separatordecimal);

                                var tot_2 = 0;
                                for (var i = 0; i < size; i++) {
                                    tot_2 = parseFloatRaf(replacethousand(getvalueofFieldINT(document.getElementById('totnow' + indextotal + '_' + i)), separatorthousand), separatorthousand, separatordecimal)
                                            + tot_2;
                                }
                                document.getElementById('totnewcuts' + indextotal).innerHTML = accounting.formatNumber(tot_2.toString(), 2, separatorthousand, separatordecimal);
                                setValueRow(indextotal);
                            }

                            function inputformatvaluefirst(index, id, formval) {


                                if (formval === 8) {
                                    if (parseFloatRaf(document.getElementById(id).value, separatorthousand, separatordecimal, 8) > 0) {

                                    } else {

                                        var oldrate = document.getElementById('s_oldrate' + index).value;
                                        //var newrate = document.getElementById('s_rate' + index).value;

                                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                        document.getElementById("errorlarge").style.display = "block";
                                        document.getElementById("errorlargetext").innerHTML = "Error! The branch rate value can not be zero. Change this value.";
                                        document.getElementById('s_rate' + index).value = oldrate;
                                        formatValueDecimal_length(document.getElementById('s_rate' + index), separatorthousand, separatordecimal, 8);
                                        return false;
                                    }
                                }

                                formatValueDecimal_length(document.getElementById(id), separatorthousand, separatordecimal, formval);
                                setValueRow(index);
                            }

                            function setValueRow(index) {



                                if (document.getElementById('s_quantold' + index) !== null) {
                                    var s_quantold = getvalueofField(document.getElementById('s_quantold' + index));
                                    var s_totold = getvalueofField(document.getElementById('s_totold' + index));

                                    var s_quantnow = getvalueofField(document.getElementById('s_quantnow' + index));
                                    var s_totnow = getvalueofField(document.getElementById('s_totnow' + index));
                                    var s_rate = getvalueofField(document.getElementById('s_rate' + index));
                                    //var s_buyv = getvalueofField(document.getElementById('s_buyv' + index));
                                    var s_spread = document.getElementById('s_spread' + index).innerHTML.trim();
                                    if (s_spread === "-") {
                                        s_spread = "0.00";
                                    }

                                    var op1 = document.getElementById('typeop').value;
                                    var tof1 = document.getElementById('tofromv').value;

                                    var tobranch = (tof1 === "T" && op1 === "BR");

                                    if ('<%=Constant.newpread%>' === 'true') {
                                        if (tobranch) {
                                            if (document.getElementById('s_rate' + index) !== null
                                                    && document.getElementById('bce_' + index) !== null) {
                                                document.getElementById('s_rate' + index).value = document.getElementById('bce_' + index).value;
                                                formatValueDecimal_length(document.getElementById('s_rate' + index), separatorthousand, separatordecimal, 8);
                                            }
                                        }
                                    }

                                    //var s_totv = document.getElementById('s_totv' + index).innerHTML.trim();
                                    if (s_quantnow !== "" && s_quantnow !== "0" && s_totnow !== "" &&
                                            s_totnow !== "0" + separatordecimal + "00") {
                                        if ((parseFloatRaf(s_quantold, separatorthousand, separatordecimal) < parseFloatRaf(s_quantnow, separatorthousand, separatordecimal)) || (parseFloatRaf(s_totold, separatorthousand, separatordecimal) < parseFloatRaf(s_totnow, separatorthousand, separatordecimal))) {
                                            var cur = document.getElementById('currency' + index).innerHTML.trim();
                                            var kind = document.getElementById('kind' + index).innerHTML.trim();
                                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                            document.getElementById("errorlarge").style.display = "block";
                                            document.getElementById("errorlargetext").innerHTML = "Error! N.Stock or Quantity are incorrect for figures <span class='font-red'>" + cur + " - " + kind + "</span>";
                                        } else {

                                            if ((parseFloatRaf(s_quantold, separatorthousand, separatordecimal) === parseFloatRaf(s_quantnow, separatorthousand, separatordecimal)) && (parseFloatRaf(s_totold, separatorthousand, separatordecimal) !== parseFloatRaf(s_totnow, separatorthousand, separatordecimal))) {
                                                var cur = document.getElementById('currency' + index).innerHTML.trim();
                                                var kind = document.getElementById('kind' + index).innerHTML.trim();
                                                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                                document.getElementById("errorlarge").style.display = "block";
                                                document.getElementById("errorlargetext").innerHTML = "Error! N.Stock or Quantity are incorrect for figures <span class='font-red'>" + cur + " - " + kind + "</span>";

                                            } else {

                                                var totv = controv(parseFloatRaf(s_totnow, separatorthousand, separatordecimal),
                                                        parseFloatRaf(s_rate, separatorthousand, separatordecimal, 8));

                                                var cur = document.getElementById('currency' + index).innerHTML.trim();
                                                var ki = document.getElementById('kind' + index).innerHTML.trim();
                                                var nocheck = document.getElementById('tofrom').value !== "on";

                                                if ('<%=Constant.newpread%>' === 'true') {
                                                    if (tobranch) {
                                                        nocheck = false;
                                                    } else {
                                                        nocheck = document.getElementById("typeop").value === "BR";
                                                    }
                                                }

                                                var buysell = "B";
                                                if (document.getElementById('tofromv').value === "T") {
                                                    buysell = "S";
                                                }


                                                if (document.getElementById('s_loc' + index).value === 'true') {
                                                    nocheck = true;
                                                }

                                                if (nocheck) {
                                                    document.getElementById('s_spread' + index).innerHTML = "";
                                                    document.getElementById('s_buyv' + index).innerHTML = "";
                                                } else {
                                                    $.ajax({
                                                        type: "POST",
                                                        url: "Operazioni_test?type=get_spread",
                                                        async: false,
                                                        data: {
                                                            type: "get_spread",
                                                            t: buysell,
                                                            q1: s_totnow,
                                                            f1: cur,
                                                            ki: ki,
                                                            ettype: op1,
                                                            ettof: tof1,
                                                            r1: parseFloatRaf(s_rate, separatorthousand, separatordecimal, 8)
                                                        },
                                                        success: function (data) {
                                                            if (data !== "") {

                                                                var arrayJson = JSON.parse(data);
                                                                document.getElementById('s_spread' + index).innerHTML = arrayJson[1];
                                                                document.getElementById('s_buyv' + index).innerHTML = arrayJson[2];

                                                                if (tobranch) {
                                                                    document.getElementById('s_rate' + index).value = arrayJson[3];
                                                                    formatValueDecimal_length(document.getElementById('s_rate' + index), separatorthousand, separatordecimal, 8);
                                                                    s_rate = getvalueofField(document.getElementById('s_rate' + index));
                                                                    totv = controv(parseFloatRaf(s_totnow, separatorthousand, separatordecimal),
                                                                            parseFloatRaf(s_rate, separatorthousand, separatordecimal, 8));
                                                                }


                                                                s_spread = arrayJson[1];
                                                                if (arrayJson[0] === "OK") {
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

                                                document.getElementById('s_totv' + index).innerHTML = accounting.formatNumber(totv, 2, separatorthousand, separatordecimal);

                                            }
                                        }
                                    } else {

                                        document.getElementById('s_totv' + index).innerHTML = "0" + separatordecimal + "00";
                                        document.getElementById('s_buyv' + index).innerHTML = "0" + separatordecimal + "00";
                                    }
                                } else {
                                    var s_quantnow = getvalueofField(document.getElementById('s_quantnow' + index));

                                    var s_totnow = getvalueofField(document.getElementById('s_totnow' + index));

                                    var s_rate = getvalueofField(document.getElementById('s_rate' + index));

                                    var s_spread = document.getElementById('s_spread' + index).innerHTML.trim();
                                    if (s_spread === "-") {
                                        s_spread = "0.00";
                                    }


                                    if (s_quantnow !== "" && s_quantnow !== "0" && s_totnow !== "" && s_totnow !== "0" + separatordecimal + "00") {

                                        var totv = controv(parseFloatRaf(s_totnow, separatorthousand, separatordecimal), parseFloatRaf(s_rate, separatorthousand, separatordecimal, 8));

                                        var cur = document.getElementById('currency' + index).innerHTML.trim();

                                        var nocheck = document.getElementById('tofrom').value === "on";

                                        var buysell = "B";
                                        if (document.getElementById('tofromv').value === "T") {
                                            buysell = "S";
                                        }


                                        if ('<%=Constant.newpread%>' === 'true') {
                                            nocheck = document.getElementById("typeop").value === "BR";
                                        }

                                        var ki = document.getElementById('kindv' + index).value.trim();

                                        if (nocheck) {
                                            document.getElementById('s_spread' + index).innerHTML = "";
                                            document.getElementById('s_buyv' + index).innerHTML = "";
                                        } else {
                                            $.ajax({
                                                type: "POST",
                                                url: "Operazioni_test?type=get_spread",
                                                async: false,
                                                data: {
                                                    type: "get_spread",
                                                    t: buysell,
                                                    q1: s_totnow,
                                                    f1: cur,
                                                    ki: ki,
                                                    r1: parseFloatRaf(s_rate, separatorthousand, separatordecimal, 8)
                                                },
                                                success: function (data) {
                                                    if (data !== "") {
                                                        var arrayJson = JSON.parse(data);
                                                        document.getElementById('s_spread' + index).innerHTML = arrayJson[1];
                                                        document.getElementById('s_buyv' + index).innerHTML = arrayJson[2];
                                                        s_spread = arrayJson[1];
                                                        if (arrayJson[0] === "OK") {
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




                                        //var buyv = totv - parseFloatRaf(s_spread);
                                        document.getElementById('s_totv' + index).innerHTML = accounting.formatNumber(totv, 2, separatorthousand, separatordecimal);
                                        //document.getElementById('s_buyv' + index).innerHTML = accounting.formatNumber(buyv, 7, separatorthousand, separatordecimal);
                                    } else {
                                        document.getElementById('s_totv' + index).innerHTML = "0" + separatordecimal + "00";
                                        document.getElementById('s_buyv' + index).innerHTML = "0" + separatordecimal + "00";
                                    }

                                }

                            }

                            function setValuefig(index) {
                                var qu1 = document.getElementById('totquantcuts' + index).innerHTML;
                                var to1 = document.getElementById('totnewcuts' + index).innerHTML;
                                document.getElementById('s_quantnow' + index).innerHTML = qu1;
                                document.getElementById('s_totnow' + index).innerHTML = to1;
                                setValueRow(index);
                            }

                            function cli(usr) {
                                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                document.getElementById("errorlarge").style.display = "block";
                                document.getElementById("errorlargetext").innerHTML = "Warning! Operation not permitted. The operation of the same type is in progress by the operator " + usr + ".<p class='ab'></p> Please wait for the end of this operation.";
                            }

                            function confoldrate(index) {
                                var oldrate = document.getElementById('s_oldrate' + index).value;
                                var newrate = document.getElementById('s_rate' + index).value;

                                if (parseFloatRaf(newrate, separatorthousand, separatordecimal, 8) > 0) {
                                    formatValueDecimal_length(document.getElementById('s_rate' + index), separatorthousand, separatordecimal, 8);

                                    var newrate = document.getElementById('s_rate' + index).value;
                                    if (parseFloatRaf(oldrate, separatorthousand, separatordecimal, 8) !== parseFloatRaf(newrate, separatorthousand, separatordecimal, 8)) {
                                        document.getElementById('confratebut_' + index).click();
                                    }
                                } else {



                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = "Error! The branch rate value can not be zero. Change this value.";
                                    document.getElementById('s_rate' + index).value = oldrate;
                                    formatValueDecimal_length(document.getElementById('s_rate' + index), separatorthousand, separatordecimal, 8);


                                }
                            }

                            function modrate(index, choice) {
                                setValueRow(index);
                                var oldrate = document.getElementById('s_oldrate' + index).value;
                                if (choice === "N") {
                                    document.getElementById('s_rate' + index).value = oldrate;
                                }
                                document.getElementById('confratebutdis_' + index).click();

                            }

                            function subform(type, but) {

                                but.disabled = true;
                                $("#" + but.id).html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");

                                $('table').each(function () {
                                    if ($.fn.dataTable.fnIsDataTable(this)) {
                                        $(this).DataTable().search('').draw();
                                    }
                                });



                                var inputs, index;
                                //var msg = "";
                                inputs = document.getElementById('f2').getElementsByTagName('span');
                                for (var index = 0; index < inputs.length; index++) {
                                    if (inputs[index].id !== "") {
                                        if (document.getElementsByName(inputs[index].id)[0] !== null && document.getElementsByName(inputs[index].id)[0] !== undefined) {
                                            document.getElementsByName(inputs[index].id)[0].value = inputs[index].innerHTML.trim();
                                        }
                                    }
                                }
                                if (type === "D") {
                                    document.getElementById('conf').value = 'NO';
                                    var motiv1 = document.getElementById('motiv1').value.trim();
                                    if (motiv1 === "") {
                                        document.getElementById('motiv1').focus();
                                        but.disabled = false;
                                        but.innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                        return false;
                                    }
                                } else {
                                    document.getElementById('conf').value = 'YES';
                                }

                                var tofrom = document.getElementById('tofromv').value;
                                if (tofrom !== "F") {
                                    var all = document.getElementsByTagName("input");
                                    for (var i = 0; i < all.length; i++) {
                                        if (all[i].name !== null && all[i].name !== "") {
                                            if (all[i].name.search("kindv") >= 0) {
                                                if (getvalueofField(all[i]) === "02" || getvalueofField(all[i]) === "03") {
                                                    var idrow = all[i].name.replace(/\D/g, "").trim();

                                                    var kind = getvalueofField(document.getElementById('kind' + idrow));
                                                    var curr = getvalueofField(document.getElementById('currency' + idrow));

                                                    var qold = parseFloatRaf(getvalueofField((document.getElementsByName('s_quantold' + idrow)[0])), separatorthousand, separatordecimal);
                                                    var qnew = parseFloatRaf(getvalueofField((document.getElementsByName('s_quantnow' + idrow)[0])), separatorthousand, separatordecimal);
                                                    var told = parseFloatRaf(getvalueofField((document.getElementsByName('s_totold' + idrow)[0])), separatorthousand, separatordecimal);
                                                    var tnew = parseFloatRaf(getvalueofField((document.getElementsByName('s_totnow' + idrow)[0])), separatorthousand, separatordecimal);

                                                    if (qold === qnew) {
                                                        if (told !== tnew) {
                                                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                                            document.getElementById("errorlarge").style.display = "block";
                                                            document.getElementById("errorlargetext").innerHTML = "Error! N.Stock or Quantity are incorrect for figures <span class='font-red'>" + curr + " - " + kind + "</span>";
                                                            but.disabled = false;
                                                            but.innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                                                            return false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                document.getElementById('f2').submit();
                            }

                            function checkaut() {
                                var online = document.getElementById('on1').value === "true";
                                if (!online) {
                                    $("#autman").bootstrapSwitch('toggleState', false);
                                    $('#autman').bootstrapSwitch('readonly', true);
                                }
                            }

                            function checkoff() {
                                var online = document.getElementById('on1').value === "true";

                                var autman = true;
                                if (document.getElementById('autman') !== null) {
                                    autman = document.getElementById('autman').checked;
                                }

                                if (online && autman) {
                                    if (document.getElementById('srcoff') !== null) {
                                        document.getElementById('srcoff').value = "ONLINE";
                                    }
                                    if (document.getElementById('srcing_div_1') !== null) {
                                        document.getElementById('srcing_div_1').style.display = '';
                                    }
                                    if (document.getElementById('srcing_div_2') !== null) {
                                        document.getElementById('srcing_div_2').style.display = 'none';
                                    }
                                    if (document.getElementById('srcing_off') !== null) {
                                        document.getElementById('srcing_off').value = '';
                                    }
                                } else {
                                    if (document.getElementById('srcoff') !== null) {
                                        document.getElementById('srcoff').value = "OFFLINE";
                                    }
                                    if (document.getElementById('srcing_div_1') !== null) {
                                        document.getElementById('srcing_div_1').style.display = 'none';
                                    }
                                    if (document.getElementById('srcing_div_2') !== null) {
                                        document.getElementById('srcing_div_2').style.display = '';
                                    }
                                }

                            }




                            function checkform() {


                                var tillfrom = document.getElementById("tillfrom").value.trim();
                                var idfrom = document.getElementById("idopentillfrom_v").value.trim();

                                var er1 = true;
                                var msg = "ERROR";

                                $.ajax({
                                    async: false,
                                    type: "POST",
                                    url: "Operazioni_test?type=verificaID_OC_SINGLE&tillfrom="
                                            + tillfrom + "&idfrom=" + idfrom,
                                    success: function (data) {
                                        if (data === "OK") {
                                            er1 = false;
                                            msg = "";
                                        } else {
                                            er1 = true;
                                            msg = data;
                                        }
                                    }
                                });

                                if (er1) {
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = "Warning! " + msg;
                                    return false;
                                }

                                var bankbranch = document.getElementById('bankbranch').value;
                                if (bankbranch === "") {
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = "Error! Field 'Bank / Branch' must be completed.";
                                    return false;
                                }

                                var ty = document.getElementById("typeop").value;
                                var off = document.getElementById('srcoff').value === "ONLINE";
                                var tofrom = document.getElementById("tofrom").checked;
                                if (ty === 'BR' && !tofrom) {
                                    if (!off) {
                                        var v = document.getElementById('srcing_off').value.trim();
                                        if (v === "") {
                                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                            document.getElementById("errorlarge").style.display = "block";
                                            document.getElementById("errorlargetext").innerHTML = "Error! Field 'Source Code (From Branch)' must be completed.";
                                            return false;
                                        }
                                    } else {
                                        var v = document.getElementById('srcing').value.trim();
                                        if (v === "") {
                                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                            document.getElementById("errorlarge").style.display = "block";
                                            document.getElementById("errorlargetext").innerHTML = "Error! Field 'Source Code (From Branch)' must be completed.";
                                            return false;
                                        }
                                    }
                                }


                                var autman = document.getElementById("autman").checked;
                                if (ty === 'BR' && !tofrom) {
                                    if (!autman) {
                                        document.getElementById("infolarge").className = document.getElementById("infolarge").className + " in";
                                        document.getElementById("infolarge").style.display = "block";
                                        return false;
                                    }
                                }
                                sub1();
                            }

                            function forRate(index) {


                            }


                            function sub1() {
                                document.getElementById('f1').submit();
                            }

                            function loadpage1() {
                                online();
                                changetype();
                                inputvirgola();
                                checkaut();
                            }

                            function loadpage2() {
                                $('#largelogin').on('shown.bs.modal', function () {
                                    $('#passwordlargelogin').focus();
                                });
                                online();

                                changetype();
                                inputvirgola();
                                checkaut();
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
    <%if (request.getParameter("search") == null) {%>
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage1();">
        <%} else {%>    
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage2();">
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
            <%@ include file="menu/menu_tr3.jsp"%>
            <!-- END MENU -->
            <%                String pswx = session.getAttribute("us_pwd").toString();
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                String usr = Engine.isBlockedOperation();

                String[] locfig = Engine.getLocalFigures();
            %>

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
                                                <input class="form-control" type="password" autocomplete="off"
                                                       placeholder="Password" name="passwordlargelogin" id="passwordlargelogin" maxlength="10" 
                                                       onkeypress="checkkeysub('buttonsubmitlargelogin', event);"> 
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

            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <!--    VUOTO RAF  -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <%@ include file="menu/shortcuts_online.jsp"%>
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction <small><b>External Transfer</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <%if (!iscentral) {%>
                    <div class="modal fade bs-modal-lg" id="infolarge" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                    <h4 class="modal-title font-dark uppercase"><b>Information message</b></h4>
                                </div>
                                <div class="modal-body">Are you sure you want to proceed in 'Manual' mode?</div>
                                <div class="modal-footer">

                                    <button type="button" class="btn green-jungle btn-outline" onclick="return sub1();"><i class="fa fa-check"></i> YES</button>
                                    <button type="button" class="btn red btn-outline" onclick="return dismiss('infolarge');" data-dismiss="modal"><i class="fa fa-check"></i> NO</button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
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
                    <%      if (usr != null) {%>
                    <script type="text/javascript">
                        cli("<%=usr%>");
                    </script>
                    <div class="row">
                        <div class="col-md-6">

                            <div class="alert alert-info">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> The operation could not be completed. Please try again. 

                            </div>
                        </div>
                        <div class="col-md-6">
                            <a href="et_change.jsp" class="btn btn-outline blue"><i class="fa fa-refresh"></i> Refresh</a>
                        </div>
                    </div>
                    <%} else if (id_safe.equals("")) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> The operation could not be completed. Safe is closed. 
                            </div>
                        </div>
                    </div>
                    <%} else {%>
                    <%
                        boolean mostraaltro = true;

                        String esito = request.getParameter("esito");
                        if (esito == null) {
                            esito = "";
                        }
                        String pr = request.getParameter("pr");
                        if (pr == null) {
                            pr = "N";
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
                        } else if (esito.equals("3IN")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. There was an error in entering data Size cuts. Try again.";
                        } else if (esito.equals("2T")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Actual total of size selected is less then the entered value. Check it and try again.";
                        } else if (esito.equals("2Q")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Actual quantity of size selected is less then the entered value. Check it and try again.";
                        } else if (esito.equals("2IN")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. There was an error in entering data value. Try again.";
                        } else if (esito.equals("1T")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Actual total selected is less then the entered value. Check it and try again.";
                        } else if (esito.equals("1Q")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Actual quantity selected is less then the entered value. Check it and try again.";
                        } else if (esito.equals("3Q")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Quantity selected is null or zero. Check it and try again.";
                        } else if (esito.equals("OK")) {
                            mostraaltro = false;
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        }

                        if (!esito.equals("")) {
                    %>
                    <div class="row">
                        <div class="col-md-12 ">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                            <%  String cod = request.getParameter("cod");
                                if (esito.equals("OK")) {
                                    if (cod != null) {
                            %>
                            <form action="Download?type=viewET_receipt" method="post" target="_blank" name="f3" id="f3">
                                <input type="hidden" name="cod" value="<%=Utility.sanitize(cod)%>"/>
                                <input type="hidden" id="typeop" name="typeop" value="CH"/>
                                <button type="submit" class="btn blue"><i class="fa fa-file-pdf-o"></i> Print your receipt</button>
                            </form>
                            <%}
                                }
                            %>
                        </div>
                    </div>
                    <hr>
                    <%}%>

                    <%if (!mostraaltro) {%>

                    <a href="et_change.jsp" class="btn blue"><i class="fa fa-plus-circle"></i> New External Change Transaction</a>

                    <%} else {%>

                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <%if (request.getParameter("search") == null) {%>
                    <form name="f1" id="f1" method="post" action="et_change.jsp" onsubmit="return checkform();"> 
                        <input type="hidden" name="srcoff" id="srcoff"/>
                        <input type="hidden" name="search" value="sra1"/>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Safe/Till</label>
                                    <select class="form-control select2" name="tillfrom" id="tillfrom">
                                        <option value="<%=id_safe%>"><%=desc_safe%></option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Id open/close</label>
                                    <div class="input-icon">
                                        <i class="fa fa-bookmark font-blue-dark"></i>
                                        <input type="text" class="form-control" id="idopentillfrom" disabled value="<%=codopentillfrom%>">
                                        <input type="hidden" class="form-control" id="idopentillfrom_v" name="idopentillfrom" value="<%=id_oc%>">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>To / From</label><p class='ab'></p>
                                    <input type="checkbox" checked class="make-switch" onchange="return isfrombranch();"
                                           id="tofrom" name="tofrom" data-size="normal" 
                                           data-on-color="primary" data-off-color="info"
                                           data-on-text="<span class='tabnow'>To</span>" 
                                           data-off-text="<span class='tabnow'>From</span>" />
                                </div>
                            </div>
                            <div class="col-md-2" id="srcing_divM">
                                <div class="form-group">
                                    <label>Auto / Manual</label><p class='ab'></p>
                                    <input type="checkbox" checked class="make-switch" onchange="return checkoff();"
                                           id="autman" name="autman" data-size="normal" 
                                           data-on-color="primary" data-off-color="info"
                                           data-on-text="<span class='clearfix'>&nbsp;Auto&nbsp;</span>" 
                                           data-off-text="<span class='clearfix'>&nbsp;Manual&nbsp;</span>" />
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Type</label>
                                    <select class="form-control select2" name="typeop" id="typeop" onchange="return changetype();">
                                        <option value="BA">Bank</option>
                                        <option value="BR">Branch</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Bank / Branch <span class="font-red">*</span></label>
                                    <select class="form-control select2" name="bankbranch" id="bankbranch"  onchange="return isfrombranch();" data-allow-clear="true">
                                    </select>
                                </div>
                            </div>
                            <div id="srcing_div">
                                <div class="col-md-4">
                                    <div class="form-group"id="srcing_div_1">
                                        <label>Source Code (From Branch)</label>
                                        <select class="form-control select2" name="srcing" id="srcing"></select>
                                    </div>
                                    <div class="form-group"id="srcing_div_2" style="display: none;">
                                        <label>Source Code (From Branch)</label>
                                        <input type="text" class="form-control" id="srcing_off" name="srcing2" 
                                               onchange="return fieldOnlyNumber(this.id);" maxlength="10"/>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>&nbsp;</label><p class='ab'></p>
                                    <button type="button" onclick="return checkform()" class="btn btn-outline dark"><i class="fa fa-spinner"></i> Load Data</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else if (request.getParameter("search").equals("sra1")) {

                        String tillfrom = request.getParameter("tillfrom");
                        String tofrom = request.getParameter("tofrom");
                        String autman = request.getParameter("autman");

                        String typeop = request.getParameter("typeop");
                        String idopentillfrom = request.getParameter("idopentillfrom");
                        String bankbranch = request.getParameter("bankbranch");
                        String srcing = request.getParameter("srcing");

                        String srcing2 = request.getParameter("srcing2");

                        String srcoff = request.getParameter("srcoff");

                        String tf = "";
                        if (tofrom == null) {
                            tf = "";
                            tofrom = "F";
                        } else if (!tofrom.equals("F")) {
                            tf = "checked";
                            tofrom = "T";
                        }
                        String am = "";
                        if (autman == null) {
                            am = "";
                            autman = "M";
                        } else {
                            am = "checked";
                            autman = "A";
                        }

                        ArrayList<String[]> array_list_oc_change = Engine.list_oc_change_real_et();
                        List<Sizecuts> array_figures_sizecuts = Engine.figures_sizecuts_enabled();

                        boolean es = Engine.insertBlockedOperation(session);
                        if (es) {%>
                    <form name="f2" id="f2" method="post" action="Operazioni?type=et_change">
                        <input type="hidden" name="srcoff" id="srcoff"/>
                        <input type="hidden" name="sizeindex" id="sizeindex" value="<%=array_list_oc_change.size()%>"/>

                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Safe/Till</label>
                                    <input type="text" class="form-control" disabled value="<%=desc_safe%>">
                                    <input type="hidden" name="tillfrom" value="<%=Utility.sanitize(tillfrom)%>"/>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Id open/close</label>
                                    <div class="input-icon">
                                        <i class="fa fa-bookmark font-blue-dark"></i>
                                        <input type="text" class="form-control" id="idopentillfrom" disabled value="<%=codopentillfrom%>">
                                        <input type="hidden" class="form-control" id="idopentillfrom_v" name="idopentillfrom" value="<%=id_oc%>">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>To / From</label><p class='ab'></p>
                                    <input type="checkbox" <%=tf%> readonly class="make-switch" 
                                           id="tofrom" data-size="normal" 
                                           data-on-color="primary" data-off-color="info"
                                           data-on-text="<span class='tabnow'>To</span>" 
                                           data-off-text="<span class='tabnow'>From</span>"
                                           >
                                    <input type="hidden" name="tofrom" value="<%=Utility.sanitize(tofrom)%>" id="tofromv" />
                                </div>
                            </div>
                            <%if (tofrom.equals("F") && typeop.equals("BR")) {%>   
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Auto / Manual</label><p class='ab'></p>
                                    <input type="checkbox" <%=am%> readonly class="make-switch"
                                           id="autman" data-size="normal" 
                                           data-on-color="primary" data-off-color="info"
                                           data-on-text="<span class='clearfix'>&nbsp;Auto&nbsp;</span>" 
                                           data-off-text="<span class='clearfix'>&nbsp;Manual&nbsp;</span>" />
                                    <input type="hidden" name="autman" value="<%=autman%>" id="autmanv" />    
                                </div>
                            </div>
                            <%}%>
                            <div class="clearfix"></div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Type</label>
                                    <input type="text" class="form-control"  readonly name="typeop"
                                           value="<%=Engine.formatBankBranch(typeop)%>">
                                    <input type="hidden" id="typeop" name="typeopv"  value="<%=Utility.sanitize(typeop)%>"/>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Bank / Branch</label>
                                    <input type="text" class="form-control"  readonly  name="bankbranch"
                                           value="<%=Engine.formatBankBranch(bankbranch, typeop, array_bank, array_branch, array_credit_card)%>"></input>
                                    <input type="hidden"  name="bankbranchv"value="<%=Utility.sanitize(bankbranch)%>"/>
                                </div>
                            </div>

                            <%if (tofrom.equals("F") && typeop.equals("BR")) {
                                    if (srcoff.equals("ONLINE")) {
                            %>
                            <div class="col-md-4">
                                <div class="form-group"id="srcing_div_1">
                                    <label>Source Code (From Branch)</label>
                                    <input type="text" class="form-control"  readonly value="<%=Utility.sanitize(Engine.getIdfromCod_ETchange(array_frombranch, srcing))%>"/>
                                    <input type="hidden" name="srcing" value="<%=Utility.sanitize(srcing)%>"/>
                                </div>
                            </div>
                            <%} else {%>
                            <div class="col-md-4">
                                <div class="form-group"id="srcing_div_2">
                                    <label>Source Code (From Branch)</label>
                                    <input type="text" class="form-control"  readonly  name="srcing" value="<%=Utility.sanitize(srcing2)%>"/>
                                </div>
                            </div>
                            <%}%>

                            <%}%>

                        </div>
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>Note</label>
                                    <div class="input-icon">
                                        <i class="fa fa-sticky-note-o font-blue"></i>
                                        <input type="text" class="form-control" name="note" id="note" maxlength="100" onkeyup="return fieldNOSPecial_2(this.id);"/>
                                    </div>
                                </div>
                            </div>  

                        </div>  

                        <%if (tofrom.equals("F") && typeop.equals("BA") || tofrom.equals("F") && typeop.equals("BR") && srcoff.equals("OFFLINE")) {

                                ArrayList<String[]> array_kind_fingures_openclose = Engine.kind_figures_openclose(typeop, srcoff.equals("ONLINE"));

                        %>
                        <div class="row"><!-- FROM BANK --- FROM BRANCH OFF -->
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
                                                <table class="table table-responsive table-bordered" id="sample_2" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow">Figures</th>
                                                            <th class="tabnow">Kind</th>
                                                            <th class="tabnow"style="width: 200px;">N. Stock</th>
                                                            <th class="tabnow"style="width: 200px;">Quantity</th>
                                                            <th class="tabnow"style="width: 200px;">Rate</th>
                                                            <th class="tabnow"style="width: 200px;">Total</th>
                                                            <th class="tabnow"style="width: 200px;">Buy Value</th>
                                                            <th class="tabnow"style="width: 200px;">Spread</th>
                                                            <th class="tabnow" style="width: 75px;">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%for (int i = 0; i < array_kind_fingures_openclose.size(); i++) {
                                                                if (tofrom.equals("F")) {
                                                                    startvalue = "";
                                                                }
                                                                boolean loc = false;
                                                                Currency cu = Engine.getCurrency(array_kind_fingures_openclose.get(i)[0], fil[0]);
                                                                if (cu.getCode().equals(locfig[0])) {
                                                                    loc = true;
                                                                }
                                                                String rate_ext = Utility.formatMysqltoDisplay(Engine.rate_sell_external(cu.getCambio_bce(), cu.getChange_sell()));
                                                        %>





                                                        <tr id="trfig<%=i%>">
                                                    <input type="hidden"id="s_loc<%=i%>" name="s_loc<%=i%>" value="<%=loc%>" />
                                                    <input type="hidden" name="currencyv<%=i%>" value="<%=array_kind_fingures_openclose.get(i)[0]%>"/>
                                                    <input type="hidden" id="kindv<%=i%>" name="kindv<%=i%>" value="<%=array_kind_fingures_openclose.get(i)[2]%>"/>
                                                    <td id="trfig_td1_<%=i%>"><span id="currency<%=i%>"><%=array_kind_fingures_openclose.get(i)[0]%> - <%=array_kind_fingures_openclose.get(i)[1]%></span></td>
                                                    <td id="trfig_td2_<%=i%>"><%=array_kind_fingures_openclose.get(i)[2]%> - <%=array_kind_fingures_openclose.get(i)[3]%></td>
                                                    <td id="trfig_td3_<%=i%>">

                                                        <%if (array_kind_fingures_openclose.get(i)[2].equals("01")) {%>
                                                        <span style="text-align: right;float: right;" id="s_quantnow<%=i%>">0</span>
                                                        <input type="hidden" name="s_quantnow<%=i%>"/>
                                                        <%} else {%>
                                                        <input type="text" class="form-control inputright" id="s_quantnow<%=i%>" 
                                                               name="s_quantnow<%=i%>" value="0" 
                                                               onkeypress="return keysub(this, event);"
                                                               onchange="return inputformatvaluefirst('<%=i%>', this.id, 0);"/>
                                                        <%}%>
                                                    </td>
                                                    <td id="trfig_td4_<%=i%>">
                                                        <%if (array_kind_fingures_openclose.get(i)[2].equals("01")) {%>
                                                        <span style="text-align: right;float: right;" id="s_totnow<%=i%>">0<%=Constant.decimal%>00</span>
                                                        <input type="hidden" name="s_totnow<%=i%>"/>
                                                        <%} else {%>
                                                        <input type="text" class="form-control inputright" 
                                                               id="s_totnow<%=i%>" 
                                                               name="s_totnow<%=i%>" 
                                                               value="0<%=Constant.decimal%>00" 
                                                               onkeypress="return keysub(this, event);" 
                                                               onchange="return inputformatvaluefirst('<%=i%>', this.id, 2);"/>
                                                        <%}%>
                                                    </td>
                                                    <td>
                                                        <%if (loc) {%>
                                                        <span style="text-align: right;float: right;" id="s_rate<%=i%>">1<%=Constant.decimal%>00000000</span>
                                                        <input type="hidden" name="s_rate<%=i%>" />
                                                        <%} else {%>
                                                        <input type="text" class="form-control inputright" 
                                                               name="s_rate<%=i%>" id="s_rate<%=i%>"
                                                               value="<%=rate_ext%>" onchange="return inputformatvaluefirst('<%=i%>', this.id, 8);">
                                                        <input type="hidden" 
                                                               name="s_oldrate<%=i%>" id="s_oldrate<%=i%>" value="<%=rate_ext%>"/>
                                                        <%}%>
                                                    </td>
                                                    <td>
                                                        <span style="text-align: right;float: right;" id="s_totv<%=i%>" name="sss">0<%=Constant.decimal%>00</span>
                                                        <input type="hidden" name="s_totv<%=i%>">
                                                    </td>
                                                    <td>

                                                        <span style="text-align: right;float: right;" id="s_buyv<%=i%>"><%=startvalue%></span>
                                                        <input type="hidden" name="s_buyv<%=i%>">
                                                    </td>
                                                    <td><span style="text-align: right;float: right;" id="s_spread<%=i%>"><%=startvalue%></span>
                                                        <input type="hidden" name="s_spread<%=i%>">
                                                    </td>
                                                    <td><%if (array_kind_fingures_openclose.get(i)[2].equals("01")) {%>
                                                        <a class="btn btn-circle green btn-outline btn-sm" data-toggle="modal" href="#full<%=i%>"><i class="fa fa-pencil"></i> Edit </a>
                                                        <%
                                                            ArrayList<String> array_sizecuts = new ArrayList<>();
                                                            for (int j = 0; j < array_figures_sizecuts.size(); j++) {
                                                                if (array_figures_sizecuts.get(j).getValuta().equals(array_kind_fingures_openclose.get(i)[0])) {
                                                                    array_sizecuts.add(array_figures_sizecuts.get(j).getIp_taglio());
                                                                }
                                                            }
                                                        %>
                                                        <div class="modal fade" id="full<%=i%>" tabindex="-1" role="dialog" aria-hidden="true">
                                                            <div class="modal-dialog modal-full">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                                        <h4 class="modal-title">
                                                                            Edit Figures: <b><%=array_kind_fingures_openclose.get(i)[0]%> - <%=array_kind_fingures_openclose.get(i)[1]%> - <%=array_kind_fingures_openclose.get(i)[2]%> - <%=array_kind_fingures_openclose.get(i)[3]%></b></h4>
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
                                                                                        <table class="table table-responsive table-bordered" id="sample_cuts_<%=i%>" width="100%">
                                                                                            <thead style="display: none;">

                                                                                            </thead>
                                                                                            <tbody>
                                                                                                <tr>
                                                                                                    <th class="tabnow">Size</th>
                                                                                                    <th class="tabnow">Quantity</th>
                                                                                                    <th class="tabnow">Total</th>

                                                                                                </tr>
                                                                                                <%

                                                                                                    int total_q_fig_sizecuts = 0;
                                                                                                    double total_now_fig_sizecuts = 0.00;
                                                                                                    double total_old_fig_sizecuts = 0.00;
                                                                                                    for (int j = 0; j < array_sizecuts.size(); j++) {


                                                                                                %>
                                                                                                <tr id="trcuts<%=i%>_<%=j%>">
                                                                                            <input type="hidden" name="sizecuts<%=i%>_<%=j%>" value="<%=array_sizecuts.get(j)%>"/>
                                                                                            <td><%=Utility.formatMysqltoDisplay(array_sizecuts.get(j))%></td>
                                                                                            <td><input type="text" class="form-control inputright" id="quantnow<%=i%>_<%=j%>" 
                                                                                                       name="quantnow<%=i%>_<%=j%>" value="0" 
                                                                                                       onchange="return checkdiff_qu('<%=i%>_<%=j%>', '<%=array_sizecuts.get(j).trim()%>', '<%=i%>', '<%=array_sizecuts.size()%>');" 
                                                                                                       >
                                                                                            </td>
                                                                                            <td><span style="text-align: right;float: right;"id="totnow<%=i%>_<%=j%>">0<%=decimal%>00</span>
                                                                                                <input type="hidden" name="totnow<%=i%>_<%=j%>"/>
                                                                                            </td>

                                                                                            </tr>
                                                                                            <%}%>
                                                                                            <tr><th>TOTAL</th>
                                                                                                <th><span class="font-blue-dark" style="text-align: right;float: right;" id="totquantcuts<%=i%>"><%=total_q_fig_sizecuts%></span>
                                                                                                    <input type="hidden" name="totquantcuts<%=i%>"/>
                                                                                                </th>
                                                                                                <th><span class="font-blue-dark" style="text-align: right;float: right;" id="totnewcuts<%=i%>"><%=Utility.formatDouble(Utility.roundDouble(total_now_fig_sizecuts, 2))%></span>
                                                                                                    <input type="hidden" name="totnewcuts<%=i%>"/>
                                                                                                </th>

                                                                                            </tr>
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
                                                                                                            {orderable: 1, targets: [0]},
                                                                                                            {orderable: 1, targets: [1]},
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
                                                                            <button type="button" class="btn green btn-outline" onclick="return setValuefig('<%=i%>');"data-dismiss="modal"><i class="fa fa-save"></i> Save changes</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        </div>
                                                        <%}%>
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
                        <div class="row">                         
                            <%if (array_kind_fingures_openclose.size() > 0) {%>

                            <div class="col-md-6">
                                <center><button type="button" onclick="return subform('N', this);" 
                                                class="btn btn-lg green btn-block" id="subformbutt">
                                        <i class="fa fa-save"></i> Confirm Transaction</button></center>
                            </div>  

                            <%}%>
                            <div class="col-md-6">
                                <center><a href="Operazioni?type=unlock_it_et&so=et_change.jsp" class="btn btn-lg red btn-block" 
                                           ><i class="fa fa-remove"></i> Cancel Operation</a>
                                </center>
                            </div> 
                        </div>

                        <%} else if (tofrom.equals("F") && typeop.equals("BR") && srcoff.equals("ONLINE")) {

                            ArrayList<ET_change> val = Engine.get_ET_change_value(srcing);
                            ArrayList<ET_change> tg = Engine.get_ET_change_tg(srcing);
                            String notepartenza = Engine.note_fromBranch(srcing).toUpperCase();

                        %>

                        <div class="row">
                            <hr>
                        </div>
                        <div class="row">
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label>Note (From Branch)</label>
                                    <div class="input-icon">
                                        <i class="fa fa-sticky-note-o font-blue"></i>
                                        <input type="text" class="form-control" maxlength="100" disabled value="<%=Utility.sanitize(notepartenza)%>"/>
                                    </div>
                                </div>
                            </div>  

                        </div>


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
                                                            Currency cu = Engine.getCurrency(et1.getValuta(), fil[0]);
                                                    %>
                                                    <tr>
                                                        <td><%=Utility.sanitize(cu.getCode())%> - <%=Utility.sanitize(cu.getDescrizione())%></td>
                                                        <td><%=Utility.sanitize(et1.getSupporto())%> - <%=Utility.sanitize(Utility.formatAL(et1.getSupporto(), array_kind, 1))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(et1.getIp_stock()), 0))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(et1.getIp_quantity()))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(et1.getIp_rate()))%></td>
                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(et1.getIp_total()))%></td>
                                                        <td><span class="font-green-sharp"><%=Utility.sanitize(Utility.formatMysqltoDisplay(et1.getIp_buyvalue()))%> *</span></td>
                                                        <td><span class="font-green-sharp"><%=Utility.sanitize(Utility.formatMysqltoDisplay(et1.getIp_spread()))%> *</span></td>
                                                        <td>
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

                                                                                                                String qua = Utility.roundDoubleandFormat(Utility.fd(et3.getIp_quantity()), 0);

                                                                                                    %>
                                                                                                    <tr id="tg_trcuts<%=i%>_<%=j%>" valign="middle">
                                                                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(et3.getIp_taglio()))%></td>
                                                                                                        <td><%=Utility.formatMysqltoDisplay(qua)%></td>
                                                                                                        <td><%=Utility.sanitize(Utility.formatMysqltoDisplay(et3.getIp_total()))%></td>
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

                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>
                                                <div class="row"><div class="col-md-12"><span class="font-green-sharp pull-right">* Value Branch Source</span></div></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <%if (val.size() > 0) {%>

                                <div class="row">
                                    <div class="col-md-6">
                                        <center><button type="button" id="subformbutt2" class="btn btn-lg green btn-block" onclick="return subform('N', this);"><i class="fa fa-save"></i> Confirm</button></center>
                                    </div>                            
                                    <div class="col-md-6">
                                        <center><a href="Operazioni?type=unlock_it_et&so=et_change.jsp" class="btn btn-lg red btn-block" 
                                                   ><i class="fa fa-remove"></i> Cancel Operation</a>
                                        </center>
                                    </div>    
                                    <div class="modal fade bs-modal-lg" id="reject_tr" tabindex="-2" role="dialog" aria-hidden="true">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                    <h4 class="modal-title font-blue uppercase"><b>Reject Transaction</b></h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="form-group form-md-line-input form-md-floating-label">
                                                        <input class="form-control" type="text" maxlength="100" name="motiv1" id="motiv1"/>
                                                        <label for="motiv1">Motivation</label>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button id="subformbutt3" type="button" class="btn blue btn-block"onclick="return subform('D', this);">
                                                        <i class="fa fa-arrow-circle-right"></i> Continue</button>
                                                    <button type="button" class="btn red btn-outline" data-dismiss="modal"><i class="fa fa-window-close"></i> Cancel</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <%}%>                    
                        <%} else if (tofrom.equals("T") && typeop.equals("BA") || tofrom.equals("T") && typeop.equals("BR")) {%>   
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
                                                <table class="table table-bordered" id="sample_0" width="100%">
                                                    <thead style="display: none;"></thead>

                                                    <tbody>
                                                        <tr>
                                                            <th class="tabnow">Figures</th>
                                                            <th class="tabnow">Kind</th>
                                                            <th class="tabnow">Actual Stock</th>
                                                            <th class="tabnow">Actual Quantity</th>
                                                            <th class="tabnow"style="width: 200px;">N. Stock</th>
                                                            <th class="tabnow"style="width: 200px;">Quantity</th>
                                                            <th class="tabnow"style="width: 200px;">Rate</th>
                                                            <th class="tabnow"style="width: 200px;">Total</th>
                                                            <th class="tabnow"style="width: 200px;">Buy Value</th>
                                                            <th class="tabnow"style="width: 200px;">Spread</th>
                                                            <th class="tabnow"style="width: 80px;">Action</th>
                                                        </tr>
                                                        <%for (int i = 0; i < array_list_oc_change.size(); i++) {
                                                                if (idopentillfrom.equals(array_list_oc_change.get(i)[0])) {
                                                                    boolean loc = false;
                                                                    Currency cu = Engine.getCurrency(array_list_oc_change.get(i)[2], fil[0]);
                                                                    if (cu.getCode().equals(locfig[0])) {
                                                                        loc = true;
                                                                    }

                                                                    String rate_ext = Utility.formatMysqltoDisplay(Engine.rate_sell_external(cu.getCambio_bce(), cu.getChange_sell()));

                                                                    if (Constant.newpread && tofrom.equals("T") && typeop.equals("BR")) {
                                                                        rate_ext = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(cu.getCambio_bce()), 8));
                                                                    }

                                                        %>



                                                    <div class="modal fade bs-modal-lg" id="confrate_<%=i%>" tabindex="-1" role="dialog" aria-hidden="true">
                                                        <button style="width: 1px; background-color: white;" type="button"data-dismiss="modal"id="confratebutdis_<%=i%>"></button>
                                                        <div class="modal-dialog modal-lg">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                                    <h4 class="modal-title font-blue uppercase"><b>Confirm message</b></h4>
                                                                </div>
                                                                <div class="modal-body" id="errorlargetext">Do you really want to change this value&#63;</div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn blue btn-outline" onclick="return modrate(<%=i%>, 'Y');">YES</button>
                                                                    <button type="button" class="btn red btn-outline" onclick="return modrate(<%=i%>, 'N');">NO</button>
                                                                </div>

                                                            </div>

                                                            <!-- /.modal-content -->
                                                        </div>
                                                        <!-- /.modal-dialog -->
                                                    </div>
                                                    <div class="modal fade bs-modal-lg" id="confratebutm_<%=i%>" tabindex="-1" role="dialog" aria-hidden="true">
                                                        <button type="button" data-toggle="modal" id="confratebut_<%=i%>" data-target="#confrate_<%=i%>"></button>
                                                    </div>
                                                    <tr id="trfig<%=i%>">

                                                    <input type="hidden" id="s_loc<%=i%>" name="s_loc<%=i%>" value="<%=loc%>" />

                                                    <td id="trfig_td1_<%=i%>">
                                                        <span id="currency<%=i%>">
                                                            <%=array_list_oc_change.get(i)[2]%> - <%=cu.getDescrizione()%>
                                                        </span>
                                                    </td>
                                                    <td id="trfig_td2_<%=i%>">
                                                        <span id="kind<%=i%>">
                                                            <%=array_list_oc_change.get(i)[1]%> - <%=Utility.formatAL(array_list_oc_change.get(i)[1], array_kind, 1)%>
                                                        </span>
                                                    </td>
                                                    <td id="trfig_td3_<%=i%>">
                                                        <span style="text-align: right;float: right;" id="s_quantold<%=i%>" name="sss">
                                                            <%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(array_list_oc_change.get(i)[4]), 0))%>
                                                        </span>
                                                        <input type="hidden" name="s_quantold<%=i%>"/>
                                                        <input type="hidden" name="currencyv<%=i%>" value="<%=array_list_oc_change.get(i)[2]%>"/>
                                                        <input type="hidden" name="kindv<%=i%>" value="<%=array_list_oc_change.get(i)[1]%>"/>
                                                        <input type="hidden" name="currency<%=i%>" />
                                                        <input type="hidden" name="kind<%=i%>"/>
                                                    </td>
                                                    <td id="trfig_td3_<%=i%>">
                                                        <span style="text-align: right;float: right;" id="s_totold<%=i%>" name="sss">
                                                            <%=Utility.formatMysqltoDisplay(array_list_oc_change.get(i)[3])%></span>
                                                        <input type="hidden"  name="s_totold<%=i%>"/>
                                                    </td>
                                                    <td id="trfig_td4_<%=i%>">

                                                        <%if (array_list_oc_change.get(i)[1].equals("01")) {%>
                                                        <span style="text-align: right;float: right;"id="s_quantnow<%=i%>" name="sss"></span>
                                                        <input type="hidden"  name="s_quantnow<%=i%>"/>
                                                        <%} else {%>
                                                        <input type="text" name="s_quantnow<%=i%>" id="s_quantnow<%=i%>" class="form-control inputright"
                                                               value="0"  onchange="return val1(this.id, '<%=i%>')"/>
                                                        <%}%>
                                                    </td>
                                                    <td id="trfig_td5_<%=i%>">
                                                        <%if (array_list_oc_change.get(i)[1].equals("01")) {%>
                                                        <span style="text-align: right;float: right;" id="s_totnow<%=i%>" name="sss"></span>
                                                        <input type="hidden"  name="s_totnow<%=i%>"/>
                                                        <%} else {%>
                                                        <input type="text" name="s_totnow<%=i%>" id="s_totnow<%=i%>" 
                                                               class="form-control inputright" value="<%=startvalue%>" 
                                                               onchange="return val2(this, '<%=i%>')"/>
                                                        <%}%>
                                                    </td>
                                                    <td id="trfig_td6_<%=i%>">
                                                        <%if (loc) {%>
                                                        <span style="text-align: right;float: right;" id="s_rate<%=i%>">1<%=Constant.decimal%>00000000</span>
                                                        <input type="hidden" name="s_rate<%=i%>" />
                                                        <%} else {
                                                            if (Constant.newpread && tofrom.equals("T") && typeop.equals("BR")) {%>
                                                        <input type="hidden" id="bce_<%=i%>" value="<%=Utility.formatMysqltoDisplay(cu.getCambio_bce())%>" />

                                                        <input type="text" class="form-control inputright" readonly
                                                               name="s_rate<%=i%>" id="s_rate<%=i%>" value="<%=rate_ext%>" 
                                                               onchange="return false;"/>
                                                        <input type="hidden" name="s_oldrate<%=i%>" id="s_oldrate<%=i%>" value="<%=rate_ext%>"/>
                                                        <%} else {%>
                                                        <input type="text" class="form-control inputright" 
                                                               name="s_rate<%=i%>" id="s_rate<%=i%>" value="<%=rate_ext%>" 
                                                               onchange="return confoldrate(<%=i%>);"/>
                                                        <input type="hidden" 
                                                               name="s_oldrate<%=i%>" id="s_oldrate<%=i%>" value="<%=rate_ext%>"/>
                                                        <%}
                                                            }%>
                                                    </td>
                                                    <td id="trfig_td7_<%=i%>">
                                                        <span style="text-align: right;float: right;" id="s_totv<%=i%>" name="sss">0<%=Constant.decimal%>00</span>
                                                        <input type="hidden"  name="s_totv<%=i%>"/>
                                                    </td>
                                                    <td id="trfig_td8_<%=i%>">
                                                        <span style="text-align: right;float: right;" id="s_buyv<%=i%>">0<%=Constant.decimal%>00</span>
                                                        <input type="hidden"name="s_buyv<%=i%>" />
                                                    </td>
                                                    <td id="trfig_td9_<%=i%>">
                                                        <%if (loc) {%>
                                                        <span style="text-align: right;float: right;" id="s_spread<%=i%>">-</span>
                                                        <%} else {%>
                                                        <span style="text-align: right;float: right;" 
                                                              id="s_spread<%=i%>">0<%=Constant.decimal%>00</span>
                                                        <%}%>
                                                        <input type="hidden"name="s_spread<%=i%>" />
                                                    </td>
                                                    <td>
                                                        <%if (array_list_oc_change.get(i)[1].equals("01")) {%>
                                                        <a class="btn btn-circle green btn-outline btn-sm" data-toggle="modal" 
                                                           href="#full<%=i%>"><i class="fa fa-pencil"></i> Edit </a>
                                                        <%
                                                            ArrayList<String> array_sizecuts = new ArrayList<>();
                                                            for (int j = 0; j < array_figures_sizecuts.size(); j++) {
                                                                if (array_figures_sizecuts.get(j).getValuta().equals(array_list_oc_change.get(i)[2])) {
                                                                    array_sizecuts.add(array_figures_sizecuts.get(j).getIp_taglio());
                                                                }
                                                            }
                                                        %>
                                                        <div class="modal fade" id="full<%=i%>" tabindex="-1" role="dialog" >
                                                            <div class="modal-dialog modal-full">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                                        <h4 class="modal-title">
                                                                            Figures: <b><%=array_list_oc_change.get(i)[2]%> - <%=Engine.formatALCurrency(array_list_oc_change.get(i)[2], array_all_currency)%></b></h4>
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
                                                                                                    <th>Actual Stock</th>
                                                                                                    <th>Actual Total</th>
                                                                                                    <th>Quantity</th>
                                                                                                    <th>Total</th>
                                                                                                </tr>
                                                                                                <%
                                                                                                    ArrayList<String[]> list_oc_change_cuts = Engine.list_oc_change_cuts_real_et(idopentillfrom);
                                                                                                    boolean visual = true;
                                                                                                    double actualst = 0;
                                                                                                    double tot_act = 0.00;
                                                                                                    double total_old_fig_sizecuts = 0.00;
                                                                                                    for (int j = 0; j < array_sizecuts.size(); j++) {
                                                                                                        String act_size = "";
                                                                                                        String act_total = "";
                                                                                                        if (visual) {
                                                                                                            act_size = "0";
                                                                                                            act_total = "0.00";
                                                                                                            for (int k = 0; k < list_oc_change_cuts.size(); k++) {
                                                                                                                if (list_oc_change_cuts.get(k)[1].equals(array_list_oc_change.get(i)[2])) {
                                                                                                                    if (list_oc_change_cuts.get(k)[3].equals(array_sizecuts.get(j))) {
                                                                                                                        act_size = Utility.roundDoubleandFormat(Utility.fd(list_oc_change_cuts.get(k)[4]), 0);
                                                                                                                        actualst = actualst + (Utility.fd(act_size));
                                                                                                                        act_total = list_oc_change_cuts.get(k)[5];
                                                                                                                        tot_act = tot_act + (Utility.fd(act_total));
                                                                                                                        break;
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }

                                                                                                        if (Utility.fd(act_total) > 0) {
                                                                                                %>

                                                                                                <tr id="trcuts<%=i%>_<%=j%>" valign="middle">
                                                                                                    <td><%=Utility.formatMysqltoDisplay(array_sizecuts.get(j))%></td>
                                                                                            <input type="hidden" name="sizecuts<%=i%>_<%=j%>" value="<%=array_sizecuts.get(j)%>"/>
                                                                                            <td>
                                                                                                <span style="text-align: right;float: right;"id="quantold<%=i%>_<%=j%>" name="sss"
                                                                                                      ><%=Utility.formatMysqltoDisplay(act_size)%>

                                                                                                </span><input type="hidden" name="quantold<%=i%>_<%=j%>"/>
                                                                                            </td>
                                                                                            <td ><span style="text-align: right;float: right;"
                                                                                                       id="totold<%=i%>_<%=j%>" name="sss"
                                                                                                       ><%=Utility.sanitize(Utility.formatMysqltoDisplay(act_total))%></span>
                                                                                                <input type="hidden" name="totold<%=i%>_<%=j%>"/>
                                                                                            </td>
                                                                                            <td ><input class="form-control inputright" id="quantnow<%=i%>_<%=j%>" 
                                                                                                        name="quantnow<%=i%>_<%=j%>" value="0" 
                                                                                                        onchange="return checkdiff_qu('<%=i%>_<%=j%>', '<%=array_sizecuts.get(j).trim()%>', '<%=i%>', '<%=array_sizecuts.size()%>');" 

                                                                                                        />
                                                                                            </td>
                                                                                            <td><span style="text-align: right;float: right;"
                                                                                                      id="totnow<%=i%>_<%=j%>"  name="sss" >0<%=decimal%>00</span>
                                                                                                <input type="hidden" name="totnow<%=i%>_<%=j%>"/>
                                                                                            </td>
                                                                                            </tr>
                                                                                            <%}
                                                                                                }%>

                                                                                            <tr>
                                                                                                <th>TOTAL</th>
                                                                                                <th><span class="font-blue-dark" style="text-align: right;float: right;" ><%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(actualst, 0))%></span></th>
                                                                                                <th><span class="font-blue-dark" style="text-align: right;float: right;" ><%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(tot_act, 2))%></span></th>
                                                                                                <th><span class="font-green" style="text-align: right;float: right;"id="totquantcuts<%=i%>"  name="sss">0</span>
                                                                                                </th>
                                                                                                <th><span class="font-green" style="text-align: right;float: right;"id="totnewcuts<%=i%>" name="sss" >0<%=decimal%>00</span>

                                                                                                </th>
                                                                                            </tr>
                                                                                            </tbody>
                                                                                        </table>
                                                                                        <input type="hidden" name="totquantcuts<%=i%>"/>
                                                                                        <input type="hidden" name="totnewcuts<%=i%>"/>
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
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="button" class="btn green btn-outline" onclick="return setValuefig('<%=i%>');"data-dismiss="modal"><i class="fa fa-save"></i> Save changes</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <%}%>
                                                        </div>
                                                    </td>
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
                        </div>
                        <div class="row">
                            <%if (array_list_oc_change.size() > 0) {%>


                            <div class="col-md-6">
                                <center><button type="button" id="subformbutt4" onclick="return subform('N', this);" 
                                                class="btn btn-lg green btn-block">
                                        <i class="fa fa-save"></i> Confirm Transaction</button></center>
                            </div>


                            <%}%>
                            <div class="col-md-6">
                                <center><a href="Operazioni?type=unlock_it_et&so=et_change.jsp" class="btn btn-lg red btn-block" 
                                           ><i class="fa fa-remove"></i> Cancel Operation</a>
                                </center>
                            </div>
                        </div>

                        <%} else {%>
                        ERROR
                        <%}%>

                        <input type="hidden" id="conf" name="conf" value="YES"/>
                    </form>
                    <%}%>
                    <%}%>
                    <%}%>
                    <%}%>



                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>000 - Head Office can not perform transaction operation.</strong>
                            </div>
                        </div>
                    </div>

                    <%
                        String tipo = (String) request.getSession().getAttribute("us_tip");
                        if (tipo.equals("3")) {

                            ArrayList<ET_change> pen = Engine.query_et_pending();

                    %>
                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">External Transfer - Pending</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_3" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Branch Source</th>
                                                                    <th class="tabnow">Id External</th>
                                                                    <th class="tabnow">Branch Destination</th>
                                                                    <th class="tabnow">Date</th>
                                                                    <th class="tabnow">Type</th>
                                                                    <th class="tabnow">Notes</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for (int k = 0; k < pen.size(); k++) {

                                                                        String link_1 = "<div class='col-md-3'><form method='post' target='_blank' action='et_change_view.jsp'>"
                                                                                + "<input type='hidden' name='cod' value='" + pen.get(k).getCod() + "'/>"
                                                                                + "<input type='hidden' name='typeop' value='" + pen.get(k).getType() + "'/>"
                                                                                + "<button type='submit' href='tb_figures.jsp' class='btn btn-sm btn btn-icon-only white popovers' container='body' data-trigger='hover' "
                                                                                + "data-container='body' data-placement='top' data-content='Show'><i class='fa fa-eye'></i></button></form></div>";

                                                                        String link_2 = "<div class='col-md-3'><form method='post' target='_blank' action='fancy_setextok.jsp'>"
                                                                                + "<input type='hidden' name='cod' value='" + pen.get(k).getCod() + "'/>"
                                                                                + "<button class='btn btn-sm btn btn-icon-only white popovers' container='body' data-trigger='hover' "
                                                                                + "data-container='body' data-placement='top' data-content='Set Status OK'><i class='fa fa-check'></i></button></form></div>";

                                                                %>
                                                                <tr>
                                                                    <td><%=pen.get(k).getFiliale()%></td>
                                                                    <td><%=pen.get(k).getId()%></td>
                                                                    <td><%=pen.get(k).getCod_dest()%></td>
                                                                    <td><%=Utility.formatStringtoStringDate(pen.get(k).getDt_it(), Constant.patternsqldate, Constant.patternnormdate)%></td>
                                                                    <td><%=ET_change.typechangeno(pen.get(k).getType())%></td>
                                                                    <td><%=pen.get(k).getNote()%></td>
                                                                    <td class="tabnow">
                                                                        <%=link_1%>
                                                                        <%=link_2%>
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
                        var e = $("#sample_3");
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
                                {orderable: 1, targets: [4]},
                                {orderable: 1, targets: [5]},
                                {orderable: !1, targets: [6]}
                            ],
                            buttons: [
                                {text: "<i class='fa fa fa-refresh'></i>",
                                    className: "btn white btn-outline",
                                    action: function (e, dt, node, config) {
                                        location.reload();
                                    }
                                }],
                            scrollX: true,
                            lengthMenu: [
                                [25, 50, 100, -1],
                                [25, 50, 100, "All"]
                            ],
                            pageLength: 25,
                            order: [],
                            dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                        });
                    };
                    jQuery().dataTable && dt();
                });
            </script>
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
