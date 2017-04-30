<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>${ctx.getEnv('portal.title')}</title>

    <link rel="Bookmark" href="${base}/static/images/favicon.ico">
    <link rel="Shortcut Icon" href="${base}/static/images/favicon.ico">

    <link href="${base}/static/lib/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/lib/h-ui/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/lib/h-ui/css/H-ui.doc.css" rel="stylesheet" type="text/css">
    <link href="${base}/static/lib/h-ui/css/H-ui.admin.css" rel="stylesheet" type="text/css">
    <link href="${base}/static/lib/h-ui/css/laypage.css" rel="stylesheet" type="text/css">
    <link href="${base}/static/lib/bootstrapSwitch/bootstrapSwitch.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/lib/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
    <link href="${base}/static/css/public.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript" src="${base}/static/lib/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/static/lib/jquery/jquery.cookie.js"></script>
    <script type="text/javascript" src="${base}/static/lib/layer1.8/layer.min.js"></script>
    <script type="text/javascript" src="${base}/static/lib/laypage/laypage.js"></script>
    <script type="text/javascript" src="${base}/static/lib/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${base}/static/lib/bootstrapSwitch/bootstrapSwitch.js"></script>
    <script type="text/javascript" src="${base}/static/lib/Validform_v5.3.2.js"></script>
    <script type="text/javascript" src="${base}/static/lib/passwordStrength-min.js"></script>
    <script type="text/javascript" src="${base}/static/lib/unslider.min.js"></script>
    <script type="text/javascript" src="${base}/static/lib/h-ui/js/H-ui.js"></script>
    <!--  调用jQueryUI实现拖动 -->
    <script type="text/javascript" src="${base}/static/lib/jquery/jquery-ui.js"></script>
    <script type="text/javascript" src="${base}/static/lib/bootstrap-modalmanager.js"></script>
    <script type="text/javascript" src="${base}/static/lib/bootstrap-modal.js"></script>
    <script type="text/javascript" src="${base}/static/lib/artTemplate/template.js"></script>
    <script type="text/javascript" src="${base}/static/lib/xdate/xdate.js"></script>

    <link rel="stylesheet" href="${base}/static/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${base}/static/lib/ztree/js/jquery.ztree.core-3.5.js"></script>

    <script type="text/javascript" src="${base}/static/js/taskCenter.js"></script>

    <script type="text/javascript">
        var portalUrl = '${base}';
        var platform_url = '${path_platform}';
        var taskListSplitInfo = '${taskList!}';
        var taskOverListSplitInfo = '${taskOverList!}';
        var projectListSplitInfo = '${projectList!}';
        var projectPerformerListSplitInfo = '${projectPerformerList!}';
        var currentDate = '${currentDate!}';
        var excludeWdids = "${excludeWdids!}";
        var calenderServiceType = '${ctx.getEnv('calenderService.type')}';
        var handleStyle='${ctx.getEnv('portal.handle.style')}';
        var taskState = "${taskState!}";
    </script>
    <style>
        .form-base select{
            width: 99%;
        }
    </style>
</head>
<body style="overflow: hidden;">
    <!--  主要内容区域   -->
    <div class="Hui-wraper ">
        <!-- 首页内容示意 -->
        <!-- 配置栏目 -->
        <div class="row cl bg-white border-all background-grey">
            <!-- 隐藏新建任务模块-->
            <#--<div class="topHiddenButton flip" id="flip">
                <i class="icon-double-angle-up"></i>
            </div>-->
            <div class="topHiddenButton flip" id="flip"><i class="icon-double-angle-up"></i></div>
            <!--end 隐藏新建任务模块-->
            <!-- 任务列表 -->
            <div class="col-12 bg-white border-left-grey mL1">
                <!-- 新建任务 -->
                <#include "task-create-list.ftl"/>
                <!--end 新建任务 -->

                <!-- 任务列表 -->
                <div id="tab_demo4" class="HuiTab cl">
                    <!-- 首页工作流列表切换tab页 -->
                    <div id="taskTab" class="tabBar4 cl">
                        <span id="#taskListTab"><i class="icon-edit"></i> 待办任务</span>
                        <span id="#taskOverListTab"><i class="icon-ok"></i> 已办任务</span>
                        <span id="#projectPerformerListTab"><i class="icon-reorder"></i> 参与项目</span>
                        <span id="#projectListTab"><i class="icon-list"></i> 权限项目</span>
                    </div>
                    <!-- 待办任务 -->
                    <#include "task-list.ftl"/>
                    <!--end 待办任务 -->
                    <!-- 已办任务 -->
                    <#include "task-over-list.ftl"/>
                    <!--end 已办任务 -->
                    <!-- 参与项目列表 -->
                    <#include "project-performer-list.ftl"/>
                    <!--end 参与项目列表 -->
                    <!-- 权限项目列表 -->
                    <#include "project-list.ftl"/>
                    <!--end 权限项目列表 -->
                </div>
                <!--end 任务列表 -->

            </div>
            <!--end 任务列表 -->

        </div>
        <!--end 配置栏目 -->
        <!--end 首页内容示意 -->
    </div>
<!--工作流暂停 -->
<#include "task-post.ftl"/>
<!--end 工作流暂停 -->
<!-- 进度条 -->
<#include "../common/progress.ftl"/>
<!--end 进度条 -->
<!-- 高级搜索窗体 -->
<#include "task-query-form.ftl"/>
<!--end 高级搜索窗体 -->
</body>
</html>
