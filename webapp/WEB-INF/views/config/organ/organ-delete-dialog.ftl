<#--删除选择弹出框-->
<div id="delUserMenu" class="modal hide fade" role="dialog" aria-labelledby="delUserMenuLabel" aria-hidden="true">
    <h4 id="roletitle" class="titleLab">
    <#--   删除部门-->
    </h4>
    <div class="row cl form-element">
        <div class="col-12 form-input"  id="delmark" style="padding-left:20px;" >
        <#-- <input name="delmark"   type="radio" value="1"/>仅删除部门，保留所有包含的用户-->
        </div>
    </div>
    <div class="row cl form-element">
        <div class="col-12 form-input"  id="delmark2" style="padding-left:20px;" >
        <#-- <input name="delmark"  type="radio" value="2" />删除部门，并且删除所有包含的用户所有角色定义中该部门所包含的用户也将一并删除-->
        </div>
    </div>
    <div class="row cl pd-10" style="text-align:center;">
        <div class="col-6 form-input">
            <button class="btn btn-secondary size-M btn-primary" onclick="deleteinfo();" type="button" id="subinfo">
                <i class="icon-save icon-large"></i> 确定
            </button>
        </div>
        <div class="col-6 form-input">
            <button type="button" class="btn btn-inverse size-M model-close" data-dismiss="modal">
                <i class="icon-remove icon-large"></i>取消
            </button>
        </div>
    </div>
</div>