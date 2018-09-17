package cn.appsys.service.devuser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;

@Transactional
@Service
public class DevUserServiceImpl implements DevUserService {

	@Resource
	private DevUserMapper devUserMapper;

	@Override
	public DevUser login(String devCode, String devPassword) {
		DevUser devUser = devUserMapper.getDevUserByDevCode(devCode);
		if(devUser != null) {
			if(!devUser.getDevPassword().equals(devPassword)) {
				devUser = null;
			}
		}
		return devUser;
	}
}
