package com.co.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.co.dao.MemberDAO;
import com.co.dao.loginDAO;
import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;
import com.co.mapper.MemberMapper;


@Service
public class MemberServiceImpl implements MemberService {
 
	
	MemberMapper membermapper;
	
    @Inject
    private MemberDAO dao;
    
    @Inject
    private loginDAO ldao;
    
    @Override
    public List<MemberVO> selectMember() throws Exception {
 
        return dao.selectMember();
    }

    

	@Override
	public void joinMember(MemberVO member) throws Exception {
		// TODO Auto-generated method stub
		dao.joinMember(member);
	}

	/*
	@Override
	public MemberVO loginMember(MemberVO member) {
		// TODO Auto-generated method stub
		return ((loginDAO) dao).loginMember(member);
	}
*/
	@Override
	public LoginDTO loginMember(LoginDTO vo) throws Exception{
		return ldao.loginMember(vo);
	}
	  



	

}