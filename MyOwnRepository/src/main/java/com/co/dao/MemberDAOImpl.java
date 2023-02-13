package com.co.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.co.dto.FileDTO;
import com.co.dto.FindIDPWDTO;
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

	@Override
	public List<FindIDPWDTO> findIDPW(FindIDPWDTO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".findIDPW", vo);
	}
/*
	@Override
	public MemberVO findPW(FindIDPWDTO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".findPW", vo);
	}
*/

	
	/* 회원정보 수정 */
	
	// 비밀번호 변경
	@Override
	public void editInfoPw(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".editInfoPw", vo);
	}
	// 이메일 변경
	@Override
	public void editInfoEmail(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".editInfoEmail", vo);
	}
	// 전화번호 변경
	@Override
	public void editInfoPhone(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".editInfoPhone", vo);
	}

	
	// 마이페이지 - 내 파일 리스트
	@Override
	public List<FileDTO> myFile(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".myFile", user_id);
	}

	// 마이페이지 - 회원탈퇴
	@Override
	public void unRegister(String id) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".unRegister", id);
	}
}