package edu.rose_hulman.miskowbs.photorecommendationapp.models;

import android.graphics.Bitmap;

/**
 * Created by miskowbs on 12/27/2017.
 */

public class Pic {

    private Search search;
    private Bitmap bitmap;

    public Pic() {

    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Pic(Search search) {
        this.search = search;
    }
}