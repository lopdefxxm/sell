package aero.framework.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.EAN8Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

public class BarcodeGenerateService {
	JBarcode localJBarcode;
	BufferedImage localBufferedImage;
	public BufferedImage getLocalBufferedImage() {
		return localBufferedImage;
	}

	public void setLocalBufferedImage(BufferedImage localBufferedImage) {
		this.localBufferedImage = localBufferedImage;
	}

	String strPath;

	/**
	 * 构造函数
	 * 
	 * @param str
	 *            条形码
	 * @author William
	 */
	public BarcodeGenerateService(String str) {
		this.localJBarcode = new JBarcode(EAN8Encoder.getInstance(),
				WidthCodedPainter.getInstance(), EAN8TextPainter.getInstance());
		try {
			this.localBufferedImage = localJBarcode.createBarcode(str);

		} catch (InvalidAtributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存成图片文件的构造函数
	 * 
	 * @param str
	 *            条形码
	 * @param strPath
	 *            路径（比如：D:\\img\aa.jepg）
	 * @author William
	 */
	public BarcodeGenerateService(String str, String strPath) {
		this.localJBarcode = new JBarcode(EAN8Encoder.getInstance(),
				WidthCodedPainter.getInstance(), EAN8TextPainter.getInstance());
		try {
			this.localBufferedImage = localJBarcode.createBarcode(str);
			this.strPath = strPath;
		} catch (InvalidAtributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存成图片文件
	 * 
	 * @author William
	 * @param paramString2
	 *            文件格式 jpeg、png、gif 三种格式
	 */
	public void saveToFile(String paramString2) {
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(
					strPath);
			ImageUtil.encodeAndWrite(localBufferedImage, paramString2,
					localFileOutputStream, 96, 96);
			localFileOutputStream.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}
	
	public File saveAsFile(String paramString2){
		File mfile = new File(strPath);
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(
					mfile);
			ImageUtil.encodeAndWrite(localBufferedImage, paramString2,
					localFileOutputStream, 96, 96);
			localFileOutputStream.close();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		
		return mfile;
	}
	

}
