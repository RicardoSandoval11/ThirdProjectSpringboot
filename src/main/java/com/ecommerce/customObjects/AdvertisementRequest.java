package com.ecommerce.customObjects;

import org.springframework.web.multipart.MultipartFile;

public class AdvertisementRequest {

    private Integer id;
    private String title;
    private String subtitle;
    private String content;
    private MultipartFile image;

    
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
    public MultipartFile getImage() {
        return image;
    }
    public void setImage(MultipartFile image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Advertisement [id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", content=" + content
                + ", image=" + image + "]";
    }
    

}
