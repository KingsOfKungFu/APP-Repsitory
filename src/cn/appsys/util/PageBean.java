package cn.appsys.util;

import java.util.List;

/**
 * 鍒嗛〉宸ュ叿绫�
 * 
 * @author Charles7c
 *
 * @param <T>
 */
public class PageBean<T> {
	/**
	 * 每页显示记录数
	 */
	private int pageSize; 
	/**
	 * 当前页
	 */
	private int currentPageNo; 
	/**
	 * 总页数
	 */
	private int totalPage; 
	/**
	 * 总记录数
	 */
	private int totalCount; 
	/**
	 * 每页查询出来的记录集合
	 */
	private List<T> result;  
	/**
	 * 起始索引
	 */
	private int startIndex; 

	// 鑾峰彇璧峰绱㈠紩鏃惰嚜鍔ㄨ绠�
	public int getStartIndex() {
		return (currentPageNo - 1) * pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		// 璁剧疆鎬昏褰曟暟鏃� 杩涜璁＄畻鎬婚〉鏁�
		if (totalCount > 0) {
			this.totalPage = (totalCount % pageSize) == 0 ? totalCount / pageSize : (totalCount / pageSize)+1;
		}
		this.totalCount = totalCount;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

}
