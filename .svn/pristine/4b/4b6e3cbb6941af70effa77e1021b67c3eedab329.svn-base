/**
 * ztree控件的数据设置
 */

var settingOrgan = {
	view: {dblClickExpand: true},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/organ/json?",
		type: "get"
	},
	callback: {
		onClick: ztreeOrganOnClick,
		onRightClick: ztreeOnRightClick,
		onAsyncSuccess: zTreeOrganOnAsyncSuccess
	}
};
var organUserSetting = {
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
	$.fn.zTree.init($("#"+treeOrgan), settingOrgan);
	zTreeOrgan = $.fn.zTree.getZTreeObj(treeOrgan);
	$('#expandTreOrgan').click(function () {
		zTreeOrgan.expandAll(true);
	});
	$('#collapseTreeOrgan').click(function () {
		zTreeOrgan.expandAll(false);
	});

	$('#organBtn').click(function () {
		saveOrgandata();
	});

	$('#openTreeUserSelect').click(function () {
		openTreeUserSelect();
	});

	$(".selectConfigModal").draggable({
		handle: ".modal-top-bar",
		cursor: 'move',
		refreshPositions: false
	});

	$('#organBtn').hide();
	initdisselect("regionCode");
});
//******************* 以下是主题树相关操作*****************************
/**
 * 刷新树节点样式
 */
