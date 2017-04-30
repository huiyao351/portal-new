<!-- 任务暂停窗体 -->
<div id="postTaskModal" class="modal hide fade panel NewWorkflowForms col-12 border-all" style="width:300px;padding-bottom: 20px;"
     tabindex="-1" role="dialog" aria-labelledby="postTaskModalLabel" aria-hidden="true">
    <div class="row cl advancedSearchLab">
        <div class="formControls col-xs-8 col-sm-8">
            <h4><i class="icon icon-white icon-edit iconPosition icon-large"></i><strong>任务暂停</strong></h4>
        </div>
        <div class="formControls col-xs-8 col-sm-2">
            <input class="btn btn-primary postBtn" type="button" id="btnSaveSignPsd" value="暂停">
        </div>
        <div class="formControls col-xs-8 col-sm-2">
            <input id="btnCancelSignPsd" class="btn btn-default" type="button" data-dismiss="modal" aria-hidden="true" value="取消">
        </div>
    </div>
    <form method="post" class="form form-horizontal" id="signPsdForm" action="${base}/signPassword" >
        <div class="row cl">
            <label class="form-label col-sm-2">暂停原因：</label>
            <div class="formControls col-sm-10">
                <textarea name="remark" class="span10" rows="5"> </textarea>
            </div>
        </div>
    </form>
</div>
<!--end 任务暂停窗体 -->