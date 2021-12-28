package com.bitc.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bitc.board.dto.BoardDto;


/* mybatis�� ����Ǿ� �ִٴ� ���� �ǹ��ϴ� ������̼�*/
@Mapper
public interface BoardMapper {
	
	/* ������ ���̽� �����Ͽ� �Խñ� ����� �ҷ����� �޼��� */
	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
}
