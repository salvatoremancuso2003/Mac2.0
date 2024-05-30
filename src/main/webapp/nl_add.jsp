<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Users"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Mac2.0 - Newsletter</title>
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

            function checkfile() {
                var oFile = document.getElementById("filepdf");
                var title = document.getElementById("title");
                var descr = document.getElementById("descr");
                var dates = document.getElementById("dates");

                if (oFile.value.trim() === "" || title.value.trim() === "" || descr.value.trim() === "" || dates.value.trim() === "") {
                    var ermsg = "You must complete all fields.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

                if (oFile.value.trim() !== "") {
                    var blnValid = false;
                    var sCurExtension = ".pdf";
                    if (oFile.value.substr(oFile.value.length - sCurExtension.length, sCurExtension.length).toLowerCase() === sCurExtension.toLowerCase()) {
                        blnValid = true;
                    }
                    if (!blnValid) {
                        var ermsg = "File type must be pdf.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                    if (oFile.files[0].size > 3145728) {
                        var ermsg = "File size must under 3mb.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }
            }

            function loadpage() {
                online();
                inputvirgola();
            }

        </script>


    </head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">
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
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_nl1.jsp"%>
            <!-- END MENU -->

            <%                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);

                ArrayList<Users> result = Engine.list_all_users();

                String datestr = Utility.getDateStartNL();

            %>


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
                            <h3 class="page-title">Newsletter <small><b>Add</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>

                    <%                        boolean central = Engine.isCentral();
                        String tipo = (String) request.getSession().getAttribute("us_tip");
                        if (tipo == null) {
                            tipo = "";
                        }
                        //tipo="1";
                        if (central && tipo.equals("3")) {
                    %>

                    <div class="row">
                        <div class="col-md-12">
                            <form class="form-horizontal" action="Operazioni?type=upload_nl" method="POST" enctype="multipart/form-data" name="f1" onsubmit="return checkfile();">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="portlet box blue">
                                            <div class="portlet-title">
                                                <div class="caption">
                                                    <i class="fa fa-plus-circle"></i>
                                                    <span class="caption-subject">Add Newsletter</span>
                                                </div>
                                                <div class="tools">
                                                    <a title="" data-original-title="" href="javascript:;" class="collapse"> </a>
                                                </div>
                                            </div>
                                            <div class="portlet-body">

                                                <div class="form-body">


                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Recipients</label>
                                                        <div class="col-md-6">
                                                            <select class="form-control select2" id="reci" name="reci" placeholder="..." onkeypress="return keysub(this, event);">
                                                                <option value="---">All</option>
                                                                <%for (int i = 0; i < result.size(); i++) {%>

                                                                <option value="<%=result.get(i).getCod()%>" ><%=result.get(i).getCod()%> - <%=result.get(i).getDe_cognome()%> <%=result.get(i).getDe_nome()%></option>

                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Title</label>
                                                        <div class="col-md-6">
                                                            <div class="input-icon">
                                                                <i class="fa fa-font font-blue"></i>
                                                                <input type="text" class="form-control" name="title" maxlength="100"
                                                                       id="title" onkeypress="return keysub(this, event);"> </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description</label>
                                                        <div class="col-md-6">
                                                            <div class="input-icon">
                                                                <i class="fa fa-font font-blue"></i>
                                                                <input type="text" class="form-control" name="descr" id="descr" maxlength="100"
                                                                       onkeypress="return keysub(this, event);"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3">File </label>
                                                        <div class="col-md-6">
                                                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                                                <div class="input-group input-large">
                                                                    <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                                        <i class="fa fa-file-o fileinput-exists font-blue"></i>&nbsp;
                                                                        <span class="fileinput-filename"> </span>
                                                                    </div>
                                                                    <span class="input-group-addon btn default btn-file">
                                                                        <span class="fileinput-new"> Select file </span>
                                                                        <span class="fileinput-exists"> Change </span>
                                                                        <input type="file" name="filepdf" id="filepdf" accept="application/pdf" 
                                                                               onkeypress="return keysub(this, event);"> </span>
                                                                    <a href="javascript:;" class="input-group-addon btn red fileinput-exists" 
                                                                       data-dismiss="fileinput"> Remove </a>
                                                                </div>
                                                                <span class="help-block"><small>Only file .pdf - Size max 3 Mb</small></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="control-label col-md-3">Date start</label>
                                                        <div class="col-md-6">
                                                            <input type="text" class="form-control form_datetime" value="<%=datestr%>" 
                                                                   name="dates" id="dates" onkeypress="return keysub(this, event);">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">    
                                                        <div class="col-md-9 col-md-offset-3">
                                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                                            <a href="nl_add.jsp" class="btn btn-outline red"><i class="fa fa-trash-o"></i> Reset</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
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
                        if (esito.equals("0")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        } else if (esito.equals("1") || esito.equals("2") || esito.equals("3") || esito.equals("4")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "Operation completed successfully. The file is corrupted or not in accordance with pdf.";
                        } else if (esito.equals("5")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. Please try again.";
                        }
                        if (!esito.equals("")) {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
                    <%}%>

                    
                            <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box red">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Manage - Newsletters</span>
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
                                                                    <th class="tabnow">Title</th>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">Date</th>
                                                                    <th class="tabnow"style="width: 100px;">Actions</th>
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
                            
                       



                    <%} else {%>
                    <div class="alert alert-danger">
                        You are not authorized to add newsletter.
                    </div>
                    <%}%>






                </div>
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
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        
        
        
        
        
        <!-- END THEME LAYOUT SCRIPTS -->

        
        <!-- END PAGE LEVEL PLUGINS -->
               
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
                                            ajax: {
                                                url: "Query?type=query_newsletters_man",
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
                                                ]
                                            ,initComplete: function (settings, json) {
                                                        $('.popovers').popover();
                                                    },
                                            colReorder: {reorderCallback: function () {
                                                    
                                                }},
                                            lengthMenu: [
                                                [25, 50, 100, -1],
                                                [25, 50, 100, "All"]
                                            ],
                                            pageLength: 25,
                                            order: [],
                                            dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
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
