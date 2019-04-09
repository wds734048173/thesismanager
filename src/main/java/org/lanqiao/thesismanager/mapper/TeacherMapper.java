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
    //获取通过id教师详情
    public Teacher selectTeacherById(int id);
    //修改教师信息
    public void updateTeacher(Teacher teacher);
    //启用教师
    public void enableTeacherById(int id);
    //停用教师
    public void disableTeacherById(int id);
    //删除教师
    public void deleteTeacherById(int id);
}
