var dtGridColumns = [{
    id : 'id',
    title : '编号',
    type : 'number',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'accountName',
    title : '用户',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'operateIp',
    title : 'IP',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'requestUri',
    title : '请求地址',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'method',
    title : '请求方式',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'times',
    title : '耗时',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'status',
    title : '状态',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'operateTime',
    title : '操作时间',
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
    	return  '<a href="javascript:;" onclick="loadPage(\'loginfo/'+record.id+'/select\');" title="查看"><span class="am-text-success am-icon-eye"></span></a>';
    }
}];

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    loadURL : 'loginfo/list',
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
});
function search(){
	  grid.parameters = new Object();
	  grid.parameters['account_name'] = $("#accountName").val();
	  grid.refresh(true);
}

function delteAll(){
	del("loginfo/deleteBatch",search);
}



