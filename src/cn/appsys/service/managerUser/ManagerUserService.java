package cn.appsys.service.managerUser;

import cn.appsys.pojo.BackendUser;

public interface ManagerUserService {

	/**
	 * ��̨��Ա�ĵ�¼ҳ��
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	BackendUser login(String userCode, String userPassword);

}
