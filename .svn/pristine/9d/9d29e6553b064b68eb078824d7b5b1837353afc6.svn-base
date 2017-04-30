<!-- 工具栏 -->
<ul class="ToRight border-bottom">
    <li class="navBorder dropDown dropDown_hover border-top border-right">
        <a href="#" class="dropDown_A">
            <span class="msgTitle">待办事项(<span id="msg_taskCount_index">0</span>)</span>
            <i class="iconfont ">&#xf02a9;</i>
        </a>
        <ul class="dropDown-menu radius box-shadow" role="menu" aria-labelledby="drop3">
            <li>
                <a href="#" onclick="openMsgTaskList()" >
                    <span>待办任务(<span id="taskCount_index_p">0</span>)</span>
                </a>
            </li>
            <li>
                <a href="#" onclick="openMsgTaskOverList()" >
                    <span>超期任务(<span id="taskOver_index_p">0</span>)</span>
                </a>
            </li>
        <#if ctx.getEnv('portal.msg.visible')=='true'>
            <li>
                <a href="#" onclick="openMsgList()">
                    <span>未读消息(<span id="msgCount_index_p">0</span>)</span>
                </a>
            </li>
        </#if>
            <li>
                <a href="#" >
                    <span>巡查任务(<span id="leas_count_p">0</span>)</span>
                </a>
            </li>
        </ul>
    </li>
    <li class="navBorder dropDown dropDown_hover border-top border-right">
        <a href="#" class="dropDown_A">
            <span class="user "><span>${userName!}（${organName!}）</span></span>
            <i class="iconfont ">&#xf02a9;</i>
        </a>
        <input type="hidden" id="userId" value="${userId!}"/>
        <input type="hidden" id="loginName" name="loginName" value="${loginName!}"/>
        <ul class="dropDown-menu radius box-shadow" role="menu" aria-labelledby="drop3">
        <#--<li><a href="#"><i class="icon-magic"></i> 切换皮肤</a></li>-->
            <li>
                <a role="menuitem" tabindex="-1" href="#" data-toggle="modal"
                   data-target="#passwordModal"><i class="icon-user"></i>密码修改</a>
            </li>
        <#if ctx.getBooleanEnv('signUserCheck.enable')>
            <li>
                <a role="menuitem" tabindex="-1" href="#" data-toggle="modal" data-target="#signPasswordModal"><i class="icon-user"></i>签名密码修改</a>
            </li>
        </#if>
            <li>
                <a role="menuitem" tabindex="-1"
                   href="#" onclick = "reLogin();" target="_self"><i class="icon-off"></i> 注 销</a>
            </li>
            <li>
                <a role="menuitem" tabindex="-1" href="javascript:logout()"><i
                        class="icon-share-alt"></i> 退出</a>
            </li>
        </ul>
    </li>
