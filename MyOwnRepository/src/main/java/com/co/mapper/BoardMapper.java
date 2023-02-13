package com.co.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.co.dto.FileDTO;
import com.co.dto.boardDTO;
import com.co.dto.commentDTO;
import com.co.dto.searchVO;


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
	
	public List<boardDTO> printAdminBoard() throws Exception {
		return boardmapper.printAdminBoard();
	}
	
	public List<boardDTO> limitAdminBoard() throws Exception {
		return boardmapper.limitAdminBoard();
	}
	
	public boardDTO selectBoard(int num) throws Exception {		// 게시글 하나만 가져오는걸로 수정
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

	public void writeComment(commentDTO letter) throws Exception{
		boardmapper.writeComment(letter);
	}
	
	public List<commentDTO> printComment(int b_num) throws Exception{
		return boardmapper.printComment(b_num);
	}
	
	public void upComment(int num) throws Exception{
		boardmapper.upComment(num);
	}
	
	public void downComment(int num) throws Exception{
		boardmapper.downComment(num);
	}
	
	public void fileUpload(FileDTO fs) throws Exception{
		boardmapper.fileUpload(fs);
	}
	
	public List<FileDTO> fileDownload(int b_num) throws Exception{
		return boardmapper.fileDownload(b_num);
	}
	
	public FileDTO fileView(int file_num) throws Exception{
		return boardmapper.fileView(file_num);
	}
	
	public List<FileDTO> fileViewer(int b_num) throws Exception{
		return boardmapper.fileViewer(b_num);
	}
	
	public List<FileDTO> viewFile(int b_num) throws Exception{
		return boardmapper.viewFile(b_num);
	}
	
	public int maxNum() throws Exception{
		return boardmapper.maxNum();
	}
	
	public int totalNum() throws Exception{
		return boardmapper.totalNum();
	}
	
	public List<boardDTO> limitBoard(int first) throws Exception{
		return boardmapper.limitBoard(first);
	}
	
	public void updateComment(commentDTO cmt) throws Exception{
		boardmapper.updateComment(cmt);
	}
	
	public void deleteComment(int c_num) throws Exception{
		boardmapper.deleteComment(c_num);
	}
	
	public void allDeleteComment(int b_num) throws Exception{
		boardmapper.allDeleteComment(b_num);
	}
	
	public void allDeleteFile(int b_num) throws Exception{
		boardmapper.allDeleteFile(b_num);
	}
	
	public void deleteFile(int file_num) throws Exception{
		boardmapper.deleteFile(file_num);
	}
	
	
	/* 비밀 게시판 */
	
	public int SCmaxNum() throws Exception{		
		return boardmapper.SCmaxNum();
	}
	
	public int SCselectMinNum() throws Exception{		
		return boardmapper.SCselectMinNum();
	}
	
	public int SCselectMaxNum() throws Exception{		
		return boardmapper.SCselectMaxNum();
	}
	
	public int SCtotalNum() throws Exception{		
		return boardmapper.SCtotalNum();
	}
	
	public boardDTO SCselectBoard(int num) throws Exception{		
		return boardmapper.SCselectBoard(num);
	}
	
	public List<boardDTO> SClimitBoard(int first) throws Exception{		
		return boardmapper.SClimitBoard(first);
	}
	
	
	/* 저장소 */
	
	public int REPOmaxNum(int sc) throws Exception {
		return boardmapper.REPOmaxNum(sc);
	}
	public int REPOselectMinNum(int sc) throws Exception {
		return boardmapper.REPOselectMinNum(sc);
	}
	public int REPOselectMaxNum(int sc) throws Exception {
		return boardmapper.REPOselectMaxNum(sc);
	}
	public int REPOtotalNum(int sc) throws Exception {
		return boardmapper.REPOtotalNum(sc);
	}
	
	public boardDTO REPOselectBoard(int num) throws Exception {
		return boardmapper.REPOselectBoard(num);
	}
	public boardDTO REPOSCselectBoard(int num) throws Exception {
		return boardmapper.REPOSCselectBoard(num);
	}
	public List<boardDTO> REPOlimitBoard(int first) throws Exception {
		return boardmapper.REPOlimitBoard(first);
	}
	public List<boardDTO> REPOSClimitBoard(int first) throws Exception {
		return boardmapper.REPOSClimitBoard(first);
	}
	
	
	/* 페이지 검색 */
	public List<boardDTO> pageTitleSearch(searchVO vo) throws Exception {
		return boardmapper.pageTitleSearch(vo);
	}
	public List<boardDTO> pageContentSearch(searchVO vo) throws Exception {
		return boardmapper.pageTitleSearch(vo);
	}
	public List<boardDTO> pageWriterSearch(searchVO vo) throws Exception {
		return boardmapper.pageTitleSearch(vo);
	}
	public List<boardDTO> pageTitleContentSearch(searchVO vo) throws Exception {
		return boardmapper.pageTitleSearch(vo);
	}

	
	/* 전체 검색 */
	public List<boardDTO> TotalSearch(searchVO vo) throws Exception {
		return boardmapper.TotalSearch(vo);
	}
	// 5개 접기
	public List<boardDTO> foldTotalSearch(searchVO vo) throws Exception {
		return boardmapper.foldTotalSearch(vo);
	}
	
	
	/* 내가 쓴 글/댓글 리스트 */
	public List<boardDTO> myPost(String id) throws Exception {
		return boardmapper.myPost(id);
	}
	public List<commentDTO> myComment(String nickname) throws Exception {
		return boardmapper.myComment(nickname);
	}
	
	
	
}

