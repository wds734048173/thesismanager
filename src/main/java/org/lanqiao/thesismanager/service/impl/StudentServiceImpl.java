package org.lanqiao.thesismanager.service.impl;

import org.lanqiao.thesismanager.mapper.StudentMapper;
import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Student;
import org.lanqiao.thesismanager.pojo.Teacher;
import org.lanqiao.thesismanager.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:57
 * @Description:
 */
@Service
public class StudentServiceImpl implements IStudentService {
    @Autowired
    StudentMapper studentMapper;
    @Override
    public int getStudentCountByCondition(Condition condition) {
        return studentMapper.selectStudentCountByCondition(condition);
    }

    @Override
    public List<Student> getStudentListByCondition(Condition condition) {
        List<Student> studentList = studentMapper.selectStudentListByCondition(condition);
        for(Student student : studentList){
            int state = student.getState();
            if(state == 0){
                student.setStateStr("启用");
            }else if(state == 1){
                student.setStateStr("停用");
            }
            int sex = student.getSex();
            if(sex == 0){
                student.setSexStr("男");
            }else{
                student.setSexStr("女");
            }
        }
        return studentList;
    }

    @Override
    public void addStudent(Student student) {
        studentMapper.insertStudent(student);
    }

    @Override
    public Student getStudent(int id) {
        return studentMapper.selectStudentById(id);
    }

    @Override
    public void modifyStudent(Student student) {
        studentMapper.updateStudent(student);
    }

    @Override
    public void disableStudentById(int id) {
        studentMapper.disableStudentById(id);
    }

    @Override
    public void enableStudentById(int id) {
        studentMapper.enableStudentById(id);
    }

    @Override
    public void removeStudentById(int id) {
        studentMapper.deleteStudentById(id);
    }
}
