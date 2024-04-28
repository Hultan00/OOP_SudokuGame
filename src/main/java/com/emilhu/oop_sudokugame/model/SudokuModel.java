package com.emilhu.oop_sudokugame.model;

import java.io.Serializable;
import java.util.Random;

import static com.emilhu.oop_sudokugame.model.SudokuUtilities.GRID_SIZE;

/**
 * Class represents a model of a sudoku grid
 *
 *
 */

public class SudokuModel implements Serializable {

	private static final int Number_Sheet = 0;

	private static final int Answer_Sheet = 1;

	private static final int Number_Not_Set = 0;

	private SudokuSquare[][][] numberTiles;

	private SudokuUtilities.SudokuLevel currentDifficulty;

	public SudokuModel() {
		initNewModel(SudokuUtilities.SudokuLevel.MEDIUM);
	}

	/**
	 * Returns copy of SudokuModel
	 *
	 * @return 2-dimensional SudokuSquare array with Number Sheet
	 *
	 */

	public SudokuSquare[][] getModel(){
		 SudokuSquare[][] copyModel = new SudokuSquare[GRID_SIZE][GRID_SIZE];
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				copyModel[row][col] = new SudokuSquare(numberTiles[row][col][Number_Sheet].getNumber());
				copyModel[row][col].setLocked(numberTiles[row][col][Number_Sheet].isLocked());
			}
		}
		return copyModel;
	}

	/**
	 * Inserts requested number to SudokuModel
	 *
	 * @return true if set number is allowed, else false
	 *
	 */

	public boolean setNumberTile(int row, int col, int number){
		if(numberTiles[row][col][Number_Sheet].getNumber() == Number_Not_Set){
			numberTiles[row][col][Number_Sheet].setNumber(number);
			numberTiles[row][col][Number_Sheet].setShown(true);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Removes requested number from SudokuModel
	 *
	 * @return true if removed number is allowed, else false
	 *
	 */

	public boolean removeNumberTile(int row, int col){
		if((numberTiles[row][col][Number_Sheet].getNumber() != Number_Not_Set) && (!numberTiles[row][col][0].isLocked())){
			numberTiles[row][col][Number_Sheet].setNumber(Number_Not_Set);
			numberTiles[row][col][Number_Sheet].setShown(false);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Compares answer sheet to number sheet
	 *
	 * @return number count not matching the correct answer
	 *
	 */

	public int numbersWrong(){
		int numbers = 0;
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				if(!numberTiles[row][col][0].isShown()){
					continue;
				}
				if(numberTiles[row][col][Number_Sheet].getNumber() != numberTiles[row][col][Answer_Sheet].getNumber()){
					numbers++;
				}
			}
		}
		return numbers;
	}

	/**
	 * Fills one correct number into number sheet
	 *
	 * @return true if action was successful, false if all numbers are already set
	 *
	 */

	public boolean giveHint(){
		boolean allSet = true;
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				if(!numberTiles[row][col][Number_Sheet].isShown()){
					allSet = false;
				}
			}
		}
		if(allSet){
			return false;
		}
		boolean squareVisible = true;
		while (squareVisible){
			Random random = new Random();
			int row = random.nextInt(9);
			int col = random.nextInt(9);
			if(!numberTiles[row][col][Number_Sheet].isShown() && !numberTiles[row][col][Number_Sheet].isLocked()){
				setNumberTile(row, col, numberTiles[row][col][Answer_Sheet].getNumber());
				squareVisible = false;
			}
		}
		return true;
	}

	/**
	 * Resets whole SudokuModel
	 *
	 */

	private void resetModel(){
		for(int x = 0;x < 2;x++) {
			for (int row = 0; row < GRID_SIZE; row++) {
				for (int col = 0; col < GRID_SIZE; col++) {
					numberTiles[row][col][x] = new SudokuSquare(Number_Not_Set);
					numberTiles[row][col][x].setLocked(false);
				}
			}
		}
	}

	/**
	 * Fills SudokuModel with reference from generated matrix
	 *
	 */

	private void createNumberTiles(SudokuUtilities.SudokuLevel difficulty){
		int generatedMatrix[][][] = SudokuUtilities.generateSudokuMatrix(difficulty);
		for(int x = 0;x < 2;x++) {
			for (int row = 0; row < GRID_SIZE; row++) {
				for (int col = 0; col < GRID_SIZE; col++) {
					numberTiles[row][col][x] = new SudokuSquare(generatedMatrix[row][col][x]);
					if(numberTiles[row][col][x].getNumber() != Number_Not_Set){
						numberTiles[row][col][x].setLocked(true);
					}
				}
			}
		}
	}

	/**
	 * Resets SudokuModel and initiates new SudokuModel based on chosen difficulty
	 *
	 */

	public void initNewModel(SudokuUtilities.SudokuLevel difficulty){
		this.currentDifficulty = difficulty;
		this.numberTiles = new SudokuSquare[GRID_SIZE][GRID_SIZE][2];
		resetModel();
		createNumberTiles(difficulty);
	}

	public SudokuUtilities.SudokuLevel getCurrentDifficulty() {
		return currentDifficulty;
	}
}
