<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.entity.NC_causal"%>
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
            function checkdescr() {
                var descr = document.getElementById("descr").value.trim();
                if (descr === "") {
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
                        document.getElementById('paym0').style.display = '';
                    } else {
                        if (kind.value === '3') {
                            document.getElementById('btcnc0').style.display = '';
                        } else {
                            document.getElementById('btcnc0').style.display = 'none';
                        }

                        if (kind.value === '4') {
                            document.getElementById('paym0').style.display = '';
                        } else {
                            document.getElementById('paym0').style.display = 'none';
                        }

                        document.getElementById('tf1').style.display = 'none';

                    }
                }
            }

            function loadpage() {
                controllakind();
                inputvirgola();
            }
        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return loadpage();">
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
        <div class="page-container">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
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
                        } else if (esito.equals("koins")) {
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
                        ArrayList<String[]> kind_payment = Engine.kind_payment();
                        NC_causal nc = Engine.getNC_causal(request.getParameter("nc_code"));
                        if (nc != null) {
                            String status = "";
                            if (nc.getAnnullato().equals("0")) {
                                status = "checked";
                            }
                            NC_category ncc = Engine.getNC_category(nc.getGruppo_nc());
                            String cat = ncc.getDe_gruppo_nc();
                            String price = ncc.getIp_prezzo_nc();
                            String tickfee = ncc.getTicket_fee();
                            String maxtick = ncc.getMax_ticket();
                            String tty = "";
                            if (ncc.getTicket_fee_type().equals("1")) {
                                tty = "checked";
                            }

                            String bns = "";
                            if (nc.getBonus().equals("1")) {
                                bns = "checked";
                            }

                            String btc = "";
                            if (nc.getFg_batch().equals("1")) {
                                btc = "checked";
                            }

                            String sct = "";
                            if (nc.getFg_scontrino().equals("1")) {
                                sct = "checked";
                            }

                            String pm = "";
                            if (nc.getPaymat().equals("1")) {
                                pm = "checked";
                            }

                            String dr = "";
                            if (nc.getDocric().equals("1")) {
                                dr = "checked";
                            }

                            ArrayList<String[]> nc_causal_payment = Engine.nc_causal_payment(nc.getFiliale(), nc.getGruppo_nc(), nc.getCausale_nc());

                            ArrayList<String[]> array_nc_descr = Engine.list_nc_descr(nc.getFg_tipo_transazione_nc());

                            if (view.equals("1")) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" >
                        <input type="hidden" id="kind0" name="tipo_transazione_nc" value="<%=Utility.sanitize(nc.getFg_tipo_transazione_nc())%>"/>
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">View Causal</span>
                                        </div>
                                        <div class="tools"> 
                                        </div>
                                    </div>
                                    <div class="portlet-body">




                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Basic </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> Payment Mode </a>
                                            </li>

                                        </ul>

                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Status</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox"  class="make-switch" <%=status%> readonly="readonly"
                                                                   name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Kind</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" readonly="readonly"
                                                                   value="<%=Utility.sanitize(Utility.formatAL(nc.getFg_tipo_transazione_nc(), array_nc_kind, 1))%>" 
                                                                   readonly="readonly"/> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Category</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control " value="<%=Utility.sanitize(nc.getGruppo_nc()) + " - " + Utility.sanitize(cat)%>" readonly="readonly"/> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Id Causal</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control " value="<%=Utility.sanitize(nc.getCausale_nc())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description</label>
                                                        <div class="col-md-9">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <select class="form-control select2" readonly="readonly" onkeypress="return keysub(this, event);" id="descrtype" name="descrtype" placeholder="...">
                                                                        <%for (int i = 0; i < array_nc_descr.size(); i++) {
                                                                                if (array_nc_descr.get(i)[0].equals(nc.getNc_de())) {%>
                                                                        <option value="<%=Utility.sanitize(array_nc_descr.get(i)[0])%>" selected="selected"><%=Utility.sanitize(array_nc_descr.get(i)[1])%></option>
                                                                        <%} else {%>
                                                                        <option value="<%=Utility.sanitize(array_nc_descr.get(i)[0])%>"><%=Utility.sanitize(array_nc_descr.get(i)[1])%></option>
                                                                        <% }
                                                                            }%>
                                                                    </select>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <input type="text" class="form-control " readonly="readonly" onkeypress="return keysub(this, event);" id="descr" name="descr" 
                                                                           value="<%=Utility.sanitize(nc.getDe_causale_nc())%>"> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Price</label>
                                                        <div class="col-md-9">
                                                            <% if (price.equals("0.00")) {%>
                                                            <input type="text" class="form-control " id="price" name="price" 
                                                                   onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                                   onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(nc.getIp_prezzo_nc()))%>"> 
                                                            <%} else {%>
                                                            <input readonly="readonly" type="text" class="form-control "  id="price" name="price"  
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(nc.getIp_prezzo_nc()))%>" > 
                                                            <%}%>
                                                        </div>
                                                    </div>
                                                    <!--
                                                        <div class="form-group">
                                                        <label class="col-md-3 control-label">In/Out Transaction</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" disabled="disabled"
                                                                   name="locfig" value="//=Utility.formatAL(nc.getFg_in_out(), array_nc_inout, 1)" > 
                                                        </div>
                                                    </div>
                                                    -->
                                                    <div id="tf1">
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label">Ticket Fee</label>
                                                            <div class="col-md-9">
                                                                <% if (tickfee.equals("0.00") || tickfee.equals("0,00")) {%>
                                                                <div class="row">
                                                                    <div class="col-md-3">
                                                                        <input type="text" class="form-control inputright" id="tfee" name="tfee"
                                                                               onkeypress="return keysub(this, event);" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(nc.getTicket_fee()))%>"> 
                                                                    </div>
                                                                    <div class="col-md-6">
                                                                        <input type="checkbox" class="make-switch" <%=tty%>
                                                                               id="titype" name="titype" onkeypress="return keysub(this, event);"
                                                                               data-size="normal" readonly="readonly"
                                                                               data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                               data-off-text="<span class='tabnow'>Currency</span>"
                                                                               >
                                                                    </div>
                                                                </div>
                                                                <%} else {%>

                                                                <div class="row">
                                                                    <div class="col-md-3">
                                                                        <input type="text" class="form-control inputright" id="tfee" name="tfee" readonly="readonly"
                                                                               onkeypress="return keysub(this, event);" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(tickfee))%>"> 
                                                                    </div>
                                                                    <div class="col-md-6">
                                                                        <input type="checkbox" class="make-switch" <%=tty%> readonly="readonly"
                                                                               id="titype" name="titype" onkeypress="return keysub(this, event);"
                                                                               data-size="normal" readonly="readonly"
                                                                               data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                               data-off-text="<span class='tabnow'>Currency</span>"
                                                                               >
                                                                    </div>
                                                                </div>
                                                                <%}%>
                                                            </div>
                                                        </div>   
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label"># Max Ticket</label>
                                                            <div class="col-md-9">
                                                                <% if (maxtick.equals("0") || maxtick.equals("")) {%>
                                                                <input type="text" class="form-control" id="maxti" readonly="readonly" 
                                                                       name="maxti" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(nc.getMax_ticket())%>"> 
                                                                <%} else {%>
                                                                <input type="text" class="form-control" id="maxti" name="maxti" onkeypress="return keysub(this, event);"
                                                                       value="<%=Utility.sanitize(maxtick)%>" readonly="readonly"> 
                                                                <%}%>
                                                            </div>
                                                        </div>   
                                                    </div>    
                                                    <div class="form-group" id="paym0" style="display: none;">
                                                        <label class="col-md-3 control-label">External Services</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=pm%> readonly="readonly"
                                                                   id="paymat" name="paymat" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="<span class='tabnow'>Yes</span>" 
                                                                   data-off-text="<span class='tabnow'>No</span>"
                                                                   data-on-color="success" data-off-color="danger"

                                                                   >
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Bonus</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=bns%> readonly="readonly"
                                                                   id="bonus" name="bonus" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>

                                                    <div class="form-group" id="btcnc0" style="display: none;">
                                                        <label class="col-md-3 control-label">Batch Change</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=btc%>
                                                                   id="batch" name="batch" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="<span class='tabnow'>Batch Tax Refund</span>" data-off-text="<span class='tabnow'>No Batch</span>"
                                                                   >
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Print Receipt</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=sct%> readonly="readonly"
                                                                   id="print" name="print" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Identity Document</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=dr%> readonly="readonly"
                                                                   id="dri" name="dri" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-body">
                                                    <%for (int i = 0; i < kind_payment.size(); i++) {
                                                            String enkp = "";
                                                            for (int j = 0; j < nc_causal_payment.size(); j++) {
                                                                if (nc_causal_payment.get(j)[0].equals(kind_payment.get(i)[0])) {
                                                                    if (nc_causal_payment.get(j)[1].equals("0")) {
                                                                        enkp = "checked";
                                                                    }
                                                                }
                                                            }
                                                    %>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label"><%=kind_payment.get(i)[1]%></label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox"  class="make-switch" <%=enkp%> readonly="readonly"
                                                                   name="status_<%=kind_payment.get(i)[0]%>" id="status_<%=kind_payment.get(i)[0]%>"
                                                                   data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else {

                    %>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_causal" onsubmit="return checkdescr();">
                        <input type="hidden" id="kind0" name="tipo_transazione_nc" value="<%=Utility.sanitize(nc.getFg_tipo_transazione_nc())%>"/>
                        <input type="hidden" value="<%=Utility.sanitize(nc.getFg_tipo_transazione_nc())%>"/>
                        <input type="hidden" name="gruppo_nc" value="<%=Utility.sanitize(nc.getGruppo_nc())%>" />
                        <input type="hidden" name="cod" value="<%=Utility.sanitize(nc.getCausale_nc())%>" />
                        <div class="row">
                            <div class="col-md-12">



                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit Causal</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>



                                    <div class="portlet-body">
                                        
                                        
                                        
                                        
                                        
                                        
                                        
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

                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Basic </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> Payment Mode </a>
                                            </li>

                                        </ul>

                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Status</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox"  class="make-switch" <%=status%>
                                                                   name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Kind</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" 
                                                                   value="<%=Utility.sanitize(Utility.formatAL(nc.getFg_tipo_transazione_nc(), array_nc_kind, 1))%>" 
                                                                   readonly="readonly"/> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Category</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control " value="<%=Utility.sanitize(nc.getGruppo_nc()) + " - " + Utility.sanitize(cat)%>" readonly="readonly"/> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Id Causal</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control " value="<%=Utility.sanitize(nc.getCausale_nc())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description <span class="font-red">*</span></label>
                                                        <div class="col-md-9">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <select class="form-control select2" onkeypress="return keysub(this, event);" id="descrtype" name="descrtype" placeholder="...">
                                                                        <%for (int i = 0; i < array_nc_descr.size(); i++) {
                                                                                if (array_nc_descr.get(i)[0].equals(nc.getNc_de())) {%>
                                                                        <option value="<%=Utility.sanitize(array_nc_descr.get(i)[0])%>" selected="selected"><%=Utility.sanitize(array_nc_descr.get(i)[1])%></option>
                                                                        <%} else {%>
                                                                        <option value="<%=Utility.sanitize(array_nc_descr.get(i)[0])%>"><%=Utility.sanitize(array_nc_descr.get(i)[1])%></option>
                                                                        <% }
                                                                            }%>
                                                                    </select>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <input type="text" class="form-control " onkeypress="return keysub(this, event);" id="descr" name="descr" 
                                                                           value="<%=Utility.sanitize(nc.getDe_causale_nc())%>"> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Price</label>
                                                        <div class="col-md-9">
                                                            <% if (price.equals("0.00")) {%>
                                                            <input type="text" class="form-control " id="price" name="price" 
                                                                   onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                                   onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(nc.getIp_prezzo_nc()))%>"> 
                                                            <%} else {%>
                                                            <input readonly="readonly" type="text" class="form-control "  id="price" name="price"  
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(nc.getIp_prezzo_nc()))%>" > 
                                                            <%}%>
                                                        </div>
                                                    </div>

                                                    <div id="tf1">
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label">Ticket Fee</label>
                                                            <div class="col-md-9">
                                                                <% if (tickfee.equals("0.00") || tickfee.equals("0,00")) {%>
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
                                                                        <input type="checkbox" class="make-switch" <%=tty%> readonly="readonly"
                                                                               id="titype" name="titype" 
                                                                               onkeypress="return keysub(this, event);"
                                                                               data-size="normal" 
                                                                               data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                               data-off-text="<span class='tabnow'>Currency</span>"
                                                                               />
                                                                    </div>
                                                                </div>
                                                                <%} else {%>
                                                                <div class="row">
                                                                    <div class="col-md-3">
                                                                        <input type="text" class="form-control inputright" name="tfeevalue" id="tfeevalue" readonly="readonly"
                                                                               value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(tickfee))%>"  /> 
                                                                    </div>
                                                                    <div class="col-md-6">
                                                                        <input type="checkbox" class="make-switch" <%=tty%> readonly="readonly"
                                                                               id="titype" name="titype" 
                                                                               onkeypress="return keysub(this, event);"
                                                                               data-size="normal" 
                                                                               data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                               data-off-text="<span class='tabnow'>Currency</span>"
                                                                               />
                                                                    </div>
                                                                </div>
                                                                <%}%>
                                                            </div>
                                                        </div>   
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label"># Max Ticket</label>
                                                            <div class="col-md-9">
                                                                <% if (maxtick.equals("0") || maxtick.equals("")) {%>
                                                                <input type="text" class="form-control" id="maxti" name="maxti" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(nc.getMax_ticket())%>"> 
                                                                <%} else {%>
                                                                <input type="text" class="form-control" id="maxti" name="maxti" onkeypress="return keysub(this, event);"
                                                                       value="<%=Utility.sanitize(maxtick)%>" readonly="readonly"> 
                                                                <%}%>
                                                            </div>
                                                        </div>   
                                                    </div>    
                                                    <div class="form-group" id="paym0" style="display: none;">
                                                        <label class="col-md-3 control-label">External Services</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=pm%>
                                                                   id="paymat" name="paymat" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="<span class='tabnow'>Yes</span>" 
                                                                   data-off-text="<span class='tabnow'>No</span>"
                                                                   data-on-color="success" data-off-color="danger"

                                                                   >
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Bonus</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=bns%>
                                                                   id="bonus" name="bonus" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>

                                                    <div class="form-group" id="btcnc0" style="display: none;">
                                                        <label class="col-md-3 control-label">Batch Change</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=btc%>
                                                                   id="batch" name="batch" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="<span class='tabnow'>Batch Tax Refund</span>" data-off-text="<span class='tabnow'>No Batch</span>"
                                                                   >
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Print Receipt</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=sct%>
                                                                   id="print" name="print" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Identity Document</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=dr%>
                                                                   id="dri" name="dri" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-body">
                                                    <%for (int i = 0; i < kind_payment.size(); i++) {
                                                            String enkp = "";
                                                            for (int j = 0; j < nc_causal_payment.size(); j++) {
                                                                if (nc_causal_payment.get(j)[0].equals(kind_payment.get(i)[0])) {
                                                                    if (nc_causal_payment.get(j)[1].equals("0")) {
                                                                        enkp = "checked";
                                                                    }
                                                                }
                                                            }
                                                    %>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label"><%=kind_payment.get(i)[1]%></label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox"  class="make-switch" <%=enkp%>
                                                                   name="status_<%=kind_payment.get(i)[0]%>" id="status_<%=kind_payment.get(i)[0]%>"
                                                                   data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}
                    } else {
                        NC_category ncc = Engine.getNC_category(request.getParameter("nc_cat"));
                        if (ncc != null) {
                            String price = ncc.getIp_prezzo_nc();
                            String tickfee = ncc.getTicket_fee();
                            String maxtick = ncc.getMax_ticket();
                            if (maxtick == null) {
                                maxtick = "";
                            }

                            String tty = "";
                            if (ncc.getTicket_fee_type().equals("1")) {
                                tty = "checked";
                            }

                            ArrayList<String[]> array_nc_descr = Engine.list_nc_descr(ncc.getFg_tipo_transazione_nc());


                    %>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=ins_causal" onsubmit="return checkdescr();">
                        <input type="hidden" name="gruppo_nc" value="<%=Utility.sanitize(ncc.getGruppo_nc())%>" />
                        <input type="hidden" name="tipo_transazione_nc" value="<%=Utility.sanitize(ncc.getFg_tipo_transazione_nc())%>" />
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">New Causal</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        
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
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Basic </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> Payment Mode </a>
                                            </li>

                                        </ul>

                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Kind</label>
                                                        <div class="col-md-9">
                                                            <input type="hidden" id="kind0" name="kind0" value="<%=Utility.sanitize(ncc.getFg_tipo_transazione_nc())%>"/>
                                                            <input type="text" class="form-control" 
                                                                   onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(Utility.formatAL(ncc.getFg_tipo_transazione_nc(), array_nc_kind, 1))%>" 
                                                                   readonly="readonly"/> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Category</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control"  onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(ncc.getGruppo_nc() + " - " + ncc.getDe_gruppo_nc())%>" readonly="readonly"/> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Id Causal</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" 
                                                                   onkeypress="return keysub(this, event);"  readonly="readonly"/> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description <span class="font-red">*</span></label>
                                                        <div class="col-md-9">
                                                            <div class="row">
                                                                <div class="col-md-4">
                                                                    <select class="form-control select2" 
                                                                            onkeypress="return keysub(this, event);" id="descrtype" name="descrtype" 
                                                                            placeholder="...">
                                                                        <%for (int i = 0; i < array_nc_descr.size(); i++) {


                                                                        %>

                                                                        <option value="<%=Utility.sanitize(array_nc_descr.get(i)[0])%>"><%=Utility.sanitize(array_nc_descr.get(i)[1])%></option>




                                                                        <%}%>
                                                                    </select>
                                                                </div>
                                                                <div class="col-md-8">
                                                                    <input type="text" class="form-control" onkeypress="return keysub(this, event);"
                                                                           id="descr" name="descr"/>                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Price</label>
                                                        <div class="col-md-9">
                                                            <% if (price.equals("0.00")) {%>
                                                            <input type="text" class="form-control" id="price" name="price" 
                                                                   onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                                   onkeypress="return keysub(this, event);"
                                                                   value="<%=startvalue%>"> 
                                                            <%} else {%>
                                                            <input readonly="readonly" type="text" id="price" name="price" 
                                                                   class="form-control" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ncc.getIp_prezzo_nc()))%>"> 
                                                            <%}%>
                                                        </div>
                                                    </div>
                                                    <div id="tf1">
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label">Ticket Fee</label>
                                                            <div class="col-md-9">
                                                                <% if (tickfee.equals("0.00") || tickfee.equals("0,00")) {%>
                                                                <div class="row">
                                                                    <div class="col-md-3">
                                                                        <input type="text" class="form-control inputright" name="tfeevalue" id="tfeevalue" 
                                                                               value="<%=startvalue%>" 
                                                                               <%if (ncc.getTicket_fee_type().equals("1")) {%>
                                                                               onchange="return formatValueDecimal_1_max(document.getElementById(this.id), '100' + separatordecimal + '00', separatorthousand, separatordecimal);"
                                                                               <%} else {%>
                                                                               onchange="return formatValueDecimal_1(document.getElementById(this.id), separatorthousand, separatordecimal);"
                                                                               <%}%>
                                                                               /> 
                                                                    </div>
                                                                    <div class="col-md-6">
                                                                        <input type="checkbox" class="make-switch" <%=tty%> readonly="readonly"
                                                                               id="titype" name="titype" 
                                                                               onkeypress="return keysub(this, event);"
                                                                               data-size="normal" 
                                                                               data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                               data-off-text="<span class='tabnow'>Currency</span>"
                                                                               />
                                                                    </div>
                                                                </div>
                                                                <%} else {%>
                                                                <div class="row">
                                                                    <div class="col-md-3">
                                                                        <input type="text" class="form-control inputright" name="tfeevalue" id="tfeevalue" readonly="readonly"
                                                                               value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(tickfee))%>"  /> 
                                                                    </div>
                                                                    <div class="col-md-6">
                                                                        <input type="checkbox" class="make-switch" <%=tty%> readonly="readonly"
                                                                               id="titype" name="titype" 
                                                                               onkeypress="return keysub(this, event);"
                                                                               data-size="normal" 
                                                                               data-on-text="<span class='tabnow'>Percent %</span>" 
                                                                               data-off-text="<span class='tabnow'>Currency</span>"
                                                                               />
                                                                    </div>
                                                                </div>
                                                                <%}%>
                                                            </div>
                                                        </div>   
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label"># Max Ticket</label>
                                                            <div class="col-md-9">
                                                                <% if (maxtick.equals("0") || maxtick.equals("")) {%>
                                                                <input type="text" class="form-control" id="maxti" name="maxti" onkeypress="return keysub(this, event);"> 
                                                                <%} else {%>
                                                                <input type="text" class="form-control" id="maxti" name="maxti" onkeypress="return keysub(this, event);"
                                                                       value="<%=Utility.sanitize(maxtick)%>" readonly="readonly"> 
                                                                <%}%>
                                                            </div>
                                                        </div>  
                                                    </div> 
                                                    <div class="form-group" id="paym0" style="display: none;">
                                                        <label class="col-md-3 control-label">External Services</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" 
                                                                   id="paymat" name="paymat" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="<span class='tabnow'>Yes</span>" 
                                                                   data-off-text="<span class='tabnow'>No</span>"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Bonus</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch"
                                                                   id="bonus" name="bonus" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>

                                                    <div class="form-group" id="btcnc0" style="display: none;">
                                                        <label class="col-md-3 control-label">Batch Change</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch"
                                                                   id="batch" name="batch" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="<span class='tabnow'>Batch Tax Refund</span>" 
                                                                   data-off-text="<span class='tabnow'>No Batch</span>"/>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Print Receipt</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch"
                                                                   id="print" name="print" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"/>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Identity Document</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" 
                                                                   id="dri" name="dri" onkeypress="return keysub(this, event);"  
                                                                   data-size="normal" data-on-text="Yes" data-off-text="No"
                                                                   data-on-color="success" data-off-color="danger"
                                                                   >
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-body">
                                                    <%for (int i = 0; i < kind_payment.size(); i++) {%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label"><%=kind_payment.get(i)[1]%></label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox"  class="make-switch" 
                                                                   name="status_<%=kind_payment.get(i)[0]%>" id="status_<%=kind_payment.get(i)[0]%>" 
                                                                   data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div> 
                        </div> 
                    </form>


                    <%} else {

                        }
                    %>

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
            <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>

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
