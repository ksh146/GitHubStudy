package kr.co.DB;

import java.util.List;

public class PageTO {
	private int curpage = 1;
	private int perpage = 10;
	private int perline = 10;
	private int amount;
	private int totalpage;
	private int startnum;
	private int endnum;
	private List<NoticeDTO> list;
	private int beginlinenum;
	private int stoplinenum;
	
	public PageTO() {
		all();
	}

	public PageTO(int curpage) {
		super();
		this.curpage = curpage;
		all();
	}

	public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
		all();
	}

	public int getPerpage() {
		return perpage;
	}

	public void setPerpage(int perpage) {
		this.perpage = perpage;
		all();
	}

	public int getPerline() {
		return perline;
	}

	public void setPerline(int perline) {
		this.perline = perline;
		all();
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
		all();
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public int getStartnum() {
		return startnum;
	}

	public void setStartnum(int startnum) {
		this.startnum = startnum;
	}

	public int getEndnum() {
		return endnum;
	}

	public void setEndnum(int endnum) {
		this.endnum = endnum;
	}

	public List<NoticeDTO> getList() {
		return list;
	}

	public void setList(List<NoticeDTO> list) {
		this.list = list;
	}

	public int getBeginlinenum() {
		return beginlinenum;
	}

	public void setBeginlinenum(int beginlinenum) {
		this.beginlinenum = beginlinenum;
	}

	public int getStoplinenum() {
		return stoplinenum;
	}

	public void setStoplinenum(int stoplinenum) {
		this.stoplinenum = stoplinenum;
	}
	
	private void all() {
		totalpage = (amount -1) / perpage +1;
		startnum = (curpage -1) * perpage +1;
		endnum = curpage * perpage;
		if (endnum > amount) {
			endnum = amount;
		}
		
		beginlinenum = ((curpage -1) / perline) * perline +1;
		stoplinenum = beginlinenum + (perline -1);
		if (stoplinenum > totalpage) {
			stoplinenum = totalpage;
		}
		
	}

}
