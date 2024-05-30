<%@page import="java.util.List"%>
<%@page import="rc.so.entity.Users"%>
<%@page import="rc.so.entity.Currency"%>
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
        <%
            String decimal = Constant.decimal;
            String thousand = Constant.thousand;
            ArrayList<Users> users = Engine.list_all_users_show();
            ArrayList<Branch> array_branch = Engine.list_branch_completeAFTER311217();
            ArrayList<NC_category> array_nc_cat = Engine.list_ALL_nc_category();
            ArrayList<Currency> arraycur = Engine.list_all_currency();

        %>

        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/moment.js" type="text/javascript"></script>
        <script type="text/javascript">

            function getbranch(namev) {
                document.getElementById(namev).value = $('#branch').val();
            }

            function checkbranch() {
                var branch = document.getElementById('branch').value;
                if (branch === "") {
                    var ermsg = "You must select at least one branch.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                return true;
            }

            function loadpage() {
                online();
                l1();
                inputvirgola();
                setDate();
            }

            function setDate() {
                var datenow = moment().format('DD/MM/YYYY HH:mm');
                var daten = moment().format('DD/MM/YYYY');
                var secnow = moment().format('HH:mm:ss');
                var yeste = moment().subtract(1, 'day').format('DD/MM/YYYY');
                $(".daten").val(daten);
                $(".secnow").val(secnow);
                $(".yeste").val(yeste);
                $(".datenowsi").val(daten);
                $(".secnow").val(secnow);
                $(".datenow").val(datenow);
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
                ArrayList<NC_category> array_nc_cat_bon = Engine.query_nc_category_bonus();
                ArrayList<String[]> array_credit_card = Engine.credit_card_enabled();
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
                            <h3 class="page-title">Report <small><b>Panel (Central)</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT BUY/SELL -->


                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-search"></i> Report Central</div>
                                    <div class="tools">
                                        <a href="javascript:;" class="collapse"> </a>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Branch</label>
                                                <select class="form-control select2" name="branch" id="branch" multiple>
                                                    <%for (int j = 0; j < array_branch.size(); j++) {%>
                                                    <option value="<%=array_branch.get(j).getCod()%>">
                                                        <%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch()%>
                                                    </option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <hr/>
                                    <div class="row">
                                        <div class="col-md-2 col-sm-2 col-xs-2">
                                            <ul class="nav nav-tabs tabs-left"id="myTab">
                                                <li class="dropdown active">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> Check (1/3)
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu"id="myTab">
                                                        <li class="active">
                                                            <a href="#tab_c_5" tabindex="-1" data-toggle="tab"> Analysis Currency </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_19" tabindex="-1" data-toggle="tab"> Analysis Details Transaction Certification Non Resident ITA </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_9" tabindex="-1" data-toggle="tab"> Analysis Interbranch </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_10" tabindex="-1" data-toggle="tab"> Analysis Interbranch Details </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_3" tabindex="-1" data-toggle="tab"> Analysis Reprint Receipt Change </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_4" tabindex="-1" data-toggle="tab"> Analysis Reprint Receipt No Change</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_13" tabindex="-1" data-toggle="tab"> Analysis Transaction Change for Agency </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_11" tabindex="-1" data-toggle="tab"> Analysis Transaction Change for Branch </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_12" tabindex="-1" data-toggle="tab"> Analysis Transaction Change for Branch Details </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_6" tabindex="-1" data-toggle="tab"> Analysis Transaction No Change for Branch </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_15_bis" tabindex="-1" data-toggle="tab"> BB Transaction List Group by Buy-Sell</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_15_tris" tabindex="-1" data-toggle="tab"> SB Transaction List Group by Sell-Buy</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_RRBS" tabindex="-1" data-toggle="tab"> Branch Stock Inquiry And Countervalue</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_14" tabindex="-1" data-toggle="tab"> Branches Closures</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_20" tabindex="-1" data-toggle="tab"> Cashier Open/Close Errors </a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> Check (2/3)
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu"id="myTab">
                                                        <li>
                                                            <a href="#tab_c_15" tabindex="-1" data-toggle="tab"> Cashier Performance</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_16" tabindex="-1" data-toggle="tab"> Cashier Performance Excel</a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_1" tabindex="-1" data-toggle="tab"> Change Turnover </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_2" tabindex="-1" data-toggle="tab"> Change Turnover Cash Advance </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_45b" tabindex="-1" data-toggle="tab"> Customer Care Refund </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_90" tabindex="-1" data-toggle="tab"> Daily Error </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_30" tabindex="-1" data-toggle="tab"> External Transfer Banking/Branching Sheet </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_SP1" tabindex="-1" data-toggle="tab"> Historical Stock Price </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_8" tabindex="-1" data-toggle="tab"> Internet Booking for Branch </a>
                                                        </li>
                                                        
                                                        <li>
                                                            <a href="#tab_1_28MA" tabindex="-1" data-toggle="tab"> Marketing Consent </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_28" tabindex="-1" data-toggle="tab"> No Change - Bonus </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_22A" tabindex="-1" data-toggle="tab">  No Change - Western Union </a>
                                                        </li> 
                                                        <li>
                                                            <a href="#tab_1_41" tabindex="-1" data-toggle="tab">  Office Stock Price Branch </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_42" tabindex="-1" data-toggle="tab">  Office Stock Price Branch Currency </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_43" tabindex="-1" data-toggle="tab">  Open Close Error </a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> Check (3/3)
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu"id="myTab">
                                                        <li>
                                                            <a href="#tab_cc_da" tabindex="-1" data-toggle="tab">  Reprint Daily Branch </a>
                                                        </li>

                                                        <li>
                                                            <a href="#tab_c_7" tabindex="-1" data-toggle="tab"> Sizes And Quantities </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_1_40" tabindex="-1" data-toggle="tab">  Stock Inquiry </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_UN" tabindex="-1" data-toggle="tab"> Unlock Pending Operation </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_21" tabindex="-1" data-toggle="tab"> VAT Refund Analysis </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_22" tabindex="-1" data-toggle="tab"> VAT Refund Branch </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_23" tabindex="-1" data-toggle="tab"> VAT Refund Branch&Causal </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_25" tabindex="-1" data-toggle="tab"> VAT Refund Branch&Currency </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_24" tabindex="-1" data-toggle="tab"> VAT Refund Causal&Branch </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_26" tabindex="-1" data-toggle="tab"> VAT Refund Currency&Branch </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_27" tabindex="-1" data-toggle="tab"> VAT Refund Currency&Nation </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_28" tabindex="-1" data-toggle="tab"> VAT Refund Nation&Currency </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_29" tabindex="-1" data-toggle="tab"> VAT Refund Pivot </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_17" tabindex="-1" data-toggle="tab"> Transaction Register Summary </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_18" tabindex="-1" data-toggle="tab"> Transaction Register Details </a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> Risk Assessment Index
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu" id="myTab">
                                                        <li>
                                                            <a href="#tab_c_43A" tabindex="-1" data-toggle="tab"> K.Y.C. Threshold </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_44A" tabindex="-1" data-toggle="tab"> K.Y.C. Abnormal Frequency </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_45A" tabindex="-1" data-toggle="tab"> K.Y.C. Abnormal Volume </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_46A" tabindex="-1" data-toggle="tab"> K.Y.C. Volume 'Rogue Country' </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_47A" tabindex="-1" data-toggle="tab"> K.Y.C. Large Denomination </a>
                                                        </li>
                                                    </ul>
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
                                                <li class="dropdown">
                                                    <a href="re_management.jsp" tabindex="-1" target="_blank"> Management Control <i class="fa fa-angle-right"></i></a>
                                                </li>
                                                <li class="dropdown">
                                                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"> List Transaction
                                                        <i class="fa fa-angle-down"></i>
                                                    </a>
                                                    <ul class="dropdown-menu" role="menu" id="myTab">
                                                        <li>
                                                            <a href="#tab_c_lcha" tabindex="-1" data-toggle="tab"> Change </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_lnch" tabindex="-1" data-toggle="tab"> No Change </a>
                                                        </li>
                                                        <li>
                                                            <a href="#tab_c_pos" tabindex="-1" data-toggle="tab"> POS / Bank Account </a>
                                                        </li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="col-md-10 col-sm-10 col-xs-10">
                                            <div class="tab-content">
                                                <div class="tab-pane fade" id="tab_1_RRBS">
                                                    <form action="ReportCentrale?type=BranchStockInquiryA" method="post" target="_blank"
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



                                                <div class="tab-pane" id="tab_c_SP1">
                                                    <form action="ReportCentrale?type=HistoricalStockPrice" method="post" target="_blank" 
                                                          onsubmit="return getbranch('HistoricalStockPrice');">
                                                        <input type="hidden" id="SP1" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Historical Stock Price</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <%

                                                                List<String> elencoanni = Engine.lista_anni_SP();
                                                            %>
                                                            <div class="col-md-12">
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <b>N.B. This report will be generated for all branches active on the chosen date, regardless of those selected in the filter.</b>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Year</label>
                                                                        <select class="form-control select2" name="year" id="year">
                                                                            <%for (int x = 0; x < elencoanni.size(); x++) {%>
                                                                            <option value="<%=elencoanni.get(x)%>"><%=elencoanni.get(x)%></option>  
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



                                                <div class="tab-pane" id="tab_c_pos">
                                                    <form action="ReportCentrale?type=lpos" method="post" target="_blank" onsubmit="return getbranch('lpos');">
                                                        <input type="hidden" id="lpos" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">List Transaction - POS / Bank Account</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Credit Card</label>
                                                                        <select class="form-control select2" name="cred" id="cred">
                                                                            <option value="---">All</option>
                                                                            <%for (int j = 0; j < array_credit_card.size(); j++) {%>
                                                                            <option value="<%=array_credit_card.get(j)[0]%>">
                                                                                <%=array_credit_card.get(j)[0]%> - <%=array_credit_card.get(j)[1]%>
                                                                            </option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>
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
                                                <div class="tab-pane" id="tab_c_lcha">
                                                    <form action="ReportCentrale?type=lcha" method="post" target="_blank" onsubmit="return getbranch('lcha');">
                                                        <input type="hidden" id="lcha" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">List Transaction - Change</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane" id="tab_c_UN">
                                                    <form action="ReportCentrale?type=pending" method="post" target="_blank" onsubmit="return getbranch('pending');">
                                                        <input type="hidden" id="pending" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Unlock Pending Operation</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Operator</label>
                                                                        <select class="form-control select2" name="operator" id="operator" multiple>
                                                                            <%for (int j = 0; j < users.size(); j++) {%>
                                                                            <option value="<%=users.get(j).getCod()%>">
                                                                                <%=users.get(j).getCod()%> - <%=users.get(j).getDe_cognome()%> <%=users.get(j).getDe_nome()%> 
                                                                            </option>
                                                                            <%}%>
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
                                                <div class="tab-pane" id="tab_c_lnch">
                                                    <form action="ReportCentrale?type=lnch" method="post" target="_blank" onsubmit="return getbranch('lnch');">
                                                        <input type="hidden" id="lnch" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">List Transaction - No Change</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <div class="md-checkbox">
                                                                            <input type="checkbox" id="vatref" name="vatref"
                                                                                   class="md-checkbox"> 
                                                                            <label for="vatref">
                                                                                <span></span>
                                                                                <span class="check"></span>
                                                                                <span class="box"></span> Only VAT REFUND
                                                                            </label>
                                                                        </div>
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
                                                <div class="tab-pane" id="tab_c_dr">
                                                    <form action="ReportCentrale?type=DailyCG" method="post" target="_blank">
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Daily Report</h4>
                                                                        <span class="help-block">Filter "Branch" on top are invalid for this report.</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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


                                                <div class="tab-pane" id="tab_cc_da">
                                                    <form action="ReportCentrale?type=DailyCentral" method="post" target="_blank" 
                                                          onsubmit="return getbranch('DailyCentral');">
                                                        <input type="hidden" id="DailyCentral" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Reprint Daily Branch</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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

                                                <div class="tab-pane fade" id="tab_1_28">
                                                    <form action="Report?type=NoChangeBonus" method="post" target="_blank" onsubmit="return getbranch('NoChangeBonus');">
                                                        <input type="hidden" id="NoChangeBonus" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Bonus</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>
                                                                <div class="clearfix"></div> 
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat_bon.size(); i++) {

                                                                            %>
                                                                            <option value="<%=array_nc_cat_bon.get(i).getGruppo_nc()%>">
                                                                                <%=array_nc_cat_bon.get(i).getGruppo_nc()%> - <%=array_nc_cat_bon.get(i).getDe_gruppo_nc()%>
                                                                            </option>
                                                                            <%
                                                                                }%>
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
                                                <div class="tab-pane " id="tab_c_43A">
                                                    <form action="ReportCentrale?type=CustomerTransactionList_AV" method="post" target="_blank" 
                                                          onsubmit="return getbranch('CustomerTransactionList_AV');">
                                                        <input type="hidden" id="CustomerTransactionList_AF" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Risk Assessment Index - Threshold</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <input type="hidden" name="frequency" value="TH" />
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
                                                <div class="tab-pane " id="tab_c_44A">
                                                    <form action="ReportCentrale?type=CustomerTransactionList_AV" method="post" target="_blank" 
                                                          onsubmit="return getbranch('CustomerTransactionList_AV');">
                                                        <input type="hidden" id="CustomerTransactionList_AF" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Risk Assessment Index - Abnormal Frequency</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Frequency</label>
                                                                        <select class="form-control select2" id="frequency" name="frequency" placeholder="...">
                                                                            <option value="D">Daily</option>
                                                                            <option value="W">Weekly</option>
                                                                            <option value="M">Monthly</option>
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

                                                <div class="tab-pane " id="tab_c_45A">
                                                    <form action="ReportCentrale?type=CustomerTransactionList_AV" method="post" target="_blank" 
                                                          onsubmit="return getbranch('CustomerTransactionList_AV');">
                                                        <input type="hidden" id="CustomerTransactionList_AV" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Risk Assessment Index - Abnormal Volume</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Frequency</label>
                                                                        <select class="form-control select2" id="frequency" name="frequency" placeholder="...">
                                                                            <option value="M1">Monthly</option>
                                                                            <option value="Q">Quarterly</option>
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

                                                <div class="tab-pane " id="tab_c_46A">
                                                    <form action="ReportCentrale?type=CustomerTransactionList_AV" method="post" target="_blank" 
                                                          onsubmit="return getbranch('CustomerTransactionList_AV');">
                                                        <input type="hidden" id="CustomerTransactionList_AV" name="branch" value=""/>
                                                        <input type="hidden" id="frequency" name="frequency" value="RC"/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Risk Assessment Index - Threshold Volume 'Rogue Country'</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4"/>
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

                                                <div class="tab-pane " id="tab_c_47A">
                                                    <form action="ReportCentrale?type=CustomerTransactionList_AV" method="post" target="_blank" 
                                                          onsubmit="return getbranch('CustomerTransactionList_AV');">
                                                        <input type="hidden" id="CustomerTransactionList_AV" name="branch" value=""/>
                                                        <input type="hidden" id="frequency" name="frequency" value="B"/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Risk Assessment Index - Uses of large denomination banknotes</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4"/>
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







                                                <div class="tab-pane " id="tab_c_45">
                                                    <form action="ReportCentrale?type=CustomerTransactionList_s" method="post" target="_blank" 
                                                          onsubmit="return getbranch('CustomerTransactionList_s');">
                                                        <input type="hidden" id="CustomerTransactionList_s" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Risk Assessment Index (Threshold)</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Threshold</label>
                                                                        <input type="text" class="form-control" 
                                                                               onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                                               id="thres" name="thres" value="0<%=decimal%>01" />
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


                                                <div class="tab-pane fade" id="tab_1_19">
                                                    <form action="Report?type=NoChangeTransactionList" method="post" target="_blank" onsubmit="return getbranch('NoChangeTransactionList');">
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
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
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
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
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
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
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
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
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
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
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
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
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
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
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
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
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



                                                <div class="tab-pane " id="tab_1_22A">
                                                    <form action="ReportCentrale?type=C_WesternUnion" method="post" target="_blank" onsubmit="return getbranch('C_WesternUnion');">
                                                        <input type="hidden" id="C_WesternUnion" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">No Change - Western Union</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <label>Category</label>
                                                                        <select class="form-control select2" multiple id="nc_cat1" name="nc_cat1" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <%for (int i = 0; i < array_nc_cat.size(); i++) {
                                                                                    if (array_nc_cat.get(i).getFg_tipo_transazione_nc().equals("1")) {
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
                                                <div class="tab-pane " id="tab_1_40">
                                                    <form action="ReportCentrale?type=C_StockInquiry" method="post" target="_blank" onsubmit="return getbranch('C_StockInquiry');">
                                                        <input type="hidden" id="C_StockInquiry" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Stock Inquiry</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Date</label>
                                                                        <input type="text"class="form-control date-picker daten" name="d3" id="d3" />
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
                                                <div class="tab-pane " id="tab_1_41">

                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <h4 class="font-blue">Office Stock Price Branch</h4>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-12">


                                                            <form action="ReportCentrale?type=C_OfficeStockPrice_now" method="post" target="_blank" onsubmit="return getbranch('C_OfficeStockPrice');">
                                                                <input type="hidden" id="C_OfficeStockPrice" name="branch" value=""/>
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

                                                            <form action="ReportCentrale?type=C_OfficeStockPrice" method="post" target="_blank" onsubmit="return getbranch('C_OfficeStockPrice2');">
                                                                <input type="hidden" id="C_OfficeStockPrice2" name="branch" value=""/>
                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Reprint - Date</label>
                                                                        <input type="text"class="form-control date-picker daten" name="d3" id="d3" >
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-4">
                                                                    <div class="form-group">
                                                                        <label>Reprint - Time</label>
                                                                        <input type="text"class="form-control timepicker timepicker-24 secnow" name="d4" id="d4" />
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
                                                            </form>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="tab-pane " id="tab_1_42">
                                                    <form action="ReportCentrale?type=C_OfficeStockPriceCurrency" method="post" 
                                                          target="_blank" onsubmit="return getbranch('C_OfficeStockPriceCurrency');">
                                                        <input type="hidden" id="C_OfficeStockPriceCurrency" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Office Stock Price Branch Currency</h4>
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
                                                <div class="tab-pane " id="tab_1_43">
                                                    <form action="ReportCentrale?type=C_OpenCloseError" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_OpenCloseError');">
                                                        <input type="hidden" id="C_OpenCloseError" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Open Close Error</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d3" name="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" id="d4" name="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Show</label>
                                                                        <select class="form-control select2" name="Show">
                                                                            <option value="---">All</option>
                                                                            <option value="01">Only Change</option>
                                                                            <option value="02">Only No Change</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Threshold</label>
                                                                        <input type="text" class="form-control" 
                                                                               onchange="return formatValueDecimal_1(this, '<%=thousand%>', '<%=decimal%>');"
                                                                               id="thres" name="thres" value="0<%=decimal%>00"
                                                                               />
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

                                                <!--CENTRALE-->          

                                                <div class="tab-pane fade" id="tab_c_28">
                                                    <form action="ReportCentrale?type=C_FreeTax_NationAmount" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_FreeTax_NationAmount');">
                                                        <input type="hidden" id="C_FreeTax_NationAmount" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Nation&Currency </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
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

                                                <div class="tab-pane fade" id="tab_1_30">
                                                    <form action="re_index_result.jsp" method="post"
                                                          onsubmit="return getbranch('C_BankBranch');">
                                                        <input type="hidden" id="C_BankBranch" name="branch" value=""/>
                                                        <input type="hidden" name="search" value="ra1"/>
                                                        <input type="hidden" name="rep" value="Banking/Branching Sheet"/>
                                                        <input type="hidden" name="return" value="re_index_central.jsp"/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Banking/Branching Sheet </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="clearfix"></div>   
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>From/To</label>
                                                                        <select class="form-control select2 "
                                                                                name="typeft" id="typeft">
                                                                            <option value="ALL">All</option>
                                                                            <option value="F">From</option>
                                                                            <option value="T">To</option>
                                                                        </select>
                                                                    </div>
                                                                </div>    
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Destination/Source</label>
                                                                        <select class="form-control select2 "
                                                                                name="typedest" id="typedest">
                                                                            <option value="ALL">All</option>
                                                                            <option value="BA">Bank</option>
                                                                            <option value="BR">Branch</option>
                                                                            <option value="PO">Pos/Bank Account</option>
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
                                                <div class="tab-pane fade" id="tab_c_29">
                                                    <form action="ReportCentrale?type=C_freeTaxPivotTotale" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_freeTaxPivotTotale');">
                                                        <input type="hidden" id="C_freeTaxPivotTotale" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Pivot </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
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
                                                <div class="tab-pane fade" id="tab_c_27">
                                                    <form action="ReportCentrale?type=C_FreeTax_AmountNation" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_FreeTax_AmountNation');">
                                                        <input type="hidden" id="C_FreeTax_AmountNation" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Currency&Nation </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_26">
                                                    <form action="ReportCentrale?type=C_FreeTax_AmountBranch" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_FreeTax_AmountBranch');">
                                                        <input type="hidden" id="C_FreeTax_AmountBranch" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Currency&Branch </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_25">
                                                    <form action="ReportCentrale?type=C_FreeTax_BranchCurrency" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_FreeTax_BranchCurrency');">
                                                        <input type="hidden" id="C_FreeTax_BranchCurrency" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Branch&Currency </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_24">
                                                    <form action="ReportCentrale?type=C_FreeTax_CausalBranch" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_FreeTax_CausalBranch');">
                                                        <input type="hidden" id="C_FreeTax_CausalBranch" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Causal&Branch </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_22">
                                                    <form action="ReportCentrale?type=C_FreeTax_Branch" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_FreeTax_Branch');">
                                                        <input type="hidden" id="C_FreeTax_Branch" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Branch </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_23">
                                                    <form action="ReportCentrale?type=C_FreeTax_BranchCausal" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_FreeTax_BranchCausal');">
                                                        <input type="hidden" id="C_FreeTax_BranchCausal" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Branch&Causal </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_21">
                                                    <form action="ReportCentrale?type=C_FreeTax_Analisys" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_FreeTax_Analisys');">
                                                        <input type="hidden" id="C_FreeTax_Analisys" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> VAT Refund Analysis </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_20">
                                                    <form action="ReportCentrale?type=C_CasherOpenCloseError" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_CasherOpenCloseError');">
                                                        <input type="hidden" id="C_CasherOpenCloseError" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Cashier Open/Close Errors </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_19">
                                                    <form action="ReportCentrale?type=C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod');">
                                                        <input type="hidden" id="C_AnalysisDetailsTransactionCertificationExtraCEEforBranchAndPeriod" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Analysis Details Transaction Certification Non Resident ITA </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Type</label><p class='ab'></p>
                                                                        <select class="form-control select2" name="tipor" id="tipor">
                                                                            <option value="01">Notes, Euro T. Cheques, Traveller's Cheques</option>
                                                                            <option value="02">Cash Advance, Credit Card, Bancomat, Bank Credit Account</option>
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
                                                <div class="tab-pane fade" id="tab_c_18">
                                                    <form action="ReportCentrale?type=C_TransactionRegisterDetail" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_TransactionRegisterDetail');">
                                                        <input type="hidden" id="C_TransactionRegisterDetail" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Transaction Register Details </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-2">
                                                                    <div class="form-group">
                                                                        <label>Last Printed Page</label>
                                                                        <input type="text" class="form-control" name="spage" id="spage" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-2">
                                                                    <div class="form-group">
                                                                        <label>Last Printed Row</label>
                                                                        <input type="text" class="form-control" name="srow" id="row" />
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
                                                <div class="tab-pane fade" id="tab_c_17">
                                                    <form action="ReportCentrale?type=C_TransactionRegisterSummary" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_TransactionRegisterSummary');">
                                                        <input type="hidden" id="C_TransactionRegisterSummary" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Transaction Register Summary </h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
                                                                    </div>
                                                                </div>                                                                      
                                                                <div class="col-md-2">
                                                                    <div class="form-group">
                                                                        <label>Last Printed Page</label>
                                                                        <input type="text" class="form-control" name="spage" id="spage" />
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
                                                <div class="tab-pane fade" id="tab_c_16">
                                                    <form action="ReportCentrale?type=C_CashierDetails" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_CashierDetails');">
                                                        <input type="hidden" id="C_CashierDetails" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Cashier Performance Excel</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_15">
                                                    <form action="ReportCentrale?type=C_CashierPerformance" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_CashierPerformance');">
                                                        <input type="hidden" id="C_CashierPerformance" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Cashier Performance</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Type</label>
                                                                        <select class="form-control select2" id="bss" name="bss" placeholder="...">
                                                                            <option value="B">Buy</option>
                                                                            <option value="S">Sell</option>
                                                                            <option value="BS">Buy / Sell</option>
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
                                                <div class="tab-pane fade" id="tab_1_15_bis">
                                                    <form action="Report?type=BBTransactionListGroup" method="post" 
                                                          target="_blank" onsubmit="return getbranch('BBTransactionListGroup');">
                                                        <input type="hidden" id="BBTransactionListGroup" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Transaction List - BuyBack Group by Buy-Sell</h4>
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
                                                                        <input type="text" class="form-control date-picker datenowsi " id="d4" name="d4" />
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
                                                <div class="tab-pane fade" id="tab_1_15_tris">
                                                    <form action="Report?type=SBTransactionListGroup" method="post" 
                                                          target="_blank" onsubmit="return getbranch('SBTransactionListGroup');">
                                                        <input type="hidden" id="SBTransactionListGroup" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Transaction List - SellBack Group by Sell-Buy</h4>
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
                                                                        <input type="text" class="form-control date-picker datenowsi " id="d4" name="d4" />
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
                                                <div class="tab-pane fade" id="tab_c_14">
                                                    <form action="ReportCentrale?type=C_CloseBranch" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_CloseBranch');">
                                                        <input type="hidden" id="C_CloseBranch" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Branches Closures</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
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
                                                <div class="tab-pane fade" id="tab_c_13">
                                                    <form action="ReportCentrale?type=C_ChangeMovimentForAgency" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_ChangeMovimentForAgency');">
                                                        <input type="hidden" id="C_ChangeMovimentForAgency" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Analysis Transaction Change for Agency</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
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
                                                <div class="tab-pane fade" id="tab_c_11">
                                                    <form action="ReportCentrale?type=C_ChangeMovimentForBranches" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_ChangeMovimentForBranches');">
                                                        <input type="hidden" id="C_ChangeMovimentForBranches" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Analysis Transaction Change for Branch</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_12">
                                                    <form action="ReportCentrale?type=C_ChangeMovimentDetailForBranches" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_ChangeMovimentDetailForBranches');">
                                                        <input type="hidden" id="C_ChangeMovimentDetailForBranches" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Analysis Transaction Change for Branch Details</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
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
                                                <div class="tab-pane fade" id="tab_c_10">
                                                    <form action="ReportCentrale?type=C_InterbranchDetails" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_InterbranchDetails');">
                                                        <input type="hidden" id="C_InterbranchDetails" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Analysis Interbranch Details</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Currencies</label><p class='ab'></p>
                                                                        <select class="form-control select2" id="curr" name="curr" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <option value="01">Local</option>
                                                                            <option value="02">Foreign</option>
                                                                        </select>

                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>State</label><p class='ab'></p>
                                                                        <select class="form-control select2" multiple id="state" name="state" placeholder="...">
                                                                            <option value="OK">OK</option>
                                                                            <option value="KOF">KO From</option>
                                                                            <option value="KOT">KO To</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Difference</label><p class='ab'></p>
                                                                        <select class="form-control select2" id="diff" name="diff" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <option value="01"><> 0</option>
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
                                                <div class="tab-pane fade" id="tab_c_9">
                                                    <form action="ReportCentrale?type=C_Interbranch" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_Interbranch');">
                                                        <input type="hidden" id="C_Interbranch" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue"> Analysis Interbranch</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Currencies</label><p class='ab'></p>
                                                                        <select class="form-control select2" id="curr" name="curr" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <option value="01">Local</option>
                                                                            <option value="02">Foreign</option>
                                                                        </select>

                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>State</label><p class='ab'></p>
                                                                        <select class="form-control select2" multiple id="state" name="state" placeholder="...">

                                                                            <option value="OK">OK</option>
                                                                            <option value="KOF">KO From</option>
                                                                            <option value="KOT">KO To</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Difference</label><p class='ab'></p>
                                                                        <select class="form-control select2" id="diff" name="diff" placeholder="...">
                                                                            <option value="...">All</option>
                                                                            <option value="01"><> 0</option>
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
                                                <div class="tab-pane fade" id="tab_c_8">
                                                    <form action="ReportCentrale?type=C_ChangeInternetBookingForBranches" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_ChangeInternetBookingForBranches');">
                                                        <input type="hidden" id="C_ChangeInternetBookingForBranches" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Internet Booking for Branch</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
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
                                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="tab-pane fade" id="tab_c_7">
                                                    <form action="ReportCentrale?type=C_SizeAndQuantity" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_SizeAndQuantity');">
                                                        <input type="hidden" id="C_SizeAndQuantity" name="branch" value=""/>
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
                                                                        <input type="text" class="form-control date-picker yeste" name="d3" id="n3" />
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
                                                <div class="tab-pane fade" id="tab_c_6">
                                                    <form action="ReportCentrale?type=C_NoChangeMovimentForBranches" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_NoChangeMovimentForBranches');">
                                                        <input type="hidden" id="C_NoChangeMovimentForBranches" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Analysis Transaction No Change for Branch</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
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
                                                <div class="tab-pane active " id="tab_c_5">
                                                    <form action="ReportCentrale?type=C_AnalysisCurrency" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_AnalysisCurrency');">
                                                        <input type="hidden" id="C_AnalysisCurrency" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Analysis Currency</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3"/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_3">
                                                    <form action="ReportCentrale?type=C_AnalysisReprint" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_AnalysisReprint');">
                                                        <input type="hidden" id="C_AnalysisReprint" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Analysis Reprint Receipt Change</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
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
                                                <div class="tab-pane fade" id="tab_c_45b">
                                                    <form action="ReportCentrale?type=C_CustomerCareRefund" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_CustomerCareRefund');">
                                                        <input type="hidden" id="C_CustomerCareRefund" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Customer Care Refund</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_90">
                                                    <form action="ReportCentrale?type=DailyError" method="post" target="_blank" 
                                                          onsubmit="return getbranch('DailyError');">
                                                        <input type="hidden" id="DailyError" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Daily Error Tracking</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4"/>
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
                                                <div class="tab-pane fade" id="tab_c_4">
                                                    <form action="ReportCentrale?type=C_AnalysisReprintNoChange" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_AnalysisReprintNoChange');">
                                                        <input type="hidden" id="C_AnalysisReprintNoChange" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Analysis Reprint Receipt No Change</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
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
                                                <div class="tab-pane fade" id="tab_c_1">
                                                    <form action="ReportCentrale?type=C_ChangeVolumeAffair" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_ChangeVolumeAffair');">
                                                        <input type="hidden" id="C_ChangeVolumeAffair" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Change Turnover</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Show Transaction Column</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Show" name="Show"
                                                                               data-size="normal" data-on-text="YES" data-off-text="NO"
                                                                               data-on-color="success" data-off-color="danger" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Show Summary</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Summ" name="Summ"
                                                                               data-size="normal" data-on-text="YES" data-off-text="NO"
                                                                               data-on-color="success" data-off-color="danger" />
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
                                                <div class="tab-pane fade" id="tab_c_2">
                                                    <form action="ReportCentrale?type=C_ChangeVolumeAffairCashAdvance" method="post" target="_blank" 
                                                          onsubmit="return getbranch('C_ChangeVolumeAffairCashAdvance');">
                                                        <input type="hidden" id="C_ChangeVolumeAffairCashAdvance" name="branch" value=""/>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <h4 class="font-blue">Change Turnover Cash Advance</h4>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-12">
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date From</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d3" id="d3" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Date To</label>
                                                                        <input type="text" class="form-control date-picker daten" name="d4" id="d4" />
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Show Transaction Column</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Show" name="Show"
                                                                               data-size="normal" data-on-text="YES" data-off-text="NO"
                                                                               data-on-color="success" data-off-color="danger" />
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <div class="form-group">
                                                                        <label>Show Summary</label><p class='ab'></p>
                                                                        <input type="checkbox" class="make-switch" id="Summ" name="Summ"
                                                                               data-size="normal" data-on-text="YES" data-off-text="NO"
                                                                               data-on-color="success" data-off-color="danger" />
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
                                                <div class="tab-pane fade" id="tab_1_28MA">
                                                    <form action="ReportCentrale?type=Marketing" method="post" target="_blank" 
                                                          onsubmit="return getbranch('Marketing');">
                                                        <input type="hidden" id="Marketing" name="branch" value=""/>
                                                        <div class="row">
                                                            
                                                            <div class="col-md-12">
                                                                <div class="col-md-12">
                                                                    <div class="form-group">
                                                                        <b>N.B. This report will be generated for all branches, regardless of those selected in the filter.</b>
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





            <script src="assets/soop/js/jquery.cookie.js" type="text/javascript"></script>
            <!-- BEGIN THEME GLOBAL SCRIPTS -->
            <script>
                                                              function l1() {
                                                                  // store the currently selected tab in the hash value
                                                                  $("ul.nav-tabs > li > a").on("shown.bs.tab", function (e) {
                                                                      var id = $(e.target).attr("href").substr(1);
                                                                      $.cookie('activeTab2', id);
                                                                      setDate();
                                                                  });
                                                                  $("ul.dropdown-menu > li > a").on("shown.bs.tab", function (e) {
                                                                      var id = $(e.target).attr("href").substr(1);
                                                                      $.cookie('activeTab2', id);
                                                                      setDate();
                                                                  });
                                                                  // on load of the page: switch to the currently selected tab
                                                                  var hash = $.cookie('activeTab2');
                                                                  if (hash !== null) {
                                                                      $('#myTab a[href="#' + hash + '"]').tab('show');
                                                                      setDate();
                                                                  }
                                                              }
            </script>

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
