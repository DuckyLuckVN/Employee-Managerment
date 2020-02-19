var current_page = 1;
var USER_CONFIG = {
    pageSize: 10,
    APIUrl: '/api/user',
    tableId: '#tblUser',
    modal: $('#userModal'),
    buildPagination: function()
    {
        let searchStr = $("input[name='search']").val();

        //Gọi ajax lấy dữ liệu total count
        $.ajax({
            url: USER_CONFIG.APIUrl + "/count",
            data: {
                search: searchStr
            },
            success: function (count)
            {
                USER_CONFIG.pagination(count, searchStr);
            }
        })
    },
    pagination : function pagination(totalrow, search)
    {

        let table = this.tableId;
        let container = $(this.tableId + ' > tbody');

        if (totalrow == 0)
        {
            container.html('');
        }
        $('[name="pagination"]').twbsPagination('destroy');

        let totalPages = Math.ceil(totalrow/USER_CONFIG.pageSize);
        $('[name="pagination"]').twbsPagination({
            totalPages: totalPages,
            visiblePages: 5,
            startPage: (current_page > totalPages) ? totalPages : current_page,
            onPageClick: function (event, page) {
                $.ajax({
                    url: USER_CONFIG.APIUrl,
                    data: {
                        start: (page - 1) * USER_CONFIG.pageSize,
                        size: USER_CONFIG.pageSize,
                        search: search
                    },
                    success: function (data) {
                        container.hide();
                        container.html('');
                        container.append(USER_CONFIG.templateBuild(data));
                        container.fadeIn('');

                        $('[data-toggle="tooltip"]').tooltip();

                        //set sự kiện cho các nút edit_user, delete_user
                        $('[name="edit_user"]').click(function () {
                            let username = $(this).attr('data-edit');
                            USER_CONFIG.showModalUpdate(username);
                        });

                        $('[name="delete_user"]').click(function () {
                            let username = $(this).attr('data-delete');
                            AlertUtil.showConfirm("Yêu cầu xác nhận", "Bạn có chắc muốn xóa tài khoản <b>" + username + "</b> này?", {
                                callbackYes: function () {
                                    USER_CONFIG.deleteUser(username);
                                }
                            });
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
    templateBuild: function (users)
    {
        let html = "";
        $.each(users, function (index, user)
        {
            html += '<tr>';
            html += '<td>' + user.username + '</td>';
            html += '<td>' + user.fullname + '</td>';
            html += '<td>' + '#############' + '</td>';
            html += '<td>';
            html += '<button name="edit_user" class="btn btn-info" data-edit="'+user.username+'" data-toggle="tooltip" data-placement="top" title="Thay đổi thông tin"><i class="fas fa-edit"></i></button>';
            html += '<button name="delete_user" class="btn btn-danger" data-delete="'+user.username+'" data-toggle="tooltip" data-placement="top" title="Xóa người dùng"><i class="fas fa-user-times"></i></button>';
            html += '</td>';
            html += '<tr>';
        });
        return html;
    },
    showModalInsert: function () {
        //clear form
        $('[name=username]').val('').attr('readonly', false);
        $('[name=password]').val('');
        $('[name=fullname]').val('');

        //set title
        $('#titleUserModal').html('Thêm tài khoản mới');
        $('#btnSubmitForm').html("Thêm tài khoản").attr('data-active', 'insert');

        //show modal
        USER_CONFIG.modal.modal('show');

    },
    showModalUpdate(id) {
        $.ajax({
            url: USER_CONFIG.APIUrl + '/' + id,
            success: function (user) {
                //set title
                $('#titleUserModal').html('Cập nhật tài khoản');
                $('#btnSubmitForm').html("Cập nhật").attr('data-active', 'update');

                //set info to form
                $('[name=username]').val(user.username).attr('readonly', true);
                $('[name=fullname]').val(user.fullname);
                $('[name=password]').val(user.password);

                //show modal
                USER_CONFIG.modal.modal('show');
            }
        })
    },
    insertUser: function () {
        //Kiểm tra tính hơp lệ đầu vào
        if (USER_CONFIG.validForm() == false) {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Thông tin nhập vào không hợp lệ, hãy kiểm tra lại!");
            return false;
        }
        let username = $('[name="username"]').val();
        let fullname = $('[name="fullname"]').val();
        let password = $('[name="password"]').val();

        //gọi ajax insert
        $.ajax({
            url: USER_CONFIG.APIUrl,
            type: 'POST',
            data: {
                username: username,
                password: password,
                fullname: fullname
            },
            success: function(data)
            {
                USER_CONFIG.modal.modal('hide');
                USER_CONFIG.buildPagination();
                AlertUtil.showSimpleSuccess("Thông báo", "Thêm tài khoản thành công!!!");
            },
            error: function (jqXHR) {
                console.log(jqXHR);
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    updateUser: function () {
        //Kiểm tra tính hơp lệ đầu vào
        if (USER_CONFIG.validForm() == false) {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Thông tin nhập vào không hợp lệ, hãy kiểm tra lại!");
            return false;
        }

        let username = $('[name="username"]').val();
        let fullname = $('[name="fullname"]').val();
        let password = $('[name="password"]').val();

        //gọi ajax update
        $.ajax({
            url: USER_CONFIG.APIUrl,
            type: 'POST',
            data: {
                username: username,
                password: password,
                fullname: fullname,
                update: 'update'
            },
            success: function(data)
            {
                USER_CONFIG.modal.modal('hide');
                USER_CONFIG.buildPagination();
                AlertUtil.showSimpleSuccess("Thông báo", "Cập nhật tài khoản <b>" + username + "</b> thành công!");
            },
            error: function (jqXHR) {
                console.log(jqXHR);
                AlertUtil.showSimpleError("Đã có lỗi", "Cập nhật tài khoản <b>" + username + "</b> thất bại!");
            }
        })
    },
    deleteUser: function (username) {
        //Gọi ajax delete
        $.ajax({
            url: USER_CONFIG.APIUrl + '/' + username,
            type: 'DELETE',
            success: function(data)
            {
                USER_CONFIG.modal.modal('hide');
                USER_CONFIG.buildPagination();
                AlertUtil.showSimpleSuccess("Thông báo", "Xóa tài khoản <b>" + username + "</b> thành công!");
            },
            error: function (jqXHR) {
                console.log(jqXHR);
                AlertUtil.showSimpleError("Đã có lỗi", "xóa tài khoản <b>" + username + "</b> thất bại!");
            }
        })
    },
    validForm: function () {
        $('#formUser').validate({
            rules: {
                username: {
                    required: true,
                    minlength: 6
                },
                fullname: {
                    required: true,
                    minlength: 6
                }
            },
            messages: {
                username: {
                    required: 'Tài khoản đang để trống',
                    minlength: 'Tên tài khoản quá ngắn'
                },
                fullname: {
                    required: 'Họ tên đang để trống',
                    minlength:  'Họ tên nhập vào quá ngắn'
                }
            }
        })

        return $('#formUser').valid();
    }
}

$(document).ready(function () {
  USER_CONFIG.buildPagination();
});

$('#month-record').change(function () {
    USER_CONFIG.buildPagination();
})

$('#user-form').submit(function ()
{
    USER_CONFIG.buildPagination();
    return false;
});

//Sự kiện hiển thị modal insert
$('#btnShowInsertUser').click(function () {
   USER_CONFIG.showModalInsert();
});

//Sự kiện khi nhấn nút submitForm
$('#btnSubmitForm').click(function () {
    let active = $(this).attr('data-active');
    if (active == 'insert')
    {
        USER_CONFIG.insertUser();
    }
    else if (active = 'update')
    {
        AlertUtil.showConfirm("Yêu cầu xác nhận", "Bạn có chắc muốn lưu thay đổi này?", {
            callbackYes: function () {
                USER_CONFIG.updateUser();
            }
        })
    }
});

//bắt sự kiện khi dòng hiển thị thay đổi
$('[name="rowShow"]').change(function () {
    let pageSize = $(this).val();
    if (pageSize != -1)
    {
        USER_CONFIG.pageSize = pageSize;
        USER_CONFIG.buildPagination();
    }

});