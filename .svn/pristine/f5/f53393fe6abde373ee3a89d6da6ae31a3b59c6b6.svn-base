<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>资源列表</title>
    <#include "../include-tree.ftl"/>

    <script type="text/javascript">
        var treeResource = "treeResource";
        var zTreeResource,rResource;
        var forgen_key_id = "${foreignId!}";
        var paramString = ${paramString!};

        var settingResource = {
            view: {dblClickExpand: true},
            check: {enable: true},
            async: {
                enable: true,
                url:cur_proj_url+"/config/resource/json?hascheck=true"
            },
            data: {keep: {parent: true}},
            callback: {
                onClick: ztreeResourceOnClick,
                onCheck: ztreeResourceOnCheck
            }
        };

        var checkAll = false;
        $(document).ready(function(){
            $.fn.zTree.init($("#"+treeResource), settingResource);
            zTreeResource = $.fn.zTree.getZTreeObj(treeResource);

            //下面是主题操作事件
            $('#saveTreeResourceRel').click(function () {
                saveTreeResourceRel();
            });
            $('#checkAllTreeResource').click(function () {
                zTreeResource.checkAllNodes(!checkAll);
                checkAll = !checkAll;
            });

            $('#expandTreeResource').click(function () {
                zTreeResource.expandAll(true);
            });
            $('#collapseTreeResource').click(function () {
                zTreeResource.expandAll(false);
            });
            $('#refreshTreeResource').click(function () {
                resetResource();
            });
        });

        //树控件的树节点的点击事件
        function ztreeResourceOnClick(event, treeId, treeNode) {
            if(treeNode.checked){
                zTreeResource.cancelSelectedNode(treeNode);
                zTreeResource.checkNode(treeNode, false, true);
            }else{
                zTreeResource.selectNode(treeNode);
                zTreeResource.checkNode(treeNode, true, true);
            }
        }
        function ztreeResourceOnCheck(event, treeId, treeNode) {
            if(treeNode.checked){
                zTreeResource.selectNode(treeNode);
                zTreeResource.checkNode(treeNode, true, true);
            }else{
                zTreeResource.cancelSelectedNode(treeNode);
                zTreeResource.checkNode(treeNode, false, true);
            }
        }
        function resetResource() {
            $.fn.zTree.init($("#"+treeResource), settingResource);
        }
        /**
         */
        function saveTreeResourceRel(){
            //测试修改过的节点记录
            var changeNodes = [];
            var nodes = zTreeResource.getChangeCheckedNodes();
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
            var url = cur_proj_url+'/config/role/saveRel?';
            var param = {
                keyId:forgen_key_id,
                paramString:$.toJSON(changeNodes)
            };
            //在iframe中调用父页面中定义的方法
            parent.returnSelectResourceData(param);
        }
    </script>

    <link rel="stylesheet" href="${base}/static/css/treeconfig.css" type="text/css">
    <link rel="stylesheet" href="${base}/static/css/config.css" type="text/css">
</head>
<body style="min-width:480px;">
<div class="Hui-wraper">
    <div class="row cl treeManage">
        <div class="col-12 left" style="width:480px;">
            <div class="ztreeLeftDiv">
                <h4 class="titleLab">
                    <div>
                        <a href="javascript:void(0);" id="refreshTreeResource"><i class="icon-refresh"></i>资源列表</a>
                        <div class="topBtnDiv">
                            <a href="javascript:void(0);" id="saveTreeResourceRel"><i class="icon-ok"></i>确定</a>
                            <a href="javascript:void(0);" id="checkAllTreeResource"><i class="icon-check"></i>全选</a>
                            <a href="javascript:void(0);" id="expandTreeResource"><i class="icon-plus"></i>展开</a>
                            <a href="javascript:void(0);" id="collapseTreeResource"><i class="icon-minus"></i>收缩</a>
                        </div>
                    </div>
                </h4>
                <ul id="treeResource" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>
</body>