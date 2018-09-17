package cn.appsys.service.appcategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
	
	/**
	 * 根据父级id查询分类列表
	 * @param object
	 * @return
	 */
	List<AppCategory> getAppCategoryListByParentId(Integer parentId);

}
