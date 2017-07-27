package org.cloud.yclient.model.bean;

import java.util.List;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/22
 */

public class NovelInfoBean {
    private String id;
    private String name;
    private String author;
    private String description;
    private String type;
    private String cover;
    private List<NovelChapterBean> latestChapter;

    public List<NovelChapterBean> getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(List<NovelChapterBean> latestChapter) {
        this.latestChapter = latestChapter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "NovelInfoBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
