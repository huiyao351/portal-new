<div id="organInfoDiv"  >
    <form method="post" class="row cl form-horizontal" id="organForm">
        <input type="hidden" id="organId" name="organId">
        <input type="hidden" id="treeTId" name="treeTId">
        <input type="hidden" id="superOrganId" name="superOrganId">
        <div class="row cl form-element">
            <div class="col-3 form-label">
                部门名称
            </div>
            <div class="col-9 form-input">
                <input name="organName" id="organName" type="text" class="input-text size-M" >
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                办公室电话
            </div>
            <div class="col-9 form-input">
                <input name="officeTel" id="officeTel" type="text" class="input-text size-M" onblur="ztree_check_integer(this,'办公室电话')" >
            </div>
        </div>

        <div class="row cl form-element"  >
            <div class="col-3 form-label">
                行政区名称
            </div>
            <div class="col-9 form-input">
                <select id="regionCode" name="regionCode"  class="select"  >
                </select>
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                邮箱
            </div>
            <div class="col-9 form-input">
                <input name="email" id="email" type="text" class="input-text size-M">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                部门编号
            </div>
            <div class="col-9 form-input">
                <input name="organNo" id="organNo" type="text" class="input-text size-M" onblur="ztree_check_integer(this,'部门编号')" >
            </div>
        </div>

        <div class="row cl form-element">
            <div class="col-3 form-label">
                创建时间
            </div>
            <div class="col-9 form-input">
                <input class="input-text size-M Wdate" name="createDate" id="createDate" type="text" onfocus="WdatePicker({firstDayOfWeek:1,skin:'twoer'})">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                备注
            </div>
            <div class="col-9 form-textarea">
                <textarea id="remark2" name="remark" style="height:80px;" ></textarea>
            </div>
        </div>
        <div class="row cl pd-10" style="text-align:center;">
            <button class="btn btn-secondary size-M btn-primary" type="button" id="organBtn">
                <i class="icon-save icon-large"></i> 提交
            </button>

        </div>

    </form>
</div>