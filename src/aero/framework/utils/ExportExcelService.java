package aero.framework.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.web.DoradoContext;

@Component
public class ExportExcelService {
	ExcelHeader mExcelHeader;
	ExcelData mExcelBody;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public File createExcelFile(Map<String, Object> mparameter) throws Exception {
		mExcelHeader = new ExcelHeader((List)mparameter.get("columns"));
		mExcelBody = new ExcelData((List)mparameter.get("datas"), mExcelHeader);

		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);
		// 创建Excel的sheet的一行
		// 创建一个Excel的单元格
		generateColHeader(sheet);
		generateData(sheet);
		String filePath = getAbsFilePath()+getFileName("",getAbsFilePath());
		File excelFile = outPutFile(wb,filePath);
		return excelFile;
	}
	
	public void doReportExcel(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		System.out.println(111);
	}
	
	@Expose
	public String generateExcel(Map<String, Object> mparameter) throws Exception{
		File excelFile = createExcelFile(mparameter);
		HttpServletRequest request = DoradoContext.getAttachedRequest();
		String project = request.getContextPath();
		String url = request.getRequestURL().toString();
		String realPath = url.substring(0,url.indexOf(project)+project.length());
		return realPath+getFilePath()+"/"+excelFile.getName();
	}

	@SuppressWarnings("deprecation")
	public void generateColHeader(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < mExcelHeader.getColSize(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(mExcelHeader.getValueByIndex(i));
		}

	}

	@SuppressWarnings("deprecation")
	public void generateData(HSSFSheet sheet) {
		for (int i = 0; i < mExcelBody.getDataSize(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			for (int j = 0; j < mExcelHeader.getColSize(); j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(GeneralTypeConvert.AnyToString(mExcelBody.getVlaueByPos(i, j)));
			}
		}

	}

	public File outPutFile(HSSFWorkbook wb, String path) throws Exception{
		FileOutputStream os;
		File file = new File(path);
		if(!file.exists()){
			file.createNewFile();
		}
		os = new FileOutputStream(path);
		wb.write(os);
		os.close();
		return file;

	}
	
	/**
	 * 获取配置文件中定义的默认目录
	 * 
	 * @return
	 */
	public String getFilePath() {
		//从配置中获取定义的默认相对路径
		Properties props = new Properties();
		InputStream in = ExportExcelService.class
				.getResourceAsStream("../../../../../Custom-Configs/system.properties");
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String key = "upload.path";
		String PathName = props.getProperty(key);
		return PathName;
	}

	/**
	 * 获取目录的绝对路径
	 * 
	 * @return
	 */
	public String getAbsFilePath() {
		//从配置中获取定义的默认相对路径
		String PathName = getFilePath().replace("/", "");
		//获取目录的绝对路径
		String strRootPath = System.getProperty("mmm.root");
		String strFilePath = strRootPath + PathName+ File.separatorChar;
		return strFilePath;
	}

	/**
	 * 生成文件名
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
		return FileName + ".xls";
	}

}
