/**
 * ztree控件的数据设置
 */
var settingRole = {
	view: {dblClickExpand: true},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/role/json",
		type: "get"
	},
	//data: {simpleData: {enable: true}},
	callback: {
		onClick: ztreeRoleOnClick,
		onRightClick: ztreeRoleOnRightClick,
		onAsyncSuccess: zTreeRoleOnAsyncSuccess
	}
};
var settingOrgan = {
	view: {showLine: false,dblClickExpand: true},
	check: {enable: true},
	async: {
		enable: true,
		url:cur_proj_url+"/config/user/json?",
		type: "get"
	},
	callback: {
		onClick: ztreeOrganOnClick,
		onCheck: ztreeOrganOnCheck,
		onAsyncSuccess: zTreeOrganOnAsyncSuccess
	}
};

var settingUser = {
	view: {showLine: false},
	data: {simpleData: {enable: true}},
	async: {
		enable: true,
		type: "get"
	},
	edit: {
		enable: true,
		editNameSelectAll: false,
		showRenameBtn: false,
		showRemoveBtn: true
	},
	callback: {
		beforeRemove: beforeRemoveUser
	}
};

$(document).ready(function(){
	initdisselect("regionCode");
	$.fn.zTree.init($("#"+treeRole), settingRole);
	zTreeRole = $.fn.zTree.getZTreeObj(treeRole);

	$.fn.zTree.init($("#"+treeOrgan), settingOrgan);
	zTreeOrgan = $.fn.zTree.getZTreeObj(treeOrgan);

	$('#expandTreeRole').click(function () {
		zTreeRole.expandAll(true);
	});
	$('#collapseTreeRole').click(function () {
		zTreeRole.expandAll(false);
	});

	////下面是主题操作事件
	$('#addTreeRole').click(function () {
		addTreeRoleItem();
	});
	$('#deleteTreeRole').click(function () {
		deleteTreeRole();
	});

	//下面是菜单树事件
	$('#expandTreeOrgan').click(function () {
		zTreeOrgan.expandAll(true);
	});
	$('#collapseTreeOrgan').click(function () {
		zTreeOrgan.expandAll(false);
	});

	$('#saveTreeUserRoleRel').click(function () {
		saveRoleUserReldata();
	});

	$('#relMenuAuth').click(function () {
		relMenuAuth();
	});

	$('#roleBtn').click(function () {
		saveRoledata();
	});

	$('#roleBtn').hide();
	$('#saveTreeUserRoleRel').hide();
});
//******************* 以下是主题树相关操作*****************************
var cur_role_userAry = [];
function zTreeRoleOnAsyncSuccess(event, treeId, treeNode, msg) {
	//默认选中第一个角色
	function filter(node) {
		return (node.level > 0 && !(node.children && node.children.length > 0));
	}
	var node = zTreeRole.getNodesByFilter(filter,true);
	zTreeRole.selectNode(node);
	ztreeRoleOnClick(null, null, node);
}
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeRoleOnClick(event, treeId, treeNode) {
	cur_role_userAry = [];

	$("#roleForm input[type='hidden']").val("");
	$("#roleForm input[type='text']").val("");
	$("#roleForm select").val("");

	zTreeRole.selectNode(treeNode);
	zTreeOrgan.checkAllNodes(false);
	if(zTreeRoleUser){
		zTreeRoleUser.destroy();
	}

	$('#roleBtn').hide();
	$('#saveTreeUserRoleRel').hide();

	if(treeNode.id && (!treeNode.children || treeNode.children.length <= 0)){
		//获取节点信息
		var url = cur_proj_url+"/config/role/info";
		var param = {roleId:treeNode.id}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				$('#roleBtn').show();
				$('#saveTreeUserRoleRel').show();

				$('#treeTId').val(treeNode.tId);
				if(result.role){
					$('#roleId').val(result.role.roleId);
					$('#roleName').val(result.role.roleName);
					$('#roleNo').val(result.role.roleNo);
				}
				$("#regionCode").find("option").removeAttr("selected")
				if(result.role && result.role.regionCode){
					$("#regionCode").find("option[value="+result.role.regionCode+"]").prop("selected",true);
				}else{
					$("#regionCode").find("option[value='parent']").prop("selected",true);
				}
				initRoleUser(result.userList);

				cur_role_userAry = result.userList;
			}
		});
	}else{
		//临时增加的节点，需要获取父节点，如果父节点也是临时增加节点，则提示保存父节点
		$('#roleBtn').show();
		$('#treeTId').val(treeNode.tId);
	}
}

