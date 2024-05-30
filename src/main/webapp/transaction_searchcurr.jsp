<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Engine"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String link_value = Engine.verifyUser(request);
    if(link_value!=null){
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
        <title>Mac2.0 - Search Currency</title>
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
        
        
        
        <%
            String decimal = Constant.decimal;
        %>


    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" style="height: 800px; background-color: white;" onload="return inputvirgola();">
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
        <div class="page-container page-content-white">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
            <div class="page-content-wrapper page-content-white">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content page-content-white">
                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <!--    VUOTO RAF  -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="clearfix"></div>






                    <%
                        ArrayList<Currency> array_cu = Engine.list_all_currency();
                        String search = request.getParameter("search");
                        if (search == null) {
                            search = "";
                        }
                        if (search.equals("")) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="transaction_searchcurr.jsp">
                        <input type="hidden" name="search" value="sra1" />
                        <div class="portlet light bordered">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="fa fa-search font-blue"></i>
                                    <span class="caption-subject font-blue bold uppercase">Search Currency</span>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Currency <span class="font-red">*</span></label>
                                    <div class="col-md-9">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <select class="form-control select2" id="curr" name="curr" placeholder="...">
                                                    <%for (int i = 0; i < array_cu.size(); i++) {%>
                                                    <option value="<%=array_cu.get(i).getCode()%>"><%=array_cu.get(i).getCode()%> - <%=array_cu.get(i).getDescrizione()%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-3 col-md-9 ">
                                        <button type="submit" class="btn btn-outline blue"><i class="fa fa-search"></i> Search</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else if (search.equals("sra1")) {

                        String codcur = request.getParameter("curr");
                        

                        ArrayList<String[]> res =  Engine.search_curr_till(codcur);

                    %>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="transaction_searchcurr.jsp">
                        <input type="hidden" name="search" value="sra1" />
                        <div class="portlet light bordered">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="fa fa-search font-blue"></i>
                                    <span class="caption-subject font-blue bold uppercase">Search Currency</span>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Currency <span class="font-red">*</span></label>
                                    <div class="col-md-9">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <select class="form-control select2" id="curr" name="curr" placeholder="...">
                                                    <%for (int i = 0; i < array_cu.size(); i++) {

                                                            if (codcur.equals(array_cu.get(i).getCode())) {%>
                                                    <option value="<%=array_cu.get(i).getCode()%>" selected="selected">
                                                        <%=array_cu.get(i).getCode()%> - <%=array_cu.get(i).getDescrizione()%>
                                                    </option>
                                                    <%} else {%>
                                                    <option value="<%=array_cu.get(i).getCode()%>"><%=array_cu.get(i).getCode()%> - <%=array_cu.get(i).getDescrizione()%></option>
                                                    <%}
                                                    %>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-offset-3 col-md-9 ">
                                        <button type="submit" class="btn btn-outline blue"><i class="fa fa-search"></i> Search</button>
                                    </div>
                                </div>
                            </div>
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
                                                                    <th class="tabnow">Currency</th>
                                                                    <th class="tabnow">Saf/Till</th>
                                                                    <th class="tabnow">Status</th>
                                                                    <th class="tabnow">Quantity</th>
                                                                    <th class="tabnow">Total</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for(int i = 0;i<res.size();i++){ 
                                                                    String[] re = res.get(i);
                                                                %>
                                                                <tr>
                                                                    <td><%=Utility.sanitize(re[0])%></td>
                                                                    <td><%=Utility.sanitize(re[1])%></td>
                                                                    <td><%=Utility.sanitize(re[2])%></td>
                                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(re[3]))%></td>
                                                                    <td><%=Utility.formatMysqltoDisplay(Utility.sanitize(re[4]))%></td>
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
                       columnDefs: [
                           {orderable: !1, targets: [0]},
                           {orderable: !1, targets: [1]},
                           {orderable: !1, targets: [2]},
                           {orderable: !1, targets: [3]},
                           {orderable: !1, targets: [4]}
                       ],
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