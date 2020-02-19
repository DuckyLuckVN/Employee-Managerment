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
				<button type="button" class="btn btn-outline-light" data-dismiss="modal">Đóng</button>
			</div>
		</div>
	</div>
</div>

<%--			Modal Record Detail		--%>
<div class="modal fade " id="recordModalDetail" role="dialog" tabindex="-1" aria-hidden="true"
	 aria-labelledby="departModal">
	<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg" role="document">
		<div class="modal-content card">
			<div class="modal-header card-header card-header-danger row">
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


<%--			Modal Record	--%>
<div class="modal fade " id="recordModal" role="dialog" tabindex="-1" aria-hidden="true" aria-labelledby="departModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content card">
			<div class="modal-header card-header card-header-warning row">
				<h5 class="card-title mx-auto" id="titleRecordModal">Thêm mới phiếu báo cáo</h5>
				<button type="button" style="position: absolute; right: 20px;" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" id="contentModalRecord">
				<%--					//Avatar--%>
				<div class="row mb2 mx-auto">
					<%--					Hình ảnh nhân viên--%>
					<div class="avatar-card mx-auto mb-3" id="showSelectStaffRecord">
						<div class="avatar shadow-lg">

						</div>
					</div>
				</div>
				<%--					Tên và mã nhân viên--%>
				<div class="text-center" style="font-size: 20px;">
					<span class="badge badge-danger" style="font-size: 20px;" name="lblStaffId">105</span>
					<b name="lblStaffName">Nguyễn Đại Hào</b>
				</div>

				<hr>
				<%--				Nội dung form--%>
				<form id="formRecord">
					<input name="recordId" type="hidden">
					<input name="staffId" type="hidden">
					<div class="row">
						<%--						Loại báo cáo--%>
						<div class="col-12 mb-3">
							<div class="form-group pb-0">
								<label>Loại báo cáo</label>
							</div>
							<div class="form-group">
								<div class="form-check form-check-radio">
									<label class="form-check-label">
										<input class="form-check-input" type="radio" name="type" id="rdoTypeGood" value="0">
										Khen thưởng
										<span class="circle">
										<span class="check"></span>
									</span>
									</label>
									<label class="form-check-label">
										<input class="form-check-input" type="radio" name="type" id="rdoTypeBad" value="1">
										Phạt hoặc kỷ luật
										<span class="circle">
										<span class="check"></span>
									</span>
									</label>
								</div>
							</div>

						</div>
						<%--						Ngày báo cáo--%>
						<div class="col-12 mb-3">
							<div class="form-group">
								<label>Ngày báo cáo</label>
								<input type="date" name="date" class="form-control">
							</div>
						</div>

						<%--						Lý do--%>
						<div class="col-12">
							<div class="form-group">
								<label for="reason">Lý do</label>
								<textarea name="reason" class="form-control" id="reason" rows="3"></textarea>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer py-1">
				<button type="button" class="btn btn-outline-light" data-dismiss="modal">Đóng</button>
				<button id="btnSubmitForm" type="button" date-active="insert" class="btn btn-success">Lưu báo cáo
				</button>
			</div>
		</div>
	</div>
</div>


<%--			Modal Staff Select	--%>
<div class="modal fade " id="staffSelectModal" role="dialog" tabindex="-1" aria-hidden="true"
	 aria-labelledby="departModal">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content card">
			<div class="modal-header card-header card-header-success row">
				<h5 class="card-title mx-auto" id="titleStaffSelectModal">Chọn nhân viên</h5>
				<button type="button" style="position: absolute; right: 20px;" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" id="contentStaffSelectModal">
				<div class="card">
					<div class="card-header card-header-primary bg-dark row mx-0">
						<div class="mr-auto col-sm-12 col-md-6">
							<h4 class="card-title " id="testClick"><s:message code="tbl.staff.title"/> </h4>
							<p class="card-category"><s:message code="tbl.staff.title.description"/></p>
						</div>
						<%--				SEARCH BAR--%>
						<form id="formSearchStaffSelect" action="/staff/index"
							  class="input-group ml-auto col-sm-12 col-md-6"
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
	</div>
</div>


<div class="container-fluid">
	<div class="row">
		<div class="col-sm-12 col-md-4">
			<div class="card card-stats">
				<div class="card-header card-header-info card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="far fa-calendar"></i>
					</div>
					<p class="card-category"><s:message code="stats.record.total"/></p>
					<h3 id="st-record-total" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Tổng số phiếu đã được báo cáo trong tháng này</span>
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
						<i class="far fa-calendar-check"></i>
					</div>
					<p class="card-category"><s:message code="stats.record.totalGood"/></p>
					<h3 id="st-record-good" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Tổng số phiếu 'Thưởng' có trong tháng này</span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12 col-md-4">
			<div class="card card-stats">
				<div class="card-header card-header-danger card-header-icon">
					<div class="card-icon rounded-circle">
						<i class="far fa-calendar-times"></i>
					</div>
					<p class="card-category"><s:message code="stats.record.totalBad"/></p>
					<h3 id="st-record-bad" class="card-title">0
					</h3>
				</div>
				<div class="card-footer">
					<div class="stats">
						<span>Tổng số phiếu 'Phạt' có trong tháng này</span>
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
						<h4 class="card-title "><s:message code="tbl.record.title"/></h4>
						<p class="card-category"><s:message code="tbl.record.title.description"/></p>
					</div>
					<%--				SEARCH BAR--%>
					<form id="recordForm" action="/staff/index" class="input-group ml-auto col-sm-12 col-md-4"
						  method="post">
						<div class="form-group bmd-form-group w-100">
							<%--							<label class="bmd-label-floating" style="color: ghostwhite;">Tìm kiếm</label>--%>
							<label class="mb-0" style="color: ghostwhite;"><s:message code="common.search"/></label>
							<input name="search" type="text" class="form-control" style="color: white"
								   value="${param.search}">
						</div>
						<select name="month" id="month-record" class="custom-select custom-select-sm rounded"
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
						<table id="tblRecord" class="table table-hover table-bordered">
							<thead class=" text-primary">
							<tr>
								<th><s:message code="tbl.record.col.staffId"/></th>
								<th><s:message code="tbl.staff.col.photo"/></th>
								<th><s:message code="tbl.record.col.staffName"/></th>
								<th><s:message code="tbl.record.col.departName"/></th>
								<th><s:message code="tbl.record.col.goodRecord"/></th>
								<th><s:message code="tbl.record.col.badRecord"/></th>
								<th><s:message code="tbl.record.col.result"/></th>
								<th><s:message code="tbl.title.action"/></th>
							</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div class="d-flex justify-content-between">
							<ul name="pagination" class="pagination"></ul>
							<button class="btn btn-success" id="btnShowFormInsert" style="font-weight: bold;">
								<i class="fas fa-plus"></i> <s:message code="tbl.record.btn.addNew"/>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
