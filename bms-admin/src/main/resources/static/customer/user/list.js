var dtGridColumns = [{
    id : 'accountName',
    title : '账户名',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'userName',
    title : '姓名',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'roleName',
    title : '角色',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    resolution:function(value,record){
    	return record.role.name;
    }
},{
    id : 'deptName',
    title : '所属部门',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
}/*,{
    id : 'deleteStatus',
    title : '是否删除',
    type : 'number',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    resolution:function(value){
        if(value==1){
        	return '是';
        }else{
        	return '否';
        }
    }
},{
    id : 'locked',
    title : '是否锁定',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    resolution:function(value){
        if(value==1){
        	return '是';
        }else{
        	return '否';
        }
    }
}*/,{
    id : 'creatorName',
    title : '创建者',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'createTime',
    title : '创建时间',
    type : 'date',
    format:'yyyy-MM-dd hh:mm:ss', 
    otype:'time_stamp_ms', 
    oformat:'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'updateTime',
    title : '更新时间',
    type : 'date',
    format:'yyyy-MM-dd hh:mm:ss', 
    otype:'time_stamp_ms', 
    oformat:'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
}/*,{
    id : 'operation',
    title : '操作',
    type : 'string',
    columnClass : 'text-center tpl-table-black-operation',
    headerStyle : 'color:grey;',
    resolution:function(value, record, column, grid, dataNo, columnNo){
        var content = '';
        content += '<a href="#" onclick="loadPage(\'user/'+record.id+'/select\');" shiro:hasPermission="user:edit"> <i class="am-icon-pencil"></i> 编辑</a> ';
        content += '  ';
        content += '<a href="#" onclick="delUser('+record.id+');" class="tpl-table-black-operation-del del-btn" shiro:hasPermission="user:delete"><i class="am-icon-trash"></i> 删除</a>';
        return content;
    }
}*/];

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    loadURL : 'user/list',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : 'refresh|print|export[excel]',
    exportFileName : '系统用户表',
    pageSize : 10,
    pageSizeLimit : [10, 20, 30]
};
var grid = $.fn.dlshouwen.grid.init(dtGridOption);


$(function() {
	grid.load();
	$('select').selected();
	var setting = {
			data: {
				simpleData: {
					enable: true,
					pIdKey: "pid",
				}
			},
			callback: {
				onClick: zTreeOnClick
			}
	};
	initDeptTree(setting);
});


function zTreeOnClick(event, treeId, treeNode) {
	console.log(treeNode.id);
	search(treeNode.id);
};

function search(deptId){
	  grid.parameters = new Object();
	  grid.parameters['name'] = $("#name").val();
	  grid.parameters['deptId'] = deptId;
	  grid.refresh(true);
}
function resetPassword(){
	   var rows = grid.getCheckedRecords();
       if (rows.length == 1) {
    	   loadPage('user/'+rows[0].id+'/toRestPassword');
       }else{
    	   layer.msg("你没有选择行或选择了多行数据", {
               icon : 0
           });
       }
}
function delUser(id){
	del("user/"+id+"/delete",search);
}


function btnDel(){
	$('.modal').modal('open');
	/*   var rows = grid.getCheckedRecords();
    if (rows.length == 1) {
 	   del('user/'+rows[0].id+'/delete',search);
    }else{
 	   layer.msg("你没有选择行或选择了多行数据", {
            icon : 0
        });
    }*/
}
function btnEdit(){
	   var rows = grid.getCheckedRecords();
    if (rows.length == 1) {
    	loadPage('user/'+rows[0].id+'/select');
    }else{
 	   layer.msg("你没有选择行或选择了多行数据", {
            icon : 0
        });
    }
}

