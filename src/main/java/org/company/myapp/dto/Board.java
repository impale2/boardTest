package org.company.myapp.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Board {
	private int bnum; //게시물번호
	private String email; //작성자이메일
	private String subject; //제목
	private String content; //내용
	private int readcnt; //조회수
	private int likecnt; //좋아요
	private int dislikecnt; //싫어요
	private String ip;
	private Date regidate; //등록일자
	private Date modidate; //수정일자
	private String removeyn; //삭제여부
	
	private List<MultipartFile> files;//파일들

	public Board(int bnum, String email, String subject, String content, int readcnt, int likecnt, int dislikecnt,
			String ip, Date regidate, Date modidate, String removeyn, List<MultipartFile> files) {
		super();
		this.bnum = bnum;
		this.email = email;
		this.subject = subject;
		this.content = content;
		this.readcnt = readcnt;
		this.likecnt = likecnt;
		this.dislikecnt = dislikecnt;
		this.ip = ip;
		this.regidate = regidate;
		this.modidate = modidate;
		this.removeyn = removeyn;
		this.files = files;
	}

	public Board() {
		super();
	}

	public int getBnum() {
		return bnum;
	}

	public void setBnum(int bnum) {
		this.bnum = bnum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReadcnt() {
		return readcnt;
	}

	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}

	public int getLikecnt() {
		return likecnt;
	}

	public void setLikecnt(int likecnt) {
		this.likecnt = likecnt;
	}

	public int getDislikecnt() {
		return dislikecnt;
	}

	public void setDislikecnt(int dislikecnt) {
		this.dislikecnt = dislikecnt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getRegidate() {
		return regidate;
	}

	public void setRegidate(Date regidate) {
		this.regidate = regidate;
	}

	public Date getModidate() {
		return modidate;
	}

	public void setModidate(Date modidate) {
		this.modidate = modidate;
	}

	public String getRemoveyn() {
		return removeyn;
	}

	public void setRemoveyn(String removeyn) {
		this.removeyn = removeyn;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "Board [bnum=" + bnum + ", email=" + email + ", subject=" + subject + ", content=" + content
				+ ", readcnt=" + readcnt + ", likecnt=" + likecnt + ", dislikecnt=" + dislikecnt + ", ip=" + ip
				+ ", regidate=" + regidate + ", modidate=" + modidate + ", removeyn=" + removeyn + ", files=" + files
				+ "]";
	}

	
	
	
}
