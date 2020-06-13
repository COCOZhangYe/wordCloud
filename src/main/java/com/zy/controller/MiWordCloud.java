package com.zy.controller;

import com.zy.service.WordCloudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@RestController
public class MiWordCloud {

    // 注入userService
    @Resource(name = "wordCloudService")
    private WordCloudService wordCloudService;

    @RequestMapping("/materialData")
    //接受form表单
    public String getWordCloud(@RequestParam(value ="words") List<String> words){

        String name = wordCloudService.wordCloud(words);
        String base64 = wordCloudService.getImageStr(System.getProperty("java.io.tmpdir")+'/'+name);
        return base64;
    }

    @RequestMapping("/fileWords")
    //接受form表单
    public String getTxtFile(@RequestParam(value ="file") MultipartFile file) throws IOException {
        String pname = file.getName();//获取文件名
        List<String> words;
        File htmlFile = File.createTempFile(pname, ".txt"); //创建临时文件
        String pathName = htmlFile.getCanonicalPath();//想要存储文件的地址
        FileOutputStream fos ;
        fos = new FileOutputStream(pathName);
        fos.write(file.getBytes()); // 写入文件
        words = wordCloudService.readTxt(pathName);
        String name = wordCloudService.wordCloud(words);
        String base64 = wordCloudService.getImageStr(System.getProperty("java.io.tmpdir")+'/'+name);
        // 终止后删除临时文件
        htmlFile.deleteOnExit();
        return base64;
    }
}
