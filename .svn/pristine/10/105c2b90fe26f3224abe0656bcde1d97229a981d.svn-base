<iframe id="userPhotoFrame" name="userPhotoFrame"  src="" style="display:none;" ></iframe>
<div id="userPhotoModal" class="modal hide fade selectConfigModal" role="dialog" aria-labelledby="userPhotoModal" aria-hidden="true" style="height:384px;">
    <h4 class="modal-top-bar">
        <div>
            <i class="icon-picture">照片信息</i>
            <div class="topBtnDiv">
                <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" >
                    <i class="icon-remove icon-large"></i>
                </a>
            </div>
        </div>
    </h4>
    <form method="post" class="row cl form-horizontal" id="userPhotoForm" enctype="multipart/form-data" target="userPhotoFrame">
        <input type="hidden" id="userPhotoId" name="userId">
        <div class="row cl form-element">
            <div class="row cl form-element">
                <div class="col-2 form-label">
                    预览
                </div>
                <div class="col-10 form-input">
                    <img  id="userPhotoImg" src="" height="200" width="200" />
                </div>
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                用户照片
            </div>
            <div class="col-10 form-input"   >
                <input name="user_Photo" id="userPhoto" type="file" class="input-text size-M">
            </div>
        </div>
        <div class="row cl pd-10" style="text-align:center;">
            <button class="btn btn-secondary size-M btn-primary" type="button"  data-dismiss="modal"  id="userPhotoImgButton" >
                <i class="icon-save icon-large"></i> 确认
            </button>
            &nbsp;&nbsp;
            <button type="button" class="btn btn-inverse size-M model-close" id="cancleBtn" data-dismiss="modal">
                <i class="icon-remove icon-large"></i>取消
            </button>
        </div>
    </form>
</div>

<div id="userSignModal" class="modal hide fade selectConfigModal" role="dialog" aria-labelledby="userSignModal" aria-hidden="true" style="height:384px;">
    <h4 class="modal-top-bar">
        <div>
            <i class="icon-picture">签名信息</i>
            <div class="topBtnDiv">
                <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" >
                    <i class="icon-remove icon-large"></i>
                </a>
            </div>
        </div>
    </h4>
    <form method="post" class="row cl form-horizontal" id="userSignForm" enctype="multipart/form-data" target="userPhotoFrame" >
        <input type="hidden" id="userSignId" name="userId">
        <div class="row cl form-element">
            <div class="row cl form-element">
                <div class="col-2 form-label">
                    预览
                </div>
                <div class="col-10 form-input">
                    <img  id="userSignImg" src="" height="200" width="350" />
                </div>
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-2 form-label">
                签名图片
            </div>
            <div class="col-10 form-input"   >
                <input name="user_Sign" id="userSign" type="file" class="input-text size-M" >
            </div>
        </div>

        <div class="row cl pd-10" style="text-align:center;">
            <button class="btn btn-secondary size-M btn-primary" type="button"   data-dismiss="modal" id="userSignImgButton"   >
                <i class="icon-save icon-large"></i> 确认
            </button>
            &nbsp;&nbsp;
            <button type="button" class="btn btn-inverse size-M model-close" id="cancleBtn2" data-dismiss="modal">
                <i class="icon-remove icon-large"></i>取消
            </button>
        </div>
    </form>
</div>

