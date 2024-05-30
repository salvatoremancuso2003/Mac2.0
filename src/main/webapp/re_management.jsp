<%@page import="rc.so.entity.Figures"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Etichette"%>
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
        <title>Mac2.0 - Report Management Control</title>
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
        
        
        
        
        <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <%
            ArrayList<Branch> array_branch = Engine.list_branch_completeAFTER311217();
            ArrayList<String[]> list_type = Engine.list_group_branch();
            ArrayList<String[]> list_group = Engine.list_branch_group();
            ArrayList<Figures> figs_buy = Engine.list_figures_buy();
//            ArrayList<String[]> array_kind_pay = Engine.kind_payment();
            ArrayList<String[]> array_country = Engine.country();
        %>


        <script type="text/javascript">

            function loadpage() {

                var mon = moment().format('MM/YYYY');
                $("#date2").val(mon);

                br_gr();
                $('#buysell').select2({
                    dropdownAutoWidth: true
                });
                //var em = $("<option/>", {value: "", text: ""});
                var em1 = $("<option/>", {value: "00", text: "All"});
                var em2 = $("<option/>", {value: "01", text: "Only Buy"});
                var em3 = $("<option/>", {value: "02", text: "Only Sell"});
                $('#buysell').append(em1);
                $('#buysell').append(em2);
                $('#buysell').append(em3);
                $('#buysell').val($('#buysell option:first-child').val()).trigger('change');


                bs();
                abilitafiltri();
            }

            function bs() {
                $('#figures').val(' ').trigger('change');
                $('#sellch').val(' ').trigger('change');
                disable_sel2('figures', 'f1');
                disable_sel2('sellch', 'f1');

                var val = document.getElementById('buysell').value;
                if (val === "00" || val === "01") { //abilita buy
                    enable_sel2('figures', 'f1');
                }
                if (val === "00" || val === "02") { //abilita sell
                    enable_sel2('sellch', 'f1');
                }
                if (val !== "00" && val !== "01" && val !== "02") {
                    //abilita tutto
                    enable_sel2('figures', 'f1');
                    enable_sel2('sellch', 'f1');
                }

            }


            function br_gr() {





                $('#gr1').select2({
                    dropdownAutoWidth: true,
                    allowClear: true,
                    placeholder: " "
                });



                $('#gr1').empty().trigger('change');
                var o = $("<option/>", {value: "", text: ""});
                $('#gr1').append(o);
            <%for (int i = 0; i < list_type.size(); i++) {%>
                var o = $("<option/>", {value: "<%=list_type.get(i)[0]%>", text: "<%=list_type.get(i)[0]%> - <%=list_type.get(i)[1]%>"});
                        $('#gr1').append(o);
            <%}%>

                        $('#gr1').val($('#gr1 option:first-child').val()).trigger('change');


                        var ch = document.getElementById('brgr').checked;

                        if (ch) {
                            enable_sel2('branch', 'f1');
                            disable_sel2('gr1', 'f1');
                            disable_sel2('gr2', 'f1');
                        } else {
                            disable_sel2('branch', 'f1');
                            enable_sel2('gr1', 'f1');
                            enable_sel2('gr2', 'f1');
                        }
                    }

                    function grx() {
                        var gra = document.getElementById('gr1').value;
                        $('#gr2').select2({
                            dropdownAutoWidth: true,
                            allowClear: true,
                            placeholder: " "
                        });

                        $('#gr2').empty().trigger('change');

            <%for (int i = 0; i < list_group.size(); i++) {%>
                        if ("<%=list_group.get(i)[2]%>" === gra) {
                            var o = $("<option/>", {value: "<%=list_group.get(i)[0]%>", text: "<%=list_group.get(i)[0]%> - <%=list_group.get(i)[1]%>"});
                                        $('#gr2').append(o);
                                    }
            <%}%>
                                    //$('#gr2').val($('#gr2 option:first-child').val()).trigger('change');
                                }

                                function versubmit() {

                                    //vale per tutti
                                    var date2 = document.getElementById('date2').value;
                                    if (date2 === "" || date2.length !== 7) {
                                        document.getElementById('date2').value = '';
                                        document.getElementById('errorlargetext').innerHTML = "You must be complete field 'Date (Month/Year)'.";
                                        document.getElementById('errorlargebtn').click();
                                        return false;
                                    }

                                    var tiporeport = document.getElementById('type').value;
                                    if (tiporeport === "giornaliero" || tiporeport === "massimale" || tiporeport === "buyback" || tiporeport === "budget"|| tiporeport === "change"|| tiporeport === "change1") {
                                        var brgr = document.getElementById('brgr');
                                        if (brgr.checked) {
                                            var branch = document.getElementById('branch').value.trim();
                                            if (branch === "") {
                                                document.getElementById('errorlargetext').innerHTML = "You must be complete field 'Branch.";
                                                document.getElementById('errorlargebtn').click();
                                                return false;
                                            }
                                        } else {
                                            var gr1 = document.getElementById('gr1').value.trim();
                                            var gr2 = document.getElementById('gr2').value.trim();
                                            if (gr1 === "" || gr2 === "") {
                                                document.getElementById('errorlargetext').innerHTML = "You must be complete fields 'Type Group' AND 'Groups'.";
                                                document.getElementById('errorlargebtn').click();
                                                return false;
                                            }
                                        }
                                    }
                                }


                                function abilitafiltri() {

                                    var tiporeport = document.getElementById('type').value;

                                    if (tiporeport === "giornaliero" || tiporeport === "massimale" || 
                                            tiporeport === "budget"|| tiporeport === "change" || tiporeport ==='contabilitauk') {
                                        
                                        disable_sel2('buysell', 'f1');
                                        disable_sel2('figures', 'f1');
                                        disable_sel2('sellch', 'f1');
                                        disable_sel2('paym', 'f1');
                                        disable_sel2('natres', 'f1');
                                        disable_sel2('natnas', 'f1');
                                        disable_sel2('business', 'f1');
                                        disable_sel2('fasce', 'f1');
                                        $("[name='Existing']").bootstrapSwitch('disabled', true);
                                        $("[name='det']").bootstrapSwitch('disabled', true);
                                        $("[name='deleted']").bootstrapSwitch('disabled', true);
                                        $("[name='prevyear']").bootstrapSwitch('disabled', true);
                                        $("[name='detdate']").bootstrapSwitch('disabled', true);
                                        $("[name='detcash']").bootstrapSwitch('disabled', true);
                                        $("[name='causal']").bootstrapSwitch('disabled', true);
                                        $('#lab_Existing').addClass("ltr");
                                        $('#lab_det').addClass("ltr");
                                        $('#lab_deleted').addClass("ltr");
                                        $('#lab_prevyear').addClass("ltr");
                                        $('#lab_detdate').addClass("ltr");
                                        $('#lab_detcash').addClass("ltr");
                                        $('#lab_causal').addClass("ltr");
                                        $('#lab_buysell').addClass("ltr");
                                        $('#lab_figures').addClass("ltr");
                                        $('#lab_sellch').addClass("ltr");
                                        $('#lab_paym').addClass("ltr");
                                        $('#lab_natres').addClass("ltr");
                                        $('#lab_natnas').addClass("ltr");
                                        $('#lab_business').addClass("ltr");
                                        $('#lab_fasce').addClass("ltr");
                                        
                                    } else if (tiporeport === "change1") {
                                        disable_sel2('buysell', 'f1');
                                        disable_sel2('figures', 'f1');
                                        disable_sel2('sellch', 'f1');
                                        disable_sel2('paym', 'f1');
                                        disable_sel2('natres', 'f1');
                                        disable_sel2('natnas', 'f1');
                                        disable_sel2('business', 'f1');
                                        disable_sel2('fasce', 'f1');
                                        $("[name='Existing']").bootstrapSwitch('disabled', true);
                                        $("[name='det']").bootstrapSwitch('disabled', true);
                                        $("[name='deleted']").bootstrapSwitch('disabled', false);
                                        
                                        $("[name='prevyear']").bootstrapSwitch('disabled', true);
                                        $("[name='detdate']").bootstrapSwitch('disabled', true);
                                        $("[name='detcash']").bootstrapSwitch('disabled', true);
                                        $("[name='causal']").bootstrapSwitch('disabled', true);
                                        $('#lab_Existing').addClass("ltr");
                                        $('#lab_det').addClass("ltr");
                                        $('#lab_deleted').removeClass("ltr");
                                        $('#lab_prevyear').addClass("ltr");
                                        $('#lab_detdate').addClass("ltr");
                                        $('#lab_detcash').addClass("ltr");
                                        $('#lab_causal').addClass("ltr");
                                        $('#lab_buysell').addClass("ltr");
                                        $('#lab_figures').addClass("ltr");
                                        $('#lab_sellch').addClass("ltr");
                                        $('#lab_paym').addClass("ltr");
                                        $('#lab_natres').addClass("ltr");
                                        $('#lab_natnas').addClass("ltr");
                                        $('#lab_business').addClass("ltr");
                                        $('#lab_fasce').addClass("ltr");
                                    } else if (tiporeport === "citta") {
                                    } else if (tiporeport === "redemption") {
                                    } else if (tiporeport === "online") {
                                    } else if (tiporeport === "fasceorarie") {
                                        disable_sel2('buysell', 'f1');
                                        disable_sel2('figures', 'f1');
                                        disable_sel2('sellch', 'f1');
                                        disable_sel2('paym', 'f1');
                                        disable_sel2('natres', 'f1');
                                        disable_sel2('natnas', 'f1');
                                        disable_sel2('business', 'f1');
                                        enable_sel2('fasce', 'f1');
                                        $("[name='Existing']").bootstrapSwitch('disabled', true);
                                        $("[name='det']").bootstrapSwitch('disabled', true);
                                        $("[name='deleted']").bootstrapSwitch('disabled', true);
                                        $("[name='prevyear']").bootstrapSwitch('disabled', true);
                                        $("[name='detdate']").bootstrapSwitch('disabled', true);
                                        $("[name='detcash']").bootstrapSwitch('disabled', true);
                                        $("[name='causal']").bootstrapSwitch('disabled', true);
                                        $('#lab_Existing').addClass("ltr");
                                        $('#lab_det').addClass("ltr");
                                        $('#lab_deleted').addClass("ltr");
                                        $('#lab_prevyear').addClass("ltr");
                                        $('#lab_detdate').addClass("ltr");
                                        $('#lab_detcash').addClass("ltr");
                                        $('#lab_causal').addClass("ltr");
                                        $('#lab_buysell').addClass("ltr");
                                        $('#lab_figures').addClass("ltr");
                                        $('#lab_sellch').addClass("ltr");
                                        $('#lab_paym').addClass("ltr");
                                        $('#lab_natres').addClass("ltr");
                                        $('#lab_natnas').addClass("ltr");
                                        $('#lab_business').addClass("ltr");
                                        $('#lab_fasce').removeClass("ltr");


                                    } else if (tiporeport === "buyback") {
                                        disable_sel2('buysell', 'f1');
                                        disable_sel2('figures', 'f1');
                                        disable_sel2('sellch', 'f1');
                                        disable_sel2('paym', 'f1');
                                        disable_sel2('natres', 'f1');
                                        disable_sel2('natnas', 'f1');
                                        disable_sel2('business', 'f1');
                                        disable_sel2('fasce', 'f1');
                                        $("[name='Existing']").bootstrapSwitch('disabled', true);
                                        $("[name='det']").bootstrapSwitch('disabled', true);
                                        $("[name='deleted']").bootstrapSwitch('disabled', true);
                                        $("[name='prevyear']").bootstrapSwitch('disabled', false);
                                        $("[name='detdate']").bootstrapSwitch('disabled', true);
                                        $("[name='detcash']").bootstrapSwitch('disabled', true);
                                        $("[name='causal']").bootstrapSwitch('disabled', true);
                                        $('#lab_Existing').addClass("ltr");
                                        $('#lab_det').addClass("ltr");
                                        $('#lab_deleted').addClass("ltr");
                                        $('#lab_prevyear').removeClass("ltr");
                                        $('#lab_detdate').addClass("ltr");
                                        $('#lab_detcash').addClass("ltr");
                                        $('#lab_causal').addClass("ltr");
                                        $('#lab_buysell').addClass("ltr");
                                        $('#lab_figures').addClass("ltr");
                                        $('#lab_sellch').addClass("ltr");
                                        $('#lab_paym').addClass("ltr");
                                        $('#lab_natres').addClass("ltr");
                                        $('#lab_natnas').addClass("ltr");
                                        $('#lab_business').addClass("ltr");
                                        $('#lab_fasce').addClass("ltr");
                                    } else if (tiporeport === "operatore") {
                                    }
                                }
        </script>

        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 




    </head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();" >

        <div class="modal fade g" tabindex="-1" role="dialog" aria-hidden="true">
            <button type="button" id="errorlargebtn" data-toggle="modal" data-target="#errorlarge">Open Modal</button>
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
        <%@ include file="menu/header1.jsp"%>
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <%@ include file="menu/menu_re2.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
            %>



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
                            <h3 class="page-title">Report <small><b>Management Control</b> </small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>


                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT BUY/SELL -->
                    <%
                        boolean iscentral = Engine.isCentral();
                        if (iscentral) {
                    %>
                    <form id="f1" name="f1" action="Gestione" method="post" target="_blank" onsubmit="return versubmit();">
                        <div class="row">
                            <div class="col-md-12">
                                <h4 class="font-yellow bold uppercase">1. SELECT REPORT</h4>
                            </div>
                            <div class="col-md-12">
                                <div class="form-group has-warning">
                                    <select class="form-control select2" name="type" id="type" onchange="return abilitafiltri();">
                                        <%if(Constant.is_UK){%>
                                        <option value="contabilitauk">UK - Report Change Accounting</option>
                                        <%}%>
                                        <option value="giornaliero">Daily Report</option>
                                        <option value="change">Report Change Accounting (No.1)</option>
                                        <option value="change1">Report Change Management Control (No.1)</option>
                                        <option value="massimale">Report Limit Insurance Branch</option>
                                        <option value="budget">Report Budget Branch</option>
                                        <option value="citta">(TBD...) Report City</option>
                                        <option value="redemption">(TBD...) Report Redemption</option>
                                        <option value="online">(TBD...) Report Online</option>
                                        <option value="fasceorarie">Report Time Bands</option>
                                        <option value="buyback">(TBD...) Report BuyBack</option>
                                        <option value="operatore">(TBD...) Report Cashier Profile</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <hr>
                        <div class="row">
                            <div class="col-md-12">
                                <h4 class="font-red-haze bold uppercase">2. General Filter</h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_Existing">Existing</label><p class='ab'></p>   
                                    <input type="checkbox" class="make-switch" 
                                           id="Existing" name="Existing" checked
                                           data-size="normal" data-on-text="YES" data-off-text="NO"
                                           data-on-color="success" data-off-color="danger" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_brgr">Branch/Group</label><p class='ab'></p>   
                                    <input type="checkbox" class="make-switch" onchange="return br_gr();"
                                           id="brgr" name="brgr" checked
                                           data-size="normal" data-on-text="Branch" data-off-text="Group"
                                           data-on-color="success" data-off-color="danger" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_det">Branch Details</label><p class='ab'></p>   
                                    <input type="checkbox" class="make-switch" 
                                           id="det" name="det" checked
                                           data-size="normal" data-on-text="YES" data-off-text="NO"
                                           data-on-color="success" data-off-color="danger" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_deleted">Delete Operations</label><p class='ab'></p>   
                                    <input type="checkbox" class="make-switch" 
                                           id="deleted" name="deleted" checked
                                           data-size="normal" data-on-text="YES" data-off-text="NO"
                                           data-on-color="success" data-off-color="danger" />
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label id="lab_branch">Branch</label>
                                    <select class="form-control select2" name="branch" id="branch" multiple>
                                        <%for (int j = 0; j < array_branch.size(); j++) {%>
                                        <option value="<%=array_branch.get(j).getCod()%>">
                                            <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                        </option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_gr1">Type Group</label>
                                    <select class="form-control select2" name="gr1" id="gr1" onchange="return grx();" style="width:100%;">

                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_gr2">Groups</label>
                                    <select class="form-control select2" name="gr2" id="gr2" multiple style="width:100%;">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-md-12">
                                <h4 class="font-blue-madison bold uppercase">3. Advanced Filter</h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_date2">Date (Month/Year)</label><p class='ab'></p>   
                                    <input type="text" id="date2" name="date2" class="form-control date-picker" 
                                           data-date-format="mm/yyyy" 
                                           data-date-viewmode="years" 
                                           data-date-minviewmode="months">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_prevyear">Previous Year</label><p class='ab'></p>   
                                    <input type="checkbox" class="make-switch" 
                                           id="prevyear" name="prevyear" checked
                                           data-size="normal" data-on-text="YES" data-off-text="NO"
                                           data-on-color="success" data-off-color="danger" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_detdate">Details Date</label><p class='ab'></p>   
                                    <input type="checkbox" class="make-switch" 
                                           id="detdate" name="detdate" checked
                                           data-size="normal" data-on-text="YES" data-off-text="NO"
                                           data-on-color="success" data-off-color="danger" />
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_detcash">Details Cashier</label><p class='ab'></p>   
                                    <input type="checkbox" class="make-switch" 
                                           id="detcash" name="detcash" checked
                                           data-size="normal" data-on-text="YES" data-off-text="NO"
                                           data-on-color="success" data-off-color="danger" />
                                </div>
                            </div>

                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_buysell">Buy/Sell</label>
                                    <select class="form-control select2" name="buysell" id="buysell" style="width:100%;" onchange="bs();">

                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_figures">Figures</label>
                                    <select class="form-control select2" name="figures" id="figures" multiple>
                                        <option value="--">All</option>
                                        <%for (int j = 0; j < figs_buy.size(); j++) {%>
                                        <option value="<%=figs_buy.get(j).getSupporto()%>">
                                            <%=figs_buy.get(j).getSupporto()%> - <%=figs_buy.get(j).getDe_supporto()%>
                                        </option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_sellch">Sell Channel</label>
                                    <select class="form-control select2" name="sellch" id="sellch" multiple>
                                        <option value="--">All</option>
                                        <option value="01">Online</option>
                                        <option value="02">Offline</option>
                                        <option value="03">VAT Refund</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_paym">Payment Mode</label>
                                    <select class="form-control select2" id="paym" name="paym">
                                        <option value="--">All</option>
                                        <option value="01">Notes</option>
                                        <option value="02">C.C. (Credit Card, Bancomat, Bank Credit Account)</option>
                                    </select>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_natres">Country of residence</label>
                                    <select class="form-control select2" name="natres" id="natres">
                                        <option value="--">All</option>
                                        <%for (int j = 0; j < array_country.size(); j++) {%>
                                        <option value="<%=array_country.get(j)[0]%>">
                                            <%=array_country.get(j)[1]%>
                                        </option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_natnas">Birth nation</label>
                                    <select class="form-control select2" name="natnas" id="natnas">
                                        <option value="--">All</option>
                                        <%for (int j = 0; j < array_country.size(); j++) {%>
                                        <option value="<%=array_country.get(j)[0]%>">
                                            <%=array_country.get(j)[1]%>
                                        </option>
                                        <%}%>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_business">Business (No Change)</label>
                                    <select class="form-control select2" name="business" id="business">
                                        <option value="--">All</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_causal">Details Causal (No Change)</label><p class='ab'></p>   
                                    <input type="checkbox" class="make-switch" 
                                           id="causal" name="causal" checked
                                           data-size="normal" data-on-text="YES" data-off-text="NO"
                                           data-on-color="success" data-off-color="danger" />
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-md-3">
                                <div class="form-group">
                                    <label id="lab_fasce">Time Bands</label>
                                    <select class="form-control select2" name="fasce" id="fasce" multiple>
                                        <option value="--">All</option>
                                        <option value="0001">0:00 - 1:00</option>
                                        <option value="0102">1:00 - 2:00</option>
                                        <option value="0203">2:00 - 3:00</option>
                                        <option value="0304">3:00 - 4:00</option>
                                        <option value="0405">4:00 - 5:00</option>
                                        <option value="0506">5:00 - 6:00</option>
                                        <option value="0607">6:00 - 7:00</option>
                                        <option value="0708">7:00 - 8:00</option>
                                        <option value="0809">8:00 - 9:00</option>
                                        <option value="0910">9:00 - 10:00</option>
                                        <option value="1011">10:00 - 11:00</option>
                                        <option value="1112">11:00 - 12:00</option>
                                        <option value="1213">12:00 - 13:00</option>
                                        <option value="1314">13:00 - 14:00</option>
                                        <option value="1415">14:00 - 15:00</option>
                                        <option value="1516">15:00 - 16:00</option>
                                        <option value="1617">16:00 - 17:00</option>
                                        <option value="1718">17:00 - 18:00</option>
                                        <option value="1819">18:00 - 19:00</option>
                                        <option value="1920">19:00 - 20:00</option>
                                        <option value="2021">20:00 - 21:00</option>
                                        <option value="2122">21:00 - 22:00</option>
                                        <option value="2223">22:00 - 23:00</option>
                                        <option value="2324">23:00 - 24:00</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <span class="help-block"><small>N.B. The strikethrough (es.<span class="ltr">Branch Details</span>) filters will not be considered.</small></span>
                            </div>
                        </div>
                        <hr>

                        <div class="row">
                            <div class="col-md-12">
                                <button type="submit" class="btn btn-lg red btn-block btn-outline"><i class="fa fa-bar-chart"></i> Generate Report</button>
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
            <div class="page-footer">
                <div class="page-footer-inner"> <%=et_index.getFooter()%></div>
                <div class="scroll-to-top">
                    <i class="icon-arrow-up"></i>
                </div>
            </div>
            <!-- END FOOTER -->
            <!-- BEGIN CORE PLUGINS -->

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
            <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>

            <!-- END THEME GLOBAL SCRIPTS -->
            <!-- BEGIN PAGE LEVEL SCRIPTS -->
            <script src="assets/pages/scripts/components-select2.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
            <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
            <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
            <script src="assets/soop/js/form-input-mask.min.js" type="text/javascript"></script>
            <!-- END PAGE LEVEL SCRIPTS -->
            <script src="assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>
            <!-- BEGIN THEME LAYOUT SCRIPTS -->
            <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
            <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>
            <script src="assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
            <!-- END CORE PLUGINS -->
            <!-- BEGIN PAGE LEVEL PLUGINS -->
            <!-- END PAGE LEVEL PLUGINS -->
            <!-- BEGIN THEME GLOBAL SCRIPTS -->

            <!-- END THEME LAYOUT SCRIPTS -->
            <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>

            <script src="assets/soop/js/jquery.cookie.js" type="text/javascript"></script>
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
