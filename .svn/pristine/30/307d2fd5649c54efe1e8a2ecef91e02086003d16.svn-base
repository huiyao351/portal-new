<!-- 新建任务窗体 -->
<div id="taskModal" class="modal hide fade panel NewWorkflowForms col-12  border-all" style="width:300px;" tabindex="-1" role="dialog" aria-labelledby="taskModalLabel" aria-hidden="true">
  <h4 class="advancedSearchLab label-success"><i class="icon-file-alt"></i> 新建任务  <a href="#" class="icon-remove close1" data-dismiss="modal" aria-hidden="true" value="取消" ></a></h4>
    <form method="post" class="form form-horizontal pd-10" id="createTaskForm">
        <div class="row cl">
            <label class="form-label col-2"><s>*</s>标题：</label>
            <div class="formControls col-10">
                <input type="text" id="instanceName" name="workflowIntanceName" class="input-text size-M" autocomplete="off" placeholder="请输入标题">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">业务类别：</label>
            <div class="formControls col-4">
                <input type="text" id="businessName" name="businessName" class="input-text size-M" autocomplete="off" readonly>
            </div>
            <label class="form-label col-2 ">流程名称：</label>
            <div class="formControls col-4 ">
                <input type="text" id="workFlowName" name="workFlowName" class="input-text size-M" autocomplete="off" readonly>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label  col-2">优先级：</label>
            <div class="formControls  col-4">
                <select name="priority" id="proselect" class="select">
                    <option value="1">普通</option>
                    <option value="2">紧急</option>
                    <option value="3">特急</option>
                </select>
            </div>
            <label class="form-label  col-2">总期限：</label>
            <div class="formControls  col-4">
                <input type="text" id="timeLimit" name="timeLimit" class="input-text size-M" autocomplete="off">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label  col-2">接收日期：</label>
            <div class="formControls  col-4">
                <input type="text" id="createTime" name="createTime1" class="input-text size-M" autocomplete="off">
            </div>
            <label class="form-label  col-2">接收人：</label>
            <div class="formControls  col-4">
                <input type="text" id="userName" name="userName" class="input-text size-M" autocomplete="off">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label  col-2">创建部门：</label>
            <div class="formControls  col-4">
                <select id="regionCode" name="regionCode" class="select">
                <#if organList?has_content>
                    <#list organList as organ>
                        <option value="${organ.regionCode!}">${organ.organName!}</option>
                    </#list>
                </#if>
                </select>
            </div>
            <label class="form-label  col-2">所属地区：</label>
            <div class="formControls  col-4">
                <select id="district" name="district" class="select">
                <#if districtList?has_content>
                    <#list districtList as district>
                        <option value="${district.districtCode!}" <#if district.districtCode??&&district.districtCode==regionCode>selected="selected" </#if>>${district.districtName!}</option>
                    </#list>
                </#if>
                </select>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label  col-2">备注：</label>
            <div class="formControls  col-10">
                <textarea name="remark" id="remark"  class="textarea" autocomplete="off"> </textarea>
            </div>
        </div>
		
        <div class="row cl pd-10" style="text-align:center;">
            <button class="btn btn-success size-M" type="button" id="createTaskBtn">
                <i class="icon-save icon-large"></i> 创建
            </button>
        </div>
		
        <div style="display: none">
            <input type="text" id="wdid" name="workflowDefinitionId">
        </div>
    </form>
</div>
<!--end 新建任务窗体 -->