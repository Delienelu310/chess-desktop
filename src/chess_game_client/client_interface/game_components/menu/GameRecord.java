package chess_game_client.client_interface.game_components.menu;

import chess_game_client.client_interface.game_components.GameMenu;
import chess_game_client.gamelogic.ChessGame.Move;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class GameRecord extends HBox{

    GameMenu menu;

    VBox whiteMoves = new VBox();    
    VBox blackMoves = new VBox();

    public GameRecord(GameMenu menu){

        this.menu = menu;

        this.getChildren().addAll(whiteMoves, blackMoves);
    
        this.setOnMouseClicked((e) -> {
            if(! (e.getSource() instanceof RecordButton)) return;
            //
        });
    }

    
    public class RecordButton extends Button{
        RecordButton(String record){
            super(record);
        }
    } 
    
    public void addMove(Move move){
        //firstly we make move into record, then write it down
    }

    public void addMove(String record, String queue){
        if(queue.equals("white")){
            whiteMoves.getChildren().add(new RecordButton(record));
        }else{
            blackMoves.getChildren().add(new RecordButton(record));
        }
    };
}
