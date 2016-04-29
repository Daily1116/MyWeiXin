/**
 * (C) Copyright 2016 Beijing CAISSA International Travel Service * Co., Ltd
 * All Rights Reserved.
 **/
package com.longyao.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信xml消息处理流程逻辑类
 * @author dailongyao
 * @date 2016/4/28 11:31 
 **/
public class WeChatProcess {
    /**
     * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
     * @param xml 接收到的微信数据
     * @return  最终的解析结果（xml格式数据）
     */
    public String processWechatMsg(String xml){
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
        String result = "";
//        System.out.println("User's question is: "+xmlEntity.getContent());
        if("text".endsWith(xmlEntity.getMsgType())){
            result = new TulingApiProcess().getTulingResult(xmlEntity.getContent());
        }


        /** 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容
         *  因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
         * */

        try {
            JSONObject json = JSON.parseObject(result);

            if(100000 == json.getIntValue("code")){ //文字类
                result = json.getString("text");
                result = new FormatXmlProcess().formatTextXmlAnswer(xmlEntity.getFromUserName(),xmlEntity.getToUserName(),result);
            }else if(200000 == json.getIntValue("code")){ //链接类
                result = json.getString("text")+json.getString("url");
                result = new FormatXmlProcess().formatTextXmlAnswer(xmlEntity.getFromUserName(),xmlEntity.getToUserName(),result);
            }else if(302000 == json.getIntValue("code")){ //新闻类
                result = new FormatXmlProcess().formatNewsXmlAnswer(xmlEntity.getFromUserName(),xmlEntity.getToUserName(),json);
            }else if(308000 == json.getIntValue("code")){ //食谱类
                result = new FormatXmlProcess().formatCookBookXmlAnswer(xmlEntity.getFromUserName(),xmlEntity.getToUserName(),json);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}