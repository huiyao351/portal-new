<div class="tabCon4 " id="taskListTab">
    <div class="workListTools">
        <!-- 表格操作菜单 -->
        <div class="complexTableTools">
            <button class="btn btn-success size-MINI" type="button" id="turnTaskBtn">
                <i class="icon-share-alt icon-large"></i> 转发
            </button>
            <button class="btn btn-success size-MINI" type="button" id="backTaskBtn">
                <i class="icon-reply icon-large"></i> 退回
            </button>
            <button class="btn btn-success size-MINI" type="button" id="postTaskBtn">
                <i class="icon-lock icon-large"></i> 暂停
            </button>

            <button class="btn btn-success size-MINI" type="button" id="unpostTaskBtn">
                <i class="icon-unlock icon-large"></i> 取消暂停
            </button>
            <button class="btn btn-success size-MINI" type="button" id="delTaskBtn">
                <i class="icon icon-trash icon-large"></i> 删除
            </button>

            <#if autoTurnTask?? && autoTurnTask=='true'>
                <button class="btn btn-success size-MINI" type="button" id="autoTurnTaskBtn" style="margin-left:50px;">
                    <i class="icon-share-alt icon-large"></i> 批量自动转发
                </button>
            </#if>
        </div>
        <!--end 表格操作菜单 -->

        <!-- 搜索 -->
        <div class="searchBar workSearch">
            <form class="form-search" method="post" action="">
                <span class="label label-important size-M" style="height:25px;line-height:25px;padding:3px 12px;font-size:14px;font-weight: 400;">
                    <input type="checkbox" class="" id="overtimeCheck" style="height:16px;width:16px;margin:0px;"> 超 期
                </span>
                <input type="text" id="searchKeyword" name="WORKFLOW_INSTANCE_NAME" placeholder="请输入项目名称" class="searchTxt " autocomplete="off">
                <button class="btn btn-primary size-M button-search ">
                    <i class="icon-search icon-large"></i> 搜 索
                </button>
                <button class="btn btn-secondary size-M advancedBtn">
                    <i class="icon-search icon-large"></i> 高级搜索
                </button>
            </form>
        </div>
        <!--end 搜索 -->
    </div>
    <!-- 表格部分 -->
    <div class="detailTableDiv">
        <table class="table table-border  table-striped table-hover">
            <thead>
            <tr>
                <th width="10px">
                    <input type="checkbox" class="ckbSelectAll" id="ckbSelectAllTaskList">
                </th>
                <th width="30px">序号</th>
                <th>任务描述</th>
                <th width="180px">流程</th>
                <th width="160px">节点名称</th>
                <th width="90px">节点状态</th>
                <th width="80px">接收时间</th>
                <th width="80px">节点时限</th>
                <th width="80px">最后时限</th>
                <th width="100px">流程状态</th>
            </tr>
            </thead>
            <tbody id="taskListTable">
            <script id="taskList" type="text/html">
                {{each taskList as task i}}
                <tr>
                    <td>
                        <input class="taskCheckBox" type="checkbox" taskId="{{task.ASSIGNMENT_ID}}"
                               wiid="{{task.WORKFLOW_INSTANCE_ID}}"
                               workflowName="{{task.WORKFLOW_INSTANCE_NAME}}" workflowState="{{task.WORKFLOW_STATE}}" {{if task.CHECKED==true}}checked{{/if}} >
                    </td>
                    <td class="tableRowNumber">{{task.ROWNUM_}}.</td>
                    <td>
                        <ul class="row-ul">
                            <li>
                                <b><a style="font-size: 12px;" href="javascript:openTask('{{task.ASSIGNMENT_ID}}',{{task.WORKFLOW_STATE}})" title="{{task.WORKFLOW_INSTANCE_NAME}}">{{task.WORKFLOW_INSTANCE_NAME}}</a></b>
                            </li>
                            <li>
                                {{if task.REMARK!=null&&task.REMARK!=""&&task.REMARK!=" "}}
                                <span title="{{task.REMARK}}">[{{task.REMARK}}]</span>
                                    <#--<span title="{{task.REMARK}}">
                                    {{if task.REMARK.length > 30}}
                                        [{{task.REMARK.substring(0,30)+"..."}}]
                                    {{else}}
                                        [{{task.REMARK}}]
                                    {{/if}}
                                    </span>-->
                                {{/if}}
                            </li>
                        </ul>
                    </td>
                    <td>
                        <ul class="row-ul">
                            <li>
                                {{task.WORKFLOW_NAME}}
                                <div style="overflow: hidden;float:left;margin-right:5px">
                                    <img title="流程图" src="${path_platform}/common/images/flow.gif"
                                         border="0" style="cursor:pointer"
                                         onclick="window.open('${path_platform}/showchart.action?taskid={{task.ASSIGNMENT_ID}}','流程图','toolbar=no,scrollbars=yes, location=no,directories=no, menubar=no,resizable=yes,top=0,left=0,width='+(screen.availWidth-10)+',height='+(screen.availHeight-30));"/>
                                </div>
                            </li>
                            <li>
                                <icon class="icon-user"></icon>{{task.CREATE_USERNAME}}
                            </li>
                        </ul>
                    </td>
                    <td>
                        <ul class="row-ul">
                            <li>
                                {{task.ACTIVITY_NAME}}
                            </li>
                            <li type="turn" taskid="{{task.ASSIGNMENT_ID}}">
                            </li>
                        </ul>
                    </td>
                    <td>
                        {{if task.TIME_STATE==2}}
                        <i class="icon-lightbulb icon-red icon-large" title="超期"></i>
                        {{else if task.TIME_STATE==1}}
                        <i class="icon-lightbulb icon-yellow-warn icon-large" title="警告"></i>
                        {{/if}}
                        {{task.OTHER_TIME}}
                    </td>
                    <td>
                        {{task.CREATE_TIME}}
                    </td>
                    <td>
                        {{task.TASK_OVER_TIME}}
                    </td>
                    <td>{{task.OVER_TIME}}</td>
                    <td>
                        {{if task.PRIORITY==2}}
                        <i class="icon-bookmark-empty icon-red icon-large"></i>紧急
                        {{/if}}
                        {{if task.PRIORITY==3}}
                        <i class="icon-bookmark icon-red icon-large"></i>特急
                        {{/if}}

                        {{if task.WORKFLOW_STATE==3}}
                        <i class="icon-lock icon-yellow-warn icon-large"></i>暂停
                        {{else if task.WORKFLOW_STATE==1}}
                            {{if task.Over}}
                            <i class="icon-lightbulb icon-red icon-large" title="超期"></i>超期
                            {{else}}
                            <i class="icon-edit icon-green-ok icon-large" title="办理中"></i>办理中
                            {{/if}}
                        {{/if}}

                        {{if (task.COOPERATION_STATE*1.0)>0}}
                        <i class="icon-ok-sign icon-green-ok icon-large"></i>办结
                        {{/if}}
                        {{if (task.ISBACK*1.0)>0}}
                        <i class="icon-reply icon-yellow-warn icon-large" title="退回"></i>退回
                        {{/if}}
                    </td>

                </tr>
                {{/each}}
            </script>
            </tbody>
        </table>
    </div>


    <!-- 翻页 -->
    <div class="text-c pt-5" id="taskListPagination">
    <#include "task-page.ftl"/>
    </div>
    <!--end 翻页 -->
</div>