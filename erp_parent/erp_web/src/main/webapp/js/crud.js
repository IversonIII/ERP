//提交的方法名称
var method = "";
var listParam="";
var saveParam="";
$(function(){
	//加载表格数据
	$('#grid').datagrid({
		url:name + '_listByPage' + listParam,
		columns:columns,
		singleSelect: true,
		pagination: true,
		toolbar: [{
			text: '新增',
			iconCls: 'icon-add',
			handler: function(){
				//设置保存按钮提交的方法为add
				method = "add";
				//清除表单数据
				$('#editForm').form('clear');
				//关闭编辑窗口
				$('#editDlg').dialog('open');
			}
		},'-',{
				text:'导出',
				iconCls: 'icon-excel',
				handler: function(){
					var formData = $('#searchForm').serializeJSON();
					$.download(name + '_export' + listParam,formData);
				}
			},'-',{
				text:'导入',
            	iconCls: 'icon-save',
				handler:function () {
					$('#importDlg').dialog('open');
                }
		}
		]
	});

	$('#importDlg').dialog({
		title:'导入数据',
		height:106,
		width:330,
		closed:true,
		buttons:[
			{
				text:'导入',
				handler:function () {
					$.ajax({
						url:"supplier_doImport",
						type:'post',
						data:new FormData($('#importForm')[0]),
						dataType:'json',
						processData:false,
						contentType:false,
						success:function (data) {
							$.messager.alert("提示",data.message,'info',function () {
								if(data.success){
									$('#importDlg').dialog('close');
									$('#grid').datagrid('reload')
								}
                            })
                        }
					});
                }
			}
		]
	})
	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});

	//初始化编辑窗口
	$('#editDlg').dialog({
		title: '编辑',//窗口标题
		width: 280,//窗口宽度
		height: 250,//窗口高度
		closed: true,//窗口是是否为关闭状态, true：表示关闭
		modal: true,//模式窗口
		buttons:[{
			text:'保存',
			iconCls: 'icon-save',
			handler:function(){
				//用记输入的部门信息
				var submitData= $('#editForm').serializeJSON();
				$.ajax({
					url: name + '_' + method+saveParam,
					data: submitData,
					dataType: 'json',
					type: 'post',
					success:function(rtn){
						//{success:true, message: 操作失败}
						$.messager.alert('提示',rtn.message, 'info',function(){
							if(rtn.success){
								//关闭弹出的窗口
								$('#editDlg').dialog('close');
								//刷新表格
								$('#grid').datagrid('reload');
							}
						});
					}
				});
			}
		},{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				//关闭弹出的窗口
				$('#editDlg').dialog('close');
			}
		}]
	});
});

/**
 * 删除
 */
function del(uuid){
	$.messager.confirm("确认","确认要删除吗？",function(yes){
		if(yes){
			$.ajax({
				url: name + '_delete?id=' + uuid,
				dataType: 'json',
				type: 'post',
				success:function(rtn){
					$.messager.alert("提示",rtn.message,'info',function(){
						//刷新表格数据
						$('#grid').datagrid('reload');
					});
				}
			});
		}
	});
}

/**
 * 修改
 */
function edit(uuid){
	//弹出窗口
	$('#editDlg').dialog('open');

	//清空表单内容
	$('#editForm').form('clear');

	//设置保存按钮提交的方法为update
	method = "update";

	//加载数据
	$('#editForm').form('load',name + '_get?id=' + uuid);
}