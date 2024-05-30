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
        <title>Mac2.0 - Website - Maintenance</title>
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
        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/soop/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>



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
            <%@ include file="menu/menu_es1A.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                String fil = Engine.getFil()[0];
                ArrayList<String[]> list_spread = Engine.list_spread();
                ArrayList<String[]> list_commissione_fissa = Engine.list_commissione_fissa();
                ArrayList<String[]> list_supporti = Engine.list_supporti();
                ArrayList<String[]> list_causali_variazioni = Engine.list_causali_variazioni();
                ArrayList<String[]> list_agevolazioni_varie = Engine.list_agevolazioni_varie();
                ArrayList<String[]> list_servizi_agg = Engine.list_servizi_agg();
                ArrayList<String[]> list_site_levelrate = Engine.list_site_levelrate();

                ArrayList<String[]> array_kind = Engine.list_all_kind(fil);

                boolean central = Engine.isCentral();
                String tipo = (String) request.getSession().getAttribute("us_tip");
                if (tipo == null) {
                    tipo = "";
                }
                //tipo="1";
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
                            <h3 class="page-title">External Services <small><b>Website - Maintenance</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT TILL -->
                    
                    
                     <%if (Constant.is_IT) {%>
                    <%if(true){%>
                    <div class="row">
                        <%if (central && tipo.equals("3")) {%>
                        <div class="col-md-6 col-md-offset-6" style="display:none;">
                            <div class="form-group">
                                <a href="tb_wb_edit_fixcomm.jsp" class="btn btn-outline blue-hoki fancyBoxRafreload"><i class="fa fa-plus-circle"></i> New Fix Commission</a>
                            </div>
                        </div>
                        <%}%>
                        <div class="col-md-6">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Spread</span>
                                    </div>
                                    <div class="actions"></div>
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
                                                                    <th class="tabnow">BCE Rate Code</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    for (int i = 0; i < list_spread.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=list_spread.get(i)[0]%></td>
                                                                    <td><%=list_spread.get(i)[2]%></td>
                                                                    <td><%=Utility.formatMysqltoDisplay(list_spread.get(i)[3])%></td>
                                                                    <td><%if (central && tipo.equals("3")) {%>
                                                                        <a href="tb_wb_edit_spread.jsp?cur_code=<%=list_spread.get(i)[0]%>" container="body" data-trigger="hover" 
                                                                           data-container="body" data-placement="top" data-content="Edit"
                                                                           class="btn btn-sm blue btn-outline btn-circle fancyBoxRafreload popovers" ><i class="fa fa-wrench"></i></a>
                                                                            <%}%>
                                                                    </td>
                                                                </tr>  
                                                                <%}
                                                                %>
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
                        <div class="col-md-6">
                            <div class="portlet box blue-hoki">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Fix Commission</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_1" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Kind</th>
                                                                    <th class="tabnow">Min</th>
                                                                    <th class="tabnow">Max</th>
                                                                    <th class="tabnow">Buy</th>
                                                                    <th class="tabnow">Sell</th>
                                                                    <th class="tabnow">Status</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    for (int i = 0; i < list_commissione_fissa.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=Utility.formatAL(list_commissione_fissa.get(i)[0], array_kind, 1)%></td>
                                                                    <td><%=Utility.formatMysqltoDisplay(list_commissione_fissa.get(i)[1])%></td>
                                                                    <td><%=Utility.formatMysqltoDisplay(list_commissione_fissa.get(i)[2])%></td>
                                                                    <td>-</td>
                                                                    <td><%=Utility.formatMysqltoDisplay(list_commissione_fissa.get(i)[3])%></td>
                                                                    <td><%=Engine.formatStatus_general(list_commissione_fissa.get(i)[4])%></td>
                                                                    <td><%if (central && tipo.equals("3")) {%>
                                                                        <a href="tb_wb_edit_fixcomm.jsp?fi_min=<%=list_commissione_fissa.get(i)[1]%>"
                                                                           container="body" data-trigger="hover" 
                                                                           data-container="body" data-placement="top" data-content="Edit"
                                                                           class="btn btn-sm blue-hoki btn-outline btn-circle fancyBoxRafreload popovers">
                                                                            <i class="fa fa-wrench"></i></a>
                                                                            <%}%>
                                                                    </td>
                                                                </tr>   
                                                                <%}
                                                                %>
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
                        <div class="col-md-12">
                            <hr>
                        </div>
                        <%if (central && tipo.equals("3")) {%>
                        <div class="col-md-6 col-md-offset-6" style="display:none;">
                            <div class="form-group">
                                <a href="" onclick="return false;" class="btn btn-outline blue-madison"><i class="fa fa-plus-circle"></i> New Error Description</a>
                            </div>
                        </div>
                        <%}%>                                    
                        <div class="col-md-6">
                            <div class="portlet box blue-steel">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - % Commission</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_2" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow"style="width: 70px;">Code</th>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">Sell Commission</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    for (int i = 0; i < list_supporti.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=list_supporti.get(i)[0]%></td>
                                                                    <td><%=Utility.formatAL(list_supporti.get(i)[0], array_kind, 1)%></td>
                                                                    <td><%=Utility.formatMysqltoDisplay(list_supporti.get(i)[1])%></td>
                                                                    <td><%if (central && tipo.equals("3")) {%>
                                                                        <a href="tb_wb_edit_figures.jsp?fi_code=<%=list_supporti.get(i)[0]%>" container="body" data-trigger="hover" 
                                                                           data-container="body" data-placement="top" data-content="Edit"
                                                                           class="btn btn-sm blue-steel btn-outline btn-circle fancyBoxRafreload popovers">
                                                                            <i class="fa fa-wrench"></i></a>
                                                                            <%}%>
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
                        <div class="col-md-6">
                            <div class="portlet box blue-madison">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Error Description</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_3" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Code</th>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">Note</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    for (int i = 0; i < list_causali_variazioni.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=list_causali_variazioni.get(i)[0]%></td>
                                                                    <td><%=list_causali_variazioni.get(i)[1]%></td>
                                                                    <td><%=list_causali_variazioni.get(i)[2]%></td>
                                                                    <td><%if (central && tipo.equals("3")) {%>
                                                                        <a href="tb_wb_edit_error.jsp?err_code=<%=list_causali_variazioni.get(i)[0]%>" container="body" data-trigger="hover" 
                                                                           data-container="body" data-placement="top" data-content="Edit"
                                                                           class="btn btn-sm blue-madison btn-outline btn-circle fancyBoxRafreload popovers" ><i class="fa fa-wrench"></i></a>
                                                                            <%}%>
                                                                    </td>
                                                                </tr>   
                                                                <%}
                                                                %>
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
                        <div class="col-md-12">
                            <hr>
                        </div>
                        <%if (central && tipo.equals("3")) {%>
                        <div class="col-md-6">
                            <div class="form-group">
                                <a href="tb_wb_edit_discounts.jsp" class="btn btn-outline blue fancyBoxRafreload"><i class="fa fa-plus-circle"></i> New Discounts</a>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <a href="tb_wb_edit_services.jsp"  class="btn btn-outline blue-sharp fancyBoxRafreload"><i class="fa fa-plus-circle"></i> New Additional Services</a>
                            </div>
                        </div>
                        <%}%>  
                        <div class="col-md-6">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Discounts</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_4" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Code</th>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">Branch</th>
                                                                    <th class="tabnow">Currency</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for (int i = 0; i < list_agevolazioni_varie.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=list_agevolazioni_varie.get(i)[0]%></td>
                                                                    <td><%=list_agevolazioni_varie.get(i)[1]%></td>
                                                                    <td><%=list_agevolazioni_varie.get(i)[9]%></td>
                                                                    <td><%=list_agevolazioni_varie.get(i)[8]%></td>
                                                                    <td><%if (central && tipo.equals("3")) {%>
                                                                        <a href="tb_wb_edit_discounts.jsp?code=<%=list_agevolazioni_varie.get(i)[0]%>" container="body" data-trigger="hover" 
                                                                           data-container="body" data-placement="top" data-content="Edit"
                                                                           class="btn btn-sm blue btn-outline btn-circle fancyBoxRafreload popovers" ><i class="fa fa-wrench"></i></a>
                                                                            <%}%>
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
                        <div class="col-md-6">
                            <div class="portlet box blue-sharp">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Additional Services</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_5" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Code</th>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">Branch</th>
                                                                    <th class="tabnow">Currency</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    for (int i = 0; i < list_servizi_agg.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=list_servizi_agg.get(i)[0]%></td>
                                                                    <td><%=list_servizi_agg.get(i)[1]%></td>
                                                                    <td><%=list_servizi_agg.get(i)[9]%></td>
                                                                    <td><%=list_servizi_agg.get(i)[8]%></td>
                                                                    <td><%if (central && tipo.equals("3")) {%>
                                                                        <a href="tb_wb_edit_services.jsp?code=<%=list_servizi_agg.get(i)[0]%>" 
                                                                           container="body" data-trigger="hover" 
                                                                           data-container="body" data-placement="top" data-content="Edit"
                                                                           class="btn btn-sm blue-sharp btn-outline btn-circle fancyBoxRafreload popovers" ><i class="fa fa-wrench"></i></a>
                                                                        <%}%>
                                                                    </td>
                                                                </tr>   
                                                                <%}
                                                                %>
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
                        <div class="col-md-12">
                            <hr>
                        </div>
                        <div class="col-md-6">
                            <div class="portlet box blue-chambray">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Level Rate</span>
                                    </div>
                                    <div class="actions"></div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_6" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Kind</th>
                                                                    <th class="tabnow">Min</th>
                                                                    <th class="tabnow">Max</th>
                                                                    <th class="tabnow">Applied Level</th>
                                                                    <th class="tabnow"style="width: 70px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for (int i = 0; i < list_site_levelrate.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=Utility.formatAL(list_site_levelrate.get(i)[0], array_kind, 1)%></td>
                                                                    <td><%=Utility.formatMysqltoDisplay(list_site_levelrate.get(i)[1])%></td>
                                                                    <td><%=Utility.formatMysqltoDisplay(list_site_levelrate.get(i)[2])%></td>
                                                                    <td><%=list_site_levelrate.get(i)[3]%></td>
                                                                    <td><%if (central && tipo.equals("3")) {%>
                                                                        <a href="tb_wb_edit_raterange.jsp?ra_code=<%=list_site_levelrate.get(i)[0]%>&ra_min=<%=list_site_levelrate.get(i)[1]%>" container="body" data-trigger="hover" 
                                                                           data-container="body" data-placement="top" data-content="Edit"
                                                                           class="btn btn-sm blue-chambray btn-outline btn-circle fancyBoxRafreload popovers" ><i class="fa fa-wrench"></i></a>
                                                                            <%}%>
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
                    <%}else{%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-warning">
                                <strong>This feature will be activated soon.</strong>
                            </div>
                        </div>
                    </div>
                    <%}%>
                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>This feature is not available in your country.</strong>
                            </div>
                        </div>
                    </div>
                    <%}%> 
                    <!-- END CONTENT -->
                    <!-- BEGIN QUICK SIDEBAR -->
                    <!-- END QUICK SIDEBAR -->
                </div>
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
                                                columnDefs: [
                                                    {orderable: 1, targets: [0]},
                                                    {orderable: 1, targets: [1]},
                                                    {orderable: 1, targets: [2]},
                                                    {orderable: !1, targets: [3]}
                                                ],
                                                buttons: [
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
                                                    [10, 50, 100, -1],
                                                    [10, 50, 100, "All"]
                                                ],
                                                pageLength: 10,
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
                    jQuery(document).ready(function () {
                        var dt1 = function () {
                            var f = $("#sample_2");
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
                                    {orderable: 1, targets: [0]},
                                    {orderable: 1, targets: [1]},
                                    {orderable: 1, targets: [2]},
                                    {orderable: !1, targets: [3]}
                                ],
                                scrollX: true,
                                buttons: [
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
                                    [10, 50, 100, -1],
                                    [10, 50, 100, "All"]
                                ],
                                pageLength: 10,
                                order: [],
                                dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                            });

                        };
                        jQuery().dataTable && dt1();
                    });
                </script>
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
                                    {orderable: 1, targets: [0]},
                                    {orderable: 1, targets: [1]},
                                    {orderable: 1, targets: [2]},
                                    {orderable: 1, targets: [3]},
                                    {orderable: 1, targets: [4]},
                                    {orderable: 1, targets: [5]},
                                    {orderable: !1, targets: [6]}
                                ],
                                scrollX: true,
                                buttons: [
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
                                    [10, 50, 100, -1],
                                    [10, 50, 100, "All"]
                                ],
                                pageLength: 10,
                                order: [],
                                dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                            });

                        };
                        jQuery().dataTable && dt1();
                    });
                </script>
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        var dt1 = function () {
                            var f = $("#sample_3");
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
                                    {orderable: 1, targets: [0]},
                                    {orderable: 1, targets: [1]},
                                    {orderable: 1, targets: [2]},
                                    {orderable: !1, targets: [3]}
                                ],
                                scrollX: true,
                                buttons: [
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
                                    [10, 50, 100, -1],
                                    [10, 50, 100, "All"]
                                ],
                                pageLength: 10,
                                order: [],
                                dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                            });

                        };
                        jQuery().dataTable && dt1();
                    });
                </script>
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        var dt1 = function () {
                            var f = $("#sample_4");
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
                                    {orderable: 1, targets: [0]},
                                    {orderable: 1, targets: [1]},
                                    {orderable: 1, targets: [2]},
                                    {orderable: 1, targets: [3]},
                                    {orderable: !1, targets: [4]}
                                ],
                                scrollX: true,
                                buttons: [
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
                                    [10, 50, 100, -1],
                                    [10, 50, 100, "All"]
                                ],
                                pageLength: 10,
                                order: [],
                                dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                            });

                        };
                        jQuery().dataTable && dt1();
                    });
                </script>
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        var dt1 = function () {
                            var f = $("#sample_5");
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
                                    {orderable: 1, targets: [0]},
                                    {orderable: 1, targets: [1]},
                                    {orderable: 1, targets: [2]},
                                    {orderable: 1, targets: [3]},
                                    {orderable: !1, targets: [4]}
                                ],
                                scrollX: true,
                                buttons: [
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
                                    [10, 50, 100, -1],
                                    [10, 50, 100, "All"]
                                ],
                                pageLength: 10,
                                order: [],
                                dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                            });

                        };
                        jQuery().dataTable && dt1();
                    });
                </script>
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        var dt1 = function () {
                            var f = $("#sample_6");
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
                                    {orderable: 1, targets: [0]},
                                    {orderable: 1, targets: [1]},
                                    {orderable: 1, targets: [2]},
                                    {orderable: 1, targets: [3]},
                                    {orderable: !1, targets: [4]}
                                ],
                                scrollX: true,
                                buttons: [
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
                                    [10, 50, 100, -1],
                                    [10, 50, 100, "All"]
                                ],
                                pageLength: 10,
                                order: [],
                                dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
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