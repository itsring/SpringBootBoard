package com.bitc.board.dto;

import java.util.List;

import lombok.Data;

/*롬복사용
 롬복 : 데이터베이스와 매필되는 dto객체를 생성시 getter/setter를 자동으로 생성해주는 프로그램
 getter/setter/toString 메서드를 자동을으로생성해 줌
 */
@Data
public class BoardDto {
	private int idx;
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
