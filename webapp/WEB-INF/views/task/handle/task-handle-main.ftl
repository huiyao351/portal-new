<!--  内容部分  -->
<div class="panel-body">
    <!--  业务内容 -->
    <div class="tabbable tabs-left">
        <div id="leftMenuDiv" class="nav-tabs">
            <ul class="nav oneMenuUL" id="myTab3">
            <#assign findDefaultName="0" />
            <#list menuList as result>
                <#if  (defaultName?? && result.text==defaultName) || (defaultName==""&& result_index==0)>
                    <#assign findDefaultName="1" />
                </#if>
            </#list>
            <#list menuList as result>
                <li resourceLink="${result.link!}" resouceName="${result.text!}">
                    <#if result.children?? && (result.children?size > 0)>
                        <a class="oneMenuA" data-toggle="tab" href="#"
                           onclick="showOrHideMenu(this)">
                            <i class="icon-folder-close"></i> ${result.text!}
                            <div style="width:20px;float:right;text-align:right;padding:2px 2px 0px 2px;">
                                <i class="icon-chevron-down"></i>
                            </div>
                        </a>
                        <ul class="nav nav-tabs twoMenuUL">
                            <#list result.children as children>
                                <li resourceLink="${children.link!}" resouceName="${children.text!}">
                                    <#if children.children?? && (children.children?size > 0)>
                                        <a class="twoMenuA" data-toggle="tab" href="#"
                                           onclick="showOrHideMenu(this)">
                                            <i class="icon-folder-close"></i> ${children.text!}
                                            <div style="width:20px;float:right;text-align:right;padding:2px 2px 0px 2px;">
                                                <i class="icon-chevron-down"></i>
                                            </div>
                                        </a>
                                        <ul class="nav nav-tabs threeMenuUL">
                                            <#list children.children as thirdChild>
                                                <li resourceLink="${thirdChild.link!}" resouceName="${thirdChild.text!}">
                                                    <a class="clickResource threeMenuA" data-toggle="tab" href="#"
                                                       onclick="openResource('${result_index}_${children_index}_${thirdChild_index}','${path_platform!}/${thirdChild.link!}','${children.text!}')" title="${children.text!}">
                                                        <i class="icon-file-alt"></i> ${thirdChild.text!}
                                                    </a>
                                                </li>
                                            </#list>
                                        </ul>
                                    <#else>
                                        <a class="clickResource twoMenuA" data-toggle="tab" href="#"
                                           onclick="openResource('${result_index}_${children_index}','${path_platform!}/${children.link!}','${children.text!}')" title="${children.text!}">
                                            <i class="icon-file-alt"></i> ${children.text!}
                                        </a>
                                    </#if>
                                </li>
                            </#list>
                        </ul>
                    <#else>
                        <a class="clickResource oneMenuA" data-toggle="tab" href="#"
                        onclick="openResource('${result_index}','${path_platform!}/${result.link!}','${result.text!}')">
                        <i class="icon-file-alt"></i> ${result.text!}
                        </a>
                    </#if>
                </li>
            </#list>
            </ul>
            <div id="divKcdj" class="kcdjInfoDiv">
                <ul class="nav projectDetailInfoUl">
                    <li id="kcdj_bg"><a href="javascript:showKcdj('kcdj','${workFlowInstanceVo.workflowIntanceId}')"><img alt="勘测定界报告" src="/portal/static/images/details.png">&nbsp;&nbsp;&nbsp;勘测定界报告</a></li>
                    <li id="kcdj_jzd"><a href="javascript:showKcdj('jzd','${workFlowInstanceVo.workflowIntanceId}')"><img alt="界址点管理" src="/portal/static/images/txt.gif">&nbsp;&nbsp;界址点管理</a></li>
                    <li id="kcdj_map"><a href="javascript:showKcdj('map','${workFlowInstanceVo.workflowIntanceId}')"><img alt="图形浏览" src="/portal/static/images/map.png">&nbsp;&nbsp;图形浏览</a></li>
                </ul>
            </div>
        </div>
        <div class="tab-content Adaptive">
            <div class="tab-pane active">
                <!-- 选项卡 -->
                <div class="tab-control">
                    <!-- 标签 -->
                    <div class="tab simple">
                        <form>
                            <input class="prev" type="button" />
                            <input class="next" type="button" />
                            <input class="find" type="button" />
                        </form>
                        <ul>
                            <!-- <li>标签<a href="javascript:;">关闭</a></li> -->
                        </ul>
                    </div>

                    <!-- 标签查找 -->
                    <div class="tab-find hidden">
                        <form>
                            <input class="text" type="text" />
                        </form>
                        <ul>
                            <!-- <li>标签<a href="javascript:;">关闭</a></li> -->
                        </ul>
                    </div>
                    <!-- 主体 -->
                    <div class="main">
                        <!-- <iframe scrolling="auto" frameborder="0"></iframe> -->
                    </div>
                </div>
                <#--<div style="width:100%;height:30px;border:1px solid #ccc;"></div>
                <iframe id="contentFrame" name="contentFrame" width="99.8%" height="749px" style="border:none;" frameborder="0" scrolling="auto"></iframe>-->
            </div>
            <div id="loading" class="loading" style="position:absolute;top:0px;left:0px;z-index: 9999;width: 100%;height: 484px;display: none;">
                <img src="static/images/loading.gif" alt="" />
            </div>
        </div>
        <div class="projectInfoDiv">
            <ul class="nav projectDetailInfoUl">
                <li>
                    <nobr>
                        流程信息：
                        <img title="流程图" src="${base}/static/images/flow.gif"
                             border="0" style="cursor:pointer"
                             onclick="openResource('flow','${path_platform!}/showchart.action?wiid=${workFlowInstanceVo.workflowIntanceId!}','流程图')" />
                        <font style="margin-left:48px;">附件信息：</font>
                        <img title="附件" src="${base}/static/images/folder_pictures.png"
                             border="0" style="cursor:pointer"
                             onclick="openResource('file','${path_platform!}/fc.action?readOnly=${readOnly!}&proid=${workFlowInstanceVo.workflowIntanceId!}','附件')" />
                    </nobr>
                </li>
                <li>项目类型：${workFlowDefineVo.workflowName!}</li>
                <#if (activityVo?? && activityVo.activityName??)>
                    <li>任务类型：${activityVo.activityName!}</li>
                    <li>开始时间：${(activityVo.beginTime?datetime)!}</li>
                    <li>截止时间：${(taskVo.overTime?datetime)!}</li>
                    <#if (beforeTasks??)>
                        <li>
                            转 发 人 ：
                            <#list beforeTasks as beforeTask>
                            ${beforeTask.userVo.userName!}&nbsp;
                            </#list>
                        </li>
                    </#if>
                <#else>
                    <li>开始时间：${(workFlowInstanceVo.createTime?datetime)!}</li>
                    <li>截止时间：${(workFlowInstanceVo.overTime?datetime)!}</li>
                </#if>
                <li>
                    <nobr>关联项目：
                    <#if (readOnly?? && readOnly=='false')>
                        <a href="#" onclick="openResource('rel','${path_platform!}/projectrelate.action?wiid=${workFlowInstanceVo.workflowIntanceId!}','关联项目')">
                            <i class="icon-random"></i>
                        </a>
                    </#if>
                    </nobr>
                    <table id="relatedProjects" border="0" cellpadding="0" cellspacing="0" style="font-size： 12px; width:100%;margin-top:4px;">
                    </table>
                </li>
            </ul>
        </div>
    </div>
    <!--end  业务内容  -->
</div>
<script src="${base}/static/lib/tab-control/js/tab-control.js"></script>
<!--end  内容部分  -->