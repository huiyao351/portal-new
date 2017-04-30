
/**
*rmb小写转大写（小写的值,转大写的字段id）
*/
function changeDx(num,name){
	var title = "人民币";
	if(f_check_number(num,title)){
	var strOutput = "",
 	strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';
		num=(num.value*100).toString();
 	var intPos = num.indexOf('.');
 	if (intPos >= 0){
 		num = num.substring(0, intPos) + num.substr(intPos + 1, 2);
 	}
 	strUnit = strUnit.substr(strUnit.length - num.length);
 	for (var i=0; i < num.length; i++){
 		strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);
 	}
	var dx = strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");
	$("#" + name).val(dx);
	} 
}

/**
*rmb小写转大写（小写的值,转大写的字段id）
*/
function changeDxWy(num,name){
	var title = "人民币";
	if(f_check_number(num,title)){
	var strOutput = "",
 	strUnit = '仟佰拾亿仟佰拾万仟佰拾元角分';
		num=(num.value*100*10000).toString();
 	var intPos = num.indexOf('.');
 	if (intPos >= 0){
 		num = num.substring(0, intPos) + num.substr(intPos + 1, 2);
 	}
 	strUnit = strUnit.substr(strUnit.length - num.length);
 	for (var i=0; i < num.length; i++){
 		strOutput += '零壹贰叁肆伍陆柒捌玖'.substr(num.substr(i,1),1) + strUnit.substr(i,1);
 	}
	var dx = strOutput.replace(/零角零分$/, '整').replace(/零[仟佰拾]/g, '零').replace(/零{2,}/g, '零').replace(/零([亿|万])/g, '$1').replace(/零+元/, '元').replace(/亿零{0,3}万/, '亿').replace(/^元/, "零元");
	$("#" + name).val(dx);
	} 
}
function getUrlVars()  {
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

// JS 四舍五入自定义小数位数，空缺补0
function formatnumber(value, num,sfbl) {
    var a, b, c, i;
    a = value.toString();
    b = a.indexOf(".");
    c = a.length;
    if (num == 0) {
        if (b != -1) {
            a = a.substring(0, b);
        }
    } else {
        if (b == -1) {
            if(sfbl){
                a = a + ".";
                for (i = 1; i <= num; i++) {
                    a = a + "0";
                }
            }
        } else {
            a = a.substring(0, b + num + 1);
            if(sfbl){
                for (i = c; i <= b + num; i++) {
                    a = a + "0";
                }
            }
        }
    }
    return a;
}

Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds() // 毫秒
	};
	if (/(y+)/.test(fmt)){
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
		
	for (var k in o){
		if (new RegExp("(" + k + ")").test(fmt)){
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]): (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

//判断object是否是空对象,空数组,空字符串,null,undefined,如果是则返回false,不是则返回true
//空字符串不包括"(空格)",只特指""
function isNotBlank(object) {
    if (typeof object === "object" && !(object instanceof Array)) {
        var hasProp = false;
        for (var prop in object) {
            hasProp = true;
            break;
        }
        if (hasProp) {
            hasProp = [hasProp];
        } else {
            return false;
        }
        return hasProp;
    }
    return typeof object != "undefined" && object != "";
}

function isBlank(object){
    return !isNotBlank(object);
}

if (!Array.prototype.forEach) {
    Array.prototype.forEach = function (callback, thisArg) {
        var T, k;
        if (this == null) {
            throw new TypeError(" this is null or not defined");
        }
        var O = Object(this);
        var len = O.length >>> 0; // Hack to convert O.length to a UInt32
        if ({}.toString.call(callback) != "[object Function]") {
            throw new TypeError(callback + " is not a function");
        }
        if (thisArg) {
            T = thisArg;
        }
        k = 0;
        while (k < len) {
            var kValue;
            if (k in O) {
                kValue = O[k];
                callback.call(T, kValue, k, O);
            }
            k++;
        }
    };
}


/**
 1、字段为纯数字，判断是否为数字
 2、字段为数字或者数字+小数点
 3、字段为字符串（不能输入特殊字符，#、@、*等），或者字母+数字
 4、校验数字的长度，从x到y
 5、字段为邮箱类型，***@***
 6、检验两字段的数值大小，进行比较
 7、判断是否为邮政编码，六位数字
 8、检验字段是否为空
 9、校验字符串的长度，从x到y
 */

/*
 * 判断是否为数字，是则返回true,否则返回false
 */
function f_check_number(obj,code){
    if(obj && obj.value){
        if (/^\-?[0-9]*\.?[0-9]*$/.test(obj.value)){
            return true;
        }else{
            obj.value="";
            f_alert(code,"请输入数字");
            //obj.focus();
            return false;
        }
    }
    return false;
}

/*
 * 判断是否为整数，是则返回true,否则返回false
 */
function f_check_integer(obj,code) {
    if(obj && obj.value){
        if (/^(\+|-)?\d+$/.test(obj.value)){
            return true;
        }else{
            obj.value="";
            f_alert(code,"请输入整数");
            //obj.focus();
            return false;
        }
    }
    return false;
}

/*
 * 判断是否为实数，是则返回true,否则返回false
 */
function f_check_float(obj,code){
    if(obj && obj.value){
        if (/^(\+|-)?\d+($|\.\d+$)/.test(obj.value)){
            return true;
        }else{
            obj.value="";
            f_alert(code,"请输入实数");
            //obj.focus();
            return false;
        }
    }
    return false;
}

/**
 * 取得字符串的字节长度
 */
function strlen(str){
    var i;
    var len = 0;
    for (i=0;i<str.length;i++){
        if (str.charCodeAt(i)>255) len+=2;else len++;
    }
    return len;
}

/*
 用途：检查输入字符串是否只由字母、数字组成
 输入：
 value：字符串
 返回：
 如果通过验证返回true,否则返回false
 */
function f_check_NumOrLett(obj,code){ //判断是否是字母、数字组成
    if(obj && obj.value){
        var regu = "^[0-9a-zA-Z]+$";
        var re = new RegExp(regu);
        var mm = obj.value.replace(/\s/g,'') ;
        if (re.test(mm)) {
            return true;
        }
        f_alert(code,"请输入字母或数字");
        //obj.focus();
        return false;
    }
    return false;
}


function f_check_NumLength(obj,code){
    if(obj && obj.value){
        if((obj.value.length >=2)&&(obj.value.length<=8)){
            return true;
        }else{
            f_alert(code,"长度在2和8之间");
            //obj.focus();
            return false;
        }
    }
    return false;
}

/*
 * 校验数字的长度和精度
 */
function f_check_double(obj,code){
    if(obj && obj.value){
        var numReg;
        var value = obj.value;
        var strValueTemp, strInt, strDec;
        var dtype = obj.eos_datatype;
        var pos_dtype = dtype.substring(dtype.indexOf("(")+1,dtype.indexOf(")")).split(",");
        var len = pos_dtype[0], prec = pos_dtype[1];
        try{
            numReg =/[\-]/;
            strValueTemp = value.replace(numReg, "");
            numReg =/[\+]/;
            strValueTemp = strValueTemp.replace(numReg, "");
            //整数
            if(prec==0){
                numReg =/[\.]/;
                if(numReg.test(value) == true){
                    f_alert(code, "输入必须为整数类型");
                    //obj.focus();
                    return false;
                }
            }
            if(strValueTemp.indexOf(".") < 0 ){
                if(strValueTemp.length >( len - prec)){
                    f_alert(code, "整数位不能超过"+ (len - prec) +"位");
                    //obj.focus();
                    return false;
                }
            }else{
                strInt = strValueTemp.substr( 0, strValueTemp.indexOf(".") );
                if(strInt.length >( len - prec)){
                    f_alert(code, "整数位不能超过"+ (len - prec) +"位");
                    //obj.focus();
                    return false;
                }
                strDec = strValueTemp.substr( (strValueTemp.indexOf(".")+1), strValueTemp.length );
                if(strDec.length > prec){
                    f_alert(code, "小数位不能超过"+  prec +"位");
                    //obj.focus();
                    return false;
                }
            }
            return true;
        }catch(e){
            f_alert('',"in f_check_double = " + e);
            //obj.focus();
            return false;
        }
    }
    return false;
}

/*
 用途：检查输入对象的值是否符合E-Mail格式
 输入：str 输入的字符串
 返回：如果通过验证返回true,否则返回false
 */
function f_check_email(obj){
    if(obj && obj.value){
        var myReg = /^([-_A-Za-z0-9\.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
        if(myReg.test(obj.value))
            return true;
        f_alert('',"请输入合法的电子邮件地址\r\n如：zhanglianfeng@163.com");
        //obj.focus();
        return false;
    }
    return false;
}

/**
 * 校验两个表单域数据的大小，目前只允许比较数字。
 * @param obj1 小值表单域
 * @param obj2 大值表单域
 */
function checkIntervalObjs(obj1 , obj2,code){
    var val1 = parseFloat(obj1.value);
    var val2 = parseFloat(obj2.value);
    // 数字类型的比较
    if((isNaN(val1) && !isnull(val1)) || (isNaN(val2) && !isnull(val2))){
        f_alert(code,"值不为数字则不能比较！");
        //obj1.focus();
        return false;
    }
    if(val1 > val2){
        //obj2.focus();
        f_alert(code,"起始值不能大于终止值！");
        return false;
    }
    return true;
}

/* 判断是否为邮政编码 */
function f_check_zipcode(obj){
    if(!f_check_number(obj,'邮编'))
        return false;
    if(obj.value.length!=6){
        f_alert('',"邮政编码长度必须是6位");
        //obj.focus();
        return false;
    }
    return true;
}

/*
 要求：一、移动电话号码为11或12位，如果为12位,那么第一位为0
 二、11位移动电话号码的第一位和第二位为"13"
 三、12位移动电话号码的第二位和第三位为"13"
 用途：检查输入手机号码是否正确
 输入：
 s：字符串
 返回：
 如果通过验证返回true,否则返回false
 */
function f_check_mobile(obj){
    if(obj && obj.value){
        var regu =/(^[1][0-9]{10}$)|(^0[1][0-9]{10}$)/;
        var re = new RegExp(regu);
        if (re.test(obj.value)) {
            return true;
        }
        f_alert('',"请输入正确的手机号码\r\n如：18795971072");
        //obj.focus();
        return false;
    }
    return false;
}

/*
 要求：一、电话号码由数字、"("、")"和"-"构成
 二、电话号码为3到8位
 三、如果电话号码中包含有区号，那么区号为三位或四位
 四、区号用"("、")"或"-"和其他部分隔开
 用途：检查输入的电话号码格式是否正确
 输入：
 strPhone：字符串
 返回：
 如果通过验证返回true,否则返回false
 */
function f_check_phone(obj){
    if(obj && obj.value){
        var regu =/^((([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$)/;
        var re = new RegExp(regu);
        if (re.test(obj.value)) {
            return true;
        }
        f_alert('',"请输入正确的电话号码\r\n如：024-49309744");
        // obj.focus();
        return false;
    }
}

/**
 * 检测字符串是否为空
 */
function isnull(str){
    var i;
    if(str.length == 0)
        return true;
    for (i=0;i<str.length;i++){
        if (str.charAt(i)!=' ')
            return true;
    }
    return false;
}

/**
 * 检测字符串是否为空
 */
function isNotNull(obj,code){
    var i;
    var str = obj.value;
    if(str == null || str == ""){
        f_alert(code,"请输入内容！");
        //obj.focus();
        return false;
    }
    return true;
}
/**
 * 检测字符串是否为空 ,返回为空对象提示字符串
 */
function isNotNullRetureStr(obj,code){
    try{
        var str = obj.value;
        if(str == null || str == ""){
            return code+"：请选择或输入内容！<br>";
        }
        return "";
    }catch(e){
        return "";
    }
    return "";
}
/**
 * 检测字符串为空
 */
function isNull(obj,code){
    var i;
    var str = obj.value;
    if(str != ""){
        //obj.focus();
        return false;
    }
    return true;
}
/**
 * 判断是否是汉字、字母、数字组成[^%&',;=?$\x22]
 */
function f_check_ZhOrNumOrLett(obj,code){ //判断是否是汉字、字母、数字组成
    if(obj && obj.value){
        var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
        var re = new RegExp(regu);
        if (re.test(obj.value)) {
            return true;
        }
        f_alert(code,"请输入常用字符或文字,\n不能含有特殊字符和空格!");
        return false;
    }
    return false;
}

function f_alert(code,str){
    var msg = "";
    if(code && code != ""){
        msg = code+":"+str;
    }else{
        msg = str;
    }
    msg = msg.replace(/\\n/g,"\n");
    alert(msg);
}

/*
 去掉double类型小数点后面多余的0
 参数：old 要处理的字符串或double
 返回值：newStr 没有多余零的小数或字符串
 例： cutZero(123.000) -> 123
 cutZero(123.0001) -> 123.0001
 cutZero(10203000.0101000) -> 10203000.0101
 cutZero(10203000) -> 10203000
 */
function cutZero(old){
    //拷贝一份 返回去掉零的新串
    var newstr=old;
    //循环变量 小数部分长度
    var leng = old.length-old.indexOf(".")-1
    //判断是否有效数
    if(old.indexOf(".")>-1){
        //循环小数部分
        for(i=leng;i>0;i--){
            //如果newstr末尾有0
            if(newstr.lastIndexOf("0")>-1 && newstr.substr(newstr.length-1,1)==0){
                var k = newstr.lastIndexOf("0");
                //如果小数点后只有一个0 去掉小数点
                if(newstr.charAt(k-1)=="."){
                    return  newstr.substring(0,k-1);
                }else{
                    //否则 去掉一个0
                    newstr=newstr.substring(0,k);
                }
            }else{
                //如果末尾没有0
                return newstr;
            }
        }
    }
    return old;
}
/**
 * 格式化数字小数位
 */
function formatNumberHandler(obj,decimal,title){
    var area = $(obj).val();
    if (area && jQuery.trim(area)!=""){
        if(f_check_number(obj,title)){
            area = Number(parseFloat(area)).toFixed(decimal);
            $(obj).val(area);
        }
    }
}
//****************以下是js关于数值精准计算的加减乘除算法*********************
/**
 * 精准计算js两个数值的相加，避免精度问题
 */
function accAdd(arg1,arg2){
    if(!arg1 || arg1 == ""){
        arg1 = 0;
    }
    if(!arg2 || arg2 == ""){
        arg2 = 0;
    }
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
    try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
    m=Math.pow(10,Math.max(r1,r2)) ;
    return (arg1*m+arg2*m)/m ;
}
//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg){
    return accAdd(arg,this);
}
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2){
    if(!arg1 || arg1 == ""){
        arg1 = 0;
    }
    if(!arg2 || arg2 == ""){
        return null;
    }
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""))
        r2=Number(arg2.toString().replace(".",""))
        return (r1/r2)*pow(10,t2-t1);
    }
}
//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg){
    return accDiv(this, arg);
}
//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以arg2的精确结果
function accMul(arg1,arg2){
    if(!arg1 || arg1 == ""){
        arg1 = 0;
    }
    if(!arg2 || arg2 == ""){
        arg2 = 0;
    }
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}
//给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg){
    return accMul(arg, this);
}
/**
 * 精准计算两数值相减
 */
