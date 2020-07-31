package com.zsn.controller;
import com.google.gson.Gson;
import com.zsn.pojo.Person;
import com.zsn.service.PersonService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("sp")
public class PersonController {
    @Autowired
private PersonService ps;
    @RequestMapping("first.do")
    public String longin(){
            return "login";
    }
   @RequestMapping("index.do")
    public String index(){
        return "index";
    }
    @RequestMapping("showWelcome.do")
    public String showWelcome(){
        return "welcome";
    }

    @RequestMapping("checkLogin.ajax")
    @ResponseBody
    public String cheacked(Person person, String remenber, HttpServletResponse response,HttpServletRequest request){
        //System.out.println(person);
        System.out.println(remenber);
        String info = ps.checkLogin(person,remenber,response,request);
        return info;
    }
    @RequestMapping(value = "logout.ajax",produces="application/text")
    @ResponseBody
    public String logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        Person person1 = ps.queryCookie(request);
        //System.out.println(new Gson().toJson(person1));
        return new Gson().toJson(person1);
    }
}
