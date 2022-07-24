package com.bitc.board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.board.dto.BoardFileDto;

//@Component : 스프링프레임워크가 자동으로 등록하여 객체를 실행할 자바클래스(빈즈와 차이점이 거의 없음)
@Component
public class FileUtils {

	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multiFiles) throws Exception {
		/* 매개변수로 받은 파일 정보가 없을경우 null을 리턴 */
		if (ObjectUtils.isEmpty(multiFiles)) {
			return null;
		}
		/* 매개변수로 받은 파일 정보에서 BoardFileDto 클래스에서 사용하는 정보만 추려내어서 리스트로 생성하여 저장할 변수 */
		List<BoardFileDto> fileList = new ArrayList<>();
		// 날짜 사용 형식을 지정하는 클래스 구글에서 검색해서 API 확인
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		// 현재시간을 현재 거주하는 지역의 시간대를 기준으로 하여 출력
		ZonedDateTime current = ZonedDateTime.now();
		// current.format(format) : 현재 날짜 및 시간을 출력 시 위에서 지정한 형식대로 출력한다는 명령
		String path = "images/" + current.format(format);
		// FIle 클래스의 객체 file을 생성(위에서 지정한 경로를 바탕으로 생성)
		// 서버 os로 주로 사용하는 linux나 unix는 폴더 및 파일, 각종 디스크 드라이버를 모두 파일로 인식함
		File file = new File(path);
		if (file.exists() == false) {
			file.mkdirs();
		}
		// 매개변수로 받아온 파일 정보에서 모든 파일 이름을 가져옴
		Iterator<String> iterator = multiFiles.getFileNames();
		String newFileName;
		String originalFileExtension;
		String contentType;

		while (iterator.hasNext()) {
			String name = iterator.next();
			// 지정한 파일명을 가지고 있는 파일의 모든 정보를 가져와서 list에 넣음
			List<MultipartFile> list = multiFiles.getFiles(name);

			for (MultipartFile mFile : list) {
				if (mFile.isEmpty() == false) {
					contentType = mFile.getContentType();
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if (contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if (contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if (contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
//					현재 시간을 기준으로 이름을 설정함 
//					1970년 1월 1일 00시 00분 00초를 기준으로해서 현재시간짜기 진행된 시간을 1/1000 으로 표현
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;

					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					/*
					 * MultipartFile 클래의 getSize() 메서드의 반환 타입이 long이기때문에 SQL문에서 1megaByte가 넘어가는 경우
					 * 1,xxx로 표시되어 문자열로 인식이 되기때문에 그대로 사용하지 못하고 문자열로 변환하여 사용
					 */
					boardFile.setFileSize(Long.toString(mFile.getSize()));
					boardFile.setOriginalFileName(mFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);

					fileList.add(boardFile);

					file = new File(path + "/" + newFileName);
					// 현재 파일을 지정한 위치로 이동
					mFile.transferTo(file);
				}
			}
		}
		// 모든 파일 정보를 가지고 있는 리스트를 반환

		return fileList;
	}
}
