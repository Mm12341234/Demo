$("#btn_Login").click(function () {
    var data = "username=" +$("#userId").val() + "&password=" +$("#userPassword").val() + "&captcha=" +1234;
    $.ajax({
        url: "../sys/login",
        type: "POST",
        dataType: "json",
        data: data,
        success: function (result) {
            if (result.code == 0) {//登录成功
                parent.location.href = '/index.html';
            } else {
                alert("密码或账户错误");
            }
        }
    });
});