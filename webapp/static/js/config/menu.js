/**
 * ztree控件的数据设置
 * @type {{view: {dblClickExpand: boolean}, check: {enable: boolean}, async: {enable: boolean, url: string, type: string}, callback: {onClick: ztreeOnClick, onRightClick: ztreeOnRightClick}}}
 */
var settingMenu = {
	view: {
		dblClickExpand: true
	},
	check: {
		enable: false
	},
	async: {
		enable: true,
		url:cur_proj_url+"/config/menu/json",
		type: "get"
	},
	callback: {
		onClick: ztreeOnClick,
		onRightClick: ztreeOnRightClick
	}
};
var settingResource = {
	view: {
		dblClickExpand: true
	},
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	async: {
		enable: true,
		url:cur_proj_url+"/config/resource/json",
		type: "get"
	},
	callback: {
		onRightClick: ztreeResourceOnRightClick,
		onCheck: ztreeResourceOnCheck,
		onClick: ztreeResourceOnClick
	}
};
$(document).ready(function(){
	$.fn.zTree.init($("#"+treeMenu), settingMenu);
	zTree = $.fn.zTree.getZTreeObj(treeMenu);
	rMenu = $("#rMenu");

	$.fn.zTree.init($("#"+treeResource), settingResource);
	zTreeResource = $.fn.zTree.getZTreeObj(treeResource);
	rResource = $("#rResource");

	$('#expandTreeMenu').click(function () {
		zTree.expandAll(true);
	});
	$('#collapseTreeMenu').click(function () {
		zTree.expandAll(false);
	});
	$('#refreshTreeMenu').click(function () {
		resetTree();
	});
	$('#expandTreeResource').click(function () {
		zTreeResource.expandAll(true);
	});
	$('#collapseTreeResource').click(function () {
		zTreeResource.expandAll(false);
	});
	$('#refreshTreeResource').click(function () {
		resetResource();
	});
	$('#saveTreeResource').click(function () {
		//保存菜单与资源的关联关系
		saveCurResourceRel();
	});
	$('#showTreeResource').click(function () {
		//修改当前状态，如果当前窗口是资源窗口，则改为属性，如果是属性则改为资源
		//修改文字、修改颜色，用于标记选中状态，该按钮值作为状态标示
		var treeNode = null;
		if (zTree.getSelectedNodes()[0]) {
			treeNode = zTree.getSelectedNodes()[0];
		}
		var panel = $(this).attr("right-panel");
		if(panel != null && panel == "resource"){
			$(this).css({"color":"#fff"});
			$(this).attr("right-panel","info");
			$(this).html('<i class="icon-credit-card"></i>属性');
			showMenuInfo(treeNode);
		}else{
			$(this).css({"color":"#000"});
			$(this).attr("right-panel","resource");
			$(this).html('<i class="icon-credit-card"></i>资源');
			showResourceInfo(treeNode);
		}
	});


	$('#menuBtn').hide();
	$('#menuBtn').click(function () {
		saveMenudata();
	});
});

/**
 * 显示右键菜单
 * @param type
 * @param x
 * @param y
 */
function showRMenu(type, x, y) {
	if(type){
		$("#rMenu ul li").hide();
		$("#rMenu ul").show();
		if (type=="root") {
			$("#m_add").show();
		} else if(type=="norel") {
			$("#m_add").show();
		} else if(type=="node"){
			$("#m_add").show();
			$("#m_del").show();
			$("#m_resource_rel").show();
		}
		rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
		$("body").bind("mousedown", onBodyMouseDown);
	}
}
//隐藏右键菜单
function hideRMenu() {
	if (rMenu) rMenu.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}
//右键相关设置
function onBodyMouseDown(event){
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
		rMenu.css({"visibility" : "hidden"});
	}
}

