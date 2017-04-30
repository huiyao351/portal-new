<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${ctx.getEnv('portal.title')}</title>
    <link href="${base}/static/lib/bootstrap2.3/css/bootstrap.css" rel="stylesheet"/>
    <link href="${base}/static/lib/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <script src="${base}/static/lib/jquery/jquery-1.11.0.min.js"></script>
    <script src="${base}/static/lib/bootstrap2.3/js/bootstrap.js"></script>
    <link href="${base}/static/css/taskhandle.css" rel="stylesheet"/>
    <style type="text/css">
        #mask {
            position: absolute;
            top: 0px;
            filter: alpha(opacity=60);
            background-color: #777;
            z-index: 1;
            left: 0px;
            opacity:0.5;
            -moz-opacity:0.5;
            text-align: center;
            font-weight: bold;
            font-size: 14px;
            color: red;
            line-height: 300px;
        }
    </style>
    <script type="text/javascript">
        //解决ie低版本浏览器获取获取ajax数据失败的问题
        jQuery.support.cors = true;
        function gotoTurn(info) {
            var checkradio= $("input[name='selectradio']:checked");
            var selectvalue;
            if (checkradio){
                selectvalue=checkradio.val();
            }
            if (!selectvalue){
                alert("该活动不能退回！");
            }else{
                var remark=$("#txtinfo").val();
                var url = "${path_platform!}/pf/taskturnback!BackTask.action?taskid=${taskid}&adids=" + selectvalue;
                showWaiting();
                //转发前保存帆软
                parent.saveReport();
                $.post(url,{remark:remark},function(data){
                    data= $.trim(data);
                    if (data!="true"){
                        alert(data);
                    }else{
                        alert("退回成功！");
                        parent.refreshTask();
                    }
                });
            }
        }

        function TabSwitch(selectedTab) {
            //1、找ID为divTab的元素；2、找className为tabs的所有LI元素（排除className为tabspace的LI元素）；3、清除所有class
            $("#divTab .tabs LI[class!='tabspace']").removeClass();

            //为当前选中的tab设置class
            $("#" + selectedTab).addClass("selectedTab");
            $("#div_tabl").toggle();
            $("#div_tab2").toggle();
        }

        $(function() {
            $("#mask").hide();
        });
        function showWaiting(){
            $("#mask").css("height",$(document).height());
            $("#mask").css("width",$(document).width());
            $("#mask").show();
        }
    </script>
</head>
<body  scroll="no"><!--  带属性的面板  -->

<div id="divTab">
    <div class="tabbable"> <!-- Only required for left/right tabs -->
        <ul class="nav nav-tabs">
            <li class="active"><a href="#tab1" data-toggle="tab">退回活动</a></li>
            <li><a href="#tab2" data-toggle="tab">退回意见</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="tab1" style="height: 253px;">
                <table cellSpacing="0" cellPadding="0" width="100%" height="100%" border="1" style="border-color: #F0F0F0;">
                    <tr>
                        <td id="tabContent" align="center"  style="text-align: center;vertical-align: top;">
                            <div id="div_tabl">
                                <table width="100%" border="0" class="table_border" id="tblturn">
                                    <thead>
                                    <tr height="30" class="title" style="background-color: #F0F0F0;">
                                        <th width="35" align="center">选择</th>
                                        <th  align="center">退回活动</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#if backActivitys?size gt 0>
                                        <#list backActivitys as backActivity>
                                        <tr>
                                            <td class="activitytd"  height="30">
                                                <input name="selectradio" type="radio" <#if backActivity_index==0>checked="checked" </#if>
                                                       value="${backActivity.activityDefinitionId!}" />
                                            </td>
                                            <td class="activitytd">${backActivity.activityName!}</td>
                                        </tr>
                                        </#list>
                                    </#if>
                                    </tbody>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="tab-pane" id="tab2" style="height: 251px;border:1px solid gray;">
                <table border="0" id="tblturn" style="width:100%;height:100%;">
                    <tr>
                        <td  align="center" style="padding-top:5px;height:100%;">
                            <textarea name="txtinfo" id="txtinfo" style="height:240px;"></textarea>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div id="button_div" style="text-align: center;margin-top: 10px;">
        <div>
            <button id="aturn" class="btn-secondary size-M hui-btn" type="button" data-loading-text="Loading..." onclick="gotoTurn()">
                <i class="icon-arrow-left icon-white"></i> 退　回
            </button>
        </div>
    </div>
</div>


<div id="mask" class="mask">正在退回中.....</div>
</body>
</html>