</ul>
<!--end 工具栏 -->
<script>
    //此处应使用配置的url地址，也就是消息地址
    var portalMsgSrc ='${ctx.getEnv('portal.msg.src')}';
    //首页页头是否展示未读消息
    var portalMsgVisible = '${ctx.getEnv('portal.msg.visible')}';
    //首页页头未读消息总数
    var portalMsgCountSrc = '${ctx.getEnv('portal.msg.count.src')}';
    //#首页是否增加在线考试验证模块
    var portalExamEnable = '${ctx.getEnv('portal.exam.enable')}';
    function openMsgList(){
        var subUrl = portalMsgSrc;
        localIframeWin(subUrl);
    }
    function reLogin(){
        try{
            $.ajax({
                url:'${base}/logout.action',
                success:function (r) {
                    setTimeout(function(){
                        window.location.href= portalUrl+"/index";
                    },150)
                }
            });
        }catch(e){
            setTimeout(function(){
                window.location.href= portalUrl+"/index";
            },150)
        }
    }
    function setTaskListTimer(){
        return setInterval(function(){
            try{
                var portal_index_msg = $.cookie('portal_index_msg');
                if(portal_index_msg && portal_index_msg == 'msgcount'){
                    refreshTaskCounts();
                    refreshMsgCounts();
                }
            }catch(e){
            }
            $.cookie('portal_index_msg',null, { expires: -1, path: '/'});
        },1000);
    }

    //数字闪动
    function blinklink(index){
        if (!document.getElementById(index).style.color){
            document.getElementById(index).style.color="red"
        }
        if (document.getElementById(index).style.color=="red"){
            document.getElementById(index).style.color="yellow"
        }else{
            document.getElementById(index).style.color="red"
        }
        timer=setTimeout("blinklink('"+index+"')",1000)
    }
    //刷新待办任务条数数字
    function refreshTaskCounts(){
        $.ajax({
            type: "POST",
            url: platform_url+"/messageprompt.action",
            success: function(data){
                if(data!=null){
                    var numArray = data.split(",");
                    if (numArray.length > 0) {
                        var taskCount_index_p = Number(numArray[2]);
                        //$('#msgCount_index_p').html(Number(numArray[1]));
                        $('#taskCount_index_p').html(taskCount_index_p);
                        //$('#msg_taskCount_index').html(taskCount_index_p);
                        $('#taskOver_index_p').html(Number(numArray[3]));
                        /*var name = "未读通知: " + Number(numArray[0]) + "条";
                        var name = "未读消息: " + Number(numArray[1]) + "条";
                        var name = "待办任务: " + Number(numArray[2]) + "条";
                        var name = "超期任务: " + Number(numArray[3]) + "条";*/
                        refreshMsg();
                    }
                }
            }
        });
    }

    function initCnt(){
        $.ajax({
            url : '/leas/xcDataExchange/getXcInspectLand',
            type : 'POST',
            data : {userId : '${userId!}'},
            dataType : 'json',
            success : function(responseObj) {
                $('#leas_count_p').html(responseObj.XCCNT + " 个");
                //alert("待巡查任务 " + responseObj.XCCNT + " 个");
            }
        });
    }

    //刷新未读短信数字
    function refreshMsgCounts(){
        if(portalMsgVisible && portalMsgVisible == "true" && portalMsgCountSrc){
            $.ajax({
                type: "GET",
                //此处的url地址，也应该配置起来，方便不同地区，获取的消息不一样
                url: portalMsgCountSrc,
                success: function(data){
                    if(data!=null){
                        $('#msgCount_index_p').html(data);
                        refreshMsg();
                    }
                }
            });
        }
    }

    function refreshMsg(){
        var taskCount = $('#taskCount_index_p').html();
        var msgCount = $('#msgCount_index_p').html();
        taskCount = !taskCount?0:Number(taskCount);
        msgCount = !msgCount?0:Number(msgCount);
        $('#msg_taskCount_index').html(taskCount+msgCount);
    }

    function openMsgTaskList(){
        var url = portalUrl+"/taskCenter/index";
        localIframeWin(url);
    }
    function openMsgTaskOverList(){
        var url = portalUrl+"/taskCenter/index?taskState=1";
        localIframeWin(url);
    }

    function localIframeWin(subUrl){
        if(subUrl){
            var zhswContentDiv = $('#mainContentZhsw');
            if(zhswContentDiv[0]){
                if(!$('#mainFrame')[0]){
                    var frameHtml = '<iframe id="mainFrame" src="" width=100% height=100% class="iframe1" ' +
                            'style="border:none;overflow:hidden;"frameborder="0" scrolling="auto" marginwidth="0" marginheight="0">';
                    $('#mainContent').append(frameHtml);
                }
                zhswContentDiv.hide();
            }
            $(window.parent.document).find("#mainFrame").attr("src", subUrl);
        }
    }
    //在线考试链接免登入
    function examCheck(){
        if(portalExamEnable && portalExamEnable == "true"){
            var userName = '${userName!}';
            $.ajax({
                type: 'post',
                url: "/office/smsManage!resultLoginName.action",
                data:{'userName':userName},
                success: function (data) {
                    $('#sysNavMenu').find('li a').each(function(){
                        if($(this).text()=='在线考试'){
                            if(userName=='系统管理员'){
                                $(this).attr("menuLink","/examAdmin/home?userName="+data);
                            }else{
                                $(this).attr("menuLink","/exam/home?userName="+data);
                            }
                            //alert($(this).attr("menuLink"));
                        }
                    })
                }
            })
        }
    }
    $(document).ready(function () {
        setTimeout(function(){
            refreshTaskCounts();
        }, 500);
        refreshMsgCounts();
        setTaskListTimer();
        setTimeout(function(){
            var  taskCount =  $('#msg_taskCount_index').html();
            taskCount = !taskCount?0:Number(taskCount);
            if(taskCount!=''&&taskCount!=null){
                blinklink('msg_taskCount_index');
            }
        }, 1000);
        examCheck()
    })
</script>