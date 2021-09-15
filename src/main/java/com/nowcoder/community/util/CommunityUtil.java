package com.nowcoder.community.util;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
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
}
