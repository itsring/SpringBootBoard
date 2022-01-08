package com.bitc.board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		/*
		 * apach�� Commons-io �� Commons-fileupload�� ����ϱ� ������ CommonsMultipartResolver��
		 * ���ؼ� MultipartResolver�� �����ϰ� ������ �����ӿ�ũ�� ���
		 */
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		/*�ִ� ���ε� ���� ����ũ�� ����
		 * �⺻ ������ byte��, 5*1024*1024 = 5*1000*1000 => 5byte�� 1000*1000 => 5MB */
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
		return commonsMultipartResolver;
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").
		addResourceLocations("file:///D:/Java11/java404/SpringBootBoard/images/");
	}
}
