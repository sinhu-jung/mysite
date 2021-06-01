package com.douzone.mysite.vo;

public class PageVo {
	private long nowPage;
	private long nowBlock;
	private long totalPage;
	private long startPage;
	private long endPage;
	private long pageScale;
	
	public long getNowPage() {
		return nowPage;
	}
	public void setNowPage(long nowPage) {
		this.nowPage = nowPage;
	}
	public long getNowBlock() {
		return nowBlock;
	}
	public void setNowBlock(long nowBlock) {
		this.nowBlock = nowBlock;
	}
	public long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	public long getStartPage() {
		return startPage;
	}
	public void setStartPage(long startPage) {
		this.startPage = startPage;
	}
	public long getEndPage() {
		return endPage;
	}
	public void setEndPage(long endPage) {
		this.endPage = endPage;
	}
	public long getPageScale() {
		return pageScale;
	}
	public void setPageScale(long pageScale) {
		this.pageScale = pageScale;
	}
}
