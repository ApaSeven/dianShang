<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">

	function get_param_up(shxm_id,shxzh_id,shxzhmch){
		//1.通过数组
		//$("#attr_param").append("<input name='attr_param' type='text' value='"+shxm_id+"_"+shxzh_id+"'/>"+" "+shxzhmch);
		//通过json
		$("#attr_param").append("<input name='attr_param' type='text' value='{\"shxm_id\":"+shxm_id+",\"shxzh_id\":"+shxzh_id+"}'/>"+" "+shxzhmch);

		
		get_sku_list()
	}
	
	/* function get_param_down(){
		$("#attr_param").append("参数");
		
		//get_sku_list()
	}
 */	
 
//根据属性交叉检索出sku
	function get_sku_list(){
		//二级分类id
		var class_2_id = "${class_2_id}";
		//属性参数 
		var inputs = $("#attr_param input[name='attr_param']");
		//声明一个查询字符串
		var param = "class_2_id="+class_2_id;
		
		$(inputs).each(function(i,n){
		var json = jQuery.parseJSON(n.value);
		param = param + "&list_stu_av["+i+"].shxm_id="+json.shxm_id+"&list_stu_av["+i+"].shxzh_id="+json.shxzh_id;
		});
		
		param = param + "&order=" + $("#order").val();
		//alert(param);//有地址
		
		//将jquery数组对象,转化成js的数组对象
		/* var attr_param = new Array();
		$(inputs).each(function(i,n){
			attr_param[i] = n.value;
		})*/
		
		
		
		//根据属性交叉检索出sku
		
		$.get("get_sku_list_by_attr.do",param,function(html){
			
			$("#sku_list_inner").html(html);
		}) 
	}
 
 
 	//排序
 	function search_order(new_order){
 		//存储排序参数
 		var old_order = $("#order").val();
 		
 		if (old_order == new_order) {
			new_order = old_order+"desc";
		}
 		
 		$("#order").val(new_order);
 		//异步调用get_sku_list
 		get_sku_list()//根据属性交叉检索出sku();
 	}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 属性列表 --><!-- 点击属性时把,所选到的属性放到上边,然后按照所选的条件过滤 -->
	<div id="attr_param">属性参数区域</div><!--要在这里边存属性名id和属性值id  -->
	
	<input id="order" value=" order by jg "/><!-- 排序属性参数区域 -->

	
	<c:forEach items="${list_attr}" var="attr" varStatus="status">
			${attr.shxm_mch}
			<%-- <div id="attr_value_${attr.id}"> --%>
				<c:forEach items="${attr.list_value}" var="val">
					<a href="javascript:get_param_up(${attr.id },${val.id },'${val.shxzh}${val.shxzh_mch }');">${val.shxzh}${val.shxzh_mch}</a>
				</c:forEach>
			<!-- </div> -->
			<br>
	</c:forEach>
	
	<hr>
	
	排序:
	
	<a href="javascript:search_order(' order by sku_xl ');">销量</a>
	
	<a href="javascript:search_order(' order by jg ');">价格</a>	
	
	<a href="javascript:search_order(' order by sku.chjshj ');">上架时间</a>
	
	<a href="javascript:search_order(' order by sku_xl ');">评论数</a>
	
	
	
	

</body>
</html>