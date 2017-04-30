<!-- 密码修改窗体 -->
<div id="passwordModal" class="modal hide fade panel NewWorkflowForms col-12 border-all" style="width:300px;padding-bottom: 20px;" tabindex="-1" role="dialog" aria-labelledby="passwordModalLabel" aria-hidden="true">
    <h4 class="advancedSearchLab"><i class="icon-screenshot icon-large"></i> 密码修改
        <a href="#" class="icon-remove close1" data-dismiss="modal" aria-hidden="true" value="取消" ></a>
    </h4>
    <form method="post" class="form form-horizontal" id="psdForm" action="${base}/password" >
        <div class="row cl">
            <div class="col-3 form-label">
                <s>*</s>旧密码
            </div>
            <div class="formControls col-9">
                <input type="password"  name="oldPassword" placeholder="请输入旧密码" class="input-text size-M" autocomplete="off">
            </div>
        </div>
        <div class="row cl">
            <div class="col-3 form-label">
                <s>*</s>新密码
            </div>
            <div class="formControls col-9">
                <input type="password"  name="newPassword" placeholder="请输入新密码" class="input-text size-M" autocomplete="off">
            </div>
        </div>
        <div class="row cl">
            <div class="col-3 form-label">
                <s>*</s>密码确认
            </div>
            <div class="formControls col-9">
                <input type="password"  name="confirmNewPassword" placeholder="请再次输入新密码" class="input-text size-M" autocomplete="off">
            </div>
        </div>
    </form>
    <div class="row cl" style="text-align:center;">
        <button id="btnSavePsd" class="btn btn-secondary size-M button-search ">
            <i class="icon-save icon-large"></i> 保 存
        </button>
    </div>
</div>
<script>
    $(function(){
        $('#btnSavePsd').click(function(){
            $('#psdForm').ajaxSubmit(function(data) {
                alert(data.msg);
                if(data.success!=null&&data.success){
                    $('#passwordModal').modal('hide')
                }
            });
        });
        $('#passwordModal').on('hidden.bs.modal', function (e) {
            $('#psdForm').resetForm();
        });
    });
</script>
<!--end 密码修改窗体-->