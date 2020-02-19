<%--
  Created by IntelliJ IDEA.
  User: DaiHao
  Date: 02/10/2019
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%--<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>--%>


<%--Tạo đối tượng connection--%>
<sql:setDataSource var="conn" driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
				   url="jdbc:sqlserver://localhost;databaseName=J5_ASM"
				   user="sa" password="123"/>


<%--Xóa đối tượng --%>
<c:if test="${not empty param.deleteId}">
	<sql:update dataSource="${conn}" var="deleteRow" scope="page">
		DELETE FROM dbo.USERS WHERE username = ?
		<sql:param value="${param.deleteId}"/>
	</sql:update>
</c:if>


<%--Thêm đối tượng--%>
<c:if test="${not empty param.insert}">
	<sql:update dataSource="${conn}" var="insertRow" scope="page">
		INSERT INTO dbo.USERS VALUES (?, ?, ?)
		<sql:param value="${param.username}"/>
		<sql:param value="${param.password}"/>
		<sql:param value="${param.fullname}"/>
	</sql:update>
</c:if>


<%--Lấy dữ liệu của đối tượng cần edit--%>
<c:if test="${not empty param.editId && empty param.edit}">
	<sql:query dataSource="${conn}" var="rsUserEdit">
		SELECT * FROM dbo.USERS WHERE username LIKE ?;
		<sql:param value="%${param.editId}%"/>
	</sql:query>
</c:if>

<%--Cập nhật dữ liệu đối tượng--%>
<c:if test="${not empty param.edit}">
	<sql:update dataSource="${conn}" var="updateRow">
		UPDATE dbo.USERS SET username = ?, password = ?, fullname = ? WHERE username LIKE ?
		<sql:param value="${param.username}"/>
		<sql:param value="${param.password}"/>
		<sql:param value="${param.fullname}"/>
		<sql:param value="${param.username}"/>
	</sql:update>
</c:if>


<%--Lấy full dữ liệu từ database--%>
<c:if test="${not empty param.search}">
	<sql:query dataSource="${conn}" var="rsUsers">
		SELECT * FROM dbo.USERS WHERE username LIKE ? OR password LIKE ? OR fullname LIKE ?;
		<sql:param value="%${param.search}%"/>
		<sql:param value="%${param.search}%"/>
		<sql:param value="%${param.search}%"/>
	</sql:query>
</c:if>


<c:if test="${empty param.search}">
	<sql:query dataSource="${conn}" var="rsUsers">
		SELECT * FROM dbo.USERS;
	</sql:query>
</c:if>

<div class="container-fluid">
	<%--						FORM --%>
	<div class="row">
		<div class="col-12 col-md-8 col-lg-5 mx-auto">
			<div class="card">
				<div class="card-header card-header-success">
					<h4 class="card-title">Tài khoản</h4>
					<p class="card-category">Form thông tin tài khoản</p>
				</div>
				<div class="card-body">
					<form method="get" action="">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group bmd-form-group">
									<label class="bmd-label-floating">Họ và tên</label>
									<input name="fullname" type="text" required class="form-control"
										   value="${rsUserEdit.rows[0].fullname}">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group bmd-form-group">
									<label class="bmd-label-floating">Tài khoản</label>
									<input name="username" type="text" required class="form-control"
										   value="${rsUserEdit.rows[0].username}" ${not empty rsUserEdit.rows[0] ? 'readonly' : ''} >
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group bmd-form-group">
									<label class="bmd-label-floating">Mật khẩu</label>
									<input name="password" type="password" required class="form-control"
										   value="${rsUserEdit.rows[0].password}">
								</div>
							</div>
							<%--												<div class="col-md-6">--%>
							<%--													<div class="form-group bmd-form-group">--%>
							<%--														<label class="bmd-label-floating">Xác nhận mật khẩu</label>--%>
							<%--														<input type="password" required class="form-control">--%>
							<%--													</div>--%>
							<%--												</div>--%>
						</div>
						<div class="d-flex">
							<c:if test="${not empty param.editId}">
								<button name="edit" type="submit" value="editUser"
										class="btn btn-success ml-auto">Cập nhật
								</button>
							</c:if>
							<c:if test="${empty param.editId}">
								<button name="insert" type="submit" value="insertUser"
										class="btn btn-success ml-auto">Thêm mới
								</button>
							</c:if>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%--						TABLE--%>
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-header card-header-primary row">
					<div class="mr-auto col-sm-12 col-md-8">
						<h4 class="card-title ">Danh sách tài khoản</h4>
						<p class="card-category">Bảng hiển thị danh sách tài khoản quản trị viên</p>
					</div>
					<%--				SEARCH BAR--%>
					<form action="" class="input-group ml-auto col-sm-12 col-md-4" method="post">
						<div class="form-group bmd-form-group w-100">
							<label class="bmd-label-floating" style="color: ghostwhite;">Tìm kiếm</label>
							<input name="search" type="text" class="form-control" style="color: white"
								   value="${param.search}">
						</div>
					</form>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-hover">
							<thead class=" text-primary">
							<tr>
								<th>Họ và tên</th>
								<th>Tài khoản</th>
								<th>Mật khẩu</th>
								<th>Thao tác</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach var="row" items="${rsUsers.rows}">
								<tr>
									<td>${row.fullname}</td>
									<td>${row.username}</td>
									<td>${row.password}</td>
									<td>
										<a class="btn btn-info" href="?editId=${row.username}"
										   data-toggle="tooltip" data-placement="top"
										   title="Thay đổi thông tin">
											<i class="fas fa-edit"></i>
										</a>
										<a class="btn btn-danger" href="?deleteId=${row.username}"
										   data-toggle="tooltip" data-placement="top" title="Xóa tài khoản">
											<i class="fas fa-user-times"></i>
										</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%--	IMPORT SCRIPT--%>
<script>
    <c:if test="${insertRow > 0}">
    Swal.fire(
        'Thêm mới thành công',
        'Bạn đã thêm mới một tài khoản thành công!',
        'success'
    )
    </c:if>
    <c:if test="${updateRow > 0}">
    Swal.fire(
        'Cập nhật thành công',
        'Bạn đã cập nhật một tài khoản thành công!',
        'success'
    )
    </c:if>
    <c:if test="${deleteRow > 0}">
    Swal.fire(
        'Xóa thành công',
        'Bạn đã xóa một tài khoản thành công!',
        'success'
    )
    </c:if>
</script>
</body>
</html>
