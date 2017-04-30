<#--签名标签的解析结果：没有意见的签名标签-->
<div class="signtag" style="margin:0px;padding:0px;border:0px;font-size:14px;margin-left: -1px;margin-bottom: -1px;">
<#list lstSign as sign>
    <#if sign?exists>
    <table id="" border=0 width="100%" >
        <tr>
            <td style="line-height:25px;" class="signOpinion" rowSpan="2">
                &nbsp;
            </td>
            <td width="150" rowSpan=2>
                <IMG src="${portalUrl}/sign/image?signVo.signId=${sign.signId}"
                     style="background-repeat: no-repeat;background-color: transparent;CURSOR: hand;width:100%;"/>
            </td>
            <td width="120">
                <#if !disabled>
                    <#if sign.userId?exists && sign.userId==signVo.userId>
                        <INPUT id="${signVo.signKey}" signid='${sign.signId}' style="BACKGROUND-IMAGE: url(${portalUrl}/static/images/sign.gif);background-repeat: no-repeat;background-color: transparent;WIDTH: 52px;HEIGHT: 22px;CURSOR: hand;border:1px solid #ffffff;"
                               onclick="showsign_${signVo.signKey}('${sign.signKey}','${sign.proId}','${sign.signType}');" type="button">
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
                       onclick="showsign_${signVo.signKey}('${signVo.signKey}','${signVo.proId}','${opinionType?default('')}');" type="button"/>
                <input type="hidden" name="${signVo.signName}" id="${signVo.signName}" value=""/>
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
    function showsign_${signVo.signKey}(signkey,proid,opintype){
        var signPasswordCheckUrl = "${platformUrl}/signUserCheck.action";
        var signUserCheck = '${signUserCheck?c}';
        var returnvalue = null;
        if(signUserCheck!=null&&signUserCheck==='true'){
            returnvalue = window.showModalDialog(signPasswordCheckUrl, null, "dialogWidth:400px;dialogHeight:120px;help:no;status:no;scroll:no;location:no;");
            if (returnvalue == null || returnvalue == '' || returnvalue != 'true')
                return;
        }


        obj = event.srcElement ? event.srcElement : event.target;
        var signid=  $(obj).attr("signid");
        if (!signid) signid="";
        var signname="${signVo.signName}";
        opintype=encodeURI(opintype);
        var signUrl="${platformUrl}/tag/signtag!signno.action?signVo.signId=" + signid + "&signVo.signKey=" + encodeURI(encodeURI(signkey)) + "&signVo.proId=" +proid + "&opinionType=" + opintype;
        returnvalue = window.showModalDialog(signUrl,null,"dialogWidth:480px;dialogHeight:280px;help:no;status:no;scroll:no;");
        if (returnvalue) {
            if (!returnvalue.signOpinion || returnvalue.signOpinion == "")
                $($(obj).parents("table")[0]).find('tr').eq(0).find("td").eq(0).html("&nbsp;");  //意见
            else
                $($(obj).parents("table")[0]).find('tr').eq(0).find("td").eq(0).html(returnvalue.signOpinion.replace(new RegExp("\n", "gm"), "<br/>"));  //意见
            if (returnvalue.signId == "" || !returnvalue.signId)
                $($(obj).parents("table")[0]).find('tr').eq(0).find("td").eq(1).html("");
            else{
                var imgUrl="${platformUrl}/tag/signtag!image.action?signVo.signId=" + returnvalue.signId + "&random=" + Math.random()*1000;
                $($(obj).parents("table")[0]).find('tr').eq(0).find("td").eq(1).html("<IMG src='"+imgUrl+"' width=100 height=50 />");  //图片
            }
            if (returnvalue.signDate){
                var dateval = returnvalue.signDate;
                if (dateval.length > 18) {
                    dateval = dateval.substring(0, 10);
                }
                $($(obj).parents("table")[0]).find('tr').eq(1).find("td").eq(0).html(dateval);  //时间
            }else{
                $($(obj).parents("table")[0]).find('tr').eq(1).find("td").eq(0).html("");  //时间
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

            if (returnvalue.signId){
                $(obj).attr("signid",returnvalue.signId);
                $("#" + signname).val(returnvalue.signId);
            }else{
                $(obj).attr("signid","");
                $("#" + signname).val("");
            }
        }
    }
</script>