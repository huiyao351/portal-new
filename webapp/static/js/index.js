var _menuJsonObj;
var modelIndex;
var mainMenuIndex;
var secondMenuIndex;
//解决ie低版本浏览器获取获取ajax数据失败的问题
jQuery.support.cors = true;
$(function(){
    $('#createTaskBtn').click(function(){
        var instanceName = $('#instanceName').val();
        if(instanceName===null||instanceName===''){
            alert('请填写标题!');
            $('#instanceName').focus();
            return;
        }else if(instanceName.length > 255) {
            alert("标题不能超过255个字符!");
            $('#instanceName').focus();
            return;
        }
        var remark = $('#remark').val();
        if(remark.length > 256) {
            alert("备注不能超过256个字符!");
            $('#remark').focus();
            return;
        }
        var timeLimitTxt = $('#timeLimit').val();
        if((timeLimitTxt.split("t").length-1)>1) {
            alert("总限期填写错误！");
            $('#timeLimit').focus();
            return;
        }else{
            var timeLimit=0;
            if(timeLimitTxt.indexOf('t')>-1)
                timeLimit= timeLimitTxt.substr(0,timeLimitTxt.indexOf('t'));
            else
                timeLimit=jQuery.trim(timeLimitTxt);
            if(timeLimit>999){
                alert("总限期不能超过999！");
                $('#timeLimit').focus();
                return;
            }

        }
        postCreateTask();
    });
});

$(window).resize(function () {
    loadLeftMenu();
});

function loadLeftMenu() {
    var leftMenuHieght;
    var winHeight = $(window).height();
    if (winHeight > 0) {
        leftMenuHieght = winHeight - 147;
    }
    resizeMainFrame();
}

/**
 * 根据平台的主菜单资源配置初始化portal页面的一、二、三级菜单
 */
