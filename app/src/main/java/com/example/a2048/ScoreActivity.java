package com.example.a2048;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    private List<Scores> mScoresList;
    private List<Scores> mScoresList2;
    private ScoreAdapter mScoreAdapter;
    private ScoreAdapter mScoreAdapter2;
    private RecyclerView mScoresRecyclerView;
    private RecyclerView mScoresRecyclerView2;
    private DataBase mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_layout);

        mDbHelper = new DataBase(this);

        mScoresList = mDbHelper.getAllScores("Score2048");

        mScoresList2 = mDbHelper.getAllScores("ScoreLightsOut");

        mScoreAdapter = new ScoreAdapter(this, mDbHelper,"Score2048");

        mScoreAdapter2 = new ScoreAdapter(this, mDbHelper,"ScoreLightsOut");



        mScoresRecyclerView = findViewById(R.id.ScoreView);
        mScoresRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mScoresRecyclerView.setAdapter(mScoreAdapter);

        mScoresRecyclerView2 = findViewById(R.id.ScoreView2);
        mScoresRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        mScoresRecyclerView2.setAdapter(mScoreAdapter2);

    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }
}