package com.co.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.co.dto.boardDTO;
import com.co.dto.commentDTO;

@Repository
public class boardDAOImpl implements boardDAO{
	
	@Inject
    private SqlSession sqlSession;
      
    private static final String Namespace = "com.co.mapper.boardMapper";
    
   
    @Override
	public void writeBoard(boardDTO letter) throws Exception{
		sqlSession.insert(Namespace+".writeBoard", letter);	
	}
	
    @Override
	public List<boardDTO> printBoard() throws Exception{
		return sqlSession.selectList(Namespace+".printBoard");
	}

	@Override
	public List<boardDTO> selectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".selectBoard", num);
	}

	@Override
	public void addView(int num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".addView", num);
	}

	@Override
	public int selectMinNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectMinNum");
	}

	@Override
	public int selectMaxNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectMaxNum");
	}

	@Override
	public void updateBoard(boardDTO letter) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".updateBoard", letter);
		
	}

	@Override
	public void deleteBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(Namespace+".deleteBoard", num);		
	}

	@Override
	public void writeComment(commentDTO letter) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(Namespace+".writeComment", letter);
	}

	@Override
	public List<commentDTO> printComment(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".printComment", b_num);
		
	}

	@Override
	public void upComment(int num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".upComment", num);
	}

	@Override
	public void downComment(int num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".downComment", num);
	}


	
}
