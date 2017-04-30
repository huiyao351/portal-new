var fullHeight = screen.height-100;
var fullWidth = screen.width-10;
var newWinTabOption = "left=0,top=0,height="+fullHeight+",width="+fullWidth+",toolbar=no,menubar=no,location=yes,status=yes,scrollbars=yes,resizable=yes";
/**
 * 首页公用脚本
 * @type {boolean}
 */
jQuery.support.cors = true;
$(function(){
    //首页一级菜单点击事件
    //initSubMenuClick();
    /*调用jQueryUI实现拖动*/
    $('.draggable').draggable();
});
function initSubMenu(type){
    var url = portalUrl+'/config/sub/all?';
    $.ajax({
        url:url,
        type:'post',
        dataType:'json',
        async:false,
        success:function (data) {
            if(data && data.length > 0){
                //刷新列表
                for (var i = 0; i < data.length; i++) {
                    var sub = data[i];
                    if(sub){
                        var current = sub.subsystemId==type?" current ":"";
                        var subLi = $('<li class="navBorder '+ current +'  border-top"/>');
                        //默认去当前portal的首页功能，
                        // 如果配置了主题类型是超链接，并且配置了URL地址，则取url地址，其他情况一律采用portal自带功能
                        var link = portalUrl+'/index?systemId='+sub.subsystemId;
                        if(!sub.subUrl){
                            sub.subUrl = "";
                        }
                        if(sub.subUrl && sub.subType == 2) {
                            link = sub.subUrl;
                        }
                        $('<a class="dropDown_A" menuIndex="'+(i+1)+'" href="#" subUrl="'+sub.subUrl+'" menuLink="'+link+'" subsystemId="'+sub.subsystemId+'" menuType="'+sub.subType+'">'+sub.subsystemTitle+'</a>').appendTo(subLi);
                        $('#sysNavMenu').append(subLi);
                    }
                }
                initSubMenuClick();
            }
        }
    });
}

function initSubMenuClick(){
    $('#sysNavMenu li[class*="navBorder"]>a').click(function () {
        //alert($(this).attr("menuIndex"));
        //oneMenuClick($(this).attr("menuIndex"));
        $('#mainContent').addClass("Hui-article");
        $('#mainContent').removeClass("Hui-article-box");

        modelIndex = $(this).attr("menuIndex");
        var menuLink = $(this).attr("menuLink");
        if(menuLink){
            var menuType = $(this).attr("menuType");
            var subUrl = $(this).attr("subUrl");
            //如果配置的是超链接，并且配置有url地址，则打开新页面，
            // 如果没有配置url，则表示加载综合事务首页
            if(menuType && menuType == 2 && subUrl){
                if(menuLink.indexOf("CAS_LOGIN=true")>-1){
                    menuLink += "&user_card="+$('#loginName').val();
                }
                window.open(menuLink,'new'+menuType,newWinTabOption);
            }else{
                window.location.href=menuLink;
            }
        }
    });
    var curSysSub = $('#sysNavMenu li[class*="current"]>a');
    modelIndex = $(curSysSub).attr("menuIndex");
    var menuType = $(curSysSub).attr("menuType");
    if(menuType && menuType==1){//只有主题类型是菜单时，才进行菜单的加载
        //创建菜单
        buildMenu();
    }
}

function resizeMainFrame(){
    var leftMenuHieght;
    var winHeight = $(window).height();
    if (winHeight > 0) {
        leftMenuHieght = winHeight - 104;
    }
    $("#mainFrame").css({height: (leftMenuHieght) + "px"});
    /*if($('.secondMenu').css('display')=='none' && $('.breadcrumbDiv').css('display')=='none') {
        $("#mainFrame").css({height: (leftMenuHieght+20) + "px"});
    }else{
        $("#mainFrame").css({height: (leftMenuHieght+41) + "px"});
    }*/
}
function buildLinkUrl(resourceUrl) {
    if (resourceUrl!=null&&resourceUrl.substr(0, 1) != "/" && resourceUrl.substr(0, 1) != "h" && resourceUrl.substr(0, 7) != "MapGtis") {
        return platform_url + '/' + resourceUrl;
    } else {
        return  resourceUrl;
    }
}

function loadMsgReminder(){
    $.get(portalUrl+"/reminder", function (result) {
        if(result!=null&&result.length>0){
            $('.Urgent>span').text(result.length);
            for(var i=0;i<result.length;i++){
                var item = '<dd><a target="_blank" href="'+result[i].moreUrl+'"><span class="label label-important"><i class="icon-exclamation-sign  icon-white"></i></span>'+
                    result[i].name+'<span class="badge badge-important pull-right">'+result[i].count+'</span></a></dd>';
                $('.reminder').append(item);
            }
        }else{
            $('.Urgent>span').text(0);
        }

    });
}

function showWindow(url,title,width,height){
    var ua = navigator.userAgent;
    if(ua.lastIndexOf("MSIE 6.0") != -1){
        if(ua.lastIndexOf("Windows NT 5.1") != -1){
            height=(height*1.0+102);
        } else if(ua.lastIndexOf("Windows NT 5.0") != -1){
            height=(height*1.0+49);
        }
    }
    try {
        return window.showModalDialog(url, title, "dialogHeight=" + height + "px;dialogWidth=" + width + "px");
    }catch (ex){
        var winOption = "height="+height+",width="+width+",top=55,left=200,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes";
        return  window.open(url,window, winOption);
    }
}

/**
 * 在url末尾增加时间戳
 * @param url
 * @returns {*}
 */
function convertURL(url) {
    var timstamp = (new Date()).valueOf();
    if (url.indexOf("?") >= 0) {
        url = url + "&t=" + timstamp;
    } else {
        url = url + "?t=" + timstamp;
    }
    return url;
}

/**
 * 登出
 */
function logout() {
    var url = platform_url+'/logout.action';
    $.post(url,function(){
    });
    window.close();
}

/**
 * 截取字符串长度，用省略号进行处理
 * @param str
 * @param limit
 * @returns {string}
 */
function sub_suolve(str,limit){
    if(!limit){
        limit = sub_length;
    }
    var temp1 = str.replace(/[^\x00-\xff]/g,"**");//精髓
    var temp2 = temp1.substring(0,limit);
    //找出有多少个*
    var x_length = temp2.split("\*").length - 1 ;
    var hanzi_num = x_length /2 ;
    var index = limit - hanzi_num ;//实际需要sub的长度是总长度-汉字长度
    var res = str.substring(0,index);
    if(index < str.length ){
        var end =res+"……" ;
    }else{
        var end = res ;
    }
    return end ;
}
function getCurDate(){
    var nowDate = new Date();
    var year = nowDate.getFullYear();
    var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1):nowDate.getMonth() + 1;
    var day = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
    var dateStr = year + "-" + month + "-" + day;
    return dateStr;
}

/*左侧菜单-隐藏显示*/
function displayLeftNavbar(obj){
    if($(obj).hasClass("open")){
        $(obj).removeClass("open");
        $('#leftFirstMenu').show();
        $("body").removeClass("big-page");
    }else{
        $(obj).addClass("open");
        $('#leftFirstMenu').hide();
        $("body").addClass("big-page");
    }
}

