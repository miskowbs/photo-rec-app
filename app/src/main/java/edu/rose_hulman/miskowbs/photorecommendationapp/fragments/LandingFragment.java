package edu.rose_hulman.miskowbs.photorecommendationapp.fragments;

import android.content.Context;
import android.content.Intent;
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

import edu.rose_hulman.miskowbs.photorecommendationapp.R;


/**
 * Created by miskowbs on 12/10/2017.
 */

public class LandingFragment extends Fragment
        implements Toolbar.OnMenuItemClickListener {

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_GALLERY_CAPTURE = 3;

    private DatabaseReference mPicsRef;
    private OnLogoutListener mListener;
    private OnIntentsListener mIntentsListener;
    private FirebaseAuth mAuth;
    private String mUid;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                Log.d("PIC", "In LandingFragment onActivityResult");
                //TODO: LandingFragment Redo based on Image tags
                break;
            case REQUEST_GALLERY_CAPTURE:

                //TODO: LandingFragment Redo based on Image tags
                break;
        }
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
                mIntentsListener.takePhotoIntent();
                return true;
            case R.id.action_photo_gallery:
                mIntentsListener.getGalleryPicsIntent();
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
        void takePhotoIntent();
        void getGalleryPicsIntent();
    }

    public interface OnLogoutListener {
        void onLogout();
    }
}
