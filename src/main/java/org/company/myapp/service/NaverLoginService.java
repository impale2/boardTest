package org.company.myapp.service;

import java.util.Map;

public interface NaverLoginService {
	//네이버로그인 apiUrl 만들기
	Map<String , String> getApiUrl() throws Exception;
	
	//토큰을 얻고 + 개인정보 얻기 
	Map<String , String> getTokenUserInfo(String code , String state) throws Exception;
	
	//db에 회원저장
	Map<String, Object> insert(String email);
	
	
}
