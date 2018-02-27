<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
//根据库中的数据,设置checkbox的状态
	$(function(){
		$(":checkbox").each(function(i,n){
			var shfxz = n.value;
			if (shfxz=="1") {
				$(this).attr("checked",true);
			}
		})
	})
	

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 迷你购物车内嵌页,通过内嵌页发起异步请求,要刷新内嵌页列表 -->
	<c:forEach items="${list_cart}" var="cart">
		<img width="70px" src="upload/image/${cart.shp_tp }"/>
		<input type="checkbox" onclick="check_item(${cart.sku_id},checked)" value="${cart.shfxz }">${cart.shfxz }
		<!-- <input type="checkbox" onclick="check_item()" ${cart.shfxz =="1"?"checked":""}>${cart.shfxz } -->
													  
		${cart.sku_mch }
		${cart.sku_jg}
		${cart.tjshl }
		<br>
	</c:forEach>
	<hr>
	总金额:${sum}
	<hr>
	<form action="check_order.do" method="post">
		结算:<input type="submit" value="结算">
	</form>
</body>
</html>