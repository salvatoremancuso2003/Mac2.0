<%@page import="rc.so.entity.Sizecuts"%>
<%@page import="rc.so.entity.Rate_history"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.Currency"%>
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
        <script src="assets/soop/js/moment.js" type="text/javascript"></script>



        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <%
            String listbranch = request.getParameter("listbranch");
            String fil[] = Engine.getFil();
            String bra[] = {fil[0]};

            String editce = request.getParameter("editce");
            if (editce == null) {
                editce = "0";
            }

            if (listbranch != null && listbranch.length() < 5) {
                bra = Utility.parseArrayValues(listbranch, ";");
            }

            Branch br = Engine.get_branch(bra[0]);
            Currency cu = Engine.getCurrency(request.getParameter("cur_code"), bra[0]);
            if (cu == null) {
                cu = Engine.getCurrency(request.getParameter("cur_code"), "000");
            }
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;

            String view = request.getParameter("view");
            if (view == null) {
                view = "0";
            }
            boolean modrate = false;
            if (bra[0].equals("000")) {
                modrate = true;
            } else {
                modrate = br.getFg_modrate().equals("1") || session.getAttribute("us_tip").equals("3") || editce.equals("1");
            }
        %>
        <script type="text/javascript">
            function checkdescr() {


                var descr = document.getElementById("descr").value.trim();
                if (descr === "") {
                    var ermsg = "You must complete all fields.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

                if (document.getElementById("dt_val").type === "hidden") {
                    return true;
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

            function typebuy() {
                var typebuy = document.getElementById('typebuy_0').checked;
                if (typebuy) {
                    document.getElementById('buystd_normal').style.display = '';
                    document.getElementById('buystd_value').style.display = 'none';
                } else {
                    document.getElementById('buystd_normal').style.display = 'none';
                    document.getElementById('buystd_value').style.display = '';
                }
            }

            function typesell() {
                var typesell = document.getElementById('typesell_0').checked;
                if (typesell) {
                    document.getElementById('sellstd_per').style.display = '';
                    document.getElementById('sellstd_value').style.display = 'none';
                } else {
                    document.getElementById('sellstd_per').style.display = 'none';
                    document.getElementById('sellstd_value').style.display = '';
                }
            }

            function remove(elem) {
                elem.parentNode.removeChild(elem);
                var ind = document.getElementById('sizecutindex').value;
                var nowind = parseInt(ind) - 1;
                document.getElementById('sizecutindex').value = nowind;
                return false;
            }

            function add() {

                var ind = document.getElementById('sizecutindex').value;
                var nowind = parseInt(ind) + 1;
                document.getElementById('sizecutindex').value = nowind;


                var div = document.createElement('div');
                div.className = 'form-group';
                var la1 = document.createElement('label');
                la1.className = 'col-md-3 control-label';
                la1.innerHTML = 'New';
                var div2 = document.createElement('div');
                div2.className = 'col-md-3';

                var inp1 = document.createElement('input');
                inp1.className = 'form-control';
                inp1.type = 'text';
                inp1.name = 'sizecut_' + nowind;
                inp1.value = '1' + '<%=Utility.sanitize(decimal)%>' + '00';
                inp1.onchange = function () {
                    return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 2);
                };
                var div3 = document.createElement('div');
                div3.className = 'col-md-3';
                var but1 = document.createElement('button');
                but1.type = 'button';
                but1.className = 'btn btn-outline red';
                but1.name = 'add1';
                but1.innerHTML = '<i class="fa fa-remove"></i> Remove';
                but1.onclick = function () { // Note this is a function
                    remove(div);
                };
                var inp3 = document.createElement('input');
                inp3.type = 'hidden';
                inp3.name = 'statsizecut_' + nowind;
                inp3.value = 'on';
                
                div.appendChild(la1);
                div2.appendChild(inp1);
                div2.appendChild(inp3);
                div.appendChild(div2);
                div3.appendChild(but1);
                div.appendChild(div3);
                
                
                
                document.getElementById('tab_1_4').appendChild(div);
            }

            function loadpage() {
                inputvirgola();
                typebuy();
                typesell();
                formatValueDecimalMax(document.getElementById('buy_std_perc'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('buy_l1'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('buy_l2'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('buy_l3'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('buy_best'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('sell_l1'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('sell_l2'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('sell_l3'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('sell_be'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');
                formatValueDecimalMax(document.getElementById('sell_std_perc'), '100' + '<%=Utility.sanitize(decimal)%>' + '00', '', '<%=Utility.sanitize(decimal)%>');

            }
        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" style="height: 850px;" onload="loadpage();">
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

                    <%String esito = request.getParameter("esito");
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
                        } else if (esito.startsWith("ko")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            if (esito.equals("ko1")) {
                                msg1 = "The operation could not be completed. There was an error during changing values &#8220;Basic&#8221; and &#8220;Rate Buy/Sell&#8221;.";
                            } else if (esito.equals("ko2")) {
                                msg1 = "The operation could not be completed. There was an error during changing values &#8220;Figures&#8221;.";
                            } else if (esito.equals("ko3")) {
                                msg1 = "The operation could not be completed. There was an error during changing values &#8220;Sizes&#8221;.";
                            } else {
                                msg1 = "The operation could not be completed.";
                            }

                        }
                        if (!esito.equals("")) {
                    %>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert <%=Utility.sanitize(classal)%>">
                                <strong><%=Utility.sanitize(msg)%> <i class="fa <%=Utility.sanitize(classfa)%>"></i></strong> <%=Utility.sanitize(msg1)%>
                            </div>
                        </div>
                    </div>
                    <%}%> 


                    <%

                        if (cu != null) {

                            boolean local = false;

                            ArrayList<String[]> list_kind = Engine.list_all_kind(bra[0]);
                            ArrayList<String[]> list_kind_currency = Engine.list_kind_currency(cu.getCode(), bra[0]);
                            ArrayList<Sizecuts> list_sizecuts_new = Engine.getfigures_sizecuts_all(cu.getCode(), bra[0]);

                            String intern = "";
                            if (cu.getInternal_cur().equals("1")) {
                                intern = "checked";
                                local = true;
                            }
                            String enablebuy = "";
                            if (cu.getEnable_buy().equals("1")) {
                                enablebuy = "checked";
                            }
                            String typebuy = "";
                            if (cu.getBuy_std_type().equals("0")) {
                                typebuy = "checked";
                            }
                            String enablesell = "";
                            if (cu.getEnable_sell().equals("1")) {
                                enablesell = "checked";
                            }
                            String typesell = "";
                            if (cu.getSell_std_type().equals("0")) {
                                typesell = "checked";
                            }

                            String bcedis = "";
                            boolean dt = false;

                            String modificafiliale = "";

                            if (editce.equals("0")) {
                                bcedis = "readonly";
                                modificafiliale = "readonly";
                            }

                            if (editce.equals("1") || bra[0].equals("000")) {
                                dt = true;
                            }

                            boolean isCentral = Engine.isCentral();

                            String centrale = "";
                            if (isCentral) {
                                centrale = " style='display:none;' ";
                            }

                            String localnonmod = "";
                            if (local) {
                                localnonmod = "readonly";
                            }

                            String enablesellback = "";
                            if (cu.getEnable_sellback().equals("1")) {
                                enablesellback = "checked";
                            }

                            if (view.equals("1")) {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Currency: <%=Utility.sanitize(cu.getCode())%> - <%=Utility.sanitize(cu.getDescrizione())%></span>
                                        </div>
                                        <div class="tools"> 
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Basic </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> Rate Buy/Sell </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3"data-toggle="tab"> Figures </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_4"data-toggle="tab"> Sizes </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_5"data-toggle="tab"> History Rate </a>
                                            </li>
                                        </ul>

                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" id="cur_code" name="cur_code"
                                                                   onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getCode())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">UIC Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" id="uic_code" 
                                                                   name="uic_code" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getUic())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" readonly="readonly" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getDescrizione())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Message</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" readonly="readonly"
                                                                   onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getMessage())%>" 
                                                                   > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Local Currency</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=Utility.sanitize(intern)%> readonly="readonly"
                                                                   id="internal" name="internal" onkeypress="return keysub(this, event);" 
                                                                   data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sell Back</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=Utility.sanitize(enablesellback)%> readonly="readonly"
                                                                   id="sellba" name="sellba" onkeypress="return keysub(this, event);" 
                                                                   data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">BCE Rate</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">

                                                                <input type="text" class="form-control" 
                                                                       name="bce" readonly="readonly"
                                                                       onchange="return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 8)"
                                                                       onkeypress="return keysub(this, event);" 
                                                                       value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(cu.getCambio_bce()))%>" <%=Utility.sanitize(bcedis)%>/>
                                                                <input type="hidden" name="oldbce" value="<%=Utility.sanitize(cu.getCambio_bce())%>"   />
                                                            </div>
                                                        </div>
                                                    </div>    

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Buy</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" <%=Utility.sanitize(enablebuy)%> class="make-switch" readonly="readonly"
                                                                   id="buy" name="buy" onkeypress="return keysub(this, event);" data-size="normal" data-on-color="success"
                                                                   data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sell</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" <%=Utility.sanitize(enablesell)%> class="make-switch" readonly="readonly"
                                                                   id="sell" name="sell" onkeypress="return keysub(this, event);" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <%if (modrate) {%>               
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Type Buy </label>
                                                        <div class="col-md-3">
                                                            <%if (session.getAttribute("us_tip").equals("3")) {%>
                                                            <input type="checkbox" <%=Utility.sanitize(typebuy)%> class="make-switch" readonly="readonly"
                                                                   id="typebuy_0" name="typebuy_0" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="info"
                                                                   data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                   onchange="return typebuy();">
                                                            <%} else {%>
                                                            <input type="checkbox" disabled class="make-switch" readonly="readonly"
                                                                   id="typebuy_0" name="typebuy_0" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="info"
                                                                   data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                   >
                                                            <%}%>

                                                        </div>
                                                        <label class="col-md-3 control-label">Buy Standard</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon" id="buystd_normal">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" 
                                                                       id="buy_std_perc" name="buy_std_perc" readonly="readonly"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" 
                                                                       value="<%=cu.getBuy_std()%>"maxlength="6"> 
                                                            </div>
                                                            <div class="input-icon" id="buystd_value" style="display: none;">

                                                                <input type="text" class="form-control" readonly="readonly"
                                                                       onchange="return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 8);"
                                                                       id="buy_std_val" name="buy_std_val" 
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(cu.getBuy_std_value())%>"> 
                                                                <input type="hidden" name="old_buy_std_val" value="<%=Utility.sanitize(cu.getBuy_std_value())%>"/>
                                                            </div>

                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Buy Level 1</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="buy_l1" name="buy_l1" readonly="readonly"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l1())%>"> 
                                                            </div>
                                                        </div>
                                                        <label class="col-md-3 control-label">Buy Level 2</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="buy_l2" name="buy_l2" readonly="readonly"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l2())%>"> 
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Buy Level 3</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="buy_l3" name="buy_l3" readonly="readonly"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l3())%>"> 
                                                            </div>
                                                        </div>
                                                        <label class="col-md-3 control-label">Buy Best</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="buy_best" name="buy_best" readonly="readonly"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_best())%>"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%} else {
                                                        if (cu.getBuy_std_type().equals("0")) {
                                                            typebuy = "on";
                                                        } else {
                                                            typebuy = null;
                                                        }%>

                                                    <input type="hidden" name="typebuy_0" value="<%=Utility.sanitize(typebuy)%>"/>
                                                    <input type="hidden" name="buy_std_perc" value="<%=Utility.sanitize(cu.getBuy_std())%>"/>
                                                    <input type="hidden" name="buy_std_val" value="<%=Utility.sanitize(cu.getBuy_std_value())%>"/>
                                                    <input type="hidden" name="buy_l1" value="<%=Utility.sanitize(cu.getBuy_l1())%>"/> 
                                                    <input type="hidden" name="buy_l2" value="<%=Utility.sanitize(cu.getBuy_l2())%>"/>
                                                    <input type="hidden" name="buy_l3" value="<%=Utility.sanitize(cu.getBuy_l3())%>"/> 
                                                    <input type="hidden" name="buy_best" value="<%=Utility.sanitize(cu.getBuy_best())%>"/> 

                                                    <%}%>



                                                    <%if (modrate) {%>  
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Type Sell</label>
                                                        <div class="col-md-3">



                                                            <%if (session.getAttribute("us_tip").equals("3")) {%>
                                                            <input type="checkbox" class="make-switch"
                                                                   id="typesell_0" name="typesell_0" onkeypress="return keysub(this, event);" readonly="readonly"
                                                                   <%=Utility.sanitize(typesell)%> data-size="normal" data-on-color="primary" data-off-color="info"
                                                                   data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                   onchange="return typesell();">
                                                            <%} else {%>
                                                            <input type="checkbox" disabled class="make-switch" readonly="readonly"
                                                                   id="typesell_0" name="typesell_0" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="info"
                                                                   data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                   onchange="return typesell();">
                                                            <%}%>
                                                        </div>     
                                                        <label class="col-md-3 control-label">Sell Standard</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon"id="sellstd_per">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="sell_std_perc" readonly="readonly"
                                                                       name="sell_std_perc" onkeypress="return keysub(this, event);" value="<%=cu.getSell_std()%>"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       > 
                                                            </div>
                                                            <div class="input-icon" id="sellstd_value" style="display: none;">

                                                                <input type="text" class="form-control" 
                                                                       value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(cu.getSell_std_value())%>" readonly="readonly"
                                                                       onchange="return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 8);"
                                                                       id="sell_std_val" name="sell_std_val" onkeypress="return keysub(this, event);"> 
                                                                <input type="hidden" name="old_sell_std_val" value="<%=cu.getSell_std_value()%>"/>
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="form-group">

                                                        <label class="col-md-3 control-label">Sell Level 1</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="sell_l1" readonly="readonly"
                                                                       name="sell_l1" onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=cu.getSell_l1()%>"> 
                                                            </div>
                                                        </div>
                                                        <label class="col-md-3 control-label">Sell Level 2</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="sell_l2" name="sell_l2" readonly="readonly"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=cu.getSell_l2()%>"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">

                                                        <label class="col-md-3 control-label">Sell Level 3</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="sell_l3" readonly="readonly"
                                                                       name="sell_l3" onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=cu.getSell_l3()%>"> 
                                                            </div>
                                                        </div>

                                                        <label class="col-md-3 control-label">Sell Best</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="sell_be" name="sell_be" readonly="readonly"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=cu.getSell_best()%>"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%} else {
                                                        if (cu.getSell_std_type().equals("0")) {
                                                            typesell = "on";
                                                        } else {
                                                            typesell = null;
                                                        }


                                                    %>

                                                    <input type="hidden" name="typesell_0" value="<%=Utility.sanitize(typesell)%>"/>
                                                    <input type="hidden" name="sell_std_perc" value="<%=cu.getSell_std()%>"/>
                                                    <input type="hidden" name="sell_std_val" value="<%=cu.getSell_std_value()%>"/>
                                                    <input type="hidden" name="sell_l1" value="<%=cu.getSell_l1()%>"/> 
                                                    <input type="hidden" name="sell_l2" value="<%=cu.getSell_l2()%>"/>
                                                    <input type="hidden" name="sell_l3" value="<%=cu.getSell_l3()%>"/> 
                                                    <input type="hidden" name="sell_be" value="<%=cu.getSell_best()%>"/> 

                                                    <%}%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">External Rate</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="sell_ext" name="sell_ext" readonly="readonly"
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(cu.getChange_sell())%>"/> 
                                                            </div>


                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_3">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <%
                                                            for (int j = 0; j < list_kind.size(); j++) {
                                                                String kind = list_kind.get(j)[0];
                                                                String kind_de = list_kind.get(j)[1];

                                                                int indexKind = Engine.index_containsKind(list_kind_currency, kind);

                                                                String statuskind = "";
                                                                if (indexKind > -1) {
                                                                    if (list_kind_currency.get(indexKind)[1].equals("0")) {
                                                                        statuskind = "checked";
                                                                    }
                                                        %>
                                                        <div class="form-group">
                                                            <div class="col-md-offset-3 col-md-3">
                                                                <label><%=kind_de%></label>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <input type="checkbox" class="make-switch" readonly="readonly"
                                                                       id="statkind<%=kind%>" name="statkind<%=kind%>" onkeypress="return keysub(this, event);" <%=statuskind%> 
                                                                       data-size="normal" data-on-color="success" data-off-color="danger"
                                                                       data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>"
                                                                       >
                                                            </div>
                                                        </div>
                                                        <%} else {%>
                                                        <div class="form-group">
                                                            <div class="col-md-offset-3 col-md-3">
                                                                <label><%=kind_de%></label>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <input type="checkbox" class="make-switch" readonly="readonly"
                                                                       id="statkind<%=kind%>" name="statkind<%=kind%>" onkeypress="return keysub(this, event);" <%=statuskind%> 
                                                                       data-size="normal" data-on-color="success" data-off-color="danger"
                                                                       data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>"
                                                                       >
                                                            </div>
                                                        </div>
                                                        <%}%>
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_4" name="tab_1_4">
                                                <%for (int i = 0; i < list_sizecuts_new.size(); i++) {%>
                                                <div class="form-group">
                                                    <div class="col-md-offset-3 col-md-3">
                                                        <input type="text" class="form-control" id="sizecut_<%=i%>"  readonly="readonly"
                                                               name="sizecut_<%=i%>"value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(list_sizecuts_new.get(i).getIp_taglio())%>"
                                                               onchange="return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 2);"
                                                               /> 
                                                        <input type="hidden" class="form-control" name="sizecut_old_<%=i%>" value="<%=list_sizecuts_new.get(i).getIp_taglio()%>"> 
                                                    </div>
                                                    <%
                                                        String ch1 = "";
                                                        if(list_sizecuts_new.get(i).getFg_stato().equals("1")){
                                                            ch1 = "checked";
                                                        }
                                                    %>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch" <%=ch1%> readonly="readonly"
                                                                       id="statsizecut_<%=i%>" name="statsizecut_<%=i%>" onkeypress="return keysub(this, event);"  
                                                                       data-size="normal" data-on-color="success" data-off-color="danger"
                                                                       data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>"
                                                                       />
                                                        </div>
                                                </div>
                                                <%}%>
                                                <input type="hidden" name="sizecutindex" id="sizecutindex" value="<%=(list_sizecuts_new.size() - 1)%>"/>
                                                <%if (editce.equals("1")) { %>
                                                <hr>
                                                <div class="form-group">
                                                    <div class="col-md-offset-3 col-md-3">
                                                        <button type="button" class="btn btn-outline blue" name="add1" onclick="return add();"><i class="fa fa-plus-circle"></i> Add</button>
                                                    </div>
                                                </div>
                                                <%}%>
                                            </div>
                                            
                                            
                                            
                                            <div class="tab-pane fade" id="tab_1_5" name="tab_1_5">
                                                <%
                                                    ArrayList<Rate_history> li = Engine.list_ratehistory(bra[0], request.getParameter("cur_code"));
                                                    if (li.size() > 0) {
                                                %>
                                                <table class="table table-responsive table-bordered table-hover" id="sample_0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow" style="width: 100px;">Type</th>
                                                            <th class="tabnow">User</th>
                                                            <th class="tabnow">Description</th>
                                                            <th class="tabnow">Date</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%for (int i = 0; i < li.size(); i++) {
                                                                Rate_history rh = li.get(i);
                                                        %>
                                                        <tr>
                                                            <td><%=rh.formatType(rh.getTipomod())%></td>
                                                            <td><%=rh.getUser()%></td>
                                                            <td><%=rh.getModify()%></td>
                                                            <td><%=Utility.formatStringtoStringDate(rh.getDt_mod(), Constant.patternsqldate, Constant.patternnormdate)%></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>
                                                <%} else {%>
                                                <div class="alert">
                                                    <center><b>No results found.</b></center>
                                                </div>
                                                <%}%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%} else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_curre" onsubmit="return checkdescr();">
                        <input type="hidden" name="listbranch" value="<%=listbranch%>" />
                        <input type="hidden" name="editce" value="<%=editce%>" />
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Currency: <%=cu.getCode()%> - <%=cu.getDescrizione()%></span>
                                        </div>
                                        <div class="tools"> 
                                            <%
                                                if (dt) {
                                            %>
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
                                            <%} else {%>
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                            <input type="hidden" id="dt_val" name="dt_val" value="-"/>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Basic </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> Rate Buy/Sell </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3"data-toggle="tab"> Figures </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_4"data-toggle="tab"> Sizes </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_5"data-toggle="tab"> History Rate </a>
                                            </li>
                                        </ul>

                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" id="cur_code" name="cur_code" onkeypress="return keysub(this, event);" value="<%=cu.getCode()%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">UIC Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" <%=Utility.sanitize(modificafiliale)%> id="uic_code" name="uic_code" onchange="return fieldOnlyNumber(this.id);" 
                                                                   onkeypress="return keysub(this, event);" value="<%=cu.getUic()%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control"<%=Utility.sanitize(modificafiliale)%> id="descr" name="descr" onkeypress="return keysub(this, event);" value="<%=cu.getDescrizione()%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Message</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="messa" name="messa" <%=Utility.sanitize(modificafiliale)%>
                                                                   onkeypress="return keysub(this, event);" value="<%=cu.getMessage()%>" 
                                                                   maxlength="100"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Local Currency</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=intern%> <%=Utility.sanitize(modificafiliale)%>
                                                                   id="internal" name="internal" onkeypress="return keysub(this, event);" 
                                                                   data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Yes</span>" data-off-text="<span class='tabnow'>No</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sell Back</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=enablesellback%>  <%=Utility.sanitize(modificafiliale)%>
                                                                   id="sellba" name="sellba" onkeypress="return keysub(this, event);" 
                                                                   data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" 
                                                                   data-off-text="<span class='tabnow'>Disabled</span>" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">BCE Rate</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">

                                                                <input type="text" class="form-control" 
                                                                       name="bce" <%=Utility.sanitize(localnonmod)%>
                                                                       onchange="return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 8)"
                                                                       onkeypress="return keysub(this, event);" 
                                                                       value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(Utility.roundDoubleandFormat(Utility.fd(cu.getCambio_bce()), 8))%>" <%=bcedis%>/>
                                                                <input type="hidden" name="oldbce" value="<%=cu.getCambio_bce()%>"   />
                                                            </div>
                                                        </div>
                                                    </div>    

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Buy</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" <%=Utility.sanitize(enablebuy)%> class="make-switch" <%=Utility.sanitize(modificafiliale)%> 
                                                                   id="buy" name="buy" onkeypress="return keysub(this, event);" data-size="normal" data-on-color="success"
                                                                   data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sell</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" <%=Utility.sanitize(enablesell)%> class="make-switch" <%=Utility.sanitize(modificafiliale)%>  
                                                                   id="sell" name="sell" onkeypress="return keysub(this, event);" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <%if (modrate) {

                                                            if (editce.equals("0")) { //per la filiale si modifica solo il currency
                                                                typebuy = "";
                                                                typesell = "";
                                                            }
                                                    %>               
                                                    <div <%=centrale%>>
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label">Type Buy </label>
                                                            <div class="col-md-3">
                                                                <%if (session.getAttribute("us_tip").equals("3")) {%>
                                                                <input type="checkbox" <%=Utility.sanitize(typebuy%> class="make-switch" <%=Utility.sanitize(modificafiliale)%>
                                                                       id="typebuy_0" name="typebuy_0" onkeypress="return keysub(this, event);"
                                                                       data-size="normal" data-on-color="primary" data-off-color="info"
                                                                       data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                       onchange="return typebuy();">
                                                                <%} else {%>
                                                                <input type="checkbox" disabled class="make-switch"
                                                                       id="typebuy_0" name="typebuy_0" onkeypress="return keysub(this, event);"
                                                                       data-size="normal" data-on-color="primary" data-off-color="info"
                                                                       data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                       >
                                                                <%}%>

                                                            </div>
                                                            <label class="col-md-3 control-label">Buy Standard</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon" id="buystd_normal">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" <%=Utility.sanitize(modificafiliale)%>  <%=Utility.sanitize(localnonmod)%>
                                                                           id="buy_std_perc" name="buy_std_perc"
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           onkeypress="return keysub(this, event);" 
                                                                           value="<%=cu.getBuy_std()%>"maxlength="6"> 
                                                                </div>
                                                                <div class="input-icon" id="buystd_value" style="display: none;">

                                                                    <input type="text" class="form-control"  <%=Utility.sanitize(localnonmod)%>
                                                                           onchange="return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 8);"
                                                                           id="buy_std_val" name="buy_std_val" 
                                                                           onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(cu.getBuy_std_value())%>"> 
                                                                    <input type="hidden" name="old_buy_std_val" value="<%=cu.getBuy_std_value()%>"/>
                                                                </div>

                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label">Buy Level 1</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="buy_l1" name="buy_l1" <%=Utility.sanitize(modificafiliale)%>
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l1())%>"  <%=Utility.sanitize(localnonmod)%>> 
                                                                </div>
                                                            </div>
                                                            <label class="col-md-3 control-label">Buy Level 2</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="buy_l2" name="buy_l2" <%=Utility.sanitize(modificafiliale)%>
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l2())%>"  <%=Utility.sanitize(localnonmod)%>> 
                                                                </div>
                                                            </div>

                                                        </div>
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label">Buy Level 3</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="buy_l3" name="buy_l3" <%=Utility.sanitize(modificafiliale)%>
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l3())%>"  <%=Utility.sanitize(localnonmod)%>> 
                                                                </div>
                                                            </div>
                                                            <label class="col-md-3 control-label">Buy Best</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="buy_best" name="buy_best" <%=Utility.sanitize(modificafiliale)%>
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           onkeypress="return keysub(this, event);" value="<%=cu.getBuy_best()%>" <%=Utility.sanitize(localnonmod)%>> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%} else {
                                                        if (cu.getBuy_std_type().equals("0")) {
                                                            typebuy = "on";
                                                        } else {
                                                            typebuy = null;
                                                        }%>

                                                    <input type="hidden" name="typebuy_0" value="<%=Utility.sanitize(typebuy)%>"/>
                                                    <input type="hidden" name="buy_std_perc" value="<%=Utility.sanitize(cu.getBuy_std())%>"/>
                                                    <input type="hidden" name="buy_std_val" value="<%=Utility.sanitize(cu.getBuy_std_value())%>"/>
                                                    <input type="hidden" name="buy_l1" value="<%=Utility.sanitize(cu.getBuy_l1())%>"/> 
                                                    <input type="hidden" name="buy_l2" value="<%=Utility.sanitize(cu.getBuy_l2())%>"/>
                                                    <input type="hidden" name="buy_l3" value="<%=Utility.sanitize(cu.getBuy_l3())%>"/> 
                                                    <input type="hidden" name="buy_best" value="<%=Utility.sanitize(cu.getBuy_best())%>"/> 

                                                    <%}%>



                                                    <%if (modrate) {%>  
                                                    <div <%=centrale%>>
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label">Type Sell</label>
                                                            <div class="col-md-3">



                                                                <%if (session.getAttribute("us_tip").equals("3")) {%>
                                                                <input type="checkbox" class="make-switch" <%=Utility.sanitize(modificafiliale)%>
                                                                       id="typesell_0" name="typesell_0" onkeypress="return keysub(this, event);"
                                                                       <%=Utility.sanitize(typesell)%> data-size="normal" data-on-color="primary" data-off-color="info"
                                                                       data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                       onchange="return typesell();">
                                                                <%} else {%>
                                                                <input type="checkbox" disabled class="make-switch"  <%=Utility.sanitize(localnonmod)%>
                                                                       id="typesell_0" name="typesell_0" onkeypress="return keysub(this, event);"
                                                                       data-size="normal" data-on-color="primary" data-off-color="info"
                                                                       data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                       onchange="return typesell();">
                                                                <%}%>
                                                            </div>     
                                                            <label class="col-md-3 control-label">Sell Standard</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon"id="sellstd_per">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_std_perc" <%=Utility.sanitize(modificafiliale)%>  <%=Utility.sanitize(localnonmod)%>
                                                                           name="sell_std_perc" onkeypress="return keysub(this, event);" value="<%=cu.getSell_std()%>"
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           > 
                                                                </div>
                                                                <div class="input-icon" id="sellstd_value" style="display: none;">

                                                                    <input type="text" class="form-control" 
                                                                           value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(cu.getSell_std_value())%>"  <%=Utility.sanitize(localnonmod)%>
                                                                           onchange="return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 8);"
                                                                           id="sell_std_val" name="sell_std_val" onkeypress="return keysub(this, event);"> 
                                                                    <input type="hidden" name="old_sell_std_val" value="<%=cu.getSell_std_value()%>"/>
                                                                </div>
                                                            </div>

                                                        </div>
                                                        <div class="form-group">

                                                            <label class="col-md-3 control-label">Sell Level 1</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_l1" name="sell_l1" onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           <%=Utility.sanitize(modificafiliale)%>  <%=Utility.sanitize(localnonmod)%> onkeypress="return keysub(this, event);" value="<%=cu.getSell_l1()%>"> 
                                                                </div>
                                                            </div>
                                                            <label class="col-md-3 control-label">Sell Level 2</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_l2" name="sell_l2" onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           <%=Utility.sanitize(modificafiliale)%>  <%=Utility.sanitize(localnonmod)%> onkeypress="return keysub(this, event);" value="<%=cu.getSell_l2()%>"> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">

                                                            <label class="col-md-3 control-label">Sell Level 3</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_l3" name="sell_l3" onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           <%=Utility.sanitize(modificafiliale)%>  <%=Utility.sanitize(localnonmod)%> onkeypress="return keysub(this, event);" value="<%=cu.getSell_l3()%>"> 
                                                                </div>
                                                            </div>

                                                            <label class="col-md-3 control-label">Sell Best</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_be" name="sell_be" 
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                           <%=Utility.sanitize(modificafiliale)%>  <%=Utility.sanitize(localnonmod)%> onkeypress="return keysub(this, event);" value="<%=cu.getSell_best()%>"> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <%} else {
                                                        if (cu.getSell_std_type().equals("0")) {
                                                            typesell = "on";
                                                        } else {
                                                            typesell = null;
                                                        }


                                                    %>

                                                    <input type="hidden" name="typesell_0" value="<%=Utility.sanitize(typesell)%>"/>
                                                    <input type="hidden" name="sell_std_perc" value="<%=Utility.sanitize(cu.getSell_std())%>"/>
                                                    <input type="hidden" name="sell_std_val" value="<%=Utility.sanitize(cu.getSell_std_value())%>"/>
                                                    <input type="hidden" name="sell_l1" value="<%=Utility.sanitize(cu.getSell_l1())%>"/> 
                                                    <input type="hidden" name="sell_l2" value="<%=Utility.sanitize(cu.getSell_l2())%>"/>
                                                    <input type="hidden" name="sell_l3" value="<%=Utility.sanitize(cu.getSell_l3())%>"/> 
                                                    <input type="hidden" name="sell_be" value="<%=Utility.sanitize(cu.getSell_best())%>"/> 

                                                    <%}%>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">External Rate</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="sell_ext" name="sell_ext"  <%=Utility.sanitize(localnonmod)%>
                                                                       <%=Utility.sanitize(modificafiliale)%>   onchange="formatValueDecimalMax(this, '100' + '<%=Utility.sanitize(decimal)%>' + '00', '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(Utility.sanitize(Utility.formatMysqltoDisplay(cu.getChange_sell()))%>"/> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_3">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <%
                                                            for (int j = 0; j < list_kind.size(); j++) {
                                                                String kind = list_kind.get(j)[0];
                                                                String kind_de = list_kind.get(j)[1];

                                                                int indexKind = Engine.index_containsKind(list_kind_currency, kind);

                                                                String statuskind = "";
                                                                if (indexKind > -1) {
                                                                    if (list_kind_currency.get(indexKind)[1].equals("0")) {
                                                                        statuskind = "checked";
                                                                    }
                                                        %>
                                                        <div class="form-group">
                                                            <div class="col-md-offset-3 col-md-3">
                                                                <label><%=kind_de%></label>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <input type="checkbox" class="make-switch" <%=Utility.sanitize(modificafiliale)%>
                                                                       id="statkind<%=Utility.sanitize(kind)%>" name="statkind<%=Utility.sanitize(kind)%>" onkeypress="return keysub(this, event);" <%=Utility.sanitize(statuskind)%> 
                                                                       data-size="normal" data-on-color="success" data-off-color="danger"
                                                                       data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>"
                                                                       >
                                                            </div>
                                                        </div>
                                                        <%} else {%>
                                                        <div class="form-group">
                                                            <div class="col-md-offset-3 col-md-3">
                                                                <label><%=kind_de%></label>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <input type="checkbox" class="make-switch" <%=Utility.sanitize(modificafiliale)%>
                                                                       id="statkind<%=Utility.sanitize(kind)%>" name="statkind<%=Utility.sanitize(kind)%>" onkeypress="return keysub(this, event);" <%=Utility.sanitize(statuskind)%> 
                                                                       data-size="normal" data-on-color="success" data-off-color="danger"
                                                                       data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>"
                                                                       >
                                                            </div>
                                                        </div>
                                                        <%}%>
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="tab-pane fade" id="tab_1_4" name="tab_1_4">
                                                <%for (int i = 0; i < list_sizecuts_new.size(); i++) {%>
                                                <div class="form-group">
                                                    <div class="col-md-offset-3 col-md-3">
                                                        <input type="text" class="form-control" id="sizecut_<%=i%>" name="sizecut_<%=i%>" 
                                                               value="<%=Utility.sanitize(Utility.formatMysqltoDisplay(list_sizecuts_new.get(i).getIp_taglio())%>" <%=Utility.sanitize(modificafiliale)%>
                                                               onchange="return formatValueDecimal_length(this, '<%=Utility.sanitize(thousand)%>', '<%=Utility.sanitize(decimal)%>', 2);"
                                                               > 
                                                        <input type="hidden" class="form-control" name="sizecut_old_<%=i%>" value="<%=list_sizecuts_new.get(i).getIp_taglio()%>"> 
                                                    </div>
                                                    <%
                                                        String ch1 = "";
                                                        if(list_sizecuts_new.get(i).getFg_stato().equals("1")){
                                                            ch1 = "checked";
                                                        }
                                                    %>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch" <%=Utility.sanitize(modificafiliale)%> <%=ch1%>
                                                                       id="statsizecut_<%=i%>" name="statsizecut_<%=i%>" onkeypress="return keysub(this, event);"  
                                                                       data-size="normal" data-on-color="success" data-off-color="danger"
                                                                       data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>"
                                                                       />
                                                        </div>
                                                </div>
                                                <%}%>
                                                <input type="hidden" name="sizecutindex" id="sizecutindex" value="<%=(list_sizecuts_new.size() - 1)%>"/>
                                                <%if (editce.equals("1")) { %>
                                                <hr>
                                                <div class="form-group">
                                                    <div class="col-md-offset-3 col-md-3">
                                                        <button type="button" class="btn btn-outline blue" name="add1" onclick="return add();"><i class="fa fa-plus-circle"></i> Add</button>
                                                    </div>
                                                </div>
                                                <%}%>
                                            </div>
                                            <div class="tab-pane fade" id="tab_1_5" name="tab_1_5">
                                                <%
                                                    ArrayList<Rate_history> li = Engine.list_ratehistory(bra[0], request.getParameter("cur_code"));

                                                    if (li.size() > 0) {

                                                %>


                                                <table class="table table-responsive table-bordered table-hover" id="sample_0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th class="tabnow" style="width: 100px;">Type</th>
                                                            <th class="tabnow">User</th>
                                                            <th class="tabnow">Description</th>
                                                            <th class="tabnow">Date</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%for (int i = 0; i < li.size(); i++) {
                                                                Rate_history rh = li.get(i);
                                                        %>
                                                        <tr>
                                                            <td><%=Utility.sanitize(rh.formatType(rh.getTipomod()))%></td>
                                                            <td><%=Utility.sanitize(rh.getUser())%></td>
                                                            <td><%=Utility.sanitize(rh.getModify())%></td>
                                                            <td><%=Utility.sanitize(Utility.formatStringtoStringDate(rh.getDt_mod(), Constant.patternsqldate, Constant.patternnormdate))%></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>
                                                <hr>
                                                <%} else {%>
                                                <div class="alert">
                                                    <center><b>No results found.</b></center>
                                                </div>
                                                <%}%>

                                                <%if (li.size() > 0) {%>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <!--<a onclick="document.getElementById('excel1').submit();" class="btn btn-outline green-jungle"><i class="fa fa-file-excel-o"></i> Export Excel (All Branch)</a>-->
                                                        <a href="tb_exportexcelhis.jsp?cur_code=<%=request.getParameter("cur_code")%>" class="btn btn-outline green-jungle"><i class="fa fa-file-excel-o"></i> Export Excel (All Branch)</a>
                                                    </div>
                                                </div>
                                                <%}%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                    <%}%>



                    <%} else {%>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-danger">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> The operation could not be completed. Try Again.
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

            <!-- END PAGE LEVEL PLUGINS -->
            <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>


            <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>


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
                                                                        processing: true,
                                                                        columnDefs: [
                                                                            {orderable: 1, targets: [0]},
                                                                            {orderable: 1, targets: [1]},
                                                                            {orderable: 1, targets: [2]},
                                                                            {orderable: !1, targets: [3]}
                                                                        ],
                                                                        colReorder: {reorderCallback: function () {
                                                                                
                                                                            }},
                                                                        lengthMenu: [
                                                                            [25, -1],
                                                                            [25, "All"]
                                                                        ],
                                                                        pageLength: 25,
                                                                        order: [],
                                                                        dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
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
