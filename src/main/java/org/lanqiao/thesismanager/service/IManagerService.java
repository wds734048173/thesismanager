package org.lanqiao.thesismanager.service;

import org.lanqiao.thesismanager.pojo.Manager;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:54
 * @Description:
 */
public interface IManagerService {
    //查询管理员信息
    public Manager getManager(Manager manager);
    //修改管理员信息
    public void modifyManager(Manager manager);
    //通过id获取管理员信息
    public Manager getManagerById(int id);
    //修改管理员密码
    public void modifyManagerPassword(Manager manager);
}
