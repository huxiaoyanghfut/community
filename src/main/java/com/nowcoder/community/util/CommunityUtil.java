package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/**
 * @author:xiaoyang
 * @Title: CommunityUtil
 * @ProjectName: community
 * @Description: 加密用户密码的工具类
 * @date: 2021/09/15 15:12
 */

public class CommunityUtil {
    //生成随机字符串用于salt
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //MD5加密，把用户密码+salt结合生成key用于加密
    public static String md5(String key) {
        if (StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
    //封装json工具，将请求或响应中的数据封装成json对象给浏览器解析
    public static String getJSONString(int code, String msg, Map<String,Object> map){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",code);
        jsonObject.put("msg",msg);
        if(map != null){
            for(String key : map.keySet()){
                jsonObject.put(key,map.get(key));
            }
        }
        return jsonObject.toJSONString();
    }

    //重载json工具
    public static String getJSONString(int code, String msg){
        return getJSONString(code,msg,null);
    }

    //重载json工具
    public static String getJSONString(int code){
        return getJSONString(code,null,null);
    }
}
