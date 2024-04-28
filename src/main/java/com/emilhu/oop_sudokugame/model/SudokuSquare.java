package com.emilhu.oop_sudokugame.model;

import java.io.Serializable;

/**
 * Class represents a model form one sudoku number box
 *
 */

public class SudokuSquare implements Serializable {

    /**
     *
     * @param number The number represented in the view
     * @param isShown If number shall be shown in the view
     * @param isLocked If the number can be changed by user / number is a starting number
     *
     */
    private int number;
    private boolean isShown;
    private boolean isLocked;

    protected SudokuSquare(int number){
        this.number = number;
        if(this.number == 0){
            this.isShown = false;
        }else {
            this.isShown = true;
            this.isLocked = false;
        }
    }

    public boolean isLocked() {
        return isLocked;
    }

    protected void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    public int getNumber() {
        return number;
    }

    protected void setNumber(int number) {
        this.number = number;
    }

    public boolean isShown() {
        return isShown;
    }

    protected void setShown(boolean shown) {
        this.isShown = shown;
    }
}
