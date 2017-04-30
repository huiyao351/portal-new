<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>主题菜单权限管理(嵌入到iframe里面的主题权限管理配合主题菜单管理功能使用)</title>
<#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeRole = "treeRole";
        var treeSubRole = "treeSubRole";
        var zTreeRole,zTreeSubRole;
        var cur_sub_id = "${curSubId}";
    </script>

    <script type="text/javascript" src="${base}/static/js/config/subsystemAuth.js"></script>
</head>
<body style="min-width:590px;">
<div class="Hui-wraper" style="padding:0px;margin:0px;padding: 0 0px!important;">
    <div class="row cl treeManage" style="padding:0px;margin:0px;">
        <div class="col-6">
            <div class="ztreeRightDiv" style="min-width:286px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeRole"><i class="icon-refresh"></i>角色列表</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeRoleRel"><i class="icon-save"></i>保存</a>
                            <a href="javascript:void(0);" id="expandTreeRole"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeRole"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
            <@treeSearch zTreeId="treeRole" />
                <ul id="treeRole" class="ztree"></ul>
            </div>
        </div>
        <div class="col-6" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-width:286px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeSubRole"><i class="icon-refresh"></i>角色预览</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreeSubRole"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeSubRole"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeSubRole" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    $('.treeManage').height(winHeight);
    $(".treeManage .ztreeRightDiv").height(winHeight-2);
    $(".treeManage .ztreeRightDiv>.ztree").css({height:(mainHeight+20)+"px"});
    $("#treeRole").height(rightMainHeight+20);
</script>
</body>