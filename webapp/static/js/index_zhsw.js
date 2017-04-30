//解决ie低版本浏览器获取获取ajax数据失败的问题
jQuery.support.cors = true;
//默认的字符串长度，超过该长度，调用sub_suolve(str,limit)方法，会自动截取，并用省略号处理
var sub_length = 52 ;
var modelIndex;

//此处url地址考虑采用json配置来实现，一个配置文件配置url地址，一个配置文件配置需要加载的模块
//通过配置文件，实现不同角色加载不同的模块（模块样式不能调整，只能调整模块名称和内容）

//关于首页各模块的数据请求的Ajax地址
//A、左上
var leftTopAjaxUrl = urlMap.leftTop.leftTopAjaxUrl;
//B、中上
var centerTopTab1AjaxUrl =  urlMap.centerTopTab1.centerTopTab1AjaxUrl;
var centerTopTab2AjaxUrl =  urlMap.centerTopTab2.centerTopTab2AjaxUrl;
var centerTopTab3AjaxUrl =  urlMap.centerTopTab3.centerTopTab3AjaxUrl;
var centerTopTab4AjaxUrl =  urlMap.centerTopTab4.centerTopTab4AjaxUrl;
//C、左中
var leftMiddleAjaxUrl =  urlMap.leftMiddle.leftMiddleAjaxUrl;
//D、中中
var centerMiddleAjaxUrl =  urlMap.centerMiddle.centerMiddleAjaxUrl;
//E、右上
var rightTopAjaxUrl =  urlMap.rightTop.rightTopAjaxUrl;
//F、右中
var rightMiddleAjaxUrl =  urlMap.rightMiddle.rightMiddleAjaxUrl;

//关于首页各模块的更多字样的超链接
//B、中上
var centerTopTab1Url =  urlMap.centerTopTab1.centerTopTab1Url;
var centerTopTab2Url =  urlMap.centerTopTab2.centerTopTab2Url;
var centerTopTab3Url =  urlMap.centerTopTab3.centerTopTab3Url;
var centerTopTab4Url =  urlMap.centerTopTab4.centerTopTab4Url;
//C、左中menu.json
var leftMiddleUrl =  urlMap.leftMiddle.leftMiddleUrl;
//D、中中
var centerMiddleUrl =  urlMap.centerMiddle.centerMiddleUrl;
//E、右上
var rightTopUrl =  urlMap.rightTop.rightTopUrl;
//F、右中
var rightMiddleUrl =  urlMap.rightMiddle.rightMiddleUrl;

/**
 * 综合事务首页数据加载说明：
 * 因数据窗口内分了上下左右几个部分,顺序是先左右，后上下
 * 左中右对应：left、center、right  **********  上中下对应：top、middle、down
 * 如果各地有独立需求，则可以单独单独调整该index页面和脚本，本地进行备份保存即可
 *
 * A、左上，图片新闻
 * B、中上，新闻公告、工作动态、国土快讯、政策法规
 * C、左中，收文
 * D、中中，发文
 * E、右上，日程管理
 * F、右中，待办任务
 * ********************************************
 * 处理逻辑分别如下：
 * ajax请求综合事务系统对应的数据，将返回数据动态加载到各个模块中
 */
$(function(){
    //A、左上，图片新闻
    loadZhswLeftTop(urlMap.leftTop.defaultSize);

    //B、中上，新闻公告、工作动态、国土快讯、政策法规
    loadZhswCenterTop(urlMap.centerTopTab1.defaultSize);
    //B中的更多超链接
    $('#zhsw_center_top_more').click(function () {
        //获取当前选中的通知公告类型，打开对应的超链接
        $(this).parent().find('span').each(function(index,element){
            //遍历选项卡，根据选中的，加载对应的url地址
            if($(this).hasClass("current")){
                //此处遍历元素，根据选中元素返回对应的超链接url
                var requestUrl = initCenterTopTabUrl(index,false);
                //打开该类型数据
                openWin(convertURL(requestUrl)+"&test="+$(this).text(),$(this).text());
            }
        });
    });

    //C、左中，收文
    loadZhswLeftMiddle(urlMap.leftMiddle.defaultSize);
    //C中的更多超链接
    $('#zhsw_left_middle_more').click(function () {
        //打开该类型数据
        openWin(convertURL(leftMiddleUrl)+"&test="+$(this).attr("id"),$(this).text());
    });

    //D、中中，发文
    loadZhswCenterMiddle(urlMap.centerMiddle.defaultSize);
    //D中的更多超链接
    $('#zhsw_center_middle_more').click(function () {
        //打开该类型数据
        openWin(convertURL(centerMiddleUrl)+"&test="+$(this).attr("id"),$(this).text());
    });

    //E、右上，日程管理
    loadZhswRightTop(getCurDate(),urlMap.rightTop.defaultSize);
    //D中的更多超链接
    $('#zhsw_right_top_more').click(function () {
        //打开该类型数据
        openWin(convertURL(rightTopUrl)+"&test="+$(this).attr("id"),$(this).text());
    });

    //F、右中，待办任务
    loadZhswRightMiddle();
    //F中的更多超链接
    /*$('#zhsw_right_middle_more').click(function () {
     //打开该类型数据
     openWin(convertURL(rightMiddleUrl)+"&test="+$(this).attr("id"),$(this).text());
     });*/
});

