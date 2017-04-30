<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>角色选择</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeRole = "treeRole";
        var treeUser = "treeUser";
        var zTreeRole,zTreeUser;
        var forgen_key_id = "${foreignId!}";
        var paramString = ${paramString!};

        /**
         * ztree控件的数据设置
         */
        var settingRole = {
            view: {showLine: true,dblClickExpand: true},
            check: {enable: true},
            async: {
                enable: true,
                url:cur_proj_url+"/config/role/json",
                type: "get"
            },
            //data: {simpleData: {enable: true}},
            callback: {
                onClick: ztreeRoleOnClick,
                onCheck: ztreeRoleOnCheck,
                onAsyncSuccess: zTreeRoleOnAsyncSuccess
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
            $.fn.zTree.init($("#"+treeRole), settingRole);
            zTreeRole = $.fn.zTree.getZTreeObj(treeRole);

            $.fn.zTree.init($("#"+treeUser), settingUser);
            zTreeUser = $.fn.zTree.getZTreeObj(treeUser);

            //下面是主题操作事件
            $('#saveTreeRoleRel').click(function () {
                saveTreeRoleRel();
            });
            $('#checkAllTreeRole').click(function () {
                zTreeRole.checkAllNodes(!checkAll);
                checkAll = !checkAll;
            });
        });

        function zTreeRoleOnAsyncSuccess(event, treeId, treeNode, msg) {
            //zTreeRole.expandAll(true);
        }

        //树控件的树节点的点击事件
        function ztreeRoleOnClick(event, treeId, treeNode) {
            if(treeNode.checked){
                zTreeRole.cancelSelectedNode(treeNode);
                zTreeRole.checkNode(treeNode, false, true);
                zTreeUser.destroy();
            }else{
                zTreeRole.selectNode(treeNode);
                zTreeRole.checkNode(treeNode, true, true);
                resetTreeUser(treeNode.id);
            }
        }
        function ztreeRoleOnCheck(event, treeId, treeNode) {
            if(treeNode.checked){
                zTreeRole.selectNode(treeNode);
                zTreeRole.checkNode(treeNode, true, true);
                resetTreeUser(treeNode.id);
            }else{
                zTreeRole.cancelSelectedNode(treeNode);
                zTreeRole.checkNode(treeNode, false, true);
                zTreeUser.destroy();
            }
        }
        function resetTreeUser(roleId) {
            settingUser.async.url = cur_proj_url+"/config/role/userjson?roleId="+roleId;
            $.fn.zTree.init($("#"+treeUser), settingUser);
            zTreeUser = $.fn.zTree.getZTreeObj(treeUser);
        }
        /**
         * 保存菜单数据
         */
        function saveTreeRoleRel(){
            //测试修改过的节点记录
            var changeNodes = [];
            var nodes = zTreeRole.getChangeCheckedNodes();
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
                    if(!(nodes[i].children && nodes[i].children.length > 0)){
                        changeNodes.push(node);
                    }
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
            parent.returnSelectRoleData(param);
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
                        <i class="icon-group"></i>角色列表
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeRoleRel"><i class="icon-ok"></i>确定</a>
                            <a href="javascript:void(0);" id="checkAllTreeRole"><i class="icon-check"></i>全选</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeRole" />
                <ul id="treeRole" class="ztree" style="padding: 0px"></ul>
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