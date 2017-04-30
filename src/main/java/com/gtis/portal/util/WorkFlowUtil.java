package com.gtis.portal.util;

public class WorkFlowUtil {
	private static final String TEMP_TASK_LAST = "TEMP";

	public static String buildTEMPActivityId(String activityId){
		StringBuffer result=new StringBuffer("");
		if (activityId.length()>TEMP_TASK_LAST.length()){
			result.append(activityId.substring(0, activityId.length()-TEMP_TASK_LAST.length()));
		}
			result.append(TEMP_TASK_LAST);
		return result.toString();
	}
	
}
