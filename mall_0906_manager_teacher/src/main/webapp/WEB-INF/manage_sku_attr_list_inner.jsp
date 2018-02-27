<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function show_value(attr_id,check){
		if(check){
			$("#attr_value_"+attr_id).show();
		}else{
			$("#attr_value_"+attr_id).hide();
		}
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>硅谷商城</title>
</head>
<body>
	<h2>选择平台属性列表</h2>
	<c:forEach items="${list_attr}" var="attr" varStatus="status">
		<input type="checkbox"name="list_stu_av[${status.index}].shxm_id"  value="${attr.id}" onclick="show_value(${attr.id},this.checked)" />${attr.shxm_mch}
	</c:forEach>
	<hr>
	
	<c:forEach items="${list_attr}" var="attr" varStatus="status">
		<div id="attr_value_${attr.id}" style="display:none;">
			<c:forEach items="${attr.list_value}" var="val">
				<input type="radio" name="list_stu_av[${status.index}].shxzh_id" value="${val.id}"  />${val.shxzh}${val.shxzh_mch}
			</c:forEach>
		</div>
	</c:forEach>
</body>
</html>