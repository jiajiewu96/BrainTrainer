package com.example.wu.jackie.braintrainer.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wu.jackie.braintrainer.R;
import com.example.wu.jackie.braintrainer.databinding.HighScoreRecycleviewItemBinding;
import com.example.wu.jackie.braintrainer.db.HighScoreEntity;
import com.example.wu.jackie.braintrainer.model.HighScoreModel;

import java.util.List;

public class HighScoreListAdapter extends RecyclerView.Adapter<HighScoreListAdapter.HighScoreViewHolder> {



    class HighScoreViewHolder extends RecyclerView.ViewHolder{

        final HighScoreRecycleviewItemBinding binding;

        private HighScoreViewHolder(HighScoreRecycleviewItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    private final LayoutInflater mInflater;
    List<? extends HighScoreModel> mHighScore;

    public HighScoreListAdapter(Context context){mInflater = LayoutInflater.from(context);}

    public void setHighScores(List<HighScoreEntity> highScores){

        mHighScore = highScores;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HighScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HighScoreRecycleviewItemBinding binding = HighScoreRecycleviewItemBinding.inflate(mInflater, parent, false);
        return new HighScoreViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreViewHolder holder, int position) {
        holder.binding.setHighScore(mHighScore.get(position));
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        if(mHighScore != null)
            return mHighScore.size();
        else return 0;
    }

}
