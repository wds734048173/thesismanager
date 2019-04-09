package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Teacher;
import org.lanqiao.thesismanager.pojo.Thesis;
import org.lanqiao.thesismanager.service.IThesisService;
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
 * @Date: 2019/4/6 08:00
 * @Description:
 */
@Controller
public class ThesisController {

    @Autowired
    IThesisService thesisService;

    //获取论文模板列表
    @RequestMapping("/manager/thesisModelList")
    public String thesisModelList(HttpServletRequest req, HttpServletResponse resp, Model model){
        int pageNum = 1;
        if(req.getParameter("currentPage") != null){
            pageNum = Integer.valueOf(req.getParameter("currentPage"));
        }
        int pageSize = 5;
        if(req.getParameter("pageSize") != null){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        Condition condition = new Condition();
        int totalRecords = thesisService.getThesisModelCount(condition);
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
        List<Thesis> thesisModelList = thesisService.getThesisModelList(condition);
        model.addAttribute("thesisModelList",thesisModelList);
        model.addAttribute("pm",pageModel);
        model.addAttribute("condition",condition);
        model.addAttribute("currentPage",pageNum);
        return "/manager/thesisModelList";
    }
}
