<div class="message-footer clearfix">
    <div class="pull-left"> ${_pageobj.getTotalElements()}  条记录</div>

    <div class="pull-right">
        <div class="inline middle">第 ${_pageobj.number+1} 页 共 ${_pageobj.getTotalPages()} 页</div>

        &nbsp; &nbsp;
        <ul class="pagination middle">
        <#if _pageobj.number==0>
            <li class="disabled" title="首页">
                <span>
                    <i class="icon-step-backward middle"></i>
                </span>
            </li>
        <#else>
            <li title="首页">
                <a href="<@url paramMap={"page":"0"} />">
                    <i class="icon-step-backward middle"></i>
                </a>
            </li>
        </#if>

            <!--上一页 -->
        <#if _pageobj.number==0 || _pageobj.getTotalPages()==1>
            <li class="disabled" title="上一页">
                <span>
                    <i class="icon-caret-left bigger-140 middle"></i>
                </span>
            </li>
        <#else>
            <li title="上一页">
                <a href="<@url paramMap={"page":"${_pageobj.number-1}"} />">
                    <i class="icon-caret-left bigger-140 middle"></i>
                </a>
            </li>
        </#if>

            <li>
                <span>
                    <input value="${_pageobj.number+1}" maxlength="3" type="text">
                </span>
            </li>
            <!--下一页 -->
        <#if _pageobj.number<_pageobj.getTotalPages()-1 >
            <li title="下一页">
                <a href="<@url paramMap={"page":"${_pageobj.number+1}"} />">
                    <i class="icon-caret-right bigger-140 middle"></i>
                </a>
            </li>
        <#else>
            <li class="disabled" title="下一页">
                <span>
                    <i class="icon-caret-right bigger-140 middle"></i>
                </span>
            </li>
        </#if>

            <!--最后一页 -->
        <#if _pageobj.number==_pageobj.getTotalPages()-1 >
            <li class="disabled" title="最后一页">
                <span>
                    <i class="icon-step-forward middle"></i>
                </span>
            </li>
        <#else>
            <li title="最后一页">
                <a href="<@url paramMap={"page":"${_pageobj.getTotalPages()-1}"} />">
                    <i class="icon-step-forward middle"></i>
                </a>
            </li>
        </#if>
        </ul>
    </div>
</div>

