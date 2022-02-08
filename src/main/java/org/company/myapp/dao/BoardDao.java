package org.company.myapp.dao;

import java.util.List;
import java.util.Map;

import org.company.myapp.dto.Board;
import org.company.myapp.dto.Page;

public interface BoardDao {
	List<Board> selectList(Page page);
	Board selectOne(int bnum);
	int insert(Board board);
	int update(Board board);
	int delete(int bnum);
	//전체게시물수
	int selectTotCnt(Page page);
	//조회수 +1
	int updateReadCnt(int bnum);
	//삭제시 update
	int updateRemoveYn(int bnum);
	//좋아요 +1
	int updateLikeCnt(int bnum);
	//싫어요 +1
	int updateDisLikeCnt(int bnum);
}
