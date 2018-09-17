package cn.appsys.dao.devuser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {

	/**
	 * 根据�?发�?�编号查询开发�??
	 * @param devCode
	 * @return
	 */
	DevUser getDevUserByDevCode(@Param("devCode")String devCode);

}
