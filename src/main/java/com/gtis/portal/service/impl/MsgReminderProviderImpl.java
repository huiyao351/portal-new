package com.gtis.portal.service.impl;

import com.gtis.portal.service.MsgReminderProvider;
import com.gtis.web.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 基于http rest 服务的信息提醒提供者
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/4/13
 */
public class MsgReminderProviderImpl implements MsgReminderProvider {
    private String name;
    private String moreUrl;
    private String url;
    private HttpClient httpClient;
    private Boolean enabled;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getCount() throws Exception{
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse response = null;
        try{
            closeableHttpClient = (CloseableHttpClient)httpClient;
            HttpGet httpGet = new HttpGet(prehandleParam());
            response = closeableHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                String result = EntityUtils.toString(entity,"UTF-8");
                if(StringUtils.isNotBlank(result))
                    return Integer.valueOf(result);
            }
        }finally {
            if(response!=null)
                response.close();
        }
        return 0;
    }

    @Override
    public String getMoreUrl() {
        return moreUrl;
    }

    @Override
    public Boolean isEnabled() {
        return enabled;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    private String prehandleParam(){
        String result = url.toString();
        if(StringUtils.isNotBlank(url)){
            if(url.indexOf("?")>-1)
                result+="&";
            else
                result+="?";
            return  result+"userId="+ SessionUtil.getCurrentUserId();
        }
        return null;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