/**
 * A、左上，图片新闻
 */
function loadZhswLeftTop(defaultSize){
    //首先判断是否存在该模块
    var curHtml = $('#zhsw_left_top');
    if(curHtml && curHtml.children.length > 0){
        var url = convertURL(leftTopAjaxUrl)+"&size="+defaultSize;
        //获取json数据
        //var jsondata = initAjaxJsonData("zhsw_left_top",leftTopAjaxUrl);
        var param = {
            type: null
        };
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            async: false,
            success: function (result) {
                if(result.constructor==String){
                    result = jQuery.parseJSON(result + "");
                }
                if (result.success) {
                    jsondata = result.data;
                    if(jsondata) {
                        try {
                            if(jsondata.constructor==String){
                                jsondata = jQuery.parseJSON(jsondata + "");
                            }
                        } catch (e) {}
                    }
                    //解析数据
                    if(jsondata){
                        var length = defaultSize;
                        if(jsondata.length<length){
                            length = jsondata.length;
                        }
                        for (var i = 0; i <length; i++) {
                            var jsonobj = jsondata[i];
                            if(jsonobj){
                                jsonobj = initJsonObjNull(jsonobj);
                                if(jsonobj.img && jsonobj.img.indexOf('http') > -1 && jsonobj.img.length > 15){
                                    var childEle = $('<li/>');
                                    //<img name="" src="${base}/static/images/1.jpg" width="100%" alt=""><a href="(Adaptive)content.html" target="_blank">认真学习三严精神</a>
                                    var title = sub_suolve(jsonobj.title,46);
                                    if(!jsonobj.url){
                                        jsonobj.url = "javascript:void(0)"
                                    }
                                    $('<img name="" src="'+jsonobj.img+'" width="100%" alt=""><a href="'+jsonobj.url+'" target="_blank" title="'+jsonobj.title+'">'+title+'</a>').appendTo(childEle);
                                    $(curHtml).find('ul[class*="pictureNews"]').append(childEle);
                                }

                            }
                        }
                    }
                }
            }
        });
    }
}

/**
 * B、中上，新闻公告、工作动态、国土快讯、政策法规
 */
function loadZhswCenterTop(defaultSize){
    //首先判断是否存在该模块
    var curHtml = $('#zhsw_center_top');
    if(curHtml && curHtml.children.length > 0){
        //B、新闻公告、工作动态、国土快讯、政策法规里面的选项卡数据加载
        $(curHtml).find('div[id="zhsw_center_top_bar"] span').each(function(index){
            var requestUrl = initCenterTopTabUrl(index,true);
            loadZhswJsonDetail("zhsw_center_top_tab"+(index+1),requestUrl,true,true,52,"newsList01",defaultSize);
        });
    }
}

/**
 * C、左中，收文
 */
function loadZhswLeftMiddle(defaultSize){
    loadZhswJsonDetail("zhsw_left_middle",leftMiddleAjaxUrl,false,false,56,'',defaultSize);
}

/**
 * D、中中，发文
 */
function loadZhswCenterMiddle(defaultSize){
    loadZhswJsonDetail("zhsw_center_middle",centerMiddleAjaxUrl,false,false,56,'',defaultSize);
}

/**
 * E、右上，日程管理
 * 请求后台，将具有日程的日期加上颜色标示
 */
