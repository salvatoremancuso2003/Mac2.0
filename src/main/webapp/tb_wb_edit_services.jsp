<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.Figures"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
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




        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>
        <%
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;
            String startvalue = "0" + decimal + "00";
        %>
        <script type="text/javascript">
            function checkdescr() {
                var dt_val = document.getElementById("dt_val").value.trim();
                if (!moment(dt_val, 'DD/MM/YYYY hh:mm').isValid() || dt_val === "31/12/1899 00:00") {
                    document.getElementById("dt_val").value = "";
                    var ermsg = "'Date Apply' are incorrect.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                var nowdate = moment();
                var pobdate1 = moment(dt_val, 'DD/MM/YYYY hh:mm', true);
                if (nowdate.isSameOrAfter(pobdate1)) {
                    var ermsg = "'Date Apply' are incorrect. The date must be after the current.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

            }

            function modificaIB(field, event) {
                field.value = RemoveAccents(field.value);
                field.value = field.value.replace(/[^a-zA-Z0-9]/g, '');
                field.value = field.value.replace(/\\/g, "");
            }


            function modificaOAM_soloap(field, event) {
                field.value = RemoveAccents(field.value.toUpperCase());
                field.value = field.value.replace(/[`~!@£§°#$%^&*()_|+\-=?;:",.<>\{\}\[\]\\\/]/gi, '');
            }

            function valid(sel, val) {
                var verified = document.getElementById(sel);
                if (verified.checked) {
                    $('#' + val).val('').change();
                    document.getElementById(val).disabled = true;
                } else {
                    document.getElementById(val).disabled = false;
                }
            }

            function checktype() {
                var typeval = document.getElementById('typeval').checked;
                if (typeval) {
                    document.getElementById('perc_value').disabled = false;
                    document.getElementById('euro_value').disabled = true;
                } else {
                    document.getElementById('perc_value').disabled = true;
                    document.getElementById('euro_value').disabled = false;
                }
            }


        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return checktype();">
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
                        String code = request.getParameter("code");
                        String[] va  = Engine.get_site_servizi_agg(code);
                        ArrayList<Branch> list_br = Engine.list_branch_enabled();
                        ArrayList<Currency> list_currency = Engine.list_all_currency();
                        if (va  != null) {
                            String type = "";
                            if (va[2].equals("0")) {
                                type = "checked";
                            }
                    %>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_wb_services" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit Additional Services</span>
                                        </div>
                                        <div class="tools"> 
                                            <div class="form-group">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <label class="col-md-3 control-label">Date Apply</label>
                                                        <div class="col-md-5">
                                                            <input type="text" class="form-control form_datetime" 
                                                                   id="dt_val" name="dt_val" onkeypress="return keysub(this, event);" 
                                                                   data-date-start-date="+0d" value=""/>
                                                        </div>
                                                        <div class="col-md-4 pull-right">
                                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" 
                                                           id="fi_code" name="fi_code" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(va[0])%>"
                                                           readonly="readonly"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Description</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="descr" name="descr"
                                                           onkeypress="return keysub(this, event);"
                                                           onchange=" return modificaOAM_soloap(this, event);"
                                                           value="<%=Utility.sanitize(va[1])%>" /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Type Value</label>
                                                <div class="col-md-9">
                                                    <input type="checkbox" <%=type%> class="make-switch" onchange="return checktype();"
                                                           id="typeval" name="typeval" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="primary" data-off-color="info"
                                                           data-on-text="<span class='tabnow'>Percent %</span>" 
                                                           data-off-text="<span class='tabnow'>Value</span>"
                                                           />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Service %</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="perc_value" name="perc_value" 
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(va[3]))%>"
                                                           onkeypress="return keysub(this, event);"
                                                           onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                           /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Service Value</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="euro_value" name="euro_value" 
                                                           onkeypress="return keysub(this, event);"
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(va[4]))%>"
                                                           onchange="return formatValueDecimal_length(this, '<%=thousand%>', '<%=decimal%>', 2);"
                                                           /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Threshold Value</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="sogliaminima" name="sogliaminima" 
                                                           onkeypress="return keysub(this, event);"
                                                           onchange="return formatValueDecimal_length(this, '<%=thousand%>', '<%=decimal%>', 2);"
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(va[5]))%>"/> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Date Start</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control date-picker" id="data_start" name="data_start" 
                                                           onkeypress="return keysub(this, event);"
                                                           value="<%=Utility.sanitize(Utility.formatStringtoStringDate(va[6], Constant.patternsql, Constant.patternnormdate_filter))%>"/> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Date End</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control date-picker" id="data_end" name="data_end" 
                                                           onkeypress="return keysub(this, event);"
                                                           value="<%=Utility.sanitize(Utility.formatStringtoStringDate(va[7], Constant.patternsql, Constant.patternnormdate_filter))%>"/> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Currency</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="currency" id="currency">
                                                        <option value="---">All</option>
                                                        <%for (int j = 0; j < list_currency.size(); j++) {

                                                                String select = "";

                                                                if (list_currency.get(j).getCode().equals(va[8])) {
                                                                    select = "selected";
                                                                }
                                                        %>
                                                        <option <%=select%> value="<%=list_currency.get(j).getCode()%>">
                                                            <%=list_currency.get(j).getCode()%> - <%=list_currency.get(j).getDescrizione()%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Branch</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="branch" id="branch">
                                                        <option value="---">All</option>
                                                        <%for (int j = 0; j < list_br.size(); j++) {

                                                                String select = "";

                                                                if (list_br.get(j).getCod().equals(va[9])) {
                                                                    select = "selected";
                                                                }
                                                        %>
                                                        <option <%=select%> value="<%=list_br.get(j).getCod()%>">
                                                            <%=list_br.get(j).getCod()%> - <%=list_br.get(j).getDe_branch()%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">BUY/SELL</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="buysell" id="buysell">
                                                        <%
                                                            String selA = va[11].equals("A") ? "selected" : "";
                                                            String selB = va[11].equals("B") ? "selected" : "";
                                                            String selS = va[11].equals("S") ? "selected" : "";
                                                        %>
                                                        <option value="A" <%=selA%>>All</option>
                                                        <option value="B" <%=selB%>>BUY ONLY</option>
                                                        <option value="S" <%=selS%>>SELL ONLY</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=ins_wb_services" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Add Additional Services</span>
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
                                                    <input type="text" class="form-control uppercase" maxlength="6" 
                                                           id="fi_code" name="fi_code" onkeyup="return modificaIB(this, event);"
                                                           onkeypress="return keysub(this, event);"
                                                           > 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Description</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="descr" name="descr"
                                                           onkeypress="return keysub(this, event);"
                                                           onchange="return modificaOAM_soloap(this, event);" /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Type Value</label>
                                                <div class="col-md-9">
                                                    <input type="checkbox" class="make-switch" onchange="return checktype();"
                                                           id="typeval" name="typeval" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="primary" data-off-color="info"
                                                           data-on-text="<span class='tabnow'>Percent %</span>" 
                                                           data-off-text="<span class='tabnow'>Value</span>"
                                                           />
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Service %</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="perc_value" name="perc_value" 
                                                           value="<%=startvalue%>"
                                                           onkeypress="return keysub(this, event);"
                                                           onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                           /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Service Value</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="euro_value" name="euro_value" 
                                                           onkeypress="return keysub(this, event);"
                                                           value="<%=startvalue%>"
                                                           onchange="return formatValueDecimal_length(this, '<%=thousand%>', '<%=decimal%>', 2);"
                                                           /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Threshold Value</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="sogliaminima" name="sogliaminima" 
                                                           onkeypress="return keysub(this, event);"
                                                           onchange="return formatValueDecimal_length(this, '<%=thousand%>', '<%=decimal%>', 2);"
                                                           value="<%=startvalue%>"/> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Date Start</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control date-picker" id="data_start" name="data_start" 
                                                           onkeypress="return keysub(this, event);" /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Date End</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control date-picker" id="data_end" name="data_end" 
                                                           onkeypress="return keysub(this, event);" /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Currency</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="currency" id="currency">
                                                        <option value="---">All</option>
                                                        <%for (int j = 0; j < list_currency.size(); j++) {

                                                                String select = "";
                                                        %>
                                                        <option <%=select%> value="<%=list_currency.get(j).getCode()%>">
                                                            <%=list_currency.get(j).getCode()%> - <%=list_currency.get(j).getDescrizione()%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Branch</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="branch" id="branch">
                                                        <option value="---">All</option>
                                                        <%for (int j = 0; j < list_br.size(); j++) {
                                                        %>
                                                        <option value="<%=list_br.get(j).getCod()%>">
                                                            <%=list_br.get(j).getCod()%> - <%=list_br.get(j).getDe_branch()%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">BUY/SELL</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="buysell" id="buysell">
                                                        <option value="A">All</option>
                                                        <option value="B">BUY ONLY</option>
                                                        <option value="S">SELL ONLY</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}
                        String esito = request.getParameter("esito");
                        if (esito == null) {
                            esito = "";
                        }
                        String classal = "alert-info";
                        String classfa = "fa-exclamation-triangle";
                        String msg = "Warning";
                        String msg1 = "No operation";
                        if (esito.equals("ok") || esito.equals("true")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        } else if (esito.equals("koins") || esito.equals("false")) {
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