function buildMenu(){
    var one_menu_id = "oneMenu_"+subsystemId;
    $.get(portalUrl+"/config/menu/menunav?systemId="+subsystemId, function (result) {
        //如果没有第一个一级菜单供默认打开，则需要默认打开子级菜单的第一个子菜单
        var isOpenFirst = true;

        _menuJsonObj = result;
        var rootMenuChildren= _menuJsonObj.children;
        /**
         * 此处处理模块下面的鼠标滑动菜单，也就是一级业务菜单
         * 备注：模块指的是横向导航条上的几大块
         * 一级业务菜单指的是鼠标滑动显示的内容
         */
        if (rootMenuChildren && rootMenuChildren.length > 0) {
            /*var taskUrl= portalUrl+"/taskCenter/index?rid="+rootMenuChildren[0].link;
            $('#mainFrame').attr("src", taskUrl);*/

            //openMenuResource(rootMenuChildren[0].link, null)
            //如果该主题菜单是菜单样式，并且该主题下之关联了一个菜单，该菜单还是一级菜单的话，隐藏一级菜单导航栏，并调整页面主线是区域高度
            if(rootMenuChildren.length == 1 && rootMenuChildren[0].children.length <= 0){
                $('#'+one_menu_id).hide();

                var newHeight = $('.header').height()+"px";
                $('.mainContent').css("top",newHeight);
                $('.munePosition').css("top",newHeight);
                $('.munePosition1').css("top",newHeight);
                $('.aColumn').css("top",newHeight);
            }
            var tag = true;
            for (var i = rootMenuChildren.length-1; i >=0; i--) {
                if(rootMenuChildren[i]){
                    if(rootMenuChildren[i].children.length > 0){
                        var rootMenu = $('<li/>');
                        $('<a href="#" main-menu-index="'+i+'"'+'>'+rootMenuChildren[i].text+'</a>').appendTo(rootMenu);
                        //$('.firstLevelNavigation ul').append(rootMenu);
                        $('#'+one_menu_id+' ul').append(rootMenu);
                    }else if(i==0){
                        isOpenFirst = false;
                        openMenuResource(rootMenuChildren[i].link, null);
                    }
                }
            }
        }

        /**
         * 左两级菜单样式
         * ******************我的办公环境************************点击一级菜单点击事件
         * 一级业务菜单点击事件处理
         * 点击一级业务，解析之前获取好的json菜单信息，组织二级、三级业务菜单，目前业务下面划分为三级已经足够使用
         * 处理步骤：
         * 1、处理主窗体的自适应宽度样式，显示业务菜单栏、面包屑、隐藏显示按钮，将当前菜单设置为current
         * 2、组织面包屑信息，根据模块、一级、二级、三级进行展示组织
         * 3、更新菜单栏大标题，对应一级菜单名称
         * 4、解析二级菜单，设置二级菜单的样式、事件，处理二级菜单没有三级菜单的情况
         * 5、处理三级菜单样式、事件，设置点击事件
         * 6、默认打开第一个可点击菜单
         */
        /**
         * 左三级菜单样式
         * ******************综合监管************************点击一级菜单点击事件
         * 一级业务菜单点击事件处理
         * 点击一级业务，解析之前获取好的json菜单信息，组织二级、三级、四级业务菜单，其中二级菜单是竖向展示，三级和四级是类似我的办公里面的样式
         * 处理步骤：
         * 1、处理主窗体的自适应宽度样式，显示业务菜单栏、面包屑、隐藏显示按钮，将当前菜单设置为current
         * 2、组织面包屑信息，根据模块、一级、二级、三级、四级进行展示组织
         * 3、解析二级菜单，设置二级菜单的样式、事件，处理二级菜单没有三级菜单的情况
         * 4、处理三级菜单样式、事件，设置点击事件
         * 5、默认打开第一个可点击菜单
         */
        $('#'+one_menu_id+' a[main-menu-index]').click(function(){
            if(subMenuType){
                //左两级
                if(subMenuType == 'two'){
                    //1、处理主窗体的自适应宽度样式，显示业务菜单栏、面包屑、隐藏显示按钮，将当前菜单设置为current
                    $('#mainContent').addClass("Hui-article-box");
                    $('#mainContent').removeClass("Hui-article");
                    $('#leftMenuAsi').show();
                    //$('#breadcrumb').show();
                    $('.dislpayArrow').show();

                    $('#'+one_menu_id).find('li[class="current"]').each(function(){
                        $(this).removeClass('current');
                    });
                    $(this).parent().addClass('current');

                    //一级菜单索引
                    mainMenuIndex = $(this).attr('main-menu-index');
                    //一级菜单对象
                    var mainMenuObj = rootMenuChildren[mainMenuIndex];

                    //2、组织面包屑信息，根据模块、一级、二级、三级进行展示组织
                    var modelName = $('#'+one_menu_id+' a[class=dropDown_A]').text();
                    var mainMenuTitle = mainMenuObj.text;
                    updateBreadcrumb(modelName,mainMenuTitle,null,null);

                    //3、更新菜单栏大标题，对应一级菜单名称
                    $('#leftMenuTitle').empty();
                    $('#leftMenuTitle').attr('title',mainMenuTitle);
                    if(mainMenuTitle!=null && mainMenuTitle.length>8){
                        mainMenuTitle=mainMenuTitle.substr(0,8);
                    }
                    $('#leftMenuTitle').append(mainMenuTitle);

                    oneMenuClickHandle(this,rootMenuChildren[mainMenuIndex],modelName);
                }else if(subMenuType == 'three'){
                    //左三级
                    $('#mainContent').addClass("Hui-article-box");
                    $('#mainContent').addClass("lineMainContent-box");
                    $('#mainContent').removeClass("Hui-article");

                    $('#leftFirstMenu').show();
                    $('#leftMenuAsi').show();
                    //$('#displaynavbar').addClass("open");
                    //$('#breadcrumb').show();
                    $('.dislpayArrow').show();

                    $('#'+one_menu_id).find('li[class="current"]').each(function(){
                        $(this).removeClass('current');
                    });
                    $(this).parent().addClass('current');

                    //一级菜单索引
                    mainMenuIndex = $(this).attr('main-menu-index');
                    //一级菜单对象
                    var mainMenuObj = rootMenuChildren[mainMenuIndex];
                    //2、组织面包屑信息，根据模块、一级、二级、三级进行展示组织
                    var modelName = $('#'+one_menu_id+' a[class=dropDown_A]').text();
                    var mainMenuTitle = mainMenuObj.text;
                    updateBreadcrumb(modelName,mainMenuTitle,null,null);

                    //3、解析二级菜单，设置二级菜单的样式、事件
                    var leftMenuChildren = mainMenuObj.children;
                    $('#leftFirstMenu ul').empty();
                    if(leftMenuChildren.length>0) {
                        var leftLineFirstMenu = false;
                        //初始化左侧竖向菜单
                        for (var i = 0; i < leftMenuChildren.length; i++) {
                            if(leftMenuChildren[i]){
                                if(leftMenuChildren[i].children.length > 0){

                                    var rootMenu = $('<li/>');
                                    $('<a href="#" left-menu-index="'+i+'"'+'><i class="icon-folder-close-alt"></i>'+leftMenuChildren[i].text+'</a>').appendTo(rootMenu);
                                    if(i==0){
                                        rootMenu.addClass('current');
                                    }
                                    $('#leftFirstMenu ul').append(rootMenu);
                                }
                            }
                        }
                        //初始化左侧菜单的点击事件
                        $('#leftFirstMenu a[left-menu-index]').click(function(){
                            $('#leftFirstMenu').find('li[class="current"]').each(function(){
                                $(this).removeClass('current');
                            });
                            $(this).parent().addClass('current');

                            //4、处理三级、四级菜单功能、样式、事件
                            //二级菜单索引
                            var leftMenuIndex = $(this).attr('left-menu-index');
                            oneMenuClickHandle(this,leftMenuChildren[leftMenuIndex],modelName);
                        });
                    }
                    //5、默认打开第一个菜单
                    $('#leftFirstMenu a[left-menu-index]:first').trigger('click');
                }
            }
        });
        //如果没有第一个一级菜单供默认打开，则需要默认打开子级菜单的第一个子菜单
        //因为菜单加载顺序是倒叙，所以去最后一个一级菜单的点击事件
        if(isOpenFirst){
            $('#oneMenu_'+subsystemId+' a[main-menu-index]:last').trigger('click');
        }
    });
}

