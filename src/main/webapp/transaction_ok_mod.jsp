<%@page import="rc.so.entity.Document"%>
<%@page import="rc.so.entity.Client"%>
<%@page import="rc.so.entity.Ch_transaction_value"%>
<%@page import="rc.so.entity.Ch_transaction_doc"%>
<%@page import="rc.so.entity.Ch_transaction"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.IT_change"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.List_ma"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
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
        <title>Mac2.0 - Confirm Tr.</title>
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
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>
        <script type="text/javascript">

            $(document).ready(function () {
                window.history.pushState(null, "", window.location.href);
                window.onpopstate = function () {
                    window.history.pushState(null, "", window.location.href);
                };
            });
        </script>
        <script type="text/javascript">
            function confdel(idval) {
                idval = idval.replace('del_', '');
                document.getElementById('codtr_del').value = document.getElementById('codtr_' + idval).value;
                document.getElementById('coddoc_del').value = document.getElementById('coddoc_' + idval).value;
            }
        </script>
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white">

        <!-- BEGIN HEADER -->

        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <div class="clearfix"> </div>
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->

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
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <%
                        String user = (String) request.getSession().getAttribute("us_cod");
                        String cod = request.getParameter("cod");
                        Ch_transaction it = Engine.query_transaction_ch_temp(cod);

                        if (it != null) {

                            String ty = "";
                            if (it.getTipotr().equals("B")) {
                                ty = "BUY";
                            } else if (it.getTipotr().equals("S")) {
                                ty = "SELL";
                            }

                            ArrayList<Ch_transaction_doc> doc = Engine.get_list_tr_doc(cod);
                            ArrayList<Ch_transaction_value> list_value = Engine.query_transaction_value(cod);
                            Client cl = Engine.query_Client_transaction(cod, it.getCl_cod());
                            ArrayList<Document> docwait = Engine.list_typedoc_tra(it, list_value, cl, "0");

                    %>
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction <%=ty%> <small><b>List required documents</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="alert alert-info">
                                <center><b>The transaction is successful. Before completing it is necessary to fill/sign the following documents:</b></center>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <%
                            boolean pr = true;
                            if (Constant.is_IT) {%>
                        <div class="col-md-12">
                            <div class="mt-element-list">
                                <div class="mt-list-container list-default ext-1">
                                    <ul>
                                        <%if (docwait.size() > 0) {
                                                boolean present_docrico = Engine.formatfromlist_doc(doc, "_docrico") != null;
                                                for (int i = 0; i < docwait.size(); i++) {
                                                    Document val = docwait.get(i);
                                                    Ch_transaction_doc dc = Engine.formatfromlist_doc(doc, val.getCodice());
                                                    String disabled = "";
                                                    //if (!val.getCodice().equals("_docrico") && docwait.get(0).isObbl() && !present_docrico) {
                                                    if (!val.getCodice().equals("_docrico") && docwait.get(0).isObbl() && !present_docrico) {
                                                        //disabled = "disabled";
                                                    }

                                                    String docdown = "target='_blank' class='btn btn-default popovers' href='Print?down=OK&type="
                                                            + val.getTypesign() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";
                                                    String pres = "";
                                                    String ico = "icon-close font-red";
                                                    String sign = "target='_blank' class='btn btn-default popovers' href='Print?type=" + val.getTypesign() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";
                                                    String ver = "class='btn btn-default popovers fancyBoxRafreload' href='Operazioni?type=verify_sign&codtr="
                                                            + it.getCod() + "+&coddoc=" + val.getCodice() + "'";
                                                    String dicok = "class='btn btn-default popovers fancyBoxRafreload' "
                                                            + "href='transaction_rendiok.jsp?codtr=" + it.getCod() + "+&coddoc=" + val.getCodice() + "'";

                                                    if (val.getCodice().equals("_macautoce")) {
                                                        sign = "target='_blank' class='btn btn-default popovers' href='Print?type=autoc&dt=" + it.getData() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";
                                                        docdown = "target='_blank' class='btn btn-default popovers' href='Print?down=OK&type=autoc&dt=" + it.getData() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";
                                                    } else if (val.getCodice().equals("_macprofcl")) {
                                                        sign = "target='_blank' class='btn btn-default popovers' href='transaction_profilcomp.jsp?cod=" + it.getCod() + "'";
                                                        docdown = "target='_blank' class='btn btn-default popovers' href='Print?down=OK&type=mod_prof_client&cod=" + it.getCod() + "'";
                                                    } else if (val.getCodice().equals("_docrico")) {
                                                        sign = "class='btn btn-default popovers' disabled href=''";
                                                        docdown = "class='btn btn-default popovers' disabled href=''";
                                                        ver = "class='btn btn-default popovers' disabled href=''";
                                                        dicok = "class='btn btn-default popovers disabled href=''";
                                                    }
                                                    String view = "class='btn btn-default popovers' disabled";
                                                    String canc = "class='btn btn-default popovers' disabled";
                                                    String upl = "class='btn btn-default popovers fancyBoxRafreload' href='transaction_upldoc.jsp?codtr=" + it.getCod() + "+&coddoc=" + val.getCodice() + "'";
                                                    if (dc != null) {
                                                        ico = "icon-check";
                                                        pres = "done";
                                                        upl = "class='btn btn-default popovers' disabled";
                                                        sign = "class='btn btn-default popovers' disabled";
                                                        ver = "class='btn btn-default popovers' disabled";
                                                        dicok = "class='btn btn-default popovers' disabled";
                                                        view = "class='btn btn-default popovers fancyBoxRaf' href='Download?type=view_doc_tr&cod=" + dc.getCodice_documento() + "'";
                                                        canc = "class='btn btn-default popovers' data-toggle='modal' data-target='#delmodal' id='del_" + val.getCodice() + "' onclick='return confdel(this.id);'";
                                                    } else if (!val.getCodice().equals("_docrico")) {
                                                        pr = false;
                                                    } else if (docwait.get(0).isObbl()) {
                                                        pr = false;
                                                    }
                                        %>

                                        <input type="hidden" id="codtr_<%=val.getCodice()%>" value="<%=Utility.sanitize(it.getCod())%>"/>
                                        <input type="hidden" id="coddoc_<%=val.getCodice()%>" value="<%=val.getCodice()%>"/>
                                        <li class="mt-list-item <%=pres%>">
                                            <div class="list-icon-container">
                                                <i class="<%=ico%>"></i> 
                                            </div>
                                            <div class="list-datetime"> 
                                                <div class="btn-group btn-group-circle">
                                                    <a <%=Utility.sanitize(docdown%> container='body' data-trigger='hover' data-container='body'
                                                                    data-placement='top' data-content='Download Document (Blank)'>
                                                        <i class="fa fa-download font-yellow"></i>
                                                    </a>
                                                    <a <%=Utility.sanitize(sign)%> 
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Sign'>
                                                        <i class="fa fa-pencil font-blue"></i>
                                                    </a>
                                                    <a <%=Utility.sanitize(ver)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Verify Sign'>
                                                        <i class="fa fa-sign-out font-grey-cascade"></i>
                                                    </a>
                                                    <a <%=Utility.sanitize(view)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='View Document'>
                                                        <i class="fa fa-file font-green"></i></a>
                                                    <button <%=canc%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Cancel Document'>
                                                        <i class="fa fa-window-close font-red-haze"></i></button>
                                                    <a <%=Utility.sanitize(upl)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Upload Document'>
                                                        <i class="fa fa-upload font-blue-madison"></i></a>
                                                    <a <%=Utility.sanitize(dicok)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Set Status OK'>
                                                        <i class="fa fa-check font-green-jungle"></i></a>
                                                </div>
                                            </div>
                                            <div class="list-item-content">
                                                <h3 class="uppercase">
                                                    <%=val.getDescrizione()%>
                                                </h3>
                                                <%if (dc == null) {
                                                        if (val.isObbl()) {%>
                                                <p><span class="badge badge-danger">REQUIRED</span></p>
                                                <%} else {%>
                                                <p><span class="badge badge-primary">OPTIONAL</span></p>
                                                <%}
                                                } else {%>
                                                <p>&nbsp;</p>
                                                <%}%>
                                            </div>
                                        </li>
                                        <%}%>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <%} else {
                                    Engine.deleteLinktransaction(user);

                                    Utility.redirect(request, response, "index.jsp");
                                }
                            }%>
                        <%if (Constant.is_CZ) {%>
                        <div class="col-md-6">
                            <ul class="list-group">
                                <div  class="list-group-item"> <i class="fa fa-pencil-square-o"></i> Prericevuta CZ SELL 
                                    <a href="Print?type=scontrino_CZ_1&codtr=<%=Utility.sanitize(it.getCod())%>&codcl=<%=Utility.sanitize(it.getCl_cod())%>" target="_blank" class="btn btn-sm btn-circle green-jungle badge popovers" 
                                       container='body' data-trigger='hover' 
                                       data-container='body' data-placement='left' data-content='View Document'
                                       > <i class="fa fa-file"></i> </a>

                                </div>

                                <div  class="list-group-item"> <i class="fa fa-pencil-square-o"></i> Prericevuta CZ BUY 
                                    <a href="Print?type=scontrino_CZ_2&codtr=<%=Utility.sanitize(it.getCod())%>&codcl=<%=Utility.sanitize(it.getCl_cod())%>" target="_blank" class="btn btn-sm btn-circle green-jungle badge popovers" 
                                       container='body' data-trigger='hover' 
                                       data-container='body' data-placement='left' data-content='View Document'
                                       > <i class="fa fa-file"></i> </a>

                                </div>

                                <div  class="list-group-item"> <i class="fa fa-pencil-square-o"></i> Ricevuta CZ SELL 
                                    <a href="Print?type=scontrino_CZ_3&codtr=<%=Utility.sanitize(it.getCod())%>&codcl=<%=Utility.sanitize(it.getCl_cod())%>" target="_blank" class="btn btn-sm btn-circle green-jungle badge popovers" 
                                       container='body' data-trigger='hover' 
                                       data-container='body' data-placement='left' data-content='View Document'
                                       > <i class="fa fa-file"></i> </a>

                                </div>

                                <div  class="list-group-item"> <i class="fa fa-pencil-square-o"></i> Ricevuta CZ BUY 
                                    <a href="Print?type=scontrino_CZ_4&codtr=<%=Utility.sanitize(it.getCod())%>&codcl=<%=Utility.sanitize(it.getCl_cod())%>" target="_blank" class="btn btn-sm btn-circle green-jungle badge popovers" 
                                       container='body' data-trigger='hover' 
                                       data-container='body' data-placement='left' data-content='View Document'
                                       > <i class="fa fa-file"></i> </a>

                                </div>

                                <div  class="list-group-item"> <i class="fa fa-pencil-square-o"></i> Ricevuta Annullo CZ SELL 
                                    <a href="Print?type=scontrino_CZ_5&codtr=<%=Utility.sanitize(it.getCod())%>&codcl=<%=Utility.sanitize(it.getCl_cod())%>" target="_blank" class="btn btn-sm btn-circle green-jungle badge popovers" 
                                       container='body' data-trigger='hover' 
                                       data-container='body' data-placement='left' data-content='View Document'
                                       > <i class="fa fa-file"></i> </a>

                                </div>

                                <div  class="list-group-item"> <i class="fa fa-pencil-square-o"></i> Ricevuta Annullo CZ BUY 
                                    <a href="Print?type=scontrino_CZ_6&codtr=<%=Utility.sanitize(it.getCod())%>&codcl=<%=Utility.sanitize(it.getCl_cod())%>" target="_blank" class="btn btn-sm btn-circle green-jungle badge popovers" 
                                       container='body' data-trigger='hover' 
                                       data-container='body' data-placement='left' data-content='View Document'
                                       > <i class="fa fa-file"></i> </a>

                                </div>
                            </ul>
                        </div>
                        <%}%>
                        <%if (Constant.is_UK) {%>

                        <div class="col-md-6">
                            <ul class="list-group">
                                <div  class="list-group-item"> <i class="fa fa-pencil-square-o"></i> Scontrino UK 
                                    <a href="Print?type=scontrinoUK&codtr=<%=Utility.sanitize(it.getCod())%>&codcl=<%=Utility.sanitize(it.getCl_cod())%>" target="_blank" class="btn btn-sm btn-circle green-jungle badge popovers" 
                                       container='body' data-trigger='hover' 
                                       data-container='body' data-placement='left' data-content='View Document'
                                       > <i class="fa fa-file"></i> </a>
                                </div>
                            </ul>
                        </div>
                        <%}%>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-md-6">
                            <%if (pr) {%>
                            <button class="btn btn-lg green-jungle btn-block" data-toggle="modal" data-target="#confmodal"><i class="fa fa-check"></i> Confirm</button>
                            <%} else {%>
                            <a disabled class="btn btn-lg grey-cascade btn-block"><i class="fa fa-check"></i> Confirm</a>
                            <%}%>
                        </div>    
                        <div class="col-md-6">
                            <button type="button" onclick="document.getElementById('deltrform').submit();" class="btn btn-lg red btn-block"><i class="fa fa-remove"></i> Delete Transaction</button>
                        </div>    
                    </div>    
                    <form id="deltrform" method="post" action="Operazioni_test?type=del_ch_tr_buy">
                        <input type="hidden" name="codtr" value="<%=Utility.sanitize(cod)%>"/>
                        <input type="hidden" name="codop" value="<%=Utility.sanitize(it.getId_open_till())%>"/>
                    </form>



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
                        } else {
                            classal = "alert-danger";
                            classfa = "fa-exclamation-triangle";
                            msg = "Error";
                            msg1 = "The operation could not be completed.";
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
                    <!-- END CONTENT -->
                    <!-- BEGIN QUICK SIDEBAR -->
                    <!-- END QUICK SIDEBAR -->
                    <div id="confmodal" class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog">
                            <form action="Operazioni_test?type=conf_ch_tr_buy" method="post" >
                                <input type="hidden" name="codtr"  value="<%=Utility.sanitize(it.getCod())%>" />
                                <input type="hidden" name="filiale"  value="<%=Utility.sanitize(it.getFiliale())%>" />
                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                        <h4 class="modal-title font-blue-madison uppercase"><b>Confirm message</b></h4>
                                    </div>
                                    <div class="modal-body">
                                        <b>Are you sure you want to confirm the transaction?</b>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-default green-jungle">YES</button>
                                        <button type="button" class="btn btn-default red" data-dismiss="modal">NO</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div id="delmodal" class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog">
                            <form action="Edit?type=del_tr_doc" method="post" >
                                <input type="hidden" id="codtr_del" name="codtr_del"  value=""  />
                                <input type="hidden" id="coddoc_del" name="coddoc_del" value="" />
                                <!-- Modal content-->
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                        <h4 class="modal-title font-red uppercase"><b>Confirm message</b></h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>Are you sure you want to delete this document?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-default green-jungle">YES</button>
                                        <button type="button" class="btn btn-default red" data-dismiss="modal">NO</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <%} else {
                    Engine.deleteLinktransaction(user);
                    Utility.redirect(request, response, "index.jsp");
                %>
                <%}%>
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