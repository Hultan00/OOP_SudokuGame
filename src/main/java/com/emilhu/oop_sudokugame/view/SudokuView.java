package com.emilhu.oop_sudokugame.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

import static com.emilhu.oop_sudokugame.model.SudokuUtilities.*;

public class SudokuView extends VBox {
    private SudokuController sudokuController;
    private Button checkButton, hintButton;
    private ArrayList<Button> numberButtons;
    private GridView theGrid;

    public SudokuView() {
        this.numberButtons = new ArrayList<>();
        initMainView();
        this.sudokuController = new SudokuController(this);
    }

    private void showAlert(String message,String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected GridView getGridView() {
        return theGrid;
    }

    private void initMainView() {
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFileMenu(),createGameMenu(),createHelpMenu());
        theGrid = new GridView();
        theGrid.setTranslateX(10);
        theGrid.setTranslateY(10);
        checkButton = new Button("Check");
        hintButton = new Button("Hint");
        hintButton.setTranslateX(-5);
        hintButton.setTranslateY(5);

        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAlert(sudokuController.onCheckCorrectNumbers() + " numbers misplaced!", "Numbers Misplaced");
            }
        });

        hintButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!sudokuController.onShowHint()){
                    showAlert("All slots are filled!", "Alert!");
                }
            }
        });

        BorderPane borderPane = new BorderPane();

        TilePane tPleftButtons = new TilePane();

        tPleftButtons.setPrefColumns(1);
        tPleftButtons.setPrefRows(2);
        tPleftButtons.setTranslateX(5);
        tPleftButtons.setTranslateY(100);
        tPleftButtons.getChildren().addAll(checkButton,hintButton);

        borderPane.setCenter(theGrid);
        borderPane.setLeft(tPleftButtons);
        borderPane.setRight(createNumberbuttons());

        this.getChildren().addAll(menuBar, borderPane); // I am a VBox (this)
    }

    private EventHandler<MouseEvent> buttonCLicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for(int x = 0; x < 10; x++) {
                if(event.getSource() == numberButtons.get(x)) {
                    if(numberButtons.get(x).getText().equals("C")){
                        sudokuController.onNumberRemove(theGrid.getTilePositionClicked()[0],theGrid.getTilePositionClicked()[1]);
                    }else {
                        sudokuController.onNumberSet(theGrid.getTilePositionClicked()[0],theGrid.getTilePositionClicked()[1], Integer.parseInt(numberButtons.get(x).getText()));
                    }
                    return;
                }
            }
        }
    };
    private TilePane createNumberbuttons(){
        TilePane TPnumberButtons = new TilePane();
        TPnumberButtons.setPrefColumns(1);
        TPnumberButtons.setPrefRows(10);
        TPnumberButtons.setTranslateX(-20);
        TPnumberButtons.setTranslateY(10);

        for(int x = 0;x < 10;x++){
            if(x == 0){
                numberButtons.add(new Button());
                numberButtons.get(x).setText("C");
            }else{
                numberButtons.add(new Button());
                numberButtons.get(x).setText(String.valueOf(x));
            }
            numberButtons.get(x).setOnMouseClicked(buttonCLicked);
            numberButtons.get(x).setTranslateY(x*2);
            TPnumberButtons.getChildren().add(numberButtons.get(x));
        }
        return TPnumberButtons;
    }

    /**
     * Creates file menu options and action event handlers to options
     *
     *
     */

    private Menu createFileMenu() {
		MenuItem exitItem = new MenuItem("Exit");
        MenuItem loadGameItem = new MenuItem("Load Game");
        MenuItem saveGameItem = new MenuItem("Save Game");
		exitItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});

        loadGameItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Loading game..");
                try {
                    if(sudokuController.onLoadGame()){
                        showAlert("Game loaded!", "Information");
                    }else{
                        showAlert("Save-game does not exist!", "Information");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        saveGameItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Saving...");
                try {
                    if(sudokuController.onSaveGame()){
                        showAlert("Game has been saved!", "Information");
                    }else{
                        showAlert("Game has not been saved!", "Information");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

		Menu fileMenu = new Menu("File");

        fileMenu.getItems().addAll(loadGameItem);
        fileMenu.getItems().addAll(saveGameItem);
        fileMenu.getItems().addAll(exitItem);

		return fileMenu;
    }

    /**
     * Creates game menu options and action event handlers to options
     *
     *
     */

    private Menu createGameMenu() {
        Menu DiffMenu = new Menu("Difficulty");
        MenuItem easyItem = new MenuItem("Easy");
        MenuItem normalItem = new MenuItem("Normal");
        MenuItem hardItem = new MenuItem("Hard");

        MenuItem newGameItem = new MenuItem("New Game");

        newGameItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Creating New Game...");
                sudokuController.onInitNewGameRound(sudokuController.getCurrentDifficulty());
            }
        });

        easyItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Creating easy game...");
                sudokuController.onInitNewGameRound(SudokuLevel.EASY);
            }
        });

        normalItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Creating normal game...");
                sudokuController.onInitNewGameRound(SudokuLevel.MEDIUM);
            }
        });

        hardItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Creating hard game...");
                sudokuController.onInitNewGameRound(SudokuLevel.HARD);
            }
        });

        Menu gameMenu = new Menu("Game");

        DiffMenu.getItems().addAll(easyItem,normalItem,hardItem);

        gameMenu.getItems().addAll(newGameItem, DiffMenu);



        return gameMenu;
    }

    /**
     * Creates help menu
     *
     *
     */

    private Menu createHelpMenu() {
        Menu helpMenu = new Menu("Help");

        MenuItem infoItem = new MenuItem("Play Rules");
        helpMenu.getItems().addAll(infoItem);

        infoItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAlert("Sudoku is played on a grid of 9 x 9 spaces. " +
                        "Within the rows and columns are 9 “squares” (made up of 3 x 3 spaces). " +
                        "Each row, column and square (9 spaces each) needs to be filled out with the numbers 1-9, " +
                        "without repeating any numbers within the row, column or square","Sudoku Play Rules");
            }
        });

        return helpMenu;
    }
}
