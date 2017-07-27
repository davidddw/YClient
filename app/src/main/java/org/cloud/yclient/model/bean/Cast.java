package org.cloud.yclient.model.bean;

/**
 * @author d05660ddw
 * @version 1.0 2016/9/22
 */

public class Cast {
    private String id;
    private String name;
    private String alt;
    private Avatar avatars;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Avatar getAvatars() {
        return avatars;
    }

    public void setAvatars(Avatar avatars) {
        this.avatars = avatars;
    }

    @Override
    public String toString() {
        return name + ":" + avatars;
    }
}
