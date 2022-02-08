package org.company.myapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.company.myapp.dao.BoardDao;
import org.company.myapp.dao.BoardFileDao;
import org.company.myapp.dto.Board;
import org.company.myapp.dto.BoardFile;
import org.company.myapp.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardDao bdao;
	@Autowired
	BoardFileDao bfdao;
	@Autowired
	FileService fs;
	@Override
	public List<Board> selectList(Page page) {
		//page 처리 값
		int curPage = page.getCurPage();//현재페이지
		int perPage = page.getPerPage();//한페이지당 게시물수
		int perBlock = page.getPerBlock(); //화면 페이지 수
		
		//1)전체게시물수
		int totCnt = bdao.selectTotCnt(page);
		
		//2)전체페이지수
		int totPage = totCnt / perPage;
		if (totCnt%perPage != 0) totPage ++; //나머지가 있으면 +1
		page.setTotPage(totPage);
		
		//3)시작번호
		int startNum = (curPage-1)*perPage+1;
		page.setStartNum(startNum);
		//4)끝번호
		int endNum = startNum + perPage -1;
		page.setEndNum(endNum);
		
		//5)시작페이지
		int startPage = curPage - ((curPage-1)%perBlock);
		page.setStartPage(startPage);
		
		//6)끝페이지
		int endPage = startPage + perBlock -1;
		if (endPage>totPage) endPage = totPage;//끝페이지는 전체페이지보다 클수없다
		page.setEndPage(endPage);
		
		System.out.println(page);
		return bdao.selectList(page);
	}
	@Override
	public Map<String, Object> selectOne(int bnum) {
		//게시물 + 게시물 파일들 조회
		//1)게시물조회
		Board board = bdao.selectOne(bnum);
		//2)게시물 파일들 조회
		List<BoardFile> bflist = bfdao.selectList(bnum);
		
		Map<String , Object> result = new HashMap<>();
		result.put("board", board);
		result.put("bflist", bflist);
		
		return result;
	}
	@Override
	public int updateReadCnt(int bnum) {
		// TODO Auto-generated method stub
		return bdao.updateReadCnt(bnum);
	}
	@Override
	public Map<String, Object> insert(Board board) throws Exception {
		
		
		bdao.insert(board);
		//2)게시물파일들 저장
				List<MultipartFile> files = board.getFiles();
		for(MultipartFile file : files) {
			String filename = fs.fileUpload(file);
			if (!filename.equals("")) {//파일이름이 공백이 아닐때 저장
				BoardFile boardFile = new BoardFile();
				boardFile.setBnum(board.getBnum());
				boardFile.setFilename(filename);
				bfdao.insert(boardFile);
			}
		}
		Map<String, Object> result = new HashMap<>();
		
		result.put("code", 0);
		result.put("msg", "저장완료");
		return result;


	}
	@Override
	public int updateRemoveYn(int bnum) {
		// TODO Auto-generated method stub
		return bdao.updateRemoveYn(bnum);
	}
	@Override
	public Map<String, Object> update(Board board, List<Integer> removefile) throws Exception {
		//1)게시물 수정
		bdao.update(board);
		//2)게시물파일 삭제
		if (removefile != null) {
			for(int fnum: removefile) {
				bfdao.delete(fnum);
			}
		}
		//3)게시물파일 추가
		List<MultipartFile> files = board.getFiles();
		if (files != null) {
			BoardFile boardFile = new BoardFile();
			boardFile.setBnum(board.getBnum());
			for(MultipartFile file : files) {
				String filename = fs.fileUpload(file);
				if (!filename.equals("")) {//파일이름이 공백이 아닐때 저장
					boardFile.setFilename(filename);
					bfdao.insert(boardFile);
				}
			}
		}
		
		//결과맵
		Map<String, Object> result = new HashMap<>();
		result.put("code", 0);
		result.put("msg", "수정완료");
		return result;
	}
	
	//좋아요 +1
	@Override
	public void updateLikeCnt(int bnum) {
		// TODO Auto-generated method stub
		
		bdao.updateLikeCnt(bnum);
	}
	@Override
	public int selectLikeCnt(int bnum) {
		// TODO Auto-generated method stub
		Board board = bdao.selectOne(bnum);
		return board.getLikecnt();
	}
	@Override
	public void updateDisLikeCnt(int bnum) {
		// TODO Auto-generated method stub
		bdao.updateDisLikeCnt(bnum);
	}
	@Override
	public int selectDisLikeCnt(int bnum) {
		// TODO Auto-generated method stub
		Board board = bdao.selectOne(bnum);
		return board.getDislikecnt();
	}
	
		
	

}
