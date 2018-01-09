package com.ticket.service;

import com.ticket.ui.CodeWindow;
import com.ticket.util.Constant;
import com.ticket.util.Http;
import com.ticket.util.Print;
import com.ticket.util.Route;
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
            String result = Http.get("https://kyfw.12306.cn/otn/login/init");
            Document doc = Jsoup.parseBodyFragment(result);
            Elements xx = doc.select("[xml:space]");
            result = Http.get(Route.HOST + xx.get(0).attr("src"));
            result = Http.post("https://kyfw.12306.cn/passport/web/auth/uamtk?appid=otn", "");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*while (true){
            try {
                byte[] img = Http.getForFile(Route.LOGIN_CODE);
                CodeWindow codeWindow = new CodeWindow("登录验证码", img);
                Print.log("请输入验证码:");
                BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
                String[] code = buf.readLine().split(" ");
                codeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < code.length; i++) {
                    List<String> codes = Constant.codes.get(code[i]);
                    stringBuffer.append(codes.get(new Random().nextInt(codes.size()))).append(",");
                }
                String msg = Http.post(Route.CAPTCHA_CHECK,
                        "answer=" + stringBuffer.substring(0, stringBuffer.length() - 1).toString() + "&login_site=E&rand=sjrand",
                        Http.TEXT_PLAIN);
                System.out.println(msg);
            }catch (Exception e){}
        }*/
    }

}
