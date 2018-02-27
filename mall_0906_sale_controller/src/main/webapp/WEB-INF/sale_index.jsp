<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<!-- 导航栏 -->
		<jsp:include page="sale_header.jsp"></jsp:include>

		<h2>商城首页</h2>
		
		
		<!-- 搜索栏 -->
		<jsp:include page="sale_search_area.jsp"></jsp:include>
		
		<!-- 类目列表 -->
		<jsp:include page="sale_class_list.jsp"></jsp:include>
</body>
</html>