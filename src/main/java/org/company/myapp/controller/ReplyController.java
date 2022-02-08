package org.company.myapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.company.myapp.dto.Reply;
import org.company.myapp.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //@Controller + @ResponseBodt:리졸버 작동 
//@Controller
@RequestMapping("reply")
public class ReplyController {
	@Autowired
	private ReplyService rs; 
	//댓글의 리스트
	@ResponseBody
	@GetMapping("list/{bnum}")
	public List<Reply> list(@PathVariable int bnum) {
		List<Reply> rlist = rs.selectList(bnum);
		return rlist;
	}
	
	//댓글추가
	@ResponseBody
	@PostMapping("/")
	public String add(@RequestBody Reply reply, HttpServletRequest request) {
		reply.setIp(request.getRemoteAddr());
		rs.insert(reply);
		return "ok";
	}
	//댓글삭제
	@DeleteMapping("{rnum}")
	public String delete(@PathVariable int rnum) {
		rs.delete(rnum);
		return "ok";
	}
	//댓글 수정
	@PutMapping("/")
	public String modify(@PathVariable Reply reply) {
		rs.update(reply);
		return"ok";
	}

}
