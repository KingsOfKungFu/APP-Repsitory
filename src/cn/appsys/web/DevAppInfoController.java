package cn.appsys.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.sun.scenario.effect.Blend.Mode;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.pojo.QueryAppInfoVO;
import cn.appsys.service.appcategory.AppCategoryService;
import cn.appsys.service.appinfo.AppinfoService;
import cn.appsys.service.appversion.AppVersionService;
import cn.appsys.service.datadictionary.DataDictionaryService;
import cn.appsys.util.PageBean;

@Controller
@RequestMapping("/dev/app")
public class DevAppInfoController {
	
	@Resource
	private AppinfoService appInfoService;
	
	@Resource
	private DataDictionaryService dataDictionaryService;
	
	@Resource
	private AppCategoryService appCategoryService;
	
	@Resource
	private AppVersionService appVersionService;
	
	@ResponseBody
	@RequestMapping("list/delapp")
	public String delapp(Model model,Integer id) {
		
		Map<String,Object> hashMap = new HashMap<>();
		List<AppVersion> versionList = appVersionService.getVersionByAppInfoId(id);
		AppInfo appInfo = appInfoService.getAppInfoById(id);
		boolean flag = false;
		if(versionList != null) {
			//��ɾ�ļ�  ����versionListɾ��ÿ���汾���ļ� 
			for (AppVersion appVersion : versionList) {
				if(appVersion.getApkLocPath() != null) {
					File file = new File(appVersion.getApkLocPath());
					//flag = file.delete();
					//�汾�ļ�ɾ��ʧ��
					/*if(flag == false) {
						hashMap.put("delResult", "false");
						break;
					}*/
				}
				//ɾ���汾
				//flag = appVersionService.delVersionByInfoId(id);
			}
			/*if(flag == false) {
				hashMap.put("delResult", "false");
				//�ص�appinfolistҳ��
				return "developer/appinfolist";
			}*/
			//ɾ��appͼƬ     ·���Բ���ɾ����
			if(appInfo.getLogoLocPath() != null) {
				File file = new File(appInfo.getLogoLocPath());
				//flag = file.delete();
			}
			/*if(flag == false) {
				hashMap.put("delResult", "false");
				//�ص�appinfolistҳ��
				return "developer/appinfolist";
			}*/
			//����Ķ�ɾ���ɹ���   ɾ��app��Ϣ
			if(flag) {
			 	flag = appInfoService.dealpp(id);
			}
			if(flag == false) {
				hashMap.put("delResult", "false");
				//�ص�appinfolistҳ��
				return "developer/appinfolist";
			}
			hashMap.put("delResult", "true");
		}
		//��ת��appinfolstҳ��
		return JSON.toJSONString(hashMap);
	}
	
	/**
	 * �鿴app��ϸ��Ϣ
	 * @param model
	 * @param appinfoid
	 * @return
	 */
	@RequestMapping("/list/appview/{appinfoid}")
	public String appview(Model model,@PathVariable Integer appinfoid) {
		
		AppInfo appInfo = appInfoService.getAppInfoById(appinfoid);
		if(appInfo != null) {
			model.addAttribute("appInfo", appInfo);
		}
		return "developer/appinfoview";
	}
	
