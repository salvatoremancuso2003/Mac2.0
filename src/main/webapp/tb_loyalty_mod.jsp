<%@page import="rc.so.entity.Ch_transaction_doc"%>
<%@page import="rc.so.entity.Client"%>
<%@page import="rc.so.entity.Ch_transaction_value"%>
<%@page import="rc.so.entity.Ch_transaction"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="rc.so.util.Engine"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>



        <script type="text/javascript">

            function modificaLOY(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase().trim());
                field.value = field.value.replace(/[`~!@#$§°%^£&*()_|+\=?;:",.<>\{\}\[\]]/gi, '');
            }
            function RemoveAccents(str) {
                var accents = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
                var accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";
                str = str.split('');
                var strLen = str.length;
                var i, x;
                for (i = 0; i < strLen; i++) {
                    if ((x = accents.indexOf(str[i])) !== -1) {
                        str[i] = accentsOut[x] + "";
                    }
                }
                return str.join('');
            }


            function verLOY(displ) {
                var loya = document.getElementById("loya").value.trim();
                var es = "-1";
                if (loya.length === 8) {
                    $.ajax({
                        async: false,
                        type: "POST",
                        url: "Query?type=queryloy_ass&q=" + loya,
                        success: function (data) {
                            if (data !== "") {
                                es = data;
                            }
                        }
                    });
                    if (es.startsWith("ERROR")) {
                        document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                        document.getElementById('errorlarge').style.display = "block";
                        document.getElementById('errorlargetext').innerHTML = es;
                        return false;
                    } else {
                        if (displ) {
                            document.getElementById('infolarge').className = document.getElementById('infolarge').className + " in";
                            document.getElementById('infolarge').style.display = "block";
                            document.getElementById('infolargetext').innerHTML = es;
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "Loyalty Code invalid.";
                    return false;
                }
            }





        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white">
        <div class="modal fade" id="infolarge" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title font-green-jungle uppercase"><b>success message</b></h4>
                    </div>
                    <div class="modal-body" id="infolargetext">INFO</div>
                    <div class="modal-footer">
                        <button type="button" class="btn dark btn-outline" onclick="return dismiss('infolarge');" data-dismiss="modal">Close</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
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
                        String codcl = request.getParameter("codcl");
                        ArrayList<Ch_transaction> li = Engine.query_transaction_ch_CLIENT_central(codcl);
                        Client cc = Engine.query_Client_central(codcl);
                        String loy = Engine.getCodiceClienteAttivo(codcl);
                        if (cc != null && !li.isEmpty() && loy != null) {

                            String lasttrcod = li.get(li.size() - 1).getCod();
                            Ch_transaction_doc d1 = Engine.get_tr_doc_central(lasttrcod, "_docrico");
                            
                            String font = "";
                            if(!Engine.data_scadenza_attiva(cc.getDt_scadenza_documento())){
                                font = "font-red";
                            }

                    %>

                    <div class="row">
                        <div class="col-md-12">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-user font-blue-dark"></i>
                                        <span class="caption-subject font-blue-dark bold uppercase">Customer</span>
                                    </div>
                                    <div class="tools">
                                        <form action="Download?type=reprint_doc_tr&cod=<%=Utility.sanitize(d1.getCodice_documento())%>" target="_blank" method="post">
                                            <button type="submit" class="btn btn-outline blue-dark"><i class="fa fa-file-pdf-o"></i> View Last Identity Document </button>
                                        </form>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <label class="col-md-3 control-label">Surname</label>
                                            <label class="col-md-3 control-label bold"><%=Utility.sanitize(cc.getCognome())%></label>
                                            <label class="col-md-3 control-label">Name</label>
                                            <label class="col-md-3 control-label bold"><%=Utility.sanitize(cc.getNome())%></label>
                                        </div>
                                        <div class="col-md-12">
                                            <label class="col-md-3 control-label">Tax Code</label>
                                            <label class="col-md-3 control-label bold"><%=Utility.sanitize(cc.getCodfisc())%></label>
                                            <label class="col-md-3 control-label">Loyalty Code</label>
                                            <label class="col-md-3 control-label bold"><%=Utility.sanitize(loy)%></label>
                                        </div>
                                        <div class="col-md-12">
                                            <label class="col-md-3 control-label">Expiration Date </label>
                                            <label class="col-md-3 control-label bold <%=font%>"><%=Utility.sanitize(cc.getDt_scadenza_documento())%></label>
                                        </div>
                                        
                                        
                                    </div>
                                    <div class="row"><hr></div>
                                    <form action="Operazioni?type=loy_assign_new" method="post">
                                        <input type="hidden" name="codcl" value="<%=Utility.sanitize(codcl)%>" />
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-3">
                                                    <div class="form-group">
                                                        <label><i class="fa fa-credit-card font-blue"></i> Loyalty Code</label>
                                                        <input type="text" class="form-control" id="loya" name="loya" maxlength="8" 
                                                               onkeyup="return modificaLOY(this, event);"
                                                               onkeypress="return keysub(this, event);"/> 

                                                    </div>
                                                </div> 
                                                <div class="col-md-3">
                                                    <label>&nbsp;</label>
                                                    <div class="input-icon">
                                                        <button type="button" class="btn btn-outline dark tooltips" 
                                                                title="Associate / Verify" onclick="return verLOY(true);">
                                                            <i class="fa fa-chevron-circle-down"></i>
                                                        </button>
                                                    </div> 
                                                </div> 
                                                <div class="col-md-3 col-md-offset-3">
                                                    <label>&nbsp;</label>
                                                    <div class="input-icon">
                                                        <button type="submit" class="btn btn-outline red pull-right" 
                                                                onclick="return verLOY(false);">
                                                            <i class="fa fa-save"></i> Assign New Code
                                                        </button>
                                                    </div> 
                                                </div> 
                                            </div> 
                                        </div> 
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
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
