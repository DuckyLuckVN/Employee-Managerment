<%--
  Created by IntelliJ IDEA.
  User: DaiHao
  Date: 26/09/2019
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%--<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags" %>


<%--			Modal avatar --%>
<div class="modal fade " id="avatarModal" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="staffModal">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content card">
			<div class="modal-body">
				<img class="rounded-1" width="100%" src="#"/>
			</div>
			<div class="modal-footer py-1">
				<div class="d-flex justify-content-between">
					<button type="button" name="btnShowSendMail" class="btn btn-danger"><i class="fas fa-envelope"></i> Gửi mail</button>
					<button type="button" class="btn btn-outline-light" data-dismiss="modal">Đóng</button>
				</div>
			</div>
		</div>
	</div>
</div>

<%--			Modal Sendmail --%>
<div class="modal fade " id="sendMailModal" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="staffModal">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content card">
			<div class="modal-body">
				<div class="form-group">
					<label>Địa chỉ người nhận</label>
					<input type="text" name="name" class="form-control"
						   placeholder="Nhập họ tên nhân viên">
				</div>
				<div class="form-group">
					<label for="notes">Nội dung</label>
					<textarea name="notes" class="form-control" rows="3"></textarea>
				</div>
			</div>
			<div class="modal-footer py-1">
				<div class="d-flex justify-content-between">
					<button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fas fa-envelope"></i> Gửi mail</button>
					<button type="button" class="btn btn-outline-light" data-dismiss="modal">Đóng</button>
				</div>
			</div>
		</div>
	</div>
</div>

<%--			Modal Staff--%>
<div class="modal fade " id="staffModal" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="staffModal">
	<div class="modal-dialog modal-lg modal-dialog-centered" role="document">
		<div class="modal-content card">
			<div class="modal-header card-header card-header-primary row">
				<h5 class="card-title mx-auto" id="titleStaffModal">Thông tin nhân viên</h5>
				<button type="button" style="position: absolute; right: 20px;" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form class="modal-body" id="formStaff">
				<div class="mx-auto">
					<div class="row mb2">
						<%--					Hình ảnh--%>
						<div class="avatar-card mx-auto mb-5">
							<div class="avatar shadow-lg">
								<label for="filePhoto">Chọn ảnh</label>
							</div>
<%--							File hình ảnh--%>
							<input type="file" accept=".jpg,.png" name="photo" id="filePhoto" class="btn btn-outline-primary">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-12">
						<div class="row">
							<div class="col-6">
								<div class="row mb-3">
									<%--				Mã nhân viên--%>
									<input name="id" type="hidden"/>

									<%--				Họ tên--%>
									<div class="col-12">
										<div class="form-group">
											<label><s:message code="tbl.staff.col.name"/></label>
											<input type="text" name="name" class="form-control"
												   placeholder="Nhập họ tên nhân viên">
										</div>
									</div>
								</div>
								<div class="row mb-2">
									<%--				Giới tính--%>
									<div class="col-12">
										<div class="form-group">
											<label><s:message code="tbl.staff.col.gender"/></label>
											<div class="form-check form-check-radio">
												<label class="form-check-label">
													<input class="form-check-input" type="radio" name="gender" id="male"
														   value="true">
													<s:message code="common.gender.male"/>
													<span class="circle">
														<span class="check"></span>
													</span>
												</label>
												<label class="form-check-label ml-3">
													<input class="form-check-input" type="radio" name="gender"
														   id="female" value="false">
													<s:message code="common.gender.female"/>
													<span class="circle">
														<span class="check"></span>
													</span>
												</label>
											</div>
										</div>
									</div>
								</div>
								<div class="row mb-2">
									<div class="col">
										<%--				Email--%>
										<div class="form-group">
											<label><s:message code="tbl.staff.col.email"/></label>
											<input type="text" name="email" class="form-control"
												   placeholder="Nhập email liên hệ">
										</div>
									</div>
									<div class="col">
										<%--				Số điện thoại--%>
										<div class="form-group">
											<label><s:message code="tbl.staff.col.phone"/></label>
											<input type="text" name="phone" class="form-control"
												   placeholder="Nhập số điện thoại">
										</div>
									</div>
								</div>
							</div>
							<div class="col-6">
								<div class="row  mb-2">
									<div class="col-6">
										<%--				Số lương--%>
										<div class="form-group">
											<label><s:message code="tbl.staff.col.salary"/></label>
											<input type="text" name="salary" class="form-control"
												   placeholder="Nhập số lương của nhân viên">
										</div>
									</div>
									<div class="col-6">
										<%--				Ngày sinh--%>
										<div class="form-group">
											<label><s:message code="tbl.staff.col.birthday"/></label>
											<input type="date" name="birthday" class="form-control"
												   placeholder="Chọn ngày sinh">
										</div>
									</div>
								</div>
								<div class="row mb-2">
									<div class="col-12">
										<%--						Chọn phòng ban--%>
										<div class="form-group">
											<label for="departSelect"><s:message code="tbl.staff.col.depart"/></label>
											<select name="departId" class="custom-select" id="departSelect">
												<option selected value="nothing">-- Chọn phòng ban --</option>
											</select>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-12">
										<div class="form-group">
											<label for="notes">Ghi chú</label>
											<textarea name="notes" class="form-control" id="notes" rows="1"></textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer py-1">
				<button type="button" class="btn btn-outline-light" data-dismiss="modal">Đóng</button>
				<button id="submitForm" type="button" date-active="insert" class="btn btn-success">Lưu nhân viên
				</button>
			</div>
		</div>
	</div>
