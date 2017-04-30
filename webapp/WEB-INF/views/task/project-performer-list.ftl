<div class="tabCon4" id="projectPerformerListTab">
    <div class="workListTools">
        <!-- 表格操作菜单 -->
        <div class="complexTableTools">
        <#if hasRestart?? && hasRestart==true>
            <button class="btn btn-success size-MINI restartProjectBtn" type="button" id="restartProjectBtn">
                <i class="icon-refresh"></i> 重办
            </button>
        </#if>
        <#if hasDel?? && hasDel==true>
            <button class="btn btn-success size-MINI delProjectBtn" type="button" id="delProjectBtn">
                <i class="icon icon-trash"></i> 删除
            </button>
        </#if>
        </div>
        <!--end 表格操作菜单 -->

        <!-- 搜索 -->
        <div class="searchBar workSearch">
            <form class="form-search" method="post" action="">
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
                    <input type="checkbox" class="ckbSelectAll" id="ckbSelectAllProList">
                </th>
                <th width="30px">序号</th>
                <th>项目描述</th>
                <th width="150px">流程</th>
                <th width="150px">节点名称</th>
                <th width="150px">结束时间</th>
                <th width="80px">创建时间</th>
                <th width="100px">状态</th>
            </tr>
            </thead>
            <tbody id="projectPerformerListTable">
            <script id="projectPerformerList" type="text/html">
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
                        <i class="icon-bookmark-empty icon-red icon-large"></i>紧急
                        {{/if}}
                        {{if project.PRIORITY==3}}
                        <i class="icon-bookmark icon-red icon-large"></i>特急
                        {{/if}}
                        {{if project.WORKFLOW_STATE==3}}
                        <i class="icon-lock icon-yellow-warn icon-large"></i>暂停
                        {{else if project.WORKFLOW_STATE==1}}
                        {{if project.Over}}
                        <i class="icon-lightbulb icon-red icon-large" title="超期"></i>超期
                        {{else}}
                        <i class="icon-edit icon-green-ok icon-large" title="办理中"></i>办理中
                        {{/if}}
                        {{else if project.WORKFLOW_STATE==2}}
                        <i class="icon-ok-sign icon-green-ok icon-large"></i>办结
                        {{/if}}

                        {{if (project.COOPERATION_STATE*1.0)>0}}
                        <i class="icon-random icon-blue-ok icon-large"></i>协办
                        {{/if}}
                        {{if (project.ISBACK*1.0)>0}}
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
    <div class="text-c pt-5" id="projectPerformerListPagination">
    <#include "task-page.ftl"/>
    </div>
</div>