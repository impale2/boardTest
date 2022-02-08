package org.company.myapp.dao;

import java.util.List;

import org.company.myapp.dto.BoardFile;

public interface BoardFileDao {
	List<BoardFile> selectList(int bnum);
	int insert(BoardFile boardFile);
	int delete(int fnum);
	
}
