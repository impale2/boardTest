package org.company.myapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.company.myapp.dao.MemberDao;
import org.company.myapp.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDao mdao;
	//파일저장
	@Autowired
	private FileService fileService;
	//암호화
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	//이메일전송
	@Autowired
	private MailSendService mailSendService;

	
	@Override
	public List<Member> selectList(String findvalue) {
		return mdao.selectList(findvalue);
	}

	@Override
	public Member selectOne(String email) {
		// TODO Auto-generated method stub
		return mdao.selectOne(email);
	}

	@Transactional
	@Override
	public Map<String, Object> insert(Member member)  throws Exception{
		Map<String, Object> result = new HashMap<>();
		//code : msg
		//0:정상
		//1:중복된 아이디
		
		//1)중복된 회원인지 체크
		Member dbmember = mdao.selectOne(member.getEmail());
		if (dbmember != null) { //회원이 존재할경우
			result.put("code", 1);
			result.put("msg", "중복된 아이디");
			return result;
		}
		//2)파일을 업로드
		String filename = fileService.fileUpload(member.getPhotofile());
		member.setFilename(filename);
		
		//3)비밀번호 암호화
		String cryptPassword = bCryptPasswordEncoder.encode(member.getPasswd());
		member.setPasswd(cryptPassword);
		
		//4)db저장
		int cnt = mdao.insert(member);
		
		//5)이메일전송
		String authCode = mailSendService.sendAuthMail(member.getEmail());
		result.put("authCode", authCode);
		
		result.put("code", 0);
		result.put("msg", "정상");
		return result;
	}

	@Override
	public String update_emailauth(String email) {
		int cnt = mdao.update_emailauth(email);
		if (cnt >0)
			return "이메일 인증 완료";
		else
			return "이메일 인증 실패";
	}

	@Override
	public Map<String, Object> update(Member member) throws Exception {
		Map<String, Object> result = new HashMap<>();
		//code, msg
		//0:성공
		//1:기존비밀번호 불일치
		
		//1)회원조회
		Member dbmember = mdao.selectOne(member.getEmail());
		
		//2)기존비밀번호 일치 여부(일반회원만)
		//입력한 비밀번호, db에 저장된 비밀번호 비교
		if (dbmember.getSimplejoin().equals("0")) {
			boolean match = bCryptPasswordEncoder.matches(member.getPasswd(), dbmember.getPasswd());
			if (!match) {
				result.put("code", 1);
				result.put("msg", "기존비밀번호 불일치");
				return result;
			}
		}
		
		//3)변경비밀번호 세팅(일반회원만)
		if (dbmember.getSimplejoin().equals("0")) {
			String passwd = null;
			if (!member.getChgpasswd().equals("")) {//변경할 비밀번호가 있다면
				passwd = member.getChgpasswd(); //변경할 패스워드
			}else {
				passwd = member.getPasswd(); //기존패스워드
			}
			member.setPasswd(bCryptPasswordEncoder.encode(passwd)); 
		}
		
		//4)파일처리
		String filename = fileService.fileUpload(member.getPhotofile());
		if (!filename.equals("")) { //파일을 변경할 경우
			member.setFilename(filename);
		}
		
		//수정
		mdao.update(member);
		
		result.put("code", 0);
		result.put("msg", "성공");
		return result;
		
	}

	@Override
	public Map<String, Object> delete(String email, String passwd) {
		Map<String, Object> result = new HashMap<>();
		//code, msg
		//0:성공
		//1:기존비밀번호 불일치
		
		//1)회원조회
		Member dbmember = mdao.selectOne(email);
		
		//2)기존비밀번호 일치 여부(일반회원만)
		//입력한 비밀번호, db에 저장된 비밀번호 비교
		if (dbmember.getSimplejoin().equals("0")) {
			boolean match = bCryptPasswordEncoder.matches(passwd, dbmember.getPasswd());
			if (!match) {
				result.put("code", 1);
				result.put("msg", "기존비밀번호 불일치");
				return result;
			}
		}
		//3)삭제
		mdao.delete(email);
		result.put("code", 0);
		result.put("msg", "성공");
		return result;
	}


}
