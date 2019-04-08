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
        }
        return teacherList;
    }
}
