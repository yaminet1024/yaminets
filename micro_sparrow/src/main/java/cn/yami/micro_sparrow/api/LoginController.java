package cn.yami.micro_sparrow.api;

import cn.yami.micro_sparrow.entity.LoginInfo;
import cn.yami.micro_sparrow.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String login(){
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAccount("13169166451");
        loginInfo.setPassword("1574150143");
        try {
            return loginService.Login(loginInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
