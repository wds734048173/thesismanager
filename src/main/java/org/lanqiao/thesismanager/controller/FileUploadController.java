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
import java.util.Properties;

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
    public Object fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String,String> map = new HashMap<>();
        if(file.isEmpty()){
            map.put("code","-1");
            return map;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = FileUploadController.class.getClassLoader().getResourceAsStream("upload.properties");
        // 使用properties对象加载输入流
        properties.load(in);
        //获取key对应的value值
        String realname = properties.getProperty("uploadPath");
//        String realname = "C:/Users/WDS/IdeaProjects/thesismanager/src/main/resources/static";
        String path = "/upload/" + new Date().getTime() + "/";
        //目标文件
        File dest = new File(realname + path + "/" + fileName);
        //保存数据库文件
        File savePath = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest); //保存文件
            map.put("code","0");
            map.put("url", URLEncoder.encode(savePath.toString(),"utf-8"));
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
    @ResponseBody
    public String downLoad(HttpServletRequest req, HttpServletResponse response) throws IOException {
        int id = Integer.valueOf(req.getParameter("id"));
        String url = thesisService.getThesisUrl(id);
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = FileUploadController.class.getClassLoader().getResourceAsStream("upload.properties");
        // 使用properties对象加载输入流
        properties.load(in);
        //获取key对应的value值
        String realname = properties.getProperty("uploadPath");
//        String realname = "C:/Users/WDS/IdeaProjects/thesismanager/src/main/resources/static";
        //截取最后一个\后面的所有字符
        String fileName = url.substring(url.lastIndexOf("\\")+1);
        if (fileName != null) {
            //设置文件路径
            File file = new File(realname + url);
            if (file.exists()) {
                response.reset(); // 非常重要
                response.setContentType("application/force-download");// 设置强制下载不打开
                //采用中文文件名需要在此处转码
                fileName = new String(fileName.getBytes("GB2312"),"ISO_8859_1");
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
                    os.close();
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
