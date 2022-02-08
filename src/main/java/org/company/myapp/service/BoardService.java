package org.company.myapp.service;

import java.util.List;
import java.util.Map;

import org.company.myapp.dto.Board;
import org.company.myapp.dto.Page;

public interface BoardService {

	List<Board> selectList(Page page);
	Map<String, Object> selectOne(int bnum);
	Map<String, Object> insert(Board board) throws Exception;
	
	//조회수 +1
	int updateReadCnt(int bnum);
	
	//삭제시 update
	int updateRemoveYn(int bnum);
	Map<String, Object> update(Board board, List<Integer> removefile) throws Exception;
	//좋아요 +1
	void updateLikeCnt(int bnum);
	//좋아요 조회
	int selectLikeCnt(int bnum);
	//싫어요 
	void updateDisLikeCnt(int bnum);
	//싫어요 조회
	int selectDisLikeCnt(int bnum);
}
