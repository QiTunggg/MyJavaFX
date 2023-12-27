package myjavafx3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class TicTacToe extends Application {

    private char whoseTurn = 'X';
    private Cell[][] cell = new Cell[3][3];
    private Label lblStatus = new Label("X's turn to play");

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane(); // Pane to hold cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pane.add(cell[i][j] = new Cell(), j, i);
            }
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);
        Scene scene = new Scene(borderPane, 450, 170);
        primaryStage.setTitle("TicTacToe"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public boolean isFull() {
        // Determine if all cells are occupied
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cell[i][j].getToken() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isWon(char token) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (cell[i][0].getToken() == token && cell[i][1].getToken()
                    == token && cell[i][2].getToken() == token) {
                return true;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (cell[0][j].getToken() == token && cell[1][j].getToken()
                    == token && cell[2][j].getToken() == token) {
                return true;
            }
        }
        // Check major diagonal
        if (cell[0][0].getToken() == token && cell[1][1].getToken() == token
                && cell[2][2].getToken() == token) {
            return true;
        }
        // Check subdiagonal
        if (cell[0][2].getToken() == token && cell[1][1].getToken() == token
                && cell[2][0].getToken() == token) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class Cell extends Pane {

        private char token = ' ';

        public Cell() {
            setStyle("-fx-border-color: black");
            setPrefSize(2000, 2000);
            setOnMouseClicked(e -> handleMouseClick());
        }

        public char getToken() {
            return token;
        }

        public void setToken(char c) {
            token = c;

            if (token == 'X') {
                Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                Line line2 = new Line(10, this.getHeight() - 10,
                        this.getWidth() - 10, 10);
                line2.startYProperty().bind(this.heightProperty().subtract(10)
                );
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                getChildren().addAll(line1, line2);
            } else if (token == 'O') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2,
                        this.getHeight() / 2,
                        this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2)
                );
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).
                        subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2)
                        .subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);
                getChildren().add(ellipse);
            }
        }

        private void handleMouseClick() {
            if (token == ' ' && whoseTurn != ' ') {
                setToken(whoseTurn); // Set token in the cell
                if (isWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn + " won! The game is over");
                    whoseTurn = ' '; // Game is over
                } else if (isFull()) {
                    lblStatus.setText("Draw! The game is over");
                    whoseTurn = ' '; // Game is over
                } else {
                    // Change the turn
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    // Display whose turn
                    lblStatus.setText(whoseTurn + "'s turn");
                }
            }
        }
    }
}