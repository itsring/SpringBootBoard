package com.bitc.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;
import com.bitc.board.service.BoardService;

/*해당 클래스가 MVC모델에서 control부분을 담당하는 파일리라는것을 알려주는 어노테이션
 * @Controller : 일반적인 control부분을 담당하는 어노테이션
 * @restController : Restful API 방식을 사용할 경우 사용하느 어노테이션 (클라이언트에 데이터 자체를 전송)*/
@Controller
public class BoardController {

	/* 비즈니스 로직을 처리하는 서비스 빈과 연결 */
	@Autowired
	private BoardService boardService;

	/* 사용자가 접속 할 수 있는 주소를 해당 메서드와 연결하는 어노테이션 */
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception {
		/*
		 * ModelAndView 클래스 : 데이터와 화면을 동시에 가지고 있는 클래스 해당 클래스 타입을 반환하면 지정한 html 파일에 데이터를
		 * 포함하여 클라이언트에게 전송 ModelAndView 클래스 생성자의 매개변수로 html 템플릿 파일의 위치를 지정함 html템플릿의 위치는
		 * /src/main/resoures/templates를 기본으로 함
		 */
		ModelAndView mv = new ModelAndView("/board/boardList");

		/* 서비스 빈의 selectBoardList()메서드를 실행하여 실제 데이터 목록을 가져옴 */
		List<BoardDto> boardList = boardService.selectBoardList();
		int arrays[] = { 1, 2, 3, 4, 5 };

		/* 가져온 데이터 목록을 ModelAndView 클래스 객체에 추가 */
		mv.addObject("boardList", boardList);
		mv.addObject("arrays", arrays);

		/* 클라이언트에 ModelAndView 클래스 타입의 객체를 전송 */
		return mv;
	}

	/*
	 * 클라이언트 요청 주소와 메서드를 연동 웹브라우저의 주소창에 서버주소 : 포트번호/board/writeBoard.do를 입력하면 연결된
	 * wirteBoard() 메서드가 실행됨
	 */
	@RequestMapping("board/writeBoard.do")
	public String writeBoard() throws Exception {
		/*
		 * 반환타입이 String일 경우 기본적으로 templates 폴더에 있는 템플릿일을 지정함 tmeplates 폴더 아래에 있는
		 * /board/boardWirte.html 파일을 지정함
		 */
		return "board/writeBoard";
	}

	/*
	 * RequestMapping과 연결된 /board/insertBoard.do와 연결된 insertBoard()메서드가 실행 매개변수로
	 * 클라이언트에서 BoardDto 타입의 객체 board를 받아옴
	 */
	@RequestMapping("board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multiFiles) throws Exception {
		/*
		 * Spring framework에서 미리 생성한 BoardService타입의 객체 boardService를 insertBoard()메서드를
		 * 실행 클라이언트에서 업로드된 파일 정보를 분석하기 위해서 BoardService에 파일정보를 매개변수로 추가함
		 */
		boardService.insertBoard(board, multiFiles);

		return "redirect:/board/openBoardList.do";
	}

	@RequestMapping("/board/selectBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam("idx") int idx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		// DB서버에 연결해서 데이터 가져오기
		BoardDto board = boardService.selectBoardDetail(idx);

		mv.addObject("board", board);

		return mv;
	}

	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception {
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}

	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(@RequestParam("idx") int idx) throws Exception {
		boardService.deleteBoard(idx);

		return "redirect:/board/openBoardList.do";
	}

	@RequestMapping("/board/downloadBoardFile.do")
	/*HttpServletResponse : 서버가 클라이언트에게 전달할 데이터를 담고있는 내장객체
	 */
	public void downloadBoardFile(@RequestParam("fileIdx") int fileIdx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response)
			throws Exception {
		/*DB에서 지정한 파일에 대한 정보를 가져오는 명령
		 * 사용자가 클릭한 파일에 대한 정보를 가져오기 때문에 BoardFileDto 타입으로 가져옴
		 List는 여러개의 Dto타입을 가져올때 사용*/
		BoardFileDto boardFile = boardService.selectBoardFileInfo(fileIdx, boardIdx);
		/*isEmpty==false =true면 데이터가 있다는 뜻 */
		if (ObjectUtils.isEmpty(boardFile) == false) {
			//파일 이름 가져오기
			String fileName = boardFile.getOriginalFileName();
			// apache commons.io의 FileUtils의 클래스를 사용하여 파일을 byte배열 타입으로 변환함
			// getStoredFilePath()를 통해서 서버에 실제 저장된 파일을 불러와서 Byte[]타입의 배열로 변환
			//tcp/ip 통신의 기본 전송 데이터타입이 byte[]이기 때문에 
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
// 클라이언트에 전송할 데이터 내장객체인 response의 헤더에 콘텐츠 타입, 크기, 형태를 설정
/*
 application/octet-stream : MIME 타입으로 지정된 데이터 타입이 아닌 다른 모든 데이터 타입을 위한 기본값
 http:/developer.mozilla.org/ko/docs/web/HTTp/Basics_of_HTTP/MIME_types/Common_types
 */
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			/*attachment; fileName에서 공백기호가 없을경우 다운로드가 안됨
			 *UTF-8을 입력하지 ㅇ낳을경우 문자가 깨진 형태로 다운로드 될 가능성이 높음*/
			response.setHeader("Content-Disposition",
					"attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
//앞에서 읽어온 파일 정보의 byte[]데이터를 response 내장 객체에 저장
			response.getOutputStream().write(files);
//마지막으로 response 내장객체의 버퍼를 정리하고 닫음
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
