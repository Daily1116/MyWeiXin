/**
 * (C) Copyright 2016 Beijing CAISSA International Travel Service * Co., Ltd
 * All Rights Reserved.
 **/
package com.longyao.wechat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 微信服务端收发消息接口
 *
 * @author dailongyao
 * @date 2016/4/28 11:20
 **/
public class WechatServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        /** 读取接收到的xml消息 */
        StringBuilder sb = new StringBuilder();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while((s = br.readLine()) != null){
            sb.append(s);
        }

        String xml = sb.toString();//接收到微信端发送过来的xml数据
//        System.out.println(xml);
        String result = "";
        /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
        String echostr = request.getParameter("echostr");
        if(echostr != null && echostr.length() > 1){
            result = echostr;
        }else {
            //正常的微信处理流程
            result = new WeChatProcess().processWechatMsg(xml);
        }
//        System.out.println(result);
        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}