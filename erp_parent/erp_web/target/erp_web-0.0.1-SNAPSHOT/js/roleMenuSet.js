$(function(){

    //权限列表
    $('#tree').tree({
        url:'role_readRoleMenus?id=' + 1,//数据来源
        animate:true,
        checkbox:true
    });

    //角色列表
    $('#grid').datagrid({
        url:'role_list',
        columns:[[
            {field:'uuid',title:'编号',width:100},
            {field:'name',title:'名称',width:100}
        ]],
        singleSelect:true,
        onClickRow:function (rowIndex,rowData) {//单击显示所选角色的权限
            $('#tree').tree({
                url:'role_readRoleMenus?id=' + rowData.uuid,//数据来源
                animate:true,//是否显示为动画效果
                checkbox:true//是否显示复选框
            })
        }
    })

    //更新角色权限
    $('#btnSave').bind('click',function () {
        //定义一个变量存放所有的选中的节点
        var nodes =  $('#tree').tree('getChecked');
        //定义一个数组存放每个节点的id
        var checkedStr = new Array();
        $.each(nodes,function (i,node) {
            checkedStr.push(node.id);
        });
        checkedStr = checkedStr.toString();
        //定义role变量接收已经打勾的选项的id
        var role = $('#grid').datagrid('getSelected');
        if(null == role){
            $.messager.alert('提示','请选择角色','info');
            return;
        }
        //定义formdata变量接收要提交的数据
        var formdata = {};
        formdata.id = role.uuid;
        formdata.checkedStr = checkedStr;
        $.ajax({
            url:'role_updateRoleMenus',
            data:formdata,
            dataType:'json',
            type:'post',
            success:function (data) {
                $.messager.alert('提示',data.message,'info');
            }
        });
    });
});