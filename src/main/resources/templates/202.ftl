<!DOCTYPE HTML>
<html>
	<head>
	
	</head>
	<body style="font-family: SimSun;line-height:1">
		<div style="width:100%;">
			
			<p align="center">
				<span style="font-size:20px;font-weight:bolder;"><b>${FYMC!"　　　"}</b></span><br/>
				<span style="font-size:25px;font-weight:bolder;"><b>协助执行通知书</b></span><br/>
				<span style="font-size:14px;font-weight:bolder;"><b>(回执)</b></span><br/>
			</p>
				
			<p style="line-height:32px;text-indent:2em">
				<div>${FYMC!"　　　"}：</div>
			</p>
			<p style="text-indent:2em;word-break:break-all">
			    <#list CKWH as ch>
				       <#if ch_index == 0>
			           <div style="padding-left:30px">你院${ch}执行裁定书、${AH!"　　　"}协助执行通知书收悉，我行处理结果如下：</div>
					   </#if>
				</#list>
			</p>
		       <#list KZZT as kt>
			      <#if  kt == "1">
			<p style="text-indent:2em">
			   
				<div style="padding-left:30px">
				     <#list KHZH as kh>
					 	 <#if kt_index == kh_index>
				        被执行人（或其他法律地位）${XM!"　　　　"}
						在我行
					
						      ${kh}
						
						账户内的
				  </#if>
				</#list>
				<#list LCZH as lczh>
					<#if kt_index == lczh_index>${lczh}金融资产
					</#if>
				</#list>
				<#list SKSE as skse>
					<#if kt_index == skse_index>${skse}份（计量单位）已被继续冻结，冻结期限自
					</#if>
				</#list>
				<#list CSKSRQ as kq>
				    <#if kt_index == kq_index>
				       ${kq}至
				    </#if>
				</#list>
				<#list CSJSRQ as jq>
				    <#if kt_index == jq_index>
				       ${jq}，该金融资产交易限制将于${jq}解除。
				    </#if>
				</#list>
				</div>
				
				
			</p>
			   </#if> 
			   </#list>
			   <#list KZZT as kt>
			  <#if  kt == "2">
			<p style="text-indent:2em">
			     
				<div style="padding-left:30px">
				<#list WNKZYY as wy>
				     <#if kt_index == wy_index>
				        因${wy}原因，被执行人（或其他法律地位）${XM!"　　　　"}在我行
				      </#if>
				</#list>  
				<#list KHZH as kh>
				          <#if kt_index == kh_index>
				                ${kh}账户内的
						   </#if>   
				       </#list>
				<#list LCZH as lczh>
				          <#if kt_index == lczh_index>
				                ${lczh}金融资产未能续冻。
						   </#if>   
				       </#list>
				  </div>
			     
			</p>
			   </#if>
            </#list>			
			<div style="background:url(images/${KHWD!"0"}.jpg);background-repeat: no-repeat;background-position: center;background-position:top;width:250px;height:250px;margin-right: 10px;margin-top: 100px;float: right;padding-top: 150px;padding-left: 100px;padding-right: 10px">
			     <div style="margin-top: -70px;padding-top: 0px">${DATE!"　　　　"}</div>			
			</div>
		</div>
	</body>
</html>



