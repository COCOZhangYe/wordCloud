package com.zy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\";
        //registry.addResourceHandler("/static/images/**").addResourceLocations("file:"+path);

        //获取文件的真实路径 work_project代表项目工程名 需要更改
        //String path = System.getProperty("user.dir")+"\\work_project\\src\\main\\resources\\static\\pciture\\";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler("/static/images/**").
                    addResourceLocations("file:"+path);
        }else{//linux和mac系统 可以根据逻辑再做处理
            registry.addResourceHandler("/static/images/**").
                    addResourceLocations("file:"+path);
        }

    }
}
