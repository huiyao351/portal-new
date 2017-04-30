<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>日历编辑</title>
    <#include "../../common/include.ftl"/>
    <link rel="stylesheet" href="${base}/static/css/config.css" type="text/css">
    <script type="text/javascript" src="${base}/static/js/config/stuff.js"></script>
    <script type="text/javascript" src="${base}/static/js/public-data.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            $('#calSaveBtn').click(function () {
                var url = cur_proj_url+'/config/cal/save?';
                $.ajax({
                    url:url,
                    type:'post',
                    dataType:'json',
                    data:$("#calForm").serialize(),
                    success:function (data) {
                        alert(data.msg);
                        if(data.success){
                        }
                    },
                    error:function (data) {
                        alert("保存失败");
                    }
                });
            });
        });
    </script>
    <#--<script type="text/javascript" src="${base}/static/js/config/busiGroup.js"></script>-->
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:880px;">
        <div class="col-7 left" style="min-width:500px;">
            <div class="configHeaderDiv" style="min-height: 520px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeDistrict"><i class="icon-refresh"></i>日历管理</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="expandTreeDistrict"><i class="icon-plus"></i>生成日历</a>
                            <a href="javascript:void(0);" id="collapseTreeDistrict"><i class="icon-minus"></i>上年度</a>
                            <a href="javascript:void(0);" id="collapseTreeDistrict"><i class="icon-minus"></i>下年度</a>
                            <a href="javascript:void(0);" id="collapseTreeDistrict"><i class="icon-minus"></i>上月份</a>
                            <a href="javascript:void(0);" id="collapseTreeDistrict"><i class="icon-minus"></i>下月份</a>
                        </div>
                    </div>
                </h4>
                <div id="stuffInfoDiv" class="stuffInfoDiv" style="overflow-y: auto;">
                    <!-- 表格部分 -->
                    <table class="table table-border table-striped table-hover" >
                        <thead>
                        <tr>
                            <th width="70px">日期</th>
                            <th width="70px">星期</th>
                            <th width="70px">类型</th>
                            <th width="90px">上午</th>
                            <th width="90px">下午</th>
                            <th>备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list calendarList as item>
                        <tr class="detailInfo" id="${item.calId!}">
                            <td>${item.calDate?string("yyyy-MM-dd")}</td>
                            <td>${item.calWeek!}</td>
                            <td>${item.calType!}</td>
                            <td>${item.amBegin?string("HH:mm")}--${item.amEnd?string("HH:mm")}</td>
                            <td>${item.pmBegin?string("HH:mm")}--${item.pmEnd?string("HH:mm")}</td>
                            <td>${item.remark!}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-5" style="min-width:200px;padding-left:10px;">
            <div class="configHeaderDiv" style="min-height: 500px;min-width: 290px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-info-sign"></i>日历编辑
                    </div>
                </h4>

                <form method="post" action="${base}/config/cal/save" class="row cl form-horizontal" id="calForm">
                    <input type="hidden" id="calId" name="calId" value="${calendar.calId!}">
                    <div class="row cl form-element">
                        <div class="col-3 form-label">
                            日期类型
                        </div>
                        <div class="col-9 form-input">
                            <input name="calType" id="calType" value="${calendar.calType!}" type="text" class="input-text size-M" >
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-3 form-label">
                            星期
                        </div>
                        <div class="col-9 form-input">
                            ${calendar.amEnd?string("HH:mm")}
                            <input name="calWeek" id="calWeek" value="${calendar.calWeek!}" type="text" class="input-text size-M">
                        </div>
                    </div>
                    <div class="row cl pd-10" style="text-align:center;">
                        <button class="btn btn-secondary size-M btn-primary" type="button" id="calSaveBtn">
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