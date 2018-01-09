package com.ticket.util;

public final class Route {

    public static final  String HOST = "https://kyfw.12306.cn";

    public static final String LOGIN_CODE = HOST + "/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&0.7098462914784165";
    public static final String CAPTCHA_CHECK = HOST + "/passport/captcha/captcha-check";

}
