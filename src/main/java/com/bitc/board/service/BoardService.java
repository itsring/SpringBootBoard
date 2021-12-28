package com.bitc.board.service;

import java.util.List;

import com.bitc.board.dto.BoardDto;

/* Controller에서 사용할 비즈니스 로직의 사용방법을 제공 */
public interface BoardService {
	
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
}
