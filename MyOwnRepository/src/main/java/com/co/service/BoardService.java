package com.co.service;

import java.util.ArrayList;
import java.util.List;

import com.co.dto.FileDTO;
import com.co.dto.MemberVO;
import com.co.dto.boardDTO;
import com.co.dto.commentDTO;
import com.co.dto.searchVO;

public interface BoardService {
	public void writeBoard(boardDTO letter) throws Exception;		// 게시글 작성
	public List<boardDTO> printBoard() throws Exception;			// 게시글 목록
	public List<boardDTO> printAdminBoard() throws Exception; 	// 관리자 게시글 목록
	public List<boardDTO> limitAdminBoard() throws Exception;	// 관리자 게시글 5개 목록
	
	public boardDTO selectBoard(int num) throws Exception;		// 특정 게시글
	
	public void addView(int num) throws Exception;		// 조회수 증가
	public void updateBoard(boardDTO letter) throws Exception;		// 게시글 수정
	public void deleteBoard(int num) throws Exception;		// 게시글 삭제
	
	
	public void writeComment(commentDTO letter) throws Exception;		// 댓글 작성
	public List<commentDTO> printComment(int b_num) throws Exception;		// 댓글 목록
	
	public void upComment(int num) throws Exception;		// 댓글 수 증가
	public void downComment(int num) throws Exception;		// 댓글 수 감소
	
	public void updateComment(commentDTO cmt) throws Exception;		// 댓글 수정
	public void deleteComment(int c_num) throws Exception;		// 댓글 삭제
	public void allDeleteComment(int b_num) throws Exception; 	// 특정 게시글 삭제시 모든 댓글 삭제
	
	
	public void fileUpload(FileDTO fs) throws Exception;		// 파일 업로드
	public List<FileDTO> fileDownload(int b_num) throws Exception;		// 파일 다운로드
	public FileDTO fileView(int file_num) throws Exception;		// 파일 보여주기
	public List<FileDTO> fileViewer(int b_num) throws Exception;	// 파일 보여주기2
	
	public List<FileDTO> viewFile(int b_num) throws Exception;	// 이미지, 영상 파일만 추출해서 가져온 후 보여주기
	
	public void allDeleteFile(int b_num) throws Exception;		// 특정 게시글 삭제시 모든 파일 삭제
	public void deleteFile(int file_num) throws Exception; 		// 파일 개별삭제
	
	public int maxNum() throws Exception;	// 마지막 게시글의 고유번호 검색
	
	public int totalNum() throws Exception;		// 유효한 게시글 총 개수 가져오기
	public List<boardDTO> limitBoard(int first) throws Exception;	// first부터 20개의 게시글 목록 가져오기
	
	// 첫 글과 마지막 글의 고유번호 조회(이전,다음 글 조회할때 사용)
	public int selectMinNum() throws Exception;		 
	public int selectMaxNum() throws Exception;
	

	/* 비밀 게시판 전용 */
	
	public int SCmaxNum() throws Exception;
	public int SCselectMinNum() throws Exception;
	public int SCselectMaxNum() throws Exception;
	public int SCtotalNum() throws Exception;
	
	public boardDTO SCselectBoard(int num) throws Exception;
	public List<boardDTO> SClimitBoard(int first) throws Exception;
	
	
	
	/* 저장소 전용 */
	// 인자 int sc는 비밀 저장소인지 아닌지 판별할때 사용
	public int REPOmaxNum(int sc) throws Exception;
	public int REPOselectMinNum(int sc) throws Exception;
	public int REPOselectMaxNum(int sc) throws Exception;
	public int REPOtotalNum(int sc) throws Exception;
	
	public boardDTO REPOselectBoard(int num) throws Exception;			// 공유 저장소에서 1개
	public boardDTO REPOSCselectBoard(int num) throws Exception;		// 비밀 저장소에서 1개
	public List<boardDTO> REPOlimitBoard(int first) throws Exception;		// 비밀 저장소 페이징
	public List<boardDTO> REPOSClimitBoard(int first) throws Exception;		// 비밀 저장소 페이징
	
	
	/* 페이지 검색 */
	public List<boardDTO> pageTitleSearch(searchVO vo) throws Exception;		// 페이지내 제목 검색 
	public List<boardDTO> pageContentSearch(searchVO vo) throws Exception;		// 페이지내 내용 검색 
	public List<boardDTO> pageWriterSearch(searchVO vo) throws Exception;		// 페이지내 작성자 검색 
	public List<boardDTO> pageTitleContentSearch(searchVO vo) throws Exception;		// 페이지내 제목+내용 검색 
	
	
	/* 전체 검색 */	
	public List<boardDTO> TotalSearch(searchVO vo) throws Exception;		// 전체 검색
	public List<boardDTO> foldTotalSearch(searchVO vo) throws Exception;	// 전체 검색 5개 접기
	
	
	/* 내가 쓴 글/댓글 리스트 */
	public List<boardDTO> myPost(String id) throws Exception;	// 내가 쓴 글
	public List<commentDTO> myComment(String nickname) throws Exception;	// 내가 쓴 댓글
	
	
	// 전체 게시글 마지막 번호 - 파일의 게시글번호 설정용
	public int totalMaxNum() throws Exception;
}
