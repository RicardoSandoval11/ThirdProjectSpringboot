package com.ecommerce.models;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "advertisements", schema = "ecommercedb")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String subtitle;
    private String content;
    private String image;
    private Date createdAt;

    public Advertisement(){}
    
    public Advertisement(
                @Qualifier("id") Integer id, 
                @Qualifier("title") String title, 
                @Qualifier("subtitle") String subtitle, 
                @Qualifier("content") String content, 
                @Qualifier("image") String image, 
                @Qualifier("createdAt") Date createdAt) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Advertisement [id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", content=" + content
                + ", image=" + image + ", createdAt=" + createdAt + "]";
    }
    
}
