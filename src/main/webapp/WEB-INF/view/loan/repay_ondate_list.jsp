<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="../include/include.jsp"%>
<%@ page session="false" pageEncoding="UTF-8"%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>loan 平台</title>
<link href="/resources/img/favicon.ico" rel="icon" />
<!-- 不知道干嘛的-->
<link href="/resources/css/bootstrap.min.css" rel="stylesheet" />
<link href="/resources/css/font-awesome.min.css?v=4.3.0"
	rel="stylesheet" />
<!-- jqGrid组件基础样式包-必要 -->
<link href="/resources/css/plugins/jqgrid/ui.jqgrid.css?0820"
	rel="stylesheet" />
<link href="/resources/css/animate.css" rel="stylesheet" />
<link href="/resources/css/style.css?v=3.2.0" rel="stylesheet" />

<style>
html,body { 
	margin: 0px;
	padding: 10px;
}

.mask_example {
	background-color: #aaa;
	position: fixed;
	z-index: 9999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	opacity: 0.5;
	display: none;
}

.mask_example .loading {
	position: fixed;
	left: 50%;
	top: 50%;
}
</style>
</head>
<body>
<div class="wrapper wrapper-content animated fadeInRight">
	<ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#">查询条件</a></li>
    </ul>
    
    <div class="row">
        <div class="col-sm-12">
        
            <!--查询条件-->
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">
                    <form role="form" class="form-inline" id="queryForm" method="post" 
                    	action="/repayOnDate/loadRepayOnDateInfoExcel.do">
                        <!--名称、编码和创建时间-->
                        <div class="form-group">
                            <label class="font-noraml">合同编号:</label>
                            <input id="contractId" class="form-control">
                        </div>
                        
                        <div class="form-group">
                            <label class="font-noraml">借款人名称:</label>
                            <input id="borrowerName" class="form-control">
                        </div>
                         <div class="form-group">
                            <label class="font-noraml">证件号:</label>
                            <input id="cardNum" class="form-control">
                        </div>
                        <div class="form-group">
                            <label class="font-noraml">产品类型:</label>
	                        <select id="productType" name="productType" class="form-control" style="width:120px;">
	                        	<option list="-1" value="-1">--请选择--</option>
	                            <option list="1" value="1">信贷</option>
                                <option list="2" value="2">车贷</option>
                               	<option list="3" value="3">房贷</option>
                                <option list="4" value="4">果满园</option>
                               	<option list="5" value="5">粮满仓</option>
	                        </select>
	                     </div>
                        <div class="form-group">
                            <label class="font-noraml">合同状态:</label>
                            <select id="contractStatus" name="contractStatus" class="form-control" style="width:120px;">
                            	<option list="-1" value="-1">--请选择--</option>
                            	<option list="1" value="1">正常还款中</option>
                                <option list="2" value="2">逾期</option>
                                <option list="3" value="3">欠违约金</option>
                                <option list="4" value="4">正常结清</option>
                                <option list="5" value="5">提前结清</option>
                                <option list="6" value="6">展期结清</option>
                                <option list="7" value="7">放弃</option>
                            </select>            
                        </div>
                        <div class="form-group">
                            <label class="font-noraml">还款状态:</label>
                        	<select id="repayStatus" name="repayStatus" class="form-control" style="width:120px;">
                        		<option list="-1" value="-1">--请选择--</option>
                            	<option list="1" value="1">未还款</option>
                                <option list="7" value="7">划款中</option>
                                <option list="5" value="5">已还款</option>
                            </select>            
                        </div>
                        <div class="form-group">
                            <label class="font-noraml">是否划扣:</label>
                        	<select id="idDeduct" name="idDeduct" class="form-control" style="width:120px;">
                        		<option list="-1" value="-1">--请选择--</option>
                            	<option list="1" value="1">是</option>
                                <option list="2" value="2">否</option>
                            	<option list="3" value="3">是(M)</option>
                                <option list="4" value="4">否(M)</option>
                            	<option list="5" value="5">是(网)</option>
                                <option list="6" value="6">否(网)</option>                                                                        
                            </select>            
                        </div>
                        <div class="form-group">
                            <label class="font-noraml">还款银行:</label>
                            <input id="repayBank" class="form-control">
                        </div>
                        <div class="form-group">
                            <label class="font-noraml" style="width:110px;">应还款日期:</label>
                            <div class="input-daterange input-group">
                                <input type="text" id="repayDateStart" class="laydate-icon form-control layer-date"
                                       value="">
                                <span class="input-group-addon bt_border0">~</span>
                                <input type="text" id="repayDateEnd" class="laydate-icon form-control layer-date"
                                       value="">
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary" id="search">查询</button>
                        <button type="button" class="btn btn-primary" id="resetForm">重置</button>
                        <button type="button" class="btn btn-primary" id="load">生成报盘文件</button>
                    </form>
                </div>
            </div>

            <div class="ibox-content clearPadding">
                <!--活动数据表格-->
                <div class="jqGrid_wrapper">
                    <table id="list2"></table>
					<div id="pager2"></div>
                </div>
                
                <div class="form-group" style="margin-top:15px">
                        <label class="control-label col-sm-1 text-danger" >合计</label>
                        <label class="control-label col-sm-2">笔数：<span id="totalCount"></span>笔</label>
                        <label class="control-label col-sm-3">待还总额：<span id="totalPayAmount"></span>元</label>
                </div>
            </div>

        </div>
    </div>
    
