// 获取路径
function GetUrlRelativePath() {
    var url = document.location.toString();
    var arrUrl = url.split("//");

    var start = arrUrl[1].indexOf("/");
    var relUrl = arrUrl[1].substring(start);// stop省略，截取从start开始到结尾的所有字符

    if (relUrl.indexOf("?") != -1) {
        relUrl = relUrl.split("?")[0];
    }
    console.log('start='+ start + "--- relUrl=" + relUrl);
    return relUrl;
}

$(document).ready(function () {
    var url = GetUrlRelativePath();
    var patt1 = new RegExp(url);
    $(document.body).find('a').each(function (i, obj) {
        var str = $(obj).attr('href');
        if (patt1.test(str)) {
            $(obj).parent().addClass("layui-this");
        }
    });
});