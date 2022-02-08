package org.company.myapp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.company.myapp.dto.BoardFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardFileDaoImpl implements BoardFileDao{
	@Autowired
	private SqlSession sqlSerssion;
	@Override
	public List<BoardFile> selectList(int bnum) {
		// TODO Auto-generated method stub
		return sqlSerssion.selectList("org.company.myapp.BoardFileMapper.selectList", bnum);
	}
	@Override
	public int insert(BoardFile boardFile) {
		// TODO Auto-generated method stub
		return sqlSerssion.insert("org.company.myapp.BoardFileMapper.insert", boardFile);
	}
	@Override
	public int delete(int fnum) {
		// TODO Auto-generated method stub
		return sqlSerssion.delete("org.company.myapp.BoardFileMapper.delete", fnum);
	}
	

}
