<%@page import="rc.so.entity.NC_causal"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.NC_category"%>
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
    if(link_value!=null){
        Utility.redirect(request, response,link_value);
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
        <title>Mac2.0 - Int. Tr. NC</title>
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
            ArrayList<String[]> array_till = Engine.list_till_enabled();
            ArrayList<Till> array_till_open = Engine.list_till_status("O", "");

            ArrayList<NC_category> array_nc = Engine.all_nc_category();
            //ArrayList<NC_causal> array_nc = Engine.all_nc_causal();
                boolean iscentral = Engine.isCentral();
                
        %>

        <script src="assets/soop/js/pace.js" type="text/javascript"></script>
        
        
        
        <link rel="stylesheet" href="assets/soop/css/pace-theme-center-circle.css" />
        
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/core-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5-min.js" type="text/javascript"></script>
        <script src="assets/soop/js/md5.js" type="text/javascript"></script>
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>

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
                //$('#tillto').val($('#tillto' + ' option:first-child').val()).trigger('change');
                $('#tillto').val('').trigger('change');
            }

            function changetill_to() {
                document.getElementById('idopentillto').value ='';
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

            function cli(usr) {
                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                document.getElementById("errorlarge").style.display = "block";
                document.getElementById("errorlargetext").innerHTML = "Warning! Operation not permitted. The operation of the same type is in progress by the operator " + usr + ".<p class='ab'></p> Please wait for the end of this operation.";
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
            
            function subform() {
                document.getElementById('butconf').disabled = true;
                $("#butconf").html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");
                
                $('table').each(function () {
                    if ($.fn.dataTable.fnIsDataTable(this)) {
                        $(this).DataTable().search('').draw();
                    }
                });
                
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
            <%@ include file="menu/menu_tr5.jsp"%>
            <!-- END MENU -->
            <%                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                String pswx = session.getAttribute("us_pwd").toString();
                String usr = Engine.isBlockedOperation();
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
                            <h3 class="page-title">Transaction <small><b>Internal Transfer No Change</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
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
                        </div>
                    </div>
                    <hr>
                    <%}%>
                    
                    
                     <%if(!iscentral){%>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
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
                            <a href="it_nochange.jsp" class="btn btn-outline blue"><i class="fa fa-refresh"></i> Refresh</a>
                        </div>
                    </div>
                    <%} else{%>
                    <%if (request.getParameter("search") == null) {%>
                    <form name="f1" id="f1" method="post" action="it_nochange.jsp" onsubmit="return checktill();">
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
                        String tillfrom = request.getParameter("tillfrom");
                        String tillto = request.getParameter("tillto");
                        boolean es = Engine.insertBlockedOperation(session);
                        if (es) {
                    %>
                    <form name="f2" id="f2" method="post" action="Operazioni?type=it_nochange" onsubmit="return subform();">

                        <input type="hidden" name="idopentillfrom" value="<%=Utility.sanitize(idopentillfrom)%>"/>
                        <input type="hidden" name="idopentillto" value="<%=Utility.sanitize(idopentillto)%>"/>
                        <input type="hidden" name="tillfrom" value="<%=Utility.sanitize(tillfrom)%>"/>
                        <input type="hidden" name="tillto" value="<%=Utility.sanitize(tillto)%>"/>

                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>From</label>
                                    <input type="text" class="form-control" readonly="readonly" name="tillfrom" value="<%=Utility.sanitize(Utility.formatAL(tillfrom, array_till, 1))%>"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>To</label>
                                    <input type="text" class="form-control" readonly="readonly" name="tillto" value="<%=Utility.sanitize(Utility.formatAL(tillto, array_till, 1))%>"/>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label>&nbsp;</label><p class='ab'></p>
                                    <a href="Operazioni?type=unlock_it_et&so=it_nochange.jsp" class="btn btn-outline red"><i class="fa fa-remove"></i> Cancel Operation</a>
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
                        <%

                            ArrayList<String[]> list_oc_nochange = Engine.list_oc_nochange_real(idopentillfrom);
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
                                                                        <td id="trnoc_td1_<%=i%>"><%=Utility.sanitize(list_oc_nochange.get(i)[1]%> - <%=Utility.sanitize(Engine.formatALNC_category(list_oc_nochange.get(i)[1], array_nc))%>
                                                                        </td>
                                                                <input type="hidden" name="nc_causal<%=i%>" value="<%=Utility.sanitize(list_oc_nochange.get(i)[1])%>"/>
                                                                <td>
                                                                    <span style="text-align: right;float: right;" id="nc_quantold<%=i%>"><%=Utility.sanitize(Utility.formatMysqltoDisplay(list_oc_nochange.get(i)[2]))%></span>
                                                                    <input type="hidden" name="nc_quantold<%=i%>" value="<%=Utility.sanitize(list_oc_nochange.get(i)[2])%>"/>
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control inputright" 
                                                                           id="nc_quantnow<%=i%>" name="nc_quantnow<%=i%>" value="0" 
                                                                           onkeypress="return keysub(this, event);"
                                                                           onchange="return formatValueINT_1_change(this,separatorthousand,separatordecimal);">
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
                        <%if (list_oc_nochange.size() > 0) {%>
                        <div class="row">
                            <div class="col-md-12">
                                <center><button type="submit" class="btn btn-lg green btn-block" id="butconf"><i class="fa fa-save"></i> Confirm Transaction</button></center>
                            </div>                            
                        </div>



                        <%}%>
                    </form>
                    <%}
                        }
                    }%>
                    
                    <%}else{%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>000 - Head Office can not perform transaction operation.</strong>
                            </div>
                        </div>
                    </div>
                    <%}%>
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


                <!-- END THEME LAYOUT SCRIPTS -->
                <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
                <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
                <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>




                <!-- BEGIN THEME GLOBAL SCRIPTS -->
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
                                                                                                   {orderable: !1, targets: [2]}
                                                                                               ],
                                                                                               lengthMenu: [[-1], ["All"]],
                                                                                               pageLength: -1,
                                                                                               order: [],
                                                                                               dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'><'col-md-6 col-sm-12'f>r><t>"
                                                                                           });
                                                                                       };

                                                                                       jQuery().dataTable && dt1();
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
