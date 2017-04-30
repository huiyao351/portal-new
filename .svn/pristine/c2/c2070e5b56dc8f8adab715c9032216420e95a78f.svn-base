<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>角色列表管理</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeRole = "treeRole";
        var treeOrgan = "treeOrgan";
        var treeRoleUser = "treeRoleUser";
        var zTreeRole,zTreeOrgan,zTreeRoleUser;
    </script>
    <script type="text/javascript" src="${base}/static/js/config/role.js"></script>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:930px;">
        <div class="col-4 left" style="min-width:220px;">
            <div class="ztreeLeftDiv" style="min-width:220px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" onclick="refreshTreeRole();" ><i class="icon-refresh"></i>角色列表</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreeRole"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeRole"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeRole" />
                <ul id="treeRole" class="ztree"></ul>
            </div>
        </div>
        <div class="col-4" style="min-width:310px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);"  onclick="refreshTreeOrgan();" id="refreshTreeOrgan"><i class="icon-refresh"></i>人员列表</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeUserRoleRel"><i class="icon-save"></i>保存</a>
                            <a href="javascript:void(0);" id="expandTreeOrgan"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeOrgan"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeOrgan" />
                <ul id="treeOrgan" class="ztree"></ul>
            </div>
        </div>
        <div class="col-4" style="min-width:290px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-info-sign"></i>属性
                    </div>
                </h4>

                <form method="post" class="row cl form-horizontal" id="roleForm">
                    <input type="hidden" id="roleId" name="roleId">
                    <input type="hidden" id="treeTId" name="treeTId">
                    <input type="hidden" id="userid" name="userid">
                    <input type="hidden" id="originaluserid" name="originaluserid">
                    <div class="row cl form-element">
                        <div class="col-3 form-label">
                            角色名称
                        </div>
                        <div class="col-9 form-input">
                            <input name="roleName" id="roleName" type="text" class="input-text size-M" >
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-3 form-label">
                            角色编码
                        </div>
                        <div class="col-9 form-input">
                            <input name="roleNo" id="roleNo" type="text" class="input-text size-M">
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-3 form-label">
                            行政区
                        </div>
                        <div class="col-9 form-input">
                            <select id="regionCode" name="regionCode"  class="select">
                            </select>
                        </div>
                    </div>
                    <div class="row cl pd-10" style="text-align:center;">
                        <button class="btn btn-secondary size-M btn-primary" type="button" id="roleBtn">
                            <i class="icon-save icon-large"></i> 提交
                        </button>
                    </div>
                </form>

                <div class="configPanelDiv" style="left:10px;right:0px;">
                    <p>用户列表</p>
                    <ul id="treeRoleUser" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>

    <div id="rRoleMenu" class="rightMenu">
        <ul style="">
            <li id="m_relMenuAuth" onclick="relMenuAuth();"><i class="icon-group"></i> 菜单权限</li>
            <li id="m_addTreeRole" onclick="addTreeRoleItem();"><i class="icon-plus-sign"></i> 添加角色</li>
            <li id="m_deleteTreeRole" onclick="deleteTreeRole();"><i class="icon-minus-sign"></i>删除角色</li>
        </ul>
    </div>

    <div id="infoModal" class="modal hide fade infoConfigModal" role="dialog" aria-labelledby="infoConfigModal" aria-hidden="true">
        <h4 class="modal-top-bar">
            <div>
                <i class="icon-exchange">信息展示</i>
                <div class="topBtnDiv">
                    <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" style="" >
                        <i class="icon-remove icon-large"></i>
                    </a>
                </div>
            </div>
        </h4>
        <iframe id="infoFrame" src="" width=100% height=100% class="iframe1" style="border:none;" frameborder="0" scrolling="auto">
        </iframe>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    $(".treeManage .ztreeRightDiv>.configPanelDiv").css({height:(contentHeight-274)+"px"});
    $(".treeManage .ztreeRightDiv>.configPanelDiv>.ztree").css({height:(contentHeight-312)+"px"});
</script>
</body>