/**
 * 针对导航栏或者是左边竖向菜单的点击事件，初始化树形菜单
 * 处理步骤：
 * 2、组织面包屑信息，根据模块、一级、二级、三级进行展示组织
 * 3、更新菜单栏大标题，对应一级菜单名称
 * 4、解析二级菜单，设置二级菜单的样式、事件，处理二级菜单没有三级菜单的情况
 * 5、处理三级菜单样式、事件，设置点击事件
 * 6、默认打开第一个可点击菜单
 */
function oneMenuClickHandle(obj,mainMenuObj,modelName){
    //4、解析二级菜单，设置二级菜单的样式、事件，处理二级菜单没有三级菜单的情况
    var secondMenuChildren = mainMenuObj.children;
    $('#secondMenuTree').empty();
    if(secondMenuChildren.length>0){
        var openFirstMenu = false;
        for(var i=0;i<secondMenuChildren.length;i++){
            secondMenuIndex = i;
            //获取二级菜单对象和二级菜单对应的三级菜单对象
            var secondMenuObj = secondMenuChildren[secondMenuIndex];
            var thirdMenuChildren = secondMenuObj.children;

            //组织二级菜单样式
            var secondMenu = $('<li class="Blind "/>');
            var secondMenuSpan = $('<span class="flip"/>');
            var thridMenuDiv = $('<div class="panel"/>');
            //处理第一个二级菜单样式
            if(secondMenuIndex==0){
                $(secondMenuSpan).addClass('flip-current');
                $(thridMenuDiv).addClass('panel-current');
            }
            //如果没有三级菜单，则需要对二级菜单增加点击事件，并独立处理样式
            if(thirdMenuChildren.length<1){
                $('<a href="#" class="secondMenuLink" second-menu-index="'+secondMenuIndex+'" second-menu-link="'+secondMenuObj.link+'" second-menu-model="'+mainMenuIndex+'"><i class="icon-file-alt"></i>'+secondMenuObj.text+'</a>').appendTo(secondMenuSpan);
                $(secondMenuSpan).appendTo(secondMenu);
                $(secondMenuSpan).removeClass("flip");
                $(secondMenuSpan).addClass("flipwhite");
            }else{
                //如果有三级菜单则需要对二级菜单增加小箭头图标，该小箭头适用于收缩三级菜单的用途
                $('<i class="icon-folder-close-alt"></i>'+secondMenuObj.text+'<i class="icon_leftmenu01"></i>').appendTo(secondMenuSpan);
                $(secondMenuSpan).appendTo(secondMenu);
            }
            $('#secondMenuTree').append(secondMenu);

            //解析三级菜单
            if(thirdMenuChildren.length>0){
                //开始设置三级菜单的迭代样式
                var thridMenuDivUL = $('<ul id="thirdMenuTree-'+secondMenuIndex+'" class="thirdMenuTree"/>');
                for(var j=0;j<thirdMenuChildren.length;j++){
                    var thirdMenu = $('<li/>');
                    if(!openFirstMenu){
                        $(thirdMenu).attr('class','current');
                        openFirstMenu = true;
                    }
                    //组织三级菜单点击超链接
                    $('<a href="#" id="third-menu-index-'+secondMenuIndex+'-'+j+'" second-menu-index="'+secondMenuIndex+'" third-menu-index="'+secondMenuIndex+'0000'+j+'" third-menu-link="'+thirdMenuChildren[j].link+'" third-menu-model="'+mainMenuIndex+'">' +
                    '<i class="icon-file-alt"></i>'+
                    thirdMenuChildren[j].text+'</a>').appendTo(thirdMenu);
                    $(thirdMenu).appendTo(thridMenuDivUL);
                }
                //组织完成三级菜单
                $(thridMenuDiv).append(thridMenuDivUL);
                $(thridMenuDiv).appendTo(secondMenu)
            }
        }
        //二级菜单点击事件(没有三级菜单的二级菜单点击事件)
        $('.secondMenuLink').click(function(){
            $(this).parent().removeClass('flip-current');
            var secondIndex = $(this).attr('second-menu-index');
            updateBreadcrumb(modelName,mainMenuObj.text, mainMenuObj.children[secondIndex].text,null);

            //清空三级菜单的选择样式
            $(this).parent().parent().parent().find('[class*="current"]').each(function(){
                $(this).removeClass('current');
            });

            //重新设置二级菜单的选择样式，删掉原有，设置当前
            $('i[class*="icon_leftmenuSecond"]').each(function(){
                $(this).remove();
            });
            $('span[class*="current"]').each(function(){
                $(this).removeClass('current');
            });
            //$(this).append('<i class="icon_leftmenuSecond"></i>');
            $(this).parent().addClass('current');
            openMenuResource($(this).attr('second-menu-link'),$(this).attr('second-menu-model'));
        });
        //三级菜单的点击事件
        $('.thirdMenuTree li a[third-menu-index]').click(function(){
            var secondIndex = $(this).attr('second-menu-index');
            updateBreadcrumb(modelName,mainMenuObj.text, mainMenuObj.children[secondIndex].text,$(this).text());

            //重新设置三级菜单的选择样式，删掉原有，设置当前
            $(this).parent().parent().find('[class="current"]').each(function(){
                $(this).removeClass('current');
            });
            $(this).parent().addClass('current');

            //清空二级菜单的选择样式
            $('i[class*="icon_leftmenuSecond"]').each(function(){
                $(this).remove();
            });
            $('span[class*="current"]').each(function(){
                $(this).removeClass('current');
            });
            openMenuResource($(this).attr('third-menu-link'),$(this).attr('third-menu-model'));
        });
        //默认打开第一个菜单，首先判断是否存在三级菜单，如果存在则打开三级菜单，如果不存在则打开二级菜单
        if($('#third-menu-index-0-0') && $('#third-menu-index-0-0').text()){
            $('#third-menu-index-0-0').trigger('click');
        }else{
            $('.secondMenuLink:first').parent().removeClass('flip-current');
            //$('.secondMenuLink:first').append('<i class="icon_leftmenuSecond"></i>');
            $('.secondMenuLink:first').trigger('click');
        }
        //根据配置信息确定是否默认展开左侧菜单
        if(!leftMenuVisible){
            $("#displaynavbar").addClass("open");
            $("body").addClass("big-page");
        }
    }
    menuCurrent();
    menuHandle();
    resizeMainFrame();
}

