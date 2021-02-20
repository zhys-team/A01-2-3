/**
 * 
 */
package com.zhys.util;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
 

/**
 * @author Administrator
 *
 */
@Slf4j
public class MarkUtils {
	
	public static MultipartFile rotateAndMark(InputStream file ,JSONArray ja ,int i,Boolean isRotale) {
		 log.info("进入圈定旋转方法");
		ByteArrayOutputStream baos =null; 
		MultipartFile multi =null;
		try { 
//			File root = new File("src/main/resources");
			log.info("获取水印图片");
			File shuiYin = ResourceUtils.getFile("classpath:shuiyin.png");
			
			if(!shuiYin.exists()) {
				log.info("水印图片不存在");
				throw new BusinessException("请在resource目录下放置水印图片，命名为shuiyin.png");
			}
	    	BufferedImage img = ImageIO.read(file); 
	    	//每张发票
	    	log.info("第"+(i+1)+"张发票");
	    	JSONObject  result = ja.getJSONObject(i);
	    	Integer angle  =  (Integer) result.get("orientation");
	    	Object regionObj = result.get("region");
	    	String regionStr =regionObj.toString();
	    	String[] arr = regionStr.substring(1, regionStr.length()-1).split(","); // 用,分割 
	    	log.info("角度:"+angle);
	    	log.info("区域:"+Arrays.toString(arr));
	    	int x =Integer.valueOf(arr[0]);
	    	int y =Integer.valueOf(arr[1]);
	    	int newWidth =Integer.valueOf(arr[2])-x;
	    	int newHeight =Integer.valueOf(arr[3])-y; 
	    	
	    	
	    	 JSONObject details = result.getJSONObject("details");
	    	 String code = details.getString("code");
	    	 String number = details.getString("number");
	    	 log.info("识别结果:"+code+"-"+number);
	    	 
	    	 if(StringUtils.isBlank(code)||StringUtils.isBlank(number)) {
	    		 log.info("识别不成功，所以图片不处理");
	    		  return null;
	    	 }
	    	if(isRotale==true) { 
	    		log.info("正在画画");
				BufferedImage imgShuiyin = ImageIO.read(shuiYin); 
		    	
		    	img.getGraphics().drawImage(imgShuiyin.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), x, y, newWidth, newHeight,null);
		    	
			}
	   	    
	    	
	    	
	    	 
	    	
	    	BufferedImage rotateImage = Thumbnails.of(img).scale(0.9).rotate(-angle).asBufferedImage();
	    	
//	    	int maxWidth = (img.getWidth()>img.getHeight())?img.getWidth():img.getHeight();
//	    	
//	    	BufferedImage roateImg = Thumbnails.of(img).size(maxWidth,maxWidth).rotate(angle).asBufferedImage();
//	    	  
//	    	
	    	log.info("图片旋转完毕:宽度"+rotateImage.getWidth()); 
	    	 
	    	 baos = new ByteArrayOutputStream();
	    	 ImageIO.write(rotateImage, "png", baos);  
	    	 
	    	// 转 MultipartFile
	 		 multi = new MockMultipartFile("file", baos.toByteArray());
//	    	 ImageIO.write(rotateImage, "png", new File(root,i+".png"));
	    	 
	 		log.info("图片处理完毕:");
	 		log.info("图片大小:"+baos.size());
	            
	    	 if(baos!=null) {
	    		 baos.flush();
		    	 baos.close();
	    	 }
	    	 
		} catch (Exception e) {
			e.printStackTrace();
			if(baos!=null) { 
		    	try {
					baos.close();
				} catch (IOException e1) { 
					e1.printStackTrace();
				}
	    	 } 
		}
		return multi;
		
		
//    	Position position = Positions.TOP_LEFT;
//		Thumbnails.of(file).size() 
//		.watermark(position , ImageIO.read(shuiYin), 0.7f)
//		.outputQuality(0.8f).toFile("C:\\Users\\Administrator\\Pictures\\wallpaper\\result.jpg");
//1.jpg是原图，2.jpg是水印图片，3.jpg是生成后的图片，watermark设置水印位置及大小
	}

 
   
	
	

}
