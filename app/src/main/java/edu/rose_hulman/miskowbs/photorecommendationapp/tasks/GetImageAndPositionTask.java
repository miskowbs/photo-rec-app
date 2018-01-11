package edu.rose_hulman.miskowbs.photorecommendationapp.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import edu.rose_hulman.miskowbs.photorecommendationapp.models.BitmapAndPosition;

/**
 * Created by miskowbs on 1/9/2018.
 */

public class GetImageAndPositionTask extends AsyncTask<String, Void, BitmapAndPosition> {
    private ImageConsumer mImageConsumer;
    private int mAdapterPosition;


    public GetImageAndPositionTask(ImageConsumer activity, int adapterPosition) {
        mImageConsumer = activity;
        mAdapterPosition = adapterPosition;
    }

    @Override
    protected BitmapAndPosition doInBackground(String... strings) {
        String urlString = strings[0];
        InputStream in;
        Bitmap bitmap = null;
        try {
            in = new URL(urlString).openStream();
            BitmapFactory.Options bmpBuffer = new BitmapFactory.Options();
            bmpBuffer.inSampleSize = 32;
//            BitmapFactory.decodeStream(in, null, bmpBuffer);

//            bmpBuffer.inSampleSize = calculateInSampleSize(bmpBuffer, 92, 92);
//            bmpBuffer.inJustDecodeBounds = false;

            bitmap = BitmapFactory.decodeStream(in, null, bmpBuffer);
        } catch (IOException e) {
            Log.e("INPUT_STREAM", e.toString());
        }
        return new BitmapAndPosition(bitmap, mAdapterPosition);
    }

    @Override
    protected void onPostExecute(BitmapAndPosition bitmap) {
        super.onPostExecute(bitmap);
        mImageConsumer.onImageLoaded(bitmap);
    }

    public interface ImageConsumer {
        void onImageLoaded(BitmapAndPosition bitmap);
    }
}

