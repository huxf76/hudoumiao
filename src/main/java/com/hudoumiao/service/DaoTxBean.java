/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.service;

import com.hudoumiao.entity.Book;
import com.hudoumiao.entity.Customer;
import com.hudoumiao.entity.Tag;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author HUXIAOFENG
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DaoTxBean {

    @PersistenceContext(unitName = "HuDouMiaoPU")
    private EntityManager em;
    @Resource
    private UserTransaction ut;

    public Book createBook() {
        try {
            ut.begin();
            Book book = new Book();
            em.persist(book);
            ut.commit();

            ut.begin();
            book.setTitle("图书" + book.getId());
            em.merge(book);
            ut.commit();

            return book;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(DaoTxBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Customer createCustomer(String name) {
        try {
            ut.begin();
            Customer customer = new Customer();
            em.persist(customer);
            ut.commit();

            ut.begin();
            if (StringUtils.isBlank(name)) {
                customer.setName("用户" + customer.getId());
            } else {
                customer.setName(name);
            }
            em.merge(customer);
            ut.commit();

            return customer;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(DaoTxBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Tag createTag(String name) {
        try {
            ut.begin();
            Tag tag = new Tag(name);
            em.persist(tag);
            ut.commit();

            return tag;
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
            try {
                ut.rollback();
            } catch (IllegalStateException | SecurityException | SystemException ex) {
                Logger.getLogger(DaoTxBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
