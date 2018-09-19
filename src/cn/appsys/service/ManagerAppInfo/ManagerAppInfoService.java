package cn.appsys.service.ManagerAppInfo;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.QueryManagerAppInfoVO;
import cn.appsys.util.PageBean;

public interface ManagerAppInfoService {

	/**
	 * ����id��ѯ��Ϣ
	 * @param appId
	 * @return
	 */
	AppInfo getAppInfoById(String appId);
	
	/**
	 * ����������ȡ��Ϣ�б�
	 * @param pageBean
	 * @param queryManagerAppInfoVO 
	 */
	void getAppInfoList(PageBean<AppInfo> pageBean, QueryManagerAppInfoVO queryManagerAppInfoVO);

	/**
	 * �������
	 * @param status
	 * @param id
	 * @return
	 */
	boolean updataVersion(Integer status, Integer id);

	

}
