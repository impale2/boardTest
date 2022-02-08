package org.company.myapp;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;

import org.company.myapp.dao.BoardDao;
import org.company.myapp.dto.Board;
import org.company.myapp.dto.Page;
import org.company.myapp.service.BoardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//스프링 테스트
//객체를 생성하기 위한 어노테이션 추가
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/**/servlet-context.xml"})

public class JunitDaoTest {


	@Autowired
	BoardDao bdao;
	@Autowired
	BoardService bs;
	@Test
	public void test() {
		Page page = new Page();
		page.setFindkey("email");
		page.setFindvalue("aa");
		List<Board> blist = bdao.selectList(page);
		System.out.println(blist);
	}
	@Test
	public void test2() throws Exception {
		Board board = new Board();
		
		board.setEmail("13");
		board.setSubject("11");
		board.setContent("11");
		System.out.println(board);
		
		bs.insert(board);	
	}
	@Test
	public void test3() throws Exception {
		Board board = new Board();
		
		board.setEmail("13");
		board.setSubject("11");
		board.setContent("11");
		System.out.println(board);
		
		bdao.insert(board);	
	}


}