function loadZhswRightTop(curDate,defaultSize){
    //rightTopAjaxUrl
    //首先判断是否存在该模块
    var curHtml = $('#zhsw_right_top');
    if(curHtml && curHtml.children.length > 0){
        //
    }
    //此处在右中上的部分显示日程列表
    var url = convertURL(rightTopAjaxUrl)+"&date="+curDate;
    loadZhswJsonDetail("zhsw_right_middle_top",url,false,false,30,'',defaultSize);
}
/**
 * F、右中，待办任务
 */
function loadZhswRightMiddle(){
    //loadZhswRightMiddleJsonDetail("zhsw_right_middle",rightMiddleAjaxUrl,false,false,30);
}

/**
 * 初始化页面中上部分，带有选项卡的数据url地址
 * @param index
 * @param isAajx
 * @returns {string}
 */
function initCenterTopTabUrl(index,isAajx){
    var requestUrl = "";
    switch(index+1){
        case 1:
            requestUrl = isAajx?centerTopTab1AjaxUrl:centerTopTab1Url;
            break;
        case 2:
            requestUrl = isAajx?centerTopTab2AjaxUrl:centerTopTab2Url;
            break;
        case 3:
            requestUrl = isAajx?centerTopTab3AjaxUrl:centerTopTab3Url;
            break;
        case 4:
            requestUrl = isAajx?centerTopTab4AjaxUrl:centerTopTab4Url;
            break;
        default:
    }
    return requestUrl;
}

/**
 * 针对中部区域进行处理，特指左中右的中部，因该区域元素规则一致，可以统一处理
 * 传递对应的元素id和请求地址即可
 * @param centerEleId
 * @param requestUrl
 * @param hasDate 是否显示日期
 * @param hasContent 是否显示内容
 * @param limit 字数省略数量
 * @param ulClass ul的样式
 */
function loadZhswJsonDetail(centerEleId,requestUrl,hasDate,hasContent,limit,ulClass,defaultSize){
    //首先判断是否存在该模块
    var curHtml = $('#'+centerEleId);
    if(curHtml && curHtml.children.length > 0){
        $(curHtml).find('ul').remove();
        var url = convertURL(requestUrl)+"&size="+defaultSize;
        //获取json数据
        //var jsondata = initAjaxJsonData(centerEleId,requestUrl);
        var param = {
            type: null
        };
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            async: false,
            success: function (result) {
                if(result.constructor==String){
                    result = jQuery.parseJSON(result + "");
                }
                if (result.success) {
                    jsondata = result.data;
                    if(jsondata) {
                        try {
                            if(jsondata.constructor==String){
                                jsondata = jQuery.parseJSON(jsondata + "");
                            }
                        } catch (e) {}
                    }
                    //解析数据
                    if(jsondata){
                        var ulEle = $('<ul/>');
                        if(!ulClass){
                            ulClass = "newsList";
                        }
                        ulEle.addClass(ulClass);

                        var length = defaultSize;
                        if(jsondata.length<length){
                            length = jsondata.length;
                        }
                        for (var i = 0; i <length; i++) {
                            var jsonobj = jsondata[i];
                            if(jsonobj){
                                jsonobj = initJsonObjNull(jsonobj);
                                var childEle = $('<li/>');
                                var title = sub_suolve(jsonobj.title,limit);
                                var tmpHtml = '';
                                if(hasDate){
                                    var date = jsonobj.date;
                                    if(date && date.length > 4){
                                        var year = date.substr(0,4);
                                        var monthday = date.substr(5);
                                        tmpHtml += '<span>'+monthday+'<br>'+year+'</span>';
                                    }
                                    /*tmpHtml += '<span>'+jsonobj.date+'</span>';*/
                                    //<span>06-04<br/>2015</span>
                                    //<a href="" target="_blank"><i class="icon-caret-right">title</i></a>
                                    //<p>content</p>
                                    tmpHtml += '<a href="'+jsonobj.url+'" target="_blank" title="'+jsonobj.title+'">'+title+'</a>';
                                }else{
                                    //<a href=""><i class="icon-caret-right"></i> 国土资 源管理2015年上半年重点工作安排</a>
                                    tmpHtml += '<a href="'+jsonobj.url+'" target="_blank" title="'+jsonobj.title+'"><i class="icon-caret-right"></i>'+title+'</a>';
                                }
                                if(hasContent){
                                    tmpHtml += '<p>'+jsonobj.content+'</p>';
                                }
                                $(tmpHtml).appendTo(childEle);
                                $(ulEle).append(childEle);
                            }
                        }
                        $(curHtml).append(ulEle);
                    }
                }
            }
        });
    }
}
/**
 * 针对中部区域进行处理，特指左中右的中部，因该区域元素规则一致，可以统一处理
 * 传递对应的元素id和请求地址即可
 * @param centerEleId
 * @param requestUrl
 * @param hasDate 是否显示日期
 * @param hasContent 是否显示内容
 * @param limit 字数省略数量
 * @param ulClass ul的样式
 */
