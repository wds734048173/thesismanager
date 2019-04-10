package org.lanqiao.thesismanager.service;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Manager;
import org.lanqiao.thesismanager.pojo.Student;

import java.util.List;

public interface IStudentService {
    //获取学生数量
    public int getStudentCountByCondition(Condition condition);
    //获取学生列表
    public List<Student> getStudentListByCondition(Condition condition);
    //新增学生信息
    public void addStudent(Student student);
    //通过id获取学生信息
    public Student getStudentById(int id);
    //通过用户名获取学生信息
    public Student getStudentByName(String username);
    //修改学生信息
    public void modifyStudent(Student student);
    //停用学生
    public void disableStudentById(int id);
    //启用学生
    public void enableStudentById(int id);
    //删除学生
    public void removeStudentById(int id);
    //修改密码
    public void modifyPassword(Student student);
    //查询学生信息
    public Student getStudent(Student student);
}
