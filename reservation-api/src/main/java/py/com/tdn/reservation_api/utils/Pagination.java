package py.com.tdn.reservation_api.utils;

import java.io.Serializable;
import java.util.List;

public class Pagination<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer currentPage;
	
	private Integer previousPage;
	
	private Integer nextPage;
	
	private Integer totalPage;
	
	private Integer pageSize;
	
	private List<T> items;

	public Pagination() {
		super();
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(Integer previousPage) {
		this.previousPage = previousPage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

}
