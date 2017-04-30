package com.gtis.portal.service.impl;

import com.gtis.portal.ex.ExceptionCode;
import com.gtis.portal.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2014/6/23
 */
@Service
public class ExceptionServiceImpl implements ExceptionService {
    @Autowired
    ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;

    @Override
    public String getExceptionMsg(String code) {
        try{
            return reloadableResourceBundleMessageSource.getMessage("exception."+code, null, Locale.getDefault());
        }catch (NoSuchMessageException e){
            return code;
        }

    }

    @Override
    public String getExceptionMsg(ExceptionCode exceptionCode) {
        try{
            return reloadableResourceBundleMessageSource.getMessage("exception."+exceptionCode.toString(), null, Locale.getDefault());
        }catch (NoSuchMessageException e){
            return exceptionCode.toString();
        }
    }
}
