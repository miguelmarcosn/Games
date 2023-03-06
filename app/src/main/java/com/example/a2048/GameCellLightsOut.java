package com.example.a2048;

public class GameCellLightsOut {
    private boolean isOn;
    private boolean isClicked;
    private boolean ishelp;

    public GameCellLightsOut(){
        this.isOn = false;
        this.isClicked = false;
        this.ishelp = false;
    }

    public void light(){
        if(isOn){
            setOn(false);
        } else {
            setOn(true);
        }
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean on) {
        this.isOn = on;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void click() {
        if (isClicked) {
            setClicked(false);
        } else {
            setClicked(true);
        }

    }


    public void setClicked(boolean click) {
        this.isClicked = click;
    }

    public boolean ishelp() {
        return ishelp;
    }

    public void sethelp(boolean help) {
        ishelp = help;
    }
}
