package com.co.service;

import java.lang.reflect.Member;
import java.util.List;

import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;


public interface MemberService {
	
    public void joinMember(MemberVO member) throws Exception;
    
    
    //public MemberVO loginMember(MemberVO member) throws Exception;
   	
    public LoginDTO loginMember(LoginDTO vo) throws Exception;
    
    public int idCheck(String id) throws Exception;
    public List<MemberVO> selectMember() throws Exception;

    // 해당 회원이 있는지 조회
	public LoginDTO selectPerson(String sessionID) throws Exception;

	 
}
