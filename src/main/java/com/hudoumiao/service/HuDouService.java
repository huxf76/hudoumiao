/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.service;

import com.hudoumiao.entity.Book;
import com.hudoumiao.entity.BookCollection;
import com.hudoumiao.entity.Customer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author HUXIAOFENG
 */
@Stateless
public class HuDouService {

    @EJB
    private DaoBean daoBean;
    @EJB
    private DaoTxBean daoTxBean;
    //
    private CacheManger cacheMgr = CacheManger.getInstance();

    public Book findBook(Long bookId, boolean create) {
        Book book = cacheMgr.getBook(bookId);
        if (book != null) {
            return book;
        }
        //
        book = daoBean.findBook(bookId);
        if (book == null) {
            if (create) {
                return daoTxBean.createBook();
            } else {
                return null;
            }
        }
        book.setCollectionCount(daoBean.findCollectionTotolCount(book));
        book.setScore1Count(daoBean.findCollectionScoreCount(book, 1));
        book.setScore2Count(daoBean.findCollectionScoreCount(book, 2));
        book.setScore3Count(daoBean.findCollectionScoreCount(book, 3));
        book.setScore4Count(daoBean.findCollectionScoreCount(book, 4));
        book.setScore5Count(daoBean.findCollectionScoreCount(book, 5));
        book.setRecentCollectionList(daoBean.findRecentCollections(book));
        book.setHotTagList(daoBean.findHotTags(book));
        book.setRelatedBooks(daoBean.findRelatedBooks(book));
        //
        cacheMgr.setBook(book);
        return book;
    }

    public Customer findCustomer(String name, boolean create) {
        Customer customer = daoBean.findCustomer(name);
        if (customer == null && create) {
            customer = daoTxBean.createCustomer(name);
        }
        return customer;
    }

    public Customer findCustomer(Long customerId) {
        Customer customer = cacheMgr.getCustomer(customerId);
        if (customer != null) {
            return customer;
        }
        customer = daoBean.findCustomer(customerId);
        cacheMgr.setCustomer(customer);
        return customer;
    }

    public BookCollection findBookCollection(Book book, Customer customer) {
        BookCollection collection = cacheMgr.getBookCollection(book, customer);
        if (collection != null) {
            return (BookCollection) getPersistObject(BookCollection.class, collection);
        }
        //
        collection = daoBean.findCollection(book, customer);
        if (collection == null) {
            collection = new BookCollection();
        } else {
            collection.setTagNames(daoBean.findTagNamesByBookCollection(collection));
        }
        cacheMgr.setBookCollection(book, customer, collection);
        //
        return (BookCollection) getPersistObject(BookCollection.class, collection);
    }

    public void saveCollection(Long bookId, Long customerId, int score, String comment, String tags) {
        Customer customer = daoBean.findCustomer(customerId);
        if (customer == null) {
            return;
        }
        Book book = daoBean.findBook(bookId);
        if (book == null) {
            return;
        }
        BookCollection collection = daoBean.saveCollection(book, customer, score, comment);
        daoBean.saveCollectionTag(collection, tags);
        //
        cacheMgr.deleteBook(book);
        cacheMgr.deleteCustomer(customer);
        cacheMgr.deleteBookCollection(book, customer);
    }

    public void removeCollection(Long bookId, Long customerId) {
        Customer customer = daoBean.findCustomer(customerId);
        if (customer == null) {
            return;
        }
        Book book = daoBean.findBook(bookId);
        if (book == null) {
            return;
        }
        BookCollection bookCollection = daoBean.findCollection(book, customer);
        if (bookCollection == null) {
            return;
        }
        daoBean.removeCollectionTags(bookCollection);
        daoBean.removeCollection(bookCollection);
        //
        cacheMgr.deleteBook(book);
        cacheMgr.deleteCustomer(customer);
        cacheMgr.deleteBookCollection(book, customer);
    }

    private Object getPersistObject(Class clazz, Object obj) {
        if (clazz == null || obj == null) {
            return null;
        }
        try {
            Method method = clazz.getMethod("getId");
            Object invoke = method.invoke(obj, new Object[0]); // empty array for null input
            if (invoke != null && invoke instanceof Long) {
                Long id = (Long) invoke;
                if (id > 0) {
                    return obj;
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(HuDouService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
