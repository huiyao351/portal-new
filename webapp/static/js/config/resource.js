/**
 * ztree控件的数据设置
 * @type {{view: {dblClickExpand: boolean}, check: {enable: boolean}, async: {enable: boolean, url: string, type: string}, callback: {onClick: ztreeOnClick, onRightClick: ztreeOnRightClick}}}
 */
var settingResource = {
	view: {
		dblClickExpand: true
	},
	check: {
		enable: false
	},
	async: {
		enable: true,
		url:cur_proj_url+"/config/resource/json"
	},
	data: {
		keep: {
			parent: true
		}
	},
	callback: {
		onClick: ztreeResourceOnClick,
		onRightClick: ztreeResourceOnRightClick
	}
};
$(document).ready(function(){
	$.fn.zTree.init($("#"+treeResource), settingResource);
	zTreeResource = $.fn.zTree.getZTreeObj(treeResource);
	rResource = $("#rResource");

	$('#expandTreeResource').click(function () {
		zTreeResource.expandAll(true);
	});
	$('#collapseTreeResource').click(function () {
		zTreeResource.expandAll(false);
	});
	$('#refreshTreeResource').click(function () {
		resetResource();
	});

	$('#resourceBtn').hide();
	$('#resourceBtn').click(function () {
		saveResourcedata();
	});
	$('#groupBtn').click(function () {
		saveGroupdata();
	});
	$("#rpModal").draggable({
		handle: ".modal-top-bar",
		cursor: 'move',
		refreshPositions: false
	});
});

function showGroupInfo(treeNode){
	$("#groupForm input[type='hidden']").val("");
	$("#groupForm input[type='text']").val("");
	$("#groupForm select").val("");
	if(treeNode.id){
		//获取节点信息
		var url = cur_proj_url+"/config/resource/infoGroup";
		var param = {groupId:treeNode.id}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				initGroupForm(treeNode,result);
			}
		});
	}else{
		//临时增加的节点，需要获取父节点，如果父节点也是临时增加节点，则提示保存父节点
		$('#groupBtn').show();
		$('#g_treeTId').val(treeNode.tId);

		$('#g_businessId').val(treeNode.pid);
		$('#g_businessName').val(treeNode.businessName);
		$('#g_groupName').val(treeNode.name);
	}
	$('#resouceInfo').hide();
	$('#groupInfo').show();
}
function showResourceInfo(treeNode){
	$("#resourceForm input[type='hidden']").val("");
	$("#resourceForm input[type='text']").val("");
	$("#resourceForm select").val("");
	if(treeNode.id){
		//获取节点信息
		var url = cur_proj_url+"/config/resource/info";
		var param = {resourceId:treeNode.id}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				initResourceForm(treeNode,result);
			}
		});
	}else{
		//临时增加的节点，需要获取父节点，如果父节点也是临时增加节点，则提示保存父节点
		$('#resourceBtn').show();
		$('#treeTId').val(treeNode.tId);

		if(treeNode.level==2){
			$('#groupId').val(null);
		}else{
			$('#groupId').val(treeNode.pid);
		}
		$('#businessId').val(treeNode.businessId);
		$('#businessName').val(treeNode.businessName);
		$('#resourceName').val(treeNode.name);
	}
	$('#resouceInfo').show();
	$('#groupInfo').hide();
}

/**
 * 初始化菜单属性表单
 * @param treeNode
 * @param result
 */
function initResourceForm(treeNode,result){
	$('#resourceBtn').show();
	$('#treeTId').val(treeNode.tId);

	$('#resourceId').val(result.resourceId);
	$('#groupId').val(result.groupId);
	$('#businessId').val(result.businessId);
	$('#businessName').val(result.business.businessName);

	$('#resourceName').val(result.resourceName);
	$('#resourceCode').val(result.resourceCode);
	$('#resourceNo').val(result.resourceNo);
	$('#resourceType').val(result.resourceType);
	$('#loadMode').val(result.loadMode);
	$('#resourceUrl').val(result.resourceUrl);
}
/**
 * 初始化菜单属性表单
 * @param treeNode
 * @param result
 */
