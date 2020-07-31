package com.zsn.service.Impl;

import com.zsn.pojo.Person;
import com.zsn.service.PersonService;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.stereotype.Service;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class PersonServiceImpl implements PersonService {
    public String checkLogin(Person p, String remenber, HttpServletResponse response, HttpServletRequest request) {
        //1.拿到当前用户
        Subject s = SecurityUtils.getSubject();


        //2.判断当前用户是否被认证，并做相关处理
        if (!s.isAuthenticated()) {
            //UsernamePasswordToken 令牌类  稍后会把保存在里面账号密码和shiro的身份和凭证比对
            UsernamePasswordToken upt = new UsernamePasswordToken(p.getPersonName(), p.getPersonPassword());
            upt.setRememberMe(true);
            try {
                //进行认证(因为我们写了自定义的realm类，所以会自动到realm类里面去认证)
                s.login(upt);
                //登录成功
//                Session session = s.getSession();
//                session.setAttribute("userName",p.getPersonName());
                if (remenber.equals("YES")){
                    SimpleCookie sc1=new SimpleCookie();
                    sc1.setName("name");
                    sc1.setValue(p.getPersonName());
                    sc1.setName("pasaword");
                    sc1.setValue(p.getPersonPassword());
                    sc1.setMaxAge(500000000);
                    sc1.saveTo(request,response);
                    //SimpleCookie sc2=new SimpleCookie();

//                    Cookie c1=new Cookie("name",p.getPersonName());
//                    Cookie c2=new Cookie("password",p.getPersonPassword());
//                    c1.setMaxAge(600000);
//                    c2.setMaxAge(600000);
//                    response.addCookie(c1);
//                    response.addCookie(c2);
                }
                return "success";
            } catch (AuthenticationException e) {
                //登录失败
                return "ERROR";
            }
        }
        return null;

    }

    public Person queryCookie(HttpServletRequest request) {
        Cookie[] cookies=request.getCookies();
        Person p=new Person();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("name")){
                p.setPersonName(cookie.getValue());
            }
            if (cookie.getName().equals("password")){
                p.setPersonPassword(cookie.getValue());
            }
        }

        return p;
    }


}
