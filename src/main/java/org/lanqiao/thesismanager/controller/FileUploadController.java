package org.lanqiao.thesismanager.controller;

import org.lanqiao.thesismanager.service.IThesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: WDS
 * @Date: 2019/4/15 20:58
 * @Description:
 */
@Controller
public class FileUploadController {

    @Autowired
    IThesisService thesisService;
    /*
     * 获取file.html页面
     */
    @RequestMapping("file")
    public String file(){
        return "/file";
    }

    /**
     * 实现文件上传
     * */
    @RequestMapping("/manager/fileUpload")
    @ResponseBody
    public Object fileUpload(@RequestParam("file") MultipartFile file){
        Map<String,String> map = new HashMap<>();
        if(file.isEmpty()){
            map.put("code","-1");
            return map;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "d:/upload/" + new Date().getTime() + "/";
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest); //保存文件
            map.put("code","0");
            map.put("url", URLEncoder.encode(dest.toString(),"utf-8"));
            return map;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            map.put("code","-2");
            return map;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            map.put("code","-3");
            return map;
        }
    }

    @RequestMapping("/manager/download")
    public String downLoad(HttpServletRequest req, HttpServletResponse response){
        int id = Integer.valueOf(req.getParameter("id"));
        String url = thesisService.getThesisUrl(id);
        //截取最后一个/后面的所有字符(http://127.0.0.1:8080/cms/ReadAddress/1479805098158.jpg)
        String fileName = url.substring(url.lastIndexOf("/")+1);
        if (fileName != null) {
            //设置文件路径
            File file = new File(url);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败";
    }
}