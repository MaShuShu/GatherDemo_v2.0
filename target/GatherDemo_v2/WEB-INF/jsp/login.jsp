<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../jsp/base/base.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login</title>
	<!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<!-- Import google fonts -->
    <!-- <link href="http://fonts.useso.com/css?family=Open+Sans:300,400,600,700,800|Shadows+Into+Light" rel="stylesheet" type="text/css" /> -->
    
	<!-- Favicon and touch icons -->
	<link rel="shortcut icon" href="<%=$root %>/images/ico/favicon.ico" type="image/x-icon" />
	<link rel="apple-touch-icon" href="<%=$root %>/images/ico/apple-touch-icon.png" />
	<link rel="apple-touch-icon" sizes="57x57" href="<%=$root %>/images/ico/apple-touch-icon-57x57.png" />
	<link rel="apple-touch-icon" sizes="72x72" href="<%=$root %>/images/ico/apple-touch-icon-72x72.png" />
	<link rel="apple-touch-icon" sizes="76x76" href="<%=$root %>/images/ico/apple-touch-icon-76x76.png" />
	<link rel="apple-touch-icon" sizes="114x114" href="<%=$root %>/images/ico/apple-touch-icon-114x114.png" />
	<link rel="apple-touch-icon" sizes="120x120" href="<%=$root %>/images/ico/apple-touch-icon-120x120.png" />
	<link rel="apple-touch-icon" sizes="144x144" href="<%=$root %>/images/ico/apple-touch-icon-144x144.png" />
	<link rel="apple-touch-icon" sizes="152x152" href="<%=$root %>/images/ico/apple-touch-icon-152x152.png" />

	<style type="text/css">
		.easyui-tabs{
			position: absolute;
			top: 20%;
			left:35%;
			}
	</style>
	<script type="text/javascript">
	//解决iframe窗体丢失Session时登录窗口显示在子页面的问题
	$(function(){
		if(top.location!=self.location)
	    {
	        top.location=self.location;
	    }
	});
	
	//登陆
	function submitForm(){
		//$("#form").submit();
		var params = $('#form').serializeArray();
		$.ajax({
			url: '<%=$root %>/index.do',
			type: 'POST',
			data: params,
			success:function(data){
				if(data){
					location="<%=$root %>/index.do";
				}else{
					$("#msg").html("帐号或密码错误！");
				}
			},error:function(msg){
				
			}
		});
	}
	
	//清空
	function clearForm(){
        $('#form').form('clear');
    }
	</script>
</head>
	<body>
		<div class="easyui-tabs" style="width:500px; height:350px;">
	        <div title="登陆" style="padding:10px;">
	        	<div style="padding:10px 60px 20px 60px">
			        <form id="form" action="<%=$root %>/index.do" method="POST">
			            <table cellpadding="5" style="width:100%; font-size:13px;">
			            	<tr>
			            		<td colspan="2" style="text-align:center;"><span id="msg" style="color:red;"></span></td>
			            	</tr>
			                <tr>
			                    <td>账号:</td>
			                    <td><input class="easyui-textbox" type="text" name="username" style="padding-left:10px; height:30px; width:90%;"/></td>
			                </tr>
			                <tr>
			                    <td>密码:</td>
			                    <td><input class="easyui-textbox" type="password" name="password" style="padding-left:10px; height:30px; width:90%;"/></td>
			                </tr>
			                <tr>
			                    <td>记住我</td>
			                    <td><input class="checkbox" type="checkbox" name="subject" style="width:40px;" /></td>
			                </tr>
			            </table>
			        </form>
			        <div style="text-align:center;padding:5px">
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" style="width:80px;">登录</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" style="width:80px;">清空</a>
			        </div>
		        </div>
	        </div>
	        <div title="注册" style="padding:10px">
	            
	        </div>
    	</div>
	</body>
</html>