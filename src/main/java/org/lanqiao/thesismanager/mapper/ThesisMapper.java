package org.lanqiao.thesismanager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Thesis;

import java.util.List;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:51
 * @Description:
 */
@Mapper
public interface ThesisMapper {
    //获取论文模板列表
    public List<Thesis> selectThesisModelList(Condition condition);
    //获取论文模板数量
    public int selectThesisModelCount(Condition condition);
    //上传论文模板
    public void insertThesisModel(Thesis thesis);
    //获取所有学生的论文信息
    public List<Thesis> selectThesisList();
    //获取所有学生的论文数量
    public List<Thesis> selectThesisCount();
    //获取上传次数的最大值
    public int selectMaxValue(Thesis thesis);
    //删除论文（包括模板）
    public void deleteThesisById(int id);
    //新增论文
    public void insertThesis(Thesis thesis);
    //学生查询论文列表
    public List<Thesis> selectStudentThesisList(Condition condition);
    //学生查询论文数量
    public int selectStudentThesisCount(Condition condition);

    //教师查询论文列表
    public List<Thesis> selectTeacherThesisList(Condition condition);
    //教师查询论文数量
    public int selectTeacherThesisCount(Condition condition);
    //通过id获取论文地址
    public String selectThesisUrl(int id);
}