function accSubtr(arg1,arg2){
    if(!arg1 || arg1 == ""){
        arg1 = 0;
    }
    if(!arg2 || arg2 == ""){
        arg2 = 0;
    }
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    //last modify by deeka
    //动态控制精度长度
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}
//给Number类型增加一个subtr方法，调用起来更加方便。
Number.prototype.subtr = function (arg){
    return accSubtr(this, arg);
}
//*************************************
/**
 * 替换
 */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
    }else {
        return this.replace(reallyDo, replaceWith);
    }
}

//初始化行政区下拉框
function initdisselect(id){
    $("#"+id).empty();
    $.ajax({
        type: "get",
        url: cur_proj_url+"/config/district/districtList",
        async: false,
        success: function (result) {
            for(var i=0 ;i<result.length;i++){
                var xzq = result[i];
                $("#"+id).append( "<option value="+xzq.districtCode+">"+xzq.districtName+"</option>" );
            }
        }
    });
}

/**
 * 获取树控件中，被修改的数据有哪些
 * 该方法处理的环境是：树控件提前加载，后期再选中节点的情况，需要根据原始数据与最终选中的状态作对比，
 * 过滤掉原始数据一致的，记录下来增加选中和取消选中的节点
 * @param cur_role_userAry 原始节点数据，id数组
 * @param zTreeOrgan 树控件
 * @returns {Array}
 */
