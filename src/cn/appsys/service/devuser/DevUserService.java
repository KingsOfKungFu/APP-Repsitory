package cn.appsys.service.devuser;

import cn.appsys.pojo.DevUser;

public interface DevUserService {

	/**
	 *  �?发�?�登�?
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	DevUser login(String devCode, String devPassword);

}
