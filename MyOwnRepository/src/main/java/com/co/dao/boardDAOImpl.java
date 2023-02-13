package com.co.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.co.dto.FileDTO;
import com.co.dto.boardDTO;
import com.co.dto.commentDTO;
import com.co.dto.searchVO;

@Repository
public class boardDAOImpl implements boardDAO{
	
	@Inject
    private SqlSession sqlSession;
      
    private static final String Namespace = "com.co.mapper.boardMapper";
    
   
    @Override
	public void writeBoard(boardDTO letter) throws Exception{
		sqlSession.insert(Namespace+".writeBoard", letter);	
	}
	
    @Override
	public List<boardDTO> printBoard() throws Exception{
		return sqlSession.selectList(Namespace+".printBoard");
	}

	@Override
	public boardDTO selectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectBoard", num);
	}

	@Override
	public void addView(int num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".addView", num);
	}

	@Override
	public int selectMinNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectMinNum");
	}

	@Override
	public int selectMaxNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectMaxNum");
	}

	@Override
	public void updateBoard(boardDTO letter) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".updateBoard", letter);
		
	}

	@Override
	public void deleteBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(Namespace+".deleteBoard", num);		
	}

	@Override
	public void writeComment(commentDTO letter) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(Namespace+".writeComment", letter);
	}

	@Override
	public List<commentDTO> printComment(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".printComment", b_num);
		
	}

	@Override
	public void upComment(int num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".upComment", num);
	}

	@Override
	public void downComment(int num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".downComment", num);
	}

	@Override
	public void fileUpload(FileDTO fs) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(Namespace+".fileUpload", fs);
	}

	@Override
	public int maxNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".maxNum");
	}

	@Override
	public List<FileDTO> fileDownload(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".fileDownload", b_num);
	}

	@Override
	public FileDTO fileView(int file_num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".fileView", file_num);
	}

	@Override
	public List<FileDTO> fileViewer(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".fileViewer", b_num);
	}

	@Override
	public List<FileDTO> viewFile(int b_num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".viewFile", b_num);
	}

	@Override
	public int totalNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".totalNum");
	}

	@Override
	public List<boardDTO> limitBoard(int first) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".limitBoard", first);
	}

	@Override
	public void updateComment(commentDTO cmt) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(Namespace+".updateComment", cmt);
	}

	@Override
	public void deleteComment(int c_num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(Namespace+".deleteComment", c_num);
	}

	@Override
	public void allDeleteComment(int b_num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(Namespace+".allDeleteComment", b_num);
	}

	@Override
	public void allDeleteFile(int b_num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(Namespace+".allDeleteFile", b_num);
	}

	@Override
	public void deleteFile(int file_num) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(Namespace+".deleteFile", file_num);
	}

	@Override
	public List<boardDTO> printAdminBoard() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".printAdminBoard");
	}

	@Override
	public List<boardDTO> limitAdminBoard() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".limitAdminBoard");
	}

	/* 비밀 게시판 */
	
	@Override
	public int SCmaxNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".SCmaxNum");
	}

	@Override
	public int SCselectMinNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".SCselectMinNum");
	}

	@Override
	public int SCselectMaxNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".SCselectMaxNum");
	}

	@Override
	public int SCtotalNum() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".SCtotalNum");
	}

	@Override
	public boardDTO SCselectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".SCselectBoard", num);
	}

	@Override
	public List<boardDTO> SClimitBoard(int first) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".SClimitBoard", first);
	}

	
	/* 저장소 */
	
	@Override
	public int REPOmaxNum(int sc) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".REPOmaxNum", sc);
	}

	@Override
	public int REPOselectMinNum(int sc) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".REPOselectMinNum", sc);
	}

	@Override
	public int REPOselectMaxNum(int sc) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".REPOselectMaxNum", sc);
	}

	@Override
	public int REPOtotalNum(int sc) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".REPOtotalNum", sc);
	}

	
	@Override
	public boardDTO REPOselectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".REPOselectBoard", num);
	}

	@Override
	public boardDTO REPOSCselectBoard(int num) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".REPOSCselectBoard", num);
	}

	@Override
	public List<boardDTO> REPOlimitBoard(int first) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".REPOlimitBoard", first);
	}

	@Override
	public List<boardDTO> REPOSClimitBoard(int first) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".REPOSClimitBoard", first);
	}

	
	/* 페이지 검색 */
	
	@Override
	public List<boardDTO> pageTitleSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".pageTitleSearch", vo);
	}

	@Override
	public List<boardDTO> pageContentSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".pageContentSearch", vo);
	}

	@Override
	public List<boardDTO> pageWriterSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".pageWriterSearch", vo);
	}

	@Override
	public List<boardDTO> pageTitleContentSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".pageTitleContentSearch", vo);
	}

	
	/* 전체 검색 */
	
	@Override
	public List<boardDTO> TotalSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".TotalSearch", vo);
	}
	// 접기 5개
	@Override
	public List<boardDTO> foldTotalSearch(searchVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".foldTotalSearch", vo);
	}

	
	/* 내가 쓴 글/댓글 리스트 */
	
	@Override
	public List<boardDTO> myPost(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".myPost", id);
	}
	@Override
	public List<commentDTO> myComment(String nickname) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".myComment", nickname);
	}

	
	

	
	
}
