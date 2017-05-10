/**
 * 默认的文件生成和下载ajax
 * 传输参数
 * 		datas 当前grid绑定的数据集
 * 		columns 表头信息[code,name,mapping]
 */
var ajaxAction = new dorado.widget.AjaxAction();
var service = "exportExcelService#generateExcel";

function doExcel(view,gridid){
	var parameter = getExcelData(view,gridid);
	ajaxAction.set("service",service);
	ajaxAction.set("parameter",parameter);
	ajaxAction.execute(function(fileUrl){
		window.location.href=fileUrl;
	});
	
}


function getExcelData(view,gridid){
	var grid = view.id(gridid);
	var dataSet = grid.get("dataSet");
	var dataPath = grid.get("dataPath");//"undefine"
	var dataType = dataSet.get("dataType");
	var dataColumns = grid.get("dataColumns");
	var columns = [];
	dataColumns.each(function(column){
		var columnPro = column.get("property");
		if(columnPro!="none") {
			var propertyDef = dataType.getPropertyDef(columnPro);
			var label = propertyDef.get("label");
			var mapping = propertyDef.get("mapping");
			var c = {
					code:columnPro,
					name:label,
					mapping:mapping
			};
		columns.push(c);
		}
	});
	var parameter = {
		datas:dataSet.getData(dataPath||null),
		columns:columns
	};
	return parameter;
}