package org.lanqiao.thesismanager.service;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Student;
import org.lanqiao.thesismanager.pojo.Teacher;

import java.util.List;

public interface ITeacherService {
    //获取教师数量
    public int getTeacherCountByCondition(Condition condition);
    //获取教师列表
    public List<Teacher> getTeacherListByCondition(Condition condition);
    //新增教师信息
    public void addTeacher(Teacher teacher);
    //通过id获取教师信息
    public Teacher getTeacher(int id);
    //修改教师信息
    public void modifyTeacher(Teacher teacher);
    //停用教师
    public void disableTeacherById(int id);
    //启用教师
    public void enableTeacherById(int id);
    //删除教师
    public void removeTeacherById(int id);
    //修改密码
    public void modifyPassword(Teacher teacher);
    //获取教师下拉列表
    public List<Teacher> getTeacherSelectList();
    //获取所有的教师列表
    public List<Teacher> getTeacherAll();
    //查询教师信息
    public Teacher getTeacher(Teacher teacher);
}
