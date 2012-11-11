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

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long customerId = Tools.getLong(Tools.getCookieValue(request, response, "customerId"));
        Long bookId = Tools.getRequestLong(request, "bookId");
        int score = Tools.getRequestInteger(request, "score").intValue();
        String tags = Tools.getRequestString(request, "tags");
        String comment = Tools.getRequestString(request, "comment");
        huDouService.saveCollection(bookId, customerId, score, comment, tags);
        response.setHeader("X-TESTCODE", "/Collection/save");
        Tools.redirect(request.getContextPath() +"/Book/" + bookId, request, response);
    }

    private void doRemove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long customerId = Tools.getLong(Tools.getCookieValue(request, response, "customerId"));
        Long bookId = Tools.getRequestLong(request, "bookId");
        
        huDouService.removeCollection(bookId, customerId);
        
        response.setHeader("X-TESTCODE", "/Collection/remove");
        Tools.redirect(request.getContextPath() +"/Book/" + bookId, request, response);
    }
}
