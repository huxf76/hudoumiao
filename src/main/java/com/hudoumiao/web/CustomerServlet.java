/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.web;

import com.hudoumiao.entity.Customer;
import com.hudoumiao.service.HuDouService;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HUXIAOFENG
 */
@WebServlet(name = "CustomerServlet", urlPatterns = {"/Customer/*"})
public class CustomerServlet extends HttpServlet {

    @EJB
    private HuDouService huDouService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = Tools.getPathInfoAt(request, 0).toLowerCase();
        switch (pathInfo) {
            case "login":
                doLogin(request, response);
                break;
            case "logout":
                doLogout(request, response);
                break;
            default:
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }// </editor-fold>

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = Tools.getRequestParameter(request, "name");
        Customer customer = huDouService.findCustomer(name, true);
        Long customerId = customer.getId();
        Tools.setCookieValue(request, response, "customerId", customerId.toString());
        response.setHeader("X-TESTCODE", "/Customer/login");
        Tools.redirect(request.getContextPath() + "/Book/100", request, response);
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tools.removeCookie(request, response, "customerId");
        response.setHeader("X-TESTCODE", "/Customer/logout");
        Tools.redirect(request.getContextPath() + "/", request, response);
    }
}