</div>

	<div class="mask_example">
		<div class="sk-spinner sk-spinner-wave loading">
			<div class="sk-rect1"></div>
			<div class="sk-rect2"></div>
			<div class="sk-rect3"></div>
			<div class="sk-rect4"></div>
			<div class="sk-rect5"></div>
		</div>
	</div>

<script src="/resources/js/jquery-2.1.1.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/resources/js/bootstrap.min.js?v=3.4.0"></script>
<!-- 日期控件 -->
<script src="/resources/js/plugins/layer/laydate/laydate.js"></script>
<!-- jqGrid -->
<script
	src="/resources/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script
	src="/resources/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<!-- 自定义js -->
<script src="/resources/js/contabs.js"></script>

<script>
	$(document).ready(function() {
		//必须先执行
		$.jgrid.defaults.styleUI = 'Bootstrap';
		
		function pageInit(){
			var table_list = $("#list2");
			//创建jqGrid组件
			table_list.jqGrid(
					{
						url : '/repayOnDate/getRepayOnDateList',//组件创建完成之后请求数据的url
						datatype : "json",//请求数据返回的类型。可选json,xml,txt
						mtype : "post",//向后台请求数据的ajax的类型。可选post,get
						colNames : [ '序号','合同编号','借款人名称','证件类型','证件号','产品类型','借款类型','合同状态','应还款日期','应还金额','待还金额'
						             ,'是否划扣','还款状态','还款银行','还款账号' ],//jqGrid的列显示名字，列显示名称，是一个数组对象
						colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....常用到的属性：name 列显示的名称；index 传到服务器端用来排序用的列名称；width 列宽度；align 对齐方式；sortable 是否可以排序
						             {name : 'no',index : 'no',width : 80,align : 'center',sortable :true,fixed:true}, 
						             {name : 'contractId',index : 'contractId',width : 120,align : 'center',sortable :true,fixed:true}, 
						             {name : 'borrowerName',index : 'borrowerName',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'cardType',index : 'cardType',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'cardNum',index : 'cardNum',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'productType',index : 'productType',width : 120,align : 'center',sortable :true,fixed:true}, 
						             {name : 'borrowType',index : 'borrowType',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'contractStatus',index : 'contractStatus',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'repayDate',index : 'repayDate',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'repayMoney',index : 'repayMoney',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'willRepayMoney',index : 'willRepayMoney',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'isDeduct',index : 'isDeduct',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'repayStatus',index : 'repayStatus',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'repayBank',index : 'repayBank',width : 100,align : 'center',sortable :true,fixed:true}, 
						             {name : 'repayAccount',index : 'repayAccount',width : 120,align : 'center',sortable :true,fixed:true}
						           ],
						pager : '#pager2',//表格页脚的占位符(一般是div)的id，定义翻页用的导航栏，必须是有效的html元素。翻页工具栏可以放置在html页面任意位置
						rowNum : 10,//一页显示多少条
						rowList : [ 2, 10, 20, 30, 50 ],//可供用户选择一页显示多少条
						sortname : 'id',//初始化的时候排序的字段,默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
						viewrecords : true,//定义是否要显示总记录数
						caption : '还款日查询',//表格的标题名字
						cellLayout : 10,//定义了单元格padding + border 宽度。通常不必修改此值。初始值为5
						cellEdit : false,//启用或者禁用单元格编辑功能
						emptyrecords : 'no info',//当返回的数据行数为0时显示的信息。只有当属性 viewrecords 设置为ture时起作用
						height : 360,
						page : 1,//设置初始的页码
						jsonReader : {
							root : "viewJsonData", //json中的viewJsonData，对应Page中的 viewJsonData。
							page : "curPage", //json中curPage，当前页号,对应Page中的curPage。
							total : "totalPages",//总的页数，对应Page中的pageSizes
							records : "totalRecords",//总记录数，对应Page中的totalRecords
							repeatitems : false,
							id : "0"
						},
						loadComplete : function(data) {//使用loadComplete方法替换gridComplete，gridComplete会被其他事件(clearGridData等)触发
							var ids = table_list.jqGrid('getDataIDs');
							for (var i = 0; i < ids.length; i++) {
								var curRowData = table_list.jqGrid(
										'getRowData', ids[i]);
								var id = curRowData['id'];
								console.info(id);
							}
							$("#totalCount").text(data.totalCount)
							$("#totalPayAmount").text(data.totalPayAmount);
							$(".mask_example").hide();//数据加载完成后，取消遮罩
						},
						gridComplete : function() {
						},
						//rownumbers:true,//如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'.
						autowidth : true, // 是否自动调整宽度
						sortorder : "desc"//排序方式,可选desc,asc
					});
					/*创建jqGrid的操作按钮容器*/
					/*可以控制界面上增删改查的按钮是否显示*/
					jQuery("#list2").jqGrid('navGrid', '#pager2', {
						edit : false,
						add : false,
						del : false
					});
				}
		pageInit();

		//搜索
		function doSerch() {
			var contractId = $('#contractId').val();
			var borrowerName = $('#borrowerName').val();
			var cardNum = $('#cardNum').val();
			var productType = $('#productType').val();
			var contractStatus = $('#contractStatus').val();
			var repayStatus = $('#repayStatus').val();
			var idDeduct = $('#idDeduct').val();
			var repayBankId = $('#repayBank').val();
			var repayDateStart = $('#repayDateStart').val();
			var repayDateEnd = $('#repayDateEnd').val();
			var url = '/repayOnDate/getRepayOnDateList';
			$("#list2").jqGrid('setGridParam', {
				url : url,
				postData : {'contractId': contractId, 'borrowerName': borrowerName, 'cardNum': cardNum,
	            	'productType': productType,'contractStatus': contractStatus,'repayStatus': repayStatus,'idDeduct': idDeduct,
	            	'repayBankId':repayBankId,'repayDateStart':repayDateStart,'repayDateEnd':repayDateEnd},
				page : 1
			}).trigger("reloadGrid");
		}
		
		// loading
		$("#search").click(function() {
			//$(".mask_example").show();
			doSerch();
		});
		
		$('#load').click(function(){
			loadRepayOnDateInfoExcel();
		});
		
		//导出还款日划扣数据excel
		function loadRepayOnDateInfoExcel(){
			var contractId = $('#contractId').val();
			var borrowerName = $('#borrowerName').val();
			var cardNum = $('#cardNum').val();
			var productType = $('#productType').val();
			var contractStatus = $('#contractStatus').val();
			var repayStatus = $('#repayStatus').val();
			var idDeduct = $('#idDeduct').val();
			var repayBankId = $('#repayBank').val();
			var repayDateStart = $('#repayDateStart').val();
			var repayDateEnd = $('#repayDateEnd').val();
			//alert(contractId+"="+borrowerName+"="+cardNum+"="+productType+"="+contractStatus+"="+repayStatus+"="+idDeduct+"="+repayBankId);
			var queryForm = document.getElementById("queryForm");
			queryForm.action="/repayOnDate/loadRepayOnDateInfoExcel.do?repayDateStart="+repayDateStart+"&repayDateEnd="+repayDateEnd
					+"&contractId="+contractId+"&borrowerName="+borrowerName+"&cardNum="+cardNum+"&productType="+productType+"&contractStatus="
					+contractStatus+"&repayStatus="+repayStatus+"&idDeduct="+idDeduct+"&repayBankId="+repayBankId;
			//$("#queryFrom").submit();
			queryForm.submit();
		}
		
		//日期d的月份加m月后的日期
		function addMoth(d,m){
			var ds=d.split('-');
			d=new Date( ds[0],ds[1]-1+m,ds[2])
			return d.toLocaleDateString().match(/\d+/g).join('-')  
		}
		
		//重置按钮
		$('#resetForm').click(function(){
			resetForm();
		});
		
		//重置事件
		function resetForm(){
			$('#contractId').val("");
			$('#borrowerName').val("");
			$('#cardNum').val("");
			$('#productType').val("-1");
			$('#contractStatus').val("-1");
			$('#repayStatus').val("-1");
			$('#idDeduct').val("-1");
			$('#repayBank').val("");
			$('#repayDateStart').val("");
			$('#repayDateEnd').val("");
		}

		// laydate
		var start = {
			elem : '#repayDateStart',
			format : 'YYYY-MM-DD',
			min : '2000-06-16', //设定最小日期
			max : '2030-06-16', //最大日期
			istime : false, //是否开启时间选择
			//istoday : true, //是否显示今天
			istoday: true,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas; //将结束日的初始值设定为开始日
				end.max = addMoth(datas, 1);
			}
		};
		var end = {
			elem : '#repayDateEnd',
			format : 'YYYY-MM-DD',
			min : laydate.now(),
			max : '2030-06-16',
			istime : false,
			istoday : true,
			choose : function(datas) {
				start.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		laydate(start);
		laydate(end);
		//时间主动设置,laydate.now()是浏览器时间，所以有点...
		//$('#repayDateStart').val(laydate.now());
		//$('#repayDateEnd').val(laydate.now());
		
		
	});
	
</script>
</body>
</html>