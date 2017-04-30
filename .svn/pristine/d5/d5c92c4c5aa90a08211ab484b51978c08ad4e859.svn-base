/**
 * ztree控件的数据设置
 */
var settingMenu = {
	view: {dblClickExpand: true},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/menuAuth/menujson?roleId="+_roleId,
		type: "get"
	},
	callback: {
		onClick: ztreeMenuOnClick,
		onAsyncSuccess: zTreeMenuOnAsyncSuccess
	}
};
$(document).ready(function(){
	$.fn.zTree.init($("#"+treeMenu), settingMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj(treeMenu);

	//下面是菜单树事件
	$('#expandTreeMenu').click(function () {
		zTreeMenu.expandAll(true);
	});
	$('#collapseTreeMenu').click(function () {
		zTreeMenu.expandAll(false);
	});
	$('#refreshTreeMenu').click(function () {
		resetTreeMenu();
	});
});

//*****************************************************************
//********************左边菜单树相关操作*****************************
//*****************************************************************
function zTreeMenuOnAsyncSuccess(event, treeId, treeNode, msg) {
	//默认选中第一个角色
	var fRoleNode = zTreeMenu.getNodes();
	if(fRoleNode && fRoleNode.length > 0){
		zTreeMenu.selectNode(fRoleNode[0]);
		ztreeMenuOnClick(null, null, fRoleNode[0]);
	}
	zTreeMenu.expandAll(true);
}

/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeMenuOnClick(event, treeId, treeNode) {
	zTreeMenu.selectNode(treeNode);

	//加载该资源下资源分区，之后选择资源分区对应的功能
	initPartiRow(treeNode.id);

	//根据状态显示不同的界面
	//通过后台加载该角色关联的资源记录，之后在资源树上进行打钩
	var url = cur_proj_url+'/config/menuAuth/reljson?';
	$.ajax({
		url:url,
		type:'get',
		dataType:'json',
		data:{menuId:treeNode.id},
		success:function (data) {
			if(data && data.length > 0){
				for(var i=0;i<data.length;i++){
					var rid = data[i].id;
				}
			}
		},
		error:function (data) {
			alert("请求失败！");
		}
	});
}
/**
 * 根据菜单获取菜单对应的资源的功能分区
 * @param resourceId
 */
function initPartiRow(menuId){
	$("#rpTbody").html("");
	if(menuId){
		var url = cur_proj_url+"/config/menuAuth/partiOperInfo";
		var param = {menuId:menuId,roleId:_roleId};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (data) {
				if(data && data.length > 0){
					for(var i=0;i<data.length;i++){
						var keyId = data[i].partitionId;
						var newRow = '<tr class="detailInfo">';
						newRow += '<td>'+data[i].partitionName+'</td>';
						newRow += '<td>'+initType(data[i].partitionType)+'</td>';
						newRow += '<td>';
						//operateType
						newRow += initOperatType(data[i].operateType);
						//newRow += initOperatType(menuId,_roleId);
						newRow += '</td>';
						newRow += '</tr>';
						$("#rpTbody").append(newRow);
					}
				}
			}
		});
	}
}

function resetTreeMenu() {
	$.fn.zTree.init($("#"+treeMenu), settingMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj(treeMenu);
}

function initType(type){
	if(partitionTypeJson){
		for(var i=0;i<partitionTypeJson.length;i++){
			if(partitionTypeJson[i].value==type){
				return partitionTypeJson[i].name;
			}
		}
	}
	return type;
}
function initOperatType(type){
	if(partitionOperatTypeJson){
		for(var i=0;i<partitionOperatTypeJson.length;i++){
			if(partitionOperatTypeJson[i].value==type){
				return partitionOperatTypeJson[i].name;
			}
		}
	}
	return type;
}
