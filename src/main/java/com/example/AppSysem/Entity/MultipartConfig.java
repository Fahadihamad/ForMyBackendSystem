package com.example.AppSysem.Entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartConfig {
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        // Set the maximum file size (in bytes) that can be uploaded
        resolver.setMaxUploadSize(10 * 1024 * 1024); // 10MB
        return resolver;
    }
}
