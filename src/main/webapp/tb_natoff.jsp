<%@page import="rc.so.entity.Kyc_parameter"%>
<%@page import="rc.so.entity.Office"%>
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
        <title>Mac2.0 - National Office</title>
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
        <%
            String decimal = Constant.decimal;
            String start = "0" + decimal + "00";
            String thousand = Constant.thousand;
        %>
        <script type="text/javascript">
            function checkdescr() {

                var descr = document.getElementById("descr").value.trim();
                var url = document.getElementById("url").value.trim();
                var decim = document.getElementById("decim").value.trim();
                if (descr === "" || url === "" || decim === "") {
                    var ermsg = "You must complete fields with <span class='font-red'>*</span>.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                } else {
                    if (decim === '<%=start%>') {
                        var ermsg = "Field 'Decimal Round' may not have value equal to " + "<%=start%>" + ".";
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = ermsg;
                        return false;
                    }
                }

            }
            
            function loadpage() {
                online();
                formatValueDecimal_1(document.getElementById("decim"), '<%=thousand%>', '<%=decimal%>');
                formatValueDecimal_1(document.getElementById("kyc_soglia"), '<%=thousand%>', '<%=decimal%>');
            }
        </script>
    </head>
    <!-- END HEAD -->
    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">
        <!-- BEGIN HEADER -->
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_ma21.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);

                Office off = Engine.get_national_office();
                String ch = "";
                if (off.getChangetype().equals("/")) {
                    ch = "checked";
                }
                String ie1 = Engine.getPath("IE");
                String ed1 = Engine.getPath("ED");
                String ch1 = Engine.getPath("CH");
                String mf1 = Engine.getPath("FI");
                if (ie1.equals("1")) {
                    ie1 = "checked";
                } else {
                    ie1 = "";
                }
                if (ed1.equals("1")) {
                    ed1 = "checked";
                } else {
                    ed1 = "";
                }
                if (ch1.equals("1")) {
                    ch1 = "checked";
                } else {
                    ch1 = "";
                }
                if (mf1.equals("1")) {
                    mf1 = "checked";
                } else {
                    mf1 = "";
                }
                boolean central = Engine.isCentral();
                String tipo = (String) request.getSession().getAttribute("us_tip");
                if (tipo == null) {
                    tipo = "";
                }
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
                            <h3 class="page-title">Maintenance <small><b>National Office</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>

                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT TILL -->
                    <% String esito = request.getParameter("esito");
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
                        } else if (esito.equals("koins")) {
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

                    <%if (central && tipo.equals("3")) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_natoff" onsubmit="return checkdescr();"> 
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit National Office</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">

                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Code</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control uppercase" onkeypress="return keysub(this, event);" name="cod" value="<%=off.getCod()%>" readonly="readonly"> 
                                                </div>
                                                <label class="col-md-2 control-label">Description</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" 
                                                           name="descr" id="descr" onkeypress="return keysub(this, event);" value="<%=off.getDe_office()%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Address</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="addr" id="addr" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getAdd_via()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">City</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="city" id="city" onkeypress="return keysub(this, event);" 
                                                           value="<%=off.getAdd_city()%>"> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Zip Code</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="zipc" id="zipc" onkeypress="return keysub(this, event);" 
                                                           value="<%=off.getAdd_cap()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">VAT Code</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="piva" id="piva" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getVat()%>"> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Enterprise Registry</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="regi" id="regi" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getReg_impr()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">REA</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="rea" id="rea" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getRea()%>"> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Change Logic <span class="font-red">*</span></label>
                                                <div class="col-md-4">
                                                    <input type="checkbox" class="make-switch" <%=ch%>
                                                           name="change" id="change" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="primary" data-off-color="info"
                                                           data-on-text="<span class='tabnow'>/</span>" data-off-text="<span class='tabnow'>*</span>">
                                                </div>
                                                <label class="col-md-2 control-label">URL Blacklist <span class="font-red">*</span></label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" name="url" id="url" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getUrl_bl()%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Buy Back Duration (day) <span class="font-red">*</span></label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" id="bbdur" name="bbdur"  maxlength="3" onkeyup="return fieldOnlyNumber(this.id);"
                                                           value="<%=off.getScadenza_bb()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">Decimal Round <span class="font-red">*</span></label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" id="decim" name="decim"  
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           onkeypress="return keysub(this, event);"
                                                           value="<%=off.getDecimalround()%>"> 
                                                </div>
                                            </div>
                                            <%if (Constant.is_IT) { %>
                                            <div class="form-group"><hr></div>
                                                <%}%>
                                            
                                                    <input type="hidden" id="kyc_mesi" name="kyc_mesi"  
                                                           maxlength="2"
                                                           value="<%=off.getKyc_mesi()%>"> 
                                                    <input type="hidden" id="kyc_soglia" name="kyc_soglia"  
                                                           value="<%=off.getKyc_soglia()%>"> 
                                            
                                            <%if (Constant.is_IT) {

                                                    ArrayList<String[]> array_country = Engine.country();
                                                    Kyc_parameter kyc_pram = new Kyc_parameter();
                                            %>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Frequency Daily <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abfreqdaily" name="kyc_abfreqdaily"  
                                                           maxlength="2" onkeyup="return fieldOnlyNumber(this.id);"
                                                           value="<%=kyc_pram.getKyc_fa1()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Frequency Weekly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abfreqweek" name="kyc_abfreqweek"  
                                                           maxlength="2" onkeyup="return fieldOnlyNumber(this.id);"
                                                           value="<%=kyc_pram.getKyc_fa2()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Frequency Monthly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abfreqmont" name="kyc_abfreqmont"  
                                                           maxlength="2" onkeyup="return fieldOnlyNumber(this.id);"
                                                           value="<%=kyc_pram.getKyc_fa3()%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Volume Monthly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abvolmont" name="kyc_abvolmont"  
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.formatMysqltoDisplay(kyc_pram.getKyc_va1())%>" /> 
                                                </div>
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Volume Quarterly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abvolqua" name="kyc_abvolqua"  
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.formatMysqltoDisplay(kyc_pram.getKyc_va2())%>" /> 
                                                </div>

                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">K.Y.C. Volume 'Rogue Country' Weekly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_rogvol" name="kyc_rogvol"  
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.formatMysqltoDisplay(kyc_pram.getKyc_vro())%>" /> 
                                                </div>
                                                <label class="col-md-2 control-label">K.Y.C. List 'Rogue Country' <span class="font-red">*</span></label>
                                                <div class="col-md-6">
                                                    <select class="form-control select2" name="kyc_rogcountry" id="kyc_rogcountry" multiple>
                                                        <%for (int j = 0; j < array_country.size(); j++) {
                                                                String sel = "";
                                                                if (kyc_pram.getKyc_listro().contains(array_country.get(j)[0])) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option value="<%=array_country.get(j)[0]%>" <%=sel%>>
                                                            <%=array_country.get(j)[0]%> - <%=array_country.get(j)[1]%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">List 'OK Country' <span class="font-red">*</span></label>
                                                <div class="col-md-10">
                                                    <select class="form-control select2" name="kyc_okountry" id="kyc_okountry" multiple>
                                                        <%for (int j = 0; j < array_country.size(); j++) {
                                                                String sel = "";
                                                                if (kyc_pram.getKyc_listok().contains(array_country.get(j)[0])) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option value="<%=array_country.get(j)[0]%>" <%=sel%>>
                                                            <%=array_country.get(j)[0]%> - <%=array_country.get(j)[1]%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group"><hr></div>
                                            <%}%>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Unlock operation after (minutes)<span class="font-red">*</span></label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" id="minutes" name="minutes"  maxlength="3" onchange="return fieldOnlyNumber_RA(this.id);"
                                                           value="<%=off.getMinutes()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">Risk Assessment Index - Days <span class="font-red">*</span></label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" id="risk_days" name="risk_days" 
                                                           maxlength="4" onchange="return fieldOnlyNumber_RA(this.id);"
                                                           value="<%=off.getRisk_days()%>"/> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Risk Assessment Index <br/>Threshold Number of Transactions <span class="font-red">*</span></label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" id="risk_ntr" name="risk_ntr"  maxlength="4" onchange="return fieldOnlyNumber_RA(this.id);"
                                                           value="<%=off.getRisk_ntr()%>"/> 
                                                </div>
                                                <label class="col-md-2 control-label">Risk Assessment Index <br/>Threshold Total <span class="font-red">*</span></label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" id="risk_soglia" name="risk_soglia"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.formatMysqltoDisplay(off.getRisk_soglia())%>"/> 
                                                </div>
                                            </div>

                                            <hr>
                                            <%

                                                String label_txt1 = "Text Receipt Part One";
                                                String label_txt2 = "Text Receipt Part Two";

                                                if (Constant.is_CZ) {
                                                    label_txt1 = "Text Prereceipt";
                                                    label_txt2 = "Text Receipt";
                                                }

                                            %>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label"><p class='ab'><%=label_txt1%></p><small>(Max 2000)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp1" name="txtrecp1" class="tinyta"
                                                              ><%=off.getTxt_ricev_1()%></textarea>
                                                    <span class="help-block" id="countertxtrecp1"></span>
                                                </div>
                                                <label class="col-md-2 control-label"><p class='ab'><%=label_txt2%></p><small>(Max 2000)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp2" name="txtrecp2" rows="15" class="tinyta"><%=off.getTxt_ricev_2()%></textarea>
                                                </div>
                                            </div>



                                            <%if (Constant.is_UK) {%>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Text Receipt Sell Buy Back<p class='ab'></p><small>(Max 2000)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp3" name="txtrecp3" class="tinyta"><%=off.getTxt_ricev_bb()%></textarea>
                                                </div>
                                            </div>
                                            <%}%>


                                            <%if (Constant.is_IT) {%>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Text Receipt Sell Buy Back<p class='ab'></p><small>(Max 2000)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp3" name="txtrecp3" class="tinyta"><%=off.getTxt_ricev_bb()%></textarea>
                                                </div>
                                                <label class="col-md-2 control-label">Text Receipt Client NO PEP<p class='ab'></p><small>(Max 300)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp4" name="txtrecp4" class="tinyta"><%=off.getTxt_nopep()%></textarea>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Text Alert Threshold Part One<p class='ab'></p><small>(Max 100)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtthr1" name="txtthr1" class="tinyta"><%=off.getTxt_alert_threshold_1()%></textarea>
                                                </div>
                                                <label class="col-md-2 control-label">Text Alert Threshold Part Two<p class='ab'></p><small>(Max 100)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtthr2" name="txtthr2" class="tinyta"><%=off.getTxt_alert_threshold_2()%></textarea>
                                                </div>
                                            </div>



                                            <hr>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Browser Sign Configuration<p class='ab'></p><small>Enable Direct Sign</small></label>
                                                <div class="col-md-2">
                                                    <label class="control-label"><b>Internet Explorer</b></label><p class='ab'></p>
                                                    <input type="checkbox" class="make-switch" <%=ie1%>
                                                           name="ie1" id="ie1" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                </div>   
                                                <div class="col-md-2">
                                                    <label class="control-label"><b>Microsoft Edge</b></label><p class='ab'></p>
                                                    <input type="checkbox" class="make-switch" <%=ed1%>
                                                           name="ed1" id="ed1" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                </div>   
                                                <div class="col-md-2">
                                                    <label class="control-label"><b>Google Chrome</b></label><p class='ab'></p>
                                                    <input type="checkbox" class="make-switch" <%=ch1%>
                                                           name="ch1" id="ch1" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                </div>   
                                                <div class="col-md-2">
                                                    <label class="control-label"><b>Mozilla Firefox</b></label><p class='ab'></p>
                                                    <input type="checkbox" class="make-switch" <%=mf1%>
                                                           name="mf1" id="mf1" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                </div>   
                                            </div>  
                                            <%}%>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post"> 
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">View National Office</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">

                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Code</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control uppercase" 
                                                           onkeypress="return keysub(this, event);" name="cod" value="<%=off.getCod()%>" readonly="readonly"> 
                                                </div>
                                                <label class="col-md-2 control-label">Description</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"
                                                           name="descr" id="descr" onkeypress="return keysub(this, event);" value="<%=off.getDe_office()%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Address</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"name="addr" id="addr" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getAdd_via()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">City</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"name="city" id="city" onkeypress="return keysub(this, event);" 
                                                           value="<%=off.getAdd_city()%>"> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Zip Code</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"name="zipc" id="zipc" onkeypress="return keysub(this, event);" 
                                                           value="<%=off.getAdd_cap()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">VAT Code</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"name="piva" id="piva" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getVat()%>"> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Enterprise Registry</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"name="regi" id="regi" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getReg_impr()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">REA</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"name="rea" id="rea" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getRea()%>"> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Change Logic</label>
                                                <div class="col-md-4">
                                                    <input type="checkbox" class="make-switch" <%=ch%> readonly="readonly"
                                                           name="change" id="change" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="primary" data-off-color="info"
                                                           data-on-text="<span class='tabnow'>/</span>" data-off-text="<span class='tabnow'>*</span>">
                                                </div>
                                                <label class="col-md-2 control-label">URL Blacklist</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control"readonly="readonly" name="url" id="url" onkeypress="return keysub(this, event);"
                                                           value="<%=off.getUrl_bl()%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Buy Back Duration (day)</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"id="bbdur" name="bbdur"  maxlength="3" onkeyup="return fieldOnlyNumber(this.id);"
                                                           value="<%=off.getScadenza_bb()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">Decimal Round</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" readonly="readonly"id="decim" name="decim"  
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           onkeypress="return keysub(this, event);"
                                                           value="<%=off.getDecimalround()%>"> 
                                                </div>
                                            </div>
                                                <%if (Constant.is_IT) {
                                                    ArrayList<String[]> array_country = Engine.country();
                                                    Kyc_parameter kyc_pram = new Kyc_parameter();
                                            %>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Frequency Daily <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abfreqdaily" name="kyc_abfreqdaily"  
                                                            readonly="readonly"
                                                           value="<%=kyc_pram.getKyc_fa1()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Frequency Weekly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abfreqweek" name="kyc_abfreqweek"  
                                                            readonly="readonly"
                                                           value="<%=kyc_pram.getKyc_fa2()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Frequency Monthly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abfreqmont" name="kyc_abfreqmont"  
                                                            readonly="readonly"
                                                           value="<%=kyc_pram.getKyc_fa3()%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Volume Monthly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abvolmont" name="kyc_abvolmont"  
                                                            readonly="readonly"
                                                           value="<%=Utility.formatMysqltoDisplay(kyc_pram.getKyc_va1())%>" /> 
                                                </div>
                                                <label class="col-md-2 control-label">K.Y.C. Abnormal Volume Quarterly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_abvolqua" name="kyc_abvolqua"  
                                                            readonly="readonly"
                                                           value="<%=Utility.formatMysqltoDisplay(kyc_pram.getKyc_va2())%>" /> 
                                                </div>

                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">K.Y.C. Volume 'Rogue Country' Weekly <span class="font-red">*</span></label>
                                                <div class="col-md-2">
                                                    <input type="text" class="form-control" id="kyc_rogvol" name="kyc_rogvol"  
                                                            readonly="readonly"
                                                           value="<%=Utility.formatMysqltoDisplay(kyc_pram.getKyc_vro())%>" /> 
                                                </div>
                                                <label class="col-md-2 control-label">K.Y.C. List 'Rogue Country' <span class="font-red">*</span></label>
                                                <div class="col-md-6">
                                                    <select class="form-control select2" name="kyc_rogcountry" id="kyc_rogcountry" multiple disabled>
                                                        <%for (int j = 0; j < array_country.size(); j++) {
                                                                String sel = "";
                                                                if (kyc_pram.getKyc_listro().contains(array_country.get(j)[0])) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option value="<%=array_country.get(j)[0]%>" <%=sel%>>
                                                            <%=array_country.get(j)[0]%> - <%=array_country.get(j)[1]%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">List 'OK Country' <span class="font-red">*</span></label>
                                                <div class="col-md-10">
                                                    <select class="form-control select2" name="kyc_okountry" id="kyc_okountry" multiple disabled>
                                                        <%for (int j = 0; j < array_country.size(); j++) {
                                                                String sel = "";
                                                                if (kyc_pram.getKyc_listok().contains(array_country.get(j)[0])) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option value="<%=array_country.get(j)[0]%>" <%=sel%>>
                                                            <%=array_country.get(j)[0]%> - <%=array_country.get(j)[1]%>
                                                        </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                                    
                                                    
                                                    
                                            <div class="form-group"><hr></div>
                                            <%}%>
                                                




                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Unlock operation after (minutes)</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" disabled value="<%=off.getMinutes()%>"> 
                                                </div>
                                                <label class="col-md-2 control-label">Risk Assessment Index - Days </label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" disabled value="<%=off.getRisk_days()%>"/> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Risk Assessment Index <br/>Threshold Number of Transactions</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" disabled value="<%=off.getRisk_ntr()%>"/> 
                                                </div>
                                                <label class="col-md-2 control-label">Risk Assessment Index <br/>Threshold Total</label>
                                                <div class="col-md-4">
                                                    <input type="text" class="form-control" disabled value="<%=Utility.formatMysqltoDisplay(off.getRisk_soglia())%>"/> 
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Text Receipt Part One<p class='ab'></p><small>(Max 2000)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp1" name="txtrecp1" class="tinyta"
                                                              ><%=off.getTxt_ricev_1()%></textarea>
                                                    <span class="help-block" id="countertxtrecp1"></span>
                                                </div>
                                                <label class="col-md-2 control-label">Text Receipt Part Two<p class='ab'></p><small>(Max 2000)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp2" name="txtrecp2" rows="15" class="tinyta"><%=off.getTxt_ricev_2()%></textarea>
                                                </div>
                                            </div>

                                            <%if (Constant.is_UK) {%>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Text Receipt Sell Buy Back<p class='ab'></p><small>(Max 2000)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp3" name="txtrecp3" class="tinyta"><%=off.getTxt_ricev_bb()%></textarea>
                                                </div>
                                            </div>
                                            <%}%>




                                            <%if (Constant.is_IT) {%>   
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Text Receipt Sell Buy Back<p class='ab'></p><small>(Max 2000)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp3" name="txtrecp3" class="tinyta"><%=off.getTxt_ricev_bb()%></textarea>
                                                </div>
                                                <label class="col-md-2 control-label">Text Receipt Client NO PEP<p class='ab'></p><small>(Max 300)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtrecp4" name="txtrecp4" class="tinyta"><%=off.getTxt_nopep()%></textarea>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Text Alert Threshold Part One<p class='ab'></p><small>(Max 100)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtthr1" name="txtthr1" class="tinyta"><%=off.getTxt_alert_threshold_1()%></textarea>
                                                </div>
                                                <label class="col-md-2 control-label">Text Alert Threshold Part Two<p class='ab'></p><small>(Max 100)</small></label>
                                                <div class="col-md-4">
                                                    <textarea id="txtthr2" name="txtthr2" class="tinyta"><%=off.getTxt_alert_threshold_2()%></textarea>
                                                </div>
                                            </div>

                                            <hr>
                                            <div class="form-group">
                                                <label class="col-md-2 control-label">Browser Sign Configuration<p class='ab'></p><small>Enable Direct Sign</small></label>
                                                <div class="col-md-2">
                                                    <label class="control-label"><b>Internet Explorer</b></label><p class='ab'></p>
                                                    <input type="checkbox" class="make-switch" <%=ie1%>
                                                           name="ie1" id="ie1" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                </div>   
                                                <div class="col-md-2">
                                                    <label class="control-label"><b>Microsoft Edge</b></label><p class='ab'></p>
                                                    <input type="checkbox" class="make-switch" <%=ed1%>
                                                           name="ed1" id="ed1" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                </div>   
                                                <div class="col-md-2">
                                                    <label class="control-label"><b>Google Chrome</b></label><p class='ab'></p>
                                                    <input type="checkbox" class="make-switch" <%=ch1%>
                                                           name="ch1" id="ch1" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                </div>   
                                                <div class="col-md-2">
                                                    <label class="control-label"><b>Mozilla Firefox</b></label><p class='ab'></p>
                                                    <input type="checkbox" class="make-switch" <%=mf1%>
                                                           name="mf1" id="mf1" onkeypress="return keysub(this, event);"
                                                           data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>YES</span>" data-off-text="<span class='tabnow'>NO</span>">
                                                </div>   
                                            </div> 
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
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

                <script src="assets/soop/tinymce/tinymce.min.js?apiKey=ma1upy7t6aft708fnem5ewb25zj40b36gdnl9ovb046gcbqx"></script>



                <!-- END CORE PLUGINS -->
                <!-- BEGIN PAGE LEVEL PLUGINS -->
                <!-- END PAGE LEVEL PLUGINS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->
                <!-- END THEME LAYOUT SCRIPTS -->
                <!-- BEGIN THEME GLOBAL SCRIPTS -->
                <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                <%if (central && tipo.equals("3")) {%>
                <script>
                                                               $(function () {
                                                                   tinymce.init({
                                                                       selector: '.tinyta',
                                                                       height: 200,
                                                                       menubar: false,
                                                                       schema: 'html5',
                                                                       toolbar1: 'undo redo | fontsizeselect fontselect | bold italic underline | alignjustify ',
                                                                       fontsize_formats: '6pt 8pt 9pt 10pt 11pt 12pt 14pt 18pt 20pt'
                                                                   });
                                                               });
                </script>
                <%} else {%>
                <script>
                    $(function () {
                        tinymce.init({
                            selector: '.tinyta',
                            height: 200,
                            menubar: false,
                            schema: 'html5',
                            readonly: 1
                        });
                    });
                </script>
                <%}%>

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
