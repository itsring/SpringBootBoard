package com.bitc.board.dto;

import lombok.Data;

/*�Һ����
 �Һ� : �����ͺ��̽��� ���ʵǴ� dto��ü�� ������ getter/setter�� �ڵ����� �������ִ� ���α׷�
 getter/setter/toString �޼��带 �ڵ������λ����� ��
 */
@Data
public class BoardDto {
	private int idx;
	private String title;
	private String contents;
	private String creater_id;
	private String created_date;
	private String update_id;
	private String updated_date;
	private int hit_cnt;
	private String passwd;

	
}
