<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String link_value = Engine.verifyUser(request);
    if(link_value!=null){
        Utility.redirect(request, response,link_value);
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
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="favicon.ico" /> 
        
        

        
        <script type="text/javascript">
            function changepaymentmode() {
                var kind = document.getElementById('kind_1').value;
                if (kind === "" || kind === "...") {
                    document.getElementById('ex6').style.display = 'none';
                    document.getElementById('ex6B').style.display = 'none';
                    return false;
                }
                if (kind === '01') {
                    document.getElementById('ex6').style.display = 'none';
                } else {
                    document.getElementById('ex6').style.display = '';
                }
                if (kind !== '06') {
                    document.getElementById('ex6B').style.display = 'none';
                } else {

                    document.getElementById('ex6B').style.display = '';
                }
            }


            function submitonEnter(idform) {
                var keycode;
                if (window.event)
                    keycode = window.event.keyCode;
                else if (e)
                    keycode = e.which;
                if (keycode + "" === "13") {
                    checknumb(idform);
                }
            }

            function checknumb() {
                
                if (document.getElementById('numb') !== null) {
                    var numb = document.getElementById('numb').value.trim();
                    if (!isPhone(numb, 8, 15)) {
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = "Error! The number entered is incorrect.";
                        return false;
                    }
                }
                
                if(document.getElementById('ex6B').style.display === ''){
                    var posnum = document.getElementById('posnum').value.trim();
                    if(posnum===""){
                        document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                        document.getElementById("errorlarge").style.display = "block";
                        document.getElementById("errorlargetext").innerHTML = "Error! Must be complete field with <span class='font-red'>*</span>.";
                        return false;
                    }
                }
                
                document.getElementById("modalwaitbutton").click();
                
                
            }


            function loadpage() {
                if (document.getElementById('numb') !== null) {
                    document.getElementById('numb').focus();
                }
                changepaymentmode();
                inputvirgola();
            }

        </script>

    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white" onload="return loadpage();" style="height: 800px;">
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
        <div id="modalwait" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title"><i class="fa fa-spinner fa-spin"></i> Processing...</h4>
                    </div>
                    <div class="modal-body">
                        <p>
                            Wait until the operation result... (NOT reload this page)
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div id="modalwaitTEST" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
            <button type="button" data-toggle="modal" data-target="#modalwait" id="modalwaitbutton" style="width: 0px;"></button>
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
                            msg1 = request.getParameter("errmsg");
                        }
                        if (!esito.equals("")) {
                    %>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert <%=classal%>">
                                <strong><%=msg%> <i class="fa <%=classfa%>"></i></strong> <%=Utility.sanitize(msg1)%>
                            </div>
                        </div>
                    </div>
                    <%}

                        String paynew = request.getParameter("paynew");
                        String bra = request.getParameter("bra").trim();
                        String idbra = request.getParameter("idbra");
                        String codtaglio = request.getParameter("codtaglio");
                        String tipolo = request.getParameter("tipolo");
                        String desc = request.getParameter("desc");
                        String tipoprodotto = request.getParameter("tipoprodotto");
                        String tip = tipolo;
                        if (tipolo.equals("0")) {
                            tip = "";
                        }
                        String fil = Engine.getFil()[0];
                        ArrayList<String[]> array_kind_payment = Engine.kind_payment();
                        ArrayList<String[]> array_credit_card = Engine.credit_card(fil);
                    %>
                    <form id="exec_paymat" action="Operazioni?type=exec_paymat" method="post" onsubmit="return checknumb();">

                        <input type="hidden" name="paynew" value="<%=Utility.sanitize(paynew)%>"/>
                        <input type="hidden" name="bra" value="<%=Utility.sanitize(bra)%>"/>
                        <input type="hidden" name="idbra" value="<%=Utility.sanitize(idbra)%>"/>
                        <input type="hidden" name="codtaglio" value="<%=Utility.sanitize(codtaglio)%>"/>
                        <input type="hidden" name="tipolo" value="<%=Utility.sanitize(tipolo)%>"/>
                        <input type="hidden" name="desc" value="<%=Utility.sanitize(desc)%>"/>
                        <input type="hidden" name="tipoprodotto" value="<%=Utility.sanitize(tipoprodotto)%>"/>

                        <div class="modal-header">
                            <h4 class="modal-title">Confirm Transaction</h4>
                        </div>
                        <div class="modal-body">

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        Code: <%=Utility.sanitize(codtaglio)%>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        Brand: <%=Utility.sanitize(bra)%>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        Description: <%=Utility.sanitize(desc) + " " + Utility.sanitize(tip)%>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        Type: <%=Utility.sanitize(tipoprodotto)%>
                                    </div>
                                </div>
                            </div>
                            <%if (tipoprodotto.equals("Carte Internazionali")) {%>
                            <input type="hidden" name="numb" value="-"/>
                            <%} else {%>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Phone Number <span class="font-red">*</span></label>
                                        <div class="input-icon">
                                            <i class="fa fa-phone font-blue"></i>
                                            <input class="form-control" type="text" autocomplete="off" onkeydown="return submitonEnter(document.getElementById('exec_paymat'));"
                                                   name="numb" id="numb"> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%}%>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Payment mode <span class="font-red">*</span></label>
                                        <select class="form-control select2" id="kind_1" name="kind_1" placeholder="..." onchange="return changepaymentmode();">
                                            <%for (int i = 0; i < array_kind_payment.size(); i++) {%>
                                            <option value="<%=array_kind_payment.get(i)[0]%>"><%=array_kind_payment.get(i)[1]%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6" id="ex6">
                                    <label>Pos</label>
                                    <select class="form-control select2" id="pos_1" name="pos_1" placeholder="...">
                                        <%for (int i = 0; i < array_credit_card.size(); i++) {%>
                                        <option value="<%=array_credit_card.get(i)[0]%>"><%=array_credit_card.get(i)[1]%></option>
                                        <%}%>
                                    </select>
                                    <p class='ab'></p>
                                </div>
                                <div class="col-md-6" id="ex6B">
                                    <label>CC Number <span class="font-red">*</span></label>
                                    <input type="text" class="form-control" id="posnum" 
                                           name="posnum" maxlength="4" onkeyup="return fieldOnlyNumber(this.id);"/>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-outline green-jungle"><i class="fa fa-check"></i> Confirm</button>
                        </div>
                    </form>

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
