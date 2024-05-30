<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Mac2.0 - Report</title>
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
        <script src="assets/soop/js/moment.js" type="text/javascript"></script>


        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 
        <%            
            ArrayList<String[]> array_bank = Engine.list_only_bank_enabled_new();
            ArrayList<String[]> array_credit_card = Engine.list_bankAccountPOS();

            ArrayList<Branch> array_branch = Engine.list_branch_completeAFTER311217();
            ArrayList<NC_category> array_nc_cat = Engine.list_ALL_nc_category();
            ArrayList<Currency> arraycur = Engine.list_all_currency();

        %>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <script type="text/javascript">

            function changetype() {
                var ty = document.getElementById("typeop").value;
                $('#bankpos').empty().trigger('change');
                if (ty === 'BA') {
                    var o = $("<option/>", {value: "---", text: "All Banks"});
                    $('#bankpos').append(o);
            <%for (int j = 0; j < array_bank.size(); j++) {%>
                    var o = $("<option/>", {value: "<%=array_bank.get(j)[0]%>", text: "<%=array_bank.get(j)[0]%> - <%=array_bank.get(j)[1]%>"});
                                $('#bankpos').append(o);
            <%}%>
                            } else if (ty === 'PO') {
                                var o = $("<option/>", {value: "---", text: "All POS / Bank Account"});
                                $('#bankpos').append(o);
            <%for (int j = 0; j < array_credit_card.size(); j++) {%>
                                var o = $("<option/>", {value: "<%=array_credit_card.get(j)[0]%>", text: "<%=array_credit_card.get(j)[0]%> - <%=array_credit_card.get(j)[1]%>"});
                                            $('#bankpos').append(o);
            <%}%>
                                        }
                                        $('#bankpos').val($('#bankpos option:first-child').val()).trigger('change');
                                    }

                                    function getbranch(namev, d1, d2) {
                                        if ($('#branch').val().trim() === "") {
                                            var ermsg = "You must select at least one branch.";
                                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                            document.getElementById("errorlarge").style.display = "block";
                                            document.getElementById("errorlargetext").innerHTML = ermsg;
                                            return false;
                                        }

                                        if (d1 !== undefined && d2 !== undefined) {
                                            var d1b = document.getElementById(d1).value.trim();
                                            var d2b = document.getElementById(d2).value.trim();
                                            if (d1b === "" || d2b === "") {
                                                document.getElementById('saerchmodbtn').click();
                                                return false;
                                            }
                                        }
                                        document.getElementById(namev).value = $('#branch').val();
                                    }

                                    function checkresult() {

                                        var res_type = document.getElementById('res_type').checked;
                                        if (res_type) {
                                            document.getElementById('res_d3A').disabled = false;
                                            document.getElementById('res_d3B').disabled = true;
                                            document.getElementById('res_d4').disabled = true;
                                        } else {
                                            document.getElementById('res_d3A').disabled = true;
                                            document.getElementById('res_d3B').disabled = false;
                                            document.getElementById('res_d4').disabled = false;
                                        }
                                    }

                                    function loadpage() {
                                        online();
                                        changetype();
                                        checkresult();
                                        l1();
                                        inputvirgola();
                                        setDate();

                                    }

                                    function setDate() {
                                        var datenowsi = moment().format('DD/MM/YYYY');
                                        var datenow = moment().format('DD/MM/YYYY HH:mm');
                                        var secnow = moment().format('HH:mm:ss');
                                        var yeste = moment().subtract(1, 'day').format('DD/MM/YYYY');
                                        $(".datenowsi").val(datenowsi);
                                        $(".datenow").val(datenow);
                                        $(".secnow").val(secnow);
                                        $(".yeste").val(yeste);
                                    }

                                    function testfil(hr, link) {
                                        var branch = document.getElementById('branch').value;
                                        if (branch === "") {
                                            var ermsg = "You must select at least one branch.";
                                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                            document.getElementById("errorlarge").style.display = "block";
                                            document.getElementById("errorlargetext").innerHTML = ermsg;
                                            return false;
                                        }
                                        link.href = hr + "&filiale=" + branch;
                                    }

        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white" onload="return loadpage();">

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

        
        
        <div class="modal fade" id="saerchmod" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Search</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <p>
                                <b>Warning!</b> You must completed 'Date From' and 'Date To'.
                            </p>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <a class="btn btn-outline red" data-dismiss="modal" onclick="return dismiss('saerchmod');"><i class="fa fa-remove"></i> OK</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="saerchmod2" tabindex="-1" role="dialog" aria-hidden="true">
            <button type="button" id="saerchmodbtn" class="" data-toggle="modal" data-target="#saerchmod"></button>
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
            <%@ include file="menu/menu_re1.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);
                //String datenowsi = new DateTime().toString("dd/MM/yyyy");
                //String datenow = new DateTime().toString("dd/MM/yyyy HH:mm");
                //String secnow = new DateTime().toString("HH:mm:ss");
                //String yeste = new DateTime().minusDays(1).toString("dd/MM/yyyy");

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
                            <h3 class="page-title">Report <small><b>Panel (Branch)</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>



                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT BUY/SELL -->
                    <input type="hidden" name="search" value="ra1"/>
                    <input type="hidden" name="return" value="re_index.jsp"/>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box green">
                                <div class="portlet-title">
                                    <div class="caption" onclick="return setDate();">
                                        <i class="fa fa-search"></i> Select Report</div>
                                    <div class="tools">
                                        <a href="javascript:;" class="collapse"> </a>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <%                                        boolean iscentral = Engine.isCentral();
                                        if (iscentral) {
                                    %>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Branch</label>
                                                <select class="form-control select2-allow-clear" name="branch" id="branch" >
                                                    <option value="" selected="selected"></option>
                                                    <%for (int j = 0; j < array_branch.size(); j++) {%>
                                                    <option value="<%=array_branch.get(j).getCod()%>">
                                                        <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                    </option>
                                                    <%}%>

                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <input type="hidden" id="iscentral" name="iscentral" value="<%=iscentral%>" >
                                    <%} else {
                                        String[] val = Engine.getFil();
                                    %>
                                    <input type="hidden" id="branch" name="branch" value="<%=val[0]%>" />
                                    <%}%>
                                    <div class="row">
                                        <div class="col-md-2 col-sm-2 col-xs-2">
                                            <ul class="nav nav-tabs tabs-left" id="myTab">
                                                <li class="active">
                                                    <a href="#tab_1_1" data-toggle="tab"> Results </a>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> Stock Inquiry
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu" id="myTab">
                                                        <li>
                                                            <a href="#tab_1_2" tabindex="-1" data-toggle="tab"> Stock Inquiry</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_3" tabindex="-1" data-toggle="tab"> Branch Stock Inquiry </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_3A" tabindex="-1" data-toggle="tab"> Branch Stock Inquiry And Countervalue</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_4" tabindex="-1" data-toggle="tab"> Stock Price </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_4A" tabindex="-1" data-toggle="tab"> Office Stock Price </a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> Open Close
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu"id="myTab">
                                                        <li>
                                                            <a href="#tab_1_5" tabindex="-1" data-toggle="tab"> Synthetical </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_6" tabindex="-1" data-toggle="tab"> Analytical </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_7" tabindex="-1" data-toggle="tab"> Errors </a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> Register
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu"id="myTab">
                                                        <li>
                                                            <a href="#tab_1_8" tabindex="-1" data-toggle="tab"> Buy </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_9" tabindex="-1" data-toggle="tab"> Sell </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_10" tabindex="-1" data-toggle="tab"> Full </a>
                                                        </li>
                                                        <%if (Constant.is_CZ) {%>
                                                        <li>
                                                            <a href="#tab_c_44" tabindex="-1" data-toggle="tab"> <img src="assets/global/img/flags/cz.png" alt=""> Quarterly CZK </a>
                                                        </li>
                                                        <%}%>
                                                    </ul>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> Transaction List
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu"id="myTab">
                                                        <li>
                                                            <a href="#tab_1_11" tabindex="-1" data-toggle="tab"> Till </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_12" tabindex="-1" data-toggle="tab"> Till - Group by Currency </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_13" tabindex="-1" data-toggle="tab"> Heavy </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_14" tabindex="-1" data-toggle="tab"> Deleted </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_15" tabindex="-1" data-toggle="tab"> BuyBack </a>
                                                        </li>
                                                        <%if (!Constant.is_IT) {%>
                                                        <li>
                                                            <a href="#tab_1_15A" tabindex="-1" data-toggle="tab"> Sanction Attempts </a>
                                                        </li>
                                                        <%}%>
                                                        <%if (Constant.is_CZ) {%>
                                                        <li>
                                                            <a href="#tab_1_15B" tabindex="-1" data-toggle="tab"> <img src="assets/global/img/flags/cz.png" alt=""> Cash Advance </a>
                                                        </li>
                                                        <%}%>



                                                    </ul>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> External Transfer
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu"id="myTab">
                                                        <li>
                                                            <a href="#tab_1_16" tabindex="-1" data-toggle="tab"> Banking Sheet </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_17" tabindex="-1" data-toggle="tab"> Branching Sheet </a>
                                                        </li>

                                                    </ul>
                                                </li>
                                                <li>
                                                    <a href="#tab_1_18" data-toggle="tab"> Internal Transfer </a>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> No Change
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu"id="myTab">
                                                        <li>
                                                            <a href="#tab_1_19" tabindex="-1" data-toggle="tab"> Transaction List </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_20" tabindex="-1" data-toggle="tab"> Category Transaction List </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_21" tabindex="-1" data-toggle="tab"> Stock Report </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_21A" tabindex="-1" data-toggle="tab"> Total Stock Report </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_22" tabindex="-1" data-toggle="tab"> Western Union Service </a>
                                                        </li>
                                                        <!--<li>
                                                            <a href="#tab_1_23" tabindex="-1" data-toggle="tab"> VAT Refund Report </a>
                                                        </li>-->
                                                        <li>
                                                            <a href="#tab_1_24" tabindex="-1" data-toggle="tab"> Insurance Transaction List </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_25" tabindex="-1" data-toggle="tab"> Transaction List User </a>
                                                        </li>
                                                        <!--<li>
                                                            <a href="#tab_1_26" tabindex="-1" data-toggle="tab"> Open Close Stock Errors </a>
                                                        </li>-->
                                                        <li>
                                                            <a href="#tab_1_27" tabindex="-1" data-toggle="tab"> Summary VAT Refund </a>
                                                        </li>

                                                        <!--<li>
                                                            <a href="#tab_1_29" tabindex="-1" data-toggle="tab"> Ticket Transaction List </a>
                                                        </li>-->
                                                    </ul>
                                                </li>
                                                <li>
                                                    <a href="#tab_1_30" data-toggle="tab"> Currency - Change List </a>
                                                </li>
                                                <li>
                                                    <a href="#tab_1_50" data-toggle="tab"> Size & Quantity </a>
                                                </li>


                                            </ul>
                                        </div>
                                        <div class="col-md-10 col-sm-10 col-xs-10">
                                            <div class="tab-content">

                                                <div class="tab-pane fade" id="tab_1_15B">
                                                    <form action="ReportCentrale?type=CACZ" method="post" target="_blank" 
                                                          onsubmit="return getbranch('CACZ');">
                                                        <input type="hidden" id="CACZ" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Cash Advance </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" name="d4" id="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_c_44">
                                                    <form action="ReportCentrale?type=QuarterlyCZK" method="post" target="_blank" 
                                                          onsubmit="return getbranch('QuarterlyCZK');">
                                                        <input type="hidden" id="QuarterlyCZK" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Quarterly CZK </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" name="d4" id="d4"/>
                                                                    </div>
                                                                </div>                                                                      
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>


                                                <div class="tab-pane active" id="tab_1_1">

                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <h4 class="font-blue">Result</h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <form action="Report?type=Dailynow" method="post" target="_blank" onsubmit="return getbranch('Dailynow');">
                                                                <input type="hidden" id="Dailynow" name="branch" value=""/>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label><b>Actual</b></label><p class="ab"></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-sharp" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                            <div class="clearfix"></div>
                                                            <hr>

                                                            <form action="Report?type=Daily" method="post" target="_blank" onsubmit="return getbranch('Daily');">
                                                                <input type="hidden" id="Daily" name="branch" value=""/>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label><b>Reprint</b></label>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">

                                                                        <input type="checkbox" class="make-switch" id="res_type" name="res_type" checked
                                                                               data-size="normal" data-on-text="Daily" data-off-text="Monthly"
                                                                               data-on-color="info" data-off-color="default" onchange="return checkresult();" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date</label>
                                                                        <input type="text" class="form-control form_datetime datenow" id="res_d3A" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <hr>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="res_d3B" name="d3"/>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="res_d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>


                                                </div>

                                                <div class="tab-pane fade" id="tab_1_2">
                                                    <form action="Report?type=StockInquiry" method="post" target="_blank" onsubmit="return getbranch('StockInquiry');">
                                                        <input type="hidden" id="StockInquiry" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Stock Inquiry - Search by date</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date</label>
                                                                        <input type="text"class="form-control date-picker datenowsi" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Time</label>
                                                                        <input type="text"class="form-control timepicker timepicker-24 secnow" name="d4" id="d4"/>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_3">
                                                    <form action="Report?type=BranchStockInquiry" method="post" target="_blank"onsubmit="return getbranch('BranchStockInquiry');">
                                                        <input type="hidden" id="BranchStockInquiry" name="branch" value="" >
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Branch Stock Inquiry - Search by date</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date</label>
                                                                        <input type="text"class="form-control date-picker datenowsi" name="d3" id="d3">
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Time</label>
                                                                        <input type="text"class="form-control timepicker timepicker-24 secnow" name="d4" id="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_3A">
                                                    <form action="Report?type=BranchStockInquiryA" method="post" target="_blank"
                                                          onsubmit="return getbranch('BranchStockInquiryA');">
                                                        <input type="hidden" id="BranchStockInquiryA" name="branch" value="" >
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Branch Stock Inquiry And Countervalue - Search by date</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date</label>
                                                                        <input type="text"class="form-control date-picker datenowsi" name="d3" id="d3">
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Time</label>
                                                                        <input type="text"class="form-control timepicker timepicker-24 secnow" name="d4" id="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_4">
                                                    <form action="Report?type=StockPrice" method="post" target="_blank" onsubmit="return getbranch('StockPrice');">
                                                        <input type="hidden" id="StockPrice" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Stock Price</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_4A">

                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <h4 class="font-blue">Office Stock Price</h4>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-12">
                                                            <form action="Report?type=OfficeStockPrice_now" target="_blank" method="post" 
                                                                  onsubmit="return getbranch('OfficeStockPrice');">
                                                                <input type="hidden" id="OfficeStockPrice" name="branch" value=""/>
                                                                <input type="hidden" name="search" value="ra1"/>
                                                                <input type="hidden" name="rep" value="Office Stock Price"/>
                                                                <input type="hidden" name="return" value="re_index.jsp"/>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Actual</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-sharp" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                            <div class="clearfix"></div>
                                                            <hr>
                                                            <div class="clearfix"></div>
                                                            <form action="re_index_result.jsp" method="post" onsubmit="return getbranch('Office Stock Price');">
                                                                <input type="hidden" id="Office Stock Price" name="branch" value=""/>
                                                                <input type="hidden" name="search" value="ra1"/>
                                                                <input type="hidden" name="rep" value="Office Stock Price"/>
                                                                <input type="hidden" name="return" value="re_index.jsp"/>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Reprint - Date</label>
                                                                        <input type="text"class="form-control date-picker datenowsi" name="d3" id="d3">
                                                                    </div>
                                                                </div><div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="tab-pane fade" id="tab_1_5">
                                                    <form action="Report?type=Openclose_Synt" method="post" target="_blank" onsubmit="return getbranch('Openclose_Synt');">
                                                        <input type="hidden" id="Openclose_Synt" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Open Close - Synthetical</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3"/>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_6" >
                                                    <form action="re_index_result.jsp" method="post" onsubmit="return getbranch('OpenCloseAnalytical');">
                                                        <input type="hidden" id="OpenCloseAnalytical" name="branch" value=""/>
                                                        <input type="hidden" name="search" value="ra1"/>
                                                        <input type="hidden" name="rep" value="Open Close - Analytical"/>
                                                        <input type="hidden" name="return" value="re_index.jsp"/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Open Close - Analytical</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d5" name="d5" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <!--<div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>-->
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>

                                                <div class="tab-pane fade" id="tab_1_7">
                                                    <form action="Report?type=Openclose_Error" method="post" target="_blank" onsubmit="return getbranch('Openclose_Error');">
                                                        <input type="hidden" id="Openclose_Error" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Open Close - Errors</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d5" name="d5" v/>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_8">
                                                    <form action="Report?type=RegisterBuyMonthly" method="post" target="_blank" onsubmit="return getbranch('RegisterBuyMonthly');">
                                                        <input type="hidden" id="RegisterBuyMonthly" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Register - Buy</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div></form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_9">
                                                    <form action="Report?type=RegisterSellMonthly" method="post" target="_blank" onsubmit="return getbranch('RegisterSellMonthly');">
                                                        <input type="hidden" id="RegisterSellMonthly" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Register - Sell</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_10">
                                                    <form action="Report?type=RegisterMonthly" method="post" target="_blank" onsubmit="return getbranch('RegisterMonthly');">
                                                        <input type="hidden" id="RegisterMonthly" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Register - Full</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4"/>
                                                                    </div>
                                                                </div><div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div>                                                              
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_11">
                                                    <form action="Report?type=TillTransactionList" method="post" target="_blank" 
                                                          onsubmit="return getbranch('TillTransactionList', 'd3raf1', 'd4raf1');">
                                                        <input type="hidden" id="TillTransactionList" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Transaction List - Till</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3raf1" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4raf1" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div>   
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_12">
                                                    <form action="Report?type=TillTransactionListCurrency" target="_blank" method="post"
                                                          onsubmit="return getbranch('TillTransactionListCurrency', 'd3raf2', 'd4raf2');">
                                                        <input type="hidden" id="TillTransactionListCurrency" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Transaction List - Till - Group by Currency</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3raf2" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4raf2" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div>   
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_13">
                                                    <form action="Report?type=HeavyTransactionList" method="post" target="_blank" 
                                                          onsubmit="return getbranch('HeavyTransactionList', 'd3raf3', 'd4raf3');">
                                                        <input type="hidden" id="HeavyTransactionList" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Transaction List - Heavy</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3raf3" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4raf3" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div>   
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_14">
                                                    <form action="Report?type=DeleteTransactionList" method="post" target="_blank" 
                                                          onsubmit="return getbranch('DeleteTransactionList', 'd3raf4', 'd4raf4');">
                                                        <input type="hidden" id="DeleteTransactionList" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Transaction List - Deleted</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3raf4" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4raf4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div>   
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_15">
                                                    <form action="Report?type=BBTransactionList" method="post" target="_blank" 
                                                          onsubmit="return getbranch('BBTransactionList', 'd3raf5', 'd4raf5');">
                                                        <input type="hidden" id="BBTransactionList" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Transaction List - BuyBack</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3raf5" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4raf5" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div> 
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>

                                                <div class="tab-pane fade" id="tab_1_15A">
                                                    <form action="Report?type=SanctionAttempts" method="post" target="_blank" 
                                                          onsubmit="return getbranch('SanctionAttempts', 'd3araf5a', 'd4araf5a');">
                                                        <input type="hidden" id="SanctionAttempts" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Transaction List - Sanction Attempts</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3araf5a" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4araf5a" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div> 
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>

                                                <div class="tab-pane fade" id="tab_1_16">
                                                    <form action="re_index_result.jsp" method="post" onsubmit="return getbranch('BBBANKINGSHEET1');" >
                                                        <input type="hidden" name="search" value="ra1"/>
                                                        <input type="hidden" name="rep" value="External Transfer - Banking Sheet"/>
                                                        <input type="hidden" name="return" value="re_index.jsp"/>
                                                        <input type="hidden" id="BBBANKINGSHEET1" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">External Transfer - Banking Sheet</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="clearfix"></div>   
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Type</label>
                                                                        <select class="form-control select2"  name="typeop" id="typeop" onchange="return changetype();">
                                                                            <option value="BA">Bank</option>
                                                                            <option value="PO">POS / Bank Account</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Bank/Pos</label>
                                                                        <select class="form-control select2" id="bankpos" name="bankpos" placeholder="...">

                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>   
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Type Operation</label>
                                                                        <select class="form-control select2" id="chnc" name="chnc" placeholder="...">
                                                                            <option value="CH">Change</option>
                                                                            <option value="NC">No Change</option>
                                                                        </select>
                                                                    </div>
                                                                </div>

                                                                <!--
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>-->
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_17">
                                                    <form action="re_index_result.jsp" method="post" onsubmit="return getbranch('BBBRANCHINGSHEET1');" >                                               
                                                        <input type="hidden" name="search" value="ra1"/>
                                                        <input type="hidden" name="rep" value="External Transfer - Branching Sheet"/>
                                                        <input type="hidden" name="return" value="re_index.jsp"/>
                                                        <input type="hidden" id="BBBRANCHINGSHEET1" name="branch" value=""/>
                                                        <!---->



                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">External Transfer - Branching Sheet</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Branch</label>
                                                                        <select class="form-control select2" name="br1" id="br1">
                                                                            <option value="---">All Branches</option>
                                                                            <%for (int i = 0; i < array_branch.size(); i++) {%>
                                                                            <option value="<%=array_branch.get(i).getCod()%>"><%=array_branch.get(i).getCod()%> - <%=array_branch.get(i).getDe_branch()%></option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Type Operation</label>
                                                                        <select class="form-control select2" id="chnc" name="chnc" placeholder="...">
                                                                            <option value="CH">Change</option>
                                                                            <option value="NC">No Change</option>
                                                                        </select>
                                                                    </div>
                                                                </div>

                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_18">
                                                    <form action="Report?type=InternalTransferList" method="post" target="_blank" onsubmit="return getbranch('InternalTransferList');">
                                                        <input type="hidden" id="InternalTransferList" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Internal Transfer</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div> 

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_19">
                                                    <form action="Report?type=NoChangeTransactionList" method="post" target="_blank" 
                                                          onsubmit="return getbranch('NoChangeTransactionList');">
                                                        <input type="hidden" id="NoChangeTransactionList" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Transaction List</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>


                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {%>
                                                                            <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div><div class="clearfix"></div> 

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_20">
                                                    <form action="Report?type=NoChangeCategoryTransactionList" method="post" target="_blank" onsubmit="return getbranch('NoChangeCategoryTransactionList');">
                                                        <input type="hidden" id="NoChangeCategoryTransactionList" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Category Transaction List</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>


                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {%>
                                                                            <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>

                                                                </div> 
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_21A">
                                                    <form action="Report?type=TotalStockReport" method="post" target="_blank" onsubmit="return getbranch('TotalStockReport');">
                                                        <input type="hidden" id="TotalStockReport" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Total Stock Report</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date</label>
                                                                        <input type="text" class="form-control form_datetime datenow" name="d3" id="n3">
                                                                    </div>
                                                                </div>

                                                                <div class="clearfix"></div> 
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {

                                                                                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("2")) {


                                                                            %>
                                                                            <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                            <%}
                                                                                }%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_21">
                                                    <form action="Report?type=StockReport" method="post" target="_blank" onsubmit="return getbranch('StockReport');">
                                                        <input type="hidden" id="StockReport" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Stock Report</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="clearfix"></div> 
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {

                                                                                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("2")) {


                                                                            %>
                                                                            <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                            <%}
                                                                                }%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_22">
                                                    <form action="Report?type=WesternUnionService" method="post" target="_blank" onsubmit="return getbranch('WesternUnionService');">
                                                        <input type="hidden" id="WesternUnionService" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Western Union Service</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div> 
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {
                                                                                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("1")) {
                                                                            %>
                                                                            <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                            <%}
                                                                                }%>
                                                                        </select>
                                                                    </div></div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>

                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_23">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <h4 class="font-blue">No Change - VAT Refund Report</h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>Date From</label>
                                                                    <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>Date To</label>
                                                                    <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                </div>
                                                            </div>

                                                            <div class="clearfix"></div>
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>&nbsp;</label><p class='ab'></p>
                                                                    <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_24">
                                                    <form action="Report?type=InsuranceTransactionList" method="post" target="_blank" onsubmit="return getbranch('InsuranceTransactionList');">
                                                        <input type="hidden" id="InsuranceTransactionList" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Insurance Transaction List</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div> 
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {
                                                                                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("5")) {
                                                                            %>
                                                                            <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                            <%}
                                                                                }%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_25">
                                                    <form action="Report?type=NoChangeTransactionListForUser" method="post" target="_blank" onsubmit="return getbranch('NoChangeTransactionListForUser');">
                                                        <input type="hidden" id="NoChangeTransactionListForUser" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Transaction List User</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="clearfix"></div> 
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {%>
                                                                            <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_26">

                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <h4 class="font-blue">No Change - Open Close Stock Errors</h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>Date From</label>
                                                                    <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>Date To</label>
                                                                    <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="form-group">
                                                                    <label>Category</label>
                                                                    <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                        <option value="...">All</option>
                                                                        <%for (int i = 0; i < array_nc_cat.size(); i++) {

                                                                                if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("2")) {


                                                                        %>
                                                                        <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                        <%}
                                                                            }%>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="clearfix"></div>
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>&nbsp;</label><p class='ab'></p>
                                                                    <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="tab-pane fade" id="tab_1_27">
                                                    <form action="Report?type=TaxFree" method="post" target="_blank" onsubmit="return getbranch('TaxFree');">
                                                        <input type="hidden" id="TaxFree" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Summary VAT Refund</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="clearfix"></div> 
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {

                                                                                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("3")) {


                                                                            %>
                                                                            <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                            <%}
                                                                                }%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>

                                                <div class="tab-pane fade" id="tab_1_29">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <h4 class="font-blue">No Change - Ticket Transaction List</h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>Date From</label>
                                                                    <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>Date To</label>
                                                                    <input type="text" class="form-control date-picker datenowsi" id="d4" name="d4" />
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="form-group">
                                                                    <label>Category</label>
                                                                    <select class="form-control select2" multiple name="nc_cat1" placeholder="...">
                                                                        <option value="...">All</option>
                                                                        <%for (int i = 0; i < array_nc_cat.size(); i++) {

                                                                                if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("21")) {


                                                                        %>
                                                                        <option value="<%=array_nc_cat.get(i).getGruppo_nc()%>"><%=array_nc_cat.get(i).getGruppo_nc()%> - <%=array_nc_cat.get(i).getDe_gruppo_nc()%></option>
                                                                        <%}
                                                                            }%>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                            <div class="clearfix"></div>
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>&nbsp;</label><p class='ab'></p>
                                                                    <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_30">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <h4 class="font-blue">Currency - Change List</h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <label>&nbsp;</label><p class='ab'></p>
                                                                    <%if (Constant.is_IT) {%>
                                                                    <a href="Fileview?type=cart_change&naz=ITA" target="_blank" 
                                                                       class="btn btn-outline dark" onclick="return testfil('Fileview?type=cart_change&naz=ITA', this);">
                                                                        <img src="assets/global/img/flags/it.png" alt=""> ITA</a>
                                                                        <%}%>
                                                                        <%if (Constant.is_CZ) {%>
                                                                    <a href="Fileview?type=cart_change&naz=CZ" target="_blank" 
                                                                       class="btn btn-outline dark" onclick="return testfil('Fileview?type=cart_change&naz=CZ', this);">
                                                                        <img src="assets/global/img/flags/cz.png" alt=""> CZ</a>
                                                                        <%}%>
                                                                        <%if (Constant.is_UK) {%>
                                                                    <a href="Fileview?type=cart_change&naz=UK" target="_blank" 
                                                                       class="btn btn-outline dark" onclick="return testfil('Fileview?type=cart_change&naz=UK', this);">
                                                                        <img src="assets/global/img/flags/gb.png" alt=""> UK</a>
                                                                        <%}%>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="tab-pane fade" id="tab_1_50">
                                                    <form action="Report?type=SizeQuantity" method="post" target="_blank" onsubmit="return getbranch('SizeQuantity');">
                                                        <input type="hidden" id="SizeQuantity" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Sizes And Quantities</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker yeste" name="d3" id="n3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Currency</label>
                                                                        <select class="form-control select2" id="curr" name="curr" placeholder="...">
                                                                            <%for (int i = 0; i < arraycur.size(); i++) {%>
                                                                            <option value="<%=arraycur.get(i).getCode()%>">
                                                                                <%=arraycur.get(i).getCode()%> - <%=arraycur.get(i).getDescrizione()%>
                                                                            </option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>&nbsp;</label><p class='ab'></p>
                                                                        <button type="submit" class="btn btn-outline green-jungle" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                </div>
                            </div>
                        </div>
                    </div>

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
            <script>

                                                        function l1() {
                                                            // store the currently selected tab in the hash value
                                                            $("ul.nav-tabs > li > a").on("shown.bs.tab", function (e) {
                                                                var id = $(e.target).attr("href").substr(1);
                                                                $.cookie('activeTab', id);
                                                                setDate();
                                                            });
                                                            $("ul.dropdown-menu > li > a").on("shown.bs.tab", function (e) {
                                                                var id = $(e.target).attr("href").substr(1);
                                                                $.cookie('activeTab', id);
                                                                setDate();
                                                            });
                                                            // on load of the page: switch to the currently selected tab
                                                            var hash = $.cookie('activeTab');
                                                            if (hash !== null) {
                                                                $('#myTab a[href="#' + hash + '"]').tab('show');
                                                                setDate();
                                                            }
                                                        }
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
