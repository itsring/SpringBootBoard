package com.bitc.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;
import com.github.pagehelper.Page;

/* Controller에서 사용할 비즈니스 로직의 사용방법을 제공 */
public interface BoardService {
	//게시판의 게시글 목록을 불러온느 추상 메서드
	List<BoardDto> selectBoardList() throws Exception;
	//DB에 게시글 추가하는 추상 메서드
	void insertBoard(BoardDto board, MultipartHttpServletRequest multiFiles) throws Exception;
	//지정한 게시글의 모든 정보를 Db에서 가져오는 추상 메서드
	BoardDto selectBoardDetail(int idx) throws Exception;
	//지정한 게시글을 DB에서 삭제하는 추상 메서드
	void deleteBoard(int idx) throws Exception;
	//지정한 게시글을 DB에서 수정하는 추상 메서드
	void updateBoard(BoardDto board) throws Exception;
	
	BoardFileDto selectBoardFileInfo(int FileIdx, int boardIdx) throws Exception;
	
	Page<BoardDto> selectEmpList(int pageNum) throws Exception;
}
