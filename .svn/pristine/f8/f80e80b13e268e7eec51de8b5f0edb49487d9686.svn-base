$(document).ready(function(){
	$('#resourceId').val($('#rId').val());
	$('#pi_partitionId').val($('#pId').val());
	$('#pfPartitionInfoId').val($('#piId').val());
	if(!$('#pi_partitionId').val()){
		clearPIPanel();
	}
	/**
	 * 全选按钮事件
	 */
	$('.ckbSelectAll').change(function(){
		$(this).parents(".table").children('tbody').find('input[type=checkbox]').prop("checked",$(this).is(':checked'));
	});

	//下面是主题操作事件
	$('#addTableRP').click(function () {
		addTableRP();
	});
	$('#deleteTableRP').click(function () {
		deleteTableRP();
	});

	$('#rpBtn').click(function () {
		saveRPdata();
	});

	$('#addTablePI').click(function () {
		addTablePI();
	});
	$('#deleteTablePI').click(function () {
		deleteTablePI();
	});
	$('#piBtn').click(function () {
		savePIdata();
	});

	refreashRPTableList();

	//initRPTrClick();
	initPITrClick();

	//默认选中第一条
	updateTableRP($('#pId').val());
	$("#rpTbody tr:first").find('td').addClass("selectTableTr");

	$('#resourceId').val($('#rId').val());
});

//初始化左边表格的点击事件，为了处理增加行后，没有点击事件问题
function initRPTrClick(){
	$('#rpTbody tr[class*="detailInfo"]').unbind("click").click(function (){
		clearPIPanel();
		$('#rpTbody tr[class*="detailInfo"]').find('td').removeClass("selectTableTr");
		$(this).find('td').addClass("selectTableTr");
		var id = $(this).children('td').eq(0).find('input[class*="checkbox"]').attr("itemId");
		updateTableRP(id);
	});
}

//刷新左边表格的相关数据处理，包含了字典列格式化、奇偶行背景色和点击事件
function refreashRPTableList(){
	//处理字典类
	$("#rpTbody tr td:nth-child(3)").each(function () {
		$(this).text(initType($(this).text()));
	});

	initTableBackground("rpInfoDiv");

	initRPTrClick();
}

var addCountRP = 1;
/**
 * 增加节点
 */
function addTableRP() {
	if($('#rId').val() && $('#rId').val() != ""){
		refreshRPForm();

		$('#resourceId').val($('#rId').val());
		$('#partitionName').val("分区" + (addCountRP++));
		$('#partitionType').attr("readonly",false);


	}
	//清空右侧元素区域数据
	clearPIPanel();
}
function updateTableRP(id) {
	refreshRPForm();
	clearPIPanel();
	if(id){
		var url = cur_proj_url+'/config/parti/info?';
		$.ajax({
			url: url,
			type:'get',
			dataType:'json',
			data:{keyId:id},
			success:function (data) {
				intiRPForm(data);
				initPIList(id);
			}
		})
	}
}

/**
 * 保存菜单数据
 */
function saveRPdata(){
	//数据必填，不能为空
	var partitionName = $('#partitionName').val();
	if(!partitionName){
		alert("请完整填写名称信息！");
		return;
	}
	$('#resourceId').val($('#rId').val());
	var url = cur_proj_url+'/config/parti/save?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#rpForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				//window.location.reload();
				//如果是插入，则在表格下面增加一行，如果是修改，则修改原来行数据
				var keyId = data.rp.partitionId;
				if(keyId){
					intiRPForm(data.rp);
					if(data.isadd){
						var newRow = '<tr class="detailInfo" id="'+keyId+'">';
						newRow += '<td><input class="checkbox" type="checkbox" itemId="'+keyId+'"><input type="hidden" value="'+keyId+'"/></td>';
						newRow += '<td>'+data.rp.partitionName+'</td>';
						newRow += '<td>'+initType(data.rp.partitionType)+'</td>';
						newRow += '</tr>';
						//$("#rpInfoDiv table tr:last").after(newRow);
						$("#rpTbody").append(newRow);
					}else{
						var tr = $('#rpTbody tr[id="'+keyId+'"]');
						/*var td1 = tr.children().eq(0);
						$(td1).find('input[type="checkbox"]').attr("itemId",keyId);*/
						tr.children().eq(1).text(data.rp.partitionName);
						tr.children().eq(2).text(initType(data.rp.partitionType));
					}
					refreashRPTableList();
					updateTableRP(keyId);

					$('#rpTbody tr[class*="detailInfo"]').find('td').removeClass("selectTableTr");
					$('#rpTbody tr[id="'+keyId+'"]').find('td').addClass("selectTableTr");
				}
			}
			//initRPTableList();
		},
		error:function (data) {
			alert("保存失败");
		}
	});
}

