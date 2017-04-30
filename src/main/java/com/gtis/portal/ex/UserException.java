
package com.gtis.portal.ex;

/**
 * .
 * <p/>
 *  用户异常类
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2014/6/23
 */
public class UserException extends RuntimeException{

    private String msg;

    public UserException(ExceptionCode exceptionCode){
        this.msg = exceptionCode.toString();
    }

    @Override
    public String getMessage(){
        return this.msg;
    }

}
