package com.nowcoder.community.util;

/**
 * @author:xiaoyang
 * @Title: RedisKeyUtil
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/24 8:51
 */

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    //点赞（用户频繁访问的功能）
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    //关注（用户频繁访问的操作）
    private static final String PREFIX_FOLLOWEE = "followee";
    private static final String PREFIX_FOLLOWER = "follower";
    //验证码（频繁访问，有时效性）
    private static final String PREFIX_KAPTCHA = "kaptcha";
    //登录凭证（有时效性）
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "user";


    //like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT +entityType +SPLIT + entityId;
    }

    //将用户的id作为key，统计用户收到的点赞（实体的userId）
    //like:user:userId -> int
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }
    // 某个用户关注的实体
    // followee:userId:entityType -> zset(entityId,now)
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    // 某个实体拥有的粉丝
    // follower:entityType:entityId -> zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    //获取验证码的key
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    //获取登录凭证的key
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    //获取登录凭证的key
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }
}