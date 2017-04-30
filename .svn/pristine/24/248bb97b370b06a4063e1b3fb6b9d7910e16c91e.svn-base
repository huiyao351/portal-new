<!-- 密码修改窗体 -->
<div id="signPasswordModal" class="modal hide fade panel NewWorkflowForms col-12 border-all" style="width:300px;padding-bottom: 20px;" tabindex="-1" role="dialog" aria-labelledby="signPasswordModalLabel" aria-hidden="true">
    <h4 class="advancedSearchLab"><i class="icon-screenshot icon-large"></i> 签名密码修改
        <a href="#" class="icon-remove close1" data-dismiss="modal" aria-hidden="true" value="取消" ></a>
    </h4>
    <form method="post" class="form form-horizontal" id="signPsdForm" action="${base}/signPassword" >
        <div class="row cl">
            <div class="col-3 form-label">
                <s>*</s>旧签名密码
            </div>
            <div class="formControls col-9">
                <input type="password"  name="oldSignPassword" placeholder="请输入旧签名密码" class="input-text size-M" autocomplete="off">
            </div>
        </div>
        <div class="row cl">
            <div class="col-3 form-label">
                <s>*</s>新签名密码
            </div>
            <div class="formControls col-9">
                <input type="password"  name="newSignPassword" placeholder="请输入新签名密码" class="input-text size-M" autocomplete="off">
            </div>
        </div>
        <div class="row cl">
            <div class="col-3 form-label">
                <s>*</s>签名密码确认
            </div>
            <div class="formControls col-9">
                <input type="password"  name="confirmNewSignPassword" placeholder="请再次输入新签名密码" class="input-text size-M" autocomplete="off">
            </div>
        </div>
    </form>
    <div class="row cl" style="text-align:center;">
        <button id="btnSaveSignPsd" class="btn btn-secondary size-M button-search ">
            <i class="icon-save icon-large"></i> 保 存
        </button>
    </div>
</div>
<!--end 密码修改窗体-->
<script>
    $(function(){
        $('#btnSaveSignPsd').click(function(){
            $('#signPsdForm').ajaxSubmit(function(data) {
                alert(data.msg);
                if(data.success!=null&&data.success){
                    $('#signPasswordModal').modal('hide')
                }
            });
        });

        $('#signPasswordModal').on('hidden.bs.modal', function (e) {
            $('#signPsdForm').resetForm();
        });
    });
</script>