function initGroupForm(treeNode,result){
	$('#groupBtn').show();
	$('#g_treeTId').val(treeNode.tId);

	$('#g_groupId').val(result.groupId);
	$('#g_businessId').val(result.businessId);
	$('#g_businessName').val(result.business.businessName);

	$('#g_groupName').val(result.groupName);
}

var addCount = 1;
var addCountGroup = 1;
/**
 * 增加节点
 * 两种情况，一种是点击业务增加，正常增加子节点
 * 一种是点击了资源组增加，则在当前资源组父节点内增加
 */
function addGroupNode() {
	hideRResource();
	var newNode = { name:"资源组" + (addCountGroup++),group:true,icon:cur_proj_url+"/static/images/folder.gif"};
	if (zTreeResource.getSelectedNodes()[0]) {
		var curNode = zTreeResource.getSelectedNodes()[0];
		if(curNode.level > 0 && curNode.id){
			if(curNode.level == 1) {
				newNode.pid=curNode.id;
				newNode.businessId = curNode.id;
				newNode.businessName = curNode.name;
				newNode = zTreeResource.addNodes(curNode, newNode);
			}else if(curNode.level == 2) {
				newNode.pid=curNode.pid;
				newNode.businessId = curNode.pid;
				newNode.businessName = curNode.getParentNode().name;
				newNode = zTreeResource.addNodes(curNode.getParentNode(), newNode);
			}
			showGroupInfo(newNode[0]);
			zTreeResource.selectNode(newNode[0]);
		}else{
			alert("请保存当前节点信息！");
		}
	}
}
/**
 * 增加节点
 * 第一种情况：右键业务节点增加资源，新节点的pid是当前节点id，businessid是当前节点id
 * 第二种情况：右键资源组节点增加资源，新节点的pid是当前节点id，businessid是当前节点的pid
 * 第三种情况：右键资源（没有资源组的孤立节点）节点增加资源，新节点的pid是当前节点pid，businessid是当前节点的pid
 * 第四种情况：右键资源（在资源组下的节点）节点增加资源，新节点的pid是当前节点pid，businessid是当前节点的父节点的pid
 */
function addResourceNode() {
	hideRResource();
	var newNode = { name:"资源" + (addCount++),group:false};
	if (zTreeResource.getSelectedNodes()[0]) {
		var curNode = zTreeResource.getSelectedNodes()[0];
		if(curNode.level > 0 && curNode.id){
			if(curNode.level == 1) {
				newNode.pid=curNode.id;
				newNode.businessId = curNode.id;
				newNode.businessName = curNode.name;
				newNode = zTreeResource.addNodes(curNode, newNode);
			}else if(curNode.level == 2){
				if(curNode.group) {
					newNode.pid=curNode.id;
					newNode.businessId = curNode.pid;
					newNode.businessName = curNode.getParentNode().name;
					newNode = zTreeResource.addNodes(curNode, newNode);
				}else{
					newNode.pid=curNode.pid;
					newNode.businessId = curNode.pid;
					newNode.businessName = curNode.getParentNode().name;
					newNode = zTreeResource.addNodes(curNode.getParentNode(), newNode);
				}
			}else if(curNode.level == 3){
				newNode.pid=curNode.pid;
				newNode.businessId = curNode.getParentNode().pid;
				newNode.businessName = curNode.getParentNode().getParentNode().name;
				newNode = zTreeResource.addNodes(curNode.getParentNode(), newNode);
			}
			showResourceInfo(newNode[0]);
			zTreeResource.selectNode(newNode[0]);
		}else{
			alert("请保存当前节点信息！");
		}
	}
}
/**
 * 删除节点
 */
