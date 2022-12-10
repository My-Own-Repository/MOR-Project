package com.co.service;

import java.util.ArrayList;
import java.util.List;

import com.co.dto.boardDTO;
import com.co.dto.commentDTO;

public interface BoardService {
	public void writeBoard(boardDTO letter) throws Exception;
	public List<boardDTO> printBoard() throws Exception;
	
	public List<boardDTO> selectBoard(int num) throws Exception;
	public void addView(int num) throws Exception;
	public void updateBoard(boardDTO letter) throws Exception;
	public void deleteBoard(int num) throws Exception;
	
	
	public void writeComment(commentDTO letter) throws Exception;
	public List<commentDTO> printComment(int b_num) throws Exception;
	
	public void upComment(int num) throws Exception;
	public void downComment(int num) throws Exception;
	
	public int selectMinNum() throws Exception;	
	public int selectMaxNum() throws Exception;
	

}
