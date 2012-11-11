/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hudoumiao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author HUXIAOFENG
 */
@Entity
@Table(name = "book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :id")})
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Version
    private int version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Transient
    private int collectionCount;
    @Transient
    private int score5Count;
    @Transient
    private int score4Count;
    @Transient
    private int score3Count;
    @Transient
    private int score2Count;
    @Transient
    private int score1Count;
    @Transient
    private List<BookCollection> recentCollectionList;
    @Transient
    private List<Tag> hotTagList;
    @Transient
    private List<Book> relatedBooks;

    public Book() {
        this.title = "foo";
    }

    public Book(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    protected int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    protected void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getScore5Count() {
        return score5Count;
    }

    public void setScore5Count(int score5Count) {
        this.score5Count = score5Count;
    }

    public int getScore4Count() {
        return score4Count;
    }

    public void setScore4Count(int score4Count) {
        this.score4Count = score4Count;
    }

    public int getScore3Count() {
        return score3Count;
    }

    public void setScore3Count(int score3Count) {
        this.score3Count = score3Count;
    }

    public int getScore2Count() {
        return score2Count;
    }

    public void setScore2Count(int score2Count) {
        this.score2Count = score2Count;
    }

    public int getScore1Count() {
        return score1Count;
    }

    public void setScore1Count(int score1Count) {
        this.score1Count = score1Count;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Book)) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, object);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @return the recentCollectionList
     */
    public List<BookCollection> getRecentCollectionList() {
        return recentCollectionList;
    }

    /**
     * @param recentCollectionList the recentCollectionList to set
     */
    public void setRecentCollectionList(List<BookCollection> recentCollectionList) {
        this.recentCollectionList = recentCollectionList;
    }

    /**
     * @return the hotTagList
     */
    public List<Tag> getHotTagList() {
        return hotTagList;
    }

    /**
     * @param hotTagList the hotTagList to set
     */
    public void setHotTagList(List<Tag> hotTagList) {
        this.hotTagList = hotTagList;
    }

    /**
     * @return the relatedBooks
     */
    public List<Book> getRelatedBooks() {
        return relatedBooks;
    }

    /**
     * @param relatedBooks the relatedBooks to set
     */
    public void setRelatedBooks(List<Book> relatedBooks) {
        this.relatedBooks = relatedBooks;
    }
}
