package com.example.demo.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class UploadUtils {

    public static String uploadSinglePic(MultipartFile firstPicture, String picturePath) throws IOException {
        //图片上传到项目目录firstPicture下
        String staticPath = System.getProperty("user.dir");
        String rootPath = staticPath.replaceAll("\\\\","/") + File.separator + "src/main/resources/static/"+picturePath;

        /**
         * 文件路径不存在则需要创建文件路径
         */
        File filePath = new File(rootPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        //获取上传图片的原始名称
        String originalFilename = firstPicture.getOriginalFilename();

        //随机生成数字来作为图片的前缀加上图片的格式jpg/png
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        //最终文件名
        String finalFilename = uuid + suffix;

        //复制图片到指定目录
        File realFile = new File(rootPath + File.separator + finalFilename);
        FileUtils.copyInputStreamToFile(firstPicture.getInputStream(), realFile);

        //获取图片的地址
        String localHost = InetAddress.getLocalHost().getHostAddress();
        String firstPictureHref = "/"+picturePath+"/" + finalFilename;
        return firstPictureHref;
    }
}