</div>



<div class="container-fluid">
	<div class="row">
		<div class="col-sm-12 col-md-4">
			<div class="card card-stats" id="_form_">
				<div class="card-header card-header-info card-header-icon">
					<div class="card-icon">
						<i class="fas fa-user-friends"></i>
					</div>
					<p class="card-category"><s:message code="stats.staff.total"/></p>
					<h3 id="st-staff-count" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Tổng số nhân viên trong công ty</span>
						<%--									<i class="material-icons text-danger">warning</i>--%>
						<%--									<a href="#pablo">Get More Space...</a>--%>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12 col-md-4">
			<div class="card card-stats">
				<div class="card-header card-header-success card-header-icon">
					<div class="card-icon">
						<i class="fas fa-mars"></i>
					</div>
					<p class="card-category"><s:message code="stats.staff.totalMale"/></p>
					<h3 id="st-staff-male" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Tổng số nhân viên "nam" hiện có</span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12 col-md-4">
			<div class="card card-stats">
				<div class="card-header card-header-rose card-header-icon">
					<div class="card-icon">
						<i class="fas fa-venus"></i>
					</div>
					<p class="card-category"><s:message code="stats.staff.totalFemale"/></p>
					<h3 id="st-staff-famale" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Tổng số nhân viên "nữ" hiện có</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%--				TABLE--%>
	<div class="row">
		<div class="col-12">
			<div class="card">
				<div class="card-header card-header-primary bg-dark row">
					<div class="mr-auto col-sm-12 col-md-8">
						<h4 class="card-title " id="testClick"><s:message code="tbl.staff.title"/></h4>
						<p class="card-category"><s:message code="tbl.staff.title.description"/></p>
					</div>
					<%--				SEARCH BAR--%>
					<form id="formSearch" action="/staff/index" class="input-group ml-auto col-sm-12 col-md-4"
						  method="post">
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
						<table id="tblStaff" class="table table-hover table-bordered">
							<thead class=" text-primary">
							<tr>
								<th><s:message code="tbl.staff.col.id"/></th>
								<th><s:message code="tbl.staff.col.photo"/></th>
								<th><s:message code="tbl.staff.col.name"/></th>
								<th><s:message code="tbl.staff.col.gender"/></th>
								<th><s:message code="tbl.staff.col.birthday"/></th>
								<th><s:message code="tbl.staff.col.email"/></th>
								<th><s:message code="tbl.staff.col.phone"/></th>
								<th><s:message code="tbl.staff.col.salary"/></th>
								<th><s:message code="tbl.staff.col.depart"/></th>
								<th><s:message code="tbl.title.action"/></th>
							</tr>
							</thead>
							<tbody>

							</tbody>

						</table>
						<div class="d-flex justify-content-between">
							<ul id="pagination" name="pagination" class="pagination"></ul>
							<button class="btn btn-success" id="btnShowFormInsert">
								<i class="fas fa-user-plus"></i> <s:message code="tbl.staff.btn.addNew"/>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
