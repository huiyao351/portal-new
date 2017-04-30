<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>egov-home配置文件</title>
<#include "../include-tree.ftl"/>
    <#--<script type="text/javascript" src="${base}/static/js/config/cal.js"></script>-->

    <script type="text/javascript">
        $(document).ready(function(){
            var curProj = "${project!}";
            if (curProj == "egov" || curProj == "gis" || curProj == "portal") {
                $('#refreshPro').hide();
            }

            $('#peojectList').change(function() {
                var type = $(this).find("option:selected").text();//$(this).val();
                if(type) {
                    var url = cur_proj_url+"/config/pro?project="+type;
                    window.location.href = url;
                }
            });
            $("#newConfigCloseBtn").click(function () {
                $('#newConfigModal').modal('hide');
                $('#newConfigForm').find(".input-text").removeAttr("aria-invalid").removeAttr("title").removeAttr("style");
                $('#newConfigForm')[0].reset();
            });

            $("#newConfigSaveBtn").click(function () {
                var lastKey = $('#curConfigKey').val();
                if(lastKey){
                    var key = $('#key').val();
                    var value = $('#value').val();
                    var comment = $('#comment').val();
                    if(key){
                        $.ajax({
                            url:cur_proj_url+"/config/pro/save",
                            type:'post',
                            data:{key:key,value:value,comment:comment,project:$('#project').val(),lastKey:lastKey},
                            success:function(data){
                                alert(data.msg);
                                if(data.success){
                                    window.location.reload();
                                }
                            }
                        });
                    }else{
                        alert("配置项必填");
                    }
                }
            });

            $("#refreshPro").click(function () {
                var type = $("#peojectList").find("option:selected").text();
                if(type){
                    if(type == "egov" || type == "gis"){
                        alert("该配置文件的刷新，只需要调用对应系统的配置文件刷新功能！");
                    }else{
                        var project = $("#peojectList").val();
                        if(project && project != ""){
                            $.ajax({
                                url:project,
                                type:'post',
                                data:{project:project},
                                success:function(data){
                                    if(data.constructor==String){
                                        data = jQuery.parseJSON(data + "");
                                    }
                                    alert(data.msg);
                                }
                            });
                        }else{
                            alert("请在egovhome下的portal配置文件中，增加对应业务系统的配置文件，如过没有，联系业务系统开发人员！");
                        }
                    }
                }
            });
        });

        function doTrEdit(obj,key){
            //找到所属行
            var trNode = $(obj).parent().parent();
            if($(trNode).attr("id")==key){
                var isdisabled = false;
                $(trNode).find("td input[type='text']").each(function () {
                    if($(this).prop("disabled")==true){
                        isdisabled = true;
                    }
                });
                if(isdisabled){
                    $("input[name='valueArr']").attr("disabled","disabled");
                    $("textarea[name='commentArr']").attr("disabled","disabled");
                    $("i[name='savePro']").hide();

                    //var key = $(trNode).attr("id");
                    $(trNode).find("input[name='valueArr']").each(function () {
                        $(this).removeAttr("disabled");
                    });
                    $(trNode).find("textarea[name='commentArr']").each(function () {
                        $(this).removeAttr("disabled");
                    });

                    $(trNode).find("i[name='savePro']").show();
                }else{
                    $("input[name='valueArr']").attr("disabled","disabled");
                    $("textarea[name='commentArr']").attr("disabled","disabled");
                    $("i[name='savePro']").hide();
                }
            }
        }

        function saveEditTr(obj,key){
            //找到所属行
            var trNode = $(obj).parent().parent();
            if($(trNode).attr("id")==key){
                var value = $(trNode).find("input[name='valueArr']").val();
                var comment = $(trNode).find("textarea[name='commentArr']").val();
                $.ajax({
                    url:cur_proj_url+"/config/pro/save",
                    type:'post',
                    data:{key:key,value:value,comment:comment,project:$('#project').val()},
                    success:function(data){
                        alert(data.msg);
                        if(data.success){
                            window.location.reload();
                        }
                    }
                });
            }
        }

        function openNewTr(obj,key){
            //找到所属行
            var trNode = $(obj).parent().parent();
            if($(trNode).attr("id")==key){
                $('#curConfigKey').val(key);
                $('#newConfigModal').modal({show:true});
            }
        }

    </script>
    <style type="text/css">
        th{
            font-size:14px;font-weight:bold;
        }
    </style>
