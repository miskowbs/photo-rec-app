package edu.rose_hulman.miskowbs.photorecommendationapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;
import java.util.UUID;

import edu.rose_hulman.miskowbs.photorecommendationapp.R;


/**
 * Created by miskowbs on 12/10/2017.
 */

public class LandingFragment extends Fragment
        implements Toolbar.OnMenuItemClickListener {

    private DatabaseReference mPicsRef;
    private OnLogoutListener mListener;
    private OnIntentsListener mIntentsListener;
    private FirebaseAuth mAuth;
    private String mUid;
    private Collection<Bitmap> mCurrentImages;

    public  LandingFragment() {
        //Required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicsRef = FirebaseDatabase.getInstance().getReference().child("users");
        //Note path isn't finalized yet
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_landing, container, false);
        Toolbar mToolbar = rootView.findViewById(R.id.toolbar);
        mToolbar.setTitle("Photo Recommendations");
        getActivity().getMenuInflater().inflate(R.menu.menu_main, mToolbar.getMenu());
        mToolbar.setOnMenuItemClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUid = mAuth.getCurrentUser().getUid();

        //TODO: add adapter here?

        return rootView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.action_logout:
                Log.d("PK", "LOGOUT Menu Item Clicked!");
                mListener.onLogout();
                return true;
            case R.id.action_take_image:
                String imagePath = mIntentsListener.takePhotoIntent();
                return true;
            case R.id.action_photo_gallery:
                Collection<String> imagePaths = mIntentsListener.getGalleryPicsIntent();
                return true;
        }
        return false;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (OnLogoutListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnLogoutListener");
        }

        try {
            mIntentsListener = (OnIntentsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnIntentsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mIntentsListener = null;
    }

    public interface OnIntentsListener {
        String takePhotoIntent();
        Collection<String> getGalleryPicsIntent();
    }

    public interface OnLogoutListener {
        void onLogout();
    }
}
