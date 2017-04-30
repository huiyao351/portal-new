<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>部门选择</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeOrgan = "treeOrgan";
        var treeUser = "treeUser";
        var zTreeOrgan,zTreeUser;
        var forgen_key_id = "${foreignId!}";
        var paramString = ${paramString!};

        /**
         * ztree控件的数据设置
         */
        var settingOrgan = {
            view: {showLine: false},
            check: {enable: true},
            async: {
                enable: true,
                url:cur_proj_url+"/config/organ/json",
                type: "get"
            },
            data: {simpleData: {enable: true}},
            callback: {
                onClick: ztreeOrganOnClick,
                onCheck: ztreeOrganOnCheck
            }
        };
        var settingUser = {
            view: {dblClickExpand: false},
            check: {enable: false},
            data: {simpleData: {enable: true}},
            async: {
                enable: true,
                type: "get"
            }
        };

        var checkAll = false;
        $(document).ready(function(){
            $.fn.zTree.init($("#"+treeOrgan), settingOrgan);
            zTreeOrgan = $.fn.zTree.getZTreeObj(treeOrgan);

            $.fn.zTree.init($("#"+treeUser), settingUser);
            zTreeUser = $.fn.zTree.getZTreeObj(treeUser);

            //下面是主题操作事件
            $('#saveTreeOrganRel').click(function () {
                saveTreeOrganRel();
            });
            $('#checkAllTreeOrgan').click(function () {
                zTreeOrgan.checkAllNodes(!checkAll);
                checkAll = !checkAll;
            });
        });

        //树控件的树节点的点击事件
        function ztreeOrganOnClick(event, treeId, treeNode) {
            if(treeNode.checked){
                zTreeOrgan.cancelSelectedNode(treeNode);
                zTreeOrgan.checkNode(treeNode, false, true);
                zTreeUser.destroy();
            }else{
                zTreeOrgan.selectNode(treeNode);
                zTreeOrgan.checkNode(treeNode, true, true);
                resetTreeUser(treeNode.id);
            }
        }
        function ztreeOrganOnCheck(event, treeId, treeNode) {
            if(treeNode.checked){
                zTreeOrgan.selectNode(treeNode);
                zTreeOrgan.checkNode(treeNode, true, true);
                resetTreeUser(treeNode.id);
            }else{
                zTreeOrgan.cancelSelectedNode(treeNode);
                zTreeOrgan.checkNode(treeNode, false, true);
                zTreeUser.destroy();
            }
        }
        function resetTreeUser(organId) {
            settingUser.async.url = cur_proj_url+"/config/organ/userjson?organId="+organId;
            $.fn.zTree.init($("#"+treeUser), settingUser);
            zTreeUser = $.fn.zTree.getZTreeObj(treeUser);
        }
        /**
         * 保存菜单数据
         */
        function saveTreeOrganRel(){
            //测试修改过的节点记录
            var changeNodes = [];
            var nodes = zTreeOrgan.getChangeCheckedNodes();
            if(nodes && nodes.length > 0){
                for(var i=0;i<nodes.length;i++){
                    var node = {
                        id:nodes[i].id,
                        name:nodes[i].name,
                        checked:nodes[i].checked,
                        leaf:true
                    }
                    if(nodes[i].children && nodes[i].children.length > 0){
                        node.leaf = false;
                    }
                    changeNodes.push(node);
                }
            }else{
                alert("请至少选择一条数据进行操作！");
                return;
            }
            var param = {
                keyId:forgen_key_id,
                paramString:$.toJSON(changeNodes)
            };
            //在iframe中调用父页面中定义的方法
            parent.returnSelectOrganData(param);
        }
    </script>
</head>
<body style="min-width:480px;">
<div class="Hui-wraper">
    <div class="row cl treeManage">
        <div class="col-9 left" style="width:330px;">
            <div class="ztreeLeftDiv">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-group"></i>部门列表
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeOrganRel"><i class="icon-ok"></i>确定</a>
                            <a href="javascript:void(0);" id="checkAllTreeOrgan"><i class="icon-check"></i>全选</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeOrgan" />
                <ul id="treeOrgan" class="ztree" style="padding: 0px"></ul>
            </div>
        </div>
        <div class="col-3" style="width:150px;padding-left:10px;">
            <div class="ztreeRightDiv" style="">
                <h4 class="titleLab">
                    <div>
                        <i class="icon-user"></i>人员预览
                    </div>
                </h4>
                <ul id="treeUser" class="ztree" style="padding: 0px"></ul>
            </div>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
</body>