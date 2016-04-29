/**
 * (C) Copyright 2016 Beijing CAISSA International Travel Service * Co., Ltd
 * All Rights Reserved.
 **/
package com.longyao.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * 封装最终的xml格式结果
 * @author dailongyao
 * @date 2016/4/28 13:22 
 **/
public class FormatXmlProcess {
    public String formatTextXmlAnswer(String to, String from, String content) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content></xml>");
        return sb.toString();
    }


    public String formatNewsXmlAnswer(String to, String from, JSONObject json) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();

        JSONArray jsonArray = json.getJSONArray("list");

        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount><![CDATA[");
        sb.append(jsonArray.size());
        sb.append("]]></ArticleCount><Articles>");
        for (Object o : jsonArray){
            JSONObject obj = null;
            if(o instanceof JSONObject){
                obj = (JSONObject) o;
            }
            sb.append("<item><Title><![CDATA[");
            assert obj != null;
            sb.append(obj.getString("article"));
            sb.append("]]></Title>");
            sb.append("<Description><![CDATA[");
            sb.append(obj.getString("source"));
            sb.append("]]></Description>");
            sb.append("<PicUrl><![CDATA[");
            sb.append(obj.getString("icon"));
            sb.append("]]></PicUrl>");
            sb.append("<Url><![CDATA[");
            sb.append(obj.getString("detailurl"));
            sb.append("]]></Url></item>");
        }
        sb.append("</Articles></xml>");
        return sb.toString();
    }

    public String formatCookBookXmlAnswer(String to, String from, JSONObject json) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();

        JSONArray jsonArray = json.getJSONArray("list");

        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount><![CDATA[");
        sb.append(jsonArray.size());
        sb.append("]]></ArticleCount><Articles>");
        for (Object o : jsonArray){
            JSONObject obj = null;
            if(o instanceof JSONObject){
                obj = (JSONObject) o;
            }
            sb.append("<item><Title><![CDATA[");
            assert obj != null;
            sb.append(obj.getString("name"));
            sb.append("]]></Title>");
            sb.append("<Description><![CDATA[");
            sb.append(obj.getString("info"));
            sb.append("]]></Description>");
            sb.append("<PicUrl><![CDATA[");
            sb.append(obj.getString("icon"));
            sb.append("]]></PicUrl>");
            sb.append("<Url><![CDATA[");
            sb.append(obj.getString("detailurl"));
            sb.append("]]></Url></item>");
        }
        sb.append("</Articles></xml>");
        return sb.toString();
    }
}