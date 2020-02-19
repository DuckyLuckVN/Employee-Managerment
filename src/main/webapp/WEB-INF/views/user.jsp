<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%--			Modal User 		--%>
<div class="modal fade " id="userModal" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="departModal">
	<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered" role="document">
		<div class="modal-content card">
			<div class="modal-header card-header card-header-rose row mx-0">
				<h5 class="card-title" id="titleUserModal">Thêm mới tài khoản</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form class="modal-body" id="formUser">
				<div class="row mb-3">
					<div class="col-12 text-center" style="font-size: 20px">
						<i class="fas fa-id-card-alt fa-10x"></i>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<div class="form-group">
							<label><s:message code="tbl.user.col.username"/></label>
							<input name="username" type="text" class="form-control" placeholder="Nhập tài khoản">
						</div>
						<div class="form-group">
							<label><s:message code="tbl.user.col.fullname"/></label>
							<input name="fullname" type="text" class="form-control" placeholder="Nhập họ tên đầy đủ">
						</div>
						<div class="form-group">
							<label><s:message code="tbl.user.col.password"/></label>
							<input name="password" type="text" class="form-control" placeholder="Nhập mật khẩu">
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer py-1">
				<button type="button" class="btn btn-outline-light" data-dismiss="modal">Đóng</button>
				<button id="btnSubmitForm" type="button" date-active="insert" class="btn btn-success"><s:message code="tbl.user.btn.addNew"/>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<%--				TABLE--%>
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-header card-header-primary row">
					<div class="mr-auto col-sm-12 col-md-8">
						<h4 class="card-title "><s:message code="tbl.user.title"/></h4>
						<p class="card-category"><s:message code="tbl.user.title.description"/></p>
					</div>
					<%--				SEARCH BAR--%>
					<form id="user-form" action="/staff/index" class="input-group ml-auto col-sm-12 col-md-4" method="post">
						<div class="form-group bmd-form-group w-100">
							<%--							<label class="bmd-label-floating" style="color: ghostwhite;">Tìm kiếm</label>--%>
							<label class="mb-0" style="color: ghostwhite;"><s:message code="common.search"/></label>
							<input name="search" type="text" class="form-control" style="color: white"
								   value="${param.search}">
						</div>
					</form>
				</div>
				<div class="card-body">
					<div class="d-flex justify-content-between">
						<ul id="a" name="pagination" class="pagination"></ul>
						<div>
							<select name="rowShow" class="custom-select custom-select-sm rounded" style="font-size: 16px;">
								<option value="-1" selected="">-- Chọn số dòng hiển thị --</option>
								<option value="5">5</option>
								<option value="10">10</option>
								<option value="15">15</option>
								<option value="20">20</option>
								<option value="25">25</option>
								<option value="30">30</option>
								<option value="50">50</option>
								<option value="60">60</option>
								<option value="75">75</option>
								<option value="100">100</option>
							</select>
						</div>
					</div>
					<div class="table-responsive">
						<table id="tblUser" class="table table-hover table-bordered">
							<thead class=" text-primary">
							<tr>
								<th><s:message code="tbl.user.col.username"/></th>
								<th><s:message code="tbl.user.col.fullname"/></th>
								<th><s:message code="tbl.user.col.password"/></th>
								<th><s:message code="tbl.title.action"/></th>
							</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
						<div class="d-flex justify-content-between">
							<ul name="pagination" class="pagination"></ul>
							<button class="btn btn-success" id="btnShowInsertUser">
								<i class="fas fa-plus"></i> <s:message code="tbl.user.btn.addNew"/>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
