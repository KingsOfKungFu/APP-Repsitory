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
	 *  å¼?å‘è?…æ³¨é”?
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
	 *  å¼?å‘è?…ç™»å½?
	 * @param request
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	@RequestMapping("/dev/dologin")
	public String doDevLogin(HttpServletRequest request,@RequestParam String devCode,@RequestParam String devPassword) {
		DevUser loginUser = devUserService.login(devCode,devPassword);
		if(loginUser == null) {
			request.setAttribute("error", "ÓÃ»§Ãû»òÃÜÂë´íÎó");
			return "devlogin";
		}
		request.getSession().setAttribute("devLoginUser", loginUser);
		return "redirect:/dev/main";
	}
	
	// åå°ç®¡ç†å’Œå¼€å‘è?…å¹³å°ç™»å½•å…¥å£è·³è½?
	@RequestMapping("/manager/login")
	public String toManagerLogin() {
		return "backendlogin";
	}
	
	@RequestMapping("/dev/login")
	public String toDevLogin() {
		return "devlogin";
	}
	
}
