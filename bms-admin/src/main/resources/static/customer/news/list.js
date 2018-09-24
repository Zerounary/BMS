var dtGridColumns = [{
    id : 'title',
    title : '标题',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'coverImg',
    title : '封面',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    resolution:function(value){
       return '<img src="'+value+'" class="tpl-table-line-img" alt=""/>';
    }
    
},{
    id : 'descs',
    title : '描述',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'isShow',
    title : '状态',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    resolution:function(value){
        if(value==1){
        	return '显示';
        }else{
        	return '隐藏';
        }
    }
},{
    id : 'createUser',
    title : '创建者',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    hideType : 'xs|sm|md|lg'
},{
    id : 'createTime',
    title : '创建时间',
    type : 'date',
    format:'yyyy-MM-dd hh:mm:ss', 
    otype:'time_stamp_ms', 
    oformat:'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    hideType : 'xs|sm|md|lg'
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
},{
    id : 'updateUser',
    title : '更新人',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;',
    hideType : 'xs|sm|md|lg'
},{
    id : 'operation',
    title : '操作',
    type : 'string',
    columnClass : 'text-center tpl-table-black-operation',
    headerStyle : 'color:grey;',
    resolution:function(value, record, column, grid, dataNo, columnNo){
        var content = '';
        content += '<a href="#" onclick="loadPage(\'news/'+record.id+'/select\');"> <i class="am-icon-pencil"></i> 编辑</a> ';
        return content;
    }
}];

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    loadURL : 'news/list',
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
	  grid.parameters['title'] = $("#title").val();
	  grid.refresh(true);
}

function delteAll(){
	var checkId = getCheckAll(grid.getCheckedRecords());
	if(checkId!=''){
		del("news/"+checkId+"/deleteBatch",search);
	}else{
		 layer.msg('请选择要删除的数据', {
             icon : 3
         });
	}
}

