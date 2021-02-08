package com.yusys.agile.utils.page;

public class PageQuery<T> {

	protected int page = 1;
	protected int pageSize = 10;
	protected T query;

	public PageQuery(){}

	public PageQuery(Integer page, Integer pageSize){
		this.page = page==null?1:page;
		this.pageSize = pageSize==null?10:pageSize;
	}

	public PageQuery(Integer page, Integer pageSize, T query){
		this.page = page==null?1:page;
		this.pageSize = pageSize==null?10:pageSize;
		this.query = query;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFrom() {
		return pageSize * (page - 1);
	}

	public T getQuery() {
		return query;
	}

	public void setQuery(T query) {
		this.query = query;
	}
}
