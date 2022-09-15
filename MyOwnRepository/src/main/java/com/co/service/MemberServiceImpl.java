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
    public List<MemberVO> selectMember() throws Exception {				// 회원 조회
 
        return dao.selectMember();
    }
    

	@Override
	public void joinMember(MemberVO member) throws Exception {			// 회원가입
		// TODO Auto-generated method stub
		dao.joinMember(member);
	}

	
	@Override
	public LoginDTO loginMember(LoginDTO vo) throws Exception{			// 로그인
		return ldao.loginMember(vo);
	}



	@Override
	public int idCheck(String id) throws Exception {		// 아이디 중복체크
		// TODO Auto-generated method stub
		return dao.idCheck(id);
	}


	@Override
	public LoginDTO selectPerson(String sessionID) throws Exception {		// 세션에 저장 된 아이디가 DB에 등록되어 있는지 조회
		// TODO Auto-generated method stub
		return dao.selectPerson(sessionID);
	}


	@Override
	public int nicknameCheck(String nickname) throws Exception {		// 닉네임 중복 체크
		// TODO Auto-generated method stub
		return dao.nicknameCheck(nickname);
	}

}