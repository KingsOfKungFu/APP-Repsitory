package cn.appsys.service.ManagerAppInfo;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.QueryManagerAppInfoVO;
import cn.appsys.util.PageBean;

public interface ManagerAppInfoService {

	/**
	 * 根据id查询信息
	 * @param appId
	 * @return
	 */
	AppInfo getAppInfoById(String appId);
	
	/**
	 * 根据条件获取信息列表
	 * @param pageBean
	 * @param queryManagerAppInfoVO 
	 */
	void getAppInfoList(PageBean<AppInfo> pageBean, QueryManagerAppInfoVO queryManagerAppInfoVO);

	/**
	 * 更新审核
	 * @param status
	 * @param id
	 * @return
	 */
	boolean updataVersion(Integer status, Integer id);

	

}
