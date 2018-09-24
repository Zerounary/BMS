var dtGridColumns = [{
    id : 'id',
    title : '编号',
    type : 'number',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'accountName',
    title : '账户名',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'ip',
    title : 'IP',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'remark',
    title : '说明',
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
    id : 'loginTime',
    title : '登录时间',
    type : 'date',
    format:'yyyy-MM-dd hh:mm:ss', 
    otype:'time_stamp_ms', 
    oformat:'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
}];

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    check : true,
    checkWidth :'37px',
    loadURL : 'loginlog/list',
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
	del("loginlog/deleteBatch",search);
}



