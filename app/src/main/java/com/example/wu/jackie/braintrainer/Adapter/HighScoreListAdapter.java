package com.example.wu.jackie.braintrainer.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wu.jackie.braintrainer.R;
import com.example.wu.jackie.braintrainer.db.HighScore;

import java.util.List;

public class HighScoreListAdapter extends RecyclerView.Adapter<HighScoreListAdapter.HighScoreViewHolder> {


    class HighScoreViewHolder extends RecyclerView.ViewHolder{
        private final TextView playerNameItemView;
        private final TextView scoreItemView;
        private final TextView percentItemView;

        private HighScoreViewHolder(View itemView){
            super(itemView);
            playerNameItemView = itemView.findViewById(R.id.playerNameItemView);
            scoreItemView = itemView.findViewById(R.id.playerScoreItemView);
            percentItemView = itemView.findViewById(R.id.playerPercentCorrectItemView);

        }
    }

    private final LayoutInflater mInflater;
    private List<HighScore> mHighScores;

    public HighScoreListAdapter(Context context){mInflater = LayoutInflater.from(context);}


    @NonNull
    @Override
    public HighScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.high_score_recycleview_item, parent, false);
        return new HighScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreViewHolder holder, int position) {
        if(mHighScores != null){
            HighScore current = mHighScores.get(position);
            holder.playerNameItemView.setText(current.getPlayerName());
            holder.scoreItemView.setText(current.getPlayerScore());
            holder.percentItemView.setText(current.getPercentQuestionsCorrect());

        }else{
            String noHighScore = "No high score yet!";
            holder.playerNameItemView.setText(noHighScore);
            holder.scoreItemView.setText("0");
            holder.percentItemView.setText("0");
        }
    }

    public void setHighScores(List<HighScore> highScores){
        mHighScores = highScores;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mHighScores != null)
            return mHighScores.size();
        else return 0;
    }

}
