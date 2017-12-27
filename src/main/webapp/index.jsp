<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'index.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/echarts.common.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#scan").click(
			function() {
				var url = "getCabbageList.action";

				$.ajax({
					url : url,
					type : "post",
					dataType : "json",
					success : function(jsonData) {

						var mainAry = new Array(jsonData.length);
						var avgAry = new Array(jsonData.length);
						var maxAry = new Array(jsonData.length);
						var xAry = new Array(jsonData.length);



						for (var i = 0; i < jsonData.length; i++) {
							
							
							mainAry[i] = jsonData[i].mainprice;
							avgAry[i] = jsonData[i].avgprice;
							maxAry[i] = jsonData[i].maxprice;
							xAry[i] = jsonData[i].publishtime;
						}

						var chart = echarts.init(document.getElementById("box"));

						chart.setOption({
							xAxis : {
								data : xAry
							},
							series : [
								{
									name : '最低价格',
									type : 'line',
									data : mainAry
								},
								{
									name : '平均价格',
									type : 'line',
									data :avgAry
								},
								{
									name : '最高价格',
									type : 'line',
									data : maxAry
								}

							]
						})

					}
				})


			}

		)

		var mychart = echarts.init(document.getElementById("box"));

		mychart.setOption({
			title : {
				text : "大白菜11月价格走势图",
				left:'center',
				
			},
			tooltip:{},
			legend : {
				data : [ '最低价格', '平均价格', '最高价格' ],
				top:25
			},
			xAxis : {
				data : []
			},
			yAxis : {

			},

			series : [
				{
					name : '最低价格',
					type : 'line',
					data : []
				},
				{
					name : '平均价格',
					type : 'line',
					data : []
				},
				{
					name : '最高价格',
					type : 'line',
					data : []
				}

			]
		})



	})
	
	
</script>
	<style type="text/css">
	#scan{
		height: 50px;
		line-height:50px;
		width: 200px;
		background: orange;
		color: white;
		border-radius: 5px;
	}
	</style>
</head>

<body>
	<div align="center">
		<p>第三天作业</p>
		<p>
			<a href="dataImport.action">抓取数据</a> 
		</p>
		<p id="scan">查看大白菜月价格走势</p>
		<p><a href="produceImg.action">生成图表</a></p>
		<p><a href="producePdf.action">生成pdf</a></p>
	</div>

	<div id="box" style="height: 400px;width: 1600px;"></div>
</body>
</html>
