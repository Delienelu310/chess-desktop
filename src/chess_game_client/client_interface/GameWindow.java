package chess_game_client.client_interface;

import chess_game_client.client_interface.game_components.Board;
import chess_game_client.client_interface.game_components.GameMenu;
import chess_game_client.client_interface.game_components.PlayerPanel;
import chess_game_client.client_interface.game_components.PromotionWindow;
import chess_game_client.gamelogic.ChessGame;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public abstract class GameWindow extends Stage{

    public ChessApp app;

    public ChessGame chessGame;

    public Board board = new Board(this);
    public PromotionWindow promotionWindow = new PromotionWindow(this);
    
    public PlayerPanel 
        whitePlayerPanel = new PlayerPanel(this),
        blackPlayerPanel = new PlayerPanel(this);
    public VBox menuContainer;
    public GameMenu gameMenu;

    public GameWindow(ChessApp app){     
        
        this.app = app;

        VBox vbox1 = new VBox(blackPlayerPanel, board, whitePlayerPanel);
        VBox vbox2 = new VBox();    //for gamemenu
        this.menuContainer = vbox2;
        HBox hbox = new HBox(vbox1, vbox2);

        Scene scene = new Scene(hbox);
        this.setScene(scene);
    }

    public void turnBoard(){
        VBox vbox = (VBox)whitePlayerPanel.getParent();
        boolean isWhiteBottom = vbox.getChildren().get(0).equals(whitePlayerPanel);

        vbox.getChildren().clear();
        
        //changing placing of player panels
        if(isWhiteBottom){
            vbox.getChildren().addAll(blackPlayerPanel, board, whitePlayerPanel);
        }else{
            vbox.getChildren().addAll(whitePlayerPanel, board, blackPlayerPanel);
        }

        //turning the board around:
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                double size = board.getBoardSize() / 8;
                if(isWhiteBottom){
                    board.physicalCells[i][j].setLayoutY((i * size));
                    board.physicalCells[i][j].setLayoutX((7-i) * size);
                }else{
                    board.physicalCells[i][j].setLayoutY(((7-i) * size));
                    board.physicalCells[i][j].setLayoutX(i * size);
                }
                
            }
        }
        
    }

    public abstract ChessGame.Figure[][] tryMove(ChessGame.Move move);
    public abstract void launch();
}