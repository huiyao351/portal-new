var tagSplitUrl,tagSplitCountUrl,refreshTimer,defaultLimit=10;
var canUseWinHeight;//根据页面可用高度
var detailTableDivHeight= 250;
//解决ie低版本浏览器获取获取ajax数据失败的问题
jQuery.support.cors = true;
$(function(){
    // #tab_demo 父级id
    // #tab_demo .tabBar span 控制条
    // #tab_demo .tabCon 内容区
    // click 事件 点击切换，可以换成mousemove 移动鼠标切换
    // 1	默认第2个tab为当前状态（从0开始）
    $.Huitab("#tab_demo2 .tabBar2 span","#tab_demo2 .tabCon2","current","click","0");
    //$.Huitab("#tab_demo3 .tabBar3 span","#tab_demo3 .tabCon3","current","click","0");
    $.Huitab("#tab_demo4 .tabBar4 span","#tab_demo4 .tabCon4","current","click","0");
    //处理分页默认加载多少行数据
    //首先根据分辨率判断，之后根据是否有缓存来判断
    //<!-- 设置显示条数 当前一页显示（1280×800）6条、8条（1920×1080）10条 、14条 -->
    //对应的可用高度分别是800以上可显示10条，700以上可显示8条，以下就是6条了
    try{
        canUseWinHeight=document.body.scrollHeight;//根据页面可用高度;
        var listHeight = getListInitHeight();//根据页面可用高度
        var rows = parseInt(listHeight/50);
        if(rows<6){
            defaultLimit = 6
        }else if(rows >= 10 && rows < 14){
            defaultLimit = 10;
        }else if(rows >= 14 && rows < 20){
            defaultLimit = 14;
        }else {
            defaultLimit = 20;
        }
        var portal_task_defaultLimit = $.cookie('portal_task_defaultLimit');
        if(portal_task_defaultLimit){
            defaultLimit = portal_task_defaultLimit;
        }

        resizeDetailTableDiv(10,true);
    }catch(e){
    }
    $('.taskListPageSize').val(defaultLimit);
});

function getListInitHeight(){
    var screenHeight = window.screen.height;
    if(screenHeight<canUseWinHeight) canUseWinHeight = screenHeight;
    var newTaskHeight = $('#NewTask').height();
    var taskTabHeight = $('#taskTab').height();
    var workListToolsHeight = $($('.workListTools')[0]).height();
    var taskPaginationHeight = $($('.taskPagination')[0]).height();
    var listHeight = canUseWinHeight - newTaskHeight - taskTabHeight - workListToolsHeight - taskPaginationHeight -38;
    return listHeight;
}
function resizeDetailTableDiv(time,delNewTask){
    if(typeof time != "undefined" && !time){
        time = 50;
    }
    //计算任务中心数据表格显示的高度
    setTimeout(function(){
        var newTaskHeight = $('#NewTask').height();
        var taskTabHeight = $('#taskTab').height();
        var hrefLink = $('#taskTab>span[class*="current"]').attr("id");
        var workListToolsHeight = $(hrefLink+' .workListTools').height();
        var taskPaginationHeight = $(hrefLink+' .taskPagination').height();
        var listHeight = canUseWinHeight - taskTabHeight - workListToolsHeight - taskPaginationHeight -36;
        if(!(typeof delNewTask != "undefined" && !delNewTask)){
            listHeight = listHeight - newTaskHeight - 0;
        }else{
            //listHeight = listHeight - 10;
        }
        if(Math.abs(detailTableDivHeight-listHeight) >4){
            detailTableDivHeight = listHeight;
        }
        $('.detailTableDiv').height(detailTableDivHeight);
    },time);
}

$(function() {
    if(window.chrome){$('.slider li').css('background-size', '100% 100%');}
    $('.slider').unslider({
        speed: 500,               //  滚动速度
        delay: 3000,              //  动画延迟
        complete: function() {},  //  动画完成的回调函数
        keys: true,               //  启动键盘导航
        fluid: true               //  支持响应式设计
    });
    // 左右控制js
    var unslider = $('.slider').unslider();
    $('.unslider-arrow').click(function(){
        var fn = this.className.split(' ')[1];
        unslider.data('unslider')[fn]();
    });
});
/*调用jQueryUI实现拖动*/
$(function() {
    $('.draggable').draggable();
});
/*   收缩  */
$(document).ready(function(){
    $(".flip").click(function(){
        var upClass = "icon-double-angle-up";
        var downClass = "icon-double-angle-down";
        var showCreateList = false;
        if($("#flip i:first").hasClass(upClass)){
            $("#flip i:first").removeClass(upClass);
            $("#flip i:first").addClass(downClass);
        }else{
            $("#flip i:first").removeClass(downClass);
            $("#flip i:first").addClass(upClass);
            showCreateList = true;
        }
        $("#NewTask").slideToggle("fast",function(){
            resizeDetailTableDiv(10,showCreateList);
        });
    });
});

function showCreateWindow(wdid,url,width,height){
    $(window.parent)[0].showCreateWindow(wdid,url,width,height);
}