function getChangeNodes(cur_rel_ary,zTreeRel){
    //获取选中节点或者是变化节点
    var changeNodes = [];
    //处理状态变化的节点中，当前角色管理记录的数据，如果选中，则修改为不选中，因为不需要传给后台
    //如果未选中，则表示删除了关联关系，需要传递到后台
    var cur_relId_str = ";";
    if(cur_rel_ary && cur_rel_ary.length > 0){
        for(var i=0;i<cur_rel_ary.length;i++){
            var rid = cur_rel_ary[i].id;
            cur_relId_str += rid+";";
            var cNode = zTreeRel.getNodeByParam("id", rid, null);
            if(!cNode.checked){
                var node = {
                    id:cur_rel_ary[i].id,
                    name:cur_rel_ary[i].name,
                    leaf:true,
                    checked:false
                }
                changeNodes.push(node);
            }
        }
    }
    var nodes = zTreeRel.getChangeCheckedNodes();
    if(nodes && nodes.length > 0){
        for(var i=0;i<nodes.length;i++){
            if(!(nodes[i].checked && cur_relId_str.indexOf(";"+nodes[i].id+";")>-1)){
                var node = {
                    id:nodes[i].id,
                    name:nodes[i].name,
                    leaf:true,
                    checked:nodes[i].checked
                };
                //只处理子节点，也就是只处理角色节点
                if(!(nodes[i].children && nodes[i].children.length > 0)){
                    changeNodes.push(node);
                }
            }
        }
    }
    return changeNodes;
}
String.prototype.endWith=function(s){
    if(s==null||s==""||this.length==0||s.length>this.length)
        return false;
    if(this.substring(this.length-s.length)==s)
        return true;
    else
        return false;
    return true;
}

