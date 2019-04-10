package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Student;
import org.lanqiao.thesismanager.pojo.Teacher;
import org.lanqiao.thesismanager.service.IStudentService;
import org.lanqiao.thesismanager.service.ITeacherService;
import org.lanqiao.thesismanager.utils.MD5Utils;
import org.lanqiao.thesismanager.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @Autowired
    ITeacherService teacherService;

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
        int searchTeacher = -1;
        if(req.getParameter("searchTeacher") != null){
            searchTeacher = Integer.valueOf(req.getParameter("searchTeacher"));
        }
        Condition condition = new Condition();
        condition.setUsername(searchUserName);
        condition.setRealname(searchRealName);
        condition.setState(searchState);
        condition.setTId(searchTeacher);
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
        //获取教师下拉列表
        List<Teacher> teacherList = teacherService.getTeacherSelectList();
        model.addAttribute("teacherList",teacherList);
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
        int teacherId = Integer.valueOf(req.getParameter("teacherId"));
        student.setUsername(username);
        student.setPassword(password);
        student.setRealname(realname);
        student.setState(state);
        student.setSex(sex);
        student.setTelphone(telphone);
        student.setEmail(email);
        student.setTId(teacherId);
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
        int teacherId = Integer.valueOf(req.getParameter("teacherId"));
        student.setUsername(username);
        student.setRealname(realname);
        student.setState(state);
        student.setSex(sex);
        student.setTelphone(telphone);
        student.setEmail(email);
        student.setId(Integer.valueOf(studentId));
        student.setTId(teacherId);
        studentService.modifyStudent(student);
        return studentList(req,resp,model);
    }

    @RequestMapping("/manager/getStudentById")
    @ResponseBody
    public Student getStudentById(HttpServletRequest req, HttpServletResponse resp){
        String studentId = req.getParameter("studentId");
        Student student = studentService.getStudentById(Integer.valueOf(studentId));
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

    //修改个人信息
    @RequestMapping("/student/updateUser")
    public String updateUser(HttpServletRequest req, HttpServletResponse resp, Model model){
        int id = ((Student)req.getSession().getAttribute("user")).getId();
        String realname = req.getParameter("realname");
        int sex = Integer.valueOf(req.getParameter("sex"));
        String telphone = req.getParameter("telphone");
        String email = req.getParameter("email");
        Student student = Student.builder().build();
        student.setId(id);
        student.setEmail(email);
        student.setTelphone(telphone);
        student.setSex(sex);
        student.setRealname(realname);
        studentService.modifyStudent(student);
        //重新给session赋值
        HttpSession session = req.getSession();
        Student retUser = studentService.getStudentById(id);
        session.setAttribute("user", retUser);
        model.addAttribute("msg", "修改成功！");
        return "/student/userInfo";
    }

    //修改密码
    @RequestMapping("/student/updatePassword")
    public String updatePassword(HttpServletRequest req, HttpServletResponse resp, Model model){
        int id = ((Student)req.getSession().getAttribute("user")).getId();
        String password = req.getParameter("password");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        //通过id查询学生信息
        Student student = studentService.getStudentById(id);
        String passwordMD5 = MD5Utils.MD5(password);
        if(!student.getPassword().equals(passwordMD5)){
            model.addAttribute("msg", "原密码输入错误，请重新输入！");
            return "/student/updatePassword";
        }
        if(!password1.equals(password2)){
            model.addAttribute("msg", "两次新密码输入不一致，请重新输入！");
            return "/student/updatePassword";
        }

        Student student1 = Student.builder().build();
        student1.setId(id);
        student1.setPassword(MD5Utils.MD5(password1));

        studentService.modifyPassword(student1);
        HttpSession session = req.getSession();
        session.invalidate();
        return "/student/login";
    }
    //重置密码
    @RequestMapping("/manager/resetStudentPassword")
    public String resetPassword(HttpServletRequest req, HttpServletResponse resp, Model model){
        String studentId = req.getParameter("studentId");
        Student student = Student.builder().build();
        student.setId(Integer.valueOf(studentId));
        student.setPassword(MD5Utils.MD5("123456"));
        studentService.modifyPassword(student);
        return studentList(req,resp,model);
    }
}