/**
 * 右键
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeOnRightClick(event, treeId, treeNode) {
	if(treeNode && treeId){
		if (treeNode.level == 0) {
			//zTree.cancelSelectedNode();
			zTree.selectNode(treeNode);
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode.level == 1 && treeNode.children && treeNode.children.length > 0) {
			zTree.selectNode(treeNode);
			showRMenu("norel", event.clientX, event.clientY);
		} else{
			zTree.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY);
		}
	}
}
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeOnClick(event, treeId, treeNode) {
	zTree.selectNode(treeNode);
	//根据状态显示不同的界面
	var panel = $('#showTreeResource').attr("right-panel");
	if(panel != null && panel == "resource"){
		showResourceInfo(treeNode);
	}else{
		showMenuInfo(treeNode);
	}
}

function showMenuInfo(treeNode){
	$("#menuForm input[type='hidden']").val("");
	$("#menuForm input[type='text']").val("");
	$('#menuInfoDiv').show();
	$('#resourceTreeDiv').hide();
	if (!treeNode || treeNode.id == "1") {
		$('#menuBtn').hide();
		$("#menuForm")[0].reset();
		return;
	}else{
		if(treeNode.id){
			//获取节点信息
			var url = cur_proj_url+"/config/menu/info";
			var param = {menuId:treeNode.id}
			$.ajax({
				type: "get",
				url: url,
				data: param,
				async: false,
				success: function (result) {
					initMenuForm(treeNode,result);
				}
			});
		}else{
			//临时增加的节点，需要获取父节点，如果父节点也是临时增加节点，则提示保存父节点
			$('#menuBtn').show();
			$('#treeTId').val(treeNode.tId);
			$('#menuParentId').val(treeNode.pid);
			$('#menuName').val(treeNode.name);
		}
	}
}

function showResourceInfo(treeNode){
	hideRMenu();
	$('#menuInfoDiv').hide();
	$('#resourceTreeDiv').show();
	//获取资源id
	//zTreeResource.expandAll(false);
	clearTreeCheckSelect(zTreeResource,true,true);
	if(!treeNode){
		return;
	}
	if(!treeNode.id){
		alert("请保存该菜单信息之后在进行关联操作！");
		return;
	}
	var url = cur_proj_url+"/config/menu/info";
	var param = {menuId:treeNode.id};
	$.ajax({
		type: "get",
		url: url,
		data: param,
		async: false,
		success: function (result) {
			var rid = result.resourceId;
			var rnode = zTreeResource.getNodeByParam("id", rid, null);
			if(rnode){
				zTreeResource.expandNode(rnode.getParentNode(), true, false, true);
				zTreeResource.checkNode(rnode, true, true);
				zTreeResource.selectNode(rnode);
			}
		}
	});
}

/**
 * 初始化菜单属性表单
 * @param treeNode
 * @param result
 */
function initMenuForm(treeNode,result){
	$('#menuBtn').show();
	$('#treeTId').val(treeNode.tId);
	$('#menuId').val(result.menuId);
	$('#menuParentId').val(result.menuParentId);
	$('#menuName').val(result.menuName);
	$('#menuOrder').val(result.menuOrder);
	$('#menuOrderHid').val(result.menuOrder);

	$('#resourceId').val(result.resourceId);
	$('#resourceName').val(result.resource.resourceName);
	$('#resourceUrl').val(result.resource.resourceUrl);
	$('#businessName').val(result.resource.business.businessName);
}

var addCount = 1;
/**
 * 增加节点
 */
function addTreeNode() {
	$("#menuForm input[type='hidden']").val("");
	$("#menuForm input[type='text']").val("");
	hideRMenu();
	var newNode = { name:"增加" + (addCount++)};
	if (zTree.getSelectedNodes()[0]) {
		var parentNode = zTree.getSelectedNodes()[0];
		if(parentNode.id){
			newNode.pid=parentNode.id;
			newNode.checked = zTree.getSelectedNodes()[0].checked;
			newNode = zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
		}else{
			alert("请保存父节点信息！");
		}
	}
	showMenuInfo(newNode[0]);
	zTree.selectNode(newNode[0]);
}
/**
 * 删除节点
 */