$(function(){
    tagSplitUrl = platform_url+"/tag/SplitData.action";
    tagSplitCountUrl = platform_url+"/tag/SplitData!RowCount.action";

    /*var firstTabId = $('#tab_demo4 .tabCon4:first').attr("id");
    $('#advancedSearchModal form #curTabId').val(firstTabId);

    var firstGroupId = $('#tab_bs span:first').attr("groupId");
    $('#advancedSearchModal form #curGroupId').val(firstGroupId);*/
    /**
     * 工作流页面选项卡切换事件
     * 用于切换待办、已办、项目列表
     */
    $('#taskTab>span').click(function(){
        $('.taskListPageSize').val(defaultLimit);

        var param ={
            start:0,
            limit:defaultLimit,
            totalCount:0,
            queryString:'{"_query":"_query"}'
        };
        var hrefLink = $(this).attr('id');
        $('.pagination>ul>li>input[class*="currentPage"]').val(1);
        loadWorkflowList(hrefLink,param);

        //清空查询条件
        var tabPane = $('#tab_demo4 div[class*=searchBar]');
        clearQueryForm(tabPane);
        //$('#tab_demo4 input[type=checkbox]').attr('checked',false);
        $('.detailTableDiv').height(detailTableDivHeight);

        //选项卡切换时，只有项目列表（权限列表）时，才会显示部门查询框，并且将任务描述栏变短，
        //反之隐藏部门查询框，拉长任务描述栏
        //query-param-row表示正行显示，project-param标示只有项目列表才显示
        if(hrefLink == "#projectListTab"){
            $('.project-param').show();
            $('.query-param-row').removeClass("col-10");
            $('.query-param-row').addClass("col-4");
        }else{
            $('.project-param').hide();
            $('.query-param-row').removeClass("col-4");
            $('.query-param-row').addClass("col-10");
        }
    });

    //默认打开待办任务
    $('#taskTab>span[id="#taskListTab"]').trigger('click');

    /**
     * 高级搜索点击事件,弹出当前tab的高级搜索框
     */
    $(".advancedBtn").click(function(){
        $('#advancedSearchModal').modal({show:true});
        //获取当前tab页的div的id，也就是用于后期查询待办、已办、项目列表用途
        var tabPaneId = $(this).parents('div[class*=tabCon4]').attr('id');
        //赋值给隐藏的高级查询框中控件
        $('#advancedSearchModal form #curTabId').val(tabPaneId);
        //如果是已办任务，隐藏工作流状态查询条件
        if(tabPaneId == 'taskOverListTab'){
            $('#advancedSearch_TASK_STATE').hide();
        }else{
            $('#advancedSearch_TASK_STATE').show();
        }
    });

    /**
     * 超期按钮的点击事件
     */
    $('#overtimeCheck').change(function(){
        if($(this).is(':checked')){
            $('#taskState').val("1");
        }else{
            $('#taskState').val("");
        }
        var param ={
            start:0,
            limit:defaultLimit,
            totalCount:0
        };
        var hrefLink = $('#taskTab>span[class*="current"]').attr("id");
        param.queryString=getAllQueryString($('#advancedSearchModal'));
        loadWorkflowList(hrefLink,param);
    });

    if(taskState && taskState == 1){
        $('#overtimeCheck').attr("checked","true");
    }

    /**
     * 查询框查询按钮事件
     */
    $('button[class*=button-search]').click(function(){
        $('#advancedSearchModal').modal('hide');
        var param ={
            start:0,
            limit:defaultLimit,
            totalCount:0
        };
        var hrefLink = $('#taskTab>span[class*="current"]').attr("id");
        param.queryString=getAllQueryString($('#advancedSearchModal'));
        loadWorkflowList(hrefLink,param);
    });
    /**
     * 高级搜索框关闭事件
     */
    $(".advancedSearchPop>h4[class*=advancedSearchLab]>a[class*=icon-remove]").click(function(){
        var tabPane = $(this).parent().parent().parent().find('div[class*=advancedSearchPop]');
        $(tabPane).hide();
    });

    /**
     * 全选按钮事件
     */
    $('.ckbSelectAll').change(function(){
        $(this).parents(".table").children('tbody').find('input[type=checkbox]').prop("checked",$(this).is(':checked'));
    });
    $('.ckbSelectAll').change(function(){
        //$(this).parent().parent().parent().parent(".table").children('tbody').find('input[type=checkbox]').prop("checked",$(this).is(':checked'));
    });

    $(".modal-backdrop").click(function(){
        $(".SearchFloat").hide();
    });

    /**
     * 以下是分页功能
     */
    /**
     * 分页显示多少条记录
     */
    $('.taskListPageSize').change(function(){
        defaultLimit = $(this).val();
        var param ={
            start:0,
            limit:defaultLimit,
            totalCount:0,
            queryString:getAllQueryString($('#advancedSearchModal'))
        };
        $(this).parent().next().next().children('.currentPage').val(1);
        loadByPagination($(this).parent().parent().parent().parent(),param);
        $.cookie('portal_task_defaultLimit',defaultLimit, { expires: 7, path: '/'});
        //$(this).parents(".table").children('tbody').find('input[type=checkbox]').prop("checked",$(this).is(':checked'));
    });

    $("#shrinkBtn").click(function() {
        $("#createTaskDiv").slideToggle("normal",function(){
            $('#shrinkBtn').toggleClass("shrinkbtn_up");
            $('#shrinkBtn').toggleClass("shrinkbtn_down");
        });
    });

    /**
     * 首页点击事件
     */
    $('.firstPage').click(function(){
        var param ={
            start:0,
            limit:defaultLimit,
            totalCount:0,
            queryString:getAllQueryString($('#advancedSearchModal'))
        };
        $(this).parent().next().next().children('.currentPage').val(1);
        loadByPagination($(this).parent().parent().parent().parent(),param);
    });
    /**
     * 上一页点击事件
     */
    $('.prePage').click(function(){
        var currentPage = $(this).parent().next().children('.currentPage').val();
        if(currentPage<=1) return;
        var prePage = parseInt(currentPage)-2;
        var start =prePage*defaultLimit;
        var param ={
            start:start,
            limit:defaultLimit,
            totalCount:0,
            queryString:getAllQueryString($('#advancedSearchModal'))
        };
        $(this).parent().next().children('.currentPage').val((parseInt(prePage)+1));
        loadByPagination($(this).parent().parent().parent().parent(),param);
    });
    /**
     * 下一页点击事件
     */
    $('.nextPage').click(function(){
        var totalPage = parseInt($(this).parent().prev().children('.totalPage').val());
        var currentPage =  parseInt($(this).parent().prev().children('.currentPage').val());
        if(currentPage>=totalPage)return;
        var nextPage = currentPage;
        var start =nextPage*defaultLimit;
        var param ={
            start:start,
            limit:defaultLimit,
            totalCount:0,
            queryString:getAllQueryString($('#advancedSearchModal'))
        };
        $(this).parent().prev().children('.currentPage').val(nextPage+1);
        loadByPagination($(this).parent().parent().parent().parent(),param);
    });
    /**
     * 最后一页点击事件
     */
    $('.lastPage').click(function(){
        var page = $(this).parent().prev().prev().children('.totalPage').val();
        page = (page!=null&&page!='')?parseInt(page):0;
        var start =(page-1)*defaultLimit;
        $(this).parent().prev().prev().children('.currentPage').val(parseInt(page));
        var param ={
            start:start,
            limit:defaultLimit,
            totalCount:0,
            queryString:getAllQueryString($('#advancedSearchModal'))
        };
        loadByPagination($(this).parent().parent().parent().parent(),param);
    });

    $('form').submit(function(){
       return false;
    });

    //初始化高级查询中工作流定义下拉框
    loadWorkflowDefinitionList($('select[name=WORKFLOW_DEFINITION_ID]'),'');
    //高级查询中工作流业务查询下拉框事件
    $('select[name=BUSINESS_ID]').change(function(){
        var businessId = $(this).val();
        loadWorkflowDefinitionList($(this).parents('.row-fluid').find('select[name=WORKFLOW_DEFINITION_ID]'),businessId);
    });

    /**
     * 转发按钮操作
     */
    $('#turnTaskBtn').click(function () {
        var selectItems = getSelectedItems($(this).parents('.tabCon4'),'taskId');
        if(selectItems==null||selectItems.length==0){
            alert('请选择转发任务！');
            return;
        }else{
            if (!confirm("是否确认进行转发！")){
                return;
            }
            for(var i=0;i<selectItems.length;i++){
                var taskId=selectItems[i].taskId;
                var getUrl=platform_url+"/task.action?taskid=" + taskId;
                $.ajax({
                    url: getUrl,
                    async: false,
                    success:function(msg){
                        if (msg!="" && msg!="true"){
                            alert(msg);
                        }else{
                            refreshTaskList();
                        }
                    }
                })
            }
        }
    });
    /**
     * 解除挂起操作
     */
    $('#unpostTaskBtn').click(function(){
        var selectItems = getSelectedItems($(this).parents('.tabCon4'),'taskId');
        if(selectItems==null||selectItems.length==0){
            alert('请选择解除挂起任务！');
            return;
        }else{
            if (!confirm("是否确认进行解除挂起！")){
                return;
            }
            for(var i=0;i<selectItems.length;i++){
                var taskId=selectItems[i].taskId;
                var getUrl=platform_url+"/task!unpost.action?taskid=" + taskId;
                $.ajax({
                    url: getUrl,
                    async: false,
                    success:function(msg){
                        if(msg == "true"){
                            refreshTaskList();
                        }else{
                            alert(msg);
                        }
                    }
                })
            }
        }
    });
    /**
     * 挂起操作
     */
    $('#postTaskBtn').click(function(){
        var selectItems = getSelectedItems($(this).parents('.tabCon4'),'taskId');
        if(selectItems==null||selectItems.length==0){
            alert('请选择挂起任务！');
            return;
        }else if(selectItems.length>1) {
            alert('请选择一条任务进行挂起！');
            return;
        }else if(selectItems[0].workflowState==3){
            alert('流程已挂起，请先解挂！');
            return;
        }else {
            $('#postTaskModal textarea[name=remark]').val("");
            var taskId = selectItems[0].taskId;
            $('#postTaskModal input[class*=postBtn]').click(function(){
                var postInfo = $('#postTaskModal textarea[name=remark]').val();
                $('#postTaskModal').modal('hide');
                showProgressWindow('任务挂起中...');
                $.ajax({
                    url:platform_url+"/task!post.action?a=1&taskid=" + taskId,
                    type:'post',
                    data:{remark:postInfo},
                    success:function(msg){
                        hideProgressWindow();
                        refreshTaskList();
                    }
                });

            });
            $('#postTaskModal').modal({show:true});
        }
    });
    /**
     * 退回操作
     */
    $('#backTaskBtn').click(function(){
        var selectItems = getSelectedItems($(this).parents('.tabCon4'),'taskId');
        if(selectItems==null||selectItems.length==0){
            alert('请选择退回任务！');
            return;
        }else{
            if (!confirm("是否确认进行退回！")){
                return;
            }
            for(var i=0;i<selectItems.length;i++){
                var taskId=selectItems[i].taskId;
                var getUrl=platform_url+"/taskturnback!BackTaskBatch.action?taskid=" + taskId;
                $.ajax({
                    url: getUrl,
                    async: false,
                    success:function(msg){
                        if (msg!="" && msg!="true"){
                            alert(msg);
                        }else{
                            refreshTaskList();
                        }
                    }
                })
            }
        }
    });

    var _delTasks = new Array();
    /**
     * 删除操作
     */
    $('#delTaskBtn').click(function(){
        var selectItems = getSelectedItems($(this).parents('.tabCon4'),'taskId');
        if(selectItems==null||selectItems.length==0){
            alert('请选择删除任务！');
            return;
        }else{
            if (!confirm("是否确认进行删除！")){
                return;
            }
            showProgressWindow('删除任务...');
            for(var i=0;i<selectItems.length;i++){
                _delTasks.push(selectItems[i]);
            }
            delTask();
        }
    });

    /**
     * 删除任务
     */
    function delTask(){
        var task = _delTasks.pop();
        if(task!=null){
            var taskId=task.taskId;
            var delUrl=platform_url+"/task!del.action?taskid=" + taskId;
            $.ajax({
                type:"POST",
                url:delUrl,
                success:delTask,
                error:delTask
            });
        }else{
            hideProgressWindow();
            refreshTaskList();
        }
    }

    /**
     * 重办项目操作
     */
    $('.restartProjectBtn').click(function(){
        var selectItems = getSelectedItems($(this).parents('.tabCon4'),'wiid');
        if(selectItems==null||selectItems.length==0){
            alert('请选择重办项目！');
            return;
        }else{
            if (!confirm("是否确认重新办理！")){
                return;
            }
            var msgs='';
            for(var i=0;i<selectItems.length;i++){
                var wiid=selectItems[i].wiid;
                var workflowName = selectItems[i].workflowName;
                var restartUrl=platform_url+"/task!restartProject.action?wiid=" + wiid;
                $.ajax({
                    type:"POST",
                    url:restartUrl,
                    async: false,
                    success:function(msg){
                        msg = msg.replace(/(^\s*)|(\s*$)/g,"");
                        if (msg == 'true' || msg=='1'){
                            msgs += "【" + workflowName + "】" + ":重新办理成功！";
                        }else{
                            msgs += "【" + workflowName + "】" + ":重新办理失败，请联系管理员！";
                        }
                    },
                    error:function(msg){
                        if (msg){
                            msgs += "[" + workflowName + "]" + ":" + "重新办理失败！";
                        }
                    }
                });
            }
            alert(msgs);
            refreshProjectList();
        }
    });


    var _delProjects = new Array();
    /**
     * 删除项目操作
     */
    $('.delProjectBtn').click(function(){
        var selectItems = getSelectedItems($(this).parents('.tabCon4'),'wiid');
        if(selectItems==null||selectItems.length==0){
            alert('请选择删除项目！');
            return;
        }else{
            if (!confirm("是否确认进行删除！")){
                return;
            }
            showProgressWindow('删除任务...');
            for(var i=0;i<selectItems.length;i++){
                _delProjects.push(selectItems[i]);
            }
            delProject();
        }
    });

    /**
     * 删除项目
     */
    function delProject(){
        var project = _delProjects.pop();
        if(project!=null){
            var wiid=project.wiid;
            var delUrl=platform_url+"/task!delProject.action?wiid=" + wiid;
            $.ajax({
                type:"POST",
                url:delUrl,
                success:delProject,
                error:delProject
            });
        }else{
            hideProgressWindow();
            refreshProjectList();
        }
    }

    /**
     * 手动输入页数操作
     */
    $('.currentPage').keydown(function(e){
        if(e.keyCode===13&&$(this).val()!=null&&$(this).val()!=''){
            var currentPageNum = parseInt($(this).val());
            var totalPageNum = parseInt($(this).parent().children('.totalPage').val());
            if(currentPageNum>=1&&currentPageNum<=totalPageNum) {
                var startPage = (currentPageNum-1) * defaultLimit;
                var param = {
                    start: startPage,
                    limit: defaultLimit,
                    totalCount: 0,
                    queryString: getAllQueryString($('#advancedSearchModal'))
                };
                loadByPagination($(this).parent().parent().parent(), param);
            }
            return false;
        }
    }).keyup(function(e){
        $(this).val($(this).val().replace(/\D/g,''));
    });
    refreshTimer = setTaskListTimer();

    $('#autoTurnTaskBtn').click(function () {
        var selectItems = getSelectedItems($(this).parents('.tabCon4'),'taskId');
        if(selectItems==null||selectItems.length==0){
            alert('请选择转发任务！');
            return;
        }else{
            if (!confirm("是否确认进行自动转发！")){
                return;
            }
            var paramString = "[";
            for(var i=0;i<selectItems.length;i++){
                var wiid = selectItems[i].wiid;
                var taskId=selectItems[i].taskId;
                paramString += "{'proid':'"+wiid+"','taskid':'"+taskId+"'},";
            }
            paramString += "{}]";
            var getUrl=portalUrl+"/taskCenter/autoTurn";
            $.ajax({
                url: getUrl,
                type:'post',
                data:{paramString:paramString},
                async: false,
                success:function(data){
                    alert(data.msg);
                    refreshTaskList();
                }
            })
        }
    });
});

