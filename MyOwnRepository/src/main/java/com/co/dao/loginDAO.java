package com.co.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.co.dto.LoginDTO;


@Repository
public interface loginDAO {
	
	public LoginDTO loginMember(LoginDTO vo) throws Exception;
	 
}
