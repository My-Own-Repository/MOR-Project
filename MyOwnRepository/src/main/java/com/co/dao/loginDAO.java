package com.co.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.co.dto.LoginDTO;


@Repository
public interface loginDAO {
	
	// 로그인
	public LoginDTO loginMember(LoginDTO vo) throws Exception;
	 
	// 카카오 로그인
	public LoginDTO kakaoLogin(String snsid) throws Exception;
}