/**
 * 定时刷新操作
 * @returns {number}
 */
function setTaskListTimer(){
    return setInterval(function(){
        try{
			var portal_index_tasklist = $.cookie('portal_index_tasklist');
			if(portal_index_tasklist && portal_index_tasklist == 'tasklist'){
                $.cookie('portal_index_msg',"msgcount", { expires: 7, path: '/'});
				var currentPage = parseInt($($('#taskListPagination>div>ul>li>input[class*="currentPage"]')[0]).val());
		        var param ={
		            start:(currentPage-1)*defaultLimit,
		            limit:defaultLimit,
		            totalCount:0
		        };
		        param.queryString=getAllQueryString($('#advancedSearchModal'));
		        param.splitInfo = taskListSplitInfo;
		        loadTaskList(tagSplitUrl,param);
			}
		}catch(e){
		}
        $.cookie('portal_index_tasklist',null, { expires: -1, path: '/'});
    },1000);
}
/**
 * 针对查询框中工作流定义下拉框的初始化方法
 * @param elements
 * @param businessId
 */
function loadWorkflowDefinitionList(elements,businessId){
    var url=portalUrl+'/taskCenter/getWorkflowDefinitions';
    var param={
        businessId:businessId
    };
    $.post(url,param,function(result){
        result.unshift({workflowName:'所有',workflowDefinitionId:''});
        var data ={
            workflowDeinfitionList:result
        }
        var html = template('queryWorkflowDefinitionList', data);
        $(elements).each(function(){
            $(this).empty();
            $(this).append(html);
        });
    });
}
/**
 * 加载待办任务方法
 * @param url
 * @param param
 */
