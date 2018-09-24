var dict;
var grid;
$(function() {
	dict = getDicts('scheduleStatus');
	var dtGridColumns = [{
	    id : 'id',
	    title : '编号',
	    type : 'number',
	    columnClass : 'text-center',
	    headerStyle:'color:grey;'
	},{
	    id : 'jobName',
	    title : '任务名称',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	},{
	    id : 'jobGroup',
	    title : '任务分组',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	    
	},{
	    id : 'cron',
	    title : '表达式',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	},{
	    id : 'status',
	    title : '任务状态',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;',
	    codeTable:dict.scheduleStatus

	},{
	    id : 'beanName',
	    title : '执行Bean',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	},{
	    id : 'methodName',
	    title : '执行方法',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	},{
	    id : 'remarks',
	    title : '描述',
	    type : 'string',
	    columnClass : 'text-center',
	    headerStyle : 'color:grey;'
	},{
	    id : 'updateTime',
	    title : '更新时间',
	    type : 'string',
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
	        if(record.status=='2'){
	        	content += '<a href="" onclick="return confirm(\'确认要恢复该任务吗？\', \'schedule/'+record.id+'/change/1\')" title="恢复"><span class="am-text-success am-icon-reply"></span></a>';
	        }else if(record.status=='1'){
	        	content += '<a href="" onclick="return confirm(\'确认要立即执行该任务吗？\',\'schedule/'+record.id+'/run\')" title="立即执行"><span class="am-text-success am-icon-play-circle"></span></a> ';
	        	content += '<a href="" onclick="return confirm(\'确认要停止该任务吗？\', \'schedule/'+record.id+'/change/0\')" title="停止"><span class="am-text-danger am-icon-stop"></span></a> ';
	        	content += '<a href="" onclick="return confirm(\'确认要暂停该任务吗？\',\'schedule/'+record.id+'/change/2\')" title="暂停"><span class="am-text-warning am-icon-pause"></span></a>';
	        }else if(record.status=='0'){
	        	content += '<a href="" onclick="return confirm(\'确认要启用该任务吗？\',\'schedule/'+record.id+'/change/1\')" title="启用"><span class="am-text-success am-icon-play"></span></a > ';
	        	content += '<a href="javascript:;" onclick="loadPage(\'schedule/'+record.id+'/select\');" title="修改"><span class="am-icon-pencil"></span></a> ';
	        	content += '<a href="" onclick="return confirm(\'确认要删除该条数据吗？\', \'schedule/'+record.id+'/delete\')" title="删除"><span class="am-text-danger am-icon-trash-o"></span></a>';
	        }
	        return content;
	    }
	}];
	var dtGridOption = {
		    lang : 'zh-cn',
		    ajaxLoad : true,
		    check : true,
		    checkWidth :'37px',
		    loadURL : 'schedule/list',
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
});

function search(){
	  grid.parameters = new Object();
	  grid.parameters['title'] = $("#title").val();
	  grid.refresh(true);
}

function delteAll(){
	var checkId = getCheckAll(grid.getCheckedRecords());
	if(checkId!=''){
		del("schedule/"+checkId+"/deleteBatch",search);
	}else{
		 layer.msg('请选择要删除的数据', {
             icon : 3
         });
	}
}
function confirm(msg, href) {
	layer.confirm(msg, {
		shade:0.01,
		offset: '30px',
		btn : [ '确定', '取消' ]
	}, function() {
		get(href,search);
	});
	return false;
}

