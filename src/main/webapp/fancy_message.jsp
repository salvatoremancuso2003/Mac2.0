<%@page import="rc.so.util.Utility"%>
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



    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="document.getElementById('motiv1').focus();">
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
                    <div class="modal-header">
                        <h4 class="modal-title">Operation Result</h4>
                    </div>
                    <div class="modal-body">
                        <%
                            String esito = request.getParameter("esito");
                            if (esito != null) {
                                if (esito.equals("true")) {%>
                        <div class="alert alert-success">
                            <strong>Success!</strong> The operation was performed successfully.
                        </div>
                        <%}%>
                        <%if (esito.equals("0")) {%>
                        <div class="alert alert-success">
                            <strong>Success!</strong> The operation was performed successfully.
                        </div>
                        <%}%>
                        <%if (esito.equals("false")) {%>
                        <div class="alert alert-danger">
                            <strong>Error!</strong> During the operation, an error occurred. Please try again.
                        </div>
                        <%}%>
                        <%if (esito.equals("kk") || esito.equals("kokk")) {%>
                        <div class="alert alert-danger">
                            <strong>Error!</strong> During the operation, an error occurred. Transaction/Operation selected is already deleted.
                        </div>
                        <%}%>
                        <%if (esito.equals("CC")) {%>
                        <div class="alert alert-danger">
                            <strong>Error!</strong> During the operation, an error occurred. The till is already closed.
                        </div>
                        <%}%>
                        <%if (esito.equals("1")) {%>
                        <div class="alert alert-danger">
                            <strong>Error!</strong> During the operation, an error occurred. Please try again.
                        </div>
                        <%}%>
                        <%if (esito.equals("Q")) {%>
                        <div class="alert alert-danger">
                            <strong>Error!</strong> Unable to complete the operation. the available quantities are insufficient. Check the value.
                        </div>
                        <%}%>
                        <%if (esito.equals("Q2")) {%>
                        <div class="alert alert-danger">
                            <strong>Error!</strong> Quantity of figures exceeds the amount available in this till.
                        </div>
                        <%}%>
                    </div>
                    <%} else {%>

                    <%}%>
                </div>
            </div>
        </div>
        <!-- BEGIN CONTENT -->
        <!-- END PAGE TITLE-->
        <!-- END PAGE HEADER-->

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
            var dt = function () {
                var e = $("#sample_0");
                e.dataTable({
                    language: {aria: {},
                        sProcessing: "Processo...",
                        emptyTable: "Nessun risultato trovato",
                        info: "Mostra _START_ di _END_ su _TOTAL_ risultati",
                        infoEmpty: "Nessun risultato trovato",
                        infoFiltered: "(filtrati su _MAX_ totali)",
                        lengthMenu: "Mostra _MENU_", search: "Trova:",
                        zeroRecords: "Nessun risultato trovato",
                        paginate: {previous: "Precedente", next: "Successiva", last: "Ultima", first: "Prima"}},
                    bStateSave: !0,
                    lengthMenu: [
                        [25, 50, 100, -1],
                        [25, 50, 100, "All"]
                    ],
                    pageLength: 25,
                    columnDefs: [
                        {orderable: !1, targets: [0]},
                        {className: "tabnow", targets: [1]},
                        {searchable: !1, targets: [0]}
                    ],
                    buttons: [
                        {extend: "excel", className: "btn green-jungle btn-outline", text: "<i class='fa fa-file-excel-o'></i> Excel"}]
                    ,
                    colReorder: {reorderCallback: function () {
                            console.info("callback");
                        }},
                    order: [],
                    dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
                    processing: true
                });
                $("tfoot input").keyup(function () {
                    e.fnFilter(this.value, e.oApi._fnVisibleToColumnIndex(
                            e.fnSettings(), $("tfoot input").index(this)));
                });
                $("tfoot input").each(function (i) {
                    this.initVal = this.value;
                });
                $("tfoot input").focus(function () {
                    if (this.className === "form-control")
                    {
                        this.className = "form-control";
                        this.value = "";
                    }
                });
                $("tfoot input").blur(function (i) {
                    if (this.value === "")
                    {
                        this.className = "form-control";
                        this.value = this.initVal;
                    }
                });
            };
            jQuery().dataTable && dt();
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