function intiRPForm(result){
	$('#partitionId').val(result.partitionId);
	$('#resourceId').val(result.resourceId);
	$('#partitionName').val(result.partitionName);
	$('#partitionType').val(result.partitionType);

	$('#pi_partitionId').val(result.partitionId);

	$('#piPanel input').show();
	$('#piPanel button').show();
	$('#piPanel a').show();
	$('#piPanel .configMainDiv').show();
	$('#piPanel .configBottomDiv').show();
}

function refreshRPForm() {
	$("#rpForm input[type='hidden']").val("");
	$("#rpForm input[type='text']").val("");
}

/**
 * 删除节点
 */
function deleteTableRP() {
	var selectItems = getSelectedItems($('#rpInfoDiv'),'itemId');
	if(selectItems==null||selectItems.length==0){
		alert('请选择需要操作的记录！');
		return;
	}else {
		if (!confirm("是否确认删除操作？该操作不可恢复！")) {
			return;
		}
		var ids = "";
		for (var i = 0; i < selectItems.length; i++) {
			var id = selectItems[i].itemId;
			if(id){
				ids += id + ",";
			}
		}
		var url = cur_proj_url+'/config/parti/del?';
		$.ajax({
			url: url,
			type:'get',
			dataType:'json',
			data:{keyId:ids},
			success:function (data) {
				alert(data.msg);
				if(data.success){
					//刷新列表
					for (var i = 0; i < selectItems.length; i++) {
						var id = selectItems[i].itemId;
						if(id){
							$('#rpTbody tr[id="'+id+'"]').remove();
						}
					}
				}
				initTableBackground("rpInfoDiv");
				updateTableRP(null);
				$("#piTbody").html("");
			}
		})
	}
}

//*****************************************************************
//*****************************************************************

function initPIList(pId){
	if(pId){
		initPIPanel(pId);
		var url = cur_proj_url+'/config/parti/jsonPI?';
		$.ajax({
			url: url,
			type:'get',
			dataType:'json',
			data:{pId:pId},
			success:function (data) {
				//遍历返回数据
				if(data && data.length > 0){
					for(var i=0;i<data.length;i++){
						var newRow = initNewPITr(data[i]);
						$("#piTbody").append(newRow);
					}
					$('#piId').val(data[0].pfPartitionInfoId);
					$('#pfPartitionInfoId').val($('#piId').val());
				}
				if($('#piId').val()){
					$("#piTbody tr:first").find('td').addClass("selectTableTr");
					updateTablePI($('#piId').val());
					initTableBackground("piInfoDiv");
				}
			}
		})
	}else{
		clearPIPanel();
	}
}

//初始化右边表格的点击事件，为了处理增加行后，没有点击事件问题
function initPITrClick(){
	$('#piTbody tr[class*="detailInfo"]').unbind("click").click(function (){
		//$('#piTbody tr[class*="detailInfo"]').click(function () {
		$('#piTbody tr[class*="detailInfo"]').find('td').removeClass("selectTableTr");
		$(this).find('td').addClass("selectTableTr");
		var id = $(this).children('td').eq(0).find('input[class*="checkbox"]').attr("itemId");
		updateTablePI(id);
	});
}

var addCountPI = 1;
function addTablePI() {
	refreshPIForm();
	if($('#pId').val() && $('#pId').val() != ""){
		$('#piBtn').show();
		$('#pi_partitionId').val($('#pId').val());
		$('#elementId').val("元素" + (addCountPI++));
	}
}
function updateTablePI(id) {
	if(id){
		var url = cur_proj_url+'/config/parti/infoPI?';
		$.ajax({
			url: url,
			type:'get',
			dataType:'json',
			data:{keyId:id},
			success:function (data) {
				intiPIfForm(data);
			}
		})
	}else{
		refreshPIForm();
	}
}

/**
 * 保存菜单数据
 */
