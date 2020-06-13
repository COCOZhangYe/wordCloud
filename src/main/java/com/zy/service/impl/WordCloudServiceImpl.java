package com.zy.service.impl;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;
import com.zy.service.WordCloudService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service("wordCloudService")
public class WordCloudServiceImpl implements WordCloudService {
    //读取txt文本
    public List<String> readTxt(String fileName){
        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8")); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<String> s = Collections.singletonList(new String(sb)); //StringBuffer ==> String
        System.out.println("txt内容为==> " + s);
        return s;
    }
    //图片转64位
    public String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        Base64.Encoder encoder = Base64.getEncoder();
        String result = encoder.encodeToString(data);
        return result;

    }
    //删除文件
    public void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    this.deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
        } else {
            System.out.println("所删除的文件不存在");
        }
    }

    //词云功能
    public String wordCloud(List<String> words){
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        frequencyAnalyzer.setMaxWordLength(4);

        // 引入中文解析器
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());

        final List<WordFrequency> wordFrequencyList = frequencyAnalyzer.load(words);
        System.out.println(words);
        for(int i=0;i<wordFrequencyList.size();i++)
        {
            System.out.println("单词"+wordFrequencyList.get(i).getWord()+"  "+"数量:"+wordFrequencyList.get(i).getFrequency());
        }
        // 设置图片分辨率
        Dimension dimension = new Dimension(500, 500);
        // 此处的设置采用内置常量即可，生成词云对象
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        Font font = new Font("STSong-Light", 2, 18);
        wordCloud.setKumoFont(new KumoFont(font));
        wordCloud.setPadding(2);
        wordCloud.setColorPalette(new ColorPalette(new Color(0xed1941), new Color(0xf26522), new Color(0x845538),new Color(0x8a5d19),new Color(0x7f7522),new Color(0x5c7a29),new Color(0x1d953f),new Color(0x007d65),new Color(0x65c294)));
        wordCloud.setBackground(new CircleBackground(200));
        wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
        wordCloud.setBackgroundColor(new Color(255, 255, 255));
        //设置背景图层为圆形
        wordCloud.setBackground(new CircleBackground(255));
        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));

        // 生成词云
        wordCloud.build(wordFrequencyList);
        OutputStream output = new ByteArrayOutputStream();
        wordCloud.writeToStream("png", output);

        //将文字写入图片
        //wordCloud.build(wordFrequencyList);
        //生成图片
        //wordCloud.writeToFile("output/chinese_language_circle.png");
        String name = null;
        File htmlFile = null; //创建临时文件
        try {
            //htmlFile = File.createTempFile("temp"+1+(int)(Math.random()*(100000000+1-1)), ".png");
            htmlFile = File.createTempFile("temp", ".png");
            name = htmlFile.getName();
            System.out.println(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //wordCloud.writeToFile(htmlFile.getCanonicalPath()); //词云图片生成到临时文件中
        //deleteFile(htmlFile); // 删除临时文件

        //wordCloud.writeToFile("src/main/resources/static/images/"+name);//将图存在静态资源
        wordCloud.writeToFile(System.getProperty("java.io.tmpdir")+'/'+name);
        System.out.println(System.getProperty("java.io.tmpdir"));

        //return "/images/"+name;

        //图片转64位
        //byte[] outputByte = ((ByteArrayOutputStream)output).toByteArray();
        //return org.apache.commons.codec.binary.Base64.encodeBase64String(outputByte);
        return name;

    }

}
