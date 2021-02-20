package com.huisir.service;

import freemarker.template.utility.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("upload")
@Slf4j
public class UploadService {

    @Value("${file-addr}")
    private String file_addr;
    /**
     * 上传文件到指定位置
     * @param fileName
     * @return
     */
    @PostMapping("/upload")
    public void upload(@RequestParam("file") byte[] file,@RequestParam("fileName") String fileName){
        log.info("接收到图片地址："+fileName);
        String url =createDateDir(file_addr+File.separator);
        getFile(file,url,fileName);
    }


    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@RequestPart(value = "file") MultipartFile file,@RequestParam("fileName") String fileName) {
        log.info("接收到图片地址："+fileName);
        String filePath =createDateDir(file_addr+File.separator);//生成了目录

        File dir = new File(filePath);
        if (!dir.exists()) {//判断文件目录是否存在
            dir.mkdirs();
        }

        File target= new File(filePath + "\\" + fileName);
        try {
            copyBytes(file.getInputStream(), target, 1024);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public  static void copyBytes(InputStream inputStream , File to,int byteSize) throws IOException{
        long start = System.currentTimeMillis();
        if(!to.exists()){
            //不存在这个文件或者目录
            createNewFile(to);
        }else if(to.isDirectory()){
            //如果存在，但是它是目录
            createNewFile(to);
        }else{
            //如果存在，但是它是文件
        }
        BufferedInputStream reader = new BufferedInputStream(inputStream);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(to));
        double begin=(double)reader.available();//获取可用字节

        //byte[]数组的大小，根据复制文件的大小可以调整，1G一下可以5M。1G以上150M，自己多试试
        byte[] b=new byte[byteSize];
        int len=0;
        String progress=null;
        while((len=reader.read(b))!=-1){
            writer.write(b,0,len);
            writer.flush();
            //显示进度
            if(!String.format("%.2f",(1-reader.available()/begin)*100).equals(progress)){
                progress=String.format("%.2f",(1-reader.available()/begin)*100);
                System.out.println("progress:"+progress+"%");
            }

        }
        reader.close();
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println("time consuming:"+(end-start)+"ms");
    }

    //递归创建文件
    public static void createNewFile(File file) throws IOException{
        if(file.exists()){
            System.out.println("the file or directory has existed!pass...");
            return;
        }
        //说明不存在这个文件或者目录
        String path=file.getAbsolutePath();
        String dirs=path.substring(0,path.lastIndexOf("\\"));
        //查看目录是否存在，没有就创建一个
        File dirFile = new File(dirs);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        file.createNewFile();
    }








    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传照片失败："+e.getMessage());
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    log.error("上传照片失败："+e1.getMessage());
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                    log.error("上传照片失败："+e1.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        File dir = new File("d:\\imagetest\\2020\\10\\11\\1602415620703_2227.png");

    }

    public  String  createDateDir(String path) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayStr = sdf.format(new Date());
        String[] dayArr = dayStr.split("-");

        String year = dayArr[0];
        String month = dayArr[1];
        String day = dayArr[2];

        String yearDir = path+year;


        String monthDir = yearDir + File.separator + month;


        String dayDir = monthDir + File.separator + day;

        System.out.println(dayDir);
        log.info("异步上传图片地址>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+dayDir);
        return dayDir;

    }

}