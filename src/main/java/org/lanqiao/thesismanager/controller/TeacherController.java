package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Condition;
import org.lanqiao.thesismanager.pojo.Student;
import org.lanqiao.thesismanager.pojo.Teacher;
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

    @RequestMapping("/manager/addTeacher")
    public String addTeacher(HttpServletRequest req, HttpServletResponse resp, Model model){
        Teacher teacher = Teacher.builder().build();
        //获取前端传入的信息
        String username = req.getParameter("username");
        String password = MD5Utils.MD5("123456");
        String realname = req.getParameter("realname");
        int state = Integer.valueOf(req.getParameter("state"));
        int sex = Integer.valueOf(req.getParameter("sex"));
        String telphone = req.getParameter("telphone");
        String email = req.getParameter("email");
        teacher.setUsername(username);
        teacher.setPassword(password);
        teacher.setRealname(realname);
        teacher.setState(state);
        teacher.setSex(sex);
        teacher.setTelphone(telphone);
        teacher.setEmail(email);
        teacherService.addTeacher(teacher);
        return teacherList(req, resp, model);
    }

    @RequestMapping("/manager/updateTeacher")
    public String updateTeacher(HttpServletRequest req, HttpServletResponse resp, Model model){
        Teacher teacher = Teacher.builder().build();

        String username = req.getParameter("username");
        String realname = req.getParameter("realname");
        int state = Integer.valueOf(req.getParameter("state"));
        int sex = Integer.valueOf(req.getParameter("sex"));
        String telphone = req.getParameter("telphone");
        String email = req.getParameter("email");
        String teacherId = req.getParameter("teacherId");

        teacher.setUsername(username);
        teacher.setRealname(realname);
        teacher.setState(state);
        teacher.setSex(sex);
        teacher.setTelphone(telphone);
        teacher.setEmail(email);
        teacher.setId(Integer.valueOf(teacherId));
        teacherService.modifyTeacher(teacher);
        return teacherList(req,resp,model);
    }

    @RequestMapping("/manager/getTeacherById")
    @ResponseBody
    public Teacher getTeacherById(HttpServletRequest req, HttpServletResponse resp){
        String teacherId = req.getParameter("teacherId");
        Teacher teacher = teacherService.getTeacher(Integer.valueOf(teacherId));
        return teacher;
    }

    @RequestMapping("/manager/enableTeacher")
    public String enableTeacher(HttpServletRequest req, HttpServletResponse resp, Model model){
        String teacherId = req.getParameter("teacherId");
        teacherService.enableTeacherById(Integer.valueOf(teacherId));
        return teacherList(req,resp,model);
    }


    @RequestMapping("/manager/disableTeacher")
    public String disableTeacher(HttpServletRequest req, HttpServletResponse resp, Model model){
        String teacherId = req.getParameter("teacherId");
        teacherService.disableTeacherById(Integer.valueOf(teacherId));
        return teacherList(req,resp,model);
    }


    @RequestMapping("/manager/deleteTeacher")
    public String deleteTeacher(HttpServletRequest req, HttpServletResponse resp, Model model){
        String teacherId = req.getParameter("teacherId");
        teacherService.removeTeacherById(Integer.valueOf(teacherId));
        return teacherList(req,resp,model);
    }

    //重置密码
    @RequestMapping("/manager/resetTeacherPassword")
    public String resetPassword(HttpServletRequest req, HttpServletResponse resp, Model model){
        String teacherId = req.getParameter("teacherId");
        Teacher teacher = Teacher.builder().build();
        teacher.setId(Integer.valueOf(teacherId));
        teacher.setPassword(MD5Utils.MD5("123456"));
        teacherService.modifyPassword(teacher);
        return teacherList(req,resp,model);
    }

    //获取教师的下拉列表
    @RequestMapping("/manager/teacherSelectList")
    @ResponseBody
    public List<Teacher> getTeacherSelectList(HttpServletRequest req, HttpServletResponse resp){
        List<Teacher> teacherList = teacherService.getTeacherSelectList();
        return teacherList;
    }

    //修改个人信息
    @RequestMapping("/teacher/updateUser")
    public String updateUser(HttpServletRequest req, HttpServletResponse resp, Model model){
        int id = ((Teacher)req.getSession().getAttribute("user")).getId();
        String realname = req.getParameter("realname");
        int sex = Integer.valueOf(req.getParameter("sex"));
        String telphone = req.getParameter("telphone");
        String email = req.getParameter("email");
        Teacher teacher = Teacher.builder().build();
        teacher.setId(id);
        teacher.setEmail(email);
        teacher.setTelphone(telphone);
        teacher.setSex(sex);
        teacher.setRealname(realname);
        teacherService.modifyTeacher(teacher);
        //重新给session赋值
        HttpSession session = req.getSession();
        Teacher retUser = teacherService.getTeacher(id);
        session.setAttribute("user", retUser);
        model.addAttribute("msg", "修改成功！");
        return "/teacher/userInfo";
    }

    //修改密码
    @RequestMapping("/teacher/updatePassword")
    public String updatePassword(HttpServletRequest req, HttpServletResponse resp, Model model){
        int id = ((Teacher)req.getSession().getAttribute("user")).getId();
        String password = req.getParameter("password");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        //通过id查询学生信息
        Teacher teacher = teacherService.getTeacher(id);
        String passwordMD5 = MD5Utils.MD5(password);
        if(!teacher.getPassword().equals(passwordMD5)){
            model.addAttribute("msg", "原密码输入错误，请重新输入！");
            return "/teacher/updatePassword";
        }
        if(!password1.equals(password2)){
            model.addAttribute("msg", "两次新密码输入不一致，请重新输入！");
            return "/teacher/updatePassword";
        }

        Teacher teacher1 = Teacher.builder().build();
        teacher1.setId(id);
        teacher1.setPassword(MD5Utils.MD5(password1));

        teacherService.modifyPassword(teacher1);
        HttpSession session = req.getSession();
        session.invalidate();
        return "/teacher/login";
    }
}
