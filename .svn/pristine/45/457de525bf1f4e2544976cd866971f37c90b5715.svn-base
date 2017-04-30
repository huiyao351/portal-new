<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>${ctx.getEnv('portal.title')}</title>
    <link rel="Bookmark" href="${base}/static/images/favicon.ico">
    <link rel="Shortcut Icon" href="${base}/static/images/favicon.ico">

    <link href="${base}/static/lib/bootstrap2.3/css/bootstrap.css" rel="stylesheet"/>
    <link href="${base}/static/lib/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />

    <script src="${base}/static/lib/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${base}/static/lib/jquery/jquery-ui.js"></script>
    <script src="${base}/static/lib/bootstrap2.3/js/bootstrap.js"></script>
    <script type="text/javascript" src="${base}/static/lib/jquery/jquery.cookie.js"></script>
    <script src="${base}/static/js/taskhandle.js"></script>
    <link href="${base}/static/css/taskhandle.css" rel="stylesheet"/>
    <link href="${base}/static/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />

    <link href="${base}/static/lib/tab-control/css/tab-control.css" rel="stylesheet"/>

    <script type="text/javascript">
        var platform_url='${path_platform}';
        var portalUrl = '${base}';
        var _defaultName = "${defaultName!}";
        var _readOnly = "${readOnly!}";
        var _fileCenterToken="${fileTokenId!}";
        var _fileCenterText=  "";
        var _fileCenterRootId= "${fileCenterNodeId!}";
        var _fileCetnerUrl = "${fileCenterUrl}/node/list.do?token=${fileTokenId!}&nid=${fileCenterNodeId!}";
        var _menuurl="${path_platform}/taskhandle!menu.action?taskid=${taskid!}";
        var _menuCount= ${menuCount!};
        var _taskId="${taskid!}";
        var _wiid="${workFlowInstanceVo.workflowIntanceId!}";
        var _proid="${workFlowInstanceVo.proId!}";
        var _resourceUrl='${path_platform}/SysResource.action?from=task&taskid=${taskid!}&proid=${workFlowInstanceVo.proId}&wiid=${workFlowInstanceVo.workflowIntanceId}&rid=';
        var _kcdjTypeConfig = "${kcdjTypeConfig!}";
        var _buildlandUrl = "${ctx.getEnv('buildland.url')}";
        var _busiType = "${busiType!}";
        var _version = "${version!}";
        var _autoSave = "false";
        var handleStyle='${ctx.getEnv('portal.handle.style')}';
    </script>
</head>
<body style="overflow:hidden"><!--  带属性的面板  -->
<div class="PopPanel">
    <div class="panel-header panelBg ">
        <h3 class="panelLabel pull-left " style="vertical-align: middle;">
            <i class="icon icon-white icon-edit iconPosition"></i>${workFlowInstanceVo.workflowIntanceName!}</h3>&nbsp;&nbsp;
        <#if workFlowInstanceVo.workflowState==2>
            <span class="label label-success panelSpan">办结</span>
        <#elseif  workFlowInstanceVo.workflowState==3>
            <span class="label label-success panelSpan">挂起</span>
        <#else>
            <span class="label label-success panelSpan">办理中</span>
        </#if>
    </div>

    <!--  内容部分  -->
    <#include "task-handle-main.ftl"/>
    <!--end  内容部分  -->
</div>
</body>
</html>
