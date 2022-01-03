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

//@Component : �����������ӿ�ũ�� �ڵ����� ����Ͽ� ��ü�� ������ �ڹ�Ŭ����(����� �������� ���� ����)
@Component
public class FileUtils {
	
	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multiFiles) throws Exception{
		/*�Ű������� ���� ���� ������ ������� null�� ���� */
		if(ObjectUtils.isEmpty(multiFiles)) {
			return null;
		}
		/*�Ű������� ���� ���� �������� BoardFileDto Ŭ�������� ����ϴ� ������ �߷���� ����Ʈ�� �����Ͽ� ������ ����*/
		List<BoardFileDto> fileList = new ArrayList<>();
		//��¥ ��� ������ �����ϴ� Ŭ���� ���ۿ��� �˻��ؼ� API Ȯ�� 
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		//����ð��� ���� �����ϴ� ������ �ð��븦 �������� �Ͽ� ���
		ZonedDateTime current = ZonedDateTime.now();
		//current.format(format) : ���� ��¥ �� �ð��� ��� �� ������ ������ ���Ĵ�� ����Ѵٴ� ���
		String path = "images/"+current.format(format);
		//FIle Ŭ������ ��ü file�� ����(������ ������ ��θ� �������� ����)
		//���� os�� �ַ� ����ϴ� linux�� unix�� ���� �� ����, ���� ��ũ ����̹��� ��� ���Ϸ� �ν���
		File file = new File(path);
		if(file.exists()==false) {
			file.mkdirs();
		}
		//�Ű������� �޾ƿ� ���� �������� ��� ���� �̸��� ������
		Iterator<String> iterator = multiFiles.getFileNames();
		String newFileName;
		String originalFileExtension;
		String contentType;
		
		while(iterator.hasNext()) {
			String name = iterator.next();
			//������ ���ϸ��� ������ �ִ� ������ ��� ������ �����ͼ� list�� ����
			List<MultipartFile> list = multiFiles.getFiles(name);
			
			for(MultipartFile mFile : list) {
				if(mFile.isEmpty() == false) {
					contentType = mFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}else {
							break;
						}
					}
//					���� �ð��� �������� �̸��� ������ 
//					1970�� 1�� 1�� 00�� 00�� 00�ʸ� ���������ؼ� ����ð�¥�� ����� �ð��� 1/1000 ���� ǥ��
					newFileName =Long.toString(System.nanoTime())+originalFileExtension;
					
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
/*
MultipartFile Ŭ���� getSize() �޼����� ��ȯ Ÿ���� long�̱⶧���� SQL������ 1megaByte�� �Ѿ�� ��� 1,xxx�� ǥ�õǾ� ���ڿ��� �ν��� �Ǳ⶧���� �״�� ������� ���ϰ� ���ڿ��� ��ȯ�Ͽ� ���
*/
					boardFile.setFileSize(Long.toString(mFile.getSize()));
					boardFile.setOriginalFileName(mFile.getOriginalFilename());
					boardFile.setStoredFilePath(path+"/"+newFileName);
					
					fileList.add(boardFile);
					
					file=new File(path+"/"+newFileName);
					//���� ������ ������ ��ġ�� �̵�
					mFile.transferTo(file);
				}
			}
		}
		//��� ���� ������ ������ �ִ� ����Ʈ�� ��ȯ
		
		return fileList;
	}
}
