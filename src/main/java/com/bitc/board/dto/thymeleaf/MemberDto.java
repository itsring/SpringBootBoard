package com.bitc.board.dto.thymeleaf;

import lombok.Data;

@Data
public class MemberDto {
	private String userId;
	private String userPw;
	private String userEmail;
	private String userName;
}
