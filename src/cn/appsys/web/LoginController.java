package cn.appsys.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.DevUserService;

@Controller
public class LoginController {

	@Resource
	private DevUserService devUserService;
	
	/**
	 *  �?发�?�注�?
	 * @param session
	 * @return
	 */
	@RequestMapping("/dev/logout")
	public String doDevLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/dev/login";
	}
	
	@RequestMapping("/dev/main")
	public String toDevMain() {
		return "developer/main";
	}
	
	/**
	 *  �?发�?�登�?
	 * @param request
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	@RequestMapping("/dev/dologin")
	public String doDevLogin(HttpServletRequest request,@RequestParam String devCode,@RequestParam String devPassword) {
		DevUser loginUser = devUserService.login(devCode,devPassword);
		if(loginUser == null) {
			request.setAttribute("error", "�û������������");
			return "devlogin";
		}
		request.getSession().setAttribute("devLoginUser", loginUser);
		return "redirect:/dev/main";
	}
	
	// 后台管理和开发�?�平台登录入口跳�?
	@RequestMapping("/manager/login")
	public String toManagerLogin() {
		return "backendlogin";
	}
	
	@RequestMapping("/dev/login")
	public String toDevLogin() {
		return "devlogin";
	}
	
}
