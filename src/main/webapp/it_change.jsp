<%@page import="rc.so.entity.Sizecuts"%>
<%@page import="java.util.List"%>
<%@page import="rc.so.entity.Till"%>
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
        <title>Mac2.0 - Int. Tr.</title>
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

            ArrayList<Currency> array_all_currency = Engine.list_all_currency();
            ArrayList<String[]> array_kind = Engine.list_all_kind(Engine.getFil()[0]);
            ArrayList<String[]> array_till = Engine.list_till_enabled();
            ArrayList<Till> array_till_open = Engine.list_till_status("O", "");
            //ArrayList<String[]> array_list_oc_change = Engine.list_oc_change();
            ArrayList<String[]> array_list_oc_change = Engine.list_oc_change_real();
            //ArrayList<String[]> array_kind_figures_openclose = Engine.kind_figures_openclose();
            List<Sizecuts> array_figures_sizecuts = Engine.figures_sizecuts_enabled();

        %>

        <script src="assets/soop/js/pace.js" type="text/javascript"></script>




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

            var separatordecimal = '<%=Constant.decimal%>';
            var separatorthousand = '<%=Constant.thousand%>';

            function loadtill() {

            <%for (int j = 0; j < array_till_open.size(); j++) {%>
                var o = $("<option/>", {value: "<%=array_till_open.get(j).getCod()%>", text: "<%=Utility.formatAL(array_till_open.get(j).getCod(), array_till, 1)%>"});
                $('#tillfrom').append(o);
            <%}%>
                //$('#tillfrom').val($('#tillfrom option:first-child').val()).trigger('change');
                $('#tillfrom').val('').trigger('change');
            }

            function changetillfrom() {

                var v1 = document.getElementById('tillfrom').value;


                $('#tillto').empty().trigger('change');
            <%for (int j = 0; j < array_till_open.size(); j++) {%>
                if (v1 !== "<%=array_till_open.get(j).getCod()%>") {
                    var o = $("<option/>", {value: "<%=array_till_open.get(j).getCod()%>", text: "<%=Utility.formatAL(array_till_open.get(j).getCod(), array_till, 1)%>"});
                    $('#tillto').append(o);
                } else {
                    document.getElementById('idopentillfrom').value = padDigits('<%=array_till_open.get(j).getCod_opcl()%>', 15);
                    document.getElementById('idopentillfrom_v').value = '<%=array_till_open.get(j).getId_opcl()%>';
                }
            <%}%>
                $('#tillto').val('').trigger('change');
                //$('#tillto').val($('#tillto' + ' option:first-child').val()).trigger('change');
            }

            function changetill_to() {
                document.getElementById('idopentillto').value = '';
                document.getElementById('idopentillto_v').value = '';
                var v1 = document.getElementById('tillto').value;
            <%for (int j = 0; j < array_till_open.size(); j++) {%>
                var ch = '<%=array_till_open.get(j).getCod()%>';
                if (ch === v1) {
                    document.getElementById('idopentillto').value = padDigits('<%=array_till_open.get(j).getCod_opcl()%>', 15);
                    document.getElementById('idopentillto_v').value = '<%=array_till_open.get(j).getId_opcl()%>';
                }
            <%}%>
            }

            function checkdiff_qu(index, size, indextotal, limitindex) {

                formatValueINT_1_change(document.getElementById('quantnow' + index), separatorthousand, separatordecimal);

                var quantnow = document.getElementById('quantnow' + index).value;
                //quantnow = accounting.formatNumber(quantnow, 0, '', '');
                //var quantnowcheck = quantnow.replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '');

                var quantnowcheck = quantnow.replace(/\D/g, '');
                document.getElementById('totnow' + index).innerHTML =
                        accounting.formatNumber((parseFloatRaf(quantnowcheck, separatorthousand, separatordecimal) *
                                parseFloatRaf(size)).toString(), 2, separatorthousand, separatordecimal);
                //document.getElementById('quantnow' + index).value = parseFloatRaf(quantnowcheck, separatorthousand, separatordecimal);
                settotal_checkdiff_qu(indextotal, limitindex);
            }

            function settotal_checkdiff_qu(indextotal, size) {
                var tot = 0;
                for (var i = 0; i < size; i++) {
                    tot = parseIntRaf(document.getElementById('quantnow' + indextotal + '_' + i).value) + tot;
                }
                document.getElementById('totquantcuts' + indextotal).innerHTML = accounting.formatNumber(parseIntRaf(tot), 0, separatorthousand, separatordecimal);


                var tot_2 = 0;
                for (var i = 0; i < size; i++) {
                    tot_2 = parseFloatRaf(replacethousand(document.getElementById('totnow' + indextotal + '_' + i).innerHTML, separatorthousand), separatorthousand, separatordecimal)
                            + tot_2;
                }
                document.getElementById('totnewcuts' + indextotal).innerHTML = accounting.formatNumber(tot_2.toString(), 2, separatorthousand, separatordecimal);
                setValueRow(indextotal);
            }

            function change_vf(type, field, index) {
                if (type === "1") {
                    fieldOnlyNumber(field.id);
                } else if (type === "2") {
                    formatValueDecimal_1(field, '<%=thousand%>', '<%=decimal%>');
                }
                setValueRow(index);
            }

            function setValueRow(index) {
                var qo = replacethousand(getvalueofField(document.getElementById('s_quantold' + index)), separatorthousand);
                var to = replacethousand(getvalueofField(document.getElementById('s_totold' + index)), separatorthousand);
                var qn = replacethousand(getvalueofField(document.getElementById('s_quantnow' + index)), separatorthousand);
                var tn = replacethousand(getvalueofField(document.getElementById('s_totnow' + index)), separatorthousand);

                var safefr = document.getElementsByName('safefr')[0].value;
                if (qn !== "" && tn !== "") {
                    var er = false;
                    if (safefr === "true") {
                        er = parseFloatRaf(qn, separatorthousand, separatordecimal) > parseFloatRaf(qo, separatorthousand, separatordecimal) || parseFloatRaf(tn, separatorthousand, separatordecimal) > parseFloatRaf(to, separatorthousand, separatordecimal);
                    } else {
                        er = parseFloatRaf(tn, separatorthousand, separatordecimal) > parseFloatRaf(to, separatorthousand, separatordecimal);
                    }
                    if (er) {
                        var kind = getvalueofField(document.getElementById('trfig_td2_' + index));
                        var curr = getvalueofField(document.getElementById('trfig_td1_' + index));
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = "Error! Quantity or Total are incorrect for figures <span class='font-red'>" + curr + " - " + kind + "</span>";
                        return false;
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

            function subform() {
                document.getElementById('butconf').disabled = true;
                $("#butconf").html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");
                
                //var st = checktill();
                //if (!st) {
                //    document.getElementById('butconf').disabled = false;
                //    document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
                //    return false;
                //}
                
                $('table').each(function () {
                    if ($.fn.dataTable.fnIsDataTable(this)) {
                        $(this).DataTable().search('').draw();
                    }
                });

                var inputs, index;
                inputs = document.getElementById('f2').getElementsByTagName('span');
                for (index = 0; index < inputs.length; ++index) {
                    if (inputs[index].id !== "") {
                        if (document.getElementsByName(inputs[index].id)[0] !== null) {
                            document.getElementsByName(inputs[index].id)[0].value = inputs[index].innerHTML.trim();
                        }
                    }
                }
                var safefr = document.getElementsByName('safefr')[0].value;
                if (safefr === "true") {
                    var all = document.getElementsByTagName("input");
                    for (var i = 0; i < all.length; i++) {
                        if (all[i].name !== null && all[i].name !== "") {
                            if (all[i].name.search("kind") >= 0) {
                                if (getvalueofField(all[i]) === "02" || getvalueofField(all[i]) === "03") {
                                    var idrow = all[i].name.replace(/\D/g, "").trim();

                                    var kind = getvalueofField(document.getElementById('trfig_td2_' + idrow));
                                    var curr = getvalueofField(document.getElementById('trfig_td1_' + idrow));

                                    var qold = parseFloatRaf(replacethousand(getvalueofField((document.getElementsByName('s_quantold' + idrow)[0])), separatorthousand), separatorthousand, separatordecimal);
                                    var qnew = parseFloatRaf(replacethousand(getvalueofField((document.getElementsByName('s_quantnow' + idrow)[0])), separatorthousand), separatorthousand, separatordecimal);
                                    var told = parseFloatRaf(replacethousand(getvalueofField((document.getElementsByName('s_totold' + idrow)[0])), separatorthousand), separatorthousand, separatordecimal);
                                    var tnew = parseFloatRaf(replacethousand(getvalueofField((document.getElementsByName('s_totnow' + idrow)[0])), separatorthousand), separatorthousand, separatordecimal);

                                    if (qold === qnew) {
                                        if (told !== tnew) {
                                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                            document.getElementById("errorlarge").style.display = "block";
                                            document.getElementById("errorlargetext").innerHTML = "Error! Quantity or Total are incorrect for figures <span class='font-red'>" + curr + " - " + kind + "</span>";
                                            document.getElementById('butconf').disabled = false;
                                            document.getElementById('butconf').innerHTML = "<i class='fa fa-save'></i> Confirm Transaction";
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

            function checktill() {

                var tillfrom = document.getElementById("tillfrom").value.trim();
                var idfrom = document.getElementById("idopentillfrom_v").value.trim();
                var tillto = document.getElementById("tillto").value.trim();
                var idto = document.getElementById("idopentillto_v").value.trim();

                if (tillfrom === "" || tillto === "") {
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = "Warning! Operation not permitted. Fields with <span class='font-red'>*</span> must be completed.";
                    return false;
                }

                var er1 = true;
                var msg = "ERROR";

                $.ajax({
                    async: false,
                    type: "POST",
                    url: "Operazioni_test?type=verificaID_OC&tillfrom="
                            + tillfrom + "&idfrom=" + idfrom + "&tillto=" + tillto + "&idto=" + idto,
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
                return true;

            }

            function loadpage1() {
                online();
                loadtill();
                inputvirgola();
            }

            function loadpage2() {
                $('#largelogin').on('shown.bs.modal', function () {
                    $('#passwordlargelogin').focus();
                });
                online();
                loadtill();
                inputvirgola();
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
            <%@ include file="menu/menu_tr2.jsp"%>
            <!-- END MENU -->
            <%
                String pswx = Utility.checkAttribute(session,"us_pwd");
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);

                String usr = Engine.isBlockedOperation();
                boolean iscentral = Engine.isCentral();
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
                                                <input class="form-control" type="password" autocomplete="off" placeholder="Password" maxlength="10"  name="passwordlargelogin" id="passwordlargelogin" onkeypress="checkkeysub('buttonsubmitlargelogin', event);"> 
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
                    <%@ include file="menu/shortcuts.jsp"%>
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction <small><b>Internal Transfer</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>


                    <%if (!iscentral) {%>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
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
                            <a href="it_change.jsp" class="btn btn-outline blue"><i class="fa fa-refresh"></i> Refresh</a>
                        </div>
                    </div>
                    <%} else {%>
                    <%if (request.getParameter("search") == null) {%>
                    <form name="f1" id="f1" method="post" action="it_change.jsp" onsubmit="return checktill();">
                        <input type="hidden" name="search" value="sra1"/>

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>From <span class="font-red">*</span></label>
                                    <select class="form-control select2" name="tillfrom" id="tillfrom" onchange="return changetillfrom();">
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>To <span class="font-red">*</span></label>
                                    <select class="form-control select2" name="tillto" id="tillto" onchange="return changetill_to();">

                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>&nbsp;</label><p class='ab'></p>
                                    <button type="submit" class="btn btn-outline dark"><i class="fa fa-spinner"></i> Load Data</button>
                                </div>
                            </div>

                            <div class="clearfix"></div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Id open till - From</label>
                                    <div class="input-icon">
                                        <i class="fa fa-bookmark font-blue-dark"></i>
                                        <input type="text" class="form-control uppercase" id="idopentillfrom" disabled/>
                                        <input type="hidden" class="form-control" id="idopentillfrom_v" name="idopentillfrom"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>Id open till - To</label>
                                    <div class="input-icon">
                                        <i class="fa fa-bookmark font-blue-dark"></i>
                                        <input type="text" class="form-control uppercase" id="idopentillto" disabled/> 
                                        <input type="hidden" id="idopentillto_v" name="idopentillto">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else if (request.getParameter("search").equals("sra1")) {

                        String idopentillfrom = request.getParameter("idopentillfrom");
                        String idopentillto = request.getParameter("idopentillto");
                        String ti_from = request.getParameter("tillfrom");
                        String ti_to = request.getParameter("tillto");
                        Till fr = Engine.getContainsTill(ti_from, array_till_open);
                        Till to = Engine.getContainsTill(ti_to, array_till_open);
                    %>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>From</label>
                                <input type="text" class="form-control" readonly="readonly" name="tillfrom" value="<%=Utility.sanitize(Utility.formatAL(ti_from, array_till, 1))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>To</label>
                                <input type="text" class="form-control" readonly="readonly" name="tillto" value="<%=Utility.sanitize(Utility.formatAL(request.getParameter("tillto"), array_till, 1))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>&nbsp;</label><p class='ab'></p>
                                <a href="Operazioni?type=unlock_it_et&so=it_change.jsp" class="btn btn-outline red"><i class="fa fa-remove"></i> Cancel Operation</a>
                            </div>
                        </div>

                        <div class="clearfix"></div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id open till - From</label>
                                <div class="input-icon">
                                    <i class="fa fa-bookmark font-blue-dark"></i>
                                    <input type="text" class="form-control uppercase" readonly="readonly" value="<%=Utility.sanitize(Engine.query_oc(idopentillfrom).getId())%>"> 
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id open till - To</label>
                                <div class="input-icon">
                                    <i class="fa fa-bookmark font-blue-dark"></i>
                                    <input type="text" class="form-control uppercase" readonly="readonly" value="<%=Utility.sanitize(Engine.query_oc(idopentillto).getId())%>">  
                                </div>
                            </div>
                        </div>
                    </div>
                    <%      if (usr != null) {
                        } else {
                            String tillfrom = request.getParameter("tillfrom");
                            String tillto = request.getParameter("tillto");
                            boolean safefr = fr.isSafe();
                            boolean safeto = to.isSafe();
                            boolean es = Engine.insertBlockedOperation(session);
                            String startvalue = "0" + decimal + "00";
                            if (es) {%>
                    <form name="f2" id="f2" method="post" action="Operazioni?type=it_change">
                        <input type="hidden" name="idopentillfrom" value="<%=Utility.sanitize(idopentillfrom)%>"/>
                        <input type="hidden" name="idopentillto" value="<%=Utility.sanitize(idopentillto)%>"/>
                        <input type="hidden" name="tillfrom" value="<%=Utility.sanitize(tillfrom)%>"/>
                        <input type="hidden" name="tillto" value="<%=Utility.sanitize(tillto)%>"/>
                        <input type="hidden" name="safefr" value="<%=safefr%>"/>
                        <input type="hidden" name="safeto" value="<%=safeto%>"/>


                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet box blue">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-money"></i>
                                            <span class="caption-subject">Figures </span>
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
                                                            <th class="tabnow">Actual Quantity</th>
                                                            <th class="tabnow">Actual Total</th>
                                                            <th class="tabnow">Quantity</th>
                                                            <th class="tabnow">Total</th>
                                                            <th class="tabnow" style="width: 80px;">Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>

                                                        <%
                                                            boolean one = false;
                                                            for (int i = 0; i < array_list_oc_change.size(); i++) {
                                                                if (idopentillfrom.equals(array_list_oc_change.get(i)[0])) {

                                                                    if (Utility.fd(array_list_oc_change.get(i)[3]) > 0) {
                                                                        one = true;
                                                                        String act_quant = "";
                                                                        String act_total1 = Utility.formatMysqltoDisplay(array_list_oc_change.get(i)[3]);

                                                                        if (!fr.isSafe()) {
                                                                            act_quant = "0";
                                                                        } else {
                                                                            act_quant = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(array_list_oc_change.get(i)[4]), 0));
                                                                        }

                                                        %>
                                                        <tr id="trfig<%=i%>">
                                                            <td id="trfig_td1_<%=i%>"><%=array_list_oc_change.get(i)[2]%> - <%=Engine.formatALCurrency(array_list_oc_change.get(i)[2], array_all_currency)%>
                                                            </td>
                                                            <td id="trfig_td2_<%=i%>"><%=array_list_oc_change.get(i)[1]%> - <%=Utility.formatAL(array_list_oc_change.get(i)[1], array_kind, 1)%>
                                                            </td>
                                                            <td id="trfig_td3_<%=i%>">
                                                                <span style="text-align: right;float: right;"  id="s_quantold<%=i%>" name="sss">
                                                                    <%=act_quant%>
                                                                </span>
                                                                <input type="hidden"  name="s_quantold<%=i%>"/>
                                                                <input type="hidden"  name="currency<%=i%>" value="<%=array_list_oc_change.get(i)[2]%>"/>
                                                                <input type="hidden"  name="kind<%=i%>" value="<%=array_list_oc_change.get(i)[1]%>"/>
                                                            </td>
                                                            <td id="trfig_td3_<%=i%>">
                                                                <span style="text-align: right;float: right;" id="s_totold<%=i%>" name="sss">
                                                                    <%=act_total1%>
                                                                </span>
                                                                <input type="hidden"  name="s_totold<%=i%>"/>
                                                            </td>
                                                            <td id="trfig_td4_<%=i%>">
                                                                <%if (array_list_oc_change.get(i)[1].equals("01")) {%>
                                                                <span style="text-align: right;float: right;"id="s_quantnow<%=i%>" name="sss"></span>
                                                                <input type="hidden" name="s_quantnow<%=i%>"/>
                                                                <%} else if (fr.isSafe()) {%>
                                                                <input type="text" name="s_quantnow<%=i%>" id="s_quantnow<%=i%>" class="form-control inputright" value="0" onchange="return change_vf('1', this, '<%=i%>');"/>
                                                                <%} else {%>
                                                                <input type="text" name="s_quantnow<%=i%>" id="s_quantnow<%=i%>" class="form-control inputright" value="0"  onchange="return change_vf('1', this, '<%=i%>');"/>
                                                                <%}%>
                                                            </td>
                                                            <td id="trfig_td5_<%=i%>">
                                                                <%if (array_list_oc_change.get(i)[1].equals("01")) {%>
                                                                <span style="text-align: right;float: right;" id="s_totnow<%=i%>" name="sss"></span>
                                                                <input type="hidden" name="s_totnow<%=i%>"/>
                                                                <%} else {%>
                                                                <input type="text" name="s_totnow<%=i%>" id="s_totnow<%=i%>" 
                                                                       class="form-control inputright" value="<%=startvalue%>"
                                                                       onchange="return change_vf('2', this, '<%=i%>');"/>
                                                                <%}%>
                                                            </td>
                                                            <td>
                                                                <%if (array_list_oc_change.get(i)[1].equals("01")) {%>
                                                                <a class="btn btn-circle green btn-outline btn-sm" data-toggle="modal" href="#full<%=i%>"><i class="fa fa-pencil"></i> Edit </a>
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

                                                                                                            ArrayList<String[]> list_oc_change_cuts = Engine.list_oc_change_cuts_real(idopentillfrom);
                                                                                                            boolean visual = false;
                                                                                                            if (fr.isSafe()) {
                                                                                                                visual = true;
                                                                                                            }
                                                                                                            //int actualst = 0;
                                                                                                            double tot_act = 0.00;
                                                                                                            double total_old_fig_sizecuts = 0.00;
                                                                                                            for (int j = 0; j < array_sizecuts.size(); j++) {
                                                                                                                String act_size = "";
                                                                                                                String act_total = "";
                                                                                                                if (visual) {
                                                                                                                    act_size = "0";
                                                                                                                    act_total = "0" + decimal + "00";
                                                                                                                    for (int k = 0; k < list_oc_change_cuts.size(); k++) {
                                                                                                                        if (list_oc_change_cuts.get(k)[1].equals(array_list_oc_change.get(i)[2])) {
                                                                                                                            if (list_oc_change_cuts.get(k)[3].equals(array_sizecuts.get(j))) {
                                                                                                                                act_size = Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(list_oc_change_cuts.get(k)[4]), 0));

                                                                                                                                //actualst = actualst + (Integer.parseInt(act_size));
                                                                                                                                act_total = Utility.formatMysqltoDisplay(Utility.sanitize(list_oc_change_cuts.get(k)[5]));

                                                                                                                                tot_act = tot_act + Utility.fd(Utility.formatDoubleforMysql(act_total));
                                                                                                                                break;
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }%>
                                                                                                        <tr id="trcuts<%=i%>_<%=j%>" valign="middle">
                                                                                                    <input type="hidden"  name="cuts_currency<%=i%>_<%=j%>" value="<%=array_list_oc_change.get(i)[2]%>"/>
                                                                                                    <input type="hidden"  name="cuts_kind<%=i%>_<%=j%>" value="<%=array_list_oc_change.get(i)[1]%>"/>

                                                                                                    <td><%=Utility.formatMysqltoDisplay(array_sizecuts.get(j))%></td>
                                                                                                    <input type="hidden" name="sizecuts<%=i%>_<%=j%>" value="<%=array_sizecuts.get(j)%>"/>
                                                                                                    <td >
                                                                                                        <span style="text-align: right;float: right;"id="quantold<%=i%>_<%=j%>" name="sss">
                                                                                                            <%=act_size%>
                                                                                                        </span><input type="hidden" name="quantold<%=i%>_<%=j%>"/>
                                                                                                    </td>
                                                                                                    <td ><span style="text-align: right;float: right;"
                                                                                                               id="totold<%=i%>_<%=j%>" name="sss"
                                                                                                               ><%=Utility.sanitize(act_total)%></span>
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
                                                                                                    <%}%>

                                                                                                    <tr>
                                                                                                        <th>TOTAL</th>
                                                                                                        <th><span class="font-blue-dark" style="text-align: right;float: right;" ><%=act_quant%></span></th>
                                                                                                        <th><span class="font-blue-dark" style="text-align: right;float: right;" >
                                                                                                                <%=act_total1%>
                                                                                                            </span></th>
                                                                                                        <th><span class="font-red" style="text-align: right;float: right;"id="totquantcuts<%=i%>"  name="sss">0</span>
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
                                                                }
                                                            }%>
                                                    </tbody>

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%if (one) {%>
                        <div class="row">
                            <div class="col-md-12">
                                <center><button type="button" onclick="return subform();" class="btn btn-lg green btn-block" id="butconf"><i class="fa fa-save"></i> Confirm Transaction</button></center>
                            </div>                            
                        </div>
                        <%}%>
                    </form>
                    <%} else {

                        }
                    %>
                    <%}
                            }
                        }%>
                    <!-- END CONTENT -->
                    <!-- BEGIN QUICK SIDEBAR -->
                    <!-- END QUICK SIDEBAR -->


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
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        }

                        if (!esito.equals("")) {
                    %>
                    <hr>

                    <div class="row">
                        <div class="col-md-12 ">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
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
                                    {orderable: 1, targets: [0]},
                                    {orderable: 1, targets: [1]},
                                    {orderable: 1, targets: [2]},
                                    {orderable: 1, targets: [3]},
                                    {orderable: 1, targets: [4]},
                                    {orderable: 1, targets: [5]},
                                    {orderable: !1, targets: [6]}
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
                <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>


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
