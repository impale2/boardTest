package org.company.myapp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.company.myapp.dto.Member;
import org.company.myapp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("member") //컨트롤러의 매핑정보
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	//리스트 폼으로 이동
	@RequestMapping("/")
	public String list() {
		return "member/list";
	}
	
	//리스트조회
	//@RequestMapping(value="list", method=RequestMethod.GET)
	@GetMapping("list")
	public String list(@RequestParam String findvalue, Model model) {
		List<Member> mlist = memberService.selectList(findvalue);
		model.addAttribute("mlist", mlist);
		return "member/list";
	}
	
	//수정폼으로 이동
	@GetMapping("modify")
	public String modify(@RequestParam String email, Model model) {
		Member member = memberService.selectOne(email);
		model.addAttribute("member", member);
		return "member/modify";
	}
	
	//수정
	@PostMapping("modify")
	public String modify(@ModelAttribute Member member , Model model,RedirectAttributes rattr) throws Exception {
		Map<String, Object> result = memberService.update(member);
		int code = (int) result.get("code");
		String msg = (String) result.get("msg");
		
		if (code == 0) { //홈으로 이동(리다이렉트이동)
			rattr.addFlashAttribute("msg", msg);
			return "redirect:/";
		}else { //수정폼으로 이동(forward이동:입력정보(member) => view전달)
			model.addAttribute("msg", msg);
			return "member/modify";
		}
		
	}
	
	//회원가입폼으로 이동
	//void : 매핑정보와 return이 같을때 return 생략가능
	@GetMapping("join")
	public void join() {}
	
	//회원가입
	@PostMapping("join")
	public String join(@ModelAttribute Member member, HttpSession session, RedirectAttributes rattr) throws Exception {
		Map<String, Object> result = memberService.insert(member);
		//code : msg : authCode
		//0:정상
		//1:중복된 아이디
		int code = (int)result.get("code");
		String msg = (String)result.get("msg");
		rattr.addFlashAttribute("msg", msg);
		if (code == 0) { //정상일때
			//이메일 인증 업데이트시 필요
			//이메일 인증 번호와 이메일을 세션에 넣기
			session.setAttribute("authCode", result.get("authCode"));
			session.setAttribute("authemail", member.getEmail());
			
			// / :절대경로(contextpath제외) 
			return "redirect:/login";
		}else {
			return "redirect:/member/join"; //정상이 아닐때
		}
	}
	
	//주소팝업창 호출
	//jusoPopup.jsp 이동시는 get방식
	//주소를 검색후 callback시에는 post방식
	@RequestMapping("jusoPopup")
	public void jusoPopup() {
		//return "member/jusoPopup"; //생략
	}
	
	//이메일에서 링크클릭시 처리(callback주소)
	@GetMapping("joinConfirm")
	public String joinConfirm(@RequestParam String authCode, HttpSession session, RedirectAttributes rattr) {
		String msg;
		String authCode_s = (String) session.getAttribute("authCode");
		if (authCode.equals(authCode_s)) { //이메일 인증이 됐다면
			String email = (String) session.getAttribute("authemail");
			msg = memberService.update_emailauth(email);
			
		}else { //이메일 인증이 안된다면(세션만료일경우 등등)
			msg = "세션 만료";
		}
		rattr.addFlashAttribute("msg", msg);
		//절대경로
		return "redirect:/login";
	}
	
	//회원탈퇴
	@GetMapping("remove")
	public String remove(@RequestParam String email, @RequestParam String passwd,
			RedirectAttributes rattr, HttpSession session) {
		Map<String, Object> result = memberService.delete(email,passwd );
		int code = (int) result.get("code");
		String msg = (String)result.get("msg");
		rattr.addFlashAttribute("msg", msg);
		//code가 0일때 : login.jsp이동
		if (code == 0) {
			//세션삭제
			session.invalidate();
			return "redirect:/login";
		}else {
			// 아닐때 : 수정으로 이동
			rattr.addAttribute("email", email);
			return "redirect:modify";
		}
	
	}
	
	
}
