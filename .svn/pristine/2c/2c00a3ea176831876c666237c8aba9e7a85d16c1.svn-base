/**
 * ztree控件的数据设置
 * @type {{view: {dblClickExpand: boolean}, check: {enable: boolean}, async: {enable: boolean, url: string, type: string}, callback: {onClick: ztreeOnClick, onRightClick: ztreeOnRightClick}}}
 */
var settingRole = {
	view: {showLine: false},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/instAuth/rolejson?wdid="+cur_wdid,
		type: "get"
	},
	data: {simpleData: {enable: true}},
	edit: {
		enable: true,
		editNameSelectAll: false,
		showRenameBtn: false,
		showRemoveBtn: true
	},
	callback: {
		onClick: ztreeRoleOnClick,
		beforeRemove: beforeRemoveRole,
		onAsyncSuccess: zTreeRoleOnAsyncSuccess
	}
};
var settingResource = {
	view: {
		dblClickExpand: true
	},
	check: {enable: true},
	async: {
		enable: true,
		url:cur_proj_url+"/config/instAuth/resourcejson?roleId=&wdid="+cur_wdid,
		type: "get"
	},
	edit: {
		enable: true,
		editNameSelectAll: false,
		showRenameBtn: false,
		showRemoveBtn: true
	},
	callback: {
		onCheck: ztreeResourceOnCheck,
		onClick: ztreeResourceOnClick,
		beforeRemove: beforeRemove,
		onAsyncSuccess: zTreeResourceOnAsyncSuccess
	}
};
$(document).ready(function(){
	$.fn.zTree.init($("#"+treeRole), settingRole);
	zTreeRole = $.fn.zTree.getZTreeObj(treeRole);

	$.fn.zTree.init($("#"+treeResource), settingResource);
	zTreeResource = $.fn.zTree.getZTreeObj(treeResource);

	//下面是主题操作事件
	$('#addTreeRole').click(function () {
		addTreeRole();
	});
	/*$('#deleteTreeRole').click(function () {
		deleteTreeRole();
	});*/

	/*$('#addTreeResource').hide();
	$('#deleteTreeResource').hide();*/
	$('#addTreeResource').click(function () {
		addTreeResource();
	});
	$('#deleteTreeResource').click(function () {
		var nodes = zTreeResource.getCheckedNodes(true);
		if(nodes && nodes.length > 0){
			var resourceId = "";
			for(var i=0;i<nodes.length;i++){
				//deleteTreeResource(nodes[i].id);
				resourceId += nodes[i].id+",";
			}
			deleteTreeResource(resourceId);
		}else{
			alert("请勾选需要删除的资源！");
		}
	});
	$('#saveTreeResource').click(function () {
		saveTreeResource();
	});

	$('#savePartiListData').click(function () {
		savePartiListData();
	});

	$("#selectModal").draggable({
		handle: ".modal-top-bar",
		cursor: 'move',
		refreshPositions: false
	});
});

var cur_role_resourseAry = [];
var cur_role_id;

//*****************************************************************
//********************左边角色树相关操作*****************************
//*****************************************************************
function zTreeRoleOnAsyncSuccess(event, treeId, treeNode, msg) {
	//默认选中第一个角色
	var fRoleNode = zTreeRole.getNodes();
	if(fRoleNode && fRoleNode.length > 0){
		zTreeRole.selectNode(fRoleNode[0]);
		cur_role_id = fRoleNode[0].id;
		ztreeRoleOnClick(null, null, fRoleNode[0]);
	}
}

/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeRoleOnClick(event, treeId, treeNode) {
	cur_role_resourseAry = [];
	cur_role_id = null;
	zTreeResource.checkAllNodes(false);
	zTreeResource.checkAllNodes(false);
	zTreeRole.selectNode(treeNode);
	cur_role_id = treeNode.id;
	//根据状态显示不同的界面
	//通过后台加载该角色关联的资源记录，之后在资源树上进行打钩
	var url = cur_proj_url+'/config/instAuth/resourcejson?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{roleId:treeNode.id,wdid:cur_wdid,ischeck:true},
		success:function (data) {
			if(data && data.length > 0){
				cur_role_resourseAry = data;
				for(var i=0;i<data.length;i++){
					var rid = data[i].id;
					var cNode = zTreeResource.getNodeByParam("id", rid, null);
					if(cNode){
						zTreeResource.checkNode(cNode, true, false);
					}
				}
				//选择第一个资源
				var cNode = zTreeResource.getNodeByParam("id", data[0].id, null);
				zTreeResourceOnAsyncSuccess(null,null,cNode,null);
			}
		},
		error:function (data) {
			alert("请求失败！");
		}
	});
}

