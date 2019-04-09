package org.lanqiao.thesismanager.service.impl;

import org.lanqiao.thesismanager.mapper.ThesisMapper;
import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Thesis;
import org.lanqiao.thesismanager.service.IThesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:58
 * @Description:
 */
@Service
public class ThesisServiceImpl implements IThesisService {

    @Autowired
    ThesisMapper thesisMapper;
    @Override
    public List<Thesis> getThesisModelList(Condition condition) {
        List<Thesis> thesisList = thesisMapper.selectThesisModelList(condition);
        for(Thesis thesis : thesisList){
            int type = thesis.getType();
            if(type == 0){
                thesis.setTypeStr("开题报告");
            }else{
                thesis.setTypeStr("毕业设计");
            }
        }
        return thesisList;
    }

    @Override
    public int getThesisModelCount(Condition condition) {
        return thesisMapper.selectThesisModelCount(condition);
    }
}
