
package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.Md5Util;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.PfOrgan;
import com.gtis.portal.entity.PfUser;
import com.gtis.portal.entity.PfUserOrganRel;
import com.gtis.portal.entity.PublicVo;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfOrganService;
import com.gtis.portal.service.PfUserOrganService;
import com.gtis.portal.service.PfUserRoleService;
import com.gtis.portal.service.PfUserService;
import com.gtis.portal.util.BlobHelper;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
import com.gtis.web.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.*;


@Controller
@RequestMapping("config/user")
public class UserController extends BaseController {
    @Autowired
    private PfOrganService organService;
    @Autowired
    private PfUserOrganService userOrganService;

    @Autowired
    private PfUserService userService;

    @Autowired
    private PfUserRoleService userRoleService;
    //学历水平下拉框
    @Resource
    @Qualifier("degreeList")
    List<PublicVo> degreeList;
    //性别下拉框
    @Resource
    @Qualifier("sexList")
    List<PublicVo> sexList;
    @Resource
    @Qualifier("boolListNumber")
    List<PublicVo> boolListNumber;

    @RequestMapping("")
    public String manage(Model model) {
        model.addAttribute("degreeList", degreeList);
        model.addAttribute("sexList", sexList);
        model.addAttribute("boolListNumber", boolListNumber);
        return "/config/user/manager";
    }

