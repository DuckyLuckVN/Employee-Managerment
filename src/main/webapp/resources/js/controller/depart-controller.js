var current_page = 1;
var DEPART_CONFIG = {
    pageSize: 5,
    APIUrl: '/api/depart',
    tableId: '#tblDepart',
    modal: $('#departModal'),
    buildPagination: function () {
        let search = $('input[name="search"]').val();

        //Gọi ajax để load dữ liệu total row về
        $.ajax({
            url: DEPART_CONFIG.APIUrl + "/count",
            data: {"search": search},
            success: function (totalRow) {
                DEPART_CONFIG.pagination(totalRow);
            }
        })
    },
    //Hàm xây dựng nội dung phân trang cho các bảng
    pagination: function pagination(totalRow) {
        let table = $(DEPART_CONFIG.tableId);
        let container = $(DEPART_CONFIG.tableId + ' > tbody');
        let search = $('input[name="search"]').val();

        if (totalRow == 0) {
            container.html('');
        }

        $('[name="pagination"]').twbsPagination('destroy');

        let totalPages = Math.ceil(totalRow / DEPART_CONFIG.pageSize);
        $('[name="pagination"]').twbsPagination({
            totalPages: totalPages,
            visiblePages: 5,
            startPage: (current_page > totalPages) ? totalPages : current_page,
            onPageClick: function (event, page) {
                current_page = page;
                $.ajax({
                    url: DEPART_CONFIG.APIUrl,
                    cache: false,
                    data: {
                        start: (page - 1) * DEPART_CONFIG.pageSize,
                        size: DEPART_CONFIG.pageSize,
                        search: search
                    },
                    success: function (data) {
                        container.hide();
                        container.html('');
                        container.append(DEPART_CONFIG.templateBuild(data));
                        container.fadeIn('slow');

                        $('[data-toggle="tooltip"]').tooltip();

                        //thêm sự kiện cho các nút nhấn edit và delete depart
                        $('[name="edit_depart"]').click(function () {
                            let id = $(this).attr('data-edit');
                            DEPART_CONFIG.showModalUpdate(id);
                        })

                        $('[name="delete_depart"]').click(function () {
                            let id = $(this).attr('data-delete');
                            AlertUtil.showConfirm("Yêu cầu các nhận", "Bạn có chắc muốn xóa phòng ban <b>" + id + "</b> này không?", {
                                // callbackYes: DEPART_CONFIG.deleteDepart(id)
                                callbackYes: function () {
                                    DEPART_CONFIG.deleteDepart(id);
                                }
                            });
                        })
                    }
                })
            },
            first: 'Trang đầu',
            prev: '<i class="fas fa-angle-left"></i>',
            next: '<i class="fas fa-angle-right"></i>',
            last: 'Trang cuối'
        });
    },
    templateBuild: function (data) {
        let html = "";
        $.each(data, function (index, value) {
            html += '<tr>';
            html += '<td><b>' + value.id + '</b></td>';
            html += '<td>' + value.name + '</td>';
            html += '<td>' + value.staffCount + '</td>';
            html += '<td>';
            html += '<button name="edit_depart" class="btn btn-info" href="#" data-edit="' + value.id + '" data-toggle="tooltip" data-placement="top" title="Thay đổi thông tin"><i class="fas fa-edit"></i></button>';
            html += '<button name="delete_depart" class="btn btn-danger" href="#" data-delete="' + value.id + '" data-toggle="tooltip" data-placement="top" title="Xóa phòng ban"><i class="fas fa-user-times"></i></button>';
            html += '</td>';
            html += '<tr>';
        })
        return html;
    },
    showStatistic: function () {
        //Gọi ajax để load dữ liệu thống kê tiêu chuẩn về, sau đó set nội dung thống kê trên website lại
        $.ajax({
            url: DEPART_CONFIG.APIUrl + '/statistic/0',
            success: function (data) {
                $('#depart-total').html(data[0]);
                $('#depart-good').html(data[1]);
                $('#depart-bad').html(data[2]);
            }
        })
    },
    showModalInsert: function () {
        //Clear form
        $('[name=id]').val('');
        $('[name=name]').val('');
        $('[name=id]').attr('readonly', false);

        //set title
        $('#titleStaffModal').html('Thêm mới phòng ban');
        $('#btnSubmitForm').html('Thêm mới');

        //set value action modal
        $('#btnSubmitForm').attr('data-active', 'insert');
        DEPART_CONFIG.modal.modal('show');


    },
    showModalUpdate: function (id) {

        //set title
        $('#titleStaffModal').html('Cập nhật phòng ban');
        $('#btnSubmitForm').html('Cập nhật');

        //set value action modal
        $('#btnSubmitForm').attr('data-active', 'update');

        //set value cho form dựa vào id truyền vào và hiện modal lên
        $.ajax({
            url: DEPART_CONFIG.APIUrl + '/' + id,
            type: 'GET',
            success: function (depart) {
                $('[name=id]').val(depart.id).attr('readonly', true);
                $('[name=name]').val(depart.name);

                DEPART_CONFIG.modal.modal('show');
            }
        });

    },
    insertDepart: function () {
        //Kiểm tra tính hơp lệ đầu vào
        if (DEPART_CONFIG.validForm() == false) {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Thông tin nhập vào không hợp lệ, hãy kiểm tra lại!");
            return false;
        }

        let id = $('[name="id"]').val();
        let name = $('[name="name"]').val();

        $.ajax({
            url: DEPART_CONFIG.APIUrl,
            type: 'POST',
            data: {
                id: id,
                name: name
            },
            success: function (depart) {
                DEPART_CONFIG.modal.modal('hide');
                DEPART_CONFIG.buildPagination();
                DEPART_CONFIG.showStatistic();
                AlertUtil.showSimpleSuccess("Thông báo", "Thêm phòng ban <b>" + depart.name + "</b> thành công!");
            },
            error: function (jqXHR) {
                console.log(jqXHR);
                AlertUtil.showSimpleError("Đã có lỗi sảy ra!", jqXHR.responseText);
            }
        })
    },
    updateDepart: function () {
        //Kiểm tra tính hơp lệ đầu vào
        if (DEPART_CONFIG.validForm() == false) {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Thông tin nhập vào không hợp lệ, hãy kiểm tra lại!");
            return false;
        }

        let id = $('[name="id"]').val();
        let name = $('[name="name"]').val();

        $.ajax({
            url: DEPART_CONFIG.APIUrl,
            type: 'POST',
            data: {
                id: id,
                name: name,
                update: 'update'
            },
            success: function(data) {
                //Tắt bảng modal đi và update lại table
                DEPART_CONFIG.modal.modal('hide');
                DEPART_CONFIG.buildPagination();

                AlertUtil.showSimpleSuccess("Thông báo", "Cập nhật thành công phòng ban có mã <b>" + id + "</b>");
            },
            error: function () {
                AlertUtil.showSimpleError("Thông báo", "Cập nhật thất bại, đã có lỗi sảy ra!!!");
            }
        })
    },
    deleteDepart: function (id) {
        $.ajax({
            url: DEPART_CONFIG.APIUrl + "/" + id,
            type: 'DELETE',
            cache: false,
            success: function () {
                DEPART_CONFIG.modal.modal('hide');
                DEPART_CONFIG.buildPagination();
                DEPART_CONFIG.showStatistic();
                AlertUtil.showSimpleSuccess("Thông báo", "Đã xóa thành công phòng ban có mã: <b>" + id + "</b>");
            },
            error: function (jqXHR) {
                console.log(jqXHR);
                AlertUtil.showSimpleError("Thông báo", "Xóa thất bại, đã có lỗi sảy ra!");
            }
        })
    },
    validForm: function () {
        $('#formDepart').validate({
           rules: {
               id: {
                   required: true
               },
               name: {
                   required: true
               }
            },
            messages: {
                id: {
                    required: 'Mã phòng ban đang để trống'
                },
                name: {
                    required: 'Tên phòng ban đang để trống'
                }
            }
        });
        return $('#formDepart').valid();
    }
}

$(document).ready(function () {
    //Xây dựng phân trang
    DEPART_CONFIG.buildPagination();

    //hiển thị thông tin thống kê
    DEPART_CONFIG.showStatistic();

})

//Bắt sự kiện search submit
$('#form-depart').submit(function () {
    DEPART_CONFIG.buildPagination();
    return false;
});

//Bắt sự kiện nhấn nút show insert form
$('#btnShowInsertDepart').click(function () {
    DEPART_CONFIG.showModalInsert();
});

//Bắt sự kiện nút submit form
$('#btnSubmitForm').click(function () {
    let active = $(this).attr('data-active');
    if (active == 'insert') {
        DEPART_CONFIG.insertDepart();
    } else if (active == 'update') {
        DEPART_CONFIG.updateDepart();
    }
});

//bắt sự kiện khi dòng hiển thị thay đổi
$('[name="rowShow"]').change(function () {
    let pageSize = $(this).val();
    if (pageSize != -1)
    {
        DEPART_CONFIG.pageSize = pageSize;
        DEPART_CONFIG.buildPagination();
    }

});


