/**
 * ztree控件的数据设置
 */
var settingSub = {
	view: {showLine: false},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/sub/json",
		type: "get"
	},
	data: {simpleData: {enable: true}},
	callback: {
		onClick: ztreeSubOnClick,
		onAsyncSuccess: zTreeSubOnAsyncSuccess
	}
};
var settingMenu = {
	view: {dblClickExpand: true},
	check: {enable: true},
	async: {
		enable: true,
		url:cur_proj_url+"/config/sub/checkjson?subId="+cur_sub_id,
		type: "get"
	},
	callback: {
		onClick: ztreeMenuOnClick,
		onCheck: ztreeMenuOnCheck
	}
};

var settingSubMenu = {
	view: {dblClickExpand: true},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/sub/menujson?subId="+cur_sub_id,
		type: "get"
	},
	edit: {
		enable: true,
		editNameSelectAll: false,
		showRenameBtn: false,
		showRemoveBtn: showRemoveBtn
	},
	callback: {
		onClick: ztreeSubMenuOnClick,
		beforeRemove: beforeRemove
	}
};

$(document).ready(function(){
	$.fn.zTree.init($("#"+treeSub), settingSub);
	zTreeSub = $.fn.zTree.getZTreeObj(treeSub);

	$.fn.zTree.init($("#"+treeMenu), settingMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj(treeMenu);

	$.fn.zTree.init($("#"+treeSubMenu), settingSubMenu);
	zTreeSubMenu = $.fn.zTree.getZTreeObj(treeSubMenu);

	//下面是主题操作事件
	$('#addTreeSub').click(function () {
		addTreeSubItem();
	});
	$('#subBtn').click(function () {
		saveSubdata();
	});
	$('#deleteTreeSub').click(function () {
		deleteTreeSubItem();
	});
	$('#refreshTreeSub').click(function () {
		resetTreeSub();
	});

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
	$('#saveTreeMenuRel').click(function () {
		saveSubMenuReldata();
	});


	//下面是菜单树事件
	$('#expandTreeSubMenu').click(function () {
		zTreeSubMenu.expandAll(true);
	});
	$('#collapseTreeSubMenu').click(function () {
		zTreeSubMenu.expandAll(false);
	});
	$('#refreshTreeSubMenu').click(function () {
		resetTreeSubMenu();
	});

});
function showSubAuth(){
	$('#showSubMenu').show();
	$('#showSubAuth').hide();
	$('#subAuthDiv').show();
	$('#menuTreeDiv').hide();
	$('#menuRelTreeDiv').hide();

	var url = cur_proj_url+'/config/sub/author?subId='+cur_sub_id;
	$('#mainFrame').attr("src", url);
}
function closeSubAuth(){
	$('#showSubMenu').hide();
	$('#showSubAuth').show();

	$('#menuTreeDiv').show();
	$('#menuRelTreeDiv').show();
	$('#subAuthDiv').hide();
}
//******************* 以下是主题树相关操作*****************************
/**
 * 刷新树节点样式
 */
function zTreeSubOnAsyncSuccess(event, treeId, treeNode,msg) {
	$("#"+treeSub).find('span[class*="ico_docu"]').each(function(){
		$(this).css("height","40px");
		$(this).css("width","40px");
	})
	$("#"+treeSub+">li>a").css("height","40px");
	$("#"+treeSub+">li>a>span").css("line-height","40px");
	if(!treeNode){
	//默认选中第一个
		var node = zTreeSub.getNodeByParam("id", cur_sub_id, null);
		ztreeSubOnClick(null,null,node);
	}
	//zTreeSub.selectNode(node);
}
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeSubOnClick(event, treeId, treeNode) {
	$("#subForm input[type='hidden']").val("");
	$("#subForm input[type='text']").val("");
	zTreeSub.selectNode(treeNode);
	$('#subInfoDiv').show();
	if(treeNode.id){
		//获取节点信息
		var url = cur_proj_url+"/config/sub/info";
		var param = {subsystemId:treeNode.id}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				$('#subBtn').show();
				$('#treeTId').val(treeNode.tId);
				$('#subsystemId').val(result.subsystemId);
				$('#subsystemTitle').val(result.subsystemTitle);
				$('#subsystemName').val(result.subsystemName);

				$('#subType').val(result.subType);
				$('#subNo').val(result.subNo);
				$('#subUrl').val(result.subUrl);
				$('#subMenuType').val(result.subMenuType);

				$('#subsystemName').attr("readonly",true);
				$('#subsystemName').css("background","#cccccc");
				$('#enabled').val(result.enabled?1:0);

				cur_sub_id = result.subsystemId;

				//加载右侧的主题菜单预览
				resetTreeMenu();
				resetTreeSubMenu();

				//判断右侧的主题权限是否可见，如果可见则加载对应的主题的权限功能
				if(!$("#subAuthDiv").is(":hidden")){
					document.getElementById("mainFrame").contentWindow.initRoleCheck(cur_sub_id);
				}
			}
		});
	}else{
		//临时增加的节点，需要获取父节点，如果父节点也是临时增加节点，则提示保存父节点
		$('#subBtn').show();
		$('#treeTId').val(treeNode.tId);
		$('#subsystemTitle').val(treeNode.name);
		$('#subsystemName').attr("readonly",false);
		$('#subsystemName').css("background","#ffffff");
		cur_sub_id = "";
	}
}
var addCountSub = 1;
/**
 * 增加节点
 */
