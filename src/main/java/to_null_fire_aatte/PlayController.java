package to_null_fire_aatte;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PlayController {
    
    private BoardGame play;
    private GameStorage storage = new GameStorage();

    @FXML
    TextField username;

    @FXML
    GridPane board;

    @FXML
    Label scoreLabel;

    @FXML
    AnchorPane startUp;

    @FXML
    Label winMessage;

    private boolean gameStarted = false;

    /**
     * Initialiserer et nytt spill og setter opp brettet
     */
    @FXML
    private void initialize() {
        play = new BoardGame();
        play.bootUp();
        createBoard();
        drawBoard();
    }

    /**
     * Metode som setter grunnlaget for brettet i starten
     */
    private void createBoard() {
        board.getChildren().clear();
        board.setPadding(new Insets(10, 0, 0, 10));
        board.setHgap(10);
        board.setVgap(10);
        scoreLabel.setPadding(new Insets(0, 0, 0, 5));
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Label tile = new Label("");
                tile.setMinWidth(45);
                tile.setMinHeight(45);
                tile.setAlignment(Pos.CENTER);
                board.add(tile, y, x);
                tile.setTextFill(Color.web("#8a307f"));
            }
        }
    }

    /**
     * Metode som oppdaterer farge og verdi på tiles, og poengsummen etter hvert trekk
     */
    public void drawBoard() {
        if (play.gameWonCheck()) winMessage(); 
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                Node node = board.getChildren().get(y*4 + x);
                node.setStyle("-fx-background-color: " + play.getTile(x, y).getTileColor() + "; -fx-font-weight: bold;");
                Label text = (Label)node;
                if (play.getTile(x, y).getTileValue() == 0){
                    text.setText("");
                }
                else {
                    text.setText(""+play.getTile(x, y).getTileValue());
                }
                scoreLabel.setText("Score: " + play.getScore());
            }
        }
    }

    /**
     * Metode som sender en "GAME OVER" alert om Exeption e er IllegalStateException
     * @param e tar inn en exepion e
     */
    private void gameOverAlert(Exception e, boolean win) {
        if (e instanceof IllegalStateException) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game finished");
            alert.setHeaderText(null);
            alert.setContentText((win) ? "Game won, you got score: " + play.getScore() + "\n\nLets play again!" : "Game over");
            alert.showAndWait();
            initialize();
        }
        // if (e instanceof IllegalArgumentException) {
        //     //TODO fjerne denne
        //     System.out.println("Umulig å flytte denne veien");
        // }
    }

    /**
     * Metode som skjuler startUp-anchorpanet
     */
    @FXML
    public void startButton() {
        startUp.toBack();
        gameStarted = true;
        initialize();
    }

    @FXML
    public void winMessage() {
        winMessage.setOpacity(1);
        winMessage.setText("You Win");
    }

    
    /**
     * Metode for å lagre et spill. Username som fylles inn av bruker lagres som username.txt. Alert om username er kortere enn 1 eller leneger enn 12 symboler 
     */
    @FXML
    public void saveGame() {
        try {
            storage.saveGame(username.getText() + ".txt", play);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Invalid Username");
            alert.setHeaderText(null);
            alert.setContentText("Username cannot contain less than 1, or more than 12 symbols!!");
            alert.showAndWait();
        }
    }

    /**
     * Laster inn et brett som er lagret i en fil som samsvarer med brukernavnet som skrives inn
     */
    @FXML
    public void loadGame() {
        try {
            this.play = storage.loadGame(username.getText() + ".txt");
            drawBoard();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("fileERROR");
            alert.setHeaderText(null);
            alert.setContentText((e instanceof IOException)? "Username does not exist." : "Username is misspelled.");
            alert.showAndWait();
        }
    }

    /**
     * 
     * @param event 
     */
    public void WASDHandler(KeyEvent event) {
        if (gameStarted) {
            try {
                switch (event.getCode()){
                    case W-> play.UP("move");
                    case A-> play.LEFT("move");
                    case S-> play.DOWN("move");
                    case D-> play.RIGHT("move");
                    default -> System.out.println("invalid key");
                }
            } catch (Exception e) {
                gameOverAlert(e, play.gameWonCheck());
            }
            drawBoard();
        }
        else {
            switch (event.getCode()){
                case A-> startButton();
                case D-> startButton();
                case W-> startButton();
                case S-> startButton();
                default -> System.out.println("feil key");
            }
        }
    }

    @FXML
    public void handleUp() {
        try {
            play.UP("move");
            drawBoard();
        } catch (Exception e) {
            gameOverAlert(e, play.gameWonCheck());
        }        
    }
    
    @FXML
    public void handleDown() {
        try {
            play.DOWN("move");
            drawBoard();
        } catch (Exception e) {
            gameOverAlert(e, play.gameWonCheck());
        }
        
    }
    
    @FXML
    public void handleRight() {
        try {
            play.RIGHT("move");
            drawBoard();
        } catch (Exception e) {
            gameOverAlert(e, play.gameWonCheck());
        }
        
    }
    
    @FXML
    public void handleLeft() {
        try {
            play.LEFT("move");
            drawBoard();
        } catch (Exception e) {
            gameOverAlert(e, play.gameWonCheck());
        }
        
    }
}
