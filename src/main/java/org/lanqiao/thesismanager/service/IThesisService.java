package org.lanqiao.thesismanager.service;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Thesis;

import java.util.List;

public interface IThesisService {
    //获取论文模板列表
    public List<Thesis> getThesisModelList(Condition condition);
    //获取论文模板数量
    public int getThesisModelCount(Condition condition);
    //获取上传次数的最大值
    public int getMaxValue(Thesis thesis);
    //上传论文模板
    public void addThesisModel(Thesis thesis);
    //删除论文
    public void removeThesisById(int id);
    //学生获取论文列表
    public List<Thesis> getStudentThesisList(Condition condition);
    //学生获取论文数量
    public int getStudentThesisCount(Condition condition);
    //新增论文
    public void addThesis(Thesis thesis);
}
