<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Mac2.0</title>
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



        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>

        <script type="text/javascript">
            var separatordecimal = '<%=Constant.decimal%>';
            var separatorthousand = '<%=Constant.thousand%>';
            function checkdescr() {
                var descr = document.getElementById("descr").value.trim();
                var br_code = document.getElementById("br_code").value.trim();
                if (descr === "" || br_code === "") {
                    var ermsg = "You must complete field with <span class='font-red'>*</span>.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
            }
        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return inputvirgola();">
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
                        ArrayList<String[]> tipogruppi = Engine.list_group_branch();
                        ArrayList<String[]> gruppi = Engine.list_branch_group();

                        String view = request.getParameter("view");
                        if (view == null) {
                            view = "0";
                        }

                        String br_code = request.getParameter("br_code");
                        Branch br = null;
                        if (br_code != null) {
                            br = Engine.get_branch(br_code);
                        }
                        if (br != null) {

                            String codATL = Engine.get_branch_ATL(br_code);

                            String ch = "";
                            if (br.getFg_persgiur().equals("1")) {
                                ch = "checked";
                            }
                            String sta = "";
                            if (br.getFg_annullato().equals("0")) {
                                sta = "checked";
                            }
                            String modra = "";
                            if (br.getFg_modrate().equals("1")) {
                                modra = "checked";
                            }

                            String crm = "";
                            if (br.getFg_crm().equals("1")) {
                                crm = "checked";
                            }
                            String age = "";
                            if (br.getFg_agency().equals("1")) {
                                age = "checked";
                            }
                            String pad = "";
                            if (br.getFg_pad().equals("1")) {
                                pad = "checked";
                            }

                            if (view.equals("1")) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" > 
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">View Branch</span>
                                        </div>
                                        <div class="tools"> 

                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> General </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3" data-toggle="tab"> Groups </a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade " id="tab_1_3">
                                                <div class="form-body">
                                                    <%for (int x = 0; x < tipogruppi.size(); x++) {%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label"><%=tipogruppi.get(x)[1]%></label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" name="brgr_<%=tipogruppi.get(x)[0]%>" disabled="disabled">
                                                                <option value=""></option>
                                                                <% 
                                                                for (int j = 0; j < gruppi.size(); j++) {
                                                                    if (br.getListagruppi() != null && gruppi.get(j)[2] != null && tipogruppi.get(x)[0] != null && gruppi.get(j)[2].equals(tipogruppi.get(x)[0])) {
                                                                        String select = "";
                                                                        if (br.getListagruppi().get(gruppi.get(j)[2]).equals(gruppi.get(j)[0])) {
                                                                            select = "selected";
                                                                        }
                                                                    }
                                                                }
                                                                %>
                                                                
                                                                <option <%=select%> value="<%=gruppi.get(j)[0]%>">
                                                                    <%=gruppi.get(j)[0]%> - <%=gruppi.get(j)[1]%>
                                                                </option>
                                                                <%}%>
                                                                <%}%>

                                                            </select>

                                                        </div>
                                                    </div>
                                                    <%}%>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" name="br_code" value="<%=Utility.sanitize(br.getCod())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="descr" id="descr" onkeypress="return keysub(this, event);" readonly="readonly"
                                                                   value="<%=Utility.sanitize(br.getDe_branch())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="addr" id="addr" onkeypress="return keysub(this, event);" readonly="readonly"
                                                                   value="<%=Utility.sanitize(br.getAdd_via())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">City</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="city" id="city" onkeypress="return keysub(this, event);" readonly="readonly"
                                                                   value="<%=Utility.sanitize(br.getAdd_city())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="zipc" id="zipc" onkeypress="return keysub(this, event);" readonly="readonly"
                                                                   value="<%=Utility.sanitize(br.getAdd_cap())%>"> 
                                                        </div>
                                                    </div>
                                                    <%if (Constant.is_CZ) {%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Opening Hours</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control"  name="otime" id="otime" maxlength="100"
                                                                   onkeypress="return keysub(this, event);" readonly="readonly"
                                                                   value="<%=Utility.sanitize(br.getOpening())%>"> 
                                                        </div>
                                                    </div>
                                                    <%}%>

                                                    <%if (Constant.is_IT) {%>    
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Company Operation</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=ch%> readonly="readonly"
                                                                   name="comp" id="comp" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Agency Operation</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=age%> readonly="readonly"
                                                                   name="age" id="age" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Edit Rate</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=modra%> readonly="readonly"
                                                                   name="modra" id="modra" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">CRM</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=crm%> readonly="readonly"
                                                                   name="crm" id="crm" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                        </div>
                                                    </div>
                                                    <%if (Constant.is_IT) {%> 
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Signature Tab</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=pad%> readonly="readonly"
                                                                   name="pad" id="pad" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Date Activation</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="datea" id="datea" onkeypress="return keysub(this, event);" readonly="readonly"
                                                                   value="<%=Utility.sanitize(br.getDt_start())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Limit Insurance</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="datea" id="datea" onkeypress="return keysub(this, event);"
                                                                   readonly="readonly"
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(br.getMax_ass()))%>"> 
                                                        </div>
                                                    </div>
                                                    <%if (Constant.is_IT) {%> 
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Target</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="target" id="target" onkeypress="return keysub(this, event);"
                                                                   readonly="readonly"
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(br.getTarget()))%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code <u>Atlante</u></label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" 
                                                                   id="atl_code" name="atl_code"
                                                                   onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(codATL)%>" 
                                                                   readonly="readonly"> 
                                                        </div>
                                                    </div>    
                                                    <%}%>



                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Status</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=sta%> readonly="readonly"
                                                                   name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
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
                    <%} else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_branch" onsubmit="return checkdescr();"> 
                        <input type="hidden" name="oldstatus" value="<%=Utility.sanitize(br.getFg_annullato())%>"/>
                        <input type="hidden" name="datecanc" value="<%=Utility.sanitize(br.getDa_annull())%>"/>
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit Branch</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">

                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> General </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3" data-toggle="tab"> Groups </a>
                                            </li>
                                            <!--
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> External Services </a>
                                            </li>
                                            -->
                                        </ul>

                                        <div class="tab-content">

                                            <div class="tab-pane fade " id="tab_1_3">
                                                <div class="form-body">
                                                    <%for (int x = 0; x < tipogruppi.size(); x++) {%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label"><%=tipogruppi.get(x)[1]%></label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" name="brgr_<%=tipogruppi.get(x)[0]%>">
                                                                <option value=""></option>
                                                                <%for (int j = 0; j < gruppi.size(); j++) {
                                                                        if (gruppi.get(j)[2].equals(tipogruppi.get(x)[0])) {
                                                                            String select = "";
                                                                            if (br.getListagruppi().get(gruppi.get(j)[2]).equals(gruppi.get(j)[0])) {
                                                                                select = "selected";
                                                                            }%>
                                                                <option <%=select%> value="<%=gruppi.get(j)[0]%>">
                                                                    <%=gruppi.get(j)[0]%> - <%=gruppi.get(j)[1]%>
                                                                </option>
                                                                <%}%>
                                                                <%}%>

                                                            </select>

                                                        </div>
                                                    </div>
                                                    <%}%>
                                                </div>


                                            </div>

                                            <div class="tab-pane fade active in" id="tab_1_1">

                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" 
                                                                   name="br_code" value="<%=Utility.sanitize(br.getCod())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="descr" id="descr" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getDe_branch())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="addr" id="addr" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(br.getAdd_via())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">City</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="city" id="city" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getAdd_city())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="zipc" id="zipc" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getAdd_cap())%>"> 
                                                        </div>
                                                    </div>
                                                    <%if (Constant.is_CZ) {%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Opening Hours</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="otime" id="otime" maxlength="100"
                                                                   onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getOpening())%>" /> 
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                    <%if (Constant.is_IT) {%> 
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Company Operation</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=ch%>
                                                                   name="comp" id="comp" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Agency Operation</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=age%> 
                                                                   name="age" id="age" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Edit Rate</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=modra%>
                                                                   name="modra" id="modra" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">CRM</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=crm%>
                                                                   name="crm" id="crm" onkeypress="return keysub(this, event);" data-size="normal" 
                                                                   data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                        </div>
                                                    </div>
                                                    <%if (Constant.is_IT) {%> 
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Signature Tab</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=pad%>
                                                                   name="pad" id="pad" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Date Activation</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control date-picker" name="datea" id="datea" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getDt_start())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Limit Insurance</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="max_ass" id="max_ass" onkeypress="return keysub(this, event);"
                                                                   onchange="return formatValueDecimal_1(this, separatorthousand, separatordecimal);"
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(br.getMax_ass()))%>"> 
                                                        </div>
                                                    </div>
                                                    <%if (Constant.is_IT) {%> 
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Target</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="target" id="target" onkeypress="return keysub(this, event);"
                                                                   onchange="return formatValueDecimal_1(this, separatorthousand, separatordecimal);"
                                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(br.getTarget()))%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code <u>Atlante</u></label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" 
                                                                   id="atl_code" name="atl_code"
                                                                   onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(codATL)%>" /> 
                                                        </div>
                                                    </div>
                                                    <%}%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Status</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=sta%>
                                                                   name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="tab-pane fade " id="tab_1_2">
                                                <%if (Constant.is_IT) {%> 
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <span class="col-md-3 control-label font-blue bold h4">OLTA</span>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">User</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="oltauser" id="oltauser" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getOlta_user())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Password</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="oltapass" id="oltapass" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getOlta_pass())%>"> 
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="form-group">
                                                        <span class="col-md-3 control-label font-blue-dark bold h4">PAYMAT</span>
                                                    </div>
                                                    <h4></h4>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Nome Azienda</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="paymat1" id="paymat1" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getPay_nomeazienda())%>"> 
                                                        </div>
                                                    </div>   
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Id Azienda</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="paymat2" id="paymat2" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getPay_idazienda())%>"> 
                                                        </div>
                                                    </div>   
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Skin</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="paymat3" id="paymat3" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getPay_skin())%>"> 
                                                        </div>
                                                    </div>   
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">User/Caller</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="paymat4" id="paymat4" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getPay_user())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Secret Key/Passwod</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="paymat5" id="paymat5" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getPay_password())%>"> 
                                                        </div>
                                                    </div> 
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Token</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="paymat6" id="paymat6" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getPay_token())%>"> 
                                                        </div>
                                                    </div>   
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Terminale/POS Esercente</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="paymat7" id="paymat7" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(br.getPay_terminale())%>"> 
                                                        </div>
                                                    </div>   
                                                </div>
                                                <%} else {%>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="alert alert-danger">
                                                            <strong>This feature is not available in your country.</strong>
                                                        </div>
                                                    </div>
                                                </div>
                                                <%}%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}
                    } else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=ins_branch" onsubmit="return checkdescr();"> 
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">New Branch</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">

                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> General </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3" data-toggle="tab"> Groups </a>
                                            </li>
                                        </ul>

                                        <div class="tab-content">

                                            <div class="tab-pane fade " id="tab_1_3">

                                                <div class="form-body">
                                                    <%for (int x = 0; x < tipogruppi.size(); x++) {%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label"><%=tipogruppi.get(x)[1]%></label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" name="brgr_<%=tipogruppi.get(x)[0]%>">
                                                                <option value=""></option>
                                                                <%for (int j = 0; j < gruppi.size(); j++) {
                                                                        if (gruppi.get(j)[2].equals(tipogruppi.get(x)[0])) {
                                                                %>
                                                                <option  value="<%=gruppi.get(j)[0]%>">
                                                                    <%=gruppi.get(j)[0]%> - <%=gruppi.get(j)[1]%>
                                                                </option>
                                                                <%}%>
                                                                <%}%>

                                                            </select>

                                                        </div>
                                                    </div>
                                                    <%}%>
                                                </div>

                                            </div>
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Code <span class="font-red">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control uppercase"
                                                               id="br_code" name="br_code" onchange="return fieldOnlyNumber(this.id);"
                                                               maxlength="3"
                                                               onkeypress="return keysub(this, event);"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Description <span class="font-red">*</span></label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control"  name="descr" id="descr" onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Address</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control"  name="addr" id="addr" onkeypress="return keysub(this, event);"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">City</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" name="city" id="city" onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Zip Code</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control"  name="zipc" id="zipc" onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                </div>
                                                <%if (Constant.is_CZ) {%>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Opening Hours</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" name="otime" id="otime" maxlength="100"
                                                               onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                </div>
                                                <%}%>
                                                <%if (Constant.is_IT) {%> 
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Company Operation</label>
                                                    <div class="col-md-9">
                                                        <input type="checkbox" class="make-switch" name="comp" id="comp" onkeypress="return keysub(this, event);"
                                                               id="foreign" data-size="normal" data-on-color="success" data-off-color="danger"
                                                               data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Agency Operation</label>
                                                    <div class="col-md-9">
                                                        <input type="checkbox" class="make-switch"
                                                               name="age" id="age" onkeypress="return keysub(this, event);" data-size="normal" 
                                                               data-on-color="success" data-off-color="danger"
                                                               data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                    </div>
                                                </div>
                                                <%}%>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Edit Rate</label>
                                                    <div class="col-md-9">
                                                        <input type="checkbox" class="make-switch"
                                                               name="modra" id="modra" onkeypress="return keysub(this, event);" data-size="normal" 
                                                               data-on-color="success" data-off-color="danger"
                                                               data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">CRM</label>
                                                    <div class="col-md-9">
                                                        <input type="checkbox" class="make-switch" name="crm" id="crm" onkeypress="return keysub(this, event);" data-size="normal" 
                                                               data-on-color="success" data-off-color="danger"
                                                               data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                    </div>
                                                </div>
                                                <%if (Constant.is_IT) {%> 
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Signature Tab</label>
                                                    <div class="col-md-9">
                                                        <input type="checkbox" class="make-switch"
                                                               name="pad" id="pad" data-size="normal" data-on-color="success" data-off-color="danger"
                                                               data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                    </div>
                                                </div>
                                                <%}%>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Date Activation</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control date-picker" name="datea" id="datea" onkeypress="return keysub(this, event);" /> 
                                                    </div>
                                                </div><div class="form-group">
                                                    <label class="col-md-3 control-label">Limit Insurance</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" name="max_ass" id="max_ass" onkeypress="return keysub(this, event);"
                                                               onchange="return formatValueDecimal_1(this, separatorthousand, separatordecimal);" value="0<%=Constant.decimal%>00"> 
                                                    </div>
                                                </div>
                                                <%if (Constant.is_IT) {%> 
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Target</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" name="target" id="target" onkeypress="return keysub(this, event);"
                                                               onchange="return formatValueDecimal_1(this, separatorthousand, separatordecimal);" value="0<%=Constant.decimal%>00"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Code <u>Atlante</u></label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" 
                                                               id="atl_code" name="atl_code"
                                                               onkeypress="return keysub(this, event);" /> 
                                                    </div>
                                                </div>
                                                <%}%>


                                            </div>
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

            <!-- END FOOTER -->
            <!--[if lt IE 9]>
    <script src="../assets/global/plugins/respond.min.js"></script>
    <script src="../assets/global/plugins/excanvas.min.js"></script> 
    <![endif]-->
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