String.prototype.startWith=function(s){
    if(s==null||s==""||this.length==0||s.length>this.length)
        return false;
    if(this.substr(0,s.length)==s)
        return true;
    else
        return false;
    return true;
}

/**
 * 设置控件只读
 * @param id  不区分大小写
 */
function setReadOnlyByID(id){
    try{
        var obj = $('#'+id);
        if(!obj[0]){
            obj = $('#'+id.toLowerCase());
        }
        if(obj[0]){
            obj.attr('readonly','true');
            var tagName=obj.attr("tagName");
            var type=obj.attr("type");
            if (tagName=="INPUT" && (type=="text" || type =="password") || type=="textarea"){
                obj.attr('readonly','true');
            }else if(tagName == "SELECT"){
                obj.attr('disabled','true');
                obj.parent().append('<input type="hidden" name="' + obj.attr("name") + '" id="' + obj.attr("id") + '" value="' + obj.attr("value") + '"/>');
            }
            obj.removeAttr("onfocus");
            obj.removeAttr("onmouseover");
            obj.removeAttr("onmouseout");
            obj.removeAttr("onclick");
            obj.removeAttr("onblur");
        }
    }catch (ex) {
    }
}

//实现不可以编辑的方法
function setReadOnly(obj){
    obj.onmouseover = function(){
        obj.setCapture();
    }
    obj.onmouseout = function(){
        obj.releaseCapture();
    }
    obj.onfocus = function(){
        obj.blur();
    }
    obj.onbeforeactivate = function(){
        return false;
    }
}