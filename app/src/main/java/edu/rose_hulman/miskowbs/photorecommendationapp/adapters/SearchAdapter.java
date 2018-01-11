package edu.rose_hulman.miskowbs.photorecommendationapp.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.miskowbs.photorecommendationapp.R;
import edu.rose_hulman.miskowbs.photorecommendationapp.models.BitmapAndPosition;
import edu.rose_hulman.miskowbs.photorecommendationapp.models.Pic;
import edu.rose_hulman.miskowbs.photorecommendationapp.models.Search;
import edu.rose_hulman.miskowbs.photorecommendationapp.tasks.GetImageAndPositionTask;

/**
 * Created by miskowbs on 12/27/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements ChildEventListener, GetImageAndPositionTask.ImageConsumer{

    private Callback mCallback;
    private DatabaseReference mSearchesRef;
    private List<Pic> mPics;

    public SearchAdapter(Callback cb, DatabaseReference firebaseRef) {
        mCallback = cb;
        mPics = new ArrayList<>();
        mSearchesRef = firebaseRef;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_box_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        final Pic pic = mPics.get(position);
        holder.mUrlView.setImageBitmap(pic.getBitmap());

        StringBuilder tagsTextBuilder = new StringBuilder();
        for(String s : pic.getSearch().getTagsAsList()) {
            tagsTextBuilder.append(s + ", ");
        }

        int length = tagsTextBuilder.length();
        if(length > 2) {
            tagsTextBuilder.delete(length - 2, length);
        }

        holder.mTagsView.setText(tagsTextBuilder.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onViewImg(pic.getSearch());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mCallback.onShowResults(pic.getSearch());
                return false;
            }
        });
    }

    public void clear() {
        mPics.clear();
    }

    public int getItemCount() {
        return mPics.size();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Search search = dataSnapshot.getValue(Search.class);
        search.setKey(dataSnapshot.getKey());
        Pic newPic = new Pic();
        newPic.setSearch(search);
        newPic.setBitmap(Bitmap.createBitmap(92, 92, Bitmap.Config.ARGB_8888));
        mPics.add(newPic);
        new GetImageAndPositionTask(this, mPics.size() - 1).execute(newPic.getSearch().getUrl());
        notifyDataSetChanged();

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        Search updatedSearch = dataSnapshot.getValue(Search.class);
        for(int i = 0; i < mPics.size(); i++) {
            Search search = mPics.get(i).getSearch();
            if(search.getKey().equals(key)) {
                search.setValues(updatedSearch);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        for(int i = 0; i < mPics.size(); i++) {
            Search s = mPics.get(i).getSearch();
            if(s.getKey().equals(key)) {
                mPics.remove(i);
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e("TAG", "Database Error" + databaseError);
    }

    @Override
    public void onImageLoaded(BitmapAndPosition bitmap) {
        Log.d("IMAGE", "Image presented");
        mPics.get(bitmap.getPosition()).setBitmap(bitmap.getBitmap());
        notifyDataSetChanged();
    }

    public interface Callback {
        public void onViewImg(Search search);

        public void onShowResults(Search search);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mUrlView;
        private TextView mTagsView;

        public ViewHolder(View itemView) {
            super(itemView);
            mUrlView = (ImageView) itemView.findViewById(R.id.thumbnail_view);
            mTagsView = (TextView) itemView.findViewById(R.id.tags_text);
        }
    }
}
