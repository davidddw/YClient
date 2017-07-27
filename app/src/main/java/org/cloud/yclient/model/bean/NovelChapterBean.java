package org.cloud.yclient.model.bean;

/**
 * @author d05660ddw
 * @version 1.0 2017/7/24
 */

public class NovelChapterBean {
    private String id;
    private String wid;
    private String title;
    private String location;
    private String novelId;

    public NovelChapterBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNovelId() {
        return novelId;
    }

    public void setNovelId(String novelId) {
        this.novelId = novelId;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "NovelChapterBean{" +
                "id='" + id + '\'' +
                ", wid='" + wid + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", novelId='" + novelId + '\'' +
                '}';
    }
}
