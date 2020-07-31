package com.zsn.service;

import com.zsn.pojo.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PersonService {
    String checkLogin(Person person, String remenber, HttpServletResponse response, HttpServletRequest request);

    Person queryCookie(HttpServletRequest request);
}
