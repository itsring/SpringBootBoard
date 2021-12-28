package com.bitc.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.CalculatorDto;
import com.bitc.board.dto.PersonDto;

//사용자의 요청을 처리하는 곳
@Controller
public class TestController {
	
	/* @Controller 어노테이션을 사용하고 @RequestMapping이 적용된 메서드의 
	 * 반환 타입을 String으로 지정했을 경우 템플릿의 위치를 지정한 것으로 간주함*/
	@RequestMapping("/test/test1")
	public String test1() throws Exception{
		return "test/test1";
	}
	
	// 사용자 클라이언트(웹브라우저)에서 서버로 접속하는 주소를 선언
	// 바로 아래에 있는 메서드와 URL을 연결해줌
	@RequestMapping("test/calculator.do")
	public String inputPage() throws Exception{
		// 단순 화면 표시만 할 경우 (html  출력만 필요할 경우) 반환값을 String을 표기
		// 반환 타입이 String으로 설정되면 해당 값이 클라이언트 화면에 출력될 템플릿의 위치가 됨 
		return "test/calculator";
	}
		
	//사용자가 form 태그를 통하여 서버로 데이터를 전송할 경우 스프링부트에서 데이터를 받는방법
	// 1. DTO 클래스를 생성하여 데이터를 받는 방식
	// 1.1 form 태그가 가지고 있는 input 태긍의 name 속성값을 DTO 클래스가 가지고 있는 멤버 변수명과 동일하게 사용
	// 2. @RequestParam 어노테이션을 사용하여 데이터를 받는 방식
	// 2.1 클라이언트에서 전송하는 데이터를 하나하나 직접 지정하여 받아오는 방식 
	// 2.2 클라이언트에서 전송하는 데이터의 수가 적을 사용
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
	
	//문제 : 부트스트랩과 스프링 부트를 사용하여 간단한 회원가입 페이지 생성
	// personDto 클래스 생성 : 순번, 이름, 나이, id, pw, email
	// 템플릿 파일 : signin.html(회원가입) signinResult.html(회원가입 결과 )
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