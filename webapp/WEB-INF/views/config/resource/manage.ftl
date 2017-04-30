<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>资源管理</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeResource = "treeResource";
        var zTreeResource,rResource;
    </script>

    <script type="text/javascript" src="${base}/static/js/config/resource.js"></script>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:1000px;">
        <div class="col-5 left">
            <div class="ztreeLeftDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeResource"><i class="icon-refresh"></i>资源管理</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreeResource"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeResource"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeResource" />
                <ul id="treeResource" class="ztree"></ul>
            </div>
        </div>
        <div class="col-7" style="padding-left: 10px">
            <div class="ztreeLeftDiv">
                <h4 class="titleLab">
                    <i class="icon-info-sign"></i> 属性
                </h4>
                <#include "resource-info.ftl"/>
            </div>
        </div>
    </div>
    <div id="rResource" class="rightMenu">
        <ul style="">
            <li id="m_add_group" onclick="addGroupNode();"><i class="icon-plus"></i> 增加资源组</li>
            <li id="m_add_resource" onclick="addResourceNode();"><i class="icon-plus"></i> 增加资源</li>
            <li id="m_del_group" onclick="removeGroupNode();"><i class="icon-remove"></i> 删除资源组</li>
            <li id="m_del_resource" onclick="removeResourceNode();"><i class="icon-remove"></i> 删除资源</li>
            <li id="m_resource_parti" onclick="resourcePartiNode();"><i class="icon-th"></i> 资源分区</li>
        </ul>
    </div>
    <div id="rpModal" class="modal hide fade rpConfigModal" style="" role="dialog" aria-labelledby="rpModalLabel" aria-hidden="true">
        <h4 class="modal-top-bar">
            <div>
                <i class="icon-th">资源分区</i>
                <div class="topBtnDiv">
                    <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" id="closeRpModal" style="" >
                        <i class="icon-remove icon-large"></i>
                    </a>
                </div>
            </div>
        </h4>
        <iframe id="mainFrame" src="" width=100% height=100% class="iframe1" style="border:none;" frameborder="0" scrolling="auto">
        </iframe>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
</body>