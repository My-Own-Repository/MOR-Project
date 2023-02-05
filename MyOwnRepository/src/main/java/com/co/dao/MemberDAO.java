package com.co.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.co.dto.FindIDPWDTO;
import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;


@Repository
public interface MemberDAO {
		
	
    public List<MemberVO> selectMember() throws Exception;		// 회원을 조회할 때 사용
    public int idCheck(String id) throws Exception;			// 아이디 중복검사
    public int nicknameCheck(String nickname) throws Exception;		// 닉네임 중복검사
    
	public void joinMember(MemberVO member) throws Exception;		// 회원가입
	public LoginDTO selectPerson(String sessionID) throws Exception;		// 로그인
			 
	// 아이디/비밀번호 찾기
	public List<FindIDPWDTO> findIDPW(FindIDPWDTO vo) throws Exception;		// 아이디 찾기
	//public MemberVO findPW(FindIDPWDTO vo) throws Exception;		// 비밀번호 찾기
	
}