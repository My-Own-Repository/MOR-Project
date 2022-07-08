package com.co.service;

import java.util.List;

import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;


public interface MemberService {
	
    public void joinMember(MemberVO member) throws Exception;
    
    
    //public MemberVO loginMember(MemberVO member) throws Exception;
   	
    public LoginDTO loginMember(LoginDTO vo) throws Exception;
    
    
    public List<MemberVO> selectMember() throws Exception;
     
          
	 
}
