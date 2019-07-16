package com.edu.bupt.wechatpost.service.impl;

import com.edu.bupt.wechatpost.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Service
public class DataServiceImpl implements DataService {
    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    private final String serverIp = "https://smart.gantch.cn/"; // ip域名（端口号）
//    private final String serverIp = "https://47.104.8.164:80/"; // 服务器ip域名（端口号）
//    private final String serverIp = "http://656a37cc.ngrok.io/"; // 本地（端口号）
    private final String downloadInterface = "api/v1/wechatPost/download?";
    private final String imageBasePath = System.getProperty("user.dir").replace("\\","/")+"/image/";

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        logger.info("正在上传图片...");
        String imageName = "";
        if(image != null){
            imageName = image.getOriginalFilename();
            //  图片类型
            String suffixName = imageName.substring(imageName.lastIndexOf("."));
            //  随机字符串重命名图片，解决liunx下中文路径的图片显示问题
            imageName = UUID.randomUUID() + suffixName;
            logger.info("上传的图片名为：" + imageName);
            String imageUrl = imageBasePath + imageName;
            try {
                //  打开文件流，若目录不存在则新建
                File downloadFile = new File(imageUrl);
                if (!downloadFile.getParentFile().exists()) {
                    downloadFile.getParentFile().mkdirs();
                }
                //  保存文件到后台服务器
                image.transferTo(downloadFile);
                String imagePath = serverIp + downloadInterface + "imageName=" + imageName;
                logger.info("上传图片成功，保存路径为: " + imageUrl);
                logger.info( "访问路径为: " + imagePath);
                return imagePath;
            } catch (IOException e) {
                logger.info("上传文件失败\n" + e.getMessage());
            }
        }
        return "";
    }

    @Override
    public void downloadImage(String imageName, HttpServletRequest request, HttpServletResponse response) throws IOException{
        logger.info("正在下载图片...");
        if(!imageName.equals("")) {
            FileInputStream is = null;
            String imageUrl = imageBasePath + imageName;
            try {
                File imageFile = new File(imageUrl);
                is = new FileInputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(is != null){
                int length = is.available();
                byte[] buffer = new byte[length];
                try {
                    is.read(buffer);
                    is.close();
                    OutputStream os = response.getOutputStream();
                    os.write(buffer);
                    os.close();
                    logger.info("加载图片成功！");
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info("加载图片失败\n" + e.getMessage());
                }
            }
        }
    }

}
