<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>附件定义</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeStuff="treeStuff";
        var zTreeStuff,rStuff;
        var _wfdId="${wfdId!}";
    </script>
    <script type="text/javascript" src="${base}/static/js/config/stuffTree.js"></script>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage">
        <div class="col-7 left">
            <div class="ztreeLeftDiv" style="min-height: 320px;min-width:450px;">
                <input type="hidden" id="wfdId" name="wfdId" value="${wfdId!}">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeStuff"><i class="icon-refresh"></i>附件定义管理</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreeStuff"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeStuff"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeStuff" class="ztree"></ul>
            </div>
        </div>
        <div class="col-5" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-height: 320px;min-width: 290px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-cog"></i>附件信息
                        <#--<div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveStuff"><i class="icon-save"></i>保存</a>
                        </div>-->
                    </div>
                </h4>
                <#include "stuff-info.ftl"/>
            </div>
        </div>
    </div>

    <div id="rStuff" class="rightMenu">
        <ul style="">
            <li id="m_addstuff"><i class="icon-plus"></i>添加材料</li>
            <li id="m_delstuff"><i class="icon-remove"></i>删除材料</li>
        </ul>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    //面板高度
    //$(".configPanel .configHeaderDiv").css({height:contentHeight-6+"px"});

    //树控件高度，要求树控件内可滚动条控制
    //var mainHeight = contentHeight - 80;
    //$(".configPanel .configHeaderDiv>.stuffInfoDiv").css({height:mainHeight+"px"});
    //$(".configPanel .configHeaderDiv>.ztree").css({height:mainHeight+"px"});
</script>
</body>