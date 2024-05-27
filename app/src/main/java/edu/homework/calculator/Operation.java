package edu.homework.calculator;

import androidx.annotation.NonNull;

public enum Operation {

    addition("+"),
    subtraction("-"),
    multiplication("×"),
    division("÷");

    private String simbol;

    Operation(String simbol) {
        this.simbol = simbol;
    }

    @NonNull
    @Override
    public String toString() {
        return simbol;
    }
}