package aero.framework.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.view.widget.grid.Column;
import com.bstek.dorado.view.widget.grid.DataGrid;

@Component
public class ReportUtil {

	@Expose
	public void exportExcel(DataGrid dataGrid,List<Map<String,Object>> datas){
		List<Column> columns = dataGrid.getColumns();
		for(Column c :columns){
			c.getName();
		}
//		dataGrid.get
//		DoradoContext.get
	}
	
	public void doExport(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, RowsExceededException, WriteException{
		System.out.println(request.getParameterMap());
	}

	/**
	 * 方法描述: 标题
	 */
	private CellFormat getTitleStyle() throws WriteException{
		// 通过函数WritableFont（）设置字体样式  
	    // 第一个参数表示所选字体  
	    // 第二个参数表示字体大小  
	    // 第三个参数表示粗体样式，有BOLD和NORMAL两种样式  
	    // 第四个参数表示是否斜体,此处true表示为斜体  
	    // 第五个参数表示下划线样式  
	    // 第六个参数表示颜色样式， 
	    WritableFont wf = new WritableFont(WritableFont.ARIAL, 14,  
	            WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,  
	            Colour.BLACK);  
	    WritableCellFormat  cf = new WritableCellFormat(wf); 
	    cf.setAlignment(Alignment.CENTRE);
	    cf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    return cf;
	}
	
	/** 
	 * 方法描述: 表头
	 */ 
	private CellFormat getHeadStyle() throws WriteException{
	    WritableFont wf = new WritableFont(WritableFont.COURIER, 12,  
	            WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,  
	            Colour.BLACK);  
	    WritableCellFormat  cf = new WritableCellFormat(wf); 
	    cf.setAlignment(Alignment.CENTRE);
	    cf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    cf.setBorder(Border.ALL,BorderLineStyle.THIN);
	    return cf;
	}
	
	/** 
	 * 方法描述: 表体
	 * 参数：表体颜色，null表示为白色
	 */ 
	private CellFormat gethuizStyle(Colour colour) throws WriteException{
	    WritableFont wf = new WritableFont(WritableFont.ARIAL, 11,  
	            WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,  
	            Colour.BLACK);  
	    WritableCellFormat  cf = new WritableCellFormat(wf); 
	    cf.setAlignment(Alignment.CENTRE);
	    cf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    cf.setBorder(Border.ALL,BorderLineStyle.THIN);
	    if(colour==null){
	    	colour = Colour.WHITE;
	    }
	    cf.setBackground(colour);
	    return cf;
	}
}
