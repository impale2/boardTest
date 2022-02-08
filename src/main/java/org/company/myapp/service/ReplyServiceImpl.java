package org.company.myapp.service;

import java.util.List;

import org.company.myapp.dao.ReplyDao;
import org.company.myapp.dto.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyDao rdao;
	@Override
	public int insert(Reply reply) {
		//1)글순서(RESTEP+1)
		rdao.updateRestep(reply);
		//2)저장
		return rdao.insert(reply);
	}
	@Override
	public List<Reply> selectList(int bnum) {
		// TODO Auto-generated method stub
		return rdao.selectList(bnum);
	}
	@Override
	public void delete(int rnum) {
		// TODO Auto-generated method stub
		rdao.delete(rnum);
	}
	@Override
	public void update(Reply reply) {
		// TODO Auto-generated method stub
		rdao.update(reply);
	}

	
}
