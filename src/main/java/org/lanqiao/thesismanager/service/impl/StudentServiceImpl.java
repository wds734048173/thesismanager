package org.lanqiao.thesismanager.service.impl;

import org.lanqiao.thesismanager.mapper.StudentMapper;
import org.lanqiao.thesismanager.mapper.TeacherMapper;
import org.lanqiao.thesismanager.mapper.ThesisMapper;
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
    @Autowired
    TeacherMapper teacherMapper;
    @Override
    public int getStudentCountByCondition(Condition condition) {
        return studentMapper.selectStudentCountByCondition(condition);
    }

    @Override
    public List<Student> getStudentListByCondition(Condition condition) {
        List<Student> studentList = studentMapper.selectStudentListByCondition(condition);
        List<Teacher> teacherList = teacherMapper.selectTeacherAll();
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
            int tId = student.getTId();
            //对指导老师进行匹配
            for(Teacher teacher : teacherList){
                if(teacher.getId() == tId){
                    student.setTeacherRealName(teacher.getRealname());
                }
            }
        }
        return studentList;
    }

    @Override
    public void addStudent(Student student) {
        studentMapper.insertStudent(student);
    }

    @Override
    public Student getStudentById(int id) {
        return studentMapper.selectStudentById(id);
    }

    @Override
    public Student getStudentByName(String username) {
        return studentMapper.selectStudentByName(username);
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

    @Override
    public void modifyPassword(Student student) {
        studentMapper.updatePassword(student);
    }

    @Override
    public Student getStudent(Student student) {
        return studentMapper.selectStudent(student);
    }

    @Override
    public List<Student> getStudentListByTId(int tId) {
        return studentMapper.selectStudentListByTId(tId);
    }
}
