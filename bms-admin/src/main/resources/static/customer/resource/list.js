var dtGridColumns = [{
    id : 'name',
    title : '资源名称',
    type : 'string',
    columnClass : 'text-left',
    headerStyle : 'color:grey;'
},{
    id : 'sourceKey',
    title : '标识',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'type',
    title : '类型',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    resolution:function(value){
        if(value=='0'){
        	return '目录';
        }else if(value=='1'){
        	return '菜单';
        }else if(value=='2'){
        	return '按钮';
        }else{
        	return '未知';
        }
    }
},{
    id : 'icon',
    title : '图标',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    resolution:function(value){
        return '<i class="'+value+'"></i>';
    }
},{
    id : 'sourceUrl',
    title : 'URL',
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
    id : 'operation',
    title : '操作',
    type : 'string',
    columnClass : 'text-center tpl-table-black-operation',
    headerStyle : 'color:grey;',
    resolution:function(value, record, column, grid, dataNo, columnNo){
        var content = '';
        content += '<a href="#" onclick="loadPage(\'resource/'+record.id+'/select\');" shiro:hasPermission="role:edit"> <i class="am-icon-pencil"></i> 编辑</a> ';
        content += '  ';
        content += '<a href="#" onclick="delResource('+record.id+');" class="tpl-table-black-operation-del del-btn" shiro:hasPermission="role:delete"><i class="am-icon-trash"></i> 删除</a>';
        return content;
    }
},{
    id : 'updateTime',
    title : '更新时间',
    type : 'date',
    format:'yyyy-MM-dd hh:mm:ss', 
    otype:'time_stamp_ms', 
    oformat:'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    hideType : 'xs|sm|md|lg'
}];

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    checkWidth :'37px',
    check : true,
    isTreeGrid : true,
    isLeafColumn : 'last',
    iconColumn : 'name',
    indexKey : 'id',//主键id 列名,默认值为id
    parentId : 'parentId',//默认值为'parentId'
    loadURL : 'resource/list',
    columns : dtGridColumns,
    gridContainer : 'dtGridContainer',
    toolbarContainer : 'dtGridToolBarContainer',
    tools : '',
    pageSize : 10,
    pageSizeLimit : [10, 20, 30]
};
var grid = $.fn.dlshouwen.grid.init(dtGridOption);


$(function() {
	grid.load();
	$('select').selected();
});

function search(){
	  grid.parameters = new Object();
	  grid.parameters['s_name'] = $("#name").val();
	  var type = $('#type').val()
	  if(type.length>0 && type!='-1'){
		  grid.parameters['s_type'] = type;
	  }
	  grid.refresh(true);
}

function delResource(id){
	del("resource/"+id+"/delete",search);
}