function loadTaskList(url,param){
    $.post(url,param,function(result){
        var data={
            taskList:handleTaskListData(result.rows)
        };
        var html = template('taskList', data);
        $('#taskListTable').empty();
        $('#taskListTable').append(html);
        loadSplitCount('#taskListPagination',tagSplitCountUrl,{splitInfo:param.splitInfo,queryString:param.queryString});
        taskListTableLoadedEvent($('#taskListTable'));
    });

}
/**
 * 分页栏的初始化操作
 * @param element
 * @param url
 * @param param
 */
function loadSplitCount(element,url,param){
    $.post(url,param,function(result){
        var recordCount = parseInt(result);
        var totalPage = Math.ceil(recordCount/defaultLimit);
        if(recordCount){
            $(element+'>div>ul>li>input[class=recordCount]').val(recordCount);
        }
        if(totalPage){
            $(element+'>div>ul>li>input[class=totalPage]').val(totalPage);
        }
    });
}

/**
 * 加载已办任务数据
 * @param url
 * @param param
 */
function loadTaskOverList(url,param){
    $.post(url,param,function(result){
        var data={
            taskOverList:handleTaskOverListData(result.rows)
        };
        var html = template('taskOverList', data);
        $('#taskOverListTable').empty();
        $('#taskOverListTable').append(html);
        loadSplitCount('#taskOverListPagination',tagSplitCountUrl,{splitInfo:param.splitInfo,queryString:param.queryString});
        taskOverListTableLoadedEvent($('#taskOverListTable'));
    });
}

