package edu.rose_hulman.miskowbs.photorecommendationapp.models;

import java.util.List;

/**
 * Created by miskowbs on 12/27/2017.
 */

public class Search {
    private String imageUrl;
    private List<String> tags;

    public Search(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Search(String imageUrl, List<String> tags) {

        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
