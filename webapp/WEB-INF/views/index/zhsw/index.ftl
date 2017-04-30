<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#include "../common/index-import.ftl"/>

    <script type="text/javascript">
        var urlMap = ${urlMapJson};
	</script>
    <script src="${base}/static/js/index_zhsw.js"></script>
</head>
<body class="blueStyle">
	<#include "index-header.ftl"/>

    <!--  主要内容区域   -->
    <section class="Hui-article mainContent pd-10 mainContent_zhsw" id="mainContent">
        <!-- 使用Hui-article-box恢复菜单栏自动缩放 -->
        <div class="Hui-wraper CBoxCenter" id="mainContentZhsw">
            <!-- 首页内容示意 -->
            <!-- 配置栏目 -->
            <div class="row cl bg-white background-grey CBoxCenterLeft">
                <!-- 中间重要内容 -->
                <!-- 新闻 -->
                <div class="mapNewsPosition" id="zhsw_left_top">
                    <section class="slider cl has-dots">
                        <a href="javascript:void(0)" class="unslider-arrow prev"></a>
                        <!--上一张控制按钮-->
                        <a href="javascript:void(0)" class="unslider-arrow next"></a>
                        <!--下一张控制按钮-->
                        <!--左右控制按钮，如果不需要可以直接删除代码。-->
                        <ul class="tabcon pictureNews">
                        </ul>
                    </section>
                </div>
                <!--end 新闻 -->

                <div class=" NewsListPosition border-top-grey border-right-grey  border-bottom-grey bg-white pd-left-10" id="zhsw_center_top" >
                    <div id="tab_demo3" class="HuiTab">
                        <div class="tabBar3 cl newsLab" id="zhsw_center_top_bar">
                            <span>${urlMap.centerTopTab1.name!}</span>
                            <span>${urlMap.centerTopTab2.name!}</span>
                            <span>${urlMap.centerTopTab3.name!}</span>
                            <span>${urlMap.centerTopTab4.name!}</span>
                            <a href="#" class="more" id="zhsw_center_top_more">更多&gt;&gt;</a>
                        </div>
                        <div class="tabCon3" id="zhsw_center_top_tab1"></div>
                        <div class="tabCon3" id="zhsw_center_top_tab2"></div>
                        <div class="tabCon3" id="zhsw_center_top_tab3"></div>
                        <div class="tabCon3" id="zhsw_center_top_tab4"></div>
                    </div>
                </div>
                <!--end 中间重要内容 -->
            </div>

            <!-- 其他模块 -->
            <div class="row cl followingPartPosition bg-white border-all followingPart">
                <div class="col-6 border-right-grey" id="zhsw_left_middle">
                    <div class="NewsPannel">
                        <h5>${urlMap.leftMiddle.name!}</h5>
                        <a href="#" class="more" id="zhsw_left_middle_more">更多&gt;&gt;</a>
                    </div>
                    <#--<ul></ul>-->
                </div>
                <div class="col-6 border-left-grey  mL1" id="zhsw_center_middle">
                    <div class="NewsPannel">
                        <h5>${urlMap.centerMiddle.name!}</h5>
                        <a href="#" class="more" id="zhsw_center_middle_more">更多&gt;&gt;</a>
                    </div>
                    <#--<ul></ul>-->
                </div>
            </div>
            <!--end 其他模块 -->

            <!-- 右侧面板 -->
            <div class=" border-all CBoxCenterRight " id="zhsw_right">
            <#--<div class=" bg-white border-all CBoxCenterRight" id="zhsw_right">-->
                <div id="zhsw_right_top">
                    <#--<div class="titleStyle01">${urlMap.rightTop.name!}</div>-->
                    <div class="NewsPannel">
                        <h5>${urlMap.rightTop.name!}</h5>
                        <a href="#" class="more" id="zhsw_right_top_more">更多&gt;&gt;</a>
                    </div>
                    <div id="obtainDate"></div>
                </div>
                <div id="zhsw_right_middle">
                    <div id="zhsw_right_middle_top">
                    </div>
                    <ul class="indexLinks border-all" style="position:absolute;bottom:0px;width:100%;border:0px;border-top:1px solid #d5d5d5;">
                        <li>
                            <a href="" class="links_02">省三级政务平台</a>
                        </li>
                        <li>
                            <a href="" class="links_03">全程跟踪管理系统</a>
                        </li>
                    </ul>
                    <div id="zhsw_right_middle_down" style="display:none;">
                        <p  class="label-success  labelPosition01"><i class="icon-list-ul"></i>${urlMap.rightMiddle.nameDown!}
                            <#--<a href="#"class="more" id="zhsw_right_middle_down_more" >更多&gt;&gt;</a>-->
                        </p>
                    </div>
                </div>
            </div>
            <!--end 右侧面板 -->
            <!--end 配置栏目 -->
            <!--end 首页内容示意 -->
        </div>
        <!--end  主要内容区域   -->
    </section>
    <script type="text/javascript">
        /*日历模块*/
        WdatePicker({
            eCont : 'obtainDate',
            onpicked : function(dp) {
                /*class="Wwday" 节假日
                class="Wtoday" 当前日期
                class="Wselday" 当前选中日期 如果是当前日期，同样是Wselday
                class="Wday" 正常日期
                */
                //alert(dp.cal.getDateStr());
                //获取点击的日期，根据日期请求对应的日程数据
                //此处在右中上的部分显示日程列表
                var url = convertURL(rightTopAjaxUrl)+"&date="+dp.cal.getDateStr();
                loadZhswJsonDetail("zhsw_right_middle_top",url,false,false,30,'',urlMap.rightTop.defaultSize);
            }
        })
        $(function() {
            $.Huitab("#tab_demo3 .tabBar3 span", "#tab_demo3 .tabCon3", "current", "click", "0");
        });
        $(function() {
            if (window.chrome) {
                $('.slider li').css('background-size', '100% 100%');
            }
            $('.slider').unslider({
                speed : 500, //  滚动速度
                delay : 3000, //  动画延迟
                complete : function() {
                }, //  动画完成的回调函数
                keys : true, //  启动键盘导航
                fluid : true //  支持响应式设计
            });
            // 左右控制js
            var unslider = $('.slider').unslider();
            $('.unslider-arrow').click(function() {
                var fn = this.className.split(' ')[1];
                unslider.data('unslider')[fn]();
            });
        });
    </script>
    <style type="text/css">
    </style>
</body>
</html>
