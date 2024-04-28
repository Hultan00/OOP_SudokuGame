package com.emilhu.oop_sudokugame.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.*;

/**
 * Class used to save and load save-game
 *
 *
 */

public class SaveGame {

    private static final String FILE_NAME = "SudokuSave.ser";

    /**
     * Creates new file and serializes SudokuModel to file
     *
     * @return True if SudokuModel was successfully saved, else false
     *
     * @throws RuntimeException if file write or read process goes wrong
     */

    public static boolean saveGame(SudokuModel sudokuModel){

        ObjectOutputStream saveStream = null;

        try {
            File f = new File(FILE_NAME);
            ButtonType doOverwrite;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Confirmation");
            alert.setContentText("A save-game already exists, do you want to overwrite current save?");
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);

            if(f.exists()){
                doOverwrite = alert.showAndWait().get();
            }else{
                doOverwrite = yesButton;
                System.out.println(doOverwrite);
            }

            if(doOverwrite == yesButton){
                FileOutputStream fout = new FileOutputStream(FILE_NAME, false);
                saveStream = new ObjectOutputStream(fout);

                saveStream.writeObject(sudokuModel);

                fout.close();
                saveStream.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return saveStream != null;
    }

    /**
     * Creates new file and serializes SudokuModel to file
     *
     * @return Sudokumodel if SudokuModel was successfully saved, else false
     *
     * @throws RuntimeException if file read/load process goes wrong or if SudokuModel class was not found
     */

    public static SudokuModel loadGame() {

        ObjectInputStream loadStream = null;
        SudokuModel savedModel = new SudokuModel();
        savedModel = null;

        try {
            File f = new File(FILE_NAME);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Loading saved game will overwrite current game, proceed?");
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);

            if(f.exists()){
                if(alert.showAndWait().get() == yesButton){
                    FileInputStream fin = new FileInputStream(FILE_NAME);
                    loadStream = new ObjectInputStream(fin);
                    savedModel = (SudokuModel) loadStream.readObject();
                    loadStream.close();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (loadStream != null)
            return savedModel;

        return null;
    }


}
