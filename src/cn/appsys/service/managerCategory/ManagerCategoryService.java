package cn.appsys.service.managerCategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface ManagerCategoryService {
	
	
	/**
	 * 一级分类
	 * @param parentId
	 * @return
	 */
	List<AppCategory> getAppCategoryLevelByParentId(Integer parentId);

	

}
