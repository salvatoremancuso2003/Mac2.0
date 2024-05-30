<%@page import="rc.so.entity.NC_transaction"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.entity.Ch_transaction_value"%>
<%@page import="rc.so.entity.Ch_transaction"%>
<%@page import="rc.so.util.Etichette"%>
<%@page import="rc.so.util.Utility"%>
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
        <title>Mac2.0 - Edit Payment Mode</title>
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
            function visual() {
                var tipotr = document.getElementById('tipotr').value;
                if (tipotr === 'S' || tipotr === 'NC') {
                    var kind = document.getElementById('kind_p1').value;
                    if (kind === '06' || kind === '07') {
                        document.getElementById('pos0').style.display = '';
                        document.getElementById('ba0').style.display = 'none';
                    } else if (kind === '08') {
                        document.getElementById('pos0').style.display = 'none';
                        document.getElementById('ba0').style.display = '';
                    } else {
                        document.getElementById('pos0').style.display = 'none';
                        document.getElementById('ba0').style.display = 'none';
                    }

                }
            }
        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return visual();">
        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <%
            String lan_index = (String) session.getAttribute("language");
            lan_index = "IT";
            Etichette et_index = new Etichette(lan_index);

        %>
        <div class="clearfix"> </div>
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
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
            <div class="page-content-wrapper">
                <!-- BEGIN CONTENT BODY -->
                <div class="page-content">

                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction <small><b>Edit Payment Mode</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                        <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
                    </div>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=editpaymode">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-body">
                                        <div class="form-body">
                                            <%String code = request.getParameter("code");
                                                Ch_transaction ch = Engine.query_transaction_ch(code);

                                                ArrayList<String[]> array_bankacc = Engine.list_bankAccount();
                                                ArrayList<String[]> array_credit_card = Engine.credit_card_enabled();
                                                if (ch != null) {

                                                    ArrayList<String[]> array_kind_pay = Engine.kind_payment();
                                                    boolean show = false;
                                                    ArrayList<Ch_transaction_value> val = Engine.query_transaction_value(code);
                                                    if (ch.getTipotr().equals("B")) {
                                                        for (int i = 0; i < val.size(); i++) {
                                                            if (val.get(i).getSupporto().equals("04")) {
                                                                show = true;
                                                                break;
                                                            }
                                                        }
                                                    } else {
                                                        show = true;
                                                    }
                                                    if (show) {

                                            %>
                                            <input type="hidden" id="tipotr" name="tipotr" value="<%=Utility.sanitize(ch.getTipotr())%>"/>
                                            <input type="hidden" id="idopen" name="idopen" value="<%=Utility.sanitize(ch.getId_open_till())%>"/>

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Type Transaction</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control uppercase" readonly value="<%=Ch_transaction.formatType(ch.getTipotr())%>"/> 
                                                </div>
                                            </div>
                                            <%if (ch.getTipotr().equals("B")) {
                                                    for (int i = 0; i < val.size(); i++) {
                                                        String ccnumb = "";
                                                        if (!val.get(i).getPos().equals("-") && !val.get(i).getPos().equals("N")) {
                                                            ccnumb = val.get(i).getPosnum();
                                                        }

                                            %>
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Kind</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" name="ki" readonly
                                                           value="<%=Utility.sanitize(Engine.get_figures(val.get(i).getSupporto(), ch.getFiliale()).getDe_supporto())%>"
                                                           /> 
                                                </div>
                                            </div>
                                            <input type="hidden" name="oldpos<%=i%>" value="<%=Utility.sanitize(val.get(i).getPos())%>" />
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Pos</label>
                                                <div class="col-md-3">
                                                    <select class="form-control select2" id="posvalue<%=i%>" name="newpos<%=i%>" 
                                                            data-container="body" 
                                                            onkeypress="return keysub(this, event);">
                                                        <%for (int x = 0; x < array_credit_card.size(); x++) {
                                                                String sel = "";
                                                                if (val.get(i).getPos().equals(array_credit_card.get(x)[0])) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option <%=sel%> value="<%=array_credit_card.get(x)[0]%>"><%=array_credit_card.get(x)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <label class="col-md-3 control-label">CC Number</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="cc_number" name="cc_number" 
                                                           maxlength="4" onkeyup="return fieldNOSPecial_2(this.id);" value="<%=Utility.sanitize(ccnumb)%>" />
                                                </div>    
                                            </div>

                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Quantity</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" name="qua" readonly
                                                           value="<%=Utility.formatMysqltoDisplay(Utility.sanitize(val.get(i).getQuantita()))%>"
                                                           /> 
                                                </div>
                                            </div>
                                            <div class="form-group" id="mot1" style="display: none;">
                                                <label class="col-md-3 control-label">Motivation</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="notes<%=i%>" name="notes<%=i%>" 
                                                           maxlength="100" onkeyup="return fieldNOSPecial_2(this.id);"  /> 
                                                </div>
                                            </div>               
                                            <%}%>

                                            <%} else {

                                                String ccnumb = ch.getCredccard_number().replaceAll("-", "");
                                                if (!ch.getLocalfigures().equals("06")) {
                                                    ccnumb = "";
                                                }

                                            %>
                                            <input type="hidden" name="old_kind" value="<%=Utility.sanitize(ch.getLocalfigures())%>" />
                                            <input type="hidden" name="old_ban" value="<%=Utility.sanitize(ch.getPos())%>" />
                                            <input type="hidden" name="old_pos" value="<%=Utility.sanitize(ch.getPos())%>" />
                                            <div class="form-group">
                                                <label class="col-md-3 control-label">Local Figures</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" id="kind_p1" name="new_kind" placeholder="..." 
                                                            onchange="return visual();" onkeypress="return keysub(this, event);">
                                                        <%for (int i = 0; i < array_kind_pay.size(); i++) {
                                                                String sel = "";
                                                                if (array_kind_pay.get(i)[0].equals(ch.getLocalfigures())) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option <%=sel%> value="<%=array_kind_pay.get(i)[0]%>"><%=array_kind_pay.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="form-group" id="ba0">
                                                <label class="col-md-3 control-label">Bank</label>
                                                <div class="col-md-9">
                                                    <select class="form-control select2" id="ban_1" name="new_ban" onkeypress="return keysub(this, event);" >
                                                        <%for (int i = 0; i < array_bankacc.size(); i++) {
                                                                String sel = "";
                                                                if (array_bankacc.get(i)[0].equals(ch.getPos())) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option <%=sel%> value="<%=array_bankacc.get(i)[0]%>"><%=array_bankacc.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group" id="pos0">
                                                <label class="col-md-3 control-label">Pos</label>
                                                <div class="col-md-3">
                                                    <select class="form-control select2" id="pos_1" name="new_pos" onkeypress="return keysub(this, event);">
                                                        <%for (int i = 0; i < array_credit_card.size(); i++) {
                                                                String sel = "";
                                                                if (array_credit_card.get(i)[0].equals(ch.getPos())) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option <%=sel%> value="<%=array_credit_card.get(i)[0]%>"><%=array_credit_card.get(i)[1]%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <label class="col-md-3 control-label">CC Number</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="cc_number" name="cc_number" 
                                                           maxlength="4" onkeyup="return fieldNOSPecial_2(this.id);" value="<%=ccnumb%>" />
                                                </div>
                                            </div>

                                            <div class="form-group" id="mot1" style="display: none;">
                                                <label class="col-md-3 control-label">Motivation</label>
                                                <div class="col-md-9">
                                                    <input type="text" class="form-control" id="notes" name="notes" 
                                                           maxlength="100" onkeyup="return fieldNOSPecial_2(this.id);"  /> 
                                                </div>
                                            </div>

                                            <%}%>

                                        </div>

                                        <%}%>

                                        <%} else {
                                            NC_transaction nc = Engine.get_NC_transaction(code);
                                            if (nc != null) {
                                                ArrayList<String[]> array_kind_pay = Engine.kind_payment_nochange(nc);

                                                String ccnumb = nc.getPosnum().replaceAll("-", "");
                                                if (!nc.getSupporto().equals("06")) {
                                                    ccnumb = "";
                                                }


                                        %>
                                        <input type="hidden" id="tipotr" name="tipotr" value="NC"/>
                                        <input type="hidden" id="idopen" name="idopen" value="<%=Utility.sanitize(nc.getId_open_till())%>"/>
                                        <input type="hidden" name="old_kind" value="<%=Utility.sanitize(nc.getSupporto())%>" />
                                        <input type="hidden" name="old_ban" value="<%=Utility.sanitize(nc.getPos())%>" />
                                        <input type="hidden" name="old_pos" value="<%=Utility.sanitize(nc.getPos())%>" />
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Local Figures</label>
                                            <div class="col-md-9">
                                                <select class="form-control select2" id="kind_p1" name="new_kind" placeholder="..." 
                                                        onchange="return visual();" onkeypress="return keysub(this, event);">
                                                    <%for (int i = 0; i < array_kind_pay.size(); i++) {
                                                            String sel = "";
                                                            if (array_kind_pay.get(i)[0].equals(nc.getSupporto())) {
                                                                sel = "selected";
                                                            }
                                                    %>
                                                    <option <%=sel%> value="<%=array_kind_pay.get(i)[0]%>"><%=array_kind_pay.get(i)[1]%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group" id="ba0">
                                            <label class="col-md-3 control-label">Bank</label>
                                            <div class="col-md-9">
                                                <select class="form-control select2" id="ban_1" name="new_ban" onkeypress="return keysub(this, event);" >
                                                    <%for (int i = 0; i < array_bankacc.size(); i++) {
                                                            String sel = "";
                                                            if (array_bankacc.get(i)[0].equals(nc.getPos())) {
                                                                sel = "selected";
                                                            }
                                                    %>
                                                    <option <%=sel%> value="<%=array_bankacc.get(i)[0]%>"><%=array_bankacc.get(i)[1]%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group" id="pos0">
                                            <label class="col-md-3 control-label">Pos</label>
                                            <div class="col-md-3">
                                                <select class="form-control select2" id="pos_1" name="new_pos" onkeypress="return keysub(this, event);">
                                                    <%for (int i = 0; i < array_credit_card.size(); i++) {
                                                            String sel = "";
                                                            if (array_credit_card.get(i)[0].equals(nc.getPos())) {
                                                                sel = "selected";
                                                            }

                                                    %>
                                                    <option <%=sel%> value="<%=array_credit_card.get(i)[0]%>"><%=array_credit_card.get(i)[1]%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                            <label class="col-md-3 control-label">CC Number</label>
                                            <div class="col-md-3">
                                                <input type="text" class="form-control" id="cc_number" name="cc_number" 
                                                       maxlength="4" onkeyup="return fieldNOSPecial_2(this.id);" value="<%=ccnumb%>" />
                                            </div>  
                                        </div>
                                        <div class="form-group" id="mot1" style="display: none;">
                                            <label class="col-md-3 control-label">Motivation</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" id="notes" name="notes" 
                                                       maxlength="100" onkeyup="return fieldNOSPecial_2(this.id);"  /> 
                                            </div>
                                        </div>
                                        <%}%>
                                        <%}%>
                                        <div class="modal-footer" id="savebutt" style="display: none;">
                                            <input type="hidden" name="idtrdel" id="idtrdel" value="<%=Utility.sanitize(code)%>"/>
                                            <button type="submit" class="btn btn-outline green"><i class="fa fa-check"></i> Confirm</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form> 
                    <%if (request.getParameter("esito") != null) {
                            if (request.getParameter("esito").equals("KO")) {%>
                    <div class="col-md-12">
                        <div class="alert alert-danger">
                            <strong>Error. <i class="fa fa-exclamation-triangle"></i></strong> Operation could not be completed. Try again.
                        </div>
                    </div>
                    <%} else if (request.getParameter("esito").equals("KOT")) {%>
                    <div class="col-md-12">
                        <div class="alert alert-danger">
                            <strong>Error. <i class="fa fa-exclamation-triangle"></i></strong> Operation could not be completed. Till are closed or inactive.
                        </div>
                    </div>
                    <%}
                        }%>
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


                                                           $(function () {
                                                               var $form = $('form');
                                                               var initialState = $form.serialize();
                                                               $('form :input').on('change input', function () {
                                                                   if (initialState === $form.serialize()) {
                                                                       $('#savebutt').toggle(false);
                                                                       $('#mot1').toggle(false);
                                                                   } else {
                                                                       $('#savebutt').toggle(true);
                                                                       $('#mot1').toggle(true);
                                                                   }
                                                                   //e.preventDefault();
                                                               });
                                                           });



                                                           $(document).ready(function () {
                                                               window.history.pushState(null, "", window.location.href);
                                                               window.onpopstate = function () {
                                                                   window.history.pushState(null, "", window.location.href);
                                                               };
                                                           });
        </script>

    </body>

</html>
