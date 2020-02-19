class AlertUtil {
    static test()
    {
        alert('day la statis test()');
    }

    static showConfirm(title, msg, {callbackYes, callbackNo})
    {
        Swal.fire({
            title: title,
            html: msg,
            type: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.value) {
                callbackYes();
            }
            else
            {
                callbackNo();
            }
        });
    }

    static showSimpleSuccess(title, msg)
    {
        swal.fire({
            type: 'success',
            title: title,
            html: msg,
            showConfirmButton: false,
            timer: 2000
        })
    }

    static showSimpleError(title, msg)
    {
        swal.fire({
            type: 'error',
            title: title,
            html: msg,
            showConfirmButton: false,
            timer: 4000
        })
    }
}

class HelperUtil {

    static stringToDate(_date,_format, _format2)
    {
        var date = moment(_date, _format).format(_format2);
        //
        // var formatLowerCase=_format.toLowerCase();
        // var formatItems=formatLowerCase.split(_delimiter);
        // var dateItems=_date.split(_delimiter);
        // var monthIndex=formatItems.indexOf("mm");
        // var dayIndex=formatItems.indexOf("dd");
        // var yearIndex=formatItems.indexOf("yyyy");
        // var month=parseInt(dateItems[monthIndex]);
        // month-=1;
        //
        // var formatedDate = new Date(`${dateItems[yearIndex]}-${month}-${dateItems[dayIndex]}`);
        // alert(`${dateItems[yearIndex]}-${month}-${dateItems[dayIndex]}`);
        return date;
    }

}
//
// AlertUtil.showConfirm("Thông báo", "bạn có chắc muốn xóa không?", function () {
//     alert("yess");
// }, function () {
//     alert("noo")
// })