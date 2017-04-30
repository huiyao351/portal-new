<header class="header border-bottom">
    <nav class="mainnav Hui-wraper">
        <h1 class="LOGO">
        ${ctx.getEnv('portal.title')}
        </h1>

        <!-- 导航栏 -->
        <ul class="cl ToCenter" id="sysNavMenu">
            <!-- 系统模块 -->
            <#--<li class="navBorder current border-top"><a class="" menuIndex="1" menuLink="/portal/index">综合资讯</a></li>
            <li class="navBorder  border-top">
                <a class="dropDown_A" menuIndex="2" href="#" menuLink="/portal/index/platform">我的办公环境</a>
            </li>
            <li class="navBorder border-top"><a class="" menuIndex="3" menuLink="http://192.168.90.7:8088/omp/map/yzt_ys">一张图</a></li>
            <li class="navBorder border-top">
                <a class="dropDown_A" menuIndex="4" href="#" menuLink="/portal/index/zhjg?systemId=zhjg">综合监管</a>
            </li>
            <li class="navBorder border-top"><a class="dropDown_A" menuIndex="5" menuLink="/portal/index/zhzx">不动产</a></li>-->
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