package com.example.yuch_nug.comtroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/signin")
    public String signInPage(){
        return "signin";
    }

    @GetMapping("/calendarAPI")
    public String openCalender(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("login") && cookie.getValue().equals("true")){
                    return "calendarAPI";
                }
            }
        }
        System.out.println("로그인 해주세요. 쿠키값이 없으시군요?");
        return "redirect:/index.html?alert=true";
    }
}
