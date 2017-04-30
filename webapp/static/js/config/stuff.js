$(document).ready(function(){
	/**
	 * 全选按钮事件
	 */
	$('.ckbSelectAll').change(function(){
		$(this).parents(".table").children('tbody').find('input[type=checkbox]').prop("checked",$(this).is(':checked'));
	});

	//下面是主题操作事件
	$('#addTableStuff').click(function () {
		addTableStuff();
	});
	$('#updateTableStuff').click(function () {
		var selectItems = getSelectedItems($('#stuffInfoDiv'),'itemId');
		if(selectItems==null||selectItems.length==0 || selectItems.length > 1){
			alert('请选择一条数据进行编辑！');
			return;
		}else {
			var id = selectItems[0].itemId;
			updateTableStuff(id);
		}
	});

	$('#deleteTableStuff').click(function () {
		deleteTableStuff();
	});

	$('#stuffInfoDiv tr[class*="detailInfo"]').click(function () {
		$('#stuffInfoDiv tr[class*="detailInfo"]').find('td').removeClass("selectTableTr");
		$(this).find('td').addClass("selectTableTr");
		var id = $(this).children('td').eq(0).find('input[class*="checkbox"]').attr("itemId");
		updateTableStuff(id);
	});

	$('#stuffBtn').click(function () {
		saveStuffdata();
	});

	$('#workflowDefinitionId').val($('#wfdId').val());
	//$('#stuffBtn').hide();

	//针对css不兼容情况下，采用js实现奇偶行颜色不同 tr[class*="detailInfo"]:even
	$('#stuffInfoDiv tr:even').css('background-color','#eee');
	$('#stuffInfoDiv tr:odd').css('background-color','#ffffff');
});

var addCountStuff = 1;
/**
 * 增加节点
 */
function addTableStuff() {
	refreshStuffForm();
	$('#workflowDefinitionId').val($('#wfdId').val());
	$('#stuffName').val("新增" + (addCountStuff++));
	$('#stuffCount').val(1);
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
				window.location.reload();
				/*intiStuffForm(data.stuffConfig);
				 //刷新列表
				 var curTr = $('#'+data.stuffConfig.stuffId);
				 if(curTr){
				 //如果存在，则更新该行数据
				 }else {
				 //如果不存在，则增加行
				 }*/
			}
		},
		error:function (data) {
			alert("保存失败");
		}
	});
}

function intiStuffForm(result){
	$('#stuffId').val(result.stuffId);
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
}

/**
 * 删除节点
 */
function deleteTableStuff() {
	var selectItems = getSelectedItems($('#stuffInfoDiv'),'itemId');
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
		var url = cur_proj_url+'/config/stuff/del?';
		$.ajax({
			url: url,
			type:'get',
			dataType:'json',
			data:{keyId:ids},
			success:function (data) {
				alert(data.msg);
				if(data.success){
					//刷新列表
					/*for (var i = 0; i < selectItems.length; i++) {
					 var id = selectItems[i].itemId;
					 $('#'+id).remove();
					 }*/
					window.location.reload();
				}
			}
		})
	}
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
