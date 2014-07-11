package com.ybase.dorm.vo;

/*
 * ��ҳ��<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��24��<br/>
 */
public class Page {

	/** ��ǰҳ�� */
	private int current;
	
	/** ��ǰ��ʾ��һҳ */
	private int first;
	
	/** ��ǰ��ʾ��һ����¼ */
	private int firstRecord;
	
	/** ��ǰ��ʾ���һ����¼ */
	private int lastRecord;
	
	/** ��ǰ��ʾβҳ */
	private int last;
	
	/** ������ */
	private int totalRecord;
	
	/** ��ҳ�� */
	private int totalPage;
	
	/** ÿҳ��ʾ���� */
	private int pageRecord = 15;
	
	/** ÿ����ʾҳ�� */
	private int pageRow = 7;

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageRecord() {
		return pageRecord;
	}

	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}

	public int getPageRow() {
		return pageRow;
	}

	public void setPageRow(int pageRow) {
		this.pageRow = pageRow;
	}
	
	public int getFirstRecord() {
		return firstRecord;
	}

	public void setFirstRecord(int firstRecord) {
		this.firstRecord = firstRecord;
	}

	public int getLastRecord() {
		return lastRecord;
	}

	public void setLastRecord(int lastRecord) {
		this.lastRecord = lastRecord;
	}

	/*
	 * ��ҳ�����ʼ��<br/>
	 * ��һ�ε��÷�ҳ���󣬽��з�ҳʱ���������ж���ĳ�ʼ��<br/>
	 * ����ʹ�÷�ҳ����ʱ����Ҫ����ҳ�����ǰlast,first ״̬���棬<br/>
	 * �����ں�һ����ҳ�������ж�ʹ�á�<br/>
	 * 
	 * @DORMITORY_V1.0, yangxb, 20140524<br/>
	 * 
	 */
	public void initPage(){
		int pageC = this.totalRecord / this.pageRecord;
		int pageY = this.totalRecord % this.pageRecord;
		if (pageY != 0) {
			this.totalPage = pageC + 1;
		} else {
			this.totalPage = pageC;
		}
		
		if(this.current == 1 || this.current > this.totalPage){
			this.current = 1;
			this.first = 1;
			this.firstRecord = 0;
			if(totalPage <= pageRow){
				this.last = this.totalPage;
				if(this.pageRecord >= this.totalRecord){
					this.lastRecord = this.totalRecord - 1;
				}else{
					this.lastRecord = this.pageRecord - 1;
				}
			}else{
				this.last = this.pageRow;
				this.lastRecord = this.pageRecord - 1;
			}
		}else{
			if(this.current == this.last){
				if((this.current + this.pageRow - 2) > this.totalPage){
					this.last = this.totalPage;
					if(this.totalPage > this.pageRow){
						this.first = this.current - 1;
					}
					
					this.firstRecord = (this.current - 1) * this.pageRecord;
					if(this.current != this.totalPage){
						this.lastRecord = this.firstRecord + this.pageRecord - 1;
					}else{
						this.lastRecord = this.totalRecord - 1;
					}
				}else{
					this.first = this.current - 1;
					this.last = this.current + this.pageRow - 1;
					this.firstRecord = (this.current - 1) * this.pageRecord;
					this.lastRecord = this.firstRecord + this.pageRecord - 1;
				}
			}else if(this.current == this.first){
				if(this.current + 1 - (this.pageRow - 1) >= 1){
					this.last = this.current + 1;
					this.first = this.last - (this.pageRow - 1);
					this.firstRecord = (this.current - 1) * this.pageRecord;
					this.lastRecord = this.firstRecord + this.pageRecord - 1;
				}else{
					this.last = this.pageRow;
					this.first = 1;
					this.firstRecord = (this.current - 1) * this.pageRecord;
					this.lastRecord = this.firstRecord + this.pageRecord - 1;
				}
			}else{
				this.firstRecord = (this.current - 1) * this.pageRecord;
				this.lastRecord = this.firstRecord + this.pageRecord - 1;
				if(this.totalPage - this.first + 1 >= this.pageRow){
					this.last = this.first - 1 + this.pageRow;
				}else{
					this.last = this.totalPage;
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "Page [current=" + current + ", first=" + first
				+ ", firstRecord=" + firstRecord + ", lastRecord=" + lastRecord
				+ ", last=" + last + ", totalRecord=" + totalRecord
				+ ", totalPage=" + totalPage + ", pageRecord=" + pageRecord
				+ ", pageRow=" + pageRow + "]";
	}

	public static void main(String args[]){
		Page page = new Page();
//		page.setFirst(1);
//		page.setLast(3);
		page.setCurrent(1);
		page.setTotalRecord(14);
		page.initPage();
		System.out.println(page.toString());
	}
	
}
