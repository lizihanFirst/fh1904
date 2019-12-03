function ajaxSetup(token) {
    $.ajaxSetup({
        beforeSend: function (XMLHttpRequest) {
            XMLHttpRequest.setRequestHeader("token", token);
        },
        complete:function(XMLHttpRequest,textStatus) {
            //通过XMLHttpRequest取得响应头，sessionstatus
            var noright = XMLHttpRequest.getResponseHeader("NORIGHT");
            // alert("返回的状态码"+noright)
            if (noright == "2006") {
                //这里怎么处理在你，这里跳转的登录页面
                location.href = "/login/login.html";
            }
        }
    })
}
