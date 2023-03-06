package com.example.a2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameCellView extends View {
    private GameCell cell;


    public GameCellView(Context context) {
        super(context);

    }

    public GameCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCell(GameCell cell) {
        this.cell = cell;
    }



}