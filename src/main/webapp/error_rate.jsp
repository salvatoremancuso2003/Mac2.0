<%@page import="rc.so.entity.Stock"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.entity.ET_change"%>
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
        <title>Mac2.0 - Change Rate</title>
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
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        
       

        <script type="text/javascript">

            <%String loc_cur = Engine.getLocalFigures()[0];%>

            function changevaluerate() {
                var idval = document.getElementById('kind_1').value.trim();
                document.getElementById('chra').value = document.getElementById('ra_' + idval).value.trim();
            }

            function controllofinale() {

                var idval = document.getElementById('kind_1').value.trim();
                var loc = '<%=loc_cur%>';

                if (idval.split(";")[2] === loc) {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "Local currency '" + loc + "' are unable to change rate.";
                    document.getElementById('chra').disabled = true;
                    document.getElementById('chra').value = document.getElementById('ra_' + idval).value.trim();
                    return false;
                }
            }

            function check(final) {
                var idval = document.getElementById('kind_1').value.trim();
                if (document.getElementById('va_' + idval) !== null) {
                    var va = document.getElementById('va_' + idval).value.trim();
                    var ra = document.getElementById('ra_' + idval).value.trim();
                    document.getElementById('tab_1_1').innerHTML = '';
                    document.getElementById('tab_1_2').innerHTML = '';
                    document.getElementById('tab_1_3').innerHTML = '';
                    document.getElementById('tab_1_4').innerHTML = '';
                    var es = true;
                    $.ajax({
                        async: false,
                        type: "POST",
                        url: "Operazioni_test?type=check_changerate&idval=" + idval + "&va=" + va + "&ra=" + ra,
                        success: function (data) {
                            var arrayJson = JSON.parse(data);
                            if (arrayJson.length > 0) {
                                if (arrayJson[0] === "falseV") {
                                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                                    document.getElementById('errorlarge').style.display = "block";
                                    document.getElementById('errorlargetext').innerHTML = arrayJson[1];
                                    es = false;
                                    document.getElementById('chra').disabled = true;
                                    document.getElementById('chra').value = document.getElementById('ra_' + idval).value.trim();

                                } else {
                                    document.getElementById('chra').disabled = false;
                                    document.getElementById('chra').value = document.getElementById('ra_' + idval).value.trim();

                                    var dim_ext = arrayJson[1].split("-")[1].trim();
                                    var dim_sel = arrayJson[2].split("-")[1].trim();
                                    var dim_sto = arrayJson[3].split("-")[1].trim();
                                    var dim_his = arrayJson[4].split("-")[1].trim();

                                    if (parseInt(dim_ext) > 0) {
                                        var div = document.getElementById('tab_1_2');
                                        var div_ta = document.createElement('div');
                                        div_ta.className = 'table-scrollable';
                                        var ta = document.createElement('table');
                                        ta.className = "table table-hover";
                                        var thead = document.createElement('thead');
                                        var row1 = document.createElement("tr");
                                        var th1 = document.createElement("th");
                                        th1.innerHTML = "Id Transaction";
                                        var th2 = document.createElement("th");
                                        th2.innerHTML = "Date";
                                        var th3 = document.createElement("th");
                                        th3.innerHTML = "Change Rate";
                                        var th4 = document.createElement("th");
                                        th4.innerHTML = "Amount";
                                        var th5 = document.createElement("th");
                                        th5.innerHTML = "Total";
                                        row1.appendChild(th1);
                                        row1.appendChild(th2);
                                        row1.appendChild(th3);
                                        row1.appendChild(th4);
                                        row1.appendChild(th5);
                                        thead.appendChild(row1);
                                        var tb = document.createElement('tbody');
                                        var limit = parseInt(dim_ext);
                                        var partenza = 5;
                                        var i = 0;
                                        while (i < limit) {
                                            var row1 = document.createElement("tr");
                                            var th1 = document.createElement("td");
                                            th1.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th2 = document.createElement("td");
                                            th2.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th3 = document.createElement("td");
                                            th3.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th4 = document.createElement("td");
                                            th4.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th5 = document.createElement("td");
                                            th5.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            row1.appendChild(th1);
                                            row1.appendChild(th2);
                                            row1.appendChild(th3);
                                            row1.appendChild(th4);
                                            row1.appendChild(th5);
                                            tb.appendChild(row1);
                                            i++;
                                        }
                                        //total

                                        var row1 = document.createElement("tr");
                                        var th1 = document.createElement("td");
                                        th1.innerHTML = arrayJson[partenza];
                                        partenza++;
                                        var th2 = document.createElement("td");
                                        th2.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th3 = document.createElement("td");
                                        th3.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th4 = document.createElement("td");
                                        th4.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th5 = document.createElement("td");
                                        th5.innerHTML = "<b>" + arrayJson[partenza] + "</b>";

                                        row1.appendChild(th1);
                                        row1.appendChild(th2);
                                        row1.appendChild(th3);
                                        row1.appendChild(th4);
                                        row1.appendChild(th5);
                                        tb.appendChild(row1);


                                        ta.appendChild(thead);
                                        ta.appendChild(tb);
                                        div_ta.appendChild(ta);
                                        div.appendChild(div_ta);



                                    } else {
                                        var div = document.getElementById('tab_1_2');
                                        var alert1 = document.createElement("div");
                                        alert1.className = 'alert alert-info';
                                        alert1.innerHTML = "No External record for this operation.";
                                        div.appendChild(alert1);
                                    }
                                    if (parseInt(dim_sel) > 0) {
                                        var div = document.getElementById('tab_1_3');
                                        var div_ta = document.createElement('div');
                                        div_ta.className = 'table-scrollable';
                                        var ta = document.createElement('table');
                                        ta.className = "table table-hover";
                                        var thead = document.createElement('thead');
                                        var row1 = document.createElement("tr");
                                        var th1 = document.createElement("th");
                                        th1.innerHTML = "Id Transaction";
                                        var th2 = document.createElement("th");
                                        th2.innerHTML = "Date";
                                        var th3 = document.createElement("th");
                                        th3.innerHTML = "Change Rate";
                                        var th4 = document.createElement("th");
                                        th4.innerHTML = "Amount";
                                        var th5 = document.createElement("th");
                                        th5.innerHTML = "Total";
                                        row1.appendChild(th1);
                                        row1.appendChild(th2);
                                        row1.appendChild(th3);
                                        row1.appendChild(th4);
                                        row1.appendChild(th5);
                                        thead.appendChild(row1);
                                        var tb = document.createElement('tbody');
                                        var limit = parseInt(dim_sto);
                                        var partenza = (parseInt(dim_ext) * 5) + 12;
                                        var i = 0;
                                        while (i < limit) {
                                            var row1 = document.createElement("tr");
                                            var th1 = document.createElement("td");
                                            th1.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th2 = document.createElement("td");
                                            th2.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th3 = document.createElement("td");
                                            th3.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th4 = document.createElement("td");
                                            th4.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th5 = document.createElement("td");
                                            th5.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            row1.appendChild(th1);
                                            row1.appendChild(th2);
                                            row1.appendChild(th3);
                                            row1.appendChild(th4);
                                            row1.appendChild(th5);
                                            tb.appendChild(row1);
                                            i++;
                                        }
                                        //total

                                        var row1 = document.createElement("tr");
                                        var th1 = document.createElement("td");
                                        th1.innerHTML = arrayJson[partenza];
                                        partenza++;
                                        var th2 = document.createElement("td");
                                        th2.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th3 = document.createElement("td");
                                        th3.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th4 = document.createElement("td");
                                        th4.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th5 = document.createElement("td");
                                        th5.innerHTML = "<b>" + arrayJson[partenza] + "</b>";

                                        row1.appendChild(th1);
                                        row1.appendChild(th2);
                                        row1.appendChild(th3);
                                        row1.appendChild(th4);
                                        row1.appendChild(th5);
                                        tb.appendChild(row1);


                                        ta.appendChild(thead);
                                        ta.appendChild(tb);
                                        div_ta.appendChild(ta);
                                        div.appendChild(div_ta);


                                    } else {
                                        var div = document.getElementById('tab_1_3');
                                        var alert1 = document.createElement("div");
                                        alert1.className = 'alert alert-info';
                                        alert1.innerHTML = "No Sell ecord for this operation.";
                                        div.appendChild(alert1);
                                    }
                                    if (parseInt(dim_sto) > 0) {
                                        var div = document.getElementById('tab_1_1');
                                        var div_ta = document.createElement('div');
                                        div_ta.className = 'table-scrollable';
                                        var ta = document.createElement('table');
                                        ta.className = "table table-hover";
                                        var thead = document.createElement('thead');
                                        var row1 = document.createElement("tr");
                                        var th1 = document.createElement("th");
                                        th1.innerHTML = "Date";
                                        var th2 = document.createElement("th");
                                        th2.innerHTML = "Change rate";
                                        var th3 = document.createElement("th");
                                        th3.innerHTML = "Amount";
                                        var th4 = document.createElement("th");
                                        th4.innerHTML = "Unloaded";
                                        var th5 = document.createElement("th");
                                        th5.innerHTML = "Balance";
                                        row1.appendChild(th1);
                                        row1.appendChild(th2);
                                        row1.appendChild(th3);
                                        row1.appendChild(th4);
                                        row1.appendChild(th5);
                                        thead.appendChild(row1);
                                        var tb = document.createElement('tbody');

                                        var limit = parseInt(dim_sto);
                                        var partenza = (parseInt(dim_ext) * 5) + (parseInt(dim_sel) * 5) + 18;
                                        var i = 0;
                                        while (i < limit) {
                                            var row1 = document.createElement("tr");
                                            var th1 = document.createElement("td");
                                            th1.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th2 = document.createElement("td");
                                            th2.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th3 = document.createElement("td");
                                            th3.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th4 = document.createElement("td");
                                            th4.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th5 = document.createElement("td");
                                            th5.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            row1.appendChild(th1);
                                            row1.appendChild(th2);
                                            row1.appendChild(th3);
                                            row1.appendChild(th4);
                                            row1.appendChild(th5);
                                            tb.appendChild(row1);
                                            i++;
                                        }
                                        //total

                                        var row1 = document.createElement("tr");
                                        var th1 = document.createElement("td");
                                        th1.innerHTML = arrayJson[partenza];
                                        partenza++;
                                        var th2 = document.createElement("td");
                                        th2.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th3 = document.createElement("td");
                                        th3.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th4 = document.createElement("td");
                                        th4.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        partenza++;
                                        var th5 = document.createElement("td");
                                        th5.innerHTML = "<b>" + arrayJson[partenza] + "</b>";
                                        row1.appendChild(th1);
                                        row1.appendChild(th2);
                                        row1.appendChild(th3);
                                        row1.appendChild(th4);
                                        row1.appendChild(th5);
                                        tb.appendChild(row1);


                                        ta.appendChild(thead);
                                        ta.appendChild(tb);
                                        div_ta.appendChild(ta);
                                        div.appendChild(div_ta);
                                    } else {
                                        var div = document.getElementById('tab_1_1');
                                        var alert1 = document.createElement("div");
                                        alert1.className = 'alert alert-info';
                                        alert1.innerHTML = "No Stock record for this operation.";
                                        div.appendChild(alert1);
                                    }
                                    if (parseInt(dim_his) > 0) {
                                        var div = document.getElementById('tab_1_4');
                                        var div_ta = document.createElement('div');
                                        div_ta.className = 'table-scrollable';
                                        var ta = document.createElement('table');
                                        ta.className = "table table-hover";
                                        var thead = document.createElement('thead');
                                        var row1 = document.createElement("tr");
                                        var th1 = document.createElement("th");
                                        th1.innerHTML = "Date";
                                        var th2 = document.createElement("th");
                                        th2.innerHTML = "Old Rate";
                                        var th3 = document.createElement("th");
                                        th3.innerHTML = "New Rate";
                                        var th4 = document.createElement("th");
                                        th4.innerHTML = "Operator";
                                        row1.appendChild(th1);
                                        row1.appendChild(th2);
                                        row1.appendChild(th3);
                                        row1.appendChild(th4);
                                        thead.appendChild(row1);
                                        var tb = document.createElement('tbody');

                                        var limit = parseInt(dim_his);
                                        var partenza = (parseInt(dim_ext) * 5) + (parseInt(dim_sel) * 5) + (parseInt(dim_sto) * 5) + 23;
                                        var i = 0;
                                        while (i < limit) {
                                            var row1 = document.createElement("tr");
                                            var th1 = document.createElement("td");
                                            th1.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th2 = document.createElement("td");
                                            th2.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th3 = document.createElement("td");
                                            th3.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            var th4 = document.createElement("td");
                                            th4.innerHTML = arrayJson[partenza];
                                            partenza++;
                                            row1.appendChild(th1);
                                            row1.appendChild(th2);
                                            row1.appendChild(th3);
                                            row1.appendChild(th4);
                                            tb.appendChild(row1);
                                            i++;
                                        }

                                        ta.appendChild(thead);
                                        ta.appendChild(tb);
                                        div_ta.appendChild(ta);
                                        div.appendChild(div_ta);


                                    } else {
                                        var div = document.getElementById('tab_1_4');
                                        var alert1 = document.createElement("div");
                                        alert1.className = 'alert alert-info';
                                        alert1.innerHTML = "No History for this operation.";
                                        div.appendChild(alert1);
                                    }
                                }
                            }
                        }
                    });
                    if (!es) {
                        return false;
                    }
                    if (!final) {
                        changevaluerate();
                    }
                }
            }


            function loadpage() {
                inputvirgola();
                check(false);
            }
        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return loadpage();" style="height: 800px;">
        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <!-- ERROR MODAL -->
        <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
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
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">
                    <%  String code = request.getParameter("code");
                        if (code != null) {
                            ET_change et = Engine.get_ET_change(code);
                            if (et != null) {
                                String fil[] = Engine.getFil();
                                String decimal = Constant.decimal;
                                String thousand = Constant.thousand;
                                ArrayList<String[]> array_kind = Engine.list_all_kind(fil[0]);
                                ArrayList<ET_change> val = Engine.get_ET_change_value(code);
                                ArrayList<String[]> array_bank = Engine.list_bank_enabled();
                                ArrayList<Branch> array_branch = Engine.list_branch_enabled();
                                ArrayList<String[]> array_credit_card = Engine.list_bank_pos_enabled();
                    %>

                    <form action="Operazioni?type=changerate" method="post" onsubmit="return controllofinale();">
                        <input type="hidden" name="code" value="<%=Utility.sanitize(code)%>" />
                        <div class="row">
                            <h3 class="page-title">External Transfer <small><b>Change Rate </b></small></h3>
                        </div>

                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Type</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=et.format_tofrom_brba(et.getFg_tofrom(), et.getFg_brba())%>"/>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Source/Destination</label>
                                    <input type="text" class="form-control" 
                                           disabled="disabled"
                                           value="<%=Utility.sanitize(et.getCod_dest()) + " - " + Utility.sanitize(Engine.formatBankBranch(et.getCod_dest(), et.getFg_brba(), array_bank, array_branch,array_credit_card))%>"/>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Code</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getId())%>"/>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Date</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(et.getDt_it(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Operator</label>
                                    <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getUser())%>"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Figures</label>
                                    <select class="form-control select2" name="kind_1" id="kind_1" placeholder="..." onchange="return check(false);">
                                        <option value=""></option>
                                        <%for (int x = 0; x < val.size(); x++) {
                                                Currency cu = Engine.getCurrency(val.get(x).getValuta());
                                        %>
                                        <option value="<%=Utility.sanitize(val.get(x).getCod()) + ";" + Utility.sanitize(val.get(x).getSupporto()) + ";" + Utility.sanitize(val.get(x).getValuta())%>">Figures: <%=Utility.sanitize(val.get(x).getSupporto())%> - <%=Utility.sanitize(Utility.formatAL(val.get(x).getSupporto(), array_kind, 1))%> | Currency: <%=Utility.sanitize(cu.getCode())%> - <%=Utility.sanitize(cu.getDescrizione())%> | Amount: <%=Utility.formatMysqltoDisplay(Utility.sanitize(val.get(x).getIp_quantity()))%> | Rate: <%=Utility.formatMysqltoDisplay(Utility.sanitize(val.get(x).getIp_rate()))%></option>
                                        <%}%>
                                    </select>
                                    <%for (int x = 0; x < val.size(); x++) {%>
                                    <input type="hidden" id="ra_<%=Utility.sanitize(val.get(x).getCod()) + ";" + Utility.sanitize(val.get(x).getSupporto()) + ";" + Utility.sanitize(val.get(x).getValuta())%>" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(val.get(x).getIp_rate()))%>"/>
                                    <input type="hidden" id="va_<%=Utility.sanitize(val.get(x).getCod()) + ";" + Utility.sanitize(val.get(x).getSupporto()) + ";" + Utility.sanitize(val.get(x).getValuta())%>" value="<%=Utility.sanitize(val.get(x).getValuta())%>"/>
                                    <%}%>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label>Change Rate</label>

                                    <input type="text" class="form-control" id="chra" name="chra"
                                           value="0<%=decimal%>00" onfocus="this.select();"
                                           onchange="return formatValueDecimal_length(this, '<%=thousand%>', '<%=decimal%>', 8);"
                                           />

                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <ul class="nav nav-tabs">
                                    <li class="active">
                                        <a href="#tab_1_1" data-toggle="tab" aria-expanded="true"> Stock </a>
                                    </li>
                                    <li class="">
                                        <a href="#tab_1_2" data-toggle="tab" aria-expanded="false"> External </a>
                                    </li>
                                    <li class="">
                                        <a href="#tab_1_3" data-toggle="tab" aria-expanded="false"> Transaction </a>
                                    </li>
                                    <li class="">
                                        <a href="#tab_1_4" data-toggle="tab" aria-expanded="false"> History Edit</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade active in" id="tab_1_1">
                                    </div>
                                    <div class="tab-pane fade" id="tab_1_2">
                                    </div>
                                    <div class="tab-pane fade" id="tab_1_3">
                                    </div>
                                    <div class="tab-pane fade" id="tab_1_4">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-md-12">
                                <center><button class="btn btn-lg red btn-block" type="submit"><i class="fa fa-save"></i> Confirm</button></center>
                            </div>                            
                        </div>
                    </form>         
                    <%} else {

                        }
                    } else {%>

                    <%}%>
                    <p class='ab'></p>
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
                        } else if (esito.equals("ko1")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. ";
                        } else if (esito.equals("ko2")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed. ";
                        }
                        if (!esito.equals("")) {
                    %>
                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=msg1%>
                            </div>
                        </div>
                    </div>
                    <%}%>

                </div>





            </div>
        </div>
        <!-- BEGIN CONTENT -->
        <!-- END PAGE TITLE-->
        <!-- END PAGE HEADER-->

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
