<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">

	function b(){

	}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true" style="height:50px">
			一级分类:<select class="easyui-combobox" name="flbh1" id="cache_list_class_1" onchange="cache_list_get_class_2(this.value)">
				<option>请选择</option>
			</select>  
			二级分类: <select class="easyui-combobox" name="flbh2" id="cache_list_class_2" onchange="get_cache_list(this.value)">
				<option>请选择</option>
			</select> 
		</div>
		<div data-options="region:'west',split:true" style="width:100px"></div>
		<div data-options="region:'center'">
		
			<div id="cache_list_inner" class="easyui-datagrid"></div>
			<a href="javascript:refresh_attr_cache();">刷新属性缓存</a>
		</div>
	</div>
	
<script type="text/javascript">

	function refresh_attr_cache(){
		//获得被选中的属性参数,和二级分类的id
		
		var class_2_id = $("#cache_list_class_2").combobox("getValue");//获取二级分类id
		var attr_array = $("#cache_list_inner").datagrid("getChecked");//被选中的属性
		var attrs = new Array();
		$(attr_array).each(function(i,n){
			attrs[i] = n.id;
		})
		//异步请求刷新属性缓存的方法
		
		$.get("refresh_attr_cache.do",{class_2_id:class_2_id,attrs:attrs},function(data){
			//返回刷新成功的属性缓存的个数
			alert("刷新了"+data+"条数据")
		})
		
	}


	$(function(){
		/* $.getJSON("js/json/class_1.js",function(data){
			$(data).each(function(i,json){
				$("#cache_list_class_1").append("<option value="+json.id+">"+json.flmch1+"</option>")
			})
		}) */
		$('#cache_list_class_1').combobox({    
		    url:'js/json/class_1.js',    
		    valueField:'id',    
		    textField:'flmch1',
		    width:100,
		    onSelect:function cache_list_get_class_2(){
				var class_1_id=$(this).combobox("getValue");
				$('#cache_list_class_2').combobox({    
				    url:'js/json/class_2_'+class_1_id+'.js',    
				    valueField:'id',    
				    textField:'flmch2',
				    width:100,
				    onSelect:function(){
				    	var class_2_id=$(this).combobox("getValue");
				   		get_cache_list(class_2_id);
				    }
				}); 
			}
		}); 
		
	})
	
	/* function cache_list_get_class_2(class_1_id){
		$.getJSON("js/json/class_2_"+class_1_id+".js",function(data){
			$("#cache_list_class_2").empty();
			$(data).each(function(i,json){
				$("#cache_list_class_2").append("<option value="+json.id+">"+json.flmch2+"</option>")
			})
		})
	} */
	
	function get_cache_list(class_2_id){
			//异步请求sevlet-----------返回内嵌页
			/* $.get("get_cache_list.do",{class_2_id:class_2_id},function(data){
				$("#cache_list_inner").html(data);
			}); */
		$('#cache_list_inner').datagrid({    
		    url:'get_attr_list_json.do',  
		    queryParams: {
		    	class_2_id:class_2_id
			},
		    columns:[[    
		        {field:'id',title:'属性id',width:100,checkbox:true},    
		        {field:'shxm_mch',title:'属性名称',width:100},    
		        {field:'list_value',title:'属性值',width:300,
					formatter: function(value,row,index){
		        		var str = "";
		        		$(value).each(function(i,json){
		        			str=str+" "+json.shxzh+json.shxzh_mch;
		        		})
						return str;
					}	
		        	
		        }  ,  
		        {field:'chjshj',title:'创建时间',width:170,
		        	formatter: function(value,row,index){
		        		
						return new Date(value).toLocaleString();
					}	
		        
		        }    
		    ]]    
		});  
	}
	
	</script>	
</body>
</html>