package com.douzone.mysite.vo;

public class PageCalVo {
	private int pageNum;
	private int maxPageNum;
	private int quotient;
	private int maxQuotient;
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getMaxPageNum() {
		return maxPageNum;
	}
	public void setMaxPageNum(int maxPageNum) {
		this.maxPageNum = maxPageNum;
	}
	public int getQuotient() {
		return quotient;
	}
	public void setQuotient(int quotient) {
		this.quotient = quotient;
	}
	public int getMaxQuotient() {
		return maxQuotient;
	}
	public void setMaxQuotient(int maxQuotient) {
		this.maxQuotient = maxQuotient;
	}
	@Override
	public String toString() {
		return "PageCalVo [pageNum=" + pageNum + ", maxPageNum=" + maxPageNum + ", quotient=" + quotient
				+ ", maxQuotient=" + maxQuotient + "]";
	}



}
