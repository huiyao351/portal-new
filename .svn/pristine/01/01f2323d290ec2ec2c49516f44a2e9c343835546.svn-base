/**
 * ztree控件的数据设置
 * @type {{view: {dblClickExpand: boolean}, check: {enable: boolean}, async: {enable: boolean, url: string, type: string}, callback: {onClick: ztreeOnClick, onRightClick: ztreeOnRightClick}}}
 */
var settingMenu = {
	view: {dblClickExpand: true},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/menu/json",
		type: "get"
	},
	callback: {
		onClick: ztreeMenuOnClick,
		onAsyncSuccess: zTreeMenuOnAsyncSuccess
	}
};
var settingRole = {
	view: {dblClickExpand: true},
	check: {enable: true},
	async: {
		enable: true,
		url:cur_proj_url+"/config/role/json",
		type: "get"
	},
	//data: {simpleData: {enable: true}},
	callback: {
		onClick: ztreeRoleOnClick,
		onCheck: ztreeRoleOnCheck,
		onAsyncSuccess: zTreeRoleOnAsyncSuccess
	}
};
$(document).ready(function(){
	$.fn.zTree.init($("#"+treeMenu), settingMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj(treeMenu);

	$.fn.zTree.init($("#"+treeRole), settingRole);
	zTreeRole = $.fn.zTree.getZTreeObj(treeRole);

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

	$('#expandTreeRole').click(function () {
		zTreeRole.expandAll(true);
	});
	$('#collapseTreeRole').click(function () {
		zTreeRole.expandAll(false);
	});

	$('#saveTreeRole').click(function () {
		saveTreeRole();
	});

	$('#savePartiListData').click(function () {
		savePartiListData();
	});
});

var cur_menu_roleAry = [];
var cur_menu_id;

//*****************************************************************
//********************左边菜单树相关操作*****************************
//*****************************************************************
function zTreeMenuOnAsyncSuccess(event, treeId, treeNode, msg) {
	//默认选中第一个菜单
	function filter(node) {
		return (node.level > 0 && !(node.children && node.children.length > 0));
	}
	var node = zTreeMenu.getNodesByFilter(filter,true);
	zTreeMenu.selectNode(node);
	cur_menu_id = node.id;
	ztreeMenuOnClick(null, null, node);
}

/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeMenuOnClick(event, treeId, treeNode) {
	cur_menu_roleAry = [];
	cur_menu_id = null;
	zTreeMenu.selectNode(treeNode);

	zTreeRole.checkAllNodes(false);
	zTreeRole.cancelSelectedNode();
	cur_menu_id = treeNode.id;

	$("#rpTbody").html("");

	$('#saveTreeRole').hide();
	$('#savePartiListData').hide();

	//只有子节点进行授权即可，后台代码会自动获取父节点菜单，对其进行关联授权
	if(treeNode.level > 0 && !(treeNode.children && treeNode.children.length > 0)){
		$('#saveTreeRole').show();
		$('#savePartiListData').show();
		//加载该资源下资源分区，之后选择资源分区对应的功能
		initPartiRow(treeNode.id);

		//根据状态显示不同的界面
		//通过后台加载该角色关联的资源记录，之后在资源树上进行打钩
		var url = cur_proj_url+'/config/menuAuth/reljson?';
		$.ajax({
			url:url,
			type:'get',
			dataType:'json',
			data:{menuId:cur_menu_id},
			success:function (data) {
				if(data && data.length > 0){
					cur_menu_roleAry = data;
					for(var i=0;i<data.length;i++){
						var rid = data[i].id;
						var cNode = zTreeRole.getNodeByParam("id", rid, null);
						if(cNode){
							zTreeRole.checkNode(cNode, true, false);
						}
					}
					//选择第一个资源
					var cNode = zTreeRole.getNodeByParam("id", data[0].id, null);
					zTreeRoleOnAsyncSuccess(null,null,cNode,null);
				}
			},
			error:function (data) {
				alert("请求失败！");
			}
		});
	}else{
		if(treeNode.children && treeNode.children.length > 0){
			ztreeMenuOnClick(null,null,treeNode.children[0]);
		}
	}
}

//*****************************************************************
//********************中间角色树相关操作*****************************
//*****************************************************************
function zTreeRoleOnAsyncSuccess(event, treeId, treeNode, msg) {
	if(treeNode && treeNode.id){
		//默认选中第一个菜单
		function filter(node) {
			return (node.level > 0 && !(node.children && node.children.length > 0));
		}
		var node = zTreeRole.getNodesByFilter(filter,true);
		zTreeRole.selectNode(node);
		ztreeRoleOnClick(null, null, node);
	}else{
		/*var fResourceNode = zTreeRole.getNodes();
		if(fResourceNode && fResourceNode.length > 0){
			//zTreeResource.selectNode(fResourceNode[0]);
			//ztreeResourceOnClick(null, null, fResourceNode[0]);
			//initPartiSelected(fResourceNode[0].id)
		}*/
	}
}

//保存角色、资源关联关系
function saveTreeRole() {
	if(!(cur_menu_id)){
		alert("请选择菜单操作！");
		return;
	}
	var menuNode = zTreeMenu.getNodeByParam("id", cur_menu_id, null);
	//只有菜单树为子节点时，角色保存功能才有效，后台自动保存角色与父节点菜单的关系
	if(menuNode.level > 0 && !(menuNode.children && menuNode.children.length > 0)){
		//获取选中节点或者是变化节点
		var changeNodes = getChangeNodes(cur_menu_roleAry,zTreeRole);
		if(!changeNodes || changeNodes.length <= 0){
			alert("当前数据无变化，无需保存！");
		}else{
			var url = cur_proj_url+'/config/menuAuth/saveRel?';
			var param = {
				menuId:cur_menu_id,
				paramString:$.toJSON(changeNodes)
			};
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:param,
				success:function (data) {
					alert(data.msg);
					ztreeMenuOnClick(null, null, menuNode);
					//resetResource();
				},
				error:function (data) {
					alert("请求失败！");
				}
			});
		}
	}else{
		alert("菜单组无需保存权限配置！");
	}
}

