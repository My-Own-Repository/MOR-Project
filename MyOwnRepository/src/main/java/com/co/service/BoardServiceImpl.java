package com.co.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.co.dao.boardDAO;
import com.co.dto.boardDTO;

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

   
}
