<#macro select id list  value="" listKey="" listValue="" style="" >
<select id="${id}" name="${id}" style="${style}">
    <#if list?is_hash_ex>
        <#local keys=list?keys/>
        <#list keys as key>
            <#if key?string==value>
                <option value="${key}" selected>${list[key]!}</option>
            <#else>
                <option value="${key}">${list[key]!}</option>
            </#if>
        </#list>
    <#else>
        <#list list as data>
            <#if listKey!="">
                <#if value==data[listKey]?string>
                    <option value="${data[listKey]}" selected>${data[listValue]}</option>
                <#else>
                    <option value="${data[listKey]}">${data[listValue]}</option>
                </#if>
            <#else>
                <#if value==data>
                    <option value="${data}" selected>${data}</option>
                <#else>
                    <option value="${data}">${data}</option>
                </#if>
            </#if>
        </#list>
    </#if>
</select>
</#macro>

<#macro url paramMap>
    <#assign _baseUrl=Request.springMacroRequestContext.requestUri/>
    <#assign _queryStr=Request.springMacroRequestContext.getQueryString()/>

    <#list  paramMap?keys as testKey>
        <#if _queryStr?index_of(testKey + "=")==0 || _queryStr?index_of("" + testKey + "=") &gt; 0>
            <#assign _queryStr=_queryStr?replace("("+testKey+"=)(\\w+)",testKey +"="+paramMap[testKey],"r")/>
        <#else>
            <#if _queryStr!="">
                <#assign _queryStr=_queryStr + "&" + testKey + "=" + paramMap[testKey]/>
            <#else>
                <#assign _queryStr= testKey + "=" + paramMap[testKey]/>
            </#if>
        </#if>
    </#list>
${_baseUrl+ "?" +_queryStr}
</#macro>

<#macro treeSearch zTreeId, onlySearchLeaf=true>
<ul class="searchDiv" searchTreeId="${zTreeId}" style="overflow-x: hidden; overflow-y: auto;">
    <div class="left" style="height: 100%; float: left; overflow:auto; margin-left: 10px;margin-top:8px;">
        <i class="icon-search icon-large" style="vertical-align: middle;"></i>
    </div>
    <div class="left" style="height: 100%; float: left; overflow:auto; margin-left: 10px; border: 1px solid #eeeeee;">
        <div class="left" style="height: 100%; float: left; overflow:auto;">
            <input type="text" class="input-text" id="searchInput" placeholder="输入搜索内容……" style="border:0;">
        </div>
        <div class="right" style="height: 100%; width: 65px; float: right; overflow:auto;">
            <input type="text" class="input-text" id="searchTip" value="" disabled="true" style="border: 0; background:#ffffff; text-align: right">
        </div>
    </div>
    <div class="left" style="height: 100%; float: left; overflow:auto; margin-left: 10px; vertical-align: middle">
        <button type="button" class="btn btn-primary size-M radius disabled" id="preNodeSearch" title="定位上一个查询结果" style=" border: 0 none; background-color: #ffffff;">
            <i class="icon-arrow-left icon-large" style="vertical-align: middle; color: #5a98de;"></i>
        </button>
    </div>
    <div class="left" style="height: 100%; float: left; overflow:auto; margin-left: 10px;">
        <button type="button" class="btn btn-primary size-M radius disabled" id="nextNodeSearch" title="定位下一个查询结果" style="border: 0 none; background-color: #ffffff;">
            <i class="icon-arrow-right icon-large" style="vertical-align: middle; color: #5a98de;"></i>
        </button>
    </div>
</ul>

<script type="text/javascript">
    // 搜索结果存储变量
    var searchNodeList = searchNodeList || [];
    // 搜索计数，每次改变都会发起搜索，
    var searchCount = searchCount || [];
    var curSearchSelectIndex = curSearchSelectIndex || [];
    // 上次搜索的内容
    var lastSearchText = lastSearchText || [];

    searchNodeList["${zTreeId}"] = [];
    searchCount["${zTreeId}"] = 0;
    curSearchSelectIndex["${zTreeId}"] = 0;
    lastSearchText["${zTreeId}"] = "";


    $("[searchTreeId='${zTreeId}'] #searchInput").on("keyup", function(o) {
        var text = this.value;
        if (text== lastSearchText["${zTreeId}"]) {
            return;
        }
        lastSearchText["${zTreeId}"] = text;
        setTimeout(function () {
            searchText(lastSearchText["${zTreeId}"], "${zTreeId}");
　　　　}, 10);
    });

    function searchText(text, zTreeId) {
        var nodeList = [];
        if (text.length > 0) {
            var zTreeObject = $.fn.zTree.getZTreeObj(zTreeId);
            nodeList = zTreeObject.getNodesByParamFuzzy("name", text, null);
            <#if onlySearchLeaf == true>
                for (var l = nodeList.length, i = l - 1; i >= 0; i--) {
                    if (nodeList[i].isParent == true) {
                        nodeList.pop(nodeList[i]);
                    }
                }
            </#if>
        }

        if (text != lastSearchText[zTreeId]) { // 如果当前查询的不是text，说明查询内容已经更新，放弃查询结果
            return;
        }
        searchNodeList[zTreeId] = nodeList;
        setTimeout(function () {
            selectNode(0, zTreeId);
　　　　}, 10);
    }

    // 选中当前索引的节点
    function selectNode(index, zTreeId) {
        var len = searchNodeList[zTreeId].length;
        if (len == 0) {
            $("[searchTreeId='" + zTreeId + "'] #searchTip").val("");
            $("[searchTreeId='" + zTreeId + "'] #preNodeSearch").addClass("disabled");
            $("[searchTreeId='" + zTreeId + "'] #nextNodeSearch").addClass("disabled");
            return;
        }

        if ($("[searchTreeId='" + zTreeId + "'] #preNodeSearch").hasClass("disabled")) {
            $("[searchTreeId='" + zTreeId + "'] #preNodeSearch").removeClass("disabled");
        }
        if ($("[searchTreeId='" + zTreeId + "'] #nextNodeSearch").hasClass("disabled")) {
            $("[searchTreeId='" + zTreeId + "'] #nextNodeSearch").removeClass("disabled");
        }
        if (index == -1) {
            index = len -1;
        } else if (index == len) {
            index = 0;
        }

        curSearchSelectIndex[zTreeId] = index;
        var zTreeObject = $.fn.zTree.getZTreeObj(zTreeId);
        zTreeObject.selectNode(searchNodeList[zTreeId][index], false, false);
        $("[searchTreeId='" + zTreeId + "'] #searchTip").val((curSearchSelectIndex[zTreeId] + 1) + "/" + len);
        $("[searchTreeId='" + zTreeId + "'] #searchInput").focus();
    }

    $("[searchTreeId='${zTreeId}'] #preNodeSearch").on("click", function() {
        selectNode(--curSearchSelectIndex['${zTreeId}'], "${zTreeId}");
    });

    $("[searchTreeId='${zTreeId}'] #nextNodeSearch").on("click", function() {
        selectNode(++curSearchSelectIndex['${zTreeId}'], "${zTreeId}");
    });
</script>
</#macro>