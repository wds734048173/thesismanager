package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Manager;
import org.lanqiao.thesismanager.service.IManagerService;
import org.lanqiao.thesismanager.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Auther: WDS
 * @Date: 2019/4/6 07:59
 * @Description:
 */
@Controller
public class ManagerController {
    @Autowired
    IManagerService managerService;
    //修改个人信息
    @RequestMapping("/manager/updateUser")
    public String updateUser(HttpServletRequest req, HttpServletResponse resp, Model model){
        int id = ((Manager)req.getSession().getAttribute("user")).getId();
        String realname = req.getParameter("realname");
        int sex = Integer.valueOf(req.getParameter("sex"));
        String telphone = req.getParameter("telphone");
        String email = req.getParameter("email");
        Manager manager = Manager.builder().build();
        manager.setId(id);
        manager.setEmail(email);
        manager.setTelphone(telphone);
        manager.setSex(sex);
        manager.setRealname(realname);
        managerService.modifyManager(manager);
        //重新给session赋值
        HttpSession session = req.getSession();
        Manager retUser = managerService.getManagerById(id);
        session.setAttribute("user", retUser);
        model.addAttribute("msg", "修改成功！");
        return "/manager/userInfo";
    }

    //修改密码
    @RequestMapping("/manager/updatePassword")
    public String updatePassword(HttpServletRequest req, HttpServletResponse resp, Model model){
        int id = ((Manager)req.getSession().getAttribute("user")).getId();
        String password = req.getParameter("password");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        //通过id查询管理员信息
        Manager manager = managerService.getManagerById(id);
        String passwordMD5 = MD5Utils.MD5(password);
        if(!manager.getPassword().equals(passwordMD5)){
            model.addAttribute("msg", "原密码输入错误，请重新输入！");
            return "/manager/updatePassword";
        }
        if(!password1.equals(password2)){
            model.addAttribute("msg", "两次新密码输入不一致，请重新输入！");
            return "/manager/updatePassword";
        }

        Manager manager1 = Manager.builder().build();
        manager1.setId(id);
        manager1.setPassword(MD5Utils.MD5(password1));

        managerService.modifyManagerPassword(manager1);
        HttpSession session = req.getSession();
        session.invalidate();
        return "/manager/login";
    }

}
