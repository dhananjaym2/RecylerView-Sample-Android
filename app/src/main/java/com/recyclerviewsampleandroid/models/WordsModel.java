package com.recyclerviewsampleandroid.models;

/**
 * Created by dhananjay on 14/1/16.
 */
public class WordsModel {

    private int id_WordsModel;
    private String word;
    private int variant;
    private String meaning;
    private double ratio;

    public WordsModel() {
    }

    public WordsModel(int id_WordsModel, String word, int variant, String meaning, double ratio) {
        this.id_WordsModel = id_WordsModel;
        this.word = word;
        this.variant = variant;
        this.meaning = meaning;
        this.ratio = ratio;
    }

    public int getId_WordsModel() {
        return id_WordsModel;
    }

    public void setId_WordsModel(int id_WordsModel) {
        this.id_WordsModel = id_WordsModel;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}