<div id="userLoginInfoModal" class="modal hide fade selectConfigModal" role="dialog" aria-labelledby="userLoginInfoModal" aria-hidden="true" style="height:247px;">
    <h4 class="modal-top-bar">
        <div>
            <i class="icon-info-sign">登陆信息</i>
            <div class="topBtnDiv">
                <a href="javascript:void(0);" class="rightclose" data-dismiss="modal" aria-hidden="true" value="取消" >
                    <i class="icon-remove icon-large"></i>
                </a>
            </div>
        </div>
    </h4>
    <form method="post" class="row cl form-horizontal" id="userLoginForm" >
        <input type="hidden" id="userLoginId" name="userId">
        <div class="row cl form-element">
            <div class="col-3 form-label">
                登陆名
            </div>
            <div class="col-9 form-input">
                <input  id="loginName" name="loginName" type="text" class="input-text size-M" >
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                登陆密码
            </div>
            <div class="col-9 form-input">
                <input   id="loginPassword" name="loginPassword" type="password" class="input-text size-M" >
            </div>
        </div>
        <div class="row cl form-element">
            <div class="col-3 form-label">
                确认登陆密码
            </div>
            <div class="col-9 form-input">
                <input  id="loginPassword2" type="password" class="input-text size-M" >
            </div>
        </div>

        <div class="row cl pd-10" style="text-align:center;">
            <button class="btn btn-secondary size-M btn-primary" type="button" id="userLoginbutton" data-dismiss="modal" >
                <i class="icon-screenshot icon-large"></i> 提交
            </button>
            &nbsp;&nbsp;
            <button type="button" class="btn btn-inverse size-M model-close" id="closeLoginInfobutton" data-dismiss="modal">
                <i class="icon-remove icon-large"></i>取消
            </button>
        </div>
    </form>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $('#photoBtn').click(function () {
            //获取用户照片
            $('#userPhoto').val('');
            var userId = $('#userPhotoId').val();
            $('#userPhotoImg').attr("src",cur_proj_url+"/config/user/userPhoto?userId="+userId+"&time="+new Date());
            $('#userPhotoModal').modal({show:true});
        });
        $('#signBtn').click(function () {
            //获取签名图片
            $('#userSign').val('');
            var userId = $('#userSignId').val();
            $('#userSignImg').attr("src",cur_proj_url+"/config/user/userSign?userId="+userId+"&time="+new Date());
            $('#userSignModal').modal({show:true});
        });
        $('#loginBtn').click(function () {
            //获取登陆用户名密码
            $('#userLoginInfoModal').modal({show:true});
        });

        $('#userLoginbutton').click(function () {
            saveUserLogindata();
        });
        $('#userPhotoImgButton').click(function () {
            var obj=$('#userPhoto');
            var url=cur_proj_url+'/config/user/saveUserPhoto';
            var form=$('#userPhotoForm');
            savePicdata(obj,url,form)
        });

        $('#userSignImgButton').click(function () {
            var obj=$('#userSign');
            var url=cur_proj_url+'/config/user/saveUserSign';
            var form=$('#userSignForm');
            savePicdata(obj,url,form)
        });
    });
    jQuery.fn.extend({
        uploadPreview: function (opts) {
            var _self = this,
                    _this = $(this);
            opts = jQuery.extend({
                Img: "userPhotoImg",
                Width: 200,
                Height: 200,
                ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
                Callback: function () {}
            }, opts || {});
            _self.getObjectURL = function (file) {
                var url = null;
                if (window.createObjectURL != undefined) {
                    url = window.createObjectURL(file);
                } else if (window.URL != undefined) {
                    url = window.URL.createObjectURL(file);
                } else if (window.webkitURL != undefined) {
                    url = window.webkitURL.createObjectURL(file);
                }
                return url;
            };
            _this.bind('change',function () {
                if (this.value) {
                    if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                        alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
                        var file = $(this);
                        /* file.after(file.clone().val(""));
                        file.remove();*/
                        file.val("");
                        $("#" + opts.Img).attr('src',"");
                        return false;
                    }

                    var  browserCfg = {};
                    var ua = window.navigator.userAgent;
                    if (ua.indexOf("MSIE")>=1){
                        browserCfg.ie = true;

                    }else if(ua.indexOf("Firefox")>=1){
                        browserCfg.firefox = true;
                    }else if(ua.indexOf("Chrome")>=1){
                        browserCfg.chrome = true;
                    }
                    var filesize = 0;
                    if(browserCfg.firefox || browserCfg.chrome ){
                        filesize =$(this)[0].files[0].size;
                    }
                    if( filesize>102400){
                        alert("操作失败！上传图片不能大于100Kb!");
                        $(this).val("");
                        $("#" + opts.Img).attr('src',"");
                        return false;
                    }

                    if (!$.support.leadingWhitespace) {
                        try {
                            $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
                        } catch (e) {
                            var src = "";
                            var obj = $("#" + opts.Img);
                            var div = obj.parent("div")[0];
                            _self.select();
                            if (top != self) {
                                window.parent.document.body.focus();
                            } else {
                                _self.blur();
                            }
                            src = document.selection.createRange().text;
                            document.selection.empty();
                            obj.hide();
                            obj.parent("div").css({
                                'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                                'width': opts.Width + 'px',
                                'height': opts.Height + 'px'
                            });
                            div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src;
                        }
                    } else {
                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
                    }
                    opts.Callback();
                }
            })
        }
    });

    $("#userPhoto").uploadPreview({ Img: "userPhotoImg", Width: 200, Height: 200, ImgType: ["gif", "jpeg", "jpg", "bmp", "png"], Callback: function () { }});
    $("#userSign").uploadPreview({ Img: "userSignImg", Width: 200, Height: 200, ImgType: ["gif", "jpeg", "jpg", "bmp", "png"], Callback: function () { }});
</script>