//解决ie低版本浏览器获取获取ajax数据失败的问题
jQuery.support.cors = true;

$(document).ready(function () {
    //检查勘测定界类型，用于处理界址点、图形定位的操作功能
    checkKcdjType();

    initLeftPanelHeight();

    //默认加载active的菜单，如果没有，则默认第一为active
    initLeftMenuStyle();

    //加载关联工程
    beginQueryRelatedProj();
});
/*调用jQueryUI实现拖动*/
$(function() {
    $('.draggable').draggable();
});
$(window).resize(function() {
    initLeftPanelHeight();
});
//用于初始化页面高度，使其自适应
function initLeftPanelHeight(){
    var winHeight=$(window).height();
    var mainHeight=0;
    var leftMiddleHeight = $('.kcdjInfoDiv').height()+10;
    var leftBottomHeight = $('.projectInfoDiv').height()+10;
    if (winHeight>0){
        mainHeight=winHeight-42;
    }
    $(".Adaptive").css({height:mainHeight  +"px"});
    $("#leftMenuDiv").css({height:mainHeight-leftBottomHeight  +"px"});
    $("#myTab3").css({height:mainHeight-leftMiddleHeight-leftBottomHeight  +"px"});
}

$(document).ready(function(){
    $("#handleModelIfr").load(function(){
        //关闭事件
        $(this).contents().find('div[id="button_div"] table td a[id!="aturn"]').click(function(){
            hideModel();
        });
    });
    $("#div_exit").click(function(){
        if (confirm("是否确认退出！")) {
            refreshTask();
        }
    });
    $("#div_trans").click(function(){
        //首先进行必填字段判断，如果满足必填条件则弹出转发窗口，否则提示信息，并return
        var urlParam = "proid="+_proid+"&taskid="+_taskId;
        var url = platform_url+"/wfRequire!checkWorkflowRequireField.action?"+urlParam;
        $.post(url, function(data) {
            if (data != "") {
                data= $.trim(data);
                var responseJson = jQuery.parseJSON(data);;
                if(responseJson.result == 'true'){
                    var strurl =portalUrl+ "/turnWorkFlow?taskid=" + _taskId;
                    showModel(strurl,"任务转发");
                }else{
                    var msg = responseJson.msg;
                    msg = msg.replace(/\\n/g,"\n");
                    alert(msg);
                    return;
                }
            }
        });

    });

    $("#div_finish").click(function(){
         $.ajax({
            type: 'POST',
            processData: false,
            url: platform_url+"/taskturn!hasNextWorkflow.action?taskid=" + _taskId,
            success: function(backxml){
                if (backxml == 'true') {
                    var strurl = platform_url+"/taskturn!finishWorkflow.action?taskid=" + _taskId;
                    var returnstr=null;
                    if (/msie 6/i.test(navigator.userAgent)) {
                        //判断是否ie6
                        returnstr = showwindow(strurl, null, 640, 355);
                    } else {
                        returnstr = showwindow(strurl, null, 640, 335);
                    }
                    if (returnstr == "finishok") {
                        refreshTask();
                    }
                    $("#loading").hide();
                } else {
                    $('#finishModal').modal('show');
                }
            }
        });


    });

    $("#div_del").click(function(){
        if (!confirm("是否确认进行删除！")) {
            return;
        }
        var delUrl = platform_url+"/task!del.action?taskid=" + _taskId;
        $.post(delUrl, function(data) {
            data = $.trim(data);
            if (data == "true" || data == "1") {
                alert("删除成功！");
                refreshTask();
            } else {
                alert("该任务无法删除！");
            }
        });
    });

    $("#div_modify").click(function(){
        $('#modifyModal').modal('show');
    });

    $("#btn_modifyWf").click(function(){
        var workflowIntanceName = $('#workflowIntanceName').val();
        if(workflowIntanceName && workflowIntanceName != ""){
            var url = portalUrl+"/taskHandle/modifyWf?proid=" + _proid+"&workflowIntanceName="+workflowIntanceName;
            $.ajax({
                url: url,
                success:function(result) {
                    alert(result.msg);
                    if(result.success){
                        $('#wfname').text(workflowIntanceName);
                        $('#modifyModal').modal('hide');
                    }
                }
            });
        }
    });


    //快速转发
    $("#div_quciktrans").click(function(){
        var dialog="";
        $('#myModal').modal('show');
    });


    $("#div_qucikturnInfo").click(function(){
        $("#loading").show();
        saveReport();
        var getUrl = platform_url+"/task.action?taskid=" + _taskId;
        $.ajax({
            url: getUrl,
            async: false,
            success:function(msg) {
                $("#loading").hide();
                if (msg != "" && msg != "true") {
                    alert(msg);
                } else {
                    refreshTask();
                }
            }
        });
    });

    //工作流办结按钮
    function fininshWf(){
        $.ajax({
            type: 'POST',
            processData: false,
            url: platform_url+"/taskturn!TurnTask.action?taskid=" + _taskId,
            data: "<Activitys><ReMark><text/></ReMark></Activitys>",
            beforeSend: function(request) {
                request.setRequestHeader("Content-Type","text/xml");
            },
            success: function(backxml){
                if (backxml == 'true') {
                    alert("办结成功");
                    refreshTask();
                } else {
                    alert(backxml);
                }
            }
        });
    }
    //工作流办结
    $("#div_finishWf").click(function(){
        $(this).attr({"disabled":"disabled"});
        saveReport();
        if(_version=="hm"){
            var url=portalUrl+"/turnWorkFlow/getTurnWorkFlowInfo?taskid="+_taskId;
            $.ajax({
                url: url,
                type: 'GET',
                error: function () {
                    fininshWf()
                },
                success: function (data) {
                    if(data!=null && data!=""){
                        var bdcUrl=_bdcdjUrl+"/wfProject/beforeTurnProjectEventEnd?proid="+data;
                        $.ajax({
                            url: bdcUrl,
                            type: 'GET',
                            error: function () {
                                fininshWf()
                            },
                            success: function (msg) {
                                if(msg!=null && msg!=""){
                                    alert(msg);
                                }

                                fininshWf();
                            }
                        });
                    }
                    else
                    fininshWf();
                }
            });
        }
        else
            fininshWf();
    });

    //退回操作
    $("#div_back").click(function(){
        var strurl = portalUrl+"/turnBackWorkFlow?taskid=" + _taskId;
        showModel(strurl,"任务退回");
    });

    $("#div_retrieve").click(function(){
        if (!confirm("是否确认进行取回！")) {
            return;
        }
        var delUrl = platform_url+"/task!retrieve.action?taskid=" + _taskId;
        $.post(delUrl, function(data) {
            data = $.trim(data);
            if (data != "") {
                alert(data);
            } else {
                alert("取回成功！");
                refreshTask();
            }
        });
    });

    //委托办理
    $("#div_trust").click(function(){
        $("#handleModelIfr").load(function(){
            var titleEle = $(this).contents().find("title");
            if(titleEle){
                var titleTxt = $(titleEle).text();
                titleTxt = trimBlank(titleTxt);
                if(titleTxt.indexOf("委托") > -1){
                    $(this).contents().find("#divTab").css('width','100%');
                    $(this).contents().find("table").css('width','100%');
                    $(this).contents().find("table tr ul[class*='tabs']").hide();
                    //关闭事件
                    $(this).contents().find('a[id!="aturn"]').click(function(){
                        hideModel();
                    });
                    //确定事件
                    //取消原来的按钮事件，执行新的按钮事件
                    var aEle = $(this).contents().find('a[id="aturn"]');
                    if(aEle){
                        var txt = $(aEle).text();
                        if(txt){
                            txt = trimBlank(txt);
                            if(txt == "委托"){
                                $(aEle).removeAttr("onclick");
                                $(aEle).unbind("click").click(function(){
                                    var userName= $("#handleModelIfr").contents().find("#sel_user").find("option:selected").text();
                                    var userId= $("#handleModelIfr").contents().find("#sel_user").val();
                                    if(confirm("确定要委托给【"+userName+"】？")){
                                        var url = platform_url+"/pf/tasktrust!Trust.action?taskid=" + _taskId+"&userId="+userId;
                                        $.post(url,null,function(data){
                                            data= $.trim(data);
                                            if (data!="true"){
                                                alert(data);
                                            }else{
                                                alert("委托成功！");
                                                refreshTask();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
        var strurl = platform_url+"/tasktrust.action?taskid=" + _taskId;
        showModel(strurl,"任务委托");
    });

    //modal窗口关闭按钮时，清空modal内容
    $('#closeHandleModal').click(function(){
        hideModel();
    });

});

//查文件中心的数据
var _returnData;

function delRelatedProj(wiidRel){
    $.ajax({
        type:"post",
        url:platform_url +'/projectrelate!delAssociatedWorkflowInstalnce.action?wiid='+_wiid+"&wiidRelated="+wiidRel,
        success:function(msg){

        }
    })
}


function refreshTask() {
    $.cookie('portal_index_tasklist','tasklist', { expires: 7, path: '/'});
    hideModel();
    window.close();
}

function showwindow(url,title,width,height){
    var ua = navigator.userAgent;
    var returnValue = "";
    if(ua.lastIndexOf("MSIE 6.0") != -1){
        if(ua.lastIndexOf("Windows NT 5.1") != -1){
            height=(height*1.0+102);
        } else if(ua.lastIndexOf("Windows NT 5.0") != -1){
            height=(height*1.0+49);
        }
        returnValue = window.showModalDialog(url,title,"dialogHeight="+height+"px;dialogWidth="+width + "px");
    }else if(ua.indexOf("Chrome") != -1){
        window.open(url,title,"height="+height+"px,width="+width + "px,toolbar=no,menubar=no,scrollbars=no,resizable=no");
        returnValue = "ok";
    }else{
        returnValue = window.showModalDialog(url,title,"dialogHeight="+height+"px;dialogWidth="+width + "px");
    }
    return returnValue;
}

function getUrl(action) {
    var ss = action.split('?');
    return [_fileCenterText+"/", ss[0], '?', (ss.length > 1 ? ss[1] : ''), '&token=' + _fileCenterToken].join('');
}

function showModel(url,title,width,height,fullscreen){
    showBstrapModel('handleModel','handleModelIfr','handleModelTitle',url,title);
}
function hideModel(){
    hideBstrapModel('handleModel','handleModelIfr','handleModelTitle');
}
function reloadWin(){
    window.location.reload();
}

/**
 * 替换
 */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
    }else {
        return this.replace(reallyDo, replaceWith);
    }
}

function trimBlank(txt){
    txt = txt.replaceAll(" ", "", true);
    txt = txt.replaceAll("　", "", true);
    return txt;
}

function showBstrapModel(modalId,modalFrameId,modalTitleId,url,title,width,height,fullscreen){
    $('#'+modalId).modal({show:true});
    //$('#'+modalId).modal('show');//.css({"width":width+"px","height":height+"px","left":left+"px","top":top+"px","margin-left":"0px"});
    //$("#"+modalFrameId).css("height",height-40+"px");
//    alert(window.parent.document.getElementById("myModal"));
//    window.parent.parent.document.getElementById("myModal").modal('show');
    $('#'+modalFrameId).attr("src", url);
    $("#"+modalTitleId).html(title);
}
function hideBstrapModel(modalId,modalFrameId,modalTitleId){
    $('#'+modalId).modal('hide');
    $('#'+modalFrameId).attr("src", "");
    $("#"+modalTitleId).html("");
}

function openResource(index,url,name){
    $('#myTab3 li').removeClass("active");
    //判断资源地址中是否配置RELOAD_IFRAME_URL=true参数，如果符合条件，在切换选项卡时，会重新加载选项卡内页面
    var reload_iframe_url = false;
    if(url && url.indexOf("RELOAD_IFRAME_URL=true") > -1){
        reload_iframe_url = true;
    }
    url = url.split('&EXTEND_PARAM')[0];
    TabControlAppend(index, name, url, reload_iframe_url);
}
//页面的自动保存方法，暂时不用
function saveReport(){
    if(!_readOnly || _readOnly == "false"){
        if(_autoSave && _autoSave == "true"){
            var contentFrameAry = document.getElementsByName("contentFrame");
            if(contentFrameAry && contentFrameAry.length > 0){
                for(var i = 0; i<contentFrameAry.length; i++) {
                    var contentFrame=contentFrameAry[i];//document.getElementById("contentFrame");
                    if(contentFrame!=null){
                        try{
                            //子页面保存，如果帆软增加了自定义的goSave方法，则可以用该方法进行处理
                            //获取子页面
                            var childWin = contentFrame.contentWindow.frames["frameMain"];
                            //判断浏览器兼容性
                            if(childWin.contentDocument){
                                //谷歌下测试可行
                                //获取子页面元素
                                var childWinEle = childWin.contentDocument.getElementById("proId");
                                //判断是否包含该方法
                                if( typeof childWin.contentWindow.goSave == 'function' ){
                                    //存在且是function
                                    childWin.contentWindow.goSave();
                                }
                            }else{
                                //测试IE8下可用
                                //获取子页面元素
                                var childWinEle = childWin.document.getElementById("proId");
                                //判断是否包含该方法
                                if(typeof childWin.goSave == 'function'){
                                    //存在且是function
                                    childWin.goSave();
                                }
                            }
                        }catch(e){
                            var childWinEle = contentFrame.contentDocument.getElementById("proId");
                            //判断是否包含该方法
                            if( typeof contentFrame.contentWindow.goSave == 'function' ){
                                //存在且是function
                                contentFrame.contentWindow.goSave();
                            }
                        }
                        try{
                            //帆软保存
                            var frameMain=contentFrame.contentWindow.document.getElementById("frameMain");
                            if(frameMain!=null){
                                var contentPane=frameMain.contentWindow.contentPane;
                                if(contentPane!=null)
                                    contentPane.writeReport();
                            }
                        }catch(e){
                        }
                    }
                }
            }

        }
    }
}

function initLeftMenuStyle(){
    //默认加载active的菜单，如果没有，则默认第一为active
    if(_defaultName && _defaultName != ""){
        $('li[resouceName="'+_defaultName+'"]').addClass("active");
    }

    var activeLi = $('.oneMenuUL li[class*="active"]');
    if(activeLi && activeLi.length > 0){
        var url = $(activeLi).attr("resourceLink");
        if(url && url != "undefined"){
            url = platform_url + "/" + url;
            TabControlAppend($(activeLi).prevAll().length, $(activeLi).attr("resouceName"), url);
        }
    }else{
        //延时0.15秒加载第一个菜单，避免事件提前执行
        setTimeout(function(){
            //如果没有默认菜单，则默认加载第一个li的菜单
            $('.oneMenuUL a[class*="clickResource"]:first').trigger('click');
        },150)
    }
}

function showOrHideMenu(obj){
    $('.oneMenuUL li[class*="active"]').removeClass("active");
    //处理隐藏显示下一集菜单/控制箭头上下
    var menuUl = $(obj).parent().find('ul');
    if(menuUl.is(":hidden")){
        menuUl.show();
        $(obj).find('div i').removeClass("icon-chevron-up");
        $(obj).find('div i').addClass("icon-chevron-down");
    }else{
        menuUl.hide();
        $(obj).find('div i').removeClass("icon-chevron-down");
        $(obj).find('div i').addClass("icon-chevron-up");
    }
}

//********************************************
//关联工程
//********************************************
function beginQueryRelatedProj(){
    $("#relatedProjects tr").each(function(){
        $(this).remove();
    });
    $.ajax({
        type: "get",
        dataType: "json",
        url: platform_url+'/projectrelate!getAssociatedWorkflowInstance.action?wiid='+_wiid,
        success: function(msg){//msg为返回的数据，在这里做数据绑定
            for(var i=0;i<msg.length;i++){
                var captionName="<a  title='"+msg[i].workflowIntanceName+"' href='javascript:showRelatedWorkFlowProject(\""+msg[i].workflowIntanceId+"\")'>";
                captionName += "<i class='icon-mobile-phone'> </i>";
                if(msg[i].workflowIntanceName.length>13)
                    captionName +=(msg[i].workflowIntanceName).substr(0,13)+"...";
                else
                    captionName += msg[i].workflowIntanceName;
                captionName += "</a>";
                if(_readOnly && _readOnly == "true"){
                    $("<tr></tr>").append("<td>"+captionName+"</td>").
                        append("<td></td>").
                        appendTo("#relatedProjects");
                }else{
                    $("<tr onmouseover='showImgControl($(this))' onmouseout='hideImgControl($(this))'></tr>").append("<td width='140px'>"+captionName+"</td>").
                        append("<td width='15px'><img alt='删除项目关联' style='cursor:pointer;display :none' " +
                        "onclick='delRelatedProj(\""+msg[i].workflowIntanceId+"\",this)' " +
                        "src='"+platform_url+"/common/images/cancel.png'/></td>").
                        appendTo("#relatedProjects");
                }
            }
        }
    });
}
function showImgControl(tdControl){
    tdControl.children("td")[1].children[0].style['display']='block';
}

function hideImgControl(tdControl){
    tdControl.children("td")[1].children[0].style['display']='none';
}
function showRelatedWorkFlowProject(wiid){
    var w_width=screen.availWidth-10;
    var w_height= screen.availHeight-32;
    var url=platform_url+'/projecthandle.action?wiid=' + wiid;
    if(handleStyle && handleStyle == "true"){
        url = portalUrl + '/projectHandle?wiid=' + wiid;
    }
    window.open(url, "_project", "left=1,top=0,height="+w_height+",width="+w_width+",resizable=yes,scrollbars=yes");
}
function delRelatedProj(wiidRel,obj){
    if (confirm("是否确认删除项目关联！")){
        $.ajax({
            type:"post",
            url:platform_url+'/projectrelate!delAssociatedWorkflowInstalnce.action?wiid='+_wiid+"&wiidRelated="+wiidRel,
            success:function(msg){
                alert("删除成功！");
                $(obj).parent().parent().remove();
            }
        })
    }
}

function checkKcdjType(){
    //扩展属性中关于勘测定界管理模块的配置属性获取
    if(typeof(_kcdjTypeConfig) == "undefined" || !_kcdjTypeConfig){
        $('#divKcdj').hide();
        $('#divKcdj').css("height","0px");
    }else{
        _kcdjTypeConfig = parseInt(_kcdjTypeConfig);
        $('#kcdj_bg').hide();
        $('#kcdj_jzd').hide();
        $('#kcdj_map').hide();
        var kcdjPanelHeight = 70;
        switch (_kcdjTypeConfig) {
            case 1:
                // 代码为1时，只显示图形浏览功能
                $('#kcdj_map').show();
                kcdjPanelHeight = 25;
                break;
            case 2:
                // 代码为2时，显示界址点成果和图形浏览功能
                $('#kcdj_jzd').show();
                $('#kcdj_map').show();
                kcdjPanelHeight = 48;
                break;
            case 3:
                // 代码为3时，显示勘测定界、界址点成果、图形浏览功能
                $('#kcdj_bg').show();
                $('#kcdj_jzd').show();
                $('#kcdj_map').show();
                break;
            case 8:
                // 代码为8时，显示界址点成果和图形浏览功能，且界址点成果可编辑
                $('#kcdj_jzd').show();
                $('#kcdj_map').show();
                kcdjPanelHeight = 50;
                break;
            case 9:
                // 代码为9时，显示勘测定界、界址点成果、图形浏览功能，且勘测定界、界址点成果可编辑
                $('#kcdj_bg').show();
                $('#kcdj_jzd').show();
                $('#kcdj_map').show();
                break;
            default:
                //默认不显示勘测定界成果模块
                $('#divKcdj').hide();
                kcdjPanelHeight = 0;
        }
        $('#divKcdj').css("height",kcdjPanelHeight+"px");
    }
}

function showKcdj(type,proid){
    if(type && _buildlandUrl){
        var typeName="勘测定界";
        var kcdjReadonly = true;
        var jzdReadonly = true;
        if(!_readOnly || _readOnly == "false"){
            switch (typeof(_kcdjTypeConfig) != "undefined" &&_kcdjTypeConfig) {
                case 8:
                    // 代码为8时，显示界址点成果和图形浏览功能，且界址点成果可编辑
                    jzdReadonly = false;
                    break;
                case 9:
                    // 代码为9时，显示勘测定界、界址点成果、图形浏览功能，且勘测定界、界址点成果可编辑
                    kcdjReadonly = false;
                    jzdReadonly = false;
                    break;
                default:
                    //默认不可编辑
                    break;
            }
        }
        var url = "";
        if(type == 'kcdj'){
            url = _buildlandUrl+"/surveybound/kcdj-main!openKcdjMain.action?proid="+proid+"&readOnly="+kcdjReadonly;
        }else if(type == 'jzd'){
            url = _buildlandUrl+"/surveybound/kcdj-jzd.action?show=false&proid="+proid+"&readOnly="+jzdReadonly;
            typeName="界址点查看";
        }else if(type == 'map'){
            url = _buildlandUrl+"/map/map-config.action?proid="+proid+"&hideTopBar=true&readOnly="+jzdReadonly;
            typeName="图形浏览";
        }
        if(url){
            openResource(type,url,typeName);
        }
    }
}