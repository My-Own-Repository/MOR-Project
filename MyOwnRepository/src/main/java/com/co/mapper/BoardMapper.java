package com.co.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.co.dto.boardDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapper {
  
	@Autowired
	BoardMapper boardmapper;			

	
	public void writeBoard(boardDTO letter) throws Exception {
		// TODO Auto-generated method stub
		boardmapper.writeBoard(letter);
	}
	
	public List<boardDTO> printBoard() throws Exception {
		return boardmapper.printBoard();
	}
	
	public List<boardDTO> selectBoard(int num) throws Exception {
		return boardmapper.selectBoard(num);
	}
	
	public void addView(int num) throws Exception {
		boardmapper.addView(num);
	}
	
	public int selectMinNum() throws Exception {
		return boardmapper.selectMinNum();
	}
	
	public int selectMaxNum() throws Exception {
		return boardmapper.selectMaxNum();
	}
	
	public void updateBoard(boardDTO letter) throws Exception{
		boardmapper.updateBoard(letter);
	}
	
	public void deleteBoard(int num) throws Exception {
		boardmapper.deleteBoard(num);
	}

}