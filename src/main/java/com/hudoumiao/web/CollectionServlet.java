/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.web;

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
@WebServlet(name = "CollectionServlet", urlPatterns = {"/Collection/*"})
public class CollectionServlet extends HttpServlet {

    @EJB
    private HuDouService huDouService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = Tools.getPathInfoAt(request, 0).toLowerCase();
        switch (pathInfo) {
            case "save":
                doSave(request, response);
                break;
            case "remove":
                doRemove(request, response);
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

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long customerId = Tools.getLong(Tools.getCookieValue(request, response, "customerId"));
        Long bookId = Tools.getLong(Tools.getRequestParameter(request, "bookId"));
        int score = Tools.getLong(Tools.getRequestParameter(request, "score")).intValue();
        String tags = Tools.getRequestParameter(request, "tags");
        String comment = Tools.getRequestParameter(request, "comment");

        huDouService.saveCollection(bookId, customerId, score, comment, tags);

        response.setHeader("X-TESTCODE", "/Collection/save");
        Tools.redirect(request.getContextPath() + "/Book/" + bookId, request, response);
    }

    private void doRemove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long customerId = Tools.getLong(Tools.getCookieValue(request, response, "customerId"));
        Long bookId = Tools.getLong(Tools.getRequestParameter(request, "bookId"));

        huDouService.removeCollection(bookId, customerId);

        response.setHeader("X-TESTCODE", "/Collection/remove");
        Tools.redirect(request.getContextPath() + "/Book/" + bookId, request, response);
    }
}
