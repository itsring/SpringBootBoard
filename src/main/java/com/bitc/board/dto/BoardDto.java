package com.bitc.board.dto;

import java.util.List;

import lombok.Data;

/*�Һ����
 �Һ� : �����ͺ��̽��� ���ʵǴ� dto��ü�� ������ getter/setter�� �ڵ����� �������ִ� ���α׷�
 getter/setter/toString �޼��带 �ڵ������λ����� ��
 */
@Data
public class BoardDto {
	private int Idx;
	private String title;
	private String contents;
	private String createrId;
	private String createdDate;
	private String updateId;
	private String updatedDate;
	private int hitCnt;
	private String passwd;
	
	private List<BoardFileDto> fileList;
}
