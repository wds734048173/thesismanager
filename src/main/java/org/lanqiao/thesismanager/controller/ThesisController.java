package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Student;
import org.lanqiao.thesismanager.pojo.Teacher;
import org.lanqiao.thesismanager.pojo.Thesis;
import org.lanqiao.thesismanager.service.IStudentService;
import org.lanqiao.thesismanager.service.IThesisService;
import org.lanqiao.thesismanager.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
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

    @Autowired
    IStudentService studentService;

    //=============================管理员相关操作=================================
    //获取论文模板列表
    @RequestMapping("/manager/thesisModelList")
    public String thesisModelManagerList(HttpServletRequest req, HttpServletResponse resp, Model model){
        int pageNum = 1;
        if(req.getParameter("currentPage") != null){
            pageNum = Integer.valueOf(req.getParameter("currentPage"));
        }
        int pageSize = 5;
        if(req.getParameter("pageSize") != null){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        int searchType = -1;
        if(req.getParameter("searchType") != null){
            searchType = Integer.valueOf(req.getParameter("searchType"));
        }
        Condition condition = new Condition();
        condition.setType(searchType);
        int totalRecords = thesisService.getThesisModelCount(condition);
        //不同操作，不同的当前页设置
        PageModel pm = new PageModel(pageNum,totalRecords,pageSize);
        String method = req.getParameter("method");
        if("add".equals(method)){
            pageNum = pm.getStartPage();
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
    //新增论文模板
    @RequestMapping("/manager/addThesisModel")
    public String addThesisModel(HttpServletRequest req, HttpServletResponse resp, Model model) throws UnsupportedEncodingException {
        Thesis thesis = Thesis.builder().build();
        //论文模板类型
        int thesisModelType = Integer.valueOf(req.getParameter("thesisModelType"));
        //论文模板地址
        String thesisModelAddress = URLDecoder.decode(req.getParameter("thesisModelAddress"),"utf-8");
        //论文模板备注
        String thesisModelRemark = req.getParameter("thesisModelRemark");

        //论文模板上传地址
        thesis.setThesisAddress(thesisModelAddress);
        thesis.setType(thesisModelType);
        thesis.setRemark(thesisModelRemark);
        //获取论文上传的最大次数
        int count = thesisService.getMaxValue(thesis);
        thesis.setCount(count + 1);
        thesisService.addThesisModel(thesis);
        return thesisModelManagerList(req, resp, model);
    }
    //删除论文模板
    @RequestMapping("/manager/deleteThesisModel")
    public String deleteThesisModel(HttpServletRequest req, HttpServletResponse resp, Model model){
        String thesisId = req.getParameter("thesisId");
        thesisService.removeThesisById(Integer.valueOf(thesisId));
        return thesisModelManagerList(req,resp,model);
    }
    //=============================学生相关操作=================================
    //获取论文模板列表
    @RequestMapping("/student/thesisModelList")
    public String thesisModelStudentList(HttpServletRequest req, HttpServletResponse resp, Model model){
        int pageNum = 1;
        if(req.getParameter("currentPage") != null){
            pageNum = Integer.valueOf(req.getParameter("currentPage"));
        }
        int pageSize = 5;
        if(req.getParameter("pageSize") != null){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        int searchType = -1;
        if(req.getParameter("searchType") != null){
            searchType = Integer.valueOf(req.getParameter("searchType"));
        }
        Condition condition = new Condition();
        condition.setType(searchType);
        int totalRecords = thesisService.getThesisModelCount(condition);
        //不同操作，不同的当前页设置
        PageModel pm = new PageModel(pageNum,totalRecords,pageSize);
        String method = req.getParameter("method");
        if("add".equals(method)){
            pageNum = pm.getStartPage();
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
        return "/student/thesisModelList";
    }
    //学生获取论文列表
    @RequestMapping("/student/thesisList")
    public String studentThesisList(HttpServletRequest req, HttpServletResponse resp, Model model){
        int pageNum = 1;
        if(req.getParameter("currentPage") != null){
            pageNum = Integer.valueOf(req.getParameter("currentPage"));
        }
        int pageSize = 5;
        if(req.getParameter("pageSize") != null){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        int searchType = -1;
        if(req.getParameter("searchType") != null){
            searchType = Integer.valueOf(req.getParameter("searchType"));
        }
        int searchCommitType = -1;
        if(req.getParameter("searchCommitType") != null){
            searchCommitType = Integer.valueOf(req.getParameter("searchCommitType"));
        }
        //获取登录者的id
        HttpSession session = req.getSession();
        Student student = (Student) session.getAttribute("user");
        int studentId = Integer.valueOf(student.getId());
        Condition condition = new Condition();
        condition.setType(searchType);
        condition.setCommitType(searchCommitType);
        condition.setSId(studentId);
        int totalRecords = thesisService.getStudentThesisCount(condition);
        //不同操作，不同的当前页设置
        PageModel pm = new PageModel(pageNum,totalRecords,pageSize);
        String method = req.getParameter("method");
        if("add".equals(method)){
            pageNum = pm.getStartPage();
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
        List<Thesis> thesisList = thesisService.getStudentThesisList(condition);
        model.addAttribute("thesisList",thesisList);
        model.addAttribute("pm",pageModel);
        model.addAttribute("condition",condition);
        model.addAttribute("currentPage",pageNum);
        return "/student/thesisList";
    }
    //新增论文
    @RequestMapping("/student/addStudentThesis")
    public String addStudentThesis(HttpServletRequest req, HttpServletResponse resp, Model model) throws UnsupportedEncodingException {
        Thesis thesis = Thesis.builder().build();
        //论文模板备注
        String thesisRemark = req.getParameter("thesisRemark");
        //论文模板类型
        int thesisType = Integer.valueOf(req.getParameter("thesisType"));
        //论文模板地址
        String thesisAddress = URLDecoder.decode(req.getParameter("thesisAddress"),"utf-8");
        //论文模板上传地址
        thesis.setThesisAddress(thesisAddress);
        //获取登录者的id
        HttpSession session = req.getSession();
        Student student = (Student) session.getAttribute("user");
        if(student.getTId() != 0){
            thesis.setTId(student.getTId());
        }
        if(student.getId() != 0){
            thesis.setSId(student.getId());
        }
        thesis.setType(thesisType);
        thesis.setRemark(thesisRemark);
        thesis.setCommitType(1);
        //获取论文上传的最大次数
        int count = thesisService.getMaxValue(thesis);
        thesis.setCount(count + 1);
        thesisService.addThesis(thesis);
        return studentThesisList(req, resp, model);
    }
    //删除论文
    @RequestMapping("/student/deleteStudentThesis")
    public String deleteStudentThesis(HttpServletRequest req, HttpServletResponse resp, Model model){
        String thesisId = req.getParameter("thesisId");
        thesisService.removeThesisById(Integer.valueOf(thesisId));
        return studentThesisList(req,resp,model);
    }
    //=============================教师相关操作=================================
    //获取论文模板列表
    @RequestMapping("/teacher/thesisModelList")
    public String thesisModelTeacherList(HttpServletRequest req, HttpServletResponse resp, Model model){
        int pageNum = 1;
        if(req.getParameter("currentPage") != null){
            pageNum = Integer.valueOf(req.getParameter("currentPage"));
        }
        int pageSize = 5;
        if(req.getParameter("pageSize") != null){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        int searchType = -1;
        if(req.getParameter("searchType") != null){
            searchType = Integer.valueOf(req.getParameter("searchType"));
        }
        Condition condition = new Condition();
        condition.setType(searchType);
        int totalRecords = thesisService.getThesisModelCount(condition);
        //不同操作，不同的当前页设置
        PageModel pm = new PageModel(pageNum,totalRecords,pageSize);
        String method = req.getParameter("method");
        if("add".equals(method)){
            pageNum = pm.getStartPage();
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
        return "/teacher/thesisModelList";
    }

    //教师获取论文列表
    @RequestMapping("/teacher/thesisList")
    public String teacherThesisList(HttpServletRequest req, HttpServletResponse resp, Model model){
        int pageNum = 1;
        if(req.getParameter("currentPage") != null){
            pageNum = Integer.valueOf(req.getParameter("currentPage"));
        }
        int pageSize = 5;
        if(req.getParameter("pageSize") != null){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        int searchType = -1;
        if(req.getParameter("searchType") != null){
            searchType = Integer.valueOf(req.getParameter("searchType"));
        }
        int searchCommitType = -1;
        if(req.getParameter("searchCommitType") != null){
            searchCommitType = Integer.valueOf(req.getParameter("searchCommitType"));
        }
        int searchSId = -1;
        if(req.getParameter("searchSId") != null){
            searchSId = Integer.valueOf(req.getParameter("searchSId"));
        }
        //获取登录者的id
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        int teacherId = Integer.valueOf(teacher.getId());
        //查询老师名下的所有学生学习，并返回前台
        List<Student> studentList = studentService.getStudentListByTId(teacherId);
        Condition condition = new Condition();
        condition.setType(searchType);
        condition.setCommitType(searchCommitType);
        condition.setTId(teacherId);
        condition.setSId(searchSId);
        int totalRecords = thesisService.getTeacherThesisCount(condition);
        //不同操作，不同的当前页设置
        PageModel pm = new PageModel(pageNum,totalRecords,pageSize);
        String method = req.getParameter("method");
        if("add".equals(method)){
            pageNum = pm.getStartPage();
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
        List<Thesis> thesisList = thesisService.getTeacherThesisList(condition);
        model.addAttribute("thesisList",thesisList);
        model.addAttribute("pm",pageModel);
        model.addAttribute("studentList",studentList);
        model.addAttribute("condition",condition);
        model.addAttribute("currentPage",pageNum);
        return "/teacher/thesisList";
    }
    //新增论文
    @RequestMapping("/teacher/addTeacherThesis")
    public String addTeacherThesis(HttpServletRequest req, HttpServletResponse resp, Model model) throws UnsupportedEncodingException {
        Thesis thesis = Thesis.builder().build();
        //论文备注
        String thesisRemark = req.getParameter("thesisRemark");
        //论文类型
        int thesisType = Integer.valueOf(req.getParameter("thesisType"));
        //论文模板地址
        String thesisAddress = URLDecoder.decode(req.getParameter("thesisAddress"),"utf-8");
        //论文上传地址
        thesis.setThesisAddress(thesisAddress);
        //论文所属学生
        int studentId = Integer.valueOf(req.getParameter("sId"));
        thesis.setSId(studentId);
        //获取登录者的id
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if(teacher.getId() != 0){
            thesis.setTId(teacher.getId());
        }
        thesis.setType(thesisType);
        thesis.setRemark(thesisRemark);
        thesis.setCommitType(2);
        //获取论文上传的最大次数
        int count = thesisService.getMaxValue(thesis);
        thesis.setCount(count + 1);
        thesisService.addThesis(thesis);
        return teacherThesisList(req, resp, model);
    }
    //删除论文
    @RequestMapping("/teacher/deleteTeacherThesis")
    public String deleteTeacherThesis(HttpServletRequest req, HttpServletResponse resp, Model model){
        String thesisId = req.getParameter("thesisId");
        thesisService.removeThesisById(Integer.valueOf(thesisId));
        return teacherThesisList(req,resp,model);
    }
}
