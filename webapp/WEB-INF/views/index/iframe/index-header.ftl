<header class="header border-bottom">
    <nav class="mainnav Hui-wraper">
        <h1 class="LOGO">
        ${ctx.getEnv('portal.title')}
        </h1>

        <!-- 导航栏 -->
        <ul class="cl ToCenter" id="sysNavMenu">
            <!-- 系统模块 -->
        </ul>
        <!--end 导航栏 -->
        <!-- 工具栏 -->
        <#include "../common/home-header.ftl">
        <!--end 工具栏 -->
    </nav>
</header>
<script>
    $(function(){
        initSubMenu(subsystemId);
    });
</script>
<#include "../../common/password.ftl">
<#if ctx.getBooleanEnv('signUserCheck.enable')>
    <#include "../../common/sign-password.ftl">
</#if>