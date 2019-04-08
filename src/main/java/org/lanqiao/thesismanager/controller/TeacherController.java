package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Teacher;
import org.lanqiao.thesismanager.service.ITeacherService;
import org.lanqiao.thesismanager.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:59
 * @Description:
 */
@Controller
public class TeacherController {

    @Autowired
    ITeacherService teacherService;

    //获取老师列表
    @RequestMapping("/manager/teacherList")
    public String teacherList(HttpServletRequest req, HttpServletResponse resp, Model model){
        int pageNum = 1;
        if(req.getParameter("currentPage") != null){
            pageNum = Integer.valueOf(req.getParameter("currentPage"));
        }
        int pageSize = 5;
        if(req.getParameter("pageSize") != null){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }

        //查询条件
        String searchTeacherName = "";
        if(req.getParameter("searchTeacherName") != null){
            searchTeacherName = req.getParameter("searchTeacherName");
        }
        String searchTeacherState = "";
        if(req.getParameter("searchTeacherState") != null){
            searchTeacherState = req.getParameter("searchTeacherState");
        }else{
            searchTeacherState = "-1";
        }
        Condition condition = new Condition();
        condition.setName(searchTeacherName);
        condition.setState(searchTeacherState);
        int totalRecords = teacherService.getTeacherCountByCondition(condition);
        //不同操作，不同的当前页设置
        PageModel pm = new PageModel(pageNum,totalRecords,pageSize);
        String method = req.getParameter("method");
        if("add".equals(method)){
            pageNum = pm.getEndPage();
        }else{
            //如果当前页大于总页数，但是排除查询不到数据的情况。当前页等于最大页
            if(pageNum > pm.getTotalPageNum() && pm.getTotalPageNum() != 0){
                pageNum = pm.getTotalPageNum();
            }
        }
        PageModel pageModel = new PageModel(pageNum,totalRecords,pageSize);
        //分页条件封装
        condition.setCurrentPage(pageModel.getStartIndex());
        condition.setPageSize(pageModel.getPageSize());
        List<Teacher> teacherList = teacherService.getTeacherListByCondition(condition);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("pm",pageModel);
        model.addAttribute("condition",condition);
        model.addAttribute("currentPage",pageNum);
        return "/manager/teacherList";
    }


}
