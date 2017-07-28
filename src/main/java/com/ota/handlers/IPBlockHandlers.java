package com.ota.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.IPBlockMapper;
import com.ota.domain.IPBlock;
import com.ota.tools.OTAToolKit;

@Controller
public class IPBlockHandlers {

	@Autowired
	IPBlockMapper ipBlockMapper;
	
	@RequestMapping("ipblock-info")
	@ResponseBody
	public List<IPBlock> ipblockInfo() {
		List<IPBlock> ipBlocks = ipBlockMapper.selectAllIPBlock();
		return ipBlocks;
	}

	@RequestMapping(value = "add-ipblock")
	@ResponseBody
	public JSONObject addIPBlock(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		String ipbegin = jsonRec.getString("ipbegin");
		String ipend = jsonRec.getString("ipend");
		String status = jsonRec.getString("status");
		IPBlock ipBlock = new IPBlock();
		ipBlock.setIpbegin(ipbegin);
		ipBlock.setIpend(ipend);
		ipBlock.setStatus(status);
		ipBlock.setCreatetime(OTAToolKit.getSystemTime());
		try {
			ipBlockMapper.insertSelective(ipBlock);
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "update-ipblock")
	@ResponseBody
	public JSONObject editIPBlock(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		int id=jsonRec.getIntValue("id");
		String ipbegin = jsonRec.getString("ipbegin");
		String ipend = jsonRec.getString("ipend");
		String status = jsonRec.getString("status");
		IPBlock ipBlock = new IPBlock();
		ipBlock.setIpbegin(ipbegin);
		ipBlock.setIpend(ipend);
		ipBlock.setStatus(status);
		ipBlock.setId(id);
		try {
			ipBlockMapper.updateByPrimaryKeySelective(ipBlock);
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "delete-ipblock")
	@ResponseBody
	public JSONObject deleteIPBlock(@RequestBody IPBlock ipBlock) {
		JSONObject json = new JSONObject();
		try {
			ipBlockMapper.deleteByPrimaryKey(ipBlock.getId());
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value = "close-ipblock")
	@ResponseBody
	public JSONObject closeIPBlock(@RequestBody IPBlock ipBlock) {
		JSONObject json = new JSONObject();
		ipBlock.setStatus("Close");
		try {
			ipBlockMapper.closeIPBlock(ipBlock);
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}
}
