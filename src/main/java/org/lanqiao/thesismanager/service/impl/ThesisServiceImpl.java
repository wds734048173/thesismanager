package org.lanqiao.thesismanager.service.impl;

import org.lanqiao.thesismanager.mapper.ThesisMapper;
import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Thesis;
import org.lanqiao.thesismanager.service.IThesisService;
import org.lanqiao.thesismanager.utils.DataMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        Map<Integer,String> thesisTypeMap = DataMapUtil.getThesisTypeMap();
        for(Thesis thesis : thesisList){
            int type = thesis.getType();
            if(thesisTypeMap.containsKey(type)){
                thesis.setTypeStr(thesisTypeMap.get(type));
            }
        }
        return thesisList;
    }

    @Override
    public int getThesisModelCount(Condition condition) {
        return thesisMapper.selectThesisModelCount(condition);
    }

    @Override
    public int getMaxValue(Thesis thesis) {
        return thesisMapper.selectMaxValue(thesis);
    }

    @Override
    public void addThesisModel(Thesis thesis) {
        thesisMapper.insertThesisModel(thesis);
    }

    @Override
    public void removeThesisById(int id) {
        thesisMapper.deleteThesisById(id);
    }
}
