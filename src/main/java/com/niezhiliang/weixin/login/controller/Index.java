package com.niezhiliang.weixin.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 */
@Controller
@RequestMapping(value = "/")
public class Index {
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "callback")
    public String callBack() {
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            System.out.println(paraName+": "+request.getParameter(paraName));
        }
        return null;
    }

    @RequestMapping(value = "login")
    public String login() {

        return "qrcode";
    }
}
