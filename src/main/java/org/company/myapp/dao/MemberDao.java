package org.company.myapp.dao;


import java.util.List;

import org.company.myapp.dto.Member;

public interface MemberDao {
	int insert(Member member);
	int update(Member member);
	int delete(String email);
	Member selectOne(String email);
	List<Member> selectList(String findvalue);
	int update_emailauth(String email);
}
