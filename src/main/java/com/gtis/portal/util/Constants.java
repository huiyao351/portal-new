package com.gtis.portal.util;

/**
 * Created with IntelliJ IDEA.
 * User: zx
 * Date: 15-3-18
 * Time: 下午7:06
 * Des:常量配置
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    public static final String WORK_FLOW_STUFF = "WORK_FLOW_STUFF";
    public static final String SPLIT_STR = "$";

    /**
     * 权限表的权限类型
     */
    public enum AuthorizeObjType{
        ZYFQ("角色控制的资源分区", 0),MENU("角色控制资源是否可见", 1), SUB("角色控制主题是否可见", 8);
        // 成员变量
        private String mc; //名称
        private Integer bm; //编码
        // 构造方法
        private AuthorizeObjType(String mc, Integer bm) {
            this.mc = mc;
            this.bm = bm;
        }
        //覆盖方法
        @Override
        public String toString() {
            return String.valueOf(this.bm);
        }
        public String getMc(){
            return this.mc;
        }

        public Integer getBm(){
            return this.bm;
        }
    }
}
