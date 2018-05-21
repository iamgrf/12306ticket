package com.ticket.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static String fetchCode(java.util.List<Point> points){
        StringBuffer stringBuffer = new StringBuffer();
        for (Point point : points) {
            stringBuffer.append(point.getX()).append(",").append(point.getY()).append(",");
        }
        return stringBuffer.toString();
    }

    public static String execJs(String jscode){
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            return String.valueOf(engine.eval(jscode));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String gmtDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy 00:00:00 'GMT+0800 (中国标准时间)'",Locale.US);
        return sdf.format(new Date());
    }

    public static String[] regex(String str, String regex){
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(str);
        if (m.find()){
            int groupCount = m.groupCount();
            String[] v = new String[groupCount];
            for (int i = 0; i < v.length; i++) {
                v[i] = m.group((i + 1));
            }
            return v;
        }else{
            throw new RuntimeException("取码出错");
        }
    }


    public static void main(String[] args) {

        JSONObject c = JSON.parseObject("{\"validateMessagesShowId\":\"_validatorMessage\",\"status\":true,\"httpstatus\":200,\"data\":{\"queryOrderWaitTimeStatus\":true,\"count\":0,\"waitTime\":54,\"requestId\":6403061813795644845,\"waitCount\":1833,\"tourFlag\":\"dc\",\"orderId\":null},\"messages\":[],\"validateMessages\":{}}");
        System.out.println(c.getJSONObject("data").get("orderId") == null);
    }

}
