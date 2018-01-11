package edu.rose_hulman.miskowbs.photorecommendationapp.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.rose_hulman.miskowbs.photorecommendationapp.R;
import edu.rose_hulman.miskowbs.photorecommendationapp.models.Pic;
import edu.rose_hulman.miskowbs.photorecommendationapp.models.Search;
import edu.rose_hulman.miskowbs.photorecommendationapp.tasks.GetImageTask;

/**
 * Created by miskowbs on 12/27/2017.
 */

public class ImageFragment extends Fragment implements GetImageTask.ImageConsumer {

    private static final String ARG_SEARCH = "search";

    private Pic mPic;
    private ImageView mImageView;

    public ImageFragment() {
        //Req empty class
    }

    public static ImageFragment getInstance(Search search) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SEARCH, search);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPic = new Pic((Search) getArguments().getParcelable(ARG_SEARCH));
            new GetImageTask(this).execute(mPic.getSearch().getUrl());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        mImageView = view.findViewById(R.id.image_view);
        mImageView.setImageBitmap(mPic.getBitmap());

        return view;
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        Log.d("IMAGE", "Image presented");
        mPic.setBitmap(bitmap);
        mImageView.setImageBitmap(bitmap);
    }
}
