<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>菜单管理</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeMenu = "treeMenu";
        var treeResource = "treeResource";
        var zTree, rMenu,zTreeResource,rResource;
    </script>
    <script type="text/javascript" src="${base}/static/js/config/menu.js"></script>

    <style type="text/css">
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:760px;">
        <div class="col-5 left">
            <div class="ztreeLeftDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeMenu"><i class="icon-refresh"></i>菜单管理</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="showTreeResource" right-panel="info"><i class="icon-credit-card"></i>属性</a>
                            <a href="javascript:void(0);" id="expandTreeMenu"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeMenu"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeMenu" />
                <ul id="treeMenu" class="ztree"></ul>
            </div>
        </div>
        <div class="col-7 ztreeRightHiddenDiv" id="resourceTreeDiv" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-width:300px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeResource"><i class="icon-refresh"></i>资源列表</a>
                        <#--<i class="icon-screenshot"></i> 资源列表-->
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeResource"><i class="icon-save"></i>保存</a>
                            <a href="javascript:void(0);" id="expandTreeResource"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeResource"><i class="icon-minus"></i>收缩</a>
                            <#--<a href="javascript:void(0);" id="refreshTreeResource"><i class="icon-refresh"></i>刷新</a>-->
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeResource" />
                <ul id="treeResource" class="ztree"></ul>
            </div>
        </div>
        <div class="col-7" id="menuInfoDiv" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-width:430px;">
                <h4 class="titleLab">
                    <i class="icon-info-sign"></i> 属性
                </h4>
            <#include "menu-info.ftl"/>
            </div>
        </div>
    </div>
    <div id="rMenu" class="rightMenu">
        <ul style="">
            <li id="m_add" onclick="addTreeNode();"><i class="icon-plus"></i> 增加菜单</li>
            <li id="m_del" onclick="removeTreeNode();"><i class="icon-remove"></i> 删除菜单</li>
            <li id="m_resource_rel" onclick="openResourceTree();"><i class="icon-exchange"></i> 资源挂接</li>
        </ul>
    </div>
    <div id="rResource" class="rightMenu">
        <ul style="padding: 0px 4px">
            <li style="padding: 0px" id="m_save_rel" onclick="saveCurResourceRel();"><i class="icon-save"></i>保存</li>
        </ul>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
</body>