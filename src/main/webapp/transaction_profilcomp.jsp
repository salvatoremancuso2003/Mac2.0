<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Constant"%>
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
        <title>Mac2.0 - K.Y.C.</title>
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
            function checkvalue() {

                var iss = document.getElementById('iss').value.trim();
                var cust = document.getElementById('cust').value.trim();
                var cust_det = document.getElementById('cust_det').value.trim();

                var main1 = document.getElementById('main1').value.trim();
                var main1_val = document.getElementById('main1_val').value.trim();
                var main2 = document.getElementById('main2').value.trim();
                var main2_val = document.getElementById('main2_val').value.trim();
                var main3 = document.getElementById('main3').value.trim();
                var main3_val = document.getElementById('main3_val').value.trim();

                if (iss === "" || iss === "01" || iss === "02" || iss === "03") {
                } else {
                    var ermsg = "'Issuing Authority' value incorrect.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                if (cust === "01" || iss === "05" || iss === "06" || iss === "08") {
                    if (cust_det === "") {
                        var ermsg = "Must be specify 'Customer Occupation Details'.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }

                }
                if (main1 === "02") {
                    if (main1_val === "") {
                        var ermsg = "Must be specify 'Main purpose'.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }
                if (main2 === "02") {
                    if (main2_val === "") {
                        var ermsg = "Must be specify 'Money and Funds Provenance'.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }
                if (main3 === "02") {
                    if (main3_val === "") {
                        var ermsg = "Must be specify 'Money final use'.";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }
            }
        </script>


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
                        String cod = request.getParameter("cod");
                    %>

                    <%if (Constant.signoffline) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Pdf?type=_macprofcl" onsubmit="return checkvalue();">
                        <%} else {%>
                        <form class="form-horizontal" role="form" name="f1" method="post" action="Print?type=mod_prof_client" onsubmit="return checkvalue();">
                            <%}%>
                            
                            <input type="hidden"name="solofirma" value="<%=Utility.sanitize(request.getParameter("solofirma"))%>" />
                            
                            <input type="hidden" name="cod" value="<%=Utility.sanitize(cod)%>" />
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-file font-blue"></i>
                                        <span class="caption-subject font-blue bold uppercase">K.Y.C. DECLARATION RISK ASSESSMENT AND CUSTOMER DUE DILIGENCE - FORM FILLING</span>
                                    </div>
                                    <div class="tools"> 
                                        <button type="submit" class="btn btn-outline blue"><i class="fa fa-check"></i> Confirm</button>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">Issuing Authority</label>
                                        <div class="col-md-9">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <select class="form-control select2" id="iss" name="iss" placeholder="..." onkeypress="return keysub(this, event);">
                                                        <option value="01">Municipality</option>
                                                        <option value="02">Police</option>
                                                        <option value="03">Prefecture</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">Customer Occupation Details <span class="font-red">*</span></label>
                                        <div class="col-md-9">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <select class="form-control select2" id="cust" name="cust" placeholder="...">
                                                        <option value="01">Salaried</option>
                                                        <option value="02">Housewife</option>
                                                        <option value="03">Retired</option>
                                                        <option value="04">Unemployed</option>
                                                        <option value="05">Self-Employed</option>
                                                        <option value="06">Professional</option>
                                                        <option value="07">Student</option>
                                                        <option value="08">Other</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control" id="cust_det" name="cust_det" onkeypress="return keysub(this, event);"  onkeyup="return fieldNameSurname(this.id);" > 

                                                </div>
                                                <div class="col-md-3">
                                                    <span class="help-block"><small>please specify</small></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <input type="hidden" name="ann" value=""/>

                                    <!--<div class="form-group">
                                        <label class="col-md-3 control-label">Customer gross annual income <span class="font-red">*</span></label>
                                        <div class="col-md-9">
                                            <div class="row">
                                                <div class="col-md-9">
                                                    <select class="form-control select2" id="ann" name="ann" placeholder="...">
                                                        <option value="01">Up To € 10.000,00</option>
                                                        <option value="02">From € 10.001,00 To € 30.000,00</option>
                                                        <option value="03">From € 30.001,00 To € 50.000,00</option>
                                                        <option value="04">From € 50.001,00 To € 70.000,00</option>
                                                        <option value="05">€ 70.001,00 and Above</option>
                                                    </select>
                                                </div>
    
                                            </div>
                                        </div>
                                    </div>-->

                                    <div class="form-group">
                                        <label class="col-md-3 control-label">Main purpose <span class="font-red">*</span></label>
                                        <div class="col-md-9">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <select class="form-control select2" id="main1" name="main1" placeholder="...">
                                                        <!--<option value="01">NO</option>-->
                                                        <option value="02">YES</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control" onkeyup="return fieldNameSurname(this.id);" id="main1_val" name="main1_val" onkeypress="return keysub(this, event);"> 
                                                </div>
                                                <div class="col-md-3">
                                                    <span class="help-block"><small>please specify</small></span>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">Money and Funds Provenance <span class="font-red">*</span></label>
                                        <div class="col-md-9">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <select class="form-control select2" id="main2" name="main2" placeholder="...">
                                                        <!--<option value="01">NO</option>-->
                                                        <option value="02">YES</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control"onkeyup="return fieldNameSurname(this.id);" id="main2_val" name="main2_val" onkeypress="return keysub(this, event);"> 

                                                </div>
                                                <div class="col-md-3">
                                                    <span class="help-block"><small>please specify</small></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">Money final use <span class="font-red">*</span></label>
                                        <div class="col-md-9">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <select class="form-control select2" id="main3" name="main3" placeholder="...">
                                                        <!--<option value="01">NO</option>-->
                                                        <option value="02">YES</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="text" class="form-control"onkeyup="return fieldNameSurname(this.id);" id="main3_val" name="main3_val" onkeypress="return keysub(this, event);"> 
                                                </div>
                                                <div class="col-md-3">
                                                    <span class="help-block"><small>please specify</small></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                </div>
            </div>
        </div>
    </div>


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
<input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>

<!-- BEGIN THEME GLOBAL SCRIPTS -->

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