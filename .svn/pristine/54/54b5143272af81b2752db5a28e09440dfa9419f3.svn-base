package com.gtis.portal.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.gtis.config.AppConfig;
import com.gtis.plat.service.SysDistrictService;
import com.gtis.plat.service.SysUserService;
import com.gtis.plat.vo.PfOrganVo;
import com.gtis.plat.vo.PfUserVo;
import com.gtis.plat.vo.UserInfo;
import com.gtis.portal.entity.PfMenu;
import com.gtis.portal.entity.PfOrgan;
import com.gtis.portal.entity.PfSubsystem;
import com.gtis.portal.entity.PfUser;
import com.gtis.portal.ex.ExceptionCode;
import com.gtis.portal.ex.UserException;
import com.gtis.portal.service.IndexService;
import com.gtis.portal.service.PfSubsystemService;
import com.gtis.portal.service.PfUserService;
import com.gtis.portal.util.RequestUtils;
import com.gtis.util.Md5Util;
import com.gtis.web.SessionUtil;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;


@Controller
public class IndexController extends BaseController{
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysDistrictService sysDistrictService;
    @Autowired
    IndexService indexService;
    @Autowired
    PfSubsystemService subsystemService;
    @Autowired
    PfUserService pfUserService;

    /**
     * 首页访问分为三种情况：
     * 1、如果系统初次部署，并未对主题进行配置，则需要打开主题配置界面（针对管理员）
     * 2、如果系统已经有了主题记录，但是没有对主题进行权限控制,则打开主题权限配置界面（针对管理员）
     * 3、满足以上要求，就可以直接打开首页，如果当前人员不是管理员，也没有配置权限，则要求对用户进行主题授权才行，否则提示无权限访问
     * @param model
     * @param systemId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("index")
    public String index(Model model,String systemId,HttpServletResponse response) throws Exception{
        initIndex(model);
        //获取有权限的主题
        List<PfSubsystem> subList = subsystemService.getSubsystemAuthorList(SessionUtil.getCurrentUserId());
        if (subList == null || subList.size() < 1){
            if (SessionUtil.getCurrentUser().isAdmin()){
                response.sendRedirect("config/sub");
                return null;
            }else {
                return "common/no-author";
            }
        }

        //根据前台传递的主题id，查询是否存在对应的主题
        PfSubsystem sub = subsystemService.findInitUrlById(systemId);
        if (sub == null){
            sub = subsystemService.getSubsystemByName(systemId);
        }
        //如果不存在，获取有权限的主题的第一个主题
        if (sub == null || StringUtils.isBlank(systemId)){
            if (subList != null && subList.size() > 0){
                sub = subList.get(0);
                systemId = sub.getSubsystemId();
            }
        }else {
            //如果存在，则判断该主题是否符合权限
            boolean hasAuth = false;
            for (PfSubsystem tmp : subList) {
                if (StringUtils.equals(tmp.getSubsystemId(),sub.getSubsystemId())){
                    hasAuth = true;
                    break;
                }
            }
            //如果没有权限，则主题改为null
            if (!hasAuth){
                sub = null;
            }
        }

        if (sub == null){
            sub = new PfSubsystem();
        }
        if(sub != null && StringUtils.isNotBlank(sub.getSubUrl())){
            sub.setSubUrl(RequestUtils.initOptProperties(sub.getSubUrl()));
        }

        model.addAttribute("sub",sub);

        //如果主题菜单没有配置类型，说明是老版本的升级，需要自动跳转到主题菜单配置界面
        if (sub.getSubType() != null){
            String returnPage = sub.getSubsystemName();
            switch (sub.getSubType()){
                case 1:
                    if (sub.getSubMenuType() != null){
                        //左两级菜单样式//左三级菜单样式
                        returnPage = sub.getSubMenuType();
                    }
                    break;
                case 2:
                    Map urlMap = indexService.getConfigIndexZhswUrl();
                    model.addAttribute("urlMap",urlMap);
                    model.addAttribute("urlMapJson",JSON.toJSONString(urlMap));
                    break;
                case 3:
                    returnPage = "iframe";
                    break;
            }
            return "index/"+returnPage+"/index";
        }else {
            return "common/no-author";
        }
    }

    public void initIndex(Model model) {
        UserInfo userInfo = SessionUtil.getCurrentUser();
        PfUser pfUser = pfUserService.findById(userInfo.getId());
        model.addAttribute("userName",SessionUtil.getCurrentUser().getUsername());
        model.addAttribute("userId",pfUser.getUserId());
        model.addAttribute("loginName",pfUser.getLoginName());
        List<PfOrganVo> organList = userInfo.getLstOragn();
        PfOrganVo organ = new PfOrganVo();
        if(organList!=null&&organList.size()>0)
            organ = organList.get(0);

        model.addAttribute("organ",organ);
        model.addAttribute("organName",organ.getOrganName());
        model.addAttribute("regionCode",organ.getRegionCode());
        model.addAttribute("organList",organList);

        String defaultDistrictCode = AppConfig.getProperty("district.code");
        List districtList = Lists.newArrayList();
        if(StringUtils.isNotBlank(defaultDistrictCode))
            districtList = sysDistrictService.querySubDistrictByCode(defaultDistrictCode);
        else
            districtList = sysDistrictService.queryAllDistrict();
        model.addAttribute("districtList",districtList);
    }
    @RequestMapping("password")
    @ResponseBody
    public Object password(Model model,String oldPassword,String newPassword,String confirmNewPassword) throws Exception{
        if(!newPassword.equals(confirmNewPassword))
            throw new UserException(ExceptionCode.NEW_PASSWORD_CONFIRM_PASSWORD_DIFF);
        else{
            String userId = SessionUtil.getCurrentUserId();
            PfUserVo userObj=sysUserService.getUserVo(userId);
            if (userObj==null){
                throw new UserException(ExceptionCode.USER_NOT_FOUND);
            }else{
                String checkpass=oldPassword;
                String md5= Md5Util.Build(checkpass);
                if (md5.equals(userObj.getLoginPassWord())){
                    if (sysUserService.savePassWord(userId, newPassword)){
                       return handlerSuccessJson();
                    }
                    else
                       throw new UserException(ExceptionCode.USER_MODIFY_ERROR);
                }else
                    throw new UserException(ExceptionCode.USER_OLD_PASSWORD_ERROR);
            }
        }
    }

    @RequestMapping("signPassword")
    @ResponseBody
    public Object signPassword(Model model,String oldSignPassword,String newSignPassword,String confirmNewSignPassword) throws Exception{
        if(!newSignPassword.equals(confirmNewSignPassword))
            throw new UserException(ExceptionCode.NEW_PASSWORD_CONFIRM_PASSWORD_DIFF);
        else{
            String userId = SessionUtil.getCurrentUserId();
            PfUserVo userObj=sysUserService.getUserVo(userId);
            if (userObj==null){
                throw new UserException(ExceptionCode.USER_NOT_FOUND);
            }else{
                String checkpass=oldSignPassword;
                String md5= Md5Util.Build(checkpass);
                if (md5.equals(userObj.getSignPassword())){
                    if (sysUserService.saveSignPassword(userId, newSignPassword)){
                        return handlerSuccessJson();
                    }
                    else
                        throw new UserException(ExceptionCode.USER_MODIFY_ERROR);
                }else
                    throw new UserException(ExceptionCode.USER_OLD_PASSWORD_ERROR);
            }
        }
    }
}