package cn.appsys.service.appcategory;

import java.util.List;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryService {
	
	/**
	 * ���ݸ���id��ѯ�����б�
	 * @param object
	 * @return
	 */
	List<AppCategory> getAppCategoryListByParentId(Integer parentId);

}
