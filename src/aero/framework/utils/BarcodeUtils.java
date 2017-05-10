package aero.framework.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import aero.framework.mail.MailUtil;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.web.DoradoContext;

@Component
public class BarcodeUtils {
	@Resource
	ZipUtils zipUtils ;
	
	@Resource
	MailUtil mailUtil;
	
	PathNameService mPathName = new PathNameService("zipPath");
	/**
	 * 图片展现接口
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 */
	public void doDisplay(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		String strBarcode = String.valueOf(httpServletRequest
				.getParameter("Barcode"));
		Integer width = Integer.valueOf(httpServletRequest.getParameter("W"));
		Integer height = Integer.valueOf(httpServletRequest.getParameter("H"));
		Color bcolor = Color.WHITE;// 前景色
		Color fcolor = Color.BLUE;// 设置背景色
		BarcodeGenerateService service = new BarcodeGenerateService(strBarcode);
		BufferedImage localBufferedImage = service.getLocalBufferedImage();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			ImageIO.write(localBufferedImage, "jpeg", out);
		} catch (IOException e) {
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setFont(new Font("微软雅黑", Font.BOLD, 35));
			// 先画出背景色
			g.setColor(bcolor);
			g.fillRect(0, 0, width, height);
			// 再画出前景色
			g.setColor(fcolor);
			// 绘制随机字符
			g.drawString("暂无图片", width / 6, height / 2);
			g.dispose();
			ImageIO.write(image, "JPEG", httpServletResponse.getOutputStream());
		}

		byte[] data = out.toByteArray();

		if (width == -1 && height == -1) {
			OutputStream outputStream = httpServletResponse.getOutputStream();
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
		} else {
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(ImageIO.read(new ByteArrayInputStream(data)), 0, 0,
					width, height, null);
			ImageIO.write(image, "PNG", httpServletResponse.getOutputStream());
		}

	}

	@Expose
	public String outPutZipFile(List<Map<String,String>> paraInfos, String customName) throws Exception {
		if(paraInfos==null || paraInfos.isEmpty()){
			throw new Exception("选择商品为空！");
		}
		List<File> mFileList = new ArrayList<File>();
		for (Map<String, String> info : paraInfos) {
			String barCode = info.get("barcode");
			String name = info.get("name");
			BarcodeGenerateService service = new BarcodeGenerateService(
					barCode, 
					mPathName.getAbsFilePath()+ 
						mPathName.getFileName(name,mPathName.getAbsFilePath()) + ".jpg");
			mFileList.add(service.saveAsFile("jpeg"));
		}
		int size = mFileList.size();
		File[] arrfile = mFileList.toArray(new File[size]);
		File zipFile = zipUtils.doZip(arrfile, customName);
		
		HttpServletRequest request = DoradoContext.getAttachedRequest();
		String project = request.getContextPath();
		String url = request.getRequestURL().toString();
		String realPath = url.substring(0,url.indexOf(project)+project.length());
		return realPath + mPathName.getFilePath() +"/"+ zipFile.getName();

	}
	
	@SuppressWarnings("unchecked")
	@Expose
	public void mailZipFile(Map<String,Object> parameter) throws Exception {
		List<Map<String,String>> paraInfos = (List<Map<String,String>>)parameter.get("paraInfos");
		String customName = (String)parameter.get("customName");
		String address = (String)parameter.get("address");
		if(paraInfos==null || paraInfos.isEmpty()){
			throw new Exception("选择商品为空！");
		}
		List<File> mFileList = new ArrayList<File>();
		for (Map<String, String> info : paraInfos) {
			String barCode = info.get("barcode");
			String name = info.get("name");
			BarcodeGenerateService service = new BarcodeGenerateService(
					barCode, 
					mPathName.getAbsFilePath()+ 
						mPathName.getFileName(name,mPathName.getAbsFilePath()) + ".jpg");
			mFileList.add(service.saveAsFile("jpeg"));
		}
		int size = mFileList.size();
		File[] arrfile = mFileList.toArray(new File[size]);
		File zipFile = zipUtils.doZip(arrfile, customName);
		mailUtil.getMailTemplate().sendMail(address, zipFile);
	}
	
}
