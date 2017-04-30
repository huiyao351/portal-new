<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>资源分区管理</title>
    <#include "../../common/include.ftl"/>

    <script type="text/javascript">
        var partitionTypeJson = ${partitionTypeJson!};
    </script>

    <link rel="stylesheet" href="${base}/static/css/config.css" type="text/css">
    <script type="text/javascript" src="${base}/static/js/config/parti.js"></script>
    <script type="text/javascript" src="${base}/static/js/public-data.js"></script>
</head>
<body style="padding: 10px;">
<div class="Hui-wraper">
    <div class="row cl configPanel">
        <input type="hidden" id="rId" name="rId" value="${rId!}">
        <input type="hidden" id="pId" name="pId" value="${pId!}">
        <input type="hidden" id="piId" name="piId" value="${piId!}">
        <div class="col-6 left">
            <div class="configHeaderDiv" style="height: 520px;min-width:300px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-cog"></i>分区
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="addTableRP"><i class="icon-plus-sign"></i>增加</a>
                            <#--<a href="javascript:void(0);" id="updateTableStuff"><i class="icon-edit"></i>编辑</a>-->
                            <a href="javascript:void(0);" id="deleteTableRP"><i class="icon-minus-sign"></i>删除</a>
                        </div>
                    </div>
                </h4>
                <div class="configMainDiv" id="rpInfoDiv" style="height:356px;">
                    <!-- 表格部分 -->
                    <table class="table table-border table-striped table-hover" >
                        <thead>
                        <tr>
                            <th width="10px">
                                <input type="checkbox" class="ckbSelectAll" id="ckbSelectAllStuff">
                            </th>
                            <th>分区名称</th>
                            <th width="90px">分区类型</th>
                        </tr>
                        </thead>
                        <tbody id="rpTbody">
                        <#list rpList as item>
                        <tr class="detailInfo" id="${item.partitionId!}">
                            <td>
                                <input class="checkbox" type="checkbox" itemId="${item.partitionId!}">
                                <input type="hidden" value="${item.partitionId!}"/>
                            </td>
                            <td>${item.partitionName!}</td>
                            <td>${item.partitionType!}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <div class="configBottomDiv" style="left:2px;right:0px;">
                    <form method="post" class="row cl form-horizontal" id="rpForm">
                        <input type="hidden" id="partitionId" name="partitionId">
                        <input type="hidden" id="resourceId" name="resourceId">

                        <div class="row cl form-element">
                            <div class="col-2 form-label" style="min-width:35px;">
                                名称
                            </div>
                            <div class="col-5 form-input">
                                <input name="partitionName" id="partitionName" type="text" class="input-text size-M" >
                            </div>
                            <div class="col-2 form-label" style="min-width:35px;">
                                类型
                            </div>
                            <div class="col-3 form-input">
                                <select class="select" name="partitionType" id="partitionType">
                                <#list partitionTypeList as item>
                                    <option value="${item.value}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                        <div class="row cl pd-10" style="text-align:center;">
                            <button class="btn btn-secondary size-M" type="button" id="rpBtn">
                                <i class="icon-save icon-large"></i> 保存
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-6" style="padding-left:10px;">
            <div class="configHeaderDiv" style="height: 520px;min-width: 300px;" id="piPanel">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-cog"></i>元素
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="addTablePI"><i class="icon-plus-sign"></i>增加</a>
                        <#--<a href="javascript:void(0);" id="updateTableStuff"><i class="icon-edit"></i>编辑</a>-->
                            <a href="javascript:void(0);" id="deleteTablePI"><i class="icon-minus-sign"></i>删除</a>
                        </div>
                    </div>
                </h4>
                <div class="configMainDiv" id="piInfoDiv" style="height:356px;">
                    <!-- 表格部分 -->
                    <table class="table table-border table-striped table-hover" >
                        <thead>
                        <tr>
                            <th width="10px">
                                <input type="checkbox" class="ckbSelectAll" id="ckbSelectAllStuff">
                            </th>
                            <th>名称</th>
                            <th width="150px">标题</th>
                        </tr>
                        </thead>
                        <tbody id="piTbody">
                        <#list piList as item>
                        <tr class="detailInfo" id="${item.pfPartitionInfoId!}">
                            <td>
                                <input class="checkbox" type="checkbox" itemId="${item.pfPartitionInfoId!}">
                                <input type="hidden" value="${item.pfPartitionInfoId!}"/>
                            </td>
                            <td>${item.elementId!}</td>
                            <td>${item.elementName!}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <div class="configBottomDiv" style="left:12px;right:0px;">
                    <form method="post" class="row cl form-horizontal" id="piForm">
                        <input type="hidden" id="pfPartitionInfoId" name="pfPartitionInfoId">
                        <input type="hidden" id="pi_partitionId" name="partitionId">
                        <div class="row cl form-element">
                            <div class="col-2 form-label" style="min-width:35px;">
                                名称
                            </div>
                            <div class="col-4 form-input">
                                <input name="elementId" id="elementId" type="text" class="input-text size-M" style="min-width:50px;" >
                            </div>
                            <div class="col-2 form-label" style="min-width:35px;">
                                标题
                            </div>
                            <div class="col-4 form-input">
                                <input name="elementName" id="elementName" type="text" class="input-text size-M" style="min-width:50px;">
                            </div>
                        </div>
                        <div class="row cl pd-10" style="text-align:center;">
                            <button class="btn btn-secondary size-M" type="button" id="piBtn">
                                <i class="icon-save icon-large"></i> 保存
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    //面板高度
    $(".configPanel .configHeaderDiv").css({height:contentHeight-6+"px"});

    /*//树控件高度，要求树控件内可滚动条控制
    var mainHeight = contentHeight - 80;
    $(".configPanel .configHeaderDiv>.stuffInfoDiv").css({height:mainHeight+"px"});*/
    //$(".configPanel .configHeaderDiv>.ztree").css({height:mainHeight+"px"});
</script>
</body>