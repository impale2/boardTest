package org.company.myapp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.company.myapp.dto.Member;
@Repository
public class MemberDaoImpl implements MemberDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public int insert(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.insert("org.company.myapp.MemberMapper.insert", member);
	}

	@Override
	public int update(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.update("org.company.myapp.MemberMapper.update", member);
	}

	@Override
	public int delete(String email) {
		// TODO Auto-generated method stub
		return sqlSession.delete("org.company.myapp.MemberMapper.delete", email);
	}

	@Override
	public Member selectOne(String email) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("org.company.myapp.MemberMapper.selectOne", email);
	}

	@Override
	public List<Member> selectList(String findvalue) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("org.company.myapp.MemberMapper.selectList", findvalue);
	}

	@Override
	public int update_emailauth(String email) {
		// TODO Auto-generated method stub
		return sqlSession.update("org.company.myapp.MemberMapper.update_emailauth", email);
	}
}
