<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>行政区管理</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeDistrict = "treeDistrict";
        var zTree, rMenu;
    </script>

    <script type="text/javascript" src="${base}/static/js/config/district.js"></script>

    <style type="text/css">
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage">
        <div class="col-5 left">
            <div class="ztreeLeftDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeDistrict"><i class="icon-refresh"></i>行政区管理</a>
                    <#--<i class="icon-screenshot"></i> 菜单管理-->
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreeDistrict"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeDistrict"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeDistrict" class="ztree"></ul>
            </div>
        </div>

        <div class="col-7" id="districtInfoDiv" style="padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <i class="icon-info-sign"></i> 属性
                </h4>
                <div>
                    <form method="post" class="row cl form-horizontal" id="districtForm">
                        <input type="hidden" id="districtId" name="districtId">
                        <input type="hidden" id="districtParentId" name="districtParentId">
                        <input type="hidden" id="treeTId" name="treeTId">
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                行政区名称
                            </div>
                            <div class="col-9 form-input">
                                <input name="districtName" id="districtName" type="text" class="input-text size-M" >
                            </div>
                        </div>
                        <div class="row cl form-element">
                            <div class="col-3 form-label">
                                行政区编码
                            </div>
                            <div class="col-9 form-input">
                                <input name="districtCode" id="districtCode" type="text" class="input-text size-M" onblur="ztree_check_integer(this,'行政区编码')">
                            </div>
                        </div>

                        <div class="row cl pd-10" style="text-align:center;">
                            <button class="btn btn-secondary size-M" type="button" id="districtBtn">
                                <i class="icon-save icon-large"></i> 提交
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id="rMenu" class="rightMenu">
        <ul style="">
            <li id="m_add" onclick="addTreeNode();"><i class="icon-plus"></i> 增加</li>
            <li id="m_del" onclick="removeTreeNode();"><i class="icon-remove"></i> 删除</li>
        </ul>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
</body>