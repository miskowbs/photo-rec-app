package edu.rose_hulman.miskowbs.photorecommendationapp.models;

import android.graphics.Bitmap;

/**
 * Created by miskowbs on 1/9/2018.
 */

public class BitmapAndPosition {
    private Bitmap bitmap;

    private int position;

    public BitmapAndPosition(Bitmap bitmap, int position) {
        this.bitmap = bitmap;
        this.position = position;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getPosition() {
        return position;
    }
}
