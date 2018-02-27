package com.atguigu.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class MyUploadUtil {
	//上传图片  (路径--文件名)
	
	public static List<String> upload_image(MultipartFile[] files) {
		
		List<String>list_image = new ArrayList<String>();
		
		//获取图片要上传到的路径
		String path = MypropertyUtil.getMyProperty("upload.properties","window_path");
		
		
		
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isEmpty()) {
				//上传
				//获取唯一文件名
				//String string = UUID.randomUUID().toString();
				String file_name = System.currentTimeMillis()+files[i].getOriginalFilename();
				
				
				try {
					files[i].transferTo(new File(path+"/"+file_name));
					
					list_image.add(file_name);

				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			
			
		}
		
		
		return list_image;
	}

}
