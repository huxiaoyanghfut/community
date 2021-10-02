package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author:xiaoyang
 * @Title: LoginTicketMapper
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/16 15:34
 */
@Mapper
@Deprecated
public interface LoginTicketMapper {
    @Select({
            "select id, user_Id, ticket, status, expired ",
            "from login_ticket",
            "where ticket = #{ticket}"
    })
    LoginTicket selectByTicket(String ticket);
    @Insert({
            "insert into login_ticket(user_Id, ticket, status, expired )",
            "values(#{userId},#{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);
    @Update({
            "update login_ticket set status=#{status} where ticket=#{ticket} "
    })
    int updateStatus(String ticket, int status);
}
