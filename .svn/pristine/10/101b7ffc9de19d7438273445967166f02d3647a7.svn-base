
package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.Md5Util;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.*;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.*;


@Controller
@RequestMapping("config/organ")
public class OrganController extends BaseController {
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

    @RequestMapping("")
    public String manage(Model model) {
        model.addAttribute("degreeList", degreeList);
        model.addAttribute("sexList", sexList);
        return "/config/organ/manager";
    }

    @RequestMapping("json")
    @ResponseBody
    public Object organjson(Model model) {
        //组织菜单树，类似构建平台
        UserInfo userInfo = SessionUtil.getCurrentUser();
        Ztree ztree = null;
        if (userInfo.isAdmin()){
            ztree = organService.getAllOrganTree(null);
        }else {
            ztree = organService.getOrganTreeByUserId(userInfo.getId());
        }

        List<Ztree> treeList=new ArrayList<Ztree>();
        treeList.add(ztree);
        if (userInfo.isAdmin()){
            //其他部门列表标志位：1表示不往其他部门树里添加子节点
            //2表示往其他部门树里添加子节点
            Ztree othertree =organService.getOtherTree(1);
            othertree.setNoR(true);
            treeList.add(othertree);
        }
        return treeList;
    }

    /**
     * 部门的详细信息
     */
    @RequestMapping("info")
    @ResponseBody
    public Object getroganList(@RequestParam(value = "organ_id", required = false) String organ_id) {
        PfOrgan organ = organService.findById(organ_id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("organ", organ);
        return result;
    }

    /*
    添加或者保存部门
     */
    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("organ") PfOrgan organ, Model model){
        if (StringUtils.isBlank(organ.getOrganId())){
            organ.setOrganId(UUIDGenerator.generate18());
        }
        boolean isok = organService.checkValidRegionCode(organ.getRegionCode(), organ.getSuperOrganId());
        if (!isok){
            return handlerErrorJson("当前部门行政区代码和上级部门行政区代码不匹配，请重新选择！");
        }
        PfOrgan organtmp = organService.findById(organ.getOrganId());
        if (organtmp != null){
            organService.update(organ);
        }else {
            organService.insert(organ);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("organ", organ);
        return result;
    }

    @RequestMapping("delOrgan")
    @ResponseBody
    public Object del(String organ_id,String del_mark,String user_id){
        //根据删除标志位来判断如何删除
        /*	1仅删除部门，保留所有包含的用户");
	        2删除部门，并且删除所有包含的用户所有角色定义中该部门所包含的用户也将一并删除");
	    ;*/
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(organ_id) && StringUtils.isNotBlank(del_mark)){
                List<String> organIdList=organService.findAllOrgan(organ_id);
                if("1".equals(del_mark)){
                    for (String  tmpId:organIdList) {
                        //删除部门
                        organService.deleteById(tmpId);
                    }
                    return handlerSuccessJson();
                }else if("2".equals(del_mark)){
                    organService.deleteOrganAndUserById(organ_id);
                    return  handlerSuccessJson();
                }
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlerErrorJson();
    }

    @RequestMapping("select")
    public String select(Model model,@RequestParam(value = "foreignId", required = true)String foreignId,String paramString) {
        model.addAttribute("foreignId",foreignId);
        model.addAttribute("paramString",paramString);
        return "/config/organ/organ-select";
    }

    //查询部门下有哪些人员
    @RequestMapping("userjson")
    @ResponseBody
    public Object userJson(Model model,@RequestParam(value = "organId", required = true)String organId) {
        //组织菜单树，类似构建平台
        List<Ztree> treeList = userService.getTreeByOrganId(organId);
        return treeList;
    }

    //通过useId查询该用户有哪些部门
    @RequestMapping("userOrganList")
    @ResponseBody
    public Object userOrganList(@RequestParam(value = "userId", required = false) String userId){
        List<Ztree>  treeList= userOrganService.findOrganbyUserId(userId);
        return treeList;
    }

    /*
    不属于任何部门的用户
     */
    @RequestMapping("findOtherUser")
    @ResponseBody
    public Object findOtherUser() {
        List<PfUser>  userList= organService.findOtherUser();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("userList", userList);
        return result;
    }

    /**
     * 根据用户id，保存所选择的部门列表信息
     * @param req
     * @param userId
     * @param paramString
     * @return
     */
    @RequestMapping("saveUserOrganRel")
    @ResponseBody
    public Object saveUserOrganRel(HttpServletRequest req,@RequestParam(value = "userId", required = true)String userId,String paramString){
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(userId)){
            System.out.println(paramString);
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            userOrganService.addOrganRelByUserId(userId, changeList);
        }
        return handlerSuccessJson();
    }

    /**
     * 根据部门id，保存所选择的用户列表信息
     * @param req
     * @param organId
     * @param paramString
     * @return
     */
    @RequestMapping("saveOrganUserRel")
    @ResponseBody
    public Object saveOrganUserRel(HttpServletRequest req,@RequestParam(value = "organId", required = true)String organId,String paramString){
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(organId)){
            System.out.println(paramString);
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            userOrganService.addUserRelByOrganId(organId, changeList);
        }
        return handlerSuccessJson();
    }

    //删除用户与部门关系
    @RequestMapping("delUserOrganRel")
    @ResponseBody
    public Object delUserOrganRel(String userId,String organId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(organId)){
                userOrganService.deleteUserOrganRelByUserIdAndOrganId(userId, organId);
                return handlerSuccessJson();
            }
            return handlerErrorJson();
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
    }
}