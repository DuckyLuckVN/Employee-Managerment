<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--			Modal changeProfile --%>
<div class="modal fade " id="changeProfileModal" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="staffModal">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content card">
			<form id="formEditProfile">
				<div class="modal-body">
					<div class="form-group">
						<label>Họ tên</label>
						<input type="hidden" name="username" class="form-control">
						<input type="text" name="fullname" class="form-control">
					</div>
					<div class="form-group">
						<label>Mật khẩu cũ</label>
						<input type="text" name="password" class="form-control"
						>
					</div>
					<div class="form-group">
						<label>Mật khẩu mới</label>
						<input type="text" name="newPassword" class="form-control"
						>
					</div>
					<div class="form-group">
						<label>Xác nhận mật khẩu</label>
						<input type="text" name="reNewPassword" class="form-control"
						>
					</div>
				</div>
				<div class="modal-footer py-1">
					<button type="submit" name="update" value="update" class="btn btn-danger">Lưu thay đổi</button>
				</div>
			</form>
		</div>
	</div>
</div>

<nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
	<div class="container-fluid nav-shadow">

		<div class="navbar-wrapper w-100">
			<a class="navbar-brand mr-auto" href="#pablo">${requestScope.page_title}</a>
			<div class="px-3 justify-content-end">
				<div id="navBarUser" class="btn-group" style="font-weight: bold;">
					<button type="button" class="btn btn-outline-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						${sessionScope.user.fullname}
					</button>
					<div class="dropdown-menu">
						<a name="btnShowInfo" class="dropdown-item" href=""><s:message code="nav.top.item.profile"/></a>
						<a id="btnShowEditProfile" data-edit="${user.username}" class="dropdown-item" href=""><s:message code="nav.top.item.changePassword"/></a>
						<div class="dropdown-divider"></div>
						<a name="btnChangeLang" class="dropdown-item" data-lang="vi" href=""><i class="fas fa-globe-asia pr-2"></i><s:message code="lang.vi"/></a>
						<a name="btnChangeLang" class="dropdown-item" data-lang="en"href=""><i class="fas fa-globe-americas  pr-2"></i><s:message code="lang.en"/></a>
						<div class="dropdown-divider"></div>
						<a name="btnLogout" class="dropdown-item" href=""><i class="fas fa-sign-out-alt pr-2"></i><s:message code="nav.left.item.logout"/></a>
					</div>
				</div>
			</div>
			<button class="navbar-toggler justify-content-end" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
				<span class="sr-only">Toggle navigation</span>
				<span class="navbar-toggler-icon icon-bar"></span>
				<span class="navbar-toggler-icon icon-bar"></span>
				<span class="navbar-toggler-icon icon-bar"></span>
			</button>
		</div>
	</div>
</nav>
