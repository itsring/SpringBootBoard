package com.bitc.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;


/* mybatis�� ����Ǿ� �ִٴ� ���� �ǹ��ϴ� ������̼�*/
@Mapper
public interface BoardMapper {
	
	/* ������ ���̽� �����Ͽ� �Խñ� ����� �ҷ����� �޼��� */
	List<BoardDto> selectBoardList() throws Exception;
	
	//DB�� �����Ͽ� �Խñ��� ����ϴ� �޼���
	void insertBoard(BoardDto board) throws Exception;
	
	//DB�� �����Ͽ� ������ �Խñ��� ��� ������ �������� �޼���
	BoardDto selectBoardDetail(int idx) throws Exception;
	
	//DB�� �����Ͽ� ������ �Խñ��� DB���� �����ϴ� �޼���
	void deleteBoard(int idx) throws Exception;
	
	//DB�� �����Ͽ� ������ �Խñ��� DB���� �����ϴ� �޼���
	void updateBoard(BoardDto board) throws Exception;
	
	//DB�� �����Ͽ� ������ �Խñ��� DB���� ��ȸ���� �����ϴ� �޼���
	void updateHitCnt(int idx) throws Exception;

	//DB�� �����Ͽ� �Խñ��� ÷�������� ������ DB�� �����ϴ� �޼���
	void insertBoardFileList(List<BoardFileDto> list) throws Exception;
	
	//DB�� �����Ͽ� �Խñ��� ÷������ ������ �������� �޼���
	List<BoardFileDto> selectBoardFileList(int idx) throws Exception;
	
	
	//DB�� �����Ͽ� �Խñ��� ÷������ ������ �������� �޼��� 
	//@Param : �Ű������� �޾ƿ� ���Ͽ� ���� ������ ǥ���ϴ� ������̼�
	//mapper���� @Param���� ������ �̸��� xml ���Ͽ��� ����� �� ���� 
	BoardFileDto selectBoardFileInfo(@Param("fileIdx") int FileIdx, @Param("boardIdx") int boardIdx) throws Exception;
}















