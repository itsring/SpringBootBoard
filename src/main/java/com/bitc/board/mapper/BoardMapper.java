package com.bitc.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bitc.board.dto.BoardDto;


/* mybatis와 연결되어 있다는 것을 의미하는 어노테이션*/
@Mapper
public interface BoardMapper {
	
	/* 데이터 베이스 연결하여 게시글 목록을 불러오는 메서드 */
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
}
