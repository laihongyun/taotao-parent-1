package com.taotao.admin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: taotao-admin-web
 * @description: 图片上传到fastdfs
 * @author: lhy
 * @create: 2020-07-17 12:01
 **/
@Controller
public class PictureController {

    @Value("${fastDFSUrl}")
    private String fastDFSUrl;

    /**
    * @Description: 文件上传
    * @Param:  页面接受参数
    * @return:
    */
    @PostMapping(path="/pic/upload",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String upload(@RequestParam("pic")MultipartFile multipartFile) throws JsonProcessingException {

        Map<String,Object> data = new HashMap<>();
        data.put("error",1);
        try{
            String path = this.getClass().getResource("/fastdfs_client.conf").getPath();
                ClientGlobal.init(path);
            StorageClient storageClient = new StorageClient();
            String[] arr = storageClient
                    .upload_file(
                            multipartFile.getBytes(),
                            FilenameUtils.getExtension(multipartFile.getOriginalFilename()),
                            null
                    );
//            拼接最后的url
            StringBuilder builder = new StringBuilder(fastDFSUrl);
            for(String str:arr){
                builder.append("/"+str);
            }
            System.out.println(builder.toString());
            data.put("url","http://"+builder.toString());
            data.put("error",0);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ObjectMapper().writeValueAsString(data);
    }
}
