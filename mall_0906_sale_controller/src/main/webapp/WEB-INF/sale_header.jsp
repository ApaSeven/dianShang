<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
//通过页面操作cookie,就是在请求回到页面的时候,在页面取出cookie操作
	$(function(){
		var yh_nch = getMyCookie("yh_nch");
		
		$("#yh_nch").text(yh_nch);
	})
	
	
	
	function getMyCookie(key){
		var cookies = (document.cookie);
		cookies = cookies.replace(/\s/,"");//去空格 
		
		var cookie_array = cookies.split(";");//按;分割
		for (var i = 0; i < cookie_array.length; i++) {
			cookie = cookie_array[i].split("=");
			
			 if (cookie[0]==key) {
 				val = cookie[1];
				return decodeURIComponent(val);
 				//break;
			 } 
		}  
	
	}
	
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--登录时访问主页面,通过servlet时,取出cookie中的昵称,放进map中返回  --><!-- 不登录状态下的 -->
	<c:if test="${empty user}">
		<a href="goto_login.do"><span id="yh_nch"></span>请登录 </a>注册  
	</c:if>
	<!-- session中获取用户昵称 -->
	<c:if test="${not empty user}">
		欢迎${user.yh_nch} 订单列表 <a href="goto_out.do">退出</a>
	</c:if>

</body>
</html>