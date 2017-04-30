<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户列表管理</title>
    <#include "../include-tree.ftl"/>
    <script type="text/javascript">
        var treeOrganUser = "treeOrganUser";
        var zTreeOrganUser;
        var forgen_key_id = "${foreignId!}";
        var paramString = ${paramString!};

        var settingOrganUser = {
            view: {dblClickExpand: true},
            check: {enable: true},
            async: {
                enable: true,
                url:cur_proj_url+"/config/user/json?",
                type: "get"
            },
            callback: {
                onClick: ztreeUserOnClick,
                onCheck: ztreeUserOnCheck
            }
        };

        $(document).ready(function(){
            $.fn.zTree.init($("#"+treeOrganUser), settingOrganUser);
            zTreeOrganUser = $.fn.zTree.getZTreeObj(treeOrganUser);

            //下面是主题操作事件
            $('#saveTreeUserRel').click(function () {
                saveTreeUserRel();
            });

            $('#expandTreeOrganUser').click(function () {
                zTreeOrganUser.expandAll(true);
            });
            $('#collapseTreeOrganUser').click(function () {
                zTreeOrganUser.expandAll(false);
            });
        });

        //树控件的树节点的点击事件
        function ztreeUserOnClick(event, treeId, treeNode) {
            if(treeNode.checked){
                zTreeOrganUser.cancelSelectedNode(treeNode);
                zTreeOrganUser.checkNode(treeNode, false, true);
            }else{
                zTreeOrganUser.selectNode(treeNode);
                zTreeOrganUser.checkNode(treeNode, true, true);
            }
        }
        function ztreeUserOnCheck(event, treeId, treeNode) {
            if(treeNode.checked){
                zTreeOrganUser.selectNode(treeNode);
                zTreeOrganUser.checkNode(treeNode, true, true);
            }else{
                zTreeOrganUser.cancelSelectedNode(treeNode);
                zTreeOrganUser.checkNode(treeNode, false, true);
            }
        }
        /**
         * 保存菜单数据
         */
        function saveTreeUserRel(){
            //测试修改过的节点记录
            var changeNodes = [];
            var nodes = zTreeOrganUser.getChangeCheckedNodes();
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
            parent.returnSelectUserData(param);
        }
    </script>
</head>
<body style="min-width:480px;">
<div class="Hui-wraper">
    <div class="row cl treeManage">
        <div class="col-12" style="">
            <div class="ztreeRightDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);"  onclick="refreshTreeOrgan();" id="refreshTreeOrgan"><i class="icon-refresh"></i>部门人员列表</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeUserRel"><i class="icon-ok"></i>确定</a>
                            <a href="javascript:void(0);" id="expandTreeOrganUser"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeOrganUser"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <@treeSearch zTreeId="treeOrganUser" />
                <ul id="treeOrganUser" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>
<#include "../config-bottom-inclue.ftl"/>
</body>