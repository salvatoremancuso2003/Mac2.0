<%@page import="rc.so.entity.Sizecuts"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Till"%>
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
        <title>Mac2.0 - Open</title>
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
        <script src="assets/soop/js/pace.min.js" type="text/javascript"></script>
        <link rel="stylesheet" href="assets/soop/css/pace-theme-center-circle.css" />



        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/core-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5.js" type="text/javascript"></script>
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>
        <%
            String opencloseid = request.getParameter("opencloseid");
            if (opencloseid == null || opencloseid.equals("null")) {
                opencloseid = "-";
            }
            String tillselected = request.getParameter("tillselected");
            String filiale = Engine.getFil()[0];
            ArrayList<Till> list_ALL_till_enabled = Engine.list_ALL_till_enabled();
            ArrayList<Till> listTill = Engine.listTill(filiale);
            ArrayList<Till> array_till = Engine.list_till_status("O", Utility.checkAttribute(session,"us_cod"));
            Till ti = Engine.getContainsTill(tillselected, listTill);
            boolean opened = false;
            if (ti != null) {
                if (!ti.isSafe()) {
                    for (int i = 0; i < array_till.size(); i++) {
                        if (!array_till.get(i).isSafe()) {
                            opened = true;
                            break;
                        }
                    }
                }
            } else {
                for (int j = 0; j < list_ALL_till_enabled.size(); j++) {
                    Till t0 = list_ALL_till_enabled.get(j);
                    if (t0.getCod().equals(tillselected)) {
                        ti = t0;
                        break;
                    }
                }

            }

            ArrayList<String[]> array_kind_fingures_openclose = Engine.kind_figures_openclose();
            List<Sizecuts> array_figures_sizecuts = Engine.figures_sizecuts_enabled();
            ArrayList<String[]> array_list_nochange = Engine.list_nochange();

            String decimal = Constant.decimal;
            String thousand = Constant.thousand;
            String startvalue = "0" + decimal + "00";

        %>


        <script type="text/javascript">
            var separatordecimal = '<%=decimal%>';
            var separatorthousand = '<%=thousand%>';
            var replth = ",";

            if (separatorthousand === ".") {
                replth = "\.";
            }
            //hide quantity figures
            function hidequantitynull() {
                var size = '<%=array_kind_fingures_openclose.size()%>';
                for (var i = 0; i < size; i++) {
                    var q1 = document.getElementById("s_totalold" + i);
                    var q2 = document.getElementById("s_totnow" + i);
                    var show = true;
                    if (q1 !== null && q2 !== null) {

                        if ((q1.innerHTML.trim() === "-0,00" || q1.innerHTML.trim() === "0.00" || q1.innerHTML.trim() === "0,00") && (q2.innerHTML.trim() === "-0,00" || q2.innerHTML.trim() === "0.00" || q2.innerHTML.trim() === "0,00")) {
                            show = false;
                        } else {
                            show = true;
                        }
                    } else {
                        q1 = document.getElementById("s_totalold" + i);
                        if (q1 !== null) {
                            q2 = document.getElementById("totalnow" + i);
                            if ((q1.innerHTML.trim() === "-0,00" || q1.innerHTML.trim() === "0.00" || q1.innerHTML.trim() === "0,00") && (q2.value.trim() === "-0,00" || q2.value.trim() === "0.00" || q2.value.trim() === "0,00")) {
                                show = false;
                            } else {
                                show = true;
                            }
                        } else {
                            show = false;
                        }
                    }
                    if (show) {
                        document.getElementById("trfig" + i).style.display = "";
                    } else {
                        if (document.getElementById("trfig" + i) !== null) {
                            document.getElementById("trfig" + i).style.display = "none";
                        }
                    }
                }
            }

            //hide quantity nochange
            function hidequantitynull_nc() {
                var size = '<%=array_list_nochange.size()%>';
                for (var i = 0; i < size; i++) {
                    var q1 = document.getElementById("nc_quantold" + i);
                    var g = document.getElementById("nc_diffgap" + i);
                    var show1 = true;
                    var show2 = true;
                    if (q1 !== null) {
                        if (q1.value === "0") {
                            show1 = false;
                        } else {
                            show1 = true;
                        }
                    }

                    if (g !== null) {
                        if (g.innerHTML === "0") {
                            show2 = false;
                        } else {
                            show2 = true;
                        }
                    }
                    if (show1 || show2) {
                        document.getElementById("trnoc" + i).style.display = "";
                    } else {
                        document.getElementById("trnoc" + i).style.display = "none";
                    }
                }
            }
            //show quantity figures
            function showAllQuantity() {
                var size = '<%=array_kind_fingures_openclose.size()%>';
                for (var i = 0; i < size; i++) {
                    if (document.getElementById("trfig" + i) !== null) {
                        document.getElementById("trfig" + i).style.display = "";
                    }
                }
            }

            //show quantity nochange
            function showAllQuantity_nc() {
                var size = '<%=array_list_nochange.size()%>';
                for (var i = 0; i < size; i++) {
                    document.getElementById("trnoc" + i).style.display = "";
                }
            }

            //initial load
            function onloadbody() {
                $('#largelogin').on('shown.bs.modal', function () {
                    $('#passwordlargelogin').focus();
                });
                $('#finalmodal').on('shown.bs.modal', function () {
                    $('#subformbutt').click(function () {
                        $(this).html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");
                        $(this).prop("disabled", true);
                    });
                });
                $('#meserr2').click(function () {
                    $('#subformbutt').html("<i class='fa fa-arrow-right'></i> Continue");
                    $('#subformbutt').prop("disabled", false);
                });
                online();
                hidequantitynull();
                hidequantitynull_nc();
                inputvirgola();
            }

            //change quantity nochange
            function checkdiff_nc(index) {
                formatValueINT_1_change(document.getElementById('nc_quantnow' + index), separatorthousand, separatordecimal);
                var now = document.getElementById('nc_quantnow' + index).value;
                if (now === "") {
                    now = "0";
                }



                //now = accounting.formatNumber(now, 0, separatorthousand, separatordecimal);

                //var nowcheck = now.replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '');
                var nowcheck = now.replace(/\D/g, '');
                var old = document.getElementById('nc_quantold' + index).value;
                var tr = document.getElementById('trnoc' + index);

                if (parseIntRaf(nowcheck) !== parseIntRaf(old)) {
                    //document.getElementById('nc_diffgap' + index).innerHTML = 
                    //       accounting.formatNumber(parseIntRaf(replacethousand(nowcheck, separatorthousand)) - 
                    //      parseIntRaf(replacethousand(old, separatorthousand)), 0, separatorthousand, separatordecimal);
                    var diffg = (parseFloatRaf(document.getElementById('nc_quantnow' + index).value, separatorthousand, separatordecimal) -
                            parseFloatRaf(old, separatorthousand, separatordecimal));

                    document.getElementById('nc_diffgap' + index).innerHTML = accounting.formatNumber(diffg, 0, separatorthousand, separatordecimal);

                    tr.className = "font-red";
                } else {
                    document.getElementById('nc_diffgap' + index).innerHTML = "0";
                    tr.className = "";
                }
            }

            function setValueOtherKind(index) {

                var oldtot = document.getElementById("totalold" + index).value;

                formatValueDecimal_length(document.getElementById('quantnow' + index), separatorthousand, separatordecimal, 0);
                formatValueDecimal_1(document.getElementById('totalnow' + index), separatorthousand, separatordecimal);

                var quantnow = document.getElementById('quantnow' + index).value;
                if (quantnow.trim() === "") {
                    document.getElementById('quantnow' + index).value = "0";
                    quantnow = "0";
                }
                var totalnow = document.getElementById('totalnow' + index).value;

                if (totalnow.trim() === "") {
                    document.getElementById('totalnow' + index).value = "0";
                    totalnow = "0";
                }


                if (parseFloatRaf(totalnow, separatorthousand, separatordecimal) !== 0
                        && parseFloatRaf(quantnow, separatorthousand, separatordecimal) === 0) {
                    document.getElementById('quantnow' + index).value = "0";
                    document.getElementById('totalnow' + index).value = "0" + separatordecimal + "00";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = "Field 'User Quantity' must be greater than Zero if 'User Total'\n\
         is greater than Zero. Check this value.";
                    setValueOtherKind(index);
                    return false;
                }


                //quantnow = replacethousand(quantnow, separatorthousand);
                totalnow = replacethousand(totalnow, separatorthousand);

                var diffgap = accounting.formatNumber(new BigNumber((parseFloatRaf(totalnow, separatorthousand, separatordecimal) -
                        parseFloatRaf(oldtot, separatorthousand, separatordecimal)).toString()).round(2, 0), 2, separatorthousand, separatordecimal);


                document.getElementById('s_diffgap' + index).innerHTML = diffgap;
                if (parseFloatRaf(totalnow, separatorthousand, separatordecimal) !== parseFloatRaf(oldtot, separatorthousand, separatordecimal)) {
                    document.getElementById('trfig_td1_' + index).className = "font-red";
                    document.getElementById('trfig_td2_' + index).className = "font-red";
                    document.getElementById('trfig_td3_' + index).className = "font-red";
                    document.getElementById('trfig_td4_' + index).className = "font-red";
                    document.getElementById('trfig_td5_' + index).className = "font-red";
                    document.getElementById('trfig_td6_' + index).className = "font-red";
                    document.getElementById('trfig_td7_' + index).className = "font-red";
                } else {
                    document.getElementById('trfig_td1_' + index).className = "";
                    document.getElementById('trfig_td2_' + index).className = "";
                    document.getElementById('trfig_td3_' + index).className = "";
                    document.getElementById('trfig_td4_' + index).className = "";
                    document.getElementById('trfig_td5_' + index).className = "";
                    document.getElementById('trfig_td6_' + index).className = "";
                    document.getElementById('trfig_td7_' + index).className = "";
                }


            }

            // change quantity figures
            function checkdiff_qu(index, size, indextotal, limitindex) {
                formatValueINT_1_change(document.getElementById('quantnow' + index), separatorthousand, separatordecimal);

                var quantnow = document.getElementById('quantnow' + index).value;

                if (quantnow.trim() === "") {
                    document.getElementById('quantnow' + index).value = "0";
                    quantnow = "0";
                }

                //quantnow= replacethousand(quantnow,separatorthousand);

                var quantnowcheck = quantnow.replace(/\D/g, '');
                //var quantnowcheck = quantnow.replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '');
                var quantold = document.getElementById('quantold' + index).innerHTML.trim();

                document.getElementById('totnow' + index).innerHTML =
                        accounting.formatNumber((parseFloatRaf(quantnowcheck, separatorthousand, separatordecimal) * parseFloatRaf(size, separatorthousand, separatordecimal)).toString(), 2, separatorthousand, separatordecimal);






                var tr = document.getElementById('trcuts' + index);
                if (parseFloatRaf(quantnow, separatorthousand, separatordecimal) !== parseFloatRaf(quantold, separatorthousand, separatordecimal)) {
                    document.getElementById('diffgapsize' + index).innerHTML = accounting.formatNumber(parseFloatRaf(quantnowcheck, separatorthousand, separatordecimal) - parseFloatRaf(quantold, separatorthousand, separatordecimal), 0, separatorthousand, separatordecimal);
                    tr.className = "font-red";
                } else {
                    document.getElementById('diffgapsize' + index).innerHTML = accounting.formatNumber(parseFloatRaf("0.00", separatorthousand, separatordecimal), 0, separatorthousand, separatordecimal);
                    tr.className = "";
                }

                settotal_checkdiff_qu(indextotal, limitindex);
            }

            // set total quantity figures
            function settotal_checkdiff_qu(indextotal, size) {
                var tot = 0;
                for (var i = 0; i < size; i++) {
                    tot = parseIntRaf(document.getElementById('quantnow' + indextotal + '_' + i).value) + (tot);
                }
                document.getElementById('totquantcuts' + indextotal).innerHTML = accounting.formatNumber(parseIntRaf(tot), 0, separatorthousand, separatordecimal);

                var tot_2 = 0;
                for (var i = 0; i < size; i++) {
                    var st1 = document.getElementById('totnow' + indextotal + '_' + i).innerHTML.trim();
                    //var st2 = replacethousand(st1, separatorthousand);
                    var st3 = parseFloatRaf(st1, separatorthousand, separatordecimal);
                    tot_2 = st3 + tot_2;
                }
                document.getElementById('totnewcuts' + indextotal).innerHTML = accounting.formatNumber(tot_2.toString(), 2, separatorthousand, separatordecimal);
            }

            //update figures difference
            function setValuefig(index) {
                var qu1 = document.getElementById('totquantcuts' + index).innerHTML.trim();
                var to1 = document.getElementById('totnewcuts' + index).innerHTML.trim();


                var oldtotal = document.getElementById('s_totalold' + index).innerHTML.trim();

                document.getElementById('s_quantnow' + index).innerHTML = qu1;
                document.getElementById('s_totnow' + index).innerHTML = to1;

                qu1 = replacethousand(qu1, separatorthousand);
                to1 = replacethousand(to1, separatorthousand);
                oldtotal = replacethousand(oldtotal, separatorthousand);


                var diffgap = accounting.formatNumber(new BigNumber((parseFloatRaf(to1, separatorthousand, separatordecimal) - parseFloatRaf(oldtotal, separatorthousand, separatordecimal).toString()).toFixed(2)).round(2, 0), 2, separatorthousand, separatordecimal);
                document.getElementById('s_diffgap' + index).innerHTML = diffgap;
                if (parseFloatRaf(to1, separatorthousand, separatordecimal) !== parseFloatRaf(oldtotal, separatorthousand, separatordecimal)) {
                    document.getElementById('trfig_td1_' + index).className = "font-red";
                    document.getElementById('trfig_td2_' + index).className = "font-red";
                    document.getElementById('trfig_td3_' + index).className = "font-red";
                    document.getElementById('trfig_td4_' + index).className = "font-red";
                    document.getElementById('trfig_td5_' + index).className = "font-red";
                    document.getElementById('trfig_td6_' + index).className = "font-red";
                    document.getElementById('trfig_td7_' + index).className = "font-red";
                } else {
                    document.getElementById('trfig_td1_' + index).className = "";
                    document.getElementById('trfig_td2_' + index).className = "";
                    document.getElementById('trfig_td3_' + index).className = "";
                    document.getElementById('trfig_td4_' + index).className = "";
                    document.getElementById('trfig_td5_' + index).className = "";
                    document.getElementById('trfig_td6_' + index).className = "";
                    document.getElementById('trfig_td7_' + index).className = "";
                }
            }

            function checkdifference() {
                resetFilter();
                var size1 = '<%=array_kind_fingures_openclose.size()%>';
                var size2 = '<%=array_list_nochange.size()%>';
                var er = false;
                var msgerrr = "<table class='table table-responsive width='100%'><thead><tr><th class='tabnow'>Type</th><th class='tabnow'>Description</th><th class='tabnow'>Difference</th><th class='tabnow'>Notes <span class='font-red'>*</span></th></tr></thead><tbody>";
                for (var i = 0; i < size1; i++) {
                    var dtr = document.getElementById('s_diffgap' + i);
                    if (dtr !== null) {
                        var dg1 = dtr.innerHTML.trim();
                        if (dg1 === '-0' || dg1 === '0' || dg1 === '0.00' || dg1 === '-0.00' || dg1 === '0,00' || dg1 === '-0,00') {
                        } else {
                            er = true;
                            var type = "Figures";
                            var cur = document.getElementById('s_curr' + i).value.trim();
                            var kin = document.getElementById('s_kind' + i).value.trim();
                            var fig = document.getElementById('trfig_td1_' + i).innerHTML.trim() + " - " + document.getElementById('trfig_td2_' + i).innerHTML.trim();
                            var notes = "<div class='input-icon'><i class='fa fa-sticky-note-o font-blue'></i>" +
                                    "<input type='text' class='form-control' maxlength='100' name='trfigerr_" + cur + "_" + kin + "'/>"
                                    + "</div>";
                            msgerrr = msgerrr + "<tr><td>" + type + "</td><td>" + fig + "</td><td>" + dg1 + "</td><td>" + notes + "</td></tr>";
                        }
                    }
                }
                for (var i = 0; i < size2; i++) {
                    var dtr2 = document.getElementById('nc_diffgap' + i);
                    if (dtr2 !== null) {
                        var dg2 = dtr2.innerHTML.trim();
                        if (dg2 === '-0' || dg2 === '0' || dg2 === '0.00' || dg2 === '-0.00' || dg2 === '0,00' || dg2 === '-0,00') {

                        } else {
                            er = true;
                            var type = "No Change";
                            var nc_cod = document.getElementById('nc_cod' + i).value.trim();
                            var fig = document.getElementById('trnoc_td1_' + i).innerHTML.trim();
                            var notes = "<div class='input-icon'>" +
                                    "<i class='fa fa-sticky-note-o font-blue'></i>" +
                                    "<input type='text' class='form-control' maxlength='100' name='trnocerr_" + nc_cod + "'></div>";
                            msgerrr = msgerrr + "<tr><td>" + type + "</td><td>" + fig + "</td><td>" + dg2 + "</td><td>" + notes + "</td></tr>";
                        }
                    }
                }
                msgerrr = msgerrr + "</tbody></table>";

                if (er) {
                    document.getElementById('errorpresent').value = "Y";
                    document.getElementById('finalmodal_title').innerHTML = "Error Handling";
                    document.getElementById('finalmodal_body').innerHTML = msgerrr;
                } else {
                    document.getElementById('errorpresent').value = "N";
                    document.getElementById('finalmodal_title').innerHTML = "Confirm Open";
                    document.getElementById('finalmodal_body').innerHTML = "Are you sure you want to continue?";

                }
            }

            function subform(but) {
                //$("#"+but.id).html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");
                //but.disabled = true;
                var inputs, index;
                //var msg = "";
                inputs = document.getElementById('f2').getElementsByTagName('span');
                for (index = 0; index < inputs.length; ++index) {
                    if (inputs[index].id !== "") {
                        if (document.getElementsByName(inputs[index].id)[0] !== null) {
                            // msg = msg + " - ID: " + inputs[index].id + " - VALUE: " + inputs[index].innerHTML.trim() + "\n";
                            document.getElementsByName(inputs[index].id)[0].value = inputs[index].innerHTML.trim();
                        }
                    }
                }

                inputs = document.getElementById('f2').getElementsByTagName('input');
                for (index = 0; index < inputs.length; ++index) {
                    var name = inputs[index].name.trim();
                    if (name.indexOf("trnocerr_") === 0 || name.indexOf("trfigerr_") === 0) {
                        if (inputs[index].value.trim() === "") {
                            but.disabled = false;
                            but.innerHTML = "<i class='fa fa-arrow-right'></i> Continue";
                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                            document.getElementById("errorlarge").style.display = "block";
                            document.getElementById("errorlargetext").innerHTML = "You must be complete 'Notes' from Errors.";
                            return false;
                        }
                    }
                }

                document.getElementById('f2').submit();
            }

            function cli(usr) {
                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                document.getElementById("errorlarge").style.display = "block";
                document.getElementById("errorlargetext").innerHTML = "Warning! Operation not permitted. The operation of the same type is in progress by the operator " + usr + ".<p class='ab'></p> Please wait for the end of this operation.";
            }

            function op() {
                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                document.getElementById("errorlarge").style.display = "block";
                document.getElementById("errorlargetext").innerHTML = "Warning! Operation not permitted. You have one till already opened! Please close that till and try again!.";
            }

            function resetrow(index) {
                var x = document.getElementsByTagName('input');
                for (var i = 0; i < x.length; i++) {
                    if (x[i].name.indexOf("quantnow" + index) === 0) {
                        x[i].value = "0";
                        var event = new Event('change');
                        x[i].dispatchEvent(event);
                    }
                }
            }

        </script>



        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>

        <script src="assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
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



        <!-- END PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>

        <script type="text/javascript">
            function resetFilter() {
                $('table').each(function () {
                    if ($.fn.dataTable.fnIsDataTable(this)) {
                        $(this).DataTable().search('').draw();
                    }
                });
                hidequantitynull();
                hidequantitynull_nc();
            }
        </script>
        <script type="text/javascript">
            function setall_F() {
                var verified = document.getElementById('setall');
                if (verified.checked) {
                    document.getElementById('setalllabel').style.display = '';
                    var span = document.getElementById('f2').getElementsByTagName('span');
                    for (var index = 0; index < span.length; ++index) {
                        if (span[index].id !== "") {
                            if (span[index].innerHTML.trim() === '0' || span[index].innerHTML.trim() === '0' + separatordecimal + '00') {

                            } else {
                                if (span[index].id.startsWith("totold")) {
                                    var indDAFARE = span[index].id.replace("totold", "").split("_")[0];
                                    setValuefig(indDAFARE);
                                }
                            }
                        }
                    }
                    var input = document.getElementById('f2').getElementsByTagName('input');
                    for (var index = 0; index < input.length; ++index) {
                        if (input[index].id !== "") {
                            if (input[index].value.trim() === '0' || input[index].value.trim() === '0' + separatordecimal + '00') {

                            } else {
                                if (input[index].id.startsWith("totalnow")) {
                                    var indDAFARE1 = input[index].id.replace("totalnow", "");
                                    setValueOtherKind(indDAFARE1);
                                } else if (input[index].id.startsWith("nc_quantold")) {
                                    var indDAFARE = input[index].id.replace("nc_quantold", "");
                                    document.getElementById('nc_quantnow' + indDAFARE).value = input[index].value.trim();
                                    checkdiff_nc(indDAFARE);
                                }
                            }
                        }
                    }
                    document.getElementById('setall1').value="1";
                    $("#setall").prop("disabled", true);
                    $("#setall").attr('disabled', 'disabled');
                    return false;
                }
            }
        </script>
    </head>
    <!-- END HEAD -->
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return onloadbody();">
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_tr1.jsp"%>
            <!-- END MENU -->
            <%                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                String pswx = Utility.checkAttribute(session,"us_pwd");
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
                            <h3 class="page-title">Open/Close - <b>Open</b></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT TILL -->
                    <%
                        if (opened) {%>

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
                    <script type="text/javascript">
                        op();
                    </script>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="alert alert-info">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> The operation could not be completed. Please try again. 
                            </div>
                        </div>
                        <div class="col-md-6">
                            <a href="ti_openclose.jsp" class="btn btn-outline blue"><i class="fa fa-arrow-left"></i> Back</a>
                        </div>
                    </div>    
                    <%} else {

                        String usr = Engine.isBlockedOperation();
                        if (usr != null) {%>
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
                            <a href="ti_openclose.jsp" class="btn btn-outline blue"><i class="fa fa-arrow-left"></i> Back</a>
                        </div>
                    </div>
                    <%} else if (ti != null) {

                        boolean es = Engine.insertBlockedOperation(session);
                        if (es) {
                            ArrayList<String[]> array_list_oc_change = Engine.list_oc_change_fisici(opencloseid);
                            ArrayList<String[]> list_oc_change_cuts = Engine.list_oc_change_cuts_fisici(opencloseid);
                            ArrayList<String[]> list_oc_nochange = Engine.list_oc_nochange_fisici(opencloseid);

                    %>
                    <form name="f2" id="f2" method="post" action="Operazioni?type=oc_open">
                        <div class="row">
                            <!-- BUY -->
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Safe - Till</label>
                                    <div class="input-icon">
                                        <i class="fa fa-calculator font-blue"></i>
                                        <input type="text" class="form-control uppercase" disabled value="<%=Utility.sanitize(ti.getName())%>"> 
                                        <input type="hidden" name="safetill" value="<%=Utility.sanitize(ti.getCod())%>"/>
                                        <input type="hidden" name="opencloseid" value="<%=Utility.sanitize(opencloseid)%>"/>
                                    </div>
                                </div>
                            </div>
                                    <input type="hidden"  id="setall1" name="setall" value="0"/>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="md-checkbox">
                                        <input type="checkbox" id="setall" onchange="return setall_F();"
                                               class="md-checkbox" onkeypress="return keysub(this, event);"> 
                                        <label for="setall">
                                            <span></span>
                                            <span class="check"></span>
                                            <span class="box"></span> SELECT ALL
                                        </label>
                                    </div>
                                    <%if (!ti.isSafe()) {%>
                                    <label id="setalllabel" class="bold uppercase" style="display:none;">The cuts and currencies were verified.</label>
                                    <%}else{%>
                                    <label id="setalllabel" class="bold uppercase" style="display:none;">The cuts and currencies were verified in Safe Real Time function</label>
                                    <%}%>
                                </div>
                            </div>
                            <div class="col-md-3 ">
                                <div class="form-group pull-right">
                                    <label>&nbsp;</label><p class='ab'></p>
                                    <a href="Operazioni?type=unlock_it_et&so=ti_openclose.jsp" class="btn btn-outline red"><i class="fa fa-remove"></i> Cancel</a>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="portlet box blue">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-money"></i>
                                            <span class="caption-subject">Figures</span>
                                        </div>
                                        <div class="actions">
                                            <button type="button" class="btn btn-default btn-sm" onclick="return showAllQuantity();">
                                                <i class="fa fa-sort-desc"></i> Show All 
                                            </button>
                                            <button type="button" class="btn btn-default btn-sm" onclick="return hidequantitynull();">
                                                <i class="fa fa-sort-asc"></i> Hide 
                                            </button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <table class="table table-responsive table-bordered" id="sample_0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow">Figures</th>
                                                            <th class="tabnow">Kind</th>
                                                            <th class="tabnow">User Quantity</th>
                                                            <th class="tabnow">User Total</th>
                                                            <th class="tabnow">System Quantity</th>
                                                            <th class="tabnow">System Total</th>
                                                            <th class="tabnow">Difference</th>
                                                            <th class="tabnow">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%for (int i = 0; i < array_kind_fingures_openclose.size(); i++) {
                                                                if (array_kind_fingures_openclose.get(i)[2].equals("01")
                                                                        || array_kind_fingures_openclose.get(i)[2].equals("02")
                                                                        || array_kind_fingures_openclose.get(i)[2].equals("03")) {

                                                                    String q_old = "0";
                                                                    String t_old = startvalue;
                                                                    String diff = startvalue;
                                                                    String clname = "";

                                                                    for (int j = 0; j < array_list_oc_change.size(); j++) {
                                                                        if (array_kind_fingures_openclose.get(i)[2].equals(array_list_oc_change.get(j)[1])
                                                                                && array_kind_fingures_openclose.get(i)[0].equals(array_list_oc_change.get(j)[2])) {
//                                                                            q_old = Utility.formatMysqltoDisplay(array_list_oc_change.get(j)[4]);
                                                                            q_old = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(Utility.sanitize(array_list_oc_change.get(j)[4])), 0));
                                                                            t_old = Utility.formatMysqltoDisplay(Utility.sanitize(array_list_oc_change.get(j)[3]));
                                                                            diff = "-" + t_old;
                                                                            if (Utility.fd(array_list_oc_change.get(j)[3]) > 0) {
                                                                                clname = "font-red";
                                                                            }
                                                                            break;
                                                                        }
                                                                    }
                                                        %>
                                                        <tr id="trfig<%=i%>">
                                                            <td  class="<%=clname%>"id="trfig_td1_<%=i%>"><%=array_kind_fingures_openclose.get(i)[0]%> - <%=array_kind_fingures_openclose.get(i)[1]%>
                                                                <input type="hidden" id="s_curr<%=i%>" name="s_curr<%=i%>" value="<%=array_kind_fingures_openclose.get(i)[0]%>" />
                                                            </td>
                                                            <td  class="<%=clname%>"id="trfig_td2_<%=i%>"><%=array_kind_fingures_openclose.get(i)[2]%> - <%=array_kind_fingures_openclose.get(i)[3]%>
                                                                <input type="hidden" id="s_kind<%=i%>" name="s_kind<%=i%>" value="<%=array_kind_fingures_openclose.get(i)[2]%>" />
                                                            </td>
                                                            <td  class="<%=clname%>"id="trfig_td3_<%=i%>">
                                                                <%if (array_kind_fingures_openclose.get(i)[2].equals("01")) {%>
                                                                <span style="text-align: right;float: right;" 
                                                                      id="s_quantnow<%=i%>" >0</span>
                                                                <input type="hidden" name="s_quantnow<%=i%>" />
                                                                <%} else {%>
                                                                <input type="text" class="form-control inputright" id="quantnow<%=i%>" 
                                                                       name="s_quantnow<%=i%>" value="<%=q_old%>" 
                                                                       onchange="return setValueOtherKind('<%=i%>');"
                                                                       onkeypress="return keysub(this, event);"/>
                                                                <%}%>
                                                            </td>
                                                            <td  class="<%=clname%>"id="trfig_td4_<%=i%>">
                                                                <%if (array_kind_fingures_openclose.get(i)[2].equals("01")) {%>
                                                                <span style="text-align: right;float: right;" id="s_totnow<%=i%>"><%=Utility.sanitize(startvalue)%></span>
                                                                <input type="hidden" name="s_totnow<%=i%>" />
                                                                <%} else {%>
                                                                <input type="text" class="form-control inputright" id="totalnow<%=i%>" 
                                                                       name="s_totnow<%=i%>" value="<%=Utility.sanitize(t_old)%>" 
                                                                       onchange="return setValueOtherKind('<%=i%>');"
                                                                       onkeypress="return keysub(this, event);">
                                                                <%}%>
                                                            </td>
                                                            <td  class="<%=clname%>"id="trfig_td5_<%=i%>">
                                                                <span style="text-align: right;float: right;" id="s_quantold<%=i%>" ><%=q_old%></span>
                                                                <input type="hidden" id="quantold<%=i%>" name="s_quantold<%=i%>" value="<%=q_old%>">
                                                            </td>
                                                            <td  class="<%=clname%>"id="trfig_td6_<%=i%>">
                                                                <span style="text-align: right;float: right;" id="s_totalold<%=i%>"><%=Utility.sanitize(t_old)%></span>
                                                                <input type="hidden" id="totalold<%=i%>" name="s_totalold<%=i%>" value="<%=Utility.sanitize(t_old)%>" />
                                                            </td>
                                                            <td  class="<%=clname%>"id="trfig_td7_<%=i%>">
                                                                <span style="text-align: right;float: right;" id="s_diffgap<%=i%>"><%=Utility.sanitize(diff)%></span>
                                                                <input type="hidden" id="diffgap<%=i%>" name="s_diffgap<%=i%>" value="<%=Utility.sanitize(diff)%>">
                                                            </td>
                                                            <td>
                                                                <%if (array_kind_fingures_openclose.get(i)[2].equals("01")) {%>
                                                                <a class="btn btn-circle green btn-outline btn-sm" data-toggle="modal" href="#full<%=i%>"><i class="fa fa-pencil"></i> Edit </a>
                                                                <%ArrayList<String> array_sizecuts = new ArrayList<>();
                                                                    for (int j = 0; j < array_figures_sizecuts.size(); j++) {
                                                                        if (array_figures_sizecuts.get(j).getValuta().equals(array_kind_fingures_openclose.get(i)[0])) {
                                                                            array_sizecuts.add(array_figures_sizecuts.get(j).getIp_taglio());
                                                                        }
                                                                    }%>
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
                                                                                                            <th class="tabnow">User Quantity</th>
                                                                                                            <th class="tabnow">User Total</th>
                                                                                                            <th class="tabnow">System Quantity</th>
                                                                                                            <th class="tabnow">System Total</th>
                                                                                                            <th class="tabnow">Difference</th>
                                                                                                        </tr>
                                                                                                        <%

                                                                                                            int total_q_fig_sizecuts = 0;
                                                                                                            double total_now_fig_sizecuts = 0.00;
                                                                                                            double total_old_fig_sizecuts = 0.00;
                                                                                                            for (int j = 0; j < array_sizecuts.size(); j++) {

                                                                                                                String si_q_old = "0";
                                                                                                                String si_t_old = startvalue;

                                                                                                                for (int k = 0; k < list_oc_change_cuts.size(); k++) {

                                                                                                                    if (array_kind_fingures_openclose.get(i)[2].equals(list_oc_change_cuts.get(k)[2])
                                                                                                                            && array_kind_fingures_openclose.get(i)[0].equals(list_oc_change_cuts.get(k)[1])
                                                                                                                            && array_sizecuts.get(j).equals(list_oc_change_cuts.get(k)[3])) {

                                                                                                                        si_q_old = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(list_oc_change_cuts.get(k)[4]), 0));

                                                                                                                        total_q_fig_sizecuts = total_q_fig_sizecuts + Integer.parseInt(Utility.roundDoubleandFormat(Utility.fd(list_oc_change_cuts.get(k)[4]), 0));
                                                                                                                        si_t_old = Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_change_cuts.get(k)[5]));
                                                                                                                        break;
                                                                                                                    }
                                                                                                                }

                                                                                                                total_old_fig_sizecuts = total_old_fig_sizecuts + Utility.fd(Utility.formatDoubleforMysql(si_t_old));


                                                                                                        %>



                                                                                                        <tr id="trcuts<%=i%>_<%=j%>">
                                                                                                            <td><%=Utility.formatMysqltoDisplay(array_sizecuts.get(j))%>
                                                                                                                <input type="hidden" name="curr<%=i%>_<%=j%>" value="<%=array_kind_fingures_openclose.get(i)[0]%>" />
                                                                                                                <input type="hidden" name="kind<%=i%>_<%=j%>" value="<%=array_kind_fingures_openclose.get(i)[2]%>" />
                                                                                                                <input type="hidden" name="cuts<%=i%>_<%=j%>" value="<%=array_sizecuts.get(j)%>" />
                                                                                                            </td>
                                                                                                            <td><input type="text" class="form-control inputright" id="quantnow<%=i%>_<%=j%>" 
                                                                                                                       name="quantnow<%=i%>_<%=j%>" value="<%=si_q_old%>" 
                                                                                                                       onkeypress="return keysub(this, event);"
                                                                                                                       onchange="return checkdiff_qu('<%=i%>_<%=j%>', '<%=array_sizecuts.get(j).trim()%>', '<%=i%>', '<%=array_sizecuts.size()%>');" 
                                                                                                                       >
                                                                                                            </td>
                                                                                                            <td><span style="text-align: right;float: right;"id="totnow<%=i%>_<%=j%>" 
                                                                                                                      ><%=Utility.sanitize(si_t_old)%></span>
                                                                                                                <input type="hidden" name="totnow<%=i%>_<%=j%>"/>
                                                                                                            </td>
                                                                                                            <td><span style="text-align: right;float: right;"id="quantold<%=i%>_<%=j%>"><%=Utility.sanitize(si_q_old)%></span>
                                                                                                                <input type="hidden"  name="quantold<%=i%>_<%=j%>"/>
                                                                                                            </td>
                                                                                                            <td><span style="text-align: right;float: right;"id="totold<%=i%>_<%=j%>"><%=Utility.sanitize(si_t_old)%></span>
                                                                                                                <input type="hidden" name="totold<%=i%>_<%=j%>"/>
                                                                                                            </td>
                                                                                                            <td><span style="text-align: right;float: right;"id="diffgapsize<%=i%>_<%=j%>">0</span>
                                                                                                                <input type="hidden" name="diffgapsize<%=i%>_<%=j%>"/>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <%}%>
                                                                                                        <tr><th>TOTAL</th>
                                                                                                            <th>
                                                                                                                <span class="font-blue-dark" style="text-align: right;float: right;" id="totquantcuts<%=i%>">
                                                                                                                    <%=Utility.formatMysqltoDisplay(String.valueOf(total_q_fig_sizecuts))%>

                                                                                                                </span>
                                                                                                                <input type="hidden" name="totquantcuts<%=i%>"/>
                                                                                                            </th>
                                                                                                            <th>
                                                                                                                <span class="font-blue-dark" style="text-align: right;float: right;" id="totnewcuts<%=i%>">
                                                                                                                    <%=Utility.sanitize(t_old)%>

                                                                                                                </span>
                                                                                                                <input type="hidden" name="totnewcuts<%=i%>"/>
                                                                                                            </th>
                                                                                                            <th></th>
                                                                                                            <th>
                                                                                                                <span class="font-green" style="text-align: right;float: right;">
                                                                                                                    <%=Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(total_old_fig_sizecuts, 2))%>
                                                                                                                </span>

                                                                                                            </th>
                                                                                                            <td></td>
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
                                                                                                                    {orderable: !1, targets: [2]},
                                                                                                                    {orderable: !1, targets: [3]},
                                                                                                                    {orderable: !1, targets: [4]},
                                                                                                                    {orderable: !1, targets: [5]}

                                                                                                                ],
                                                                                                                scrollX: true,
                                                                                                                lengthMenu: [[-1], ["All"]],
                                                                                                                pageLength: -1,
                                                                                                                order: [],
                                                                                                                dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t>"
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
                                                                                    <button type="button" class="btn green btn-outline" 
                                                                                            onclick="return setValuefig('<%=i%>');"data-dismiss="modal">
                                                                                        <i class="fa fa-save"></i> Save changes</button>
                                                                                    <button type="button" class="btn red btn-outline" 
                                                                                            onclick="return resetrow('<%=i%>');" ><i class="fa fa-remove"></i> Reset</button>
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
                            <div class="col-md-12">
                                <div class="portlet box blue-dark">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-arrows-v"></i>
                                            <span class="caption-subject">No change</span>
                                        </div>
                                        <div class="actions">
                                            <button type="button" class="btn btn-default btn-sm" onclick="return showAllQuantity_nc();">
                                                <i class="fa fa-sort-desc"></i> Show All 
                                            </button>
                                            <button type="button" class="btn btn-default btn-sm" onclick="return hidequantitynull_nc();">
                                                <i class="fa fa-sort-asc"></i> Hide 
                                            </button>
                                        </div>
                                    </div>

                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <table class="table table-responsive table-bordered" id="sample_1" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow">Description</th>
                                                            <th class="tabnow">User Quantity</th>
                                                            <th class="tabnow">System Quantity</th>
                                                            <th class="tabnow">Difference</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%for (int i = 0; i < array_list_nochange.size(); i++) {
                                                                String nc_quant = "0";
                                                                String clname = "";
                                                                for (int j = 0; j < list_oc_nochange.size(); j++) {
                                                                    if (array_list_nochange.get(i)[0].equals(list_oc_nochange.get(j)[1])) {
                                                                        nc_quant = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(list_oc_nochange.get(j)[2]), 0));
                                                                        clname = "font-red";
                                                                        break;
                                                                    }
                                                                }
                                                        %>
                                                        <tr id="trnoc<%=i%>" class="<%=clname%>">
                                                            <td id="trnoc_td1_<%=i%>"><%=array_list_nochange.get(i)[0]%> - <%=array_list_nochange.get(i)[1]%>
                                                                <input type="hidden" id="nc_cod<%=i%>" name="nc_cod<%=i%>" value="<%=array_list_nochange.get(i)[0]%>"/>
                                                            </td>
                                                            <td>
                                                                <input type="text" class="form-control inputright" 
                                                                       id="nc_quantnow<%=i%>" 
                                                                       name="nc_quantnow<%=i%>" value="0" 
                                                                       onchange="return checkdiff_nc('<%=i%>');" onkeypress="return keysub(this, event);"/>
                                                            </td>
                                                            <td>
                                                                <span style="text-align: right;float: right;"><%=nc_quant%></span>
                                                                <input type="hidden" id="nc_quantold<%=i%>" name="nc_quantold<%=i%>" value="<%=nc_quant%>"/>
                                                            </td>
                                                            <td>
                                                                <span style="text-align: right;float: right;" id="nc_diffgap<%=i%>"><%=nc_quant%></span>
                                                                <input type="hidden" name="nc_diffgap<%=i%>"/>
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

                            <div class="col-md-12">
                                <center><a class="btn btn-lg green-jungle btn-block" href="#finalmodal" data-toggle="modal" onclick="return checkdifference();"><i class="fa fa-save"></i> Open</a></center>
                            </div>                                    

                            <div class="modal fade paddtop" id="finalmodal" tabindex="-1" role="dialog" aria-hidden="true">
                                <div class="modal-dialog modal-full">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                            <h4 class="modal-title" id="finalmodal_title"></h4>
                                        </div>
                                        <div class="modal-body" id="finalmodal_body"> 
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn red btn-outline" data-dismiss="modal"><i class="fa fa-remove"></i> Cancel</button>
                                            <button type="button" id="subformbutt" onclick="return subform(this);" class="btn green"><i class="fa fa-arrow-right"></i> Continue</button>
                                        </div>
                                    </div>
                                    <!-- /.modal-content -->
                                </div>
                                <!-- /.modal-dialog -->
                            </div>                                  

                            <%} else {%>

                            <%}%>
                        </div>
                        <input type="hidden" id="errorpresent" name="errorpresent" value="N" />
                    </form>
                    <%}
                        }%>


                    <!-- BEGIN CONTENT -->

                    <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title font-red uppercase"><b>Error message</b></h4>
                                </div>
                                <div class="modal-body" id="errorlargetext">ERROR</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn dark btn-outline" id="meserr2" onclick="return dismiss('errorlarge');" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
                    </div>
                </div>

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
            <!--[if lt IE 9]>
    <script src="../assets/global/plugins/respond.min.js"></script>
    <script src="../assets/global/plugins/excanvas.min.js"></script> 
    <![endif]-->
            <!-- BEGIN CORE PLUGINS -->

            <!-- END CORE PLUGINS -->
            <!-- BEGIN PAGE LEVEL PLUGINS -->
            <!-- END PAGE LEVEL PLUGINS -->
            <!-- BEGIN THEME GLOBAL SCRIPTS -->


            <!-- END THEME LAYOUT SCRIPTS -->

            <!-- BEGIN THEME GLOBAL SCRIPTS -->
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
                                {orderable: !1, targets: [7]}
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
                    var dt1 = function () {
                        var f = $("#sample_1");
                        f.dataTable({
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
                                {orderable: !1, targets: [3]}
                            ],
                            scrollX: true,
                            lengthMenu: [[-1], ["All"]],
                            pageLength: -1,
                            order: [],
                            dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'><'col-md-6 col-sm-12'f>r><t>"
                        });
                    };

                    jQuery().dataTable && dt1();
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

    </body> 
</html>
