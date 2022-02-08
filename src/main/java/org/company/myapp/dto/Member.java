package org.company.myapp.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class Member {
	private String email;
	private String passwd;
	private String chgpasswd; //변경 비밀번호
	private String zipcode;
	private String addr;
	private String addrdetail;
	private String filename; //파일이름
	private String emailauth="0"; //0:미인증, 1:인증
	private String simplejoin="0"; //0:일반가입,1:네이버,2:카카오,3.구글
	private Date regidate;
	
	private MultipartFile photofile; //파일
	
	public Member() {
		super();
	}

	public Member(String email, String passwd, String chgpasswd, String zipcode, String addr, String addrdetail,
			String filename, String emailauth, String simplejoin, Date regidate, MultipartFile photofile) {
		super();
		this.email = email;
		this.passwd = passwd;
		this.chgpasswd = chgpasswd;
		this.zipcode = zipcode;
		this.addr = addr;
		this.addrdetail = addrdetail;
		this.filename = filename;
		this.emailauth = emailauth;
		this.simplejoin = simplejoin;
		this.regidate = regidate;
		this.photofile = photofile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getChgpasswd() {
		return chgpasswd;
	}

	public void setChgpasswd(String chgpasswd) {
		this.chgpasswd = chgpasswd;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAddrdetail() {
		return addrdetail;
	}

	public void setAddrdetail(String addrdetail) {
		this.addrdetail = addrdetail;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getEmailauth() {
		return emailauth;
	}

	public void setEmailauth(String emailauth) {
		this.emailauth = emailauth;
	}

	public String getSimplejoin() {
		return simplejoin;
	}

	public void setSimplejoin(String simplejoin) {
		this.simplejoin = simplejoin;
	}

	public Date getRegidate() {
		return regidate;
	}

	public void setRegidate(Date regidate) {
		this.regidate = regidate;
	}

	public MultipartFile getPhotofile() {
		return photofile;
	}

	public void setPhotofile(MultipartFile photofile) {
		this.photofile = photofile;
	}

	@Override
	public String toString() {
		return "Member [email=" + email + ", passwd=" + passwd + ", chgpasswd=" + chgpasswd + ", zipcode=" + zipcode
				+ ", addr=" + addr + ", addrdetail=" + addrdetail + ", filename=" + filename + ", emailauth="
				+ emailauth + ", simplejoin=" + simplejoin + ", regidate=" + regidate + ", photofile=" + photofile
				+ "]";
	}


}
