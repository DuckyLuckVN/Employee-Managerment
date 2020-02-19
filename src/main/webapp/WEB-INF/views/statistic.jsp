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

<%--			Modal Staff Select	--%>
<div class="modal fade " id="staffSelectModal" role="dialog" tabindex="-1" aria-hidden="true"
	 aria-labelledby="departModal">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content card">
			<div class="modal-header card-header card-header-danger row">
				<h5 class="card-title mx-auto" id="titleStaffSelectModal">Chi tiết phòng ban</h5>
				<button type="button" style="position: absolute; right: 20px;" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" id="contentStaffSelectModal">
<%--					<div class="card-header card-header-primary bg-dark row mx-0">--%>
<%--						<div class="mr-auto col-sm-12 col-md-6">--%>
<%--							<h4 class="card-title " id="testClick"><s:message code="tbl.staff.title"/></h4>--%>
<%--							<p class="card-category"><s:message code="tbl.staff.title.description"/></p>--%>
<%--						</div>--%>
<%--					</div>--%>
						<div class="table-responsive">
							<table id="tblStaffSelect" class="table table-hover table-bordered">
								<thead class=" text-primary">
								<tr>
									<th><s:message code="tbl.staff.col.id"/></th>
									<th><s:message code="tbl.staff.col.photo"/></th>
									<th><s:message code="tbl.staff.col.name"/></th>
									<th><s:message code="tbl.staff.col.depart"/></th>
									<th><s:message code="tbl.title.action"/></th>
								</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
							<ul id="paginationStaffSelect" class="pagination"></ul>
						</div>
			</div>
		</div>
	</div>
</div>

<%--			Modal Record Detail		--%>
<div class="modal fade " id="recordDetailModal" role="dialog" tabindex="-1" aria-hidden="true"
	 aria-labelledby="departModal">
	<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg" role="document">
		<div class="modal-content card">
			<div class="modal-header card-header card-header-primary row">
				<h5 class="card-title mx-auto" id="titleRecordDetailModal">Thông tin chi tiết</h5>
				<button type="button" style="position: absolute; right: 20px;" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" id="contentModalRecordDetail">
				<div class="mx-auto">
					<%--					//Avatar--%>
					<div class="row mb2">
						<%--					Hình ảnh nhân viên--%>
						<div class="avatar-card mx-auto mb-3">
							<div class="avatar shadow-lg">

							</div>
						</div>
					</div>
					<%--					Tên và mã nhân viên--%>
					<div class="text-center" style="font-size: 20px;">
						<span class="badge badge-danger" style="font-size: 20px;">103</span>
						<b>Nguyễn Đại Hào</b>
					</div>
				</div>

				<div class="row">
					<div class="col-12">
						<%--						Nội dung chi tiết các record--%>
						<div class="accordion">
							<div class="card">
								<div class="card-header border border-success" id="headingOne">
									<h2 class="mb-0">
										<div class="d-flex justify-content-between">
											<button type="button" class="btn btn-link" style="font-weight: bold;"
													data-toggle="collapse" data-target="#collapseOne">
												Báo cáo số #1
											</button>
											<div>
												<%--												//Thêm--%>
												<button type="button" class="btn btn-info">
													<i class="fas fa-edit"></i>
												</button>
												<%--												//Xóa--%>
												<button type="button" class="btn btn-danger">
													<i class="far fa-trash-alt"></i>
												</button>
											</div>
										</div>
									</h2>
								</div>
								<%--								Thông tin chi tiết record--%>
								<div id="collapseOne" class="collapse" data-parent="#contentModalRecordDetail">
									<div class="card-body">
										<ul class="list-group">
											<li class="list-group-item"><b>Loại báo cáo: </b> Khen thưởng</li>
											<li class="list-group-item"><b>Ngày phát hành: </b> 30/04/1975</li>
											<li class="list-group-item"><b>Lý do: </b> Thành tích tốt trong nhiệm kỳ này
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
<%--	Select box chọn tháng thống kê--%>
	<div class="row mb-3">
		<div class="col-sm-12 col-md-4 col-lg-3">
			<select id="selectMonth" name="month" id="month-record" class="custom-select custom-select-sm rounded"
					style="font-size: 16px;">
				<option value="0" selected><s:message code="common.onThisYear"/></option>
				<option value="1"><s:message code="common.month"/> 1</option>
				<option value="2"><s:message code="common.month"/> 2</option>
				<option value="3"><s:message code="common.month"/> 3</option>
				<option value="4"><s:message code="common.month"/> 4</option>
				<option value="5"><s:message code="common.month"/> 5</option>
				<option value="6"><s:message code="common.month"/> 6</option>
				<option value="7"><s:message code="common.month"/> 7</option>
				<option value="8"><s:message code="common.month"/> 8</option>
				<option value="9"><s:message code="common.month"/> 9</option>
				<option value="10"><s:message code="common.month"/> 10</option>
				<option value="11"><s:message code="common.month"/> 11</option>
				<option value="12"><s:message code="common.month"/> 12</option>
			</select>
		</div>
	</div>
