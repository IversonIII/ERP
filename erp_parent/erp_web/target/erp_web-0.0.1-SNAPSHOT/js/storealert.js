$(function(){
	$('#grid').datagrid({
		url:'storedetail_storealertList',
		columns:[[
		    {field:'uuid',title:'商品编号',width:100},
		    {field:'name',title:'商品名称',width:100},
		    {field:'storeNum',title:'库存数量',width:100},
		    {field:'outNum',title:'待发货数量',width:100},
		]],
		singleSelect:true,
		toolbar:[{
			text:'发送预警邮件',
			iconCls:'icon-add',
			handler:function(){
				$.ajax({
					url:'storedetail_sendStorealertMail',
					data:'post',
					dataType:'json',
					success:function(data){
						$.messager.alert('提示信息',data.message,'info');
					}
				});
			}
		}]
	});
})
	
	