//package com.wh.searchall.config;
//
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//
//import javax.servlet.MultipartConfigElement;
//
///**
// * @author wanghan
// * @description 配置文件上传大小
// * @date 2021/2/2 8:45
// **/
//public class TomcatConfig {
//    @Value("1024KB")
//    private String MaxFileSize;
//    @Value("20MB")
//    private String MaxRequestSize;
//
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //  单个数据大小
//        factory.setMaxFileSize();
//        // 总上传数据大小
//        factory.setMaxRequestSize(MaxRequestSize);
//
//        return factory.createMultipartConfig();
//    }
//}
