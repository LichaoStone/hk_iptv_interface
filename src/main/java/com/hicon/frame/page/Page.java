package com.hicon.frame.page;

import java.io.Serializable;

/**
 * 
 * @作者 栗超
 * @时间 2018年5月25日 上午8:52:58
 * @说明
 */
public class Page implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int pageSize = 18;
	protected int totalRows = 0;
	protected int pageStartRow = 0;
	protected boolean paginationController = false;
	protected boolean paginationService = false;
	protected String sqlWhere;
	protected String sortWhere;

	public String getSqlWhere() {
		return this.sqlWhere;
	}

	public void setSqlWhere(String sqlWhere) {
		this.sqlWhere = sqlWhere;
	}

	public String getSortWhere() {
		return this.sortWhere;
	}

	public void setSortWhere(String sortWhere) {
		this.sortWhere = sortWhere;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getPageStartRow() {
		return this.pageStartRow;
	}

	public int getTotalRows() {
		return this.totalRows;
	}

	public void setPageSize(int i) {
		this.pageSize = i;
	}

	public void setPageStartRow(int i) {
		this.pageStartRow = i;
	}

	public void setTotalRows(int i) {
		this.totalRows = i;
	}

	public boolean isPaginationController() {
		return paginationController;
	}

	public void setPaginationController(boolean paginationController) {
		this.paginationController = paginationController;
	}

	public boolean isPaginationService() {
		return paginationService;
	}

	public void setPaginationService(boolean paginationService) {
		this.paginationService = paginationService;
	}
}