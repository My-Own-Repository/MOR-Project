package com.co.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.co.dao.boardDAO;
import com.co.dto.FileDTO;
import com.co.dto.boardDTO;
import com.co.dto.commentDTO;
import com.co.dto.searchVO;

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
	public boardDTO selectBoard(int num) throws Exception {
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

	@Override
	public void fileUpload(FileDTO fs) throws Exception {
		// TODO Auto-generated method stub
		dao.fileUpload(fs);
	}

	@Override
	public int maxNum() throws Exception {
		// TODO Auto-generated method stub
		return dao.maxNum();
	}

	@Override
	public List<FileDTO> fileDownload(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return dao.fileDownload(b_num);
	}

	@Override
	public FileDTO fileView(int file_num) throws Exception {
		// TODO Auto-generated method stub
		return dao.fileView(file_num);
	}

	@Override
	public List<FileDTO> fileViewer(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return dao.fileViewer(b_num);
	}

	@Override
	public List<FileDTO> viewFile(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return dao.viewFile(b_num);
	}

	@Override
	public int totalNum() throws Exception {
		// TODO Auto-generated method stub
		return dao.totalNum();
	}

	@Override
	public List<boardDTO> limitBoard(int first) throws Exception {
		// TODO Auto-generated method stub
		return dao.limitBoard(first);
	}

	@Override
	public void updateComment(commentDTO cmt) throws Exception {
		// TODO Auto-generated method stub
		dao.updateComment(cmt);
	}

	@Override
	public void deleteComment(int c_num) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteComment(c_num);
	}

	@Override
	public void allDeleteComment(int b_num) throws Exception {
		// TODO Auto-generated method stub
		dao.allDeleteComment(b_num);
	}

	@Override
	public void allDeleteFile(int b_num) throws Exception {
		// TODO Auto-generated method stub
		dao.allDeleteFile(b_num);
	}

	@Override
	public void deleteFile(int file_num) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteFile(file_num);
	}

	@Override
	public List<boardDTO> printAdminBoard() throws Exception {
		// TODO Auto-generated method stub
		return dao.printAdminBoard();
	}

	@Override
	public List<boardDTO> limitAdminBoard() throws Exception {
		// TODO Auto-generated method stub
		return dao.limitAdminBoard();
	}

	/* 비밀 게시판 */
	
	@Override
	public int SCmaxNum() throws Exception {
		// TODO Auto-generated method stub
		return dao.SCmaxNum();
	}

	@Override
	public int SCselectMinNum() throws Exception {
		// TODO Auto-generated method stub
		return dao.SCselectMinNum();
	}

	@Override
	public int SCselectMaxNum() throws Exception {
		// TODO Auto-generated method stub
		return dao.SCselectMaxNum();
	}

	@Override
	public int SCtotalNum() throws Exception {
		// TODO Auto-generated method stub
		return dao.SCtotalNum();
	}

	@Override
	public boardDTO SCselectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return dao.SCselectBoard(num);
	}

	@Override
	public List<boardDTO> SClimitBoard(int first) throws Exception {
		// TODO Auto-generated method stub
		return dao.SClimitBoard(first);
	}

	
	
	/* 저장소 */
	
	@Override
	public int REPOmaxNum(int sc) throws Exception {
		// TODO Auto-generated method stub
		return dao.REPOmaxNum(sc);
	}

	@Override
	public int REPOselectMinNum(int sc) throws Exception {
		// TODO Auto-generated method stub
		return dao.REPOselectMinNum(sc);
	}

	@Override
	public int REPOselectMaxNum(int sc) throws Exception {
		// TODO Auto-generated method stub
		return dao.REPOselectMaxNum(sc);
	}

	@Override
	public int REPOtotalNum(int sc) throws Exception {
		// TODO Auto-generated method stub
		return dao.REPOtotalNum(sc);
	}

	@Override
	public boardDTO REPOselectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return dao.REPOselectBoard(num);
	}

	@Override
	public boardDTO REPOSCselectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return dao.REPOSCselectBoard(num);
	}

	@Override
	public List<boardDTO> REPOlimitBoard(int first) throws Exception {
		// TODO Auto-generated method stub
		return dao.REPOlimitBoard(first);
	}

	@Override
	public List<boardDTO> REPOSClimitBoard(int first) throws Exception {
		// TODO Auto-generated method stub
		return dao.REPOSClimitBoard(first);
	}

	
	
	/* 페이지 검색 */
	
	@Override
	public List<boardDTO> pageTitleSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.pageTitleSearch(vo);
	}

	@Override
	public List<boardDTO> pageContentSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.pageContentSearch(vo);
	}

	@Override
	public List<boardDTO> pageWriterSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.pageWriterSearch(vo);
	}

	@Override
	public List<boardDTO> pageTitleContentSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.pageTitleContentSearch(vo);
	}

	
	/* 전체 검색 */
	
	@Override
	public List<boardDTO> TotalSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.TotalSearch(vo);
	}
	// 5개 접기
	@Override
	public List<boardDTO> foldTotalSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.foldTotalSearch(vo);
	}

	
	
   
}
