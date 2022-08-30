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
    public List<MemberVO> selectMember() throws Exception {
 
        return sqlSession.selectList(Namespace+".selectMember");
    }

	@Override
	public void joinMember(MemberVO member) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(Namespace+".joinMember", member);
		
	}

	@Override
	public int idCheck(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".idCheck", id);
	}

	@Override
	public LoginDTO selectPerson(String sessionID) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectPerson");
	}
    

 

/*
	@Override
	public MemberVO loginMember(MemberVO member) {
		// TODO Auto-generated method stub
		MemberVO res = null;
		
		try {
			res = sqlSession.selectOne(Namespace+".loginMember", member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
*/





 
}