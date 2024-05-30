<%@page import="rc.so.entity.Ch_transaction_value"%>
<%@page import="rc.so.entity.Ch_transaction_refund"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.entity.Ch_transaction"%>
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
        <title>Mac2.0 - Refund</title>
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



        <script type="text/javascript">

            function loadvalori() {
                inputvirgola2();
                var status = document.getElementById('status').checked;
                if (!status) {
                    document.getElementById("me1").style.display = "none";
                    document.getElementById("ty1").style.display = "none";
                    document.getElementById("br1").style.display = "none";
                    document.getElementById("va1").style.display = "none";
                } else {
                    document.getElementById("me1").style.display = "";
                    var method = document.getElementById('Method');
                    if (method !== null) {
                        if (method.checked) {
                            document.getElementById("br1").style.display = "";
                            $('#Type').bootstrapSwitch('readonly', false);
                        } else {
                            $('#Type').bootstrapSwitch('state', false);
                            $('#Type').bootstrapSwitch('readonly', true);
                            document.getElementById("br1").style.display = "none";
                        }
                    } else {
                        $('#Type').bootstrapSwitch('state', false);
                        $('#Type').bootstrapSwitch('readonly', true);
                        document.getElementById("br1").style.display = "none";
                    }
                    document.getElementById("ty1").style.display = "";
                    var type = document.getElementById('Type');
                    if (type !== null) {
                        if (type.checked) {
                            document.getElementById("va1").style.display = "none";
                        } else {
                            document.getElementById("va1").style.display = "";
                        }
                    } else {
                        document.getElementById("va1").style.display = "none";
                    }
                }
            }




            function changeMeth() {
                var method = document.getElementById('Method');
                if (method !== null) {
                    if (method.checked) {
                        document.getElementById("br1").style.display = "";
                        //$('#Type').bootstrapSwitch('readonly', false);
                        $('#Type').bootstrapSwitch('state', false);
                        $('#Type').bootstrapSwitch('readonly', true);
                    } else {
                        $('#Type').bootstrapSwitch('state', false);
                        $('#Type').bootstrapSwitch('readonly', true);
                        document.getElementById("br1").style.display = "none";
                    }
                } else {
                    document.getElementById("br1").style.display = "none";
                }
            }

            function changeTy() {
                var type = document.getElementById('Type');
                if (type !== null) {
                    if (type.checked) {
                        document.getElementById("va1").style.display = "none";
                    } else {
                        document.getElementById("va1").style.display = "";
                    }
                } else {
                    document.getElementById("va1").style.display = "none";
                }
            }
            function inputvirgola2() {
                $(document).find('input').keydown(function (evt) {
                    if (evt.which === 110) {
                        if ($(this).val().endsWith(",")) {
                        } else {
                            $(this).val($(this).val() + ',');
                        }
                        evt.preventDefault();
                    };
                });
            }


        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return loadvalori();" style="height: 600px;">
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
                    <%String code = request.getParameter("code");
                        if (code != null) {
                            ArrayList<Branch> array_branch = Engine.list_branch_enabled();
                            String decimal = Constant.decimal;
                            String thousand = Constant.thousand;
                            Ch_transaction ch = Engine.query_transaction_ch(code);

                            boolean iscashadvance = false;
                            ArrayList<Ch_transaction_value> list = Engine.query_transaction_value(code);
                            for (int x = 0; x < list.size(); x++) {
                                if (list.get(x).getSupporto().equals("04")) {
                                    iscashadvance = true;
                                    break;
                                }
                            }

                            Ch_transaction_refund ref = new Ch_transaction_refund(ch);

                            String update = "false";
                            String st = "";
                            if (ch.getRefund().equals("1")) {
                                st = "checked";
                                ref = Engine.get_refund_trans(ch.getCod());
                                if (ref == null) {
                                    ref = new Ch_transaction_refund(ch);
                                } else {
                                    update = "true";
                                }
                            }

                            String me = "";
                            if (ref.getMethod().equals("BR")) {
                                me = "checked";
                            }
                            String ty = "";
                            if (ref.getType().equals("CO")) {
                                ty = "checked";
                            }

                            if (iscashadvance) {
                                ty = "readonly";
                            }


                    %>
                    <form id="deletetrmod" class="form-horizontal" name="deletetrmod" action="Operazioni?type=edit_ref" method="post">
                        <input type="hidden" name="idtr" value="<%=Utility.sanitize(ch.getCod())%>"/>
                        <input type="hidden" name="idref" value="<%=Utility.sanitize(ref.getCod())%>"/>
                        <input type="hidden" name="update" value="<%=Utility.sanitize(update)%>"/>
                        <input type="hidden" name="completevalue" value="<%=Utility.sanitize(ch.getPay())%>"/>
                        <div class="portlet light bordered">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="icon-wrench font-blue"></i>
                                    <span class="caption-subject font-blue bold uppercase">Refund Transaction</span>
                                </div>
                                <div class="tools"> 
                                    <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="form-body">

                                    <div class="row">
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Status</label>
                                            <div class="col-md-9">
                                                <input type="checkbox" class="make-switch" onchange="return loadvalori();" <%=st%>
                                                       name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                       data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                            </div>
                                        </div>



                                        <div class="form-group"  id="me1">
                                            <label class="col-md-3 control-label">Method</label>
                                            <div class="col-md-9">
                                                <input type="checkbox" class="make-switch" onchange="return changeMeth();" <%=me%>
                                                       name="method" id="Method" data-size="normal" data-on-color="info" data-off-color="default"
                                                       data-on-text="<span class='tabnow'>Branch</span>" data-off-text="<span class='tabnow'>Transfer</span>">
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group" id="br1">

                                            <label class="col-md-3 control-label">Branch</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" readonly 
                                                       value="<%=Engine.formatBankBranch(ref.getBranch_cod(), "BR", null, array_branch,null)%>"/>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group" id="ty1">
                                            <label class="col-md-3 control-label">Type</label>
                                            <div class="col-md-9">
                                                <input type="checkbox" class="make-switch large" onchange="return changeTy();" readonly
                                                       name="typeref" id="Type" data-size="normal" data-on-color="info" data-off-color="default"
                                                       data-on-text="<span class='tabnow'>&nbsp;Complete&nbsp;</span>" data-off-text="<span class='tabnow'>&nbsp;Partial&nbsp;</span>"/>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group" id="va1">
                                            <label class="col-md-3 control-label">Value</label>
                                            <div class="col-md-3">
                                                <input type="text" class="form-control" id="val" name="val" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ref.getValue()))%>"
                                                       onchange="return formatValueDecimalMax(this, '<%=Utility.sanitize(ref.getValue())%>', '<%=thousand%>', '<%=decimal%>');"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else {%>

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
                        } else if (esito.equals("ko")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "Code Refund are incorrect or disabled.";
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
