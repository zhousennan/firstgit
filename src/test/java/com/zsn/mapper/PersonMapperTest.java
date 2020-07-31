package com.zsn.mapper;

import com.zsn.pojo.Person;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class PersonMapperTest {
    @Autowired
private PersonMapper p;
    @Test
    public void queryPersonByPersonName() {
        Person zsn = p.queryPersonByPersonName("zsn");
        System.out.println(zsn);
    }
}