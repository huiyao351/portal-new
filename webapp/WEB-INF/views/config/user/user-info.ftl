<div id="userInfoDiv"  >
    <form method="post" class="row cl form-horizontal" id="userForm">
        <input type="hidden" id="userId" name="userId">
        <input type="hidden" id="organId2" name="organId">
        <input type="hidden" id="treeTId2">
        <div class="row cl form-element" style="padding-top: 0px;">
            <div class="col-2 form-label">
                用户名
            </div>
            <div class="col-4 form-input">
                <input name="userName" id="userName" type="text" class="input-text size-M" >
            </div>
            <div class="col-2 form-label">
                用户编号
            </div>
            <div class="col-4 form-input">
                <input name="userNo" id="userNo" type="text" class="input-text size-M" onblur="ztree_check_integer(this,'用户编号')"  >
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                性别
            </div>
            <div class="col-4 form-input"">
                <select id="userSex" name="userSex"  class="select">
                <#list sexList as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
            <div class="col-2 form-label">
                出生日期
            </div>
            <div class="col-4 form-input">
                <input class="input-text size-M Wdate" name="birthdate" id="birthdate" type="text" onfocus="WdatePicker({firstDayOfWeek:1,skin:'twoer'})">
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                手机
            </div>
            <div class="col-4 form-input">
                <input name="mobilePhone" id="mobilePhone" type="text" class="input-text size-M"  onblur="ztree_check_integer(this,'移动电话')" >
            </div>

            <div class="col-2 form-label">
                办公电话
            </div>
            <div class="col-4 form-input">
                <input name="officePhone" id="officePhone" type="text" class="input-text size-M" onblur="ztree_check_integer(this,'办公电话')" >
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                职称
            </div>
            <div class="col-4 form-input">
                <input name="userRank" id="userRank" type="text" class="input-text size-M" >
            </div>
            <div class="col-2 form-label">
                职位
            </div>
            <div class="col-4 form-input">
                <input name="userPost" id="userPost" type="text" class="input-text size-M" >
            </div>
        </div>

        <div class="row cl form-element">
            <div class="col-2 form-label">
                家庭电话
            </div>
            <div class="col-4 form-input">
                <input name="homePhone" id="homePhone" type="text" class="input-text size-M"  onblur="ztree_check_integer(this,'家庭电话')">
            </div>
            <div class="col-2 form-label">
                邮箱
            </div>
            <div class="col-4 form-input">
                <input name="email" id="email" type="text" class="input-text size-M" >
            </div>
        </div>

        <div class="row cl form-element">
            <div class="col-2 form-label">
                学历
            </div>
            <div class="col-4 form-input"">
                <select id="userDegree" name="userDegree"  class="select">
                <#list degreeList as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
            <div class="col-2 form-label">
                是否可用
            </div>
            <div class="col-4 form-input"">
                <select id="isValid" name="isValid"  class="select">
                <#list boolListNumber as item>
                    <option value="${item.value}">${item.name}</option>
                </#list>
                </select>
            </div>
        </div>

        <div class="row cl form-element">
            <div class="col-2 form-label">
                家庭地址
            </div>
            <div class="col-10 form-input">
                <input name="address" id="address" type="text" class="input-text size-M" >
            </div>
        </div>

        <div class="row cl form-element" style="padding-bottom: 0px;">
            <div class="col-2 form-label">
                备注
            </div>
            <div class="col-10 form-textarea">
                <textarea id="remark2" name="remark" style="height:54px;" ></textarea>
            </div>
        </div>

        <div class="row cl form-element" style="padding-top: 6px;">
            <div class="col-3 form-label">
                <button class="btn btn-secondary size-M btn-primary" type="button" id="userBtn">
                    <i class="icon-save icon-large"></i> 提交
                </button>
            </div>
            <div class="col-9 cl" id="userLoginInfoButton"  >
                <div class="col-4 form-label">
                    <button class="btn size-M" type="button" data-toggle="modal" id="photoBtn" >
                        <i class="icon-picture icon-large"></i> 照片
                    </button>
                </div>
                <div class="col-4 form-label">
                    <button class="btn size-M" type="button" data-toggle="modal" id="signBtn" >
                        <i class="icon-picture icon-large"></i> 签名
                    </button>
                </div>
                <div class="col-4 form-label">
                    <button class="btn  size-M " type="button" data-toggle="modal" id="loginBtn">
                        <i class="icon-info-sign icon-large"></i> 登陆信息
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>
<#include "../user/user-info-login.ftl"/>
<script type="text/javascript">
</script>