function loadZhswRightMiddleJsonDetail(centerEleId,requestUrl,hasDate,hasContent,limit,ulClass){
    //首先判断是否存在该模块
    var curHtml = $('#'+centerEleId);
    if(curHtml && curHtml.children.length > 0){
        var url = convertURL(requestUrl)+"&defaultSize="+defaultSize;
        //获取json数据
        //var jsondata = initAjaxJsonData(centerEleId,requestUrl);
        var param = {
            type: null
        };
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            async: false,
            dataType: "jsonp",
            success: function (result) {
                if(result.constructor==String){
                    result = jQuery.parseJSON(result + "");
                }
                if (result.success) {
                    jsondata = result.data;
                    if(jsondata) {
                        try {
                            if(jsondata.constructor==String){
                                jsondata = jQuery.parseJSON(jsondata + "");
                            }
                        } catch (e) {}
                    }
                    //解析数据
                    if(jsondata){
                        if(!ulClass){
                            ulClass = "obtainDateContent";
                        }
                        var topulEle = $('<ul/>');
                        topulEle.addClass(ulClass);
                        var downulEle = $('<ul/>');
                        downulEle.addClass(ulClass);

                        var topIndex=1;
                        var downIndex=1;
                        for (var i = 0; i <jsondata.length; i++) {
                            var jsonobj = jsondata[i];
                            if(jsonobj){
                                jsonobj = initJsonObjNull(jsonobj);
                                //此处分待办任务和超期任务进行处理，上面显示超期，下面显示待办
                                var childEle = $('<li/>');
                                //<a href="#"><font >1.</font> 无锡市滨湖区2016年度第32批次村镇...</a>
                                var title = sub_suolve(jsonobj.title,limit);
                                var type = jsonobj.type;
                                if(type){
                                    if(type == "top"){
                                        var tmpHtml ='<a href="'+jsonobj.url+'" target="_blank" title="'+jsonobj.title+'"><font>'+(topIndex)+'.</font>'+title+'</a>';
                                        $(tmpHtml).appendTo(childEle);
                                        $(topulEle).append(childEle);
                                        topIndex ++;
                                    }else if(type == "down"){
                                        var tmpHtml ='<a href="'+jsonobj.url+'" target="_blank" title="'+jsonobj.title+'"><font>'+(downIndex)+'.</font>'+title+'</a>';
                                        $(tmpHtml).appendTo(childEle);
                                        $(downulEle).append(childEle);
                                        downIndex ++;
                                    }
                                }
                            }
                        }
                        $(curHtml).find('div[id="'+centerEleId+'_top"]').append(topulEle);
                        $(curHtml).find('div[id="'+centerEleId+'_down"]').append(downulEle);
                    }
                }
            }
        });

    }
}
/**
 * 根据url地址请求返回模块所需要的json对象数据，
 * 如果是字符串，该方法会将字符串转换为json对象
 * 返回数据格式
 * {"success":true,"data":[{"id": "","url":"","title":"","date":"","content":""},{"id": "","url":"","title":"","date":"","content":""}],"msg":"操作成功"}
 * 其中数据部分格式：'[{"id": "","url":"","title":"","date":"","content":""},{"id": "","url":"","title":"","date":"","content":""}]'
 *  其中date、content是针对页面中上部的数据展示
 *  type适用于区分待办任务、超期任务
 *  以上字段可作其他用途
 * @param requestUrl
 */
