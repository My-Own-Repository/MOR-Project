package com.co.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.co.dto.boardDTO;

@Repository
public interface boardDAO {
	public void writeBoard(boardDTO letter) throws Exception;		// 게시글 작성
	public List<boardDTO> printBoard() throws Exception;		// 게시글 목록
	public List<boardDTO> selectBoard(int num) throws Exception;		// 특정 게시글
	public void addView(int num) throws Exception;		// 조회수 증가
}
