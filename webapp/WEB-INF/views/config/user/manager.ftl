<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户管理</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeUser="treeUser";
        var treeUserOrgan="treeUserOrgan";
        var treeUserRole="treeUserRole";
        var zTreeUser,rUser,zTreeUserOrgan,zTreeUserRole;
    </script>
    <script type="text/javascript" src="${base}/static/js/config/user.js"></script>
    <style type="text/css">
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:960px;">
        <div class="col-3 left" style="min-width:220px;">
            <div class="ztreeLeftDiv" style="min-width:220px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);"  onclick="refreshTreeUser()" ><i class="icon-refresh"></i>用户列表</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreOrgan"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeOrgan"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeUser" />
                <ul id="treeUser" class="ztree"></ul>
            </div>
        </div>

        <div class="col-6" style="min-width:460px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeSubMenu"><i class="icon-info-sign"></i>属性</a>
                    </div>
                </h4>
            <#--用户信息-->
            <#include "../user/user-info.ftl"/>
            </div>
        </div>
        <div class="col-3" style="min-width:240px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-reorder"></i>组织机构
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="openTreeOrganSelect"><i class="icon-exchange"></i>部门</a>
                            <a href="javascript:void(0);" id="openTreeRoleSelect"><i class="icon-exchange"></i>角色</a>
                        </div>
                    </div>
                </h4>
                <div class="configPanelDiv configPanelTopDiv" style="left:10px;right:0px;">
                    <p>所属部门</p>
                    <ul id="treeUserOrgan" class="ztree"></ul>
                </div>
                <div class="configPanelDiv configPanelBottomDiv" style="left:10px;right:0px;">
                    <p>所属角色</p>
                    <ul id="treeUserRole" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
    <div id="rUser" class="rightMenu">
        <ul style="">
            <li id="m_adduser" onclick="addUserNode();"><i class="icon-plus"></i> 添加用户</li>
            <li id="m_deluser" onclick="removeUserNode();"><i class="icon-remove"></i>删除用户</li>
        </ul>
    </div>

    <#--删除选择弹出框-->
    <#include "../organ/organ-delete-dialog.ftl"/>

    <div id="selectModal" class="modal hide fade selectConfigModal" role="dialog" aria-labelledby="stuffSelectModal" aria-hidden="true">
        <h4 class="modal-top-bar">
            <div>
                <i class="icon-exchange">数据选择</i>
                <div class="topBtnDiv">
                    <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" id="closeStuffModal" style="" >
                        <i class="icon-remove icon-large"></i>
                    </a>
                </div>
            </div>
        </h4>
        <iframe id="selectFrame" src="" width=100% height=100% class="iframe1" style="border:none;" frameborder="0" scrolling="auto">
        </iframe>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    $(".treeManage .ztreeRightDiv>.configPanelTopDiv").css({height:((mainHeight)*30/100)+"px"});
    $(".treeManage .ztreeRightDiv>.configPanelBottomDiv").css({height:(mainHeight-((mainHeight)*30/100)-2)+"px"});

    $(".treeManage .ztreeRightDiv>.configPanelTopDiv>.ztree").css({height:($(".treeManage .ztreeRightDiv>.configPanelTopDiv").height()-40)+"px"});
    $(".treeManage .ztreeRightDiv>.configPanelBottomDiv>.ztree").css({height:($(".treeManage .ztreeRightDiv>.configPanelBottomDiv").height()-40)+"px"});
</script>
</body>