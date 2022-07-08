package com.co.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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
	public void joinMember(MemberVO member) {
		// TODO Auto-generated method stub
		sqlSession.insert(Namespace+".joinMember");
		
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