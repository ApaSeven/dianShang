<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	$(function(){
		var url="${url}";
		var title = "${title}";
		add_spu(url,title);
	})

//商品属性添加
	function add_attr(url,title){
		var b = $('#tt').tabs('exists',title);
		if (b) {
			 $('#tt').tabs('select',title);
		}else{
		
			$('#tt').tabs('add',{
				title:title,
				href:url,
				closable:true,
				tools:[{
					iconCls:'icon-mini-refresh',
					handler:function(){
						alert('refresh')
					}
				}]
			})
		}
		
	}
//spu添加
	function add_spu(url,title){
		var b = $('#tt').tabs('exists',title);
		if (b) {
			$('#tt').tabs('select',title);
		}else{
			$('#tt').tabs('add',{
				title:title,
				href:url,
				closable:true,
				tools:[{
					iconCls:'icon-mini-refresh',
					handler:function(){
						alert('refresh')
					}
				}]
			})
		}
	}
//商品库存单元添加
	function add_sku(url,title){
		var b = $('#tt').tabs('exists',title);
		if (b) {
			$('#tt').tabs('select',title);
		}else{
			$('#tt').tabs('add',{
				title:title,
				href:url,
				closable:true,
				tools:[{
					iconCls:'icon-mini-refresh',
					handler:function(){
						alert('refresh')
					}
				}]
			})
		}
	}	
	
//商品库存单元查询select_attr('goto_attr_list.do','商品属性查询')
	function select_attr(url,title){
		var b = $('#tt').tabs('exists',title);
		if (b) {
			$('#tt').tabs('select',title);
		}else{
			$('#tt').tabs('add',{
				title:title,
				href:url,
				closable:true,
				tools:[{
					iconCls:'icon-mini-refresh',
					handler:function(){
						alert('refresh')
					}
				}]
			})
		}
	}	
	

</script>
<title>Insert title here</title>
</head>
<body class="easyui-layout">

	
	
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px"><h2 >后台管理系统</h2></div>
	<div data-options="region:'west',split:true,title:'West'" style="width:190px;padding:10px;">
	
	
	
	<div class="easyui-accordion" style="width:180px;height:300px;">
		<div title="商品管理">
		
			<ul class="easyui-tree">
			<li>
				<span>商品管理</span>
				<ul>
					<li><a href="javascript:add_spu('goto_spu_add.do','商品添加');">	商品添加</a></li>
					<li>
						<span>商品属性管理</span>
						<ul>
							<li><a href="javascript:select_attr('goto_attr_list.do','商品属性查询');">商品属性查询</a></li>
							<li><a href="javascript:add_attr('goto_attr_add.do','商品属性添加');">商品属性添加</a></li>
						</ul>
					</li>
					
					<li>
						<span>商品库存单元管理</span>
						<ul>	
							<li>商品库存单元查询</li>
							<li><a href="javascript:add_sku('goto_sku_add.do','商品库存单元添加');">商品库存单元添加</a></li>
						</ul>
					</li>
				</ul>
			</li>
		</ul>
		
		
		</div>
		<div title="缓存管理">
		<a href="javascript:add_attr('goto_cache.do','商品缓存管理');">	商品缓存管理</a>
		</div>
	</div>
	


	</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">east region</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">south region</div>
	<div data-options="region:'center',title:'Center'">
		<div id="tt" class="easyui-tabs" style="height:500px">
		
		</div>
	</div>
	
</body>
</html>