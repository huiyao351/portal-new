var settingRole = {
	view: {dblClickExpand: true},
	check: {enable: true},
	async: {
		enable: true,
		url:cur_proj_url+"/config/role/json",
		type: "get"
	},
	callback: {
		onClick: ztreeRoleOnClick,
		onCheck: ztreeRoleOnCheck,
		onAsyncSuccess: zTreeSubOnAsyncSuccess
	}
};

var settingSubRole = {
	view: {dblClickExpand: true},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/sub/roleReljson?subId="+cur_sub_id,
		type: "get"
	},
	edit: {
		enable: true,
		editNameSelectAll: false,
		showRenameBtn: false,
		showRemoveBtn: showRemoveBtn
	},
	callback: {
		beforeRemove: beforeRemove,
		onAsyncSuccess: zTreeSubRoleRelOnAsyncSuccess
	}
};
var cur_sub_roleAry = [];
$(document).ready(function(){
	$.fn.zTree.init($("#"+treeRole), settingRole);
	zTreeRole = $.fn.zTree.getZTreeObj(treeRole);

	$.fn.zTree.init($("#"+treeSubRole), settingSubRole);
	zTreeSubRole = $.fn.zTree.getZTreeObj(treeSubRole);

	//下面是角色树事件
	$('#expandTreeRole').click(function () {
		zTreeRole.expandAll(true);
	});
	$('#collapseTreeRole').click(function () {
		zTreeRole.expandAll(false);
	});
	$('#refreshTreeRole').click(function () {
		resetTreeRole();
	});
	$('#saveTreeRoleRel').click(function () {
		saveSubRoleReldata();
	});


	//下面是主题角色关系预览
	$('#expandTreeSubRole').click(function () {
		zTreeSubRole.expandAll(true);
	});
	$('#collapseTreeSubRole').click(function () {
		zTreeSubRole.expandAll(false);
	});
	$('#refreshTreeSubRole').click(function () {
		resetTreeSubRole();
	});
});
//******************* 以下是角色树操作*****************************
function zTreeSubOnAsyncSuccess(event, treeId, treeNode,msg) {
	//加载右侧的主题角色预览
	initRoleCheck(cur_sub_id);
}
function initRoleCheck(subId){
	cur_sub_id = subId;
	zTreeRole.checkAllNodes(false);
	resetTreeSubRole();
	if(subId){
		//获取节点信息
		var url = cur_proj_url+"/config/sub/roleRelList";
		var param = {subId:subId}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				//加载右侧的主题角色预览
				cur_sub_roleAry = result;
				if(cur_sub_roleAry && cur_sub_roleAry.length > 0){
					for (var i=0;i<cur_sub_roleAry.length;i++) {
						var id=cur_sub_roleAry[i].id;
						var node = zTreeRole.getNodeByParam("id", id, null);
						if(node){
							zTreeRole.checkNode(node, true, true);
						}
					}
				}
			}
		});
	}
}
//树控件的复选框、单选框的check事件
function ztreeRoleOnCheck(event, treeId, treeNode) {
	if(!treeNode.children || treeNode.children.length <= 0){
		zTreeRole.selectNode(treeNode);
	}
}
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeRoleOnClick(event, treeId, treeNode) {
	if (treeNode && (!treeNode.children || treeNode.children.length <= 0)) {
		zTreeRole.checkNode(treeNode, true, true);
	}
}
/**
 * 保存角色数据
 */
function saveSubRoleReldata(){
	//获取选中节点或者是变化节点
	var changeNodes = getChangeNodes(cur_sub_roleAry,zTreeRole);
	if(!changeNodes || changeNodes.length <= 0){
		alert("当前数据无变化，无需保存！");
	}else{
		var url = cur_proj_url+'/config/sub/saveRoleRel?';
		var param = {
			subId:cur_sub_id,
			paramString:$.toJSON(changeNodes)
		};
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:param,
			success:function (data) {
				alert(data.msg);
				if(data.success){
					resetTreeSubRole();
				}
			},
			error:function (data) {
				alert("请求失败！");
			}
		});
	}

}
//******************* 以上是角色树操作*****************************
//******************* 以下是主题角色预览部分操作*****************************
function zTreeSubRoleRelOnAsyncSuccess(event, treeId, treeNode,msg) {
	zTreeSubRole.expandAll(true);
}
function showRemoveBtn(treeId, treeNode) {
	if (treeNode && (!treeNode.children || treeNode.children.length <= 0)) {
		if(treeNode.icon.indexOf("folder.gif") < 0){
			return true;
		}
	}
	return false;
}
function beforeRemove(treeId, treeNode) {
	zTreeSubRole.selectNode(treeNode);
	if(confirm("确认删除“" + treeNode.name + "” 关联关系吗？")){
		var url = cur_proj_url+"/config/sub/delRoleRel";
		var param = {subId:cur_sub_id,roleId:treeNode.id};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				alert(result.msg);
				if(result.success){
					var node = zTreeRole.getNodeByParam("id", treeNode.id, null);
					if(node){
						zTreeRole.checkNode(node, false,true, false);
					}
				}
			}
		});
	}else{
		return false;
	}
}
//******************* 以上是主题角色预览部分操作*****************************
function resetTreeRole() {
	$.fn.zTree.init($("#"+treeRole), settingRole);
	zTreeRole = $.fn.zTree.getZTreeObj(treeRole);
}
function resetTreeSubRole() {
	settingSubRole.async.url = cur_proj_url+"/config/sub/roleReljson?subId="+cur_sub_id;
	$.fn.zTree.init($("#"+treeSubRole), settingSubRole);
	zTreeSubRole = $.fn.zTree.getZTreeObj(treeSubRole);
	$('#subRoleInfoDiv').hide();
}