function addTreeSubItem() {
	$("#subForm input[type='hidden']").val("");
	$("#subForm input[type='text']").val("");
	var newNode = { name:"主题" + (addCountSub++),icon:cur_proj_url+"/static/images/40x40icon05.png"};
	newNode = zTreeSub.addNodes(null, newNode);
	$('#subInfoDiv').show();
	$('#subBtn').show();
	$('#treeTId').val(newNode[0].tId);
	$('#subsystemTitle').val(newNode[0].name);
	$('#subsystemName').attr("readonly",false);
	$('#subsystemName').css("background","#ffffff");
	zTreeSub.selectNode(newNode[0]);
	zTreeSubOnAsyncSuccess(null,null,newNode[0],null);
}
/**
 * 保存菜单数据
 */
function saveSubdata(){
	//数据必填，不能为空
	var title = $('#subsystemTitle').val();
	var name = $('#subsystemName').val();
	if(!title || !name){
		alert("请完整填写名称和代码！");
		return;
	}
	var url = cur_proj_url+'/config/sub/save?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#subForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				var node = zTreeSub.getNodeByTId($('#treeTId').val());
				node.name=data.sub.subsystemTitle;
				node.id=data.sub.subsystemId;
				node.kz1=data.sub.subsystemName;

				$('#subsystemId').val(data.sub.subsystemId);
				$('#subsystemTitle').val(data.sub.subsystemTitle);
				$('#subsystemName').val(data.sub.subsystemName);

				$('#subType').val(data.sub.subType);
				$('#subNo').val(data.sub.subNo);
				$('#subUrl').val(data.sub.subUrl);
				$('#subMenuType').val(data.sub.subMenuType);

				$('#subsystemName').attr("readonly",true);
				$('#subsystemName').css("background","#cccccc");
				$('#enabled').val(data.sub.enabled?1:0);
				zTreeSub.updateNode(node);
				zTreeSubOnAsyncSuccess(null,null,node,null);
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
function deleteTreeSubItem() {
	if (confirm("确定要删除该节点吗，不可恢复？")==true){
		var nodes = zTreeSub.getSelectedNodes();
		if (nodes && nodes.length>0) {
			var curNode = nodes[0];
			if(curNode && curNode.id){
				removeCallback(curNode);
			}else{
				zTreeSub.removeNode(curNode);
			}
			$('#subInfoDiv').hide();
		}
	}
}
/**
 * 删除返回函数
 * @param node
 */
function removeCallback(node){
	var url = cur_proj_url+'/config/sub/del?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{keyId:node.id},
		success:function (data) {
			alert(data.msg);
			if(data.success){
				zTreeSub.removeNode(node);
			}
		},
		error:function (data) {
			alert("请求失败！");
		}
	});
}
//******************* 以上是主题树相关操作*****************************
//******************* 以下是菜单树操作*****************************
//树控件的复选框、单选框的check事件
function ztreeMenuOnCheck(event, treeId, treeNode) {
	if(!treeNode.children || treeNode.children.length <= 0){
		zTreeMenu.selectNode(treeNode);
	}
}
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeMenuOnClick(event, treeId, treeNode) {
	if (treeNode && (!treeNode.children || treeNode.children.length <= 0)) {
		zTreeMenu.checkNode(treeNode, true, true);
	}
	$("#subMenuForm input[type='hidden']").val("");
	$("#subMenuForm input[type='text']").val("");

	if(treeNode.id && treeNode.children.length < 1){
		$('#menuInfoDiv').show();
		//获取节点信息
		var url = cur_proj_url+"/config/menu/info";
		var param = {menuId:treeNode.id}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				$('#treeTId').val(treeNode.tId);

				$('#menuId').val(result.menuId);
				$('#resourceName').val(result.resource.resourceName);
				$('#resourceUrl').val(result.resource.resourceUrl);
				$('#businessName').val(result.resource.business.businessName);
			}
		});
	}else{
		$('#menuInfoDiv').hide();
	}
}
/**
 * 保存菜单数据
 */