function removeTreeNode() {
	if (confirm("确定要删除该节点吗，不可恢复？")==true){
		hideRMenu();
		var nodes = zTree.getSelectedNodes();
		if (nodes && nodes.length>0) {
			var curNode = nodes[0];
			if(curNode && curNode.id){
				if (curNode.children && curNode.children.length > 0) {
					var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
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
				zTree.removeNode(curNode);
			}
		}
	}
}
/**
 * 删除返回函数
 * @param node
 */
function removeCallback(node){
	var url = cur_proj_url+'/config/menu/del?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{menuId:node.id},
		success:function (data) {
			alert(data.msg);
			if(data.success){
				zTree.removeNode(node);
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
function saveMenudata(){
	var menuName = $('#menuName').val();
	var menuOrder = $('#menuOrder').val();
	if(!menuName || !menuOrder){
		alert("请完整填写菜单标题和排序码信息！");
		return;
	}
	var menuOrderObj = document.getElementById("menuOrder");
	if(ztree_check_integer(menuOrderObj,'排序码')){
		var url = cur_proj_url+'/config/menu/save?';
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:$("#menuForm").serialize(),
			success:function (data) {
				alert(data.msg);
				if(data.success){
					var node = zTree.getNodeByTId($('#treeTId').val());
					node.name=data.menu.menuName;
					node.id=data.menu.menuId;
					node.pid=data.menu.menuParentId;
					$('#menuId').val(data.menu.menuId);
					$('#menuParentId').val(data.menu.menuParentId);
					zTree.updateNode(node);
				}
			},
			error:function (data) {
				alert("保存失败");
			}
		});
	}
}

function openResourceTree(){
	//获取该菜单的资源信息，用于将右侧资源树中对应的资源设置为选中状态，最好能定位到该资源
	//获取节点信息
	var nodes = zTree.getSelectedNodes();
	if(nodes && nodes.length > 0){
		showResourceInfo(nodes[0]);
	}
}

function ztreeResourceOnRightClick(event, treeId, treeNode) {
	if (treeNode && (!treeNode.children || treeNode.children.length <= 0)) {
		zTreeResource.selectNode(treeNode);
		zTreeResource.checkNode(treeNode, true, true);
		showRResourceMenu("node", event.clientX, event.clientY);
	}
}
/**
 * 显示右键菜单
 * @param type
 * @param x
 * @param y
 */
function showRResourceMenu(type, x, y) {
	$("#rResource ul").show();
	rResource.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyResourceMouseDown);
}
//隐藏右键菜单
function hideRResourceMenu() {
	if (rResource) rResource.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyResourceMouseDown);
}
//右键相关设置
function onBodyResourceMouseDown(event){
	if (!(event.target.id == "rResource" || $(event.target).parents("#rResource").length>0)) {
		rResource.css({"visibility" : "hidden"});
	}
}
//树控件的复选框、单选框的check事件
function ztreeResourceOnCheck(event, treeId, treeNode) {
	event.srcEvent.clientX = event.srcEvent.clientX-100;
	ztreeResourceOnClick(event.srcEvent, treeId, treeNode);
}
//树控件的树节点的点击事件
function ztreeResourceOnClick(event, treeId, treeNode) {
	//alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
	clearTreeCheckSelect(zTreeResource,true,true);
	if (treeNode && (!treeNode.children || treeNode.children.length <= 0)) {
		zTreeResource.selectNode(treeNode);
		zTreeResource.checkNode(treeNode, true, true);
		showRResourceMenu("node", event.clientX+40, event.clientY-10);
	}
}
/**
 * 保存选中的资源和菜单的关联关系
 */
function saveCurResourceRel(){
	hideRResourceMenu();
	//获取资源id
	//获取节点信息,需要获取当前的菜单和资源id，传递后台进行关联
	var url = cur_proj_url+"/config/menu/saveRel";
	var menuNodes = zTree.getSelectedNodes();
	var reNodes = zTreeResource.getSelectedNodes();
	if(menuNodes && menuNodes.length > 0 && reNodes && reNodes.length > 0){
		var menuNode = menuNodes[0];
		var reNode = reNodes[0];
		if (menuNode && (!menuNode.children || menuNode.children.length <= 0) && reNode && (!reNode.children || reNode.children.length <= 0)) {
			var param = {menuId:menuNode.id,resourceId:reNode.id}
			$.ajax({
				type: "post",
				url: url,
				data: param,
				async: false,
				success: function (data) {
					alert(data.msg);
					if(data.success){
					}
				}
			});
		}else{
			alert("所选择的菜单、资源必须为最后一级子节点！");
		}
	}else{
		alert("请选择需要关联的资源地址！");
	}
}

/**
 * 选中节点
 * @param checked
 */
function checkTreeNode(checked) {
	var nodes = zTree.getSelectedNodes();
	if (nodes && nodes.length>0) {
		zTree.checkNode(nodes[0], checked, true);
	}
	hideRMenu();
}
/**
 * 刷新ztree控件
 */
function resetTree() {
	hideRMenu();
	$.fn.zTree.init($("#"+treeMenu), settingMenu);
}

function resetResource() {
	$.fn.zTree.init($("#"+treeResource), settingResource);
}
/**
 * 输入框整型控制
 * @param obj
 * @param code
 * @returns {boolean}
 */
function ztree_check_integer(obj,code) {
	if(obj && obj.value){
		if (/^(\+|-)?\d+$/.test(obj.value)){
			return true;
		}else{
			f_alert(code,"请输入整数");
			var node = zTree.getNodeByTId($('#treeTId').val());
			obj.value=$('#menuOrderHid').val();
			obj.focus();
			return false;
		}
	}
	return false;
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
function f_alert(code,str){
	var msg = "";
	if(code && code != ""){
		msg = code+":"+str;
	}else{
		msg = str;
	}
	msg = msg.replace(/\\n/g,"\n");
	alert(msg);
}