package org.company.myapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.company.myapp.dto.Board;
import org.company.myapp.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDaoImpl implements BoardDao{
	@Autowired
	private SqlSession session;
	

	@Override
	public List<Board> selectList(Page page) {
		// TODO Auto-generated method stub
		return session.selectList("org.company.myapp.BoardMapper.selectList", page);
	}

	@Override
	public Board selectOne(int bnum) {
		// TODO Auto-generated method stub
		return session.selectOne("org.company.myapp.BoardMapper.selectOne", bnum);
	}

	@Override
	public int insert(Board board) {
		// TODO Auto-generated method stub
		return session.insert("org.company.myapp.BoardMapper.insert", board);
	}

	@Override
	public int update(Board board) {
		// TODO Auto-generated method stub
		return session.update("org.company.myapp.BoardMapper.update", board);
	}

	@Override
	public int delete(int bnum) {
		// TODO Auto-generated method stub
		return session.delete("org.company.myapp.BoardMapper.delete", bnum);
	}
	//게시물 전체수 구하기
	@Override
	public int selectTotCnt(Page page) {
		// TODO Auto-generated method stub
		return session.selectOne("org.company.myapp.BoardMapper.selectTotCnt",page);
	}
	
	@Override
	public int updateReadCnt(int bnum) {
		// 조회수 +1
		return session.update("org.company.myapp.BoardMapper.updateReadCnt",bnum);
	}

	@Override
	public int updateRemoveYn(int bnum) {
		// 게시물 삭제시 업데이트
		return session.update("org.company.myapp.BoardMapper.updateRemoveYn",bnum);
	}

	@Override
	public int updateLikeCnt(int bnum) {
		// TODO Auto-generated method stub
		return session.update("org.company.myapp.BoardMapper.updateLikeCnt",bnum);
	}

	@Override
	public int updateDisLikeCnt(int bnum) {
		// TODO Auto-generated method stub
		return session.update("org.company.myapp.BoardMapper.updateDisLikeCnt",bnum);
	}

}
