<%@page import="rc.so.entity.Codici_sblocco_file"%>
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
        <title>Mac2.0 - Unlock Codes</title>
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
            <%@ include file="menu/menu_ma24.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);

                ArrayList<Codici_sblocco_file> list_codici_sblocco_file = Engine.list_codici_sblocco_file();
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
                            <h3 class="page-title">Maintenance <small><b>Unlock Codes</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <%if (central && tipo.equals("3")) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <a href="tb_edit_unlock.jsp" class="btn btn-outline dark fancyBoxRafreload"><i class="fa fa-plus-circle"></i> Generate Unlock Codes</a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Unlock Codes Files</span>
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
                                                                    <th class="tabnow">Id</th>
                                                                    <th class="tabnow">Number of codes</th>
                                                                    <th class="tabnow">Assigned to</th>
                                                                    <th class="tabnow">Date</th>
                                                                    <th class="tabnow"style="width: 100px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    for (int i = 0; i < list_codici_sblocco_file.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=list_codici_sblocco_file.get(i).getId()%></td>
                                                                    <td><%=list_codici_sblocco_file.get(i).getNumcod()%></td>
                                                                    <td><%=list_codici_sblocco_file.get(i).getDest()%></td>
                                                                    <td><%=Utility.formatStringtoStringDate(list_codici_sblocco_file.get(i).getDt_oper(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss")%></td>
                                                                    <td>
                                                                        <a href="Download?type=view_list_cod&cod=<%=list_codici_sblocco_file.get(i).getId()%>&doc=pdf" target="_blank" class="btn btn-sm red btn-outline btn-circle tooltips" title="Pdf" data-container="body">
                                                                            <i class="fa fa-file-pdf-o"></i>
                                                                        </a>
                                                                        <a href="Download?type=view_list_cod&cod=<%=list_codici_sblocco_file.get(i).getId()%>&doc=excel" target="_blank" class="btn btn-sm green-jungle btn-outline btn-circle tooltips" title="Excel" data-container="body">
                                                                            <i class="fa fa-file-excel-o"></i>
                                                                        </a>
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
                    </div>


                    <hr>
                    <div class="row">
                        <%if (request.getParameter("search") == null) {%>
                        <form name="f1" method="post" action="tb_unlock.jsp">
                            <input type="hidden" name="search" value="ok1"/>
                            <div class="col-md-12">
                                <div class="portlet box red">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-search"></i> Search Code</div>
                                        <div class="tools">
                                            <a href="javascript:;" class="collapse"> </a>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label>Code</label>
                                                        <div class="form-group">
                                                            <input type="text" class="form-control" id="codice" name="codice" 
                                                                   maxlength="10" onchange="return fieldNOSPecial_1(this.id)" />
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label>Status</label>
                                                        <div class="form-group">
                                                            <select class="form-control select2" id="status" name="status">
                                                                <option value="...">...</option>
                                                                <option value="E">UNUSED</option>
                                                                <option value="U">USED</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label><p class='ab'></p>
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <%} else {%>
                        <form name="f1" method="post" action="tb_unlock.jsp">
                            <input type="hidden" name="search" value="ok1"/>
                            <div class="col-md-12">
                                <div class="portlet box red">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-search"></i> Search Code</div>
                                        <div class="tools">
                                            <a href="javascript:;" class="collapse"> </a>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label>Code</label>
                                                        <div class="form-group">
                                                            <input type="text" class="form-control" id="codice" name="codice" 
                                                                   maxlength="10" onchange="return fieldNOSPecial_1(this.id)" 
                                                                   value="<%=Utility.sanitize(request.getParameter("codice"))%>"/>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label>Status</label>
                                                        <div class="form-group">
                                                            <select class="form-control select2" id="status" name="status">
                                                                <%
                                                                    if (request.getParameter("status").equals("...")) {%>
                                                                <option value="..." selected>...</option>
                                                                <option value="E">UNUSED</option>
                                                                <option value="U">USED</option>
                                                                <%} else if (request.getParameter("status").equals("E")) {%>
                                                                <option value="...">...</option>
                                                                <option value="E" selected>UNUSED</option>
                                                                <option value="U">USED</option>
                                                                <%} else if (request.getParameter("status").equals("U")) {%>
                                                                <option value="...">...</option>
                                                                <option value="E">UNUSED</option>
                                                                <option value="U" selected>USED</option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label>&nbsp;</label><p class='ab'></p>
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                        <a href="tb_unlock.jsp" class="btn btn-outline red" ><i class="fa fa-refresh"></i> Reset</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>

                        <%

                            ArrayList<String[]> li = Engine.query_cs(request.getParameter("codice"), request.getParameter("status"));

                        %>

                        <div class="col-md-12">
                            <div class="portlet box yellow">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Unlock Codes</span>
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
                                                        <table class="table table-responsive table-bordered table-hover" id="sample_1" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Code</th>
                                                                    <th class="tabnow">Assigned To</th>
                                                                    <th class="tabnow">Date</th>
                                                                    <th class="tabnow">Status</th>
                                                                    <th class="tabnow"style="width: 100px;">Actions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%                                                                    for (int i = 0; i < li.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=Utility.sanitize(li.get(i)[0])%></td>
                                                                    <td><%=Utility.sanitize(li.get(i)[1])%></td>
                                                                    <td><%=Utility.sanitize(li.get(i)[2])%></td>
                                                                    <td><%=Utility.sanitize(li.get(i)[3])%></td>
                                                                    <%if (li.get(i)[3].equals("USED")) {%>
                                                                    <td>
                                                                        <a href="tb_unlock_view.jsp?code=<%=Utility.sanitize(li.get(i)[0])%>" class="btn btn-sm yellow btn-outline btn-circle tooltips fancyBoxRaf" 
                                                                           title="View usage details" data-container="body" >
                                                                            <i class="fa fa-eye"></i>
                                                                        </a>
                                                                    </td>
                                                                    <%} else {%>
                                                                    <td>-</td>
                                                                    <%}%>
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


                        <%}%>
                    </div>



                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-warning">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> You don't have permission to access on this page.
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
                                                                                       {orderable: 1, targets: [3]},
                                                                                       {orderable: !1, targets: [4]}
                                                                                   ],
                                                                                   scrollX: true,
                                                                                   buttons: [
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
                                    {orderable: !1, targets: [4]}
                                ],
                                scrollX: true,
                                buttons: [
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
