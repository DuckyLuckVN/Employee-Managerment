<%--
  Created by IntelliJ IDEA.
  User: DaiHao
  Date: 04/10/2019
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%--<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>--%>
<div class="mapouter">
	<div class="gmap_canvas">
		<iframe width="100%" height="100%" id="gmap_canvas"
				src="https://maps.google.com/maps?q=Ho%20chi%20minh&t=&z=13&ie=UTF8&iwloc=&output=embed" frameborder="0"
				scrolling="no" marginheight="0" marginwidth="0"></iframe>
		<a href="https://www.embedgooglemap.net/blog/best-wordpress-themes/">temas para embedgooglemap.net</a></div>
	<style>
		.mapouter {
		position: relative;
		text-align: right;
		height: 95%;
		width: 100%;
	}

	.gmap_canvas {
		overflow: hidden;
		background: none !important;
		height: 95%;
		width: 100%;
	}</style>
</div>