function ztreeRoleOnRightClick(event, treeId, treeNode) {
	if (treeNode && !treeNode.noR) { //noR属性为true表示禁止右键菜单
		zTreeRole.selectNode(treeNode);
		showRMenu(event.clientX, event.clientY);
	}
}
function showRMenu(x, y) {
	$("#rRoleMenu>ul>li").hide();
	$("#rRoleMenu").show();

	var pnode = zTreeRole.getSelectedNodes()[0];
	if(pnode.level > 0 && pnode.group){
		$("#m_addTreeRole").show();
	}else if(!pnode.children || pnode.children.length <= 0){
		$("#m_relMenuAuth").show();
		$("#m_deleteTreeRole").show();
	}
	$("#rRoleMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
	if (!(event.target.id == "rRoleMenu" || $(event.target).parents("#rRoleMenu").length>0)) {
		$("#rRoleMenu").css({"visibility" : "hidden"});
	}
}
function hideRMenu() {
	$("#rRoleMenu").hide();
	$("body").unbind("mousedown", onBodyMouseDown);
}
/**
 * 增加节点
 */
function addTreeRoleItem() {
	$("#roleForm input[type='hidden']").val("");
	$("#roleForm input[type='text']").val("");

	$('#roleBtn').show();

	//获取当前右键的行政区，初始化角色信息窗口中行政区下拉框
	var pnode = zTreeRole.getSelectedNodes()[0];
	if(pnode && pnode.children && pnode.children.length > 0){
		$('#regionCode').val(pnode.id);
	}
	hideRMenu();
}
/**
 * 保存菜单数据
 */
function saveRoledata(){
	//数据必填，不能为空
	var title = $('#roleName').val();
	if(!title ){
		alert("请填写要添加的角色名称");
		return;
	}

	$('#roleBtn').hide();
	$('#saveTreeUserRoleRel').hide();

	var url = cur_proj_url+'/config/role/save?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#roleForm").serialize(),
		success:function (data) {
			alert(data.msg);
			$('#roleBtn').show();
			$('#saveTreeUserRoleRel').show();
			if(data.success){
				var node = zTreeRole.getNodeByTId($('#treeTId').val());
				if(node){
					node.name=data.role.roleName;
					node.id=data.role.roleId;
					node.kz1=data.role.regionCode;
					zTreeRole.updateNode(node);
				}else{
					node = {id:data.role.roleId,name:data.role.roleName};
					var pnode = zTreeRole.getSelectedNodes()[0];
					if(pnode && pnode.children && pnode.children.length > 0){
						zTreeRole.addNodes(pnode,node);
						zTreeRole.updateNode(pnode);
					}
				}
				node = zTreeRole.getNodeByParam("id", data.role.roleId, null);
				ztreeRoleOnClick(null, null, node);
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
function deleteTreeRole() {
	if (confirm("确定要删除该节点吗，不可恢复？")==true){
		var nodes = zTreeRole.getSelectedNodes();
		if (nodes && nodes.length>0) {
			var curNode = nodes[0];
			if(curNode && curNode.id){
				removeCallback(curNode);
			}else{
				zTreeRole.removeNode(curNode);
				$('#roleBtn').hide();
				$('#saveTreeUserRoleRel').hide();
			}
		}
	}
	hideRMenu();
}
/**
 * 删除返回函数
 * @param node
 */
function removeCallback(node){
	var url = cur_proj_url+'/config/role/del?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{roleId:node.id},
		success:function (data) {
			alert(data.msg);
			if(data.success){
				zTreeRole.removeNode(node);
				$('#roleBtn').hide();
				$('#saveTreeUserRoleRel').hide();
			}
		},
		error:function (data) {
			alert("请求失败！");
		}
	});
}
//******************************************************************
//******************* 以下是部门树树操作*****************************
function zTreeOrganOnAsyncSuccess(event, treeId, treeNode, msg) {
}
//树控件的复选框、单选框的check事件
function ztreeOrganOnCheck(event, treeId, treeNode) {
	if(!treeNode.children || treeNode.children.length <= 0){
		zTreeOrgan.selectNode(treeNode);
	}
}
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeOrganOnClick(event, treeId, treeNode) {
	if (treeNode && (!treeNode.children || treeNode.children.length <= 0)) {
		zTreeOrgan.checkNode(treeNode, true, true);
	}
}
/**
 * 保存菜单数据
 */
function saveRoleUserReldata(){
	//测试修改过的节点记录
	var roleNodes = zTreeRole.getSelectedNodes();
	if(roleNodes.length == 0 || (roleNodes[0].children && roleNodes[0].children.length > 0)){
		alert("请选中角色之后，再执行相关操作!");
		return false;
	}
	//获取选中节点或者是变化节点
	var changeNodes = getChangeNodes(cur_role_userAry,zTreeOrgan);
	if(!changeNodes || changeNodes.length <= 0){
		alert("当前数据无变化，无需保存！");
	}else{
		var url = cur_proj_url+"/config/role/saveUserRole";
		var param = {
			roleId:roleNodes[0].id,
			paramString:$.toJSON(changeNodes)
		};
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:param,
			success: function (result) {
				alert(result.msg);
				if(result.success){
					ztreeRoleOnClick(null, null, roleNodes[0]);
				}
			}
		});
	}
}
//*****************************************************************
//******************* 以下是角色下用户信息操作*****************************
function initRoleUser(userList){
	if(userList && userList.length > 0){
		var jsonUsers=[];
		var pid="userroot";
		for (var i=0;i<userList.length;i++) {
			var id=userList[i].id;
			var node = zTreeOrgan.getNodeByParam("id", id, null);
			if(node){
				zTreeOrgan.checkNode(node, true, true);
			}

			var name=userList[i].name;
			var json={id:id,name:name,pId:pid};
			jsonUsers.push(json);
		}
		$.fn.zTree.init($("#"+treeRoleUser), settingUser,jsonUsers);
		zTreeRoleUser = $.fn.zTree.getZTreeObj(treeRoleUser);
	}
}

