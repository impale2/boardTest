package org.company.myapp.service;

import java.util.List;
import java.util.Map;

import org.company.myapp.dto.Member;

public interface MemberService {

	List<Member> selectList(String findvalue);

	Member selectOne(String email);

	Map<String, Object> insert(Member member) throws Exception;

	String update_emailauth(String email);

	Map<String, Object> update(Member member) throws Exception;

	Map<String, Object> delete(String email, String passwd);



}
