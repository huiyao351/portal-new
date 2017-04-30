package com.gtis.portal.util;

import com.gtis.plat.service.SysWorkFlowDefineService;
import com.gtis.plat.service.SysWorkFlowInstanceService;
import com.gtis.plat.vo.PfWorkFlowDefineVo;
import com.gtis.plat.vo.PfWorkFlowInstanceVo;
import com.gtis.portal.model.Menu;
import com.gtis.portal.service.PfResourceService;
import com.gtis.spring.Container;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class WorkFlowXmlUtil {
//	static int MAPMAXSIZE = 100;
//	static HashMap<String, WorkFlowXml> workFlowDefineMap = new HashMap<String, WorkFlowXml>();
//	static LinkedHashMap<String, WorkFlowXml> workFlowInstanceMap = new LinkedHashMap<String, WorkFlowXml>(
//			100);
    @Autowired
	SysWorkFlowInstanceService workFlowIntanceService;
    @Autowired
	SysWorkFlowDefineService workFlowDefineService;

    public SysWorkFlowInstanceService getWorkFlowIntanceService() {
        return workFlowIntanceService;
    }

    public void setWorkFlowIntanceService(SysWorkFlowInstanceService workFlowIntanceService) {
        this.workFlowIntanceService = workFlowIntanceService;
    }

    public SysWorkFlowDefineService getWorkFlowDefineService() {
		return workFlowDefineService;
	}

	public void setWorkFlowDefineService(
			SysWorkFlowDefineService workFlowDefineService) {
		this.workFlowDefineService = workFlowDefineService;
	}

	public WorkFlowXml getWorkFlowDefineModel(PfWorkFlowDefineVo defineVo) {
//		WorkFlowXml modelXml = workFlowDefineMap.get(defineVo
//				.getWorkflowDefinitionId());
//		if (modelXml != null) {
//
//			if (modelXml.getModifyDate() != null
//					&& !modelXml.getModifyDate().equals(
//							defineVo.getModifyDate()))
//				modelXml = null;
//		}
//		if (modelXml == null) {
			String xml = workFlowDefineService.getWorkFlowDefineXml(defineVo);
            WorkFlowXml modelXml = new WorkFlowXml(xml);
			modelXml.setModifyDate(defineVo.getModifyDate());
//			workFlowDefineMap.put(defineVo.getWorkflowDefinitionId(), modelXml);
//		}
//		return modelXml;
        return modelXml;
	}

	public WorkFlowXml getWorkFlowInstanceModel(PfWorkFlowInstanceVo instanceVo) {
//		WorkFlowXml modelXml = workFlowInstanceMap.get(instanceVo
//				.getWorkflowIntanceId());
//		if (modelXml != null) {
//			if (modelXml.getModifyDate() != null
//					&& !modelXml.getModifyDate().equals(
//							instanceVo.getModifyDate()))
//				modelXml = null;
//		}
//		if (modelXml == null) {
//			if (workFlowInstanceMap.size() > MAPMAXSIZE) {
//				synchronized (workFlowInstanceMap) {
//					workFlowInstanceMap.remove(workFlowInstanceMap.keySet()
//							.iterator().next().toString());
//				}
//			}
			String xml = workFlowIntanceService
					.getWorkflowInstanceXml(instanceVo);
            WorkFlowXml modelXml = new WorkFlowXml(xml);
			modelXml.setModifyDate(instanceVo.getModifyDate());
//			workFlowInstanceMap
//					.put(instanceVo.getWorkflowIntanceId(), modelXml);
//		}
		return modelXml;
	}
	
	public void updateGobalValByProId(String proId, String valName,
			String val) {
		PfWorkFlowInstanceVo vo=workFlowIntanceService.getWorkflowInstanceByProId(proId);
		updateGobalVal(vo.getWorkflowIntanceId(),valName,val);
	}

	public HashMap<String, Object> getGobalValByProId(String proId) {
		PfWorkFlowInstanceVo vo=workFlowIntanceService.getWorkflowInstanceByProId(proId);
        if (vo!=null)
		    return getGobalVal(vo.getWorkflowIntanceId());
        else
            return new HashMap<String, Object>();
	}
	
	public void updateGobalVal(String workflowIntanceId, String valName,
			String val) {
        workFlowIntanceService.updateGobalVal(workflowIntanceId, valName, val);
	}

	public HashMap<String, Object> getGobalVal(String workflowIntanceId) {
		return workFlowIntanceService.getGobalVal(workflowIntanceId);
	}

	public static WorkFlowXml getDefineModel(PfWorkFlowDefineVo defineVo) {
		WorkFlowXmlUtil factory = (WorkFlowXmlUtil) Container
				.getBean("WorkFlowXmlUtil");
		return factory.getWorkFlowDefineModel(defineVo);
	}

	public static WorkFlowXml getInstanceModel(PfWorkFlowInstanceVo instanceVo) {
		WorkFlowXmlUtil factory = (WorkFlowXmlUtil) Container
				.getBean("WorkFlowXmlUtil");
		return factory.getWorkFlowInstanceModel(instanceVo);
	}
	
	
	
	public static void updateGobalVals(String workflowIntanceId, String valName,
			String val) {
		WorkFlowXmlUtil factory = (WorkFlowXmlUtil) Container
		.getBean("WorkFlowXmlUtil");
		factory.updateGobalVal(workflowIntanceId,valName,val);
	}
	
	public static HashMap<String, Object> getGobalVals(String workflowIntanceId) {
		WorkFlowXmlUtil factory = (WorkFlowXmlUtil) Container.getBean("WorkFlowXmlUtil");
		return factory.getGobalVal(workflowIntanceId);
	}
	
	public static void updateGobalValsByProId(String proId, String valName,
			String val) {
		WorkFlowXmlUtil factory = (WorkFlowXmlUtil) Container
		.getBean("WorkFlowXmlUtil");
		factory.updateGobalValByProId(proId,valName,val);
	}
	
	public static HashMap<String, Object> getGobalValsByProId(String proId) {
		WorkFlowXmlUtil factory = (WorkFlowXmlUtil) Container
		.getBean("WorkFlowXmlUtil");
		return factory.getGobalValByProId(proId);
	}

	public static List<Menu> getWorkFlowHandleMenu(String menuXml,String taskid,PfWorkFlowInstanceVo workFlowInstanceVo,PfResourceService resourceService) throws Exception{
		List<Menu> menuList=new ArrayList<Menu>();
		//取办理菜单
		if(StringUtils.isNotBlank(menuXml)){
			menuXml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+menuXml;
			Document document = DocumentHelper.parseText(menuXml);
			if(document!=null){
				//一级菜单
				List resourceNodeList=document.selectNodes("//Resources/Resource");
				if(resourceNodeList!=null){
					//资源map
					List<String> resourceIdList = new ArrayList<String>();
					for(int i=0;i<resourceNodeList.size();i++){
						Element resourceEl=(Element)resourceNodeList.get(i);
						resourceIdList.add(resourceEl.attributeValue("Id"));

						//二级菜单
						List childrenNodeList= resourceEl.selectNodes("//Resources/Resource[@Id='"+resourceEl.attributeValue("Id")+"']/Resource");
						if(childrenNodeList!=null && childrenNodeList.size()>0){
							for(int j=0;j<childrenNodeList.size();j++){
								Element childEl=(Element)childrenNodeList.get(j);
								resourceIdList.add(childEl.attributeValue("Id"));

								//三级菜单
								List childChildNodeList= resourceEl.selectNodes("//Resources/Resource[@Id='"+resourceEl.attributeValue("Id")+"']/Resource[@Id='"+childEl.attributeValue("Id")+"']/Resource");
								if(childChildNodeList!=null && childChildNodeList.size()>0){
									for(int k=0;k<childChildNodeList.size();k++){
										Element childChildEl=(Element)childChildNodeList.get(k);
										resourceIdList.add(childChildEl.attributeValue("Id"));
									}
								}
							}
						}
					}
					//根据资源id集合查找资源集合
					HashMap<String,String> urlParamMap = resourceService.toUrlParamMapByResIdList(resourceIdList);
					if (urlParamMap == null){
						urlParamMap = new HashMap<String, String>();
					}

					for(int i=0;i<resourceNodeList.size();i++){
						Menu menu=new Menu();
						Element resourceEl=(Element)resourceNodeList.get(i);
						menu.setId(resourceEl.attributeValue("Id"));
						menu.setText(resourceEl.attributeValue("Name"));
						String firstLink="/SysResource.action?from=task&taskid="+taskid+"&proid="+workFlowInstanceVo.getProId()+"&wiid="+workFlowInstanceVo.getWorkflowIntanceId()+"&rid="+resourceEl.attributeValue("Id");
						List childrenNodeList1= document.getRootElement().selectNodes("//Resources/Resource[@Id='"+resourceEl.attributeValue("Id")+"']/Resource");
						if(childrenNodeList1!=null && childrenNodeList1.size()>0){
							Element childEl1=(Element)childrenNodeList1.get(0);
							firstLink="/SysResource.action?from=task&taskid="+taskid+"&proid="+workFlowInstanceVo.getProId()+"&wiid="+workFlowInstanceVo.getWorkflowIntanceId()+"&rid="+childEl1.attributeValue("Id");
						}
						firstLink += "&EXTEND_PARAM&" + urlParamMap.get(resourceEl.attributeValue("Id"));
						menu.setLink(firstLink);
						List childrenNodeList= resourceEl.selectNodes("//Resources/Resource[@Id='"+resourceEl.attributeValue("Id")+"']/Resource");
						if(childrenNodeList!=null && childrenNodeList.size()>0){
							List<Menu> childMenuList=new ArrayList<Menu>();
							for(int j=0;j<childrenNodeList.size();j++){
								Menu childMenu=new Menu();
								Element childEl=(Element)childrenNodeList.get(j);
								childMenu.setId(childEl.attributeValue("Id"));
								childMenu.setText(childEl.attributeValue("Name"));
								String firstChildLink="/SysResource.action?from=task&taskid="+taskid+"&proid="+workFlowInstanceVo.getProId()+"&wiid="+workFlowInstanceVo.getWorkflowIntanceId()+"&rid="+childEl.attributeValue("Id");
								List childChildNodeList= resourceEl.selectNodes("//Resources/Resource[@Id='"+resourceEl.attributeValue("Id")+"']/Resource[@Id='"+childEl.attributeValue("Id")+"']/Resource");
								if(childChildNodeList!=null && childChildNodeList.size()>0){
									Element childEl1=(Element)childChildNodeList.get(0);
									firstChildLink="/SysResource.action?from=task&taskid="+taskid+"&proid="+workFlowInstanceVo.getProId()+"&wiid="+workFlowInstanceVo.getWorkflowIntanceId()+"&rid="+childEl1.attributeValue("Id");
								}
								firstChildLink += "&EXTEND_PARAM&" + urlParamMap.get(childEl.attributeValue("Id"));
								childMenu.setLink(firstChildLink);


								if(childChildNodeList!=null && childChildNodeList.size()>0){
									List<Menu> childChildMenuList=new ArrayList<Menu>();
									for(int k=0;k<childChildNodeList.size();k++){
										Menu childChildMenu=new Menu();
										Element childChildEl=(Element)childChildNodeList.get(k);
										childChildMenu.setId(childChildEl.attributeValue("Id"));
										childChildMenu.setText(childChildEl.attributeValue("Name"));
										String childChildLink = "/SysResource.action?from=task&taskid="+taskid+"&proid="+workFlowInstanceVo.getProId()+"&wiid="+workFlowInstanceVo.getWorkflowIntanceId()+"&rid="+childChildEl.attributeValue("Id");
										childChildLink += "&EXTEND_PARAM&" + urlParamMap.get(childChildEl.attributeValue("Id"));
										childChildMenu.setLink(childChildLink);
										childChildMenuList.add(childChildMenu);
									}
									childMenu.setChildren(childChildMenuList);
								}

								childMenuList.add(childMenu);
							}
							menu.setChildren(childMenuList);
							menu.setExpanded(true);
						}
//                             menu.setChildren();
						menuList.add(menu);
					}
				}
			}
		}
		return menuList;
	}
}
