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
	 *  寮?鍙戣?呮敞閿?
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
	 *  寮?鍙戣?呯櫥褰?
	 * @param request
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	@RequestMapping("/dev/dologin")
	public String doDevLogin(HttpServletRequest request,@RequestParam String devCode,@RequestParam String devPassword) {
		DevUser loginUser = devUserService.login(devCode,devPassword);
		if(loginUser == null) {
			request.setAttribute("error", "用户名或密码错误");
			return "devlogin";
		}
		request.getSession().setAttribute("devLoginUser", loginUser);
		return "redirect:/dev/main";
	}
	
	// 鍚庡彴绠＄悊鍜屽紑鍙戣?呭钩鍙扮櫥褰曞叆鍙ｈ烦杞?
	@RequestMapping("/manager/login")
	public String toManagerLogin() {
		return "backendlogin";
	}
	
	@RequestMapping("/dev/login")
	public String toDevLogin() {
		return "devlogin";
	}
	
}
