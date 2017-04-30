<script type="text/javascript">
    /**
     * 处理页面高度，自动增加滚动条处理，每个独立的面板提供单独的滚动条来处理
     */
    //alert($(window).height()+"--"+$(document).height()+"--"+$(document.body).height()+"--"+$(document.body).outerHeight(true)); //浏览器当前窗口可视区域高度
    /*alert($(document).height()); //浏览器当前窗口文档的高度
    alert($(document.body).height());//浏览器当前窗口文档body的高度
    alert($(document.body).outerHeight(true));//浏览器当前窗口文档body的总高度 包括border padding margin*/
    winHeight = $(window).height();//根据页面可用高度
    var screenHeight = window.screen.height;
    if(screenHeight<winHeight) winHeight = screenHeight;
    var contentHeight = winHeight-10;
    $('.treeManage').height(contentHeight);
    contentHeight = contentHeight - 6;
    $(".treeManage .ztreeLeftDiv").height(contentHeight-6);
    $(".treeManage .ztreeRightDiv").height(contentHeight-6);

    //树控件高度，要求树控件内可滚动条控制
    var mainHeight = contentHeight - 6 - 64;
    var leftMainHeight = mainHeight;
    var rightMainHeight = mainHeight;
    var searchDivHeight = 0;
    if ($(".treeManage .ztreeLeftDiv .searchDiv").height() != null) {
        searchDivHeight = $(".treeManage .ztreeLeftDiv .searchDiv").height();
        leftMainHeight = mainHeight - searchDivHeight;
    }
    $(".treeManage .ztreeRightDiv").each(function(){
        if($(this).find('.searchDiv')){
            if(searchDivHeight == 0){
                searchDivHeight = $(this).find('.searchDiv').height();
            }
            rightMainHeight = mainHeight - searchDivHeight;
        }
    });
    if ($(".treeManage .ztreeRightDiv .searchDiv").height() != null) {
        //rightMainHeight = rightMainHeight - $(".treeManage .ztreeRightDiv .searchDiv").height();
    }
    $(".treeManage .ztreeLeftDiv>.ztree").css({height:leftMainHeight+"px"});
    $(".treeManage .ztreeRightDiv>.ztree").css({height:rightMainHeight+"px"});

    $(".modal").draggable();
</script>