<%--	Thông tin stats của phòng ban--%>
	<div class="row mb-3">
		<div class="col-sm-12 col-lg-3 order-lg-12">
			<div class="card card-stats">
				<div class="card-header card-header-info card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-door-closed"></i>
					</div>
					<p class="card-category">Tổng phòng ban</p>
					<h3 class="card-title" id="depart-total">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
					</div>
				</div>
			</div>
			<div class="card card-stats">
				<div class="card-header card-header-success card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-home"></i>
					</div>
					<p class="card-category">Số phòng ban xuất sắc </p>
					<h3 class="card-title" id="depart-good">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
					</div>
				</div>
			</div>
			<div class="card card-stats">
				<div class="card-header card-header-danger card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-house-damage"></i>
					</div>
					<p class="card-category">Số phòng ban kỷ luật</p>
					<h3 class="card-title" id="depart-bad">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
					</div>
				</div>
			</div>
		</div>
<%--		Bảng thống kê TOP 10 phòng ban--%>
		<div class="col-sm-12 col-lg-9 order-lg-1">
			<div class="card">
				<div class="card-header card-header-danger row">
					<div class="mr-auto col-sm-12 col-md-8">
						<h4 class="card-title ">TOP 10 phòng ban xuất sắc nhất</h4>
						<p class="card-category">Bảng hiển thị danh sách 10 phòng ban có điểm thưởng cao nhất</p>
					</div>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table id="tblTop10Depart" class="table table-hover table-striped table-bordered">
							<thead class=" text-primary">
							<tr>
								<th>TOP</th>
								<th>Mã phòng</th>
								<th>Tên phòng</th>
								<th>Số nhân viên</th>
								<th>Phiếu thưởng</th>
								<th>Phiếu phạt</th>
								<th>Tổng điểm thưởng</th>
								<th>Thao tác</th>
							</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12 col-lg-3">
			<div class="card card-stats">
				<div class="card-header card-header-info card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-user-friends"></i>
					</div>
					<p class="card-category">Tổng nhân viên</p>
					<h3 id="st-staff-count" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
					</div>
				</div>
			</div>
			<div class="card card-stats">
				<div class="card-header card-header-success card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-mars"></i>
					</div>
					<p class="card-category">Nhân viên Nam</p>
					<h3 id="st-staff-male" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
					</div>
				</div>
			</div>
			<div class="card card-stats">
				<div class="card-header card-header-rose card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="fas fa-venus"></i>
					</div>
					<p class="card-category">Nhân viên Nữ</p>
					<h3 id="st-staff-female" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
					</div>
				</div>
			</div>
		</div>
		<%--		Bảng thống kê TOP 10 nhân viên xuất sắc--%>
		<div class="col-sm-12 col-lg-9">
			<div class="card">
				<div class="card-header card-header-warning row">
					<div class="mr-auto col-sm-12 col-md-8">
						<h4 class="card-title ">TOP 10 nhân viên xuất sắc nhất</h4>
						<p class="card-category">Bảng hiển thị danh sách 10 nhân viên có điểm thưởng cao nhất</p>
					</div>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table id="tblTop10Staff" class="table table-hover table-striped table-bordered">
							<thead class=" text-primary">
							<tr>
								<th>TOP</th>
								<th>ID</th>
								<th>Tên nhân viên</th>
								<th>Số phiếu thưởng</th>
								<th>Số phiếu phạt</th>
								<th>Tổng điểm thưởng</th>
								<th>Thao tác</th>
							</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
