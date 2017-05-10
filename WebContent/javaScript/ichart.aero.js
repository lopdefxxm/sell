var ajaxAction = new dorado.widget.AjaxAction();
var ajaxService = "IChartSupport#dataSupport";
/**
 * 生成通过条形图的方法
 * @param config
 * 	datas  使用dorado.entityList类型
 *  name   指定entity显示字段。
 *  value  指定entity值字段。
 * 	title  标题
 *  render 渲染div的ID值
 *  height 高度
 *  width  宽度
 */
function myChart_Column2D(config){
	if(!config.datas || config.datas.length == 0){
		return;
	}
	var data = getRightData(config);
	var title = config.title;
	title = title?title:"条形统计图";
	
	var render = config.render;
	render = render?render:"canvasDiv";
	
	var width = config.width;
	var height = config.height;
	var chart = new iChart.Column2D({
		animation:true,
		render : render,
		data: data,
		title : title,
		width : width,
		height : height,
		coordinate:{height:'90%',background_color:'#edf8fa'},
	});
	chart.draw();
}

function myChart_Column2D_NEW(config){
	ajaxAction.set("service",ajaxService);
	ajaxAction.set("async","false");
	ajaxAction.set("parameter",{
		name:config.name,
		value: config.value,
		table: config.table,
		condition:config.condition
	});
	ajaxAction.execute(function(data){
		if(!data || data.length == 0){
			return;
		}
		var title = config.title;
		title = title?title:"条形统计图";
		
		var render = config.render;
		render = render?render:"canvasDiv";
		
		var width = config.width;
		var height = config.height;
		var chart = new iChart.Column2D({
			animation:true,
			render : render,
			data: data,
			title : title,
			width : width,
			height : height,
			coordinate:{height:'90%',background_color:'#edf8fa'},
		});
		chart.draw();
	});
	
}

/**
 * 将entityList转换为ichart能识别的数组格式
 * @param datas
 */
function getRightData(config){
	var array = [];
	config.datas.each(function(data){
		var name = data.NAME || "未知";
		var value = data.VALUE;
		array.push({
			name:name,
			value:value,
			color:getColor(value)
		});
	});
	return array;
}

/**
 * 根据指定的value得到一个颜色的16位数值
 * 算法为：将数字转换为16位数值，若长度超过6位则截取前6位做为颜色值，否则进行参数的平方递归
 * @param num
 * @returns
 */
function getColor(num){
	var color = "ffffff";
	num = Math.floor(num);
	if(num && num!=1 && num!=-1){
		color = num.toString(16);
		if(color.length<6){
			return getColor(num*num);
		}
		color = color.substring(0,6);
	}
	return "#"+color;
}
