package com.example.shiro;

import com.example.cache.J2CacheUtils;
import com.example.dao.SysMenuDao;
import com.example.dao.SysUserDao;
import com.example.entity.SysMenuEntity;
import com.example.entity.SysUserEntity;
import com.example.service.SysUserService;
import com.example.utils.Constant;
import com.example.utils.Global;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //角色权限和对应权限添加
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        //查询所有的权限
        SysUserEntity sysUser=sysUserDao.queryObject(user.getUserId());

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //系统管理员，拥有最高权限
//        if (Constant.SUPER_ADMIN == sysUser.getId()) {
//            //添加所有角色&权限
//
//        }

//        for (SysRole role:sysUser.getRoleList()) {
//            //添加角色
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            for (SysPermission permission:role.getPermissionList()) {
//                //添加权限
//                simpleAuthorizationInfo.addStringPermission(permission.getPerms());
//            }
//            for (SysMenu menu:role.getMenuList()) {
//                //添加权限
//                if(menu.getType()==2){
//                    simpleAuthorizationInfo.addStringPermission(menu.getPerms());
//                }
//            }
//        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        //查询用户信息
        SysUserEntity user = sysUserDao.queryByUserName(username);

        //账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        // 把当前用户放入到session中
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        session.setAttribute(Global.CURRENT_USER, user);

        List<String> permsList;

        //系统管理员，拥有最高权限
        if (Constant.SUPER_ADMIN == user.getUserId()) {
            List<SysMenuEntity> menuList = sysMenuDao.queryList(new HashMap<String, Object>());
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(user.getUserId());
        }
        J2CacheUtils.put(Constant.PERMS_LIST + user.getUserId(), permsList);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }


}
