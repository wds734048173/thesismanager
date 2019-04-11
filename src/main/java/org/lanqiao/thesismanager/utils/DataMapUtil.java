package org.lanqiao.thesismanager.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: WDS
 * @Date: 2019/1/11 14:55
 * @Description:
 */
public class DataMapUtil {
    private static Map<Integer,String> sexMap;
    private static Map<Integer,String> stateMap;
    private static Map<Integer,String> thesisTypeMap;

    //普通的状态
    public static Map<Integer,String> getStateMap(){
        stateMap = new HashMap<Integer,String>();
        stateMap.put(0,"启用");
        stateMap.put(1,"停用");
//        stateMap.put(2,"删除");
        return stateMap;
    }

    //性别
    public static Map<Integer,String> getSexMap(){
        sexMap = new HashMap<Integer,String>();
        sexMap.put(0,"男");
        sexMap.put(1,"女");
        return sexMap;
    }

    //论文类型
    public static Map<Integer,String> getThesisTypeMap(){
        thesisTypeMap = new HashMap<Integer,String>();
        thesisTypeMap.put(0,"课题申请表");
        thesisTypeMap.put(1,"开题报告");
        thesisTypeMap.put(2,"任务书");
        thesisTypeMap.put(3,"中期报告");
        thesisTypeMap.put(4,"毕业设计");
        return thesisTypeMap;
    }
}
