package cn.appsys.dao.appinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.QueryAppInfoVO;

public interface AppInfoMapper {
	
	/**
	 * 根据infoid删除app
	 * @param id
	 * @return
	 */
	int dealpp(@Param("id") Integer id);
	
	/**
	 * 根据id查询app信息
	 * @param appinfoid
	 * @return
	 */
	AppInfo getAppInfoById(@Param("appinfoid")Integer appinfoid);
	/**
	 * 根据条件查询总记录数
	 * @param queryAppInfoV
	 * @return
	 */
	/*int getAppInfoCount(@Param("queryAppInfoVO") QueryAppInfoVO queryAppInfoVO);*/
	int getAppInfoCount(QueryAppInfoVO queryAppInfoVO);
	
	/**
	 * 根据条件查询每页记录集合
	 * @param queryAppInfoV
	 * @return
	 */
	/*List<AppInfo> getAppInfoList(@Param("queryAppInfoVO") QueryAppInfoVO queryAppInfoVO);*/
	List<AppInfo> getAppInfoList(QueryAppInfoVO queryAppInfoVO);
	
	
}
