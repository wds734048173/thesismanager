function pageClick(k) {
	$(k).parent().find("div").removeClass("active");
	$(k).addClass("active");
	var text = $(k).text();
	$("#flTitle").text($(k).text());
    var url = "";
    if (text == "老师管理") {
        url = "/manager/teacherList";
    } else if (text == "学生管理") {
        url = "/manager/studentList";
    } else if (text == "论文模板管理") {
        url = "/manager/thesisModelList";
    } else if (text == "论文管理") {
        url = "/manager/thesisList";
    } else if (text == "个人信息管理") {
        url = "/manager/userInfo";
    }else if (text == "密码管理") {
        url = "/manager/toUpdatePassword";
    }
    $(".content").load(url);
}