function removeGroupNode() {
	if (confirm("确定要删除该节点吗，不可恢复？")==true){
		hideRResource();
		var nodes = zTreeResource.getSelectedNodes();
		if (nodes && nodes.length>0) {
			var curNode = nodes[0];
			if(curNode && curNode.id){
				if (curNode.children && curNode.children.length > 0) {
					var msg = "是否同时删除该资源组目录下的资源数据？\n\n删除后不可恢复！";
					if (confirm(msg)==true){
						removeCallback(curNode,"delGroup",true);
					}else{
						removeCallback(curNode,"delGroup",false);
					}
					//$('#delGropuModal').modal({show:true});
				} else {
					removeCallback(curNode,"delGroup");
				}
			}else{
				zTreeResource.removeNode(curNode);
			}
		}
	}
}
/**
 * 删除节点
 */
function removeResourceNode() {
	if (confirm("确定要删除该节点吗，不可恢复？")==true){
		hideRResource();
		var nodes = zTreeResource.getSelectedNodes();
		if (nodes && nodes.length>0) {
			var curNode = nodes[0];
			if(curNode){
				if (curNode.children && curNode.children.length > 0) {
					var msg = "确定要删除该资源？操作不可恢复！";
					if (confirm(msg)==true){
						removeCallback(curNode,"del");
					}
				} else {
					removeCallback(curNode,"del");
				}
			}else{
				zTreeResource.removeNode(curNode);
			}
		}
	}
}
/**
 * 删除返回函数
 * @param node
 */
function removeCallback(node,delUrlType,delResource){
	var url = cur_proj_url+'/config/resource/'+delUrlType+'?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{keyId:node.id,delResource:delResource},
		success:function (data) {
			alert(data.msg);
			if(data.success){
				zTreeResource.removeNode(node);
			}
			if (typeof(delResource) != "undefined" && !delResource) {
				resetResource();
			}
		},
		error:function (data) {
			alert("请求失败！");
		}
	});
}

/**
 * 保存菜单数据
 */
function saveGroupdata(){
	var g_groupName = $('#g_groupName').val();
	if(!g_groupName){
		alert("请完整填写资源组名称！");
		return;
	}
	var url = cur_proj_url+'/config/resource/saveGroup?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#groupForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				var node = zTreeResource.getNodeByTId($('#g_treeTId').val());

				node.name=data.group.groupName;
				node.id=data.group.groupId;
				node.pid=data.group.businessId;
				node.businessId=data.group.businessId;
				node.businessName=data.group.business.businessName;
				$('#g_groupId').val(data.group.groupId);
				$('#g_businessId').val(data.group.businessId);
				zTreeResource.updateNode(node);
			}
		},
		error:function (data) {
			alert("保存失败");
		}
	});
}
/**
 * 保存菜单数据
 */
function saveResourcedata(){
	var resourceName = $('#resourceName').val();
	if(!resourceName){
		alert("请完整填写资源名称！");
		return;
	}
	var url = cur_proj_url+'/config/resource/save?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#resourceForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				var node = zTreeResource.getNodeByTId($('#treeTId').val());
				node.name=data.resource.resourceName;
				node.id=data.resource.resourceId;
				if(data.resource.groupId){
					node.pid=data.resource.groupId;
				}else{
					node.pid=data.resource.businessId;
				}
				node.businessId=data.resource.businessId;
				node.businessName=data.resource.business.businessName;
				$('#resourceId').val(data.resource.resourceId);
				$('#groupId').val(data.resource.groupId);
				$('#businessId').val(data.resource.businessId);

				zTreeResource.updateNode(node);
			}
		},
		error:function (data) {
			alert("保存失败");
		}
	});
}
//树控件的树节点的点击事件
/**
 * 点击修改资源组和资源的信息
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeResourceOnClick(event, treeId, treeNode) {
	if (treeNode) {
		zTreeResource.selectNode(treeNode);
		//根据状态显示不同的界面
		if (treeNode.level == 2) {
			if(treeNode.group){
				//资源组
				showGroupInfo(treeNode);
			}else{
				//没有分组的资源
				showResourceInfo(treeNode);
			}
		}else if (treeNode.level == 3) {
			showResourceInfo(treeNode);
		}
	}
}
function ztreeResourceOnRightClick(event, treeId, treeNode) {
	if (treeNode){
		zTreeResource.selectNode(treeNode);
		zTreeResource.checkNode(treeNode, true, true);
		//此处处理逻辑如下：
		//treeNode.level == 1如果选择的是业务文件夹，则可以看到增加菜单，
		//treeNode.level == 2 并且groupId属性为空，如果选择的是业务下面的资源组，则可以看到增加菜单、删除资源组，看不到删除资源，新增资源组的父节点都是该业务节点
		//如果选择的是资源，则只有增删资源，新增资源的父节点是该资源的父节点（可能是业务，也可能是资源组）
		//资源有两种情况，一种treeNode.level == 2 并且groupId属性不为空，
		//一种是treeNode.level == 3

		//对于树控件，相当于只处理界别大于等于1的节点
		if (treeNode.level == 1) {
			showRResourceMenu("business", event.clientX, event.clientY);
		}else if (treeNode.level == 2) {
			if(treeNode.group){
				//资源组
				showRResourceMenu("group", event.clientX, event.clientY);
			}else{
				//没有分组的资源
				showRResourceMenu("resource", event.clientX, event.clientY);
			}
		}else if (treeNode.level == 3) {
			showRResourceMenu("resource", event.clientX, event.clientY);
		}
	}
}
/**
 * 显示右键菜单
 * @param type
 * @param x
 * @param y
 */
