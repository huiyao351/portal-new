var settingDistrict = {
	view: {
		dblClickExpand: true
	},
	check: {
		enable: false
	},
	async: {
		enable: true,
		url:cur_proj_url+"/config/district/json",
		type: "get"
	},
	callback: {
		onClick: ztreeOnClick,
		onRightClick: ztreeOnRightClick
	}
};

$(document).ready(function(){
	$.fn.zTree.init($("#"+treeDistrict), settingDistrict);
	zTree = $.fn.zTree.getZTreeObj(treeDistrict);
	rMenu = $("#rMenu");
	$('#expandTreeDistrict').click(function () {
		zTree.expandAll(true);
	});
	$('#collapseTreeDistrict').click(function () {
		zTree.expandAll(false);
	});
	$('#refreshTreeDistrict').click(function () {
		resetTree();
	});

	$('#districtBtn').hide();
	$('#districtBtn').click(function () {
		saveDistrictdata();
	});
});

/**
 * 显示右键菜单
 * @param type
 * @param x
 * @param y
 */
function showRMenu(type, x, y) {
	$("#rMenu ul").show();
	if (type=="root") {
		$("#m_del").hide();
	}else if(type=="node"){
		$("#m_del").show();
	}
	rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

	$("body").bind("mousedown", onBodyMouseDown);
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
	if (!treeNode) {
		zTree.cancelSelectedNode();
	} else if (treeNode && !treeNode.noR) { //noR属性为true表示禁止右键菜单
		if (treeNode.level ==0) {
			showRMenu("root", event.clientX, event.clientY);
		} else {
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

	showDistrictInfo(treeNode);
}

function showDistrictInfo(treeNode){
	$("#districtForm input[type='hidden']").val("");
	$("#districtForm input[type='text']").val("");
	if (!treeNode || treeNode.id == "1") {
		$('#districtBtn').hide();
		$("#districtForm")[0].reset();
		return;
	}else{
		if(treeNode.id){
			//获取节点信息
			var url = cur_proj_url+"/config/district/info";
			var param = {districtId:treeNode.id}
			$.ajax({
				type: "get",
				url: url,
				data: param,
				async: false,
				success: function (result) {
					initDistrictForm(treeNode,result);
				}
			});
		}else{
			//临时增加的节点，需要获取父节点，如果父节点也是临时增加节点，则提示保存父节点
			$('#districtBtn').show();
			$('#treeTId').val(treeNode.tId);
			$('#districtParentId').val(treeNode.pid);
			$('#districtName').val(treeNode.name);
		}
	}
	$('#districtInfoDiv').show();
}


/**
 * 初始化菜单属性表单
 * @param treeNode
 * @param result
 */
function initDistrictForm(treeNode, result){
	$('#districtBtn').show();
	$('#treeTId').val(treeNode.tId);
	$('#districtId').val(result.districtId);
	$('#districtName').val(result.districtName);
	$('#districtParentId').val(result.districtParentId);
	$('#districtCode').val(result.districtCode);
}

var addCount = 1;
/**
 * 增加节点
 */
function addTreeNode() {
	$("#districtForm input[type='hidden']").val("");
	$("#districtForm input[type='text']").val("");
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
	} else {
		newNode = zTree.addNodes(null, newNode);
	}
	showDistrictInfo(newNode[0]);
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
			var menuId = nodes[0];
			if(menuId){
				if (nodes[0].children && nodes[0].children.length > 0) {
					var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
					if (confirm(msg)==true){
						removeCallback(nodes[0]);
					}
				} else {
					removeCallback(nodes[0]);
				}
			}else{
				zTree.removeNode(nodes[0]);
			}
		}
	}
}
/**
 * 删除返回函数
 * @param node
 */
function removeCallback(node){
	var url = cur_proj_url+'/config/district/del?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{districtId:node.id},
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
function saveDistrictdata(){
	var districtCode = document.getElementById("districtCode");
	if(ztree_check_integer(districtCode,'行政区编码')){
		var url = cur_proj_url+'/config/district/save?';
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:$("#districtForm").serialize(),
			success:function (data) {
				alert(data.msg);
				if(data.success){
					var node = zTree.getNodeByTId($('#treeTId').val());
					node.name=data.district.districtName;
					node.id=data.district.districtId;
					node.pid=data.district.districtParentId;
					$('#districtId').val(data.district.districtId);
					$('#districtParentId').val(data.district.districtParentId);
					zTree.updateNode(node);
				}
			},
			error:function (data) {
				alert("保存失败");
			}
		});
	}
}






/**
 * 刷新ztree控件
 */
function resetTree() {
	hideRMenu();
	$.fn.zTree.init($("#"+treeDistrict), settingDistrict);
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
			return false;
		}
	}
	return false;
}
