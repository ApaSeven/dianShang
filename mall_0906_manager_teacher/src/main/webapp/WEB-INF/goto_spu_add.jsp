<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>硅谷商城</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true" style="height:50px">
			一级分类:<select name="flbh1" id="spu_class_1" onchange="spu_get_class_2(this.value)"></select>  
			二级分类: <select name="flbh2" id="spu_class_2"></select> 
			商品品牌:<select name="pp_id" id="spu_tm" ></select>
		</div>
		<div data-options="region:'west',split:true" style="width:100px"></div>
		<div data-options="region:'center'">
			<form action="save_spu.do" method="post" enctype="multipart/form-data">
				商品名称:<input name="shp_mch" type="text" value="测试商品spu"><br>
				商品描述:<textarea name="shp_msh" >测试商品描述</textarea>
				选择图片:
				<div id="button_id">
					<img id="img_0" src="image/upload_hover.png" width="100px height:100px" style="border:1px solid black" onclick="button_img_click(0)">
					<input id="file_0" name="files" type="file" onchange="button_img_change(0)" style="display:none;" ><br>
				</div>
				<input type="submit" value="提交">
			</form>
		</div>
	</div>
	
	
<script type="text/javascript">
	$(function(){
		$.getJSON("js/json/class_1.js",function(data){
			$(data).each(function(i,json){
				$("#spu_class_1").append("<option value="+json.id+">"+json.flmch1+"</option>")
			})
		})
	})
	
	function spu_get_class_2(class_1_id){
		$.getJSON("js/json/class_2_"+class_1_id+".js",function(data){
			$("#spu_class_2").empty();
			$(data).each(function(i,json){
				$("#spu_class_2").append("<option value="+json.id+">"+json.flmch2+"</option>")
			})
		})
		spu_get_tm(class_1_id);
	}
	
	function spu_get_tm(class_1_id){
		$.getJSON("js/json/tm_class_1_"+class_1_id+".js",function(data){
			$("#spu_tm").empty();
			$(data).each(function(i,json){
				$("#spu_tm").append("<option value="+json.id+">"+json.ppmch+"</option>")
			})
		})
	}
	
	
	function button_img_click(index){
		$("#file_"+index).click()
		
	}
	
	function button_img_change(index){
	var file = $("#file_"+index)[0].files[0];
		
		var url = window.URL.createObjectURL(file);
		
		$("#img_"+index).attr("src",url);
		
		//追加图片,判断用户选择的是不是最后一张
		var length = $("#button_id :file").length;//有几张图片
		if((index+1)==length){
			add_button(index);
		}
			
	}
	
	
	function add_button(index){
		var a = '<img id="img_'+(index+1)+'" src="image/upload_hover.png" width="100px height:100px" style="border:1px solid black" onclick="button_img_click('+(index+1)+')">'
		var b = '<input id="file_'+(index+1)+'" name="files" type="file" onchange="button_img_change('+(index+1)+')" style="display:none;" >'
		$("#button_id").append(a+b);
	}
	
	
</script>

</body>
</html>