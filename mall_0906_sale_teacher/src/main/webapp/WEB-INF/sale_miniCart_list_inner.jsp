<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">

	

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:forEach items="${list_cart}" var="cart">
		<img width="70px" src="upload/image/${cart.shp_tp }"/>
		${cart.sku_mch }
		${cart.sku_jg}
		${cart.tjshl }
		<br>
	</c:forEach>
	总金额:${sum}
</body>
</html>