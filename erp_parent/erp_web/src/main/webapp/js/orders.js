var oper = Request['oper'];
var type = Request['type']*1;
$(function(){
	var url = 'orders_listByPage';
	if(oper == 'myOrders'){
		url = 'orders_myListByPage?t1.type='+type;
	}
	if(oper == 'orders'){
		url += '?t1.type='+type;
	}
	if(oper == 'doCheck'){
		url += '?t1.type=1&t1.state=0';
	}
	if(oper == 'doStart'){
		url += '?t1.type=1&t1.state=1';
	}
	if(oper == 'doInStore'){
		url += '?t1.type=1&t1.state=2';
	}
	if(oper == 'doOutStore'){
		url += '?t1.type=2&t1.state=0';
	}
	
	$('#grid').datagrid({
		url:url,
		columns:getColumns(),
		singleSelect:true,
		pagination:true,
		onDblClickRow:function(rowIndex,rowData){
			//打开订单详情窗口
			$('#ordersDlg').dialog('open')
			//订单详情的显示内容
			$('#uuid').html(rowData.uuid);
			$('#waybillsn').html(rowData.waybillsn);
			$('#supplierName').html(rowData.supplierName);
			$('#state').html(getState(rowData.state));
			$('#createrName').html(rowData.createrName);
			$('#checkerName').html(rowData.checkerName);
			$('#starterName').html(rowData.starterName);
			$('#enderName').html(rowData.enderName);
			$('#createtime').html(formatDate(rowData.createtime));
			$('#checktime').html(formatDate(rowData.checktime));
			$('#starttime').html(formatDate(rowData.starttime));
			$('#endtime').html(formatDate(rowData.endtime));
			//加载明细数据
			$('#itemgrid').datagrid('loadData',rowData.orderdetails);
		}
	});
	
	//明细表格
	$('#itemgrid').datagrid({
		columns:[[
		    {field:'uuid',title:'编号',width:100},
		    {field:'goodsuuid',title:'商品编号',width:100},
		    {field:'goodsname',title:'商品名称',width:100},
		    {field:'price',title:'价格',width:100},
		    {field:'num',title:'数量',width:100},
		    {field:'money',title:'金额',width:100},
		    {field:'state',title:'订单状态',width:100,formatter:getDetailState},
		]],
		fitColumns:true,
		singleSelect:true
	});

	//订单详情窗口配置
	var orderDlgCfg = {
			title:'订单详情',
			height:320,
			width:700,
			modal:true,
			closed:true,
			toolbar:toolbar
	};

	//订单详情窗口的按钮数组(工具栏)
	var orderDlgToolbar = new Array();

	//这里是添加按钮的地方
	//添加导出按钮
	orderDlgToolbar.push({
		text:'导出',
		iconCls:'icon-excel',
		handler:doExport
	});
	//物流详情
    orderDlgToolbar.push({
		text:'物流详情',
		iconCls:'icon-search',
		handler:function () {
			if(($('#waybillsn').html())== ''){
				$.messager.alert('提示','没有物流信息','info');
				return;
			}
			//打开物流详情窗口
			$('#waybillDlg').dialog('open');
			$('#waybillgrid').datagrid({
				url:'orders_waybilldetailList?waybillsn=' + $('#waybillsn').html()
			});
        }
	});
	
	//添加审核按钮
	if(oper == 'doCheck'){
		orderDlgToolbar.push({
			text:'审核',
			iconCls:'icon-search',
			handler:doCheck
		});
	}

    //添加确认按钮
    if(oper == 'doStart'){
        orderDlgToolbar.push({
            text:'确认',
            iconCls:'icon-search',
            handler:doStart
        });
    }
	
	if(oper == 'doInStore'||oper == 'doOutStore'){
		$('#itemgrid').datagrid({
			onDblClickRow:function(rowIndex,rowData){
				$('#itemDlg').dialog('open');
				//因为orders.html那边是用隐藏域传过来的所以只能这样子获取($('#itemuuid').val();)
				$('#id').val(rowData.uuid);
				$('#goodsuuid').html(rowData.goodsuuid);
				$('#goodsname').html(rowData.goodsname);
				$('#num').html(rowData.num);
			}
		});
	}
	if(oper == 'myOrders'){
		var btnText='采购申请';
		if(type == 2){
			btnText="销售订单录入";
			$('#ordersupplier').html("客户");
		}
		$('#grid').datagrid({
			toolbar:[
		         {
		        	 text:btnText,
		        	 iconCls:'icon-add',
		        	 handler:function(){
		        		 $('#addOrdersDlg').dialog('open');
		        	 }
		         }
			]
		})
	}
    //动态给订单详情窗口 添加工具栏
	if(orderDlgToolbar.length > 0){
		orderDlgCfg.toolbar = orderDlgToolbar;
	}
	
	//订单详情窗口
	$('#ordersDlg').dialog(orderDlgCfg);

	//定义变量显示出入库标题
    var dlgTitle = '入库';
    if(type == 2){
        dlgTitle = '出库';
    }

    $('#itemDlg').dialog({
        title:dlgTitle,
        height:200,
        width:300,
        modal:true,
        closed:true,
        buttons:[
            {
                text:dlgTitle,
                iconCls:'icon-save',
                handler:doInOutStore
            }
        ]
    });

    //采购申请窗口
    $('#addOrdersDlg').dialog({
        title:'采购申请',
        width:700,
        height:400,
        modal:true,
        closed:true
    });

    //物流详情grid
    $('#waybillgrid').datagrid({
        columns:[[
            {field:'exedate',title:'执行日期',width:100},
            {field:'exetime',title:'执行时间',width:100},
            {field:'info',title:'物流信息',width:220}
        ]],
		singleSelect:true,
        fitColumns:true,
        rownumbers:true
    });
})

