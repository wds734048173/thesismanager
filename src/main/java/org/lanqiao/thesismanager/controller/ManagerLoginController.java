package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.pojo.Manager;
import org.lanqiao.thesismanager.service.IManagerService;
import org.lanqiao.thesismanager.utils.MD5Utils;
import org.lanqiao.thesismanager.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @Auther: WDS
 * @Date: 2019/1/12 20:20
 * @Description:登录注册管理
 */
@Controller
public class ManagerLoginController {

    @Autowired
    IManagerService managerService;

    @RequestMapping("/imageCodePage")
    public void imageCodePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerifyCode vc  = new VerifyCode();
        BufferedImage bi = vc.getImage();
        String rcode = vc.getText();
        rcode = rcode.toLowerCase();
        HttpSession s = request.getSession();
        s.setAttribute("rcodes",rcode);
        VerifyCode.output(bi,response.getOutputStream());
    }


    @RequestMapping("/manager/login")
    public String login(HttpServletRequest req, HttpServletResponse resp, Model model){
        if(req.getParameter("username") == null){
            return "/manager/login";
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(StringUtils.isEmpty(username)){
            model.addAttribute("msg","用户名不能为空！");
            return "/manager/login";
        }
        if(username.length() > 20){
            model.addAttribute("msg","用户名不能多于20个字符！");
            return "/manager/login";
        }
        if(StringUtils.isEmpty(password)){
            model.addAttribute("msg","密码不能为空！");
            return "/manager/login";
        }
        //要验证的验证码
        String rcode = req.getParameter("rcode");
        //验证码中的值
        String s = (String) req.getSession().getAttribute("rcodes");
        //验证码的比较
        if (StringUtils.isEmpty(s)||!rcode.equalsIgnoreCase(s)){
            model.addAttribute("msg","验证码错误");
            return "/manager/login";
        }else {
            String pwdMD5 = MD5Utils.MD5(password);
            Manager user = Manager.builder().build();
            user.setUsername(username);
            user.setPassword(pwdMD5);
            Manager retUser = managerService.getManager(user);
            if (retUser == null) {
                model.addAttribute("msg", "用户名或密码错误，请重新输入！");
                return "/manager/login";
            } else {
                int state = retUser.getState();
                if(state == 0){
                    HttpSession session = req.getSession();
                    session.setAttribute("user", retUser);
                    return "/manager/index";
                }else{
                    model.addAttribute("msg", "该用户状态不对，请联系管理员！");
                    return "/manager/login";
                }

            }
        }
    }
    //管理员注册（有注册功能，但是不一定对外开放）
    @RequestMapping("/manager/register")
    public String register(HttpServletRequest req, HttpServletResponse resp, Model model){
        //注册页面密码确认
        String username = req.getParameter("username");
        if(username.length() > 20){
            model.addAttribute("msg","用户名长度多于20，请重新输入");
            return "/manager/userRegister";
        }
        Manager users = managerService.getManagerByName(username);
        //判断用户名是否存在
        if (users !=null){
            model.addAttribute("msg","用户名已存在，请重新输入");
            return "/manager/userRegister";
        }else {
            String password1 = req.getParameter("password1");
            String password2 = req.getParameter("password2");
            if (StringUtils.isEmpty(password1)|| StringUtils.isEmpty(password2)||!password1.equalsIgnoreCase(password2)){
                model.addAttribute("msg","密码不一致，请重新输入");
                return "/manager/userRegister";
            }
            //性别
            int sex =Integer.valueOf(req.getParameter("sex"));
            //真实名称
            String realname = req.getParameter("realname").trim();
            if(realname.length() > 20){
                model.addAttribute("msg","真实名称长度多于20，请重新输入");
                return "/manager/userRegister";
            }
            //手机号
            String telphone = req.getParameter("telphone");
            if(!Pattern.matches("^1[3,4,5,6,7,8][0-9]\\d{8}$",telphone)){
                model.addAttribute("msg","手机号格式错误，请重新输入");
                return "/manager/userRegister";
            }
            //邮箱
            String email = req.getParameter("email");
            if(!Pattern.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",email)){
                model.addAttribute("msg","邮箱格式错误，请重新输入");
                return "/manager/userRegister";
            }
            Manager manager = new Manager();
            manager.setUsername(username);
            String pwdMD5 = MD5Utils.MD5(password2);
            manager.setPassword(pwdMD5);
            manager.setSex(sex);
            manager.setState(0);
            manager.setTelphone(telphone);
            manager.setEmail(email);
            manager.setRealname(realname);
            managerService.addManager(manager);
            model.addAttribute("msg","注册成功");
            return "/manager/login";
        }
    }

    @RequestMapping("/manager/exit")
    public String exit(HttpServletRequest req, HttpServletResponse resp, Model model){
        HttpSession session = req.getSession();
        session.invalidate();
        return "/manager/login";
    }
}
