package com.bitc.board.dto;

import lombok.Data;

//t_file 테이블과 연동되는 Dto 클래스
// 
@Data
public class BoardFileDto {
	private int fileIdx;
	private int  boardIdx;
	private String originalFileName;
	private String storedFilePath;
	/*
	  원래는 long타입으로 사용해야 하지만 sql에서 사용자가 알아보기 쉽도록 크기를 kByte부터 확인하는 형태로 변경하는 수식이 들어가면서
	  fileSize가 String type으로 변환되어 DTO에서도 String type으로 데이터를 사용함
	  */
	private String fileSize;
}
