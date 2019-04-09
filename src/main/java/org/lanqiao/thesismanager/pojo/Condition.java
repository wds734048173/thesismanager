package org.lanqiao.thesismanager.pojo;

import lombok.*;

/**
 * @Auther: WDS
 * @Date: 2019/1/11 21:03
 * @Description:
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Condition {

    //状态
    private int state;
    //用户名
    private String username;
    //真实姓名
    private String realname;
    //学生id
    private int sId;
    //学生id
    private int tId;
    //论文类型
    private int type;
    //分页
    private int currentPage;
    private  int pageSize;




}
