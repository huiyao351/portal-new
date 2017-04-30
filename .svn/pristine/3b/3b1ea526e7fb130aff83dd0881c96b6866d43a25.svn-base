/**
 * ztree控件的数据设置
 */

var settingUser = {
	view: {dblClickExpand: true},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/user/json?",
		type: "get"
	},
	callback: {
		onClick: ztreeUserOnClick,
		onRightClick: ztreeUserOnRightClick,
		onAsyncSuccess: zTreeUserOnAsyncSuccess
	}
};

$(document).ready(function(){
	$.fn.zTree.init($("#"+treeUser), settingUser);
	zTreeUser = $.fn.zTree.getZTreeObj(treeUser);
	rUser = $("#rUser");

	$('#expandTreOrgan').click(function () {
		zTreeUser.expandAll(true);
	});
	$('#collapseTreeOrgan').click(function () {
		zTreeUser.expandAll(false);
	});

	$("#userInfoDiv").show();

	$('#userBtn').click(function () {
		saveUserdata();
	});

	$('#openTreeRoleSelect').click(function () {
		openTreeRoleSelect();
	});
	$('#openTreeOrganSelect').click(function () {
		openTreeOrganSelect();
	});

	$(".selectConfigModal").draggable({
		handle: ".modal-top-bar",
		cursor: 'move',
		refreshPositions: false
	});
});

//******************* 以下是用户树相关操作*****************************
//******************* 包含用户树左右键点击和加载成功事件*****************************
/**
 * 刷新树节点样式
 */
function zTreeUserOnAsyncSuccess(event, treeId, treeNode,msg) {
	function filter(node) {
		return (node.level > 1 && !node.group);
	}
	var node = zTreeUser.getNodesByFilter(filter,true);
	zTreeUser.selectNode(node);
	ztreeUserOnClick(null, null, node);
	initUserOrgan(node.id);
	initUserRole(node.id);
}
//用户菜单左击时间
function ztreeUserOnClick(event, treeId, treeNode) {
	$("#userForm input[type='hidden']").val("");
	$("#userForm input[type='text']").val("");
	$("#userForm textarea").val("");
	$("#userForm")[0].reset();
	zTreeUser.selectNode(treeNode);
	if(treeNode.id && treeNode.level > 0 && !treeNode.group){
		var url = cur_proj_url+"/config/user/info";
		var param = {userId:treeNode.id}
		//$('#userSignImg').attr("src",cur_proj_url+"/config/user/userSign?userId="+treeNode.id+"&time="+new Date());
		//$('#userPhotoImg').attr("src",cur_proj_url+"/config/user/userPhoto?userId="+treeNode.id+"&time="+new Date());
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				if(result.success){
					var user=result.user;
					if(user){
						$('#treeTId2').val(treeNode.tId);
						$('#userId').val(user.userId);
						$('#userName').val(user.userName);
						$('#userSignId').val(user.userId);
						$('#userPhotoId').val(user.userId);
						deafautvalue("userRank",user.userRank);
						deafautvalue("userNo",user.userNo);
						deafautvalue("userPost",user.userPost);
						deafautvalue("mobilePhone",user.mobilePhone);
						deafautvalue("officePhone",user.officePhone);
						deafautvalue("homePhone",user.homePhone);
						deafautvalue("email",user.email);
						deafautvalue("address",user.address);

						$('#userLoginId').val(user.userId);
						deafautvalue("loginName",user.loginName);
						deafautvalue("loginPassword",user.loginPassword);
						deafautvalue("loginPassword2",user.loginPassword);
						deafautvalue("remark2",user.remark);
						if(user.birthdate){
							$('#birthdate').val(new Date(user.birthdate).Format("yyyy-MM-dd"));
						}else{
							$('#birthdate').val("");
						}
						$("#userDegree").find("option[value="+user.userDegree+"]").attr("selected","selected");
						$("#userSex").find("option[value="+user.userSex+"]").attr("selected","selected");
						$("#userInfoDiv").show();
						$("#userLoginInfoButton [type='button'] ").attr("disabled",false);
						$("#userLoginInfoButton [type='button'] ").attr("readonly",false);
						$("#userLoginInfoButton [type='button'] ").removeClass("btn-readonly");
						$("#userLoginInfoButton [type='button'] ").addClass("btn-secondary");
						$("#userLoginInfoButton [type='button'] ").addClass("btn-primary");

						initUserOrgan(user.userId);
						initUserRole(user.userId);
					}
				}
			}
		});
	}
}

