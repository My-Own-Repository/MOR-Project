package com.co.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.co.dto.FileDTO;
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
	
	public void fileUpload(FileDTO fs) throws Exception;		// 파일 업로드
	public List<FileDTO> fileDownload(int b_num) throws Exception;		// 파일 다운로드(+파일 여러개 가져오기)
	public FileDTO fileView(int file_num) throws Exception; 	// 파일 보여주기(+파일 한개 가져오기)
	public List<FileDTO> fileViewer(int b_num) throws Exception;	// 파일 보여주기2
	
	public List<FileDTO> viewFile(int b_num) throws Exception;	// 이미지, 영상 파일만 추출해서 가져온 후 보여주기
	
	public int maxNum() throws Exception;		// 마지막으로 게시 된 게시글의 고유번호 검색 (파일 업로드시 파일을 업로드한 게시글을 특정하기 위함.)

	public int totalNum() throws Exception;		// 게시글 총 개수 가져오기
	public List<boardDTO> limitBoard(int first) throws Exception;		// first부터 20개의 게시글 목록 가져오기
	
	// 첫 글과 마지막 글의 고유번호 조회(이전,다음 글 조회할때 사용)
	public int selectMinNum() throws Exception;	
	public int selectMaxNum() throws Exception;
	
}
