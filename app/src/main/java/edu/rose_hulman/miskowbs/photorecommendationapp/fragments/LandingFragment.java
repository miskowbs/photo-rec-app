package edu.rose_hulman.miskowbs.photorecommendationapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import edu.rose_hulman.miskowbs.photorecommendationapp.adapters.SearchAdapter;
import edu.rose_hulman.miskowbs.photorecommendationapp.models.Search;


/**
 * Created by miskowbs on 12/10/2017.
 */

public class LandingFragment extends Fragment
        implements Toolbar.OnMenuItemClickListener, SearchAdapter.Callback {

    private DatabaseReference mSearchesRef;
    private OnLogoutListener mListener;
    private OnIntentsListener mIntentsListener;
    private SearchAdapter mAdapter;
    private FirebaseAuth mAuth;
    private String mUid;

    public  LandingFragment() {
        //Required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchesRef = FirebaseDatabase.getInstance().getReference().child("searches");
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

        mAdapter = new SearchAdapter(this, mSearchesRef);
        RecyclerView searchList = rootView.findViewById(R.id.searches_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        searchList.setLayoutManager(manager);
        searchList.setAdapter(mAdapter);
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
            throw new ClassCastException(context.toString()
                    + " must implement OnIntentsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mIntentsListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSearchesRef.removeEventListener(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        attachSearchList();
    }

    private void attachSearchList() {
        mAdapter.clear();
        mSearchesRef.addChildEventListener(mAdapter);
    }

    @Override
    public void onViewImg(Search search) {
        //TODO: Show the image
    }

    @Override
    public void onShowResults(Search search) {
        //TODO: Image search intent using the tags in the search
    }

    public interface OnIntentsListener {
        void takePhotoIntent();
        void getGalleryPicsIntent();
    }

    public interface OnLogoutListener {
        void onLogout();
    }
}