function openMenuResource(linkstr, model) {
    if (linkstr != null) {
        linkstr = linkstr.replace("r:","");
        $.get(portalUrl+"/config/resource/open?link=" + linkstr, function (result) {
            var resourceUrl = buildLinkUrl(result.resourceUrl);
            if (resourceUrl && resourceUrl.indexOf("MapGtis") == -1) {
                if (resourceUrl.indexOf('?') > -1) {
                    resourceUrl += '&rid=' + linkstr;
                } else {
                    resourceUrl += '?rid=' + linkstr;
                }
            }
            $('#mainFrame').attr("src", resourceUrl);
        });
    }
}

/**
 * 更新面包屑
 * @param mainMenu
 * @param secondMenu
 * @param thirdMenu
 */
function updateBreadcrumb(model,mainMenu,secondMenu,thirdMenu){
    $('#breadcrumb').empty();
    $('#breadcrumb').append('<i class="icon-home icon-large"></i>&nbsp;'+model);
    if(thirdMenu!=null&&thirdMenu!=''){
        $('#breadcrumb').append('<span class="c-999 en"><icon class="icon-angle-right icon-large"></icon></span>');
        $('#breadcrumb').append(mainMenu);
        $('#breadcrumb').append('<span class="c-999 en"><icon class="icon-angle-right icon-large"></icon></span>');
        $('#breadcrumb').append(secondMenu);
        $('#breadcrumb').append('<span class="c-999 en"><icon class="icon-angle-right icon-large"></icon></span>');
        //$('#breadcrumb').append('<span class="c-666">'+thirdMenu+'</span>');
        $('#breadcrumb').append(thirdMenu);
    }else if(secondMenu!=null&&secondMenu!=''){
        $('#breadcrumb').append('<span class="c-999 en"><icon class="icon-angle-right icon-large"></icon></span>');
        $('#breadcrumb').append(mainMenu);
        $('#breadcrumb').append('<span class="c-999 en"><icon class="icon-angle-right icon-large"></icon></span>');
        //$('#breadcrumb').append('<span class="c-666">'+secondMenu+'</span>');
        $('#breadcrumb').append(secondMenu);
    }else if(mainMenu!=null&&mainMenu!=''){
        $('#breadcrumb').append('<span class="c-999 en"><icon class="icon-angle-right icon-large"></icon></span>');
        $('#breadcrumb').append(mainMenu);
    }
}

