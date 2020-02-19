<div class="col-12 col-md-8 col-lg-4 mx-auto border mb-5 form-p-5 mt-5">
	<form action="/staff/index">
		<input type="hidden" name="id" class="form-control"/>
		<label>Họ tên:</label>
		<input name="name" class="form-control" value="${param.name}" required/>
		<div class="row mt-2">
			<div class="col-12">
				<label>Giới tính:</label>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender" ${staff.gender || param.gender ? 'checked' : ''} value="true" required>
					<label class="form-check-label">Nam</label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="gender" ${staff.gender || param.gender ? 'checked' : ''} value="true" required>
					<label class="form-check-label">Nữ</label>
				</div>
			</div>
		</div>
		<div class="row mt-1">
			<div class="col">
				<label>Số điện thoại:</label>
				<input name="phone" class="form-control" value="${param.phone}" required/>
			</div>
			<div class="col">
				<label>Ngày sinh:</label>
				<input type="date" name="birthday" class="form-control" value="${param.birthday}" data-date-format="dd/MM/yyyy" required/>
			</div>
		</div>
		<%--			<label>Ảnh</label>--%>
		<%--			<div class="custom-file">--%>
		<%--				<input type="file" class="custom-file-input" id="customFile">--%>
		<%--				<label class="custom-file-label" for="customFile" data-browse="Chọn file">Choose file</label>--%>
		<%--			</div>--%>
		<div class="row mt-2">
			<div class="col-12">
				<label>Email:</label>
				<input name="email" class="form-control" value="${param.email}" required/>
			</div>
		</div>

		<div class="row mt-1">
			<div class="col">
				<label>Lương:</label>
				<input name="salary" class="form-control" value="${param.salary}" required/>
			</div>
			<div class="col">
				<label>Phòng ban:</label>
				<select name="depart_id" class="form-control">
					<c:forEach items="${departs}" var="d">
						<option value="${d.id}" ${(s.depart.id == d.id || param.depart_id == d.id) ? 'selected' : ''}>${d.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<label>Ghi chú: </label>
		<textarea name="notes" class="form-control">${param.notes}</textarea>
		<br>
		<input type="submit" name="insert" class="btn btn-info btn-block form-control" value="Thêm mới">

	</form>
</div>