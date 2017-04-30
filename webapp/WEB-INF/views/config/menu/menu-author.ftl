<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>主菜单授权</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeMenu = "treeMenu";
        var treeRole = "treeRole";
        var zTreeMenu,zTreeRole;
        var partitionTypeJson = ${partitionTypeJson!};
        var partitionOperatTypeJson = ${partitionOperatTypeJson!};
    </script>

    <link rel="stylesheet" href="${base}/static/css/treeconfig.css" type="text/css">
    <link rel="stylesheet" href="${base}/static/css/config.css" type="text/css">
    <script type="text/javascript" src="${base}/static/js/config/menuAuth.js"></script>

    <style type="text/css">
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:900px;">
        <div class="col-3 left">
            <div class="ztreeLeftDiv" style="min-width:220px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeMenu"><i class="icon-refresh"></i>菜单管理</a>
                        <div class="topBtnDiv">
                            <#--<a href="javascript:void(0);" id="showTreeResource" right-panel="info"><i class="icon-credit-card"></i>属性</a>-->
                            <a href="javascript:void(0);" id="expandTreeMenu"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeMenu"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeMenu" />
                <ul id="treeMenu" class="ztree"></ul>
            </div>
        </div>
        <div class="col-4" id="resourceTreeDiv" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-width:300px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-group"></i>角色
                        <#--<i class="icon-screenshot"></i> 资源列表-->
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeRole"><i class="icon-save"></i>保存</a>
                            <a href="javascript:void(0);" id="expandTreeRole"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeRole"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeRole" />
                <ul id="treeRole" class="ztree"></ul>
            </div>
        </div>
        <div class="col-5" id="partiDiv" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-width:300px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-info-sign"></i> 分区
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="savePartiListData"><i class="icon-save"></i>保存</a>
                        </div>
                    </div>
                </h4>
                <div class="configMainDiv" id="rpInfoDiv" style="overflow-y: auto;overflow-x:hidden;">
                    <!-- 表格部分 -->
                    <table class="table table-border table-striped table-hover" >
                        <thead>
                        <tr>
                            <th>分区名称</th>
                            <th width="60px">分区类型</th>
                            <th width="90px">操作</th>
                        </tr>
                        </thead>
                        <tbody id="rpTbody">
                        <#--<tr class="detailInfo" id="tr_${item.partitionId!}">
                            <td>${item.partitionName!}</td>
                            <td>${item.partitionType!}</td>
                            <td>
                            <select id="4C77602678BA44E38A07318E7BBE004A" class="select" name="operTypeSel">
                            <option value=""></option>
                            <option value="0" selected="selected">完全控制</option>
                            <option value="1">只读</option><option value="2">不可见</option>
                            </select>
                            </td>
                        </tr>-->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    $(".treeManage .ztreeRightDiv>.configMainDiv").css({height:(mainHeight+10)+"px"});
</script>
</body>