package edu.rose_hulman.miskowbs.photorecommendationapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.miskowbs.photorecommendationapp.R;
import edu.rose_hulman.miskowbs.photorecommendationapp.models.Search;

/**
 * Created by miskowbs on 12/27/2017.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements ChildEventListener{

    private List<Search> mSearches;
    private Callback mCallback;
    private DatabaseReference mSearchesRef;

    public SearchAdapter(Callback cb, DatabaseReference firebaseRef) {
        mCallback = cb;
        mSearches = new ArrayList<>();
        mSearchesRef = firebaseRef;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_box_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        final Search search = mSearches.get(position);
        holder.mUrlView.setText(search.getKey());

        StringBuilder tagsTextBuilder = new StringBuilder();
        for(String s : search.getTagsAsList()) {
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
                mCallback.onViewImg(search);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mCallback.onShowResults(search);
                return false;
            }
        });
    }

    public void clear() {
        mSearches.clear();
    }

    public int getItemCount() {
        return mSearches.size();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Search search = dataSnapshot.getValue(Search.class);
        search.setKey(dataSnapshot.getKey());
        mSearches.add(0, search);
        notifyDataSetChanged();

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        Search updatedSearch = dataSnapshot.getValue(Search.class);
        for(Search search : mSearches) {
            if(search.getKey().equals(key)) {
                search.setValues(updatedSearch);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        for(Search s : mSearches) {
            if(s.getKey().equals(key)) {
                mSearches.remove(s);
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

    public interface Callback {
        public void onViewImg(Search search);

        public void onShowResults(Search search);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUrlView;
        private TextView mTagsView;

        public ViewHolder(View itemView) {
            super(itemView);
            mUrlView = (TextView) itemView.findViewById(R.id.search_text);
            mTagsView = (TextView) itemView.findViewById(R.id.tags_text);
        }
    }
}
