package com.example.yuch_nug.dao;

import com.example.yuch_nug.dto.LoginDTO;
import com.example.yuch_nug.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginDAO {

    @Autowired
    private LoginMapper loginMapper;

    public int loginSave(String id, String pw){
        return loginMapper.loginSave(id, pw);
    }
    public List<LoginDTO> login(String id, String pw){
        return loginMapper.login(id, pw);
    }
}

