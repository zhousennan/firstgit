package com.zsn.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class PermissionMapperTest {
@Autowired
private PermissionMapper ps;
    @Test
    public void queryPermissionByPersonName() {
        System.out.println("11111111111111111111");
        List<String> list = ps.queryPermissionByPersonName("zsn");
        for (String s : list) {
            System.out.println(s);
        }
    }
}