function addTreeRole() {
	//打开角色选择列表，弹出窗口将选择数据回刷回来，之后入库，刷新角色树
	if(cur_wdid){
		var param = {wdid:cur_wdid};
		var url = cur_proj_url+'/config/role/select?foreignId='+cur_wdid+'&paramString='+$.toJSON(param);
		$('#mainFrame').attr("src", url);
		$('#selectModal').modal({show:true});
	}
}
function returnSelectRoleData(param){
	//解析modal窗口返回的数据，将数据存储到后台，之后刷新系统界面
	if(param && param.keyId && param.paramString){
		//var detailJson = jQuery.parseJSON(param.paramString);
		var url = cur_proj_url+'/config/instAuth/saveRel?';
		param.wdid = param.keyId;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:param,
			success:function (data) {
				alert(data.msg);
				$('#selectModal').modal('hide');
				resetTreeRole();
				//zTreeRoleOnAsyncSuccess(null,null,null,null);
			},
			error:function (data) {
				alert("请求失败！");
			}
		});
	}
}
function deleteTreeRole(roleId) {
	if(cur_wdid && roleId){
		var url = cur_proj_url+"/config/instAuth/delRel";
		var param = {roleId:roleId,wdid:cur_wdid};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				alert(result.msg);
				if(result.success){
					resetTreeRole();
				}
			}
		});
	}
}

//右侧图标删除角色功能
function beforeRemoveRole(treeId, treeNode) {
	zTreeRole.selectNode(treeNode);
	if(confirm("确认删除“" + treeNode.name + "” 关联关系吗？")){
		deleteTreeRole(treeNode.id);
	}else{
		return false;
	}
}

//*****************************************************************
//********************中间资源树树相关操作*****************************
//*****************************************************************
function zTreeResourceOnAsyncSuccess(event, treeId, treeNode, msg) {
	if(treeNode && treeNode.id){
		zTreeResource.selectNode(treeNode);
		ztreeResourceOnClick(null, null, treeNode);
	}else{
		/*var fResourceNode = zTreeResource.getNodes();
		if(fResourceNode && fResourceNode.length > 0){
			//zTreeResource.selectNode(fResourceNode[0]);
			//ztreeResourceOnClick(null, null, fResourceNode[0]);
			//initPartiRow(fResourceNode[0].id)
		}*/
	}
}

function addTreeResource() {
	//打开资源选择列表，弹出窗口将选择数据回刷回来，之后入库，刷新资源树
	if(cur_role_id){
		var param = {roleId:cur_role_id,wdid:cur_wdid};
		var url = cur_proj_url+'/config/resource/select?foreignId='+cur_wdid+'&paramString='+$.toJSON(param);
		$('#mainFrame').attr("src", url);
		$('#selectModal').modal({show:true});
	}
}

function returnSelectResourceData(param){
	//解析modal窗口返回的数据，将数据存储到后台，之后刷新系统界面
	if(param && param.keyId && param.paramString){
		var url = cur_proj_url+'/config/instAuth/saveResourceRel?';
		param.wdid = param.keyId;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:param,
			success:function (data) {
				alert(data.msg);
				$('#selectModal').modal('hide');
				resetResource();
				setTimeout(function () {
					var roleNode = zTreeRole.getNodeByParam("id", cur_role_id, null);
					ztreeRoleOnClick(null, null, roleNode);
				}, 1000);

			},
			error:function (data) {
				alert("请求失败！");
			}
		});
	}
}
function deleteTreeResource(resourceId) {
	if(cur_wdid && cur_role_id && resourceId){
		var url = cur_proj_url+"/config/instAuth/delResourceRel";
		var param = {resourceId:resourceId,roleId:cur_role_id,wdid:cur_wdid};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				alert(result.msg);
				if(result.success){
					resetResource();
					setTimeout(function () {
						var roleNode = zTreeRole.getNodeByParam("id", cur_role_id, null);
						ztreeRoleOnClick(null, null, roleNode);
					}, 1000);
				}
			}
		});
	}
}

//保存角色、资源关联关系
function saveTreeResource() {
	if(!(cur_role_id && cur_wdid)){
		alert("请选择角色操作！");
		return;
	}
	var roleNode = zTreeRole.getNodeByParam("id", cur_role_id, null);
	//获取选中节点或者是变化节点
	var changeNodes = [];

	//处理状态变化的节点中，当前角色管理记录的数据，如果选中，则修改为不选中，因为不需要传给后台
	//如果未选中，则表示删除了关联关系，需要传递到后台
	var cur_role_resourseIdStr = "";
	if(cur_role_resourseAry && cur_role_resourseAry.length > 0){
		for(var i=0;i<cur_role_resourseAry.length;i++){
			var rid = cur_role_resourseAry[i].id;
			cur_role_resourseIdStr += rid+";";
			var cNode = zTreeResource.getNodeByParam("id", rid, null);
			if(cNode.checked){
				//zTreeResource.checkNode(cNode, false, false);
			}else{
				var node = {
					id:cur_role_resourseAry[i].id,
					name:cur_role_resourseAry[i].name,
					checked:false
				}
				changeNodes.push(node);
			}
		}
	}
	var nodes = zTreeResource.getChangeCheckedNodes();
	if(nodes && nodes.length > 0){
		for(var i=0;i<nodes.length;i++){
			if(!(nodes[i].checked && cur_role_resourseIdStr.indexOf(nodes[i].id)>-1)){
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
		var url = cur_proj_url+'/config/instAuth/updateResourceVisible?';
		var param = {
			roleId:cur_role_id,
			wdid:cur_wdid,
			paramString:$.toJSON(changeNodes)
		};
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:param,
			success:function (data) {
				alert(data.msg);
				ztreeRoleOnClick(null, null, roleNode);
				//resetResource();
			},
			error:function (data) {
				alert("请求失败！");
			}
		});
	}
}

