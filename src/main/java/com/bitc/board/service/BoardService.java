package com.bitc.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;

/* Controller���� ����� ����Ͻ� ������ ������� ���� */
public interface BoardService {
	//�Խ����� �Խñ� ����� �ҷ��´� �߻� �޼���
	List<BoardDto> selectBoardList() throws Exception;
	//DB�� �Խñ� �߰��ϴ� �߻� �޼���
	void insertBoard(BoardDto board, MultipartHttpServletRequest multiFiles) throws Exception;
	//������ �Խñ��� ��� ������ Db���� �������� �߻� �޼���
	BoardDto selectBoardDetail(int idx) throws Exception;
	//������ �Խñ��� DB���� �����ϴ� �߻� �޼���
	void deleteBoard(int idx) throws Exception;
	//������ �Խñ��� DB���� �����ϴ� �߻� �޼���
	void updateBoard(BoardDto board) throws Exception;
	
	BoardFileDto selectBoardFileInfo(int FileIdx, int boardIdx) throws Exception;
}
