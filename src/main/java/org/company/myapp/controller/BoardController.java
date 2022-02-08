package org.company.myapp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.company.myapp.dto.Page;
import org.company.myapp.service.BoardService;
import org.company.myapp.dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("board")
public class BoardController {
	@Autowired
	BoardService bs;
	@RequestMapping("/")
	public String list() {
		return"board/home";
	}
	//게시물조회
	@RequestMapping("list")
	public String list(@ModelAttribute Page page, Model model) {
		List<Board> blist = bs.selectList(page);
		model.addAttribute("blist", blist);
		return "board/list";
	}
	
	//상세조회폼으로 이동
	@RequestMapping("detail")
	public String detail(@RequestParam int bnum , Model model) {
		//조회수 +1
		bs.updateReadCnt(bnum);
		//게시물+게시물의 파일들
		Map<String, Object> result = bs.selectOne(bnum);
		model.addAttribute("result", result);
		return "board/detail";
	}
	//게시물등록폼으로 이동
	@GetMapping("add")
	public void add() {}
	
	//게시물 등록
	@PostMapping("add")
	public String add(@ModelAttribute Board board, HttpServletRequest request,
			RedirectAttributes rattr) throws Exception{
		//사용자의 ip
		board.setIp(request.getRemoteAddr()); 
		Map<String, Object> result = bs.insert(board);
		String msg = (String)result.get("msg");
		rattr.addFlashAttribute("msg", msg);
		return "redirect:list";	
	}
	
	//게시물삭제
	@RequestMapping("remove")
	public String remove(@RequestParam int bnum) {
		bs.updateRemoveYn(bnum);
		return "redirect:list";
	}
	
	//게시물수정폼으로 이동
	@GetMapping("modify")
	public String modify(@RequestParam int bnum, Model model) {
		Map<String, Object> result = bs.selectOne(bnum);
		model.addAttribute("result", result);
		return "board/modify";
	}
	
	//게시물수정
	//required = false : 데이터가 존재하지 않을수도 있다
	@PostMapping("modify")
	public String modify(@ModelAttribute Board board, 
			@RequestParam(required = false) List<Integer> removefile,
			HttpServletRequest request, RedirectAttributes rattr) throws Exception {
		//사용자의 ip
		board.setIp(request.getRemoteAddr()); 
		
		Map<String, Object> result =bs.update(board,removefile);
		String msg = (String)result.get("msg");
		rattr.addFlashAttribute("msg", msg);
		rattr.addAttribute("bnum", board.getBnum());
		return "redirect:detail";
	}
	//좋아요+1
	@ResponseBody //리턴값을 값자체로 보낸다
	@PutMapping("likecnt/{bnum}")
	public String likecnt(@PathVariable int bnum) {
		//db 좋아요+1 수정
		bs.updateLikeCnt(bnum);
		//db likecnt 조회
		int likecnt = bs.selectLikeCnt(bnum);
		return String.valueOf(likecnt);
	}
	
	//싫어요+1
	@ResponseBody //리턴값을 값자체로 보낸다
	@PutMapping("dislikecnt/{bnum}")
	public String dislikecnt(@PathVariable int bnum) {
		//db 싫어요+1 수정
		bs.updateDisLikeCnt(bnum);
		//db dislikecnt 조회
		int dislikecnt = bs.selectDisLikeCnt(bnum);
		return String.valueOf(dislikecnt);
	}
}
