package com.bitc.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.service.BoardService;

/*해당 클래스가 MVC모델에서 control부분을 담당하는 파일리라는것을 알려주는 어노테이션
 * @Controller : 일반적인 control부분을 담당하는 어노테이션
 * @restController : Restful API 방식을 사용할 경우 사용하느 어노테이션 (클라이언트에 데이터 자체를 전송)*/
@Controller
public class BoardController {
	
	
	/*비즈니스 로직을 처리하는 서비스 빈과 연결*/
	@Autowired
	private BoardService boardService;
	
	/*사용자가 접속 할 수 있는 주소를 해당 메서드와 연결하는 어노테이션 */
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception{
		/* ModelAndView 클래스 : 데이터와 화면을 동시에 가지고 있는 클래스 
		 * 해당 클래스 타입을 반환하면 지정한 html 파일에 데이터를 포함하여 클라이언트에게 전송
		 * ModelAndView 클래스 생성자의 매개변수로 html 템플릿 파일의 위치를 지정함
		 * html템플릿의 위치는 /src/main/resoures/templates를 기본으로 함*/
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		/*서비스 빈의 selectBoardList()메서드를 실행하여 실제 데이터 목록을 가져옴 */
		List<BoardDto> boardList = boardService.selectBoardList();
		int arrays[]= {1,2,3,4,5};
		
		/*가져온 데이터 목록을 ModelAndView 클래스 객체에 추가 */
		mv.addObject("boardList",boardList);
		mv.addObject("arrays",arrays);
		
		/*클라이언트에 ModelAndView 클래스 타입의 객체를 전송*/
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
