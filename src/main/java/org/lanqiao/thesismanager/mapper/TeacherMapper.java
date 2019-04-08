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
    public int selectTeacherCountByCondition(Condition condition);

    public List<Teacher> selectTeacherListByCondition(Condition condition);
}
