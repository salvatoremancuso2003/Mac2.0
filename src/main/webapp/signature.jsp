<%@page import="rc.so.util.HtmlEncoder"%>
<%@page import="rc.so.db.Db_Master"%>
<%@page import="rc.so.entity.Client"%>
<%@page import="java.net.InetAddress"%>
<%@page import="rc.so.entity.Ch_transaction_refund"%>
<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.NC_causal"%>
<%@page import="rc.so.entity.NC_transaction"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.entity.Ch_transaction_value"%>
<%@page import="rc.so.entity.Ch_transaction_doc"%>
<%@page import="rc.so.entity.Ch_transaction"%>
<%@page import="rc.so.util.Engine"%>
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
        <title>Mac2.0 - Sign Page</title>
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

        <%
            String cod = null;
            String typesi = null;
            String codtr = null;
            String codcl = null;
            String base64 = null;
            String base64NOMARK = null;
            String token = request.getParameter("token");

            String solofirma = request.getParameter("solofirma");

            if (solofirma == null) {
                solofirma = "NO";
            }

            String oldloy = request.getParameter("oldloy");
            if (oldloy == null) {
                oldloy = "0";
            }

            Ch_transaction ct = null;

            if (token != null) {

                Ch_transaction_doc d1 = Engine.get_tr_doc(token);
                if (d1 != null) {
                    base64 = d1.getContent();
                } else {

                }

                //session.setAttribute("payloadtoken", null);
            } else {
                cod = (String) session.getAttribute("cod");
                base64 = (String) session.getAttribute("payload");
                base64NOMARK = (String) session.getAttribute("payload2");
                typesi = Utility.checkAttribute(session,"typesi");
                codtr = (String) session.getAttribute("codtr");
                codcl = (String) session.getAttribute("codcl");

                String customTAG = "";
                String privacy = "0";
                String aggiungi = "0";
                if (Constant.aggiungifirma) {
                    aggiungi = "1";
                }

                String t1 = "Date:";
                String t2 = "Pay In/Out:";
                String t1_s = "";
                String t2_s = "";
                String ht1 = "";
                String ht2 = "";
                String ht3 = "";

                String v01 = "";
                String v02 = "";
                String v03 = "";
                String v11 = "";
                String v12 = "";
                String v13 = "";
                String v21 = "";
                String v22 = "";
                String v23 = "";
                String v31 = "";
                String v32 = "";
                String v33 = "";
                String v41 = "";
                String v42 = "";
                String v43 = "";

                if (typesi.equals("_macprofcl")) {
                    t1 = "Date:";
                    t2 = "Document:";
                    t1_s = new DateTime().toString(Constant.patternnormdate);
                    t2_s = "K.Y.C. DECLARATION";
                } else if (typesi.equals("_macrefund")) {
                    t1 = "Date:";
                    t2 = "Document:";
                    Ch_transaction ch = Engine.query_transaction_ch(codtr);
                    Ch_transaction_refund ref = Engine.get_refund_trans(ch.getCod());
                    t1_s = Utility.formatStringtoStringDate(ref.getDt_refund(), "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss");
                    t2_s = "Refund Transaction";
                    ht1 = "Total";
                    v01 = Utility.formatMysqltoDisplay(ref.getValue());
                } else if (typesi.equals("_macnctaxf")) {
                    t1 = "Date:";
                    t2 = "Document:";

                    NC_transaction nc = Engine.get_NC_transaction(codtr);
                    if (nc != null) {
                        NC_category ncc = Engine.getNC_category(nc.getGruppo_nc());
                        t1_s = Utility.formatStringtoStringDate(nc.getData(), Constant.patternsqldate, Constant.patternnormdate);;
                        t2_s = "Transaction No Change";
                        ht1 = "Type";
                        ht2 = "Quantity";
                        ht3 = "Total";
                        v01 = ncc.getDe_gruppo_nc();
                        v02 = nc.getRicevuta();
                        v03 = Utility.formatMysqltoDisplay(nc.getQuantita());
                    }

                } else if (typesi.equals("_macautoce")) {
                    t1 = "Date:";
                    t2 = "Document:";
                    t1_s = new DateTime().toString(Constant.patternnormdate);
                    t2_s = "SELF-CERTIFICATION";
                } else {
                    privacy = "1";
                    ct = Engine.query_transaction_ch_temp(codtr);

                    //Db_Master db = new Db_Master();
                    //Client cl = db.query_Client_transaction(ct.getCod(), ct.getCl_cod());
                    //db.closeDB();
                    //String ba1 = HtmlEncoder.getBase86customtagsPEP(cl.getPep().equalsIgnoreCase("YES"));
                    //String ba2 = HtmlEncoder.getBase86customtagsPri();
                    String ba1 = HtmlEncoder.getMaschera2();
                    String ba2 = HtmlEncoder.getMaschera3();
                    t1_s = Utility.formatStringtoStringDate(ct.getData(), Constant.patternsqldate, Constant.patternnormdate);
                    t2_s = Utility.formatMysqltoDisplay(ct.getPay());
                    ht1 = "Currency";
                    ht2 = "Quantity";
                    ht3 = "Rate";
                    customTAG = "{\"Tag\":\"`sigClie\",\"Html\":\"" + ba1 + "\"},{\"Tag\":\"`sigCl2\",\"Html\":\"" + ba2 + "\"}";

                    //codtr = "109171125203519714FhRH29o";
                    //base64 = "109171125203519714FhRH29o";
                    //cod = "109171125203519714FhRH29o";
                    ArrayList<Ch_transaction_value> li = Engine.query_transaction_value(codtr);

                    for (int i = 0; i < li.size(); i++) {
                        if (i == 0) {
                            v01 = li.get(i).getValuta();
                            v02 = Utility.formatMysqltoDisplay(li.get(i).getQuantita());
                            v03 = Utility.formatMysqltoDisplay(li.get(i).getRate());
                        } else if (i == 1) {
                            v11 = li.get(i).getValuta();
                            v12 = Utility.formatMysqltoDisplay(li.get(i).getQuantita());
                            v13 = Utility.formatMysqltoDisplay(li.get(i).getRate());
                        } else if (i == 2) {
                            v21 = li.get(i).getValuta();
                            v22 = Utility.formatMysqltoDisplay(li.get(i).getQuantita());
                            v23 = Utility.formatMysqltoDisplay(li.get(i).getRate());
                        } else if (i == 3) {
                            v31 = li.get(i).getValuta();
                            v32 = Utility.formatMysqltoDisplay(li.get(i).getQuantita());
                            v33 = Utility.formatMysqltoDisplay(li.get(i).getRate());
                        } else if (i == 4) {
                            v41 = li.get(i).getValuta();
                            v42 = Utility.formatMysqltoDisplay(li.get(i).getQuantita());
                            v43 = Utility.formatMysqltoDisplay(li.get(i).getRate());
                        }
                    }
                }

                String fisso2 = Utility.createServerReturnSignLink();
                String fisso_add = Utility.createServerReturnADDLink();
                String signport = Engine.getPath("signport");
                if (signport == null) {
                    signport = "4880";
                }

                signport = signport.trim();

                //signport = "8082";
                if (cod != null) {
                    //session.setAttribute("payload", null);
                    //session.setAttribute("cod", null);
                }

                ArrayList<Ch_transaction_doc> doc = Engine.get_list_tr_doc(codtr);

                String bs64SGN = "";
                if (Engine.formatfromlist_doc(doc, "_signobb") != null) {
                    bs64SGN = Engine.formatfromlist_doc(doc, "_signobb").getContent();
                }

                //MODIFICARE
                //oldloy = "1";

        %>

        <script type="text/javascript">
            function addsign() {
                document.getElementById('sibu').className = "btn blue btn-lg btn-block blue";
                document.getElementById('sibu').innerHTML = "<i class='fa fa-spinner fa-spin'></i> Signature in progress";

                var d1OLD = '{\n\
                            \"Command\":\"SIGN\",\n\
                            \"privacy\":\"<%=privacy%>\",\n\
                            \"aggiungi\":\"<%=aggiungi%>\",\n\
                            \"codtr\":\"<%=codtr%>\",\n\
                            \"codcl\":\"<%=codcl%>\",\n\
                            \"typesign\":\"<%=typesi%>\",\n\
                            \"Token\":\"<%=cod%>\",\n\
                            \"Message\":\"Info utente\",\n\
                            \"Payload\":\"<%=base64%>\",\n\
                            \"CustomTags\":[],\n\
                            \"Signature\":\"<%=bs64SGN%>\",\n\
                            \"splash":{\n\
\"Title1\": \"\",\n\
\"Text1\": \"\",\n\
\"Title2\": \"\",\n\
\"Text2\": \"\",\n\
\"TableHeader":{\n\
\"Col1\": \"\",\n\
\"Col2\": \"\",\n\
\"Col3\": \"\"\n\
},\
\"Rows":[{\n\
\n\
\"Col1\": \"\",\n\
\"Col2\": \"\",\n\
\"Col3\": \"\"\n\
\},{\n\
\n\
\"Col1\": \"\",\n\
\"Col2\": \"\",\n\
\"Col3\": \"\"\n\
\},{\n\
\n\
\"Col1\": \"\",\n\
\"Col2\": \"\",\n\
\"Col3\": \"\"\n\
\},{\n\
\n\
\"Col1\": \"\",\n\
\"Col2\": \"\",\n\
\"Col3\": \"\"\n\
\},{\n\
\n\
\"Col1\": \"\",\n\
\"Col2\": \"\",\n\
\"Col3\": \"\"\n\
\}]\n\
},\
                            \"CallbackUrl\":\"<%=fisso_add%>",\n\
                            \"Response\":\"\"}';

                var d1 =
                        '{\n\
                            \"Command\":\"SIGN\",\n\
                            \"aggiungi\":\"<%=aggiungi%>\",\n\
                            \"privacy\":\"<%=privacy%>\",\n\
                            \"codtr\":\"<%=codtr%>\",\n\
                            \"oldclient\":\"<%=Utility.sanitize(oldloy)%>\",\n\
                            \"codcl\":\"<%=codcl%>\",\n\
                            \"typesign\":\"<%=typesi%>\",\n\
                            \"Token\":\"<%=cod%>\",\n\
                            \"Message\":\"Info utente\",\n\
                            \"Payload\":\"<%=base64%>\",\n\
                            \"Payload2\":\"<%=base64NOMARK%>\",\n\
                            \"CustomTags\":[<%=customTAG%>],\n\
                            \"Signature\":\"<%=bs64SGN%>\",\n\
                            \"splash":{\n\
\"Title1\": \"<%=t1%>\",\n\
\"Text1\": \"<%=t1_s%>\",\n\
\"Title2\": \"<%=t2%>\",\n\
\"Text2\": \"<%=t2_s%>\",\n\
\"TableHeader":{\n\
\"Col1\": \"<%=ht1%>\",\n\
\"Col2\": \"<%=ht2%>\",\n\
\"Col3\": \"<%=ht3%>\"\n\
},\
\"Rows":[{\n\
\n\
\"Col1\": \"<%=v01%>\",\n\
\"Col2\": \"<%=v02%>\",\n\
\"Col3\": \"<%=v03%>\"\n\
\},{\n\
\n\
\"Col1\": \"<%=v11%>\",\n\
\"Col2\": \"<%=v12%>\",\n\
\"Col3\": \"<%=v13%>\"\n\
\},{\n\
\n\
\"Col1\": \"<%=v21%>\",\n\
\"Col2\": \"<%=v22%>\",\n\
\"Col3\": \"<%=v23%>\"\n\
\},{\n\
\n\
\"Col1\": \"<%=v31%>\",\n\
\"Col2\": \"<%=v32%>\",\n\
\"Col3\": \"<%=v33%>\"\n\
\},{\n\
\n\
\"Col1\": \"<%=v41%>\",\n\
\"Col2\": \"<%=v42%>\",\n\
\"Col3\": \"<%=v43%>\"\n\
\}]\n\
},\
                            \"CallbackUrl\":\"<%=fisso_add%>",\n\
                            \"Response\":\"\"}';


                $.ajax({
                    async: true,
                    url: 'http://localhost:<%=signport%>/',
                    dataType: 'text',
                    type: 'post',
                    contentType: 'application/json',
                    data: d1,

                    success: function (data, textStatus, jQxhr) {

                        console.info(data);
                        console.info(textStatus);
                        console.info(jQxhr);

                        if (data === "OKR") {
                            document.getElementById('sibu').className = "btn blue btn-lg btn-block green-jungle";
                            document.getElementById('sibu').innerHTML = "<i class='fa fa-pencil-square'></i> Sign Document";
                            document.getElementById('errorlargetext').innerHTML = "Customer has been rejected the transaction. Please close this page and delete this transaction.";
                            document.getElementById('errorbutton').click();
                        } else if (data === "ERR") {
                            document.getElementById('sibu').className = "btn blue btn-lg btn-block green-jungle";
                            document.getElementById('sibu').innerHTML = "<i class='fa fa-pencil-square'></i> Sign Document";
                            document.getElementById('errorlargetext').innerHTML = "Error during signature. Check your signature tab and try again";
                            document.getElementById('errorbutton').click();
                        } else {
                            window.location.href = "signature.jsp?token=<%=cod%>";
                        }
                    }
                    ,
                    error: function (jqXhr, textStatus, errorThrown) {

                        console.error("RAF: " + this.data);
                        console.error("RAF: " + errorThrown);
                        console.error("RAF: " + textStatus);
                        console.error("RAF: " + jqXhr);
                        var msg = errorThrown + "";
                        if (textStatus.trim().indexOf("error") !== -1) {
                            msg = "Application 'WacomFLTray' has been stopped. Please start this application before proceeding.";
                        }
                        if (msg.trim().indexOf("Failed to load") !== -1) {
                            msg = "Application 'WacomFLTray' has been stopped. Please start this application before proceeding.";
                        }
                        document.getElementById('sibu').className = "btn blue btn-lg btn-block green-jungle";
                        document.getElementById('sibu').innerHTML = "<i class='fa fa-pencil-square'></i> Sign Document";
                        document.getElementById('errorlargetext').innerHTML = msg;
                        document.getElementById('errorbutton').click();
                    }
                });
            }

            function sign() {
                document.getElementById('sibu').className = "btn blue btn-lg btn-block blue";
                document.getElementById('sibu').innerHTML = "<i class='fa fa-spinner fa-spin'></i> Signature in progress";
                //var myHeaders = new Headers();
                // myHeaders.header("Access-Control-Allow-Origin", "*");
                //  myHeaders.header("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
                //    myHeaders.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");

                var d2 = '{\n\
                            \"Command\":\"SIGN\",\n\
                            \"aggiungi\":\"<%=aggiungi%>\",\n\
                            \"privacy\":\"<%=privacy%>\",\n\
                            \"codtr\":\"<%=codtr%>\",\n\
                            \"oldclient\":\"<%=Utility.sanitize(oldloy)%>\",\n\
                            \"codcl\":\"<%=codcl%>\",\n\
                            \"typesign\":\"<%=typesi%>\",\n\
                            \"Token\":\"<%=cod%>\",\n\
                            \"Message\":\"Info utente\",\n\
                            \"Payload\":\"<%=base64%>\",\n\
                            \"Payload2\":\"<%=base64NOMARK%>\",\n\
                            \"CustomTags\":[<%=customTAG%>],\n\
                            \"Signature\":\"\",\n\
                            \"splash":{\n\
\"Title1\": \"<%=t1%>\",\n\
\"Text1\": \"<%=t1_s%>\",\n\
\"Title2\": \"<%=t2%>\",\n\
\"Text2\": \"<%=t2_s%>\",\n\
\"TableHeader":{\n\
\"Col1\": \"<%=ht1%>\",\n\
\"Col2\": \"<%=ht2%>\",\n\
\"Col3\": \"<%=ht3%>\"\n\
},\
\"Rows":[{\n\
\n\
\"Col1\": \"<%=v01%>\",\n\
\"Col2\": \"<%=v02%>\",\n\
\"Col3\": \"<%=v03%>\"\n\
\},{\n\
\n\
\"Col1\": \"<%=v11%>\",\n\
\"Col2\": \"<%=v12%>\",\n\
\"Col3\": \"<%=v13%>\"\n\
\},{\n\
\n\
\"Col1\": \"<%=v21%>\",\n\
\"Col2\": \"<%=v22%>\",\n\
\"Col3\": \"<%=v23%>\"\n\
\},{\n\
\n\
\"Col1\": \"<%=v31%>\",\n\
\"Col2\": \"<%=v32%>\",\n\
\"Col3\": \"<%=v33%>\"\n\
\},{\n\
\n\
\"Col1\": \"<%=v41%>\",\n\
\"Col2\": \"<%=v42%>\",\n\
\"Col3\": \"<%=v43%>\"\n\
\}]\n\
},\
                            \"CallbackUrl\":\"<%=fisso2%>",\n\
                            \"Response\":\"\"}';
                //console.log(d2);


                $.ajax({
                    async: true,
//                    crossDomain: true,
//                    accepts: '*',
                    url: 'http://localhost:<%=signport%>/',
                    dataType: 'text',
                    type: 'post',
//                    headers: myHeaders,
                    contentType: 'application/json',
                    data: d2,

                    success: function (data, textStatus, jQxhr) {

                        console.info(data);
                        console.info(textStatus);
                        console.info(jQxhr);

                        if (data === "OKR") {
                            document.getElementById('sibu').className = "btn blue btn-lg btn-block green-jungle";
                            document.getElementById('sibu').innerHTML = "<i class='fa fa-pencil-square'></i> Sign Document";
                            document.getElementById('errorlargetext').innerHTML = "Customer has been rejected the transaction. Please close this page and delete this transaction.";
                            document.getElementById('errorbutton').click();
                        } else if (data === "ERR") {
                            document.getElementById('sibu').className = "btn blue btn-lg btn-block green-jungle";
                            document.getElementById('sibu').innerHTML = "<i class='fa fa-pencil-square'></i> Sign Document";
                            document.getElementById('errorlargetext').innerHTML = "Error during signature. Check your signature tab and try again";
                            document.getElementById('errorbutton').click();
                        } else {
                            window.location.href = "signature.jsp?token=<%=cod%>";
                        }
                    }
                    ,
                    error: function (jqXhr, textStatus, errorThrown) {
                        console.error("RAF: " + errorThrown);
                        console.error("RAF: " + textStatus);
                        console.error("RAF: " + jqXhr);

                        var msg = errorThrown + "";

                        if (textStatus.trim().indexOf("error") !== -1) {
                            msg = "Application 'WacomFLTray' has been stopped. Please start this application before proceeding.";
                        }

                        if (msg.trim().indexOf("Failed to load") !== -1) {
                            msg = "Application 'WacomFLTray' has been stopped. Please start this application before proceeding.";
                        }

                        document.getElementById('sibu').className = "btn blue btn-lg btn-block green-jungle";
                        document.getElementById('sibu').innerHTML = "<i class='fa fa-pencil-square'></i> Sign Document";

                        document.getElementById('errorlargetext').innerHTML = msg;
                        document.getElementById('errorbutton').click();

                    }
                });
            }
        </script>

        <%}%>
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

                    <div class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                        <button id="okbutton" type="button" class="btn btn-info btn-lg"  data-toggle="modal" data-backdrop="static" 
                                data-keyboard="false" data-target="#modalok">Open Modal</button>
                    </div>
                    <div class="modal fade bs-modal-lg" id="modalok" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title font-green-jungle uppercase"><b>Sign Complete <i class="fa fa-check-circle"></i></b></h4>
                                </div>
                                <div class="modal-body">Signature has been complete. Please close this page and go back to the previous operation.</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn dark btn-outline" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
                    </div>
                    <div class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                        <button id="errorbutton" type="button" class="btn btn-info btn-lg"  data-toggle="modal" data-backdrop="static" 
                                data-keyboard="false" data-target="#errorlarge">Open Modal</button>
                    </div>
                    <div class="modal fade bs-modal-lg" id="errorlarge" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title font-red uppercase"><b>Error message <i class="fa fa-remove"></i></b></h4>
                                </div>
                                <div class="modal-body" id="errorlargetext">descrizione errore dal processo di firma</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn dark btn-outline" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
                    </div>

                    <div class="modal fade bs-modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
                        <button id="waitingsignbutton" type="button" class="btn btn-info btn-lg"  data-toggle="modal" data-backdrop="static" 
                                data-keyboard="false" data-target="#waitingsign">Open Modal</button>
                    </div>
                    <div class="modal fade bs-modal-lg" id="waitingsign" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title font-blue uppercase"><b>document signing in progress. Please wait until the signature is completed.</b></h4>
                                </div>
                                <div class="modal-body"><p></p>
                                    <center><i class="fa fa-3x fa-spinner fa-spin"></i></center>
                                </div>
                                <div class="modal-footer fade">
                                    <button type="button" class="btn dark btn-outline" data-dismiss="modal" id="waitingsignbuttonclose">Close</button>
                                </div>
                            </div>
                            <!-- /.modal-content -->
                        </div>
                        <!-- /.modal-dialog -->
                    </div>




                    <%
                        if (token != null) {

                            if (base64 == null) {%>

                    <script type="text/javascript">
                        document.getElementById('waitingsignbuttonclose').click();
                        document.getElementById('errorlargetext').innerHTML = "Customer has been rejected the transaction. Please close this page and delete this transaction.";
                        document.getElementById('errorbutton').click();
                    </script>

                    <%} else {%>
                    <div class="row">
                        <div class="portlet light">
                            <div class="portlet-title">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="alert alert-success">Signature has been complete. Please close this page and go back to the previous operation.</div>
                                    </div>
                                </div>
                            </div>
                            <%if (false) {%>
                            <div class="portlet-body">
                                <div class="row">

                                    <div class="col-md-12">
                                        <h4 class="font-blue"><b>Signed File:</b></h4>
                                    </div>
                                    <div class="col-md-12">

                                        <iframe src="data:application/pdf;base64,<%=base64%>" 
                                                style="height:800px;width:100%;">
                                        </iframe>
                                    </div>

                                </div>
                            </div>
                            <%}%>

                        </div>
                    </div>
                    <%}
                    } else {

                        if (cod == null || base64 == null) {

                        } else {
                    %>
                    <div class="row">
                        <div class="portlet light">
                            <div class="portlet-title">
                                <div class="row">
                                    <%if (solofirma.equals("TRUE")) {%>
                                    <div class="col-md-12">
                                        <button type="button" id="sibu" class="btn blue btn-lg btn-block green-jungle" onclick="return addsign();">
                                            <i class="fa fa-plus" ></i> Add Sign Customer</button>
                                    </div>
                                    <%} else {%>
                                    <div class="col-md-12">

                                        <button type="button" id="sibu" class="btn blue btn-lg btn-block green-jungle" onclick="return sign();">
                                            <i class="fa fa-pencil-square" ></i> Sign Document</button>
                                    </div>
                                    <%}%>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <div class="row">
                                    <div class="col-md-12">

                                        <iframe src="data:application/pdf;base64,<%=Utility.sanitize(base64)%>" 
                                                style="height:800px;width:100%;">
                                        </iframe>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <%}%>
                    <%}%>



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