function saveSubMenuReldata(){
	//测试修改过的节点记录
	var changeNodes = [];
	var nodes = zTreeMenu.getChangeCheckedNodes();
	if(nodes && nodes.length > 0){
		for(var i=0;i<nodes.length;i++){
			var node = {
				id:nodes[i].id,
				//name:nodes[i].name,
				checked:nodes[i].checked
			}
			changeNodes.push(node);
		}
	}else{
		alert("当前数据无变化，无需保存！");
		return;
	}
	var url = cur_proj_url+'/config/sub/saveRel?';
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
				resetTreeMenu();
			}
		},
		error:function (data) {
			alert("请求失败！");
		}
	});
}
//******************* 以上是菜单树操作*****************************
//******************* 以下是主题菜单预览部分操作*****************************
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeSubMenuOnClick(event, treeId, treeNode) {
	$("#subMenuForm input[type='hidden']").val("");
	$("#subMenuForm input[type='text']").val("");
	zTreeSubMenu.selectNode(treeNode);

	if(treeNode.id && treeNode.children.length < 1){
		$('#subMenuInfoDiv').show();
		//获取节点信息
		var url = cur_proj_url+"/config/menu/info";
		var param = {menuId:treeNode.id}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				$('#sb_treeTId').val(treeNode.tId);

				$('#sb_menuId').val(result.menuId);
				$('#sb_resourceName').val(result.resource.resourceName);
				$('#sb_resourceUrl').val(result.resource.resourceUrl);
				$('#sb_businessName').val(result.resource.business.businessName);
			}
		});
	}else{
		$('#subMenuInfoDiv').hide();
	}
}

function showRemoveBtn(treeId, treeNode) {
	return treeNode.level>0;
}
function beforeRemove(treeId, treeNode) {
	zTreeSubMenu.selectNode(treeNode);
	if(confirm("确认删除“" + treeNode.name + "” 关联关系吗？")){
		var url = cur_proj_url+"/config/sub/delRel";
		var param = {subId:cur_sub_id,menuId:treeNode.id};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				alert(result.msg);
				if(result.success){
					//zTreeSubMenu.removeNode(treeNode);
					resetTreeMenu();
				}
			}
		});
	}else{
		return false;
	}
}
//******************* 以上是主题菜单预览部分操作*****************************

/**
 * 刷新ztree控件
 */
function resetTreeSub() {
	$.fn.zTree.init($("#"+treeSub), settingSub);
	zTreeSub = $.fn.zTree.getZTreeObj(treeSub);
	$('#subInfoDiv').hide();
}
function resetTreeMenu() {
	settingMenu.async.url = cur_proj_url+"/config/sub/checkjson?subId="+cur_sub_id;
	$.fn.zTree.init($("#"+treeMenu), settingMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj(treeMenu);
	$('#menuInfoDiv').hide();
	resetTreeSubMenu();
}
function resetTreeSubMenu() {
	settingSubMenu.async.url = cur_proj_url+"/config/sub/menujson?subId="+cur_sub_id;
	$.fn.zTree.init($("#"+treeSubMenu), settingSubMenu);
	zTreeSubMenu = $.fn.zTree.getZTreeObj(treeSubMenu);
	$('#subMenuInfoDiv').hide();
}