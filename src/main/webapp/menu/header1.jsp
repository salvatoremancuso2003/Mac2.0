<%-- 
    Document   : header1
    Created on : 25-mag-2016, 10.11.52
    Author     : raffaele
--%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.util.Etichette"%>
<%
    String lab = Engine.getStringHeader(session);
    if (lab == null) {
        Utility.redirect(request, response, "login.jsp");
    } else {

        String lan1 = (String) session.getAttribute("language");
        lan1 = "IT";
        Etichette et = new Etichette(lan1);


%>

<div class="page-header navbar navbar-fixed-top">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner ">
        <!-- BEGIN LOGO -->
        <div class="page-logo">
            <div class="menu-toggler sidebar-toggler">
                <span></span>
            </div>
        </div>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse">
            <span></span>
        </a>
        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN TOP NAVIGATION MENU -->
        <div class="top-menu">
            <ul class="nav navbar-nav pull-right">
                <li class="dropdown dropdown-quick-sidebar-toggler">
                    <a onclick="return onbottom();" 
                       class="tooltips" data-placement="bottom" 
                       data-original-title="Go to the bottom" style="color: #79869a; text-decoration: none;" >
                        <i class="icon-arrow-down"></i>
                    </a>
                </li>
                <li class="dropdown dropdown-user">
                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                        <%if (Constant.test) {%>
                        <img alt='' class="img-circle" src='assets/soop/img/BT.png'/>
                        <%} else {%>
                        <img alt="" class="img-circle" src="assets/soop/img/av3.png" />
                        <%}%>
                        <span class="username username-hide-on-mobile"><%=lab%></span>
                        <!--<i class="fa fa-angle-down"></i>-->
                    </a>
                    <ul class="dropdown-menu dropdown-menu-default">
                        <li>
                            <a href="changepassword.jsp">
                                <i class="icon-key"></i> Change password</a>
                        </li>
                    </ul>
                </li>
                <!-- END USER LOGIN DROPDOWN -->
                <!-- BEGIN QUICK SIDEBAR TOGGLER -->
                <!-- DOC: Apply "dropdown-dark" class after below "dropdown-extended" to change the dropdown styte -->
                <li class="dropdown dropdown-quick-sidebar-toggler">
                    <a href="Login?type=2" class="dropdown-toggle tooltips" data-placement="bottom" data-original-title="<%=et.getLabel3_logout()%>">
                        <i class="icon-logout"></i>
                    </a>
                </li>

                <!-- END QUICK SIDEBAR TOGGLER -->
            </ul>
        </div>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END HEADER INNER -->
</div>

<%}%>