function initAjaxJsonData(centerEleId,requestUrl){
    var jsondata = null;
    var url = convertURL(requestUrl)+"&defaultSize="+defaultSize;
    //正式ajax请求
    var param = {
        type: null
    };
    $.ajax({
        type: "POST",
        url: url,
        data: param,
        async: false,
        dataType: "jsonp",
        success: function (result) {
            if(result.constructor==String){
                result = jQuery.parseJSON(result + "");
            }
            if (result.success) {
                jsondata = result.data;
                if(jsondata) {
                    try {
                        if(jsondata.constructor==String){
                            jsondata = jQuery.parseJSON(jsondata + "");
                        }
                    } catch (e) {}
                }
            }
        }
    });
    //jsondata = '[{"id": "","url":"111","title":"222","date":"333","content":""},{"id": "","url":"444","title":"555","date":"666","content":""}]';
    //解析数据
    /*if(!jsondata){
     jsondata = initTestAjaxJsonData(centerEleId,requestUrl)
     }
     //var
     if(jsondata) {
     try {
     if(jsondata[0].length==1){
     jsondata = jQuery.parseJSON(jsondata + "");
     }
     } catch (e) {}
     }
     return jsondata;*/
}

function initTestAjaxJsonData(centerEleId,requestUrl){
    //模拟测试数据
    var jsondata = [];
    for(var t=0;t<9;t++){
        var text = "";
        if(t == 0){text = "国土资源部矿产资源储量评审中心石油天然气共生伴生矿产综合勘查综合评价研究公开招标公告";
        }else if(t == 1){text = "中国土地勘测规划院2015年度新增建设用地管理信息核实项目中标公告";
        }else if(t == 2){text = "中国土地勘测规划院2016年全国土地利用变更调查监测与核查遥感监测任务公开招标公告";
        }else if(t == 3){text = "我国12.72亿亩耕地无重金属污染";
        }else if(t == 4){text = "不动产登记暂行条例实施细则";
        }else if(t == 5){text = "2016年一季度国土资源主要统计数据";
        }else if(t == 6){text = "中国土地勘测规划院2016年全国土地利用变更调查监测与核查遥感监测任务公开招标公告";
        }else if(t == 7){text = "我国12.72亿亩耕地无重金属污染";
        }else if(t == 8){text = "不动产登记暂行条例实施细则";
        }else if(t == 9){text = "2016年一季度国土资源主要统计数据";
        }
        var content = "3月3日下午，国土资源部党组书记、部长、国家土地总督察姜大明主持召开第9次部党组会，传达学习习近平总书记就学习毛泽东同志《党委会的工作方法》的重要批示精神，研究贯彻落实意见，传达学习习近平总书记就学习毛泽东同志《党委会的工作方法》的重要批示精神，研究贯彻落实意见。";
        var jsonobj = {
            img : portalUrl+"/static/images/"+(t+1)+".jpg",
            url : "http://www.gtmap.cn",
            title : text,
            content : content,
            type : (t%2 ==0) ?"top":"down",
            date : "2016-05-1"+(t+1)
        };
        if(t<=7){
            jsondata.push(jsonobj);
        }
        if(t>7 && centerEleId == "zhsw_right_middle"){
            jsondata.push(jsonobj);
        }
    }
    return jsondata;
}

/**
 * 窗体打开方法
 * @param url
 * @param name
 */
function openWin(url,name){
    var w_width=screen.availWidth-10;
    var w_height= screen.availHeight-32;
    // 若name中存在特殊字符，ie下无法打开窗口
    // 去掉name中的特殊字符
    name = name.replace(/[\@\#\$\%\^\&\*\{\}\:\"\<\>\?]/g, "");
    window.open(url, name, "left=1,top=0,height="+w_height+",width="+w_width+",resizable=yes,scrollbars=yes");
}

function initJsonObjNull(jsonobj){
    if (typeof(jsonobj.id) == "undefined" || !jsonobj.id) {
        jsonobj.id = "";
    }
    if (typeof(jsonobj.title) == "undefined" || !jsonobj.title) {
        jsonobj.title = "";
    }
    if (typeof(jsonobj.url) == "undefined" || !jsonobj.url) {
        jsonobj.url = "";
    }
    if (typeof(jsonobj.date) == "undefined" || !jsonobj.date) {
        jsonobj.date = "";
    }
    if (typeof(jsonobj.content) == "undefined" || !jsonobj.content) {
        jsonobj.content = "";
    }
    return jsonobj;
}

