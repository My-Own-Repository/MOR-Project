package com.co.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.co.dao.boardDAO;
import com.co.dto.boardDTO;
import com.co.dto.commentDTO;

@Service
public class BoardServiceImpl implements BoardService{
	
    @Inject
    private boardDAO dao;
    
    
	@Override
	public void writeBoard(boardDTO letter) throws Exception {
		// TODO Auto-generated method stub
		dao.writeBoard(letter);
	}
    
    @Override
    public List<boardDTO> printBoard() throws Exception {
        return dao.printBoard();
    }

	@Override
	public List<boardDTO> selectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectBoard(num);
	}

	@Override
	public void addView(int num) throws Exception {
		// TODO Auto-generated method stub
		dao.addView(num);
	}

	@Override
	public int selectMinNum() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectMinNum();
	}

	@Override
	public int selectMaxNum() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectMaxNum();
	}

	@Override
	public void updateBoard(boardDTO letter) throws Exception {
		// TODO Auto-generated method stub
		dao.updateBoard(letter);
	}

	@Override
	public void deleteBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteBoard(num);
	}

	@Override
	public void writeComment(commentDTO letter) throws Exception {
		// TODO Auto-generated method stub
		dao.writeComment(letter);
	}

	@Override
	public List<commentDTO> printComment(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return dao.printComment(b_num);
	}

	@Override
	public void upComment(int num) throws Exception {
		// TODO Auto-generated method stub
		dao.upComment(num);
	}

	@Override
	public void downComment(int num) throws Exception {
		// TODO Auto-generated method stub
		dao.downComment(num);
	}



   
}
