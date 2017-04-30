<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>部门菜单管理</title>
<#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeOrgan="treeOrgan";
        var treeOrganUser="treeOrganUser";
        var zTreeOrgan,rOrgan,zTreeOrganUser;
        WdatePicker({
            eCont : 'obtainDate',
            onpicked : function(dp) {
                alert(dp.cal.getDateStr())
            }
        });
    </script>
    <script type="text/javascript" src="${base}/static/js/config/organ.js"></script>
    <style type="text/css">
        /* 置灰样式*/
        .btn-readonly {
            color: #fff;
            background-color: #798388;
            border-color: #6E787D;
        }
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:960px;">
        <div class="col-4 left" style="min-width:220px;">
            <div class="ztreeLeftDiv" style="min-width:220px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);"  onclick="refreshTreeOrgan()" ><i class="icon-refresh"></i>部门列表</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreOrgan"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeOrgan"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeOrgan" />
                <ul id="treeOrgan" class="ztree"></ul>
            </div>
        </div>
        <div class="col-5" style="min-width:360px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab" style="padding-bottom: 0px;">
                    <div>
                        <i class="icon-info-sign"></i>属性
                    </div>
                </h4>
            <#-- 部门属性-->
            <#include "organ-info.ftl"/>
            </div>
        </div>
        <div class="col-3" style="min-width:180px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-reorder"></i>人员列表
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="openTreeUserSelect"><i class="icon-exchange"></i>人员</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeOrganUser" class="ztree"></ul>
            </div>
        </div>
    </div>
    <div id="rOrganMenu" class="rightMenu">
        <ul style="">
            <li id="m_add" onclick="addTreeNode();"><i class="icon-plus"></i> 添加部门</li>
            <#--<li id="m_adduser" onclick="addUserNode();"><i class="icon-plus"></i> 添加用户</li>-->
            <li id="m_del" onclick="removeTreeNode();"><i class="icon-remove"></i>删除部门</li>
        </ul>
    </div>

    <#--删除选择弹出框-->
    <#include "organ-delete-dialog.ftl"/>

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
        <iframe id="selectFrame" src="" width=100% height=100% class="iframe1" style="border:none;" frameborder="0" scrolling="no">
        </iframe>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
</body>