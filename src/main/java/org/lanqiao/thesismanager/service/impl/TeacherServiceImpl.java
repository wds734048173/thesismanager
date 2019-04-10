package org.lanqiao.thesismanager.service.impl;

import org.lanqiao.thesismanager.mapper.TeacherMapper;
import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Teacher;
import org.lanqiao.thesismanager.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:56
 * @Description:
 */
@Service
public class TeacherServiceImpl implements ITeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public int getTeacherCountByCondition(Condition condition) {
        return teacherMapper.selectTeacherCountByCondition(condition);
    }

    @Override
    public List<Teacher> getTeacherListByCondition(Condition condition) {
        List<Teacher> teacherList = teacherMapper.selectTeacherListByCondition(condition);
        for(Teacher teacher : teacherList){
            int state = teacher.getState();
            if(state == 0){
                teacher.setStateStr("启用");
            }else if(state == 1){
                teacher.setStateStr("停用");
            }
            int sex = teacher.getSex();
            if(sex == 0){
                teacher.setSexStr("男");
            }else{
                teacher.setSexStr("女");
            }
        }
        return teacherList;
    }

    @Override
    public void addTeacher(Teacher teacher) {
        teacherMapper.insertTeacher(teacher);
    }

    @Override
    public Teacher getTeacher(int id) {
        return teacherMapper.selectTeacherById(id);
    }

    @Override
    public void modifyTeacher(Teacher teacher) {
        teacherMapper.updateTeacher(teacher);
    }

    @Override
    public void disableTeacherById(int id) {
        teacherMapper.disableTeacherById(id);
    }

    @Override
    public void enableTeacherById(int id) {
        teacherMapper.enableTeacherById(id);
    }

    @Override
    public void removeTeacherById(int id) {
        teacherMapper.deleteTeacherById(id);
    }

    @Override
    public void modifyPassword(Teacher teacher) {
        teacherMapper.updatePassword(teacher);
    }

    @Override
    public List<Teacher> getTeacherSelectList() {
        return teacherMapper.selectTeacherSelectList();
    }

    @Override
    public List<Teacher> getTeacherAll() {
        return teacherMapper.selectTeacherAll();
    }

    @Override
    public Teacher getTeacher(Teacher teacher) {
        return teacherMapper.selectTeacher(teacher);
    }
}
