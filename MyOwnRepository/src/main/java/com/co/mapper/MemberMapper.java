package com.co.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MemberMapper {
  
	@Autowired
	MemberMapper membermapper;			

	
	@Test
	public LoginDTO loginMember(LoginDTO member) throws Exception{			// 로그인
		
		return membermapper.loginMember(member);
		
	}
	
	public void joinMember(MemberVO member) throws Exception {			// 회원가입
		// TODO Auto-generated method stub

		membermapper.joinMember(member);
		
	}
	

	public int nicknameCheck(String nickname) throws Exception{		// 닉네임 체크
		return membermapper.nicknameCheck(nickname);
	}
}