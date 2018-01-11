package edu.rose_hulman.miskowbs.photorecommendationapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by miskowbs on 12/27/2017.
 */

public class Search implements Parcelable{
    private String url;
    private HashMap<String, String> tags;
    private String key;
    private String uid;

    public Search() {
        //Required empty constructor
    }

    public Search(String url) {
        this.url = url;
    }

    public Search(String url, HashMap<String, String> tags) {

        this.url = url;
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public List<String> getTagsAsList() {
        ArrayList<String> tagList = new ArrayList<>();
        try {
            for (String t : this.tags.values()) {
                tagList.add(t);
            }
        } catch (NullPointerException e) {
            Log.e("TAG", e.getMessage());
        }
        return tagList;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValues(Search s) {
        this.url = s.url;
        this.tags = s.tags;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeMap(tags);
    }
}
