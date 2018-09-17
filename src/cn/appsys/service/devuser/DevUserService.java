package cn.appsys.service.devuser;

import cn.appsys.pojo.DevUser;

public interface DevUserService {

	/**
	 *  å¼?å‘è?…ç™»å½?
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	DevUser login(String devCode, String devPassword);

}