function zTreeOrganOnAsyncSuccess(event, treeId, treeNode,msg) {
	//默认选中第一个角色
	var nodes = zTreeOrgan.getNodes();
	if(nodes && nodes.length > 0){
		var rootNode = nodes[0];
		if(rootNode && rootNode.children && rootNode.children.length > 0){
			var childNode = rootNode.children[0];
			zTreeOrgan.selectNode(childNode);
			ztreeOrganOnClick(null, null, childNode);
			zTreeOrgan.expandNode(childNode, true, false, true);
		}
	}
}
//******************* 以上是主题树相关操作*****************************
//******************* 以下是部门树左键操作*****************************
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeOrganOnClick(event, treeId, treeNode) {
	zTreeOrgan.selectNode(treeNode);
	if(treeNode.id=="treeother"){
		$.ajax({
			type: "get",
			url: cur_proj_url+"/config/organ/findOtherUser",
			async: false,
			success: function (result) {
				initOrganUser(treeNode.id);
			}
		});
	}else{
		if(treeNode.id &&(!treeNode.mark) && treeNode.id != 'treeroot'){
			//获取节点信息
			var url = cur_proj_url+"/config/organ/info";
			var param = {organ_id:treeNode.id}
			$.ajax({
				type: "get",
				url: url,
				data: param,
				async: false,
				success: function (result) {
					$('#organBtn').show();
					var organ= result.organ;
					$('#treeTId').val(treeNode.tId);
					$("#organId").val(organ.organId);
					deafautvalue("organName",organ.organName);
					deafautvalue("officeTel",organ.officeTel);
					deafautvalue("remark",organ.remark);
					deafautvalue("superOrganId",organ.superOrganId);
					deafautvalue("organNo",organ.organNo);
					deafautvalue("email",organ.email);
					if(organ.createDate){
						$("#createDate").val(new Date(organ.createDate).Format("yyyy-MM-dd"));
					}else{
						$("#createDate").val("");
					}
					if(organ.regionCode){
						$("#regionCode").find("option[value="+organ.regionCode+"]").prop("selected","selected");
					}
					$("#organInfoDiv").show();
					initOrganUser(organ.organId);
				}
			});
		}
	}
}
//***********************************************************
//******************以下是部门树右键及其菜单操作操作*****************************
function ztreeOnRightClick(event, treeId, treeNode) {
	if (treeNode && !treeNode.noR) { //noR属性为true表示禁止右键菜单
		zTreeOrgan.selectNode(treeNode);
		showRMenu( event.clientX, event.clientY);
	}
}
function showRMenu( x, y) {
	$("#rOrganMenu").show();
	var pnode=  zTreeOrgan.getSelectedNodes()[0];
	if(pnode.id=="treeroot"){
		$("#m_adduser").hide();
		$("#m_del").hide();
	}else if(pnode.id=="treeother"){
		$("#rOrganMenu").hide();
	}else{
		$("#m_adduser").show();
		$("#m_del").show();
	}
	$("#rOrganMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
	if (!(event.target.id == "rOrganMenu" || $(event.target).parents("#rOrganMenu").length>0)) {
		$("#rOrganMenu").css({"visibility" : "hidden"});
	}
}
function removeTreeNode() {
	$("#roletitle").html("");
	$("#delmark").html("");
	$("#delmark2").html("");
	$("#roletitle").html("<i class='icon-question-sign'></i>删除部门(谨慎操作)");
	$("#delmark").html("<input name='delmark'  checked='checked' type='radio' value='1'/>仅删除部门，保留所包含的用户");
	$("#delmark2").html("<input name='delmark'   type='radio' value='2'/>删除部门，并且删除所包含用户");
	$("#delUserMenu").modal('show');
	hideRMenu();

}
function hideRMenu() {
	$("#rOrganMenu").hide();
	$("body").unbind("mousedown", onBodyMouseDown);
}
function addTreeNode() {
	$("#organForm input[type='hidden']").val("");
	$("#organForm input[type='text']").val("");
	$("#organForm textarea").val("");
	$("#organInfoDiv").show();
	$('#organBtn').show();
	var curNode=  zTreeOrgan.getSelectedNodes()[0];
	if(curNode && curNode.kz1){
		$("#regionCode").find("option[value='"+curNode.kz1+"']").attr("selected","selected");
	}
	hideRMenu();
}
//***********************************************************

/**
 * 保存部门数据
 */
function saveOrgandata(){
	//数据必填，不能为空
	var title = $('#organName').val();
	var organNo = $('#organNo').val();
	if(!title ){
		alert("请填写要添加的部门名称");
		return;
	}
	if(!organNo ){
		alert("部门编号不能为空");
		return;
	}

	var url = cur_proj_url+'/config/organ/save?';
	//获取所要添加的父节点Id
	var pnode=  zTreeOrgan.getSelectedNodes()[0];
	//如果是虚拟的部门节点，添加部门时候，父id为空
	if(!$("#organId").val()){
		$("#superOrganId").val(pnode.id);
	}
	if(pnode.id=="treeroot"){
		$("#superOrganId").val("");
	}
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		async: false,
		data:$("#organForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				var organ=data.organ;
				var id=organ.organId;
				var name=organ.organName;
				var pid=organ.superOrganId;
				var newNode = { name:name,pId:pid,id:id};
				if($("#organId").val()){
					var node = zTreeOrgan.getNodeByTId($('#treeTId').val());
					node.name=name;
					node.id=id;
					node.pid=pid;
					zTreeOrgan.updateNode(node);
					zTreeOrgan.selectNode(node);
				}else{
					newNode=zTreeOrgan.addNodes(pnode, newNode);
				}
			}
		},
		error:function (data) {
			alert("保存失败");
		}
	});
}
function deleteinfo(){
	var mark=$("#delUserMenu input[type='radio'][name='delmark']:checked").val();
	/*	1仅删除部门，保留所有包含的用户");
	 2删除部门，并且删除所有包含的用户所有角色定义中该部门所包含的用户也将一并删除");
	 3仅删除用户与当前部门的关系信息");
	 4永久删除用户（部门和角色列表中删除此用户");*/
	var organnode = zTreeOrgan.getSelectedNodes()[0];
	if(mark=="1"||mark=="2"){
		var url = cur_proj_url+"/config/organ/delOrgan";
		$.ajax({
			type: "get",
			url: url,
			data: {organ_id:organnode.id,del_mark:mark},
			async: false,
			success: function (result) {
				alert(result.msg);
				if(result.success){
					zTreeOrgan.removeNode(organnode);
					$("#delUserMenu").modal('hide');
					refreshTreeOrgan();
				}
			}
		});
	}
}
//刷新部门树
function  refreshTreeOrgan(){
	zTreeOrgan.reAsyncChildNodes(null, "refresh");
}
//***********************************************************
//**********************删除用于关系**************
function initOrganUser(organId){
	if(organId){
		organUserSetting.async.url = cur_proj_url+'/config/organ/userjson?organId='+organId;
		$.fn.zTree.init($("#"+treeOrganUser), organUserSetting);
		zTreeOrganUser = $.fn.zTree.getZTreeObj(treeOrganUser);
	}
}
function openTreeUserSelect() {
	//打开部门选择列表，弹出窗口将选择数据回刷回来，之后入库，刷新角色树
	var selectNode = zTreeOrgan.getSelectedNodes()[0];
	if(selectNode && selectNode.id && !selectNode.group && selectNode.level > 0){
		var foreignId = selectNode.id;
		var param = {organId:foreignId};
		var url = cur_proj_url+'/config/user/select?foreignId='+foreignId+'&paramString='+$.toJSON(param);
		$('#selectFrame').attr("src", url);
		$('#selectModal').modal({show:true});
	}
}
function returnSelectUserData(param){
	//解析modal窗口返回的数据，将数据存储到后台，之后刷新系统界面
	if(param && param.keyId && param.paramString){
		var selectNode = zTreeOrgan.getSelectedNodes()[0];
		if(selectNode && selectNode.id && !selectNode.group && selectNode.level > 0){
			var organId = selectNode.id;
			var url = cur_proj_url+'/config/organ/saveOrganUserRel?';
			param.organId = organId;
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:param,
				success:function (data) {
					alert(data.msg);
					$('#selectModal').modal('hide');
					initOrganUser(organId);
				},
				error:function (data) {
					alert("请求失败！");
				}
			});
		}
	}
}

function beforeRemoveUser(treeId, treeNode) {
	zTreeOrganUser.selectNode(treeNode);
	if(confirm("确认删除“" + treeNode.name + "” 关联关系吗？")){
		var nodes=	zTreeOrgan.getSelectedNodes();
		var organId=nodes[0].id;
		deleteTreeOrgan(organId,treeNode.id);
	}else{
		return false;
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

//***********************************************************

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

