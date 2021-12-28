package com.bitc.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.mapper.BoardMapper;


/* ���ο��� �ڹ� ������ ó���ϴ� ������̼�
 * ������ interface ��� �����ϴ� �ǹ� */
@Service
public class BoardServiceImpl implements BoardService {

	
	@Autowired
	private BoardMapper boardMapper;
	
	/* �θ��� BoardService �������̽��� ������ �ִ� �߻�޼��带 ������ */
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		
		/*mybatis�� ����Ǿ� �ִ� BoardMapper�� �̿��Ͽ� ���� �����ͺ��̽����� �����͸� ��ȸ*/
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board) throws Exception{
		boardMapper.insertBoard(board);	
	}
	
}
