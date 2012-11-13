/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.service;

import com.hudoumiao.entity.Book;
import com.hudoumiao.entity.BookCollection;
import com.hudoumiao.entity.CollectionTag;
import com.hudoumiao.entity.Customer;
import com.hudoumiao.entity.Tag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author HUXIAOFENG
 */
@Stateless
public class DaoBean {

    @EJB
    private DaoTxBean daoTxBean;
    @PersistenceContext(unitName = "HuDouMiaoPU")
    private EntityManager em;

    public Book findBook(Long bookId) {
        Book book;
        try {
            TypedQuery<Book> query = em.createNamedQuery("Book.findById", Book.class);
            query.setParameter("id", bookId);
            book = query.getSingleResult();
        } catch (NoResultException ex) {
            book = null;
        }
        return book;
    }

    public List<Book> findRelatedBooks(Book book) {
        if (book == null) {
            return null;
        }
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.id > :x AND b.id < :y AND b.id <> :id ORDER BY b.id", Book.class);
        query.setMaxResults(6);
        query.setParameter("id", book.getId());
        query.setParameter("x", book.getId() - 4);
        query.setParameter("y", book.getId() + 4);
        return query.getResultList();
    }

    public List<BookCollection> findRecentCollections(Book book) {
        if (book == null) {
            return null;
        }
        TypedQuery<BookCollection> query = em.createQuery("SELECT c FROM BookCollection c WHERE c.book=:book ORDER BY c.id DESC", BookCollection.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    public int findCollectionTotolCount(Book book) {
        String countQuery = "SELECT COUNT(c) FROM BookCollection c WHERE c.book=:book";
        Long totalCount = (Long) em.createQuery(countQuery).setParameter("book", book).getSingleResult();
        return totalCount.intValue();
    }

    public int findCollectionScoreCount(Book book, int score) {
        String countQuery = "SELECT COUNT(c) FROM BookCollection c WHERE c.book=:book AND c.score=:score";
        Long totalCount = (Long) em.createQuery(countQuery).setParameter("book", book).setParameter("score", score).getSingleResult();
        return totalCount.intValue();
    }

    public List<Tag> findHotTags(Book book) {
        Query query = em.createNativeQuery("SELECT t.name as name, COUNT(t.id) as tcount FROM  tag t JOIN collection_tag ct on (ct.tag_id = t.id) JOIN book_collection bc on (bc.id=ct.collection_id) JOIN book b on (b.id=bc.book_id) WHERE b.id=?1 GROUP BY t.id ORDER BY tcount desc LIMIT 5");
        query.setParameter(1, book.getId());
        List list = query.getResultList();
        List<Tag> tagList = new LinkedList();
        for (int i = 0; i < list.size(); i++) {
            Object[] row = (Object[]) list.get(i);
            String name = (String) row[0];
            Long count = (Long) row[1];
            Tag tag = new Tag(name, count);
            tagList.add(tag);
        }
        return tagList;
    }

    public Customer findCustomer(String name) {
        Customer customer;
        try {
            TypedQuery<Customer> query = em.createNamedQuery("Customer.findByName", Customer.class);
            query.setParameter("name", name);
            List<Customer> resultList = query.getResultList();
            if (resultList.size() > 0) {
                customer = resultList.get(0);
            } else {
                customer = null;
            }
        } catch (NoResultException ex) {
            customer = null;
        }
        return customer;
    }

    public Customer findCustomer(Long customerId) {
        Customer customer;
        try {
            TypedQuery<Customer> query = em.createNamedQuery("Customer.findById", Customer.class);
            query.setParameter("id", customerId);
            customer = query.getSingleResult();
        } catch (NoResultException ex) {
            customer = null;
        }
        return customer;
    }

    public BookCollection findCollection(Book book, Customer customer) {
        BookCollection bookCollection;
        try {
            TypedQuery<BookCollection> query = em.createQuery("SELECT bc FROM BookCollection bc WHERE bc.book=:book AND bc.customer=:customer", BookCollection.class);
            query.setParameter("book", book);
            query.setParameter("customer", customer);
            List<BookCollection> resultList = query.getResultList();
            if (resultList.size() > 0) {
                bookCollection = resultList.get(0);
            } else {
                bookCollection = null;
            }
        } catch (NoResultException ex) {
            bookCollection = null;
        }
        return bookCollection;
    }

    public String findTagNamesByBookCollection(BookCollection collection) {
        try {
            TypedQuery<CollectionTag> query = em.createQuery("SELECT t FROM CollectionTag t WHERE t.collection=:collection", CollectionTag.class);
            query.setParameter("collection", collection);
            List<CollectionTag> resultList = query.getResultList();
            if (resultList.size() <= 0) {
                return null;
            }
            List names = new LinkedList();
            for (int i = 0; i < resultList.size(); i++) {
                CollectionTag collectionTag = resultList.get(i);
                String name = collectionTag.getTag().getName();
                names.add(name);
            }
            return StringUtils.join(names, " ");
        } catch (NoResultException ex) {
            return null;
        }
    }

    public void saveCollectionTag(BookCollection collection, String tags) {
        String[] split = StringUtils.split(tags);
        if (split == null || split.length == 0) {
            return;
        }
        Set set = new HashSet(Arrays.asList(split)); // unique tag
        for (Iterator it = set.iterator(); it.hasNext();) {
            String name = (String) it.next();
            Tag tag = getTagWithCreation(name);
            saveCollectionTag(collection, tag);
        }
    }

    public BookCollection saveCollection(Book book, Customer customer, int score, String comment) {
        BookCollection bookCollection = findCollection(book, customer);
        if (bookCollection == null) {
            bookCollection = new BookCollection(score, comment, book, customer);
            em.persist(bookCollection);
        } else {
            bookCollection.setScore(score);
            bookCollection.setComment(comment);
            em.merge(bookCollection);
        }
        return bookCollection;
    }

    public void removeCollectionTags(BookCollection collection) {
        try {
            Query query = em.createQuery("DELETE FROM CollectionTag ct WHERE ct.collection=:collection");
            query.setParameter("collection", collection);
            query.executeUpdate();
        } catch (Exception ex) {
        }
    }

    public void removeCollection(BookCollection collection) {
        em.remove(collection);
    }

    /*
     ********************************************************************
     */
    //
    private Tag findTag(String name) {
        Tag tag;
        try {
            TypedQuery<Tag> query = em.createNamedQuery("Tag.findByName", Tag.class);
            query.setParameter("name", name);
            tag = query.getSingleResult();
        } catch (NoResultException ex) {
            tag = null;
        }
        return tag;
    }

    private Tag getTagWithCreation(String name) {
        Tag tag = findTag(name);
        if (tag == null) {
            return daoTxBean.createTag(name);
        }
        return tag;
    }

    private CollectionTag saveCollectionTag(BookCollection collection, Tag tag) {
        CollectionTag collectionTag = findCollectionTag(collection, tag);
        if (collectionTag == null) {
            collectionTag = new CollectionTag(tag, collection);
            em.persist(collectionTag);
        }
        return collectionTag;
    }

    private CollectionTag findCollectionTag(BookCollection collection, Tag tag) {
        CollectionTag collectionTag;
        try {
            TypedQuery<CollectionTag> query = em.createQuery("SELECT bc FROM CollectionTag bc WHERE bc.collection=:collection AND bc.tag=:tag", CollectionTag.class);
            query.setParameter("collection", collection);
            query.setParameter("tag", tag);
            collectionTag = query.getSingleResult();
        } catch (NoResultException ex) {
            collectionTag = null;
        }
        return collectionTag;
    }
}
