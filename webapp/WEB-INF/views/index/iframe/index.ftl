<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#include "../common/index-import.ftl"/>

    <script type="text/javascript">
	</script>
    <style type="text/css">
    </style>
</head>
<body class="greenStyle">
	<#include "index-header.ftl"/>

    <!--  主要内容区域   -->
    <section class="Hui-article mainContent mainContent_zhsw" id="mainContent">
        <iframe id="mainFrame" src="${sub.subUrl!}" width=100% height=100% class="iframe1" style="border:none;overflow:hidden;"
                frameborder="0" scrolling="auto" marginwidth="0" marginheight="0">
    </section>
    <script type="text/javascript">
        $(function() {
            var iframeUrl = "${sub.subUrl!}";
            //$('#mainFrame').attr("src", iframeUrl);
        });
    </script>
</body>
</html>
