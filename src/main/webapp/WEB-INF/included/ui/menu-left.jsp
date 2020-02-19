<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<div class="sidebar" data-color="purple" data-background-color="white" data-image="../assets/img/sidebar-1.jpg">
	<!--
	  Tip 1: You can change the color of the sidebar using: data-color="purple | azure | green | orange | danger"

	  Tip 2: you can also add an image using data-image tag
  -->
	<div class="logo">
		<a class="simple-text logo-normal" style="font-weight: bold;">
			<s:message code="nav.left.title"/>
		</a>
	</div>
	<div class="sidebar-wrapper ps-container ps-theme-default">
		<ul class="nav">
			<li class="nav-item ${requestScope.page_name == 'main' ? 'active' : ''}">
				<a class="nav-link" href="/index">
					<i class="fas fa-home"></i>
					<p><s:message code="nav.left.item.index"/></p>
				</a>
			</li>
			<li class="nav-item ${requestScope.page_name == 'staff' ? 'active' : ''}">
				<a class="nav-link" href="/staff">
					<i class="fas fa-user-tie"></i>
					<p><s:message code="nav.left.item.staff"/></p>
				</a>
			</li>
			<li class="nav-item ${requestScope.page_name == 'depart' ? 'active' : ''}">
				<a class="nav-link" href="/depart">
					<i class="fas fa-building"></i>
					<p><s:message code="nav.left.item.depart"/></p>
				</a>
			</li>
			<li class="nav-item ${requestScope.page_name == 'record' ? 'active' : ''}">
				<a class="nav-link" href="/record">
					<i class="fas fa-adjust"></i>
					<p><s:message code="nav.left.item.record"/></p>
				</a>
			</li>
			<li class="nav-item ${requestScope.page_name == 'statistic' ? 'active' : ''}">
				<a class="nav-link" href="/statistic">
					<i class="fas fa-chart-pie"></i>
					<p><s:message code="nav.left.item.statistic"/></p>
				</a>
			</li>
			<li class="nav-item ${requestScope.page_name == 'user' ? 'active' : ''}">
				<a class="nav-link" href="/user">
					<i class="fas fa-user-secret"></i>
					<p><s:message code="nav.left.item.user"/></p>
				</a>
			</li>
			<%--			<li class="nav-item ${requestScope.page_name == 'query' ? 'active' : ''}">--%>
			<%--				<a class="nav-link" href="/query">--%>
			<%--					<i class="fas fa-database"></i>--%>
			<%--					<p>SQL Query Page</p>--%>
			<%--				</a>--%>
			<%--			</li>--%>
			<li class="nav-item active-pro ">
				<a id="btnLogout" class="nav-link" href="./upgrade.html">
					<i class="fas fa-sign-out-alt"></i>
					<p><s:message code="nav.left.item.logout"/></p>
				</a>
			</li>
		</ul>
		<div class="ps-scrollbar-x-rail" style="left: 0px; bottom: 0px;">
			<div class="ps-scrollbar-x" tabindex="0" style="left: 0px; width: 0px;"></div>
		</div>
		<div class="ps-scrollbar-y-rail" style="top: 0px; right: 0px;">
			<div class="ps-scrollbar-y" tabindex="0" style="top: 0px; height: 0px;"></div>
		</div>
	</div>
	<div class="sidebar-background" style="background: url('/resources/image/sidebar.jpg');">
	</div>
</div>