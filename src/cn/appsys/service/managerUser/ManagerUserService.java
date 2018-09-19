package cn.appsys.service.managerUser;

import cn.appsys.pojo.BackendUser;

public interface ManagerUserService {

	/**
	 * 后台人员的登录页面
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	BackendUser login(String userCode, String userPassword);

}
