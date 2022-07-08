package com.co.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.co.dto.MemberVO;


@Repository
public interface MemberDAO {
	String NAMESPACE = "mymember.";
	
	
    public List<MemberVO> selectMember() throws Exception;


	public void joinMember(MemberVO member);
	
	// public MemberVO loginMember(MemberVO member);	// �α���
	
     
}