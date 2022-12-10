package com.co.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.co.dto.boardDTO;
import com.co.dto.commentDTO;

@Repository
public interface boardDAO {
	public void writeBoard(boardDTO letter) throws Exception;		// 게시글 작성
	public List<boardDTO> printBoard() throws Exception;		// 게시글 목록
	public List<boardDTO> selectBoard(int num) throws Exception;		// 특정 게시글
	public void addView(int num) throws Exception;		// 조회수 증가
	public void updateBoard(boardDTO letter) throws Exception;		// 글 수정
	public void deleteBoard(int num) throws Exception;		// 글 삭제
	
	
	public void writeComment(commentDTO letter) throws Exception;	// 댓글 작성
	public List<commentDTO> printComment(int b_num) throws Exception;		// 댓글 목록
	
	public void upComment(int num) throws Exception;		// 댓글 수 증가
	public void downComment(int num) throws Exception;		// 댓글 수 감소
	
	// 첫 글과 마지막 글의 고유번호 조회(이전,다음 글 조회할때 사용)
	public int selectMinNum() throws Exception;	
	public int selectMaxNum() throws Exception;
	
}
