package com.co.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.co.dto.FileDTO;
import com.co.dto.FindIDPWDTO;
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
	
	// 아이디/비밀번호 찾기
	public List<FindIDPWDTO> findIDPW(FindIDPWDTO vo) throws Exception{
		return membermapper.findIDPW(vo);
	}
	/*
	public MemberVO findPW(FindIDPWDTO vo) throws Exception{
		return membermapper.findPW(vo);
	}
	*/
	
	
	/* 회원정보 수정 */
	// 비밀번호 변경
	public void editInfoPw(MemberVO vo) throws Exception{
		membermapper.editInfoPw(vo);
	}
	// 이메일 변경
	public void editInfoEmail(MemberVO vo) throws Exception{
		membermapper.editInfoEmail(vo);
	}
	// 전화번호 변경
	public void editInfoPhone(MemberVO vo) throws Exception{
		membermapper.editInfoPhone(vo);
	}
	
	
	// 마이페이지 - 내 파일 리스트
	public List<FileDTO> myFile(String user_id) throws Exception{
		return membermapper.myFile(user_id);
	}
	
	// 마이페이지 - 회원탈퇴
	public void unRegister(String id) throws Exception {
		membermapper.unRegister(id);
	}
}