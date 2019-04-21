package org.lanqiao.thesismanager.pojo;

import lombok.*;

import java.util.Date;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:50
 * @Description:论文管理
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Thesis {
    private int id;
    //指导老师id（或管理员id）
    private int tId;
    //学生id（0表示管理员上传的模板）
    private int sId;
    private String sIdStr;
    //论文地址
    private String thesisAddress;
    //论文备注
    private String remark;
    //论文提交次数（通过是否有tid判断是谁提交的）
    private int count;
    //论文状态（0上传1已操作（学生已修改，老师已批阅））
    private int state;
    private String stateStr;
    //论文类型（0开题报告，1毕业设计（论文））
    private int type;
    private String typeStr;
    //提交论文人类型（0管理员1学生2老师）
    private int commitType;
    private String commitTypeStr;

    private Date ctime;
    private Date rtime;
}