function beforeRemoveUser(treeId, treeNode) {
	zTreeRoleUser.selectNode(treeNode);
	if(confirm("确认删除“" + treeNode.name + "” 关联关系吗？")){
		var nodes=	zTreeRole.getSelectedNodes();
		var roleId=nodes[0].id;
		removeTreeRoleUser(roleId,treeNode.id);
	}else{
		return false;
	}
}
function removeTreeRoleUser(roleId,userId) {
	if(userId && roleId){
		var url = cur_proj_url+"/config/role/delUserRoleRel?";
		var param = {roleId:roleId,userId:userId};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				alert(result.msg);
				if(result.success) {
					var nodes = zTreeOrgan.getNodesByParam("id", userId, null);
					if(nodes && nodes.length > 0){
						var tmpIds = "";
						for (var i=0;i<nodes.length;i++) {
							tmpIds += nodes[i].id + ";";
							zTreeOrgan.checkNode(nodes[i], false, true);
						}
						if(cur_role_userAry && cur_role_userAry.length > 0){
							for(var i=0;i<cur_role_userAry.length;i++){
								var rid = cur_role_userAry[i].id;
								if(tmpIds.indexOf(rid+";") > -1){
									cur_role_userAry.splice(i,1);
									i--;
								}
							}
						}
					}
				}
			}
		});
	}
}

//*************************************************************************

/*
 刷新树
 */
function refreshTreeRole(){
	zTreeRole.reAsyncChildNodes(null, "refresh");
}
/*
刷新树
 */
function refreshTreeOrgan(){
	zTreeOrgan.reAsyncChildNodes(null, "refresh");
}

function ztree_check_integer(obj,code) {
	if(obj && obj.value){
		if (/^(\+|-)?\d+$/.test(obj.value)){
			return true;
		}else{
			f_alert(code,"请输入数字");
			return false;
		}
	}
	return false;
}

/**
 * 打开该角色拥有的菜单权限列表
 */
function relMenuAuth(){
	var selectNode = zTreeRole.getSelectedNodes()[0];
	if(selectNode && selectNode.id){
		var url = cur_proj_url+'/config/menuAuth/relmenu?roleId='+selectNode.id;
		$('#infoFrame').attr("src", url);
		$('#infoModal').modal({show:true});
	}
	hideRMenu();
}