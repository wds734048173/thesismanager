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
}
