package com.example.a2048;

public class Scores {
    private int id;
    private String name;
    private String score;

    public Scores() {}

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getScore() {
        return this.score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
