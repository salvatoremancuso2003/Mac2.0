<%@page import="rc.so.entity.NC_category"%>
<%@page import="rc.so.entity.ET_change"%>
<%@page import="rc.so.entity.Till"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="rc.so.util.Etichette"%>
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
        <title>Mac2.0 - Ext. Tr. NC</title>
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
            String fil[] = Engine.getFil();
            ArrayList<ET_change> array_frombranch = Engine.get_ET_change_frombranch(fil[0], "0", "NC");
            ArrayList<String[]> array_till = Engine.list_till_enabled();
            ArrayList<NC_category> array_nc = Engine.all_nc_category();
            ArrayList<Branch> array_branch = Engine.list_branch_enabled();
            ArrayList<String[]> array_credit_card = Engine.list_bank_pos_enabled();
            String cod = request.getParameter("cod");
            ET_change et = Engine.get_ET_change(cod);
            String desc_safe = Utility.formatAL(et.getTill_from(), array_till, 1);
            String id_oc = et.getIdopen_from();
            String tf = "";
            if (et.getFg_tofrom().equals("T")) {
                tf = "checked";
            }
        %>



        <script src="assets/soop/js/controlli.js" type="text/javascript"></script>
        <script src="assets/soop/js/bignumber.js" type="text/javascript"></script>
        <script src="assets/soop/js/accounting.min.js" type="text/javascript"></script>
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

            var separatordecimal = '<%=Constant.decimal%>';
            var separatorthousand = '<%=Constant.thousand%>';
            function isfrombranch() {
                var f1 = document.getElementById("typeop");
                if (f1 !== null) {
                    var ty = document.getElementById("typeop").value;
                    var b1 = document.getElementById("bankbranch");
                    if (b1 !== null) {
                        var bankbranch = document.getElementById("bankbranch").value;
                        var tofrom = document.getElementById("tofrom").checked;
                        $('#srcing').empty().trigger('change');
                        if (ty === "BR" && !tofrom) {
                            document.getElementById('srcing_div').style.display = '';
            <%for (int j = 0; j < array_frombranch.size(); j++) {
                    ET_change et1 = array_frombranch.get(j);
            %>
                            var conf = "<%=et1.getFiliale()%>";
                            if (bankbranch === conf) {
                                var o = $("<option/>", {value: "<%=array_frombranch.get(j).getCod()%>", text: "<%=array_frombranch.get(j).getId()%>"});
                                $('#srcing').append(o);
                            }
            <%}%>
                            $('#srcing').val($('#srcing option:first-child').val()).trigger('change');
                        } else {
                            document.getElementById('srcing_div').style.display = 'none';
                        }
                    }
                }
                checkoff();
            }

            function changetype() {
                var f1 = document.getElementById("typeop");
                if (f1 !== null) {
                    var ty = document.getElementById("typeop").value;
                    $('#bankbranch').empty().trigger('change');
            <%for (int j = 0; j < array_branch.size(); j++) {

                    if (!array_branch.get(j).getCod().equals(fil[0])) {
            %>
                    var o = $("<option/>", {value: "<%=array_branch.get(j).getCod()%>", text: "<%=array_branch.get(j).getCod()%> - <%=array_branch.get(j).getDe_branch().toUpperCase()%>"});
                                $('#bankbranch').append(o);
            <%}
                }%>

                                $('#bankbranch').val($('#bankbranch option:first-child').val()).trigger('change');
                            }
                            isfrombranch();
                        }

                        function cli(usr) {
                            document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                            document.getElementById("errorlarge").style.display = "block";
                            document.getElementById("errorlargetext").innerHTML = "Warning! Operation not permitted. The operation of the same type is in progress by the operator " + usr + ".<p class='ab'></p> Please wait for the end of this operation.";
                        }

                        function subform(type) {
                            var inputs, index;
                            //var msg = "";
                            inputs = document.getElementById('f2').getElementsByTagName('span');
                            for (index = 0; index < inputs.length; ++index) {
                                if (inputs[index].id !== "") {
                                    if (document.getElementsByName(inputs[index].id)[0] !== null) {
                                        // msg = msg + " - ID: " + inputs[index].id + " - VALUE: " + inputs[index].innerHTML.trim() + "\n";
                                        document.getElementsByName(inputs[index].id)[0].value = inputs[index].innerHTML.trim();
                                    }
                                }
                            }

                            if (type === "D") {
                                document.getElementById('conf').value = 'NO';
                                var motiv1 = document.getElementById('motiv1').value.trim();
                                if (motiv1 === "") {
                                    document.getElementById('motiv1').focus();
                                    return false;
                                }
                            } else {
                                document.getElementById('conf').value = 'YES';
                            }
                            document.getElementById('f2').submit();
                        }

                        function checkoff() {
                            var b1 = false;
                            //var b1 = navigator.onLine;
                            if (b1) {
                                document.getElementById('srcoff').value = "ONLINE";
                                if (document.getElementById('srcing_div_1') !== null) {
                                    document.getElementById('srcing_div_1').style.display = '';
                                }
                                if (document.getElementById('srcing_div_2') !== null) {
                                    document.getElementById('srcing_div_2').style.display = 'none';
                                }
                                if (document.getElementById('srcing_off') !== null) {
                                    document.getElementById('srcing_off').value = '';
                                }
                            } else {
                                document.getElementById('srcoff').value = "OFFLINE";
                                if (document.getElementById('srcing_div_1') !== null) {
                                    document.getElementById('srcing_div_1').style.display = 'none';
                                }
                                if (document.getElementById('srcing_div_2') !== null) {
                                    document.getElementById('srcing_div_2').style.display = '';
                                }
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
                    <div class="row">
                        <div class="col-md-11">
                            <h3 class="page-title">Transaction External Transfer No Change <small><b>View</b></small></h3>
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <img src="assets/soop/img/logocl.png" alt="" class="img-responsive" style="text-align: right;"/> 
                        </div>
                    </div>
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->

                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Code</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getId())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(et.getDt_it(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getUser())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Status</label><p class='ab'></p>
                                <%=et.formatStatus(et.getFg_annullato())%>
                            </div>
                        </div>
                        <div class="clearfix"></div>  
                        <% if (et.getFg_annullato().equals("1")) {%>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Date</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(Utility.formatStringtoStringDate(et.getDel_dt(), Constant.patternsqldate, Constant.patternnormdate))%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Operator</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getDel_user())%>"/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Delete Motivation</label>
                                <input type="text" class="form-control" disabled="disabled" value="<%=Utility.sanitize(et.getDel_motiv())%>"/>
                            </div>
                        </div>
                        <div class="clearfix"></div>  
                        <%}%>
                        <hr>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Safe/Till</label>
                                <input type="text" class="form-control" disabled value="<%=Utility.sanitize(desc_safe)%>">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Id open/close</label>
                                <div class="input-icon">
                                    <i class="fa fa-bookmark font-blue-dark"></i>
                                    <input type="text" class="form-control" disabled value="<%=Utility.sanitize(id_oc)%>">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>To / From</label><p class='ab'></p>
                                <input type="checkbox" <%=tf%> readonly class="make-switch" 
                                       data-on-color="primary" data-off-color="info"
                                       data-on-text="<span class='tabnow'>To</span>" 
                                       data-off-text="<span class='tabnow'>From</span>"
                                       >
                            </div>
                        </div>
                        <div class="clearfix"></div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Type</label>
                                <input type="text" class="form-control" id="typeop" readonly name="typeop"
                                       value="<%=Engine.formatBankBranch(et.getFg_brba())%>">
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label>Branch</label>
                                <input type="text" class="form-control"  readonly  name="bankbranch"
                                       value="<%=Engine.formatBankBranch(et.getCod_dest(), et.getFg_brba(), null, array_branch,array_credit_card)%>"></input>
                            </div>
                        </div>
                        <%if (et.getFg_tofrom().equals("F")) {%>
                        <div class="col-md-3">
                            <div class="form-group"id="srcing_div_2">
                                <label>Source Code (From Branch)</label>
                                <input type="text" class="form-control"  readonly  name="srcing" value="<%=Utility.sanitize(et.getId_in())%>"/>
                            </div>
                        </div>
                        <%}%>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Note</label>
                                <div class="input-icon">
                                    <i class="fa fa-sticky-note-o font-blue"></i>
                                    <input type="text" class="form-control" disabled value="<%=Utility.sanitize(et.getNote())%>"/>
                                </div>
                            </div>
                        </div>  
                    </div>  

                    <%

                        ArrayList<ET_change> val = Engine.get_ET_nochange_value(cod);

                    %>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="portlet box blue-dark">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="fa fa-arrows-v"></i>
                                        <span class="caption-subject">No change</span>
                                    </div>
                                </div>
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="portlet-body">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <table class="table table-bordered" id="sample_1" width="100%">
                                                            <thead>
                                                                <tr>
                                                                    <th class="tabnow">Description</th>
                                                                    <th class="tabnow">Quantity</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for (int i = 0; i < val.size(); i++) {%>
                                                                <tr>
                                                                    <td><%=Utility.sanitize(val.get(i).getNc_causal())%> - 
                                                                        <%=Utility.sanitize(Engine.formatALNC_category(val.get(i).getNc_causal(), array_nc))%>
                                                                    </td>
                                                                    <td>
                                                                        <%=Utility.sanitize(val.get(i).getIp_quantity())%>
                                                                    </td>
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
    </div>
    <div class="page-footer">
        <div class="page-footer-inner"> <%=et_index.getFooter()%></div>
        <div class="scroll-to-top">
            <i class="icon-arrow-up"></i>
        </div>
    </div>
    <!-- END FOOTER -->
    <!-- BEGIN CORE PLUGINS -->



    <script type="text/javascript">
        jQuery(document).ready(function () {
            var dt = function () {
                var e = $("#sample_1");
                e.dataTable({
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
                    columnDefs: [
                        {orderable: !1, targets: [0]},
                        {orderable: !1, targets: [1]}
                    ],
                    scrollX: true,
                    lengthMenu: [[-1], ["All"]],
                    pageLength: -1,
                    order: [],
                    dom: "<'row' <'col-md-12'>><'row'<'col-md-6 col-sm-12'><'col-md-6 col-sm-12'f>r><t>"
                });
            };
            jQuery().dataTable && dt();
        });</script>

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
