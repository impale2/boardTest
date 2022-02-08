package org.company.myapp.dao;

import java.util.List;

import org.company.myapp.dto.Reply;

public interface ReplyDao {
	List<Reply> selectList(int bnum);
	int insert(Reply reply);
	int updateRestep(Reply reply);
	int delete(int rnum);
	int update(Reply reply);
}
