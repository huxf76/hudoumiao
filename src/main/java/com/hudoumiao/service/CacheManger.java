/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.service;

import com.hudoumiao.entity.Book;
import com.hudoumiao.entity.BookCollection;
import com.hudoumiao.entity.Customer;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 *
 * @author HUXIAOFENG
 */
public class CacheManger {

    private static CacheManger instance = null;
    private static MemCachedClient mcc = null;
    private static boolean disabled = true;
    private static boolean debug = false;

    private CacheManger() {
        if (disabled) {
            return;
        }
        mcc = new MemCachedClient();
        String[] servers = {"10.31.160.97:11211"};
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.initialize();

    }

    public static CacheManger getInstance() {
        if (instance == null) {
            instance = new CacheManger();
        }
        return instance;
    }

    public Book getBook(Long bookId) {
        if (disabled || bookId == null) {
            return null;
        }
        Object get = mcc.get("Book:" + bookId);
        if (get == null) {
            return null;
        } else {
            log("Cached Book=" + bookId);
            return (Book) get;
        }
    }

    public void setBook(Book book) {
        if (disabled || book == null) {
            return;
        }
        boolean set = mcc.set("Book:" + book.getId(), book);
        log("Cache Book=" + book.getId() + " result=" + set);
    }

    public void deleteBook(Book book) {
        if (disabled || book == null) {
            return;
        }
        boolean delete = mcc.delete("Book:" + book.getId());
        log("Cache Delete Book=" + book.getId() + " result=" + delete);
    }

    public Customer getCustomer(Long id) {
        if (disabled || id == null) {
            return null;
        }
        Customer customer = (Customer) mcc.get("Customer:" + id);
        if (customer != null) {
            log("Cached Customer:" + id);
        }
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (disabled || customer == null) {
            return;
        }
        boolean set = mcc.set("Customer:" + customer.getId(), customer);
        log("Cache Customer=" + customer.getId() + " result=" + set);
    }

    public void deleteCustomer(Customer customer) {
        if (disabled || customer == null) {
            return;
        }
        boolean delete = mcc.delete("Customer:" + customer.getId());
        log("Cache Delete Customer=" + customer.getId() + " result=" + delete);
    }

    public BookCollection getBookCollection(Book book, Customer customer) {
        if (disabled || book == null || customer == null) {
            return null;
        }
        String cacheKey = "BookCollection:Book=" + book.getId() + "Customer" + customer.getId();
        BookCollection collection = (BookCollection) mcc.get(cacheKey);
        if (collection != null) {
            log("Cached " + cacheKey);
        }
        return collection;
    }

    public void setBookCollection(Book book, Customer customer, BookCollection collection) {
        if (disabled || collection == null || book == null || customer == null) {
            return;
        }
        String cacheKey = "BookCollection:Book=" + book.getId() + "Customer" + customer.getId();
        boolean set = mcc.set(cacheKey, collection);
        log("Cache BookCollection=" + cacheKey + " result=" + set);
    }

    public void deleteBookCollection(Book book, Customer customer) {
        if (disabled || book == null || customer == null) {
            return;
        }
        String cacheKey = "BookCollection:Book=" + book.getId() + "Customer" + customer.getId();
        boolean delete = mcc.delete(cacheKey);
        log("Cache Delete " + cacheKey + " result=" + delete);
    }

    private void log(String msg) {
        if (debug) {
            System.out.println(msg);
        }
    }
}
