package cn.yami.micro_sparrow.utils;

import com.gargoylesoftware.htmlunit.WebClient;

public class HtmlunitInit {

    private static volatile HtmlunitInit instance;
    private  volatile WebClient client;

    private HtmlunitInit(){
        client = new WebClient();
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setDownloadImages(false);
        client.getOptions().setHistorySizeLimit(1);
        client.getOptions().setRedirectEnabled(false);
    }

    public static HtmlunitInit getInstance() {
        if (instance == null){
            synchronized (HtmlunitInit.class){
                if (instance == null){
                    instance = new HtmlunitInit();
                    return instance;
                }
            }
        }
        return instance;
    }


    public WebClient getClient() {
        client.getCookieManager().clearCookies();
        return client;
    }
}
