package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息,用于代替session对象.
 */
@Component
public class HostHolder {
    //在浏览器访问服务端的一次请求线程中设置TreadLocal<User>变量持有用户数据，以便在该线程中复用,可以理解为用户持有一个自己的User
    //如用于显示用户信息
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
