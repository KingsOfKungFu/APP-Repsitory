package cn.appsys.dao.ManagerCategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface ManagerCategoryMapper {

	List<AppCategory> getAppCategoryLevelByParentId(@Param("parentId")Integer parentId);


}
