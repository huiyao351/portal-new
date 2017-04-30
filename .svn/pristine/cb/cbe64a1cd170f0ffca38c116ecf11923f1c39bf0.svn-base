$(document).ready(function(){
	menuCurrent();
	menuHandle();
});

/**
 * 设置二级、三级菜单收缩样式
 * @param pn
 * @param cn
 */
function unfoldMenu(pn, cn){
	var p = $('span.flip'), c = $('div.panel'), cc = $('div.panel-current');

	p.removeClass('flip-current');
	cc.hide(200, function(){
		$(this).removeAttr('style').removeClass('panel-current');
	})
	if(c.index(cn) != c.index(cc)){
		pn.addClass('flip-current');
		cn.show(200, function(){
			$(this).removeAttr('style').addClass('panel-current');
		});
	}
}

function menuHandle(){
	$('span.flip').click(function(){
		var pn = $(this), cn = pn.next();
		unfoldMenu(pn, cn);
	});
}

//设置默认下当前展开
function menuCurrent(){
	var idx = $('input.menu-code-index').val(), m, pn, cn, p = $('span.flip'), c = $('div.panel'), cc = $('div.panel-current');
	if(/c(\d)+/.test(idx)){ //判断c（十进制）条件
		m = $('a[data-service-index="' + idx + '"]').addClass('current');
		cn = m.parents('div.panel');
		pn = cn.prev();
		unfoldMenu(pn, cn);
	}
}