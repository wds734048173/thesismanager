package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Manager;
import org.lanqiao.thesismanager.service.IManagerService;
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
    @RequestMapping("/manager/updateUser")
    public String updateUser(HttpServletRequest req, HttpServletResponse resp, Model model){
//        int userId = Integer.valueOf(req.getParameter("userId"));
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
//        HttpSession session = req.getSession();
//        session.invalidate();
        //重新给session赋值
        HttpSession session = req.getSession();
        Manager retUser = managerService.getManagerById(id);
        session.setAttribute("user", retUser);
        model.addAttribute("msg", "修改成功！");
        return "/manager/userInfo";
    }
}
