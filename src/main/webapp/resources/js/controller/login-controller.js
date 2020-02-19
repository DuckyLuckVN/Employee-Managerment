var EDIT_PROFILE = {
    showForm: function (username) {
        $.ajax({
            url: `/api/user/${username}`,
            success: function (user) {
                $('#formEditProfile [name="username"]').val(user.username);
                $('#formEditProfile [name="fullname"]').val(user.fullname);

                //show modal
                $('#changeProfileModal').modal('show');
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    sendUpdate: function () {
        if (EDIT_PROFILE.validForm() == false)
        {
            AlertUtil.showSimpleError("Lỗi nhập liệu", "Vui lòng kiểm tra lại các trường đã nhập!");
            return;
        }

        let formData = new FormData($('#formEditProfile')[0]);
        $.ajax({
            url: '/api/user/changepassword',
            data: {
                update: 'update',
                username:  $('#formEditProfile [name="username"]').val(),
                fullname:  $('#formEditProfile [name="fullname"]').val(),
                password:  $('#formEditProfile [name="password"]').val(),
                newPassword:  $('#formEditProfile [name="newPassword"]').val(),
                reNewPassword:  $('#formEditProfile [name="reNewPassword"]').val()
            },
            type: "POST",
            success: function (user) {
                //clear form
                $('#formEditProfile [name="fullname"]').val('');
                $('#formEditProfile [name="password"]').val('');
                $('#formEditProfile [name="reNewPassword"]').val('');
                $('#formEditProfile [name="newPassword"]').val('');

                //hide modal
                $('#changeProfileModal').modal('hide');
                AlertUtil.showSimpleSuccess("Thông báo", "Cập nhật lại thông tin tài khoản thành công")
            },
            error: function (jqXHR) {
                AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
            }
        })
    },
    validForm: function () {
        $('#formEditProfile').validate({
            rules: {
                fullname: {
                    required: true,
                    minlength: 10
                },
                password: {
                    required: true
                },
                newPassword: {
                    required: true,
                    minlength: 6
                },
                reNewPassword: {
                    required: true,
                    minlength: 6,
                    equalTo: '[name="newPassword"]'
                }
            },
            messages: {
                fullname: {
                    required: 'Họ tên không đang để trống',
                    minlength: 'Họ tên nhập vào quá ngắn'
                },
                password: {
                    required: 'Mật khẩu cũ đang bỏ trống'
                },
                newPassword: {
                    required: 'Mật khẩu mới chưa được nhập',
                    minlength: 'Độ dài mật khẩu mới quá ngắn'
                },
                reNewPassword: {
                    required: 'Mật khẩu xác nhận chưa được nhập',
                    minlength: 'Độ dài mật khẩu xác nhận quá ngắn',
                    equalTo: 'Mật khẩu xác nhận không trùng khớp'
                }
            }
        });

        return $('#formEditProfile').valid();
    }
}

$("#formLogin [name=\"remember\"]").on('change', function() {
    if ($(this).is(':checked')) {
        $(this).attr('value', 'true');
    } else {
        $(this).attr('value', 'false');
    }

    $('#checkbox-value').text($('#checkbox1').val());
});

function logout() {
    //send ajax check
    $.ajax({
        url: '/api/user/logout',
        type: 'GET',
        success: function (data) {
            let timerInterval
            Swal.fire({
                title: 'Đăng xuất thành công!',
                type: 'success',
                html: 'Chuyển đến trang <b>đăng nhập</b> trong <strong></strong> tíc tắc.',
                timer: 3000,
                onBeforeOpen: () => {
                    Swal.showLoading()
                    timerInterval = setInterval(() => {
                        Swal.getContent().querySelector('strong')
                            .textContent = Swal.getTimerLeft()
                    }, 100)
                },
                allowOutsideClick: false,
                onClose: () => {
                    clearInterval(timerInterval)
                }
            }).then((result) => {
                if (
                    /* Read more about handling dismissals below */
                    result.dismiss === Swal.DismissReason.timer
                ) {
                    window.location.replace('/login');
                }
            })
        },
        error: function (jqXHR) {
            AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
        }
    })
}

//Sự kiện nhấn nút login của trang login.jsp
$('#formLogin').submit(function () {
    //lấy dữ liệu
    let username = $('#formLogin [name="username"]').val();
    let password = $('#formLogin [name="password"]').val();
    let remember = $('#formLogin [name="remember"]').val();
    // alert(remember);

    //send ajax check
    $.ajax({
        url: '/api/user/login',
        type: 'POST',
        data: {
            username: username,
            password: password,
            remember: remember
        },
        success: function (data) {
            let timerInterval
            Swal.fire({
                title: 'Đăng nhập thành công!',
                type: 'success',
                html: 'Bạn sẽ được chuyển đến <b>trang chính</b> trong <strong></strong> tíc tắc.',
                timer: 3000,
                onBeforeOpen: () => {
                    Swal.showLoading()
                    timerInterval = setInterval(() => {
                        Swal.getContent().querySelector('strong')
                            .textContent = Swal.getTimerLeft()
                    }, 100)
                },
                allowOutsideClick: false,
                onClose: () => {
                    clearInterval(timerInterval)
                }
            }).then((result) => {
                if (
                    /* Read more about handling dismissals below */
                    result.dismiss === Swal.DismissReason.timer
                ) {
                    window.location.replace('/index');
                }
            })
        },
        error: function (jqXHR) {
            AlertUtil.showSimpleError("Đã có lỗi sảy ra", jqXHR.responseText);
        }
    })

    return false;
});

//Sự kiện nhấn logout của navbar
$('#navBarUser [name="btnLogout"]').click(function () {
    logout();
    return false;
})

$('#btnLogout').click(function () {
    logout();
    return false;
})

$('[name="btnChangeLang"]').click(function () {
    let lang = $(this).attr("data-lang");
    document.cookie = `lang=${lang}; path=/`
    Location.reload();
    return false;
})

$(document).ready(function () {

    $('#btnShowEditProfile').click(function () {
        let username = $(this).attr('data-edit');
        EDIT_PROFILE.showForm(username);
        return false;
    });

    $('#formEditProfile').submit(function () {
        EDIT_PROFILE.sendUpdate();
        return false;
    });
})