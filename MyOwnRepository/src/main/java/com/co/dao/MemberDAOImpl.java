package com.co.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.co.dto.LoginDTO;
import com.co.dto.MemberVO;


@Repository
public class MemberDAOImpl implements MemberDAO {
 
    @Inject
    private SqlSession sqlSession;
    
  
    
    private static final String Namespace = "com.co.mapper.memberMapper";
    
    @Override
    public List<MemberVO> selectMember() throws Exception {				// 회원 조회
 
        return sqlSession.selectList(Namespace+".selectMember");
    }

	@Override
	public void joinMember(MemberVO member) throws Exception {			// 회원가입
		// TODO Auto-generated method stub
		sqlSession.insert(Namespace+".joinMember", member);
		
	}

	@Override
	public int idCheck(String id) throws Exception {				// 아이디 중복 체크
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".idCheck", id);
	}

	@Override
	public LoginDTO selectPerson(String sessionID) throws Exception {		// 로그인
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectPerson");
	}

	@Override
	public int nicknameCheck(String nickname) throws Exception {		// 닉네임 중복 체크
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".nicknameCheck", nickname);
	}

}