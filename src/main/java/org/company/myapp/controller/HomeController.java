package org.company.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.company.myapp.service.LoginService;
import org.company.myapp.service.NaverLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
	@Autowired
	private LoginService loginService; 
	@Autowired
	private NaverLoginService naverLoginService;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}

//로그인폼으로 이동
@GetMapping("loginbs")
public void login(HttpSession session, Model model) throws Exception {
	//네이버 apiURL호출
	Map<String,String> result = naverLoginService.getApiUrl();
	String apiURL = result.get("apiURL");
	String state = result.get("state");
	session.setAttribute("state", state);//인증값은 세션에 저장
	model.addAttribute("apiURL", apiURL); //apiURL 뷰로
}

//로그인
@PostMapping("login")
public String login(@RequestParam String email, @RequestParam String passwd, 
		HttpSession session, RedirectAttributes rattr) {
	Map<String, Object> result = loginService.login(email, passwd);
	//code, msg
	//0 : 성공
	//1 : 이메일미존재
	//2 : 비밀번호 불일치
	//3 : 이메일 미인증
	int code = (int) result.get("code");
	String msg = (String)result.get("msg");
	rattr.addFlashAttribute("msg", msg);
	//성공일때는 home.jsp로 이동
	if (code == 0) {
		session.setAttribute("email", email);
		session.setMaxInactiveInterval(60*60*3);//세션의 유지 시간
		return "redirect:/";
	}else {	//실패일경우 loginbs.jsp로 이동
		return "redirect:loginbs";
	}
}

//콜백주소 : naver_callback
@RequestMapping("naver_callback")
public String naver_callback(@RequestParam String code, @RequestParam String state,
			HttpSession session, RedirectAttributes rattr) throws Exception {
	Map<String, String> result = naverLoginService.getTokenUserInfo(code, state);
	String email = result.get("email");
	//db에 회원을 등록
	Map<String, Object> naverResult = naverLoginService.insert(email);
	int rcode = (int)naverResult.get("code");
	String msg = (String)naverResult.get("msg");
	
	rattr.addFlashAttribute("msg", msg);
	if (rcode > 1) { //일반가입 회원인 경우
		return "redirect:/login";
	}
	//정상
	session.setAttribute("email", email);//세션에 이메일넣기
	return "redirect:/";
	

}

//로그아웃
@GetMapping("logout")
public String logout(HttpSession session) {
	session.invalidate();
	return "redirect:/";
}

//회사정보
@GetMapping("company")
public String company() {
	return "company";
}



@GetMapping("test")
public String test() {
	return "index";
}

@GetMapping("preview")
public String preview() {
	return "preview";
 }
}