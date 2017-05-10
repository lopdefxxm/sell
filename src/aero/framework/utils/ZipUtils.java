package aero.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.stereotype.Component;

@Component
public class ZipUtils {
	PathNameService mPathName = new PathNameService("zipPath");

	/**
	 * 压缩文件夹
	 * 
	 * @author Taoyuqiang
	 * @param zipOut
	 * @param folder
	 * @param base
	 * @throws Exception
	 */
	public void zip(ZipOutputStream zipOut, File folder, String base)
			throws Exception {
		if (folder.isDirectory()) {
			File[] fileList = folder.listFiles();
			for (File file : fileList) {
				zip(zipOut, file, base + file.getName());
			}
		} else {
			zipOut.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(folder);
			byte[] b = new byte[10240];
			int len;
			while ((len = in.read(b)) != -1) {
				zipOut.write(b, 0, len);
			}
			zipOut.flush();
			in.close();
		}
	}

	/**
	 * 按文件数组进行压缩
	 * 
	 * @param zipOut
	 * @param Files
	 * @param base
	 * @throws Exception
	 */
	public void zipFiles(ZipOutputStream zipOut, File[] Files, String base)
			throws Exception {
		for (File mFile : Files) {
			if (mFile.isDirectory()) {
				throw new Exception("包含非文件,请检查");
			} else {
				zipOut.putNextEntry(new ZipEntry(mFile.getName()));
				FileInputStream in = new FileInputStream(mFile);
				byte[] b = new byte[10240];
				int len;
				while ((len = in.read(b)) != -1) {
					zipOut.write(b, 0, len);
				}
				zipOut.flush();
				in.close();
			}
		}

	}

	/**
	 * 将制定的文件目录进行压缩
	 * @param folder
	 * @return 压缩后的文件对象
	 * @throws Exception
	 */
	public File doZip(File folder) throws Exception {
		// 压缩文件
		String zipFileName = folder.getCanonicalPath() + ".zip"; // 打包后文件名字
		File zipFile = new File(zipFileName);
		if (zipFile.exists())
			zipFile.createNewFile();
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
				zipFile));
		zipOut.setEncoding("GBK");// 支持GBK中文编码
		zip(zipOut, folder, "");
		zipOut.close();
		//压缩完成之后，返回文件对象
//		return mPathName.getResTypeAddr("http://localhost:8080/sell");
		return zipFile;

	}

	
	public File doZip(File[] Files, String customName) throws Exception {

		// 压缩文件
		String zipFileName = mPathName.getAbsFilePath()
				+ mPathName.getFileName(customName, mPathName.getAbsFilePath())
				+ ".zip"; // 打包后文件名字
		File zipFile = new File(zipFileName);
		if (zipFile.exists())
			zipFile.createNewFile();
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(
				zipFile));
		zipOut.setEncoding("GBK");// 支持GBK中文编码
		zipFiles(zipOut, Files, "");
		zipOut.close();
//		return mPathName.getResTypeAddr("http://localhost:8080/sell") + ".zip";
		return zipFile;

	}

}
