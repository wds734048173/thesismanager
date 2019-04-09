package org.lanqiao.thesismanager.service;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Thesis;

import java.util.List;

public interface IThesisService {
    //获取论文模板列表
    public List<Thesis> getThesisModelList(Condition condition);
    //获取论文模板数量
    public int getThesisModelCount(Condition condition);
}
