package cn.appsys.dao.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	/**
	 * 根据父id获取分类列表
	 * @param parentId
	 * @return
	 */
	List<AppCategory> getAppCategoryListByParentId(@Param("parentId")Integer parentId);

}
