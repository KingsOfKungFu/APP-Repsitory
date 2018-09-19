package cn.appsys.service.managerUser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.dao.managerUser.ManagerUserMapper;
import cn.appsys.pojo.BackendUser;

@Service
@Transactional
public class ManagerUserServiceImpl implements ManagerUserService {
	
	@Resource
	private ManagerUserMapper managerUserMapper;

	@Override
	public BackendUser login(String userCode, String userPassword) {
		 BackendUser backendUser = null;
		 backendUser = managerUserMapper.getManagerUserByUserCode(userCode);
		 if (backendUser != null) {
			 if (!backendUser.getUserPassword().equals(userPassword)) {
				 backendUser = null;
			 }
		 }
		 return backendUser;
	}

}
