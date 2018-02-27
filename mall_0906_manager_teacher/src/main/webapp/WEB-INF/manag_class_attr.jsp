<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function (){
		$.getJSON("js/json/class_1.js",function(data){
			// 用js循环json的分类集合
			$(data).each(function(i,json){
				// 将分类集合的内容生成<option>对象加载到下拉列表中
				$("#class_1_select_attr").append("<option value="+json.id+">"+json.flmch1+"</option>");
			});
		});
	});
	
	function get_class_2_attr(class_1_id){

		$.getJSON("js/json/class_2_"+class_1_id+".js",function(data){
			// 用js循环json的分类集合
			$("#class_2_select_attr").empty();
			$(data).each(function(i,json){
				// 将分类集合的内容生成<option>对象加载到下拉列表中
				$("#class_2_select_attr").append("<option value="+json.id+">"+json.flmch2+"</option>");
			});
		});
		
	}
	


	function get_spu_by_id(tm_id){
		var class_1_id = $("#class_1_select_attr").val();
		var class_2_id = $("#class_2_select_attr").val();
		$.post("sku_get_spu.do",{pp_id:tm_id,class_1_id:class_1_id,class_2_id:class_2_id},function(data){
			$(data).each(function(i,json){
				$("#spu_select_sku").append("<option value="+json.id+">"+json.shp_mch+"</option>");
			});
		});
	}
</script>
<title>硅谷商城</title>
</head>
<body>
	<select name="flbh1" id="class_1_select_attr" onchange="get_class_2_attr(this.value)">
		<option>请选择</option>
	</select>
	<select name="flbh2" id="class_2_select_attr">
		<option>请选择</option>
	</select>
</body>
</html>