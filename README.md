# weixin-login
微信第三方登录
![演示gif](https://github.com/niezhiliang/weixin-login/blob/master/imgs/weixin.gif)


#### 第一步

 在开放平台设置好回调地址，例如地址是 `www.niezhiliang.com` 那么在生成二维码的时候回调只能写该域名下的地址
 
 在申请二维码页面回调地址(你的redirect_uri) 还必须加上http或者https，还必须进行转码操作  
 
 平常我们是`http://www.niezhiliang/callback` 必须转成这个样子 `http%3a%2f%2fwww.niezhiliang.com%2fcallback`
 
 会报你的`redirect_uri` 不正确
 
 
 申请二维码页面代码：
 
         <!DOCTYPE html>
         <html  xmlns:th="http://www.thymeleaf.org">
         <head>
             <meta charset="UTF-8"/>
             <title>微信登录页面</title>
             <style>
                 .impowerBox .qrcode {width: 200px;}
                 .impowerBox .title {display: none;}
                 .impowerBox .info {width: 200px;}
                 .status_icon {display: none}
                 .impowerBox .status {text-align: center;}
             </style>
             <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
         </head>
         <body>
         <div id="obj" style="text-align: center">
         
         </div>
         </body>
         <script>
             var obj = new WxLogin({
                 self_redirect:false,//true将页面跳转放在ifream里面   false直接跳转到要跳转的页面
                 id:"obj",
                 appid: "wx0c05ce174cd624b7",
                 scope: "snsapi_login",
                 redirect_uri: "http%3a%2f%2fwww.niezhiliang.com%2fcallback",
                 state: "",
                 style: "",
                 href: ""
             });
         </script>
         </html>


#### 第二步：

    手机扫描二维码以后会访问我们之前设置的回调地址，可以得到请求token的code  拿到token以后就可以通过token去请求
    
    用户的基本信息 如：
    
    {
        "openid": "oRFVX0i662JO-p1o_jnqPEU88ahc",
        "nickname": "苏雨丶",
        "sex": 1,
        "language": "zh_CN",
        "city": "Yichun",
        "province": "Jiangxi",
        "country": "CN",
        "headimgurl": "http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Q0j4TwGTfTKlDY7yfJB1ZehJECjLQ8d89rVkX3sZFGB7ry1Q720yU5qAc2rFJfcG6gMibXwN6QnZTRIQyiaeMm8Q\/132",
        "privilege": [],
        "unionid": "of_IS5sWN3Ah0JdJ7O1LvDFT_4l0"
    }
    
    
    
回调方法：
            
        @RequestMapping(value = "/callback")
            public String callBack(Model model) {
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
                    String result = HttpUtil.getResult(url.toString());
                    model.addAttribute("wxinfo",result);
                    model.addAttribute("username",map.get("username"));
                    model.addAttribute("password",map.get("password"));
                    //拿到用户信息后跳转到要跳转的页面  
                    return "index";
                }
        
                return "index";
            }
    
    
我这边只是个demo 所以做的很简单，大家根据自己的业务需求来做吧
