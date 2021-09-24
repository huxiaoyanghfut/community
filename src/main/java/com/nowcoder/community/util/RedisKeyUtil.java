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
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    //like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT +entityType +SPLIT + entityId;
    }
}
