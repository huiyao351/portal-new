<#--签名标签的解析结果：带有意见的签名标签-->
<style>
    /*.signtag{
        margin:0px;padding:0px;border:0px;font-size:14px;margin-left: -1px;margin-bottom: -1px;
    }
    .signtag .signOpinion{
        *//*border-left: 0px solid #ddd;border-bottom: 0px solid #ddd;*//*
        line-height:25px;padding-left:4px;
    }
    .signtag .signBtn{
        background-color: transparent;WIDTH: 52px;HEIGHT: 22px;CURSOR: hand;border:1px solid #ffffff;
    }*/
</style>
<div class="signtag" style="margin:0px;padding:0px;border:0px;font-size:14px;margin-left: -1px;margin-bottom: -1px;">
<#list lstSign as sign>
    <#if sign?exists>
    <table id="" border=0 width="100%" >
        <tr>
            <td style="line-height:25px;padding-left:4px;" class="signOpinion" rowSpan="2">
                <#if sign.signOpinion?exists>
                    ${sign.signOpinion?replace("\n","<br/>")}
                <#else>
                    &nbsp;
                </#if>
            </td>
            <td width="150" rowSpan=2>
                <IMG src="${portalUrl}/sign/image?signVo.signId=${sign.signId}"
                     style="background-repeat: no-repeat;background-color: transparent;CURSOR: hand;width:100%;"/>
            </td>
            <td width="120">
                <#if !disabled>
                    <#if sign.userId?exists && sign.userId==signVo.userId>
                        <INPUT id="${signVo.signKey}" style="BACKGROUND-IMAGE: url(${portalUrl}/static/images/sign.gif);background-repeat: no-repeat;background-color: transparent;WIDTH: 52px;HEIGHT: 22px;CURSOR: hand;border:1px solid #ffffff;"
                               class="signBtn" signid='${sign.signId}' onclick="showsign_${signVo.signKey}(event,'${sign.signKey}','${sign.proId}','${sign.signType}');" type="button">
                    </#if>
                </#if>
            </td>
        </tr>
        <tr>
            <td width="80">
                <div style="padding-left:4px;<#if showSignDate?exists && !showSignDate>display:none;</#if>">${sign.signDate?if_exists?string("yyyy-MM-dd")}</div>
            </td>
        </tr>
    </table>
    </#if>
</#list>
<#if !disabled>
<#if createNew>
<table id="" border=0 width="100%">
    <tr>
        <td style="line-height:25px;" class="signOpinion" rowSpan="2">
            &nbsp;
        </td>
        <td width="150" rowSpan=2>
            &nbsp;
        </td>
        <td width="120">
            <INPUT id="${signVo.signKey}" style="BACKGROUND-IMAGE: url(${portalUrl}/static/images/sign.gif);background-repeat: no-repeat;background-color: transparent;WIDTH: 52px;HEIGHT: 22px;CURSOR: hand;border:1px solid #ffffff;"
                   class="signBtn" onclick="showsign_${signVo.signKey}(event,'${signVo.signKey?default('')}','${signVo.proId?default('')}');" type="button">
        </td>
    </tr>
    <tr>
        <td width="80"></td>
    </tr>
</table>
</#if>
</#if>
</div>
<script type="text/javascript">
    function showsign_${signVo.signKey}(event,signkey,proid){
        var signPasswordCheckUrl = "${platformUrl}/signUserCheck.action";
        var signUserCheck = '${signUserCheck?c}';
        var returnvalue = null;
        if(signUserCheck!=null&&signUserCheck==='true') {
            if (/msie 6/i.test(navigator.userAgent)) {
                //判断是否ie6
                returnvalue = window.showModalDialog(signPasswordCheckUrl, null, "dialogWidth:480px;dialogHeight:120px;help:no;status:no;scroll:no;");
            } else {
                returnvalue = window.showModalDialog(signPasswordCheckUrl, null, "dialogWidth:480px;dialogHeight:120px;help:no;status:no;scroll:no;");
            }
            if (returnvalue == null || returnvalue == '' || returnvalue != 'true')
                return;
        }

        var obj = event.srcElement ? event.srcElement : event.target;
        var signid= $(obj).attr("signid");
        if (!signid) signid="";

        var signUrl="${platformUrl}/tag/signtag!sign.action?t=" + (new Date()).valueOf() + "&signVo.signKey=" + encodeURI(encodeURI(signkey))+"&taskid=${taskid!}";
        if(signid && signid != null){
            signUrl += ("&signVo.signId=" + signid);
        }
        if(proid && proid != null){
            signUrl += ("&signVo.proId=" + proid);
        }
    <#if opinionType?exists>
        var signtype="${opinionType}";
    <#else>
        var signtype="";
    </#if>
        if(signtype && signtype != null){
            signUrl += ("&opinionType=" + encodeURI(signtype));
        }
        returnvalue = null;
        if(/msie 6/i.test(navigator.userAgent)){
            //判断是否ie6
            returnvalue = window.showModalDialog(signUrl,null,"dialogWidth:480px;dialogHeight:455px;help:no;status:no;scroll:no;");
        }else{
            returnvalue = window.showModalDialog(signUrl,null,"dialogWidth:480px;dialogHeight:425px;help:no;status:no;scroll:no;");
        }

        if (returnvalue){
            //签名意见
            if (returnvalue.signOpinion)
                $($(obj).parents("table")[0]).find('tr').eq(0).find("td").eq(0).html(returnvalue.signOpinion.replace(new RegExp("\n","gm"),"<br/>"));
            else
                $($(obj).parents("table")[0]).find('tr').eq(0).find("td").eq(0).html("&nbsp;");
            //签名图片
            if(returnvalue.signId){
                $($(obj).parents("table")[0]).find('tr').eq(0).find("td").eq(1).html("<IMG src='${platformUrl}/tag/signtag!image.action?t=" + (new Date()).valueOf() + "&signVo.signId=" + returnvalue.signId + "' width=100 height=50 />");  //图片
                $(obj).attr("signid",returnvalue.signId);
            }else {
                $($(obj).parents("table")[0]).find('tr').eq(0).find("td").eq(1).html("&nbsp;");
                $(obj).attr("signid","");
            }
            //签名时间
            var showSignDate=true;
        <#if showSignDate?exists && !showSignDate>
            showSignDate=false;
        </#if>
            if(showSignDate){
                if(returnvalue.signDate){
                    var dateval= returnvalue.signDate;
                    if (dateval.length>18){
                        dateval= dateval.substring(0,10);
                    }
                    $($(obj).parents("table")[0]).find('tr').eq(1).find('td').eq(0).html(dateval);
                }
            }else {
                $($(obj).parents("table")[0]).find('tr').eq(1).find('td').eq(0).html("");
            }
        }
    }
</script>