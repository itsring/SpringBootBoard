package com.bitc.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.CalculatorDto;
import com.bitc.board.dto.PersonDto;

//������� ��û�� ó���ϴ� ��
@Controller
public class TestController {
	
	/* @Controller ������̼��� ����ϰ� @RequestMapping�� ����� �޼����� 
	 * ��ȯ Ÿ���� String���� �������� ��� ���ø��� ��ġ�� ������ ������ ������*/
	@RequestMapping("/test/test1")
	public String test1() throws Exception{
		return "test/test1";
	}
	
	// ����� Ŭ���̾�Ʈ(��������)���� ������ �����ϴ� �ּҸ� ����
	// �ٷ� �Ʒ��� �ִ� �޼���� URL�� ��������
	@RequestMapping("test/calculator.do")
	public String inputPage() throws Exception{
		// �ܼ� ȭ�� ǥ�ø� �� ��� (html  ��¸� �ʿ��� ���) ��ȯ���� String�� ǥ��
		// ��ȯ Ÿ���� String���� �����Ǹ� �ش� ���� Ŭ���̾�Ʈ ȭ�鿡 ��µ� ���ø��� ��ġ�� �� 
		return "test/calculator";
	}
		
	//����ڰ� form �±׸� ���Ͽ� ������ �����͸� ������ ��� ��������Ʈ���� �����͸� �޴¹��
	// 1. DTO Ŭ������ �����Ͽ� �����͸� �޴� ���
	// 1.1 form �±װ� ������ �ִ� input �±��� name �Ӽ����� DTO Ŭ������ ������ �ִ� ��� ������� �����ϰ� ���
	// 2. @RequestParam ������̼��� ����Ͽ� �����͸� �޴� ���
	// 2.1 Ŭ���̾�Ʈ���� �����ϴ� �����͸� �ϳ��ϳ� ���� �����Ͽ� �޾ƿ��� ��� 
	// 2.2 Ŭ���̾�Ʈ���� �����ϴ� �������� ���� ���� ���
	@RequestMapping("/test/calculatorProcess.do")
	public ModelAndView inputPageProcess(CalculatorDto dto) {
		
		ModelAndView mv = new ModelAndView("/test/calculatorProcess");
	
		int result = dto.getNum1() + dto.getNum2();
		dto.setResult(result);
		
		mv.addObject("data",dto);
		
		return mv;
	}
	
	@RequestMapping("/test/calculatorProcess2.do")
	public ModelAndView inputPageProcess(
			@RequestParam("num1") int num1, 
			@RequestParam("num2") int num2
			) 	
	{
		ModelAndView mv = new ModelAndView("/test/calculatorProcess2");
		
		int result  =num1-num2;
		mv.addObject("num1", num1);
		mv.addObject("num2", num2);
		mv.addObject("result", result);
		
		return mv;
	}
	
	//���� : ��Ʈ��Ʈ���� ������ ��Ʈ�� ����Ͽ� ������ ȸ������ ������ ����
	// personDto Ŭ���� ���� : ����, �̸�, ����, id, pw, email
	// ���ø� ���� : signin.html(ȸ������) signinResult.html(ȸ������ ��� )
	@RequestMapping("join/signin.do")
	public String test2() throws Exception{
		return "join/signin";
	}
	
	@RequestMapping("/join/signinResult.do")
	public ModelAndView testResult(PersonDto dto) throws Exception{
		ModelAndView mv = new ModelAndView("/join/signinResult");
		dto.setAge(dto.getAge());
		dto.setEmail(dto.getEmail());
		dto.setId(dto.getId());
		dto.setIdx(dto.getIdx());
		dto.setName(dto.getName());
		dto.setPasswd(dto.getPasswd());
		mv.addObject("data",dto);
		
		return mv;
	}
}