<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">


//搜索框

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="search.do">
		<input type="text" name="keywords">
		<input type="submit" value="搜索">
	</form>
	<br>
	
	
	<!--迷你购物车  -->
	<jsp:include page="sale_miniCart.jsp"></jsp:include>
	<!-- <a href="javascript:;" onmouseover="">购物车</a> -->
	<!-- a标签增加函数 -->
</body>
</html>