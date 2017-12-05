package com.huateng.shiro;

import com.huateng.dao.RolePermissionMapper;
import com.huateng.dao.UserMapper;
import com.huateng.dao.UserRoleMapper;
import com.huateng.entity.User;
import com.huateng.entity.UserRole;
import com.huateng.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shuaion 2017/11/28
 **/
@Component
public class ShiroDbRelam extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    private Logger logger = LoggerFactory.getLogger(ShiroDbRelam.class);
/*
* shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();当访问到页面的时候，
* 链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，所以如果只是简单的身份认证没有权限的控制的话，
* 那么这个方法可以不进行实现，直接返回null即可。在这个方法中主要是使用类：SimpleAuthorizationInfo进行角色的添加和权限的添加。
* */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("-----doGetAuthorizationInfo--------");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String userName = principalCollection.getPrimaryPrincipal().toString();
        User user = userMapper.getUserByName(userName);
        List<UserRole> userRoles = userRoleMapper.getUserRoleById(user.getId().toString());
        Set<String> roles = new HashSet<String>();
        for (UserRole role:userRoles){
            roles.add(role.getRoleCode());
        }
        simpleAuthorizationInfo.setRoles(roles);

        List<Map<String,Object>> pers = rolePermissionMapper.getPermissionByUser(user.getId());
        Set<String> permissions = new HashSet<String>();
        for (Map<String,Object> per:pers){
            permissions.add(per.get("permiss").toString());
        }
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /*这里不做密码校验 密码校验需要实现 CredentialsMatcher 或者 配置SimpleCredentialsMatcher类*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //这个token，是在登录方法里面，调用login方法，把用户名和密码传递过来的
        //Subject subject = SecurityUtils.getSubject();
        //subject.login(new UsernamePasswordToken(systemVo.getUsername(), systemVo.getPassword()));

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userMapper.getUserByName(token.getUsername());
        if (user==null){
            throw new UnknownAccountException("用户不存在");
        }
        /*if(!String.valueOf(token.getPassword()).equals(user.getPassword())){
            return null;
        }*/
        // 如果身份认证验证成功，返回一个AuthenticationInfo实现
        // 这里处理的流程：暂时是，先通过了系统的自定义流程之后，再调用shiro的login功能，跳转到这里，所以
        // 就不用去获取用户等数据了。直接返回对象：简单的对象实体 new SimpleAuthenticationInfo(username,
        // password, getName());
        // 第一个参数，可以在授权方法中获取到
        logger.info("-------doGetAuthenticationInfo-------");

         return new SimpleAuthenticationInfo(token.getUsername(),user.getPassword(),this.getName());
    }
}
