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
            String result = Http.get(Route.HOST + "/otn/login/init");
            Document doc = Jsoup.parseBodyFragment(result);
            Elements xx = doc.select("[xml:space]");
            Http.get(Route.HOST + xx.get(0).attr("src"));
            Http.post(Route.HOST + "/passport/web/auth/uamtk", "appid=otn");
            while (true){
                byte[] img = Http.getForFile(Route.HOST + "/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&" + new Random().nextDouble());
                CodeWindow codeWindow = new CodeWindow("登录验证码", img);
                Print.log("请输入验证码:");
                BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
                String code = buf.readLine();
                codeWindow.dispose();
                String login_code = Util.fetchCode(code);
                System.out.println(login_code);
                config.put("login_code", login_code);

                result = Http.post(Route.HOST + "/passport/captcha/captcha-check",
                        "answer=" + login_code + "&login_site=E&rand=sjrand");
                JSONObject msgJson = JSON.parseObject(result);
                Print.log(msgJson.getString("result_message"));
                if (4 == msgJson.getInteger("result_code")){
                    break;
                }
            }

            result = Http.post(Route.HOST + "/passport/web/login", String.format("username=%s&password=%s&appid=otn", config.get("username"), config.get("password")));
            System.out.println(result);
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
        System.out.println(result);
    }
}