    @RequestMapping("json")
    @ResponseBody
    public Object organuserjson(Model model) {
        //组织菜单树，类似构建平台
        UserInfo userInfo = SessionUtil.getCurrentUser();
        String regionCode = SessionUtil.getCurrentUser().getRegionCode();
        if (userInfo.isAdmin()){
            regionCode = null;
        }
        Ztree ztree = organService.getAllOrganUserTree(regionCode);
        List<Ztree> treeList=new ArrayList<Ztree>();
        treeList.add(ztree);

        if (userInfo.isAdmin()){
            //其他部门列表标志位：1表示不往其他部门树里添加子节点
            //2表示往其他部门树里添加子节点
            Ztree othertree =organService.getOtherTree(2);
            othertree.setNoR(true);
            treeList.add(othertree);
        }
        return treeList;
    }
    /*
    所选用户的详细信息
     */
    @RequestMapping("info")
    @ResponseBody
    public Object getUserinfo(@RequestParam(value = "userId", required = true) String userId,HttpServletResponse response) throws Exception{
        PfUser user= userService.findById(userId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("user", user);
        return result;
    }

    @RequestMapping("select")
    public String select(Model model,@RequestParam(value = "foreignId", required = true)String foreignId,String paramString) {
        model.addAttribute("foreignId",foreignId);
        model.addAttribute("paramString",paramString);
        return "/config/user/user-select";
    }

    @RequestMapping(value="save")
    @ResponseBody
    public Object saveUserinfo (HttpServletRequest req , @ModelAttribute(value="user") PfUser user,Model model) throws Exception{
        if (StringUtils.isBlank(user.getUserId())){
            user.setUserId(UUIDGenerator.generate18());
        }
        PfUser usertmp = userService.findById(user.getUserId());
        if (usertmp != null){
            user.setOrganId(usertmp.getOrganId());
            userService.updateUserinfo(user);
        }else {
            //密码不填写，默认为空字符串，用md5加密
            user.setLoginPassword(Md5Util.Build(""));
            userService.insert(user);
            //添加用户与组织的关系表里
            PfUserOrganRel userOrganRel=new PfUserOrganRel();
            userOrganRel.setUserId(user.getUserId());
            userOrganRel.setOrganId(user.getOrganId());
            userOrganRel.setUdrId(UUIDGenerator.generate18());
            userOrganService.insert(userOrganRel);

        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("user", user);
        return result;
    }

    @RequestMapping("delUser")
    @ResponseBody
    public Object del(String organ_id,String del_mark,String user_id){
        //根据删除标志位来判断如何删除
        /*	3仅删除用户与当前部门的关系信息");
	        4永久删除用户（部门和角色列表中删除此用户");
	    */
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(organ_id) && StringUtils.isNotBlank(del_mark) && StringUtils.isNotBlank(user_id)){
                if ("3".equals(del_mark)){
                    //删除部门用户关系
                    userOrganService.deleteUserOrganRelByUserIdAndOrganId(user_id, organ_id);
                    return handlerSuccessJson();
                }else if("4".equals(del_mark)){
                    //删除用户
                    userService.deleteById(user_id);
                    return handlerSuccessJson();
                }
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerErrorJson();
    }

    @RequestMapping("userSign")
    @ResponseBody
    public void getUserSign(@RequestParam(value = "userId", required = true) String userId,HttpServletResponse response) throws Exception{
        PfUser user= userService.findById(userId);
        if(user!=null){
            if(user.getUserSign()!=null&&user.getUserSign().length()!=0){
                outputImg( user.getUserSign(),response);
            }
        }
    }

    @RequestMapping("userPhoto")
    @ResponseBody
    public void getUserPhoto(@RequestParam(value = "userId", required = true) String userId,HttpServletResponse response) throws Exception{
        PfUser user= userService.findById(userId);
        if(user!=null){
            if(user.getUserPhoto()!=null&&user.getUserPhoto().length()!=0){
                outputImg( user.getUserPhoto(),response);
            }
        }
    }

    @RequestMapping("saveUserLogin")
    @ResponseBody
    public Object saveUserLogin(@ModelAttribute(value="user") PfUser userlogin) throws Exception{
        PfUser usertmp= userService.findById(userlogin.getUserId());
        if(usertmp!=null){
            PfUser user=new PfUser();
            user.setUserId(userlogin.getUserId());
            user.setLoginName(userlogin.getLoginName());
            user.setLoginPassword(Md5Util.Build(userlogin.getLoginPassword()));
            userService.updateUserLogin(user);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("success", true);
            result.put("msg", "操作成功！");
            return result;
        }
        return handlerErrorJson();
    }

    @RequestMapping("saveUserPhoto")
    @ResponseBody
    public void saveUserPhoto(@RequestParam(value = "userId", required = true) String userId,@RequestParam MultipartFile user_Photo,HttpServletResponse response) throws Exception{
        PfUser user= userService.findById(userId);
        response.setContentType("text/html;charset=GBK");
        PrintWriter out=  response.getWriter();
        String msg="";
        if(user!=null){
            Map<String, Object> result = new HashMap<String, Object>();
            if( user_Photo.getSize()!=0){
                //判断上传文件是否大于100K
                if(user_Photo.getSize()>102400){
                    msg="操作失败！上传图片不能大于100Kb!";
                }else{
                    Blob user_Photoblob=  BlobHelper.createBlob(user_Photo.getBytes());
                    user.setUserPhoto(user_Photoblob);
                    userService.updateUserBlob(user,0);
                    msg="操作成功！";
                }
            }else{
                msg="操作失败！没有上传的图片！";
            }
        }
        out.print("<script type='text/javascript'>alert('"+msg+"');</script>");
    }
    @RequestMapping("saveUserSign")
    @ResponseBody
    public void saveUserSign(@RequestParam(value = "userId", required = true) String userId,  @RequestParam MultipartFile user_Sign,HttpServletResponse response) throws Exception{
        PfUser user= userService.findById(userId);
        response.setContentType("text/html;charset=GBK");
        PrintWriter out=  response.getWriter();
        String msg="";
        if(user!=null){
            Map<String, Object> result = new HashMap<String, Object>();
            if( user_Sign.getSize()!=0){
                //判断上传文件是否大于100K
                if(user_Sign.getSize()>102400){
                    msg="操作失败！上传图片不能大于100Kb!";
                }else {
                    Blob user_Signblob = BlobHelper.createBlob(user_Sign.getBytes());
                    user.setUserSign(user_Signblob);
                    userService.updateUserBlob(user, 1);
                    msg="操作成功！";
                }
            }else{
                msg="操作失败！没有上传的图片！";
            }
        }
        out.print("<script type='text/javascript'>alert('"+msg+"');</script>");
    }

    private void outputImg(Blob blob ,HttpServletResponse response) throws  Exception{
        InputStream insm =  blob.getBinaryStream();
        byte[] buf = new byte[1024];;
        OutputStream out = response.getOutputStream();
        int len = 0;
        while ((len=insm.read(buf))!=-1){
            out.write(buf, 0, len);
        }
        out.flush();
        out.close();
        insm.close();
    }

}