	/**
	 * ���Ӱ汾
	 * @param request
	 * @param appVersion
	 * @param attach
	 * @return
	 */
	@RequestMapping("addversionsave")
	public String addversionsave(HttpServletRequest request,@ModelAttribute AppVersion appVersion,
			@RequestParam(value="a_downloadLink",required=false) MultipartFile attach) {
		String IdPicPath = null;
		String fileName = null;
		String path = null;
		//�ж��ļ��Ƿ�Ϊ��
		if(!attach.isEmpty()) {
			//�ļ��ķ������洢·��
			path = request.getServletContext().getRealPath("statics"+File.separator+"uploadfils");
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 900000000;
			if(attach.getSize() > filesize) {
				request.getSession().setAttribute("fileUploadError", "�ϴ��ļ����ܳ���500kb");
				//�������Ӱ汾ҳ��
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")){
				//���ļ���
				fileName = System.currentTimeMillis()+"_xxx.apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//����
				try {
					attach.transferTo(targetFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "�ϴ�ʧ��");
					return "developer/appversionadd";
				}
				IdPicPath = path+File.separator+fileName;
				
			}else {
				request.setAttribute("fileUploadError", "�ϴ��ļ���ʽ����ȷ");
				return "developer/appversionadd";
			}
		}
		
		//���ƶ���ֵ
		//��������  �ļ��洢·��+�ļ���
		appVersion.setDownloadLink(path+fileName);
		//������
		DevUser devUser = (DevUser) request.getSession().getAttribute("devLoginUser");
		appVersion.setCreatedBy(devUser.getCreatedBy());
		//����ʱ��
		appVersion.setCreationDate(new Date());
		//apk�ļ��ķ������洢·��
		appVersion.setApkLocPath(path);
		//�ϴ���apk�ļ�����
		appVersion.setApkFileName(fileName);
		
		if(!appVersionService.addVersion(appVersion)) {
			return "developer/appversionadd";
		}
		
		return "redirect:/dev/app/list";
	}
	
	/**
	 * ����appId��ѯ�汾�б�
	 * @param model
	 * @param appinfoid
	 * @return
	 */
	@RequestMapping("list/appversionadd/{appinfoid}")
	public String appversionadd(Model model,@PathVariable Integer appinfoid) {
		
		List<AppVersion> appVersionList = appVersionService.getAppVersionByInfoid(appinfoid);
		if(appVersionList != null) {
			model.addAttribute("appVersionList",appVersionList);
		}
		return "developer/appversionadd";
	}
	
	
	
	/**
	 * ���ݸ�if��ѯ�����б�
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getclist/{pid}")
	public String getCategoryList(@PathVariable Integer pid) {
		List<AppCategory> appCategoryList = appCategoryService.getAppCategoryListByParentId(pid);
		return JSON.toJSONString(appCategoryList);
	}
	
	/**
	 * ��ת����ҳ
	 * @param model
	 * @param queryAppInfoVO
	 * @return
	 */
	@RequestMapping("/list")
	public String appList(Model model,@ModelAttribute QueryAppInfoVO queryAppInfoVO) {
		
		Integer currentPageNo = 1;
		if(queryAppInfoVO.getPageIndex() != null) {
			currentPageNo = queryAppInfoVO.getPageIndex();
		}
		Integer pageSize = 5;
		PageBean<AppInfo> pageBean = new PageBean<AppInfo>();
		pageBean.setCurrentPageNo(currentPageNo);
		pageBean.setPageSize(pageSize);
		
		appInfoService.getAppInfoList(pageBean,queryAppInfoVO);
		
		//��ѯapp״̬
		List<DataDictionary> statusList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_STATUS");
		//��ѯapp����ƽ̨
		List<DataDictionary> flatFormList = dataDictionaryService.getDataDictionaryListByTypeCode("APP_FLATFORM");
		//��ѯһ������
		List<AppCategory> categoryLevel1List = appCategoryService.getAppCategoryListByParentId(null);
		
		// ���Ʒ���Ļ���
		// �������һ������  ˵����ѡ���  ���Կ϶���������������  ��ΪӦ�ý���������ȫ����ѯ
		if(queryAppInfoVO.getQueryCategoryLevel1() != null) {
			List<AppCategory> categoryLevel2List = appCategoryService.getAppCategoryListByParentId(queryAppInfoVO.getQueryCategoryLevel1());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if(queryAppInfoVO.getQueryCategoryLevel2() != null) {
			List<AppCategory> categoryLevel3List = appCategoryService.getAppCategoryListByParentId(queryAppInfoVO.getQueryCategoryLevel2());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("queryAppInfoVO", queryAppInfoVO);
		
		
		
		return "developer/appinfolist";
	}
	
}
