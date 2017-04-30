<!-- 新建任务 -->
<div class="row cl pl-5" id="NewTask">
    <div class="col-1" style="padding:10px 0px 0px 0px;">
        <h3 class="NewTaskLab">新建任务:</h3>
		<img src="${base}/static/images/pic_01.png" alt="" width="100%" />
    </div>
    <div class="col-11" style="padding-bottom: 6px;">
        <#if createType?? && createType == 'group'>
            <!-- 新建任务按照业务组来进行区分 -->
            <div id="tab_demo2" class="HuiTab">
                <div class="tabBar2 cl" id="tab_bs">
                    <#list businessGroupList as businessGroupItem>
                        <span tabid="${businessGroupItem.businessGroupId!}" groupId="${businessGroupItem.businessIds?replace('"',"@")}" onclick="queryBusinessGroup('${businessGroupItem.businessIds?replace('"',"@")}')" >${businessGroupItem.businessGroupName!}</span>
                    </#list>
                </div>
                <#list businessGroupList as businessGroupItem>
                    <div class="tabCon2">
                        <#if businessGroupItem.workflowDefineMap??>
                            <#list businessGroupItem.workflowDefineMap?keys as businessItem>
                                <div class="row border-bottom-grey businessWd">
                                    <a href="javascript:void(0)" title="${businessItem?split("@")[0]}" class="col-1" style="padding-top: 10px;text-align:right;font-size:14px;font-weight: bold;" onclick="turnBusinessTab('<#if businessItem?index_of("@")!=-1>${businessItem?split("@")[1]}</#if>');">
                                        <#if businessItem?index_of("@")!=-1>
                                        <#if (businessItem?split("@")[0])?length gt 6 >${businessItem?substring(0,5)+"..."}<#else>${businessItem?split("@")[0]}</#if>
                                        <#else>${businessItem}</#if>
                                    </a>
                                    <ul class="toDoList col-11">
                                        <#list businessGroupItem.workflowDefineMap[businessItem] as workflowDefineItem>
                                            <li>
                                                <a href="javascript:showCreateWindow('${workflowDefineItem.workflowDefinitionId}','${workflowDefineItem.createUrl}',${workflowDefineItem.createWidth},${workflowDefineItem.createHeight})"
                                                   role="button"
                                                   title="${workflowDefineItem.workflowName}"><#if workflowDefineItem.workflowName?length gt 13 >${workflowDefineItem.workflowName?substring(0,12)+"..."}<#else>${workflowDefineItem.workflowName}</#if></a>
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                            </#list>
                        </#if>
                    </div>
                </#list>
            </div>
        <#else>
            <!-- 新建任务按照业务来进行区分，旧的新建任务列表 -->
            <div id="tab_demo2" class="HuiTab">
                <div class="tabBar2 cl" id="tab_bs">
                    <#list workFlowDefineMap?keys as businessItem>
                        <#list workFlowDefineMap[businessItem] as workflowDefineItem>
                            <#if workflowDefineItem_index < 1>
                                <span tabid="${workflowDefineItem.businessId}" onclick="turnBusinessTab('${workflowDefineItem.businessId}');">${businessItem}</span>
                            </#if>
                        </#list>
                    </#list>
                </div>
                <#list workFlowDefineMap?keys as businessItem>
                    <div class="tabCon2">
                        <ul class="toDoList">
                            <#list workFlowDefineMap[businessItem] as workflowDefineItem>
                                <li>
                                    <a href="javascript:showCreateWindow('${workflowDefineItem.workflowDefinitionId}','${workflowDefineItem.createUrl}',${workflowDefineItem.createWidth},${workflowDefineItem.createHeight})"
                                       role="button"
                                       title="${workflowDefineItem.workflowName}"><#if workflowDefineItem.workflowName?length gt 13 >${workflowDefineItem.workflowName?substring(0,12)+"..."}<#else>${workflowDefineItem.workflowName}</#if></a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                </#list>
            </div>
        </#if>
    </div>
</div>
<!--end 新建任务 -->