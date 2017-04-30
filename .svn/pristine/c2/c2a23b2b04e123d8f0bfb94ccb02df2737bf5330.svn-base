<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>菜单管理</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeMenu = "treeMenu";
        var zTreeMenu;
        var partitionTypeJson = ${partitionTypeJson!};
        var partitionOperatTypeJson = ${partitionOperatTypeJson!};

        var _roleId = "${roleId!}";
    </script>

    <script type="text/javascript" src="${base}/static/js/config/roleMenuAuth.js"></script>
</head>
<body style="min-width:460px;">
<div class="Hui-wraper">
    <div class="row cl treeManage">
        <div class="col-5 left">
            <div class="ztreeLeftDiv" style="min-width:220px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeMenu"><i class="icon-refresh"></i>菜单管理</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreeMenu"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeMenu"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeMenu" class="ztree"></ul>
            </div>
        </div>
        <div class="col-7" id="partiDiv" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-width:220px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-info-sign"></i> 分区
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