<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.BlacklistM"%>
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



        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/validate.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/soop/tinymce/tinymce.min.js?apiKey=ma1upy7t6aft708fnem5ewb25zj40b36gdnl9ovb046gcbqx"></script>
        <%
            ArrayList<String[]> country = Engine.country();
            ArrayList<String[]> district = Engine.district();
            ArrayList<String[]> idcard = Engine.identificationCard();
        %>
        <script type="text/javascript">
            function changecountry(index) {
                var coun = document.getElementById('country' + index).value;
                if (coun !== '<%=Constant.codnaz%>') {
                    document.getElementById('dz' + index).style.display = 'none';
                } else {
                    document.getElementById('dz' + index).style.display = '';
                }
            }
            function loadpage() {
                changecountry('0');
                changecountry('1');
                inputvirgola();
            }

            function checkdescr() {
                var surname = document.getElementById("surname").value.trim();
                var name = document.getElementById("name").value.trim();
                var date1 = document.getElementById("date1").value.trim();
                if (surname === "" || name === "" || date1 === "") {
                    var ermsg = "You must complete the fields with <span class='font-blue'>*</span>: Surname, Name, Date (Place of Birth).";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

                //email
                var email = document.getElementById("email").value.trim();
                if (email !== "") {
                    var v1 = validate.single(email, {presence: true, email: true});
                    if (undefined !== v1) {
                        var ermsg = "Field 'Email' (Basic) is incorrect.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }

                //data nascita
                var date1 = document.getElementById("date1").value.trim();
                if (!isValidDate(date1)) {
                    var ermsg = "Field 'Date' (Place of Birth) is incorrect.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

                //Issue Date
                var isdate = document.getElementById("isdate").value.trim();
                if (isdate !== "") {
                    if (!isValidDate(isdate)) {
                        var ermsg = "Field 'Issue Date' (Document Identity) is incorrect.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }
                //expiration date
                var exdate = document.getElementById("exdate").value.trim();
                if (exdate !== "") {
                    if (!isValidDate(exdate)) {
                        var ermsg = "Field 'Expiration Date' (Document Identity) is incorrect.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }
            }


            function RemoveAccents(str) {
                var accents = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
                var accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";
                str = str.split('');
                var strLen = str.length;
                var i, x;
                for (i = 0; i < strLen; i++) {
                    if ((x = accents.indexOf(str[i])) !== -1) {
                        str[i] = accentsOut[x] + "'";
                    }
                }
                return str.join('');
            }

            function modificaOAMSurname(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase());
                fieldNameSurnameNEW(field.id);
            }

            function fieldNameSurnameNEW(fieldid) {
                var stringToReplace = document.getElementById(fieldid).value;
                var specialChars = "~`!#$%^&*+=-[]();,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°èéòàù§*ç@|!£$%&/()=?^€ì";
                for (var i = 0; i < specialChars.length; i++) {
                    stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
                }
                stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
                document.getElementById(fieldid).value = stringToReplace;
            }

        </script>
    </head>
    <!-- END HEAD -->
    <body class="page-full-width page-content-white" onload="return loadpage();" style="height: 700px;">
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
                        String view = request.getParameter("view");
                        if (view == null) {
                            view = "0";
                        }

                        String bl_code = request.getParameter("bl_code");
                        BlacklistM bl = Engine.get_list_BlMacc(bl_code);
                        if (bl != null) {

                            String chsex = "";
                            if (bl.getSesso().equals("M")) {
                                chsex = "checked";
                            }
                            String sta = "";
                            if (bl.getFg_annullato().equals("0")) {
                                sta = "checked";
                            }

                            if (view.equals("1")) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_black" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">View Blacklist Maccorp</span>
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
                                                <a href="#tab_1_2" data-toggle="tab"> Place of Birth </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3"data-toggle="tab"> Document Identity </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_4"data-toggle="tab"> Message </a>
                                            </li>

                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Status</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" disabled class="make-switch" <%=sta%>
                                                                   name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" 
                                                                   value="<%=Utility.sanitize(bl.getCode())%>" name="bl_code" id="bl_code" onkeypress="return keysub(this, event);" 
                                                                   readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Surname</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"disabled name="surname" id="surname" 
                                                                   onkeypress="return keysub(this, event);" 
                                                                   onkeyup="return modificaOAMSurname(this, event);"
                                                                   value="<%=Utility.sanitize(bl.getCognome())%>"> 
                                                        </div>
                                                        <label class="col-md-3 control-label">Name</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"disabled name="name" id="name"
                                                                   onkeypress="return keysub(this, event);"
                                                                   onkeyup="return modificaOAMSurname(this, event);"
                                                                   value="<%=Utility.sanitize(bl.getNome())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sex</label>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch"disabled <%=chsex%> name="sex" id="sex" onkeypress="return keysub(this, event);" 
                                                                   data-size="normal" data-on-color="primary" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Male</span>" 
                                                                   data-off-text="<span class='tabnow'>Female</span>">
                                                        </div>
                                                        <label class="col-md-3 control-label">Tax Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"disabled name="taxc" id="taxc" onkeypress="return keysub(this, event);"  
                                                                   value="<%=Utility.sanitize(bl.getCodfisc())%>"> 
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="country0"disabled name="country0" onkeypress="return keysub(this, event);" 
                                                                    onchange="return changecountry('0', '0');">
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <%if (country.get(i)[0].equals(bl.getNazione())) {%>
                                                                <option value="<%=country.get(i)[0]%>" selected="selected"><%=country.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <label class="col-md-3 control-label">City</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"disabled name="city0" id="city0" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(bl.getCitta())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">

                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control"disabled name="addr" id="addr" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(bl.getIndirizzo())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dz0">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"disabled name="zipc0" id="zipc0" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(bl.getCap())%>"> 
                                                        </div>
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" disabled id="district0" name="district0" onkeypress="return keysub(this, event);" >
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(bl.getProvincia())) {%>
                                                                <option value="<%=district.get(i)[0]%>" selected="selected"><%=district.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>

                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Email</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"disabled name="email" id="email" onkeypress="return keysub(this, event);"  
                                                                   value="<%=Utility.sanitize(bl.getEmail())%>"> 
                                                        </div>
                                                        <label class="col-md-3 control-label">Phone Number</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"disabled name="phone" maxlength="11" id="phone" onkeypress="return keysub(this, event);" onkeyup="return fieldOnlyNumber(this.id);" 
                                                                   value="<%=Utility.sanitize(bl.getTelefono())%>"> 
                                                            <span class="help-block">Insert without the +</span>
                                                        </div>
                                                    </div>



                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">City</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"disabled id="city1" name="city1" onkeypress="return keysub(this, event);" 
                                                               value="<%=Utility.sanitize(bl.getCitta_nascita())%>"> 
                                                    </div>
                                                    <div class="clearfix" id="dz1">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2"disabled id="district1" name="district1" onkeypress="return keysub(this, event);" >
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(bl.getProvincia_nascita())) {%>
                                                                <option value="<%=district.get(i)[0]%>" selected="selected"><%=district.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Country</label>
                                                    <div class="col-md-3">
                                                        <select class="form-control select2"disabled id="country1" name="country1" onkeypress="return keysub(this, event);" 
                                                                onchange="return changecountry('1', '0');">
                                                            <option value="-">...</option>
                                                            <%for (int i = 0; i < country.size(); i++) {%>
                                                            <%if (country.get(i)[0].equals(bl.getNazione_nascita())) {%>
                                                            <option value="<%=country.get(i)[0]%>" selected="selected"><%=country.get(i)[1]%></option>
                                                            <%} else {%>
                                                            <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                            <%}%>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                    <label class="col-md-3 control-label">Date</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker"disabled id="date1" name="date1" onkeypress="return keysub(this, event);" 
                                                               value="<%=Utility.sanitize(Utility.formatStringtoStringDate(bl.getDt_nascita(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="tab-pane fade" id="tab_1_3">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Identification Card</label>
                                                    <div class="col-md-3">
                                                        <select class="form-control select2"disabled id="doctype" name="doctype" onkeypress="return keysub(this, event);" >
                                                            <option value="-">...</option>
                                                            <%for (int i = 0; i < idcard.size(); i++) {%>
                                                            <%if (idcard.get(i)[0].equals(bl.getTipo_documento())) {%>
                                                            <option value="<%=idcard.get(i)[0]%>" selected="selected"><%=idcard.get(i)[1]%></option>
                                                            <%} else {%>
                                                            <option value="<%=idcard.get(i)[0]%>"><%=idcard.get(i)[1]%></option>
                                                            <%}%>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                    <label class="col-md-3 control-label">Number</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" disabled id="numdoc" name="numdoc" onkeypress="return keysub(this, event);"  
                                                               value="<%=Utility.sanitize(bl.getNumero_documento())%>"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Issue Date</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker"disabled id="isdate" name="isdate" onkeypress="return keysub(this, event);"  
                                                               value="<%=Utility.sanitize(Utility.formatStringtoStringDate(bl.getDt_rilascio_documento(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                    </div>
                                                    <label class="col-md-3 control-label">Expiration Date</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker"disabled id="exdate" name="exdate" onkeypress="return keysub(this, event);"  
                                                               value="<%=Utility.sanitize(Utility.formatStringtoStringDate(bl.getDt_scadenza_documento(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Issued By</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="dociss"disabled name="dociss" onkeypress="return keysub(this, event);" 
                                                               value="<%=Utility.sanitize(bl.getRilasciato_da_documento())%>"> 
                                                    </div>
                                                    <label class="col-md-3 control-label">Place of Issue</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"disabled id="docplace" name="docplace" onkeypress="return keysub(this, event);" 
                                                               value="<%=Utility.sanitize(bl.getLuogo_rilascio_documento())%>"> 
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_4">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Text Message</label>
                                                    <div class="col-md-9">
                                                        <textarea id="txtmsg" name="txtmsg" class="tinyta"><%=Utility.sanitize(bl.getText())%></textarea>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form> 
                                                    <script>


                                                               $(function () {
                                                                   tinymce.init({
                                                                       selector: '.tinyta',
                                                                       height: 200,
                                                                       menubar: false,
                                                                       schema: 'html5',
                                                                       readonly : 1,
                                                                   });
                                                               });






                </script>
                    <%} else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_black" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit Blacklist Maccorp</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Basic </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> Place of Birth </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3"data-toggle="tab"> Document Identity </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_4"data-toggle="tab"> Message </a>
                                            </li>

                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Status</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=sta%>
                                                                   name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" 
                                                                   value="<%=Utility.sanitize(bl.getCode())%>" name="bl_code" id="bl_code" onkeypress="return keysub(this, event);" 
                                                                   readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Surname</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="surname" id="surname" 
                                                                   onkeypress="return keysub(this, event);" 
                                                                   onkeyup="return modificaOAMSurname(this, event);"
                                                                   value="<%=Utility.sanitize(bl.getCognome())%>"> 
                                                        </div>
                                                        <label class="col-md-3 control-label">Name</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="name" id="name" 
                                                                   onkeypress="return keysub(this, event);" 
                                                                   onkeyup="return modificaOAMSurname(this, event);"
                                                                   value="<%=Utility.sanitize(bl.getNome())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sex</label>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch" <%=chsex%> name="sex" id="sex" onkeypress="return keysub(this, event);" 
                                                                   data-size="normal" data-on-color="primary" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Male</span>" 
                                                                   data-off-text="<span class='tabnow'>Female</span>">
                                                        </div>
                                                        <label class="col-md-3 control-label">Tax Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="taxc" id="taxc" onkeypress="return keysub(this, event);"  
                                                                   value="<%=Utility.sanitize(bl.getCodfisc())%>"> 
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="country0" name="country0" onkeypress="return keysub(this, event);" 
                                                                    onchange="return changecountry('0', '0');">
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <%if (country.get(i)[0].equals(bl.getNazione())) {%>
                                                                <option value="<%=country.get(i)[0]%>" selected="selected"><%=country.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <label class="col-md-3 control-label">City</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="city0" id="city0" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(bl.getCitta())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">

                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="addr" id="addr" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(bl.getIndirizzo())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dz0">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="zipc0" id="zipc0" onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(bl.getCap())%>"> 
                                                        </div>
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="district0" name="district0" onkeypress="return keysub(this, event);" >
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(bl.getProvincia())) {%>
                                                                <option value="<%=district.get(i)[0]%>" selected="selected"><%=district.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>

                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Email</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="email" id="email" onkeypress="return keysub(this, event);"  
                                                                   value="<%=Utility.sanitize(bl.getEmail())%>"> 
                                                        </div>
                                                        <label class="col-md-3 control-label">Phone Number</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="phone" maxlength="11" id="phone" onkeypress="return keysub(this, event);" onkeyup="return fieldOnlyNumber(this.id);" 
                                                                   value="<%=Utility.sanitize(bl.getTelefono())%>"> 
                                                            <span class="help-block">Insert without the +</span>
                                                        </div>
                                                    </div>



                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">City</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="city1" name="city1" onkeypress="return keysub(this, event);" 
                                                               value="<%=Utility.sanitize(bl.getCitta_nascita())%>"> 
                                                    </div>
                                                    <div class="clearfix" id="dz1">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="district1" name="district1" onkeypress="return keysub(this, event);" >
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(bl.getProvincia_nascita())) {%>
                                                                <option value="<%=district.get(i)[0]%>" selected="selected"><%=district.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Country</label>
                                                    <div class="col-md-3">
                                                        <select class="form-control select2" id="country1" name="country1" onkeypress="return keysub(this, event);" 
                                                                onchange="return changecountry('1', '0');">
                                                            <option value="-">...</option>
                                                            <%for (int i = 0; i < country.size(); i++) {%>
                                                            <%if (country.get(i)[0].equals(bl.getNazione_nascita())) {%>
                                                            <option value="<%=country.get(i)[0]%>" selected="selected"><%=country.get(i)[1]%></option>
                                                            <%} else {%>
                                                            <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                            <%}%>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                    <label class="col-md-3 control-label">Date</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker" id="date1" name="date1" onkeypress="return keysub(this, event);" 
                                                               value="<%=Utility.sanitize(Utility.formatStringtoStringDate(bl.getDt_nascita(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="tab-pane fade" id="tab_1_3">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Identification Card</label>
                                                    <div class="col-md-3">
                                                        <select class="form-control select2" id="doctype" name="doctype" onkeypress="return keysub(this, event);" >
                                                            <option value="-">...</option>
                                                            <%for (int i = 0; i < idcard.size(); i++) {%>
                                                            <%if (idcard.get(i)[0].equals(bl.getTipo_documento())) {%>
                                                            <option value="<%=idcard.get(i)[0]%>" selected="selected"><%=idcard.get(i)[1]%></option>
                                                            <%} else {%>
                                                            <option value="<%=idcard.get(i)[0]%>"><%=idcard.get(i)[1]%></option>
                                                            <%}%>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                    <label class="col-md-3 control-label">Number</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="numdoc" name="numdoc" onkeypress="return keysub(this, event);"  
                                                               value="<%=Utility.sanitize(bl.getNumero_documento())%>"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Issue Date</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker" id="isdate" name="isdate" onkeypress="return keysub(this, event);"  
                                                               value="<%=Utility.sanitize(Utility.formatStringtoStringDate(bl.getDt_rilascio_documento(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                    </div>
                                                    <label class="col-md-3 control-label">Expiration Date</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker" id="exdate" name="exdate" onkeypress="return keysub(this, event);"  
                                                               value="<%=Utility.sanitize(Utility.formatStringtoStringDate(bl.getDt_scadenza_documento(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Issued By</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="dociss" name="dociss" onkeypress="return keysub(this, event);" 
                                                               value="<%=Utility.sanitize(bl.getRilasciato_da_documento())%>"> 
                                                    </div>
                                                    <label class="col-md-3 control-label">Place of Issue</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="docplace" name="docplace" onkeypress="return keysub(this, event);" 
                                                               value="<%=Utility.sanitize(bl.getLuogo_rilascio_documento())%>"> 
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_4">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Text Message</label>
                                                    <div class="col-md-9">
                                                        <textarea id="txtmsg" name="txtmsg" class="tinyta"><%=Utility.sanitize(bl.getText())%></textarea>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <script>


                        $(function () {
                            tinymce.init({
                                selector: '.tinyta',
                                height: 200,
                                menubar: false,
                                schema: 'html5',
                                toolbar1: 'undo redo | fontsizeselect fontselect | bold italic underline | alignjustify ',
                                fontsize_formats: '8pt 9pt 10pt 11pt 12pt 14pt 18pt 20pt'
                            });
                        });
                    </script>
                    <%}
                    } else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=ins_black" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">New Blacklist Maccorp</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Basic </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> Place of Birth </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3"data-toggle="tab"> Document Identity </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_4"data-toggle="tab"> Message </a>
                                            </li>

                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control uppercase" disabled="disabled"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Surname <span class="font-blue">*</span></label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"  name="surname" id="surname" 
                                                                   onkeypress="return keysub(this, event);" 
                                                                   onkeyup="return modificaOAMSurname(this, event);"
                                                                   > 
                                                        </div>
                                                        <label class="col-md-3 control-label">Name <span class="font-blue">*</span></label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"  name="name" id="name" 
                                                                   onkeypress="return keysub(this, event);"
                                                                   onkeyup="return modificaOAMSurname(this, event);"
                                                                   > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sex</label>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch" checked name="sex" id="sex" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Male</span>" 
                                                                   data-off-text="<span class='tabnow'>Female</span>">
                                                        </div>
                                                        <label class="col-md-3 control-label">Tax Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="taxc" id="taxc" onkeypress="return keysub(this, event);" maxlength="20"
                                                                   > 
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="country0" name="country0" placeholder="..." onkeypress="return keysub(this, event);"
                                                                    onchange="return changecountry('0', '0');">
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < country.size(); i++) {%>

                                                                <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>

                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <label class="col-md-3 control-label">City</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="city0" id="city0" onkeypress="return keysub(this, event);" 
                                                                   > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">

                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" name="addr" id="addr" onkeypress="return keysub(this, event);" 
                                                                   > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dz0">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="zipc0" id="zipc0" onkeypress="return keysub(this, event);"
                                                                   > 
                                                        </div>
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="district0" name="district0" placeholder="..." onkeypress="return keysub(this, event);">
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < district.size(); i++) {%>

                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>

                                                                <%}%>
                                                            </select>
                                                        </div>

                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Email</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="email" id="email" onkeypress="return keysub(this, event);" 
                                                                   > 
                                                        </div>
                                                        <label class="col-md-3 control-label">Phone Number</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" name="phone" maxlength="11" id="phone" onkeypress="return keysub(this, event);" onkeyup="return fieldOnlyNumber(this.id);"/>
                                                            <span class="help-block"><small>Insert without '+'</small></span>
                                                        </div>
                                                    </div>



                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">City</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="city1" name="city1" onkeypress="return keysub(this, event);" 
                                                               > 
                                                    </div>
                                                    <div class="clearfix" id="dz1">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="district1" name="district1" onkeypress="return keysub(this, event);">
                                                                <option value="-">...</option>
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Country</label>
                                                    <div class="col-md-3">
                                                        <select class="form-control select2" id="country1" name="country1" onkeypress="return keysub(this, event);" onchange="return changecountry('1', '0');">
                                                            <option value="-">...</option>
                                                            <%for (int i = 0; i < country.size(); i++) {%>
                                                            <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                    <label class="col-md-3 control-label">Date <span class="font-blue">*</span></label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker" name="date1" id="date1" onkeypress="return keysub(this, event);"/> 
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="tab-pane fade" id="tab_1_3">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Identification Card</label>
                                                    <div class="col-md-3">
                                                        <select class="form-control select2" id="doctype" name="doctype" onkeypress="return keysub(this, event);">
                                                            <option value="-">...</option>
                                                            <%for (int i = 0; i < idcard.size(); i++) {%>
                                                            <option value="<%=idcard.get(i)[0]%>"><%=idcard.get(i)[1]%></option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                    <label class="col-md-3 control-label">Number</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" name="numdoc" id="numdoc" onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Issue Date</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker" name="isdate" id="isdate" onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                    <label class="col-md-3 control-label">Expiration Date</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control date-picker" name="exdate" id="exdate" onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Issued By</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" name="dociss" id="dociss" onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                    <label class="col-md-3 control-label">Place of Issue</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" name="docplace" id="docplace" onkeypress="return keysub(this, event);" > 
                                                    </div>
                                                </div>
                                            </div>
                                                        
                                            <div class="tab-pane fade" id="tab_1_4">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Text Message</label>
                                                    <div class="col-md-9">
                                                        <textarea id="txtmsg" name="txtmsg" class="tinyta"></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                                                        <script>


                                                               $(function () {
                                                                   tinymce.init({
                                                                       selector: '.tinyta',
                                                                       height: 200,
                                                                       menubar: false,
                                                                       schema: 'html5',
                                                                       toolbar1: 'undo redo | fontsizeselect fontselect | bold italic underline | alignjustify ',
                                                                       fontsize_formats: '8pt 9pt 10pt 11pt 12pt 14pt 18pt 20pt'
                                                                   });
                                                               });






                </script>
                    <%}%>
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
            <script src="assets/soop/js/components-editors.min.js" type="text/javascript"></script>
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
