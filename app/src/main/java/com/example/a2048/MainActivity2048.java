package com.example.a2048;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2048 extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private GameCell[][] mCells = new GameCell[4][4];
    private GestureDetector mGestureDetector;
    private GameCellView[][] mCellViews = new GameCellView[4][4];
    private DataBase db;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2048);
        db = new DataBase(this);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mCellViews[i][j] = new GameCellView(this);
                mCellViews[i][j].invalidate();
            }
        }
        addRandomCell();
        addRandomCell();
        updateCellsBackground();
        mGestureDetector = new GestureDetector(this, this);

    }

    public void addRandomCell(){
        boolean full = true;
        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[0].length; j++) {
                if (mCells[i][j] == null) {
                    full = false;
                }
            }
        }
        if(full ==false) {
            GridLayout gameCells = findViewById(R.id.game_board);
            int i, j;
            boolean isFull = true;
            do {
                i = (int) (Math.random() * mCells.length);
                j = (int) (Math.random() * mCells[0].length);
                isFull = false;
            } while (mCells[i][j] != null);
            if (!isFull) {
                GameCell cell = new GameCell();
                mCells[i][j] = cell;
                mCellViews[i][j].setCell(cell);
                ;
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = getResources().getDimensionPixelSize(R.dimen.game_cell_size);
                params.height = getResources().getDimensionPixelSize(R.dimen.game_cell_size);
                params.setGravity(Gravity.CENTER);
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);


                if (mCellViews[i][j].getParent() != null) {
                    ((ViewGroup) mCellViews[i][j].getParent()).removeView(mCellViews[i][j]);
                }
                gameCells.addView(mCellViews[i][j], params);
                updateCellsBackground();
            }

        }

    }


    public void updateCellsBackground() {
        GridLayout gameCells = findViewById(R.id.game_board);
        for (int row = 0; row < gameCells.getRowCount(); row++) {
            for (int col = 0; col < gameCells.getColumnCount(); col++) {
                GameCellView cellView = (GameCellView) gameCells.getChildAt(row * gameCells.getColumnCount() + col);
                GameCell cell = mCells[row][col];

                int imageResource;
                if (cell != null) {
                    Log.d("Cell value", "Row: " + row + ", Col: " + col + ", Value: " + cell.getValue());

                    switch (cell.getValue()) {
                        case 2:
                            imageResource = R.drawable.casilla2;
                            break;
                        case 4:
                            imageResource = R.drawable.casilla4;
                            break;
                        case 8:
                            imageResource = R.drawable.casilla8;
                            break;
                        case 16:
                            imageResource = R.drawable.casilla16;
                            break;
                        case 32:
                            imageResource = R.drawable.casilla32;
                            break;
                        case 64:
                            imageResource = R.drawable.casilla64;
                            break;
                        case 128:
                            imageResource = R.drawable.casilla128;
                            break;
                        case 256:
                            imageResource = R.drawable.casilla256;
                            break;
                        case 512:
                            imageResource = R.drawable.casilla512;
                            break;
                        case 1024:
                            imageResource = R.drawable.casilla1024;
                            break;
                        case 2048:
                            imageResource = R.drawable.casilla2048;
                            break;
                        default:
                            imageResource = R.drawable.off;
                            break;
                    }
                } else {
                    imageResource = R.drawable.casilla_vacia;
                }

                cellView.setCell(cell);
                cellView.setBackgroundResource(imageResource);
            }
        }
        updateScore();
        win();
        loose();
    }





    public int moveUp() {
        int score = 0;
        boolean[][] merged = new boolean[mCells.length][mCells[0].length]; // matriz booleana para rastrear las celdas que ya se han fusionado

        for (int j = 0; j < mCells[0].length; j++) {
            for (int i = 1; i < mCells.length; i++) {
                if (mCells[i][j] != null) {
                    for (int k = i; k > 0; k--) {
                        if (mCells[k-1][j] == null) {
                            mCells[k-1][j] = mCells[k][j];
                            mCellViews[k-1][j].invalidate();
                            mCells[k][j] = null;

                            mCellViews[k][j].invalidate();
                            if (k > 1 && mCells[k-2][j] != null) {
                                mCellViews[k-2][j].invalidate();
                            }
                        } else if (mCells[k-1][j].getValue() == mCells[k][j].getValue() && !merged[k-1][j]) {
                            int mergedValue = mCells[k-1][j].getValue() * 2;
                            mCells[k-1][j].setValue(mergedValue);
                            score += mergedValue;
                            mCells[k][j] = null;
                            mCellViews[k-1][j].invalidate();
                            mCellViews[k][j].invalidate();
                            merged[k-1][j] = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
            merged = new boolean[mCells.length][mCells[0].length];
        }
        addRandomCell();
        updateCellsBackground();
        updateCellView();
        updateScore();
        return score;
    }

    public int moveLeft() {
        int score = 0;
        boolean[][] merged = new boolean[mCells.length][mCells[0].length]; // matriz booleana para rastrear las celdas que ya se han fusionado

        for (int i = 0; i < mCells.length; i++) {
            for (int j = 1; j < mCells[0].length; j++) {
                if (mCells[i][j] != null) {
                    for (int k = j; k > 0; k--) {
                        if (mCells[i][k-1] == null) {
                            mCells[i][k-1] = mCells[i][k];
                            mCellViews[i][k-1].invalidate();
                            mCells[i][k] = null;

                            mCellViews[i][k].invalidate();
                            if (k < mCells[0].length-1 && mCells[i][k+1] != null) {
                                mCellViews[i][k+1].invalidate();
                            }
                        } else if (mCells[i][k-1].getValue() == mCells[i][k].getValue() && !merged[i][k-1]) {
                            int mergedValue = mCells[i][k-1].getValue() * 2;
                            mCells[i][k-1].setValue(mergedValue);
                            score += mergedValue;
                            mCells[i][k] = null;
                            mCellViews[i][k-1].invalidate();
                            mCellViews[i][k].invalidate();
                            merged[i][k-1] = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
            merged = new boolean[mCells.length][mCells[0].length];
        }
        addRandomCell();
        updateCellsBackground();
        updateCellView();
        updateScore();
        return score;
    }






    public int moveDown() {
        int score = 0;
        boolean[][] merged = new boolean[mCells.length][mCells[0].length];
        for (int j = 0; j < mCells[0].length; j++) {
            for (int i = mCells.length - 2; i >= 0; i--) {
                if (mCells[i][j] != null) {
                    for (int k = i; k < mCells.length - 1; k++) {
                        if (mCells[k+1][j] == null) {
                            mCells[k+1][j] = mCells[k][j];
                            mCellViews[k+1][j].invalidate();
                            mCells[k][j] = null;
                            mCellViews[k][j].invalidate();
                            if (k < mCells.length - 2 && mCells[k+2][j] != null) {
                                mCellViews[k+2][j].invalidate();
                            }
                        } else if (mCells[k+1][j].getValue() == mCells[k][j].getValue() && !merged[k+1][j]) {
                            int mergedValue = mCells[k+1][j].getValue() * 2;
                            mCells[k+1][j].setValue(mergedValue);
                            score += mergedValue;
                            mCells[k][j] = null;
                            mCellViews[k+1][j].invalidate();
                            mCellViews[k][j].invalidate();
                            merged[k+1][j] = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
            merged = new boolean[mCells.length][mCells[0].length];
        }
        addRandomCell();
        updateCellsBackground();
        updateCellView();
        updateScore();
        return score;
    }

    public int moveRight() {
        int score = 0;
        boolean[][] merged = new boolean[mCells.length][mCells[0].length]; // matriz booleana para rastrear las celdas que ya se han fusionado

        for (int i = 0; i < mCells.length; i++) {
            for (int j = mCells[0].length - 2; j >= 0; j--) {
                if (mCells[i][j] != null) {
                    for (int k = j + 1; k < mCells[0].length; k++) {
                        if (mCells[i][k] == null) {
                            mCells[i][k] = mCells[i][k-1];
                            mCellViews[i][k].invalidate();
                            mCells[i][k-1] = null;

                            mCellViews[i][k-1].invalidate();
                            if (k < mCells[0].length - 1 && mCells[i][k+1] != null) {
                                mCellViews[i][k+1].invalidate();
                            }
                        } else if (mCells[i][k].getValue() == mCells[i][k-1].getValue() && !merged[i][k]) {
                            int mergedValue = mCells[i][k].getValue() * 2;
                            mCells[i][k].setValue(mergedValue);
                            score += mergedValue;
                            mCells[i][k-1] = null;
                            mCellViews[i][k].invalidate();
                            mCellViews[i][k-1].invalidate();
                            merged[i][k] = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
            merged = new boolean[mCells.length][mCells[0].length];
        }
        addRandomCell();
        updateCellsBackground();
        updateCellView();
        updateScore();
        return score;
    }

    private void updateCellView() {
        for (int i = 0; i < mCellViews.length; i++) {
            for (int j = 0; j < mCellViews[0].length; j++) {
                mCellViews[i][j].setCell(mCells[i][j]);
                mCellViews[i][j].invalidate();

            }
        }
    }




    public boolean loose() {
        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[0].length; j++) {
                if (mCells[i][j] == null) {
                    return false;
                }
            }
        }

        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[0].length; j++) {
                if (i > 0 && mCells[i][j].getValue() == mCells[i-1][j].getValue()) {
                    return false;
                }
                if (j > 0 && mCells[i][j].getValue() == mCells[i][j-1].getValue()) {
                    return false;
                }
                if (i < mCells.length - 1 && mCells[i][j].getValue() == mCells[i+1][j].getValue()) {
                    return false;
                }
                if (j < mCells[0].length - 1 && mCells[i][j].getValue() == mCells[i][j+1].getValue()) {
                    return false;
                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You loose!");
        builder.setMessage("Introduce your name to save your score");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();

                ContentValues values = new ContentValues();
                values.put(DataBase.COLUMN_NAME_2048, name);
                values.put(DataBase.COLUMN_SCORE_2048, score);

                db.insert("Score2048", values);
                finish();

            }
        });

        builder.show();

        return true;
    }


    public boolean win() {
        boolean isWin = false;
        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[0].length; j++) {
                if (mCells[i][j] != null && mCells[i][j].getValue() == 2048) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Congratulations! You win!");
                    builder.setMessage("Introduce your name to save your score");

                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = input.getText().toString();

                            ContentValues values = new ContentValues();
                            values.put(DataBase.COLUMN_NAME_2048, name);
                            values.put(DataBase.COLUMN_SCORE_2048, score);

                            db.insert("Score2048", values);
                            finish();

                        }
                    });

                    builder.show();

                }
            }
        }
        return isWin;
    }

    private void updateScore(){
        score = 0;
        TextView scoreView = findViewById(R.id.score);
        for (int i = 0; i < mCells.length; i++) {
            for (int j = 0; j < mCells[0].length; j++) {
                if (mCells[i][j] != null) {
                    score += mCells[i][j].getValue();
                }
            }
        }
        scoreView.setText("Score: "+String.valueOf(score));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float diffX = e2.getX() - e1.getX();
        float diffY = e2.getY() - e1.getY();

        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX > 0) {
                moveRight();
            } else {
                moveLeft();
            }
        }
        else {
            if (diffY > 0) {
                moveDown();
            } else {
                moveUp();
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }



}