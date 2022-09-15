package com.co.service;

import java.util.List;

import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;


public interface MemberService {
	
    public void joinMember(MemberVO member) throws Exception;		// 회원가입
	
    public LoginDTO loginMember(LoginDTO vo) throws Exception;		// 로그인
    
    public int idCheck(String id) throws Exception;			// 아이디 중복 체크
    public int nicknameCheck(String nickname) throws Exception;		// 닉네임 중복 체크
    
    public List<MemberVO> selectMember() throws Exception;		// 회원 조회

	public LoginDTO selectPerson(String sessionID) throws Exception;	 // 세션에 저장 된 아이디가 DB에 등록되어 있는지 조회

	 
}
