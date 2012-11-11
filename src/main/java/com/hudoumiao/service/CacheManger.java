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

    private CacheManger() {
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
            //System.out.println("Cached Book=" + bookId);
            return (Book) get;
        }
    }

    public void setBook(Book book) {
        if (disabled || book == null) {
            return;
        }
        boolean set = mcc.set("Book:" + book.getId(), book);
        // System.out.println("Cache Book=" + book.getId() + " result=" + set);
    }

    public void deleteBook(Book book) {
        if (disabled || book == null) {
            return;
        }
        boolean delete = mcc.delete("Book:" + book.getId());
        //System.out.println("Cache Delete Book=" + book.getId() + " result=" + delete);
    }

    public Customer getCustomer(Long id) {
        if (disabled || id == null) {
            return null;
        }
        Customer customer = (Customer) mcc.get("Customer:" + id);
        if (customer != null) {
            //System.out.println("Cached Customer:" + id);
        }
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (disabled || customer == null) {
            return;
        }
        boolean set = mcc.set("Customer:" + customer.getId(), customer);
        // System.out.println("Cache Customer=" + customer.getId() + " result=" + set);
    }

    public void deleteCustomer(Customer customer) {
        if (disabled || customer == null) {
            return;
        }
        boolean delete = mcc.delete("Customer:" + customer.getId());
        //System.out.println("Cache Delete Customer=" + customer.getId() + " result=" + delete);
    }

    public BookCollection getBookCollection(Book book, Customer customer) {
        if (disabled || book == null || customer == null) {
            return null;
        }
        String cacheKey = "BookCollection:Book=" + book.getId() + "Customer" + customer.getId();
        BookCollection collection = (BookCollection) mcc.get(cacheKey);
        if (collection != null) {
            //System.out.println("Cached " + cacheKey);
        }
        return collection;
    }

    public void setBookCollection(Book book, Customer customer, BookCollection collection) {
        if (disabled || collection == null || book == null || customer == null) {
            return;
        }
        String cacheKey = "BookCollection:Book=" + book.getId() + "Customer" + customer.getId();
        boolean set = mcc.set(cacheKey, collection);
        //System.out.println("Cache BookCollection=" + cacheKey + " result=" + set);
    }

    public void deleteBookCollection(Book book, Customer customer) {
        if (disabled || book == null || customer == null) {
            return;
        }
        String cacheKey = "BookCollection:Book=" + book.getId() + "Customer" + customer.getId();
        boolean delete = mcc.delete(cacheKey);
        //System.out.println("Cache Delete " + cacheKey + " result=" + delete);
    }
}
