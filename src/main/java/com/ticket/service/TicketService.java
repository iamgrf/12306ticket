package com.ticket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ticket.ui.LoginCodeView;

public class TicketService extends TicketCore{

    public TicketService(String configPath) throws Exception {
        super(configPath);
    }

    public void run(){
        showLoginCode();
    }

    /**
     * 打开登录验证码
     */
    private void showLoginCode() {
        new LoginCodeView(this, "登录验证码", "/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&");
    }

    /**
     * 点击登录回调
     */
    @Override
    public void loginCall() {
        boolean b = captchaCheck();
        if (!b){
            showLoginCode();
            return;
        }
        b = login();
        if (!b){
            System.exit(0);
        }
        initMy12306();
        checkTicket();
    }

    /**
     * 检测有没有票，有票就下单，没有继续找
     */
    private void checkTicket() {
        while (true){
            result = queryTrain();

            JSONObject msgJson = JSON.parseObject(result);
            JSONArray resultTrain = msgJson.getJSONObject("data").getJSONArray("result");

            String[] fight_train_nums = ticketConfig.getFightTrainNum().split("\\|");
            for (int i = 0; i < fight_train_nums.length; i++) {
                for (int j = 0; j < resultTrain.size(); j++) {
                    String[] train = resultTrain.getString(j).split("\\|");
                    if (!fight_train_nums[i].equals(train[3])){
                        continue;
                    }
                    if ("Y".equals(train[11])){
                        ticketConfig.setSecretStr(train[0]);
                        ticketConfig.setTrainNo(train[2]);
                        ticketConfig.setStationTrainCode(train[3]);
                        ticketConfig.setLeftTicket(train[12]);
                        ticketConfig.setTrainLocation(train[15]);
                        submit();
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
