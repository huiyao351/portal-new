<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>主题菜单管理</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeSub = "treeSub";
        var treeMenu = "treeMenu";
        var treeSubMenu = "treeSubMenu";
        var zTreeSub,zTreeMenu,zTreeSubMenu;
        var cur_sub_id = "${curSubId}";
    </script>

    <script type="text/javascript" src="${base}/static/js/config/subsystem.js"></script>

    <style type="text/css">
        .form-element{
            padding:4px 10px 4px 0px ;
        }
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:780px;">
        <div class="col-4 left" style="min-width:300px;">
            <div class="ztreeLeftDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-columns"></i>菜单主题
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="showSubMenu" onclick="closeSubAuth()" style="display:none;"><i class="icon-cogs"></i>菜单</a>
                            <a href="javascript:void(0);" id="showSubAuth" onclick="showSubAuth()"><i class="icon-cogs"></i>权限</a>
                            <a href="javascript:void(0);" id="addTreeSub"><i class="icon-plus-sign"></i>增加</a>
                            <a href="javascript:void(0);" id="deleteTreeSub"><i class="icon-minus-sign"></i>删除</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeSub" class="ztree"></ul>
                <div style="display: none;margin-top:0px;" id="subInfoDiv">
                    <form method="post" class="row cl form-horizontal" id="subForm">
                        <input type="hidden" id="subsystemId" name="subsystemId">
                        <input type="hidden" id="treeTId" name="treeTId">

                        <div class="row cl form-element">
                            <div class="col-12 form-input" style="color:#0000FF;padding-top:3px;text-align:right;">
                                *提醒：首次部署系统请完善主题菜单配置项。
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                名称
                            </div>
                            <div class="col-9 form-input">
                                <input name="subsystemTitle" id="subsystemTitle" type="text" class="input-text size-M" >
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                代码
                            </div>
                            <div class="col-3 form-input">
                                <input name="subsystemName" id="subsystemName" type="text" class="input-text size-M">
                            </div>
                            <div class="col-3 form-label">
                                排序码
                            </div>
                            <div class="col-3 form-input">
                                <input name="subNo" id="subNo" type="text" class="input-text size-M">
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                是否可用
                            </div>
                            <div class="col-3 form-input">
                                <select class="select" name="enabled" id="enabled">
                                <#list boolListNumber as item>
                                    <option value="${item.value}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                            <div class="col-3 form-label">
                                主题类型
                            </div>
                            <div class="col-3 form-input">
                                <select class="select" name="subType" id="subType">
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
                                <input name="subUrl" id="subUrl" type="text" class="input-text size-M">
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                菜单样式
                            </div>
                            <div class="col-9 form-input">
                                <select class="select" name="subMenuType" id="subMenuType">
                                    <option value=""></option>
                                <#list subMenuTypeList as item>
                                    <option value="${item.value}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>

                        <div class="row cl" style="text-align:center;">
                            <button class="btn btn-secondary size-M" type="button" id="subBtn">
                                <i class="icon-save icon-large"></i> 保存
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div id="menuTreeDiv" class="col-5" style="min-width:330px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeMenu"><i class="icon-refresh"></i>菜单选择列表</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeMenuRel"><i class="icon-save"></i>保存</a>
                            <a href="javascript:void(0);" id="expandTreeMenu"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeMenu"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
            <@treeSearch zTreeId="treeMenu" />
                <ul id="treeMenu" class="ztree"></ul>
                <div style="margin-top:20px;display: none;" id="menuInfoDiv">
                    <form method="post" class="row cl form-horizontal" id="menuForm">
                        <input type="hidden" id="menuId">
                        <input type="hidden" id="treeTId">

                        <div class="row cl form-element">
                            <div class="col-2 form-label">
                                资源名称
                            </div>
                            <div class="col-9 form-input">
                                <input id="resourceName" type="text" class="input-text size-M" disabled="true">
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-2 form-label">
                                资源地址
                            </div>
                            <div class="col-9 form-input">
                                <input id="resourceUrl" type="text" class="input-text size-M" disabled="true">
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-2 form-label">
                                所属业务
                            </div>
                            <div class="col-9 form-input">
                                <input id="businessName" type="text" class="input-text size-M" disabled="true">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div id="menuRelTreeDiv" class="col-3" style="min-width:230px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeSubMenu"><i class="icon-refresh"></i>主题预览</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreeSubMenu"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeSubMenu"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeSubMenu" class="ztree"></ul>
            </div>
        </div>
        <div id="subAuthDiv" class="col-8" style="min-width:600px;padding-left:10px;display:none;">
            <iframe id="mainFrame" src="" width=100% height=100% class="iframe1" style="border:none;" frameborder="0" scrolling="auto">
            </iframe>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    $(".treeManage .ztreeLeftDiv>.ztree").css({height:mainHeight-270+"px"});
    $("#treeMenu").css({height:(contentHeight-204-$(".treeManage .ztreeRightDiv .searchDiv").height())+"px"});
    $("#subAuthDiv").css({height:(contentHeight-4)+"px"});
    $("#treeSubMenu").css({height:(mainHeight)+"px"});
</script>
</body>