</head>
<body>
<div class="Hui-wraper">
    <div class="row cl treeManage" style="min-width:780px;">
        <div class="col-12 left" style="min-width:700px;">
            <div class="ztreeLeftDiv" style="min-height: 400px;">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-cog"></i>配置文件管理
                        <select class="select" name="peojectList" id="peojectList" style="width:150px;font-size:14px;">
                        <#--<#list fileList as item>
                            <option value="${item!}" <#if item==project>selected="selected" </#if>>${item!}</option>
                        </#list>-->
                        <#list propMap?keys as item>
                            <option value="${propMap[item]!}" <#if item==project>selected="selected" </#if>>${item!}</option>
                        </#list>
                        </select>
                        <a href="javascript:void(0);" style="margin-left:20px;" id="refreshPro"><i class="icon-refresh"></i>刷新系统配置项</a>
                        <div class="topBtnDiv">
                            <#--<a href="javascript:void(0);" id="createCal"><i class="icon-plus"></i>刷新系统配置项</a>-->
                        </div>
                    </div>
                </h4>
                <div id="stuffInfoDiv" class="stuffInfoDiv" style="overflow-y: auto;overflow-x: hidden;">
                    <input type="hidden" id="project" name="project" value="${project!}">
                    <!-- 表格部分 -->
                    <table class="table table-border table-striped table-hover" >
                        <thead>
                        <tr>
                            <th width="150px">配置项</th>
                            <th width="450px">配置值</th>
                            <th>注释说明</th>
                            <th width="90px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list entryMap?keys as key>
                        <tr class="detailInfo" id="${key!}">
                            <td style="font-size:14px;font-weight:bold;">${key!}</td>
                            <td><input name="valueArr" value="${entryMap[key].value!}" type="text" class="input-text" style="height:42px;width:100%;font-size:14px;padding:2px 4px;" disabled="disabled" ></td>
                            <td>
                                <textarea name="commentArr" class="textarea" style="height:42px;width:99%;font-size:14px;padding:2px 4px;" disabled="disabled" >${entryMap[key].comment!}</textarea>
                            </td>
                            <td style="font-size:14px;font-weight:bold;">
                                <i name="editPro" class="icon-pencil icon-large"  onclick="doTrEdit(this,'${key!}')" title="编辑"></i>
                                <i name="savePro" class="icon-save icon-large" style="margin-left:10px; display:none; " onclick="saveEditTr(this,'${key!}')" title="保存"></i>
                                <i name="insertPro" class="icon-plus icon-large" style="margin-left:10px;" onclick="openNewTr(this,'${key!}')" title="下方插入"></i>
                            </td>
                        </tr>
                        </#list>
                        <#--<#list proList as item>
                        <tr class="detailInfo" id="${item.key!}">
                            <td style="font-size:14px;font-weight:bold;">${item.key!}</td>
                            <td><input name="valueArr" value="${item.value!}" type="text" class="input-text" style="height:42px;width:100%;font-size:14px;padding:2px 4px;" disabled="disabled" ></td>
                            <td>
                                <textarea name="commentArr" class="textarea" style="height:42px;width:99%;font-size:14px;padding:2px 4px;" disabled="disabled" >${item.comment!}</textarea>
                            </td>
                            <td style="font-size:14px;font-weight:bold;">
                                <i name="editPro" class="icon-pencil icon-large" onclick="doTrEdit(this,'${item.key!}')">编辑</i>
                                <i name="savePro" class="icon-save icon-large" style="margin-left:20px; display:none;" onclick="saveEditTr(this,'${item.key!}')">保存</i>
                            </td>
                        </tr>
                        </#list>-->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div id="newConfigModal" class="modal hide fade selectConfigModal" role="dialog" aria-labelledby="stuffSelectModal" aria-hidden="true" style="height:240px;">
        <h4 class="modal-top-bar">
            <div>
                <i class="icon-file">新增配置项</i>
                <div class="topBtnDiv">
                    <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" id="closeStuffModal" style="" >
                        <i class="icon-remove icon-large"></i>
                    </a>
                </div>
            </div>
        </h4>
        <form method="post" class="row cl form-horizontal" id="newConfigForm">
            <input type="hidden" id="curConfigKey"/>
            <div class="row cl form-element">
                <div class="col-2 form-label">
                    配置项
                </div>
                <div class="col-10 form-input">
                    <input name="key" id="key" type="text" class="input-text size-M" >
                </div>
            </div>
            <div class="row cl form-element" style="padding-top: 0px;">
                <div class="col-2 form-label">
                    配置值
                </div>
                <div class="col-10 form-input">
                    <input name="value" id="value" type="text" class="input-text size-M" >
                </div>
            </div>
            <div class="row cl form-element" style="padding-top: 0px;">
                <div class="col-2 form-label">
                    注释说明
                </div>
                <div class="col-10 form-input">
                    <textarea id="comment" name="comment" class="textarea" style="height:60px;width:99%;padding:2px 4px;"></textarea>
                </div>
            </div>
            <div class="row cl form-element" style="padding-top: 6px;">
                <div class="col-3">
                </div>
                <div class="col-3 form-label">
                    <button class="btn btn-secondary size-M btn-primary" type="button" id="newConfigSaveBtn">
                        <i class="icon-save icon-large"></i> 提交
                    </button>
                </div>
                <div class="col-3 form-label">
                    <button class="btn btn-inverse size-M" type="button" id="newConfigCloseBtn">
                        <i class="icon-remove icon-large"></i> 关闭
                    </button>
                </div>
                <div class="col-3">
                </div>
            </div>
        </form>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
<script type="text/javascript">
    $(function(){
        $(".treeManage .ztreeLeftDiv>.stuffInfoDiv").height(contentHeight-60);
    });
</script>
</body>