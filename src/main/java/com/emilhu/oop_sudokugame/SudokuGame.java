package com.emilhu.oop_sudokugame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.emilhu.oop_sudokugame.model.SudokuModel;
import com.emilhu.oop_sudokugame.view.SudokuView;

/**
 * Creates a window and ties model, view and controller together.
 */
public class SudokuGame extends Application {

    @Override
    public void start(Stage primaryStage) {

        SudokuView view = new SudokuView();
        Scene scene = new Scene(view, 420, 350);
        primaryStage.setTitle("Sudoku Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
