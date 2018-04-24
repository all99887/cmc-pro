package com.higitech.cmcpro.admin.consts;

/**
 * Created by lyx on 2018/4/21.
 */
public final class NameConsts {

    private NameConsts() throws Exception {
        throw new Exception("这玩意不用实例化");
    }

    public final class SessionKeys{
        public static final String USER = "loginUser";

        public static final String USER_PERMISSION = "userPermission";

        public static final String PIC_CAPTCHA_KEY = "picCaptchaKey";
    }



}