//树控件的复选框、单选框的check事件
function ztreeRoleOnCheck(event, treeId, treeNode) {
	if(treeNode.checked){
		zTreeRole.selectNode(treeNode);
		initPartiSelected(treeNode.id);
	}else{
		zTreeRole.cancelSelectedNode(treeNode);
		initPartiSelected(null);
	}
}
//树控件的树节点的点击事件
function ztreeRoleOnClick(event, treeId, treeNode) {
	zTreeRole.checkNode(treeNode, true, true);
	//加载该资源下资源分区，之后选择资源分区对应的功能
	initPartiSelected(treeNode.id);
}

//*****************************************************************
//********************右侧资源分区相关操作*****************************
//*****************************************************************
/**
 * 保存菜单数据
 */
function savePartiListData(){
	var menuNode = zTreeMenu.getNodeByParam("id", cur_menu_id, null);
	//只有菜单树为子节点时，角色保存功能才有效，后台自动保存角色与父节点菜单的关系
	if(menuNode.level > 0 && !(menuNode.children && menuNode.children.length > 0)){
		var nodes = zTreeRole.getSelectedNodes();
		if (nodes && nodes.length>0) {
			var roleId = nodes[0].id;
			if(cur_menu_id && roleId){
				var partAry = [];
				$('select[name="operTypeSel"]').each(function(){
					var option = $(this).find('option:selected');
					var obj = {id:$(this).attr("id"),name:option.val()};
					partAry.push(obj);
				});
				if(partAry && partAry.length > 0){
					var param = {
						menuId:cur_menu_id,
						roleId:roleId,
						paramString:$.toJSON(partAry)
					};
					var url = cur_proj_url+'/config/menuAuth/updatePartOperType?';
					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						data:param,
						success:function (data) {
							alert(data.msg);
							initPartiRow(cur_menu_id);
							setTimeout(function () {
								initPartiSelected(roleId);
							}, 500);
						},
						error:function (data) {
							alert("请求失败！");
						}
					});
				}
			}
		}
	}else{
		alert("菜单组无需保存权限配置！");
	}
}
/**
 * 根据菜单获取菜单对应的资源的功能分区
 * @param resourceId
 */
function initPartiRow(menuId){
	$("#rpTbody").html("");
	if(menuId){
		var url = cur_proj_url+"/config/menuAuth/partiInfo";
		var param = {menuId:menuId};
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
 * 根据菜单和角色，获取该角色对该菜单的功能分区配置
 * @param resourceId
 */
function initPartiSelected(roleId){
	$('#rpTbody .select').val("");
	if(cur_menu_id && roleId){
		var url = cur_proj_url+"/config/menuAuth/partiOperInfo";
		var param = {menuId:cur_menu_id,roleId:roleId};
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (data) {
				if(data && data.length > 0){
					for(var i=0;i<data.length;i++){
						var keyId = data[i].authorizeObjId;
						//获取select，之后选中数据
						var partSelect = $('#'+keyId);
						$('#'+keyId).val(data[i].operateType);
					}
				}
			}
		});
	}
}

function resetTreeMenu() {
	$.fn.zTree.init($("#"+treeMenu), settingMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj(treeMenu);
}

/**
 * 刷新ztree控件
 */
function resetTreeRole() {
	$.fn.zTree.init($("#"+treeRole), settingRole);
	zTreeRole = $.fn.zTree.getZTreeObj(treeRole);
	zTreeRole.checkAllNodes(false);
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
	tdHtm += '<option value="" ></option>';
	if(partitionOperatTypeJson){
		for(var i=0;i<partitionOperatTypeJson.length;i++){
			var type = partitionOperatTypeJson[i];
			tdHtm += '<option value="'+type.value+'" ';
			/*if(type.value == obj.operType){
				tdHtm += ' selected="selected"';
			}*/
			tdHtm += '>'+type.name+'</option>';
		}
	}
	tdHtm += '</select>';
	return tdHtm;
}