function savePIdata(){
	//数据必填，不能为空
	var elementId = $('#elementId').val();
	var elementName = $('#elementName').val();
	if(!elementId || !elementName){
		alert("请完整填写名称、标题信息！");
		return;
	}
	var url = cur_proj_url+'/config/parti/savePI?';
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:$("#piForm").serialize(),
		success:function (data) {
			alert(data.msg);
			if(data.success){
				//如果是插入，则在表格下面增加一行，如果是修改，则修改原来行数据
				var keyId = data.pi.pfPartitionInfoId;
				if(keyId){
					intiPIfForm(data.pi);
					if(data.isadd){
						var newRow = initNewPITr(data.pi);
						$("#piTbody").append(newRow);
					}else{
						var tr = $('#piTbody tr[id="'+keyId+'"]');
						/*var td1 = tr.children().eq(0);
						 $(td1).find('input[type="checkbox"]').attr("itemId",keyId);*/
						tr.children().eq(1).text(data.pi.elementId);
						tr.children().eq(2).text(data.pi.elementName);
						/*$(tr).find('td:nth-child(2)').text(data.pi.elementId);
						$(tr).find('td:nth-child(3)').text(data.pi.elementName);*/
					}
					initPITrClick();

					$('#piId').val(keyId);
					$('#pfPartitionInfoId').val($('#piId').val());
					$('#piTbody tr[class*="detailInfo"]').find('td').removeClass("selectTableTr");
					$('#piTbody tr[id="'+keyId+'"]').find('td').addClass("selectTableTr");
					initTableBackground("piInfoDiv");
				}
			}
		},
		error:function (data) {
			alert("保存失败");
		}
	});
}

function intiPIfForm(result){
	$('#piBtn').show();
	$('#pfPartitionInfoId').val(result.pfPartitionInfoId);
	$('#partitionId').val(result.partitionId);
	$('#elementId').val(result.elementId);
	$('#elementName').val(result.elementName);
}

function refreshPIForm() {
	$('#piInfoDiv tr[class*="detailInfo"]').find('td').removeClass("selectTableTr");
	$("#piForm input[type='hidden']").val("");
	$("#piForm input[type='text']").val("");
}

/**
 * 删除节点
 */
function deleteTablePI() {
	var selectItems = getSelectedItems($('#piInfoDiv'),'itemId');
	if(selectItems==null||selectItems.length==0){
		alert('请选择需要操作的记录！');
		return;
	}else {
		if (!confirm("是否确认删除操作？该操作不可恢复！")) {
			return;
		}
		var ids = "";
		for (var i = 0; i < selectItems.length; i++) {
			var id = selectItems[i].itemId;
			if(id){
				ids += id + ",";
			}
		}
		var url = cur_proj_url+'/config/parti/delPI?';
		$.ajax({
			url: url,
			type:'get',
			dataType:'json',
			data:{keyId:ids},
			success:function (data) {
				alert(data.msg);
				if(data.success){
					//刷新列表
					for (var i = 0; i < selectItems.length; i++) {
						var id = selectItems[i].itemId;
						if(id){
							$('#piTbody tr[id="'+id+'"]').remove();
						}
					}
				}
				initTableBackground("piInfoDiv");
				refreshPIForm();
			}
		})
	}
}

//初始化右边窗体控件，清空之后，显示对应的按钮，并初始化外键id
function initPIPanel(pId){
	clearPIPanel();
	$('#pId').val(pId);
	$('#piPanel input').show();
	$('#piPanel button').show();
	$('#piPanel a').show();
	$('#piPanel .configMainDiv').show();
	$('#piPanel .configBottomDiv').show();
	$('#pi_partitionId').val($('#pId').val());
	$('#pfPartitionInfoId').val($('#piId').val());
}
//清空右边窗体控件、和数据
function clearPIPanel(){
	refreshPIForm();
	$("#piTbody").html("");

	$('#piId').val("");

	$('#piPanel input').hide();
	$('#piPanel button').hide();
	$('#piPanel a').hide();
	$('#piPanel .configMainDiv').hide();
	$('#piPanel .configBottomDiv').hide();
	$('#pi_partitionId').val($('#pId').val());
	$('#pfPartitionInfoId').val($('#piId').val());
}

/**
 * 获取选择的记录
 */
function getSelectedItems(element,key){
	var result=[];
	var itemIds=[];
	$(element).find('table>tbody>tr>td>input[type=checkbox]:checked').each(function(){
		var itemId = $(this).attr(key);
		if($.inArray(itemId,itemIds)==-1){
			result.push({itemId:itemId});
			itemIds.push(itemId);
		}
	});
	return result;
}

function initType(type){
	for(var i=0;i<partitionTypeJson.length;i++){
		if(partitionTypeJson[i].value==type){
			return partitionTypeJson[i].name;
		}
	}
	return type;
}

function initTableBackground(id){
	$('#'+id+' tr:even').css('background-color','#eee');
	$('#'+id+' tr:odd').css('background-color','#ffffff');
}


function initNewPITr(obj){
	var newRow = '<tr class="detailInfo" id="'+obj.pfPartitionInfoId+'">';
	newRow += '<td><input class="checkbox" type="checkbox" itemId="'+obj.pfPartitionInfoId+'"><input type="hidden" value="'+obj.pfPartitionInfoId+'"/></td>';
	newRow += '<td>'+obj.elementId+'</td>';
	newRow += '<td>'+obj.elementName+'</td>';
	newRow += '</tr>';
	return newRow;
}