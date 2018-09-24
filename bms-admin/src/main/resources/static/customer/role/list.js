var grid;
$(function() {
	var dtGridColumns = [{
	    id : 'name',
	    title : '角色名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	},{
	    id : 'key',
	    title : '标识',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	},{
	    id : 'deptName',
	    title : '所属部门',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	},{
	    id : 'status',
	    title : '状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;',
	    resolution:function(value){
	        if(value==1){
	        	return '删除';
	        }else{
	        	return '正常';
	        }
	    }
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
	},{
	    id : 'operation',
	    title : '操作',
	    type : 'string',
	    columnClass : 'text-center tpl-table-black-operation',
	    headerStyle : 'color:grey;',
	    resolution:function(value, record, column, grid, dataNo, columnNo){
	        var content = '';
	        content += '<a href="#" onclick="loadPage(\'role/'+record.id+'/select\');" shiro:hasPermission="role:edit"> <i class="am-icon-pencil"></i> 编辑</a> ';
	        content += '  ';
	        content += '<a href="#" onclick="delRole('+record.id+');" class="tpl-table-black-operation-del del-btn" shiro:hasPermission="role:delete"><i class="am-icon-trash"></i> 删除</a>';
	        return content;
	    }
	}];

	var dtGridOption = {
	    lang : 'zh-cn',
	    ajaxLoad : true,
	    check : true,
	    checkWidth :'37px',
	    loadURL : 'role/list',
	    columns : dtGridColumns,
	    gridContainer : 'dtGridContainer',
	    toolbarContainer : 'dtGridToolBarContainer',
	    tools : '',
	    pageSize : 10,
	    pageSizeLimit : [10, 20, 30]
	};
	grid = $.fn.dlshouwen.grid.init(dtGridOption);
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
	  grid.parameters['deptId'] = deptId;
	  grid.refresh(true);
}

function permission(){
	var rows = grid.getCheckedRecords();
    if (rows.length == 1) {
   	 iconLayer = layer.open({
         type : 2,
         scrollbar: false,
         content : 'role/'+rows[0].id+'/permission',
         area :  ['400px','500px'],
         maxmin : true,
         shift : 4,
         title : '<i class="am-icon-cogs"></i>&nbsp;选择角色资源'
     });
    }else{
 	   layer.msg("你没有选择行或选择了多行数据", {
            icon : 0
        });
    }
}

function delRole(id){
	del("role/"+id+"/delete",search);
}

