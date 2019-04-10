package org.lanqiao.thesismanager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lanqiao.thesismanager.pojo.Manager;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:51
 * @Description:
 */
@Mapper
public interface ManagerMapper {
    //获取管理员信息
    public Manager selectManager(Manager manager);
    //修改管理员信息
    public void updateManager(Manager manager);
    //通过id获取管理员信息
    public Manager selectManagerById(int id);
    //修改密码
    public void updatePassword(Manager manager);
    //通过用户名获取用户信息
    public Manager selectManagerByName(String username);
    //新增管理员信息
    public void insertManager(Manager manager);
}
