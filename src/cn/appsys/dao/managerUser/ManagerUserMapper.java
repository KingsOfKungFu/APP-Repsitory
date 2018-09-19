package cn.appsys.dao.managerUser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;

public interface ManagerUserMapper {

	BackendUser getManagerUserByUserCode(@Param("userCode")String userCode);

}
