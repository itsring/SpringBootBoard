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
import com.github.pagehelper.PageInfo;

/*�ش� Ŭ������ MVC�𵨿��� control�κ��� ����ϴ� ���ϸ���°��� �˷��ִ� ������̼�
 * @Controller : �Ϲ����� control�κ��� ����ϴ� ������̼�
 * @restController : Restful API ����� ����� ��� ����ϴ� ������̼� (Ŭ���̾�Ʈ�� ������ ��ü�� ����)*/
@Controller
public class BoardController {

	/* ����Ͻ� ������ ó���ϴ� ���� ��� ���� */
	@Autowired
	private BoardService boardService;

	/* ����ڰ� ���� �� �� �ִ� �ּҸ� �ش� �޼���� �����ϴ� ������̼� */
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList(@RequestParam(required=false, defaultValue= "1", value="pageNum") int pageNum) throws Exception {
		/*
		 * ModelAndView Ŭ���� : �����Ϳ� ȭ���� ���ÿ� ������ �ִ� Ŭ���� �ش� Ŭ���� Ÿ���� ��ȯ�ϸ� ������ html ���Ͽ� �����͸�
		 * �����Ͽ� Ŭ���̾�Ʈ���� ���� ModelAndView Ŭ���� �������� �Ű������� html ���ø� ������ ��ġ�� ������ html���ø��� ��ġ��
		 * /src/main/resoures/templates�� �⺻���� ��
		 */
		ModelAndView mv = new ModelAndView("/board/boardList");
		PageInfo<BoardDto> page = new PageInfo<>(boardService.selectEmpList(pageNum), 5);
		
		mv.addObject("users", page);
		/* ���� ���� selectBoardList()�޼��带 �����Ͽ� ���� ������ ����� ������ */
		List<BoardDto> boardList = boardService.selectBoardList();
		int arrays[] = { 1, 2, 3, 4, 5 };

		/* ������ ������ ����� ModelAndView Ŭ���� ��ü�� �߰� */
		mv.addObject("boardList", boardList);
		mv.addObject("arrays", arrays);

		/* Ŭ���̾�Ʈ�� ModelAndView Ŭ���� Ÿ���� ��ü�� ���� */
		return mv;
	}

	/*
	 * Ŭ���̾�Ʈ ��û �ּҿ� �޼��带 ���� ���������� �ּ�â�� �����ּ� : ��Ʈ��ȣ/board/writeBoard.do�� �Է��ϸ� �����
	 * wirteBoard() �޼��尡 �����
	 */
	@RequestMapping("board/writeBoard.do")
	public String writeBoard() throws Exception {
		/*
		 * ��ȯŸ���� String�� ��� �⺻������ templates ������ �ִ� ���ø����� ������ tmeplates ���� �Ʒ��� �ִ�
		 * /board/boardWirte.html ������ ������
		 */
		return "board/writeBoard";
	}

	/*
	 * RequestMapping�� ����� /board/insertBoard.do�� ����� insertBoard()�޼��尡 ���� �Ű�������
	 * Ŭ���̾�Ʈ���� BoardDto Ÿ���� ��ü board�� �޾ƿ�
	 */
	@RequestMapping("board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multiFiles) throws Exception {
		/*
		 * Spring framework���� �̸� ������ BoardServiceŸ���� ��ü boardService�� insertBoard()�޼��带
		 * ���� Ŭ���̾�Ʈ���� ���ε�� ���� ������ �м��ϱ� ���ؼ� BoardService�� ���������� �Ű������� �߰���
		 */
		boardService.insertBoard(board, multiFiles);

		return "redirect:/board/openBoardList.do";
	}

	@RequestMapping("/board/selectBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam("idx") int idx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		// DB������ �����ؼ� ������ ��������
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
	/*HttpServletResponse : ������ Ŭ���̾�Ʈ���� ������ �����͸� ����ִ� ���尴ü
	 */
	public void downloadBoardFile(@RequestParam("fileIdx") int fileIdx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response)
			throws Exception {
		/*DB���� ������ ���Ͽ� ���� ������ �������� ���
		 * ����ڰ� Ŭ���� ���Ͽ� ���� ������ �������� ������ BoardFileDto Ÿ������ ������
		 List�� �������� DtoŸ���� �����ö� ���*/
		BoardFileDto boardFile = boardService.selectBoardFileInfo(fileIdx, boardIdx);
		/*isEmpty==false =true�� �����Ͱ� �ִٴ� �� */
		if (ObjectUtils.isEmpty(boardFile) == false) {
			//���� �̸� ��������
			String fileName = boardFile.getOriginalFileName();
			// apache commons.io�� FileUtils�� Ŭ������ ����Ͽ� ������ byte�迭 Ÿ������ ��ȯ��
			// getStoredFilePath()�� ���ؼ� ������ ���� ����� ������ �ҷ��ͼ� Byte[]Ÿ���� �迭�� ��ȯ
			//tcp/ip ����� �⺻ ���� ������Ÿ���� byte[]�̱� ������ 
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
// Ŭ���̾�Ʈ�� ������ ������ ���尴ü�� response�� ����� ������ Ÿ��, ũ��, ���¸� ����
/*
 application/octet-stream : MIME Ÿ������ ������ ������ Ÿ���� �ƴ� �ٸ� ��� ������ Ÿ���� ���� �⺻��
 http:/developer.mozilla.org/ko/docs/web/HTTp/Basics_of_HTTP/MIME_types/Common_types
 */
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			/*attachment; fileName���� �����ȣ�� ������� �ٿ�ε尡 �ȵ�
			 *UTF-8�� �Է����� ��������� ���ڰ� ���� ���·� �ٿ�ε� �� ���ɼ��� ����*/
			response.setHeader("Content-Disposition",
					"attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
//�տ��� �о�� ���� ������ byte[]�����͸� response ���� ��ü�� ����
			response.getOutputStream().write(files);
//���������� response ���尴ü�� ���۸� �����ϰ� ����
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
