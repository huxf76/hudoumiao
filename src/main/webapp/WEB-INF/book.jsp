<%-- 
    Document   : book
    Created on : Nov 8, 2012, 10:06:59 PM
    Author     : HUXIAOFENG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>胡豆苗</title>
        <link href="http://img3.douban.com/css/packed_douban325159512.css" rel="stylesheet" type="text/css">
        <link href="http://img3.douban.com/css/separation/packed__all7114949039.css" rel="stylesheet" type="text/css">

        <link href="http://img3.douban.com/css/lib/packed_jquery.snippet5936216753.css" type="text/css" rel="stylesheet">
        <link href="http://img3.douban.com/css/ui/packed_dialog4563741467.css" type="text/css" rel="stylesheet">

        <style type="text/css">
            .nav-srh .inp{position:relative;z-index:40}#nav-srh-suggest{position:absolute;background:#fff;border:1px solid #ddd;z-index:99;width:304px}#nav-srh-suggest li{overflow:hidden;height:24px}#nav-srh-suggest li.active{background:#eee}#nav-srh-suggest li a{height:24px;line-height:24px;color:#666;display:block;padding:0 6px;overflow:hidden;zoom:1}#nav-srh-suggest li a:hover{background:#eee;color:#666}#nav-srh-suggest li a strong{color:#000}
        </style>
    </head>
    <body>

        <div class="top-nav">
            <div class="bd">
                <div class="top-nav-info">
                    <span class="perf-metric"><!-- _performtips_ --></span>
                    <a href="#"> ${customer.name}的帐号</a>
                    <a href="${pageContext.request.servletContext.contextPath}/Customer/logout">退出</a>
                </div>
                <div class="top-nav-items">
                    <img src="${pageContext.request.servletContext.contextPath}/images/hudoumiao.png" >
                </div>
            </div>
        </div>


        <div id="wrapper">

            <h1>
                <span property="v:itemreviewed">&nbsp;</span>
                <div class="clear"></div>
            </h1>
            <h1>
                <span property="v:itemreviewed">${book.title}</span>
                <div class="clear"></div>
            </h1>


            <div id="content">

                <div class="grid-16-8 clearfix">


                    <div class="article">
                        <div class="indent">
                            <div class="subjectwrap clearfix">

                                <div class="subject clearfix">
                                    <div id="mainpic">
                                        <img src="http://img3.douban.com/mpic/s5741591.jpg" >
                                        <br>
                                    </div>

                                    <div id="info">
                                        <span>
                                            <span class="pl"> 作者</span>: 林海音
                                        </span><br/>
                                        <span class="pl">出版社:</span> 当代中国出版社<br/>
                                        <span class="pl">出版年:</span> 2004-8<br/>
                                        <span class="pl">页数:</span> 217<br/>
                                        <span class="pl">定价:</span> 25.00元<br/>
                                        <span class="pl">装帧:</span> 12k平装<br/>
                                        <span class="pl"></span><br>
                                        <span class="pl">最热标签:</span> 
                                        <c:choose>
                                            <c:when test="${empty book.hotTagList}">
                                                还没有针对此书的标签。
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${book.hotTagList}" var="tag" varStatus="status">
                                                    ${tag.name}(${tag.count})&nbsp;
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                        <br/>
                                    </div>
                                </div>

                                <div id="interest_sectl">
                                    <div class="rating_wrap clearbox" rel="v:rating">
                                        <p class="rating_self font_normal">
                                            ${book.collectionCount}人评价
                                        </p>
                                        <span class="stars5 starstop" title="力荐"></span>
                                        &nbsp;${book.score5Count}<br>
                                        <span class="stars4 starstop" title="推荐"></span>
                                        &nbsp;${book.score4Count}<br>
                                        <span class="stars3 starstop" title="还行"></span>
                                        &nbsp;${book.score3Count}<br>
                                        <span class="stars2 starstop" title="较差"></span>
                                        &nbsp;${book.score2Count}<br>
                                        <span class="stars1 starstop" title="很差"></span>
                                        &nbsp;${book.score1Count}<br>
                                    </div>
                                    <br>

                                </div>
                            </div>
                        </div>

                        <div class="related_info">
                            <h2>
                                收藏
                                &nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;
                            </h2>
                            <c:choose>
                                <c:when test="${empty bookCollection}">
                                    <form method="POST" action="${pageContext.request.servletContext.contextPath}/Collection/save">
                                        <input type="hidden" name="ADD_BOOK_COLLECTION" value="1"/>
                                        评分： 
                                        <input type="radio" name="score" value="1"/>
                                        <input type="radio" name="score" value="2"/>
                                        <input type="radio" name="score" value="3" checked="true"/>
                                        <input type="radio" name="score" value="4"/>
                                        <input type="radio" name="score" value="5"/><br/>
                                        标签：<input type="text" name="tags" value="" />(空格分隔)<br/>
                                        评语：<input type="text"  name="comment" value="${bookCollection.comment}"/><br/>
                                        <input type="hidden" name="bookId" value="${book.id}"/>
                                        <input type="submit" name="submit" value="收藏"/>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form method="POST" action="${pageContext.request.servletContext.contextPath}/Collection/save">
                                        评分：
                                        <input type="radio" name="score" value="1" <c:if test="${bookCollection.score==1}">checked="true"</c:if> />
                                        <input type="radio" name="score" value="2" <c:if test="${bookCollection.score==2}">checked="true"</c:if> />
                                        <input type="radio" name="score" value="3" <c:if test="${bookCollection.score==3}">checked="true"</c:if> />
                                        <input type="radio" name="score" value="4" <c:if test="${bookCollection.score==4}">checked="true"</c:if> />
                                        <input type="radio" name="score" value="5" <c:if test="${bookCollection.score==5}">checked="true"</c:if> /><br/>
                                        标签：<input type="text" name="tags" value="${bookCollection.tagNames}" />(空格分隔)<br/>
                                        评语：<input type="text"  name="comment" value="${bookCollection.comment}"/><br/>
                                        <input type="hidden" name="bookId" value="${book.id}"/>
                                        <input type="submit" name="submit" value="更改" />
                                    </form>
                                    <form method="POST" action="${pageContext.request.servletContext.contextPath}/Collection/remove">
                                        <input type="hidden" name="bookId" value="${book.id}"/>
                                        <input type="submit" name="submit" value="删除" />
                                    </form>
                                    <br/>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="related_info">
                            <h2>
                                内容简介
                                &nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;
                            </h2>
                            <div class="indent" id="link-report">
                                <style type="text/css" media="screen">
                                    .intro p{text-indent:2em;}
                                </style>
                                <div class="intro">
                                    <p>林海音，台湾著名作家，以《城南旧事》等作品享誉文坛。林海音，小名英子，原籍台湾省苗栗县，1918年3月18日生于日本大孤，不久即随家人回到日本侵占下的台湾。其父不甘在日寇铁蹄下生活，又举家迁居北京，小英子即在北京长大。林海音是个比北平人还要北平的老北京，她深切地眷恋着她的第二故乡北京，她称在北京度过的二十五年时间，是金色年代，可以和故宫的琉璃瓦互映。北京城高的胡同、四合院，西山脚下的毛驴，以及脖子上挂 着铃铛的骆驼……这些影像都给了她创作的灵感。</p>    <p>摄影师沈继光先生愿意陪伴作者再走城南，用镜头去收拾古城的残片：大柯的几根垂落枝条，瓦陇中存留的枯叶，临街老店被涂盖的字号，半扇院门的插闩，还有屋顶上闲置的花盆以及巴在皇城墙上的冬雪……</p></div>
                            </div>
                            <h2>
                                作者简介
                                &nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;&nbsp;&middot;
                            </h2>
                            <div class="indent">
                                <style type="text/css" media="screen">
                                    .intro p{text-indent:2em;}
                                </style>
                                <div class="intro">
                                    <p>林海音（1918-2001），现代女作家。她原籍台湾苗栗，生于日本，长于北京。1960年以小说《城南旧事》成名。林海音不仅创作了许多小说和散文作品，她在出版业上亦有许多成绩。从1951年开始，她主编《联合报》副刊10年，树立了编辑的典范，提升了文艺副刊的水准和地位；1961年创办“纯文学出版社”，发掘鼓励许许多多的年轻作家。</p></div>
                            </div>
                        </div>
                    </div>

                    <div class="aside">
                        <h2>谁收藏了这本书?</h2>
                        <div class="indent" id="collector">
                            <c:choose>
                                <c:when test="${empty book.hotTagList}">
                                    还没有针对此书的收藏。
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${book.recentCollectionList}" var="collection" varStatus="status">
                                        <div class="ll"><img src="http://img3.douban.com/icon/u63478651-1.jpg" class="pil"  /></div>
                                        <div style="padding-left:60px">${collection.customer.name}<br/>
                                            <div class="pl ll"> ${collection.createDate}</div><br/>
                                            评分：${collection.score}
                                            <br/>
                                            <span class="pl"></span>
                                            ${collection.comment} <br/>
                                        </div>
                                        <div class="clear"></div><br/>
                                        <div class="ul" style="margin-bottom:12px;"></div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <h2>相关的书? </h2>
                        <div class="indent" id="collector">
                            <c:forEach items="${book.relatedBooks}" var="book" varStatus="status">
                                <a href="${book.id}">${book.title}</a> 
                                <br/>
                                <div class="ul" style="margin-bottom:12px;"></div>
                            </c:forEach>
                        </div>
                        <div class="clear"></div>
                        <br/>
                    </div>

                </div>
            </div>

            <div id="footer">
                <span id="icp" class="fleft gray-link">
                    &copy; 2012 hudoumiao.com, all rights reserved
                </span>
            </div>
        </div>

    </body>
</html>
