<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<meta charset="utf-8">
<title>${ctx.getEnv('portal.title')}</title>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="${base}/static/images/favicon.ico">
<link rel="Shortcut Icon" href="${base}/static/images/favicon.ico">

<!--[if lt IE 9]>
<script type="text/javascript" src="${base}/static/lib/html5.js"></script>
<script type="text/javascript" src="${base}/static/lib/respond.min.js"></script>
<script type="text/javascript" src="${base}/static/lib/PIE_IE678.js"></script>
<![endif]-->


<link href="${base}/static/lib/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/lib/h-ui/css/style.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/lib/h-ui/css/H-ui.doc.css" rel="stylesheet" type="text/css">
<link href="${base}/static/lib/h-ui/css/H-ui.admin.css" rel="stylesheet" type="text/css">
<link href="${base}/static/lib/h-ui/css/laypage.css" rel="stylesheet" type="text/css">
<link href="${base}/static/lib/bootstrapSwitch/bootstrapSwitch.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/lib/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/lib/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/css/public.css" rel="stylesheet" type="text/css" />

<!--[if IE 7]>
<link href="${base}/static/lib/font-awesome/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
<![endif]-->

<!--[if IE 6]>
<script type="text/javascript" src="${base}/static/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->

<script type="text/javascript" src="${base}/static/lib/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${base}/static/lib/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${base}/static/lib/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="${base}/static/lib/layer1.8/layer.min.js"></script>
<script type="text/javascript" src="${base}/static/lib/laypage/laypage.js"></script>
<script type="text/javascript" src="${base}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/static/lib/bootstrapSwitch/bootstrapSwitch.js"></script>
<script type="text/javascript" src="${base}/static/lib/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${base}/static/lib/passwordStrength-min.js"></script>
<script type="text/javascript" src="${base}/static/lib/unslider.min.js"></script>
<script type="text/javascript" src="${base}/static/lib/h-ui/js/H-ui.js"></script>
<!--  调用jQueryUI实现拖动 -->
<script type="text/javascript" src="${base}/static/lib/jquery/jquery-ui.js"></script>
<script type="text/javascript" src="${base}/static/lib/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${base}/static/lib/bootstrap-modal.js"></script>
<script src="${base}/static/js/leftMenu.js"></script>
<script src="${base}/static/js/index_common.js"></script>

<script type="text/javascript">
    //解决ie低版本浏览器获取获取ajax数据失败的问题
    jQuery.support.cors = true;
    var portalUrl = '${base}';
    var platform_url='${path_platform}';
    var subsystemId='${sub.subsystemId!}';
    var handleStyle='${ctx.getEnv('portal.handle.style')}';
</script>
<style type="text/css">
</style>