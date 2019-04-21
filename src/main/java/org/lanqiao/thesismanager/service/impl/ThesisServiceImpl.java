package org.lanqiao.thesismanager.service.impl;

import org.lanqiao.thesismanager.mapper.StudentMapper;
import org.lanqiao.thesismanager.mapper.ThesisMapper;
import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Student;
import org.lanqiao.thesismanager.pojo.Thesis;
import org.lanqiao.thesismanager.service.IThesisService;
import org.lanqiao.thesismanager.utils.DataMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Autowired
    StudentMapper studentMapper;
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

    @Override
    public List<Thesis> getStudentThesisList(Condition condition) {
        List<Thesis> thesisList =  thesisMapper.selectStudentThesisList(condition);
        Map<Integer,String> thesisTypeMap = DataMapUtil.getThesisTypeMap();
        Map<Integer,String> thesisCommitTypeMap = DataMapUtil.getThesisCommitTypeMap();
        for(Thesis thesis : thesisList){
            int type = thesis.getType();
            if(thesisTypeMap.containsKey(type)){
                thesis.setTypeStr(thesisTypeMap.get(type));
            }
            int commitType = thesis.getCommitType();
            if(thesisCommitTypeMap.containsKey(commitType)){
                thesis.setCommitTypeStr(thesisCommitTypeMap.get(commitType));
            }
        }
        return thesisList;
    }

    @Override
    public int getStudentThesisCount(Condition condition) {
        return thesisMapper.selectStudentThesisCount(condition);
    }

    @Override
    public void addThesis(Thesis thesis) {
        thesisMapper.insertThesis(thesis);
    }

    @Override
    public List<Thesis> getTeacherThesisList(Condition condition) {
        List<Thesis> thesisList =  thesisMapper.selectTeacherThesisList(condition);
        Map<Integer,String> thesisTypeMap = DataMapUtil.getThesisTypeMap();
        Map<Integer,String> thesisCommitTypeMap = DataMapUtil.getThesisCommitTypeMap();

        List<Student> studentList = studentMapper.selectStudentListByCondition(null);
        Map<Integer,String> studentIdNameMap = new HashMap<>();
        for(Student student : studentList){
            studentIdNameMap.put(student.getId(),student.getRealname());
        }


        for(Thesis thesis : thesisList){
            int type = thesis.getType();
            if(thesisTypeMap.containsKey(type)){
                thesis.setTypeStr(thesisTypeMap.get(type));
            }
            int commitType = thesis.getCommitType();
            if(thesisCommitTypeMap.containsKey(commitType)){
                thesis.setCommitTypeStr(thesisCommitTypeMap.get(commitType));
            }
            int sId = thesis.getSId();
            if(studentIdNameMap.containsKey(sId)){
                thesis.setSIdStr(studentIdNameMap.get(sId));
            }
        }
        return thesisList;
    }

    @Override
    public int getTeacherThesisCount(Condition condition) {
        return thesisMapper.selectTeacherThesisCount(condition);
    }

    @Override
    public String getThesisUrl(int id) {
        return thesisMapper.selectThesisUrl(id);
    }
}
