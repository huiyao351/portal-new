/**
 * ztree控件的数据设置
 */
var settingWfd = {
	check: {enable: false},
	async: {
		enable: true,
		url:cur_proj_url+"/config/workflow/json",
		type: "get"
	},
	callback: {
		onClick: ztreeWfdOnClick,
		onRightClick: ztreeWfdOnRightClick
	}
};

$(document).ready(function(){
	$.fn.zTree.init($("#"+treeWfd), settingWfd);
	zTreeWfd = $.fn.zTree.getZTreeObj(treeWfd);
	rMenuWorkflow = $("#rMenuWorkflow");

	//下面是菜单树事件
	$('#expandTreeWfd').click(function () {
		zTreeWfd.expandAll(true);
	});
	$('#collapseTreeWfd').click(function () {
		zTreeWfd.expandAll(false);
	});
	$('#refreshTreeWfd').click(function () {
		resetTreeWfd();
	});
	$('#m_wfd_stuff_tree').click(function () {
		openStuffList();
	});
	$('#m_wfd_inst_auth').click(function () {
		openWfdInstAuthConfig();
	});
	$('#m_wfd_image_tree').click(function () {
		openWfdImage();
	});

	$('#wfdImageTree').click(function () {
		openWfdImage();
	});
	$('#wfdStuffTree').click(function () {
		openStuffList();
	});
	$('#wfdInstAuthTree').click(function () {
		openWfdInstAuthConfig();
	});

	$('#wfdBtn').click(function () {
		saveWfddata();
	});
	$('#wfdStuffBtn').click(function () {
		openStuffList();
	});
	$("#wfdBtn").hide();
	$("#wfdStuffBtn").hide();

	$('#bsBtn').click(function () {
		saveBsdata();
	});
	$("#bsBtn").hide();


	$("#stuffModal").draggable({
		handle: ".modal-top-bar",
		cursor: 'move',
		refreshPositions: false
	});
	$("#imageModal").draggable({
		handle: ".modal-top-bar",
		cursor: 'move',
		refreshPositions: false
	});
});
//******************* 以下是主题树相关操作*****************************
/**
 * 左键点击
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeWfdOnClick(event, treeId, treeNode) {
	//根据所点击的节点，判断是打开业务表单还是工作流表单
	$("#wfdForm input[type='hidden']").val("");
	$("#wfdForm input[type='text']").val("");
	zTreeWfd.selectNode(treeNode);
	if(treeNode.level > 0){
		var url = null;
		var iswfd = false;
		var param = {keyId:treeNode.id}
		if(treeNode.level == 1) {
			//获取业务信息
			iswfd = false;
			url = cur_proj_url+"/config/workflow/infoBs";
		}else if(treeNode.level > 1){
			//获取工作流定义信息
			iswfd = true;
			url = cur_proj_url+"/config/workflow/info";
		}
		$.ajax({
			type: "get",
			url: url,
			data: param,
			async: false,
			success: function (result) {
				$('#bsInfoDiv').show();
				if(!iswfd){
					initBusinessForm(treeNode,result);
				}else{
					initWfdForm(treeNode,result);
				}
			}
		});
	}
}
function initBusinessForm(treeNode,result){
	$('#wfdInfoDiv').hide();
	$('#bsInfoDiv').show();
	$('#bsBtn').show();
	$('#bs_treeTId').val(treeNode.tId);

	$('#businessId').val(result.businessId);
	$('#bs_businessName').val(result.businessName);
	$('#businessCode').val(result.businessCode);
	$('#remark').val(result.remark);
	$('#businessNo').val(result.businessNo);
	$('#datasourceUrl').val(result.datasourceUrl);
	$('#datasourceType').val(result.datasourceType);
	$('#datasourceUser').val(result.datasourceUser);
	$('#datasourcePass').val(result.datasourcePass);
	$('#businessUrl').val(result.businessUrl);
	$('#bs_remark').val(result.remark);
}
function initWfdForm(treeNode,result){
	$('#wfdInfoDiv').show();
	$('#bsInfoDiv').hide();
	$('#wfdBtn').show();
	$('#wfdStuffBtn').show();

	$('#treeTId').val(treeNode.tId);

	$('#workflowDefinitionId').val(result.workflowDefinitionId);
	$('#businessName').val(result.business.businessName);
	$('#regionCode').val(result.regionCode);
	$('#workflowCode').val(result.workflowCode);
	$('#workflowName').val(result.workflowName);
	$('#workflowVersion').val(result.workflowVersion);
	$('#priority').val(result.priority);
	$('#remark').val(result.remark);
	$('#createUrl').val(result.createUrl);
	$('#createHeight').val(result.createHeight);
	$('#createWidth').val(result.createWidth);
	$('#workflowDefinitionNo').val(result.workflowDefinitionNo);
	$('#isValid').val(result.isValid);
	$('#isMonitor').val(result.isMonitor);
	$('#timeLimit').val(result.timeLimit);
	$('#remark').val(result.remark);
}
/**
 * 保存菜单数据
 */
