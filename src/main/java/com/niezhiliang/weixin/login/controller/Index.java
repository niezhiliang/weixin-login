package com.niezhiliang.weixin.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    @Value("${wx.userinfo_url}")
    private String userinfo_url;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "callback")
    public String callBack() {
        logger.info(appid + "\t" + secret + "\t"+grant_type+"\t"+request_url);
        String code = request.getParameter("code");
        if (code != null) {
            StringBuffer url = new StringBuffer();
            /*********获取token************/
            url.append(request_url)
                    .append("appid=")
                    .append(appid)
                    .append("&secret=")
                    .append(secret)
                    .append("&code=")
                    .append(code)
                    .append("&grant_type=")
                    .append(grant_type);
            logger.info(url.toString());

            JSONObject jsonObject =
                    JSON.parseObject(HttpUtil.getResult(url.toString()));
            String openid =jsonObject.get("openid").toString();
            String token = jsonObject.get("access_token").toString();

            /*********获取userinfo************/
            url = new StringBuffer();
            url.append(userinfo_url)
                    .append("access_token=")
                    .append(token)
                    .append("&openid=")
                    .append(openid);
            logger.info(url.toString());
            String result = HttpUtil.getResult(url.toString());
            logger.info(result);

            return "success";
        }

        return "success";
    }

    @RequestMapping(value = "login")
    public String login() {

        return "qrcode";
    }
}
