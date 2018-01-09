package com.ticket.util;

import java.util.List;
import java.util.Random;

public class Util {

    public static String fetchCode(String code){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < code.length(); i++) {
            List<String> codes = Constant.codes.get(String.valueOf(code.charAt(i)));
            stringBuffer.append(codes.get(new Random().nextInt(codes.size()))).append(",");
        }
        return stringBuffer.substring(0, stringBuffer.length() - 1).toString();
    }

}
