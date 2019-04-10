package org.lanqiao.thesismanager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Student;

import java.util.List;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:51
 * @Description:
 */
@Mapper
public interface StudentMapper {
    //获取学生数量
    public int selectStudentCountByCondition(Condition condition);
    //获取学生列表
    public List<Student> selectStudentListByCondition(Condition condition);
    //新增学生信息
    public void insertStudent(Student student);
    //获取通过id学生详情
    public Student selectStudentById(int id);
    //修改学生信息
    public void updateStudent(Student student);
    //启用学生
    public void enableStudentById(int id);
    //停用学生
    public void disableStudentById(int id);
    //删除学生
    public void deleteStudentById(int id);
    //修改密码
    public void updatePassword(Student student);
}
