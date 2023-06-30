package com.example.yuch_nug.mapper;

import com.example.yuch_nug.dto.LoginDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface LoginMapper {
    public int loginSave (String id, String pw);
    public List<LoginDTO> login (String id, String pw);
}
