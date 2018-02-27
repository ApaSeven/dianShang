<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript">



</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 商品列表 -->
	
	
	<div class="Sbox">
		<c:forEach items="${list_sku}" var="sku">
			<div class="list">
				<div class="img">
					<img alt="" width="100%" height="70%" src="upload/image/${sku.spu.shp_tp}">
				</div>
				<div class="price">¥${sku.jg}</div>
				<div class="title"><a href="goto_sku_detail.do?sku_id=${sku.id}&spu_id=${sku.spu.id}" target="_blank">${sku.sku_mch}</a></div>
			</div>
		</c:forEach>
	</div>
	
	
	<%-- <c:forEach items="${list_sku }" var="sku">
		${sku.id} ${sku.spu.shp_tp}${sku.sku_mch}${sku.jg}${sku.sku_xl}
	</c:forEach> --%>

</body>
</html>