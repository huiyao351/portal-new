<h4 class="titleLab">
    <i class="icon-info-sign"></i> 工作流属性
</h4>
<div>
    <form method="post" class="row cl form-horizontal" id="wfdForm">
        <input type="hidden" id="workflowDefinitionId" name="workflowDefinitionId">
        <input type="hidden" id="treeTId" name="treeTId">

        <div class="row cl form-element">
            <div class="col-2 form-label">
                名称
            </div>
            <div class="col-4 form-input">
                <input name="workflowName" id="workflowName" type="text" class="input-text size-M" >
            </div>
            <div class="col-2 form-label">
                业务类型
            </div>
            <div class="col-4 form-input">
                <input id="businessName" name="resourceName" type="text" class="input-text size-M" disabled="true">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                编号
            </div>
            <div class="col-4 form-input">
                <input id="workflowDefinitionNo" name="workflowDefinitionNo" type="text" class="input-text size-M">
            </div>
            <div class="col-2 form-label">
                版本
            </div>
            <div class="col-4 form-input">
                <input id="workflowVersion" name="workflowVersion" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                地区码
            </div>
            <div class="col-4 form-input">
                <input id="regionCode" name="regionCode" type="text" class="input-text size-M">
            </div>
            <div class="col-2 form-label">
                办理时限
            </div>
            <div class="col-4 form-input">
                <input id="timeLimit" name="timeLimit" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                定义编码
            </div>
            <div class="col-4 form-input">
                <input id="workflowCode" name="workflowCode" type="text" class="input-text size-M">
            </div>
            <div class="col-2 form-label">
                优先级
            </div>
            <div class="col-4 form-input">
                <select class="select" id="priority" name="priority">
                <#list priorityList as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                是否可用
            </div>
            <div class="col-4 form-input">
                <select class="select" name="isValid" id="isValid">
                <#list boolListNumber as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
            <div class="col-2 form-label">
                是否监控
            </div>
            <div class="col-4 form-input">
                <select class="select" name="isMonitor" id="isMonitor">
                <#list boolListNumber as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                新建任务URL
            </div>
            <div class="col-10 form-input">
                <input id="createUrl" name="createUrl" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                窗体高度
            </div>
            <div class="col-4 form-input">
                <input id="createHeight" name="createHeight" type="text" class="input-text size-M">
            </div>
            <div class="col-2 form-label">
                窗体宽度
            </div>
            <div class="col-4 form-input">
                <input id="createWidth" name="createWidth" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                备注
            </div>
            <div class="col-10 form-textarea">
                <textarea id="remark" name="remark" style="height:40px;" ></textarea>
            </div>
        </div>
        <div class="row cl " style="text-align:center;">
            <button class="btn btn-secondary size-M" type="button" id="wfdBtn">
                <i class="icon-save icon-large"></i> 保存
            </button>
        </div>
    </form>
</div>