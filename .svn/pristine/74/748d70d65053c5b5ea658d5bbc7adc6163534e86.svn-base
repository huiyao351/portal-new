<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>附件定义</title>
    <#include "../../common/include.ftl"/>

    <script type="text/javascript">
    </script>

    <link rel="stylesheet" href="${base}/static/css/config.css" type="text/css">
    <script type="text/javascript" src="${base}/static/js/config/stuff.js"></script>
    <script type="text/javascript" src="${base}/static/js/public-data.js"></script>
</head>
<body style="padding: 10px;">
<div class="Hui-wraper">
    <div class="row cl configPanel">
        <div class="col-7 left">
            <div class="configHeaderDiv" style="min-height: 520px;min-width:450px;">
                <input type="hidden" id="wfdId" name="wfdId" value="${wfdId!}">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-cog"></i>附件定义管理
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="addTableStuff"><i class="icon-plus-sign"></i>增加</a>
                            <#--<a href="javascript:void(0);" id="updateTableStuff"><i class="icon-edit"></i>编辑</a>-->
                            <a href="javascript:void(0);" id="deleteTableStuff"><i class="icon-minus-sign"></i>删除</a>
                        </div>
                    </div>
                </h4>
                <div id="stuffInfoDiv" class="stuffInfoDiv" style="overflow-y: auto;">
                    <!-- 表格部分 -->
                    <table class="table table-border table-striped table-hover" >
                        <thead>
                        <tr>
                            <th width="10px">
                                <input type="checkbox" class="ckbSelectAll" id="ckbSelectAllStuff">
                            </th>
                            <th width="25px">序号</th>
                            <th>附件名称</th>
                            <th width="60px">介质</th>
                            <th width="50px">总数</th>
                            <th width="50px">已收数</th>
                            <th width="50px">待补数</th>
                            <th width="00px">备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list stuffList as item>
                        <tr class="detailInfo" id="${item.stuffId!}">
                            <td>
                                <input class="checkbox" type="checkbox" itemId="${item.stuffId!}">
                                <input type="hidden" value="${item.stuffId!}"/>
                                <input type="hidden" value="${item.workflowDefinitionId!}"/>
                            </td>
                            <td>${item.stuffXh!}</td>
                            <td>${item.stuffName!}</td>
                            <td>${item.meterial!}</td>
                            <td>${item.stuffCount!}</td>
                            <td>${item.ysnum!}</td>
                            <td>${item.dbnum!}</td>
                            <td>${item.remark!}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-5" style="padding-left:10px;">
            <div class="configHeaderDiv" style="min-height: 500px;min-width: 290px;">
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
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    //面板高度
    $(".configPanel .configHeaderDiv").css({height:contentHeight-6+"px"});

    //树控件高度，要求树控件内可滚动条控制
    var mainHeight = contentHeight - 80;
    $(".configPanel .configHeaderDiv>.stuffInfoDiv").css({height:mainHeight+"px"});
    //$(".configPanel .configHeaderDiv>.ztree").css({height:mainHeight+"px"});
</script>
</body>