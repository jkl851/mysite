package com.douzone.mysite.vo;

public class Guestbook01Vo {
	private Long no;
	private String name;
	private String password;
	private String msg;
	private String reg_date;
	
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override
	public String toString() {
		return "Guestbook01Vo [no=" + no + ", name=" + name + ", msg=" + msg + ", reg_date="
				+ reg_date + "]";
	}
	
}
