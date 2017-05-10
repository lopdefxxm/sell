package aero.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class PathNameService {
	String strKey;// Properties的文件中的键码
	String strFileName;

	/**
	 * 
	 * 
	 * @param strKey
	 *            system.properties文件中的键码
	 */
	public PathNameService(String strKey) {
		this.strKey = strKey;
	}

	/**
	 * 获取配置文件中定义的默认目录
	 * 
	 * @return
	 */
	public String getFilePath() {
		// 从配置中获取定义的默认相对路径
		Properties props = new Properties();
		InputStream in = ZipUtils.class
				.getResourceAsStream("../../../../../Custom-Configs/system.properties");
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String PathName = props.getProperty(strKey);
		return PathName;
	}

	/**
	 * 获取目录的绝对路径
	 * 
	 * @return
	 */
	public String getAbsFilePath() {
		// 从配置中获取定义的默认相对路径
		String PathName = getFilePath().replace("/", "");
		// 获取目录的绝对路径
		String strRootPath = System.getProperty("mmm.root");
		String strFilePath = strRootPath + PathName + File.separatorChar;
		return strFilePath;
	}

	/**
	 * 生成文件名
	 * 
	 * @author WangTianHai
	 * 
	 * @param strName
	 *            前台用户输入的文件名
	 * @param FilePath
	 *            文件路径
	 * @return
	 */
	public String getFileName(String strName, String FilePath) {
		String FileName = "";
		if (StringUtils.isNotEmpty(strName)) {// 文件名不为空的情况
			File f = new File(FilePath + File.separatorChar + strName);
			if (f.exists()) {// 如果文件名在此目录中已存在 则改成文件名加时间戳的格式
				java.util.Date date = new java.util.Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				FileName = strName + sdf.format(date);
			} else {
				FileName = strName;
			}
		} else {// 如果文件名为空 则默认时间戳为文件名
			java.util.Date date = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			FileName = sdf.format(date);
		}
		this.strFileName = FileName;
		return FileName;
	}

	/**
	 * 获取资源的访问地址
	 * 
	 * @param httpAddr
	 *            资源域名及文件夹如 "http://localhost:8080/sell"
	 * @return
	 */
	public String getResTypeAddr(String httpAddr) {
		return httpAddr + getFilePath() + "/"
				+ getFileName(this.strFileName, getAbsFilePath());
	}
}
