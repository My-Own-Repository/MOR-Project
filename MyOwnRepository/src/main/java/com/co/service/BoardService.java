package com.co.service;

import java.util.List;

import com.co.dto.boardDTO;

public interface BoardService {
	public void writeBoard(boardDTO letter) throws Exception;
	public List<boardDTO> printBoard() throws Exception;
	
	public List<boardDTO> selectBoard(int num) throws Exception;
	public void addView(int num) throws Exception;
}
