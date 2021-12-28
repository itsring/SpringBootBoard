package com.bitc.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.mapper.BoardMapper;


/* 내부에서 자바 로직을 처리하는 어노테이션
 * 지정한 interface 대신 실행하는 의미 */
@Service
public class BoardServiceImpl implements BoardService {

	
	@Autowired
	private BoardMapper boardMapper;
	
	/* 부모인 BoardService 인터페이스가 가지고 있는 추상메서드를 재정의 */
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		
		/*mybatis와 연결되어 있는 BoardMapper를 이용하여 실제 데이터베이스에서 데이터를 조회*/
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board) throws Exception{
		boardMapper.insertBoard(board);	
	}
	
}
