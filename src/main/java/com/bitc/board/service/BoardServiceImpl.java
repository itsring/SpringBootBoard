package com.bitc.board.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.board.common.FileUtils;
import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;
import com.bitc.board.mapper.BoardMapper;

/* ���ο��� �ڹ� ������ ó���ϴ� ������̼�
 * ������ interface ��� �����ϴ� �ǹ� */
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private FileUtils fileUtils;

	/* �θ��� BoardService �������̽��� ������ �ִ� �߻�޼��带 ������ */
	@Override
	public List<BoardDto> selectBoardList() throws Exception {

		/* mybatis�� ����Ǿ� �ִ� BoardMapper�� �̿��Ͽ� ���� �����ͺ��̽����� �����͸� ��ȸ */
		return boardMapper.selectBoardList();
	}

	// �θ��� BoardService �������̽��� ������ �ִ� �߻�޼��带 ������
	// ���ε�� ������ �м��ϱ� ���� �Ű����� �� ���� �߰�
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multiFiles) throws Exception {
		// DB�� �����ϴ� Mapper�� insertBoard()�޼��带 ����Ͽ� ���� DB�� �����͸� �߰���
		// ������ DB�� �������� �ʱ� ���� ��� �ּ�ó��
		boardMapper.insertBoard(board);
//		
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getIdx(), multiFiles);
		if (CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
		/*
		 * ObjectUtils : �����������ӿ�ũ���� �����ϴ� ��ƿ Ŭ����, ������ ��ü�� ������� �ƴ��� �Ǵ��� isEmpty() : ������
		 * ��ü�� ������� �ƴ��� Ȯ��, ������� true, �ƴϸ� false
		 * 
		 */
		/*
		 * if(ObjectUtils.isEmpty(multiFiles)==false) { ���ε�� �������� ��ü�� �������ִ� ��� ���� �̸��� ��ȯ
		 * Iterator<String> iterator = multiFiles.getFileNames(); String name; // ������ ����
		 * ���� ������ ����
		 * 
		 * �����Ͱ� �ִ��� Ȯ�� while(iterator.hasNext()) { name = iterator.next(); //������ ��ȯ
		 * System.out.println("file tag name : "+name); //���ϸ� ��� //���Ͽ� ���� ��� ������
		 * MultipartFile Ŭ������ ������ ListŸ������ ��ȯ List<MultipartFile> list =
		 * multiFiles.getFiles(name);
		 * 
		 * //���� ���� ��� for(MultipartFile mFile : list) {
		 * System.out.println("---------------Start file info--------------- ");
		 * System.out.println("file name : "+ mFile.getOriginalFilename()); // ���� ��
		 * System.out.println("file size : "+ mFile.getSize()); //���� ũ��
		 * System.out.println("file content type : "+ mFile.getContentType()); //���� Ÿ��
		 * System.out.println("----------------End file info---------------- "); } } }
		 */
	}

	@Override
	public BoardDto selectBoardDetail(int idx) throws Exception {
		boardMapper.updateHitCnt(idx);
		//���� �Խñ� �������� ÷�����Ͽ� ���� ������ ����(�Խñۿ� ���� ������ ����)
		BoardDto board = boardMapper.selectBoardDetail(idx);
		
		//�ش� �Խ��ۿ� ÷�ε� ���� ����Ʈ�� mapper�� ���ؼ� ������
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(idx);
		//BoardDto Ŭ���� Ÿ���� ��ü�� ���� ����Ʈ�� ������
		board.setFileList(fileList);
		return board;
	}

	@Override
	public void deleteBoard(int idx) throws Exception {
		boardMapper.deleteBoard(idx);
	}

	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}
	
	@Override
	public BoardFileDto selectBoardFileInfo(@Param("fileIdx") int fileIdx, @Param("boardIdx") int boardIdx) throws Exception{
		return boardMapper.selectBoardFileInfo(fileIdx, boardIdx);
	}
}














