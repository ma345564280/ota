package com.ota.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.ota.dao.BurnMacSNMapper;
import com.ota.domain.BurnMacSN;
import com.ota.tools.OTAToolKit;

@Controller
public class BurnMacSNHandlers {
	@Autowired
	BurnMacSNMapper burnMacSNMapper;

	@RequestMapping("burnmacsn-info")
	@ResponseBody
	public Map<String, Object> burnMacSNInfo(HttpServletRequest request) {
		int offset = Integer.parseInt(request.getParameter("offset"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String status = request.getParameter("status");
		if (status.equals("All Status"))
			status = null;
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("status: " + status);
		map.put("status", status);
		try {
			PageBounds pageBounds = OTAToolKit.getPagerBoundsByParameter(pageSize, offset);
			List<BurnMacSN> burnMacSNs;
			int total;
			
			burnMacSNs = burnMacSNMapper.selectAllWithFilter(map, pageBounds);
			total = burnMacSNMapper.countAllWithFilter(map, pageBounds);
			Map<String, Object> mapSumAndList = new HashMap<>();
			if (burnMacSNs != null) {
				mapSumAndList.put("total", total);
				mapSumAndList.put("rows", burnMacSNs);
				return mapSumAndList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("delete-burnmacsn")
	@ResponseBody
	public JSONObject deleteBox(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		Integer id = Integer.parseInt(jsonRec.getString("id"));
		try {
			burnMacSNMapper.deleteByPrimaryKey(id);
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("edit-burnmacsn")
	@ResponseBody
	public JSONObject editBox(@RequestBody BurnMacSN burnMacSN) {
		JSONObject json = new JSONObject();
		
		String updatetime = OTAToolKit.getSystemTime();
		burnMacSN.setUpdatetime(updatetime);
		
		try {
			burnMacSNMapper.updateByPrimaryKeySelective(burnMacSN);
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping("add-burnmacsn")
	@ResponseBody
	public JSONObject addBox(@RequestBody BurnMacSN burnMacSN) {
		JSONObject json = new JSONObject();
		
		String createtime = OTAToolKit.getSystemTime();
		burnMacSN.setCreatetime(createtime);
		
		try {
			burnMacSNMapper.insertSelective(burnMacSN);
			json.put("code", 1);
			json.put("msg", "success");
		} catch (Exception e) {
			json.put("code", -1);
			json.put("msg", "failed");
			e.printStackTrace();
		}
		return json;
	}

}

