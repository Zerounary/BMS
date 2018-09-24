var dtGridColumns = [{
    id : 'name',
    title : '部门简称',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'fullname',
    title : '部门全称',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'pname',
    title : '上级部门',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
},{
    id : 'tips',
    title : '备注',
    type : 'string',
    columnClass : 'text-center',
    headerStyle : 'color:grey;'
}];

var dtGridOption = {
    lang : 'zh-cn',
    ajaxLoad : true,
    checkWidth :'37px',
    check : true,
    loadURL : 'dept/list',
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
function search(pid){
	  grid.parameters = new Object();
	  grid.parameters['pid'] = pid;
	  grid.refresh(true);
}

function btnDel(){
	   var rows = grid.getCheckedRecords();
 if (rows.length == 1) {
	   del('dept/'+rows[0].id+'/delete',search);
 }else{
	   layer.msg("你没有选择行或选择了多行数据", {
         icon : 0
     });
 }
}
function btnEdit(){
	   var rows = grid.getCheckedRecords();
 if (rows.length == 1) {
 	loadPage('dept/'+rows[0].id+'/select');
 }else{
	   layer.msg("你没有选择行或选择了多行数据", {
         icon : 0
     });
 }
}

