<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.Company"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
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
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        
     
        
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" />
        <%
            ArrayList<String[]> country = Engine.country();
            ArrayList<String[]> district = Engine.district();
            ArrayList<String[]> city = Engine.listcity();
            ArrayList<String[]> docid = Engine.identificationCard();

        %>
        <script type="text/javascript">

            function changecountry(index, all) {

                if (all === "1") {
                    for (var i = -1; i < 2; i++) {
                        var el = document.getElementById('country' + i);
                        if (el !== null) {

                            var coun = el.value;

                            if (coun !== '<%=Constant.codnaz%>') {
                                document.getElementById('cab_1' + i).style.display = 'none';
                                document.getElementById('dis_1' + i).style.display = 'none';
                                if (document.getElementById('dis_2' + i) !== null) {
                                    document.getElementById('dis_2' + i).style.display = 'none';
                                }
                                document.getElementById('zip_1' + i).style.display = 'none';
                            } else {
                        
                                document.getElementById('cab_1' + i).style.display = '';
                                document.getElementById('dis_1' + i).style.display = '';
                                if (document.getElementById('dis_2' + i) !== null) {
                                    document.getElementById('dis_2' + i).style.display = '';
                                }
                                document.getElementById('zip_1' + i).style.display = '';
                            }

                        }
                    }
                }
                if (all === "0") {
                    var coun = document.getElementById('country' + index).value;
                    if (coun !== '<%=Constant.codnaz%>') {
                        document.getElementById('cab_1' + index).style.display = 'none';
                        document.getElementById('dis_1' + index).style.display = 'none';
                        if (document.getElementById('dis_2' + index) !== null) {
                            document.getElementById('dis_2' + index).style.display = 'none';
                        }
                        document.getElementById('zip_1' + index).style.display = 'none';
                    } else {
                        document.getElementById('cab_1' + index).style.display = '';
                        document.getElementById('dis_1' + index).style.display = '';
                        if (document.getElementById('dis_2' + index) !== null) {
                            document.getElementById('dis_2' + index).style.display = '';
                        }
                        document.getElementById('zip_1' + index).style.display = '';
                    }
                }
            }


            function checkdescr() {
                var ag_surname = document.getElementById("ag_surname").value.trim();
                var ag_name = document.getElementById("ag_name").value.trim();
                if (ag_surname === "" || ag_name === "") {
                    var ermsg = "You must complete fields with <span class='font-red'>*</span>.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
            }
            
            function loadpage(){
                changecountry('0', '1');
                inputvirgola();
            }

        </script>
    </head>
    <!-- END HEAD -->



    <body class="page-full-width page-content-white" onload="return loadpage();" style="height: 850px;">
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
                    <%                        String esito = request.getParameter("esito");
                        if (esito == null) {
                            esito = "";
                        }
                        String classal = "alert-info";
                        String classfa = "fa-exclamation-triangle";
                        String msg = "Warning";
                        String msg1 = "No operation";
                        if (esito.equals("OK")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        } else if (esito.startsWith("KO")) {
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
                    
                    
                    <%                        Company co = Engine.get_Company(request.getParameter("co_code"));
                        if (co != null) {
                    %>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=ins_agent" onsubmit="return checkdescr();">
                        <input type="hidden" name="co_code" value="<%=Utility.sanitize(co.getNdg())%>"> 
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Add Agent</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" disabled="disabled"> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Surname <span class="font-red">*</span></label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_surname" name="ag_surname" onkeypress="return keysub(this, event);"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Name <span class="font-red">*</span></label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_name" name="ag_name" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sex</label>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch" checked id="ag_sex" name="ag_sex" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Male</span>" data-off-text="<span class='tabnow'>Female</span>">
                                                        </div>

                                                        <label class="col-md-3 control-label">Tax Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_tax" name="ag_tax" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="ag_addr" name="ag_addr" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="zip_11">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_cap" name="ag_cap" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>


                                                    <div class="form-group" id="dis_11">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_district" name="ag_district" onkeypress="return keysub(this, event);"placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>

                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="cab_11">
                                                        <label class="col-md-3 control-label">CAB</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_city" name="ag_city" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < city.size(); i++) {%>
                                                                <option value="<%=city.get(i)[0]%>"><%=city.get(i)[0]%> - <%=city.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="country1" name="ag_country" onkeypress="return keysub(this, event);" placeholder="..." onchange="return changecountry('1', '0');">
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Date of Birth</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_daob" name="ag_daob" onkeypress="return keysub(this, event);"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">City of Birth</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_ciob" name="ag_ciob" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dis_21">
                                                        <label class="col-md-3 control-label">District of Birth</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_distrob" name="ag_distrob" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Document Identity</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_docid" name="ag_docid" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < docid.size(); i++) {%>
                                                                <option value="<%=docid.get(i)[0]%>"><%=docid.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <label class="col-md-3 control-label">Number Doc. Id.</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_docnum" name="ag_docnum" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Issue Date</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_issda" name="ag_issda" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                        <label class="col-md-3 control-label">Expiration Date</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_exda" name="ag_exda" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Issued By</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " id="ag_issby" name="ag_issby" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                        <label class="col-md-3 control-label">Place of Issue</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " id="ag_isspla" name="ag_isspla" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                    </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else {%>

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