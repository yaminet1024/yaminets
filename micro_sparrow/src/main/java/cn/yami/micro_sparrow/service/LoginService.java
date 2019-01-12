package cn.yami.micro_sparrow.service;

import cn.yami.micro_sparrow.entity.LoginInfo;
import cn.yami.micro_sparrow.redis.RedisService;
import cn.yami.micro_sparrow.utils.Encrypt;
import cn.yami.micro_sparrow.utils.HtmlunitInit;
import com.gargoylesoftware.htmlunit.Cache;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    RedisService redisService;

    public String Login(LoginInfo loginInfo) throws IOException {
        WebClient webClient = HtmlunitInit.getInstance().getClient();
        HtmlPage page = webClient.getPage("https://www.yuque.com/login");
        if (page.getForms().size() == 0){
            return null;
        }
        HtmlForm form = page.getForms().get(0);
        List<HtmlInput> inputList = form.getInputsByValue("");

        if (inputList.size()==0){
            return null;
        }
        HtmlInput accountInput = inputList.get(0);
        accountInput.setValueAttribute(loginInfo.getAccount());
        HtmlInput passwordInput = inputList.get(1);

        passwordInput.setValueAttribute(loginInfo.getPassword());
        HtmlButton login = form.getButtonByName("");
        if (login == null){
            return null;
        }
        WebResponse response = login.click().getWebResponse();
        return response.getResponseHeaders().get(10).getValue();
    }
}
