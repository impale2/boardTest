package org.company.myapp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.company.myapp.dto.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDaoImpl implements ReplyDao{
	@Autowired
	private SqlSession sqlSession; 
	@Override
	public int insert(Reply reply) {
		// TODO Auto-generated method stub
		return sqlSession.insert("org.company.myapp.ReplyMapper.insert", reply);
	}


	@Override
	public int updateRestep(Reply reply) {
		// TODO Auto-generated method stub
		return sqlSession.update("org.company.myapp.ReplyMapper.updateRestep", reply);
	}


	@Override
	public List<Reply> selectList(int bnum) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("org.company.myapp.ReplyMapper.selectList", bnum);
	}


	@Override
	public int delete(int rnum) {
		// TODO Auto-generated method stub
		return sqlSession.delete("org.company.myapp.ReplyMapper.delete", rnum);
	}


	@Override
	public int update(Reply reply) {
		// TODO Auto-generated method stub
		return sqlSession.update("org.company.myapp.ReplyMapper.update", reply);
	
	}
	

}
