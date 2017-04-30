<!-- 高级搜索窗体 -->
<div id="advancedSearchModal" class="modal hide fade panel NewWorkflowForms col-12 border-all draggable" style="width:300px;" tabindex="-1" role="dialog" aria-labelledby="advancedSearchModal" aria-hidden="true">
    <h4 class="advancedSearchLab"><i class="icon-screenshot"></i> 高级搜索 <a href="#" class="icon-remove close1" data-dismiss="modal" aria-hidden="true" value="取消" ></a></h4>
    <!-- 高级搜索 -->
    <form action="" class="form-horizontal">
        <hidden id="curTabId"></hidden>
        <hidden id="curGroupId"></hidden>
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
        <div class="row cl pd-10" id="advancedSearch_TASK_STATE">
            <div class="col-2 form-label">
                状态
            </div>
            <div class="col-4">

                <select class="select" name="TASK_STATE">
                    <option value="">所有</option>
                    <option value="1">超期</option>
                </select>

            </div>
            <div class="col-2 form-label">
            </div>
            <div class="col-4">
            </div>
        </div>
        <div class="row cl pd-10" style="text-align:center;">
            <button id="show" class="btn btn-secondary size-M button-search ">
                <i class="icon-search icon-large"></i> 搜 索
            </button>
        </div>
    </form>
    <!--end 高级搜索 -->
</div>
<!--end 高级搜索窗体 -->