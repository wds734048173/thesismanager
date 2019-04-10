package org.lanqiao.thesismanager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Teacher;

import java.util.List;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:51
 * @Description:
 */
@Mapper
public interface TeacherMapper {
    //获取教师数量
    public int selectTeacherCountByCondition(Condition condition);
    //获取教师列表
    public List<Teacher> selectTeacherListByCondition(Condition condition);
    //新增教师信息
    public void insertTeacher(Teacher teacher);
    //通过id获取教师详情
    public Teacher selectTeacherById(int id);
    //通过用户名获取教师详情
    public Teacher selectTeacherByName(String username);
    //修改教师信息
    public void updateTeacher(Teacher teacher);
    //启用教师
    public void enableTeacherById(int id);
    //停用教师
    public void disableTeacherById(int id);
    //删除教师
    public void deleteTeacherById(int id);
    //修改密码
    public void updatePassword(Teacher teacher);
    //获取可用老师列表
    public List<Teacher> selectTeacherSelectList();
    //获取所有的老师列表
    public List<Teacher> selectTeacherAll();
    //获取学生信息
    public Teacher selectTeacher(Teacher teacher);
}
