package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @author:xiaoyang
 * @Title: AlphaDaoHibernateImpl
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/10 19:55
 */

@Repository("alphaHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao {
    @Override
    public String select() {
        return "Hibernate";
    }
}

