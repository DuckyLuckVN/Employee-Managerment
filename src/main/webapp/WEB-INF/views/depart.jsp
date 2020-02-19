<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>

<%--			Modal Depart		--%>
<div class="modal fade " id="departModal" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="departModal">
	<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered" role="document">
		<div class="modal-content card">
			<div class="modal-header card-header card-header-primary row">
				<h5 class="card-title mx-auto" id="titleStaffModal">Thông tin phòng ban</h5>
				<button type="button" style="position: absolute; right: 20px;" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form class="modal-body" id="formDepart">
				<div class="row mb-3">
					<div class="col-12 text-center" style="font-size: 18px">
						<i class="fas fa-hotel fa-10x"></i>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<div class="form-group">
							<label><s:message code="tbl.depart.col.id"/></label>
							<input name="id" type="text" class="form-control" placeholder="Nhập mã phòng ban">
						</div>
						<div class="form-group">
							<label><s:message code="tbl.depart.col.name"/></label>
							<input name="name" type="text" class="form-control" placeholder="Nhập tên phòng ban">
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer py-1">
				<button type="button" class="btn btn-outline-light" data-dismiss="modal">Đóng</button>
				<button id="btnSubmitForm" type="button" date-active="insert" class="btn btn-success">Lưu phòng ban
				</button>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-sm-12 col-md-4">
			<div class="card card-stats">
				<div class="card-header card-header-info card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-door-closed"></i>
					</div>
					<p class="card-category"><s:message code="stats.depart.total"/></p>
					<h3 class="card-title" id="depart-total">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Tổng số phòng ban trong công ty</span>
						<%--									<i class="material-icons text-danger">warning</i>--%>
						<%--									<a href="#pablo">Get More Space...</a>--%>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12 col-md-4">
			<div class="card card-stats">
				<div class="card-header card-header-success card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-home"></i>
					</div>
					<p class="card-category"><s:message code="stats.depart.goodDepart"/></p>
					<h3 class="card-title" id="depart-good">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Số phòng ban có tổng <b style="font-weight: bold;">điểm thưởng cao</b> trong tháng</span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12 col-md-4">
			<div class="card card-stats">
				<div class="card-header card-header-danger card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-house-damage"></i>
					</div>
					<p class="card-category"><s:message code="stats.depart.badDepart"/></p>
					<h3 class="card-title" id="depart-bad">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Số phòng ban có tổng <b style="font-weight: bold;">điểm phạt cao</b> trong tháng</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%--				TABLE--%>
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-header card-header-primary row">
					<div class="mr-auto col-sm-12 col-md-8">
						<h4 class="card-title "><s:message code="tbl.depart.title"/> </h4>
						<p class="card-category"><s:message code="tbl.depart.title.description"/> </p>
					</div>
					<%--				SEARCH BAR--%>
					<form id="form-depart" action="/staff/index" class="input-group ml-auto col-sm-12 col-md-4" method="post">
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
						<table id="tblDepart" class="table table-hover table-bordered">
							<thead class=" text-primary">
							<tr>
								<th><s:message code="tbl.depart.col.id"/></th>
								<th><s:message code="tbl.depart.col.name"/></th>
								<th><s:message code="tbl.depart.title.staffCount"/></th>
								<th><s:message code="tbl.title.action"/></th>
							</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
						<div class="d-flex justify-content-between">
							<ul name="pagination" class="pagination"></ul>
							<button class="btn btn-success" id="btnShowInsertDepart">
								<i class="fas fa-plus"></i> <s:message code="tbl.depart.btn.addNew"/>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
