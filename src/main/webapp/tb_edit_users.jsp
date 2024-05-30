<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.Users"%>
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
        <script src="assets/soop/js/validate.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            function checkpass() {
                var newpass = document.getElementById("newpass").value.trim();
                if (newpass === "") {
                    var ermsg = "Field 'New password' cannot be blank.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

            }
            function checkdescr() {



                var surname = document.getElementById("surname").value.trim();
                var name = document.getElementById("name").value.trim();

                if (surname === "" || name === "") {
                    var ermsg = "You must complete fields with <span class='font-red'>*</span>.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

                var email = document.getElementById("mail").value.trim();
                if (email !== "") {
                    var v1 = validate.single(email, {presence: true, email: true});
                    if (undefined !== v1) {
                        var ermsg = "Field 'Email address' is incorrect.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }



            }




        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return inputvirgola();">
        <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-2" role="dialog" aria-hidden="true">
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
                        } else if (esito.equals("okP")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully. The user password has been reset.";

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

                        ArrayList<String[]> valid = Users.listValidity();
                        ArrayList<String[]> listtype = Users.listTypeuser();
                        ArrayList<String[]> liststatus = Users.listStatususer();

                        String us_code = request.getParameter("us_code");
                        Users us = Engine.get_user(us_code);
                        if (us != null) {
                            String codATL = Engine.get_user_ATL(us_code);
                            if (view.equals("1")) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">View User</span>
                                        </div>
                                        <div class="tools"> 

                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" 
                                                           id="us_code" name="us_code" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(us.getCod())%>" 
                                                           readonly="readonly"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Username</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.sanitize(us.getUsername())%>" readonly="readonly"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Surname</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" readonly="readonly"
                                                           onkeypress="return keysub(this, event);" id="surname" name="surname" 
                                                           onkeyup="return fieldNameSurname(this.id);" value="<%=Utility.sanitize(us.getDe_cognome())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Name </label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" readonly="readonly"
                                                           onkeypress="return keysub(this, event);" id="name" name="name" 
                                                           onkeyup="return fieldNameSurname(this.id);" value="<%=Utility.sanitize(us.getDe_nome())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Type</label>
                                                <div class="col-md-9">
                                                    <input type="hidden" id="typeuser" value="<%=Utility.sanitize(us.getFg_tipo())%>"/>
                                                    <%for (int i = 0; i < listtype.size(); i++) {
                                                            if (listtype.get(i)[0].equals(us.getFg_tipo())) {%>
                                                    <input type="text" class="form-control" readonly="readonly"
                                                           onkeypress="return keysub(this, event);"
                                                           onkeyup="return fieldNameSurname(this.id);" value="<%=listtype.get(i)[1]%>"/> 
                                                    <%} else {%>
                                                    <%}%>
                                                    <%}%>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Validity Password (Day)</label>
                                                <div class="col-md-9">
                                                    <input type="hidden" id="valid" value="<%=Utility.sanitize(us.getValidita())%>"/>
                                                    <%for (int i = 0; i < valid.size(); i++) {
                                                            if (valid.get(i)[0].equals(us.getValidita())) {%>
                                                    <input type="text" class="form-control" readonly="readonly"
                                                           onkeypress="return keysub(this, event);"
                                                           onkeyup="return fieldNameSurname(this.id);" value="<%=valid.get(i)[1]%>"/> 
                                                    <%} else {%>
                                                    <%}%>
                                                    <%}%>


                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Accounting Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" 
                                                           readonly="readonly"
                                                           value="<%=Utility.sanitize(us.getConto())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Email address</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" readonly="readonly"
                                                           value="<%=Utility.sanitize(us.getEmail())%>"> 
                                                </div>
                                            </div>
                                            <%if (Constant.is_IT) {%>
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

                                                    <input type="hidden" id="status" value="<%=Utility.sanitize(us.getFg_stato())%>"/>
                                                    <%for (int i = 0; i < liststatus.size(); i++) {
                                                            if (liststatus.get(i)[0].equals(us.getFg_stato())) {%>
                                                    <input type="text" class="form-control" readonly="readonly"
                                                           onkeypress="return keysub(this, event);"
                                                           onkeyup="return fieldNameSurname(this.id);" value="<%=liststatus.get(i)[1]%>"/> 
                                                    <%} else {%>
                                                    <%}%>
                                                    <%}%>




                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_user" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit User</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" 
                                                           id="us_code" name="us_code" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(us.getCod())%>" 
                                                           readonly="readonly"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Username</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.sanitize(us.getUsername())%>" readonly="readonly"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Surname <span class="font-red">*</span></label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" id="surname" name="surname" 
                                                           onkeyup="return fieldNameSurname(this.id);" value="<%=Utility.sanitize(us.getDe_cognome())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Name <span class="font-red">*</span></label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" id="name" name="name" 
                                                           onkeyup="return fieldNameSurname(this.id);" value="<%=Utility.sanitize(us.getDe_nome())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Type</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" id="typeuser" name="typeuser">
                                                        <%for (int i = 0; i < listtype.size(); i++) {
                                                                if (listtype.get(i)[0].equals(us.getFg_tipo())) {%>
                                                        <option value="<%=listtype.get(i)[0]%>" selected=""><%=listtype.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=listtype.get(i)[0]%>"><%=listtype.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Validity Password (Day)</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" id="valid" name="valid">
                                                        <%for (int i = 0; i < valid.size(); i++) {
                                                                if (valid.get(i)[0].equals(us.getValidita())) {%>
                                                        <option value="<%=valid.get(i)[0]%>" selected=""><%=valid.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=valid.get(i)[0]%>"><%=valid.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Accounting Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" 
                                                           id="conto" name="conto" 
                                                           value="<%=Utility.sanitize(us.getConto())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Email address</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" id="mail" name="mail" 
                                                           value="<%=Utility.sanitize(us.getEmail())%>"> 
                                                </div>
                                            </div>
                                            <%if (Constant.is_IT) {%>
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
                                                    <select class="form-control select2" id="status" name="status">
                                                        <%for (int i = 0; i < liststatus.size(); i++) {
                                                                if (liststatus.get(i)[0].equals(us.getFg_stato())) {%>
                                                        <option value="<%=liststatus.get(i)[0]%>" selected=""><%=liststatus.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=liststatus.get(i)[0]%>"><%=liststatus.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="row">
                        <div class="col-md-12">
                            <a href="tb_edit_users_resetpass.jsp?us_code=<%=Utility.sanitize(us_code)%>" class="btn btn-outline red"><i class="fa fa-key"></i> Reset Password</a>
                        </div>
                    </div>
                    <p class='ab'></p>
                    <%}
                    } else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=ins_user" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Add User</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" 
                                                           id="us_code" name="us_code" onkeypress="return keysub(this, event);" maxlength="4" onchange="return fieldOnlyNumber(this.id);"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Username</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" onkeypress="return keysub(this, event);" disabled="disabled"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Surname <span class="font-red">*</span></label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" id="surname" name="surname"
                                                           onkeyup="return fieldNameSurname(this.id);"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Name <span class="font-red">*</span></label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" id="name" name="name" onkeyup="return fieldNameSurname(this.id);" 
                                                           > 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Type</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" id="typeuser" name="typeuser">
                                                        <%for (int i = 0; i < listtype.size(); i++) {%>
                                                        <option value="<%=listtype.get(i)[0]%>"><%=listtype.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Validity Password (Day)</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" id="valid" name="valid">
                                                        <%for (int i = 0; i < valid.size(); i++) {%>
                                                        <option value="<%=valid.get(i)[0]%>"><%=valid.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Accounting Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" 
                                                           id="conto" name="conto"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Email address</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           onkeypress="return keysub(this, event);" id="mail" name="mail"> 
                                                </div>
                                            </div>
                                            <%if (Constant.is_IT) {%>
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
                    </form>
                    <%}%>
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
