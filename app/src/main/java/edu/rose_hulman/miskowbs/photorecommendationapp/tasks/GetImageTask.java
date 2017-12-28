package edu.rose_hulman.miskowbs.photorecommendationapp.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by miskowbs on 12/27/2017.
 */

public class GetImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageConsumer mImageConsumer;

    public GetImageTask(ImageConsumer activity) { mImageConsumer = activity; }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlString = strings[0];
        InputStream in;
        Bitmap bitmap = null;
        try {
            in = new URL(urlString).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.e("INPUT_STREAM", e.toString());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mImageConsumer.onImageLoaded(bitmap);
    }

    public interface ImageConsumer {
        void onImageLoaded(Bitmap bitmap);
    }
}
