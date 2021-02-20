package com.invoice.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.fusesource.hawtbuf.ByteArrayInputStream;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Pdf2picDemo {
//    public static void main(String[] args){
//        //File file = new File("d:\\test.pdf");
//        try {
//        	pdfbox();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//			
//		}
//        System.out.println("wnabi");
//
//    }
    
    public static byte[] pdfbox(File pdf_file) throws IOException {
    	FileInputStream fileInputStream = null;
    	PDDocument doc = null;
    	ByteArrayOutputStream os = null;
    	try {
    		 long start = System.currentTimeMillis();
    	        //pdf路径
//    	        File file = new File("d:\\test.pdf");
    		 fileInputStream = new FileInputStream(pdf_file); 
    	        // 加载解析PDF文件
    	        doc = PDDocument.load(fileInputStream);
    	        PDFRenderer pdfRenderer = new PDFRenderer(doc);
    	        PDPageTree pages = doc.getPages();
    	        int pageCount = pages.getCount();
    	        
    	            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 200);
    	            os  = new ByteArrayOutputStream();
    	            ImageIO.write(bim, "jpg", os);
    	            byte[] datas = os.toByteArray();
    	            return datas;
//    	            InputStream is = new ByteArrayInputStream(datas);
//    	            //jpg文件转出路径
//    	            MultipartFileToFile.inputStreamToFile(is, new File("d:/" + i + ".jpg"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("pdf转换成jpg异常{}",e.getMessage());
			return null;
		}finally {
			try {
				if(fileInputStream!=null) {
					fileInputStream.close();
				}
				if(doc!=null) {
					doc.close();
				}
				if(os!=null) {
					os.close();
				}
				
				
			} catch (Exception e2) {
				e2.printStackTrace();
				log.error("关闭pdfbox方法中的流异常{}",e2.getMessage());
			}
		}
       
        
    }

    
    public static String getMsg(File file) {
    	PDDocument doc = null;
    	try {
    		log.info("pdf:getmsg＝＝＝");
             doc = PDDocument.load(file);
             log.info("pdf:getmsg加载＝＝＝");
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            log.info("pdf:getmsg个数＝＝＝");
           
            	log.info("pdf:getmsg 开始获取＝＝＝");
                // 方式1,第二个参数是设置缩放比(即像素)
                BufferedImage image = renderer.renderImageWithDPI(0, 130, ImageType.RGB);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

                HashMap hints = new HashMap();
                hints.put(DecodeHintType.CHARACTER_SET, "utf-8");//设置字符集
                hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//                hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
                //获取二维码的内容
                try {
                	log.info("pdf:getmsg读取＝＝＝");
					String result = new QRCodeReader().decode(bitmap, hints).getText();
					
					System.out.println("二维码内容:"+result);
					log.info("二维码内容:"+result);
					return result;
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("pdf1:"+e.getMessage());
				} catch (ChecksumException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("pdf2:"+e.getMessage());
				} catch (FormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("pdf3:"+e.getMessage());
				}
            
        } catch (IOException e) {
            e.printStackTrace();
            log.info("pdf4:"+e.getMessage());
        }finally {
			try {
				if(doc!=null) {
					doc.close();
				}
				
			} catch (Exception e2) {
				e2.printStackTrace();
				log.error("关闭流异常"+e2.getMessage());
			}
		}
    	return null;
    }
    
    /**
     * 识别二维码
     */
    public static String QRReader(File file) throws IOException, NotFoundException {
        MultiFormatReader formatReader = new MultiFormatReader();
        //读取指定的二维码文件
        BufferedImage bufferedImage =ImageIO.read(file);
        BinaryBitmap binaryBitmap= new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        //定义二维码参数
        Map  hints= new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);
        //输出相关的二维码信息
        System.out.println("解析结果："+result.toString());
        System.out.println("二维码格式类型："+result.getBarcodeFormat());
        System.out.println("二维码文本内容："+result.getText());
        bufferedImage.flush();
        return result.toString();
    }
    
    public static void main(String[] args) {
    		getMsg(new File("D:/333.pdf"));
	}
    
}