/**
 * 加载参与项目列表数据
 * @param url
 * @param param
 */
function loadProjectPerformerList(url,param){
    $.post(url,param,function(result){
        var data={
            projectList:handleProjectListData(result.rows)
        };
        var html = template('projectList', data);
        $('#projectPerformerListTable').empty();
        $('#projectPerformerListTable').append(html);
        $('.ckbSelectAll').attr("checked",false);
        loadSplitCount('#projectPerformerListPagination',tagSplitCountUrl,{splitInfo:param.splitInfo,queryString:param.queryString});
        projectListTableLoadedEvent($('#projectListTable'));
    });
}

/**
 * 加载项目列表数据
 * @param url
 * @param param
 */
function loadProjectList(url,param){
    $.post(url,param,function(result){
        var data={
            projectList:handleProjectListData(result.rows)
        };
        var html = template('projectList', data);
        $('#projectListTable').empty();
        $('#projectListTable').append(html);
        $('.ckbSelectAll').attr("checked",false);
        loadSplitCount('#projectListPagination',tagSplitCountUrl,{splitInfo:param.splitInfo,queryString:param.queryString});
        projectListTableLoadedEvent($('#projectListTable'));
    });
}
/**
 * 打开待办任务项目
 * @param taskId
 * @param workflowState
 */
function openTask(taskId,workflowState){
    if(workflowState!=3){
        var url = platform_url+'/taskhandle.action?taskid='+taskId;
        if(handleStyle && handleStyle == "true"){
            url = portalUrl+'/taskHandle?taskid='+taskId;
        }
        openWin(url,'_task');
    }else{
        alert('任务暂停中，请取消暂停后再查看！');
    }
}
/**
 * 打开项目列表项目
 * @param wiid
 * @param workflowState
 */
function openProject(wiid,workflowState){
    if(workflowState!=3) {
        var url = platform_url + '/projecthandle.action?wiid=' + wiid;
        if(handleStyle && handleStyle == "true"){
            url = portalUrl + '/projectHandle?wiid=' + wiid;
        }
        openWin(url, '_task');
    }else{
        alert('项目暂停中，请取消暂停后再查看！');
    }
}
/**
 * 打开已办任务项目
 * @param taskId
 */
function openTaskOver(taskId){
    var url = platform_url+'/taskoverhandle.action?taskid='+taskId;
    if(handleStyle && handleStyle == "true"){
        url = portalUrl+'/taskOverHandle?taskid='+taskId;
    }
    openWin(url,'_task');
}
/**
 * 窗体打开方法
 * @param url
 * @param name
 */
function openWin(url,name){
    var w_width=screen.availWidth-14;
    var w_height= screen.availHeight-66;
    window.open(url, name, "left=1,top=0,height="+w_height+",width="+w_width+",resizable=yes,scrollbars=yes");
}
/**
 * 待办任务数据解析
 * @param data
 * @returns {*}
 */
