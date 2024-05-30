<%@page import="rc.so.entity.Rate_history"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.Currency"%>
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

            if (listbranch != null && listbranch.length() < 5) {
                bra = Utility.parseArrayValues(listbranch, ";");
            }

            Currency cu = Engine.getCurrency(request.getParameter("cur_code"), bra[0]);
            if (cu == null) {
                cu = Engine.getCurrency(request.getParameter("cur_code"), "000");
            }
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;

        %>
        <script type="text/javascript">
            function checkdescr() {

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

            function loadpage() {
                inputvirgola();
                typebuy();
                typesell();
                formatValueDecimalMax(document.getElementById('buy_std_perc'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('buy_l1'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('buy_l2'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('buy_l3'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('buy_best'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('sell_l1'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('sell_l2'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('sell_l3'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('sell_be'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');
                formatValueDecimalMax(document.getElementById('sell_std_perc'), '100' + '<%=decimal%>' + '00', '', '<%=decimal%>');

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
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
                    <%}%>  
                    
                    
                    <%

                        if (cu != null) {

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

                            String localnonmod = "";
                            if (cu.getInternal_cur().equals("1")) {
                                localnonmod = "readonly";
                            }

                            String bcedis = "";
                            boolean dt = true;

                    %>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_spread" onsubmit="return checkdescr();">
                        <input type="hidden" name="listbranch" value="<%=Utility.sanitize(listbranch)%>" />
                        <input type="hidden" name="cur_code" value="<%=Utility.sanitize(request.getParameter("cur_code"))%>" />
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Currency: <%=Utility.sanitize(cu.getCode())%> - <%=Utility.sanitize(cu.getDescrizione())%></span>
                                        </div>
                                        <div class="tools"> 
                                            <%
                                                if (cu.getInternal_cur().equals("0")) {
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
                                            <%}
                                            } else {%>
                                            <div class="form-group">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <label class="font-red control-label"><b>Local Currency cannot be modified.</b></label>
                                                    </div>
                                                </div>
                                            </div>
                                            <%}%>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_2" data-toggle="tab"> Rate Buy/Sell </a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_2">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">BCE Rate</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <input type="text" class="form-control" 
                                                                       name="bce" readonly
                                                                       onchange="return formatValueDecimal_length(this, '<%=thousand%>', '<%=decimal%>', 8)"
                                                                       onkeypress="return keysub(this, event);" 
                                                                       value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(cu.getCambio_bce()))%>" <%=Utility.sanitize(bcedis)%>/>
                                                                <input type="hidden" name="oldbce" value="<%=Utility.sanitize(cu.getCambio_bce())%>"   />
                                                            </div>
                                                        </div>
                                                    </div>    

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Buy</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" <%=enablebuy%> class="make-switch" readonly
                                                                   id="buy" name="buy" onkeypress="return keysub(this, event);" data-size="normal" data-on-color="success"
                                                                   data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sell</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" <%=enablesell%> class="make-switch" readonly
                                                                   id="sell" name="sell" onkeypress="return keysub(this, event);" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Type Buy </label>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" <%=typebuy%> class="make-switch" 
                                                                   id="typebuy_0" name="typebuy_0" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="info"
                                                                   data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                   onchange="return typebuy();">
                                                        </div>
                                                        <label class="col-md-3 control-label">Buy Standard</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon" id="buystd_normal">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control"
                                                                       id="buy_std_perc" name="buy_std_perc" <%=localnonmod%>
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                       onkeypress="return keysub(this, event);" 
                                                                       value="<%=Utility.sanitize(cu.getBuy_std())%>"maxlength="6"> 
                                                            </div>
                                                            <div class="input-icon" id="buystd_value" style="display: none;">

                                                                <input type="text" class="form-control" 
                                                                       onchange="return formatValueDecimal_length(this, '<%=thousand%>', '<%=decimal%>', 8);"
                                                                       id="buy_std_val" name="buy_std_val" <%=localnonmod%>
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(cu.getBuy_std_value()))%>"> 
                                                                <input type="hidden" name="old_buy_std_val" value="<%=Utility.sanitize(cu.getBuy_std_value())%>"/>
                                                            </div>

                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Buy Level 1</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="buy_l1" name="buy_l1"<%=localnonmod%>
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l1())%>"> 
                                                            </div>
                                                        </div>
                                                        <label class="col-md-3 control-label">Buy Level 2</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="buy_l2" name="buy_l2"<%=localnonmod%>
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l2())%>"> 
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Buy Level 3</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="buy_l3" name="buy_l3" <%=localnonmod%>
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_l3())%>"> 
                                                            </div>
                                                        </div>
                                                        <label class="col-md-3 control-label">Buy Best</label>
                                                        <div class="col-md-3">
                                                            <div class="input-icon">
                                                                <i class="font-blue fa fa-percent"></i>
                                                                <input type="text" class="form-control" id="buy_best" name="buy_best"<%=localnonmod%>
                                                                       onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                       onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getBuy_best())%>"> 
                                                            </div>
                                                        </div>
                                                    </div>





                                                    <div >
                                                        <div class="form-group">
                                                            <label class="col-md-3 control-label">Type Sell</label>
                                                            <div class="col-md-3">

                                                                <input type="checkbox" class="make-switch"
                                                                       id="typesell_0" name="typesell_0" onkeypress="return keysub(this, event);"
                                                                       <%=typesell%> data-size="normal" data-on-color="primary" data-off-color="info"
                                                                       data-on-text="<span class='tabnow'>Percent %</span>" data-off-text="<span class='tabnow'>Value</span>"
                                                                       onchange="return typesell();">

                                                            </div>     
                                                            <label class="col-md-3 control-label">Sell Standard</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon"id="sellstd_per">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_std_perc" <%=localnonmod%>
                                                                           name="sell_std_perc" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getSell_std())%>"
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                           > 
                                                                </div>
                                                                <div class="input-icon" id="sellstd_value" style="display: none;">

                                                                    <input type="text" class="form-control" <%=localnonmod%>
                                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(cu.getSell_std_value()))%>"
                                                                           onchange="return formatValueDecimal_length(this, '<%=thousand%>', '<%=decimal%>', 8);"
                                                                           id="sell_std_val" name="sell_std_val" onkeypress="return keysub(this, event);"> 
                                                                    <input type="hidden" name="old_sell_std_val" value="<%=Utility.sanitize(cu.getSell_std_value())%>"/>
                                                                </div>
                                                            </div>

                                                        </div>
                                                        <div class="form-group">

                                                            <label class="col-md-3 control-label">Sell Level 1</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_l1" name="sell_l1" onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                           <%=localnonmod%>  onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getSell_l1())%>"> 
                                                                </div>
                                                            </div>
                                                            <label class="col-md-3 control-label">Sell Level 2</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_l2" name="sell_l2" onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                           <%=localnonmod%> onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getSell_l2())%>"> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">

                                                            <label class="col-md-3 control-label">Sell Level 3</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_l3" name="sell_l3" onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                           <%=localnonmod%> onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getSell_l3())%>"> 
                                                                </div>
                                                            </div>

                                                            <label class="col-md-3 control-label">Sell Best</label>
                                                            <div class="col-md-3">
                                                                <div class="input-icon">
                                                                    <i class="font-blue fa fa-percent"></i>
                                                                    <input type="text" class="form-control" id="sell_be" name="sell_be" 
                                                                           onchange="formatValueDecimalMax(this, '100' + '<%=decimal%>' + '00', '<%=thousand%>', '<%=decimal%>');"
                                                                           <%=localnonmod%>  onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(cu.getSell_best())%>"> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>


                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>

                                              


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
