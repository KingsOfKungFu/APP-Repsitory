package cn.appsys.service.managerAppVersion;

import cn.appsys.pojo.AppVersion;

public interface ManagerAppVersionService {

	/**
	 * ����id��ѯ����İ汾��
	 * @param versionId
	 * @return
	 */
	AppVersion getAppVersionById(String versionId);

}
