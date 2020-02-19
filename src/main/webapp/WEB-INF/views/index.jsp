<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%--<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>--%>
<html>
<head>
	<meta charset="UTF-8">
	<base href="${pageContext.request.contextPath}"/>
	<link rel="icon" href="/resources/image/web-icon.ico">
	<jsp:include page="/WEB-INF/included/css_main.jsp"/>
	<title>Trang Chủ | Quản Lý Nhân Viên</title>

</head>
<body>
<div class="wrapper">
	<%--		MENU			--%>
	<jsp:include page="/WEB-INF/included/ui/menu-left.jsp"/>

	<%--		CONTAINER		--%>
	<div class="main-panel ps-container ps-theme-default">

		<%--		NAV BAR			--%>
		<jsp:include page="/WEB-INF/included/ui/nav-bar.jsp"/>


		<%--		NỘI DUNG		--%>
		<div class="content pt-0">
			<c:if test="${not empty page_include_path}">
				<jsp:include page="${page_include_path}"/>
			</c:if>
		</div>

		<%-- 		FOOTER 		--%>
		<jsp:include page="/WEB-INF/included/ui/footer.jsp"/>

	</div>
</div>
<%--	IMPORT SCRIPT--%>
<jsp:include page="/WEB-INF/included/js_main.jsp"/>
</body>
</html>
