package com.example.a2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a2048.Scores;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>{
    class ScoreViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView score;
        public ScoreViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            score = (TextView) itemView.findViewById(R.id.score);
        }
    }

    private final LayoutInflater mInflater;
    DataBase db;
    Context mContext;
    String game;

    public ScoreAdapter(Context context, DataBase db, String game) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.game = game;
        this.db = db;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.playerscore, parent, false);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        final ScoreViewHolder h = holder;
        Scores current = db.query(position, game);
        holder.name.setText(current.getName());
        holder.score.setText(current.getScore());
    }

    @Override
    public int getItemCount() {
        return (int) db.count(game);
    }
}