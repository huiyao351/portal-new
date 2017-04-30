/**
 * ztree控件的数据设置
 */
var settingStuff = {
	view: {dblClickExpand: true},
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/stuff/json?wfdId="+_wfdId,
		type: "get"
	},
	callback: {
		onClick: ztreeStuffOnClick,
		onRightClick: ztreeStuffOnRightClick,
		onAsyncSuccess: zTreeStuffOnAsyncSuccess
	}
};
$(document).ready(function(){
	$.fn.zTree.init($("#"+treeStuff), settingStuff);
	zTreeStuff = $.fn.zTree.getZTreeObj(treeStuff);
    rStuff = $("#rStuff");

	$('#m_addstuff').click(function () {
		addTableStuff();
	});

	$('#m_delstuff').click(function () {
		deleteTableStuff();
	});

	$('#stuffBtn').click(function () {
		saveStuffdata();
	});

    $('#expandTreeStuff').click(function () {
        zTreeStuff.expandAll(true);
    });
    $('#collapseTreeStuff').click(function () {
        zTreeStuff.expandAll(false);
    });

    $('#refreshTreeStuff').click(function () {
        resetTreeStuff();
    });

	$('#workflowDefinitionId').val($('#wfdId').val());
	//$('#stuffBtn').hide();
});

var addCountStuff = 1;
//******************* 包含用户树左右键点击和加载成功事件*****************************
/**
 * 刷新树节点样式
 */
function zTreeStuffOnAsyncSuccess(event, treeId, treeNode,msg) {
	//默认选中第一个
	function filter(node) {
		return (node.level > 0);
	}
	var node = zTreeStuff.getNodesByFilter(filter,true);
	zTreeStuff.selectNode(node);
	ztreeStuffOnClick(null, null, node);
}
//用户菜单左击
function ztreeStuffOnClick(event, treeId, treeNode) {
	refreshStuffForm();
	zTreeStuff.selectNode(treeNode);
	if(treeNode.id && treeNode.level > 0){
		updateTableStuff(treeNode.id);
	}
}

function updateTableStuff(id) {
	if(id){
		var url = cur_proj_url+'/config/stuff/info?';
		$.ajax({
			url: url,
			type:'get',
			dataType:'json',
			data:{keyId:id},
			success:function (data) {
				intiStuffForm(data);
			}
		})
	}
}

/**
 * 保存菜单数据
 */
function saveStuffdata(){
	var parentNode = zTreeStuff.getSelectedNodes()[0];
	//数据必填，不能为空
	var stuffXh = $('#stuffXh').val();
	var stuffName = $('#stuffName').val();
	var stuffCount = $('#stuffCount').val();
	if(!stuffXh || !stuffName || !stuffCount){
		alert("请完整填写序号、名称、总数信息！");
		return;
	}
	var url = cur_proj_url+'/config/stuff/save?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#stuffForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				if(data.insert){
					var newNode = { name:data.stuffConfig.stuffName,id:data.stuffConfig.stuffId};
					zTreeStuff.addNodes(parentNode,newNode);
					newNode=zTreeStuff.getNodeByParam("id", newNode.id);
					zTreeStuff.selectNode(newNode);
				}else{
					var node = zTreeStuff.getNodeByParam("id",data.stuffConfig.stuffId);
					if(node){
						node.name=data.stuffConfig.stuffName;
                        zTreeStuff.updateNode(node);
                        zTreeStuff.selectNode(node);
					}
				}
			}
		},
		error:function (data) {
			alert("保存失败");
		}
	});
}

function intiStuffForm(result){
	$('#stuffId').val(result.stuffId);
    $('#proId').val(result.proId);
	$('#stuffXh').val(result.stuffXh);
	$('#stuffName').val(result.stuffName);
	$('#meterial').val(result.meterial);
	$('#stuffCount').val(result.stuffCount);
	$('#remark').val(result.remark);
	$('#workflowDefinitionId').val(result.workflowDefinitionId);
	$('#ysnum').val(result.ysnum);
	$('#dbnum').val(result.dbnum);
}

function refreshStuffForm() {
	$("#stuffForm input[type='hidden']").val("");
	$("#stuffForm input[type='text']").val("");
	$("#stuffForm textarea").val("");
	$("#stuffForm")[0].reset();
}

/**********************************************************************
/**
 * 右键相关处理
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeStuffOnRightClick(event, treeId, treeNode) {
    if (!treeNode) {
        zTreeStuff.cancelSelectedNode();
    } else if (treeNode && !treeNode.noR) { //noR属性为true表示禁止右键菜单
        zTreeStuff.selectNode(treeNode);
        if (treeNode.level ==0) {
            showRStuffMenu("root", event.clientX, event.clientY);
        } else {
            showRStuffMenu("node", event.clientX, event.clientY);
        }
    }
}
function showRStuffMenu(type,x, y) {
    $("#rStuff ul").show();
    if (type=="root") {
        $("#m_delstuff").hide();
    }else if(type=="node"){
        $("#m_delstuff").show();
    }
	rStuff.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyStuffMouseDown);
}
//隐藏右键菜单
function hideRStuffMenu() {
	if (rStuff) rStuff.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyStuffMouseDown);
}
//右键相关设置
function onBodyStuffMouseDown(event){
    if (!(event.target.id == "rStuff" || $(event.target).parents("#rStuff").length>0)) {
        rStuff.css({"visibility" : "hidden"});
    }
}
/**
 * 增加节点
 */
function addTableStuff() {
    var stuffNode = zTreeStuff.getSelectedNodes()[0];
    if(stuffNode == null || stuffNode.id == null){
        alert('请选择需要操作的记录！');
        return;
    }

	refreshStuffForm();
	$('#workflowDefinitionId').val($('#wfdId').val());
	$('#stuffName').val("新增" + (addCountStuff++));
	$('#stuffCount').val(1);
    $('#proId').val(stuffNode.id);
    if(stuffNode.id != "stuffroot"){
        $('#proId').val(stuffNode.id);
    }

	hideRStuffMenu();
}

/**
 * 删除节点
 */
function deleteTableStuff() {
	var stuffNode = zTreeStuff.getSelectedNodes()[0];
	if(stuffNode == null || stuffNode.id == null){
		alert('请选择需要操作的记录！');
		return;
	}else {
		if (!confirm("是否确认删除操作？该操作不可恢复！")) {
			return;
		}

		var url = cur_proj_url+'/config/stuff/del?';
		$.ajax({
			url: url,
			type:'get',
			dataType:'json',
			data:{keyId:stuffNode.id},
			success:function (data) {
				alert(data.msg);
				if(data.success){
					zTreeStuff.removeNode(stuffNode);
					refreshStuffForm();
				}
			}
		})
		hideRStuffMenu();
	}
}
function resetTreeStuff() {
    settingStuff.async.url = cur_proj_url+"/config/stuff/json?wfdId="+_wfdId,
    $.fn.zTree.init($("#"+treeStuff), settingStuff);
    zTreeStuff = $.fn.zTree.getZTreeObj(treeStuff);
}