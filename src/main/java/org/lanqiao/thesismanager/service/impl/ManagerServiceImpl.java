package org.lanqiao.thesismanager.service.impl;

import org.lanqiao.thesismanager.mapper.ManagerMapper;
import org.lanqiao.thesismanager.pojo.Manager;
import org.lanqiao.thesismanager.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:55
 * @Description:
 */
@Service
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    ManagerMapper managerMapper;
    @Override
    public Manager getManager(Manager manager) {
        return managerMapper.selectManager(manager);
    }

    @Override
    public void modifyManager(Manager manager) {
        managerMapper.updateManager(manager);
    }

    @Override
    public Manager getManagerById(int id) {
        return managerMapper.selectManagerById(id);
    }

    @Override
    public void modifyManagerPassword(Manager manager) {
        managerMapper.updatePassword(manager);
    }
}
