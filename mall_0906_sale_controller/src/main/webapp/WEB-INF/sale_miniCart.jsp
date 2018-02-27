<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	function show_cart(){
		$.get("get_miniCart.do",function(data){
			$("#miniCart_list_inner").html(data);
		})//获迷你购物车的信息
		$("#miniCart_list_inner").show();
	}
	
	function hide_cart(){
		$("#miniCart_list_inner").hide();
	}
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 点击购物车时打开一个新的页面 -->
	<a target="_blank" href="goto_cart_list.do" onmouseover="show_cart()" onmouseout="hide_cart()">购物车</a>
	<div id = "miniCart_list_inner"></div>
</body>
</html>