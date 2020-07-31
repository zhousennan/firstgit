package com.zsn.mapper;

import com.zsn.pojo.Person;

import java.util.List;

public interface PersonMapper {
    int deleteByPrimaryKey(String personId);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(String personId);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);

    Person queryPersonByPersonName(String userName);


}