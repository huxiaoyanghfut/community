package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @author:xiaoyang
 * @Title: AlphaDaoMyBatisImpl
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/10 19:55
 */

@Repository
@Primary
public class AlphaDaoMyBatisImpl implements AlphaDao{
    @Override
    public String select() {
        return "MyBatis";
    }
}
