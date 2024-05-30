<%@page import="rc.so.entity.VATcode"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.CustomerKind"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
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



        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>
        <%
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;

        %>
        <script type="text/javascript">
            function checkdescr() {
                var descr = document.getElementById("descr").value.trim();
                var thr_kyc = document.getElementById("thr_kyc").value.trim();
                var maxweek = document.getElementById("maxweek").value.trim();
                var thr_aml = document.getElementById("thr_aml").value.trim();
                var thr_cee = document.getElementById("thr_cee").value.trim();
                if (descr === "" || thr_kyc === "" || maxweek === "" || thr_aml === "" || thr_cee === "") {
                    var ermsg = "You must complete all fields.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

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

            function cha_sales() {
                if (document.getElementById("list_type_selecttipov") !== null) {
                    var list_type_selecttipov = document.getElementById("list_type_selecttipov").value.trim();
                    var thr_tax_disc = document.getElementById("thr_tax_disc");
                    var value_tax_disc = document.getElementById("value_tax_disc");
                    var des_tax_disc = document.getElementById("des_tax_disc");

                    if (list_type_selecttipov === "0") {
                        thr_tax_disc.disabled = false;
                        value_tax_disc.disabled = false;
                        des_tax_disc.disabled = false;
                    } else if (list_type_selecttipov === "1") {
                        thr_tax_disc.disabled = true;
                        value_tax_disc.disabled = true;
                        des_tax_disc.disabled = true;
                    }
                }
            }


            function loadpage() {
                formatValueDecimal_1(document.getElementById('thr_kyc'), '<%=thousand%>', '<%=decimal%>');
                formatValueDecimal_1(document.getElementById('maxweek'), '<%=thousand%>', '<%=decimal%>');
                formatValueDecimal_1(document.getElementById('thr_aml'), '<%=thousand%>', '<%=decimal%>');
                formatValueDecimal_1(document.getElementById('thr_cee'), '<%=thousand%>', '<%=decimal%>');
                cha_sales();
                inputvirgola();
            }

        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return loadpage();">
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
                    <div class="clearfix"></div>
                    <%
                        String view = request.getParameter("view");
                        if (view == null) {
                            view = "0";
                        }
                        String dateApply = Utility.getDefaultDateAplly(true, false, 0, 0, 30, null);
                        ArrayList<String[]> list_type_selecttipov = Engine.list_type_selecttipov();
                        ArrayList<VATcode> list_va = Engine.li_vat("0");
                        ArrayList<String[]> list_type_customer = Engine.list_type_customer();
                        ArrayList<String[]> list_type_kind = Engine.list_type_kind();
                        ArrayList<String[]> list_category_nations = Engine.category_nations();
                        String kt_code = request.getParameter("kt_code");
                        CustomerKind ck = Engine.get_customerKind(kt_code);
                        if (ck != null) {
                            String prch = "";
                            if (ck.getStampa_autocertificazione().equals("1")) {
                                prch = "checked";
                            }
                            String uo = "";
                            if (ck.getFg_uploadobbl().equals("1")) {
                                uo = "checked";
                            }
                            String sta = "";
                            if (ck.getFg_annullato().equals("0")) {
                                sta = "checked";
                            }
                            String tf = "";
                            if (ck.getTaxfree().equals("1")) {
                                tf = "checked";
                            }
                            if (view.equals("1")) {%>

                    <form class="form-horizontal" role="form" name="f1" method="post">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">View Kind of Transaction</span>
                                        </div>
                                        <div class="tools"> 

                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" 
                                                           id="kt_code" name="kt_code" value="<%=Utility.sanitize(ck.getTipologia_clienti())%>" 
                                                           readonly="readonly" onkeypress="return keysub(this, event);"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Description</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" name="descr" id="descr" onkeypress="return keysub(this, event);"
                                                           readonly="readonly"   value="<%=Utility.sanitize(ck.getDe_tipologia_clienti())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Resident/Not Resident</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="list_type_kind" id="list_type_kind" disabled="disabled">
                                                        <%for (int i = 0; i < list_type_kind.size(); i++) {%>
                                                        <%if (list_type_kind.get(i)[0].equals(ck.getFg_nazionalita())) {%>
                                                        <option selected="selected" value="<%=list_type_kind.get(i)[0]%>"><%=list_type_kind.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_type_kind.get(i)[0]%>"><%=list_type_kind.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Customer Type</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="list_type_customer" id="list_type_customer"disabled="disabled">
                                                        <%for (int i = 0; i < list_type_customer.size(); i++) {%>
                                                        <%if (list_type_customer.get(i)[0].equals(ck.getFg_tipo_cliente())) {%>
                                                        <option selected="selected" value="<%=list_type_customer.get(i)[0]%>"><%=list_type_customer.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_type_customer.get(i)[0]%>"><%=list_type_customer.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">KYC Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           name="thr_kyc" id="thr_kyc" onkeypress="return keysub(this, event);" disabled="disabled"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.sanitize(ck.getIp_max_singola_transazione())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Max Weekly</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           name="maxweek" id="maxweek" onkeypress="return keysub(this, event);" disabled="disabled"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.sanitize(ck.getIp_max_settimanale())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">AML Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           name="thr_aml" id="thr_aml" onkeypress="return keysub(this, event);"  disabled="disabled"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.sanitize(ck.getIp_soglia_antiriciclaggio())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Geographic Area</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="list_category_nations" id="list_category_nations"disabled="disabled">
                                                        <%for (int i = 0; i < list_category_nations.size(); i++) {%>
                                                        <%if (list_category_nations.get(i)[0].equals(ck.getFg_area_geografica())) {%>
                                                        <option selected="selected" value="<%=list_category_nations.get(i)[0]%>"><%=list_category_nations.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_category_nations.get(i)[0]%>"><%=list_category_nations.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <%if (Constant.is_IT) {%>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Print ExtraUE Certification</label>
                                                <div class="col-md-9">
                                                    <input type="checkbox" class="make-switch" <%=prch%> disabled="disabled"
                                                           id="ecee" name="ecee" data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                                </div>
                                            </div>
                                            <%}%>
                                            <%if (Constant.is_IT) {%>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">ExtraUE Certification Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control"  disabled="disabled"
                                                           name="thr_cee" id="thr_cee" 
                                                           onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.sanitize(ck.getIp_soglia_extraCEE_certification())%>" 
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"> 
                                                </div>
                                            </div>
                                            <%} else {%>
                                            <input type="hidden" name="thr_cee" value="0.00" /> 
                                            <%}%>


                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Status</label>
                                                <div class="col-md-9">
                                                    <input type="checkbox" class="make-switch" <%=sta%> disabled="disabled"
                                                           name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Upload required</label>
                                                <div class="col-md-3">
                                                    <input type="checkbox" class="make-switch" <%=uo%> disabled="disabled"
                                                           id="uploadobbl" name="uploadobbl" data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                                </div>
                                            </div>   
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">TaxFree Transaction</label>
                                                <div class="col-md-3">
                                                    <input type="checkbox" class="make-switch" <%=tf%> disabled="disabled"
                                                           id="taxfree" name="taxfree" data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                                </div>
                                            </div>   
                                            <%if (Constant.is_IT) {%>
                                            <hr>             
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Sales Type </label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="list_type_selecttipov" onchange="return cha_sales();"
                                                            id="list_type_selecttipov" onkeypress="return keysub(this, event);" disabled="disabled">
                                                        <%for (int i = 0; i < list_type_selecttipov.size(); i++) {%>
                                                        <%if (list_type_selecttipov.get(i)[0].equals(ck.getTipofat())) {%>
                                                        <option selected value="<%=list_type_selecttipov.get(i)[0]%>"><%=list_type_selecttipov.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_type_selecttipov.get(i)[0]%>"><%=list_type_selecttipov.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div> 
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">VAT Code</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="vatcode"  disabled="disabled"
                                                            id="vatcode" onkeypress="return keysub(this, event);">
                                                        <%for (int i = 0; i < list_va.size(); i++) {%>
                                                        <%if (list_va.get(i).getCodice().equals(ck.getVatcode())) {%>
                                                        <option selected value="<%=list_va.get(i).getCodice()%>"><%=list_va.get(i).getCodice()%> - <%=list_va.get(i).getDescrizione()%> - <%=Utility.formatMysqltoDisplay(list_va.get(i).getAliquota())%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_va.get(i).getCodice()%>"><%=list_va.get(i).getCodice()%> - <%=list_va.get(i).getDescrizione()%> - <%=Utility.formatMysqltoDisplay(list_va.get(i).getAliquota())%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div> 
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Stamp Duty Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" disabled="disabled"
                                                           maxlength="11" onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ck.getIp_soglia_bollo()))%>" 
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"/> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Stamp Duty Value</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" disabled="disabled"
                                                           maxlength="11" onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ck.getIp_value_bollo()))%>" 
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"/> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Stamp Duty Description</label>
                                                <div class="col-md-9">
                                                    <textarea rows="3" cols="30" class="form-control" disabled="disabled"
                                                              onchange="return fieldNOSPecial_1(this.id);"><%=Utility.sanitize(ck.getDescr_bollo())%></textarea>
                                                </div>
                                            </div>
                                            <%}%>
                                            <%if (Constant.is_UK) {
                                                String thr_mini = Utility.formatMysqltoDisplay(Engine.getConf("thr.mini"));
                                                String thr_quart = Utility.formatMysqltoDisplay(Engine.getConf("thr.quart"));
                                            %>
                                            <hr> 

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Mini Heavy Transaction Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" disabled="disabled"
                                                           name="thr_mini" id="thr_mini" onkeypress="return keysub(this, event);"  maxlength="11"
                                                           value="<%=thr_mini%>" /> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">AML Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" disabled="disabled"
                                                           name="thr_quart" id="thr_quart" onkeypress="return keysub(this, event);"  maxlength="11"
                                                           value="<%=thr_quart%>" /> 
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
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_kindtra" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit Kind of Transaction</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Date Apply</label>
                                            <div class="col-md-3">
                                                <input type="text" class="form-control form_datetime" 
                                                       id="dt_val" name="dt_val" onkeypress="return keysub(this, event);" data-date-start-date="+0d"
                                                       value="<%=dateApply%>">
                                            </div>
                                        </div>
                                        <hr> 

                                        <div class="form-body">
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Code</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" 
                                                           id="kt_code" name="kt_code" value="<%=Utility.sanitize(ck.getTipologia_clienti())%>" readonly="readonly" onkeypress="return keysub(this, event);"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Description</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" name="descr" id="descr" onkeypress="return keysub(this, event);"
                                                           value="<%=Utility.sanitize(ck.getDe_tipologia_clienti())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Resident/Not Resident</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="list_type_kind" id="list_type_kind">
                                                        <%for (int i = 0; i < list_type_kind.size(); i++) {%>
                                                        <%if (list_type_kind.get(i)[0].equals(ck.getFg_nazionalita())) {%>
                                                        <option selected="selected" value="<%=list_type_kind.get(i)[0]%>"><%=list_type_kind.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_type_kind.get(i)[0]%>"><%=list_type_kind.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Customer Type</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="list_type_customer" id="list_type_customer">
                                                        <%for (int i = 0; i < list_type_customer.size(); i++) {%>
                                                        <%if (list_type_customer.get(i)[0].equals(ck.getFg_tipo_cliente())) {%>
                                                        <option selected="selected" value="<%=list_type_customer.get(i)[0]%>"><%=list_type_customer.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_type_customer.get(i)[0]%>"><%=list_type_customer.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">KYC Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           name="thr_kyc" id="thr_kyc" onkeypress="return keysub(this, event);"  maxlength="11"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.sanitize(ck.getIp_max_singola_transazione())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Max Weekly</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           name="maxweek" id="maxweek" onkeypress="return keysub(this, event);"  maxlength="11"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.sanitize(ck.getIp_max_settimanale())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">AML Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           name="thr_aml" id="thr_aml" onkeypress="return keysub(this, event);"  maxlength="11"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=Utility.sanitize(ck.getIp_soglia_antiriciclaggio())%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Geographic Area</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="list_category_nations" id="list_category_nations">
                                                        <%for (int i = 0; i < list_category_nations.size(); i++) {%>
                                                        <%if (list_category_nations.get(i)[0].equals(ck.getFg_area_geografica())) {%>
                                                        <option selected="selected" value="<%=list_category_nations.get(i)[0]%>"><%=list_category_nations.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_category_nations.get(i)[0]%>"><%=list_category_nations.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <%if (Constant.is_IT) {%>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Print ExtraUE Certification</label>
                                                <div class="col-md-9">
                                                    <input type="checkbox" class="make-switch" <%=prch%>
                                                           id="ecee" name="ecee" data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                                </div>
                                            </div>
                                            <%}%>
                                            <%if (Constant.is_IT) {%>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">ExtraUE Certification Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control"  maxlength="11"
                                                           name="thr_cee" id="thr_cee" 
                                                           onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.sanitize(ck.getIp_soglia_extraCEE_certification())%>" 
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"> 
                                                </div>
                                            </div>
                                            <%} else {%>
                                            <input type="hidden" name="thr_cee" value="0.00" /> 
                                            <%}%>    


                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Status</label>
                                                <div class="col-md-9">
                                                    <input type="checkbox" class="make-switch" <%=sta%>
                                                           name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Upload required</label>
                                                <div class="col-md-3">
                                                    <input type="checkbox" class="make-switch" <%=uo%>
                                                           id="uploadobbl" name="uploadobbl" data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>"/>
                                                </div>
                                            </div>   
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">TaxFree Transaction</label>
                                                <div class="col-md-3">
                                                    <input type="checkbox" class="make-switch" <%=tf%> 
                                                           id="taxfree" name="taxfree" data-size="normal" data-on-color="success" data-off-color="danger"
                                                           data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>" />
                                                </div>
                                            </div>
                                            <%if (Constant.is_IT) {%>
                                            <hr>             
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Sales Type </label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="list_type_selecttipov" onchange="return cha_sales();"
                                                            id="list_type_selecttipov" onkeypress="return keysub(this, event);">
                                                        <%for (int i = 0; i < list_type_selecttipov.size(); i++) {%>
                                                        <%if (list_type_selecttipov.get(i)[0].equals(ck.getTipofat())) {%>
                                                        <option selected value="<%=list_type_selecttipov.get(i)[0]%>"><%=list_type_selecttipov.get(i)[1]%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_type_selecttipov.get(i)[0]%>"><%=list_type_selecttipov.get(i)[1]%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div> 
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">VAT Code </label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" name="vatcode" 
                                                            id="vatcode" onkeypress="return keysub(this, event);">
                                                        <%for (int i = 0; i < list_va.size(); i++) {%>
                                                        <%if (list_va.get(i).getCodice().equals(ck.getVatcode())) {%>
                                                        <option selected value="<%=list_va.get(i).getCodice()%>"><%=list_va.get(i).getCodice()%> - <%=list_va.get(i).getDescrizione()%> - <%=Utility.formatMysqltoDisplay(list_va.get(i).getAliquota())%></option>
                                                        <%} else {%>
                                                        <option value="<%=list_va.get(i).getCodice()%>"><%=list_va.get(i).getCodice()%> - <%=list_va.get(i).getDescrizione()%> - <%=Utility.formatMysqltoDisplay(list_va.get(i).getAliquota())%></option>
                                                        <%}%>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div> 
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Stamp Duty Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" name="thr_tax_disc" id="thr_tax_disc" 
                                                           maxlength="11" onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ck.getIp_soglia_bollo()))%>" 
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"/> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Stamp Duty Value</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" name="value_tax_disc" id="value_tax_disc" 
                                                           maxlength="11" onkeypress="return keysub(this, event);" 
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ck.getIp_value_bollo()))%>" 
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"/> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Stamp Duty Description</label>
                                                <div class="col-md-9">
                                                    <textarea rows="3" cols="30" class="form-control" name="des_tax_disc" id="des_tax_disc" 
                                                              onchange="return fieldNOSPecial_1(this.id);"><%=Utility.sanitize(ck.getDescr_bollo())%></textarea>
                                                </div>
                                            </div>
                                            <%}%>
                                            <%if (Constant.is_UK) {
                                                String thr_mini = Utility.formatMysqltoDisplay(Engine.getConf("thr.mini"));
                                                String thr_quart = Utility.formatMysqltoDisplay(Engine.getConf("thr.quart"));
                                            %>
                                            <hr> 

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Mini Heavy Transaction Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           name="thr_mini" id="thr_mini" onkeypress="return keysub(this, event);"  maxlength="11"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=thr_mini%>"> 
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">AML Threshold</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" 
                                                           name="thr_quart" id="thr_quart" onkeypress="return keysub(this, event);"  maxlength="11"
                                                           onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                           value="<%=thr_quart%>"> 
                                                </div>
                                            </div>

                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}
                    } else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=ins_newkindtra" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">New Kind of Transaction</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Date Apply</label>
                                            <div class="col-md-3">
                                                <input type="text" class="form-control form_datetime" 
                                                       id="dt_val" name="dt_val" onkeypress="return keysub(this, event);" data-date-start-date="+0d"
                                                       value="<%=dateApply%>">
                                            </div>
                                        </div>
                                        <hr> 
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Code</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control uppercase" disabled="disabled"> 
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Description</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="descr" id="descr" onkeypress="return keysub(this, event);"> 
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Resident/Not Resident</label>
                                            <div class="col-md-9">
                                                <select class="form-control select2" name="list_type_kind" id="list_type_kind" onkeypress="return keysub(this, event);">
                                                    <%for (int i = 0; i < list_type_kind.size(); i++) {%>
                                                    <option value="<%=list_type_kind.get(i)[0]%>"><%=list_type_kind.get(i)[1]%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Customer Type</label>
                                            <div class="col-md-9">
                                                <select class="form-control select2" name="list_type_customer" id="list_type_customer" onkeypress="return keysub(this, event);">
                                                    <%for (int i = 0; i < list_type_customer.size(); i++) {%>
                                                    <option value="<%=list_type_customer.get(i)[0]%>"><%=list_type_customer.get(i)[1]%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">KYC Threshold</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="thr_kyc" id="thr_kyc" maxlength="11"
                                                       onkeypress="return keysub(this, event);" value="0<%=decimal%>01" 
                                                       onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"> 
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Max Weekly</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="maxweek" id="maxweek" maxlength="11" onkeypress="return keysub(this, event);" value="2999<%=decimal%>96" onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"> 
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">AML Threshold</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="thr_aml" id="thr_aml" maxlength="11" onkeypress="return keysub(this, event);" value="1000<%=decimal%>00" onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"> 
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Geographic Area</label>
                                            <div class="col-md-9">
                                                <select class="form-control select2" name="list_category_nations" id="list_category_nations" onkeypress="return keysub(this, event);">
                                                    <%for (int i = 0; i < list_category_nations.size(); i++) {%>
                                                    <option value="<%=list_category_nations.get(i)[0]%>"><%=list_category_nations.get(i)[1]%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                        <%if (Constant.is_IT) {%>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Print ExtraUE Certification</label>
                                            <div class="col-md-9">
                                                <input type="checkbox" class="make-switch"
                                                       id="ecee" name="ecee" data-size="normal" data-on-color="success" data-off-color="danger"
                                                       data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                            </div>
                                        </div>
                                        <%}%>
                                        <%if (Constant.is_IT) {%>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">ExtraUE Certification Threshold</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="thr_cee" id="thr_cee" maxlength="11" onkeypress="return keysub(this, event);" value="3000<%=decimal%>00" onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"> 
                                            </div>
                                        </div>
                                        <%} else {%>
                                        <input type="hidden" name="thr_cee" value="0.00" /> 
                                        <%}%>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Upload required</label>
                                            <div class="col-md-3">
                                                <input type="checkbox" class="make-switch"
                                                       id="uploadobbl" name="uploadobbl" data-size="normal" data-on-color="success" data-off-color="danger"
                                                       data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">TaxFree Transaction</label>
                                            <div class="col-md-3">
                                                <input type="checkbox" class="make-switch" 
                                                       id="taxfree" name="taxfree" data-size="normal" data-on-color="success" data-off-color="danger"
                                                       data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                            </div>
                                        </div>
                                        <%if (Constant.is_IT) {%>
                                        <hr>             
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Sales Type </label>
                                            <div class="col-md-9">
                                                <select class="form-control select2" name="list_type_selecttipov" onchange="return cha_sales();"
                                                        id="list_type_selecttipov" onkeypress="return keysub(this, event);">
                                                    <%for (int i = 0; i < list_type_selecttipov.size(); i++) {%>
                                                    <option value="<%=list_type_selecttipov.get(i)[0]%>"><%=list_type_selecttipov.get(i)[1]%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">VAT Code </label>
                                            <div class="col-md-9">
                                                <select class="form-control select2" name="vatcode" 
                                                        id="vatcode" onkeypress="return keysub(this, event);">
                                                    <%for (int i = 0; i < list_va.size(); i++) {%>
                                                    <option value="<%=list_va.get(i).getCodice()%>"><%=list_va.get(i).getCodice()%> - <%=list_va.get(i).getDescrizione()%> - <%=Utility.formatMysqltoDisplay(list_va.get(i).getAliquota())%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div> 
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Stamp Duty Threshold</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="thr_tax_disc" id="thr_tax_disc" 
                                                       maxlength="11" onkeypress="return keysub(this, event);" 
                                                       value="0<%=decimal%>00" onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"/> 
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Stamp Duty Value</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" name="value_tax_disc" id="value_tax_disc" 
                                                       maxlength="11" onkeypress="return keysub(this, event);" 
                                                       value="0<%=decimal%>00" onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"/> 
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Stamp Duty Description</label>
                                            <div class="col-md-9">
                                                <textarea rows="3" cols="30" class="form-control" name="des_tax_disc" id="des_tax_disc" 
                                                          onchange="return fieldNOSPecial_1(this.id);"> </textarea>
                                            </div>
                                        </div>
                                        <%}%>
                                        
                                        
                                        
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}%>

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