function saveWfddata(){
	//数据必填，不能为空
	var timeLimit = $('#timeLimit').val();
	var name = $('#workflowName').val();
	if(!timeLimit || !name){
		alert("请完整工作流定义名称和办理时限！");
		return;
	}
	var url = cur_proj_url+'/config/workflow/save?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#wfdForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				var node = zTreeWfd.getNodeByTId($('#treeTId').val());
				node.name=data.wfd.workflowName;

				initWfdForm(node,data.wfd);
				zTreeWfd.updateNode(node);
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
function saveBsdata(){
	//数据必填，不能为空
	var name = $('#bs_businessName').val();
	if(!name){
		alert("请输入业务名称！");
		return;
	}
	var url = cur_proj_url+'/config/workflow/saveBs?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#bsForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				var node = zTreeWfd.getNodeByTId($('#bs_treeTId').val());
				node.name=data.business.businessName;
				initBusinessForm(node,data.business);
				zTreeWfd.updateNode(node);
			}
		},
		error:function (data) {
			alert("保存失败");
		}
	});
}

/**
 * 显示右键菜单
 * @param type
 * @param x
 * @param y
 */
function showRMenu(type, x, y) {
	rMenuWorkflow.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	$("body").bind("mousedown", onBodyMouseDown);
}
//隐藏右键菜单
function hideRMenu() {
	if (rMenuWorkflow) rMenuWorkflow.css({"visibility": "hidden"});
	$("body").unbind("mousedown", onBodyMouseDown);
}
//右键相关设置
function onBodyMouseDown(event){
	if (!(event.target.id == "rMenuWorkflow" || $(event.target).parents("#rMenuWorkflow").length>0)) {
		rMenuWorkflow.css({"visibility" : "hidden"});
	}
}

/**
 * 右键
 * @param event
 * @param treeId
 * @param treeNode
 */
function ztreeWfdOnRightClick(event, treeId, treeNode) {
	if(treeNode && treeId){
		if (treeNode.level == 2) {
			zTreeWfd.selectNode(treeNode);
			$('#workflowDefinitionId').val(treeNode.id);
			showRMenu("root", event.clientX, event.clientY);
		}
	}
}

/**
 * 刷新ztree控件
 */
function resetTreeWfd() {
	$.fn.zTree.init($("#"+treeWfd), settingWfd);
	zTreeWfd = $.fn.zTree.getZTreeObj(treeWfd);

	$("#wfdForm input[type='hidden']").val("");
	$("#wfdForm input[type='text']").val("");

	$('#wfdBtn').hide();
	$('#wfdStuffBtn').hide();

	$('#wfdInfoDiv').show();
	$('#bsInfoDiv').hide();
}
function getSelectNodeId(){
	if (zTreeWfd.getSelectedNodes()[0]) {
		var node = zTreeWfd.getSelectedNodes()[0];
		if(node.id){
			return node.id;
		}
	}
	return null;
}
function openStuffList(){
	var wfdId = getSelectNodeId();
	if(wfdId){
		var url = cur_proj_url+'/config/stuff?wfdId='+wfdId;
		//window.open(url);
		$('#mainFrame').attr("src", url);
		$('#stuffModal').modal({show:true});
	}else{
		alert("请选择一个工作流定义！");
	}
}

function openWfdImage(){
	var wfdId = getSelectNodeId();
	if(wfdId){
		var url = cur_proj_url+"/config/workflow/image?keyId="+wfdId;
		$('#wfdImage').attr("src",url);
		$('#imageModal').modal({show:true});
	}else{
		alert("请选择一个工作流定义！");
	}
}

function openWfdInstAuthConfig(){
	var wfdId = getSelectNodeId();
	if(wfdId){
		var url = cur_proj_url+'/config/instAuth?wdid='+wfdId;
		//window.open(url);
		$('#mainFrame').attr("src", url);
		$('#stuffModal').modal({show:true});
	}else{
		alert("请选择一个工作流定义！");
	}
}