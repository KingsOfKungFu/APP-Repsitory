package cn.appsys.service.managerCategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface ManagerCategoryService {
	
	
	/**
	 * һ������
	 * @param parentId
	 * @return
	 */
	List<AppCategory> getAppCategoryLevelByParentId(Integer parentId);

	

}
