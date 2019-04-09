package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Student;
import org.lanqiao.thesismanager.service.IStudentService;
import org.lanqiao.thesismanager.service.IStudentService;
import org.lanqiao.thesismanager.utils.MD5Utils;
import org.lanqiao.thesismanager.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:59
 * @Description:
 */
@Controller
public class StudentController {
    @Autowired
    IStudentService studentService;

    //获取老师列表
    @RequestMapping("/manager/studentList")
    public String studentList(HttpServletRequest req, HttpServletResponse resp, Model model){
        int pageNum = 1;
        if(req.getParameter("currentPage") != null){
            pageNum = Integer.valueOf(req.getParameter("currentPage"));
        }
        int pageSize = 5;
        if(req.getParameter("pageSize") != null){
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        //查询条件
        String searchUserName = "";
        if(req.getParameter("searchUserName") != null){
            searchUserName = req.getParameter("searchUserName");
        }
        String searchRealName = "";
        if(req.getParameter("searchRealName") != null){
            searchRealName = req.getParameter("searchRealName");
        }
        int searchState = -1;
        if(req.getParameter("searchState") != null){
            searchState = Integer.valueOf(req.getParameter("searchState"));
        }
        Condition condition = new Condition();
        condition.setUsername(searchUserName);
        condition.setRealname(searchRealName);
        condition.setState(searchState);
        int totalRecords = studentService.getStudentCountByCondition(condition);
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
        List<Student> studentList = studentService.getStudentListByCondition(condition);
        model.addAttribute("studentList",studentList);
        model.addAttribute("pm",pageModel);
        model.addAttribute("condition",condition);
        model.addAttribute("currentPage",pageNum);
        return "/manager/studentList";
    }

    @RequestMapping("/manager/addStudent")
    public String addStudent(HttpServletRequest req, HttpServletResponse resp, Model model){
        Student student = Student.builder().build();
        //获取前端传入的信息
        String username = req.getParameter("username");
        String password = MD5Utils.MD5("123456");
        String realname = req.getParameter("realname");
        int state = Integer.valueOf(req.getParameter("state"));
        int sex = Integer.valueOf(req.getParameter("sex"));
        String telphone = req.getParameter("telphone");
        String email = req.getParameter("email");
        student.setUsername(username);
        student.setPassword(password);
        student.setRealname(realname);
        student.setState(state);
        student.setSex(sex);
        student.setTelphone(telphone);
        student.setEmail(email);
        studentService.addStudent(student);
        return studentList(req, resp, model);
    }

    @RequestMapping("/manager/updateStudent")
    public String updateStudent(HttpServletRequest req, HttpServletResponse resp, Model model){
        Student student = Student.builder().build();

        String username = req.getParameter("username");
        String realname = req.getParameter("realname");
        int state = Integer.valueOf(req.getParameter("state"));
        int sex = Integer.valueOf(req.getParameter("sex"));
        String telphone = req.getParameter("telphone");
        String email = req.getParameter("email");
        String studentId = req.getParameter("studentId");

        student.setUsername(username);
        student.setRealname(realname);
        student.setState(state);
        student.setSex(sex);
        student.setTelphone(telphone);
        student.setEmail(email);
        student.setId(Integer.valueOf(studentId));
        studentService.modifyStudent(student);
        return studentList(req,resp,model);
    }

    @RequestMapping("/manager/getStudentById")
    @ResponseBody
    public Student getStudentById(HttpServletRequest req, HttpServletResponse resp){
        String studentId = req.getParameter("studentId");
        Student student = studentService.getStudent(Integer.valueOf(studentId));
        return student;
    }

    @RequestMapping("/manager/enableStudent")
    public String enableStudent(HttpServletRequest req, HttpServletResponse resp, Model model){
        String studentId = req.getParameter("studentId");
        studentService.enableStudentById(Integer.valueOf(studentId));
        return studentList(req,resp,model);
    }


    @RequestMapping("/manager/disableStudent")
    public String disableStudent(HttpServletRequest req, HttpServletResponse resp, Model model){
        String studentId = req.getParameter("studentId");
        studentService.disableStudentById(Integer.valueOf(studentId));
        return studentList(req,resp,model);
    }


    @RequestMapping("/manager/deleteStudent")
    public String deleteStudent(HttpServletRequest req, HttpServletResponse resp, Model model){
        String studentId = req.getParameter("studentId");
        studentService.removeStudentById(Integer.valueOf(studentId));
        return studentList(req,resp,model);
    }
}
