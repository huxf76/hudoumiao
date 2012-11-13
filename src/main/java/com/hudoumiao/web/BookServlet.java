/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.web;

import com.hudoumiao.entity.Book;
import com.hudoumiao.entity.BookCollection;
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
@WebServlet(name = "BookServlet", urlPatterns = {"/Book/*"})
public class BookServlet extends HttpServlet {

    @EJB
    private HuDouService huDouService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String cookieValue = Tools.getCookieValue(request, response, "customerId");
        Long customerId = Tools.getLong(cookieValue);
        Customer customer = huDouService.findCustomer(customerId);
        if (customer == null) {
            response.setHeader("X-TESTCODE", "/Book, must login");
            Tools.redirect(request.getContextPath() + "/", request, response);
            return;
        } else {
            request.setAttribute("customer", customer);
        }

        String pathInfo = Tools.getPathInfoAt(request, 0);
        Long bookId = Tools.getLong(pathInfo);
        Book book = huDouService.findBook(bookId, true);
        request.setAttribute("book", book);

        BookCollection bookCollection = huDouService.findBookCollection(book, customer);
        request.setAttribute("bookCollection", bookCollection);
        response.setHeader("X-TESTCODE", "/Book, rander book page");
        Tools.randerPage("/WEB-INF/book.jsp", request, response);
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
}
