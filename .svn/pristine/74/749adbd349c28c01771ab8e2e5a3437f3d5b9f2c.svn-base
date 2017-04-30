<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#--<meta http-equiv="X-UA-Compatible" content="IE=8"/>-->
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7" />
    <title>${ctx.getEnv('portal.title')}</title>
    <link rel="Bookmark" href="${base}/static/images/favicon.ico">
    <link rel="Shortcut Icon" href="${base}/static/images/favicon.ico">

    <link href="${base}/static/lib/bootstrap2.3/css/bootstrap.css" rel="stylesheet"/>
    <#--<link href="${base}/static/lib/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />-->

    <script src="${base}/static/lib/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="${base}/static/lib/jquery/jquery-ui.js"></script>
    <script src="${base}/static/lib/bootstrap2.3/js/bootstrap.js"></script>
    <script type="text/javascript" src="${base}/static/lib/jquery/jquery.cookie.js"></script>
    <script src="${base}/static/js/taskhandle.js"></script>
    <link href="${base}/static/css/taskhandle.css" rel="stylesheet"/>
    <link href="${base}/static/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />

    <link href="${base}/static/lib/tab-control/css/tab-control.css" rel="stylesheet"/>
    <style>
    </style>

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
        var _autoSave = "${ctx.getEnv('portal.handle.auto.save')}";
        var handleStyle='${ctx.getEnv('portal.handle.style')}';
    </script>
</head>
<body style="overflow:hidden">
<div class="PopPanel">
    <div class="panel-header panelBg ">
        <h3 class="panelLabel pull-left " style="vertical-align: middle;" id="div_modify">
            <i class="icon icon-white icon-edit iconPosition"></i><span id="wfname">${workFlowInstanceVo.workflowIntanceName!}</span>
        </h3>&nbsp;&nbsp;
        <#if (taskBackState??)&&(taskBackState==true)>
            <span class="label label-warning panelSpan">已退回<#if taskBackRemark??>，${taskBackRemark}</#if></span>
        <#else >
            <span class="label label-success panelSpan">办理中</span>
        </#if>

        <!-- 关闭及其其它操作  -->
        <div class="pull-right">
        <#if hasDel?string('true','false')=='true'>
            <button class="panelButton"  id="div_del"  type="button"><i class="icon-remove icon-white icon-large"></i>删除</button>
        </#if>
        <#if canTrustTask?string('true','false')=='true'>
            <button class="panelButton" id="div_trust"  type="button"><i class="icon-white icon-share-alt icon-large"></i>委托</button>
        </#if>
            <button class="panelButton"  id="div_back"  type="button"><i class="icon-arrow-left icon-white icon-large"></i>退回</button>
        <#if hasFinish?string('true','false')=='true'>
            <button class="panelButton"  id="div_finish"  type="button"><i class="icon-white icon-ok icon-large"></i>办结</button>
        <#else>
            <#if quickTurn?string('true','false')=='true'>
                <button class="panelButton"  id="div_quciktrans"  type="button"><i class="icon-share-alt icon-white icon-large"></i>转发</button>
            <#else>
                <button class="panelButton" id="div_trans"  type="button"><i class="icon-share-alt icon-white icon-large"></i>转发</button>
            </#if>
        </#if>
        </div>
        <#--<div class="pull-right">
            <button class="panelButton"  id="div_del"  type="button"><i class="icon-remove icon-white"></i>删除</button>
            <button class="panelButton" id="div_trust" type="button"><i class="icon-white icon-share-alt"></i>委托</button>
            <button class="panelButton"  id="div_back"  type="button"><i class="icon-arrow-left icon-white"></i>退回</button>
            <button class="panelButton"  id="div_finish"  type="button"><i class="icon-white icon-ok"></i>办结</button>
            <button class="panelButton"  id="div_quciktrans"  type="button"><i class="icon-share-alt icon-white"></i>快速转发</button>
            <button class="panelButton" id="div_trans"  type="button"><i class="icon-share-alt icon-white"></i>转发</button>
        </div>-->
        <!--end  关闭及其其它操作 -->
    </div>

    <!--  内容部分  -->
    <#include "task-handle-main.ftl"/>
    <!--end  内容部分  -->
</div>
<div id="myModal" class="modal hide fade border-all myModal draggable" role="dialog" aria-labelledby="myModal" aria-hidden="true">
    <h4 class="modal-top-bar">
        <div>
            <h4 class="modal-title" style="margin:0;"><i class="icon-question-sign icon-large"></i>确定要转发吗？</h4>
            <div class="topBtnDiv">
                <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="关闭">
                    <i class="icon-remove"></i>
                </a>
            </div>
        </div>
    </h4>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-footer">
                <button id="div_qucikturnInfo" type="button" class="btn btn-primary bottom-btn">
                    <i class="icon-share-alt icon-white icon-large"></i> 转　发
                </button>

                <button type="button" class="btn bottom-btn-close" data-dismiss="modal">
                    <i class="icon-remove icon-large"></i> 关　闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="finishModal" class="modal hide fade border-all finishModal draggable" role="dialog" aria-labelledby="finishModal" aria-hidden="true">
    <h4 class="modal-top-bar">
        <div>
            <h4 class="modal-title" style="margin:0;"><i class="icon-question-sign icon-large"></i>确定要办结吗？</h4>
            <div class="topBtnDiv">
                <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="关闭">
                    <i class="icon-remove"></i>
                </a>
            </div>
        </div>
    </h4>
    <div class="row cl" style="text-align:center;padding-top:10px;">
        <button id="div_finishWf" class="btn-secondary size-M hui-btn" type="button">
            <i class="icon-white icon-ok"></i> 办　结
        </button>
    </div>
</div><!-- /.modal -->
<div id="handleModel" class="modal hide fade border-all handleModal draggable" role="dialog" aria-labelledby="handleModal" aria-hidden="true" style="height:380px;">
    <h4 class="modal-top-bar">
        <div>
            <h4 class="modal-title" style="margin:0;" id="handleModelTitle"></h4>
            <div class="topBtnDiv">
                <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="关闭" id="closeHandleModal" >
                    <i class="icon-remove"></i>
                </a>
            </div>
        </div>
    </h4>
    <iframe id="handleModelIfr" name="handleModelIfr" src="" width=100% class="iframe1" style="height:350px;width:819px;border:none;" frameborder="0" scrolling="auto">
    </iframe>
</div>
<div id="modifyModal" class="modal hide fade border-all myModal draggable" role="dialog" aria-labelledby="modifyModal" aria-hidden="true">
    <h4 class="modal-top-bar">
        <div>
            <h4 class="modal-title" style="margin:0;"><i class="icon icon-white icon-edit iconPosition icon-large"></i>项目名称编辑</h4>
            <div class="topBtnDiv">
                <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="关闭" >
                    <i class="icon-remove"></i>
                </a>
            </div>
        </div>
    </h4>
    <div class="row cl">
        <div class="col-3 form-label" style="padding-top:4px;">
            项目名称：
            <input type="text" id="workflowIntanceName" class="input-text size-M" autocomplete="off" value="${workFlowInstanceVo.workflowIntanceName!}" style="width: 460px;border: solid 1px #aaa;border-radius: 0px;">
        </div>
    </div>
    <div class="row cl" style="text-align:center;">
        <button class="btn-secondary size-M" type="button" id="btn_modifyWf">
            <i class="icon-ok icon-large"></i> 确　定
        </button>
    </div>
</div>
</body>
</html>
