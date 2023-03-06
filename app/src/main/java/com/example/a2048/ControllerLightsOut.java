package com.example.a2048;
import android.view.View;
import android.widget.ImageButton;


public class ControllerLightsOut {
    BoardLightsOut board;
    BoardLightsOut copyBoard;
    ImageButton[][] buttons;

    public ControllerLightsOut(ImageButton[][] bArray) {
        this.buttons = bArray;
        this.board = new BoardLightsOut(this.buttons.length, this.buttons[0].length);
        board.randomize();
        this.copyBoard = new BoardLightsOut(board);
        board.flush();
        updateView();
    }

    public void updateView(){
        for (int i = 0; i <buttons.length; i++){
            for (int j = 0; j < buttons[i].length; j++) {
                if (board.getPos(i,j).ishelp()) {
                    buttons[i][j].setImageResource(R.drawable.help2);
                    buttons[i][j].setVisibility(View.VISIBLE);
                } else if (board.getPos(i,j).isOn()) {
                    buttons[i][j].setImageResource(R.drawable.on2);
                    buttons[i][j].setVisibility(View.VISIBLE);
                } else if (!board.getPos(i,j).isOn()) {
                    buttons[i][j].setImageResource(R.drawable.off2);
                    buttons[i][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void click(int i, int j){
        board.click(i,j);
        updateView();
    }

    public void retryBoard(){
        board = copyBoard;
        copyBoard = new BoardLightsOut(copyBoard);
        updateView();
    }

    public void help(){
        board.help(copyBoard);
        updateView();
    }

    public boolean win(){

        return this.board.win();
    }
}
