package com.higitech.cmcpro.admin.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by lyx on 2018/4/21.
 */
public final class PwdUtil {
    private PwdUtil() throws Exception {
        throw new Exception("这玩意不用实例化");
    }

    /**
     * 生成存入数据库的hash后的密码
     * @param pwd
     * @return
     */
    public static String pwdHash(String pwd){
        return BCrypt.hashpw(pwd, BCrypt.gensalt(10));
    }

    /**
     * 对比密码是否正确
     * @param pwd
     * @param pwdDb
     * @return
     */
    public static boolean comparePwd(String pwd, String pwdDb){
        return BCrypt.checkpw(pwd, pwdDb);
    }
}