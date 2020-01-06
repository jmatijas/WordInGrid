package com.josipmatijas.wordingrid;

public class DiceData {

    private int rotation = 0;
    private int letterIndex = 0;
    private String[] letters = new String[6];

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getLetterIndex() {
        return letterIndex;
    }

    public void setLetterIndex(int letterIndex) {
        this.letterIndex = letterIndex;
    }

    public String[] getLetters() {
        return letters;
    }

    // Source: https://en.wikipedia.org/wiki/Talk%3ABoggle
    public static final String[] DICE_STRINGS = new String[]{
            "AAEEGN",
            "ELRTTY",
            "AOOTTW",
            "ABBJOO",
            "EHRTVW",
            "CIMOTU",
            "DISTTY",
            "EIOSST",
            "DELRVY",
            "ACHOPS",
            "HIMNQU",
            "EEINSU",
            "EEGHNW",
            "AFFKPS",
            "HLNNRZ",
            "DEILRX"
    };

}
