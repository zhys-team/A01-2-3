package com.invoice.util;

 
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
/**
 * @ClassName MultipartFileToFile
 * @Description MultipartFile转fie
 * @Author TongGuoBo
 * @Date 2019/6/19 13:48
 **/
@Slf4j
public class MultipartFileToFile {
 
    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {
 
        File toFile = null;
         {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }
 
    //获取流文件
    public static void inputStreamToFile(InputStream ins, File file) {
    	OutputStream os=null;
        try {
        	os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            log.info("inputStreamToFile转换完成＝＝＝＝");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				ins.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
 
    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
    if (file != null) {
        File del = new File(file.toURI());
        del.delete();
    }
}
}
