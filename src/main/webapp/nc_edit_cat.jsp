<%@page import="rc.so.entity.NC_vatcode"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.util.Engine"%>
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
        <title>Mac2.0 - No Change</title>
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


        <link href="assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />

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
        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>



        <%
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;
            String startvalue = "0" + decimal + "00";
        %>
        <script type="text/javascript">

            var separatordecimal = "<%=decimal%>";
            var separatorthousand = "<%=thousand%>";

            function checkdescr() {
                var descr = document.getElementById("descr").value.trim();
                var cod = document.getElementById("cod").value.trim();
                if (descr === "" || cod === "") {
                    var ermsg = "You must complete fields with <span class='font-red'>*</span>.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

                var seall = document.getElementById('seall').checked;
                var branch = document.getElementById('branch').value;
                if (branch === "" && !seall) {
                    var ermsg = "You must select at least one branch.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }


                var dt_val = document.getElementById("dt_val").value.trim();

                if (!moment(dt_val, 'DD/MM/YYYY hh:mm').isValid() || dt_val === "31/12/1899 00:00") {
                    document.getElementById("dt_val").value = "";
                    var ermsg = "'Date Apply' are incorrect.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                var nowdate = moment();
                var pobdate1 = moment(dt_val, 'DD/MM/YYYY hh:mm', true);
                if (nowdate.isSameOrAfter(pobdate1)) {
                    var ermsg = "'Date Apply' are incorrect. The date must be after the current.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

                var k1test = document.getElementById('kind0');
                if (k1test === null) {

                    k1test = document.getElementById('kind_1');
                }

                //NUOVA
                var kind = k1test.value;


                if ('<%=Constant.is_IT%>' === 'true' && kind === '2') {
                    if (document.getElementById('regi').checked) {
                        var price0 = parseFloatRaf(document.getElementById('price').value, separatorthousand, separatordecimal);
                        var price1 = parseFloatRaf(document.getElementById('am_esolv1').value, separatorthousand, separatordecimal);
                        var price2 = parseFloatRaf(document.getElementById('am_esolv2').value, separatorthousand, separatordecimal);
                        if (isemptyField(document.getElementById('esolv1')) || isemptyField(document.getElementById('v_esolv1'))) {
                            var ermsg = "'Accounting Code #1' and 'VAT Code #1' must be completed.";
                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                            document.getElementById("errorlarge").style.display = "block";
                            document.getElementById("errorlargetext").innerHTML = ermsg;
                            return false;
                        } else {
                            if (price1 <= 0) {
                                var ermsg = "'Amount #1' must be greater than zero.";
                                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                document.getElementById("errorlarge").style.display = "block";
                                document.getElementById("errorlargetext").innerHTML = ermsg;
                                return false;
                            }
                            if (isemptyField(document.getElementById('esolv2')) || isemptyField(document.getElementById('v_esolv2'))) {
                                if (price2 > 0) {
                                    var ermsg = "'Accounting Code #2' and 'VAT Code #2' must be completed.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    return false;
                                    return false;
                                }
                            } else {
                                if (price2 <= 0) {
                                    var ermsg = "'Amount #2' must be greater than zero.";
                                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                    document.getElementById("errorlarge").style.display = "block";
                                    document.getElementById("errorlargetext").innerHTML = ermsg;
                                    return false;
                                }
                            }
                        }
                        if (price0 !== (price1 + price2)) {
                            var ermsg = "Price error. Check fields: 'Price', 'Amount #1' and 'Amount #2'.";
                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                            document.getElementById("errorlarge").style.display = "block";
                            document.getElementById("errorlargetext").innerHTML = ermsg;
                            return false;
                        }

                    }
                }

                return true;


            }

            function valid(sel, val) {
                var verified = document.getElementById(sel);
                if (verified.checked) {
                    $('#' + val).val('').change();
                    document.getElementById(val).disabled = true;
                } else {
                    document.getElementById(val).disabled = false;
                }
            }

            function controllakind() {
                var kind = document.getElementById('kind0');
                if (kind !== null) {
                    if (kind.value === '21') { //TICKETS
                        document.getElementById('tf1').style.display = '';
                    } else {
                        document.getElementById('tf1').style.display = 'none';
                    }
                    if (kind.value === '7') { //INTERNET 
                        document.getElementById('ip2A').style.display = '';
                        document.getElementById('ip2C').style.display = 'none';
                        checkcorresp();
                    } else {
                        document.getElementById('ip2A').style.display = 'none';
                        document.getElementById('ip2B').style.display = 'none';
                        document.getElementById('ip2C').style.display = '';
                    }
                }
            }

            function checkcorresp() {
                if (document.getElementById('corres').checked) {
                    document.getElementById('ip2B').style.display = '';
                } else {
                    document.getElementById('ip2B').style.display = 'none';
                }
            }

            function controllakind1() {
                var kind = document.getElementById('kind_1');
                if (kind !== null) {
                    if (kind.value === '21') { //TICKETS
                        document.getElementById('tf2').style.display = '';
                    } else {
                        document.getElementById('tf2').style.display = 'none';
                    }

                    if (kind.value === '7') { //INTERNET 
                        document.getElementById('ip2A').style.display = '';
                        document.getElementById('ip2C').style.display = 'none';
                        checkcorresp();
                    } else {
                        document.getElementById('ip2A').style.display = 'none';
                        document.getElementById('ip2B').style.display = 'none';
                        document.getElementById('ip2C').style.display = '';
                    }
                }
                formatValueDecimalMax(document.getElementById('periva'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
            }


            function changetipoticket(kindid) {
                var kind = document.getElementById(kindid).value;
                if (kind === "21") {
                    var titype = document.getElementById('titype').checked;
                    $("#tfeevalue").removeAttr("onchange");
                    if (titype) {
                        $("#tfeevalue").attr("onchange", 'formatValueDecimalMax(tfeevalue, "100" + separatordecimal + "00", separatorthousand, separatordecimal)');
                    } else {
                        $("#tfeevalue").attr("onchange", 'formatValueDecimal_1(tfeevalue, separatorthousand, separatordecimal)');
                    }
                    $("#tfeevalue").change();
                }
            }

            function loadpage() {
                controllakind();
                controllakind1();
                inputvirgola();
                viewfiscal();
            }

            function viewfiscal() {
                 var kind = document.getElementById('kind0');
                if (kind === null) {

                    kind = document.getElementById('kind_1');
                }
                
                if (kind !== null) {
                    var checked = ($('#regi').prop('checked'));
                    if (checked) {
                        if ('<%=Constant.is_IT%>' === 'true' && kind.value === '2') {
                            $('#viewfiscal').show();
                            $('#viewfiscal1').show();
                            $('#viewfiscal2').show();
                            $('#viewfiscal3').show();
                            $('#viewfiscal4').show();
                            $('#viewfiscal5').show();
                        } else {
                            $('#viewfiscal').show();
                            $('#viewfiscal1').hide();
                            $('#viewfiscal2').show();
                            $('#viewfiscal3').hide();
                            $('#viewfiscal4').hide();
                            $('#viewfiscal5').hide();
                        }
                    } else {
                        $('#viewfiscal').hide();
                        $('#viewfiscal1').hide();
                        $('#viewfiscal2').hide();
                        $('#viewfiscal3').hide();
                        $('#viewfiscal4').hide();
                        $('#viewfiscal5').hide();
                    }
                } else {
                    $('#viewfiscal').hide();
                    $('#viewfiscal1').hide();
                    $('#viewfiscal2').hide();
                    $('#viewfiscal3').hide();
                    $('#viewfiscal4').hide();
                    $('#viewfiscal5').hide();
                }
            }
        </script>
    </head>
    <!-- END HEAD -->



    <body class="page-full-width page-content-white bg-white" onload="return loadpage();">

        <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
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
        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container bg-white">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
            <div class="page-content-wrapper bg-white">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content bg-white">
                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <!--    VUOTO RAF  -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="clearfix"></div>
                    <%
                        String esito = request.getParameter("esito");
                        if (esito == null) {
                            esito = "";
                        }
                        String classal = "alert-info";
                        String classfa = "fa-exclamation-triangle";
                        String msg = "Warning";
                        String msg1 = "No operation";
                        if (esito.equals("ok")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        } else if (esito.equals("koins1")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. 'Id Category' already present.";
                        } else if (esito.equals("koins2")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed.";
                        }
                        if (!esito.equals("")) {
                    %>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
                    <%}%>


                    <%

                        String view = request.getParameter("view");
                        if (view == null) {
                            view = "0";
                        }
                        String dateApply = Utility.getDefaultDateAplly(true, false, 0, 0, 30, null);
                        ArrayList<Branch> array_branch = Engine.list_branch_enabled();
                        ArrayList<String[]> array_nc_kind = Engine.nc_kind();
                        ArrayList<String[]> array_nc_department = Engine.nc_department();

                        NC_category nc = Engine.getNC_category(request.getParameter("nc_code"));
                        if (nc != null) {

                            String dep1 = nc.getDepartment();

                            String status = "";
                            if (nc.getAnnullato().equals("0")) {
                                status = "checked";
                            }
                            String tty = "";
                            if (nc.getTicket_fee_type().equals("1")) {
                                tty = "checked";
                            }

                            String tena = "";
                            if (nc.getTicket_enabled().equals("1")) {
                                tena = "checked";
                            }

                            String corr = "";
                            if (nc.getInt_corrisp().equals("1")) {
                                corr = "checked";
                            }

                            String regi = "";
                            if (nc.getFg_registratore().equals("1")) {
                                regi = "checked";
                            }

                            if (view.equals("1")) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">View Category</span>
                                        </div>
                                        <div class="tools"> 

                                        </div>
                                    </div>
                                    <div class="portlet-body">

                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Status</label>
                                                <div class="col-md-9">
                                                    <input type="checkbox" <%=status%> class="make-switch" readonly="readonly"
                                                           id="status" name="status" onkeypress="return keysub(this, event);" 
                                                           data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                           data-on-color="success" data-off-color="danger"
                                                           >
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Id Category</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" onkeypress="return keysub(this, event);" readonly="readonly"
                                                           id="cod" name="cod" value="<%=Utility.sanitize(nc.getGruppo_nc())%>" readonly="readonly"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Description</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" id="descr" name="descr" readonly="readonly"
                                                           value="<%=Utility.sanitize(nc.getDe_gruppo_nc())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Kind</label>
                                                <div class="col-md-9">
                                                    <input type="hidden" id="kind0" name="kind0" readonly="readonly"
                                                           onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(nc.getFg_tipo_transazione_nc())%>"/>
                                                    <input type="text" class="form-control" 
                                                           name="kind" value="<%=Utility.sanitize(Utility.formatAL(nc.getFg_tipo_transazione_nc(), array_nc_kind, 1))%>" 
                                                           disabled="disabled"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Price</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" readonly="readonly"
                                                           id="price" name="price" onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(nc.getIp_prezzo_nc()))%>"> 
                                                </div>
                                            </div>
                                            <div id="tf1">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Ticket Fee</label>
                                                    <div class="col-md-9">
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <input type="text" class="form-control inputright" readonly="readonly" 
                                                                       value="<%= Utility.formatMysqltoDisplay(Utility.sanitize(nc.getTicket_fee()))%>"/> 
                                                            </div><div class="col-md-6">
                                                                <input type="checkbox" class="make-switch" <%=tty%> readonly="readonly"
                                                                       id="tfee" name="tfee" onkeypress="return keysub(this, event);" 
                                                                       data-size="normal" data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                       data-off-text="<span class='tabnow'>Currency</span>" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"># Max Ticket</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" readonly="readonly"
                                                               id="maxti" name="maxti" onkeypress="return keysub(this, event);"
                                                               value="<%=Utility.sanitize(nc.getMax_ticket())%>"> 
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Edit Ticket fee</label>
                                                    <div class="col-md-9">
                                                        <input type="checkbox" class="make-switch" readonly="readonly" <%=tena%>
                                                               id="editti" name="editti" onkeypress="return keysub(this, event);"
                                                               data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                               data-on-color="success" data-off-color="danger">
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="ip2A">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Corresponding</label>
                                                    <div class="col-md-9">
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <input type="checkbox" readonly="readonly" class="make-switch" onchange="return checkcorresp();" <%=corr%> 
                                                                       id="corres" name="corres" onkeypress="return keysub(this, event);" 
                                                                       data-size="normal" data-on-text="<span class='tabnow'>Yes</span>" 
                                                                       data-off-text="<span class='tabnow'>No</span>" 
                                                                       data-on-color="success" data-off-color="danger"
                                                                       />
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="ip2B">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Percent IVA</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control inputright" 
                                                               id="periva" name="periva" readonly="readonly"
                                                               onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');"
                                                               value="<%=Utility.sanitize(nc.getInt_iva())%>"> 
                                                    </div>
                                                    <label class="col-md-3 control-label">Accounting code</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control inputright" readonly="readonly" value="<%=Utility.sanitize(nc.getInt_code())%>"
                                                               id="acode" name="acode" onkeypress="return keysub(this, event);" 
                                                               onchange="return fieldOnlyNumber(this.id);"> 
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="ip2C">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Fiscal Receipt</label>
                                                    <div class="col-md-3">
                                                        <input type="checkbox" <%=regi%> class="make-switch" readonly="readonly"
                                                               id="regi" name="regi" onkeypress="return keysub(this, event);" 
                                                               data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                               data-on-color="success" data-off-color="danger"
                                                               />
                                                    </div>
                                                    <div id="viewfiscal">
                                                        <%if (Constant.is_IT && nc.getFg_tipo_transazione_nc().equals("2")) {%>
                                                        <%} else {%>
                                                        <label class="col-md-3 control-label">Fiscal Department</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" readonly="readonly" id="department" name="department"
                                                                   onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(Utility.formatAL(dep1, array_nc_department, 1))%>" 
                                                                   maxlength="100"> 
                                                        </div>
                                                        <%}%>
                                                    </div>
                                                </div>
                                                <%if (Constant.is_IT && nc.getFg_tipo_transazione_nc().equals("2")) {%>

                                                <%} else {%>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Accounting Code #1</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control"readonly="readonly" id="esolv1" name="esolv1" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(nc.getConto_coge_01())%>" maxlength="10" /> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Accounting Code #2</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control"readonly="readonly" id="esolv2" name="esolv2" onkeypress="return keysub(this, event);"  value="<%=Utility.sanitize(nc.getConto_coge_02())%>" maxlength="10" /> 
                                                    </div>
                                                </div>
                                                <%}%>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Header</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" readonly="readonly" id="headre" name="headre" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(nc.getDe_scontrino())%>" maxlength="100"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Text</label>
                                                    <div class="col-md-9">
                                                        <textarea type="text" rows="10" readonly="readonly"class="form-control" id="textre" name="textre" maxlength="1000"><%=Utility.sanitize(nc.getDe_riga())%></textarea>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>    
                    <%} else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_category" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit Category</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">

                                        <div class="form-body">

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Branch</label>
                                                <div class="col-md-6">
                                                    <select class="form-control select2" name="listbranch" id="branch" multiple>
                                                        <%for (int j = 0; j < array_branch.size(); j++) {%>
                                                        <option value="<%=array_branch.get(j).getCod()%>">
                                                            <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="md-checkbox">
                                                        <input type="checkbox" id="seall" name="seall" class="md-checkbox" onchange="return valid('seall', 'branch');"> 
                                                        <label for="seall">
                                                            <span></span>
                                                            <span class="check"></span>
                                                            <span class="box"></span> Select All
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Date Apply</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control form_datetime" 
                                                           id="dt_val" name="dt_val" onkeypress="return keysub(this, event);" data-date-start-date="+0d"
                                                           value="<%=dateApply%>">
                                                </div>
                                            </div>
                                            <hr>


                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Status</label>
                                                <div class="col-md-9">
                                                    <input type="checkbox" <%=status%> class="make-switch"
                                                           id="status" name="status" onkeypress="return keysub(this, event);" 
                                                           data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                           data-on-color="success" data-off-color="danger"
                                                           >
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Id Category</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" onkeypress="return keysub(this, event);" 
                                                           id="cod" name="cod" value="<%=Utility.sanitize(nc.getGruppo_nc())%>" readonly="readonly"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Description <span class="font-red">*</span></label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" id="descr" name="descr" 
                                                           value="<%=Utility.sanitize(nc.getDe_gruppo_nc())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Kind</label>
                                                <div class="col-md-9">
                                                    <input type="hidden" id="kind0" name="kind0" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(nc.getFg_tipo_transazione_nc())%>"/>
                                                    <input type="text" class="form-control" 
                                                           name="kind" value="<%=Utility.sanitize(Utility.formatAL(nc.getFg_tipo_transazione_nc(), array_nc_kind, 1))%>" 
                                                           disabled="disabled"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Price</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           id="price" name="price" onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           onkeypress="return keysub(this, event);" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(nc.getIp_prezzo_nc()))%>"> 
                                                </div>
                                            </div>
                                            <%if (Constant.is_IT && nc.getFg_tipo_transazione_nc().equals("2")) {
                                                    NC_vatcode vat = Engine.get_NC_vatcode(nc.getGruppo_nc());
                                                    if (vat.getAccountingcode1().equals("")) {
                                                        vat.setAccountingcode1(nc.getConto_coge_01());
                                                        vat.setVatcode1(nc.getConto_coge_02());
                                                        vat.setPrice1(nc.getIp_prezzo_nc());
                                                        vat.setDepartment1(nc.getDepartment());
                                                    }
                                            %>        
                                            <hr>

                                            <div id="ip2B">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Percent IVA</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control inputright" 
                                                               id="periva" name="periva" 
                                                               onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');"
                                                               value="<%=Utility.sanitize(nc.getInt_iva())%>"> 
                                                    </div>
                                                    <label class="col-md-3 control-label">Accounting code</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control inputright" value="<%=Utility.sanitize(nc.getInt_code())%>"
                                                               id="acode" name="acode" onkeypress="return keysub(this, event);" 
                                                               onchange="return fieldOnlyNumber(this.id);"> 
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Fiscal Receipt</label>
                                                <div class="col-md-3">
                                                    <input type="checkbox" <%=regi%> class="make-switch" onchange="return viewfiscal()"
                                                           id="regi" name="regi" onkeypress="return keysub(this, event);" 
                                                           data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                           data-on-color="success" data-off-color="danger"
                                                           />
                                                </div>

                                            </div> 
                                            <div id="ip2C">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Accounting Code #1</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="esolv1" name="esolv1" 

                                                               onkeypress="return keysub(this, event);" 
                                                               maxlength="10" value="<%=Utility.sanitize(vat.getAccountingcode1())%>"> 
                                                    </div>
                                                    <div id="viewfiscal1">
                                                        <label class="col-md-3 control-label">Accounting Code #2</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="esolv2" name="esolv2"  

                                                                   onkeypress="return keysub(this, event);" maxlength="10" value="<%=Utility.sanitize(vat.getAccountingcode2())%>"> 
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="viewfiscal2">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">VAT Code #1</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="v_esolv1" name="v_esolv1" 
                                                                   onkeypress="return keysub(this, event);" maxlength="10" value="<%=Utility.sanitize(vat.getVatcode1())%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">VAT Code #2</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="v_esolv2" name="v_esolv2" 
                                                                   onkeypress="return keysub(this, event);" maxlength="10" value="<%=Utility.sanitize(vat.getVatcode2())%>"> 
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="viewfiscal">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Amount #1</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="am_esolv1" 
                                                                   name="am_esolv1" onkeypress="return keysub(this, event);"
                                                                   onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');" 
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(vat.getPrice1()))%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Amount #2</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="am_esolv2" name="am_esolv2" onkeypress="return keysub(this, event);" 
                                                                   onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');" 
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(vat.getPrice2()))%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Fiscal Department #1</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="department" name="department" onkeypress="return keysub(this, event);" 
                                                                    placeholder="...">
                                                                <%for (int i = 0; i < array_nc_department.size(); i++) {
                                                                        String sel = "";
                                                                        if (vat.getDepartment1().equals(array_nc_department.get(i)[0])) {
                                                                            sel = "selected";
                                                                        }%>
                                                                <option <%=sel%> value="<%=array_nc_department.get(i)[0]%>"><%=array_nc_department.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <label class="col-md-3 control-label">Fiscal Department #2</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="department2" name="department2" onkeypress="return keysub(this, event);" 
                                                                    placeholder="...">
                                                                <%for (int i = 0; i < array_nc_department.size(); i++) {
                                                                        String sel = "";
                                                                        if (vat.getDepartment2().equals(array_nc_department.get(i)[0])) {
                                                                            sel = "selected";
                                                                        }%>
                                                                <option <%=sel%> value="<%=array_nc_department.get(i)[0]%>"><%=array_nc_department.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="form-group">
                                                    <hr>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Header</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control"  id="headre" name="headre" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(nc.getDe_scontrino())%>" maxlength="100"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Text</label>
                                                    <div class="col-md-9">
                                                        <textarea type="text" rows="10" class="form-control" id="textre" name="textre" maxlength="1000"><%=Utility.sanitize(nc.getDe_riga())%></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <%} else {%>

                                            <div id="ip2B">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Percent IVA</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control inputright" 
                                                               id="periva" name="periva" 
                                                               onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');"
                                                               value="<%=Utility.sanitize(nc.getInt_iva())%>"> 
                                                    </div>
                                                    <label class="col-md-3 control-label">Accounting code</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control inputright" value="<%=Utility.sanitize(nc.getInt_code())%>"
                                                               id="acode" name="acode" onkeypress="return keysub(this, event);" 
                                                               onchange="return fieldOnlyNumber(this.id);"> 
                                                    </div>
                                                </div>
                                            </div>


                                            <div id="ip2C">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Accounting Code #1</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" id="esolv1" name="esolv1" onkeypress="return keysub(this, event);" maxlength="10" value="<%=Utility.sanitize(nc.getConto_coge_01())%>"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Accounting Code #2</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" id="esolv2" name="esolv2" onkeypress="return keysub(this, event);"  maxlength="10" value="<%=Utility.sanitize(nc.getConto_coge_02())%>"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Header</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control"  id="headre" name="headre" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(nc.getDe_scontrino())%>" maxlength="100"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Text</label>
                                                    <div class="col-md-9">
                                                        <textarea type="text" rows="10" class="form-control" id="textre" name="textre" maxlength="1000"><%=Utility.sanitize(nc.getDe_riga())%></textarea>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Fiscal Receipt</label>
                                                    <div class="col-md-3">
                                                        <input type="checkbox" <%=regi%> class="make-switch" onchange="return viewfiscal()"
                                                               id="regi" name="regi" onkeypress="return keysub(this, event);" 
                                                               data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                               data-on-color="success" data-off-color="danger"
                                                               />
                                                    </div>
                                                    <div id="viewfiscal">
                                                        <label class="col-md-3 control-label">Fiscal Department</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="department" name="department" onkeypress="return keysub(this, event);" 
                                                                    placeholder="...">
                                                                <%for (int i = 0; i < array_nc_department.size(); i++) {
                                                                        String sel = "";
                                                                        if (dep1.equals(array_nc_department.get(i)[0])) {
                                                                            sel = "selected";
                                                                        }%>
                                                                <option <%=sel%> value="<%=array_nc_department.get(i)[0]%>"><%=array_nc_department.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>


                                            <%}%> 

                                            <div id="tf1">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Ticket Fee</label>
                                                    <div class="col-md-9">
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <input type="text" class="form-control inputright" name="tfeevalue" id="tfeevalue" 
                                                                       value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(nc.getTicket_fee()))%>" 
                                                                       <%if (nc.getTicket_fee_type().equals("1")) {%>
                                                                       onchange="return formatValueDecimal_1_max(document.getElementById(this.id), '100' + separatordecimal + '00', separatorthousand, separatordecimal);"
                                                                       <%} else {%>
                                                                       onchange="return formatValueDecimal_1(document.getElementById(this.id), separatorthousand, separatordecimal);"
                                                                       <%}%>
                                                                       /> 
                                                            </div>
                                                            <div class="col-md-6">
                                                                <input type="checkbox" class="make-switch" <%=tty%>
                                                                       id="titype" name="titype" onkeypress="return keysub(this, event);" 
                                                                       data-size="normal" data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                       data-off-text="<span class='tabnow'>Currency</span>" onchange="return changetipoticket('kind0');" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"># Max Ticket</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" 
                                                               id="maxti" name="maxti" 
                                                               onkeypress="return keysub(this, event);"  
                                                               onchange="return fieldOnlyNumber(this.id);"
                                                               value="<%=Utility.sanitize(nc.getMax_ticket())%>"> 
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Edit Ticket fee</label>
                                                    <div class="col-md-9">
                                                        <input type="checkbox" class="make-switch" <%=tena%>
                                                               id="editti" name="editti" onkeypress="return keysub(this, event);"
                                                               data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                               data-on-color="success" data-off-color="danger">
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="ip2A">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Corresponding</label>
                                                    <div class="col-md-9">
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <input type="checkbox" class="make-switch" onchange="return checkcorresp();" <%=corr%>
                                                                       id="corres" name="corres" onkeypress="return keysub(this, event);" 
                                                                       data-size="normal" data-on-text="<span class='tabnow'>Yes</span>" 
                                                                       data-off-text="<span class='tabnow'>No</span>" 
                                                                       data-on-color="success" data-off-color="danger"
                                                                       />
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}
                    } else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=new_category" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">New Category</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Branch</label>
                                                <div class="col-md-6">
                                                    <select class="form-control select2" name="listbranch" id="branch" multiple>
                                                        <%for (int j = 0; j < array_branch.size(); j++) {%>
                                                        <option value="<%=array_branch.get(j).getCod()%>">
                                                            <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-md-3">
                                                    <div class="md-checkbox">
                                                        <input type="checkbox" id="seall" name="seall" class="md-checkbox" onchange="return valid('seall', 'branch');"> 
                                                        <label for="seall">
                                                            <span></span>
                                                            <span class="check"></span>
                                                            <span class="box"></span> Select All
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Date Apply</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control form_datetime" 
                                                           id="dt_val" name="dt_val" onkeypress="return keysub(this, event);" data-date-start-date="+0d"
                                                           value="<%=dateApply%>">
                                                </div>
                                            </div>
                                            <hr>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Id Category <span class="font-red">*</span></label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" onkeypress="return keysub(this, event);" id="cod" name="cod" maxlength="10" 
                                                           onchange="return fieldNOSPecial(this.id);" /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Description <span class="font-red">*</span></label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="descr" name="descr" onkeypress="return keysub(this, event);" > 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Kind</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" onkeypress="return keysub(this, event);"
                                                            id="kind_1" name="kind_1" placeholder="..." onchange="return controllakind1();">
                                                        <%for (int i = 0; i < array_nc_kind.size(); i++) {%>
                                                        <option value="<%=array_nc_kind.get(i)[0]%>"><%=array_nc_kind.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Price</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           id="price" name="price" onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           onkeypress="return keysub(this, event);" value="<%=startvalue%>"> 
                                                </div>
                                            </div>
                                            <div id="tf2">    
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Ticket Fee</label>
                                                    <div class="col-md-9">
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <input type="text" class="form-control inputright" name="tfeevalue" id="tfeevalue" 
                                                                       value="<%=startvalue%>" 
                                                                       onchange="return formatValueDecimal_1(document.getElementById(this.id), separatorthousand, separatordecimal);"
                                                                       /> 
                                                            </div>
                                                            <div class="col-md-6">
                                                                <input type="checkbox" class="make-switch" 
                                                                       id="titype" name="titype" onkeypress="return keysub(this, event);" 
                                                                       data-size="normal" data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                       data-off-text="<span class='tabnow'>Currency</span>" onchange="return changetipoticket('kind_1');" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label"># Max Ticket</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" 
                                                               id="maxti" name="maxti" 
                                                               onkeypress="return keysub(this, event);"  
                                                               onchange="return fieldOnlyNumber(this.id);" > 
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Edit Ticket fee</label>
                                                    <div class="col-md-9">
                                                        <input type="checkbox" class="make-switch"
                                                               id="editti" name="editti" onkeypress="return keysub(this, event);"
                                                               data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                               data-on-color="success" data-off-color="danger">
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="ip2A">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Corresponding</label>
                                                    <div class="col-md-9">
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <input type="checkbox" class="make-switch" onchange="return checkcorresp();"
                                                                       id="corres" name="corres" onkeypress="return keysub(this, event);" 
                                                                       data-size="normal" data-on-text="<span class='tabnow'>Yes</span>" 
                                                                       data-off-text="<span class='tabnow'>No</span>" 
                                                                       data-on-color="success" data-off-color="danger"
                                                                       />
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="ip2B">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Percent IVA</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control inputright" 
                                                               id="periva" name="periva" 
                                                               onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');"
                                                               value="<%=startvalue%>"> 
                                                    </div>
                                                    <label class="col-md-3 control-label">Accounting code</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control inputright" 
                                                               id="acode" name="acode" 
                                                               onkeypress="return keysub(this, event);" onchange="return fieldOnlyNumber(this.id);"> 
                                                    </div>
                                                </div>
                                            </div>

                                            <%if (Constant.is_IT) {%>
                                            <div class="form-group">
                                                <hr>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Fiscal Receipt</label>
                                                <div class="col-md-3">
                                                    <input type="checkbox" class="make-switch" onchange="return viewfiscal()"
                                                           id="regi" name="regi" onkeypress="return keysub(this, event);" 
                                                           data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                           data-on-color="success" data-off-color="danger"
                                                           />
                                                </div>

                                            </div> 
                                            <div id="ip2C">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Accounting Code #1</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="esolv1" name="esolv1" 

                                                               onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                    <div id="viewfiscal1">
                                                        <label class="col-md-3 control-label">Accounting Code #2</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="esolv2" name="esolv2"  

                                                                   onkeypress="return keysub(this, event);" maxlength="10"> 
                                                        </div>
                                                    </div>

                                                </div>
                                                <div id="viewfiscal2">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">VAT Code #1</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="v_esolv1" name="v_esolv1" 
                                                                   onkeypress="return keysub(this, event);" maxlength="10"> 
                                                        </div>
                                                        <div id="viewfiscal4">
                                                            <label class="col-md-3 control-label">VAT Code #2</label>
                                                            <div class="col-md-3">
                                                                <input type="text" class="form-control" id="v_esolv2" name="v_esolv2" 
                                                                       onkeypress="return keysub(this, event);" maxlength="10"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="viewfiscal">
                                                    <div class="form-group" id="viewfiscal5">
                                                        <label class="col-md-3 control-label">Amount #1</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="am_esolv1" 
                                                                   name="am_esolv1" onkeypress="return keysub(this, event);"
                                                                   onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                                   > 
                                                        </div>

                                                        <label class="col-md-3 control-label">Amount #2</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="am_esolv2" name="am_esolv2" onkeypress="return keysub(this, event);" 
                                                                   onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');" 
                                                                   > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">


                                                        <label class="col-md-3 control-label">Fiscal Department #1</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="department" name="department" onkeypress="return keysub(this, event);" 
                                                                    placeholder="...">
                                                                <%for (int i = 0; i < array_nc_department.size(); i++) {
                                                                %>
                                                                <option value="<%=array_nc_department.get(i)[0]%>"><%=array_nc_department.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <div id="viewfiscal3">
                                                            <label class="col-md-3 control-label">Fiscal Department #2</label>
                                                            <div class="col-md-3">
                                                                <select class="form-control select2" id="department2" name="department2" onkeypress="return keysub(this, event);" 
                                                                        placeholder="...">
                                                                    <%for (int i = 0; i < array_nc_department.size(); i++) {
                                                                    %>
                                                                    <option value="<%=array_nc_department.get(i)[0]%>"><%=array_nc_department.get(i)[1]%></option>
                                                                    <%}%>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <hr>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Header</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control"  id="headre" name="headre" 
                                                               onkeypress="return keysub(this, event);" maxlength="100"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Text</label>
                                                    <div class="col-md-9">
                                                        <textarea type="text" rows="10" class="form-control" id="textre" name="textre" maxlength="1000"></textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <%} else {%>
                                            <div id="ip2C">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Accounting Code #1</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" id="esolv1" name="esolv1" onkeypress="return keysub(this, event);"  maxlength="10" /> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Accounting Code #2</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" id="esolv2" name="esolv2" onkeypress="return keysub(this, event);"  maxlength="10" /> 
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Header</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" id="headre" name="headre" onkeypress="return keysub(this, event);" maxlength="100"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Receipt Text</label>
                                                    <div class="col-md-9">
                                                        <textarea type="text" rows="10" class="form-control" id="textre" name="textre" maxlength="1000"></textarea>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Fiscal Receipt</label>
                                                    <div class="col-md-3">
                                                        <input type="checkbox" class="make-switch"
                                                               id="regi" name="regi" onkeypress="return keysub(this, event);" onchange="return viewfiscal()"
                                                               data-size="normal" data-on-text="Enabled" data-off-text="Disabled"
                                                               data-on-color="success" data-off-color="danger"
                                                               />
                                                    </div>
                                                    <div id="viewfiscal">
                                                        <label class="col-md-3 control-label">Fiscal Department</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="department" name="department" onkeypress="return keysub(this, event);" 
                                                                    placeholder="...">
                                                                <%for (int i = 0; i < array_nc_department.size(); i++) {
                                                                %>
                                                                <option value="<%=array_nc_department.get(i)[0]%>"><%=array_nc_department.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}%>

                    <!-- BEGIN CONTENT -->

                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->


                </div>
                <!-- END CONTENT -->
                <!-- BEGIN QUICK SIDEBAR -->
                <!-- END QUICK SIDEBAR -->
            </div>
            <!-- END CONTAINER -->
            <!-- BEGIN FOOTER -->
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
        <!-- END PAGE LEVEL PLUGINS -->
        <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>

        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN PAGE LEVEL SCRIPTS -->
        <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
        <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
        <script src="assets/soop/js/form-input-mask.min.js" type="text/javascript"></script>

        <!-- END PAGE LEVEL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
        <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
        <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>

        <!-- END PAGE LEVEL PLUGINS -->

        <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
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
