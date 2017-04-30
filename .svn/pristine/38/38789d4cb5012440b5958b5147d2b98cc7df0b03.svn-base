/**
 * ztree控件的数据设置
 */
var settingBusiGroup = {
	view: {showLine: false},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/busi/groupjson",
		type: "get"
	},
	data: {simpleData: {enable: true}},
	callback: {
		onClick: ztreeBusiGroupOnClick
	}
};
var settingBusi = {
	view: {showLine: false,dblClickExpand: true},
	check: {enable: true},
	async: {
		enable: true,
		url:cur_proj_url+"/config/busi/json?",
		type: "get"
	},
	callback: {
		onClick: ztreeBusiOnClick,
		onCheck: ztreeBusiOnCheck,
		onAsyncSuccess: zTreeBusiOnAsyncSuccess
	}
};
$(document).ready(function(){
	$.fn.zTree.init($("#"+treeBusiGroup), settingBusiGroup);
	zTreeBusiGroup = $.fn.zTree.getZTreeObj(treeBusiGroup);

	$.fn.zTree.init($("#"+treeBusi), settingBusi);
	zTreeBusi = $.fn.zTree.getZTreeObj(treeBusi);

	////下面是主题操作事件
	$('#addTreeBusiGroup').click(function () {
		addTreeBusiGroupItem();
	});
	$('#busiGroupBtn').click(function () {
		saveBusiGroupdata();
	});
	$('#deleteTreeBusiGroup').click(function () {
		deleteTreeBusiGroup();
	});

	$('#saveTreeBusiGroupRel').click(function () {
		saveBusiGroupReldata();
	});

	$('#busiGroupBtn').hide();
});
//******************* 以下是主题树相关操作*****************************
function zTreeBusiGroupOnAsyncSuccess(event, treeId, treeNode, msg) {
}

var cur_busi_group_relAry = [];
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeBusiGroupOnClick(event, treeId, treeNode) {
	$("#busiGroupForm input[type='hidden']").val("");
	$("#busiGroupForm input[type='text']").val("");
	zTreeBusiGroup.selectNode(treeNode);
	zTreeBusi.checkAllNodes(false);
	if(treeNode.id){
		//获取节点信息
		var url = cur_proj_url+"/config/busi/groupinfo";
		var param = {groupId:treeNode.id}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				$('#busiGroupBtn').show();
				$('#treeTId').val(treeNode.tId);
				$('#businessGroupId').val(result.group.businessGroupId);
				$('#businessIds').val(result.group.businessIds);
				$('#businessGroupName').val(result.group.businessGroupName);
				$('#businessGroupNo').val(result.group.businessGroupNo);

				initBusiGroupRel(result.relList);
			}
		});
	}else{
		//临时增加的节点，需要获取父节点，如果父节点也是临时增加节点，则提示保存父节点
		$('#busiGroupBtn').show();
		$('#treeTId').val(treeNode.tId);
		$('#subsystemTitle').val(result.name);
		cur_sub_id = "";
	}
}
function initBusiGroupRel(relList){
	if(relList && relList.length > 0){
		cur_busi_group_relAry = relList;
		for (var i=0;i<relList.length;i++){
			var id=relList[i].value;
			var node = zTreeBusi.getNodeByParam("id", id, null);
			if(node){
				zTreeBusi.checkNode(node, true, true);
			}
		}
	}
}
/**
 * 增加节点
 */
function addTreeBusiGroupItem() {
	$("#busiGroupForm input[type='hidden']").val("");
	$("#busiGroupForm input[type='text']").val("");
	$('#busiGroupBtn').show();
	zTreeBusi.checkAllNodes(false);
}
/**
 * 保存菜单数据
 */
function saveBusiGroupdata(){
	//数据必填，不能为空
	var title = $('#businessGroupName').val();
	var bsgNo = $('#businessGroupNo').val();
	if(!title || !bsgNo){
		alert("请完整填写业务分组名称和编号！");
		return;
	}

	var url = cur_proj_url+'/config/busi/savegroup?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#busiGroupForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				var node = zTreeBusiGroup.getNodeByTId($('#treeTId').val());
				if(node){
					node.name=data.group.businessGroupName;
					node.id=data.group.businessGroupId;
					node.kz1=data.group.businessGroupNo;
					zTreeBusiGroup.updateNode(node);
				}else{
					var newNode={id:data.group.businessGroupId,name:data.group.businessGroupName,kz1:data.group.businessGroupNo};
					zTreeBusiGroup.addNodes(null, newNode);
				}
			}

		},
		error:function (data) {
			alert("保存失败");
		}
	});
}
/**
 * 删除节点
 */
