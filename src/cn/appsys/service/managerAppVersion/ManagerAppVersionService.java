package cn.appsys.service.managerAppVersion;

import cn.appsys.pojo.AppVersion;

public interface ManagerAppVersionService {

	/**
	 * 根据id查询ａｐｐ的版本号
	 * @param versionId
	 * @return
	 */
	AppVersion getAppVersionById(String versionId);

}
