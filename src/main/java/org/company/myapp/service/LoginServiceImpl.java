package org.company.myapp.service;

import java.util.HashMap;
import java.util.Map;

import org.company.myapp.dao.MemberDao;
import org.company.myapp.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private MemberDao mdao;

	//암호화
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Map<String, Object> login(String email, String passwd) {
		Map<String, Object> result = new HashMap();
		//code, msg
		//0 : 성공
		//1 : 이메일미존재
		//2 : 비밀번호 불일치
		//3 : 이메일 미인증
		//4 : 간편가입 회원
		
		//회원조회
		Member member = mdao.selectOne(email);
		//1)이메일 미존재
		if (member == null) {
			result.put("code", 1);
			result.put("msg", "이메일이 존재하지 않습니다.");
			return result;
		}
		//2)간편가입 체크
		String simplejoin = member.getSimplejoin();
		if (!simplejoin.equals("0")) {
			if (simplejoin.equals("1")) { //네이버간편가입회원
				result.put("code", 4);
				result.put("msg", "네이버 간편가입 회원입니다.!");
				return result;
			}else
				return result;
		}
		
		//3)비밀번호 체크
		//평문과 암호문을 비교해서 일치여부 반환
		boolean match =  bCryptPasswordEncoder.matches(passwd, member.getPasswd());
		if (!match) {
			result.put("code", 2);
			result.put("msg", "비밀번호가 일치 하지 않습니다.");
			return result;
		}
		//4)이메일 인증 체크
		if (!member.getEmailauth().equals("1")) {
			result.put("code", 3);
			result.put("msg", "이메일 미 인증");
			return result;
		}
		
		//성공
		result.put("code", 0);
		result.put("msg", "로그인 성공");
		return result;
		

	}

}