function ztreeUserOnRightClick(event, treeId, treeNode) {
	if(treeNode){
		//不允许右键，直接退出
		if(treeNode.noR || treeNode.level < 1){
			return;
		}
		zTreeUser.selectNode(treeNode);
		if(treeNode.group){
			//部门的右键
			showRUserMenu("organ", event.clientX, event.clientY);
		}else{
			//用户的右键
			showRUserMenu("user", event.clientX, event.clientY);
		}
	}
}
function showRUserMenu(type, x, y) {
	$("#rUser ul li").hide();
	if (type=="organ") {
		$("#m_adduser").show();
	} else if(type=="user") {
		$("#m_deluser").show();
	}
	rUser.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyUserMouseDown);
}
//隐藏右键菜单
function hideRUserMenu() {
	if (rUser) rUser.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyUserMouseDown);
}
//右键相关设置
function onBodyUserMouseDown(event){
	if (!(event.target.id == "rUser" || $(event.target).parents("#rUser").length>0)) {
		rUser.css({"visibility" : "hidden"});
	}
}

function addUserNode(){
	$("#userForm input").val("");
	$("#userForm textarea").val("");
	$("#userLoginInfoDiv input").val("");
	$("#userInfoDiv").show();
	$("#userBtn").show();

	var selectNode = zTreeUser.getSelectedNodes()[0];
	$("#organId2").val(selectNode.id);
	$("#userLoginInfoButton [type='button'] ").attr("disabled",true);
	$("#userLoginInfoButton [type='button'] ").attr("readonly","readonly");
	$("#userLoginInfoButton [type='button'] ").addClass("btn-readonly");
	$("#userLoginInfoButton [type='button'] ").removeClass("btn-secondary");
	$("#userLoginInfoButton [type='button'] ").removeClass("btn-primary");
	hideRUserMenu();
}
function removeUserNode(){
	$("#roletitle").html("");
	$("#delmark").html("");
	$("#delmark2").html("");
	$("#roletitle").html("<i class='icon-question-sign'></i>删除用户(谨慎操作)");
	$("#delmark").html(" <input name='delmark' checked='checked'  type='radio' value='3'/>仅删除用户与当前部门的关系信息");
	$("#delmark2").html(" <input name='delmark'   type='radio' value='4'/>永久删除用户（部门和角色列表中删除此用户）");
	$("#delUserMenu").modal('show');
	hideRUserMenu();
}
//******************************************************************************
//******************* 以下是用于选择部门、角色相关操作*****************************
function openTreeOrganSelect() {
	//打开部门选择列表，弹出窗口将选择数据回刷回来，之后入库，刷新角色树
	var selectNode = zTreeUser.getSelectedNodes()[0];
	if(selectNode && selectNode.id && !selectNode.group && selectNode.level > 0){
		var foreignId = selectNode.id;
		var param = {userId:foreignId};
		var url = cur_proj_url+'/config/organ/select?foreignId='+foreignId+'&paramString='+$.toJSON(param);
		$('#selectFrame').attr("src", url);
		$('#selectModal').modal({show:true});
	}
}
function returnSelectOrganData(param){
	//解析modal窗口返回的数据，将数据存储到后台，之后刷新系统界面
	if(param && param.keyId && param.paramString){
		var selectNode = zTreeUser.getSelectedNodes()[0];
		if(selectNode && selectNode.id && !selectNode.group && selectNode.level > 0){
			var userId = selectNode.id;
			var url = cur_proj_url+'/config/organ/saveUserOrganRel?';
			param.userId = userId;
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:param,
				success:function (data) {
					alert(data.msg);
					$('#selectModal').modal('hide');
					initUserOrgan(userId);
				},
				error:function (data) {
					alert("请求失败！");
				}
			});
		}
	}
}
function openTreeRoleSelect() {
	//打开角色选择列表，弹出窗口将选择数据回刷回来，之后入库，刷新角色树
	var selectNode = zTreeUser.getSelectedNodes()[0];
	if(selectNode && selectNode.id && !selectNode.group && selectNode.level > 0){
		var foreignId = selectNode.id;
		var param = {userId:foreignId};
		var url = cur_proj_url+'/config/role/select?foreignId='+foreignId+'&paramString='+$.toJSON(param);
		$('#selectFrame').attr("src", url);
		$('#selectModal').modal({show:true});
	}
}
function returnSelectRoleData(param){
	//解析modal窗口返回的数据，将数据存储到后台，之后刷新系统界面
	if(param && param.keyId && param.paramString){
		var selectNode = zTreeUser.getSelectedNodes()[0];
		if(selectNode && selectNode.id && !selectNode.group && selectNode.level > 0){
			var userId = selectNode.id;
			var url = cur_proj_url+'/config/role/saveUserRoleRel?';
			param.userId = userId;
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:param,
				success:function (data) {
					alert(data.msg);
					$('#selectModal').modal('hide');
					initUserRole(userId);
				},
				error:function (data) {
					alert("请求失败！");
				}
			});
		}
	}
}
//******************************************************************************
//******************* 以下是右侧用户拥有的部门、角色的操作，包括加载和删除*****************************
var userOrganSetting = {
	view: {showLine: false},
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
		beforeRemove: beforeRemoveOrgan
	}
};
var userRoleSetting = {
	view: {showLine: false},
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
		beforeRemove: beforeRemoveRole
	}
};
function initUserOrgan(userId){
	userOrganSetting.async.url = cur_proj_url+'/config/organ/userOrganList?userId='+userId;
	$.fn.zTree.init($("#"+treeUserOrgan), userOrganSetting);
	zTreeUserOrgan = $.fn.zTree.getZTreeObj(treeUserOrgan);
}
function initUserRole(userId){
	userRoleSetting.async.url = cur_proj_url+'/config/role/userRoleList?userId='+userId;
	$.fn.zTree.init($("#"+treeUserRole), userRoleSetting);
	zTreeUserRole = $.fn.zTree.getZTreeObj(treeUserRole);
}
function beforeRemoveOrgan(treeId, treeNode) {
	zTreeUserOrgan.selectNode(treeNode);
	if(confirm("确认删除“" + treeNode.name + "” 关联关系吗？")){
		var nodes=	zTreeUser.getSelectedNodes();
		var userId=nodes[0].id;
		deleteTreeOrgan(treeNode.id,userId);
	}else{
		return false;
	}
}
function beforeRemoveRole(treeId, treeNode) {
	zTreeUserRole.selectNode(treeNode);
	if(confirm("确认删除“" + treeNode.name + "” 关联关系吗？")){
		var nodes=	zTreeUser.getSelectedNodes();
		var userId=nodes[0].id;
		deleteTreeRole(treeNode.id,userId);
	}else{
		return false;
	}
}
function deleteTreeRole(roleId,userId) {
	if(userId && roleId){
		var url = cur_proj_url+"/config/role/delUserRoleRel";
		var param = {roleId:roleId,userId:userId};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				alert(result.msg);
			}
		});
	}
}
function deleteTreeOrgan(organId,userId) {
	if(userId && organId){
		var url = cur_proj_url+"/config/organ/delUserOrganRel?";
		var param = {organId:organId,userId:userId};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				alert(result.msg);
			}
		});
	}
}
//***************************************************************************
//******************* 以下是用户信息保存操作*****************************
function saveUserdata(){
	var username=  $("#userName").val();
	var userNode = zTreeUser.getSelectedNodes()[0];
	var userNo=  $("#userNo").val();
	if(!username){
		alert("请输入用户名");
		return false;
	}
	if(!userNo){
		alert("请输入用户编号");
		return false;
	}
	var url = cur_proj_url+"/config/user/save";
	$.ajax({
		type: "post",
		url: url,
		data:$('#userForm').serialize() ,
		async: false,
		success: function (result) {
			alert(result.msg);
			if(result.success){
				if(!$("#treeTId2").val()){
					var newNode = { name:username,id:result.user.userId};
					zTreeUser.addNodes(userNode,newNode);
					var newnode=zTreeUser.getNodeByParam("id", newNode.id);
					zTreeUser.selectNode(newnode);
					$('#treeTId2').val(newNode.id);
					$("#userLoginInfoButton [type='button'] ").attr("disabled",false);
					$("#userLoginInfoButton [type='button'] ").attr("readonly",false);
					$("#userLoginInfoButton [type='button'] ").removeClass("btn-readonly");
					$("#userLoginInfoButton [type='button'] ").addClass("btn-secondary");
					$("#userLoginInfoButton [type='button'] ").addClass("btn-primary");
					$('#userLoginId').val(newNode.id);
					$('#userSignId').val(newNode.id);
					$('#userPhotoId').val(newNode.id);
				}else{
					var node = zTreeUser.getNodeByTId($('#treeTId2').val());
					if(node){
						node.name=username;
						node.id=result.user.userId;
						zTreeUser.updateNode(node);
						zTreeUser.selectNode(node);
					}
				}
			}
		}

	});
}

