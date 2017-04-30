<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>工作流实例权限</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeRole = "treeRole";
        var treeResource = "treeResource";
        var zTreeRole, zTreeResource;
        var cur_wdid = "${wdid!}";
        var partitionTypeJson = ${partitionTypeJson!};
        var partitionOperatTypeJson = ${partitionOperatTypeJson!};
    </script>

    <script type="text/javascript" src="${base}/static/js/config/instAuth.js"></script>

    <style type="text/css">
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:760px;">
        <div class="col-3 left">
            <div class="ztreeLeftDiv" style="min-width:200px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-group"></i>角色
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="addTreeRole"><i class="icon-exchange"></i>选择</a>
                            <#--<a href="javascript:void(0);" id="deleteTreeRole"><i class="icon-minus-sign"></i>删除</a>-->
                        </div>
                    </div>
                </h4>
                <ul id="treeRole" class="ztree"></ul>
            </div>
        </div>
        <div class="col-4" id="resourceTreeDiv" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-width:260px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-list"></i>资源
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeResource"><i class="icon-save"></i>保存</a>
                            <a href="javascript:void(0);" id="addTreeResource"><i class="icon-exchange"></i>选择</a>
                            <a href="javascript:void(0);" id="deleteTreeResource"><i class="icon-minus-sign"></i>删除</a>
                        </div>
                    </div>
                </h4>

                <ul id="treeResource" class="ztree"></ul>
            </div>
        </div>
        <div class="col-5" id="partiDiv" style="padding-left:10px;">
            <div class="ztreeRightDiv" style="min-width:290px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-info-sign"></i> 分区
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="savePartiListData"><i class="icon-save"></i>保存</a>
                        </div>
                    </div>
                </h4>
                <div class="configMainDiv" id="rpInfoDiv">
                    <!-- 表格部分 -->
                    <table class="table table-border table-striped table-hover" >
                        <thead>
                        <tr>
                            <th>分区名称</th>
                            <th width="70px">分区类型</th>
                            <th width="90px">操作</th>
                        </tr>
                        </thead>
                        <tbody id="rpTbody">
                        <#--<tr class="detailInfo" id="tr_${item.partitionId!}">
                            <td>${item.partitionName!}</td>
                            <td>${item.partitionType!}</td>
                            <td>
                            <select id="4C77602678BA44E38A07318E7BBE004A" class="select" name="operTypeSel">
                            <option value=""></option>
                            <option value="0" selected="selected">完全控制</option>
                            <option value="1">只读</option><option value="2">不可见</option>
                            </select>
                            </td>
                        </tr>-->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
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
        <iframe id="mainFrame" src="" width=100% height=100% class="iframe1" style="border:none;" frameborder="0" scrolling="auto">
        </iframe>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    //树控件高度，要求树控件内可滚动条控制
    var configMainDivHeight = contentHeight - 60;
    $(".treeManage .ztreeRightDiv>.configMainDiv").css({height:configMainDivHeight+"px"});
</script>
</body>