function handleTaskListData(data){
    if(!data){
        return null;
    }
    var selectedTaskIds = getSelectedTaskIds();
    for(var i=0;i<data.length;i++){
        var item = data[i];
        var isChecked = false;
        var taskId = data[i].ASSIGNMENT_ID;
        if(selectedTaskIds!=null&&selectedTaskIds.length>0&&($.inArray(taskId,selectedTaskIds)>-1)){
            isChecked = true;
        }
        data[i].CHECKED = isChecked;
        //流程超期
        var isOver=false;
        var cDate=new XDate(currentDate);
        if (item.OVER_TIME && item.OVER_TIME!="") {
            var overTime = new XDate(item.OVER_TIME);
            if (overTime.valueOf()<cDate.valueOf())
                isOver=true;
        }
        //data[i].TIME_STATE 状态区分：0：正常，1：警告，2：超期
        if (item.TASK_OVER_TIME && item.TASK_OVER_TIME!="") {
            if(calenderServiceType==='wholeDay'){
                if(data[i].WORKFLOW_STATE==3){
                    data[i].OVER_TIME='-';
                    data[i].TASK_OVER_TIME='-';
                }else{
                    data[i].OVER_TIME=getDayDate(item.OVER_TIME);
                    data[i].TASK_OVER_TIME=getDayDate(item.TASK_OVER_TIME);
                }
                var taskOverTime = data[i].TASK_OVER_TIME;

                if(data[i].WORKFLOW_STATE==3){
                    data[i].OTHER_TIME = "-";
                }else{
                    $.ajax({
                        type:'post',
                        url:platform_url+'/calendar!calLeftTime.action?overTime='+taskOverTime,
                        async:false,
                        success:function(result){
                            var timeLeft = parseInt(result);
                            var otherTime = "";
                            if(timeLeft<0){
                                otherTime += "超期"+Math.abs(timeLeft)+ "天";
                                data[i].TIME_STATE=2;
                            }else if(timeLeft < 1){
                                otherTime += "剩余" + timeLeft + "天";
                                data[i].TIME_STATE=1;
                            }else {
                                otherTime += "剩余" + timeLeft + "天";
                                data[i].TIME_STATE=0;
                            }
                            data[i].OTHER_TIME = otherTime;
                        }
                    });
                }
            }else if(calenderServiceType==='default'){
                data[i].TASK_OVER_TIME=formatDate(item.TASK_OVER_TIME);

                var taskIsOver = false;
                var taskOverTime = new XDate(item.TASK_OVER_TIME);
                if (taskOverTime.valueOf()<cDate.valueOf()){
                    taskIsOver=true;
                }else{
                    taskIsOver=false;
                }

                var otherTime="";
                if (taskIsOver){
                    data[i].TIME_STATE=2;
                    otherTime="超期";
                }else{
                    otherTime="剩余";
                    data[i].TIME_STATE=0;
                }

                var hours=taskOverTime.diffHours(cDate);
                var days= Math.floor((Math.abs(hours)/24));
                var timeLimit = item.TIME_LIMIT;
                if(timeLimit!=null&&timeLimit.indexOf('.')>-1)
                    hours=(Math.abs(hours)-days*24).toFixed(1);
                else
                    hours=Math.ceil(Math.abs(hours)-days*24,0);
                if (hours==24){
                    days++;
                    hours=0;
                }
                if(days <= 0 && hours >0 && hours < 5){
                    data[i].TIME_STATE=1;
                }
                if (days>0)
                    otherTime=otherTime +days + "天";
                if (hours>0)
                    otherTime=otherTime +hours + "小时";
                if (days>9999){
                    otherTime="-";
                }
                data[i].OTHER_TIME = otherTime;

            }
        }
        data[i].Over = isOver;
        if(data[i].hasOwnProperty('BEGIN_TIME'))
            data[i].BEGIN_TIME=formatDate(item.BEGIN_TIME);
        if(data[i].hasOwnProperty('OVER_TIME'))
            data[i].OVER_TIME=formatDate(item.OVER_TIME);
        if(data[i].hasOwnProperty('CREATE_TIME'))
            data[i].CREATE_TIME=formatDate(item.CREATE_TIME);

    }

    return data;
}
/**
 * 已办任务数据解析
 * @param data
 * @returns {*}
 */
function handleTaskOverListData(data){
    for(var i=0;i<data.length;i++){
        var item = data[i];
        var isOver=false;
        var cDate=new XDate(item.FINISH_TIME);
        if (item.TASK_OVER_TIME && item.TASK_OVER_TIME!="") {
            var taskOverTime = new XDate(item.TASK_OVER_TIME);
            if (taskOverTime.valueOf()<cDate.valueOf())
                isOver=true;
        }
        data[i].Over = isOver;

        if(data[i].hasOwnProperty('FINISH_TIME'))
            data[i].FINISH_TIME=formatDate(item.FINISH_TIME);
        if(data[i].hasOwnProperty('CREATE_TIME'))
            data[i].CREATE_TIME=formatDate(item.CREATE_TIME);
        if(data[i].hasOwnProperty('FINISH_TIME_COUNT'))
            data[i].FINISH_TIME_COUNT=formatDate(item.FINISH_TIME_COUNT);
        if(data[i].hasOwnProperty('TASK_OVER_TIME'))
            data[i].TASK_OVER_TIME=formatDate(item.TASK_OVER_TIME);
        if(data[i].TASK_OVER_TIME==null||data[i].TASK_OVER_TIME=='')
            data[i].TASK_OVER_TIME='-';
    }
    return data;
}
/**
 * 项目列表数据解析
 * @param data
 * @returns {*}
 */
function handleProjectListData(data){
    if(data && data.length > 0){
        for(var i=0;i<data.length;i++){
            var item = data[i];
            var isOver=false;
            var cDate;
            if (item.OVER_TIME && item.OVER_TIME!="") {
                var overTime = new XDate(item.OVER_TIME);
                if (item.FINISH_TIME && item.FINISH_TIME!="") {
                    cDate=new XDate(item.FINISH_TIME);
                    if (overTime.valueOf() < cDate.valueOf())
                        isOver = true;
                }else{
                    cDate=new XDate(currentDate);
                    if (overTime.valueOf() < cDate.valueOf())
                        isOver = true;
                }
            }
            data[i].Over = isOver;
            var finish=false;
            if(data[i].hasOwnProperty('FINISH_TIME'))
                data[i].FINISH_TIME=formatDate(item.FINISH_TIME);
            if (data[i].FINISH_TIME && data[i].FINISH_TIME!=""){
                finish=true;
            }
            data[i].FINISH = finish;
            if(data[i].FINISH_TIME==null||data[i].FINISH_TIME=='')
                data[i].FINISH_TIME='正在办理中...';
            if(data[i].hasOwnProperty('CREATE_TIME'))
                data[i].CREATE_TIME=formatDate(item.CREATE_TIME);
            if(data[i].hasOwnProperty('FINISH_TIME_COUNT'))
                data[i].FINISH_TIME_COUNT=formatDate(item.FINISH_TIME_COUNT);
        }
    }
    return data;
}