//日期格式化器
function  formatDate(data){
	return new Date(data).Format('yyyy-MM-dd');
}

//订单状态
function getState(data){
	if(type == 1){
		switch(data * 1){
		case 0: return '未审核';
		case 1: return '已审核';
		case 2: return '已确定';
		case 3: return '已入库';
		default: return '';
		}
	}
	if(type == 2){
		switch(data * 1){
			case 0: return '未出库';
			case 1: return '已出库';
		}
	}
}

//订单明细状态
function getDetailState(data){
	if(type == 1){
		switch(data * 1){
			case 0: return '未入库';
			case 1: return '已入库';
		}
	}
	if(type == 2){
		switch(data * 1){
			case 0: return '未出库';
			case 1: return '已出库';
		}
	}
}

//审核
function doCheck(){
	//友好语句
	$.messager.confirm('确定','确定要审核吗?',function(yes){
		if(yes){
			$.ajax({
				url:'orders_doCheck?id='+$('#uuid').html(),
				dataType:'json',
				type:'post',
				success:function(data){
					$.messager.alert('提示',data.message,'info',function(){
						if(data.success){
							//关闭订单详情窗口
							$('#ordersDlg').dialog('close');
							//刷新订单列表
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	});
}
function doStart(){
	//友好语句
	$.messager.confirm('确定','确定要确认吗?',function(yes){
		if(yes){
			$.ajax({
				url:'orders_doStart?id='+$('#uuid').html(),
				dataType:'json',
				type:'post',
				success:function(data){
					$.messager.alert('提示',data.message,'info',function(){
						if(data.success){
							//关闭订单详情窗口
							$('#ordersDlg').dialog('close');
							//刷新订单列表
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	});
}

//入库
function doInOutStore(){
	var message = "确认要入库吗？";
	var url = "orderdetail_doInStore"
		if(type == 2){
			message = "确认要出库吗？";
			url = "orderdetail_doOutStore"
		}
	//友好提示
	$.messager.confirm('确定','确定要入库吗?',function(yes){
		if(yes){
			//定义一个变量接收页面的值
			var formdata = $('#itemForm').serializeJSON();
			//判断是否选了仓库
			if(formdata.storeuuid == ''){
				$.messager.alert('提示','请选择要入的仓库','info');
				return;
			}
			//用ajax提交,带上订单的uuid
			$.ajax({
				url:url,
				data:formdata,
				dataType:'json',
				type:'post',
				success:function(data){
					$.messager.alert('提示',data.message,'info',function(){
						if(data.success){
							//关闭入库窗口
							$('#itemDlg').dialog('close');
							//修改入库后的明细状态     getSelected:返回第一个被选中的行或如果没有选中的行则返回null;
							var row = $('#itemgrid').datagrid('getSelected')
							row.state=1;
							//声明一个变量存数据
							var value = $('#itemgrid').datagrid('getData')
							//重载数据,更新状态
							$('#itemgrid').datagrid('loadData',value);
							
							//声明一个boolean变量存是否全部都已经入库
							var allIn = true;
							$.each(value.rows,function(i,row){
								if(row.state*1==0){
									allIn = false;
									return false;
								}
							});
							if(allIn == true){
								$('#ordersDlg').dialog('close');
								$('#grid').datagrid('reload');
							}
						}
					});
				}
			});
		}
	});
}

//根据对应的类型获取对应的列
function getColumns(){
	if(type == 1){
		return [[
			  {field:'uuid',title:'编号',width:100},
			  {field:'createtime',title:'生成日期',width:100,formatter:formatDate},
			  {field:'checktime',title:'审核日期',width:100,formatter:formatDate},
			  {field:'starttime',title:'确认日期',width:100, formatter:formatDate},
			  {field:'endtime',title:'入库日期',width:100,formatter:formatDate},
			  {field:'createrName',title:'下单员',width:100},
			  {field:'checkerName',title:'审核员',width:100},
			  {field:'starterName',title:'采购员',width:100},
			  {field:'enderName',title:'库管员',width:100},
			  {field:'supplierName',title:'供应商',width:100},
			  {field:'totalmoney',title:'合计金额',width:100},
			  {field:'state',title:'订单状态',width:100,formatter:getState},
			  {field:'waybillsn',title:'运单号',width:100}
		]]
	}
	if(type == 2){
		return [[
  		    {field:'uuid',title:'编号',width:100},
  		    {field:'createtime',title:'生成日期',width:100,formatter:formatDate},
  		    {field:'endtime',title:'入库日期',width:100,formatter:formatDate},
  		    {field:'createrName',title:'下单员',width:100},
  		    {field:'enderName',title:'库管员',width:100},
  		    {field:'supplierName',title:'客户',width:100},
  		    {field:'totalmoney',title:'合计金额',width:100},
  		    {field:'state',title:'状态',width:100,formatter:getState},
  		    {field:'waybillsn',title:'运单号',width:100}
		]]
	}
}

//导出
function doExport(){
	$.download('orders_exportById',{id:$('#uuid').html()});
}

