package com.niezhiliang.weixin.login.controller;

import com.niezhiliang.weixin.login.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 */
@Controller
@RequestMapping(value = "/")
public class Index {
    private final static Logger logger = Logger.getLogger(Index.class.getName());
    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.grant_type}")
    private String grant_type;

    @Value("${wx.request_url}")
    private String request_url;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "callback")
    @ResponseBody
    public String callBack() {
        logger.info(appid + "\t" + secret + "\t"+grant_type+"\t"+request_url);
        String code = request.getParameter("code");
        if (code != null) {
            StringBuffer url = new StringBuffer();
            url.append(request_url)
                    .append("appid=")
                    .append("&secret=")
                    .append(secret)
                    .append("&code=")
                    .append(code)
                    .append("&grant_type=")
                    .append(grant_type);
            logger.info(url.toString());
            String result =
                HttpUtil.getResult(url.toString());
            return result;
        }

        return "success";
    }

    @RequestMapping(value = "login")
    public String login() {

        return "qrcode";
    }
}
