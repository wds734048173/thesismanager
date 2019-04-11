function pageClick(k) {
	$(k).parent().find("div").removeClass("active");
	$(k).addClass("active");
	var text = $(k).text();
	$("#flTitle").text($(k).text());
    var url = "";
    if (text == "论文模板管理") {
        url = "/teacher/thesisModelList";
    } else if (text == "论文管理") {
        url = "/teacher/thesisList";
    } else if (text == "个人信息管理") {
        url = "/teacher/userInfo";
    } else if (text == "密码管理") {
        url = "/teacher/toUpdatePassword";
    }
    $(".content").load(url);
}