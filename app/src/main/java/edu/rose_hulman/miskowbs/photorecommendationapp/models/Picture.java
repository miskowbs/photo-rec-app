package edu.rose_hulman.miskowbs.photorecommendationapp.models;

import java.util.Collection;

/**
 * Created by truepose on 12/4/2017.
 */

public class Picture {
    private Collection<String> tags;
    private String path;

    public Picture(String path) {
        this.path = path;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
