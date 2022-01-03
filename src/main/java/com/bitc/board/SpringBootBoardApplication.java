package com.bitc.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

/*
 ��������Ʈ�� �⺻ ����� �ڵ����� (@EnableAutoConfiguration)�� ������
 apache�� Commons-fileupload�� ����ϱ� ���ؼ� ������ �߱� ������ �ش� �κ��� �ڵ� ������ �����ؾ��� 
 */
@SpringBootApplication(exclude = { MultipartAutoConfiguration.class })
public class SpringBootBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBoardApplication.class, args);
	}

}
