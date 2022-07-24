package com.bitc.board.dto;

import lombok.Data;

//t_file ���̺�� �����Ǵ� Dto Ŭ����
// 
@Data
public class BoardFileDto {
	private int fileIdx;
	private int  boardIdx;
	private String fileSize;
	private String originalFileName;
	private String storedFilePath;
	/*
	  ������ longŸ������ ����ؾ� ������ sql���� ����ڰ� �˾ƺ��� ������ ũ�⸦ kByte���� Ȯ���ϴ� ���·� �����ϴ� ������ ���鼭
	  fileSize�� String type���� ��ȯ�Ǿ� DTO������ String type���� �����͸� �����
	  */
	
}
