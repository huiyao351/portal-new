<div class="tabCon4" id="taskOverListTab">
    <div class="workListTools" style="height:31px;">
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
        </div>
    <!--end 搜索 -->
    </div>

    <!-- 表格部分 -->
    <div class="detailTableDiv">
        <table class="table table-border  table-striped table-hover">
            <thead>
            <tr>
                <th width="10px">&nbsp;&nbsp;&nbsp;
                </th>
                <th width="30px">序号</th>
                <th>任务描述</th>
                <th width="180px">流程</th>
                <th width="150px">节点名称</th>
                <th width="100px">任务完成时间</th>
                <th width="100px">任务截止时间</th>
                <th width="100px">状态</th>
            </tr>
            </thead>
            <tbody id="taskOverListTable">
            <script id="taskOverList" type="text/html">
                {{each taskOverList as taskOver i}}
                <tr>
                    <td>
                        &nbsp;&nbsp;<#--<input class="taskCheckBox" type="checkbox">-->
                    </td>
                    <td class="tableRowNumber">{{taskOver.ROWNUM_}}.</td>
                    <td>

                        <ul class="row-ul">
                            <li>
                                <b><a style="font-size: 12px;" href="javascript:openTaskOver('{{taskOver.ASSIGNMENT_ID}}')" title="{{taskOver.WORKFLOW_INSTANCE_NAME}}">{{taskOver.WORKFLOW_INSTANCE_NAME}}</a></b>
                            </li>
                            <li>
                                {{if taskOver.REMARK!=null&&taskOver.REMARK!=""&&taskOver.REMARK!=" "}}
                                <span title="{{taskOver.REMARK}}">[{{taskOver.REMARK}}]</span>
                                {{/if}}
                            </li>
                        </ul>
                    </td>
                    <td>
                        <ul class="row-ul">
                            <li>
                                {{taskOver.WORKFLOW_NAME}}
                                <div style="overflow: hidden;float:left;margin-right:5px">
                                    <img title="流程图" src="${path_platform}/common/images/flow.gif"
                                         border="0" style="cursor:pointer"
                                         onclick="window.open('${path_platform}/showchart.action?wiid={{taskOver.WORKFLOW_INSTANCE_ID}}','流程图','toolbar=no,scrollbars=yes, location=no,directories=no, menubar=no,resizable=yes,top=0,left=0,width='+(screen.availWidth-10)+',height='+(screen.availHeight-30));"/>
                                </div>
                            </li>
                            <li type="turn" taskid="{{taskOver.ASSIGNMENT_ID}}">
                            </li>
                        </ul>
                    </td>
                    <td>
                        {{taskOver.ACTIVITY_NAME}}
                    </td>
                    <td>
                        {{taskOver.FINISH_TIME}}
                    </td>
                    <td>
                        {{taskOver.TASK_OVER_TIME}}
                    </td>
                    <td>
                        {{if taskOver.PRIORITY==2}}
                        <i class="icon-bookmark-empty icon-red icon-large"></i>紧急
                        {{/if}}
                        {{if taskOver.PRIORITY==3}}
                        <i class="icon-bookmark icon-red icon-large"></i>特急
                        {{/if}}

                        {{if taskOver.WORKFLOW_STATE==3}}
                        <i class="icon-lock icon-yellow-warn icon-large"></i>暂停
                        <#--{{else if taskOver.WORKFLOW_STATE==1}}
                            {{if taskOver.Over}}
                            <i class="icon-lightbulb icon-red icon-large" title="超期"></i>超期
                            {{else}}
                            <i class="icon-edit icon-green-ok icon-large" title="办理中"></i>办理中
                            {{/if}}-->
                        {{else if taskOver.WORKFLOW_STATE==2}}
                        <i class="icon-ok-sign icon-green-ok icon-large"></i>办结
                        {{/if}}

                        {{if (taskOver.COOPERATION_STATE*1.0)>0}}
                        <i class="icon-random icon-blue-ok icon-large"></i>协办
                        {{/if}}
                        {{if (taskOver.ISBACK*1.0)>0}}
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
    <div class="text-c pt-5" id="taskOverListPagination">
    <#include "task-page.ftl"/>
    </div>
</div>