function beforeRemove(treeId, treeNode) {
	zTreeRole.selectNode(treeNode);
	if(confirm("确认删除“" + treeNode.name + "” 关联关系吗？")){
		deleteTreeResource(treeNode.id);
	}else{
		return false;
	}
}

//树控件的复选框、单选框的check事件
function ztreeResourceOnCheck(event, treeId, treeNode) {
	if(treeNode.checked){
		zTreeResource.selectNode(treeNode);
		initPartiRow(treeNode.id);
	}else{
		zTreeResource.cancelSelectedNode(treeNode);
		initPartiRow(null);
	}
}
//树控件的树节点的点击事件
function ztreeResourceOnClick(event, treeId, treeNode) {
	zTreeResource.checkNode(treeNode, true, true);
	//加载该资源下资源分区，之后选择资源分区对应的功能
	initPartiRow(treeNode.id);
}

//*****************************************************************
//********************右侧资源分区相关操作*****************************
//*****************************************************************
/**
 * 保存菜单数据
 */
function savePartiListData(){
	var nodes = zTreeResource.getSelectedNodes();
	if (nodes && nodes.length>0) {
		var resourceId = nodes[0].id

		if(cur_wdid && cur_role_id && resourceId){
			var partAry = [];
			$('select[name="operTypeSel"]').each(function(){
				var option = $(this).find('option:selected');
				var obj = {id:$(this).attr("id"),name:option.val()};
				partAry.push(obj);
			});
			if(partAry && partAry.length > 0){
				var param = {
					wdid:cur_wdid,
					roleId:cur_role_id,
					resourceId:resourceId,
					paramString:$.toJSON(partAry)
				};
				var url = cur_proj_url+'/config/instAuth/updatePartOperType?';
				$.ajax({
					url:url,
					type:'post',
					dataType:'json',
					data:param,
					success:function (data) {
						alert(data.msg);
						initPartiRow(resourceId);
					},
					error:function (data) {
						alert("请求失败！");
					}
				});
			}
		}
	}
}

function initPartiRow(resourceId){
	$("#rpTbody").html("");
	if(cur_wdid && cur_role_id && resourceId){
		var url = cur_proj_url+"/config/instAuth/partiInfo";
		var param = {resourceId:resourceId,roleId:cur_role_id,wdid:cur_wdid};
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
						newRow += initPartOperatTypeHtml(data[i]);
						newRow += '</td>';
						newRow += '</tr>';
						$("#rpTbody").append(newRow);
					}
				}
			}
		});
	}
}

/**
 * 刷新ztree控件
 */
function resetTreeRole() {
	$.fn.zTree.init($("#"+treeRole), settingRole);
	zTreeRole = $.fn.zTree.getZTreeObj(treeRole);
	zTreeResource.checkAllNodes(false);
}

function resetResource() {
	/*var nodes = zTreeRole.getSelectedNodes();
	if (nodes && nodes.length>0) {
		ztreeRoleOnClick(null, null, nodes[0]);
		var roleId = nodes[0].id;
		settingResource.async.url = cur_proj_url+"/config/instAuth/resourcejson?roleId="+roleId+"&wdid="+cur_wdid;
		$.fn.zTree.init($("#"+treeResource), settingResource);
		zTreeResource = $.fn.zTree.getZTreeObj(treeResource);
	}*/
	settingResource.async.url = cur_proj_url+"/config/instAuth/resourcejson?wdid="+cur_wdid;
	$.fn.zTree.init($("#"+treeResource), settingResource);
	zTreeResource = $.fn.zTree.getZTreeObj(treeResource);
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
function initPartOperatTypeHtml(obj){
	var tdHtm = '<select id="'+obj.partitionId+'" class="select" name="operTypeSel">';
	/*tdHtm += '<option value="" ></option>';*/
	if(partitionOperatTypeJson){
		for(var i=0;i<partitionOperatTypeJson.length;i++){
			var type = partitionOperatTypeJson[i];
			tdHtm += '<option value="'+type.value+'" ';
			if(type.value == obj.operType){
				tdHtm += ' selected="selected"';
			}
			tdHtm += '>'+type.name+'</option>';
		}
	}
	tdHtm += '</select>';
	return tdHtm;
}