function showRResourceMenu(type, x, y) {
	$("#rResource ul li").hide();
	if (type=="business") {
		$("#m_add_group").show();
		$("#m_add_resource").show();
	} else if(type=="group") {
		$("#m_add_group").show();
		$("#m_add_resource").show();
		$("#m_del_group").show();
	} else if(type=="resource") {
		$("#m_add_resource").show();
		$("#m_del_resource").show();
		$("#m_resource_parti").show();
	}
	rResource.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyResourceMouseDown);
}
//隐藏右键菜单
function hideRResource() {
	if (rResource) rResource.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyResourceMouseDown);
}
//右键相关设置
function onBodyResourceMouseDown(event){
	if (!(event.target.id == "rResource" || $(event.target).parents("#rResource").length>0)) {
		rResource.css({"visibility" : "hidden"});
	}
}

/**
 * 选中节点
 * @param checked
 */
function checkTreeNode(checked) {
	var nodes = zTreeResource.getSelectedNodes();
	if (nodes && nodes.length>0) {
		zTreeResource.checkNode(nodes[0], checked, true);
	}
	hideRResource();
}

function resetResource() {
	$.fn.zTree.init($("#"+treeResource), settingResource);
}

/**
 * 清空树控件的选择和选中状态
 * @param treeObj
 */
function clearTreeCheckSelect(treeObj,doCheck,doSelect){
	treeObj.checkAllNodes(false);
	if(doCheck){
		var rCheckNodes = treeObj.getCheckedNodes();
		if(rCheckNodes && rCheckNodes.length > 0){
			for(var i=0;i<rCheckNodes.length;i++){
				var cNode = rCheckNodes[i];
				treeObj.checkNode(cNode, false, true);
			}
		}
	}
	if(doSelect){
		var reNodes = treeObj.getSelectedNodes();
		if(reNodes && reNodes.length > 0){
			for(var i=0;i<reNodes.length;i++){
				var cNode = reNodes[i];
				treeObj.cancelSelectedNode(cNode);
			}
		}
	}
}

//资源分区功能
function resourcePartiNode(){
	hideRResource();
	var nodes = zTreeResource.getSelectedNodes();
	if (nodes && nodes.length>0) {
		var curNode = nodes[0];
		if(curNode){
			if (!(curNode.children && curNode.children.length > 0)) {
				var keyId = curNode.id;
				if(keyId){
					var url = cur_proj_url+'/config/parti?rId='+keyId;
					$('#mainFrame').attr("src", url);
					$('#rpModal').modal({show:true});
				}else{
					alert("请选择一个工作流定义！");
				}
			}
		}
	}
}