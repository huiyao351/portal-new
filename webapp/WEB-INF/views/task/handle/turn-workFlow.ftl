<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${ctx.getEnv('portal.title')}</title>
    <link href="${base}/static/lib/bootstrap2.3/css/bootstrap.css" rel="stylesheet"/>
    <link href="${base}/static/lib/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <script src="${base}/static/lib/jquery/jquery-1.11.0.min.js"></script>
    <script src="${base}/static/lib/jquery/vscontext.jquery.js"></script>
    <script src="${base}/static/lib/bootstrap2.3/js/bootstrap.js"></script>
    <link href="${base}/static/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
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

        var firstuserEmpty = "${ctx.getEnv('portal.workflow.turn.firstuser.empty')}";

        function TabSwitch(selectedTab) {
            //1、找ID为divTab的元素；2、找className为tabs的所有LI元素（排除className为tabspace的LI元素）；3、清除所有class
            $("#divTab .tabs LI[class!='tabspace']").removeClass();

            //为当前选中的tab设置class
            $("#" + selectedTab).addClass("selectedTab");
            $("#div_tabl").toggle();
            $("#div_tab2").toggle();
        }


        var activityRelType;
        $(function () {
            $("#mask").hide();
            $("#div_tab2").toggle();
            var opinionsInfo = getOpinions();
            initializeTransferTable(getTurnInfo());
            initializeContextMenu();
        });

        function getTurnInfo() {
            var returnXml = '${turnXml!}';
            return returnXml;
        }

        function getOpinions() {
            var opinionJSON =${opinions!};
            return opinionJSON;
        }

        /**
         *初始化意见右键菜单
         */
        function initializeContextMenu(){
            var opinions = getOpinions();
            if(opinions!=null&&opinions!=''){
                var opinionList = opinions;//$.parseJSON(opinions);
                var menuItems={};
                for(var index in opinionList){
                    if(opinionList[index].opinIsUse=='1'){
                        var menuItem = $('<a href="#"  >&nbsp;&nbsp;<i class="icon-caret-right icon-large"></i>&nbsp;&nbsp;'+opinionList[index].opinContent+'</a>');
                        /*menuItem.attr('href','#');
                        menuItem.bind('click', getOpinionFromContextMenu);
                        menuItem.text(opinionList[index].opinContent);*/
                        menuItem.bind('click', getOpinionFromContextMenu);
                        $(".vs-context-menu ul").append($('<li></li>').append(menuItem));
                    }
                }
                $('#opinionText').vscontext({menuBlock: 'vs-context-menu'});
            }
        }

        function getOpinionFromContextMenu(){
            $("#opinionText").val($(this).text());
        }

        function beginTurn(info){
            var confirmTurnInfo='${confirmTurnInfo!}';
            if ("true"==confirmTurnInfo){
                var html="";
                $('input:checked').each(function(){
                    html=html+"【"+$(this).parent().parent().children().eq(1).html()+"】";
                    html=html+$(this).parent().parent().children().eq(3).find("option:selected").text();
                    //可能多个人
                    var rowspanCount= parseInt($(this).parent().attr('rowspan'));
                    if (rowspanCount && rowspanCount>1){
                        var trRow=$(this).parent().parent();
                        for(var i=1;i<rowspanCount;i++){
                            trRow=trRow.next();
                            html=html+"、"+$(trRow).children().eq(1).find("option:selected").text();
                        }
                    }

                    html=html+"<br/>"
                });


                postTurnInfo(info);
            }else{
                postTurnInfo(info);
            }
        }
        function postTurnInfo(info) {
            var url = "${path_platform!}/taskturn!TurnTask.action?taskid=${taskid!}" ;
            var turnXML = getTurnXml();
            if (turnXML != null) {
                showWaiting();
                //转发前保存帆软
                parent.saveReport();
                $.ajax({
                    url: url,
                    data: turnXML,
                    processData: false,
                    contentType: 'text/xml',
                    type: 'POST',
                    error: function () {
                        $("#mask").hide();
                        alert('工作流转发错误！');
                    },
                    success: function (data) {
                        if (data == 'true') {
                            parent.refreshTask();
                        } else {
                            $("#mask").hide();
                            data = data.replace(/\\n/g,"\n");
                            alert(data);
                        }
                    }
                });
            }

        }

        /**
         *获取到需要提交的XML
         */
        function getTurnXml() {
            var users = 0;
            var activitysElement = '<Activitys RelType="' + activityRelType + '"';
            if ($('#sendMsg input').attr('checked') != null && $('#sendMsg input').is(':checked'))
                activitysElement += ' SendSMS="' + $('#sendMsg input').is(':checked') + '"';
            else
                activitysElement += ' SendSMS= "false"';
            activitysElement += '>';
            $('#activityTable').find('input:checked').each(function (i) {
                if($(this).parent().attr('name')!=null&&$(this).parent().attr('name')!='xbbj'){
                    var activityElement = '<Activity ';
                    var userInfoElement = '<UserInfo ';
                    var rowspan = 1;
                    $(this).parent().parent().find('td').each(function (j) {
                        if (j == 0) {
                            if ($(this).attr('rowspan') != null)
                                rowspan = parseInt($(this).attr('rowspan'));
                        } else if (j == 1) {
                            activityElement += 'Id="' + $(this).attr('name') + '">';
                        } else if (j == 2) {
                            userInfoElement += 'RoleId="' + $(this).find('select').val() + '"';
                        } else if (j == 3) {
                            if ($(this).find('select').val() != null && $(this).find('select').val() != ""){
                                if ($(this).find('select').val() != "-2"){
                                    users += 1;
                                    userInfoElement += ' Id="' + $(this).find('select').val() + '">';
                                }
                            }else{
                                users += 1;
                                userInfoElement += ' Id="-1">';
                            }
                        }
                    });
                    userInfoElement += "</UserInfo>";
                    activityElement += userInfoElement;

                    if(rowspan>1){
                        var nextUserInfoElements = $(this).parent().parent().nextAll();
                        for (var m = 0; m < nextUserInfoElements.length; m++) {
                            var userInfoElement = '<UserInfo ';
                            $(nextUserInfoElements[m]).find('td').each(function (j) {
                                if (j == 0) {
                                    userInfoElement += 'RoleId="' + $(this).find('select').val() + '"';
                                } else if (j == 1) {
                                    if ($(this).find('select').val() != null && $(this).find('select').val() != ""){
                                        if ($(this).find('select').val() != "-2"){
                                            users += 1;
                                            userInfoElement += ' Id="' + $(this).find('select').val() + '">';
                                        }
                                    }else{
                                        users += 1;
                                        userInfoElement += ' Id="-1">';
                                    }
                                }

                            });
                            userInfoElement += "</UserInfo>";
                            activityElement += userInfoElement;
                        }
                    }

                    activityElement += "</Activity>";
                    activitysElement += activityElement;
                }

            });



            var remarkElement = '<ReMark>';
            var remarkTextElement = '<text>';
            if ($('#opinionText').val() != null && $('#opinionText').val() != "")
                remarkTextElement += $('#opinionText').val();
            remarkTextElement += '</text>';
            remarkElement += remarkTextElement + '</ReMark>';
            activitysElement += remarkElement + '</Activitys>';

            //如果配置了第一个默认人员为空，则在返回转发人员格式的时候，需要判断至少选择一个办理人员
            if(firstuserEmpty && firstuserEmpty == "true"){
                var newTypeTd = $("#newTypeTd").val();
                if ((users && users > 0)||newTypeTd=='协办办结'){
                    return activitysElement;
                }
                alert("请至少选择一个人员转发！");
                return null;
            }else{
                return activitysElement;
            }
        }

        /**
         *增加一行
         */
        function addRow() {
            var imgObj = $(this);
            var oldRow = imgObj.parent().parent();
            var newRow = $('<tr></tr>');
            var activityId = $(oldRow.find('td')[0]).attr('name')
            newRow.append($(oldRow.find('td')[2]).clone(true));
            newRow.append($(oldRow.find('td')[3]).clone(true));
            var newImgNode = $('<img name="' + activityId + '" style="cursor:hand" ' + ' src="${path_platform!}/common/images/delete.gif" height="15" ext:qtip="删除"/>');
            newImgNode.bind('click', delRow);
            var newImgTd = $('<td></td>').append(newImgNode);
            newRow.append(newImgTd);

            if ($(oldRow.find('td')[0]).attr('rowspan') != null) {
                var rowspanCount= parseInt($(oldRow.find('td')[0]).attr('rowspan'));
                var cRow=$(oldRow);
                for(var i=0;i<rowspanCount-1;i++){
                    cRow=cRow.next();
                }
                newRow.insertAfter(cRow);
                $(oldRow.find('td')[0]).attr('rowspan', rowspanCount + 1);
                $(oldRow.find('td')[1]).attr('rowspan', rowspanCount + 1);
            }
            else {
                newRow.insertAfter($(oldRow));
                $(oldRow.find('td')[0]).attr('rowspan', 2);
                $(oldRow.find('td')[1]).attr('rowspan', 2);
            }
            refreshNewAddUserSelectOption(newRow,activityId);
        }

        /**
         * 删除行
         */
        function delRow() {
            var imgObj = $(this);
            var activityId = $(this).attr('name');
            var currentRow = imgObj.parent().parent();

            $('#activityTable').find('td[name="' + activityId + '"]').attr('rowspan', parseInt($('#activityTable').find('td[name="' + activityId + '"]').attr('rowspan')) - 1);
            currentRow.remove();
        }

        /**
         * 角色或部门列表变化处理方法
         */
        function changeRole() {
            var roleSelectObj = $(this);
            var roleSelectObjId = $(this).attr('ID');
            var activityId=roleSelectObjId.substring(4);//固定格式role+activityID
            refreshNewAddUserSelectOption($(roleSelectObj.parent().parent()),activityId);
        }

        /**
         *刷新用户列表(废弃不用)
         * @param row  用户select所在的行对象
         */
        function refreshUserSelectOption(row) {
            var userSelectObj = row.find('select[Id="users"]');
            var activityIdSelected=$(row.find('td')[0]).attr('name');
            var roleNameSelected = $(row.find('select[Id="role'+activityIdSelected+'"]')[0]).find("option:selected").text();
            if(userSelectObj!=null&&userSelectObj.length>0){
                userSelectObj[0].options.length = 0;
                var selectCondition = 'Activity[Id="'+activityIdSelected+'"]>User[Id="' + ($(row.find('select')[0]).val()) + '"]'+'[Name="'+roleNameSelected+'"]>UserInfo';
                if($($.parseXML(getTurnInfo())).find(selectCondition)!=null&&($($.parseXML(getTurnInfo())).find(selectCondition).length>1)){
                    if(firstuserEmpty && firstuserEmpty == "true"){
                        $(userSelectObj[0]).append($('<option value ="-2"></option>'));
                    }
                    $(userSelectObj[0]).append($('<option value ="">---全部---</option>'));
                }
                $($.parseXML(getTurnInfo())).find(selectCondition).each(function (i) {
                    var userNode = $(this);
                    $(userSelectObj[0]).append($('<option value ="' + userNode.attr('Id') + '">' + userNode.attr('Name') + '</option>'));
                });
            }
        }
        /**
         *刷新用户列表
         * @param newrow  用户select所在的行对象
         * @param activityId 活动Id
         */
        function refreshNewAddUserSelectOption(newrow,activityId) {
            var userSelectObj = newrow.find('select[Id="users"]');
            var roleNameSelected = $(newrow.find('select[Id="role'+activityId+'"]')[0]).find("option:selected").text();
            if(userSelectObj!=null&&userSelectObj.length>0){
                userSelectObj[0].options.length = 0;
                var selectCondition = 'Activity[Id="'+activityId+'"]>User[Id="' + ($(newrow.find('select')[0]).val()) + '"]'+'[Name="'+roleNameSelected+'"]>UserInfo';
                var selectAll = $($($.parseXML(getTurnInfo())).find('Activity[Id="'+activityId+'"]')).attr('SelectAll');
                if($($.parseXML(getTurnInfo())).find(selectCondition)!=null&&($($.parseXML(getTurnInfo())).find(selectCondition).length>1)&&selectAll=='true'){
                    if(firstuserEmpty && firstuserEmpty == "true"){
                        $(userSelectObj[0]).append($('<option value ="-2"></option>'));
                    }
                    $(userSelectObj[0]).append($('<option value ="">---全部---</option>'));
                }
                $($.parseXML(getTurnInfo())).find(selectCondition).each(function (i) {
                    var userNode = $(this);
                    var defaultSelectedUserInfo = $(this).attr('DefaultSelected');
                    if (!defaultSelectedUserInfo)
                        $(userSelectObj[0]).append($('<option value ="' + userNode.attr('Id') + '">' + userNode.attr('Name') + '</option>'));
                    else
                        $(userSelectObj[0]).append($('<option value ="' + userNode.attr('Id') + '"  selected="selected">' + userNode.attr('Name') + '</option>'));
                });
            }
        }

        /**
         *初始化转发列表
         * @param turnInfo
         */
        function initializeTransferTable(turnInfo) {
            if (turnInfo === null || turnInfo === '')
                return;
            var turnInfoDoc = $.parseXML(turnInfo)
            var activityRoot = $(turnInfoDoc).find('*').eq(0);
            activityRelType = activityRoot.attr('RelType');
            var activitySendSMS = activityRoot.attr('SendSMS');
            var cooperRootId = activityRoot.attr('cooperRootId');

            var index = 0;

            var targetAdIds = "";
            $(turnInfoDoc).find('Activity').each(function (i) {
                var activityNode = $(this);
                var activityName = $(this).attr('Name');
                var activityId = $(this).attr('Id');

                targetAdIds += activityId+";";

                var multiSelect = $(this).attr('MutiSelect');
                var selectAll = $(this).attr('SelectAll');

                var defaultSelected = $(this).attr('DefaultSelected');
                var newRow = $('<tr></tr>');
                var newTypeTd = $('<td></td>');


                //为了兼容IE6和IE7的浏览器下的Radio控件,才写成字符串拼接
                var inputChecked="";
                if(defaultSelected!=null&&defaultSelected==='true')
                    inputChecked= 'checked="checked"';
                var inputNodeType = "";
                if(activityRelType==='and'){
                    inputNodeType = 'checkbox';
                }else{
                    inputNodeType = 'radio';
                }
                var newInputNode = $('<input name="relType" type="'+inputNodeType+'"'+inputChecked+'/>');
                newTypeTd.append(newInputNode);
                newTypeTd.attr('name', activityId);
                newRow.append(newTypeTd);


                newTypeTd = $('<td></td>');
                newTypeTd.attr('name', activityId);
                newTypeTd.text(activityName);
                newRow.append(newTypeTd);
                if (activityNode.find('User').length > 0) {
                    newTypeTd = $('<td></td>');
                    var newSelectRole = $('<select></select>');
                    newSelectRole.bind('change', changeRole);
                    newSelectRole.attr('id', 'role' + activityId);
                    var firstRoleId;
                    activityNode.find('User').each(function (j) {
                        var roleNode = $(this);
                        if (j === 0)
                            firstRoleId = $(this).attr('Id');
                        var roleName = $(this).attr('Name');
                        var roleId = $(this).attr('Id');
                        //如果一个节点中出现了重复的角色或者部门，则合并，例如场景：某个节点的转发对象是多个人，而这些人都是同一角色或者部门
                        //如果不合并，则会出现重复的转发角色或者部门
                        if(newSelectRole.find('option[value="'+roleId+'"]').length>0)
                            return;
                        var defaultSelectedUser = $(this).attr('DefaultSelected');
                        if (!defaultSelectedUser)
                            newSelectRole.append($('<option value ="' + roleId + '">' + roleName + '</option>'));
                        else
                            newSelectRole.append($('<option value ="' + roleId + '" selected="selected">' + roleName + '</option>'));
                    });
                    newTypeTd.append(newSelectRole);
                    newRow.append(newTypeTd);
                    if (firstRoleId != null && firstRoleId != '') {
                        newTypeTd = $('<td></td>');
                        var newSelectUser = $('<select id="users"></select>');
                        if(activityNode.find('User[Id="' + firstRoleId + '"]>UserInfo')!=null&&activityNode.find('User[Id="' + firstRoleId + '"]>UserInfo').length>1&&selectAll=='true'){
                            if(firstuserEmpty && firstuserEmpty == "true"){
                                newSelectUser.append($('<option value ="-2"></option>'));
                            }
                            newSelectUser.append($('<option value ="">---全部---</option>'));
                        }

                        activityNode.find('User[Id="' + firstRoleId + '"]>UserInfo').each(function (m) {
                            var userNode = $(this);
                            var defaultSelectedUserInfo = $(this).attr('DefaultSelected');
                            if (!defaultSelectedUserInfo)
                                newSelectUser.append($('<option value ="' + userNode.attr('Id') + '">' + userNode.attr('Name') + '</option>'));
                            else
                                newSelectUser.append($('<option value ="' + userNode.attr('Id') + '"  selected="selected">' + userNode.attr('Name') + '</option>'));
                        });
                        newTypeTd.append(newSelectUser);
                    }
                    newRow.append(newTypeTd);
                    newTypeTd = $('<td></td>');
                    if (multiSelect != null && multiSelect === 'true') {
                        var tmpImgNode = $("<img name='addRowImg' style=\"cursor:hand\"  src=\"${path_platform!}/common/images/drop-add.gif\" height=\"15\" ext:qtip=\"添加\"/>");
                        tmpImgNode.bind('click', addRow);
                        newTypeTd.append(tmpImgNode);
                    }
                    newRow.append(newTypeTd);
                } else {
                    newRow.append($('<td></td><td></td><td></td>'));
                }
                if (i == 0)
                    $('#activityTable tbody').append(newRow);
                else
                    newRow.insertAfter($('#activityTable tr:eq(' + index + ')'));

                index += 1;

                refreshNewAddUserSelectOption(newRow,activityId);


            });
            if(cooperRootId!=null&&cooperRootId!='')
            {
                var newRow = $('<tr></tr>');
                var newTypeTd = $('<td></td>');

                var newInputNode = $('<input name="relType" type="radio" checked="checked"/>');
                newTypeTd.append(newInputNode);
                newTypeTd.attr('name', "xbbj");
                newRow.append(newTypeTd);

                newTypeTd = $('<td></td>');
                newTypeTd.text('协办办结');
                $("#newTypeTd").val('协办办结');

                newRow.append(newTypeTd);
                newRow.append($('<td></td><td></td><td></td>'));
                if($('#activityTable tbody').find('tr').length==0){
                    $('#activityTable tbody').append(newRow);
                }else{
                    newRow.insertAfter($('#activityTable tr:eq(' + index + ')'));
                }

            }


            if (activitySendSMS != null && activitySendSMS === 'true') {
                $('#sendMsg').css('display', 'block');
                $('#sendMsg input').attr('checked', true);
            }

            var defaultNotChecked = true;
            $('#activityTable').find('input[name="relType"]').each(function (k) {
                if ($(this).attr('checked') != null)
                    defaultNotChecked = false;
            });
            if (defaultNotChecked) {
                $($('#activityTable').find('input[name="relType"]')[0]).attr('checked', true);
            }
            //给所有下拉框一个事件
            $("#activityTable select").click(function(){
                $(this).parent().parent().find("input").each(function() {
                    $(this).attr("checked", "checked");
                });
            });

            if(targetAdIds != null){
                initJoinMutilTransUser(targetAdIds);
            }

        }


        function showWaiting(){

            $("#mask").css("height",$(document).height());
            $("#mask").css("width",$(document).width());
            $("#mask").show();

        }
    </script>
    <script type="text/javascript">
        /**
         * 针对“多”转“一”时，要求“一”的人员必须和其中最早转的“多”所选择的人员保持一致，
         * 比如a、b、c转给d，b先转给d，选择的人员是的d1，那么要求a、c转发时，d的可选人员只有d1，其他不允许选择
         * 操作步骤：
         * 1、首先遍历所有目标节点，获取目标节点的第一个转发任务记录，针对临时记录，数据库存储在待办任务表，但是节点id加上了Temp后缀
         * 2、获取到临时记录的目标人员，过滤该页面的目标节点对应的角色、人员下拉框，将角色、人员设置为符合条件的，之后去掉下拉框的操作事件
         */
        function initJoinMutilTransUser(targetAdIds){
            var proid = "${proid!}";
            var taskid = "${taskid!}";
            var url = "${base}/turnWorkFlow/filterJoinTransUser?proid="+proid+"&taskid="+taskid+"&targetAdIds="+targetAdIds;
            $.ajax({
                type: "get",
                url: url,
                async: false,
                success: function (result) {
                    if(result){
                        if(result.constructor==String){
                            result = jQuery.parseJSON(result + "");
                        }
                        $.each(result,function(name,value) {
                            $('#role'+name).find("option[value='"+value.roleId+"']").attr("selected",true);

                            var user = $('#users').find("option[value='"+value.userId+"']");
                            if(!(user && user != "" && user.length > 0)){
                                $('#users').append($('<option value ="' + value.userId + '"  selected="selected">' + value.userName + '</option>'));
                            }else{
                                $('#users').find("option[value='"+value.userId+"']").attr("selected",true);
                            }
                            $('#role'+name).attr("disabled",true);
                            $('#users').attr("disabled",true);
                        });
                    }
                }
            });
        }

    </script>
