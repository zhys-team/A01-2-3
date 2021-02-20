/**
 * 
 */
package top.anets.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.util.StreamUtils;
 

public class PDFUtils {
	public static void pdfTojpg(String filepath,String target,Double zoom) {
        File pdfFile = new File(filepath);
        String jpgPathFile = filepath.substring(0, filepath.lastIndexOf('.'));
        PDDocument pdDoc = null;
        List<File> files = new ArrayList<File>();
        try {
            if (pdfFile.exists()) {

                pdDoc = PDDocument.load(pdfFile);
                int pageCount = pdDoc.getNumberOfPages();
                List pages = pdDoc.getDocumentCatalog().getAllPages();
                for (int i = 0; i < 1; i++) {
                    PDPage page = (PDPage) pages.get(i);
                    BufferedImage image = page.convertToImage();  
                    
                    
                  //根据比例计算缩放图的比例
            		int newWidth=(int) Math.ceil((double)image.getWidth()/zoom);
                    int newHeight=(int) Math.ceil((double)image.getHeight()/zoom);
                    //BufferedImage.TYPE_INT_RGB是不带透明色，TYPE_INT_ARGB带透明色，
            		//根据宽高生成一个缓冲图像画布
                    BufferedImage canvas = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                    //得到画笔对象在画布上画原图的缩放图像
                    canvas.getGraphics().drawImage(image.getScaledInstance(newWidth,newHeight,image.SCALE_SMOOTH), 0, 0,null);                   
                   
                    
                    
                
                    try {
                        File file = new File(target);
                        ImageIO.write(canvas, "jpg", file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image.flush();
                }
            } 

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pdDoc != null) {
                    pdDoc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

 
	
    
    public static void main(String[] args) { 
    	pdfTojpg("C:\\Users\\Administrator\\Documents\\invoice\\202101\\07\\322223_555_333.pdf", 
    			"C:\\Users\\Administrator\\Documents\\invoice\\202101\\07\\565.jpg", 2d);
	}
}