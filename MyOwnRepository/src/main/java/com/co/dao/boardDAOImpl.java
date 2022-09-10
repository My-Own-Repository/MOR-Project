package com.co.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.co.dto.boardDTO;

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
	
}
