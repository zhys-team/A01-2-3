package com.zhys.util;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageCut {
	// ===源图片路径名称如:c:\1.jpg
	private String srcpath;

	// ===剪切图片存放路径名称.如:c:\2.jpg
	private String subpath;

	// ===剪切点x坐标
	private int x;

	private int y;

	// ===剪切点宽度
	private int width;

	private int height;


	/**
	 * @param srcImage
	 *            需要裁减的源图片
	 * @param outImage
	 *            裁减后的图片
	 * @param x
	 *            图片x开始像素(注意是像素为单位)
	 * @param y
	 *            图片y开始像素
	 * @param width
	 *            图片像素宽度
	 * @param height
	 *            图片像素高度
	 */
	public ImageCut(String srcImage, String outImage, int x, int y, int width,
			int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.srcpath = srcImage;
		this.subpath = outImage;
	}
	
	
	
	public static void main(String[] args) {
		ImageCut ic = new ImageCut("D:\\20190520164107.jpg","D:\\20190520164108.jpg",0,0,1080,1920);
		ic.startCut();
	}
	
	public void startCut() {
		try {
			cut();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cutImag() {

		Image croppedImage;
		// Image sourceImage = new Image();
		ImageFilter cropFilter;

		cropFilter = new CropImageFilter(25, 30, 75, 75);// 四个参数分别为图像起点坐标和宽高，即CropImageFilter(int
															// x,int y,int
															// width,int
															// height)，详细情况请参考API
		// croppedImage= Toolkit.getDefaultToolkit().createImage(new
		// FilteredImageSource(sourceImage.getSource(),cropFilter));
		// 如果是在Component的子类中使用，可以将上面的Toolkit.getDefaultToolkit().去掉。
		// FilteredImageSource是一个ImageProducer对象。

	}

	/**
	 * 对图片裁剪，并把裁剪完蛋新图片保存 。
	 * 
	 */
	private void cut() throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;

		try {
			// 读取图片文件
			is = new FileInputStream(srcpath);

			/*******************************************************************
			 * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。
			 * 参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "gif"）等 。
			 */
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName("jpg");
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);

			/*******************************************************************
			 * <p>
			 * iis:读取源.true:只向前搜索
			 * </p>
			 * .将它标记为 ‘只向前搜索’。 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
			 * 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			 */
			reader.setInput(iis, true);

			/**
			 * <p>
			 * 描述如何对流进行解码的类
			 * <p>
			 * .用于指定如何在输入时从 Java Image I/O 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其
			 * ImageReader 实现的 getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			 */
			ImageReadParam param = reader.getDefaultReadParam();

			/*******************************************************************
			 * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
			 * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			 */
			Rectangle rect = new Rectangle(x, y, width, height);

			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);

			/*******************************************************************
			 * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的
			 * BufferedImage 返回。
			 */
			BufferedImage bi = reader.read(0, param);

			// 保存新图片
			ImageIO.write(bi, "jpg", new File(subpath));
		}

		finally {
			if (is != null)
				is.close();
			if (iis != null)
				iis.close();
		}

	}
}