function savePicdata(obj,url,form){
	if(!obj.val()){
		alert("请选择上传图片在提交！");
		return false;
	}
	form[0].action=url;
	form.submit();
	var ua = window.navigator.userAgent;
	if (ua.indexOf("MSIE")>=1){
		obj.select();
		document.execCommand('Delete');
		obj.blur();
	}else if(ua.indexOf("Firefox")>=1){
		obj.val("");
	}else if(ua.indexOf("Chrome")>=1){
		obj.val("");
	}
}

// 保存登陆信息
function  saveUserLogindata(){
	var password=$("#loginPassword").val();
	var password2=$("#loginPassword2").val();
	var loginName=$("#loginName").val();
	if(password&&(!loginName)){
		alert("请输入登录名");
		return false;
	}
	if(password!=password2){
		alert("两次输入的密码不一致");
		return false;
	}
	var url = cur_proj_url+'/config/user/saveUserLogin?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		async: false,
		data:$("#userLoginForm").serialize(),
		success:function (data) {
			alert(data.msg);
		}
	});
}
//******************* 以上是菜单树操作*****************************

//******************* 以上是主题菜单预览部分操作*****************************
function deleteinfo(){
	var mark=$("input[type='radio']:checked").val();
	/*	1仅删除部门，保留所有包含的用户");
	 2删除部门，并且删除所有包含的用户所有角色定义中该部门所包含的用户也将一并删除");
	 3仅删除用户与当前部门的关系信息");
	 4永久删除用户（部门和角色列表中删除此用户");*/
	var userNode = zTreeUser.getSelectedNodes()[0];
	//获取父节点所属部门信息
	var parentNode = userNode.getParentNode();
	if(mark=="3"||mark=="4"){
		var url = cur_proj_url+"/config/user/delUser";
		$.ajax({
			type: "get",
			url: url,
			data: {organ_id:parentNode.id,del_mark:mark,user_id:userNode.id},
			async: false,
			success: function (result) {
				alert(result.msg);
				if(result.success){
					zTreeUser.removeNode(userNode);
					$("#delUserMenu").modal('hide');
				}
			}
		});
	}
}
//刷新部门树
function refreshTreeUser(){
	zTreeUser.reAsyncChildNodes(null, "refresh");
}
//提供默认值
function  deafautvalue(value1,value2){
	if(value2){
		$("#"+value1).val(value2);
	}else{
		$("#"+value1).val("");
	}
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


