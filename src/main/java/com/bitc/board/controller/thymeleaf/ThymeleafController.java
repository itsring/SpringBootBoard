package com.bitc.board.controller.thymeleaf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.thymeleaf.MemberDto;


@Controller
public class ThymeleafController {
	@RequestMapping("/thymeleaf")
	public String thymeleafIndex() throws Exception {
		return "/thymeleaf/index";
	}
	
	@RequestMapping("thymeleaf/value1")
	public ModelAndView thymeleafValue1() throws Exception{
		ModelAndView mv = new ModelAndView("/thymeleaf/value1");

		MemberDto member = new MemberDto();
		member.setUserId("tester01");
		member.setUserEmail("tester01@bitc.co.kr");
		member.setUserPw("bitc1234");
		member.setUserName("tester01");

		List<MemberDto> memberList = new ArrayList<MemberDto>();
		
		memberList.add(member);
		
		
		
		mv.addObject("member",member);
		mv.addObject("memberList",memberList);
		mv.addObject("image","/photo-3.jpg");
		return mv;
	}
}
