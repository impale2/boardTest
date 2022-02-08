package org.company.myapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.company.myapp.dao.MemberDao;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.company.myapp.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NaverLoginServiceImpl implements NaverLoginService {
	@Autowired
	private MemberDao mdao; 
	
	//네이버 로그인버튼의 url생성 
	@Override
	public Map<String, String> getApiUrl() throws Exception {
		String clientId = "J3UhoprsZ_vUaMjPgcSJ";//애플리케이션 클라이언트 아이디값";
		String redirectURI = URLEncoder.encode("http://localhost:8081/myapp/naver_callback", "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += "&client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&state=" + state;
		
		//네이버로그인 apiURL과 인증값(state)를 반환
		Map<String,String> result = new HashMap<>();
		result.put("apiURL", apiURL);
		result.put("state", state);
		
		return result;
		
	}
	//토큰 + 개인정보 얻기
	@Override
	public Map<String, String> getTokenUserInfo(String code, String state)throws Exception {
		// TODO Auto-generated method stub
		String access_token = getToken(code,state);
		Map<String, String> result = getUserInfo(access_token);
		
		
		return result;
	}
	//토큰얻기
	
	public String getToken(String code, String state) throws Exception {
	    String clientId = "J3UhoprsZ_vUaMjPgcSJ";//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "hx3LhBebfG";//애플리케이션 클라이언트 시크릿값";
	    String redirectURI = URLEncoder.encode("YOUR_CALLBACK_URL", "UTF-8");
	    String apiURL;
	    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
	    apiURL += "client_id=" + clientId;
	    apiURL += "&client_secret=" + clientSecret;
	    apiURL += "&redirect_uri=" + redirectURI;
	    apiURL += "&code=" + code;
	    apiURL += "&state=" + state;
	    String access_token = "";
	    String refresh_token = "";
	    System.out.println("apiURL="+apiURL);
	    try {
	      URL url = new URL(apiURL);
	      HttpURLConnection con = (HttpURLConnection)url.openConnection();
	      con.setRequestMethod("GET");
	      int responseCode = con.getResponseCode();
	      BufferedReader br;
	      System.out.print("responseCode="+responseCode);
	      if(responseCode==200) { // 정상 호출
	        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      } else {  // 에러 발생
	        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	      }
	      String inputLine;
	      StringBuffer res = new StringBuffer();
	      while ((inputLine = br.readLine()) != null) {
	        res.append(inputLine);
	      }
	      br.close();
	      if(responseCode==200) { //성공일때
	    	  System.out.println(res.toString());
	    	  //json파싱
	    	  JSONObject object = (JSONObject) new JSONParser().parse(res.toString());
	    	  access_token = (String) object.get("access_token");
	      }
	    } catch (Exception e) {
	      System.out.println(e);
	    }
		return access_token; //토큰반환
	}
	
	//개인정보 얻기
	//매개변수 :네이버 로그인 접근토큰
	
	public Map<String, String> getUserInfo(String token) throws Exception{
		// TODO Auto-generated method stub
        
        String header = "Bearer " + token; // Bearer 다음에 공백 추가

        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(apiURL,requestHeaders);


        System.out.println(responseBody);
        //json 파싱
        JSONObject object = (JSONObject) new JSONParser().parse(responseBody);
        object = (JSONObject) object.get("response");
        String email = (String) object.get("email");
		
	    System.out.println(email);
        //리턴 맵
	    Map<String, String> result = new HashMap<>();
	    result.put("email", email);
        
		return result;
	}
    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
	@Override
	public Map<String, Object> insert(String eamil) {
		// TODO Auto-generated method stub
		Map<String , Object> result = new HashMap<>();
		//code, msg
		// 0 : 네이버간편가입완료
		// 1 : 이미 네이버간편 가입된 회원
		// 2 : 일반회원가입된 회원
		
		//1)회원조회
		Member dbmember =  mdao.selectOne(eamil);
		
		//2)회원가입이 된 회원
		if(dbmember != null) {
			if(dbmember.getSimplejoin().equals("0")) {//일반회원
				result.put("code", 2);
				result.put("msg", "일반회원");
			}else if(dbmember.getSimplejoin().equals("1")) {
				result.put("code", 1);
				result.put("msg", "네이버 간편가입회원");
			}
			return result;
		}
		
		//3)회원가입
		Member member = new Member();
		member.setEmail(eamil);
		member.setPasswd("naver");
		member.setSimplejoin("1"); // 1 : 네이버 간편가입
		int cnt  = mdao.insert(member);
		
		result.put("code", 0);
		result.put("msg", "네이버 간편가입 완료");
		
		
		return result;
	}


	
}
