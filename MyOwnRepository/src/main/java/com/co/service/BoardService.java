package com.co.service;

import java.util.ArrayList;
import java.util.List;

import com.co.dto.boardDTO;

public interface BoardService {
	public void writeBoard(boardDTO letter) throws Exception;
	public List<boardDTO> printBoard() throws Exception;
	
	public List<boardDTO> selectBoard(int num) throws Exception;
	public void addView(int num) throws Exception;
	public void updateBoard(boardDTO letter) throws Exception;
	public void deleteBoard(int num) throws Exception;
	
	public int selectMinNum() throws Exception;	
	public int selectMaxNum() throws Exception;
	

}
