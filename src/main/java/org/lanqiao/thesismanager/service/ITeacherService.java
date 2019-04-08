package org.lanqiao.thesismanager.service;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Teacher;

import java.util.List;

public interface ITeacherService {
    public int getTeacherCountByCondition(Condition condition);

    public List<Teacher> getTeacherListByCondition(Condition condition);
}
