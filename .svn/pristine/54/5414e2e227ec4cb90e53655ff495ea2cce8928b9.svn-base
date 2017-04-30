<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>主题菜单权限管理（可独立运行）</title>
<#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeSub = "treeSub";
        var treeRole = "treeRole";
        var treeSubRole = "treeSubRole";
        var zTreeSub,zTreeRole,zTreeSubRole;
        var cur_sub_id = "${curSubId}";
    </script>

    <script type="text/javascript" src="${base}/static/js/config/subsystemAuthManage.js"></script>

    <style type="text/css">
        .form-element{
            padding:4px 10px 4px 0px ;
        }
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:900px;">
        <div class="col-4 left" style="min-width:280px;">
            <div class="ztreeLeftDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-columns"></i>菜单主题
                    </div>
                </h4>
                <ul id="treeSub" class="ztree"></ul>
                <div style="display: none;margin-top:0px;" id="subInfoDiv">
                    <form method="post" class="row cl form-horizontal" id="subForm">
                        <input type="hidden" id="subsystemId" name="subsystemId">
                        <input type="hidden" id="treeTId" name="treeTId">

                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                名称
                            </div>
                            <div class="col-9 form-input">
                                <input name="subsystemTitle" id="subsystemTitle" type="text" class="input-text size-M" disabled="disabled" >
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                代码
                            </div>
                            <div class="col-3 form-input">
                                <input name="subsystemName" id="subsystemName" type="text" class="input-text size-M" disabled="disabled">
                            </div>
                            <div class="col-3 form-label">
                                排序码
                            </div>
                            <div class="col-3 form-input">
                                <input name="subNo" id="subNo" type="text" class="input-text size-M" disabled="disabled">
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                是否可用
                            </div>
                            <div class="col-3 form-input">
                                <select class="select" name="enabled" id="enabled" disabled="disabled">
                                <#list boolListNumber as item>
                                    <option value="${item.value}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                            <div class="col-3 form-label">
                                主题类型
                            </div>
                            <div class="col-3 form-input">
                                <select class="select" name="subType" id="subType" disabled="disabled">
                                <#list subTypeList as item>
                                    <option value="${item.value}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                超链接
                            </div>
                            <div class="col-9 form-input">
                                <input name="subUrl" id="subUrl" type="text" class="input-text size-M" disabled="disabled">
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                菜单样式
                            </div>
                            <div class="col-9 form-input">
                                <select class="select" name="subMenuType" id="subMenuType" disabled="disabled">
                                    <option value=""></option>
                                <#list subMenuTypeList as item>
                                    <option value="${item.value}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-4" style="min-width:280px;padding-left:10px;">
            <div class="ztreeRightDiv">
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
                <ul id="treeRole" class="ztree"></ul>
            </div>
        </div>
        <div class="col-4" style="min-width:280px;padding-left:10px;">
            <div class="ztreeRightDiv">
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
    $(".treeManage .ztreeLeftDiv>.ztree").css({height:mainHeight-200+"px"});
</script>
</body>