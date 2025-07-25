package com.example.yuch_nug.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubscribeMapper {
    void insertEmail(@Param("email") String email);
    int emailExists(@Param("email") String email);
}