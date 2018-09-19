package cn.appsys.dao.managerAppVersion;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface ManagerAppVersionMapper {

	AppVersion getAppVersionById(@Param("versionId")String versionId);

}
