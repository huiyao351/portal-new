<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>日历编辑</title>
<#include "../include-tree.ftl"/>
    <#--<script type="text/javascript" src="${base}/static/js/config/cal.js"></script>-->

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
            $('tr[class*="detailInfo"]').click(function () {
                $('#createCalDiv').hide();
                $('#calSaveBtn').show();
                var url = cur_proj_url+'/config/cal/open?calId='+$(this).attr("id");
                $.ajax({
                    url:url,
                    type:'post',
                    success:function (data) {
                        if(data && data.calId){
                            var doDate = "calDate;amBegin;amEnd;pmBegin;pmEnd";
                            jQuery.each(data, function(key, item) {
                                if(doDate.indexOf(key)>-1){
                                    var date = new Date(item);
                                    if(date && date.getDate()){
                                        if(key == "calDate"){
                                            $('#'+key).val(date.Format("yyyy-MM-dd"));
                                        }else{
                                            $('#'+key).val(date.getHours()+":"+date.getMinutes());
                                        }
                                    }
                                }else {
                                    $('#'+key).val(item);
                                }
                                //alert(date.getHours()+"---"+date.getMinutes());
                            });
                        }
                    },
                    error:function (data) {
                        alert("操作失败");
                    }
                });
            });
            $('#createCal').click(function () {
                $('#createCalDiv').show();
            });
            $('#calCreateBtn').click(function () {
                var calYear = $('#calYear').val();
                if(confirm("确定要创建"+calYear+"年度日历？")){
                    var url = cur_proj_url+'/config/cal/create';
                    $.ajax({
                        url:url,
                        type:'post',
                        dataType:'json',
                        data:{year:calYear},
                        success:function (data) {
                            alert(data.msg);
                            if(data.success){
                                window.location.reload();
                            }
                        },
                        error:function (data) {
                            alert("操作失败");
                        }
                    });
                }
            });
            $('#calDelBtn').click(function () {
                var calYear = $('#calYear').val();
                if(confirm("确定要删除"+calYear+"年度日历？")){
                    var url = cur_proj_url+'/config/cal/del';
                    $.ajax({
                        url:url,
                        type:'post',
                        dataType:'json',
                        data:{year:calYear},
                        success:function (data) {
                            alert(data.msg);
                            if(data.success){
                                window.location.reload();
                            }
                        },
                        error:function (data) {
                            alert("操作失败");
                        }
                    });
                }
            });

            var curYear = parseInt($('#year').val());
            var curMonth = parseInt($('#month').val());
            $('#lastYear').click(function () {
                reloadCal(curYear-1,curMonth);
            });
            $('#nextYear').click(function () {
                reloadCal(curYear+1,curMonth);
            });
            $('#lastMonth').click(function () {
                if(curMonth==1){
                    curMonth = 12;
                    curYear = curYear -1;
                }else{
                    curMonth = curMonth -1;
                }
                reloadCal(curYear,curMonth);
            });
            $('#nextMonth').click(function () {
                if(curMonth==12){
                    curMonth = 1;
                    curYear = curYear +1;
                }else{
                    curMonth = curMonth +1;
                }
                reloadCal(curYear,curMonth);
            });
        });
        function reloadCal(year,month){
            var paramString = "{year:"+year+",month:"+month+"}";
            var url = cur_proj_url + "/config/cal?paramString="+paramString;
            window.location.href=url;
        }
    </script>
    <#--<script type="text/javascript" src="${base}/static/js/config/busiGroup.js"></script>-->
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:780px;">
        <div class="col-8 left" style="min-width:500px;">
            <div class="ztreeLeftDiv" style="min-height: 350px;">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTree"><i class="icon-refresh"></i>日历管理</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="createCal"><i class="icon-plus"></i>生成日历</a>
                            <a href="javascript:void(0);" id="lastYear"><i class="icon-arrow-up"></i>上年度</a>
                            <a href="javascript:void(0);" id="nextYear"><i class="icon-arrow-down"></i>下年度</a>
                            <a href="javascript:void(0);" id="lastMonth"><i class="icon-arrow-up"></i>上月份</a>
                            <a href="javascript:void(0);" id="nextMonth"><i class="icon-arrow-down"></i>下月份</a>
                        </div>
                    </div>
                </h4>
                <div id="stuffInfoDiv" class="stuffInfoDiv" style="overflow-y: auto;">
                    <input type="hidden" id="year" name="year" value="${year!}">
                    <input type="hidden" id="month" name="month" value="${month!}">
                    <!-- 表格部分 -->
                    <table class="table table-border table-striped table-hover" >
                        <thead>
                        <tr>
                            <th width="70px">日期</th>
                            <th width="65px">星期</th>
                            <th width="65px">类型</th>
                            <th width="85px">上午</th>
                            <th width="85px">下午</th>
                            <th>备注</th>
                        </tr>
                        </thead>
                        <tbody id="calendarListTbody">
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
        <div class="col-4" style="min-width:200px;padding-left:10px;">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-info-sign"></i>日历编辑
                    </div>
                </h4>

                <form method="post" class="row cl form-horizontal" id="calForm">
                    <input type="hidden" id="calId" name="calId" value="${calendar.calId!}">
                    <#--<input type="hidden" id="calDate" name="calDate" value="${calendar.calDate!}">
                    <input type="hidden" id="calWeek" name="calWeek" value="${calendar.calWeek!}">-->
                    <div class="row cl form-element">
                        <div class="col-4 form-label">
                            日期
                        </div>
                        <div class="col-8 form-input">
                            <input style="background-color:#ccc;" name="calDate" id="calDate" value="${calendar.calDate!}" type="text" class="input-text size-M" readonly="readonly">
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-4 form-label">
                            星期
                        </div>
                        <div class="col-8 form-input">
                            <input style="background-color:#ccc;" name="calWeek" id="calWeek" value="${calendar.calWeek!}" type="text" class="input-text size-M" readonly="readonly">
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-4 form-label">
                            日期类型
                        </div>
                        <div class="col-8 form-input">
                            <select class="select" name="calType" id="calType">
                            <#list calTypeList as item>
                                <option value="${item.value}">${item.name}</option>
                            </#list>
                            </select>
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-4 form-label">
                            上午上班
                        </div>
                        <div class="col-8 form-input">
                            <input type="text" name="amBeginStr" id="amBeginStr" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="input-text size-M Wdate"/>
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-4 form-label">
                            上午下班
                        </div>
                        <div class="col-8 form-input">
                            <input type="text" name="amEndStr" id="amEndStr" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="input-text size-M Wdate"/>
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-4 form-label">
                            下午上班
                        </div>
                        <div class="col-8 form-input">
                            <input type="text" name="pmBeginStr" id="pmBeginStr" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="input-text size-M Wdate"/>
                        </div>
                    </div>
                    <div class="row cl form-element">
                        <div class="col-4 form-label">
                            下午下班
                        </div>
                        <div class="col-8 form-input">
                            <input type="text" name="pmEndStr" id="pmEndStr" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'H:mm'})" class="input-text size-M Wdate"/>
                        </div>
                    </div>
                    <div class="row cl pd-10" style="text-align:center;">
                        <button class="btn btn-secondary size-M btn-primary" type="button" id="calSaveBtn" style="display:none;">
                            <i class="icon-save icon-large"></i> 提交
                        </button>
                    </div>
                </form>
                <div id="createCalDiv" class="row cl form-horizontal configPanelDiv configPanelTopDiv" style="left:10px;right:0px;display:none;">
                    <div class="row cl form-element">
                        <div class="col-4 form-label">
                            年份
                        </div>
                        <div class="col-8 form-input">
                            <select class="select" name="calYear" id="calYear">
                            <#list yearList as item>
                                <#if item.value==((year+1)+"")>
                                    <option value="${item.value!}" selected="selected">${item.name!}</option>
                                <#else>
                                    <option value="${item.value!}">${item.name!}</option>
                                </#if>
                            </#list>
                            </select>
                        </div>
                    </div>
                    <div class="row cl form-element pd-10" style="text-align:center;">
                        <div class="col-6 form-input">
                            <button class="btn btn-secondary size-M btn-primary" type="button" id="calCreateBtn">
                                <i class="icon-save icon-large"></i> 创建日历
                            </button>
                        </div>
                        <div class="col-6 form-input">
                            <button class="btn btn-secondary size-M btn-danger" type="button" id="calCreateBtn">
                                <i class="icon-remove icon-large"></i> 删除日历
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    $(function(){
        $(".treeManage .ztreeLeftDiv>.stuffInfoDiv").height(contentHeight-60);
    });
</script>
</body>