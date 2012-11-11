<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>胡豆苗</title>
        <style type="text/css">
            /* Reset */
            body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,p,blockquote,th,td { margin:0; padding:0; }
            table { border-collapse:collapse; border-spacing:0; }
            fieldset,img { border:0; }
            address,caption,cite,code,dfn,em,strong,th,var { font-style:normal; font-weight:normal; }
            ol,ul { list-style:none; }
            caption,th { text-align:left; }
            h1,h2,h3,h4,h5,h6 { font-size:100%; font-weight:normal; }
            q:before,q:after { content:''; }
            abbr,acronym { border:0; }

            /* Font,  Link & Container */
            body { font:12px/1.6 arial,helvetica,sans-serif; }
            a:link { color:#369;text-decoration:none; }
            a:visited { color:#669;text-decoration:none; }
            a:hover { color:#fff;text-decoration:none;background:#039; }
            a:active { color:#fff;text-decoration:none;background:#f93; }
            button { cursor:pointer;line-height:1.2; }
            .mod { width:100%; }
            .hd:after, .bd:after, .ft:after, .mod:after {content:'\0020';display:block;clear:both;height:0; }
            .error-tip { margin-left:10px; }
            .error-tip, .error { color:#fe2617; }

            /* Layout */
            .wrapper { width:950px;margin:0 auto; }
            #header { padding-top:30px; }
            #content { min-height:400px;*height:400px; }
            #header, #content { margin-bottom:40px; }
            #header, #content, #footer { width:100%;overflow:hidden; }
            #footer { color:#999;padding-top:6px;border-top: 1px dashed #ddd; }
            .article { float:left;width:590px; }
            .aside { float:right;width:310px;color:#666; }
            .aside li { padding-bottom: 20px; }
            .bside { float:left;width:310px;color:#666; }
            .bside li { padding-bottom: 20px; }

            .narrow-layout .wrapper { width:90%; }
            .narrow-layout h1 { padding-bottom:10px; }
            .narrow-layout #header { padding-top:10px;margin-bottom:20px; }
            .narrow-layout .article, .narrow-layout .aside { width:auto;float:none;margin-bottom:20px; }
            .narrow-layout .aside li { padding:0;margin-bottom:10px; }
            .narrow-layout .article, .narrow-layout .bside { width:auto;float:none;margin-bottom:20px; }
            .narrow-layout .bside li { padding:0;margin-bottom:10px; }
            .narrow-layout .fright { display:block;float:none; }

            /* header */
            .logo { float:left; width:215px;  height:30px; overflow:hidden; line-height:10em; }
            a.logo:link,
            a.logo:visited,
            a.logo:hover,
            h1 { color:#494949;display:block;font-size:25px;font-weight:bold;line-height:1.1;margin:0;padding:0 0 30px;word-wrap:break-word; }

            /* form */
            .item { clear:both;margin:15px 0;zoom:1; }
            label { display: inline-block; float:left; margin-right: 15px; text-align: right; width: 60px; font-size: 14px; line-height: 30px; vertical-align: baseline }
            .remember { cursor: pointer; font-size: 12px; display: inline; width: auto; text-align: left; float: none; margin: 0; color: #666 }
            .item-captcha input,
            .basic-input { width: 200px; padding: 5px; height: 18px; font-size: 14px;vertical-align:middle; -moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius: 3px; border: 1px solid #c9c9c9 }
            .item-captcha input:focus,
            .basic-input:focus { border: 1px solid #a9a9a9 }
            .item-captcha input { width:100px; }
            .item-captcha .pl { color:#666; }
            .btn-submit { cursor: pointer;color: #ffffff;background: #3fa156; border: 1px solid #528641; font-size: 14px; font-weight: bold; padding:6px 26px; border-radius: 3px; -moz-border-radius: 3px; -webkit-border-radius: 3px; *width: 100px;*height:30px; }
            .btn-submit:hover { background-color:#4fca6c;border-color:#6aad54; }
            .btn-submit:active { background-color:#3fa156;border-color:#528641; }
            #item-error { padding-left:75px; }

            /* footer */
            .fright { float:right; }
            .icp { float:left; }

            .item-captcha img { max-width:70%; }
            body { -webkit-text-size-adjust: none;-webkit-touch-callout: none;-webkit-tap-highlight-color: transparent; }

        </style>
    </head>
    <body>
        <div class="wrapper">
            <div id="header">
                <img src="images/hudoumiao.png"/>
            </div>

            <div id="content">
                <h1>登录胡豆苗</h1>
                <div class="article">
                    <form method="POST" action="${pageContext.request.servletContext.contextPath}/Customer/login">
                        <div class="item">
                            <label>用户名</label>
                            <input  type="text" name="name" class="basic-input" maxlength="60" value="" tabindex="1"/>
                        </div>
                        <div class="item">
                            <label>&nbsp;</label>
                            <input type="submit" value="登录" name="user_login" class="btn-submit" tabindex="5"/>
                        </div>
                        <div class="item">
                            <label>&nbsp;</label>
                            <ul class="bside">
                                <li>&nbsp;</li>
                                <li><b>如何登录？</b></li>
                                <li>输入任意用户名即可登录。<br/>
                                    如果用户名为空，则系统生成一个随机账户。<br/>
                                    如果用户名不存在，则系统生成一个此用户名的账户。</li>
                                <li><b>登录后能干什么？</b></li>
                                <li>每个用户登录后缺省显示ID=100的图书，用户可以收藏、评分。用户也可以查看其它图书并收藏评分。</li>
                            </ul>
                        </div>
                    </form>

                </div>
                <ul id="side-nav" class="aside">
                    <li><b>什么是胡豆苗？</b></li>
                    <li>胡豆苗是一个原型系统，功能类似于豆瓣读书的收藏、标签、评分功能。</li>
                    <li><b>胡豆苗有什么神奇的地方？</b></li>
                    <li>胡豆苗原型系统主要体现在大访问量下Cache对于系统性能提高的作用。</li>

                </ul>
            </div>

            <div id="footer">
                <span id="icp" class="fleft gray-link">
                    &copy; 2012 hudoumiao.com, all rights reserved
                </span>
            </div>
    </body>
</html>
