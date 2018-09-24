var dtGridColumns = [{
    id : 'name',
    title : '名称',
    type : 'string',
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
          content += '<a href="#" onclick="loadPage(\'test/'+record.id+'/select\');"> <i class="am-icon-pencil"></i> 编辑</a> ';
          content += '  ';
          content += '<a href="#" onclick="delResource('+record.id+');" class="tpl-table-black-operation-del del-btn"><i class="am-icon-trash"></i> 删除</a>';
          return content;
    }
}];

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    checkWidth :'37px',
    check : true,
    isTreeGrid : true,
    isLeafColumn : 'last',
    iconColumn : 'code',
    indexKey : 'id',//主键id 列名,默认值为id
    parentId : 'pid',//默认值为'parentId'
    loadURL : 'test/list',
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
	  grid.parameters['name'] = $("#name").val();
	  grid.refresh(true);
}
function toEidt(){
	 var rows = grid.getCheckedRecords();
	    if (rows.length>1) {
	    	layer.msg("请选择一条记录", {icon : 0});
	    }else{
	    	if(rows.length==1){
	    		loadPage('test/' + rows[0].id + '/select');
	    	}else{
	    		loadPage('test/listUI');
	    	}
	    }
}
function deltest(id){
	del("test/"+id+"/delete",search);
}
function toAdd(){
    var rows = grid.getCheckedRecords();
    if (rows.length>1) {
    	layer.msg("请选择一条记录", {icon : 0});
    }else{
    	if(rows.length==1){
    		if(rows[0].pid!=0){
    			layer.msg("请选择根目录", {icon : 0});
    		}else{
    			loadPage('test/form?pid='+rows[0].id+'&code='+rows[0].code);
    		}
    	}else{
    		loadPage('test/form');
    	}
    }
}
function delResource(id){
	del("test/"+id+"/delete",search);
}

