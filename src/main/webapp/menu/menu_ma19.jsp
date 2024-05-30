<%-- 
    Document   : menu1
    Created on : 25-mag-2016, 11.04.18
    Author     : raffaele
--%>

<%@page import="rc.so.util.Engine"%>
<%@page import="rc.so.util.Constant"%>
<%@page import="rc.so.util.Etichette"%>
<%
    String lan2 = (String) session.getAttribute("language");
    lan2 = "IT";
    Etichette et2 = new Etichette(lan2);
%>

<div class="page-sidebar-wrapper">
    <!-- BEGIN SIDEBAR -->
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
            <li class="sidebar-toggler-wrapper hide">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler">
                    <span></span>
                </div>
                <!-- END SIDEBAR TOGGLER BUTTON -->
            </li>
            <!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
            <li class="nav-item">
                <a href="index.jsp" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title"><%=et2.getHomepage()%></span>
                    <span class="selected"></span>
                </a>
            </li>
            <li class="nav-item active">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-cogs"></i>
                    <span class="title">Maintenance</span>
                    <span class="arrow open"></span>
                </a>
                <ul class="sub-menu">
<li class="nav-item start">
                        <a href="tb_esolv.jsp" class="nav-link ">
                            <i class="fa fa-address-card-o"></i>
                            <span class="title">Accounting Code</span>
                        </a>
                    </li>
                    <li class="nav-item ">
                        <a href="tb_agency.jsp" class="nav-link ">
                            <i class="fa fa-group"></i>
                            <span class="title">Agency</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_bank.jsp" class="nav-link ">
                            <i class="fa fa-bank"></i>
                            <span class="title">Bank</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_blackM.jsp" class="nav-link ">
                            <i class="fa fa-remove"></i>
                            <span class="title">Blacklist Maccorp</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_branch.jsp" class="nav-link ">
                            <i class="fa fa-anchor"></i>
                            <span class="title">Branch</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_groupbr.jsp" class="nav-link ">
                            <i class="fa fa-group"></i>
                            <span class="title">Branch Group</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_cashierperf.jsp" class="nav-link ">
                            <i class="fa fa-user-circle"></i>
                            <span class="title">Cashier Conf.</span>
                        </a>
                    </li>
                    
                    <li class="nav-item start">
                        <a href="tb_city.jsp" class="nav-link ">
                            <i class="fa fa-plane"></i>
                            <span class="title">City</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_company.jsp" class="nav-link ">
                            <i class="fa fa-group"></i>
                            <span class="title">Company</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_nations.jsp" class="nav-link ">
                            <i class="fa fa-plane"></i>
                            <span class="title">Country</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_creditcard.jsp" class="nav-link ">
                            <i class="fa fa-credit-card-alt"></i>
                            <span class="title">Credit Card</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_currency.jsp" class="nav-link ">
                            <i class="fa fa-balance-scale"></i>
                            <span class="title">Currency</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_district.jsp" class="nav-link ">
                            <i class="fa fa-plane"></i>
                            <span class="title">District</span>
                        </a>
                    </li>
                    <li class="nav-item ">
                        <a href="fileinteg.jsp" class="nav-link">
                            <i class="fa fa-share"></i>
                            <span class="title">Export Files</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_figures.jsp" class="nav-link ">
                            <i class="fa fa-money"></i>
                            <span class="title">Figures</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_fixcomm.jsp" class="nav-link ">
                            <i class="fa <%=Constant.iconcur%>"></i>
                            <span class="title">Fix Commission</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_doctype.jsp" class="nav-link ">
                            <i class="fa fa-credit-card"></i>
                            <span class="title">Identity Documents</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_intbook.jsp" class="nav-link ">
                            <i class="fa fa-briefcase"></i>
                            <span class="title">Internet Booking</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_kindfixcomm.jsp" class="nav-link ">
                            <i class="fa <%=Constant.iconcur%>"></i>
                            <span class="title">Kind Fix Commission</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_kindtrans.jsp" class="nav-link ">
                            <i class="fa fa-arrows-h"></i>
                            <span class="title">Kind of Transaction</span>
                        </a>
                    </li>
                    <li class="nav-item active">
                        <a href="tb_raterange.jsp" class="nav-link ">
                            <i class="fa <%=Constant.iconcur%>"></i>
                            <span class="title">Level Rate</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_lowcommju.jsp" class="nav-link ">
                            <i class="fa fa-font"></i>
                            <span class="title">Low Commission Justify</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_loyalty.jsp" class="nav-link ">
                            <i class="fa fa-user-circle-o"></i>
                                <span class="title">Loyalty</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_natoff.jsp" class="nav-link ">
                            <i class="fa fa-flag"></i>
                            <span class="title">National Office</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="nc_category.jsp" class="nav-link ">
                            <i class="fa fa-list"></i>
                            <span class="title">No Change</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_safetill.jsp" class="nav-link ">
                            <i class="fa fa-cubes"></i>
                            <span class="title">Safe and Till</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_unlock.jsp" class="nav-link ">
                            <i class="fa fa-unlock"></i>
                            <span class="title">Unlock Codes</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_unlockratejust.jsp" class="nav-link ">
                            <i class="fa fa-font"></i>
                            <span class="title">Unlock Rate Justify</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_users.jsp" class="nav-link ">
                            <i class="icon-users"></i>
                            <span class="title">Users</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="tb_vatcode.jsp" class="nav-link ">
                            <i class="fa fa-calculator"></i>
                            <span class="title">VAT Code</span>
                        </a>
                    </li>

                </ul>
            </li>
            <li class="nav-item start">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-arrows-h"></i>
                    <span class="title">Transaction</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item start">
                        <a href="ti_openclose.jsp" class="nav-link ">
                            <i class="fa fa-calculator"></i>
                            <span class="title">Open/Close</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="it_change.jsp" class="nav-link ">
                            <i class="fa fa-reply"></i>
                            <span class="title">Internal Transfer</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="et_change.jsp" class="nav-link ">
                            <i class="fa fa-share"></i>
                            <span class="title">External Transfer</span>
                        </a>
                    </li>

                    <li class="nav-item start">
                        <a href="transaction.jsp" class="nav-link ">
                            <i class="fa fa-exchange"></i>
                            <span class="title">Change</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="it_nochange.jsp" class="nav-link ">
                            <i class="fa fa-reply"></i>
                            <span class="title">Internal Transfer NC</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="et_nochange.jsp" class="nav-link ">
                            <i class="fa fa-share"></i>
                            <span class="title">External Transfer NC</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="nc_transaction.jsp" class="nav-link ">
                            <i class="fa fa-map-o"></i>
                            <span class="title">No Change</span>
                        </a>
                    </li>

                </ul>
            </li>
            <li class="nav-item">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-list"></i>
                    <span class="title">List</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item ">
                        <a href="oc_safereal.jsp" class="nav-link ">
                            <i class="fa fa-list"></i>
                            <span class="title">Safe Real-Time</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="oc_list.jsp" class="nav-link ">
                            <i class="fa fa-list"></i>
                            <span class="title">Open/Close</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="transaction_list.jsp" class="nav-link ">
                            <i class="fa fa-list"></i>
                            <span class="title">Transaction</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="transaction_listnc.jsp" class="nav-link ">
                            <i class="fa fa-list"></i>
                            <span class="title">Transaction No Change</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="it_list.jsp" class="nav-link ">
                            <i class="fa fa-list"></i>
                            <span class="title">Internal Transfer</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="et_list.jsp" class="nav-link ">
                            <i class="fa fa-list"></i>
                            <span class="title">External Transfer</span>
                        </a>
                    </li>
                    <li class="nav-item start">
                        <a href="kyc_list.jsp" class="nav-link ">
                            <i class="fa fa-list"></i>
                            <span class="title">K.Y.C. Declaration</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li class="nav-item">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-bar-chart"></i>
                    <span class="title">Report</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item start">
                        <a href="re_index.jsp" class="nav-link ">
                            <i class="fa fa-pie-chart"></i>
                            <span class="title">Panel (Branch)</span>
                        </a>
                    </li>
                    <%
                        boolean central1 = Engine.isCentral();
                        String tipo1 = (String) request.getSession().getAttribute("us_tip");
                        if (tipo1 == null) {
                            tipo1 = "";
                        }
                        if (central1 && (tipo1.equals("2") || tipo1.equals("3"))) {%>
                    <li class="nav-item start">
                        <a href="re_index_central.jsp" class="nav-link ">
                            <i class="fa fa-pie-chart"></i>
                            <span class="title">Panel (Central)</span>
                        </a>
                    </li>
                    <%}%>
                </ul>
            </li>
            <li class="nav-item ">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-share-square"></i>
                    <span class="title">External Services</span>
                    <span class="arrow"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item start">
                        <a href="javascript:;" class="nav-link nav-toggle">
                            <i class="fa fa-globe"></i>
                            <span class="title">Website</span>
                            <span class="arrow"></span>
                        </a>
                        <ul class="sub-menu">
                            <li class="nav-item">
                                <a href="tb_website.jsp" class="nav-link">
                                    <i class="fa fa-cogs"></i> Maintenance</a>
                            </li>
                            <li class="nav-item">
                                <a href="web_tran.jsp" class="nav-link">
                                    <i class="fa fa-arrows-h"></i> Transaction</a>
                            </li>
                        </ul>
                    </li>
                    <!--
                    <li class="nav-item start">
                        <a href="es_olta.jsp" class="nav-link">
                            <i class="fa fa-ticket"></i>
                            <span class="title">OLTA</span>
                        </a>
                    </li>
                    -->
                    <li class="nav-item start">
                        <a href="es_tangerine.jsp" class="nav-link">
                            <i class="fa fa-ticket"></i>
                            <span class="title"><%=Constant.eithcettaOS%></span>
                        </a>
                    </li>
                    <%if(Constant.mostrapaymat){%>
                    <li class="nav-item ">
                        <a href="es_paym.jsp" class="nav-link">
                            <i class="fa fa-credit-card"></i>
                            <span class="title">Paymat</span>
                        </a>
                    </li>
                    <%}%>
                   
                </ul>
            </li>
            <li class="nav-item ">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="fa fa-newspaper-o"></i>
                    <span class="title">Newsletter</span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item ">
                        <a href="nl_add.jsp" class="nav-link ">
                            <i class="fa fa-plus"></i>
                            <span class="title">Add</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a href="nl_view.jsp" class="nav-link ">
                            <i class="fa fa-file-pdf-o"></i>
                            <span class="title">View</span>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
        <!-- END SIDEBAR MENU -->
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>