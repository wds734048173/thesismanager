package org.lanqiao.thesismanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: WDS
 * @Date: 2019/1/18 16:26
 * @Description:工具类，获取订单编号
 */
public class OrderNo extends Thread {
    private static long orderNum = 0l;
    private static String date ;
    private static String saleOrderPer = "CO";
    private static String buyerOrderPer = "PO";


    /**
     * 生成订单编号
     * @return
     */
    public static synchronized String getOrderNo(int typeId) {
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
            orderNum  = 0l;
        }
        orderNum ++;
        long orderNo = Long.parseLong((date)) * 10000;
        orderNo += orderNum;
        String no = "";
        if(typeId == 1){//采购单
            no = buyerOrderPer + orderNo;
        }else if(typeId == 2){//销售单
            no = saleOrderPer + orderNo;
        }
        return no;
    }
}
