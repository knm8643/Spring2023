package com.example.yuch_nug.service;

import com.example.yuch_nug.dao.LoginDAO;
import com.example.yuch_nug.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginDAO loginDAO;

    public int loginSave(String id, String pw){
        return loginDAO.loginSave(id, pw);
    }
    public List<LoginDTO> login(String id, String pw){
        return loginDAO.login(id, pw);
    }
}
