var isInsertMode = true;
var current_page = 1;
var RECORD_CONFIG = {
    pageSize: 10,
    APIUrl: '/api/record',
    tableId: '#tblRecord',
    defaultAvatarName: 'default-avatar.png',
    modalDetail: $('#recordModalDetail'),
    modalStaffSelect: $('#staffSelectModal'),
    modal: $('#recordModal'),
    buildStaffSelect: function(){
        let search = $('#formSearchStaffSelect [name="search"]').val();
        $.ajax({
            url: '/api/staff/count',
            data: {
                search: search
            },
            success: function (count) {
                RECORD_CONFIG.staffSelectPagination(count, search);
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    staffSelectPagination: function (totalRow, search) {
        let container = $('#tblStaffSelect tbody');
        if (totalRow == 0) {container.html('');}

        $('#paginationStaffSelect').twbsPagination('destroy');


        $('#paginationStaffSelect').twbsPagination({
            totalPages: Math.ceil(totalRow / 5),
            visiblePages: 5,
            onPageClick: function (event, page) {
                $.ajax({
                    url: '/api/staff',
                    data: {
                        start: (page - 1) * 5,
                        size: 5,
                        search: search
                    },
                    success: function (staffs) {
                        container.hide();
                        container.html('');
                        container.append(RECORD_CONFIG.templateBuildStaffSelect(staffs));
                        container.fadeIn("slow");

                        //Tạo sự kiện cho nút chọn staff
                        $('[name="btnSelectStaff"]').click(function () {
                            let staffId = $(this).attr('data-select');
                            let staffName = $(this).attr('data-name');
                            let staffPhoto = $(this).attr('data-image');
                            $('#formRecord [name="staffId"]').val(staffId);

                            //set avatar, name, id
                            $('#recordModal [name="lblStaffName"]').html(staffName);
                            $('#recordModal [name="lblStaffId"]').html(staffId);
                            $('#recordModal .avatar').css('background-image', `url('/resources/image/staff/${staffPhoto != 'null' ? staffPhoto : RECORD_CONFIG.defaultAvatarName}')`);

                            //Set giá trị staffId vào input form
                            $('#recordModal input[name="staffId"]').val(staffId);

                            //đóng modal select lại
                            RECORD_CONFIG.modalStaffSelect.modal('hide');
                        });
                    }
                });
            },
            first: 'Trang đầu',
            prev: '<i class="fas fa-angle-left"></i>',
            next: '<i class="fas fa-angle-right"></i>',
            last: 'Trang cuối'
        });

    },
    buildPagination: function () {
        let searchStr = $('#recordForm input[name="search"]').val();
        let month = $('#month-record').val();

        // alert(searchStr + ", " + month);

        //Gọi ajax lấy dữ liệu total count
        $.ajax({
            url: RECORD_CONFIG.APIUrl + "/count",
            data: {
                month: month,
                search: searchStr
            },
            success: function (count) {
                // alert('count: ' + count)
                if (searchStr.length != 0)
                    RECORD_CONFIG.pagination(count, month, searchStr);
                else
                    RECORD_CONFIG.pagination(count, month, null);
            }
        })
    },
    pagination: function pagination(totalrow, month, search) {
        let table = this.tableId;
        let container = $(this.tableId + ' > tbody');

        if (totalrow == 0) {
            container.html('');
        }
        $('[name="pagination"]').twbsPagination('destroy');

        let totalPages = Math.ceil(totalrow / this.pageSize);
        $('[name="pagination"]').twbsPagination({

            totalPages: totalPages,
            visiblePages: 5,
            startPage: (current_page > totalPages) ? totalPages : current_page,
            onPageClick: function (event, page) {
                current_page = page;
                $.ajax({
                    url: RECORD_CONFIG.APIUrl,
                    data: {
                        start: (page - 1) * RECORD_CONFIG.pageSize,
                        size: RECORD_CONFIG.pageSize,
                        month: month,
                        search: search
                    },
                    success: function (data) {
                        container.hide();
                        container.html('');
                        container.append(RECORD_CONFIG.templateBuild(data));
                        container.fadeIn("slow");
                        //Tạo sự kiện cho nút showDetail
                        $('[name="btnShowDetail"]').click(function () {
                            let staffId = $(this).attr('data-detail');
                            RECORD_CONFIG.showModalDetail(staffId);
                            //Thiết lập sự kiện khi nhấn nút xem chi tiết
                        });

                        //Sự kiện khi click vào ảnh trên bảngg
                        $('[name="btnShowAvatar"]').click(function () {

                            let modalAvatar = $('#avatarModal');

                            let img = $('#avatarModal .modal-body img');
                            let src = $(this).prop('src');
                            img.attr('src', src);
                            modalAvatar.modal('show');
                        });
                    }
                })
            },
            first: 'Trang đầu',
            prev: '<i class="fas fa-angle-left"></i>',
            next: '<i class="fas fa-angle-right"></i>',
            last: 'Trang cuối'
        });
    },
    templateBuild: function (recordDTOs) {
        let html = "";
        $.each(recordDTOs, function (index, record) {
            let isHaveDepart = record.departName != null;

            html += '<tr>';
            html += '<td><b>' + record.staffId + '</b></td>';
            html += `<td><img name="btnShowAvatar" width="64px" src="/resources/image/staff/${record.staffPhoto != null ? record.staffPhoto : RECORD_CONFIG.defaultAvatarName}" </td>`;
            html += '<td>' + record.staffName + '</td>';
            html += '<td ' + (!isHaveDepart ? 'style="color: red; font-weight: bold;"' : '') + '>' + (isHaveDepart ? record.departName : 'không có') + '</td>';
            html += '<td>' + record.numRecordGood + '</td>';
            html += '<td>' + record.numRecordBad + '</td>';
            html += '<td><b class="record-result-status">' + ((record.numRecordGood - record.numRecordBad) > 0 ? 'Thưởng' : 'Phạt') + '</b></td>';
            html += '<td>';
            html += '<button name="btnShowDetail" class="btn btn-outline-info" href="#" data-detail="' + record.staffId + '" data-toggle="tooltip" data-placement="top" title="Xem thông tin chi tiết"><i class="fas fa-eye"></i></button>';
            html += '</td>';
            html += '<tr>';
        });
        return html;
    },
    templateBuildStaffSelect: function (staffs) {
        let html = "";
        $.each(staffs, function (index, staff) {

            html += '<tr>';
            html += '<td><b>' + staff.id + '</b></td>';
            html += `<td><img name="btnShowAvatar" width="64px" src="/resources/image/staff/${staff.photo != null ? staff.photo : RECORD_CONFIG.defaultAvatarName}" </td>`;
            html += '<td>' + staff.name + '</td>';
            html += `<td>${staff.depart != null ? staff.depart.name : 'không có'}</td>`;
            html += '<td><button name="btnSelectStaff" class="btn btn-outline-info" href="#" data-select="' + staff.id + '" data-name="' + staff.name + '" data-image="' + staff.photo + '">Chọn</button></td>';
            html += '<tr>';
        });
        return html;
    },
    showStatistic: function () {
        let eRecordTotal = $('#st-record-total');
        let eRecordGood = $('#st-record-good');
        let eRecordBad = $('#st-record-bad');
        let month = $('#month-record').val();

        //Gọi ajax lấy thông tin thống kê;
        $.ajax({
            url: RECORD_CONFIG.APIUrl + '/statistic/' + month,
            success: function (data) {
                eRecordTotal.html((data[0] != null ? data[0] : '0'));
                eRecordGood.html((data[1] != null ? data[1] : '0'));
                eRecordBad.html((data[2] != null ? data[2] : '0'));
            }
        })
    },
    showModalInsert: function () {
        $('#titleRecordModal').html('Thêm phiếu ghi nhận');
        //set attribute data-active lại cho nút submit
        $('#btnSubmitForm').attr('data-active', 'insert').html('Thêm mới');

        //clear form và avatar
        $('#recordModal [name="lblStaffId"]').html('');
        $('#recordModal [name="lblStaffName"]').html('Chọn nhân viên');
        $('#recordModal input[name="staffId"]').val('');
        $('#recordModal input[name="recordId"]').val('');
        $('#recordModal .avatar').css('background-image', `url('/resources/image/staff/${RECORD_CONFIG.defaultAvatarName}')`);

        $(`#recordModal [name="type"]`).attr('checked', false);
        $('#recordModal [name="date"]').val('');
        $('#recordModal [name="reason"]').val('');

        isInsertMode = true;

        $('#recordModal').modal('show');
    },
    showModalUpdate: function (recordId) {
        //Gọi ajax lấy dữ liệu của record theo mã truyền vào sau
        $('#titleRecordModal').html('Thay đổi thông tin phiếu ghi nhận số #' + recordId);
        $.ajax({
            url: RECORD_CONFIG.APIUrl + '/' + recordId,
            success: function (record) {
                //gọi tiếp ajax đến để lấy thông tin staff của record
                $.ajax({
                    url: '/api/staff/' + record.staff.id,
                    success: function (staff) {
                        //set thông tin của record và staff lên form modal và show modal
                        $('#btnSubmitForm').attr('data-active', 'update').html('cập nhật');

                        //set giá trị cho form và avatar
                        $('#recordModal [name="lblStaffId"]').html(staff.id);
                        $('#recordModal [name="lblStaffName"]').html(staff.name);
                        $('#recordModal input[name="staffId"]').val(staff.id);
                        $('#recordModal input[name="recordId"]').val(record.id);
                        $('#recordModal .avatar').css('background-image', `url('/resources/image/staff/${staff.photo != null ? staff.photo : RECORD_CONFIG.defaultAvatarName}')`);

                        $(`#recordModal [name="type"]`).attr('checked', false);
                        $(`#recordModal [name="type"][value="${record.type}"]`).attr('checked', true);
                        $('#recordModal [name="date"]').val(moment(record.date, 'DD-MM-YYYY').format('YYYY-MM-DD'));
                        $('#recordModal [name="reason"]').val(record.reason);


                        isInsertMode = false;

                        $('#recordModal').modal('show');

                    },
                    error: function (jqXHR) {
                        AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
                    }
                })
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    showModalDetail: function (staffId) {
        let month = $('#month-record').val();
        let modalDetailBody = $('#contentModalRecordDetail');

        $.ajax({
            url: '/api/staff/' + staffId,
            success: function (staff) {
                $.ajax({
                    url: '/api/record/staff/' + staff.id,
                    data: {month: month},
                    success: function (records) {
                        //lấy về html tạo ra nội dung các thẻ báo cáo của staffId truyền vào
                        htmlContent = RECORD_CONFIG.templateRecordDetail(staff, records);

                        //Clear nội dung cũ của containerModalDetail
                        modalDetailBody.html('');

                        //Gán giá trị nội dung các thẻ record chi tiết vào containerModalDetail
                        modalDetailBody.html(htmlContent);

                        //set sự kiện cho các nút sau khi đã được tạo
                        $('[name="btnEdit"]').click(function () {
                            let recordId = $(this).attr('data-edit');
                            RECORD_CONFIG.showModalUpdate(recordId);
                        })
                        $('[name="btnDelete"]').click(function () {
                            let recordId = $(this).attr('data-delete');
                            let staffId = $(this).attr('data-staff');
                            RECORD_CONFIG.deleteRecord(recordId, staffId);
                        })

                        //Show lên màn hình modalDetail
                        RECORD_CONFIG.modalDetail.modal('show');
                    },
                })
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        });
    },
    showModalSelectStaff: function () {

    },
    templateRecordDetail: function (staff, records) {

        //Gen phần hình ảnh và tên nhân viên
        let header =
            `<div class="mx-auto mb-3">` +
            `<div class="row mb-2">` +
            `<div class="avatar-card mx-auto mb-3">` +
            `<div class="avatar shadow-lg" style="background: url('/resources/image/staff/${staff.photo != null ? staff.photo : RECORD_CONFIG.defaultAvatarName}'); background-size: 100% 100%;">` +
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
                `<div>` +
                `<button type="button" name="btnEdit" class="btn btn-info" data-edit="${record.id}">` +
                `<i class="fas fa-edit"></i>` +
                `</button>` +

                `<button type="button" name="btnDelete" class="btn btn-danger" data-delete="${record.id}" data-staff="${record.staff.id}">` +
                `<i class="far fa-trash-alt"></i>` +
                `</button>` +
                `</div>` +
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
    },
    insertRecord: function () {
        //Kiểm tra tính hơp lệ đầu vào
        if (RECORD_CONFIG.validForm() == false) {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Thông tin nhập vào không hợp lệ, hãy kiểm tra lại!");
            return false;
        }

        //lấy dữ liệu
        let staffId = $('#formRecord [name="staffId"]').val();
        let type = $('#formRecord [name="type"]:checked').val();
        let date = $('#formRecord [name="date"]').val();
        let reason = $('#formRecord [name="reason"]').val();

        //gửi ajax insert
        $.ajax({
            url: RECORD_CONFIG.APIUrl,
            data: {
                staffId: staffId,
                type: type,
                date: date,
                reason: reason
            },
            type: 'POST',
            success: function (record) {
                RECORD_CONFIG.modal.modal('hide');
                RECORD_CONFIG.buildPagination();
                AlertUtil.showSimpleSuccess("Thông báo", "Đã thêm phiếu báo cáo thành công!");
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    updateRecord: function () {

        //Kiểm tra tính hơp lệ đầu vào
        if (RECORD_CONFIG.validForm() == false) {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Thông tin nhập vào không hợp lệ, hãy kiểm tra lại!");
            return false;
        }

        //lấy dữ liệu
        let recordId = $('#formRecord [name="recordId"]').val();
        let staffId = $('#formRecord [name="staffId"]').val();
        let type = $('#formRecord [name="type"]:checked').val();
        let date = $('#formRecord [name="date"]').val();
        let reason = $('#formRecord [name="reason"]').val();

        //gửi ajax update
        $.ajax({
            url: RECORD_CONFIG.APIUrl,
            data: {
                update: 'update',
                recordId: recordId,
                staffId: staffId,
                type: type,
                date: date,
                reason: reason
            },
            type: 'POST',
            success: function (record) {
                RECORD_CONFIG.modal.modal('hide');
                RECORD_CONFIG.buildPagination();
                AlertUtil.showSimpleSuccess("Thông báo", `Cập nhật phiếu báo cáo số <b>${recordId}</b> thành công!`);
                RECORD_CONFIG.showModalDetail(staffId);
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    deleteRecord: function (recordId, staffId) {
        //gửi ajax delete
        $.ajax({
            url: RECORD_CONFIG.APIUrl + '/' + recordId,
            type: 'DELETE',
            success: function (record) {
                RECORD_CONFIG.modal.modal('hide');
                RECORD_CONFIG.buildPagination();
                AlertUtil.showSimpleSuccess("Thông báo", `Xóa phiếu báo cáo số <b>${recordId}</b> thành công!`);
                RECORD_CONFIG.showModalDetail(staffId);
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    validForm: function () {
        $('#formRecord').validate({
            ignore: [],
            errorPlacement: function (error, element)
            {
                if (element.attr('name') == 'type')
                    error.insertAfter(element.parent().parent());
                else if (element.attr('name') == 'staffId')
                    error.insertAfter(element);
                else
                    error.insertAfter(element);
            },
            rules: {
                staffId: {
                    required: true
                },
                type: {
                    required: true
                },
                date: {
                    required: true
                },
                reason: {
                    required: true,
                    minlength: 10
                }
            },
            messages: {
                staffId: {
                    required: 'Chưa chọn nhân viên để ghi nhận'
                },
                type: {
                    required: 'Loại ghi nhận chưa được chọn'
                },
                date: {
                    required: 'Ngày ghi nhận chưa được chọn'
                },
                reason: {
                    required: 'Lý do ghi nhận chưa có',
                    minlength: 'Độ dài lý do ghi nhận quá ngắn'
                }
            }
        });

        return $('#formRecord').valid();
    }
}

$(document).ready(function () {
    //Xây dựng phân trang
    RECORD_CONFIG.buildPagination();

    //Show thông tin thống kê
    RECORD_CONFIG.showStatistic();
});

$('#month-record').change(function () {
    //Xây dựng phân trang lại
    RECORD_CONFIG.buildPagination();

    //Show thông tin thống kê
    RECORD_CONFIG.showStatistic();
})

//Sự kiện form search record submit
$('#recordForm').submit(function () {
    RECORD_CONFIG.buildPagination();
    return false;
});

//Sự kiện form search staff select submit
$('#formSearchStaffSelect').submit(function () {
    RECORD_CONFIG.buildStaffSelect();
    return false;
});


//Sự kiện click show modal record để insert
$('#btnShowFormInsert').click(function () {
    RECORD_CONFIG.showModalInsert();
});

//Sự kiện click chọn nhân viên
$('#showSelectStaffRecord').click(function () {
    RECORD_CONFIG.buildStaffSelect();
   $('#staffSelectModal').modal('show');
});


//Sự kiện nhấn submit form
$('#btnSubmitForm').click(function () {
    let active = $(this).attr('data-active');
    if (active == 'insert')
    {
        RECORD_CONFIG.insertRecord()
    }
    else if (active == 'update')
    {
        RECORD_CONFIG.updateRecord();
    }
});

//bắt sự kiện khi dòng hiển thị thay đổi
$('[name="rowShow"]').change(function () {
    let pageSize = $(this).val();
    if (pageSize != -1)
    {
        RECORD_CONFIG.pageSize = pageSize;
        RECORD_CONFIG.buildPagination();
    }

});