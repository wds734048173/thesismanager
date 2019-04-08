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
    public Manager selectManager(Manager manager);
}
