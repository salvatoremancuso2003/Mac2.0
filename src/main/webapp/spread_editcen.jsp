
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Etichette"%>
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
        <title>Mac2.0 - Edit Spread</title>
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
        <!-- FANCYBOX -->
        <script type="text/javascript" src="assets/soop/js/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>
        
        
        
        
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script type="text/javascript">
            function valid(sel, val) {
                var verified = document.getElementById(sel);
                if (verified.checked) {
                    $('#' + val).val('').change();
                    document.getElementById(val).disabled = true;
                } else {
                    document.getElementById(val).disabled = false;
                }
            }

            function checkdescr() {
                var seall = document.getElementById('seall').checked;
                var branch = document.getElementById('branch').value;
                if (branch === "" && !seall) {
                    var ermsg = "You must select at least one branch.";
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

        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->

            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);

            %>

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

            <!-- BEGIN CONTENT -->
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
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Maintenance Currency <small><b>Edit Spread</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                        <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->


                    <%
                        ArrayList<Branch> array_branch = Engine.list_branch_enabled(); 
                        String listbranch = "";
                    %>

                    <%if (request.getParameter("search") == null) {%>
                    <form name="f1" method="post" action="spread_editcen.jsp" onsubmit="return checkdescr();">
                        <input type="hidden" name="search" value="ar1"/>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet box green">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-search"></i> Select Branch</div>
                                        <div class="tools">
                                            <a href="javascript:;" class="collapse"> </a>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label>Branch</label>
                                                    <select class="form-control select2" name="branch" id="branch" multiple>
                                                        <%for (int j = 0; j < array_branch.size(); j++) {%>
                                                        <option value="<%=array_branch.get(j).getCod()%>">
                                                            <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label>&nbsp;</label><br/>
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
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label>&nbsp;</label><p class='ab'></p>
                                                    <button type="submit" class="btn btn-outline dark" ><i class="fa fa-check"></i> Confirm</button>
                                                    <a href="spread_editcen.jsp" class="btn btn-outline red" ><i class="fa fa-remove"></i> Cancel</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else if (request.getParameter("search").equals("ar1")) {
                        String di = "";
                        String ch = "";
                        String[] a = request.getParameterValues("branch");
                        if (a == null) {
                            di = "disabled";
                            ch = "checked";
                            for (int j = 0; j < array_branch.size(); j++) {
                                listbranch = listbranch + array_branch.get(j).getCod()+";";
                            }
                        } else {
                            listbranch = Utility.parseArrayValues(a);
                        }


                    %>
                    <form name="f1" method="post" action="spread_editcen.jsp" onsubmit="return checkdescr();">
                        <input type="hidden" name="search" value="ar1"/>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet box green">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="fa fa-search"></i> Select Branch</div>
                                        <div class="tools">
                                            <a href="javascript:;" class="collapse"> </a>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label>Branch</label>
                                                    <select class="form-control select2" name="branch" id="branch" multiple <%=di%>>
                                                        <%

                                                            for (int j = 0; j < array_branch.size(); j++) {
                                                                if (a != null) {
                                                                    for (int i = 0; i < a.length; i++) {
                                                                        if (a[i].equals(array_branch.get(j).getCod())) {%>
                                                        <option selected value="<%=array_branch.get(j).getCod()%>"><%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%></option>
                                                        <%} else {%>
                                                        <option value="<%=array_branch.get(j).getCod()%>"><%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%></option>
                                                        <%}%>
                                                        <%}%>
                                                        <%} else {%>
                                                        <option value="<%=array_branch.get(j).getCod()%>"><%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%></option>
                                                        <%}%>

                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label>&nbsp;</label><br/>
                                                    <div class="md-checkbox">
                                                        <input type="checkbox" id="seall" name="seall" class="md-checkbox" onchange="return valid('seall', 'branch');" <%=ch%>> 
                                                        <label for="seall">
                                                            <span></span>
                                                            <span class="check"></span>
                                                            <span class="box"></span> Select All
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label>&nbsp;</label><p class='ab'></p>
                                                    <button type="submit" class="btn btn-outline dark" ><i class="fa fa-check"></i> Confirm</button>
                                                    <a href="spread_editcen.jsp" class="btn btn-outline red" ><i class="fa fa-remove"></i> Cancel</a>
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

                    <%}%>
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
        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <!-- END PAGE LEVEL PLUGINS -->
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
                                                                            url: "Query?type=tb_spread&listbranch=<%=Utility.sanitize(listbranch)%>",
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
                                                                            {extend: "excel", className: "btn white btn-outline", text: "<i class='fa fa-file-excel-o'></i> Excel"},
                                                                            {extend: "pdf", className: "btn white btn-outline", text: "<i class='fa fa-file-pdf-o'></i> Pdf"},
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
