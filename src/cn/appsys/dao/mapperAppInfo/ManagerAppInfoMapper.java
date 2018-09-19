package cn.appsys.dao.mapperAppInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.QueryManagerAppInfoVO;

public interface ManagerAppInfoMapper {

	int getAppInfoCount(QueryManagerAppInfoVO queryManagerAppInfoVO);

	List<AppInfo> getAppInfoList(QueryManagerAppInfoVO queryManagerAppInfoVO);

	AppInfo getAppInfoById(@Param("appId")String appId);

	boolean updataVersion(@Param("status")Integer status, @Param("id")Integer id);

}
