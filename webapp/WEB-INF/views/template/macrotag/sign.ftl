<#-- 签名标签的宏调用，应用于业务系统中spring配置文件中freemarkerConfig--freemarkerSettings--auto_import  -->
<#-- 新工程将该文件拷贝过去，配置spring即可 -->
<#macro sign signdivId="" proId="" signKey="" signName="" opinionType="" opinion=false
 disabled=false orderByUserNo=false showSignDate=false optinionSize="" imagesUrl="" imageWidth="" imageHeight="">
<div id="${signdivId!}"></div>
<script type="text/javascript">
    //解决ie低版本浏览器获取获取ajax数据失败的问题
    jQuery.support.cors = true;
    $(document).ready(function() {
        var params = {
            "signVo.signKey":"${signKey!}",
            "signVo.signName":"${signName!}",
            "signVo.proId":"${proId!}",
            <#if opinion?exists>
                "opinion":${opinion?string("true","false")},//是否显示意见，默认true
            </#if>
            <#if opinionType?exists>
                opinionType:"${opinionType!}",//意见类型，用于右键默认意见
            </#if>
            <#if orderByUserNo?exists>
                "orderByUserNo":${orderByUserNo?string("true","false")},//是否按照用户编号排序，默认false
            </#if>
            <#if optinionSize?exists>
                optinionSize:"${optinionSize!}",//意见文字字体大小，默认14
            </#if>
            <#if imagesUrl?exists>
                imagesUrl:"${imagesUrl!}",//签名按钮图标地址，有默认
            </#if>
            <#if imageWidth?exists>
                imageWidth:"${imageWidth!}",//签名按钮图标宽度，默认52
            </#if>
            <#if imageHeight?exists>
                imageHeight:"${imageHeight!}",//签名按钮图标高度，默认22
            </#if>
            <#if showSignDate?exists>
                showSignDate:${showSignDate?string('true', 'false')},//是否显示日期，默认true
            </#if>
            disabled:${disabled?string("true","false")}//是否可用，暂时不提供传参，后台根据权限控制来处理
        };
        var ajaxUrl="${portalUrl!}/sign/tag";

        var urlParam = document.location.search;
        if($("#taskid")){
            if($("#taskid").val() && $("#taskid").val() != ""){
                params.taskid = $("#taskid").val();
                urlParam = urlParam.replace("taskid","taskid_");
            }
        }

        if($("#wiid")){
            if($("#wiid").val() && $("#wiid").val() != ""){
                params.wiid = $("#wiid").val();
                urlParam = urlParam.replace("wiid","wiid_");
            }
        }

        if($("#proid")){
            if($("#proid").val() && $("#proid").val() != ""){
                params.proid = $("#proid").val();
                urlParam = urlParam.replace("proid","proid_");
            }
        }

        if($("#rid")){
            if($("#rid").val() && $("#rid").val() != ""){
                params.rid = $("#rid").val();
                urlParam = urlParam.replace("rid","rid_");
            }
        }

        if($("#from")){
            if($("#from").val() && $("#from").val() != ""){
                params.from = $("#from").val();
                urlParam = urlParam.replace("from","from_");
            }
        }
        $.ajax({
            type: "POST",
            url: ajaxUrl + urlParam,
            data: params,
            cache: false,
            success: function(msg) {
                $("#" +"${signdivId!}").html(msg);
            }
        });
    });
</script>
</#macro>
