/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.web;

import java.io.IOException;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author HUXIAOFENG
 */
public class Tools {

    public static Long randomBookId() {
        Random r = new Random();
        int nextInt = r.nextInt(9999998) + 1;
        Long id = new Long(nextInt);
        return id;
    }

    public static Long getLong(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        str = str.trim();
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Integer getInteger(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Boolean getBoolean(String str) {
        if (str == null) {
            return null;
        }
        return Boolean.valueOf(str);
    }

    public static String getRequestParameter(HttpServletRequest request, String param) throws ServletException, IOException {
        if (StringUtils.isBlank(param)) {
            return null;
        }
        String result = request.getParameter(param);
        if (StringUtils.isBlank(result)) {
            result = null;
        } else {
            result = result.trim();
        }
        return result;
    }

    public static String getPathInfoAt(HttpServletRequest request, int index) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }
        String[] pathArray = StringUtils.split(pathInfo, "/");
        String path = pathArray[index];
        if (StringUtils.isBlank(path)) {
            path = null;
        } else {
            path = path.trim();
        }
        return path;
    }

    public static String getCookieValue(HttpServletRequest request, HttpServletResponse response, String cname) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            String name = cookie.getName();
            if (name.equals(cname)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static void setCookieValue(HttpServletRequest request, HttpServletResponse response, String cname, String cvalue) {
        Cookie cookie = new Cookie(cname, cvalue);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cname) {
        Cookie cookie = new Cookie(cname, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void randerPage(String url, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }

    public static void redirect(String url, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(url);
        response.flushBuffer();
    }
}
