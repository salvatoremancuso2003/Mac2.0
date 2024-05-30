<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.ET_change"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
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
        <title>Mac2.0 - Ext. Tr. NC</title>
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
            String fil[] = Engine.getFil();
            ArrayList<ET_change> array_frombranch = Engine.get_ET_change_frombranch(fil[0], "0", "NC");
            ArrayList<String[]> array_till = Engine.list_till_enabled();
            ArrayList<Till> array_till_open = Engine.list_till_status("O", "");
            ArrayList<NC_category> array_nc = Engine.all_nc_category();
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

        <script src="assets/soop/js/pace.js" type="text/javascript"></script>
        <link rel="stylesheet" href="assets/soop/css/pace-theme-center-circle.css" />
        
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/core-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5.js" type="text/javascript"></script>
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
        <script type="text/javascript">

            var separatordecimal = '<%=Constant.decimal%>';
            var separatorthousand = '<%=Constant.thousand%>';
            function isfrombranch() {

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
                    ET_change et1 = array_frombranch.get(j);
            %>
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

                checkoff();
            }

            function changetype() {

                $('#bankbranch').empty().trigger('change');
            <%for (int j = 0; j < array_branch.size(); j++) {

                    if (!array_branch.get(j).getCod().equals(fil[0])) {
            %>
                var o = $("<option/>", {value: "<%=array_branch.get(j).getCod()%>", text: "<%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch().toUpperCase()%>"});
                        $('#bankbranch').append(o);
            <%}
                }%>

                        $('#bankbranch').val('').trigger('change');
                        //$('#bankbranch').val($('#bankbranch option:first-child').val()).trigger('change');
                        isfrombranch();
                    }



                    function cli(usr) {
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = "Warning! Operation not permitted. The operation of the same type is in progress by the operator " + usr + ".<p class='ab'></p> Please wait for the end of this operation.";
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
                        for (index = 0; index < inputs.length; ++index) {
                            if (inputs[index].id !== "") {
                                if (document.getElementsByName(inputs[index].id)[0] !== null) {
                                    // msg = msg + " - ID: " + inputs[index].id + " - VALUE: " + inputs[index].innerHTML.trim() + "\n";
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
                            document.getElementById('srcoff').value = "ONLINE";
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
                            document.getElementById('srcoff').value = "OFFLINE";
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
                            document.getElementById("errorlargetext").innerHTML = "Error! Field 'Branch' must be completed.";
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
            <%@ include file="menu/menu_tr6.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                String usr = Engine.isBlockedOperation();
                String pswx = Utility.checkAttribute(session,"us_pwd");
                boolean iscentral = Engine.isCentral();
                //iscentral = false;
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
                            <h3 class="page-title">Transaction <small><b>External Transfer No Change</b></small></h3>
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

                                    <button type="button" class="btn green-jungle btn-outline" onclick="return sub1();"><i class="fa fa-check"></i> Confirm</button>
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
                            <a href="et_nochange.jsp" class="btn btn-outline blue"><i class="fa fa-refresh"></i> Refresh</a>
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
                        } else if (esito.equals("1Q") || esito.equals("Q")) {
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
                            <%if (esito.equals("OK")) {
                                    String cod = request.getParameter("cod");
                                    if (cod != null) {%>
                            <form action="Download?type=viewET_frbr_receipt" method="post" target="_blank" name="f3" id="f3">
                                <input type="hidden" name="cod" value="<%=Utility.sanitize(cod)%>"/>
                                <input type="hidden" id="typeop" name="typeop" value="NC"/>
                                <button type="submit" class="btn green-jungle"><i class="fa fa-file-pdf-o"></i> Print your receipt</button>
                            </form>
                            <%}
                                }%>
                        </div>
                    </div>
                    <hr>
                    <%}%>


                    <%if (!mostraaltro) {%>

                    <a href="et_nochange.jsp" class="btn blue"><i class="fa fa-plus-circle"></i> New External No Change Transaction</a>

                    <%} else {%>

                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <%if (request.getParameter("search") == null) {%>
                    <form name="f1" id="f1" method="post" action="et_nochange.jsp">
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
                                           data-off-text="<span class='tabnow'>From</span>"
                                           >
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
                                    <input type="hidden" id="typeop" name="typeop" value="BR">
                                    <input type="text" class="form-control" value="Branch" disabled>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Branch <span class="font-red">*</span></label>
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
                                    <button type="button" onclick="return checkform();" class="btn btn-outline dark"><i class="fa fa-spinner"></i> Load Data</button>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else if (request.getParameter("search").equals("sra1")) {
                        String tillfrom = request.getParameter("tillfrom");
                        String tofrom = request.getParameter("tofrom");
                        String typeop = request.getParameter("typeop");

                        String autman = request.getParameter("autman");

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

                        ArrayList<String[]> list_oc_nochange = Engine.list_oc_nochange_real(idopentillfrom);

                        boolean es = Engine.insertBlockedOperation(session);
                        if (es) {%>
                    <form name="f2" id="f2" method="post" action="Operazioni?type=et_nochange">
                        <input type="hidden" name="srcoff" id="srcoff"/>
                        <input type="hidden" name="sizeindex" id="sizeindex" value="<%=list_oc_nochange.size()%>"/>

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
                                    <input type="hidden" name="tofrom" value="<%=Utility.sanitize(tofrom)%>"/>
                                </div>
                            </div>
                            <%if (tofrom.equals("F") && typeop.equals("BR")) {%>   
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label>Auto / Manual</label><p class='ab'></p>
                                    <input type="checkbox" <%=am%> readonly class="make-switch"
                                           data-size="normal" 
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
                                    <input type="text" class="form-control" readonly 
                                           value="Branch">
                                    <input type="hidden" id="typeop" name="typeop"  value="<%=Utility.sanitize(typeop)%>"/>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Branch</label>
                                    <input type="text" class="form-control"  readonly  name="bankbranch"
                                           value="<%=Engine.formatBankBranch(bankbranch, typeop, null, array_branch,array_credit_card)%>"></input>
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
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Note</label>
                                    <div class="input-icon">
                                        <i class="fa fa-sticky-note-o font-blue"></i>
                                        <input type="text" class="form-control" name="note" id="note" maxlength="100" onkeyup="return fieldNOSPecial_2(this.id);"/>
                                    </div>
                                </div>
                            </div>  

                        </div>  
                        <%if (tofrom.equals("T")) {%>
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
                                                                        <th class="tabnow">Actual Stock</th>
                                                                        <th class="tabnow">Quantity</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%for (int i = 0; i < list_oc_nochange.size(); i++) {%>
                                                                    <tr id="trnoc<%=i%>">
                                                                        <td id="trnoc_td1_<%=i%>"><%=Utility.sanitize(list_oc_nochange.get(i)[1])%> - <%=Utility.sanitize(Engine.formatALNC_category(list_oc_nochange.get(i)[1], array_nc))%>
                                                                        </td>
                                                                <input type="hidden" name="nc_causal<%=i%>" value="<%=Utility.sanitize(list_oc_nochange.get(i)[1])%>"/>
                                                                <td>
                                                                    <span style="text-align: right;float: right;" id="nc_quantold<%=i%>"><%=Utility.sanitize(list_oc_nochange.get(i)[2])%></span>
                                                                    <input type="hidden" name="nc_quantold<%=i%>" value="<%=Utility.sanitize(list_oc_nochange.get(i)[2])%>"/>
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control inputright" 
                                                                           id="nc_quantnow<%=i%>" name="nc_quantnow<%=i%>" value="0" 
                                                                           onchange="return formatValueINT_1_change(this, separatorthousand, separatordecimal);" />
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
                        <div class="row">
                            <%if (list_oc_nochange.size() > 0) {%>
                            <div class="col-md-6">
                                <center><button id="subformbutt" type="button" onclick="return subform('N', this);" 
                                                class="btn btn-lg green btn-block">
                                        <i class="fa fa-save"></i> Confirm Transaction</button></center>
                            </div>                            

                            <%}%>
                            <div class="col-md-6">
                                <center><a href="Operazioni?type=unlock_it_et&so=et_nochange.jsp" class="btn btn-lg red btn-block" ><i class="fa fa-remove"></i> Cancel Operation</a></center>
                            </div>
                        </div>
                        <%} else if (tofrom.equals("F") && srcoff.equals("OFFLINE")) {
                            ArrayList<String[]> array_list_nochange = Engine.list_nochange();
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
                                                <table class="table table-responsive table-bordered" id="sample_1" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow">Description</th>
                                                            <th class="tabnow" style="width: 200px;">Quantity</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%for (int i = 0; i < array_list_nochange.size(); i++) {
                                                        %>
                                                        <tr id="trnoc<%=i%>">
                                                            <td id="trnoc_td1_<%=i%>"><%=array_list_nochange.get(i)[0]%> - <%=array_list_nochange.get(i)[1]%>
                                                            </td>
                                                            <td>
                                                                <input type="text" class="form-control inputright" 
                                                                       id="nc_quantnow<%=i%>" name="nc_quantnow<%=i%>" 
                                                                       value="0" onchange="return formatValueINT_1_change(this, separatorthousand, separatordecimal);" />
                                                                <input type="hidden" name="nc_causal<%=i%>" value="<%=array_list_nochange.get(i)[0]%>"/>
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
                            <%if (array_list_nochange.size() > 0) {%>

                            <div class="col-md-6">
                                <center><button id="subformbutt2" type="button" onclick="return subform('N', this);" 
                                                class="btn btn-lg green btn-block">
                                        <i class="fa fa-save"></i> Confirm Transaction</button></center>
                            </div>    

                            <%}%>
                            <div class="col-md-6">
                                <center><a href="Operazioni?type=unlock_it_et&so=et_nochange.jsp" class="btn btn-lg red btn-block" ><i class="fa fa-remove"></i> Cancel Operation</a></center>
                            </div>
                        </div>
                        <%} else if (tofrom.equals("F") && srcoff.equals("ONLINE")) {
                            ArrayList<ET_change> val = Engine.get_ET_nochange_value(srcing);
                        %>
                        <%if (val.size() > 0) {%>
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
                                                <table class="table table-responsive table-bordered" id="sample_1" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow">Description</th>
                                                            <th class="tabnow" style="width: 200px;">Quantity</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%for (int i = 0; i < val.size(); i++) {
                                                        %>
                                                        <tr>
                                                            <td><%=Utility.sanitize(val.get(i).getNc_causal())%> - <%=Utility.sanitize(Engine.formatALNC_category(val.get(i).getNc_causal(), array_nc))%>
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
                        <div class="row">
                            <div class="col-md-6">
                                <center><button type="button" class="btn btn-lg green btn-block" id="subformbutt3" onclick="return subform('N', this);"><i class="fa fa-save"></i> Confirm</button></center>
                            </div>    
                            <div class="col-md-6">
                                <center><a href="Operazioni?type=unlock_it_et&so=et_nochange.jsp" class="btn btn-lg red btn-block" ><i class="fa fa-remove"></i> Cancel Operation</a></center>
                            </div>


                        </div>
                        <%}%>                    
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
                    <%}%>
                </div>
            </div>

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
        });</script>

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
                        {orderable: !1, targets: [1]}
                    ],
                    scrollX: true,
                    lengthMenu: [[-1], ["All"]],
                    pageLength: -1,
                    order: [],
                    dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'><'col-md-6 col-sm-12'f>r><t>"
                });
            };
            jQuery().dataTable && dt();
        });</script>
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
