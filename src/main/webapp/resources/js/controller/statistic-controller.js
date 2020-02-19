var STATS_CONFIG = {
    modalStaffSelect: $('#staffSelectModal'),
    modalRecordDetail: $('#recordDetailModal'),
    tblDepartContainer: $('#tblTop10Depart tbody'),
    tblStaffContainer: $('#tblTop10Staff tbody'),
    defaultAvatarName: 'default-avatar.png',
    selectMonth: $('#selectMonth'),
    showTop10Depart: function (month) {
        if (month == null) {
            month = this.selectMonth.val()
        }
        // Gọi ajax lấy dữ liệu top 10
        let container = STATS_CONFIG.tblDepartContainer;
        container.html('');
        container.hide();
        // Gọi ajax lấy dữ liệu top 10
        $.ajax({
            url: `/api/depart/statistic/top10record`,
            data: {month: month},
            success: function (data) {
                //Lặp để insert các dòng giá trị vào bảng
                $.each(data, function (index, value) {
                    let row;
                    row += '<tr>';
                    row += `<td><b>${index + 1}</b></td>`;
                    row += `<td>${value.id}</td>`;
                    row += `<td>${value.name}</td>`;
                    row += `<td>${value.staffCount}</td>`;
                    row += `<td>${value.numGoodRecord}</td>`;
                    row += `<td>${value.numBadRecord}</td>`;
                    row += `<td><b class="text-warning">${value.totalPoint}</b></td>`;
                    row += `<td> <button type="button" name="btnShowStaffOfDepart" class="btn btn-outline-danger" data-detail="${value.id}"><i class="far fa-eye"></i></button></td>`;
                    row += '</tr>';
                    container.append(row);
                });
                container.fadeIn('slow');

                //bắt sự kiện các nút click detail của depart
                $('[name="btnShowStaffOfDepart"]').click(function () {
                    let departId = $(this).attr('data-detail');
                    STATS_CONFIG.showStaffSelectModal(departId);
                });
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    showTop10Staff: function (month) {

        if (month == null) {
            month = this.selectMonth.val()
        }

        let container = STATS_CONFIG.tblStaffContainer;
        container.html('');
        container.hide();
        // Gọi ajax lấy dữ liệu top 10 staff
        $.ajax({
            url: `/api/staff/statistic/top10record`,
            data: {month: month},
            success: function (data) {
                //Lặp để insert các dòng giá trị vào bảng
                $.each(data, function (index, value) {
                    let row;
                    row += '<tr>';
                    row += `<td><b>${index + 1}</b></td>`;
                    row += `<td><b>${value.id}</b></td>`;
                    row += `<td>${value.name}</td>`;
                    row += `<td>${value.numGoodRecord}</td>`;
                    row += `<td>${value.numBadRecord}</td>`;
                    row += `<td><b class="text-primary">${value.totalPoint}</b></td>`;
                    row += `<td> <button type="button" name="btnShowRecordOfStaff" class="btn btn-outline-warning" data-detail="${value.id}"><i class="far fa-eye"></i></button> </td>`;
                    row += '</tr>';
                    container.append(row);
                });
                container.fadeIn('slow');

                //Bắt sự kiện nút click detail của staff
                $('[name="btnShowRecordOfStaff"]').click(function () {
                    let staffId = $(this).attr('data-detail');
                    STATS_CONFIG.showRecordOfStaffModal(staffId);
                });
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    showStatisticDepart: function (month) {
        if (month == null) {
            month = this.selectMonth.val()
        }
        //Gọi ajax để load dữ liệu thống kê tiêu chuẩn về, sau đó set nội dung thống kê trên website lại
        $.ajax({
            url: `/api/depart/statistic/${month}`,
            success: function (data) {
                $('#depart-total').html(data[0]);
                $('#depart-good').html(data[1]);
                $('#depart-bad').html(data[2]);
            }
        })
    },
    showStatisticStaff: function () {
        //Gọi ajax lấy dữ liệu thống kê
        $.ajax({
            url: '/api/staff/statistic',
            success: function (data) {
                let eStaffStCount = $('#st-staff-count');
                let eStaffStMale = $('#st-staff-male');
                let eStaffStFemale = $('#st-staff-female');

                eStaffStCount.html(data[0]);
                eStaffStMale.html(data[1]);
                eStaffStFemale.html(data[2]);
            }
        })
    },
    showStaffSelectModal: function (departId) {
        let container = $('#tblStaffSelect tbody');
        $('#titleStaffSelectModal').html(`Chi tiết nhân viên phòng ban "${departId}"`);
        container.hide();
        container.html('');
        //gọi ajax lấy về danh sách staff của phòng ban id = departId
        $.ajax({
            url: `/api/staff/depart/${departId}`,
            success: function (staffs) {
                //lặp danh sách và insert vào bảng container
                $.each(staffs, function (index, staff) {
                    let row;
                    row += `<tr>`;
                    row += `<td>${staff.id}</td>`;
                    row += `<td><img name="btnShowAvatar" width="64px" src="/resources/image/staff/${staff.photo != null ? staff.photo : STATS_CONFIG.defaultAvatarName}" </td>`;
                    row += `<td>${staff.name}</td>`;
                    row += `<td>${staff.depart.name}</td>`;
                    row += `<td><button name="btnShowRecordOfStaff" class="btn btn-outline-info" data-detail="${staff.id}"><i class="far fa-eye"></i></button></td>`;
                    row += `</tr>`;
                    container.append(row);
                })
                container.fadeIn('slow');
                STATS_CONFIG.modalStaffSelect.modal('show');

                //Bắt sự kiện click nút showRecordOfStaff
                $('[name="btnShowRecordOfStaff"]').click(function () {
                    let staffId = $(this).attr('data-detail');
                   STATS_CONFIG.showRecordOfStaffModal(staffId);
                });
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })

    },
    showRecordOfStaffModal: function (staffId) {
        let container = $('#contentModalRecordDetail');
        let month = STATS_CONFIG.selectMonth.val();

        //đặt lại tiêu đề cho modal record detail
        $('#titleRecordDetailModal').html(`Chi tiết các ghi nhận của nhân viên "${staffId}" trong ${month == 0 ? 'cả năm' : 'tháng ' + month}`);


        //Gọi ajax lấy dữ liệu của staff
        $.ajax({
            url: `/api/staff/${staffId}`,
            success: function (staff) {
                //gọi ajax lấy ra record detail đổ vào modal record detail
                $.ajax({
                    url: '/api/record/staff/' + staff.id,
                    data: {month: month},
                    success: function (records) {
                        //lấy về html tạo ra nội dung các thẻ báo cáo của staffId truyền vào
                        htmlContent = STATS_CONFIG.templateRecordDetail(staff, records);
                        //Clear nội dung cũ của containerModalDetail
                        container.html('');
                        //Gán giá trị nội dung các thẻ record chi tiết vào containerModalDetail
                        container.html(htmlContent);
                        //Show lên màn hình modalDetail
                        STATS_CONFIG.modalRecordDetail.modal('show');
                    },
                })
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })

    },
    templateRecordDetail: function (staff, records) {

        //Gen phần hình ảnh và tên nhân viên
        let header =
            `<div class="mx-auto mb-3">` +
            `<div class="row mb-2">` +
            `<div class="avatar-card mx-auto mb-3">` +
            `<div class="avatar shadow-lg" style="background: url('/resources/image/staff/${staff.photo != null ? staff.photo : STATS_CONFIG.defaultAvatarName}'); background-size: 100% 100%;">` +
            `</div>` +
            `</div>` +
            `</div>` +
            `<div class ="text-center" style="font-size: 20px;" >` +
            `<span class ="badge badge-danger" style="font-size: 20px;" > ${staff.id} </span>` +
            `<b> ${staff.name} </b>` +
            `</div>` +
            `</div>`

        //Gen nội dung các thẻ record chi tiết
        let content = '';
        $.each(records, function (index, record) {
            content +=
                `<div class="row">` +
                `<div class="col-12">` +
                `<div class="accordion" id="recordDetail${record.id}">` +
                `<div class="card my-2">` +
                `<div class="card-header border ${record.type == 0 ? 'border-success' : 'border-danger'}" id="headingOne">` +
                `<h2 class="mb-0">` +
                `<div class="d-flex justify-content-between">` +
                `<button type="button" class="btn btn-link ${record.type == 0 ? 'text-success' : 'text-danger'}" style="font-weight: bold; font-size: 14px;" data-toggle="collapse" data-target="#collapseRecordDetail${record.id}">` +
                `Báo cáo số <span class="badge ${record.type == 0 ? 'badge-success' : 'badge-danger'}" style="font-size: 15px;"> #${record.id} </span>` +
                `</button>` +
                `</div>` +
                `</h2>` +
                `</div>` +

                `<div id="collapseRecordDetail${record.id}" class="collapse" data-parent="#recordDetail${record.id}">` +
                `<div class="card-body">` +
                `<ul class="list-group">` +
                `<li class="list-group-item"><b>Loại báo cáo: </b> ${record.type == 0 ? 'Khen thưởng' : 'phạt'}</li>` +
                `<li class="list-group-item"><b>Ngày phát hành: </b> ${record.date} </li>` +
                `<li class="list-group-item"><b>Lý do: </b> ${record.reason} </li>` +
                `</ul>` +
                `</div>` +
                `</div>` +
                `</div>` +
                `</div>` +
                `</div>` +
                `</div>`
        });

        return header + content;
    }
}

$(document).ready(function () {
    STATS_CONFIG.showTop10Depart();
    STATS_CONFIG.showTop10Staff();
    STATS_CONFIG.showStatisticDepart();
    STATS_CONFIG.showStatisticStaff();
})

//Sự kiện khi select box tháng thay đổi
STATS_CONFIG.selectMonth.change(function () {
    STATS_CONFIG.showStatisticDepart();
    STATS_CONFIG.showTop10Depart();
    STATS_CONFIG.showTop10Staff();
})