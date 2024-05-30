<%@page import="rc.so.entity.Ch_transaction_value"%>
<%@page import="rc.so.entity.Ch_transaction_doc"%>
<%@page import="rc.so.entity.Document"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Ch_transaction_refund"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.entity.Ch_transaction"%>
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
        <title>Mac2.0 - Refund</title>
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

            function inputvirgola2() {
                $(document).find('input').keydown(function (evt) {
                    if (evt.which === 110) {
                        if ($(this).val().endsWith(",")) {
                        } else {
                            $(this).val($(this).val() + ',');
                        }
                        evt.preventDefault();
                    }
                    ;
                });
            }

            function loadpage() {
                inputvirgola2();
                checktype();
            }

            function checktype() {
                if (document.getElementById('Type').checked) {
                    document.getElementById('val_id').style.display = 'none';
                } else {
                    document.getElementById('val_id').style.display = '';
                }
            }


            function check(formid, cod) {
                var co = document.getElementById('codusagetta').value.trim();
                var ido = document.getElementById('idopentill').value.trim();
                if (co === "" || ido === "") {
                    var ermsg = "You must complete fields with <span class='font-red'>*</span>.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }

                var occ = "true";
                $.ajax({
                    async: false,
                    type: "POST",
                    url: "Operazioni_test?type=controllaoccupato_till&q=none",
                    success: function (data) {
                        occ = data;
                    }
                });
                if (occ === "true") {
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    document.getElementById('errorlargetext').innerHTML = "Warning! Operation not permitted. You have an operation pending (Open/Close, Internal/External transfer), before proceeding you MUST first finish that operation.";
                    return false;
                }

                if (cod) {
                    $.ajax({
                        type: "POST",
                        url: "Query?type=checkunlockcode&q=" + co,
                        success: function (data) {
                            if (data === "true") {
                                document.getElementById(formid).submit();
                            } else {
                                document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                                document.getElementById("errorlarge").style.display = "block";
                                document.getElementById("errorlargetext").innerHTML = "Code Refund is invalid. Try again.";
                                return false;
                            }
                        }
                    });
                }




            }

            function checkvalue() {
                var oFile = document.getElementById("filepdf");
                if (oFile.value.trim() === "") {
                    var ermsg = "You must select one file.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }


                var blnValid = false;
                var sCurExtension = ".pdf";
                if (oFile.value.substr(oFile.value.length - sCurExtension.length, sCurExtension.length).toLowerCase() === sCurExtension.toLowerCase()) {
                    blnValid = true;
                }
                if (!blnValid) {
                    var ermsg = "File type must be pdf.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                if (oFile.files[0].size > 3145728) {
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = "Failed to proceed. File size is not allowed. The file exceeds the maximum size allowed (3 MB).";
                    return false;
                }
            }
        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" style="height: 600px;" onload="return loadpage();">
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
                            msg1 = "Code Refund are incorrect or disabled.";
                        } else if (esito.equals("koQ")) {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "Quantity of figures exceeds the amount available in this till.";
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
                        String code = request.getParameter("code");
                        if (code != null) {
                            ArrayList<Branch> array_branch = Engine.list_branch_enabled();
                            String decimal = Constant.decimal;
                            String thousand = Constant.thousand;
                            Ch_transaction ch = Engine.query_transaction_ch(code);

                            boolean iscashadvance = false;
                            ArrayList<Ch_transaction_value> list = Engine.query_transaction_value(code);
                            for (int x = 0; x < list.size(); x++) {
                                if (list.get(x).getSupporto().equals("04")) {
                                    iscashadvance = true;
                                    break;
                                }
                            }

                            Ch_transaction_refund ref = new Ch_transaction_refund(ch);

                            String update = "false";
                            if (ch.getRefund().equals("1")) {
                                ref = Engine.get_refund_trans(ch.getCod());
                                if (ref == null) {
                                    ref = new Ch_transaction_refund(ch);
                                } else {
                                    update = "true";
                                }
                            }
                            String ty = "";
                            if (ref.getType().equals("CO")) {
                                ty = "checked";
                            }
                            String me = "";
                            if (ref.getMethod().equals("BR")) {
                                me = "checked";
                            }

                            if (iscashadvance) {
                                ty = "readonly";
                            }

                            Till topen = Engine.get_till_opened(Utility.checkAttribute(session,"us_cod"));
                            ArrayList<String[]> array_till = Engine.list_till_enabled();
                            boolean onlyprint = Engine.get_branch(Engine.getFil()[0]).getFg_pad().equals("0");
                            if (!Constant.is_IT) {
                                onlyprint = true;
                            }
                            
                            String completevalue = ch.getPay();
                            
                            if(Constant.is_CZ){
                                double cv = Utility.fd(ch.getPay()) + Utility.fd(ch.getRound());
                                completevalue = Utility.roundDoubleandFormat(cv, 2);
                            }
                            
                            if (ch.getRefund().equals("0")) {%>

                    <%if (topen == null) {%>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="alert alert-danger">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> The operation could not be completed. No till opened. 
                            </div>
                        </div>
                    </div>  
                    <%} else {%>
                    <form id="submit_f1" class="form-horizontal" name="submit_f1" action="Operazioni?type=insert_ref" method="post">
                        <input type="hidden" name="idref" value="<%=Utility.sanitize(ref.getCod())%>"/>
                        <input type="hidden" name="idtr" value="<%=Utility.sanitize(code)%>"/>
                        <input type="hidden" name="completevalue" value="<%=Utility.sanitize(completevalue)%>"/>
                        <input type="hidden" name="autonomo" value="SI"/>

                        <div class="portlet light bordered">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="fa fa-money font-red"></i>
                                    <span class="caption-subject font-red bold uppercase">Refund Transaction</span>
                                </div>
                                <div class="tools"> 
                                    <button type="button" class="btn btn-outline red" onclick="return check('submit_f1', true);"><i class="fa fa-check"></i> Confirm</button>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="form-body">
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Id Transaction</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" value="<%=Utility.sanitize(ch.getId())%>" 
                                                       readonly />
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Branch</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" readonly 
                                                       value="<%=Engine.formatBankBranch(ref.getBranch_cod(), "BR", null, array_branch, null)%>"/>
                                            </div>
                                        </div>
                                        <%
                                            String rd1 = "readonly";
                                            String rd2 = "";
                                            if (Constant.is_CZ) {
                                                rd1 = "";
                                                rd2 = "readonly";
                                            }
                                        %>
                                        <div class="clearfix"></div>
                                        <div class="form-group">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Type</label>
                                            <div class="col-md-9">
                                                <input type="checkbox" class="make-switch" <%=rd1%> onchange="return checktype();"
                                                       name="typeref" 
                                                       id="Type" 
                                                       data-size="normal" data-on-color="info" data-off-color="default"
                                                       data-on-text="<span class='tabnow'>&nbsp;Complete&nbsp;</span>" 
                                                       data-off-text="<span class='tabnow'>&nbsp;Partial&nbsp;</span>">
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group" id="val_id">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Value</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" id="val" name="val" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ref.getValue()))%>"
                                                       <%=Utility.sanitize(rd2)%>
                                                       onchange="return formatValueDecimalMax(this, '<%=Utility.sanitize(ref.getValue())%>', '<%=thousand%>', '<%=decimal%>');"/>
                                            </div>
                                            <label class="col-md-3 text-left"><%=Utility.sanitize(ref.getDescrizione())%></label>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Till</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" disabled value="<%=Utility.formatAL(topen.getCod(), array_till, 1)%> (Id Open: <%=topen.getCod_opcl()%>)"/>
                                                <input type="hidden" name="till" value="<%=topen.getCod()%>"/>
                                                <input type="hidden" id="idopentill" name="idopentill" value="<%=topen.getId_opcl()%>"/>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>        
                                        <div class="form-group">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Code Refund <span class='font-red'>*</span></label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" name="codusagetta" id="codusagetta" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}%>



                    <%} else if (ref.getStatus().equals("0")) {%>
                    <%if (topen == null) {%>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="alert alert-danger">
                                <strong>Warning <i class="fa fa-exclamation-triangle"></i></strong> The operation could not be completed. No till opened. 
                            </div>
                        </div>
                    </div>  
                    <%} else {%>
                    <form id="submit_f2" class="form-horizontal" name="submit_f2" action="Operazioni?type=insert_ref" 
                          method="post" onsubmit="return check('submit_f2', false);">
                        <input type="hidden" name="idref" value="<%=Utility.sanitize(ref.getCod())%>"/>
                        <input type="hidden" name="idtr" value="<%=Utility.sanitize(code)%>"/>
                        <input type="hidden" name="codusagetta" id="codusagetta" value="-" />
                        <input type="hidden" name="autonomo" value="NO"/>


                        <div class="portlet light bordered">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="icon-wrench font-blue"></i>
                                    <span class="caption-subject font-blue bold uppercase">Refund Transaction</span>
                                </div>
                                <div class="tools"> 
                                    <button type="submit" class="btn btn-outline blue">
                                        <i class="fa fa-check"></i> Confirm
                                    </button>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="form-body">
                                    <div class="row">
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Id Transaction</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" value="<%=Utility.sanitize(ch.getId())%>" readonly />
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Branch</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" disabled value="<%=Engine.formatBankBranch(ref.getBranch_cod(),
                                                        "BR", null, array_branch, null)%>"/>
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Type</label>
                                            <div class="col-md-9">
                                                <input type="checkbox" class="make-switch" <%=ty%> disabled
                                                       name="typeref" id="Type" data-size="normal" data-on-color="info" data-off-color="default"
                                                       data-on-text="<span class='tabnow'>&nbsp;Complete&nbsp;</span>" data-off-text="<span class='tabnow'>&nbsp;Partial&nbsp;</span>">
                                            </div>
                                        </div>
                                        <div class="clearfix"></div>
                                        <div class="form-group" id="val_id">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Value</label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ref.getValue()))%>" disabled />
                                                <input type="hidden" name="val" value="<%=Utility.sanitize(ref.getValue())%>"/>
                                            </div>
                                        </div>

                                        <div class="clearfix"></div>
                                        <div class="form-group">
                                            <p class='ab'></p>
                                            <label class="col-md-3 control-label">Till <span class='font-red'>*</span></label>
                                            <div class="col-md-6">
                                                <input type="text" class="form-control" disabled value="<%=Utility.formatAL(topen.getCod(), array_till, 1)%> (Id Open: <%=topen.getCod_opcl()%>)"/>
                                                <input type="hidden" name="till" value="<%=topen.getCod()%>"/>
                                                <input type="hidden" id="idopentill" name="idopentill" value="<%=topen.getId_opcl()%>"/>
                                            </div>
                                        </div>



                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>  
                    <%}%>
                    <%} else {%>
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="icon-eye font-blue"></i>
                                <span class="caption-subject font-blue bold uppercase">Refund Transaction</span>
                            </div>
                        </div>
                        <div class="portlet-body form-horizontal">
                            <div class="form-body">
                                <div class="row">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">Id Transaction</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" value="<%=Utility.sanitize(ch.getId())%>" 
                                                   readonly />
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="form-group">
                                        <p class='ab'></p>
                                        <label class="col-md-3 control-label">Branch</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" readonly 
                                                   value="<%=Engine.formatBankBranch(ref.getBranch_cod(), "BR", null, array_branch, null)%>"/>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="form-group"  id="me1">
                                        <p class='ab'></p>
                                        <label class="col-md-3 control-label">Method</label>
                                        <div class="col-md-9">
                                            <input type="checkbox" class="make-switch" readonly <%=me%>
                                                   name="method" id="Method" data-size="normal" data-on-color="info" data-off-color="default"
                                                   data-on-text="<span class='tabnow'>Branch</span>" data-off-text="<span class='tabnow'>Transfer</span>">
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="form-group">
                                        <p class='ab'></p>
                                        <label class="col-md-3 control-label">Type</label>
                                        <div class="col-md-9">
                                            <input type="checkbox" class="make-switch" <%=ty%> readonly
                                                   name="typeref" id="Type" data-size="normal" data-on-color="info" data-off-color="default"
                                                   data-on-text="<span class='tabnow'>&nbsp;Complete&nbsp;</span>" data-off-text="<span class='tabnow'>&nbsp;Partial&nbsp;</span>">
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="form-group" id="val_id">
                                        <p class='ab'></p>
                                        <label class="col-md-3 control-label">Value</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" 
                                                   value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(ref.getValue()))%>" readonly />
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="form-group">
                                        <p class='ab'></p>
                                        <label class="col-md-3 control-label">Code Refund</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" 
                                                   value="<%=Utility.sanitize(ref.getCod_usaegetta())%>" readonly />
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="form-group">
                                        <p class='ab'></p>
                                        <label class="col-md-3 control-label">User</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" 
                                                   value="<%=Utility.sanitize(ref.getUser_refund())%>" readonly />
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="form-group">
                                        <p class='ab'></p>
                                        <label class="col-md-3 control-label">Date Refund</label>
                                        <div class="col-md-6">
                                            <input type="text" class="form-control" 
                                                   value="<%=Utility.sanitize(Utility.formatStringtoStringDate(ref.getDt_refund(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss"))%>"
                                                   readonly />
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>

                    <%
                        if (ref.getMethod().equals("BR")) {%>

                    <%
                        ArrayList<Ch_transaction_doc> doc = Engine.get_list_tr_doc(code);
                        String docty = "_macrefund";
                        if (!ch.getCn_number().equals("") && !ch.getCn_number().equals("-")) {
                            docty = "_maccredinot";
                        }

                        boolean refundprinted = false;
                        for (int i = 0; i < doc.size(); i++) {
                            if (doc.get(i).getTipodoc().equals("_macrefund") || doc.get(i).getTipodoc().equals("_maccredinot")) {
                                refundprinted = true;
                                break;
                            }
                        }
                        if (!refundprinted) {%>
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="icon-docs font-red"></i>
                                <span class="caption-subject font-red bold uppercase">Document Actions</span>
                            </div>
                        </div>
                        <div class="portlet-body form-horizontal">
                            <div class="form-body">
                                <% if (!onlyprint) {%>
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <%if (Constant.signoffline) {%>
                                        <a class="btn btn-default blue" href="Pdf?type=_macrefund&codtr=<%=Utility.sanitize(code)%>" target="_blank">
                                            <i class="fa fa-pencil-square-o"></i> Sign
                                        </a>
                                        <%} else {%>
                                        <a class="btn btn-default blue" href="Print?type=receiptrefund&codtr=<%=Utility.sanitize(code)%>" target="_blank">
                                            <i class="fa fa-pencil-square-o"></i> Sign
                                        </a>
                                        <%}%>
                                    </div>
                                </div>
                                <%if (!Constant.signoffline) {%>                
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <a class="btn btn-default green-jungle" href="Operazioni?type=verify_sign&codtr=<%=Utility.sanitize(code)%>&coddoc=<%=docty%>">
                                            <i class="fa fa-sign-out"></i> Verify Sign
                                        </a>
                                    </div>
                                </div>
                                <%}%>
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <hr>         
                                    </div>
                                </div>
                                <%}%>
                                <form method="POST" enctype="multipart/form-data" action="Operazioni?type=upload_doc_tra_ref" onsubmit="return checkvalue();">
                                    <input type="hidden" name="codtr" value="<%=Utility.sanitize(code)%>" />
                                    <input type="hidden" name="coddoc" value="<%=docty%>" />
                                    <div class="form-group">

                                        <div class="col-md-3">
                                            <a class="btn btn-default green-jungle" href="Print?down=OK&type=receiptrefund&codtr=<%=Utility.sanitize(code)%>" target="_blank">
                                                <i class="fa fa-download"></i> Download
                                            </a>
                                        </div>
                                        <div class="col-md-9">
                                            <div class="row">
                                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                                    <div class="input-group input-large">
                                                        <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                                            <i class="fa fa-file-o fileinput-exists font-blue"></i>&nbsp;
                                                            <span class="fileinput-filename"> </span>
                                                        </div>
                                                        <span class="input-group-addon btn default btn-file">
                                                            <span class="fileinput-new"> Select file </span>
                                                            <span class="fileinput-exists"> Change </span>
                                                            <input type="file" name="filepdf" id="filepdf" accept="application/pdf" 
                                                                   onkeypress="return keysub(this, event);"> </span>
                                                        <a href="javascript:;" class="input-group-addon btn red fileinput-exists" 
                                                           data-dismiss="fileinput"> Remove </a>
                                                    </div>
                                                    <span class="help-block"><small>Only file .pdf - Size max 3 Mb</small></span>
                                                    <button type="submit" class="btn red"><i class="fa fa-upload"></i> Upload</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form> 

                                <%}%>
                            </div>
                        </div>
                    </div>
                    <%}%>



                    <%}%>




                    <%} else {%>
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
