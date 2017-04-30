<!-- 高级搜索窗体 -->
<div id="advancedSearchModal" class="modal hide fade panel NewWorkflowForms col-12 border-all draggable" style="width:300px;" tabindex="-1" role="dialog" aria-labelledby="advancedSearchModal" aria-hidden="true">
    <h4 class="advancedSearchLab"><i class="icon-screenshot icon-large"></i> 高级搜索 <a href="#" class="icon-remove close1" data-dismiss="modal" aria-hidden="true" value="取消" ></a></h4>
    <!-- 高级搜索 -->
    <form action="" class="form form-horizontal">
        <hidden id="curTabId"></hidden>
        <hidden id="curGroupId"></hidden>
        <input id="CREATE_ORGAN" type="hidden" name="CREATE_ORGAN" value="${curOrgan.organId!}">
        <div class="row cl">
            <div class="col-2 form-label">
                任务描述
            </div>
            <div class="formControls col-4 query-param-row">
                <input type="text" name="REMARK" class="input-text size-M" autocomplete="off">
            </div>
            <div class="col-2 form-label project-param">
                部门
            </div>
            <div class="formControls col-4 project-param">
                <input id="CREATE_ORGAN_NAME" value="${curOrgan.organName!}" type="text" readonly="readonly" name="CREATE_ORGAN_NAME" class="input-text size-M" autocomplete="off"><a id="menuBtn" href="#" onclick="showMenu(); return false;" style="margin-left:-15px;font-size: 7pt"><i class="icon-caret-down"></i></a>
            </div>
        </div>
        <div class="row cl" id="advancedSearch_TASK_STATE">
            <div class="col-2 form-label">
                状态
            </div>
            <div class="formControls col-4">
                <select class="select" name="TASK_STATE" id="taskState">
                    <option value="">所有</option>
                    <option value="1">超期</option>
                </select>
            </div>
            <div class="col-2 form-label">
                创建人
            </div>
            <div class="formControls col-4">
                <input type="text" name="CREATE_USERNAME" class="input-text size-M" autocomplete="off">
            </div>
        </div>
        <div class="row cl row-fluid">
            <div class="col-2 form-label">
                业务
            </div>
            <div class="formControls col-4">

                <select class="select" name="BUSINESS_ID">
                    <option value="">所有</option>
                <#list businessList as businessItem>
                    <option value="${businessItem.businessId}">${businessItem.businessName}</option>
                </#list>
                </select>

            </div>
            <div class="col-2 form-label">
                流程类型
            </div>
            <div class="formControls col-4">

                <select class="select" name="WORKFLOW_DEFINITION_ID">
                    <script id="queryWorkflowDefinitionList" type="text/html">
                        {{each workflowDeinfitionList as workflowDefinition i}}
                        <option value="{{workflowDefinition.workflowDefinitionId}}">
                            {{workflowDefinition.workflowName}}
                        </option>
                        {{/each}}
                    </script>
                </select>

            </div>
        </div>
        <div class="row cl">
            <div class="col-2 form-label">
                时间段
            </div>
            <div class="formControls col-10 ">
                <input class="input-text size-M Wdate timeWidth" style="width:188px;" name="BEGIN_TIME" type="text" onfocus="WdatePicker({firstDayOfWeek:1,skin:'twoer'})">
                <font>~</font>
                <input class="input-text size-M Wdate timeWidth" name="FINISH_TIME" type="text" onfocus="WdatePicker({firstDayOfWeek:1,skin:'twoer'})">
            </div>
        </div>
        <div class="row cl" style="text-align:center;">
            <button id="show" class="btn btn-secondary size-M button-search ">
                <i class="icon-search icon-large"></i> 搜 索
            </button>
        </div>
    </form>
    <div id="menuContent" class="menuContent" style="display:none; position: absolute;height:200px;overflow-y:scroll;background-color: #fff;border: 1px solid #aaa;">
        <ul id="treeOrgan" class="ztree" style="margin-top:0;"></ul>
    </div>
    <!--end 高级搜索 -->
</div>

<SCRIPT type="text/javascript">
    //该段脚本是用于处理部门下拉框选项的功能
    /**
     * ztree控件的数据设置
     */
    var settingOrgan = {
        view: {showLine: false},
        check: {enable: true},
        async: {
            enable: true,
            url:"${base}/config/organ/json",
            type: "get"
        },
        data: {simpleData: {enable: true}},
        callback: {
            onClick: ztreeOrganOnClick
        }
    };

    function ztreeOrganOnClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeOrgan");
        var nodes = zTree.getSelectedNodes();
        var selectName = "";
        var selectId = "";
        nodes.sort(function compare(a,b){return a.id-b.id;});
        for (var i=0, l=nodes.length; i<l; i++) {
            selectName += nodes[i].name + ",";
            selectId += nodes[i].id + ",";
        }
        if (selectName.length > 0 ) selectName = selectName.substring(0, selectName.length-1);
        if (selectId.length > 0 ) selectId = selectId.substring(0, selectId.length-1);
        $("#CREATE_ORGAN_NAME").val(selectName);
        if(selectId == "treeroot"){
            selectId = "";
        }
        $("#CREATE_ORGAN").val(selectId);
        hideMenu();
    }

    function showMenu() {
        var hrefLink = $('#taskTab>span[class*="current"]').attr("id");
        if(hrefLink=='#projectListTab'){
            var cityObj = $("#CREATE_ORGAN_NAME");
            var cityOffset = $("#CREATE_ORGAN_NAME").offset();

            //获取modal窗口位置，之后用控件位置减去窗口位置
            var modalOffset = $("#advancedSearchModal").offset();
            var left = cityOffset.left - modalOffset.left-1;
            var top = cityOffset.top - modalOffset.top;
            $("#menuContent").css({left:left + "px", top:top + cityObj.outerHeight() + "px",width:$("#CREATE_ORGAN_NAME").innerWidth() + "px"}).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
            hideMenu();
        }
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#treeOrgan"), settingOrgan);
        $('#CREATE_ORGAN_NAME').click(function () {
            showMenu();
        });

    });
</SCRIPT>
<!--end 高级搜索窗体 -->