/**
 * 菜单打开
 * @param mainMenu
 * @param secondMenu
 * @param thirdMenu
 */
function openMenu(mainMenu,secondMenu,thirdMenu){
    $(window.parent)[0].openMenu(mainMenu,secondMenu,thirdMenu);
}
/**
 * 分页栏的数据处理
 * @param element
 * @param param
 */
function loadByPagination(element,param){
    if($(element).attr('id')!=null&&$(element).attr('id')==='taskListPagination'){
        param.splitInfo = taskListSplitInfo;
        loadTaskList(tagSplitUrl,param);
    }else if($(element).attr('id')!=null&&$(element).attr('id')==='taskOverListPagination'){
        param.splitInfo = taskOverListSplitInfo;
        loadTaskOverList(tagSplitUrl,param);
    }else if($(element).attr('id')!=null&&$(element).attr('id')==='projectListPagination'){
        param.splitInfo = projectListSplitInfo;
        loadProjectList(tagSplitUrl,param);
    }else if($(element).attr('id')!=null&&$(element).attr('id')==='projectPerformerListPagination'){
        param.splitInfo = projectPerformerListSplitInfo;
        loadProjectPerformerList(tagSplitUrl,param);
    }
}
/**
 * 格式化日期
 * @param value
 * @returns {*}
 */
function formatDate(value){
    if(value && value!=''){
        if(value.indexOf('T')>-1)
           return value.replace('T',' ');
    }
    return value;
}
/**
 * 获取天数
 * @param value
 * @returns {*}
 */
function getDayDate(value){
    if(value && value!=''){
        if(value.indexOf('T')>-1)
            return value.substr(0,value.indexOf('T'));
    }
    return value;
}

/**
 * 通过ajax获取转发人
 */
function taskListTableLoadedEvent(element){
    $(element).find("li[type='turn']").each(function(){
        var taskid=$(this).attr("taskid");
        $.get(platform_url+"/taskhandle!getSenderUsers.action?taskid=" + taskid, function(result){
            result=result.replace(/(^\s*)|(\s*$)/g, "");
            if (result!="") {
                var users = result;
                if(result.length > 6){
                    users = users.substr(0,6)+"...";
                }
                result="<icon class='icon-user'></icon>&nbsp;<span title='"+result+"'>转发人：" + users + '</span>';
                $("li[taskid='" + taskid + "']").html(result);
            }else{
                $("li[taskid='" + taskid + "']").html("&nbsp;&nbsp;&nbsp;&nbsp;");
            }

        });
    });
}
/**
 * 通过ajax获取转发人
 * @param element
 */
function taskOverListTableLoadedEvent(element){
    taskListTableLoadedEvent(element);
    /*$(element).find("span[type='turn']").each(function(){
        var taskid=$(this).attr("taskid")
        if (taskid)
            $(this).load(platform_url+"/taskhandle!getSenderUsers.action?taskid=" + taskid);
    });*/
}

/**
 * 通过ajax获取当前活动
 * @param element
 */
function projectListTableLoadedEvent(element){
    $(element).find("span[type='activity']").each(function(){
        var wiid=$(this).attr("wiid")
        if (wiid)
            $(this).load(platform_url+"/projecthandle!getActivityNames.action?wiid=" + wiid);
    });
}
/**
 * 组织查询框数据字符串
 * */
function getAllQueryString(element){
    if(!element || element[0]){
        element = $('#advancedSearchModal');
    }
    var result = '{"_query":"_query"';


    var hrefLink = $('#taskTab>span[class*="current"]').attr("id");
    if(hrefLink){
        //处理查询栏中的工作流名称参数
        var keyword = $(''+hrefLink+' input[name="WORKFLOW_INSTANCE_NAME"]').val();
        if(keyword){
            result+=',"WORKFLOW_INSTANCE_NAME":"'+keyword+'"';
        }

        //如果不是项目列表查询，则把部门查询参数置空
        if(hrefLink!='#projectListTab'){
            $("#CREATE_ORGAN").val("");
        }
    }

    $(element).find('form').each(function(){
        $(this).find('input, select').each(function(){
            if (!this.name) return;
            if ('radio' === this.type) {
                result+= ',"'+this.name+'":"'+this.checked ? this.value : ''+'"';
            } else if ('checkbox' === this.type) {
                result+= ',"'+this.name+'":"'+this.is(':checked')+'"';
            } else {
                //result+= ',"'+this.name+'":"'+this.value+'"';
                if(this.value){
					result+= ',"'+this.name+'":"'+this.value.replace(/^\s\s*/, '').replace(/\s\s*$/, '')+'"';
				}
            }
        });
    });

    var curGroupId = $(element).find('form #curGroupId').val();
    if(curGroupId){
        curGroupId = curGroupId.replaceAll("@",'"', true);
        var busiJson  = jQuery.parseJSON(curGroupId + "");
        var busiIds = "";
        if(busiJson){
            for(var key in busiJson){
                busiIds = busiIds+"'"+key+"',";
            }
            busiIds += "''";
            result+=',"BUSINESS_IDS":"'+busiIds+'"';
        }
    }

    result +='}';
    return result;
}
/**
 * 获取选择的记录
 * @param element
 * @param key
 * @returns {Array}
 */
