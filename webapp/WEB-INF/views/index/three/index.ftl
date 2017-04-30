<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#include "../common/index-import.ftl"/>
    <script src="${base}/static/js/index.js"></script>
    <script type="text/javascript">
        var leftMenuVisible =${ctx.getEnv('menu.left.visible')};
        var subMenuType = '${sub.subMenuType!}';
	</script>
    <style type="text/css">
    </style>
</head>
<body class="greenStyle">
	<#include "../../task/task-create-form.ftl"/>
    <#include "../common/index-header.ftl"/>
	<!-- 左侧菜单 -->
    <!-- 竖向二级菜单 -->
    <div class="aColumn" style="display:none;" id="leftFirstMenu">
        <ul>
        </ul>
    </div>
	<!-- 展出菜单 横向 三级、四级菜单树-->
	<div class="Hui-aside munePosition1 leftMuneList02" id="leftMenuAsi" style="display:none;">
		<div class="leftMenu">
			<ul class="Menu01" id="secondMenuTree">
			</ul>
		</div>
	</div>
    <div class="dislpayArrow arrowPosition1" style="display:none;" ><a id="displaynavbar" class="pngfix" href="javascript:void(0);" onclick="displayLeftNavbar(this)"></a></div>
	<section class="Hui-article mainContent" id="mainContent" style="border:none;overflow:hidden;">
        <!-- 面包屑 -->
        <nav class="breadcrumb" id="breadcrumb" style="display:none;">
        </nav>
        <!--end 面包屑 -->
        <!--end 展出菜单 -->
        <iframe id="mainFrame" src="" width=100% height=100% class="iframe1" style="border:none;overflow:hidden;"
                frameborder="0" scrolling="auto" marginwidth="0" marginheight="0">
	</section>
	<!--end  主要内容区域   -->

</body>
</html>
