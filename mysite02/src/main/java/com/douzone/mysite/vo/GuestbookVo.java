package com.douzone.mysite.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GuestbookVo {
	private Long no;
	private String name;
	private String password;
	private String regDate;
	private String message;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegDate() {
		return regDate;
	}
	public String getRegDate2() {
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		String regDate2 = date.format(time);
		return regDate2;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "GuestbookVo [no=" + no + ", name=" + name + ", password=" + password + ", regDate=" + regDate
				+ ", message=" + message + "]";
	}
	

}
