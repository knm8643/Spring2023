package com.example.yuch_nug.comtroller;

import com.example.yuch_nug.dto.LoginDTO;
import com.example.yuch_nug.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/SiginPage")
    public String SigInPage(HttpServletRequest request, String id, String pw) {
        System.out.println("사용자가 입력한 정보는?");
        System.out.println(id + "/" + pw);
        loginService.loginSave(id, pw);

        String referer = request.getHeader("Referer"); // Referer 헤더 값을 얻습니다.

        if (referer != null) {
            return "redirect:/"; // 이전 페이지로 리다이렉트합니다.
        } else {
            return "redirect:/index.html"; // 이전 페이지 정보가 없으면 기본 페이지로 이동합니다.
        }
    }

    @PostMapping("/logins")
    public String loginPage(String id, String pw, HttpServletResponse response) {
        System.out.println("로그인 값 : " + id + " : " + pw);

        if (id.equals("") || pw.equals("")) {
            System.out.println("입력된 정보가 없음");
        } else {
            List<LoginDTO> loginResult = loginService.login(id, pw);

            // 회신된 정보
            System.out.println(loginResult.size());
            for (LoginDTO loginDTO : loginResult) {
                System.out.println(loginDTO.getId());
                System.out.println(loginDTO.getPw());
            }

            if (loginResult.size() > 0) {
                System.out.println("로그인에 성공했습니다");

                Cookie cookie = new Cookie("login", "true");
                cookie.setMaxAge(3600);
                cookie.setPath("/");
                response.addCookie(cookie);
            } else {
                System.out.println("로그인에 실패했습니다");
            }
        }
        return "redirect:/index.html";
    }
}