</head>
<body  scroll="no"><!--  带属性的面板  -->
<div id="divTab">
    <div class="tabbable"> <!-- Only required for left/right tabs -->
        <ul class="nav nav-tabs">
            <li class="active"><a href="#tab1" data-toggle="tab">任务转发</a></li>
            <li><a href="#tab2" data-toggle="tab"> 意 　 见 </a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="tab1" style="height: 253px;">
                <table cellSpacing="0" cellPadding="0" width="100%" height="100%" border="1" style="border-color: #F0F0F0;">
                    <input type="hidden" id="newTypeTd" >
                    <tr>
                        <td id="tabContent" align="center"  style="text-align: center;vertical-align: top;">
                            <div id="div_tabl">
                                <table id="activityTable" width="100%" border="0" class="table_border">
                                    <thead>
                                    <tr style="background-color: #F0F0F0"  height="30">
                                        <th width="7%">选择</th>
                                        <th width="41%">转发活动</th>
                                        <th width="32%">参与角色</th>
                                        <th width="15%">参与人</th>
                                        <th width="5%"></th>
                                    </tr>
                                    </thead>
                                    <tbody>

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
                            <textarea name="opinionText" id="opinionText" style="height:240px;"></textarea>
                        </td>
                    </tr>
                </table>
                <div class="vs-context-menu">
                    <ul style="">
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <table cellSpacing="0" cellPadding="0" width="100%" height="100%" border="0" style="">
        <tr>
            <td width="80px">
                <div id="sendMsg">
                    <input type="checkbox" name="sendMsg" value="">&nbsp;&nbsp;发送短信</input>
                </div>

            </td>
            <td style="text-align:right;">
            <#if subProcessFinish?? && subProcessFinish==true>
                <div>
                    <span style="color: red;">*当前子流程即将办结，将转发至主流程下个节点</span>
                </div>
            </#if>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <div id="button_div" style="text-align: center;">
                    <button id="aturn" class="btn-secondary size-M hui-btn" type="button" data-loading-text="Loading..." onclick="beginTurn()">
                        <i class="icon-share-alt icon-white icon-large"></i> 转　发
                    </button>
                </div>
            </td>
        </tr>
    </table>
</div>
<div id="mask" class="mask">正在转发中.....</div>
</body>
</html>
