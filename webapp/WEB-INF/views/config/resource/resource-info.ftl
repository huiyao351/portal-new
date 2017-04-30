<div id="resouceInfo">
    <form method="post" class="row cl form-horizontal" id="resourceForm">
        <input type="hidden" id="resourceId" name="resourceId">
        <input type="hidden" id="groupId" name="groupId">
        <input type="hidden" id="businessId" name="businessId">

        <input type="hidden" id="treeTId" name="treeTId">

        <div class="row cl form-element">
            <div class="col-2 form-label">
                资源名称
            </div>
            <div class="col-10 form-input">
                <input name="resourceName" id="resourceName" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                所属业务
            </div>
            <div class="col-10 form-input">
                <input id="businessName" type="text" class="input-text size-M" disabled="true">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                资源编码
            </div>
            <div class="col-10 form-input">
                <input name="resourceCode" id="resourceCode" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                资源编号
            </div>
            <div class="col-10 form-input">
                <input name="resourceNo" id="resourceNo" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                资源类型
            </div>
            <div class="col-10 form-input">
                <select class="select" name="resourceType" id="resourceType">
                <#list zylxList as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                加载模式
            </div>
            <div class="col-10 form-input">
                <select class="select" name="loadMode" id="loadMode">
                <#list zyjzmsList as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                资源地址
            </div>
            <div class="col-10 form-input">
                <input name="resourceUrl" id="resourceUrl" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl pd-10" style="text-align:center;">
            <button class="btn btn-secondary size-M" type="button" id="resourceBtn">
                <i class="icon-save icon-large"></i> 保存
            </button>
        </div>
    </form>
</div>
<div id="groupInfo" style="display: none;">
    <form method="post" class="row cl form-horizontal" id="groupForm">
        <input type="hidden" id="g_groupId" name="groupId">
        <input type="hidden" id="g_businessId" name="businessId">

        <input type="hidden" id="g_treeTId" name="g_treeTId">

        <div class="row cl form-element">
            <div class="col-2 form-label">
                分组名称
            </div>
            <div class="col-10 form-input">
                <input name="groupName" id="g_groupName" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                所属业务
            </div>
            <div class="col-10 form-input">
                <input id="g_businessName" type="text" class="input-text size-M" disabled="true">
            </div>
        </div>
        <div class="row cl pd-10" style="text-align:center;">
            <button class="btn btn-secondary size-M" type="button" id="groupBtn">
                <i class="icon-save icon-large"></i> 保存
            </button>
        </div>
    </form>
</div>