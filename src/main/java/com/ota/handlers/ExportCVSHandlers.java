package com.ota.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.BoxMapper;
import com.ota.dao.BoxTypeMapper;
import com.ota.dao.OTAGroupMapper;
import com.ota.domain.Box;
import com.ota.domain.OTAGroup;
import com.ota.tools.ExcelUtil;

@Controller
public class ExportCVSHandlers {
	@Autowired
	HttpSession session;
	@Autowired
	private BoxMapper boxMapper;
	@Autowired
	private BoxTypeMapper boxTypeMapper;
	@Autowired
	private OTAGroupMapper otagroupMapper;

	@RequestMapping(value = "exportcvs", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject otalogRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = new JSONObject();
		try {
			List<Box> boxList = boxMapper.selectBox();
			System.out.println(boxList.size());
			String filename = "BoxCVS";
			List<Map<String, Object>> list = createExcelRecord(boxList);
			String keys[] = { "Mac", "Userid", "Businessid", "Type", "Appversion", "Romversion", "Hardwareversion",
					"Manufacturer", "Dvbsupport", "Homeui", "Group", "OnlineTime" };// map中的key
			String columnNames[] = { "Mac", "Userid", "Businessid", "Type", "Appversion", "Romversion",
					"Hardwareversion", "Manufacturer", "Dvbsupport", "Homeui", "Group", "OnlineTime" };// 列名
			downloadExcelMethod(response, filename, list, keys, columnNames);

			jsonObject.put("code", "1");
			jsonObject.put("msg", "success!");
		} catch (Exception exception) {
			exception.printStackTrace();
			jsonObject.put("code", "-1");
			jsonObject.put("msg", "error in download CVS!");

		}
		return jsonObject;
	}

	private List<Map<String, Object>> createExcelRecord(List<Box> projects) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "deviceinfo");
		listmap.add(map);
		// 导出费用管理表
		Box box = null;
		for (int j = 0; j < projects.size(); j++) {
			box = projects.get(j);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
			String onlineTime = null;
			if (box.getOnlinetime() == null) {
				onlineTime = "";
			} else {
				onlineTime = format.format(box.getOnlinetime());
			}
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("Mac", box.getMac());
			mapValue.put("Userid", box.getUserid());
			mapValue.put("Businessid", box.getBusinessid());
			if ((box.getModelid() + "").equals("") || box.getModelid() == null) {
				mapValue.put("Type", "");
			} else {
				mapValue.put("Type", boxTypeMapper.selectByPrimaryKey(box.getModelid()).getType());
			}
			mapValue.put("Appversion", box.getAppversion());
			mapValue.put("Romversion", box.getRomversion());
			mapValue.put("Hardwareversion", box.getHardwareversion());
			mapValue.put("Manufacturer", box.getManufacturer());
			mapValue.put("Dvbsupport", box.getDvbsupport());
			mapValue.put("Homeui", box.getHomeui());
			if ((box.getGroupid() + "").equals("") || box.getGroupid() == null) {
				mapValue.put("Group", "");
			} else {
				OTAGroup ota_ = otagroupMapper.selectByPrimaryKey(box.getGroupid());
				if(ota_ == null) {
					mapValue.put("Group", "");
				} else {
					mapValue.put("Group", ota_.getGroupname());
				}
			}
			mapValue.put("OnlineTime", onlineTime);
			listmap.add(mapValue);
		}
		return listmap;
	}

	private void downloadExcelMethod(HttpServletResponse response, String fileName, List<Map<String, Object>> list,
			String[] keys, String[] columnNames) throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));// "iso-8859-1"
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
}
