<%@page import="rc.so.entity.Booking"%>
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

        <%
            String cod = request.getParameter("cod");
        %>
        <!-- END THEME LAYOUT SCRIPTS -->
        <script src="assets/global/scripts/datatable.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
        <script src="assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
        <script type="text/javascript" src="assets/soop/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/soop/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/soop/js/fancy.js"></script>
        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
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
        <script type="text/javascript">

            function verifybooking(bookingvalue, operation) {
                if ((operation !== "DELETE") && (bookingvalue === null ||
                        bookingvalue === undefined ||
                        bookingvalue === "" ||
                        bookingvalue === "undefined" ||
                        bookingvalue === "null" ||
                        bookingvalue === "none")) {
                    return "OK";
                } else {
                    var out = "KO";
                    $.ajax({
                        async: false,
                        type: "POST",
                        url: "Operazioni_test?type=updatesito&bookingvalue=" + bookingvalue +
                                "&opr=" + operation + "&modified=" + document.getElementById('modified').value +
                                "&cod=" + '<%=Utility.sanitize(cod)%>' +
                                "&pay=" + document.getElementById('pay').value,
                        success: function (data) {
                            out = data;
                        }
                    });
                    return out;
                }
            }

            function blockkey() {
                $('#deltrbutton').prop("disabled", true);
                $('#confbutton').prop("disabled", true);
                $('#deltrbutton').html("<i class='fa fa-spinner fa-spin'></i> Wait! Delete in progress...");
                $('#confbutton').html("<i class='fa fa-spinner fa-spin'></i> Wait! Delete in progress...");
                var esitob = verifybooking(document.getElementById('booking').value, "DELETE");

                //console.log(esitob);
                //return false;

                if (esitob === "OK") {
                    document.getElementById('deltrform').submit();
                } else {
                    $('#deltrbutton').prop("disabled", false);
                    $('#confbutton').prop("disabled", false);
                    $('#deltrbutton').html("<i class='fa fa-remove'></i> Delete Transaction");
                    $('#confbutton').html("<i class='fa fa-check'></i> Confirm");
                    /////////////////////////////////////////////////////////////
                    document.getElementById('errorlargetext').innerHTML = esitob;
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    return false;
                }
            }

            function blockkey2() {
                var esitob = verifybooking(document.getElementById('booking').value, "CONFIRM");
                if (esitob === "OK") {
                    document.getElementById('confform').submit();
                } else {
                    $('#deltrbutton').prop("disabled", false);
                    $('#delbutton2').prop("disabled", false);
                    $('#confbutton').prop("disabled", false);
                    $('#confbutton2').prop("disabled", false);

                    $('#deltrbutton').html("<i class='fa fa-remove'></i> Delete Transaction");
                    $('#confbutton').html("<i class='fa fa-check'></i> Confirm");
                    $('#confbutton2').html("YES");
                    $('#delbutton2').html("NO");

                    document.getElementById('errorlargetext').innerHTML = esitob;
                    document.getElementById('errorlarge').className = document.getElementById('errorlarge').className + " in";
                    document.getElementById('errorlarge').style.display = "block";
                    return false;
                }
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

                        String kycpres = request.getParameter("kycpres");

                        if (kycpres == null) {
                            kycpres = "0";
                        }

                        String oldloy = request.getParameter("oldloy");
                        if (oldloy == null) {
                            oldloy = "0";
                        }

                        String booking = request.getParameter("booking");
                        String channel = "";
                        if (booking == null) {
                            String[] double_check = Engine.internetbooking_ch(cod);
                            if (double_check != null) {
                                channel = double_check[0];
                                if (double_check[0].equals("01") || double_check[0].equals("05")) {
                                    booking = double_check[1];
                                } else {
                                    booking = "none";
                                }
                            } else {
                                booking = "none";
                            }
                        }

                        String modified = request.getParameter("modified");
                        if (modified == null) {
                            modified = "NO";
                        }

                        Engine.insertTR("W", user, "CONFERMA DOCUMENTI PER TRANSAZIONE " + cod + " - BOOKING RIFERIMENTO: " + booking + " -- " + modified);

                        Ch_transaction it = Engine.query_transaction_ch_temp(cod);

                    %>
                    <%if (it != null) {
                            String ty = "";
                            if (it.getTipotr().equals("B")) {
                                ty = "BUY";
                            } else if (it.getTipotr().equals("S")) {
                                ty = "SELL";
                            }

                            ArrayList<Ch_transaction_doc> doc = Engine.get_list_tr_doc(cod);
                            ArrayList<Ch_transaction_value> list_value = Engine.query_transaction_value(cod);
                            Client cl = Engine.query_Client_transaction(cod, it.getCl_cod());
                            ArrayList<Document> docwait = Engine.list_typedoc_tra(it, list_value, cl, kycpres);

                            boolean isbooking = booking != null && !booking.equals("") && !booking.equals("none");
                            if (channel.equals("")) {
                                if (isbooking) {
                                    Booking bo = Engine.get_prenot(booking);
                                    if (bo != null) {
                                        channel = "- Canale: " + Utility.formatAL(bo.getCanale(), Engine.list_internetbooking(), 1);
                                    }
                                }
                            } else {
                                channel = "- Canale: " + Utility.formatAL(channel, Engine.list_internetbooking(), 1);
                            }
                    %>

                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction <%=ty%> <small><b>List required documents</b></small> <%=Utility.sanitize(channel)%></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" height="75px" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <a onclick="return location.reload();"
                               class="btn btn-lg blue-steel btn-block"><i class="fa fa-refresh"></i> Click <b>HERE</b> or press <b>F5</b> to refresh this page!</a>
                        </div>    
                        <div class="col-md-12">
                            <div class="note note-danger">
                                <h3 class="block">Warning! Don't close this page.</h3>
                                <h4> To remove this transaction or to correct the data entered do not close the browser or
                                    click on the back button but click on <b>'Delete Transaction'</b></h4>
                            </div>
                        </div>

                    </div>  


                    <hr>
                    <div class="row">
                        <div class="col-md-12">
                            <h4>
                                The transaction is successful. Before completing it is necessary to fill/sign the following documents:
                            </h4>
                        </div>
                    </div>


                    <div class="row">
                        <%
                            boolean pr = true;
                            if (Constant.is_IT) { %>
                        <div class="col-md-12">
                            <div class="mt-element-list">
                                <div class="mt-list-container list-default ext-1">
                                    <ul>
                                        <%if (docwait.size() > 0) {

                                                String signport = Engine.getPath("signport");
                                                if (signport == null) {
                                                    signport = "4880";
                                                }
                                                signport = signport.trim();

                                                boolean present_docrico = Engine.formatfromlist_doc(doc, "_docrico") != null;

                                                boolean present_firmaobbl = Engine.formatfromlist_doc(doc, "_macfaexsee") != null
                                                        || Engine.formatfromlist_doc(doc, "_macfaexseebo") != null
                                                        || Engine.formatfromlist_doc(doc, "_macrecsee") != null;

                                                boolean firmadisponibile = Engine.formatfromlist_doc(doc, "_signobb") != null;

                                                //aggiungere verificafirma
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
                                                    String sign = "target='_blank' class='btn btn-default popovers' href='Pdf?type=" + val.getCodice()
                                                            + "&codtr=" + it.getCod()
                                                            + "&oldloy=" + oldloy
                                                            + "&codcl=" + it.getCl_cod() + "'";
                                                    String dicok = "class='btn btn-default popovers fancyBoxRafreload' "
                                                            + "href='transaction_rendiok.jsp?codtr=" + it.getCod() + "+&coddoc=" + val.getCodice() + "'";

                                                    String applicafirma = "target='_blank' class='btn btn-default popovers' href='Pdf?type=" + val.getCodice()
                                                            + "&codtr=" + it.getCod()
                                                            + "&codcl=" + it.getCl_cod()
                                                            + "&solofirma=TRUE'";

                                                    if (val.getCodice().equals("_macautoce")) {
                                                        sign = "target='_blank' class='btn btn-default popovers' href='Pdf?type=_macautoce&dt=" + it.getData() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";
                                                        applicafirma = "target='_blank' class='btn btn-default popovers' href='Pdf?type=_macautoce&dt=" + it.getData() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "&solofirma=TRUE'";

                                                        docdown = "target='_blank' class='btn btn-default popovers' href='Print?down=OK&type=autoc&dt=" + it.getData() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";
                                                    } else if (val.getCodice().equals("_macprofcl")) {
                                                        sign = "target='_blank' class='btn btn-default popovers' href='transaction_profilcomp.jsp?cod=" + it.getCod() + "'";
                                                        applicafirma = "target='_blank' class='btn btn-default popovers' href='transaction_profilcomp.jsp?cod=" + it.getCod() + "&solofirma=TRUE'";
                                                        docdown = "target='_blank' class='btn btn-default popovers' "
                                                                + "href='Print?down=OK&type=mod_prof_client&cod=" + it.getCod() + "'";
                                                    } else if (val.getCodice().equals("_docrico")) {
                                                        sign = "class='btn btn-default popovers' disabled href=''";
                                                        applicafirma = "class='btn btn-default popovers' disabled href=''";
                                                        docdown = "class='btn btn-default popovers' disabled href=''";
                                                        //dicok = "class='btn btn-default popovers disabled href=''";
                                                    }
                                                    String view = "class='btn btn-default popovers' disabled";
                                                    String canc = "class='btn btn-default popovers' disabled";
                                                    String upl = "class='btn btn-default popovers fancyBoxRafreload' href='transaction_upldoc.jsp?codtr=" + it.getCod() + "+&coddoc=" + val.getCodice() + "'";
                                                    if (dc != null) {
                                                        ico = "icon-check";
                                                        pres = "done";
                                                        upl = "class='btn btn-default popovers' disabled";
                                                        sign = "class='btn btn-default popovers' disabled";
                                                        dicok = "class='btn btn-default popovers' disabled";
                                                        view = "class='btn btn-default popovers fancyBoxRaf' href='Download?type=view_doc_tr&cod=" + dc.getCodice_documento() + "'";
                                                        canc = "class='btn btn-default popovers' data-toggle='modal' data-target='#delmodal' id='del_" + val.getCodice() + "' onclick='return confdel(this.id);'";
                                                    } else if (!val.getCodice().equals("_docrico")) {
                                                        pr = false;
                                                    } else if (docwait.get(0).isObbl()) {
                                                        pr = false;
                                                    }

                                                    boolean firmare = false;

                                                    if (Constant.aggiungifirma) {
                                                        firmare = val.getCodice().equals("_macfaexsee")
                                                                || val.getCodice().equals("_macfaexseebo")
                                                                || val.getCodice().equals("_macrecsee");
                                                    } else {
                                                        firmare = true;
                                                    }


                                        %>

                                        <input type="hidden" id="codtr_<%=val.getCodice()%>" value="<%=Utility.sanitize(it.getCod())%>"/>
                                        <input type="hidden" id="coddoc_<%=val.getCodice()%>" value="<%=val.getCodice()%>"/>
                                        <input type="hidden" id="booking" name="booking" value="<%=Utility.sanitize(booking)%>" />
                                        <input type="hidden" id="modified" name="modified" value="<%=Utility.sanitize(modified)%>" />
                                        <input type="hidden" name="pay" id="pay" value="<%=Utility.sanitize(it.getPay())%>" />
                                        <li class="mt-list-item <%=pres%>">
                                            <div class="list-icon-container">
                                                <i class="<%=ico%>"></i>
                                            </div>
                                            <div class="list-datetime"> 
                                                <div class="btn-group btn-group-circle">
                                                    <a <%=Utility.sanitize(docdown)%> container='body' data-trigger='hover' data-container='body' data-placement='top' data-content='Download Document (Blank)'>
                                                        <i class="fa fa-download font-yellow"></i>
                                                    </a>
                                                    <%if (firmare) {%>
                                                    <a <%=Utility.sanitize(sign)%> 
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Sign'>
                                                        <i class="fa fa-pencil font-blue"></i>
                                                    </a>
                                                    <%} else {
                                                        if (present_firmaobbl && firmadisponibile) {

                                                        } else {
                                                            applicafirma = "class='btn btn-default popovers' disabled";
                                                        }

                                                    %>


                                                    <a <%=Utility.sanitize(applicafirma)%> 
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Add Signature'>
                                                        <i class="fa fa-plus font-dark"></i> 
                                                    </a>
                                                    <%}%>
                                                    <%if (val.getCodice().equals("_docrico")) {%>
                                                    <a <%=Utility.sanitize(view)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='View Document'>
                                                        <i class="fa fa-file font-green"></i>
                                                    </a>
                                                    <%}%>
                                                    <a <%=canc%> 
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Cancel Document'>
                                                        <i class="fa fa-window-close font-red-haze"></i></a>
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

                        <%if (Constant.is_CZ) {

                                /*
                            Ch_transaction_doc dc1 = Engine.formatfromlist_doc(doc, "_czbuy");
                            Ch_transaction_doc dc2 = Engine.formatfromlist_doc(doc, "_czsell");
                            if (dc1 == null && dc2 == null) {
                                Engine.insert_CZ_receipt(cod, cl.getCode());
                                doc = Engine.get_list_tr_doc(cod);
                            }
                                 */
                        %>
                        <div class="col-md-12">
                            <div class="mt-element-list">
                                <div class="mt-list-container list-default ext-1">
                                    <ul>
                                        <%if (docwait.size() > 0) {

                                                for (int i = 0; i < docwait.size(); i++) {
                                                    Document val = docwait.get(i);
                                                    Ch_transaction_doc dc = Engine.formatfromlist_doc(doc, val.getCodice());
                                                    String disabled = "";
                                                    String print = "class='btn btn-default popovers' class='btn btn-default popovers' href='Print?down=OK&type="
                                                            + val.getTypesign() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";

                                                    String pres = "";
                                                    String ico = "icon-close font-red";
                                                    String dicok = "class='btn btn-default popovers fancyBoxRafreload' "
                                                            + "href='transaction_rendiok.jsp?codtr=" + it.getCod() + "&coddoc=" + val.getCodice() + "'";

                                                    String view = "class='btn btn-default popovers' disabled";
                                                    String canc = "class='btn btn-default popovers' disabled";
                                                    String upl = "class='btn btn-default popovers fancyBoxRafreload' href='transaction_upldoc.jsp?codtr=" + it.getCod() + "+&coddoc=" + val.getCodice() + "'";
                                                    if (dc != null) {
                                                        ico = "icon-check";
                                                        pres = "done";
                                                        upl = "class='btn btn-default popovers' disabled";
                                                        dicok = "class='btn btn-default popovers' disabled";
                                                        view = "class='btn btn-default popovers fancyBoxRaf' href='Download?type=view_doc_tr&cod=" + dc.getCodice_documento() + "'";
                                                        canc = "class='btn btn-default popovers' data-toggle='modal' data-target='#delmodal' id='del_" + val.getCodice() + "' onclick='return confdel(this.id);'";
                                                    } else {
                                                        if (val.isObbl()) {
                                                            pr = false;
                                                        }
                                                    }
                                                    if (!val.getCodice().equals("_docrico")) {
                                                        view = " target='_blank' class='btn btn-default popovers' class='btn btn-default popovers' href='Print?r=OK&down=OK&type="
                                                                + val.getTypesign() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";

                                                    }

                                        %>

                                        <input type="hidden" id="codtr_<%=val.getCodice()%>" value="<%=Utility.sanitize(it.getCod())%>"/>
                                        <input type="hidden" id="coddoc_<%=val.getCodice()%>" value="<%=val.getCodice()%>"/>
                                        <input type="hidden" id="booking"  name ="booking" value="<%=Utility.sanitize(booking)%>" />
                                        <input type="hidden" id="modified" name="modified" value="<%=Utility.sanitize(modified)%>" />
                                        <input type="hidden" name="pay" id="pay" value="<%=Utility.sanitize(it.getPay())%>" />
                                        <li class="mt-list-item <%=pres%>">
                                            <div class="list-icon-container">
                                                <i class="<%=ico%>"></i>
                                            </div>
                                            <div class="list-datetime"> 
                                                <div class="btn-group btn-group-circle">
                                                    <%if (!val.getCodice().equals("_docrico")) {%>
                                                    <a <%=Utility.sanitize(view)%>
                                                        container='body' data-trigger='hover'
                                                        data-container='body' data-placement='top' data-content='View Document'>
                                                        <i class="fa fa-file font-green"></i>
                                                    </a>
                                                    <%} else {%>
                                                    <a <%=Utility.sanitize(view)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='View Document'>
                                                        <i class="fa fa-file font-green"></i></a>
                                                    <a <%=canc%> 
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Cancel Document'>
                                                        <i class="fa fa-window-close font-red-haze"></i></a>
                                                    <a <%=Utility.sanitize(upl)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Upload Document'>
                                                        <i class="fa fa-upload font-blue-madison"></i></a>
                                                    <a <%=Utility.sanitize(dicok)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Set Status OK'>
                                                        <i class="fa fa-check font-green-jungle"></i></a>
                                                        <%}%>
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
                        <%}
                            }%>
                        <%if (Constant.is_UK) {

                                Ch_transaction_doc dc1 = Engine.formatfromlist_doc(doc, "_receipt");
                                if (dc1 == null) {
                                    Engine.insert_UK_receipt(Utility.sanitize(cod), Utility.sanitize(cl.getCode()));
                                    doc = Engine.get_list_tr_doc(cod);
                                }
                        %>
                        <div class="col-md-12">
                            <div class="mt-element-list">
                                <div class="mt-list-container list-default ext-1">
                                    <ul>
                                        <%if (docwait.size() > 0) {
                                                for (int i = 0; i < docwait.size(); i++) {
                                                    Document val = docwait.get(i);
                                                    Ch_transaction_doc dc = Engine.formatfromlist_doc(doc, val.getCodice());
                                                    String disabled = "";
                                                    String pres = "";
                                                    String ico = "icon-close font-red";
                                                    String print = "class='btn btn-default popovers' disabled";
                                                    String download = "class='btn btn-default popovers' disabled";
                                                    String dicok = "class='btn btn-default popovers fancyBoxRafreload' "
                                                            + "href='transaction_rendiok.jsp?codtr=" + it.getCod() + "+&coddoc="
                                                            + val.getCodice() + "'";
                                                    String view = "class='btn btn-default popovers' disabled";
                                                    String canc = "class='btn btn-default popovers' disabled";
                                                    String upl = "class='btn btn-default popovers fancyBoxRafreload' href='transaction_upldoc.jsp?codtr=" + it.getCod() + "+&coddoc=" + val.getCodice() + "'";
                                                    if (dc != null) {
                                                        ico = "icon-check";
                                                        pres = "done";
                                                        upl = "class='btn btn-default popovers' disabled";
                                                        dicok = "class='btn btn-default popovers' disabled";
                                                        view = "class='btn btn-default popovers fancyBoxRaf' href='Download?type=view_doc_tr&cod=" + dc.getCodice_documento() + "'";
                                                        if (val.getCodice().equals("_receipt")) {
                                                            print = "class='btn btn-default popovers' href='Print?type=printdocUKreceipt&cod=" + dc.getCodice_documento() + "'";
                                                            view = "class='btn btn-default popovers' target='_blank' href='Download?type=view_doc_tr&cod=" + dc.getCodice_documento() + "'";
                                                        } else {
                                                            canc = "class='btn btn-default popovers' data-toggle='modal' data-target='#delmodal' id='del_" + val.getCodice() + "' onclick='return confdel(this.id);'";
                                                        }
                                                    } else {

                                                        if (val.getCodice().equals("_heavy")) {
                                                            download = "target='_blank' class='btn btn-default popovers' href='Print?down=OK&type=" + val.getCodice() + "&codtr=" + it.getCod() + "&codcl=" + it.getCl_cod() + "'";
                                                        }

                                                        if (val.isObbl()) {
                                                            pr = false;
                                                        }
                                                    }


                                        %>

                                        <input type="hidden" id="codtr_<%=val.getCodice()%>" value="<%=Utility.sanitize(it.getCod())%>"/>
                                        <input type="hidden" id="coddoc_<%=val.getCodice()%>" value="<%=val.getCodice()%>"/>
                                        <input type="hidden" id="booking" name="booking" value="<%=Utility.sanitize(booking)%>" />
                                        <input type="hidden" id="modified" name="modified" value="<%=Utility.sanitize(modified)%>" />
                                        <input type="hidden" name="pay" id="pay" value="<%=Utility.sanitize(it.getPay())%>" />
                                        <li class="mt-list-item <%=pres%>">
                                            <div class="list-icon-container">
                                                <i class="<%=ico%>"></i>
                                            </div>
                                            <div class="list-datetime"> 
                                                <div class="btn-group btn-group-circle">
                                                    <%if (!val.getCodice().equals("_receipt")) {%>
                                                    <a <%=Utility.sanitize(download)%> 
                                                        container='body' data-trigger='hover' 
                                                        data-container='body' data-placement='top' 
                                                        data-content='Download Document'>
                                                        <i class="fa fa-download font-dark"></i>
                                                    </a>
                                                    <%}%>



                                                    <a <%=Utility.sanitize(view)%>
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='View Document'>
                                                        <i class="fa fa-file font-green"></i></a>
                                                    <a <%=canc%> 
                                                        container='body' data-trigger='hover' <%=disabled%>
                                                        data-container='body' data-placement='top' data-content='Cancel Document'>
                                                        <i class="fa fa-window-close font-red-haze"></i></a>
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
                        <%}
                            }%>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-md-6">
                            <%if (pr) {%>
                            <button id="confbutton" class="btn btn-lg green-jungle btn-block" data-toggle="modal" data-target="#confmodal"><i class="fa fa-check"></i> Confirm</button>
                            <%} else {%>
                            <a disabled class="btn btn-lg grey-cascade btn-block"><i class="fa fa-check"></i> Confirm</a>
                            <%}%>
                        </div>    
                        <div class="col-md-6">
                            <button type="button" id="deltrbutton"
                                    onclick="return blockkey();" 
                                    class="btn btn-lg red btn-block"><i class="fa fa-remove"></i> Delete Transaction</button>

                        </div>    
                    </div>    
                    <form id="deltrform" method="post" action="Operazioni_test?type=del_ch_tr_buy">
                        <input type="hidden" name="codtr" value="<%=Utility.sanitize(cod)%>"/>
                        <input type="hidden" id="booking" name="booking" value="<%=Utility.sanitize(booking)%>"/>
                        <input type="hidden" id="modified" name="modified" value="<%=Utility.sanitize(modified)%>" />
                        <input type="hidden" name="pay" id="pay" value="<%=Utility.sanitize(it.getPay())%>" />
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
                            <form action="Operazioni_test?type=conf_ch_tr_buy" method="post" id="confform">
                                <input type="hidden" name="codtr"  value="<%=Utility.sanitize(it.getCod())%>" />
                                <input type="hidden" id="booking" name="booking" value="<%=Utility.sanitize(booking)%>"/>
                                <input type="hidden" id="modified" name="modified" value="<%=Utility.sanitize(modified)%>" />
                                <input type="hidden" name="pay" id="pay" value="<%=Utility.sanitize(it.getPay())%>" />
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
                                        <button type="button" onclick="return blockkey2();" id="confbutton2"  class="btn btn-default green-jungle">YES</button>
                                        <button type="button" id="delbutton2" class="btn btn-default red" data-dismiss="modal">NO</button>
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
                <%} else {%>
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-danger">
                            <strong>Error <i class="fa fa-warning"></i></strong> No transaction found.
                        </div>
                    </div>
                </div>
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