<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<meta charset="UTF-8">
	<jsp:include page="/WEB-INF/included/css_main.jsp"/>
	<title>Quản Lý Nhân Viên | Đăng Nhập</title>
</head>
<body style="background: #fff4a9;">
<div class="container-fluid">
	<div class="row justify-content-center mt-md-5">
		<div class="col-sm-12 col-md-8 col-lg-4">
			<div class="card">
				<div class="card-header card-header-primary text-center mb-3" style="font-weight: bold; font-size: 18px"><s:message code="login.form.title"/> </div>
				<div class="card-body p-0">
					<div class="row mb-3">
						<div class="col-12 text-center" style="font-size: 15px">
							<i class="fas fa-user-shield fa-10x"></i>
						</div>
					</div>
					<form id="formLogin" action="/login" method="post" class="row">
						<div class="form-group col-12">
							<label for="email_address" class="text-md-right"><s:message code="login.form.lbl.username"/></label>
							<input type="text" id="email_address" class="form-control" name="username" required
								   autofocus>
						</div>
						<div class="form-group col-12">
							<label for="password" class="text-md-right"><s:message code="login.form.lbl.password"/></label>
							<input type="password" id="password" class="form-control" name="password" required>
						</div>

						<div class="form-check col-12 ml-3">
							<label class="form-check-label">
								<input class="form-check-input" name="remember" type="checkbox" value="false">
								<s:message code="login.form.chk.remember"/>
								<span class="form-check-sign">
							  <span class="check"></span>
						  	</span>
							</label>
						</div>

						<div class="col-md-6 mx-auto mb-3">
							<button type="submit" class="btn btn-primary w-100">
								<s:message code="login.form.btn.submit"/>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<jsp:include page="/WEB-INF/included/js_main.jsp"/>

<c:if test="${isExpired}">
	<script>
		AlertUtil.showSimpleError("Phiên đăng nhập hết hạn", "Phiên lưu đăng nhập của bạn đã hết hạn, vui lòng đăng nhập lại!");
	</script>
</c:if>
</html>
