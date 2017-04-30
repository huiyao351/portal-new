<div class="tabCon4" id="projectListTab">
    <div class="workListTools">
        <!-- 表格操作菜单 -->
        <div class="complexTableTools">
        <#if hasRestart?? && hasRestart==true>
            <button class="btn btn-success size-MINI" type="button" id="restartProjectBtn">
                <i class="icon-refresh"></i> 重办
            </button>
        </#if>
        <#if hasDel?? && hasDel==true>
            <button class="btn btn-success size-MINI" type="button" id="delProjectBtn">
                <i class="icon icon-trash"></i> 删除
            </button>
        </#if>
        </div>
        <!--end 表格操作菜单 -->

        <!-- 搜索 -->
        <div class="searchBar workSearch">
            <form class="form-search" method="post" action="">
                <input type="text" id="searchKeyword" name="WORKFLOW_INSTANCE_NAME" placeholder="请输入项目名称" class="searchTxt " autocomplete="off">
                <#--<input class="btn btn-default advancedBtn" type="button" value="高级搜索" id="show">
                <input id="searchBtn" name="searchBtn" type="button" value="搜索" class="searchBtn button-search">-->
                <button class="btn btn-primary size-M button-search ">
                    <i class="icon-search icon-large"></i> 搜 索
                </button>
                <button class="btn btn-secondary size-M advancedBtn">
                    <i class="icon-search icon-large"></i> 高级搜索
                </button>
            </form>
            <!-- 高级搜索 -->
            <div class="draggable advancedSearchPop">
                <h4 class="advancedSearchLab">
                    <i class="icon-search"></i> 高级搜索 <a href="#" class="icon-remove close"></a>
                </h4>
                <form action="" class="form-horizontal">
                    <div class="row cl pd-10">
                        <div class="col-2 form-label">
                            任务描述
                        </div>
                        <div class="col-4">
                            <input type="text" name="REMARK" class="input-text size-M" autocomplete="off">
                        </div>
                        <div class="col-2 form-label">
                            创建人
                        </div>
                        <div class="col-4">
                            <input type="text" name="CREATE_USERNAME" class="input-text size-M" autocomplete="off">
                        </div>
                    </div>
                    <div class="row cl pd-10 row-fluid">
                        <div class="col-2 form-label">
                            业务
                        </div>
                        <div class="col-4">
                            
                                <select class="select" name="BUSINESS_ID">
                                    <option value="">所有</option>
                                <#list businessList as businessItem>
                                    <option value="${businessItem.businessId}">${businessItem.businessName}</option>
                                </#list>
                                </select>
                           
                        </div>
                        <div class="col-2 form-label">
                            流程类型
                        </div>
                        <div class="col-4">
                            
                                <select class="select" name="WORKFLOW_DEFINITION_ID">
                                    <script id="queryWorkflowDefinitionList" type="text/html">
                                        {{each workflowDeinfitionList as workflowDefinition i}}
                                        <option value="{{workflowDefinition.workflowDefinitionId}}">
                                            {{workflowDefinition.workflowName}}
                                        </option>
                                        {{/each}}
                                    </script>
                                </select>
                           
                        </div>
                    </div>
                    <div class="row cl pd-10">
                        <div class="col-2 form-label">
                            时间段
                        </div>
                        <div class="col-10 ">
                            <input class="input-text size-M Wdate timeWidth" style="width:188px;" name="BEGIN_TIME" type="text" onfocus="WdatePicker({firstDayOfWeek:1,skin:'twoer'})">
                            <font>~</font>
                            <input class="input-text size-M Wdate timeWidth" name="FINISH_TIME" type="text" onfocus="WdatePicker({firstDayOfWeek:1,skin:'twoer'})">
                        </div>
                    </div>
                    <div class="row cl pd-10">
                        <div class="col-2 form-label">
                            状态
                        </div>
                        <div class="col-4">
                            
                                <select class="select" name="STATE">
                                    <option value="">所有</option>
                                    <option value="1">正在办理</option>
                                    <option value="2">已办结</option>
                                    <option value="3">挂起</option>
                                </select>
                           
                        </div>
                    </div>
                    <div class="row cl pd-10">
                        <div class="col-xs-10  col-offset-2">
                            <button id="show" class="btn btn-secondary size-M button-search ">
                                <i class="icon-search icon-large"></i> 搜 索
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <!--end 高级搜索 -->
        </div>
        <!--end 搜索 -->
    </div>

    <!-- 表格部分 -->
    <div class="detailTableDiv">
        <table class="table table-border  table-striped table-hover">
            <thead>
            <tr>
                <th width="10px">
                    <input type="checkbox" class="ckbSelectAll" id="ckbSelectAllProList">
                </th>
                <th width="30px">序号</th>
                <th>项目描述</th>
                <th width="250px">流程</th>
                <th width="150px">节点名称</th>
                <th width="150px">结束时间</th>
                <th width="80px">创建时间</th>
                <th width="120px">状态</th>
            </tr>
            </thead>
            <tbody id="projectListTable">
            <script id="projectList" type="text/html">
                {{each projectList as project i}}
                <tr>
                    <td>
                        <input class="taskCheckBox" type="checkbox" workflowName="{{project.WORKFLOW_INSTANCE_NAME}}"
                               wiid="{{project.WORKFLOW_INSTANCE_ID}}">
                    </td>
                    <td class="tableRowNumber">{{project.ROWNUM_}}.</td>
                    <td{{if !project.FINISH}} style="color:#336699" {{/if}}>
                        <ul class="row-ul">
                            <li>
                                <b><a style="font-size: 12px" href="javascript:openProject('{{project.WORKFLOW_INSTANCE_ID}}',{{project.WORKFLOW_STATE}})" title="{{project.WORKFLOW_INSTANCE_NAME}}">{{project.WORKFLOW_INSTANCE_NAME}}</a></b>
                            </li>
                            <li>
                                {{if project.REMARK!=null&&project.REMARK!=""&&project.REMARK!=" "}}
                                <span title="{{project.REMARK}}">[{{project.REMARK}}]</span>
                                {{/if}}
                            </li>
                        </ul>
                    </td>
                    <td>
                        <ul class="row-ul">
                            <li>
                                {{project.WORKFLOW_NAME}}
                                <div style="overflow: hidden;float:left;margin-right:5px">
                                    <img title="流程图" src="${path_platform}/common/images/flow.gif"
                                         border="0" style="cursor:pointer"
                                         onclick="window.open('${path_platform}/showchart.action?wiid={{project.WORKFLOW_INSTANCE_ID}}','流程图','toolbar=no,scrollbars=yes, location=no,directories=no, menubar=no,resizable=yes,top=0,left=0,width='+(screen.availWidth-10)+',height='+(screen.availHeight-30));"/>
                                </div>
                            </li>
                            <li>
                                {{project.CREATE_USERNAME}}
                            </li>
                        </ul>
                    </td>
                    <td>
                        {{if !project.FINISH}} <span type="activity" wiid="{{project.WORKFLOW_INSTANCE_ID}}"></span>{{/if}}
                    </td>
                    <td>
                        <ul class="row-ul">
                            <li>
                                {{project.FINISH_TIME}}
                            </li>
                            <li>
                                {{project.FINISH_TIME_COUNT}}
                            </li>
                        </ul>
                    </td>
                    <td>
                        {{project.CREATE_TIME}}
                    </td>
                    <td>
                        {{if project.PRIORITY==2}}
                            <span class="label label-warning">
                               <i class="icon-bookmark-empty icon-white"></i> 紧急
                            </span>
                        {{/if}}
                        {{if project.PRIORITY==3}}
                            <span class="label label-important">
                               <i class="icon-bookmark icon-white"></i> 特急
                            </span>
                        {{/if}}
                        {{if project.WORKFLOW_STATE==3}}
                            <span class="label label-warning">
                               <i class="icon-lock icon-white"></i> 暂停
                            </span>
                        {{else if project.WORKFLOW_STATE==1}}
                        {{if project.Over}}
                            <span class="label label-important">
                               <i class="icon-tag icon-white"></i> 超期
                            </span>
                        {{else}}
                            <span class="label label-success">
                               <i class="icon-edit icon-white"></i> 办理中
                            </span>
                        {{/if}}
                        {{else if project.WORKFLOW_STATE==2}}
                            <span class="label label-default">
                               <i class="icon-ok-sign icon-white"></i> 办结
                            </span>
                        {{/if}}


                        {{if (project.COOPERATION_STATE*1.0)>0}}
                            <span class="label label-secondary">
                               <i class="icon-random icon-white"></i> 协办
                            </span>
                        {{/if}}
                        {{if (project.ISBACK*1.0)>0}}
                            <span class="label label-warning">
                               <i class="icon-reply icon-white"></i> 退回
                            </span>
                        {{/if}}
                    </td>

                </tr>
                {{/each}}
            </script>
            </tbody>
        </table>
    </div>

    <!-- 翻页 -->
    <div class="text-c pt-5" id="projectListPagination">
    <#include "task-page.ftl"/>
    </div>
</div>