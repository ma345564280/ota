package com.ota.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ota.dao.BoxTypeMapper;
import com.ota.dao.BusinessMapper;
import com.ota.dao.FileMappingMapper;
import com.ota.dao.OTAGroupMapper;
import com.ota.dao.UpdatefileMapper;
import com.ota.domain.FileMapping;
import com.ota.domain.FileMappingInfo;
import com.ota.domain.UpdateFileInfo;
import com.ota.domain.Updatefile;
import com.ota.domain.UpdatefileAndFilter;
import com.ota.tools.OTAToolKit;

@Controller
public class PatchHandler {

	@Autowired
	UpdatefileMapper upfm;
	@Autowired
	BusinessMapper businessMapper;
	@Autowired
	BoxTypeMapper boxTypeMapper;
	@Autowired
	FileMappingMapper fmm;
	@Autowired
	OTAGroupMapper otaGroupMapper;
	@Autowired
	private HttpSession session;

	// HttpServletRequest request = ((ServletRequestAttributes)
	// RequestContextHolder.getRequestAttributes()).getRequest();
	private String downloadUrl;
	private String fileMD5;
	private long size;

	@RequestMapping("patch-info")
	@ResponseBody
	public List<UpdateFileInfo> pathcInfo(HttpServletRequest request) {
		int offset = Integer.parseInt(request.getParameter("offset"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String customer = request.getParameter("customer");
		String model = request.getParameter("model");
		Integer customerid;
		Integer modelid;
		if (customer.equals("All Customer"))
			customerid = null;
		else {
			customerid = businessMapper.selectBusinessidByName(customer);
		}
		if (model.equals("All Model"))
			modelid = null;
		else {
			modelid = boxTypeMapper.selectModelIDByType(model);
		}
		
		List<UpdateFileInfo> updatefileInfoList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("customerid", customerid);
			map.put("modelid", modelid);
			PageBounds pageBounds = OTAToolKit.getPagerBoundsByParameter(pageSize, offset);
			updatefileInfoList = upfm.selectAllUpdateFileWithFilter(map, pageBounds);
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		return updatefileInfoList;
	}

	@RequestMapping("patch-detail")
	@ResponseBody
	public JSONObject patchDetail(@RequestBody JSONObject patchId) {
		JSONObject json = new JSONObject();
		int id = Integer.parseInt(patchId.getString("patchId"));
		Updatefile updatefile = upfm.selectByPrimaryKey(id);
		session.setAttribute("businessid", updatefile.getBusinessid());
		json.put("updatefile", updatefile);
		if (updatefile.getMappingid() != null
				&& updatefile.getMappingid().length() > 0) {
			String mappingid = updatefile.getMappingid();
			String[] multMapping = mappingid.split(",");
			List<FileMappingInfo> filemapping = new ArrayList<FileMappingInfo>();
			for (int i = 0; i < multMapping.length; i++) {
				FileMapping tempF = fmm.selectByPrimaryKey(Integer
						.parseInt(multMapping[i]));
				Integer modelid = tempF.getModelid();
				String model = boxTypeMapper.selectByPrimaryKey(modelid).getType();
				FileMappingInfo fmi = new FileMappingInfo();
				fmi.setAppversion(tempF.getAppversion());
				fmi.setBusinessid(tempF.getBusinessid());
				fmi.setDvbsupport(tempF.getDvbsupport());
				fmi.setHardwareversion(tempF.getHardwareversion());
				fmi.setHomeui(tempF.getHomeui());
				fmi.setId(tempF.getId());
				fmi.setIpbegin(tempF.getIpbegin());
				fmi.setIpend(tempF.getIpend());
				fmi.setIsCompulsive(tempF.getIsCompulsive());
				fmi.setManufacturer(tempF.getManufacturer());
				fmi.setManufacturer(tempF.getManufacturer());
				fmi.setModel(model);
				fmi.setRomVersion(tempF.getRomVersion());
				fmi.setUserid(tempF.getUserid());
				
				filemapping.add(fmi);
			}

			json.put("filemapping", filemapping);
		}

		return json;
	}

	@RequestMapping("deleteFile")
	@ResponseBody
	public JSONObject deleteFile(@RequestBody JSONObject id) {
		JSONObject json = new JSONObject();
		System.out.println("id:" + id.getString("id"));
		int deleteid = Integer.parseInt(id.getString("id"));

		try {
			Updatefile updatefile = upfm.selectByPrimaryKey(deleteid);
			String mappingid = updatefile.getMappingid();
			if (mappingid != null && mappingid.length() > 0) {
				String[] deletefilteridArrary = mappingid.split(",");
				// 逐条删除updatefile 对应的filter记录
				for (String deletefilterid : deletefilteridArrary) {
					fmm.deleteByPrimaryKey(Integer.parseInt(deletefilterid));
				}
			}
			upfm.deleteByPrimaryKey(deleteid);
			json.put("id", deleteid);
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("deleteFilter")
	@ResponseBody
	public JSONObject deleteFilter(@RequestBody JSONObject id) {
		JSONObject json = new JSONObject();
		int deleteid = Integer.parseInt(id.getString("id"));
		int fileid = Integer.parseInt(id.getString("fileid"));

		try {
			fmm.deleteByPrimaryKey(deleteid);
			Updatefile updatefile = upfm.selectByPrimaryKey(fileid);
			String mappingid = updatefile.getMappingid();
			// mappingid 一定存在 否则 业务逻辑异常或数据库中的数据错误
			mappingid = OTAToolKit.deleteFilterIdInFile(mappingid,
					id.getString("id"));
			updatefile.setMappingid(mappingid);
			upfm.updateByPrimaryKeySelective(updatefile);

			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("patchAddFilter")
	@ResponseBody
	public JSONObject patchAddFilter(
			@RequestBody UpdatefileAndFilter updatefileAndFilter) {
		System.out.println("addfilter:session.getAttribute(\"businessid\"): "
				+ session.getAttribute("businessid"));
		JSONObject json = new JSONObject();

		Updatefile upfFromWeb = updatefileAndFilter.getUpdateFile();
		Integer modelid = boxTypeMapper.selectModelIDByType(updatefileAndFilter.getType());
		FileMapping filemapping = updatefileAndFilter.getFileMapping(modelid);

		try {
			filemapping.setBusinessid((String) session
					.getAttribute("businessid"));
			// autoID 是插入新增filemapping后 由数据库返回的自增id 但是返回的一直是1 ，于是没有使用
			// ，又做了一次取id的操作
			// 有待优化的sql问题
			int autoID = fmm.insertSelective(filemapping);
			System.out.println(autoID);
			int id = fmm.selectlastid();
			String mappingid = null;
			if (upfm.selectByPrimaryKey(upfFromWeb.getId()).getMappingid() == null
					|| upfm.selectByPrimaryKey(upfFromWeb.getId())
							.getMappingid().length() <= 0) {
				mappingid = "" + id;

			} else {
				mappingid = upfm.selectByPrimaryKey(upfFromWeb.getId())
						.getMappingid();
				mappingid = mappingid + "," + id;
			}

			upfFromWeb.setMappingid(mappingid);
			//upfFromWeb.setBusinessid((String) session.getAttribute("businessid"));
			upfm.updateByPrimaryKeySelective(upfFromWeb);

			json.put("code", "1");
			json.put("msg", "success");
			json.put("id", id);
		} catch (Exception e) {
			json.put("code", "-1");
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("patchUpdateFilter")
	@ResponseBody
	public JSONObject patchUpdateFilter(
			@RequestBody UpdatefileAndFilter updatefileAndFilter) {
		JSONObject json = new JSONObject();
		Updatefile upfFromWeb = updatefileAndFilter.getUpdateFile();
		Integer modelid = boxTypeMapper.selectModelIDByType(updatefileAndFilter.getType());
		FileMapping filemapping = updatefileAndFilter.getFileMapping(modelid);

		try {
			// 更新
			filemapping.setId(Integer.parseInt(upfFromWeb.getMappingid()));
			fmm.updateByPrimaryKeySelective(filemapping);
			json.put("code", "1");
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", "-1");
			json.put("msg", "failed");
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("addupdateFile")
	@ResponseBody
	public JSONObject addupdateFile(@RequestBody UpdateFileInfo updateFileInfo) {
		JSONObject json = new JSONObject();
		Updatefile updatefile = new Updatefile();
		Integer customerid = businessMapper.selectBusinessidByName(updateFileInfo.getName());
		Integer groupid = otaGroupMapper.selectGroupIDByGroupName(updateFileInfo.getGroupname());
		Integer modelid = boxTypeMapper.selectModelIDByType(updateFileInfo.getType());
		String fileVersion = updateFileInfo.getFileVersion();
		String description = updateFileInfo.getDescription();
		
		try {
			updatefile.setBusinessid(customerid+"");
			updatefile.setFileVersion(fileVersion);
			updatefile.setDescription(description);
			updatefile.setGroupid(groupid);
			updatefile.setModelid(modelid);
			updatefile.setDownload(downloadUrl);
			updatefile.setMd5(fileMD5);
			updatefile.setSize((int) size);
			System.out.println(fileMD5 + " " + size);
			updatefile.setUpdateTime(new Date());
			int autoid = upfm.insertAndGetId(updatefile);
			int id = upfm.selectlastid();
			json.put("code", 1);
			json.put("msg", "success");
			json.put("id", id);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", -1);
		}

		return json;
	}

	@RequestMapping("uploadupdatefile")
	@ResponseBody
	public JSONObject uploadupdatefile(HttpServletRequest req,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		JSONObject json = new JSONObject();
		System.out.println("开始");
		String path = req.getSession().getServletContext()
				.getRealPath("upload");
		String businessName = (String) session.getAttribute("business_name");
		Properties prop = System.getProperties();
		String osname = prop.getProperty("os.name");
		if (osname.startsWith("win") || osname.startsWith("Win")) {
			path = path + "\\" + businessName;
		} else {
			path = path + "/" + businessName;
		}
		String fileName = OTAToolKit.addTimeStamp(file.getOriginalFilename());
		// /ota/src/main/webapp/upload
		System.out.println("path:" + path);
		System.out.println("fileName:" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
			fileMD5 = OTAToolKit.getMd5ByFile(targetFile);
			size = targetFile.length();
			if (osname.startsWith("win") || osname.startsWith("Win")) {
				downloadUrl = "upload\\" + businessName + "\\" + fileName;
			} else {
				downloadUrl = "upload/" + businessName + "/" + fileName;
			}
			System.out.println(downloadUrl);
			json.put("msgcode", "1");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msgcode", "-1");
		}

		return json;
	}

	@RequestMapping("uploadlog")
	@ResponseBody
	public JSONObject uploadlog(HttpServletRequest req,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		String sn = req.getParameter("sn").toString();
		String date = req.getParameter("date").toString();
		JSONObject json = new JSONObject();
		if (sn.isEmpty() || date.isEmpty()) {
			json.put("code", "0");
			return json;
		}

		try {
			String path = req.getSession().getServletContext()
					.getRealPath("log");

			String fileName = file.getOriginalFilename();
			// /ota/src/main/webapp/log
			System.out.println("path:" + path);
			System.out.println("sn:" + sn);
			System.out.println("fileName:" + fileName);
			String[] split = fileName.split("_");
			String model = split[0];
			String version = split[1];
			String time = split[2];
			System.out.println("model:" + model);
			System.out.println("version:" + version);
			System.out.println("time:" + time);
			Properties prop = System.getProperties();
			String osname = prop.getProperty("os.name");
			if (osname.startsWith("win") || osname.startsWith("Win")) {
				path = path + "\\" + sn + "\\" + date;
			} else {
				path = path + "/" + sn+ "/" + date;
			}
			File targetFile = new File(path, getLogName(fileName));
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存

			file.transferTo(targetFile);
			json.put("code", "1");
		} catch (Exception e) {
			json.put("code", "0");
		}

		return json;
	}
	
	@RequestMapping("test")
	@ResponseBody
	public String test(HttpServletRequest req) {
		String aa = req.getParameter("aa");
		String bb = req.getParameter("bb");
		System.out.println(aa+":"+bb);
		return aa+":"+bb;
	}

	private String getLogName(String fileName) {
		int lastIndexOf = fileName.lastIndexOf(".");
		String subSequence = fileName.subSequence(0, lastIndexOf).toString();
		return subSequence + "_" + System.currentTimeMillis() + ".log";

	}
	
	 @RequestMapping(value="/upload", produces = "text/json;charset=UTF-8")
	    @ResponseBody
	    public String uploadFileHandler(HttpServletRequest request, @RequestParam("file") MultipartFile file){
	    	System.out.println("fileupload");
	        //上传文件每日单独保存
	        String path = request.getSession().getServletContext()
					.getRealPath("upload");
			String systemdate = OTAToolKit.getSystemDate();
			Properties prop = System.getProperties();
			String osname = prop.getProperty("os.name");
			if (osname.startsWith("win") || osname.startsWith("Win")) {
				path = path + "\\" + systemdate;
			} else {
				path = path + "/" + systemdate;
			}
			String fileName = OTAToolKit.addTimeStamp(file.getOriginalFilename());
			// /ota/src/main/webapp/upload
			System.out.println("path:" + path);
			System.out.println("fileName:" + fileName);
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}

			// 保存
			try {
				file.transferTo(targetFile);
				fileMD5 = OTAToolKit.getMd5ByFile(targetFile);
				size = targetFile.length();
				if (osname.startsWith("win") || osname.startsWith("Win")) {
					downloadUrl = "upload\\" + systemdate + "\\" + fileName;
				} else {
					downloadUrl = "upload/" + systemdate + "/" + fileName;
				}
				System.out.println(downloadUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return null;
	    }

}
