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

/* 내부에서 자바 로직을 처리하는 어노테이션
 * 지정한 interface 대신 실행하는 의미 */
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;

	@Autowired
	private FileUtils fileUtils;

	/* 부모인 BoardService 인터페이스가 가지고 있는 추상메서드를 재정의 */
	@Override
	public List<BoardDto> selectBoardList() throws Exception {

		/* mybatis와 연결되어 있는 BoardMapper를 이용하여 실제 데이터베이스에서 데이터를 조회 */
		return boardMapper.selectBoardList();
	}

	// 부모인 BoardService 인터페이스가 가지고 있는 추상메서드를 재정이
	// 업로드된 파일을 분석하기 위한 매개변수 및 내용 추가
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multiFiles) throws Exception {
		// DB를 조작하는 Mapper의 insertBoard()메서드를 사용하여 실제 DB에 데이터를 추가함
		// 실제로 DB에 저장하지 않기 위해 잠시 주석처리
		boardMapper.insertBoard(board);
//		
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getIdx(), multiFiles);
		if (CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
		/*
		 * ObjectUtils : 스프링프레임워크에서 지원하는 유틸 클래스, 지정한 객체가 비었는지 아닌지 판단함 isEmpty() : 지정한
		 * 객체가 비었는지 아닌지 확인, 비었으면 true, 아니면 false
		 * 
		 */
		/*
		 * if(ObjectUtils.isEmpty(multiFiles)==false) { 업로드된 파일정보 객체가 가지고있는 모든 파일 이름을 반환
		 * Iterator<String> iterator = multiFiles.getFileNames(); String name; // 각각의 파일
		 * 명을 저장할 변수
		 * 
		 * 데이터가 있는지 확인 while(iterator.hasNext()) { name = iterator.next(); //데이터 반환
		 * System.out.println("file tag name : "+name); //파일명 출력 //파일에 대한 모든 정보를
		 * MultipartFile 클래스로 생성한 List타입으로 반환 List<MultipartFile> list =
		 * multiFiles.getFiles(name);
		 * 
		 * //파일 정보 출력 for(MultipartFile mFile : list) {
		 * System.out.println("---------------Start file info--------------- ");
		 * System.out.println("file name : "+ mFile.getOriginalFilename()); // 파일 명
		 * System.out.println("file size : "+ mFile.getSize()); //파일 크기
		 * System.out.println("file content type : "+ mFile.getContentType()); //파일 타입
		 * System.out.println("----------------End file info---------------- "); } } }
		 */
	}

	@Override
	public BoardDto selectBoardDetail(int idx) throws Exception {
		boardMapper.updateHitCnt(idx);
		//현재 게시글 정보에는 첨부파일에 대한 정보가 없음(게시글에 대한 정보만 있음)
		BoardDto board = boardMapper.selectBoardDetail(idx);
		
		//해당 게스글에 첨부된 파일 리스트를 mapper를 통해서 가져옴
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(idx);
		//BoardDto 클래스 타입의 객체에 파일 리스트를 저장함
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














