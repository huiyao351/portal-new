<!-- 进度条窗体 -->
<div id="progressModal" class="modal hide fade panel" tabindex="-1" role="dialog"
     aria-labelledby="progressModalLabel" aria-hidden="true" style="background:#eee;">
    <h4 class="advancedSearchLab label-success"><i class="icon-spinner"></i>操作处理中</h4>
    <div class="panel-header modal-header panelBg" style="padding: 20px;margin:0px;">
        <h4 id="progressModalLabel" class="panelLabel pull-left " style="margin:0px;">
            <i class="icon-spinner icon-spin icon-2x pull-left"></i>任务处理中
        </h4>
    </div>
    <div class="modal-body">
        <div style="width: 540px">
            <form class="form-base" id="postTaskForm">
                <div class="row-fluid">
                    <div class="progress progress-striped active">
                        <div class="bar" style="width: 100%;"></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<!--end 进度条窗体 -->