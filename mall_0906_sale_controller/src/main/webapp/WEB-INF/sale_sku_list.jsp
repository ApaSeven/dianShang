<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">


</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	列表检索页面:<br>
	属性列表
	<jsp:include page="sale_attr_list_inner.jsp"></jsp:include>
	<hr>
	商品列表
	<div id="sku_list_inner">
		<jsp:include page="sale_sku_list_inner.jsp"></jsp:include>
	</div>
</body>
</html>