package com.ota.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ota.dao.BoxTypeMapper;
import com.ota.domain.BoxType;

@Controller
public class productHandlers {

	@Autowired
	BoxTypeMapper boxTypeMapper;
	
	@RequestMapping("product-info")
	@ResponseBody
	public List<BoxType> productInfo() {
		List<BoxType> boxTypes = boxTypeMapper.selectAllType();
		return boxTypes;
	}

	@RequestMapping(value = "check-newproduct")
	@ResponseBody
	public JSONObject checkNewGroup(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		String type = jsonRec.getString("type");
		String pid = jsonRec.getString("pid");
		String description = jsonRec.getString("description");
		String statusString = jsonRec.getString("status");
		System.out.println("statusString: "+statusString);
		int status = 0;
		// open是1close是0
		if (statusString.equals("Open"))
			status = 1;

		Map<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("pid", pid);
		int count = boxTypeMapper.checkNewProduct(map);
		System.out.println(count);
		if (count == 0) {
			json.put("code", "1");
		} else {
			json.put("code", "-1");
		}
		return json;
	}

	@RequestMapping(value = "add-product")
	@ResponseBody
	public JSONObject addProduct(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		String type = jsonRec.getString("type");
		String pid = jsonRec.getString("pid");
		String description = jsonRec.getString("description");
		String statusString = jsonRec.getString("status");
		System.out.println("statusString: "+statusString);
		int status = 0;
		// open是1close是0
		if (statusString.equals("Open"))
			status = 1;
		BoxType boxType = new BoxType();
		boxType.setType(type);
		boxType.setPid(pid);
		boxType.setDescription(description);
		boxType.setStatus(status);
		try {
			boxTypeMapper.insertSelective(boxType);
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "update-product")
	@ResponseBody
	public JSONObject editProduct(@RequestBody JSONObject jsonRec) {
		JSONObject json = new JSONObject();
		int id=jsonRec.getIntValue("id");
		int id2=jsonRec.getInteger("id");
		System.out.println("id1: " + id);
		System.out.println("id: " + id2);
		String type = jsonRec.getString("type");
		String pid = jsonRec.getString("pid");
		String description = jsonRec.getString("description");
		String statusString = jsonRec.getString("status");
		System.out.println("statusString: "+statusString);
		int status = 0;
		// open是1close是0
		if (statusString.equals("Open"))
			status = 1;
		BoxType boxType = new BoxType();
		boxType.setId(id);
		boxType.setType(type);
		boxType.setPid(pid);
		boxType.setDescription(description);
		boxType.setStatus(status);
		try {
			boxTypeMapper.updateByPrimaryKeySelective(boxType);
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "delete-product")
	@ResponseBody
	public JSONObject deleteProduct(@RequestBody BoxType boxType) {
		JSONObject json = new JSONObject();
		try {
			boxTypeMapper.deleteByPrimaryKey(boxType.getId());
			json.put("code", "1");
		} catch (Exception exception) {
			json.put("code", "-1");
			exception.printStackTrace();
		}
		return json;
	}
}
