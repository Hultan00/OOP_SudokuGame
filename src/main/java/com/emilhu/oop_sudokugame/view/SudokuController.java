package com.emilhu.oop_sudokugame.view;

import com.emilhu.oop_sudokugame.model.SaveGame;
import com.emilhu.oop_sudokugame.model.SudokuModel;
import com.emilhu.oop_sudokugame.model.SudokuUtilities;

import java.io.IOException;

public class SudokuController {
    private SudokuModel sudokuModel;
    private SudokuView sudokuView;

    protected SudokuController(SudokuView sudokuView){
        this.sudokuModel = new SudokuModel();
        this.sudokuView = sudokuView;
        updateGridView();
    }

    /**
     * Retrieves copy of SudokuModel and updates gris view with reference from the model
     *
     *
     */

    private void updateGridView(){
        sudokuView.getGridView().setNumberTiles(sudokuModel.getModel());
    }

    /**
     * Initiates new round in SudokuModel
     *
     *
     */

    protected void onInitNewGameRound(SudokuUtilities.SudokuLevel difficulty){
        sudokuModel.initNewModel(difficulty);
        updateGridView();
    }

    /**
     * Requests a number change/set to SudokuModel
     *
     * @return true if action was allowed and performed, else false
     */

    protected boolean onNumberSet(int row, int col, int numberSet){
        if(!sudokuModel.setNumberTile(row,col,numberSet)){
            return false;
        }
        updateGridView();
        return true;
    }

    /**
     * Requests a number removal to SudokuModel
     *
     * @return true if action was allowed and performed, else false
     */

    protected boolean onNumberRemove(int row, int col){
        if(!sudokuModel.removeNumberTile(row,col)){
            return false;
        }
        updateGridView();
        return true;
    }

    /**
     * Request a int number from SudokuModel
     *
     * @return int with count on numbers wrong
     */

    protected int onCheckCorrectNumbers(){
        return sudokuModel.numbersWrong();
    }

    /**
     * Requests a show hint from SudokuModel
     *
     * @return true if action was allowed and performed
     */

    protected boolean onShowHint(){
        if(!sudokuModel.giveHint()){
            return false;
        }
        updateGridView();
        return true;
    }

    /**
     * Saves SudokuModel to file
     *
     * @return true if action was successfully performed
     */

    protected boolean onSaveGame() throws IOException {
        boolean isSaved;
        isSaved = SaveGame.saveGame(sudokuModel);
        return isSaved;
    }

    /**
     * Loads SudokuModel from file
     *
     * @return true if action was successfully performed
     */

    protected boolean onLoadGame() throws Exception {
        SudokuModel savedModel = SaveGame.loadGame();
        if(savedModel == null){
            return false;
        }else{
            sudokuModel = savedModel;
            updateGridView();
            return true;
        }
    }

    protected SudokuUtilities.SudokuLevel getCurrentDifficulty(){
        return sudokuModel.getCurrentDifficulty();
    }
}
