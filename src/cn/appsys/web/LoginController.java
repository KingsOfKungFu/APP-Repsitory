package cn.appsys.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.devuser.DevUserService;
import cn.appsys.service.managerUser.ManagerUserService;

@Controller
public class LoginController {

	@Resource
	private DevUserService devUserService;
	
	@Resource
	private ManagerUserService managerUserService; 
	
	/**
	 * 后台管理的注销
	 * @param session
	 * @return
	 */
	@RequestMapping("/manager/logout")
	public String doManagerLoginout(HttpSession session) {
		return "redirect:/";
	}
	
	@RequestMapping("/manager/main")
	public String main() {
		return "backend/main";
	}
	/**
	 * 后台管理人员的登录页面
	 * @param request
	 * @param userCode
	 * @param userPassword
	 * @return
	 */
	@RequestMapping("/manager/dologin")
	public String doManagerLogin(HttpServletRequest request,@RequestParam String userCode,@RequestParam String userPassword) {
		BackendUser backendUser= managerUserService.login(userCode,userPassword);
		if (backendUser == null) {
			request.setAttribute("error", "用户编码或密码错误");
			return "backendlogin";
		}
		request.setAttribute("backendUser", backendUser);
		return "redirect:/manager/main";
		
	}
	
	/**
	 *  瀵�?閸欐垼?鍛暈闁�?
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
	 *  瀵�?閸欐垼?鍛瑜�?
	 * @param request
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	@RequestMapping("/dev/dologin")
	public String doDevLogin(HttpServletRequest request,@RequestParam String devCode,@RequestParam String devPassword) {
		DevUser loginUser = devUserService.login(devCode,devPassword);
		if(loginUser == null) {
			request.setAttribute("error", "鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒");
			return "devlogin";
		}
		request.getSession().setAttribute("devLoginUser", loginUser);
		return "redirect:/dev/main";
	}
	
	// 閸氬骸褰寸粻锛勬倞閸滃苯绱戦崣鎴�?鍛挬閸欐壆娅ヨぐ鏇炲弳閸欙綀鐑︽潪?
	@RequestMapping("/manager/login")
	public String toManagerLogin() {
		return "backendlogin";
	}
	
	@RequestMapping("/dev/login")
	public String toDevLogin() {
		return "devlogin";
	}
	
}
