package com.zsn.realm;
import com.zsn.mapper.PermissionMapper;
import com.zsn.mapper.PersonMapper;
import com.zsn.pojo.Permission;
import com.zsn.pojo.Person;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @ClassName MyRealm
 * @Description 自定义的reaml类   实现用户认证和授权
 * @Author ss
 * @Date 2020/7/28 10:56
 * @Version 1.0
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private PersonMapper pm;
@Autowired
private PermissionMapper ps;
    //用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进来授权了");
        SimpleAuthorizationInfo sai=new SimpleAuthorizationInfo();
        //Subject subject= SecurityUtils.getSubject();
        //  Person person=(Person)subject.getPrincipal();
        // sai.addStringPermission("admain");



        String principal =(String)principals.getPrimaryPrincipal();
        System.out.println(principal);
        List<String> permissions= ps.queryPermissionByPersonName(principal);
        Set<String> roles=new HashSet<String>();
        for (String s:permissions) {
            System.out.println(s);
            roles.add(s);
        }

//        if (principal.equals("user"))
//        roles.add("user");
//        if (principal.equals("admain"))
//        roles.add("admain");
        sai.addRoles(roles);
        return sai;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1.将token强转为UsernamePasswordToken类型（可以通过这个类型可以拿到身份(用户名)）
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;

        Person p = pm.queryPersonByPersonName(token1.getUsername());

        if (p != null) {
            //2.设置shiro比对器身份
            Object principal = p.getPersonName();
            //3.设置比对器里面的密码(把数据库里面的密码作为比对密码)
            String credentials = p.getPersonPassword();
            //4.自动给令牌类里面的用户名设置加盐方式
            ByteSource salt = ByteSource.Util.bytes(p.getPersonName());
            //5.设置realm的名称
            String name = this.getName();
            //new SimpleAuthenticationInfo(principal,credentials,salt,realmName)才是真正的认证
            /*
           如何认证：
           将上面的principal和token1里面的用户名来比对
           将上面的credentials和token1里面的密码来比对
           因为spring-shiro.xml里面配置加密方式 所以会自动把token1里面的密码以MD5加密，加密1024次
          因为上面传了个salt，表示再把token1里面的密码再以salt加盐
           * */
            return new SimpleAuthenticationInfo(principal, credentials, salt, name);
        } else {
            throw new AuthenticationException();
        }

    }
//    public static void main(String[] args) {
//        //加密测试代码
//        //设置加密方式
//        String algorithmName="MD5";
//        //设置待加密的原密码
//        Object source="123";
//        //设置加盐方式(一般来说都是以用户名来加盐)
//        Object salt= ByteSource.Util.bytes("user");
//        //加密次数
//        int hashIterations=1024;
//        SimpleHash newPassword=new SimpleHash(algorithmName, source, salt, hashIterations);
//        System.out.println(newPassword.toString());
//    }
}
