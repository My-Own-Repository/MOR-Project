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
	public LoginDTO loginMember(LoginDTO member) throws Exception{
		//MemberVO member = new MemberVO();
		//LoginDTO member = new LoginDTO();
		
		member.setid("admin");
		member.setpw("admin");
		
		
		return membermapper.loginMember(member);
		
	}
	
	
	private void joinMember(MemberVO member) {
		// TODO Auto-generated method stub
		membermapper.joinMember(member);
	}
	
/*
	public MemberVO loginMember(MemberVO member) {
		return membermapper.loginMember(member);
	}
	*/
}