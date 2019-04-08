function pageClick(k) {
	$(k).parent().find("div").removeClass("active");
	$(k).addClass("active");
	var text = $(k).text();
	$("#flTitle").text($(k).text());
    var url = "";
    if (text == "品牌管理") {
        url = "/manager/brandList";
    } else if (text == "颜色管理") {
        url = "/manager/colorList";
    } else if (text == "尺码管理") {
        url = "/manager/sizeList";
    } else if (text == "分类管理") {
        url = "/manager/toGoodsClassList";
    } else if (text == "商品列表") {
        url = "/manager/goodsList";
    } else if (text == "库存列表"){
        url = "/manager/stockList";
    } else if (text == "评价管理") {
        url = "/manager/commentList";
    } else if (text == "供应商管理") {
        url = "/manager/supplierList";
    } else if (text == "客户管理") {
        url = "/manager/customerList";
    } else if (text == "采购单管理"){
        url = "/manager/supplierOrderList";
    } else if (text == "销售单管理"){
        url = "/manager/orderList";
    } else if (text == "个人管理"){
        url = "/manager/userInfo";
    } else if (text == "店铺管理"){
        url= "/manager/store";
    } else if (text == "店铺列表管理"){
        url= "/manager/storeList";
    }
    $(".content").load(url);
}