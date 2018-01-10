package com.ticket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ticket.ui.CodeWindow;
import com.ticket.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TicketCore {

    private Map<String, String> config;

    public TicketCore(Map<String, String> config){
        this.config = config;
    }

    public void login() {
        try {
            String result = null;
            while (true){
                byte[] img = Http.getForFile(Route.HOST + "/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&" + new Random().nextDouble());
                CodeWindow codeWindow = new CodeWindow("登录验证码", img);
                Print.log("请输入验证码:");
                BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
                String code = buf.readLine();
                codeWindow.dispose();
                String login_code = Util.fetchCode(code);
                config.put("login_code", login_code);

                result = Http.post(Route.HOST + "/passport/captcha/captcha-check",
                        "answer=" + login_code + "&login_site=E&rand=sjrand");
                JSONObject msgJson = JSON.parseObject(result);
                Print.log(msgJson.getString("result_message"));
                if (4 == msgJson.getInteger("result_code")){
                    break;
                }
            }
            int index = 30;
            while (index > 0){
                result = Http.post(Route.HOST + "/passport/web/login", String.format("username=%s&password=%s&appid=otn", config.get("username"), config.get("password")));
                System.out.println("login="+result);
                if (result != null && result.length() > 0){
                    break;
                }
                index--;
            }
            if (result == null || result.length() == 0){
                Print.log("登录失败,退出程序.");
                System.exit(0);
            }

            JSONObject msgJson = JSON.parseObject(result);
            if (0 != msgJson.getInteger("result_code")){
                Print.log(msgJson.getString("result_message"));
                Print.log("退出程序.");
                System.exit(0);
            }
            Print.log(msgJson.getString("result_message"));
            config.put("uamtk", msgJson.getString("uamtk"));

            Http.post(Route.HOST + "/otn/login/userLogin", "_json_att=");
            Http.get(Route.HOST + "/otn/passport?redirect=/otn/login/userLogin");
            result = Http.post(Route.HOST + "/passport/web/auth/uamtk", "appid=otn");
            msgJson = JSON.parseObject(result);
            config.put("newapptk", msgJson.getString("newapptk"));

            Http.post(Route.HOST + "/otn/uamauthclient", "tk="+config.get("newapptk"));

            Http.get(Route.HOST + "/otn/index/initMy12306");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void queryTrain(){
        String result = Http.get(Route.HOST + String.format("/otn/leftTicket/log?leftTicketDTO.train_date=%s&" +
                        "leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&" +
                        "purpose_codes=ADULT", config.get("train_date"),
                config.get("from_station"), config.get("to_station")));
        JSONObject msgJson = JSON.parseObject(result);
        if (!msgJson.getBoolean("status")){
            Print.log("出行信息有误.");
            System.exit(0);
        }
        result = Http.get(Route.HOST + String.format("/otn/leftTicket/queryZ?leftTicketDTO.train_date=%s&" +
                        "leftTicketDTO.from_station=%s&leftTicketDTO.to_station=%s&purpose_codes=ADULT", config.get("train_date"),
                config.get("from_station"), config.get("to_station")));
        msgJson = JSON.parseObject(result);
    }

    public void submit() {
        String result = Http.post(Route.HOST + "/otn/login/checkUser", "_json_att=");
        String parameter = "secretStr=%s&train_date=%s&back_train_date=%s&tour_flag=dc&purpose_codes=ADULT&query_from_station_name=%s&query_to_station_name=%s&undefined=";
        //{"validateMessagesShowId":"_validatorMessage","status":true,"httpstatus":200,"data":"N","messages":[],"validateMessages":{}}
        result = Http.post(Route.HOST + "/otn/leftTicket/submitOrderRequest", String.format(parameter, config.get("secretStr"),
                config.get("train_date"), "2018-01-10", config.get("SNH"), config.get("JIQ")));
        Print.log(result);
        result = Http.post(Route.HOST + "/otn/confirmPassenger/initDc", "_json_att=");
    }
}
