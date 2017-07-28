package com.ota.handlers;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
public class DownloadHandler {
	@RequestMapping("download")
	public ResponseEntity<byte[]> download() throws IOException {
		System.out.println("asdasdasd");

//@RequestMapping("/spring-web/{symbolicName:[a-z-]+}-{version:\d\.\d\.\d}.{extension:\.[a-z]}") 

		return null;
//		String path = "D:\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\springMVC\\WEB-INF\\upload\\图片10（定价后）.xlsx";
//		File file = new File(path);
//		HttpHeaders headers = new HttpHeaders();
//		String fileName = new String("你好.xlsx".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
//		headers.setContentDispositionFormData("attachment", fileName);
//		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

}
