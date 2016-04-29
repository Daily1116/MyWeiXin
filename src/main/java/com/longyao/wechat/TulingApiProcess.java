/**
 * (C) Copyright 2016 Beijing CAISSA International Travel Service * Co., Ltd
 * All Rights Reserved.
 **/
package com.longyao.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 调用图灵机器人api接口，获取智能回复内容
 * @author dailongyao
 * @date 2016/4/28 13:21 
 **/
public class TulingApiProcess {
    public String getTulingResult(String content){
        String apiUrl = "http://www.tuling123.com/openapi/api?key=5607f0cbaaa6c8668a0f38e97f309ab6&info=";
        String param = "";
        try {
            param = apiUrl + URLEncoder.encode(content,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(param);

        String result = "";

        try {
            HttpResponse response = HttpClients.createDefault().execute(post);
            if(response.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** 请求失败处理 */
        if(null == result){
            return "对不起，你说的话真是太高深了……";
        }

        return result;
    }
}