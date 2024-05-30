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
        <title>Mac2.0 - Currency</title>
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
        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/soop/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>



        <script type="text/javascript">
            function checkfile() {
                var but = document.getElementById('uplexc');
                but.disabled = true;
                $("#uplexc").html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");

                document.getElementById("dataalert").style.display = "none";
                var oFile = document.getElementById("fileexl1");
                if (oFile.value.trim() === "") {
                    oFile.click();
                    but.disabled = false;
                    but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                    return false;
                } else {
                    var ext = oFile.value.trim().substr(oFile.value.trim().lastIndexOf('.')).toLowerCase();
                    if (ext !== ".xls" && ext !== ".xlsm" && ext !== ".xlsx") {
                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                        document.getElementById('errorlarge').style.display = "block";
                        document.getElementById('errorlargetext').innerHTML = "The file must be .xls or .xlsm or .xlsx";
                        but.disabled = false;
                        but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                        return false;
                    }
                }
                var dimmax = 7340032;
                if (oFile.files[0].size > dimmax) {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "The file exceeds the maximum size allowed (7 MB).";
                    but.disabled = false;
                    but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                    return false;
                }
                var dt_val = document.getElementById("dt_val").value.trim();
                if (!moment(dt_val, 'DD/MM/YYYY hh:mm').isValid() || dt_val === "31/12/1899 00:00") {
                    document.getElementById("dt_val").value = "";
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "'Date Apply' are incorrect. The date must be after the current.";
                    but.disabled = false;
                    but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                    return false;
                }
                var nowdate = moment();
                var pobdate1 = moment(dt_val, 'DD/MM/YYYY hh:mm', true);
                if (nowdate.isSameOrAfter(pobdate1)) {
                    document.getElementById("dt_val").value = "";
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "'Date Apply' are incorrect. The date must be after the current.";
                    but.disabled = false;
                    but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                    return false;
                }

            }

            function checkfilesp() {
                var but = document.getElementById('uplexcsp');
                but.disabled = true;
                $("#uplexcsp").html("<i class='fa fa-circle-o-notch fa-spin'></i> Sending...");

                document.getElementById("dataalertsp").style.display = "none";
                var oFile = document.getElementById("fileexl1sp");
                if (oFile.value.trim() === "") {
                    oFile.click();
                    but.disabled = false;
                    but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                    return false;
                } else {
                    var ext = oFile.value.trim().substr(oFile.value.trim().lastIndexOf('.')).toLowerCase();
                    if (ext !== ".xls" && ext !== ".xlsm" && ext !== ".xlsx") {
                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                        document.getElementById('errorlarge').style.display = "block";
                        document.getElementById('errorlargetext').innerHTML = "The file must be .xls or .xlsm or .xlsx";
                        but.disabled = false;
                        but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                        return false;
                    }
                }
                var dimmax = 7340032;
                if (oFile.files[0].size > dimmax) {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "The file exceeds the maximum size allowed (7 MB).";
                    but.disabled = false;
                    but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                    return false;
                }
                var dt_val = document.getElementById("dt_valsp").value.trim();
                if (!moment(dt_val, 'DD/MM/YYYY hh:mm').isValid() || dt_val === "31/12/1899 00:00") {
                    document.getElementById("dt_valsp").value = "";
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "'Date Apply' are incorrect. The date must be after the current.";
                    but.disabled = false;
                    but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                    return false;
                }
                var nowdate = moment();
                var pobdate1 = moment(dt_val, 'DD/MM/YYYY hh:mm', true);
                if (nowdate.isSameOrAfter(pobdate1)) {
                    document.getElementById("dt_valsp").value = "";
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "'Date Apply' are incorrect. The date must be after the current.";
                    but.disabled = false;
                    but.innerHTML = "<i class='fa fa-upload'></i> Upload";
                    return false;
                }

            }
        </script>
    </head>
    <!-- END HEAD -->
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return online();">

        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_ma11.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
            %>
            <div class="modal fade" id="largelogin" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Password request</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <div class="input-icon">
                                    <i class="fa fa-key font-blue"></i>
                                    <input class="form-control" type="password" autocomplete="off" placeholder="Password" name="password"> 
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-arrow-right"></i> Continue</button>
                            <a type="button" class="btn btn-outline red" data-dismiss="modal" onclick="return dismiss('largelogin');"><i class="fa fa-remove"></i> Cancel</a>
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
                            <h3 class="page-title">Maintenance <small><b>Currency</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>

                    <%
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
                        if (esito.equals("errcu1")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. The file is corrupted or not in accordance with excel. Try again.";
                        } else if (esito.equals("errcu2")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Cannot read file or value are incorrect. Try again.";
                        } else if (esito.equals("errcu3")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Check your connection and try again.";
                        } else if (esito.equals("okcur1")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully. Currency has been updates.";
                        } else if (esito.equals("okcur2")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully. Spread has been updates.";
                        } else {
                            esito = "";
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

                    <form class="form-horizontal" action="Operazioni?type=upload_cur01" method="POST" enctype="multipart/form-data" name="fu1" onsubmit="return checkfile();">
                        <div class="modal fade bs-modal-lg" id="uplexe" tabindex="-2" role="dialog" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                        <h4 class="modal-title font-green-jungle uppercase"><b>Upload excel currency</b></h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label class="col-md-3 control-label">Date Apply</label>
                                                    <div class="col-md-6">
                                                        <input type="text" class="form-control form_datetime" 
                                                               id="dt_val" name="dt_val" 
                                                               onkeypress="return keysub(this, event);" data-date-start-date="+0d"
                                                               value="">
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">File </label>
                                            <div class="col-md-9">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file-o fileinput-exists font-green-jungle"></i>&nbsp;
                                                            <span class="fileinput-filename nowrap"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class="fileinput-new"> Select file </span>
                                                            <span class="fileinput-exists"> Change </span>
                                                            <input type="file" name="fileexl1" id="fileexl1" accept="<%=Engine.getConf("ext.excel")%>" 
                                                                   onkeypress="return keysub(this, event);"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" 
                                                           data-dismiss="fileinput"> Remove </a>
                                                    </div>
                                                    <span class="help-block"><small>Only file .xls,.xlsm,.xlsx - Size max 7 Mb</small></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group" id="dataalert" style="display: none;">
                                            <div class="col-md-12">
                                                <div class="alert alert-danger">
                                                    <strong>Warning!</strong> 'Date Apply' are incorrect. The date must be after the current.
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn green-jungle btn-outline" id="uplexc"><i class="fa fa-upload"></i> Upload</button>
                                        <button type="button" class="btn red btn-outline" data-dismiss="modal"><i class="fa fa-window-close"></i> Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                    <form class="form-horizontal" action="Operazioni?type=upload_cur02" method="POST" enctype="multipart/form-data" name="fu11" onsubmit="return checkfilesp();">

                        <div class="modal fade bs-modal-lg" id="uplexespread" tabindex="-2" role="dialog" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                        <h4 class="modal-title font-green-jungle uppercase"><b>Upload Spread (Excel)</b></h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <label class="col-md-3 control-label">Date Apply</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control form_datetime" 
                                                               id="dt_valsp" name="dt_val" 
                                                               onkeypress="return keysub(this, event);" data-date-start-date="+0d"
                                                               value="">
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-md-3">File </label>
                                            <div class="col-md-9">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file-o fileinput-exists font-green-jungle"></i>&nbsp;
                                                            <span class="fileinput-filename nowrap"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class="fileinput-new"> Select file </span>
                                                            <span class="fileinput-exists"> Change </span>
                                                            <input type="file" name="fileexl1" id="fileexl1sp" accept="<%=Engine.getConf("ext.excel")%>" 
                                                                   onkeypress="return keysub(this, event);"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" 
                                                           data-dismiss="fileinput"> Remove </a>
                                                    </div>
                                                    <span class="help-block"><small>Only file .xls,.xlsm,.xlsx - Size max 7 Mb</small></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group" id="dataalertsp" style="display: none;">
                                            <div class="col-md-12">
                                                <div class="alert alert-danger">
                                                    <strong>Warning!</strong> 'Date Apply' are incorrect. The date must be after the current.
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn green-jungle btn-outline" id="uplexcsp"><i class="fa fa-upload"></i> Upload</button>
                                        <button type="button" class="btn red btn-outline" data-dismiss="modal"><i class="fa fa-window-close"></i> Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

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
                        String tipo = (String) session.getAttribute("us_tip");
                        if (tipo == null) {
                            tipo = "";
                        }

                        boolean central = Engine.isCentral();
                        if (central) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <a href="rate_editcen.jsp" target="_blank" class="btn btn-outline dark"><i class="fa fa-balance-scale"></i> Edit Rate</a>
                                <a href="spread_editcen.jsp" target="_blank" class="btn btn-outline dark"><i class="fa fa-money"></i> Edit Spread</a>
                                <!--<a href="rate_historymo.jsp" target="_blank" class="btn btn-outline dark"><i class="fa fa-info"></i> History Rate</a>-->
                                <%if (!tipo.equals("0")) {%>
                                <button data-toggle="modal" data-target="#uplexe" class="btn btn-outline dark"><i class="fa fa-file-excel-o"></i> Upload Excel</button>
                                <span class="alert font-dark"><small><%=Engine.get_last_excel_upl()%></small></span>
                                <%if (true) {%>
                                <button data-toggle="modal" data-target="#uplexespread" class="btn btn-outline blue-steel">
                                    <i class="fa fa-file-excel-o"></i> Upload Spread (Excel)</button>
                                <%}%>
                                <%}%>
                            </div>
                        </div>
                    </div>
                    <%}%>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT TILL -->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Currency</span>
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
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_0" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow"style="width: 70px;">Code</th>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">BCE Rate</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            </tbody>
                                                            <tfoot>
                                                                <tr>
                                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..."></th>
                                                                    <th><input type="text" class="form-control" name="dest1" placeholder="..." disabled=""></th>
                                                                </tr>
                                                            </tfoot>
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
                <!-- END CORE PLUGINS -->
                <!-- BEGIN PAGE LEVEL PLUGINS -->
                <!-- END PAGE LEVEL PLUGINS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->
                <!-- END THEME LAYOUT SCRIPTS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->
                <script type="text/javascript">
                                        jQuery(document).ready(function () {
                                            var dt1 = function () {
                                                var f = $("#sample_0");
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
                                                    processing: true,
                                                    ajax: {
                                                        url: "Query?type=tb_currency&central=<%=central%>",
                                                        dataSrc: "aaData",
                                                        type: "GET"
                                                    },
                                                    columnDefs: [
                                                        {orderable: 1, targets: [0]},
                                                        {orderable: 1, targets: [1]},
                                                        {orderable: 1, targets: [2]},
                                                        {orderable: !1, targets: [3]}
                                                    ],
                                                    buttons: [
                                                        {text: "<i class='fa fa-file-pdf-o'></i> Excel",
                                                            className: "btn white btn-outline",
                                                            action: function (e, dt, node, config) {
                                                                //window.open('Download?type=viewExcel&cod=' + cexcel, '_blank');
                                                                window.open('Fileview?type=tb_currency&value=excel', '_blank');
                                                            }
                                                        },
                                                        {text: "<i class='fa fa-file-pdf-o'></i> Pdf",
                                                            className: "btn white btn-outline",
                                                            action: function (e, dt, node, config) {
                                                                //window.open('Download?type=viewPdf&cod=' + cpdf, '_blank');
                                                                window.open('Fileview?type=tb_currency&value=pdf', '_blank');
                                                            }
                                                        },
                                                        {extend: "colvis", className: "btn white btn-outline", text: "<i class='fa fa-list-alt'></i> Columns"},
                                                        {text: "<i class='fa fa fa-refresh'></i>",
                                                            className: "btn white btn-outline",
                                                            action: function (e, dt, node, config) {
                                                                location.reload();
                                                            }
                                                        }]
                                                    ,
                                                    colReorder: {reorderCallback: function () {
                                                            
                                                        }},
                                                    lengthMenu: [
                                                        [25, 50, 100, -1],
                                                        [25, 50, 100, "All"]
                                                    ],
                                                    pageLength: 25,
                                                    order: [],
                                                    dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                                                });
                                                $("#sample_0 tfoot input").keyup(function () {
                                                    f.fnFilter(this.value, f.oApi._fnVisibleToColumnIndex(
                                                            f.fnSettings(), $("#sample_0 tfoot input").index(this)));
                                                });
                                                $("#sample_0 tfoot input").each(function (i) {
                                                    this.initVal = this.value;
                                                });
                                                $("#sample_0 tfoot input").focus(function () {
                                                    if (this.className === "form-control")
                                                    {
                                                        this.className = "form-control";
                                                        this.value = "";
                                                    }
                                                });
                                                $("#sample_0 tfoot input").blur(function (i) {
                                                    if (this.value === "")
                                                    {
                                                        this.className = "form-control";
                                                        this.value = this.initVal;
                                                    }
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
