<%@page import="rc.so.util.Constant"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.entity.Branch"%>
<%@page import="java.util.ArrayList"%>
<%@page import="rc.so.db.Db_Master"%>
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
        <title>Mac2.0 - Monitor Branch</title>
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
        <script src="assets/soop/js/pace.js" type="text/javascript"></script>
        
        
        
    </head>
    <!-- END HEAD -->

    <body class="page-full-width page-content-white">
        <!-- BEGIN HEADER -->
        <!-- END HEADER -->
        <!-- BEGIN HEADER & CONTENT DIVIDER -->
        <!-- END HEADER & CONTENT DIVIDER -->
        <!-- BEGIN CONTAINER -->
        <div class="page-container">
            <!-- BEGIN MENU -->
            <!-- END MENU -->
            <%
                response.setIntHeader("Refresh", 120);
                boolean centraleok = true;
                ArrayList<Branch> allbr = Engine.list_branch();
                ArrayList<String[]> listafilialiip = new ArrayList<String[]>();
                Db_Master dbm = new Db_Master();
                String curtime = new DateTime().toString(Constant.patternnormdate);
                if (dbm.getC() == null) {
                    centraleok = false;
                } else {
                    curtime = dbm.getNowDT().toString(Constant.patternnormdate);
                    listafilialiip = dbm.getIpFiliale();
                }

                String col = "blue";
                String stc = "STATUS HEAD OFFICE: ";
                if (centraleok) {
                    col = "green-jungle";
                    stc += "OK";
                } else {
                    col = "red";
                    stc += "KO";
                }

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
                            <h3 class="page-title">Monitor Branch</h3> 
                        </div>
                        <div class="col-md-1" style="text-align: right;">
                            <a href="login.jsp"> <img src="assets/soop/img/logocl.png" alt="Homepage" class="img-responsive" style="text-align: right;"/> </a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <span class="pull-right">Last update: <%=curtime%></span>
                        </div>
                        <div class="col-md-8 col-md-offset-2">
                            <div class="mt-element-list">
                                <div class="mt-list-head list-todo <%=col%>">
                                    <div class="list-head-title-container">
                                        <h3 class="list-title"><%=stc%></h3>

                                    </div>
                                </div>
                                <div class="mt-list-container list-todo">
                                    <div class="list-todo-line"></div>
                                    <ul>

                                        <%
                                            int ok =0;
                                                int wa =0;
                                                int ko =0;
                                            if (!listafilialiip.isEmpty()) {
                                                
                                                int ind = 0;
                                                for (int x = 0; x < listafilialiip.size(); x++) {
                                                    String fil = listafilialiip.get(x)[0];
                                                    if (!fil.equals("000")) {
                                                        ind++;
                                                        boolean generale = false;
                                                        Branch br = Engine.get_Branch(fil, allbr);
                                                        String ip = listafilialiip.get(x)[1];
                                                        Db_Master dbf = new Db_Master(true, ip);
                                                        boolean mysql = false;
                                                        int aggfiliale = -1;
                                                        if (dbf.getC() != null) {
                                                            mysql = true;
                                                            aggfiliale = dbf.getAggdaScaricare_SUFILIALE(fil);
                                                        }
                                                        dbf.closeDB();
                                                        String piok1 = "";
                                                        String piko1 = "";
                                                        if (mysql) {
                                                            piok1 = "style='color:green;'";
                                                        } else {
                                                            piko1 = "style='color:red;'";
                                                        }
                                                        int agg = dbm.getAggdaScaricare(fil);
                                                        String piok2 = "";
                                                        String piko2 = "";
                                                        if (agg == 0 && aggfiliale == 0) {
                                                            piok2 = "style='color:green;'";
                                                        } else {
                                                            piko2 = "style='color:red;'";
                                                        }
                                                        if (mysql && agg == 0 && aggfiliale == 0) {
                                                            generale = true;
                                                        }
                                                        String color = "blue-hoki";
                                                        String ba = "badge-default";
                                                        String text = "KO";
                                                        String ic = "fa-database";
                                                        if (generale) {
                                                            color = "green-jungle";
                                                            ic = "fa-check";
                                                            text = "OK";
                                                            ok++;
                                                        } else {
                                                            if (mysql || (agg == 0 && aggfiliale == 0)) {
                                                                text = "!";
                                                                ic = "fa-comment-o";
                                                                wa++;
                                                            } else {
                                                                color = "red";
                                                                text = "KO";
                                                                ic = "fa-exclamation-triangle";
                                                                ko++;
                                                            }
                                                        }


                                        %>
                                        <li class="mt-list-item">
                                            <div class="list-todo-icon bg-white">
                                                <i class="fa <%=ic%>"></i>
                                            </div>
                                            <div class="list-todo-item <%=color%>">
                                                <a class="list-toggle-container" data-toggle="collapse" href="#task<%=ind%>" aria-expanded="false">
                                                    <div class="list-toggle done uppercase">
                                                        <div class="list-toggle-title bold">BRANCH: <%=fil%> - <%=br.getDe_branch()%></div>
                                                        <div class="badge <%=ba%> pull-right bold"><%=text%></div>
                                                    </div>
                                                </a>
                                                <div class="task-list panel-collapse collapse" id="task<%=ind%>" >
                                                    <ul>
                                                        <li class="task-list-item">
                                                            <div class="task-icon">
                                                                <a href="javascript:;">
                                                                    <i class="fa fa-table"></i>
                                                                </a>
                                                            </div>
                                                            <div class="task-status">
                                                                <a <%=piok1%> href="javascript:;">
                                                                    <i class="fa fa-check"></i>
                                                                </a>
                                                                <a <%=piko1%> href="javascript:;">
                                                                    <i class="fa fa-close"></i>
                                                                </a>
                                                            </div>
                                                            <div class="task-content">
                                                                <h4 class="uppercase bold">
                                                                    <a href="javascript:;">MYSQL</a>
                                                                </h4>
                                                                <p> Service Mysql on <%=ip%> - Port 3306  </p>
                                                            </div>
                                                        </li>
                                                        <li class="task-list-item">
                                                            <div class="task-icon">
                                                                <a href="javascript:;">
                                                                    <i class="fa fa-pencil"></i>
                                                                </a>
                                                            </div>
                                                            <div class="task-status">
                                                                <a <%=piok2%> href="javascript:;">
                                                                    <i class="fa fa-check"></i>
                                                                </a>
                                                                <a <%=piko2%> href="javascript:;">
                                                                    <i class="fa fa-close"></i>
                                                                </a>
                                                            </div>
                                                            <div class="task-content">
                                                                <h4 class="uppercase bold">
                                                                    <a href="javascript:;">UPDATE</a>
                                                                </h4>
                                                                <p>Pending Update: <b><%=agg%></b></p>
                                                                <p>Pending Update: <b><%=aggfiliale%></b></p>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </li>
                                        <%}
                                                }
                                            }%>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="mt-element-list">
                                <div class="mt-list-head list-todo dark">
                                    <div class="list-head-title-container">
                                        <h4 class="list-title">TOTAL BRANCH: <%=listafilialiip.size()-1%></h4>
                                        
                                        <h4 class="list-title">OK: <%=ok%></h4>
                                        
                                        <h4 class="list-title">WARNING: <%=wa%></h4>
                                        
                                        <h4 class="list-title">KO: <%=ko%></h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <%dbm.closeDB();%>
            <!-- END CONTAINER -->
            <!-- BEGIN FOOTER -->

            <!-- END FOOTER -->
            <!-- BEGIN CORE PLUGINS -->
            <script src="assets/global/plugins/jquery.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
            
            <!-- END CORE PLUGINS -->
            <!-- BEGIN PAGE LEVEL PLUGINS -->
            <!-- END PAGE LEVEL PLUGINS -->
            <!-- BEGIN THEME GLOBAL SCRIPTS -->
            <script src="assets/global/scripts/app.min.js" type="text/javascript"></script>
            <script src="assets/pages/scripts/components-date-time-pickers.min.js" type="text/javascript"></script>
            <!-- END THEME GLOBAL SCRIPTS -->
            <!-- BEGIN PAGE LEVEL SCRIPTS -->
            <!-- END PAGE LEVEL SCRIPTS -->
            <!-- BEGIN THEME LAYOUT SCRIPTS -->
            <script src="assets/layouts/layout/scripts/layout.min.js" type="text/javascript"></script>
            <script src="assets/layouts/layout/scripts/demo.min.js" type="text/javascript"></script>

            <!-- END PAGE LEVEL PLUGINS -->
            <!-- BEGIN THEME GLOBAL SCRIPTS -->


            <!-- END THEME LAYOUT SCRIPTS -->

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
