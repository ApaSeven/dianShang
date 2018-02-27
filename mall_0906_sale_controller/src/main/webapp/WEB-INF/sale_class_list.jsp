<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">

//遍历显示spu

	$(function(){
		$.getJSON("js/json/class_1.js",function(data){
			$(data).each(function(i,json){
				$("#sale_list_class_1").append("<li onmouseover='sale_list_get_class_2("+json.id+")' value="+json.id+">"+json.flmch1+"</li>")
			})
		})
	})
	
	function sale_list_get_class_2(class_1_id){
		$.getJSON("js/json/class_2_"+class_1_id+".js",function(data){
			$("#sale_list_class_2").empty();
			$(data).each(function(i,json){
				$("#sale_list_class_2").append("<li value="+json.id+"> <a target='_blank' href='goto_sku_list.do?class_2_id="+json.id+"'>" +json.flmch2+"</a></li>")
			})
		})
	}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
		<ul id="sale_list_class_1" style="float:left;width:70px"></ul>
		<ul id="sale_list_class_2" style="float:left;width:70px"></ul>
 
	
</body>
</html>