/**
 * 显示新建任务窗体
 * @param wdid
 * @param createUrl
 * @param width
 * @param height
 */
function showCreateWindow(wdid, createUrl, width, height) {
    if(createUrl===null||createUrl===''){
        var url = portalUrl+"/taskCenter/workflowDefinition?wdid="+wdid;
        $.get(url,function(result){
            initializeCreateTaskForm(result);
            $('#taskModal').modal({show:true});
        });
    }else{
        var strurl="";
        if (createUrl.indexOf("?")>0)
            strurl=createUrl+"&wdid=" + wdid;
        else
            strurl=createUrl+"?wdid=" + wdid;
        var returnValue=showWindow(convertURL(strurl),null,width,height);
        if(returnValue!=null&&returnValue!=''){
            if (typeof(returnValue)=="string"){
                if(returnValue.indexOf("refresh,") < 0){
                    returnValue = "refresh" + "," + returnValue;
                }
                openTaskWin(returnValue);
            }
        }
    }
}
/**
 * 初始化新建任务窗体
 * @param workflowMap
 */
function initializeCreateTaskForm(workflowMap){
    $('#wdid').val(workflowMap.workflowDefine.workflowDefinitionId);
    $('#instanceName').val("");
    $('#businessName').val(workflowMap.workflowDefine.businessVo.businessName);
    $('#workFlowName').val(workflowMap.workflowDefine.workflowName);
    $('#timeLimit').val(workflowMap.workflowDefine.timeLimit);
    $('#createTime').val(workflowMap.createTime);
    $('#userName').val(workflowMap.username);
    $('#proselect').val(workflowMap.workflowDefine.priority);
}

/**
 * ajax提交新建任务窗体内容
 */
function postCreateTask(){
    var queryString = $('#createTaskForm').formSerialize();
    $.post(portalUrl+'/taskCenter/createDefaultTask',queryString,function(result){
        if(result.success){
            $.cookie('portal_index_tasklist',"tasklist", { expires: 7, path: '/'});
            $('#taskModal').modal('hide');
            openTaskWin("refresh" + "," + result.taskId);
        }else{
            alert(result);
        }
    });
}

/**
 * 打开某个待办任务
 * @param returnstr
 */
function openTaskWin(returnstr){
    if (!returnstr) return;
    if (typeof(returnstr)=="object") return ;
    var str=returnstr.split(",");
    if (str[0]=='refresh'){
        if (str[1]!=""){
            var url=platform_url+"/taskhandle.action?taskid=" + str[1];
            if(handleStyle && handleStyle == "true"){
                url = portalUrl+'/taskHandle?taskid=' + str[1];
            }
            url = convertURL(url);
            var w_width=screen.availWidth-10;
            var w_height= screen.availHeight-32;
            window.open(url, "_task", "left=1,top=0,height="+w_height+",width="+w_width+",resizable=yes,scrollbars=yes");
        }
    }
}

/**
 * 打开某个菜单
 * @param mainMenu
 * @param secondMenu
 * @param thirdMenu
 */
function openMenu(mainMenu,secondMenu,thirdMenu){
    $('#navMenu li a:contains("'+mainMenu+'")').trigger('click');
    $('#secondMenuTree li a:contains("'+secondMenu+'")').trigger('click');
    $('#thirdMenuTree li a:contains("'+thirdMenu+'")').trigger('click');
}