function deleteTreeBusiGroup() {
	if (confirm("确定要删除该节点吗，不可恢复？")==true){
		var nodes = zTreeBusiGroup.getSelectedNodes();
		if (nodes && nodes.length>0) {
			var curNode = nodes[0];
			if(curNode && curNode.id){
				removeCallback(curNode);
			}else{
				zTreeBusiGroup.removeNode(curNode);
				addTreeBusiGroupItem();
			}
		}
	}
}
/**
 * 删除返回函数
 * @param node
 */
function removeCallback(node){
	var url = cur_proj_url+'/config/busi/delgroup?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{groupId:node.id},
		success:function (data) {
			alert(data.msg);
			if(data.success){
				zTreeBusiGroup.removeNode(node);
				addTreeBusiGroupItem();
			}
		},
		error:function (data) {
			alert("请求失败！");
		}
	});
}
//******************************************************************
//******************* 以下是部门树树操作*****************************
function zTreeBusiOnAsyncSuccess(event, treeId, treeNode, msg) {
	//默认选中第一个
	var nodes = zTreeBusiGroup.getNodes();
	if(nodes && nodes.length > 0){
		var node = nodes[0];
		zTreeBusiGroup.selectNode(node);
		ztreeBusiGroupOnClick(null,null,node);
	}
}
//树控件的复选框、单选框的check事件
function ztreeBusiOnCheck(event, treeId, treeNode) {
	if(!treeNode.children || treeNode.children.length <= 0){
		zTreeBusi.selectNode(treeNode);
	}
}
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeBusiOnClick(event, treeId, treeNode) {
	if (treeNode && (!treeNode.children || treeNode.children.length <= 0)) {
		zTreeBusi.checkNode(treeNode, true, true);
	}
}
/**
 * 保存菜单数据
 */
function saveBusiGroupReldata(){
	var busiGroupNodes= zTreeBusiGroup.getSelectedNodes();
	if(busiGroupNodes.length==0){
		alert("请选中业务分组进行操作!");
		return;
	}
	var busiGroupNode = busiGroupNodes[0];
	//获取选中节点或者是变化节点
	var changeNodes = [];
	//处理状态变化的节点中，当前角色管理记录的数据，如果选中，则修改为不选中，因为不需要传给后台
	//如果未选中，则表示删除了关联关系，需要传递到后台
	var cur_busi_group_relIdStr = "";
	if(cur_busi_group_relAry && cur_busi_group_relAry.length > 0){
		for(var i=0;i<cur_busi_group_relAry.length;i++){
			var rid = cur_busi_group_relAry[i].value;
			cur_busi_group_relIdStr += rid+";";
			var cNode = zTreeBusi.getNodeByParam("id", rid, null);
			if(cNode.checked){
				//zTreeResource.checkNode(cNode, false, false);
			}else{
				var node = {
					id:cur_busi_group_relAry[i].value,
					name:cur_busi_group_relAry[i].name,
					checked:false
				}
				changeNodes.push(node);
			}
		}
	}
	var nodes = zTreeBusi.getChangeCheckedNodes();
	if(nodes && nodes.length > 0){
		for(var i=0;i<nodes.length;i++){
			if(!(nodes[i].checked && cur_busi_group_relIdStr.indexOf(nodes[i].id)>-1)){
				var node = {
					id:nodes[i].id,
					name:nodes[i].name,
					checked:nodes[i].checked
				}
				changeNodes.push(node);
			}
		}
	}
	if(!changeNodes || changeNodes.length <= 0){
		alert("当前数据无变化，无需保存！");
	}else{
		var url = cur_proj_url+'/config/busi/saveBusiGruopRel?';
		var param = {
			groupId:busiGroupNode.id,
			paramString:$.toJSON(changeNodes)
		};
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:param,
			success:function (data) {
				alert(data.msg);
				ztreeBusiGroupOnClick(null, null, busiGroupNode);
			},
			error:function (data) {
				alert("请求失败！");
			}
		});
	}
}
//*****************************************************************
/*
刷新树
 */
function refreshTreeBusi(){
	zTreeBusi.reAsyncChildNodes(null, "refresh");
}

function ztree_check_integer(obj,code) {
	if(obj && obj.value){
		if (/^(\+|-)?\d+$/.test(obj.value)){
			return true;
		}else{
			f_alert(code,"请输入数字");
			obj.value = "";
			return false;
		}
	}
	return false;
}