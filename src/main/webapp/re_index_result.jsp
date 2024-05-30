<%@page import="rc.so.entity.Office_sp"%>
<%@page import="rc.so.db.Db_Master"%>
<%@page import="rc.so.entity.ET_change"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Openclose"%>
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
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 
        <%
            String fil = Engine.getFil()[0];

            ArrayList<String[]> array_bank = Engine.list_bank_enabled();
            ArrayList<String[]> array_credit_card = Engine.credit_card(fil);
            ArrayList<String[]> list_bankAccountPOS = Engine.list_bankAccountPOS();
            ArrayList<Branch> array_branch = Engine.list_branch_completeAFTER311217();

        %>

        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>




        <script type="text/javascript">

            function changetype() {

                if (document.getElementById("typeop") === null) {

                } else {
                    var ty = document.getElementById("typeop").value;
                    $('#bankpos').empty().trigger('change');
                    var o = $("<option/>", {value: "...", text: "All"});
                    $('#bankpos').append(o);
                    if (ty === 'BA') {
            <%for (int j = 0; j < array_bank.size(); j++) {%>
                        var o = $("<option/>", {value: "<%=array_bank.get(j)[0]%>", text: "<%=array_bank.get(j)[1]%>"});
                        $('#bankpos').append(o);
            <%}%>
                    } else if (ty === 'PO') {
            <%for (int j = 0; j < array_credit_card.size(); j++) {%>
                        var o = $("<option/>", {value: "<%=array_credit_card.get(j)[0]%>", text: "<%=array_credit_card.get(j)[1]%>"});
                        $('#bankpos').append(o);
            <%}%>
                    }
                    //$('#bankpos').val($('#bankpos option:first-child').val()).trigger('change');
                }
            }

            function verify_checked() {
                if ($('.rafcl:checked').size() === 0) {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "Select at least one External Transfer.";
                    return false;
                }

            }


            function ch_all() {
                $(".rafcl").prop('checked', $(document.getElementById('check_0')).prop('checked'));
            }

            function loadpage() {
                online();
                changetype();
                inputvirgola();
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

            <%@ include file="menu/menu1.jsp"%>
            <!-- END MENU -->
            <%
                String lan_index = (String) session.getAttribute("language");
                lan_index = "IT";
                Etichette et_index = new Etichette(lan_index);

            %>

            <div class="modal fade" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
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
                            <h3 class="page-title">Report <small><b>Panel</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                    <!-- SELECT BUY/SELL -->

                    <%if (request.getParameter("search") == null) {%>
                    <%} else if (request.getParameter("search").equals("ra1")) {

                        String rep = request.getParameter("rep");
                        if (rep == null) {
                            rep = "";
                        }

                        if (rep.equals("Open Close - Analytical")) {
                            String branch = request.getParameter("branch");

                            ArrayList<Till> ti = Engine.listTill(branch);
                            ArrayList<Openclose> list_openclose_report = Engine.list_openclose_report(request.getParameter("d4"), request.getParameter("d5"), branch);
                    %>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Search - Report Open Close - Analytical</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form action="Report?type=Openclose_Anal" method="post" target="_blank">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <h4 class="font-blue"><b>Select Open/Close</b></h4>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date From</label>
                                                        <input type="text" class="form-control date-picker" value="<%=Utility.sanitize(request.getParameter("d4"))%>" id="d4" name="d4" readonly/>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date To</label>
                                                        <input type="text" class="form-control date-picker" value="<%=Utility.sanitize(request.getParameter("d5"))%>" id="d5" name="d5" readonly/>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>

                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Open/Close</label>
                                                        <select class="form-control select2" name="idopenclose" id="idopenclose">
                                                            <%for (int i = 0; i < list_openclose_report.size(); i++) {
                                                                    Openclose oc = list_openclose_report.get(i);%>
                                                            <option value="<%=Utility.sanitize(oc.getCod())%>"><%=Utility.sanitize(oc.getId())%> | <%=Utility.sanitize(Utility.formatStringtoStringDate(oc.getData(), Constant.patternsqldate, Constant.patternnormdate))%> | <%=Utility.sanitize(oc.getFg_tipo())%> | <%=Utility.sanitize(oc.getTill()) + " " + Utility.sanitize(Engine.formatAL_Till(oc.getTill(), ti))%> |</option>
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
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="clearfix">
                                <a href="<%=Utility.sanitize(request.getParameter("return"))%>" class="btn btn-outline red"><i class="fa fa-arrow-left"></i> Back</a>
                            </div>
                        </div>
                    </div>

                    <%} else if (rep.equals("External Transfer - Banking Sheet")) {

                        String typeop = Engine.formatBankBranch(request.getParameter("typeop"));

                        String bankpos = request.getParameter("bankpos");
                        if (bankpos.equals("---")) {
                            bankpos = null;
                        }

                        String desc = "";
                        if (typeop.toLowerCase().contains("pos")) {
                            desc = "All POS / BANK ACCOUNT";
                            typeop = "POS / BANK ACCOUNT";
                        } else {
                            desc = "All Banks";
                        }

                        if (bankpos != null) {
                            String d1 = Engine.formatBankBranch(bankpos, "BA", array_bank, null,array_credit_card);
                            desc = bankpos + " - " + d1;
                        }

                        ArrayList<ET_change> liET_change = Engine.list_ET_change_report(request.getParameter("d3"), request.getParameter("d4"),
                                bankpos, false, true, null, request.getParameter("branch"), typeop, request.getParameter("chnc"));

                    %>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Search - External Transfer - Banking <%=Utility.sanitize(desc)%></span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form action="Report?type=ToBankingSheet" method="post" target="_blank">
                                        <input type="hidden" name="typeop" value="<%=Utility.sanitize(request.getParameter("typeop"))%>"/>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <h4 class="font-blue"><b>Select External Transfer</b></h4>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date From</label>
                                                        <input type="text" class="form-control" value="<%=Utility.sanitize(request.getParameter("d3"))%>" id="d3" name="d3" readonly/>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date To</label>
                                                        <input type="text" class="form-control" value="<%=Utility.sanitize(request.getParameter("d4"))%>" id="d4" name="d4" readonly/>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>

                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Type Operation</label>
                                                        <input type="hidden" class="form-control" value="<%=Utility.sanitize(request.getParameter("chnc"))%>" name="chnc" readonly/>
                                                        <input type="text" class="form-control" value="<%=ET_change.typechangeno(request.getParameter("chnc"))%>" readonly/>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>External Transfer</label>
                                                        <select class="form-control select2" name="idopenclose" id="idopenclose">
                                                            <%for (int i = 0; i < liET_change.size(); i++) {
                                                                    ET_change oc = liET_change.get(i);

                                                                    String br = Engine.formatBankBranch(oc.getCod_dest(), "BA", array_bank, null,array_credit_card);



                                                            %>

                                                            <option value="<%=Utility.sanitize(oc.getCod())%>">
                                                                <%=Utility.sanitize(oc.getId())%> | 
                                                                <%=Utility.sanitize(Utility.formatStringtoStringDate(oc.getDt_it(),
                                                                        Constant.patternsqldate, Constant.patternnormdate))%>|
                                                                <%=oc.format_tofrom(oc.getFg_tofrom())%> <%=Utility.sanitize(oc.getCod_dest())%> - <%=br%></option>

                                                            <%}%>
                                                        </select>
                                                    </div>

                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">

                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="clearfix">
                                <a href="<%=Utility.sanitize(request.getParameter("return"))%>" class="btn btn-outline red"><i class="fa fa-arrow-left"></i> Back</a>
                            </div>
                        </div>
                    </div>

                    <%} else if (rep.equals("Banking/Branching Sheet")) {

                        String d3 = request.getParameter("d3");
                        String d4 = request.getParameter("d4");

                        String typeft = request.getParameter("typeft");
                        String typedest = request.getParameter("typedest");
                        String branch = request.getParameter("branch");

                        ArrayList<ET_change> liET_change = Engine.list_ET_change_report_central(d3, d4, typeft, typedest, branch);


                    %>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-search"></i>
                                        <span class="caption-subject">Search - External Transfer - Banking/Branching Sheet</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form action="ReportCentrale?type=C_BankBranch" method="post" target="_blank" onsubmit="return verify_checked();">
                                        <div class="row">
                                            <div class="col-md-12">

                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date From</label>
                                                        <input type="text" class="form-control date-picker" value="<%=Utility.sanitize(request.getParameter("d3"))%>" id="d3" name="d3" readonly/>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date To</label>
                                                        <input type="text" class="form-control date-picker" value="<%=Utility.sanitize(request.getParameter("d4"))%>" id="d4" name="d4" readonly/>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>

                                                <div class="col-md-12">
                                                    <div class="form-group">

                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <!-- BEGIN PORTLET-->
                                                                <div class="portlet light bordered">
                                                                    <div class="portlet-title">
                                                                        <div class="caption">
                                                                            <i class="icon-bar-chart font-blue"></i>
                                                                            <span class="caption-subject font-blue bold uppercase">External Transfer</span>
                                                                        </div>
                                                                        <div class="tools"> 

                                                                        </div>
                                                                    </div>
                                                                    <div class="portlet-body">
                                                                        <table class="table table-responsive table-bordered table-hover" id="sample_0" width="100%">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th class="tabnow" style="width: 110px;"> 
                                                                                        <div class="md-checkbox">
                                                                                            <input type="checkbox" id="check_0"
                                                                                                   name="check_0" class="md-checkbox" 
                                                                                                   onchange="return ch_all();"
                                                                                                   /> 
                                                                                            <label for="check_0">
                                                                                                <span></span>
                                                                                                <span class="check"></span>
                                                                                                <span class="box"></span> <b>Select/Unselect All</b>
                                                                                            </label>

                                                                                        </div>
                                                                                    </th>
                                                                                    <th class="tabnow">Code</th>
                                                                                    <th class="tabnow">Date</th>
                                                                                    <th class="tabnow">Branch</th>
                                                                                    <th class="tabnow">Type</th>
                                                                                    <th class="tabnow">To/From</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%for (int i = 0; i < liET_change.size(); i++) {
                                                                                        ET_change oc = liET_change.get(i);

                                                                                        String filiale = Engine.formatBankBranch(oc.getFiliale(), "BR", null, array_branch,null);
                                                                                        String descr = "";

                                                                                        String d = ET_change.format_tofrom_brba(oc.getFg_tofrom(), 
                                                                                                oc.getFg_brba(), oc.getCod_dest(), list_bankAccountPOS);

                                                                                        if (oc.getFg_brba().equals("BR")) {
                                                                                            descr = Engine.formatBankBranch(oc.getCod_dest(), "BR", null, array_branch,null);
                                                                                        } else {
                                                                                            descr = Engine.formatBankBranch(oc.getCod_dest(), "BA", array_bank, null, array_credit_card);
                                                                                        }%>
                                                                                <tr>
                                                                                    <td>
                                                                                        <div class="md-checkbox">
                                                                                            <input type="checkbox" id="check_<%=Utility.sanitize(oc.getCod())%>" 
                                                                                                   name="check_<%=Utility.sanitize(oc.getCod())%>" class="md-checkbox rafcl"> 
                                                                                            <label for="check_<%=Utility.sanitize(oc.getCod())%>">
                                                                                                <span></span>
                                                                                                <span class="check"></span>
                                                                                                <span class="box"></span>
                                                                                            </label>
                                                                                        </div>
                                                                                    </td>
                                                                                    <td><%=Utility.sanitize(oc.getId())%></td>
                                                                                    <td><%=Utility.sanitize(Utility.formatStringtoStringDate(oc.getDt_it(), Constant.patternsqldate, Constant.patternnormdate))%></td>
                                                                                    <td><%=filiale%></td>
                                                                                    <td><%=d%></td>
                                                                                    <td><%=Utility.sanitize(oc.getCod_dest()) + " - " + Utility.sanitize(descr)%></td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </tbody>
                                                                        </table>
                                                                    </div>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Type Report</label><p class='ab'></p>
                                                        <input type="checkbox" class="make-switch" id="Output" name="Output" checked
                                                               data-size="normal" data-on-text="Pdf" data-off-text="Excel"
                                                               data-on-color="success" data-off-color="danger" />&nbsp;
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="clearfix">
                                <a href="<%=Utility.sanitize(request.getParameter("return"))%>" class="btn btn-outline red"><i class="fa fa-arrow-left"></i> Back</a>
                            </div>
                        </div>
                    </div>


                    <%} else if (rep.equals("Office Stock Price")) {
                        String d3 = request.getParameter("d3");
                        String data1 = Utility.formatStringtoStringDate_null(d3, Constant.patternnormdate_filter, Constant.patternsql);
                        String branch = request.getParameter("branch");
                        if (branch == null || branch.equals("")) {
                            branch = Engine.getFil()[0];
                        }
                        ArrayList<Office_sp> li = Engine.list_query_officesp(branch, data1);
                    %>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Search -Office Stock Price - Branch</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form action="Report?type=OfficeStockPrice" method="post" target="_blank">
                                        <input type="hidden" name="branch" value="<%=Utility.sanitize(branch)%>"/>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <h4 class="font-blue"><b>Select Office Stock Price</b></h4>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date</label>
                                                        <input type="text" class="form-control" value="<%=Utility.sanitize(request.getParameter("d3"))%>" id="d3" name="d3" readonly/>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>

                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Office Stock Price</label>
                                                        <select class="form-control select2" name="spres" id="spres">
                                                            <%for (int i = 0; i < li.size(); i++) {
                                                                    Office_sp oc = li.get(i);
                                                            %>
                                                            <option value="<%=Utility.sanitize(oc.getCodice())%>"><%=Utility.sanitize(oc.getOpenclose_id())%> | <%=Utility.sanitize(oc.getType_oc())%> | <%= Utility.sanitize(Utility.formatStringtoStringDate(oc.getData(), Constant.patternsqldate, Constant.patternnormdate))%></option>
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
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="clearfix">
                                <a href="<%=Utility.sanitize(request.getParameter("return"))%>" class="btn btn-outline red"><i class="fa fa-arrow-left"></i> Back</a>
                            </div>
                        </div>
                    </div>





                    <%} else if (rep.equals("External Transfer - Branching Sheet")) {

                        String br1 = request.getParameter("br1");
                        if (br1.equals("---")) {
                            br1 = null;
                        }

                        String cdbr = request.getParameter("branch");
                        if (request.getParameter("branch").equals("---")) {
                            cdbr = Engine.getFil()[0];
                        }

                        //Branch br = Engine.get_branch(request.getParameter("branch"));
                        ArrayList<ET_change> liET_change = Engine.list_ET_change_report(request.getParameter("d3"), request.getParameter("d4"),
                                br1, true, false, null, cdbr, null, request.getParameter("chnc"));


                    %>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Search - External Transfer - Branch</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <form action="Report?type=ToBranchingSheet" method="post" target="_blank">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <h4 class="font-blue"><b>Select External Transfer</b></h4>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date From</label>
                                                        <input type="text" class="form-control" value="<%=Utility.sanitize(request.getParameter("d3"))%>" id="d3" name="d3" readonly/>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Date To</label>
                                                        <input type="text" class="form-control" value="<%=Utility.sanitize(request.getParameter("d4"))%>" id="d4" name="d4" readonly/>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>Type Operation</label>
                                                        <input type="hidden" class="form-control" value="<%=Utility.sanitize(request.getParameter("chnc"))%>" name="chnc" readonly/>
                                                        <input type="text" class="form-control" value="<%=ET_change.typechangeno(request.getParameter("chnc"))%>" readonly/>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group">
                                                        <label>External Transfer</label>
                                                        <select class="form-control select2" name="idopenclose" id="idopenclose">
                                                            <%for (int i = 0; i < liET_change.size(); i++) {
                                                                    ET_change oc = liET_change.get(i);

                                                                    Branch br = Engine.get_branch(oc.getCod_dest());
                                                            %>

                                                            <option value="<%=Utility.sanitize(oc.getCod())%>">
                                                                <%=Utility.sanitize(oc.getId())%> | 
                                                                <%=Utility.sanitize(Utility.formatStringtoStringDate(oc.getDt_it(), Constant.patternsqldate, Constant.patternnormdate))%>| <%=oc.format_tofrom(oc.getFg_tofrom())%> <%=Utility.sanitize(br.getCod())%> - <%=Utility.sanitize(br.getDe_branch())%></option>

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
                                                        <button type="submit" class="btn btn-outline dark" ><i class="fa fa-search"></i> Search</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="clearfix">
                                <a href="<%=Utility.sanitize(request.getParameter("return"))%>" class="btn btn-outline red"><i class="fa fa-arrow-left"></i> Back</a>
                            </div>
                        </div>
                    </div>





                    <%} else {%>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-bar-chart"></i>
                                        <span class="caption-subject">Results - Report</span>
                                    </div>
                                    <div class="actions">

                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <h4 class="font-blue"><b>Report Title</b></h4>
                                            </div>
                                            <div class="form-group">
                                                <a class="btn btn-outline green-jungle"><i class="fa fa-file-excel-o"></i> Export Excel</a>
                                                <a class="btn btn-outline red"><i class="fa fa-file-pdf-o"></i> Export Pdf</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <a href="<%=Utility.sanitize(request.getParameter("return"))%>" class="btn btn-outline red"><i class="fa fa-arrow-left"></i> Back</a>
                        </div>
                    </div>
                    <%}
                        }%>

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
                                                                                                                   scrollX: true,
                                                                                                                   columnDefs: [
                                                                                                                       {orderable: !1, targets: [0]},
                                                                                                                       {orderable: 1, targets: [1]},
                                                                                                                       {orderable: 1, targets: [2]},
                                                                                                                       {orderable: 1, targets: [3]},
                                                                                                                       {orderable: 1, targets: [4]},
                                                                                                                       {orderable: 1, targets: [5]}
                                                                                                                   ],
                                                                                                                   buttons: [],
                                                                                                                   colReorder: {reorderCallback: function () {

                                                                                                                       }},
                                                                                                                   lengthMenu: [
                                                                                                                       [10, 50, 100, -1],
                                                                                                                       [10, 50, 100, "All"]
                                                                                                                   ],
                                                                                                                   pageLength: 10,
                                                                                                                   order: [[3, "asc"]],
                                                                                                                   dom: "<'row' <'col-md-12'B>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><t><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>"
                                                                                                               });
                                                                                                           };
                                                                                                           jQuery().dataTable && dt1();
                                                                                                       });
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
