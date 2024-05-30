<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.entity.Company_attach"%>
<%@page import="rc.so.entity.Company"%>
<%@page import="rc.so.entity.Currency"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.util.Engine"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String link_value = Engine.verifyUser(request);
    if(link_value!=null){
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
        <%
            ArrayList<String[]> country = Engine.country();
            ArrayList<String[]> district = Engine.district();
            ArrayList<String[]> city = Engine.listcity();
            ArrayList<String[]> docid = Engine.identificationCard();

        %>
        <script type="text/javascript">

            function checkdescr() {
                var descr = document.getElementById("descr").value.trim();
                if (descr === "") {
                    var ermsg = "You must complete fields with <span class='font-red'>*</span>.";
                    document.getElementById("errorlarge").className = document.getElementById("errorlarge").className + " in";
                    document.getElementById("errorlarge").style.display = "block";
                    document.getElementById("errorlargetext").innerHTML = ermsg;
                    return false;
                }
                
                
                
            }


            function changecountry(index, all) {

                if (all === "1") {
                    for (var i = -1; i < 2; i++) {
                        var el = document.getElementById('country' + i);
                        if (el !== null) {
                            var coun = el.value;
                            if (coun !== '<%=Constant.codnaz%>') {
                                document.getElementById('cab_1' + i).style.display = 'none';
                                document.getElementById('dis_1' + i).style.display = 'none';
                                if (document.getElementById('dis_2' + i) !== null) {
                                    document.getElementById('dis_2' + i).style.display = 'none';
                                }
                                document.getElementById('zip_1' + i).style.display = 'none';
                            } else {
                                document.getElementById('cab_1' + i).style.display = '';
                                document.getElementById('dis_1' + i).style.display = '';
                                if (document.getElementById('dis_2' + i) !== null) {
                                    document.getElementById('dis_2' + i).style.display = '';
                                }
                                document.getElementById('zip_1' + i).style.display = '';
                            }

                        }
                    }
                }
                if (all === "0") {
                    var coun = document.getElementById('country' + index).value;
                    if (coun !== '<%=Constant.codnaz%>') {
                        document.getElementById('cab_1' + index).style.display = 'none';
                        document.getElementById('dis_1' + index).style.display = 'none';
                        if (document.getElementById('dis_2' + index) !== null) {
                            document.getElementById('dis_2' + index).style.display = 'none';
                        }
                        document.getElementById('zip_1' + index).style.display = 'none';
                    } else {
                        document.getElementById('cab_1' + index).style.display = '';
                        document.getElementById('dis_1' + index).style.display = '';
                        if (document.getElementById('dis_2' + index) !== null) {
                            document.getElementById('dis_2' + index).style.display = '';
                        }
                        document.getElementById('zip_1' + index).style.display = '';
                    }
                }
            }


            function addcomponent(idadd, ndg) {

                var div1 = document.createElement('div');
                div1.className = 'form-group';

                var div2 = document.createElement('div');
                div2.className = 'col-md-offset-3 col-md-9';

                var div3 = document.createElement('div');
                div3.className = 'fileinput fileinput-new';
                div3.setAttribute('data-provides', 'fileinput');


                var span1 = document.createElement('span');
                span1.className = 'btn btn-outline blue btn-file';

                var span2 = document.createElement('span');
                span2.className = 'fileinput-new';
                span2.innerHTML = ' Select One File ';

                var span3 = document.createElement('span');
                span3.className = 'fileinput-exists';
                span3.innerHTML = ' Change ';

                var input1 = document.createElement('input');
                input1.type = "file";
                input1.name = "file1_" + ndg;

                span1.appendChild(span2);
                span1.appendChild(span3);
                span1.appendChild(input1);

                var span4 = document.createElement('span');
                span4.className = 'fileinput-filename';

                var a1 = document.createElement('a');
                a1.className = 'close fileinput-exists';
                a1.href = 'javascript:;';
                a1.setAttribute('data-dismiss', 'fileinput');

                div3.appendChild(span1);
                div3.appendChild(span4);
                div3.appendChild(document.createTextNode(' \u00A0 '));

                div3.appendChild(a1);

                div2.appendChild(div3);
                div1.appendChild(div2);
                document.getElementById(idadd).appendChild(div1);


            }
            
            function loadpage(){
                changecountry('-1', '1');
                inputvirgola();
            }

        </script>
    </head>
    <!-- END HEAD -->



    <body class="page-full-width page-content-white" style="height: 850px;" onload="return loadpage()">
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
                    <!-- BEGIN PAGE HEADER-->
                    <!-- BEGIN THEME PANEL -->
                    <!--    VUOTO RAF  -->
                    <!-- END THEME PANEL -->
                    <!-- BEGIN PAGE BAR -->
                    <!-- END PAGE BAR -->
                    <!-- BEGIN PAGE TITLE-->
                    <div class="clearfix"></div>
                    <%                        
                        String view = request.getParameter("view");
                        if (view == null) {
                            view = "0";
                        }
                        String esito = request.getParameter("esito");
                        if (esito == null) {
                            esito = "";
                        }
                        
                        
                        
                        
                        String classal = "alert-info";
                        String classfa = "fa-exclamation-triangle";
                        String msg = "Warning";
                        String msg1 = "No operation";
                        if (esito.equals("OK")) {
                            classal = "alert-success";
                            classfa = "fa-check";
                            msg = "Success";
                            msg1 = "Operation completed successfully.";
                        } else if (esito.startsWith("KO")) {
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
                                <strong><%=msg%> <i class="fa <%=Utility.sanitize(classfa)%>"></i></strong> <%=Utility.sanitize(msg1)%>
                            </div>
                        </div>
                    </div>
                    <%}%>








                    <%  Company co = Engine.get_Company(request.getParameter("co_code"));
                        if (co != null) {
                            ArrayList<Company_attach> list_att = Engine.company_listAttachment(co.getNdg(), "0");
                            String sta = "";
                            if (co.getFg_annullato().equals("0")) {
                                sta = "checked";
                            }
                            ArrayList<Company> listAgent = Engine.get_Agent_company(co.getNdg());
                            
                            
                            if(view.equals("1")){%>
                                
                            
                            
                            <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_comp" enctype="multipart/form-data" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-eye font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">View Company</span>
                                        </div>
                                        <div class="tools"> 
                                            
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Company </a>
                                            </li>
                                            <%if (listAgent.size() > 0) {%>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> List Agent </a>
                                            </li>
                                            <%}%>

                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Status</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=Utility.sanitize(sta)%> readonly="readonly"
                                                                   name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" 
                                                                   id="com_code" name="com_code" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(co.getNdg())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="descr" 
                                                                   name="descr" readonly="readonly"
                                                                   onkeypress="return keysub(this, event);" 
                                                                   value="<%=Utility.sanitize(co.getRagione_sociale())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">VAT Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="vat" name="vat" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(co.getCodice_fiscale())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="addr" name="addr" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(co.getIndirizzo())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="zip_1-1">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" readonly="readonly"
                                                                   id="cap" name="cap" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(co.getCap())%>"> 
                                                        </div>
                                                    </div>


                                                    <div class="form-group" id="dis_1-1">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" disabled id="district" name="district" onkeypress="return keysub(this, event);"placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(co.getProvincia())) {%>
                                                                <option value="<%=district.get(i)[0]%>" selected="selected"><%=Utility.sanitize(district.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=Utility.sanitize(district.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="cab_1-1">
                                                        <label class="col-md-3 control-label">CAB - City</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" disabled id="city" name="city" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < city.size(); i++) {%>
                                                                <%if (city.get(i)[0].equals(co.getCab_comune())) {%>
                                                                <option value="<%=Utility.sanitize(city.get(i)[0])%>" selected="selected"><%=Utility.sanitize(city.get(i)[0])%> - <%=Utility.sanitize(city.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(city.get(i)[0])%>"><%=Utility.sanitize(city.get(i)[0])%> - <%=Utility.sanitize(city.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" disabled id="country-1" name="country-1" 
                                                                    onkeypress="return keysub(this, event);"
                                                                    placeholder="..." onchange="return changecountry('-1', '0');">
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <%if (country.get(i)[0].equals(co.getPaese_estero_residenza())) {%>
                                                                <option value="<%=Utility.sanitize(country.get(i)[0])%>" selected="selected"><%=Utility.sanitize(country.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(country.get(i)[0])%>"><%=Utility.sanitize(country.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <hr>

                                                    <div class="form-group">
                                                        <label class="col-md-6 control-label">Attachment 
                                                        </label>
                                                        <div class="clearfix"></div>
                                                        <p class='ab'></p>
                                                        <%if (list_att.size() > 0) {
                                                                for (int i = 0; i < list_att.size(); i++) {

                                                                    Company_attach att = list_att.get(i);

                                                        %>
                                                        <div class="col-md-3"></div>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " value="<%=Utility.sanitize(att.getDescr())%>" disabled="disabled"> 
                                                        </div>
                                                        <div class="col-md-6">
                                                            <a href="Download?type=viewET_company_attach&cod=<%=Utility.sanitize(att.getCod())%>" target="_blank" class="btn btn-sm btn-icon-only green-jungle tooltips" 
                                                               title="Show File"><i class="fa fa-file-o"></i></a>
                                                        </div>
                                                        <%}
                                                        } else {%>
                                                        <div class="col-md-3"></div>
                                                        <div class="col-md-3 font-red">No attachment</div>  
                                                        <%}%>

                                                    </div>

                                                </div>

                                            </div>
                                            <%if (listAgent.size() > 0) {%>        
                                            <div class="tab-pane fade" id="tab_1_2">

                                                <%

                                                    for (int x = 0; x < listAgent.size(); x++) {

                                                        Company ag = listAgent.get(x);
                                                        String ch = "";
                                                        if (ag.getSesso().equals("M")) {
                                                            ch = "checked";
                                                        }
                                                        ArrayList<Company_attach> list_att_ag = Engine.company_listAttachment(ag.getNdg(), "0");

                                                %>

                                                <div class="form-body" id="agent_<%=x%>">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-6">
                                                            <input type="text" class="form-control uppercase" value="<%=Utility.sanitize(ag.getNdg())%>" disabled="disabled"> 
                                                        </div>
                                                        <div class="col-md-3">
                                                            
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Surname</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_surname" name="ag_<%=Utility.sanitize(ag.getNdg())%>_surname" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getCognome())%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Name</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_name" name="ag_<%=Utility.sanitize(ag.getNdg())%>_name" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getNome())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sex</label>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch" <%=Utility.sanitize(ch)%> disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_sex" name="ag_<%=Utility.sanitize(ag.getNdg())%>_sex" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Male</span>" data-off-text="<span class='tabnow'>Female</span>">
                                                        </div>

                                                        <label class="col-md-3 control-label">Tax Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_tax" name="ag_<%=Utility.sanitize(ag.getNdg())%>_tax" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getCodice_fiscale())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_addr" name="ag_<%=Utility.sanitize(ag.getNdg())%>_addr" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getIndirizzo())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="zip_1<%=x%>">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_cap" name="ag_<%=Utility.sanitize(ag.getNdg())%>_cap" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getCap())%>"> 
                                                        </div>
                                                    </div>


                                                    <div class="form-group" id="dis_1<%=x%>">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_district" name="ag_<%=Utility.sanitize(ag.getNdg())%>_district" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(ag.getProvincia())) {%>
                                                                <option value="<%=district.get(i)[0]%>" selected="selected"><%=district.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="cab_1<%=x%>">
                                                        <label class="col-md-3 control-label">CAB - City</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_city" name="ag_<%=Utility.sanitize(ag.getNdg())%>_city" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < city.size(); i++) {%>
                                                                <%if (city.get(i)[0].equals(ag.getCab_comune())) {%>
                                                                <option value="<%=Utility.sanitize(city.get(i)[0])%>" selected="selected"><%=Utility.sanitize(city.get(i)[0])%> - <%=Utility.sanitize(city.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(city.get(i)[0])%>"><%=Utility.sanitize(city.get(i)[0])%> - <%=Utility.sanitize(city.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" disabled id="country<%=x%>" name="ag_<%=Utility.sanitize(ag.getNdg())%>_country" onkeypress="return keysub(this, event);"
                                                                    placeholder="..." onchange="return changecountry('<%=x%>', '0');">
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <%if (country.get(i)[0].equals(ag.getPaese_estero_residenza())) {%>
                                                                <option value="<%=Utility.sanitize(country.get(i)[0])%>" selected="selected"><%=Utility.sanitize(country.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(country.get(i)[0])%>"><%=Utility.sanitize(country.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Date of Birth</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_daofby" name="ag_<%=Utility.sanitize(ag.getNdg())%>_daofby" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(Utility.formatStringtoStringDate(ag.getDt_nascita(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">City of Birth</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_ciofby" name="ag_<%=Utility.sanitize(ag.getNdg())%>_ciofby" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getComune_nascita())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dis_2<%=x%>">
                                                        <label class="col-md-3 control-label">District of Birth</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" disabled id="districtpob<%=x%>" name="ag_<%=Utility.sanitize(ag.getNdg())%>_districtpob" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(ag.getCod_provincia_nascita())) {%>
                                                                <option value="<%=Utility.sanitize(district.get(i)[0])%>" selected="selected"><%=Utility.sanitize(district.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(district.get(i)[0])%>"><%=Utility.sanitize(district.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Document Identity</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_docid" name="ag_<%=Utility.sanitize(ag.getNdg())%>_docid" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < docid.size(); i++) {%>
                                                                <%if (docid.get(i)[0].equals(ag.getTipo_documento())) {%>
                                                                <option value="<%=Utility.sanitize(docid.get(i)[0])%>" selected="selected"><%=Utility.sanitize(docid.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(docid.get(i)[0])%>"><%=Utility.sanitize(docid.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <label class="col-md-3 control-label">Number Doc. Id.</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_numdocid" name="ag_<%=Utility.sanitize(ag.getNdg())%>_numdocid" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getNumero_documento())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Issue Date</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_isdate" name="ag_<%=Utility.sanitize(ag.getNdg())%>_isdate" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(Utility.formatStringtoStringDate(ag.getDt_rilascio(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Expiration Date</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" disabled id="ag_<%=Utility.sanitize(ag.getNdg())%>_exdate" name="ag_<%=Utility.sanitize(ag.getNdg())%>_exdate" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(Utility.formatStringtoStringDate(ag.getDt_scadenza(), "yyyy-MM-dd", "dd/MM/yyyy"))%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Issued By</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control "id="ag_<%=Utility.sanitize(ag.getNdg()Utility.sanitize(%>_issby" disabled name="ag_<%=Utility.sanitize(ag.getNdg())%>_issby" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(ag.getAutorita_rilascio())%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Place of Issue</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " id="ag_<%=Utility.sanitize(ag.getNdg())%>_issplace" disabled name="ag_<%=Utility.sanitize(ag.getNdg())%>_issplace" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(ag.getLuogo_rilascio_documento())%>"> 
                                                        </div>
                                                    </div><hr>
                                                    <div class="form-group">
                                                        <label class="col-md-6 control-label">Attachment
                                                            
                                                        </label>
                                                        <div class="clearfix"></div>
                                                        <p class='ab'></p>
                                                        <%if (list_att_ag.size() > 0) {
                                                                for (int i = 0; i < list_att_ag.size(); i++) {

                                                                    Company_attach att = list_att_ag.get(i);

                                                        %>
                                                        <div class="col-md-3"></div>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " value="<%=Utility.sanitize(att.getDescr())%>" disabled="disabled"> 
                                                        </div>
                                                        <div class="col-md-6">
                                                            <a href="Download?type=viewET_company_attach&cod=<%=Utility.sanitize(att.getCod())%>" target="_blank" class="btn btn-sm btn-icon-only green-jungle tooltips" 
                                                               title="Show File"><i class="fa fa-file-o"></i></a>
                                                        </div>
                                                        <%}
                                                        } else {%>
                                                        <div class="col-md-3"></div>
                                                        <div class="col-md-3 font-red">No attachment</div>  
                                                        <%}%>

                                                    </div>
                                                </div>
                                                <hr class="hrraf">
                                                <%}%>
                                            </div>
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                   <%}else{%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=edit_comp" enctype="multipart/form-data" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-wrench font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">Edit Company</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Company </a>
                                            </li>
                                            <%if (listAgent.size() > 0) {%>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> List Agent </a>
                                            </li>
                                            <%}%>

                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Status</label>
                                                        <div class="col-md-9">
                                                            <input type="checkbox" class="make-switch" <%=Utility.sanitize(sta)%>
                                                                   name="status" id="status" data-size="normal" data-on-color="success" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Enabled</span>" data-off-text="<span class='tabnow'>Disabled</span>">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" 
                                                                   id="com_code" name="com_code" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(co.getNdg())%>" readonly="readonly"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description <span class="font-red">*</span></label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="descr" name="descr"
                                                                   onkeypress="return keysub(this, event);" 
                                                                   onkeyup="return modificaOAM(this,event);" maxlength="70"
                                                                   value="<%=Utility.sanitize(co.getRagione_sociale())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">VAT Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="vat" name="vat" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(co.getCodice_fiscale())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="addr" name="addr" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.sanitize(co.getIndirizzo())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="zip_1-1">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="cap" name="cap" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(co.getCap())%>"> 
                                                        </div>
                                                    </div>


                                                    <div class="form-group" id="dis_1-1">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" id="district" name="district" onkeypress="return keysub(this, event);"placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(co.getProvincia())) {%>
                                                                <option value="<%=Utility.sanitize(district.get(i)[0])%>" selected="selected"><%=Utility.sanitize(district.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(district.get(i)[0])%>"><%=Utility.sanitize(district.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="cab_1-1">
                                                        <label class="col-md-3 control-label">CAB - City</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" id="city" name="city" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < city.size(); i++) {%>
                                                                <%if (city.get(i)[0].equals(co.getCab_comune())) {%>
                                                                <option value="<%=Utility.sanitize(city.get(i)[0])%>" selected="selected"><%=Utility.sanitize(city.get(i)[0])%> - <%=Utility.sanitize(city.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(city.get(i)[0])%>"><%=Utility.sanitize(city.get(i)[0])%> - <%=Utility.sanitize(city.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" id="country-1" name="country-1" 
                                                                    onkeypress="return keysub(this, event);"
                                                                    placeholder="..." onchange="return changecountry('-1', '0');">
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <%if (country.get(i)[0].equals(co.getPaese_estero_residenza())) {%>
                                                                <option value="<%=Utility.sanitize(country.get(i)[0])%>" selected="selected"><%=Utility.sanitize(country.get(i)[1])%></option>
                                                                <%} else {%>
                                                                <option value="<%=Utility.sanitize(country.get(i)[0])%>"><%=Utility.sanitize(country.get(i)[1])%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <hr>

                                                    <div class="form-group">
                                                        <label class="col-md-6 control-label">Attachment 
                                                            <i class="fa fa-plus-circle font-blue tooltips"
                                                               title="Click to upload another file"
                                                               onclick="return addcomponent('tab_1_1', '<%=Utility.sanitize(co.getNdg())%>');"></i>
                                                        </label>
                                                        <div class="clearfix"></div>
                                                        <p class='ab'></p>
                                                        <%if (list_att.size() > 0) {
                                                                for (int i = 0; i < list_att.size(); i++) {

                                                                    Company_attach att = list_att.get(i);

                                                        %>
                                                        <div class="col-md-3"></div>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " value="<%=Utility.sanitize(att.getDescr())%>" disabled="disabled"> 
                                                        </div>
                                                        <div class="col-md-6">
                                                            <a href="Download?type=viewET_company_attach&cod=<%=Utility.sanitize(att.getCod())%>" target="_blank" class="btn btn-sm btn-icon-only green-jungle tooltips" 
                                                               title="Show File"><i class="fa fa-file-o"></i></a>
                                                            <a href="Edit?type=delete_company_attach&co_code=<%=Utility.sanitize(co.getNdg())%>&cod=<%=Utility.sanitize(att.getCod())%>" class="btn btn-sm btn-icon-only red tooltips"title="Delete">
                                                                <i class="fa fa-trash"></i></a>
                                                        </div>
                                                        <%}
                                                        } else {%>
                                                        <div class="col-md-3"></div>
                                                        <div class="col-md-3 font-red">No attachment</div>  
                                                        <%}%>

                                                    </div>

                                                </div>

                                            </div>
                                            <%if (listAgent.size() > 0) {%>        
                                            <div class="tab-pane fade" id="tab_1_2">

                                                <%

                                                    for (int x = 0; x < listAgent.size(); x++) {

                                                        Company ag = listAgent.get(x);
                                                        String ch = "";
                                                        if (ag.getSesso().equals("M")) {
                                                            ch = "checked";
                                                        }
                                                        ArrayList<Company_attach> list_att_ag = Engine.company_listAttachment(ag.getNdg(), "0");

                                                %>

                                                <div class="form-body" id="agent_<%=x%>">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-6">
                                                            <input type="text" class="form-control uppercase" value="<%=Utility.sanitize(ag.getNdg())%>" disabled="disabled"> 
                                                        </div>
                                                        <div class="col-md-3">
                                                            <a class="btn btn-outline red" href="Edit?type=delete_agent&co_cod=<%=Utility.sanitize(co.getNdg())%>&cod_ag=<%=Utility.sanitize(ag.getNdg())%>"><i class="fa fa-remove"></i> Delete Agent</a>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Surname</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"  id="ag_<%=Utility.sanitize(ag.getNdg())%>_surname" name="ag_<%=Utility.sanitize(ag.getNdg())%>_surname" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getCognome())%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Name</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"  id="ag_<%=Utility.sanitize(ag.getNdg())%>_name" name="ag_<%=Utility.sanitize(ag.getNdg())%>_name" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getNome())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sex</label>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch" <%=Utility.sanitize(ch)%> id="ag_<%=Utility.sanitize(ag.getNdg())%>_sex" name="ag_<%=Utility.sanitize(ag.getNdg())%>_sex" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Male</span>" data-off-text="<span class='tabnow'>Female</span>">
                                                        </div>

                                                        <label class="col-md-3 control-label">Tax Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_<%=Utility.sanitize(ag.getNdg())%>_tax" name="ag_<%=Utility.sanitize(ag.getNdg())%>_tax" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getCodice_fiscale())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_<%=Utility.sanitize(ag.getNdg())%>_addr" name="ag_<%=Utility.sanitize(ag.getNdg())%>_addr" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getIndirizzo())%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="zip_1<%=x%>">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_<%=Utility.sanitize(ag.getNdg())%>_cap" name="ag_<%=Utility.sanitize(ag.getNdg())%>_cap" onkeypress="return keysub(this, event);" value="<%=Utility.sanitize(ag.getCap())%>"> 
                                                        </div>
                                                    </div>


                                                    <div class="form-group" id="dis_1<%=x%>">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_<%=Utility.sanitize(ag.getNdg())%>_district" name="ag_<%=Utility.sanitize(ag.getNdg())%>_district" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(ag.getProvincia())) {%>
                                                                <option value="<%=district.get(i)[0]%>" selected="selected"><%=district.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="cab_1<%=x%>">
                                                        <label class="col-md-3 control-label">CAB - City</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_<%=ag.getNdg()%>_city" name="ag_<%=ag.getNdg()%>_city" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < city.size(); i++) {%>
                                                                <%if (city.get(i)[0].equals(ag.getCab_comune())) {%>
                                                                <option value="<%=city.get(i)[0]%>" selected="selected"><%=city.get(i)[0]%> - <%=city.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=city.get(i)[0]%>"><%=city.get(i)[0]%> - <%=city.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="country<%=x%>" name="ag_<%=ag.getNdg()%>_country" onkeypress="return keysub(this, event);"
                                                                    placeholder="..." onchange="return changecountry('<%=x%>', '0');">
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <%if (country.get(i)[0].equals(ag.getPaese_estero_residenza())) {%>
                                                                <option value="<%=country.get(i)[0]%>" selected="selected"><%=country.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Date of Birth</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_<%=ag.getNdg()%>_daofby" name="ag_<%=ag.getNdg()%>_daofby" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.formatStringtoStringDate(ag.getDt_nascita(), "yyyy-MM-dd", "dd/MM/yyyy")%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">City of Birth</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_<%=ag.getNdg()%>_ciofby" name="ag_<%=ag.getNdg()%>_ciofby" onkeypress="return keysub(this, event);" value="<%=ag.getComune_nascita()%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dis_2<%=x%>">
                                                        <label class="col-md-3 control-label">District of Birth</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="districtpob<%=x%>" name="ag_<%=ag.getNdg()%>_districtpob" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <%if (district.get(i)[0].equals(ag.getCod_provincia_nascita())) {%>
                                                                <option value="<%=district.get(i)[0]%>" selected="selected"><%=district.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Document Identity</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_<%=ag.getNdg()%>_docid" name="ag_<%=ag.getNdg()%>_docid" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < docid.size(); i++) {%>
                                                                <%if (docid.get(i)[0].equals(ag.getTipo_documento())) {%>
                                                                <option value="<%=docid.get(i)[0]%>" selected="selected"><%=docid.get(i)[1]%></option>
                                                                <%} else {%>
                                                                <option value="<%=docid.get(i)[0]%>"><%=docid.get(i)[1]%></option>
                                                                <%}%>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <label class="col-md-3 control-label">Number Doc. Id.</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_<%=ag.getNdg()%>_numdocid" name="ag_<%=ag.getNdg()%>_numdocid" onkeypress="return keysub(this, event);" value="<%=ag.getNumero_documento()%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Issue Date</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_<%=ag.getNdg()%>_isdate" name="ag_<%=ag.getNdg()%>_isdate" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.formatStringtoStringDate(ag.getDt_rilascio(), "yyyy-MM-dd", "dd/MM/yyyy")%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Expiration Date</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_<%=ag.getNdg()%>_exdate" name="ag_<%=ag.getNdg()%>_exdate" onkeypress="return keysub(this, event);"
                                                                   value="<%=Utility.formatStringtoStringDate(ag.getDt_scadenza(), "yyyy-MM-dd", "dd/MM/yyyy")%>"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Issued By</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control "id="ag_<%=ag.getNdg()%>_issby" 
                                                                   name="ag_<%=ag.getNdg()%>_issby" onkeypress="return keysub(this, event);"
                                                                   value="<%=ag.getAutorita_rilascio()%>"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Place of Issue</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " id="ag_<%=ag.getNdg()%>_issplace" name="ag_<%=ag.getNdg()%>_issplace" onkeypress="return keysub(this, event);"
                                                                   value="<%=ag.getLuogo_rilascio_documento()%>"> 
                                                        </div>
                                                    </div><hr>
                                                    <div class="form-group">
                                                        <label class="col-md-6 control-label">Attachment
                                                            <i class="fa fa-plus-circle font-blue tooltips"
                                                               title="Click to upload another file" onclick="return addcomponent('agent_<%=x%>', '<%=ag.getNdg()%>');"></i>
                                                        </label>
                                                        <div class="clearfix"></div>
                                                        <p class='ab'></p>
                                                        <%if (list_att_ag.size() > 0) {
                                                                for (int i = 0; i < list_att_ag.size(); i++) {

                                                                    Company_attach att = list_att_ag.get(i);

                                                        %>
                                                        <div class="col-md-3"></div>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " value="<%=att.getDescr()%>" disabled="disabled"> 
                                                        </div>
                                                        <div class="col-md-6">
                                                            <a href="Download?type=viewET_company_attach&cod=<%=att.getCod()%>" target="_blank" class="btn btn-sm btn-icon-only green-jungle tooltips" 
                                                               title="Show File"><i class="fa fa-file-o"></i></a>
                                                            <a href="Edit?type=delete_company_attach&co_code=<%=co.getNdg()%>&cod=<%=att.getCod()%>" class="btn btn-sm btn-icon-only red tooltips"title="Delete">
                                                                <i class="fa fa-trash"></i></a>
                                                        </div>
                                                        <%}
                                                        } else {%>
                                                        <div class="col-md-3"></div>
                                                        <div class="col-md-3 font-red">No attachment</div>  
                                                        <%}%>

                                                    </div>
                                                </div>
                                                <hr class="hrraf">
                                                <%}%>
                                            </div>
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%}} else {%>
                    <form class="form-horizontal" role="form" name="f1" method="post" action="Edit?type=ins_comp" onsubmit="return checkdescr();">
                        <div class="row">
                            <div class="col-md-12">
                                <!-- BEGIN PORTLET-->
                                <div class="portlet light bordered">
                                    <div class="portlet-title">
                                        <div class="caption">
                                            <i class="icon-plus font-blue"></i>
                                            <span class="caption-subject font-blue bold uppercase">New Company</span>
                                        </div>
                                        <div class="tools"> 
                                            <button type="submit" class="btn btn-outline blue"><i class="fa fa-save"></i> Save changes</button>
                                        </div>
                                    </div>
                                    <div class="portlet-body">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab"> Company </a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab"> Agent </a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane fade active in" id="tab_1_1">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" disabled="disabled"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Description <span class="font-red">*</span></label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" 
                                                                   id="descr" name="descr"
                                                                   onkeyup="return modificaOAM(this,event);" maxlength="70"
                                                                   > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">VAT Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="vat" name="vat" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="addr" name="addr" onkeypress="return keysub(this, event);" maxlength="100"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="zip_1-1">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="cap" name="cap" onkeypress="return keysub(this, event);" maxlength="10"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dis_1-1">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" id="district" name="district"  onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[0]%> - <%=district.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="cab_1-1">
                                                        <label class="col-md-3 control-label">CAB - City</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" id="city" name="city" onkeypress="return keysub(this, event);"  placeholder="...">
                                                                <%for (int i = 0; i < city.size(); i++) {%>
                                                                <option value="<%=city.get(i)[0]%>"><%=city.get(i)[0]%> - <%=city.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-9">
                                                            <select class="form-control select2" id="country-1" name="country" onkeypress="return keysub(this, event);"  placeholder="..." onchange="return changecountry('-1', '0');">
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="tab-pane fade" id="tab_1_2">
                                                <div class="form-body">
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Code</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control uppercase" disabled="disabled"> 
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Surname</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_surname" name="ag_surname" onkeypress="return keysub(this, event);"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">Name</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_name" name="ag_name" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Sex</label>
                                                        <div class="col-md-3">
                                                            <input type="checkbox" class="make-switch" checked id="ag_sex" name="ag_sex" onkeypress="return keysub(this, event);"
                                                                   data-size="normal" data-on-color="primary" data-off-color="danger"
                                                                   data-on-text="<span class='tabnow'>Male</span>" data-off-text="<span class='tabnow'>Female</span>">
                                                        </div>

                                                        <label class="col-md-3 control-label">Tax Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_tax" name="ag_tax" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Address</label>
                                                        <div class="col-md-9">
                                                            <input type="text" class="form-control" id="ag_addr" name="ag_addr" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="zip_11">
                                                        <label class="col-md-3 control-label">Zip Code</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_cap" name="ag_cap" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dis_11">
                                                        <label class="col-md-3 control-label">District</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_district" name="ag_district" onkeypress="return keysub(this, event);"placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>

                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="cab_11">
                                                        <label class="col-md-3 control-label">CAB</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_city" name="ag_city" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < city.size(); i++) {%>
                                                                <option value="<%=city.get(i)[0]%>"><%=city.get(i)[0]%> - <%=city.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Country</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="country1" name="ag_country" onkeypress="return keysub(this, event);" placeholder="..." onchange="return changecountry('1', '0');">
                                                                <%for (int i = 0; i < country.size(); i++) {%>
                                                                <option value="<%=country.get(i)[0]%>"><%=country.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Date of Birth</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_daob" name="ag_daob" onkeypress="return keysub(this, event);"> 
                                                        </div>

                                                        <label class="col-md-3 control-label">City of Birth</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_ciob" name="ag_ciob" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group" id="dis_21">
                                                        <label class="col-md-3 control-label">District of Birth</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_distrob" name="ag_distrob" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < district.size(); i++) {%>
                                                                <option value="<%=district.get(i)[0]%>"><%=district.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Document Identity</label>
                                                        <div class="col-md-3">
                                                            <select class="form-control select2" id="ag_docid" name="ag_docid" onkeypress="return keysub(this, event);" placeholder="...">
                                                                <%for (int i = 0; i < docid.size(); i++) {%>
                                                                <option value="<%=docid.get(i)[0]%>"><%=docid.get(i)[1]%></option>
                                                                <%}%>
                                                            </select>
                                                        </div>
                                                        <label class="col-md-3 control-label">Number Doc. Id.</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="ag_docnum" name="ag_docnum" onkeypress="return keysub(this, event);"> 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Issue Date</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_issda" name="ag_issda" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                        <label class="col-md-3 control-label">Expiration Date</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control date-picker" id="ag_exda" name="ag_exda" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-md-3 control-label">Issued By</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " id="ag_issby" name="ag_issby" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                        <label class="col-md-3 control-label">Place of Issue</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control " id="ag_isspla" name="ag_isspla" onkeypress="return keysub(this, event);" > 
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </form>
                    <%}%>
                    <!-- BEGIN CONTENT -->
                    <!-- END PAGE TITLE-->
                    <!-- END PAGE HEADER-->
                </div>
                <!-- END CONTENT -->
                <!-- BEGIN QUICK SIDEBAR -->
                <!-- END QUICK SIDEBAR -->
            </div>
            <!-- END CONTAINER -->
            <!-- BEGIN FOOTER -->

            <!-- END FOOTER -->
            <!--[if lt IE 9]>
    <script src="../assets/global/plugins/respond.min.js"></script>
    <script src="../assets/global/plugins/excanvas.min.js"></script> 
    <![endif]-->
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

            <input type="text" id="test1" name="test1" style="display: none; width: 1px;"/>
            
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
