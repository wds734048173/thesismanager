package org.lanqiao.thesismanager.pojo;

import lombok.*;

import java.util.Date;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:49
 * @Description:老师账号
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Teacher {
    private int id;
    //用户名
    private String username;
    //密码
    private String password;
    //性别（0男1女）
    private int sex;
    private String sexStr;
    //真实名称
    private String realname;
    //联系电话
    private String telphone;
    //邮箱
    private String email;
    //状态（0启用1停用2删除）
    private int state;
    private String stateStr;

    private Date ctime;
    private Date rtime;
}
