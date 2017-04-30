<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>业务组管理</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeBusiGroup = "treeBusiGroup";
        var treeBusi = "treeBusi";
//        var zTreeRole,zTreeOrgan,zTreeRoleUser;
        var zTreeBusiGroup,zTreeBusi;
    </script>
    <script type="text/javascript" src="${base}/static/js/config/busiGroup.js"></script>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:880px;">
        <div class="col-3 left" style="min-width:220px;">
            <div class="ztreeLeftDiv" style="min-width:220px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-reorder"></i>业务分组
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="addTreeBusiGroup"   data-toggle="modal" data-target="#roleInfoDiv" ><i class="icon-plus-sign"></i>增加</a>
                            <a href="javascript:void(0);" id="deleteTreeBusiGroup"><i class="icon-minus-sign"></i>删除</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeBusiGroup" class="ztree"></ul>
            </div>
        </div>
        <div class="col-4" style="min-width:200px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-refresh"></i>业务流程列表
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeBusiGroupRel"><i class="icon-save"></i>保存</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeBusi" class="ztree"></ul>
            </div>
        </div>
        <div class="col-5" style="min-width:200px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-info-sign"></i>属性
                    </div>
                </h4>

                <form method="post" class="row cl form-horizontal" id="busiGroupForm">
                    <input type="hidden" id="businessGroupId" name="businessGroupId">
                    <input type="hidden" id="treeTId" name="treeTId">
                    <input type="hidden" id="businessIds" name="businessIds">
                    <div class="row cl form-element">
                        <div class="col-3 form-label">
                            分组名称
                        </div>
                        <div class="col-9 form-input">
                            <input name="businessGroupName" id="businessGroupName" type="text" class="input-text size-M" >
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-3 form-label">
                            分组编码
                        </div>
                        <div class="col-9 form-input">
                            <input name="businessGroupNo" id="businessGroupNo" type="text" class="input-text size-M" onfocus="ztree_check_integer(this,'编号')">
                        </div>
                    </div>
                    <div class="row cl pd-10" style="text-align:center;">
                        <button class="btn btn-secondary size-M btn-primary" type="button" id="busiGroupBtn">
                            <i class="icon-save icon-large"></i> 提交
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
</body>