<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true" style="height:50px">
			一级分类:<select class="easyui-combobox" name="flbh1" id="attr_list_class_1" onchange="attr_list_get_class_2(this.value)">
				<option>请选择</option>
			</select>  
			二级分类: <select class="easyui-combobox" name="flbh2" id="attr_list_class_2" onchange="get_attr_list(this.value)">
				<option>请选择</option>
			</select> 
		</div>
		<div data-options="region:'west',split:true" style="width:100px"></div>
		<div data-options="region:'center'">
			<div id="attr_list_inner"></div>
		</div>
	</div>
	
<script type="text/javascript">
	$(function(){
		/* $.getJSON("js/json/class_1.js",function(data){
			$(data).each(function(i,json){
				$("#attr_list_class_1").append("<option value="+json.id+">"+json.flmch1+"</option>")
			})
		}) */
		$('#attr_list_class_1').combobox({    
		    url:'js/json/class_1.js',    
		    valueField:'id',    
		    textField:'flmch1',
		    width:100,
		    onSelect:function attr_list_get_class_2(){
				var class_1_id=$(this).combobox("getValue");
				$('#attr_list_class_2').combobox({    
				    url:'js/json/class_2_'+class_1_id+'.js',    
				    valueField:'id',    
				    textField:'flmch2',
				    width:100,
				    onSelect:function(){
				    	var class_2_id=$(this).combobox("getValue");
				   		get_attr_list(class_2_id);
				    }
				}); 
			}
		}); 
		
	})
	
	/* function attr_list_get_class_2(class_1_id){
		$.getJSON("js/json/class_2_"+class_1_id+".js",function(data){
			$("#attr_list_class_2").empty();
			$(data).each(function(i,json){
				$("#attr_list_class_2").append("<option value="+json.id+">"+json.flmch2+"</option>")
			})
		})
	} */
	
	function get_attr_list(class_2_id){
			//异步请求sevlet-----------返回内嵌页
			/* $.get("get_attr_list.do",{class_2_id:class_2_id},function(data){
				$("#attr_list_inner").html(data);
			}); */
		$('#attr_list_inner').datagrid({    
		    url:'get_attr_list_json.do',  
		    queryParams: {
		    	class_2_id:class_2_id
			},
		    columns:[[    
		        {field:'id',title:'属性id',width:100},    
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