function getSelectedItems(element,key){
    var result=[];
    var itemIds=[];
    $(element).find('table>tbody>tr>td>input[type=checkbox]:checked').each(function(){
        var itemId = $(this).attr(key);
        var taskId = $(this).attr('taskId');
        var wiid = $(this).attr('wiid');
        var workflowName = $(this).attr('workflowName');
        var workflowState = $(this).attr('workflowState');
        if($.inArray(itemId,itemIds)==-1){
            result.push({taskId:taskId,wiid:wiid,workflowName:workflowName,workflowState:workflowState});
            itemIds.push(itemId);
        }
    });
    return result;
}
/**
 * 获取所选择的taskid值
 * @returns {Array}
 */
function getSelectedTaskIds(){
    var result=[];
    $("#taskListTable").find('tr>td>input[type=checkbox]:checked').each(function(){
        var taskId = $(this).attr('taskId');
        if($.inArray(taskId,result)==-1){
            result.push(taskId);
        }
    });
    return result;
}
/**
 * 刷新待办任务
 */
function refreshTaskList(){
    /*var param ={
        start:0,
        limit:defaultLimit,
        totalCount:0,
        queryString:'{"_query":"_query"}',
        splitInfo:taskListSplitInfo
    };
    $('.pagination>ul>li>input[class*="currentPage"]').val(1);
    loadTaskList(tagSplitUrl,param);*/
    $.cookie('portal_index_tasklist',"tasklist", { expires: 7, path: '/'});
    $.cookie('portal_index_msg',"msgcount", { expires: 7, path: '/'});
}
/**
 * 刷新项目列表
 */
function refreshProjectList(){
    var param ={
        start:0,
        limit:defaultLimit,
        totalCount:0,
        queryString:'{"_query":"_query"}',
        splitInfo:projectListSplitInfo
    };
    $('.pagination>ul>li>input[class*="currentPage"]').val(1);

    //判断是参与项目还是权限项目
    var hrefLink = $('#taskTab>span[class*="current"]').attr("id");
    if(hrefLink=='#projectListTab'){
        loadProjectList(tagSplitUrl,param);
    }else if(hrefLink=='#projectPerformerListTab'){
        loadProjectPerformerList(tagSplitUrl,param);
    }

}
/**
 * 显示进度条
 * @param title
 */
function showProgressWindow(title){
    title = title!=null&&title!=''?title:'任务处理中...';
    $('#progressModal h3').empty();
    $('#progressModal h3').html('<i class="icon icon-white icon-edit iconPosition"></i>'+title);
    $('#progressModal').modal({show:true});
}
/**
 * 隐藏进度条
 */
function hideProgressWindow(){
    $('#progressModal').modal('hide');
}
/**
 * 更新进度条
 * @param value
 */
function updateProgressWindow(value){
    var progressWidth = 'width:'+value+'%';
    $('#progressModal div[class=bar]').attr('style',progressWidth);
}
/**
 * 切换
 * @param bussinessId
 */
function turnBusinessTab(businessId){
    resizeDetailTableDiv(50,true);
    $('select[name="BUSINESS_ID"]').val(businessId);
    $('select[name="WORKFLOW_DEFINITION_ID"]').val("");
    $('select[name="BUSINESS_ID"]').trigger("change");
    var param ={
        start:0,
        limit:defaultLimit,
        totalCount:0
    };
    var hrefLink = $('#taskTab>span[class*="current"]').attr("id");
    param.queryString=getAllQueryString($(''+hrefLink));
    loadWorkflowList(hrefLink,param);
}
/**
 * 切换
 * @param businessIds
 */
function queryBusinessGroup(businessIds){
    $('select[name="BUSINESS_ID"]').val("");
    $('select[name="WORKFLOW_DEFINITION_ID"]').val("");
    $('#curGroupId').val("");
    //解析业务分组包含的业务id
    if(businessIds){
        resizeDetailTableDiv(50,true);
        $('#curGroupId').val(businessIds);
        var param ={
            start:0,
            limit:defaultLimit,
            totalCount:0
        };
        var hrefLink = $('#taskTab>span[class*="current"]').attr("id");
        param.queryString=getAllQueryString($(''+hrefLink));
        loadWorkflowList(hrefLink,param);
    }
}

/**
 * 加载待办、已办、项目列表
 * @param hrefLink
 * @param param
 */
function loadWorkflowList(hrefLink,param){
    if(hrefLink=='#taskListTab'){
        param.splitInfo = taskListSplitInfo;
        loadTaskList(tagSplitUrl,param);
        refreshTimer = setTaskListTimer();
    }else if(hrefLink=='#taskOverListTab'){
        if(refreshTimer!=null)
            clearInterval(refreshTimer);
        param.splitInfo = taskOverListSplitInfo;
        loadTaskOverList(tagSplitUrl,param);
    }else if(hrefLink=='#projectListTab'){
        if(refreshTimer!=null)
            clearInterval(refreshTimer);
        param.splitInfo=projectListSplitInfo;
        loadProjectList(tagSplitUrl,param);
    }else if(hrefLink=='#projectPerformerListTab'){
        if(refreshTimer!=null)
            clearInterval(refreshTimer);
        param.splitInfo=projectPerformerListSplitInfo;
        loadProjectPerformerList(tagSplitUrl,param);
    }
}

/**
 * 清空查询条件
 * @param element
 */
function clearQueryForm(element){
    $(element).find('form').each(function(){
        $(this).find('input, select').each(function(){
            if (!this.name) return;
            if ('radio' === this.type) {
                $(this).removeAttr("checked");
            } else if ('checkbox' === this.type) {
                $(this).attr("checked", false);
            } else {
                $(this).val("");
            }
        });
    });
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