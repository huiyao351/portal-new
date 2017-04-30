
package com.gtis.portal.web.config;

import com.alibaba.fastjson.JSON;
import com.gtis.common.util.UUIDGenerator;
import com.gtis.plat.service.SysOpinionService;
import com.gtis.portal.entity.PfRole;
import com.gtis.portal.entity.PfUser;
import com.gtis.portal.entity.PfUserRoleRel;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.model.ZtreeChanged;
import com.gtis.portal.service.PfDistrictService;
import com.gtis.portal.service.PfRoleService;
import com.gtis.portal.service.PfUserRoleService;
import com.gtis.portal.service.PfUserService;
import com.gtis.portal.util.RequestUtils;
import com.gtis.portal.web.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping("config/role")
public class RoleController extends BaseController {
    @Autowired
    private PfUserRoleService userRoleServer;

    @Autowired
    private PfUserService userService;

    @Autowired
    private PfRoleService roleService;
    @Autowired
    PfDistrictService districtService;

    @RequestMapping("select")
    public String select(Model model,@RequestParam(value = "foreignId", required = true)String foreignId,String paramString) {
        model.addAttribute("foreignId",foreignId);
        model.addAttribute("paramString",paramString);
        return "/config/role/role-select";
    }

    @RequestMapping("")
    public String manage(Model model) {
        return "/config/role/manager";
    }

    @RequestMapping("json")
    @ResponseBody
    public Object rolejson(Model model) {
        //组织菜单树，类似构建平台
//        List<Ztree> treeList = roleService.getTreeByXzqdm(null);
        Ztree tree = roleService.getRoleRegionTree(null);
        return tree;
    }

    @RequestMapping("userjson")
    @ResponseBody
    public Object userJson(Model model,@RequestParam(value = "roleId", required = true)String roleId) {
        //组织菜单树，类似构建平台
        List<Ztree> treeList = userService.getTreeByRoleId(roleId);
        return treeList;
    }

    /*
    查询角色详细信息，以及角色下的用户列表
     */
    @RequestMapping("info")
    @ResponseBody
    public Object getRole(@RequestParam(value = "roleId", required = false) String roleId) {
        PfRole role = roleService.findById(roleId);
        List<Ztree> userList= userService.getTreeByRoleId(roleId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("role", role);
        result.put("userList", userList);
        return result;
    }
    /*
    保存用户与角色的关系
     */
    @RequestMapping("saveUserRole")
    @ResponseBody
    public Object saveUserRole(HttpServletRequest req,@RequestParam(value = "roleId", required = true)String roleId,String paramString) {
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(roleId)){
            System.out.println(paramString);
            List<ZtreeChanged> changeList = JSON.parseArray(StringUtils.trim(paramString),ZtreeChanged.class);
            userRoleServer.addRoleUserRel(roleId, changeList);
        }
        return super.handlerSuccessJson();
    }
    /*
    添加或修改角色
     */
    @RequestMapping("save")
    @ResponseBody
    public Object save(HttpServletRequest req,@ModelAttribute("role") PfRole role, Model model){
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(role.getRoleId())){
           role.setRoleId(UUIDGenerator.generate18());
           List<PfRole> roletmp2 =  roleService.getRoleByName(role.getRoleName());
            if(roletmp2!=null&& roletmp2.size()>0){
                result.put("success", false);
                result.put("msg", "要添加的角色名称已经存在！");
                return result;
            }
        }

        PfRole roletmp = roleService.findById(role.getRoleId());
        if (roletmp != null){
            roleService.update(role);
        }else {
            roleService.insert(role);
        }

        result.put("success", true);
        result.put("msg", "操作成功！");
        result.put("role", role);
        return result;
    }
    /*
    删除角色及角色下的用户及用户关系
     */
    @RequestMapping("del")
    @ResponseBody
    public Object del(@ModelAttribute("role") PfRole role){
        if (RequestUtils.checkIsAdmin()){
            if (role!=null){
                roleService.deleteById(role.getRoleId());
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlererrorJson();
    }
    /*
    删除角色下的用户及用户关系
     */
    @RequestMapping("delUserRoleRel")
    @ResponseBody
    public Object delUserRoleRel(String roleId,String userId){
        if (RequestUtils.checkIsAdmin()){
            if (StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(userId)){
                userRoleServer.deleteUserRoleByUseridAndRoleId(roleId,userId);
                return handlerSuccessJson();
            }
        }else{
            return handlerErrorJson(exceptionService.getExceptionMsg("9001"));
        }
        return handlererrorJson();
    }

    private Object handlererrorJson(){
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", false);
        result.put("msg", "操作失败！");
        return result;
    }
    private Set datahandle(String data){
        Set s=new HashSet();
        String[] datas = data.split("@");
        if(datas!=null){
            for (int i=0 ;i<datas.length;i++){
                s.add(datas[i]);
            }
        }
        return s;
    }

    //通过useId查询该用户有哪些角色
    @RequestMapping("userRoleList")
    @ResponseBody
    public Object userRoleList(@RequestParam(value = "userId", required = false) String userId){
        List<Ztree>  treeList= userRoleServer.findRolebyUserId(userId);
        return treeList;
    }

    @RequestMapping("saveUserRoleRel")
    @ResponseBody
    public Object saveUserRoleRel(HttpServletRequest req,@RequestParam(value = "userId", required = true)String userId,String paramString){
        if (StringUtils.isNotBlank(paramString) && StringUtils.isNotBlank(userId)){
            System.out.println(paramString);
            List<ZtreeChanged> changeList = JSON.parseArray(paramString,ZtreeChanged.class);
            userRoleServer.addRoleRel(userId,changeList);
        }
        return handlerSuccessJson();
    }
}