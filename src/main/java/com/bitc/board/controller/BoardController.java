package com.bitc.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.service.BoardService;

/*�ش� Ŭ������ MVC�𵨿��� control�κ��� ����ϴ� ���ϸ���°��� �˷��ִ� ������̼�
 * @Controller : �Ϲ����� control�κ��� ����ϴ� ������̼�
 * @restController : Restful API ����� ����� ��� ����ϴ� ������̼� (Ŭ���̾�Ʈ�� ������ ��ü�� ����)*/
@Controller
public class BoardController {
	
	
	/*����Ͻ� ������ ó���ϴ� ���� ��� ����*/
	@Autowired
	private BoardService boardService;
	
	/*����ڰ� ���� �� �� �ִ� �ּҸ� �ش� �޼���� �����ϴ� ������̼� */
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		/* ModelAndView Ŭ���� : �����Ϳ� ȭ���� ���ÿ� ������ �ִ� Ŭ���� 
		 * �ش� Ŭ���� Ÿ���� ��ȯ�ϸ� ������ html ���Ͽ� �����͸� �����Ͽ� Ŭ���̾�Ʈ���� ����
		 * ModelAndView Ŭ���� �������� �Ű������� html ���ø� ������ ��ġ�� ������
		 * html���ø��� ��ġ�� /src/main/resoures/templates�� �⺻���� ��*/
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		/*���� ���� selectBoardList()�޼��带 �����Ͽ� ���� ������ ����� ������ */
		List<BoardDto> boardList = boardService.selectBoardList();
		int arrays[]= {1,2,3,4,5};
		
		/*������ ������ ����� ModelAndView Ŭ���� ��ü�� �߰� */
		mv.addObject("boardList",boardList);
		mv.addObject("arrays",arrays);
		
		/*Ŭ���̾�Ʈ�� ModelAndView Ŭ���� Ÿ���� ��ü�� ����*/
		return mv;
	}
	@RequestMapping("board/writeBoard.do")
	public String writeBoard() throws Exception{
		return "board/writeBoard";
	}
	@RequestMapping("board/insertBoard.do")
	public String insertBoard(BoardDto board) throws Exception{
		boardService.insertBoard(board);
		
		return "redirect:/board/openBoardList.do";
	}
	
}
