package com.emilhu.oop_sudokugame.view;

import com.emilhu.oop_sudokugame.model.SudokuSquare;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import static com.emilhu.oop_sudokugame.model.SudokuUtilities.*;

/**
 * Class represents a number grid box view
 *
 */

public class GridView extends VBox{
    private Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private TilePane numberPane;
    private int[] tilePositionClicked;

    protected GridView() {
        tilePositionClicked = new int[2];
        tilePositionClicked[0] = -1;
        tilePositionClicked[1] = -1;
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        // ...
        numberPane = makeNumberPane();
        numberPane.setMaxSize(32*GRID_SIZE+8,32*GRID_SIZE+8);
        numberPane.setMinSize(32*GRID_SIZE+8,32*GRID_SIZE+8);
        // ...
        this.getChildren().addAll(numberPane);
    }

    protected int[] getTilePositionClicked() {
        int copyTilePositionClicked[] = new int[2];
        copyTilePositionClicked[0] = tilePositionClicked[0];
        copyTilePositionClicked[1] = tilePositionClicked[1];
        return copyTilePositionClicked;
    }

    /**
     * Sets all the numbers in the grid
     *
     *
     */

    protected void setNumberTiles(SudokuSquare[][] sudokuSquares){
        resetGrid();
        for(int row = 0;row < GRID_SIZE;row++){
            for(int col = 0;col < GRID_SIZE;col++){
                if(sudokuSquares[row][col].isShown()){
                    this.numberTiles[row][col].setText("");
                    numberTiles[row][col].setText(String.valueOf(sudokuSquares[row][col].getNumber()));
                    if(sudokuSquares[row][col].isLocked()){
                        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18);
                        numberTiles[row][col].setFont(font);
                    }else{
                        Font font = Font.font("Verdana", FontWeight.THIN, FontPosture.REGULAR, 17);
                        numberTiles[row][col].setFont(font);
                    }
                }
            }
        }
    }

    /**
     * Removes all numbers from the grid
     *
     *
     */

    private void resetGrid(){
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                numberTiles[row][col].setText("");
            }
        }
    }

    /**
     * Number box click handler
     *
     *
     */

    private EventHandler<MouseEvent> tileCLickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for(int row = 0; row < GRID_SIZE; row++) {
                for(int col = 0; col < GRID_SIZE; col++) {
                    if(event.getSource() == numberTiles[row][col]) {
                        tilePositionClicked[0] = row;
                        tilePositionClicked[1] = col;
                        return;
                    }
                }
            }
        }
    };

    // called by constructor (only)
    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label tile = new Label(""); // data from model
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;"); // css style
                tile.setOnMouseClicked(tileCLickHandler); // add your custom event handler
                // add new tile to grid
                numberTiles[row][col] = tile;
            }
        }
    }

    private TilePane makeNumberPane() {
        // create the root tile pane
        TilePane root = new TilePane();
        root.setPrefColumns(SECTIONS_PER_ROW);
        root.setPrefRows(SECTIONS_PER_ROW);
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
        TilePane[][] sections = new TilePane[SECTIONS_PER_ROW][SECTIONS_PER_ROW];
        int i = 0;
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SECTION_SIZE);
                section.setPrefRows(SECTION_SIZE);
                section.setStyle( "-fx-border-color: black; -fx-border-width: 0.5px;");

                // add number tiles to this section
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        // calculate which tile and add
                        section.getChildren().add(
                                numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col]);
                    }
                }

                // add the section to the root tile pane
                root.getChildren().add(section);
            }
        }

        return root;
    }
}
