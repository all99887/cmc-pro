package com.higitech.cmcpro.admin.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by lyx on 2018/4/21.
 */
public final class PwdUtil {
    private PwdUtil() throws Exception {
        throw new Exception("这玩意不用实例化");
    }

    /**
     * 返回系统默认的密码
     * @return
     */
    public static String genDefaultPwd(){
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String digestHex = md5.digestHex("123456higitech");
        digestHex = pwdHash(digestHex);
        return digestHex;
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

    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("fd004166c950fa50715419cc6fb091abbcbe8067dbb4b8292a4151c8894e6f3d", BCrypt.gensalt(10)));
    }
}
