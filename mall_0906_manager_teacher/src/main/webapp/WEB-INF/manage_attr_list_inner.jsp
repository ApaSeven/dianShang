<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	
	$(function(){
		
	})
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<c:forEach items="${list_attr}" var="attr" varStatus="status">
			${attr.shxm_mch}
			<div id="attr_value_${attr.id}">
				<c:forEach items="${attr.list_value}" var="val">
					${val.shxzh}${val.shxzh_mch}
				</c:forEach>
			</div>
		</c:forEach>

	

</body>
</html>