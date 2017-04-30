<div>
    <form method="post" class="row cl form-horizontal" id="stuffForm">
        <input type="hidden" id="stuffId" name="stuffId">
        <input type="hidden" id="workflowDefinitionId" name="workflowDefinitionId">
        <input type="hidden" id="treeTId" name="treeTId">
        <input type="hidden" id="proId" name="proId">

        <div class="row cl form-element">
            <div class="col-3 form-label">
                序号
            </div>
            <div class="col-9 form-input">
                <input name="stuffXh" id="stuffXh" type="text" class="input-text size-M" onblur="f_check_integer(this,'序号')" >
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                名称
            </div>
            <div class="col-9 form-input">
                <input name="stuffName" id="stuffName" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                介质
            </div>
            <div class="col-9 form-input">
                <select class="select" name="meterial" id="meterial">
                <#list meterialList as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                附件总数
            </div>
            <div class="col-9 form-input">
                <input id="stuffCount" name="stuffCount" type="text" class="input-text size-M" onblur="f_check_integer(this,'序号')">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                已收份数
            </div>
            <div class="col-9 form-input">
                <input id="ysnum" name="ysnum" type="text" class="input-text size-M" onblur="f_check_integer(this,'序号')">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                待补份数
            </div>
            <div class="col-9 form-input">
                <input id="dbnum" name="dbnum" type="text" class="input-text size-M" onblur="f_check_integer(this,'序号')">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                备注
            </div>
            <div class="col-9 form-textarea">
                <textarea id="remark" name="remark" style="height:80px;" ></textarea>
            </div>
        </div>
        <div class="row cl pd-10" style="text-align:center;">
            <button class="btn btn-secondary size-M" type="button" id="stuffBtn">
                <i class="icon-save icon-large"></i> 保存
            </button>
        </div>
    </form>
</div>