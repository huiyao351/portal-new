<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>工作流管理</title>
<#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeWfd = "treeWfd";
        var zTreeWfd,rMenuWorkflow;
    </script>

    <script type="text/javascript" src="${base}/static/js/config/workflow.js"></script>

    <style type="text/css">
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-height: 400px;min-width:1000px;">
        <div class="col-5 left">
            <div class="ztreeLeftDiv" style="min-height: 400px;min-width:420px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeWfd"><i class="icon-refresh"></i>工作流管理</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="wfdInstAuthTree"><i class="icon-picture"></i>实例权限</a>
                            <#--<a href="javascript:void(0);" id="wfdImageTree"><i class="icon-picture"></i>流程图</a>-->
                            <a href="javascript:void(0);" id="wfdStuffTree"><i class="icon-list"></i>附件管理</a>
                            <a href="javascript:void(0);" id="expandTreeWfd"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeWfd"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeWfd" />
                <ul id="treeWfd" class="ztree"></ul>
            </div>
        </div>
        <div class="col-7" style="padding-left:10px;">
            <div id="wfdInfoDiv" class="ztreeRightDiv" style="min-height: 435px;min-width:500px;">
            <#include "wfd-edit-info.ftl"/>
            </div>
            <div id="bsInfoDiv" class="ztreeRightDiv" style="display: none;min-height: 435px;min-width:500px;">
            <#include "bs-edit-info.ftl"/>
            </div>
        </div>
    </div>

    <div id="rMenuWorkflow" class="rightMenu">
        <ul style="">
            <li id="m_wfd_inst_auth"><i class="icon-user"></i>实例权限</li>
            <li id="m_wfd_stuff_tree"><i class="icon-list"></i>附件管理</li>
            <li id="m_wfd_image_tree"><i class="icon-picture"></i>流程图</li>
        </ul>
    </div>

    <div id="stuffModal" class="modal hide fade stuffConfigModal" role="dialog" aria-labelledby="stuffModalLabel" aria-hidden="true">
        <h4 class="modal-top-bar">
            <div>
                <i class="icon-sitemap">工作流配置</i>
                <div class="topBtnDiv">
                    <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" id="closeStuffModal" style="" >
                        <i class="icon-remove icon-large"></i>
                    </a>
                </div>
            </div>
        </h4>
        <iframe id="mainFrame" src="" width=100% height=100% class="iframe1" style="border:none;" frameborder="0" scrolling="no">
        </iframe>
    </div>

    <div id="imageModal" class="modal hide fade imageConfigModal" role="dialog" aria-labelledby="iamgeModalLabel" aria-hidden="true">
        <h4 class="modal-top-bar">
            <div>
                <i class="icon-picture">流程图</i>
                <div class="topBtnDiv">
                    <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" id="closeImageModal" style="" >
                        <i class="icon-remove icon-large"></i>
                    </a>
                </div>
            </div>
        </h4>
        <div style="overflow: auto;height:560px;width:100%;">
            <img id="wfdImage" src="" alt="" style=""/>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
</body>