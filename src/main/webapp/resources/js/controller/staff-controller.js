$.validator.addMethod("dateAfter", function(value, element, params) {
    try {
            return false;
    } catch (e) {}
    return false;
}, 'message');
var current_page = 1;
var STAFF_CONFIG = {
    pageSize: 10,
    APIUrl: '/api/staff',
    tableId: '#tblStaff',
    defaultAvatarName: 'default-avatar.png',
    modal: $('#staffModal'),
    buildPagination: function () {
        let searchStr = $("input[name='search']").val();

        //Gọi ajax lấy dữ liệu total count
        $.ajax({
            url: STAFF_CONFIG.APIUrl + "/count",
            data: {
                search: searchStr
            },
            success: function (count) {
                // alert('count: ' + count)
                if (searchStr.length != 0) {
                    STAFF_CONFIG.pagination(count);
                } else {
                    STAFF_CONFIG.pagination(count);
                }
            }
        })
    },
    pagination: function pagination(totalrow) {

        let table = this.tableId;
        let container = $(this.tableId + ' > tbody');
        let search = $('input[name="search"]').val();

        if (totalrow == 0) {
            container.html('');
        }

        let totalPages = Math.ceil(totalrow / STAFF_CONFIG.pageSize);

        $('[name="pagination"]').twbsPagination('destroy');
        $('[name="pagination"]').twbsPagination({
            totalPages: Math.ceil(totalrow / STAFF_CONFIG.pageSize),
            visiblePages: 5,
            startPage: (current_page > totalPages) ? totalPages : current_page,
            onPageClick: function (event, page) {
                current_page = page;
                $.ajax({
                    url: STAFF_CONFIG.APIUrl,
                    data: {
                        start: (page - 1) * STAFF_CONFIG.pageSize,
                        size: STAFF_CONFIG.pageSize,
                        search: search
                    },
                    success: function (data) {
                        container.hide();
                        container.html('');
                        container.append(STAFF_CONFIG.templateBuild(data));
                        container.fadeIn('slow' );
                        $('[data-toggle="tooltip"]').tooltip();

                        //Set sự kiện cho các nút đã sinh
                        $('[name="edit_staff"]').click(function () {
                            STAFF_CONFIG.showModalUpdate($(this).attr("data-edit"));
                        });

                        $('[name="delete_staff"]').click(function () {
                            //hiển thị thông báo xác nhận xóa
                            let staffId = $(this).attr("data-delete");
                            let msg = "Bạn có chắc muốn xóa nhân viên có mã <b>" + staffId + "</b> này không?";
                            AlertUtil.showConfirm("Yêu cầu xác nhận", msg, {
                                callbackYes: function () {
                                    STAFF_CONFIG.deleteStaff(staffId);
                                }
                            });
                        });

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
    templateBuild: function (data) {
        let html = "";
        $.each(data, function (index, staff) {
            let isHaveBirthday = staff.birthday != null ? true : false;
            let isHaveDepart = staff.depart != null ? true : false;
            html += '<tr>';
            html += '<td><b>' + staff.id + '</b></td>';
            html += `<td><img name="btnShowAvatar" width="64px" src="/resources/image/staff/${staff.photo != null ? staff.photo : STAFF_CONFIG.defaultAvatarName}" </td>`;
            html += '<td>' + staff.name + '</td>';
            html += '<td>' + (staff.gender ? 'Nam' : 'Nữ') + '</td>';
            html += '<td' + (!isHaveBirthday ? ' style="color: red; font-weight: bold;"' : '') + '>' + (isHaveBirthday ? staff.birthday : 'chưa nhập') + '</td>';
            html += '<td>' + staff.email + '</td>';
            html += '<td>' + staff.phone + '</td>';
            html += '<td>' + staff.salary.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,') + ' đ</td>';
            html += '<td><b' + (!isHaveDepart ? ' style="color: red;"' : '') + '>' + (isHaveDepart ? staff.depart.name : 'không có') + '</b></td>';
            html += '<td>';
            html += '<button name="edit_staff" class="btn btn-info" href="#" data-edit="' + staff.id + '" data-toggle="tooltip" data-placement="top" title="Thay đổi thông tin"><i class="fas fa-edit"></i></button>';
            html += '<button name="delete_staff" class="btn btn-danger" href="#" data-delete="' + staff.id + '" data-toggle="tooltip" data-placement="top" title="Xóa nhân viên"><i class="fas fa-user-times"></i></button>';
            html += '</td>';
            html += '<tr>';
        })
        return html;
    },
    showStatistic: function () {
        //Gọi ajax lấy dữ liệu thống kê
        $.ajax({
            url: STAFF_CONFIG.APIUrl + '/statistic',
            success: function (data) {
                let eStaffStCount = $('#st-staff-count');
                let eStaffStMale = $('#st-staff-male');
                let eStaffStFemale = $('#st-staff-famale');

                eStaffStCount.html(data[0]);
                eStaffStMale.html(data[1]);
                eStaffStFemale.html(data[2]);
            }
        })

    },
    loadDepart: function () {
        $.ajax({
            url: '/api/depart/all',
            success: function (departs) {
                let eDepartSelect =  $('#departSelect');
                $.each(departs, function (index, depart) {
                    eDepartSelect.append(new Option(depart.name, depart.id));
                })
            }
        })
    },
    showModalInsert: function()
    {
        $('#titleStaffModal').html("Thêm mới nhân viên")

        //clear form
        $('[name="id"]').val('');
        $('[name="name"]').val('');
        $('[name="email"]').val('');
        $('[name="gender"]').attr('checked', false);
        $('[name="phone"]').val('');
        $('[name="salary"]').val('');
        $('[name="photo"]').val('');
        $('[name="birthday"]').val('');
        $('[name="notes"]').val('').html('');
        $('[name="departId"]').val('nothing');

        $('.avatar').css('background-image', '');

        $('#submitForm').attr('data-action', 'insert');
        $('#submitForm').html("Lưu nhân viên");
        STAFF_CONFIG.modal.modal('show');
    },
    showModalUpdate: function (id) {
        $.ajax({
            url: STAFF_CONFIG.APIUrl + '/' + id,
            success: function (staff) {
                $('#titleStaffModal').html("Cập nhật nhân viên")

                //set giá trị của staff cần update ngược lên form, sau đó hiển thị modal update lên
                $('input[name="id"]').val(staff.id);
                $('input[name="name"]').val(staff.name);


                if (staff.birthday != null)
                {
                    $('input[name="birthday"]').val(moment(staff.birthday, "DD-MM-YYYY").format("YYYY-MM-DD"));
                }
                else
                    $('input[name="birthday"]').val('');

                //bỏ check tất cả
                $('[name="gender"]').attr('checked', false);
                //check theo giá trị giới tính
                $('input[name="gender"][value=' + staff.gender + ']').attr('checked', true);

                $('input[name="email"]').val(staff.email);
                $('input[name="salary"]').val(staff.salary);
                $('input[name="phone"]').val(staff.phone);
                $('textarea[name="notes"]').html(staff.notes);
                $('textarea[name="notes"]').val(staff.notes);

                $('select[name="departId"]').val(staff.depart != null ? staff.depart.id : 'nothing');

                //xử lý hình ảnh, nếu staff không có hình thì nó sẽ set avatar về thành avatar mặc định
                if(staff.photo != null) {
                    $('.avatar').css('background-image', 'url(/resources/image/staff/' + staff.photo + ')');
                }
                else {
                    $('.avatar').css('background-image', 'url(/resources/image/staff/' + STAFF_CONFIG.defaultAvatarName + ')');
                }

                $('#submitForm').attr('data-action', 'update');
                $('#submitForm').html("Cập nhật nhân viên");
                STAFF_CONFIG.modal.modal();
            }
        })
    },
    deleteStaff: function (id) {
        $.ajax({
            url: STAFF_CONFIG.APIUrl + '/' + id,
            type: 'DELETE',
            success: function (data) {
                AlertUtil.showSimpleSuccess('Thông báo', "Đã xóa nhân viên số <b>" + id + "</b> thành công!");
                //Tải lại bảng dữ liệu
                STAFF_CONFIG.buildPagination();
            },
            error: function (jqXHR, error, errorThrown) {
                alert(jqXHR);
                console.log(jqXHR);
            }
        })
    },
    insertStaff: function () {
        //kiểm tra validation
        if (STAFF_CONFIG.validForm() == false)
        {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Vui lòng kiểm tra lại dữ liệu vừa nhập");
            return;
        }

        //lấy ra giá trị và insert
        // alert($('[name="photo"]').val());
        let form = document.getElementById("formStaff");
        let data = new FormData($('#formStaff')[0]);
        let data2 = new FormData();

        data2.append('name',     $('[name="name"]').val())
        data2.append('gender',   $('[name="gender"]:checked').val())
        data2.append('birthday', $('[name="birthday"]').val())
        data2.append('photo', $('[name="photo"]')[0].files[0]);
        data2.append('email',    $('[name="email"]').val())
        data2.append('phone',    $('[name="phone"]').val())
        data2.append('salary',   $('[name="salary"]').val())
        data2.append('notes',   $('[name="notes"]').val())
        data2.append('departId',   $('[name="departId"]').val())

        console.log("sending insert:");
        console.log(data);
        $.ajax({
            url: STAFF_CONFIG.APIUrl,
            type: 'POST',
            data: data2,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                STAFF_CONFIG.modal.modal('hide');
                STAFF_CONFIG.buildPagination();
                AlertUtil.showSimpleSuccess("Thông báo", "Thêm nhân viên mới thành công!");

            },
            error: function (jqXHR) {
               AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }

        })
    },
    updateStaff: function () {
        //kiểm tra validation
        if (STAFF_CONFIG.validForm() == false)
        {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Vui lòng kiểm tra lại dữ liệu vừa nhập");
            return;
        }
        
        let data = new FormData($('#formStaff')[0]);
        data.append("update", "updateStaff");

        $.ajax({
            url: STAFF_CONFIG.APIUrl,
            data: data,
            type: 'POST',
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                //Đóng modal update lại, sau ođ1 thông báo thành công!
                STAFF_CONFIG.modal.modal('hide');
                AlertUtil.showSimpleSuccess("Thông báo", "Cập nhật thành công!!!");
                STAFF_CONFIG.buildPagination();
                STAFF_CONFIG.showStatistic();

            },
            error: function (jqXHR) {
                console.log(jqXHR);
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    validForm: function (callback) {
        //Validate form staff
        let valid = $('#formStaff').validate({
            errorPlacement: function (error, element) {
                if (element.attr('name') == 'gender')
                    error.insertAfter(element.parent().parent());
                else
                    error.insertAfter(element)
            },
            rules: {
                name: {
                    minlength: 3,
                    required: true,
                },
                gender: {
                    required: true
                },
                email: {
                    email: true,
                    required: true
                },
                phone: {
                    digits: true,
                    rangelength: [9, 13],
                    required: true
                },
                salary: {
                    digits: true,
                    min: 0,
                    required: true
                },
                birthday: {
                    required: true,
                }
            },
            messages: {
                name: {
                    minlength: 'Độ dài tối thiểu phải là 9',
                    required: 'Họ tên đang bỏ trống'
                },
                gender: {
                    required: 'Giới tính chưa được chọn'
                },
                email: {
                    email: 'Email nhập vào không hợp lệ',
                    required: 'Email đang bỏ trống'
                },
                phone: {
                    digits: 'Số điện thoại phải là số',
                    required: 'Số điện thoại đang để trống',
                    rangelength: 'Độ dài số không hợp lệ'
                },
                salary: {
                    digits: 'Lương nhập vào phải là số',
                    min: 'Lương nhập vào phải lớn hơn 0',
                    required: 'Lương đang bỏ trống'
                },
                birthday: {
                    required: 'Ngày sinh không được bỏ trống',
                }
            }
        });

        return $('#formStaff').valid();
    }
}

$(document).ready(function () {
    //Gọi ajax để load dữ liệu total row về
    STAFF_CONFIG.buildPagination();

    //Show dữ liệu thống kê
    STAFF_CONFIG.showStatistic();

    STAFF_CONFIG.loadDepart();

    $('h5').click(function () {
        if (STAFF_CONFIG.validForm())
        {
            alert('OK');
        }
    })


});

$('#formSearch').submit(function () {
    STAFF_CONFIG.buildPagination();
    return false;
});

//Kiểm tra sự kiện chọn file ảnh và show ảnh photo staff
function showImage(inputFile) {
    if (inputFile.files && inputFile.files[0])
    {
        let reader = new FileReader();

        reader.onload = function (e) {
            $('.avatar-card > .avatar').css('background-image', "url(" + e.target.result + ")");
        }

        reader.readAsDataURL(inputFile.files[0]);
    }
}
//Sự kiện khi file ảnh được chọn
$('#filePhoto').change(function () {
    showImage(this);
})

$('#btnShowFormInsert').click(function () {
    STAFF_CONFIG.showModalInsert();
});

//Bắt sự kiện nút submit của form được click
$('#submitForm').click(function () {
    let action = $(this).attr('data-action');

    if (action == 'insert')
    {
        STAFF_CONFIG.insertStaff();
    }
    else if (action == 'update')
    {
        AlertUtil.showConfirm("Yêu cầu xác nhận", "Bạn có chắc muốn cập nhật không??", {
            callbackYes: function () {
                STAFF_CONFIG.updateStaff();
            }
        })
    }
});

//bắt sự kiện khi dòng hiển thị thay đổi
$('[name="rowShow"]').change(function () {
   let pageSize = $(this).val();
   if (pageSize != -1)
   {
       STAFF_CONFIG.pageSize = pageSize;
       STAFF_CONFIG.buildPagination();
   }
});

//Bắt sự kiện hiện modal gửi mail
$('[name="btnShowSendMail"]').click(function () {
    $('#avatarModal').modal('hide');
   $('#sendMailModal').modal('show');
});

