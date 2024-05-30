<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Etichette"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String link_value = Engine.verifyUser(request);
    if (link_value != null) {
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
        <title>Mac2.0 - Export Files</title>
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
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>

        

        <script type="text/javascript">


            function chtypecora() {
                var sendtype_cor = document.getElementById('sendtype_cor').value.trim();
                document.getElementById('from_cor').value = '';
                $('#from_cor').datepicker('remove');
                if (sendtype_cor === "0") {
                    document.getElementById('from_cor_et').innerHTML = 'Month';
                    $('#from_cor').datepicker({
                        format: 'mm/yyyy',
                        orientation: 'bottom',
                        autoclose: !0
                    });
                } else {
                    document.getElementById('from_cor_et').innerHTML = 'Year';
                    $('#from_cor').datepicker({
                        format: 'yyyy',
                        orientation: 'bottom',
                        autoclose: !0
                    });
                }
            }

            function checkformoam() {
                var from_oam = document.getElementById('from_oam').value.trim();
                if (from_oam === "") {
                    var ermsg = "You must complete 'Month' field.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                document.getElementById('f1_oam').submit();
                return false;
            }

            function checkformanti() {
                var from_oam = document.getElementById('from_ant').value.trim();
                var to_ant = document.getElementById('to_ant').value.trim();
                if (from_oam === "" || to_ant === "") {
                    var ermsg = "You must complete 'Date From' and 'Date To' fields.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                document.getElementById('f1_anti').submit();
                return false;
            }

            function checkformcor() {
                var from_oam = document.getElementById('from_cor').value.trim();
                if (from_oam === "") {
                    var ermsg = "You must complete 'Month' field.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                document.getElementById('f1_cor').submit();
                return false;
            }

            function checkformesolver() {
                var from_oam = document.getElementById('from_eso').value.trim();
                if (from_oam === "") {
                    var ermsg = "You must complete 'Day' field.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                document.getElementById('f1_eso').submit();
                return false;
            }

            function dismiss(modal) {
                document.getElementById(modal).className = "modal fade";
                document.getElementById(modal).style.display = "none";
            }

            function loadpage() {
                online();
                chtypecora();
                inputvirgola();
            }

        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">

        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_es4.jsp"%>
            <!-- END MENU -->

            <%                
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                
                ArrayList<Branch> array_branch = Engine.list_branch_enabled();
                ArrayList<Branch> array_branch_2 = Engine.list_branch_completeAFTER311217();
                

                
                
            %>


            <!-- BEGIN CONTENT -->
            <div class="page-content-wrapper">
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
                            <h3 class="page-title">Export Files <small><b>Create Export File</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>

                    <%                        
                        if(!Constant.is_CZ){
                        boolean central = Engine.isCentral();
                        String tipo = (String) request.getSession().getAttribute("us_tip");
                        if (tipo == null) {
                            tipo = "";
                        }
                        if (tipo.equals("2") || tipo.equals("3")) {
                    %>
                    <div class="row">
                        
                        
                        
                        
                        <%if (central) {%>
                        <%if (Constant.is_IT) {%>
                        <div class="col-md-6">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-doc font-red"></i>
                                        <span class="caption-subject font-red bold uppercase">CORA</span>
                                    </div>
                                    <div class="tools"> 
                                        <button type="button" class="btn btn-outline red" onclick="return checkformcor();">
                                            <i class="fa fa-arrow-right"></i> Export File</button>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form class="form-horizontal" action="Operazioni?type=coraexp" target="_blank" role="form" name="f1_cor" id="f1_cor" 
                                          method="post" >
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label" id="from_cor_et"></label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control" 
                                                               id="from_cor" name="from"
                                                               onkeypress="return keysub(this, event);"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Type</label>
                                                    <div class="col-md-9">
                                                        <select class="form-control select2" onchange="return chtypecora();"
                                                                id="sendtype_cor" name="sendtype" placeholder="...">
                                                            <option value="0">Monthly</option>
                                                            <option value="1">Yearly</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-doc font-blue"></i>
                                        <span class="caption-subject font-blue bold uppercase">OAM</span>
                                    </div>
                                    <div class="tools"> 
                                        <button type="button" class="btn btn-outline blue" onclick="return checkformoam();">
                                            <i class="fa fa-arrow-right"></i> Export File</button>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form class="form-horizontal" action="Operazioni?type=oamexp" target="_blank" role="form" name="f1_oam" id="f1_oam" 
                                          method="post" >
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Month</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control date-picker" 
                                                               id="from_oam" name="from"  data-date-format="mm/yyyy"
                                                               onkeypress="return keysub(this, event);"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Type</label>
                                                    <div class="col-md-9">
                                                        <select class="form-control select2" name="sendtype" placeholder="...">
                                                            <option value="0">Ordinary</option>
                                                            <option value="1">Replacement of correcting</option>
                                                        </select>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-doc font-green-dark"></i>
                                        <span class="caption-subject font-green-dark bold uppercase">ESOLVER</span>
                                    </div>
                                    <div class="tools"> 
                                        <button type="button" class="btn btn-outline green-dark" onclick="return checkformesolver();">
                                            <i class="fa fa-arrow-right"></i> Export File</button>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form class="form-horizontal" action="Operazioni?type=esolv" target="_blank" role="form" name="f1_eso" id="f1_eso" 
                                          method="post" >
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Branch</label>
                                                    <div class="col-md-9">
                                                        <select class="form-control select2" name="branch" id="branch" >
                                                            <option value="---" selected="selected">All</option>
                                                            <%for (int j = 0; j < array_branch_2.size(); j++) {%>
                                                            <option value="<%=array_branch_2.get(j).getCod()%>">
                                                                <%=array_branch_2.get(j).getCod()%> - <%=array_branch_2.get(j).getDe_branch()%>
                                                            </option>
                                                            <%}%>

                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Day</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control date-picker" 
                                                               id="from_eso" name="from" onkeypress="return keysub(this, event);"/>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-doc font-grey-salsa"></i>
                                        <span class="caption-subject font-grey-salsa bold uppercase">Money Laundering</span>
                                    </div>
                                    <div class="tools"> 
                                        <button type="button" class="btn btn-outline grey-salsa" onclick="return checkformanti();">
                                            <i class="fa fa-arrow-right"></i> Export File</button>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form class="form-horizontal" action="Operazioni?type=antiexp" target="_blank" role="form" name="f1_anti" id="f1_anti" 
                                          method="post" >
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Date From</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control date-picker" 
                                                               id="from_ant" name="from_ant"  data-date-format="dd/mm/yyyy"
                                                               onkeypress="return keysub(this, event);"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Date To</label>
                                                    <div class="col-md-9">
                                                        <input type="text" class="form-control date-picker" 
                                                               id="to_ant" name="to_ant"  data-date-format="dd/mm/yyyy"
                                                               onkeypress="return keysub(this, event);"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Type</label>
                                                    <div class="col-md-9">
                                                        <select class="form-control select2" name="sendtype" placeholder="...">
                                                            <option value="A">Master Data</option>
                                                            <option value="R">Registration</option>
                                                        </select>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%}%>
                        
                        <div class="col-md-12">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-screen-desktop font-dark"></i>
                                        <span class="caption-subject font-dark bold uppercase">Monitor (Central)</span>
                                    </div>
                                    <div class="tools"> 
                                        <button type="button" class="btn btn-outline dark" onclick="return document.getElementById('mon_cen').submit();">
                                            <i class="fa fa-arrow-right"></i> Export File</button>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form class="form-horizontal" action="Operazioni?type=moncentr" role="form" method="post" id="mon_cen" >
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Branch</label>
                                                    <div class="col-md-9">
                                                        <select class="form-control select2" name="branch" id="branch" >
                                                            <option value="---" selected="selected">All</option>
                                                            <%for (int j = 0; j < array_branch.size(); j++) {%>
                                                            <option value="<%=array_branch.get(j).getCod()%>">
                                                                <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                            </option>
                                                            <%}%>

                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <%
                                if (request.getParameter("esito") != null) {

                                    if (request.getParameter("esito").equals("okcen")) {
                            %>                                
                            <div class="alert alert-success">
                                <b><center>The file has been successfully released.</center></b>
                            </div>
                            <%}
                                if (request.getParameter("esito").equals("kocen")) {

                            %>
                            <div class="alert alert-danger">
                                <b><center>Error. The file was not released. Try again.</center></b>
                            </div>
                            <%}%>
                            <%}%>
                        </div>
                        <%} else {%>
                        <div class="col-md-12">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-screen-desktop font-dark"></i>
                                        <span class="caption-subject font-dark bold uppercase">Monitor (Branch)</span>
                                    </div>
                                    <div class="tools"> 
                                        <button type="button" class="btn btn-outline dark"  onclick="return document.getElementById('mon_fil').submit();">
                                            <i class="fa fa-arrow-right"></i> Export File</button>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form class="form-horizontal" action="Operazioni?type=monfil" role="form" method="post" id="mon_fil" >
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">Branch</label>
                                                    <div class="col-md-9">
                                                        <%Branch test = Engine.get_branch(Engine.getFil()[0]);%>
                                                        <select class="form-control select2" name="branch" id="branch" >
                                                            <option value="<%=test.getCod()%>"> <%=test.getCod()%> - <%=test.getDe_branch()%> </option>
                                                        </select>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <%
                                if (request.getParameter("esito") != null) {

                                    if (request.getParameter("esito").equals("okfil")) {
                            %>                                
                            <div class="alert alert-success">
                                <b><center>The file has been successfully released</center></b>
                            </div>
                            <%}
                                if (request.getParameter("esito").equals("kofil")) {

                            %>
                            <div class="alert alert-danger">
                                <b><center>Error. The file was not released. Try again.</center></b>
                            </div>
                            <%}%>
                            <%}%>
                        </div>
                        <%}%>
                        
                    </div>
                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-warning"> <i class="fa fa-exclamation-triangle"></i> You are not allowed to export files.
                            </div>
                        </div>
                    </div>
                    <%}%>
                    <%}else{%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>This feature is not available in your country.</strong>
                            </div>
                        </div>
                    </div>
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
            <!-- BEGIN THEME LAYOUT SCRIPTS -->
            <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
            <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
            <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
            <!-- END CORE PLUGINS -->

            <!-- BEGIN THEME GLOBAL SCRIPTS -->


            <!-- END THEME LAYOUT SCRIPTS -->
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
