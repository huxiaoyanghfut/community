package com.nowcoder.community.service;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.PasswordAuthentication;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * @author:xiaoyang
 * @Title: UserService
 * @ProjectName: community
 * @Description: TODO
 * @date: 2021/09/14 19:36
 */
@Service
public class UserService implements CommunityConstant{
    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private HostHolder hostHolder;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User findUserById(int id){
        return userMapper.selectById(id);
    }

    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }

    public int updateHeader(int userId, String headerUrl) {
        return userMapper.updateHeader(userId, headerUrl);
    }

    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        if (user == null){
            throw new IllegalArgumentException("参数不能为空！");
        }
        if (StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg", "账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg", "账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg", "邮箱不能为空！");
            return map;
        }
        User user1 = userMapper.selectByName(user.getUsername());
        if (user1 != null){
            map.put("usernameMsg", "账号已被注册！");
            return map;
        }

        user1 = userMapper.selectByEmail(user.getEmail());
        if (user1 != null){
            map.put("emailMsg", "邮箱已被使用！");
            return map;
        }
        //注册用户
        //用户输入信息合格，往数据库中添加用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);


        //激活邮件,往用户注册邮箱中发送邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        user = userMapper.selectByName(user.getUsername());
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(),"马客网账号激活",content);

        return map;
    }

    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        System.out.println(user);
        if (user.getStatus() == 1){
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        }else {
            return ACTIVATION_FAILURE;
        }
        }

    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        //用map来存调用服务后的反馈消息
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if (StringUtils.isBlank(username)){
            map.put("usernameMsg", "用户名不能为空！");
            return map;

        }
        if (StringUtils.isBlank(password)){
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }
        //验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在！");
            return map;
        }
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活！");
            return map;
        }
        //将明文密码加密处理后与数据库中密码进行比对
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)){
            map.put("passwordMsg", "密码不正确！");
            return map;
        }

        //生成登陆凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds*1000));
        loginTicketMapper.insertLoginTicket(loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    //登出
    public void logout(String ticket) {
        loginTicketMapper.updateStatus(ticket, 1);
    }

    public LoginTicket findLoginTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }

    //修改密码
    public Map<String, Object> updatePassword(String oldPassword, String newPassword, String newPasswordConfirm) {
        Map<String, Object> map = new HashMap<>();
        //原密码判空
        if (StringUtils.isBlank(oldPassword)){
            map.put("oldPasswordMsg", "请输入原始密码！");
            return map;
        }

        if (StringUtils.isBlank(newPassword)){
            map.put("newPasswordMsg", "请输入新密码！");
            return map;
        }

        if (newPassword.length() < 8){
            map.put("newPasswordMsg", "新密码长度不能小于8位！");
            return map;
        }
        if (!newPassword.equals(newPasswordConfirm)){
            map.put("newPasswordMsg", "两次输入密码不一致！");
            return map;
        }
        //原密码校验
        //获取当前用户
        User currentUser = hostHolder.getUser();
        oldPassword = CommunityUtil.md5(oldPassword + currentUser.getSalt());
        if (!oldPassword.equals(currentUser.getPassword())){
            map.put("oldPasswordMsg", "请输入正确的原始密码！");
            return map;
        }
        //修改密码，将数据库中用户的旧密码修改为新密码
        map.put("salt",currentUser.getSalt());
        newPassword =  CommunityUtil.md5(newPassword + currentUser.getSalt());
        userMapper.updatePassword(currentUser.getId(), newPassword);
        return map;
    }


}
