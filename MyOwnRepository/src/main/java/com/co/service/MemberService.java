package com.co.service;

import java.util.List;

import com.co.dto.FileDTO;
import com.co.dto.FindIDPWDTO;
import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;


public interface MemberService {
	
    public void joinMember(MemberVO member) throws Exception;		// 회원가입
	
    public LoginDTO loginMember(LoginDTO vo) throws Exception;		// 로그인
    
    public int idCheck(String id) throws Exception;			// 아이디 중복 체크
    public int nicknameCheck(String nickname) throws Exception;		// 닉네임 중복 체크
    
    public List<MemberVO> selectMember() throws Exception;		// 회원 조회

	public LoginDTO selectPerson(String sessionID) throws Exception;	 // 세션에 저장 된 아이디가 DB에 등록되어 있는지 조회

	// 아이디/비밀번호 찾기
	public List<FindIDPWDTO> findIDPW(FindIDPWDTO vo) throws Exception;		// 아이디 찾기
	//public MemberVO findPW(FindIDPWDTO vo) throws Exception;		// 비밀번호 찾기
	
	
	// 회원정보 수정
	public void editInfoPw(MemberVO vo) throws Exception;	// 비밀번호 변경
	public void editInfoEmail(MemberVO vo) throws Exception;	// 이메일 변경
	public void editInfoPhone(MemberVO vo) throws Exception;	// 전화번호 변경

	
	// 마이페이지 - 내 파일 리스트
	public List<FileDTO> myFile(String user_id) throws Exception;
	
	// 마이페이지 - 회원탈퇴
	public void unRegister(String id) throws Exception;
	
	// 카카오 로그인
	public LoginDTO kakaoLogin(String snsid) throws Exception;
	
	// 카카오 회원가입
	public void kakaoJoin(MemberVO vo) throws Exception;
}
